package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit test imports.
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Qpms9CommandTestRunner {

	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	Qpms9CommandTestRunner () {
		// Get new logger from Logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// main() function.
	
		public static void main (String[] args) {
			
			// Create a new testRunner.
			Qpms9CommandTestRunner testRunner = new Qpms9CommandTestRunner();
			
			// Save the results from the tests on the class "CoreControllerExternalProgrammesTest".
			// Use the static function JUnitCore.runClass() for this purpose. 
			Result result = JUnitCore.runClasses(Qpms9CommandTest.class);
			
			for (Failure failure : result.getFailures()) 
				testRunner.logger.log(Level.SEVERE, failure.getMessage());
			
			if (result.wasSuccessful())
				testRunner.logger.log(Level.INFO, "[TEST]\tSUCCESS");
		}
}
