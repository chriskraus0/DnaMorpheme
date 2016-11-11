package testDrives;

//Imports.
import java.util.Set;
import java.net.URL;
import java.util.Iterator;

// Project specific imports.
import core.CoreController;
import core.ModuleBuilder;
import core.exceptions.CommandFailedException;
import modules.CdHitJob;

/**
 * Test drive class to test core classes such as 
 * core.Controller, core.ModuleBuilder, core.common.CommandFailedException, core.CheckExternalProgrammes.
 * This test drive iterates over 3 independently created instances of the Module CdHitJob.
 * @see core.Controller
 * @see core.Controller#checkExternalProgrammes()
 * @see core.ModuleBuilder
 * @see core.ModuleBuilder#createNewCdHitJob()
 * @see core.Module
 * @see modules.CdHitJob
 * @author christopher
 *
 */
public class TestCoreControllerModulesExternalProgrammes {
	
	// Variables.
	
	// Constructors.
	
	// Methods.
	public static void main (String[] args) {
		
		// Determine the location of this particular test class.
		URL location = TestCoreControllerModulesExternalProgrammes.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println("Location of Class \"" + TestCoreControllerModulesExternalProgrammes.class.getName() +"\":");
	    System.out.println(location.getFile());
	    
	    // Determine the location of the working directory after starting the application.
	    System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		TestCoreControllerModulesExternalProgrammes thisJob = new TestCoreControllerModulesExternalProgrammes();
		
		// Run the core components.
		thisJob.run();
	}
	
	public void run() {

	    // Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing config file.
		CoreController.checkExternalProgrammes("config/config.txt");
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Create 3 new CD-HIT jobs.
		moduleBuilder.createNewCdHitJob();
		moduleBuilder.createNewCdHitJob();
		moduleBuilder.createNewCdHitJob();
		
		// Get the moduleIDs.
		Set<Integer> moduleIdSet = ModuleBuilder.getModuleIDs();
		Iterator<Integer> it = moduleIdSet.iterator();
		
		while (it.hasNext()) {
			CdHitJob thisModule = (CdHitJob) ModuleBuilder.getModule(it.next().intValue());
			System.out.println("Module with ID \"" + thisModule.getModuleID() + "\"");
			System.out.println("Call command:");
			
			// Call command.
			try {
				thisModule.callCommand("TEST", thisModule.getStorageID());
			} catch (CommandFailedException ce) {
				
			}
		}
		
	}
}
