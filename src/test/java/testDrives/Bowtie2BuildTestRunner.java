package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit imports.
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Bowtie2BuildTestRunner {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public Bowtie2BuildTestRunner () {
		// Call Logger to get a new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Methods.
	
	// main().
	public static void main(String[] args) {
		
		// Create a new test runner.
		Bowtie2BuildTestRunner testRunner = new Bowtie2BuildTestRunner();
		
		// Run test on the class "Bowtie2BuildTest.class" and save the results 
		// in "testResult";
		Result testResult = JUnitCore.runClasses(Bowtie2BuildTest.class);
		
		// Notify if there has been any failure.
		for (Failure failure: testResult.getFailures())
			testRunner.logger.log(Level.SEVERE, failure.getMessage());
		
		// Notify if the test was successful.
		if (testResult.wasSuccessful()) 
			testRunner.logger.log(Level.INFO, "[Test]\tSUCCESS");
	}

}
