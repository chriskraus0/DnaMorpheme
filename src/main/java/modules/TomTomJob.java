package modules;

import core.common.CommandState;

//Imports.

import core.common.Module;
import core.common.ModuleType;
import core.exceptions.CommandFailedException;

//TODO: Just a bare bones module. This must be extended!

public class TomTomJob extends Module {

	// Variables.
	
	private String command;
	
	// Constructors.
	public TomTomJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd) {
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
		// TODO Auto-generated method stub
		return CommandState.SUCCESS;
	}

}
