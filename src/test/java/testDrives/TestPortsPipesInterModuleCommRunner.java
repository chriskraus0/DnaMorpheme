package testDrives;

//Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit testing imports.
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Test runner for the test class TestPortsPipesInterModuleCommTest.
 * @author christopher
 *
 */

public class TestPortsPipesInterModuleCommRunner {
	
	// Variables.
	private Logger logger;
	
	// Constructor.
	public TestPortsPipesInterModuleCommRunner () {
		this.logger = Logger.getLogger(TestPortsPipesInterModuleCommRunner.class.getName());
	}
	
	public static void main(String[] args) {
		
		TestPortsPipesInterModuleCommRunner testRunner = new TestPortsPipesInterModuleCommRunner();
		
		Result result = JUnitCore.runClasses(TestPortsPipesInterModuleCommTest.class);
		
		for (Failure failure : result.getFailures()) {
			testRunner.logger.log(Level.SEVERE, "[TEST]\t" + failure.toString());
		}
		
		if (result.wasSuccessful()) {
			testRunner.logger.log(Level.INFO, "[TEST]\tSUCCESS");
		}
	}

}
