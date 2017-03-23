package testModules;

//Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Map;
import java.util.HashMap;

// Project-specific I/O imports.
import core.CharPipe;
import core.InputPort;

// Project-specific imports.
import core.common.CommandState;
import core.common.Module;
import core.ModuleNode;
import core.common.ModuleState;
import core.common.ModuleType;

import modules.commands.Commands;

//Java exceptions.
import java.io.IOException;

// Project-specific exceptions.
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;

/**
 * Test module which shows the data which it received from a source
 * via an import port and a char pipe.
 * @author christopher
 *
 */

public class TestOutput extends Module {

	// Variables.
	
	private Map<Commands, String> command;
	
	private ModuleNode moduleNode;
	
	private String finalOutput;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public TestOutput(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, HashMap<Commands, String> cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		
		// Call the Logger factory to get a new logger.
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
			}
		} catch (CommandFailedException ce) {
			this.logger.log(Level.SEVERE, ce.getMessage());
			ce.printStackTrace();
		}
	}
	
	public synchronized CommandState callCommand() throws CommandFailedException {
		
		CommandState cState = CommandState.STARTING;
		
		this.moduleNode = this.getSuperModuleNode();
		
		// Parse commands.
		String[] newCommand = parseCommand();
		
		// Variable which holds the command string.
		String cmdString = "";
		
		for (String i : newCommand) 
			cmdString += i;
		
		// Test system output.
		this.logger.log(Level.INFO, "QPMS9 with moduleID \"" + this.getModuleID() + "\" and storageID \"" 
				+ this.getStorageID() + "\" :\n"
				+ "Command \"" + cmdString + "\" called.\n"
				+ "Associated storageID " + this.getStorageID());
		
		
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
				
				// Check for interrupted threads.
				if (Thread.interrupted()) {
					this.getInputPort().getPipe().readClose();
					throw new InterruptedException("Thread Interrupted");
				}
				
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
		
		catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		}
		
		this.logger.log(Level.INFO, "Here is the output:");
		this.logger.log(Level.INFO, input);
		
		this.finalOutput = input;
		
		cState = CommandState.SUCCESS;
				
		return cState;
	}
	
	public String returnFinalOutput() {
		return this.finalOutput;
	}
	
	private String[] parseCommand () {
		String[] currCommand = new String[1];
		
		currCommand[0] = this.command.get(Commands.path);
		
		return currCommand;
	}

}
