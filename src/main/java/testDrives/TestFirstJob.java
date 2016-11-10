package testDrives;

//Imports.
import java.util.Set;
import java.util.Iterator;

// Project specific imports.
import core.CoreController;
import core.ModuleBuilder;
import core.common.CommandFailedException;
import modules.CdHitJob;

public class TestFirstJob {
	
	// Variables.
	
	// Methods.
	public static void main (String[] args) {
		TestFirstJob thisJob = new TestFirstJob();
		thisJob.run();
	}
	
	public void run() {
		CoreController.getInstance();
		CoreController.checkExternalProgrammes("../../config/config.txt");
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Create 3 new CD-HIT jobs.
		moduleBuilder.createNewCdHitJob();
		moduleBuilder.createNewCdHitJob();
		moduleBuilder.createNewCdHitJob();
		
		// Get the moduleIDs.
		Set<Integer> moduleIdSet = ModuleBuilder.getModuleIDs();
		Iterator<Integer> it = moduleIdSet.iterator();
		
		while (it.hasNext()) {
			System.out.println("Module with ID \"" + ModuleBuilder.getModule(it.next().intValue()).getModuleID() + "\"");
			System.out.println("Call command:");
			CdHitJob thisModule = (CdHitJob) ModuleBuilder.getModule(it.next().intValue());
			// Call command.
			try {
				thisModule.callCommand("TEST", thisModule.getStorageID());
			} catch (CommandFailedException ce) {
				
			}
		}
		
	}
}
