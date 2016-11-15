package modules;

import core.common.CommandState;

// Imports.

// Project specific imports.
import core.common.Module;
import core.common.ModuleType;
import core.InputPort;

// Project specific exceptions.
import core.exceptions.CommandFailedException;

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
		
		String inputStuff;
		
		// Read first chars.
		//((InputPort) this.getInputPort()).readFromCharPipe(data, offset, length);
		
		
		// If everything worked out return SUCCESS.
		return CommandState.SUCCESS;
	}
	

}
