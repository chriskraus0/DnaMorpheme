package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit imports.
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * TestRunner class to test the behavior of the external program Samtools.
 * @author christopher
 *
 */
public class SamtoolsTestRunner {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public SamtoolsTestRunner() {
		
		// Call Logger to get a new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// main().
	
	public static void main (String[] args) {
		SamtoolsTestRunner testRunner = new SamtoolsTestRunner();
		
		// Save the results of the test in the Result result.
		Result result = JUnitCore.runClasses(SamtoolsTest.class);
		
		// Check for failures.
		for (Failure failure : result.getFailures()) 
			testRunner.logger.log(Level.SEVERE, failure.getMessage());
		
		// Inform about successful test.
		if (result.wasSuccessful())
			testRunner.logger.log(Level.INFO, "[TEST]\tSUCCESS");
	}

}
