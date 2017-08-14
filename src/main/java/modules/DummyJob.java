package modules;

//Imports.

// Java utility imports.
import java.util.HashMap;

//Project specific imports.
import core.common.Module;
import core.common.ModuleType;

import modules.commands.Commands;

/**
 * This dummy module has no other purpose than acting as a buffer for module nodes which are not 
 * interconnected.
 * @author Christopher Kraus
 *
 */
public class DummyJob extends Module {

	// Variables.
	
	private HashMap<Commands, String> dummyCommand;
	
	// Constructors.
	public DummyJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, HashMap<Commands, String> cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.dummyCommand = cmd;
	}

	
	// Methods.
	
	// Dummy getter.
	public HashMap<Commands, String> getDummyCommand() {
		return this.dummyCommand;
	}
			
	@Override
	public void run() {
		// Nothing to run on a thread.
	}

}
