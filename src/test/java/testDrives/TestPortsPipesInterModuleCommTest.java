package testDrives;


// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// Import JUnit test classes.
import org.junit.Test;
import junit.framework.TestCase;

// Import project-specific classes.
import core.CoreController;
import core.ModuleBuilder;
import testModules.TestOutput;

/**
 * Test drive class for testing of the inter-module communication via ports and pipes.
 * This test drive class creates three instances of three modules (one per module),
 * which will be inter-connected via I/O-ports and I/O-pipes. 
 * The first module will read data from a specified testing folder
 * and pass that information to the second. The second module will modify that data and
 * send it to the third which will in return write the data to the hard disk.
 * The following classes will be tested:
 * @see core.common.Pipe
 * @see core.common.Port
 * @see core.common.InputPort
 * @see core.common.OutputPort
 * @author christopher
 */

public class TestPortsPipesInterModuleCommTest extends TestCase {
	
	// Variables.
	
	private Logger logger;
	
	// Test variables.
	private String output;
	private String testVar;
	
	// Constructors.
	
	public TestPortsPipesInterModuleCommTest () {
		
		// Initialize the logger.
		this.logger = Logger.getLogger(TestPortsPipesInterModuleCommTest.class.getName());
	}
	
	// Test the transferred and modified text for correctness.
	@Test
	public void testTransferredStrings () {
		
		TestPortsPipesInterModuleComm thisTest = new TestPortsPipesInterModuleComm();
		
		// Start the threads.
		thisTest.go();
		
		assertEquals(output, testVar);
		
	}
		
	public void go() {
	
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Create 3 new modules. 
		
		String[] inputTestJobCommand = new String[1];
		inputTestJobCommand[0] = "testFiles/testFile1.txt";
		int testInputModule = moduleBuilder.createInputTestJob(inputTestJobCommand);
		
		String[] testTransferModuleCommand = new String[1];
		testTransferModuleCommand[0] = "Test Transfer";
		int testTransferModule = moduleBuilder.createTestTransferJob(testTransferModuleCommand);
		
		String[] testOputputCommand = new String[1];
		testOputputCommand[0] = "Test Output";
		int testOputputModule = moduleBuilder.createTestoutputJob(testOputputCommand);
		
	
	
		
		// Prepare moduleNodes.
		String testInputTransferNodeName = moduleBuilder.prepareJobs(testInputModule,testTransferModule);
		String testTransferOutputNodeName = moduleBuilder.prepareJobs(testTransferModule,testOputputModule);
		
		// Set up test variable testVar.
		
		this.testVar = "NEW LINE!!!\n"
					+ "Hello this is the test file \"testFile1.txt\".\n"
					+ "This fill will serve das a file to be tested.\n"
					+ "I have to write some stuff into this file so that it\n" 
					+ "will exceed the committed buffersize of 1024 characters.\n"
					+ "Hmm this may take some more time than I initally anticipated.\n"
					+ "But Anyhow let's continue to write some weird stuff into this file.\n"
					+ "Did you recognize how continously the size of each sentence increases?\n"
					+ "Fascinating isn't it?\n"
					+ "Oh well, there goes the symmetry.\n"
					+ "How sad.\n"
					+ "Sad indeed.\n"
					+ "Or maybe not.\n"
					+ "I don't know yet.\n"
					+ "But maybe it will increase again.\n"
					+ "But for what reason and what extend?\n"
					+ "That is a very good question which I dare to answer here:\n"
					+ "Long long (not integer) ago there was a shining beautiful steed\n"
					+ "which was called Karl. It out-shone (it is a real word!) all the other\n"
					+ "steeds which were next to it, eventhough they ready did not give a damn.\n"
					+ "And so in its very limited wisdom it spontaneously decided to tell its story.\n"
					+ "Long long ago there was a shingin beautiful steed which was called Karl.\n" 
					+ "It out-shone (it is a real word!) all the other steeds which were next to it,\n"
					+ "eventhough they ready did not give a damn.\n"
					+ "And so in its very limited wisdom it spontaneously decided to tell its story.\n"
					+ "Long long ago there was a shingin beautiful steed which was called Karl.\n" 
					+ "It out-shone (it is a real word!) all the other steeds which were next to it,\n"
					+ "eventhough they ready did not give a damn.\n"
					+ "HAH! more than 1024 characters! DONE!\n"
					+ "Ah to make this story complete: Hello World!\n";
		
		// Start new threads for "inputCdHitNodeName".
		try {
			moduleBuilder.startJobs(testInputTransferNodeName);
		} catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		}
		
		// Start new threads for "cdHitQpmsNodeName".
		try {
			moduleBuilder.startJobs(testTransferOutputNodeName);
			this.output = ((TestOutput) ModuleBuilder.getModule(testOputputModule)).returnFinalOutput();
		} catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		}
		
		
	}
	
}
