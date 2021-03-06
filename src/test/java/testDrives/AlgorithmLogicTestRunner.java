package testDrives;

//Imports.

//Java imports.

//Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

//JUnit imports.
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AlgorithmLogicTestRunner {

	// Variables.
	
	// Logger.
	private Logger logger;
	
	public AlgorithmLogicTestRunner () {
		// Call Logger to get a new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// main() function.
	
	public static void main (String[] args) {
		// Create a new testRunner.
		AlgorithmLogicTestRunner testRunner = new AlgorithmLogicTestRunner();
		
		// Save the results from the tests on the class "AlgorithmLogicTest".
		// Use the static function JUnitCore.runClass() for this purpose. 
		Result result = JUnitCore.runClasses(AlgorithmLogicTest.class);
		
		for (Failure failure : result.getFailures()) 
			testRunner.logger.log(Level.SEVERE, failure.getMessage());
		
		if (result.wasSuccessful())
			testRunner.logger.log(Level.INFO, "[TEST]\tSUCCESS");
	}
}
