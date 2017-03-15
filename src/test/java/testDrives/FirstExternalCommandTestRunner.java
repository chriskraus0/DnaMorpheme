package testDrives;

import java.util.logging.Level;

// Imports.

// Java utility imports.
import java.util.logging.Logger;

// JUnit imports.
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;



/**
 * Test runner to test external programs.
 * @author christopher
 *
 */
public class FirstExternalCommandTestRunner {
	
	// Variables.
	private Logger logger;
	
	// Constructor.
	public FirstExternalCommandTestRunner () {
		
		// Call logging factory to ge a new logger.
		logger = Logger.getLogger(FirstExternalCommandTestRunner.class.getName());
	}
	
	// main() function.
	public static void main (String[] args) {
		
		FirstExternalCommandTestRunner testRunner = new FirstExternalCommandTestRunner();
		
		Result result = JUnitCore.runClasses(FirstExternalCommandTest.class);
	
		testRunner.logger.log(Level.INFO, "[TEST]\tFirst External Command Test");
	
		for (Failure failure: result.getFailures()) {
			testRunner.logger.log(Level.SEVERE,"[TEST]\t" + failure.toString());
		}
		
		if (result.wasSuccessful()) 
			testRunner.logger.log(Level.INFO,"[TEST]\tSUCCESS");
		
	}

}
