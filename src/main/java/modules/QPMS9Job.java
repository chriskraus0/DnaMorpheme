package modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.ArrayList;

// Project-specific imports.
import core.CharPipe;
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

public class QPMS9Job extends Module {

	// Variables.
	private ModuleNode moduleNode;
	
	// External commands for qpms9 software.
	private Map<Commands, String> command;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public QPMS9Job(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, HashMap<Commands, String> cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		// Initialize the logger.
		this.logger = Logger.getLogger(QPMS9Job.class.getName());
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
			this.logger.log(Level.SEVERE, ce.getMessage());
			ce.printStackTrace();
		}
	}
	
	public synchronized CommandState callCommand() throws CommandFailedException {
		
		CommandState cState = CommandState.STARTING;
		
		this.moduleNode = this.getSuperModuleNode();
		
		// Variable holding the command string.
		String newCommands [] = this.parseCommand();
		
		// Internal input is not required for this module.
		/*
		// Save input from pipe
		String input = "";
	
		// Save number of read characters.
		int charNumber = 0;
	
		// Prepare char buffer "data".
		int bufferSize = 1024;
		char[] data = new char[bufferSize];
				
		// Read from InputPort (via CharPipe).
		
		try {
			charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
			while (charNumber != -1) {
									
				// If the number of read characters is smaller than the buffer limit 
				// write the remaining characters in the String variable input.
				if (charNumber < bufferSize) {
					for (int i = 0; i < charNumber; i++)
					input += data[i];
				} else {
					input += new String(data);
				}
				
				charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
			}
			
			// Close input pipe.
			((CharPipe) this.getInputPort().getPipe()).readClose();
			
			// Signal done reading input.
			this.setModuleState(ModuleState.INPUT_DONE);
			this.moduleNode.notifyModuleObserver();
						
		} catch (PipeTypeNotSupportedException pe) {
			this.logger.log(Level.SEVERE, pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		} 			
		
		this.logger.log(Level.INFO, "Here is the output:");
		this.logger.log(Level.INFO, input);
		*/
		
		// External command.
		String line="";
		String qpms9Output="";
		
		try {
			// Start an external process with the pre-defined command array.
		    Process process = Runtime.getRuntime().exec(newCommands);
		    
		    // Read the STDIN from the unix process.
		    Reader r = new InputStreamReader(process.getInputStream());
		    
		    // Read line by line using the BufferedReader.
		    BufferedReader in = new BufferedReader(r);
		    while((line = in.readLine()) != null) {
		    	qpms9Output += line + PhysicalConstants.getNewLine();
		    }
		    in.close();
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		}
		
		// Print the qpms9 output.
    	this.logger.log(Level.INFO, qpms9Output);
		
		// Checked exception. TODO: Add ExternalCommandHandler
		
		cState = CommandState.SUCCESS;
		
		return cState;
	}
	
	private String[] parseCommand() {
				
		ArrayList<String> newCommand = new ArrayList<String>();
		
		// Add the absolute path and the executable to the command.
		newCommand.add(VerifiedExternalPrograms.getQpms9Path() 
				+ PhysicalConstants.getPathSeparator() + VerifiedExternalPrograms.getQpms9Exe());
		
		// Insert new fasta input for Qpms9.
		newCommand.add(this.command.get(Commands.fasta));
		
		// Parse the option l (length of the planted motif search; PMS).
		newCommand.add("-" + Commands.l.toString());
		newCommand.add(this.command.get(Commands.l));
		
		// Parse the option d (Hamming distance of the planted motif search; PMS).
		newCommand.add("-" + Commands.d.toString());
		newCommand.add(this.command.get(Commands.d));
		
		// Parse the option q (min. portion of the quorum of the planted mortif search; PMS).
		newCommand.add("-" + Commands.q.toString());
		newCommand.add(this.command.get(Commands.q));
		
		// Convert to an String[] array.
		
		String[] finishedCommand = new String[newCommand.size()];
		
		for (int i = 0; i < newCommand.size(); i ++)
			finishedCommand[i] = newCommand.get(i);
				
		return finishedCommand;
	}

}
