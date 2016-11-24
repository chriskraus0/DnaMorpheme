package modules;

import java.io.IOException;

import core.InputPort;
import core.ModuleBuilder;
import core.common.CommandState;

//Imports.

import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;
import core.common.PipeState;
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;

//TODO: Just a bare bones module. This must be extended!
public class QPMS9Job extends Module {

	// Variables.
	private String command;
	
	// Constructors.
	public QPMS9Job(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd) {
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
			}
		} catch (CommandFailedException ce) {
			System.err.println(ce.getMessage());
			ce.printStackTrace();
		}
	}
	
	public synchronized CommandState callCommand() throws CommandFailedException {
		
		CommandState cState = CommandState.STARTING;
		
		// Test system output.
		System.out.println("QPMS9 with moduleID \"" + this.getModuleID() + "\" and storageID \"" + this.getStorageID() + "\" :");
		System.out.println("Command " + this.command + " called");
		System.out.println("Associated storageID " + this.getStorageID());
		
		
		// Save input from pipe
		String input = "";
		
		// Synchronize the pipe.
		synchronized (this.getInputPort().getPipe()) {
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
				
				// Notify that this thread is done with reading from pipe.
				this.getInputPort().getPipe().setPipeState(PipeState.FINISHED);
				
				while (this.getInputPort().getPipe().getPipeState().equals(PipeState.FINISHED)) {
					this.getInputPort().getPipe().notifyAll();
				}
				
			} catch (PipeTypeNotSupportedException pe) {
				System.err.println(pe.getMessage());
				pe.printStackTrace();
			}
			
			catch (IOException ie) {
				System.err.println(ie.getMessage());
				ie.printStackTrace();
			} 			
			
			System.out.println("Here is the output:");
			System.out.println(input);
			
			// Checked exception. TODO: Add ExternalCommandHandler
			
			cState = CommandState.SUCCESS;
			
			
		}
		
		return cState;
	}

}
