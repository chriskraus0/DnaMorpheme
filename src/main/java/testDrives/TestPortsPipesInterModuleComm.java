package testDrives;

// Imports.

// Java utility imports.
import java.util.HashMap;

// Project-specific imports.
import core.CoreController;
import core.ModuleBuilder;

import modules.commands.Commands;

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
public class TestPortsPipesInterModuleComm {

	// Variables.
	
	// Constructors.
	
	public TestPortsPipesInterModuleComm () {}
	
	// Methods.
	public static void main (String[] args)  {
		TestPortsPipesInterModuleComm thisTest = new TestPortsPipesInterModuleComm();
		thisTest.go();
		
	}
		
	public void go() {
	
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes();
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Create 3 new modules. 
		
		HashMap<Commands, String> inputTestJobCommand = new HashMap<Commands, String>();
		inputTestJobCommand.put(Commands.path, "testFiles/testFile1.txt");
		int testInputModule = moduleBuilder.createInputTestJob(inputTestJobCommand);
		
		HashMap<Commands, String> testTransferModuleCommand = new HashMap<Commands, String>();
		testTransferModuleCommand.put(Commands.path, "Test Transfer");
		int testTransferModule = moduleBuilder.createTestTransferJob(testTransferModuleCommand);
		
		HashMap<Commands, String> testOputputCommand = new HashMap<Commands, String>();
		testOputputCommand.put(Commands.path, "Test Output");
		int testOputputModule = moduleBuilder.createTestoutputJob(testOputputCommand);
	
		
		// Prepare moduleNodes.
		String testInputTransferNodeName = moduleBuilder.prepareJobs(testInputModule,testTransferModule);
		String testTransferOutputNodeName = moduleBuilder.prepareJobs(testTransferModule,testOputputModule);
		
		
		// Start new threads for "inputCdHitNodeName".
		try {
			moduleBuilder.startJobs(testInputTransferNodeName);
		} catch (InterruptedException intE) {
			System.err.println(intE.getMessage());
			intE.printStackTrace();
		}
		
		// Start new threads for "cdHitQpmsNodeName".
		try {
			moduleBuilder.startJobs(testTransferOutputNodeName);
		} catch (InterruptedException intE) {
			System.err.println(intE.getMessage());
			intE.printStackTrace();
		}
		
	}
}
