package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.logging.Level;

// JUnit imports.
import org.junit.Test;

import core.CoreController;
import core.ModuleBuilder;
import core.ModuleObserver;
import core.common.ModuleState;
import junit.framework.TestCase;
import modules.commands.Commands;

public class Qpms9CommandTest extends TestCase {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public Qpms9CommandTest () {
		
		// Call Logger to get a new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	
	// Methods.
	
	/**
	 * JUnit test method.
	 */
	@Test
	public void start() {
		
		// Start the test.
		this.testRun();
	}
	
	
	public void testRun () {
		
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
		inputReaderCommand.put(Commands.path, "testFiles/testFile4.fasta");
		int inputReaderID = moduleBuilder.createNewInputReader(inputReaderCommand);
		
		// Create new qpms9 module.
		HashMap<Commands, String> qpms9JobCommand = new HashMap<Commands, String>();
		qpms9JobCommand.put(Commands.fasta, "testFiles/testFile4.fasta");
		qpms9JobCommand.put(Commands.l, "8");
		qpms9JobCommand.put(Commands.d, "3");
		qpms9JobCommand.put(Commands.q, "100");
		qpms9JobCommand.put(Commands.o, "tmpData/qpms9Test.out");
		
		int qpms9JobID = moduleBuilder.createNewQpms9Job(qpms9JobCommand);
		
		// Prepare module nodes.
		String inputReaderQpms9JobNodeName = moduleBuilder.prepareJobs(inputReaderID, qpms9JobID);
		
		// Start new thread for "inputReaderCdHitJobNodeName".
		try {
			moduleBuilder.startJobs(inputReaderQpms9JobNodeName);
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
