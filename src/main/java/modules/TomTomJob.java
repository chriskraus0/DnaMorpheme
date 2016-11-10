package modules;

import core.common.CommandFailedException;
import core.common.CommandState;

//Imports.

import core.common.Module;
import core.common.ModuleType;

//TODO: Just a bare bones module. This must be extended!

public class TomTomJob extends Module {

	public TomTomJob(int moduleID, int storageID, ModuleType mType) {
		super(moduleID, storageID, mType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandState callCommand(String command, int storageID) throws CommandFailedException {
		// TODO Auto-generated method stub
		return CommandState.SUCCESS;
	}

}
