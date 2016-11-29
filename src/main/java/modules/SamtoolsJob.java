package modules;



//Imports.

import core.common.Module;
import core.common.ModuleType;
import core.JobController;
import core.common.CommandState;
import core.exceptions.CommandFailedException;

//TODO: Just a bare bones module. This must be extended!

public class SamtoolsJob extends Module {
	
	// Variables.
	private String command;
	private JobController jobController;
	
	// Constructors.
	public SamtoolsJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd, JobController jobController) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		this.jobController = jobController;
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
