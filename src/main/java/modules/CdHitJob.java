package modules;

// Imports.

// Java exception imports.
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

// Java I/O imports.
import java.io.BufferedReader;

// Project specific imports.
import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;
import core.common.PortState;
import core.common.CommandState;

import core.VerifiedExternalPrograms;
import core.InputPort;
import core.OutputPort;
import core.PhysicalConstants;
import core.ModuleNode;

import storage.SequenceStorage;
import modules.commands.Commands;

import externalStorage.ExtStorageType;

// Project specific exceptions.
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;
import storage.exceptions.TruncatedFastaHeadException;

/**
 * This module starts a job on a new thread. It contacts the external program "cd-hit".
 * cd-hit clusters multiple fasta sequences for a defined alignment identity value.
 * The results are saved separately in either a user specified folder or in the "tmpData" folder
 * by default.  
 * @author Christopher Kraus
 *
 */
public class CdHitJob extends Module {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// External commands for Cd-Hit software.
	private Map<Commands, String> command;
	
	// Module node of the current job.
	private ModuleNode moduleNode;

	// Input retrieved from input port.
	private String input;
	
	// Output to save a specified location.
	private String output;
	
	// Save the file path to the Cd-Hit cluster file.
	private String cdHitClusterFilePath = "";
		
	// SequenceStorage object for storing fasta data.
	private SequenceStorage fastaStorage;
	
	// Save the identity given to this command.
	private double identity;
	
	// Constructors.
	public CdHitJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, HashMap<Commands, String> cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		this.logger = Logger.getLogger(CdHitJob.class.getName());
		this.fastaStorage = new SequenceStorage();
	}
	
	// Methods.
		
	@Override
	public void run () {
		try {
			CommandState cState = this.callCommand();
			if (cState.equals(CommandState.SUCCESS)) {
				this.setModuleState(ModuleState.SUCCESS);
				
				// Notify the ModuleObserver about the state of this ModuleNode.
				this.moduleNode.notifyModuleObserver();
				
				// If there was an output file, 
				// notify the ModuleObserver about the written output of this Module.
				if (!this.cdHitClusterFilePath.isEmpty())
					this.moduleNode.notifyModuleObserverOutput(this.cdHitClusterFilePath, 
							ExtStorageType.CDHIT_EXT_STORAGE, this.identity);
			}
		} catch (CommandFailedException ce) {
			this.logger.log(Level.SEVERE, ce.getMessage());
			ce.printStackTrace();
		}
	}
		
	/**
	 * Calls an external command and processes data.
	 * @return CommandState commandState
	 * @throws CommandFailedException
	 */
	public synchronized CommandState callCommand() throws CommandFailedException {
		
		this.moduleNode = this.getSuperModuleNode();
		
		CommandState cState = CommandState.STARTING;
							
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
				// Test whether the port is connected before transferring data.
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
		// Store if any input was read.
		if (! this.input.isEmpty()) {
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
		
		// Parse the Cd-Hit command.
		
		String [] newCommands = this.parseCommand();
		
		// External command.
		
		String line="";
		String cdHitOutput="";
		
		try {
			// Start an external process with the pre-defined command array.
		    Process process = Runtime.getRuntime().exec(newCommands);
		    
		    // Read the STDIN from the unix process.
		    Reader r = new InputStreamReader(process.getInputStream());
		    
		    // Read line by line using the BufferedReader.
		    BufferedReader in = new BufferedReader(r);
		    while((line = in.readLine()) != null) {
		    	cdHitOutput += line + PhysicalConstants.getNewLine();
		    }
		    in.close();
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		}
		
		// Print the Cd-Hit output.
    	this.logger.log(Level.INFO, cdHitOutput);
		    	
    	// Save the path to the created Cd-Hit cluster after writing it.
    	// ATTENTION: CD-HIT CREATS ITS OWN OUTPUT WITH AN SUFFIX ".clstr"
    	// THIS MAY CHANGE IN A DIFFERENT VERSION OF CD-HIT!!!
    	this.cdHitClusterFilePath = this.command.get(Commands.o) + ".clstr";
    	
    	// Save the given identity parameter.
    	this.identity = Double.parseDouble(this.command.get(Commands.c));
    	   			
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
	 * This private method parses the incoming commands to a String[] array.
	 * @return String[] commands
	 */
	private String[] parseCommand () {
		ArrayList<String> newCommand = new ArrayList<String>();
		
		// Add the absolute path and the executable to the command.
		newCommand.add(VerifiedExternalPrograms.getCdhitPath() 
				+ PhysicalConstants.getPathSeparator() + VerifiedExternalPrograms.getCdhitExe());
		
		// Parse the option T (number of threads/cores).
		newCommand.add("-" + Commands.T.toString());
		newCommand.add(this.command.get(Commands.T));
		
		// Parse the option i (location of the input fasta file).
		newCommand.add("-" + Commands.i.toString());
		newCommand.add(this.command.get(Commands.i));
		
		// Parse the option o (location of the output fasta file).
		newCommand.add("-" + Commands.o.toString());
		newCommand.add(this.command.get(Commands.o));
		
		// Convert to an String[] array.
		// TODO: Isn't there a better solution?
		
		String[] finishedCommand = new String[newCommand.size()];
		
		for (int i = 0; i < newCommand.size(); i ++)
			finishedCommand[i] = newCommand.get(i);
		
		return (String[]) finishedCommand;
	}
	

}
