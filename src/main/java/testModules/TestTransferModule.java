package testModules;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// Project specific imports.
import core.common.CommandState;
import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;

import core.InputPort;
import core.OutputPort;
import core.ModuleNode;

// Java exceptions.
import java.io.IOException;

// Project specific exceptions.
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;

/**
 * Test module which transfers data from a source to a sink via 
 * a input and output port and two pipes. This module also 
 * manipulates the data to proof that output is different than 
 * the input.
 * @author christopher
 *
 */
public class TestTransferModule extends Module {
	// Variables.
	
	private String[] command;
	
	private ModuleNode moduleNode;
	
	private String input;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public TestTransferModule(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String[] cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		
		// Call Logger factory to get a new logger.
		this.logger = Logger.getLogger(TestTransferModule.class.getName());
	}
	
	// Methods.
	
	// Setters.
		
	// End setters.
		
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
		
		this.moduleNode = this.getSuperModuleNode();
		
		CommandState cState = CommandState.STARTING;
		
		// Save the command string.
		String cmdString = "";
		
		for (String i : this.command) 
			cmdString += i;
		
		// Test system output.
		this.logger.log(Level.INFO, "CdHitJob with moduleID \"" + this.getModuleID() + "\" and storageID \"" 
				+ this.getStorageID() + "\" :\n" 
				+ "Command \"" + cmdString + "\" called.\n"
				+ "Associated storageID " + this.getStorageID());
		
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
						throw new InterruptedException ("Thread interrupted");	
					}
					
					// If the number of read characters is smaller than the buffer limit 
					// write the remaining characters in the String variable input.
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
	
		catch (PipeTypeNotSupportedException pe) {
			this.logger.log(Level.SEVERE, pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		} catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		}
	
		this.setModuleState(ModuleState.INPUT_DONE);
		this.moduleNode.notifyModuleObserver();
	
		// Modify the input.
		String output = "NEW LINE!!!\n";
		output += new String(this.input);
		output += "NEW LINE2!!!\n";
		// Write to OutputPort (via CharPipe).
		
		try {
			
			// Write to the output pipe.
			((OutputPort) this.getOutputPort()).writeToCharPipe(output);
			
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
			
				
		// If everything worked out return SUCCESS.
		cState = CommandState.SUCCESS;	
		
		return cState;
	}
}
