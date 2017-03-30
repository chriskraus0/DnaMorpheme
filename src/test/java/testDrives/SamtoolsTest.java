package testDrives;

// Imports.

// Java imports.
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.logging.Level;

// JUnit test class imports.
import junit.framework.TestCase;
import org.junit.Test;

//Project-specific imports.
import core.CoreController;
import core.ModuleBuilder;
import core.ModuleObserver;
import core.PhysicalConstants;
import core.common.ModuleState;
import modules.commands.Commands;

public class SamtoolsTest extends TestCase {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public SamtoolsTest () {
		
		// Call Logger to get a new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Test method.
	@Test
	public void testRun () {
		this.start();
	}
	
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
		HashMap<Commands, String> samtoolsJobCommand = new HashMap<Commands, String>();
		samtoolsJobCommand.put(Commands.b, "");
		samtoolsJobCommand.put(Commands.S, "");
		samtoolsJobCommand.put(Commands.input, "testFiles" + PhysicalConstants.getPathSeparator() + "testFile7.sam");
		samtoolsJobCommand.put(Commands.redirect, ">");
		samtoolsJobCommand.put(Commands.output, "tmpData" + PhysicalConstants.getPathSeparator() + "testFile7.bam");
		
		int samtoolsJobID = moduleBuilder.createNewCdHitJob(samtoolsJobCommand);
		
		// Prepare module nodes.
		String inputReaderSamtoolsJobNodeName = moduleBuilder.prepareJobs(inputReaderID, samtoolsJobID);
		
		// Start new thread for "inputReaderCdHitJobNodeName".
		try {
			moduleBuilder.startJobs(inputReaderSamtoolsJobNodeName);
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
