package testDrives;

import core.CoreController;
import core.ListExternalPrograms;

/**
 * Test-Drive class to test an real external command.
 * @author christopher
 *
 */
public class TestFirstExternalCommand {
	
	// Variables.

	// Constructors.
	
	// Methods.
	public static void main (String[] args) {
		
	    // Determine the location of the working directory after starting the application.
	    System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
	    TestFirstExternalCommand thisJob = new TestFirstExternalCommand();
		
		// Run the core components.
		thisJob.run();
	}
	
	public void run() {

	    // Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		ListExternalPrograms externalProgramHandler = CoreController.checkExternalProgrammes();
		
		// Create a storage in the provided working directory path.
		
		
	}

}
