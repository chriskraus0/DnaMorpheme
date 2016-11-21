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
		
		try {
			File inFile = new File (this.command);
			
			FileReader fReader = new FileReader(inFile);
			BufferedReader bReader = new BufferedReader(fReader);
			
			((OutputPort) this.getOutputPort()).writeToCharPipe(bReader.readLine());
			
			String line;
			
			while ( (line = bReader.readLine()) != null) {
				((OutputPort) this.getOutputPort()).writeToCharPipe(line);
			}
			
			bReader.close();
			
		} catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return CommandState.SUCCESS;
	}

}
