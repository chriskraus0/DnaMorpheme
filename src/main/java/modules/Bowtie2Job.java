package modules;

// Imports.

// Java I/O imports.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.FileOutputStream;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

// Project-specific imports.
import core.InputPort;
import core.ModuleNode;
import core.PhysicalConstants;
import core.VerifiedExternalPrograms;
import core.common.CommandState;
import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;

import modules.commands.Commands;

// Project-specific exceptions.
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;
import storage.SequenceStorage;
import storage.exceptions.TruncatedFastaHeadException;

/**
 * This class provides bowtie2 jobs. These guarantee that reference fasta files 
 * are properly indexed and bowtie2 is run and a SAM output is saved.
 * @author Christopher Kraus
 *
 */
public class Bowtie2Job extends Module {

	// Variables.
	
	// HashMap holding all the commands to be parsed.
	private Map<Commands, String> command;
		
	// Module node of the current job.
	private ModuleNode moduleNode;
	
	// Input retrieved from input port.
	private String input;
			
	// SequenceStorage object for storing fasta data.
	private SequenceStorage fastaStorage;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public Bowtie2Job(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, HashMap<Commands, String> cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		
		// Call Logger to get new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
		
		// Instantiate the fasta storage.
		this.fastaStorage = new SequenceStorage();
	}

	// Methods.
	
	@Override
	public void run () {
		try {
			CommandState cState = this.callCommand();
			if (cState.equals(CommandState.SUCCESS)) {
				this.setModuleState(ModuleState.SUCCESS);
				this.moduleNode.notifyModuleObserver();
			}
		} catch (CommandFailedException ce) {
			System.err.println(ce.getMessage());
			ce.printStackTrace();
		}
	}
	
	
	public synchronized CommandState callCommand() throws CommandFailedException {
		this.moduleNode = this.getSuperModuleNode();
		
		CommandState cState = CommandState.STARTING;
					
		// Checked exception. TODO: Add ExternalCommandHandler
		
		// Save input from pipe
		this.input = "";
				
		// Start processing of the command.
		cState = CommandState.PROCESSING;
		
		// Save number of read characters.
		int charNumber = 0;
	
		// Prepare char buffer "data".
		int bufferSize = 1024;
		char[] data = new char[bufferSize];
		
		// Read from InputPort (via CharPipe).		
		try {
				charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
				
				while (charNumber != -1) {
												
					// Check for interrupted threads.
					
					if (Thread.interrupted()) {
						this.getInputPort().getPipe().readClose();
						this.getOutputPort().getPipe().writeClose();
						throw new InterruptedException ("Thread interrupted");
					}
					
					// If the number of read characters is greater than the buffer limit 
					// write the remaining characters from the data[] char array 
					// as new String into the variable "input".
					if (charNumber < bufferSize) {
						for (int i = 0; i < charNumber; i++)
						this.input += data[i];
					} else {
						this.input += new String(data);
					}
					
					charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
					
				}
	
				this.getInputPort().getPipe().readClose();
		
						
		} catch (PipeTypeNotSupportedException pe) {
			this.logger.log(Level.SEVERE, pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		} 
		
		catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		}
	
		this.setModuleState(ModuleState.INPUT_DONE);
		this.moduleNode.notifyModuleObserver();
		
		// Store fasta input in SequenceStorage.class.
		try {
			this.fastaStorage.parseMultipleFasta(this.input);
		} catch (TruncatedFastaHeadException te) {
			this.logger.log(Level.SEVERE, te.getMessage());
			te.printStackTrace();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
								
		// EXTERNAL COMMANDS:
		
		// If options "reference" and "index_base" are provided run bowtie2-build.
		if (this.command.containsKey(Commands.reference) 
				&& this.command.containsKey(Commands.index_base)) {
						
			// Parse the bowtie2-build command.
			String [] bowtie2BuildCommand = this.parseBowtie2BuildCommand();
			
			String stdInLine="";
			String stdErrLine = "";
			String bowtie2BuildOutput="";
			String bowtie2BuildErr="";
			
			try {
				// Start an external process with the pre-defined command array.
			    Process process = Runtime.getRuntime().exec(bowtie2BuildCommand);
			    
			    // Read the STDIN from the unix process.
			    Reader stdr = new InputStreamReader(process.getInputStream());
			    
			    // Read the STDERR fromt he unix process.
			    Reader stderr = new InputStreamReader(process.getErrorStream());
			    
			    // Read line by line from the STDIN.
			    BufferedReader stdIn = new BufferedReader(stdr);
			    while((stdInLine = stdIn.readLine()) != null) {
			    	bowtie2BuildOutput += stdInLine + PhysicalConstants.getNewLine();
			    }
			    
			    // Read line by line from the STDERR.
			    BufferedReader stdErr = new BufferedReader(stderr);
			    while ((stdErrLine = stdErr.readLine()) != null) {
			    	bowtie2BuildErr += stdErrLine + PhysicalConstants.getNewLine();
			    }
			    
			    // Close the buffered readers.
			    stdErr.close();
			    stdIn.close();
			    
			} catch (IOException ie) {
				this.logger.log(Level.SEVERE, ie.getMessage());
				ie.printStackTrace();
			}
			
			// Print the bowtie2 output.
	    	this.logger.log(Level.INFO, bowtie2BuildOutput);
	    		    	
	    	// If there was an error throw a new exception.
	    	if (!bowtie2BuildErr.isEmpty()
	    			&& !(bowtie2BuildErr.indexOf("Building a SMALL index") == 0)) {
	    		throw new CommandFailedException (
	    				VerifiedExternalPrograms.getBowtie2BuildPath() + 
	    				PhysicalConstants.getPathSeparator() + 
	    				VerifiedExternalPrograms.getBowtie2BuildExe() +
	    				": ERROR: " + bowtie2BuildErr);
	    	}
	    	
		}
		
		// Parse the bowtie2 command.
		String [] bowtie2Command = this.parseBowtie2Command();
		
		// Run bowtie2 command.
		String stdInLine = "";
		String bowtie2Output = "";
		String stdErrLine = "";
		String bowtie2Err = "";
		
		try {
			// Start an external process with the pre-defined command array.
		    Process process = Runtime.getRuntime().exec(bowtie2Command);
		    
		    // Read the STDIN from the unix process.
		    Reader stdInReader = new InputStreamReader(process.getInputStream());
		    
		    // Read the STDERR form the unix process.
		    Reader stdErrReader = new InputStreamReader(process.getErrorStream());
		    
		    // Read line by line from the STDIN.
		    BufferedReader stdIn = new BufferedReader(stdInReader);
		    while((stdInLine = stdIn.readLine()) != null) {
		    	bowtie2Output += stdInLine + PhysicalConstants.getNewLine();
		    }
		    
		    // Read line by line from the STDERR.
		    BufferedReader stdErr = new BufferedReader(stdErrReader);
		    while ((stdErrLine = stdErr.readLine()) != null) {
		    	bowtie2Err += stdErrLine + PhysicalConstants.getNewLine();
		    }
		    
		    // Close buffered readers.
		    stdErr.close();
		    stdIn.close();
		    
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		}
		
		// Print the bowtie2 output.
    	this.logger.log(Level.INFO, bowtie2Output);
    	
    	if (!bowtie2Err.isEmpty()
    			&& !(bowtie2Err.indexOf("of these") > 8)) {
    		throw new CommandFailedException(
    				VerifiedExternalPrograms.getBowtie2Path()
    				+ PhysicalConstants.getPathSeparator()
    				+ VerifiedExternalPrograms.getBowtie2Exe()
    				+ ": Error: " + bowtie2Err);
    	} else {
    		// If everything works fine bowtie2 will write the results to the STDERR.
    		// I don't know why the authors intended to write it to the STDERR. *sigh*
    		this.logger.log(Level.INFO, bowtie2Err);
    	}
	    	
    	// TODO: This is not satisfactory. We can not do calculations in the root folder and move
    	// files afterwards. We have to change to a working directory either in the space the 
    	// user intended, or in the tmpData folder. This need to be changed.
    	 
    	// Move the resulting index files to the folder tmpData
    	
    	// ArrayList<File> holding the bt2 files.
    	ArrayList<Path> bt2Files = new ArrayList<Path>();
    	
    	// Globbing the *bt2 files by defining a PathMatcher.
    	PathMatcher bt2matcher = FileSystems.getDefault().getPathMatcher("glob:**.bt2");
    	
    	try {
	    	Files.walkFileTree(Paths.get("."), new SimpleFileVisitor<Path>()  {
	    		@Override
	    		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	    			if (bt2matcher.matches(file)) {
	    				bt2Files.add(file);
	    				
	    				// Copy the file and delete afterwards.
	    						    				
	    				// Open a new output stream to copy the file to.
	    				OutputStream ofStream = new FileOutputStream(
	    						"."
	    						+ PhysicalConstants.getPathSeparator()
	    						+ "tmpData"
	    						+ PhysicalConstants.getPathSeparator()
	    						+ file.getFileName());
	    				
	    				
	    				// Native I/O File.copy handles the copy process.
	    				Files.copy(file, ofStream);
	    						    				
	    				// Close the output stream after finishing.
	    				ofStream.close();
	    				
	    				// Delete the old file.
	    				File oldFile = file.toFile();
	    				oldFile.delete();
	    				
	    			}
	    			return FileVisitResult.CONTINUE;
	    		}
	    		
	    		@Override 
	    		public FileVisitResult visitFileFailed (Path file, IOException iex) throws IOException {
	    			return FileVisitResult.CONTINUE;
	    		}
	    	});
    	} catch (IOException ie) {
    		this.logger.log(Level.SEVERE, ie.getMessage());
    		ie.printStackTrace();
    	}
						    	
		/*
		// Write to OutputPort (via CharPipe).
		try {
			
			// Write to the output pipe.
			((OutputPort) this.getOutputPort()).writeToCharPipe(this.output);
			
			this.getOutputPort().getPipe().writeClose();
			
			this.setModuleState(ModuleState.OUTPUT_DONE);
			this.getSuperModuleNode().notifyModuleObserver();
			
			
		} catch (PipeTypeNotSupportedException pe) {
			this.logger.log(Level.SEVERE, pe.getMessage());
			pe.printStackTrace();
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		} 
		*/	
				
		// If everything worked out return SUCCESS.
		cState = CommandState.SUCCESS;	
		
		return cState;
	}
	
	/**
	 * This private method parses the incoming options to a String[] array
	 * for the program bowtie2-build.
	 * @return String[] commands
	 * @throws CommandFailedException
	 */
	private String[] parseBowtie2BuildCommand () {
		ArrayList<String> newCommand = new ArrayList<String>();
		
		// Add the absolute path and the executable to the command.
		newCommand.add(VerifiedExternalPrograms.getBowtie2BuildPath() 
				+ PhysicalConstants.getPathSeparator()
				+ VerifiedExternalPrograms.getBowtie2BuildExe());
		
		// Add the reference and the index base options.
		newCommand.add(this.command.get(Commands.reference));
		newCommand.add(this.command.get(Commands.index_base));
			
		// Convert to an String[] array.		
		String[] finishedCommand = new String[newCommand.size()];
		
		for (int i = 0; i < newCommand.size(); i ++)
			finishedCommand[i] = newCommand.get(i);
		
		return finishedCommand;
	}
	
	private String[] parseBowtie2Command () throws CommandFailedException {
		ArrayList<String> newCommand = new ArrayList<String>();
		
		// Add the absolute path and the executable to the command.
		newCommand.add(VerifiedExternalPrograms.getBowtie2Path()
				+ PhysicalConstants.getPathSeparator() + VerifiedExternalPrograms.getBowtie2Exe());
		
		// Parse the option "x" for the index base for the bowtie 2 index.
		newCommand.add("-" + Commands.x.toString());
		newCommand.add(this.command.get(Commands.x));
		
		// Parse the alternative read types (either single-end or paired-end reads).
		// The "U" option was used thus these are single-end reads.
		if (this.command.containsKey(Commands.U)) {
			newCommand.add("-" + Commands.U.toString());
			newCommand.add(this.command.get(Commands.U));
		}
		
		// The "m1" and "m2" option was used, thus these are pair-end reads.
		else if (this.command.containsKey(Commands.m1)
				&& this.command.containsKey(Commands.m2)) {
			// Forward reads.
			newCommand.add("-" + Commands.m1.toString());
			newCommand.add(this.command.get(Commands.m1));
			// Reverse reads.
			newCommand.add("-" + Commands.m2.toString());
			newCommand.add(this.command.get(Commands.m2));
			
		}
		
		// None was used. Throw a CommandFailedException.
		else {
			throw new CommandFailedException("ERROR: Neither option for single-end reads (\"U\")"
					+ ", nor options for paired-end reads (\"m1\" and \"m2\") were provided.");
		}
		
		// Parse the option "S" (location of an output SAM file).
		newCommand.add("-" + Commands.S.toString());
		newCommand.add(this.command.get(Commands.S));
		
		// Convert to an String[] array.
		// TODO: Isn't there a better solution?
		
		String[] finishedCommand = new String[newCommand.size()];
		
		for (int i = 0; i < newCommand.size(); i ++)
			finishedCommand[i] = newCommand.get(i);
		
		return finishedCommand;
	}
	

}
