package testDrives;

// Imports.

// Java utility imports.
/*
import java.util.logging.Logger;
import java.util.logging.Level;
*/

// JUnit imports.
import org.junit.Test;
import junit.framework.TestCase;

// Project-specific imports.
import algorithmLogic.AlgorithmController;

public class AlgorithmLogicTest extends TestCase {

	// Variables.
	
	// Logger.
	
	//private Logger logger;
	
	// Constructors.
	public AlgorithmLogicTest () {
		// Call Logger to get a new logger.
		//this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// JUnit test.
	
	/**
	 * JUnit test method.
	 */
	@Test
	public void testThis () {
				
		// Run the core components.
		this.start();
		
	}
	
	/**
	 * Run the test.
	 */
	public void start() {
		
		// Define the command line arguments.
		String[] args = new String[2];
		args[0] = "testFiles/testFile8.fasta";
		args[1] = "tmpData";
		
		// Run main in the AlgorithmController.
		AlgorithmController.main(args);
		
		// TODO: Just a dummy test.
		assertEquals("1", "1");
	}
}
