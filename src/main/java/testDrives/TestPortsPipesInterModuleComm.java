package testDrives;

import core.CoreController;
import core.ModuleBuilder;
import core.common.ModuleState;
import core.common.PipeType;
import core.exceptions.OccupiedException;
import core.exceptions.PipeTypeNotSupportedException;
import core.exceptions.CommandFailedException;
import core.exceptions.NotFoundException;

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
public class TestPortsPipesInterModuleComm implements Runnable {

	// Variables.
	
	// Constructors.
	
	public TestPortsPipesInterModuleComm () {}
	
	// Methods.
	public static void main (String[] args)  {
		TestPortsPipesInterModuleComm thisTest = new TestPortsPipesInterModuleComm();
		Thread thisThread = new Thread(thisTest);
		thisThread.start();
		
	}
	
	public void run() {
		this.go();
	}
	
	public synchronized void go() {
		
		// Create a new Singleton "CoreController"
		CoreController.getInstance();
		
		// Check for the existing configuration file.
		CoreController.checkExternalProgrammes("config/config.txt");
		ModuleBuilder moduleBuilder = CoreController.generateModuleBuilder();
		
		// Create 3 new modules. 

		// TODO: Continue here!
		int inputModule = moduleBuilder.createNewInputReader("testFiles/testFile1.txt");
		int cdHitModule = moduleBuilder.createNewCdHitJob("Test CdHitJob");
		int qPMS9Module = moduleBuilder.createNewQpms9Job("Test qPMS9Module");
	
		
		// Create pipes.
		ModuleBuilder.getModule(inputModule).getOutputPort().createNewPipe(PipeType.CHAR);
		ModuleBuilder.getModule(cdHitModule).getInputPort().createNewPipe(PipeType.CHAR);
		ModuleBuilder.getModule(cdHitModule).getOutputPort().createNewPipe(PipeType.CHAR);
		ModuleBuilder.getModule(qPMS9Module).getInputPort().createNewPipe(PipeType.CHAR);
		
		// Connecting ports via pipes.
		try {
			ModuleBuilder.getModule(inputModule).getOutputPort().connectPorts(
				ModuleBuilder.getModule(cdHitModule).getInputPort().getPipe(), 
				ModuleBuilder.getModule(cdHitModule).getInputPort());
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
			ModuleBuilder.getModule(cdHitModule).getOutputPort().connectPorts(
				ModuleBuilder.getModule(qPMS9Module).getInputPort().getPipe(), 
				ModuleBuilder.getModule(qPMS9Module).getInputPort());
		} 
		
		catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (OccupiedException oe) {
			System.err.println(oe.getMessage());
			oe.printStackTrace();
		}
		
		// Run threads.
		try {
			moduleBuilder.startJob(inputModule, this);
			moduleBuilder.startJob(cdHitModule, this);
			moduleBuilder.startJob(qPMS9Module, this);	
			
		} catch (InterruptedException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		}
		
		// Clean up pipes.
		try {
			wait();
			ModuleBuilder.getModule(inputModule).getOutputPort().removePipe();
			
			wait();
			ModuleBuilder.getModule(cdHitModule).getInputPort().removePipe();
			
			wait();
			ModuleBuilder.getModule(cdHitModule).getOutputPort().removePipe();
			ModuleBuilder.getModule(qPMS9Module).getInputPort().removePipe();
		
		} catch (NotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException intE) {
			// TODO Auto-generated catch block
			intE.printStackTrace();
		}
		
		// Sent in data which should be transformed.
	}
}
