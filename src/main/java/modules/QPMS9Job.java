package modules;

import java.io.IOException;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;

// Project-specific imports.
import core.CharPipe;
import core.InputPort;

import core.common.CommandState;
import core.common.Module;
import core.ModuleNode;
import core.common.ModuleState;
import core.common.ModuleType;

import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;
import modules.commands.Commands;

//TODO: Just a bare bones module. This must be extended!
public class QPMS9Job extends Module {

	// Variables.
	private ModuleNode moduleNode;
	
	// External commands for cdhit software.
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
		String newCommand [] = this.parseCommand();
						
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
		
		// Checked exception. TODO: Add ExternalCommandHandler
		
		cState = CommandState.SUCCESS;
		
		return cState;
	}
	
	private String[] parseCommand() {
		// TODO: Add more options here.
		String[] currCommand = new String[1];
		currCommand[0] = this.command.get(Commands.path);
		
		return currCommand;
	}

}
