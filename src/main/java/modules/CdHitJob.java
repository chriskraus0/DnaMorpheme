package modules;

import core.common.CommandState;

// Imports.

import java.io.IOException;

// Project specific imports.
import core.common.Module;
import core.common.ModuleType;
import core.InputPort;
import core.OutputPort;

// Project specific exceptions.
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;

// TODO: Just a bare bones module. This must be extended!
public class CdHitJob extends Module {
	// Variables.
	
	// Constructors.
	public CdHitJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandState callCommand(String command, int storageID) throws CommandFailedException {
		// TODO Auto-generated method stub
		
		// Test system output.
		System.out.println("CdHitJob with moduleID \"" + this.getModuleID() + "\" and storageID \"" + this.getStorageID() + "\" :");
		System.out.println("Command " + command + " called");
		System.out.println("Associated storageID " + storageID);
		
		// Checked exception. TODO: Add ExternalCommandHandler
		
		// Save input from pipe
		String input = "";
		
		// Save number of read characters.
		int charNumber = 0;
	
		// Prepare char buffer "data".
		int bufferSize = 1024;
		char[] data = new char[bufferSize];
		
		// Read from InputPort (via CharPipe).
		try {
			while (charNumber != -1) {
				charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
				input += data.toString();
				
				// If the number of read characters is smaller than the buffer limit 
				// write the remaining characters in the String variable input.
				if (charNumber < bufferSize) {
					for (int i = 0; i < charNumber; i++)
					input += data[i];
				}
			}
			
			
		} catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		} 
		
		input += "Here is a new modified additional line";
		
		// Write to OutputPort (via CharPipe).
		try {
			((OutputPort) this.getOutputPort()).writeToCharPipe(input);
		} catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		} catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		} 
		
		// If everything worked out return SUCCESS.
		return CommandState.SUCCESS;
	}
	

}
