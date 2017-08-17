package modules;

//Imports.

// Java utility imports.
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

import core.InputPort;
// Project-specific imports.
import core.ModuleNode;
import core.PhysicalConstants;
import core.VerifiedExternalPrograms;
import core.common.CommandState;
import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;
import core.common.PortState;
import modules.commands.Commands;

import storage.SequenceStorage;

// Project-specific exceptions.
import storage.exceptions.TruncatedFastaHeadException;
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;
import externalStorage.ExtStorageType;

public class SamtoolsJob extends Module {
	
	// Variables.
	
		// Logger.
		private Logger logger;
		
		// External commands for the Samtools software.
		private Map<Commands, String> command;
		
		// Module node of the current job.
		private ModuleNode moduleNode;

		// Input retrieved from input port.
		private String input;
		
		// Output to save a specified location.
		private String output;
		
		// Variable holding the hammingDistance.
		private double hammingDistance;
		
		// External output file.
		private String outputFile;
			
		// SequenceStorage object for storing fasta data.
		private SequenceStorage fastaStorage;
		
		// The type of current samtools job.
		private SamtoolsJobType samtoolsJobType;
		
	// Constructors.
	public SamtoolsJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, HashMap<Commands, String> cmd, double hammingDistance) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		
		// Create new fasta storage.
		this.fastaStorage = new SequenceStorage();
		
		// Initialize the hammingDistance.
		this.hammingDistance = hammingDistance;
		
		// Call Logger to get a new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Methods.
	@Override
	public void run () {
		try {
			CommandState cState = this.callCommand();
			if (cState.equals(CommandState.SUCCESS)) {
				this.setModuleState(ModuleState.SUCCESS);
				this.moduleNode.notifyModuleObserver();
				// Notify the ModuleObserver about the saved external file.
				if (! (this.samtoolsJobType == SamtoolsJobType.TVIEW_BAM))
					this.moduleNode.notifyModuleObserverOutput(this.outputFile, ExtStorageType.BAM_EXT_STORAGE, this.hammingDistance);
				// TODO: else
					// this.moduleNode.notifyModuleObserverOutput(this.outputFile, ExtStorageType.TVIEW_EXT_STORAGE, this.hammingDistance);
			}
		} catch (CommandFailedException ce) {
			System.err.println(ce.getMessage());
			ce.printStackTrace();
		}
	}

	public synchronized CommandState callCommand() throws CommandFailedException {
		
		this.moduleNode = super.getSuperModuleNode();
		
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
			
			// Test whether this port is connected.
			if (this.getInputPort().getPortState() == PortState.CONNECTED) {
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
		
			}
						
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
		if (!this.input.isEmpty()) {
			try {
				this.fastaStorage.parseMultipleFasta(this.input);
			} catch (TruncatedFastaHeadException te) {
				this.logger.log(Level.SEVERE, te.getMessage());
				te.printStackTrace();
			} catch (Exception e) {
				this.logger.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
				
		
		// Parse the samtools command.
		
		String [] newCommands;
		
		// Parse "SAM to BAM conversion" command.
		if (this.command.containsKey(Commands.view) 
				&& this.command.containsKey(Commands.b)
				&& this.command.containsKey(Commands.S)) {
			newCommands = this.parseSamToBamCommand();
			this.samtoolsJobType = SamtoolsJobType.SAM_2_BAM;
		} 
		// Parse "remove non-mapping reads from BAM" command.
		else if (this.command.containsKey(Commands.view)
				&& this.command.containsKey(Commands.F)
				&& this.command.containsKey(Commands.b)) {
			newCommands = this.parseSamRemoveUnmappedCommand();
			this.samtoolsJobType = SamtoolsJobType.REMOVE_READS_BAM;
		} 
		// Parse the "sort SAM file" command.
		else if (this.command.containsKey(Commands.sort)) {
			newCommands = this.parseBamSortCommand();
			this.samtoolsJobType = SamtoolsJobType.SORT_BAM;
		} 
		// Parse the "index BAM file" command.
		else if (this.command.containsKey(Commands.index)) {
			newCommands = this.parseBamIndexCommand();
			this.samtoolsJobType = SamtoolsJobType.INDEX_BAM;
		}
		// Parse the "index reference" command.
		else if (this.command.containsKey(Commands.faidx)) {
			newCommands = this.parseSamRefIndexCommand();
			this.samtoolsJobType = SamtoolsJobType.INDEX_FASTA;
		} 
		// Parse the "show tview for specific region and convert to text" command.
		else if (this.command.containsKey(Commands.tview)) {
			newCommands = this.parseSamTviewCommand();
			this.samtoolsJobType = SamtoolsJobType.TVIEW_BAM;
		} 		
		// If crucial options for samtools are missing throw an exception.
		else {
			throw new CommandFailedException (
					VerifiedExternalPrograms.getSamtoolsPath()
					+ PhysicalConstants.getPathSeparator()
					+ VerifiedExternalPrograms.getSamtoolsExe()
					+ ": Error: Required options were not provided.");
		}
		
		// Save output file and path.
		this.outputFile = this.command.get(Commands.output);
		
		// External command.
		
		String stdInline="";
		String samtoolsOutput="";
		String stdErrLine="";
		String samtoolsErr="";
		
		try {
			// Start an external process with the pre-defined command array.
		    Process process = Runtime.getRuntime().exec(newCommands);
		    
		    // Read the STDIN from the unix process.
		    Reader stdInR = new InputStreamReader(process.getInputStream());
		    
		    // Read the STDERR from the unix process.
		    Reader stdErrR = new InputStreamReader(process.getErrorStream());
		    
		    // Read the STDIN line by line using the BufferedReader.
		    BufferedReader stdInBr = new BufferedReader(stdInR);
		    while((stdInline = stdInBr.readLine()) != null) {
		    	samtoolsOutput += stdInline + PhysicalConstants.getNewLine();
		    }
		    
		    // read the STDERR line by line.
		    BufferedReader stdErrBr = new BufferedReader(stdErrR);
		    while((stdErrLine = stdErrBr.readLine()) != null) {
		    	samtoolsErr += stdErrLine + PhysicalConstants.getNewLine();
		    }
		    
		    // If there is output from STDERR throw new exception.
		    if (!samtoolsErr.isEmpty()) {
		    	throw new CommandFailedException(
		    			VerifiedExternalPrograms.getSamtoolsPath()
						+ PhysicalConstants.getPathSeparator() 
						+ VerifiedExternalPrograms.getSamtoolsExe()
						+ ": Error:"
						+ samtoolsErr);
		    }
		    
		    // Close buffered readers.
		    stdErrBr.close();
		    stdInBr.close();
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		}
		
		// Print the cd-hit output.
    	this.logger.log(Level.INFO, samtoolsOutput);
		
		
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
	 * This method parses the "convert SAM to BAM" command.
	 * @return String[] convertCommand
	 */
	private String[] parseSamToBamCommand () {
		
		String[] convertCommand = new String[6];
		
		// Add the absolute path and the executable to the command.
		convertCommand[0] = VerifiedExternalPrograms.getSamtoolsPath()
				+ PhysicalConstants.getPathSeparator() 
				+ VerifiedExternalPrograms.getSamtoolsExe();
		
		// Add the "view" command.
		convertCommand[1] = Commands.view.toString();
		
		// Add the "read SAM format" and "write BAM file format" command.
		convertCommand[2] = "-" + Commands.b.toString() + Commands.S.toString();
		
		// Add the "input" command.
		convertCommand[3] = this.command.get(Commands.input);
		
		// Add the "output file" command.
		convertCommand[4] = "-" + Commands.o.toString();
		convertCommand[5] = this.command.get(Commands.o);
				
		return convertCommand;
	}
	
	/**
	 * This method parses the "Sort BAM files" command.
	 * @return
	 */
	private String[] parseBamSortCommand() {
		
		String[] sortCommand = new String[3];
		
		// Add the absolute path and the executable to the command.
		sortCommand[0] = VerifiedExternalPrograms.getSamtoolsPath()
				+ PhysicalConstants.getPathSeparator() 
				+ VerifiedExternalPrograms.getSamtoolsExe();
		
		// Add the "sort" command.
		sortCommand[1] = Commands.sort.toString();
		
		// Add the "input" command.
		sortCommand[2] = this.command.get(Commands.input);
		
		// Add the "output" command.
		sortCommand[3] = this.command.get(Commands.output);
						
		return sortCommand;
	}
	
	/**
	 * This method parses the "remove unmapped reads" command.
	 * @return
	 */
	private String[] parseSamRemoveUnmappedCommand() {
		
		String[] uniqCommand = new String[7];
		
		// Add the absolute path and the executable to the command.
		uniqCommand[0] = VerifiedExternalPrograms.getSamtoolsPath()
				+ PhysicalConstants.getPathSeparator() 
				+ VerifiedExternalPrograms.getSamtoolsExe();
		
		// Add the "view" command.
		uniqCommand[1] = Commands.view.toString();
		
		// Add the "flag #4 (not mapping)" command.
		uniqCommand[2] = "-" + Commands.F.toString();
		uniqCommand[3] = this.command.get(Commands.F);
		
		// Add the "BAM output format" command.
		uniqCommand[4] = "-" + Commands.b.toString();
		
		// Add the "input" command.
		uniqCommand[4] = this.command.get(Commands.input);
		
		// Add the "output file" command.
		uniqCommand[5] = Commands.o.toString();
		uniqCommand[6] = this.command.get(Commands.o);
		
		return uniqCommand;
	}
	
	/**
	 * This method parses the "BAM indexing" command.
	 * @return String[] indexCommand
	 */
	private String[] parseBamIndexCommand() {
		String[] indexCommand = new String[3];
		
		// Add the absolute path and the executable to the command.
		indexCommand[0] = VerifiedExternalPrograms.getSamtoolsPath()
			+ PhysicalConstants.getPathSeparator() 
			+ VerifiedExternalPrograms.getSamtoolsExe();
		
		// Add "index" command.
		indexCommand[1] = Commands.index.toString();
		
		// Add "input" command.
		indexCommand[2] = this.command.get(Commands.input);
		
		return indexCommand;
	}
	
	/**
	 * This method parses the "index for fasta reference" command.
	 * @return
	 */
	private String[] parseSamRefIndexCommand() {
		String[] refIndexCommand = new String[3];
		
		// Add the absolute path and the executable to the command.
		refIndexCommand[0] = VerifiedExternalPrograms.getSamtoolsPath()
			+ PhysicalConstants.getPathSeparator() 
			+ VerifiedExternalPrograms.getSamtoolsExe();
		
		// Add the "faidx" command
		refIndexCommand[1] = Commands.faidx.toString();
		
		// Add the "input" command
		refIndexCommand[2] = this.command.get(Commands.input);
		
		return refIndexCommand;
	}
	
	/**
	 * This method parses the "tview" command.
	 * @return
	 */
	private String[] parseSamTviewCommand() {
		String[] tviewCommand = new String[7];

		// Add the absolute path and the executable to the command.
		tviewCommand[0] = VerifiedExternalPrograms.getSamtoolsPath()
			+ PhysicalConstants.getPathSeparator() 
			+ VerifiedExternalPrograms.getSamtoolsExe();

		// Add "tview" command.
		tviewCommand[1] = Commands.tview.toString();
		
		// Add "exact search position" command.
		tviewCommand[2] = "-" + Commands.p.toString();
		tviewCommand[3] = this.command.get(Commands.p);
		
		// Add "export" command.
		tviewCommand[4] = "-" + Commands.d.toString();
		tviewCommand[5] = this.command.get(Commands.d);
		
		// Add "input" comand.
		tviewCommand[6] = this.command.get(Commands.input);
		
		return tviewCommand;
	}

}
