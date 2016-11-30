package testModules;

import java.io.IOException;

import core.CharPipe;
//Imports.
import core.InputPort;
import core.common.CommandState;
import core.common.Module;
import core.ModuleNode;
import core.common.ModuleState;
import core.common.ModuleType;
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;

public class TestOutput extends Module {

	// Variables.
	private String command;
	private ModuleNode moduleNode;
	
	// Constructors.
	public TestOutput(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
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
		
		CommandState cState = CommandState.STARTING;
		
		this.moduleNode = this.getSuperModuleNode();
		
		// Test system output.
		System.out.println("QPMS9 with moduleID \"" + this.getModuleID() + "\" and storageID \"" + this.getStorageID() + "\" :");
		System.out.println("Command " + this.command + " called");
		System.out.println("Associated storageID " + this.getStorageID());
		
		
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
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		} 			
		
		catch (InterruptedException intE) {
			System.err.println("Error: " + intE.getMessage());
			intE.printStackTrace();
		}
		
		System.out.println("Here is the output:");
		System.out.println(input);
		
		cState = CommandState.SUCCESS;
				
		return cState;
	}

}
