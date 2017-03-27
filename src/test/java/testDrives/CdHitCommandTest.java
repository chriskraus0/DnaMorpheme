package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.logging.Level;

// JUnit imports.
import org.junit.Test;
import junit.framework.TestCase;
import modules.commands.Commands;

//Project-specific imports.
import core.CoreController;
import core.ModuleBuilder;
import core.ModuleObserver;
import core.common.ModuleState;

public class CdHitCommandTest extends TestCase {
		
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public CdHitCommandTest () {
		// Call Logger to get a new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
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
		
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		
		// Create the module builder.
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Get ModuleObserver.
		ModuleObserver moduleObserver = moduleBuilder.getJobcontroller().getModuleObserver();
		
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
		
		assertEquals(moduleObserver.getProducerState(), ModuleState.SUCCESS);
		assertEquals(moduleObserver.getConsumerState(), ModuleState.SUCCESS);
		
	}
}
