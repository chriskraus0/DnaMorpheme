package modules;

import core.common.CommandState;

//Imports.

import core.common.Module;
import core.common.ModuleType;
import core.exceptions.CommandFailedException;

//TODO: Just a bare bones module. This must be extended!
public class QPMS9Job extends Module {

	public QPMS9Job(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandState callCommand(String command, int storageID) throws CommandFailedException {
		
		// Test system output.
		System.out.println("QPMS9 with moduleID \"" + this.getModuleID() + "\" and storageID \"" + this.getStorageID() + "\" :");
		System.out.println("Command " + command + " called");
		System.out.println("Associated storageID " + storageID);
		
		
		// Checked exception. TODO: Add ExternalCommandHandler
				
		return CommandState.SUCCESS;
	}

}
