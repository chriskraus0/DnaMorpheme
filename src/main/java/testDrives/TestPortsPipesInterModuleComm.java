package testDrives;

import core.CoreController;
import core.ModuleBuilder;
import core.exceptions.OccupiedException;
import core.exceptions.PipeTypeNotSupportedException;
import core.exceptions.CommandFailedException;

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
		thisTest.run();
		
	}
	
	public void run() {
		
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes("config/config.txt");
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Create 3 new modules. 
		
		int inputModule = moduleBuilder.createNewInputReader();
		int cdHitModule = moduleBuilder.createNewCdHitJob();
		int qPMS9Module = moduleBuilder.createNewQpms9Job();
		
		
		// Connecting ports via pipes.
		try {
			moduleBuilder.getModule(inputModule).getOutputPort().connectPorts(
				moduleBuilder.getModule(cdHitModule).getInputPort().getPipe(), 
				moduleBuilder.getModule(cdHitModule).getInputPort());
		} 
		
		catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (OccupiedException oe) {
			System.err.println(oe.getMessage());
			oe.printStackTrace();
		}
		
		// Connecting ports via pipes.
		try {
			moduleBuilder.getModule(cdHitModule).getOutputPort().connectPorts(
				moduleBuilder.getModule(qPMS9Module).getInputPort().getPipe(), 
				moduleBuilder.getModule(qPMS9Module).getInputPort());
		} 
		
		catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (OccupiedException oe) {
			System.err.println(oe.getMessage());
			oe.printStackTrace();
		}
		
		// Read external file and send it to the next module.
		try {
			moduleBuilder.getModule(inputModule).callCommand("testFiles/inFile.txt", moduleBuilder.getModule(inputModule).getStorageID());
		} catch (CommandFailedException ce) {
			System.err.println(ce.getMessage());
			ce.printStackTrace();
		}
		
		// Sent in data which should be transformed.
	}
}
