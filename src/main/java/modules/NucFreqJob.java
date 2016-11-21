package modules;

import core.common.CommandState;

//Imports.

// Projecte specific imports.
import core.common.Module;
import core.common.ModuleType;
import core.exceptions.CommandFailedException;

//TODO: Just a bare bones module. This must be extended!

public class NucFreqJob extends Module {
	
	// Variables.
	private String command;

	public NucFreqJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		// TODO Auto-generated constructor stub
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
	
	public CommandState callCommand() throws CommandFailedException {
		// TODO Auto-generated method stub
		return CommandState.SUCCESS;
	}

}
