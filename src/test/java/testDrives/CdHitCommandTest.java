package testDrives;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// JUnit imports.
import org.junit.Test;
import junit.framework.TestCase;
import modules.commands.CdHitCommands;
//Project-specific imports.
import core.CoreController;
import core.ModuleBuilder;

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
		
		// Create new InputReader module.
		String[] inputReaderCommand = new String[1];
		inputReaderCommand[0] = "testFiles/testFile.fasta";
		int inputReaderID = moduleBuilder.createNewInputReader(inputReaderCommand);
		
		// Create new CdHitJob module.
		String[] cdHitJobCommand = new String[7];
		cdHitJobCommand[0] = "cdhit";
		cdHitJobCommand[1] = "-" + CdHitCommands.T.toString();
		cdHitJobCommand[2] = "1";
		cdHitJobCommand[3] = "-" + CdHitCommands.i.toString();
		cdHitJobCommand[4] = "testFiles/testFile.fasta";
		cdHitJobCommand[5] = "-" + CdHitCommands.o.toString();
		cdHitJobCommand[6] = "tmpData/test.out";
		
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
		
	}
}
