package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.HashMap;

// JUnit imports.
import junit.framework.TestCase;
import org.junit.Test;

// Project-specific imports.
import core.CoreController;
import core.ListExternalPrograms;
import core.ModuleBuilder;
import modules.commands.Commands;

/**
 * This TestCase class creates and external module handler. Receives data from an external file
 * and saves it for the duration of the runtime in the folder "tmpData".
 * @author Christopher Kraus
 *
 */

public class ReadFileAndStoreTmpDataTest extends TestCase {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public ReadFileAndStoreTmpDataTest () {
		
		// Call Logger to get a new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Methods.
	
	// JUnit test.
	@Test
	public void runTest () {
		this.start();
		
		// TODO: Temporary test assertion until further parts of the test drive are ready.
		assertTrue( true );
	}
	
	private void start () {
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		// Instantiate an external program handler.
		ListExternalPrograms extProgramHandler = CoreController.checkExternalProgrammes();
		
		// Create the module builder (a factory for modules).
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Create new read input command.
		HashMap<Commands, String> inputTestJobCommand = new HashMap<Commands, String>();
		inputTestJobCommand.put(Commands.path, "testFiles/testFile1.txt");
		int inputModule = moduleBuilder.createInputTestJob(inputTestJobCommand);
		
		// Create storage controller.
		//TODO: Create StorageController class.
		
		// Create internal storage factory.
		
		
		// Create new output save module.
		String[] internalStorageCommand;
		
	}
	

}
