package modules;

import core.common.CommandState;

// Imports.

// Project specific imports.
import core.common.Module;
import core.common.ModuleType;
import core.common.CommandFailedException;

// TODO: Just a bare bones module. This must be extended!
public class CdHitJob extends Module {
	// Variables.
	
	// Constructors.
	public CdHitJob(String moduleID, String storageID, ModuleType mType) {
		super(moduleID, storageID, mType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandState callCommand(String command, Module storageID) throws CommandFailedException {
		// TODO Auto-generated method stub
		
		// Checked exception. TODO: Add ExternalCommandHandler
		
		
		// If everything worked out return SUCCESS.
		return CommandState.SUCCESS;
	}
	

}
