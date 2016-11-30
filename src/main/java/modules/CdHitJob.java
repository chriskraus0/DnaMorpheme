package modules;

import core.common.CommandState;

// Imports.

import java.io.IOException;

// Project specific imports.
import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;

import core.InputPort;
import core.OutputPort;
import core.ModuleNode;

// Project specific exceptions.
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;

public class CdHitJob extends Module {
	// Variables.
	
	private String command;
	
	private ModuleNode moduleNode;
	
	// Constructors.
	public CdHitJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
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
			System.err.println(ce.getMessage());
			ce.printStackTrace();
		}
	}
	
	public synchronized CommandState callCommand() throws CommandFailedException {
		
		this.moduleNode = this.getSuperModuleNode();
		
		CommandState cState = CommandState.STARTING;
			
		// Test system output.
		System.out.println("CdHitJob with moduleID \"" + this.getModuleID() + "\" and storageID \"" + this.getStorageID() + "\" :");
		System.out.println("Command " + this.command + " called");
		System.out.println("Associated storageID " + this.getStorageID());
		
		// Checked exception. TODO: Add ExternalCommandHandler
		
		// Save input from pipe
		String input = "";
				
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
					
					/*TODO:
					 * if (Thread.interrupted()) {
						throw new InterruptedException ("Thread interrupted");
					}*/
					
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

				this.getInputPort().getPipe().readClose();
		
						
		} catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		} 
	
		this.setModuleState(ModuleState.INPUT_DONE);
		this.moduleNode.notifyModuleObserver();
	
		// Modify the input.
		String output = input + "NEW LINE!!!\n";
		
		// Write to OutputPort (via CharPipe).
		
		try {
			
			// Write to the output pipe.
			((OutputPort) this.getOutputPort()).writeToCharPipe(output);
			
			this.getOutputPort().getPipe().writeClose();
			
			this.setModuleState(ModuleState.OUTPUT_DONE);
			this.getSuperModuleNode().notifyModuleObserver();
			
			
		} catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		} catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		} 
			
				
		// If everything worked out return SUCCESS.
		cState = CommandState.SUCCESS;	
		
		return cState;
	}
	

}
