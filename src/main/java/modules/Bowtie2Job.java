package modules;

import core.common.CommandState;

//Imports.

import core.common.Module;
import core.common.ModuleType;
import core.exceptions.CommandFailedException;

//TODO: Just a bare bones module. This must be extended!
public class Bowtie2Job extends Module {

	public Bowtie2Job(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandState callCommand(String command, int storageID) throws CommandFailedException {
		// TODO Auto-generated method stub
		return CommandState.SUCCESS;
	}

}
