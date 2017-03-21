package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit imports.
import org.junit.Test;
import junit.framework.TestCase;

//Project-specific imports.
import core.CoreController;
import core.ModuleBuilder;
import core.ListExternalPrograms;
import extProgs.ExtProgType;

public class FastaStorageTest extends TestCase{
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public FastaStorageTest () {
		// Call Logger to get a new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// JUnit test.
	
	@Test
	public void testThis () {
		
		// Get a new instance.
		FastaStorageTest thisTest = new FastaStorageTest();
		
		// Run the core components.
		thisTest.start();
		
		
	}
	
	public void start() {
		
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		
		// Create the module builder.
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Create InputReader module.
		String[] inputReaderCommand = new String[1];
		
	}

}
