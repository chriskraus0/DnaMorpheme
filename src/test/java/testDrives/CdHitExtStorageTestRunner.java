package testDrives;

// Imports.

// Java imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit imports.
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Test runner for testing the external Cd-Hit storage class.
 * @see externalStorage.CdHitExtStorage
 * @author Christopher Kraus
 *
 */
public class CdHitExtStorageTestRunner {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors:
	public CdHitExtStorageTestRunner () {
		// Call the Logger class to get a class-specific logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// main () function.
	
	public static void main (String[] args) {
		// Create a new testRunner.
		CdHitExtStorageTestRunner testRunner = new CdHitExtStorageTestRunner();
		
		// TODO: Change this!
		// Save the results from the tests on the class "CoreControllerExternalProgrammesTest".
		// Use the static function JUnitCore.runClass() for this purpose. 
		Result result = JUnitCore.runClasses(CdHitExtStorageTest.class);
		
		for (Failure failure : result.getFailures()) 
			testRunner.logger.log(Level.SEVERE, failure.getMessage());
		
		if (result.wasSuccessful())
			testRunner.logger.log(Level.INFO, "[TEST]\tSUCCESS");
	}

}
