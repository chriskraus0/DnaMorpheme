package core;

import core.common.CommandState;
import core.common.Module;

//Imports

// Internal project imports.
import core.common.ModuleType;
import core.exceptions.CommandFailedException;

//TODO: Just a bare bones module. This must be extended!

public class ExternalCommandHandler extends Module {

	public ExternalCommandHandler(int moduleID, int storageID, ModuleType mType) {
		super(moduleID, storageID, mType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandState callCommand(String command, int storageID) throws CommandFailedException {
		// TODO Auto-generated method stub
		
		// TODO resolve this:
		/*switch (storageID.getModuleType()) {
		case CDHIT_JOB:
			
			break;
		case QPMS9_JOB:
			break;
		case BOWTIE2_JOB:
			break;
		case TOMTOM_JOB:
			break;
		case SEQ_LOGO_JOB:
			break;
		case PMS_CONSENSUS_JOB:
			break;
		case NUCFREQ_JOB:
			break;
		case EXTERNAL_COMMAND:
			break;
		case SAMTOOLS_JOB:
			break;
		case UNDEFINED:
			break;
		}*/
		
		return CommandState.SUCCESS;
	}

}
