package core;

// Imports.

// Java I/O imports.
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

// Project specific imports.
import core.common.Module;
import core.common.ModuleType;
import core.common.CommandState;
import core.OutputPort;

// Project specific exceptions.
import core.exceptions.CommandFailedException;

public class InputReader extends Module {
	
	// Variables.
	private String command;
	
	// Constructors.
	public InputReader(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
	}
	
	// Methods.
	@Override
	public void run () {
		try {
			this.callCommand();
		} catch (CommandFailedException ce) {
			System.err.println(ce.getMessage());
			ce.printStackTrace();
		}
	}

	public synchronized CommandState callCommand() throws CommandFailedException {
		
		CommandState cState = CommandState.SUCCESS;
				
		try {
						
			File inFile = new File (this.command);
			
			FileReader fReader = new FileReader(inFile);
			BufferedReader bReader = new BufferedReader(fReader);
			
			String data = "";
			
			while ( (data = (bReader.readLine())) != null) {
								
				((OutputPort) this.getOutputPort()).writeToCharPipe(data);
				
			}
			
			bReader.close();
			
		} catch (IOException ie) {
			System.err.println("IOException in " + this.getClass().toString() 
					+ "#callCommand()" + ": " + ie.getMessage());
			ie.printStackTrace();
			cState = CommandState.FAIL;
		} catch (Exception e) {
			System.err.println("Exception in " + this.getClass().toString() 
					+ "#callCommand()" + ": " + e.getMessage());
			e.printStackTrace();
			cState = CommandState.FAIL;
		}
		
		return cState;
	}

}
