package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.logging.Level;

// JUnit imports.
import org.junit.Test;
import junit.framework.TestCase;

// Project-specific imports.
//Project-specific imports.
import core.CoreController;
import core.ModuleBuilder;
import core.ModuleObserver;
import core.common.ModuleState;
import modules.commands.Commands;

/**
 * Test case to test the external storage for Cd-Hit clustering files.
 * @see externalStorage.CdHitExtStorage
 * @author Christopher Kraus
 *
 */

public class CdHitExtStorageTest extends TestCase {

	// Variables.
	
	private Logger logger;
	
	// Constructors.
	
	public CdHitExtStorageTest () {
		
		// Call Logger method to generate a new logger for this class.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// JUnit test.
	@Test
	public void testFunction () {
		
		// Run the core components and the external storage.
		this.start();
	}
	
	/**
	 * Run the test.
	 */
	public void start() {

		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		
		// Create the module builder.
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Get ModuleObserver.
		ModuleObserver moduleObserver = moduleBuilder.getJobcontroller().getModuleObserver();
		
		// TODO: Create a calls for the external storage.
		
		// Create new InputReader module.
		HashMap<Commands, String> inputReaderCommand = new HashMap<Commands, String>();
		inputReaderCommand.put(Commands.path, "testFiles/testFile.fasta");
		int inputReaderID = moduleBuilder.createNewInputReader(inputReaderCommand);
		
		// Create new CdHitJob module.
		HashMap<Commands, String> cdHitJobCommand = new HashMap<Commands, String>();
		cdHitJobCommand.put(Commands.T, "1");
		cdHitJobCommand.put(Commands.i, "testFiles/testFile.fasta");
		cdHitJobCommand.put(Commands.o, "tmpData/test.out");
		
		int cdHitJobID = moduleBuilder.createNewCdHitJob(cdHitJobCommand);
		
		// TODO: Thread for external storage needs to be incorporated.
		
		// Prepare module nodes.
		String inputReaderCdHitJobNodeName = moduleBuilder.prepareJobs(inputReaderID, cdHitJobID);
		
		// Start new thread for "inputReaderCdHitJobNodeName".
		try {
			moduleBuilder.startJobs(inputReaderCdHitJobNodeName);
		} catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		assertEquals(ModuleState.SUCCESS, moduleObserver.getProducerState());
		assertEquals(ModuleState.SUCCESS, moduleObserver.getConsumerState());
		
	}
}
