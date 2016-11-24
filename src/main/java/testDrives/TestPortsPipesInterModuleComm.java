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
		thisThread.setName("mainThread");
		thisThread.start();
		
	}
	
	public void run() {
		this.go();
	}
	
	public void go() {
		
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
		
		// Start first 2 threads.
		try {
			moduleBuilder.startJob(inputModule);
			moduleBuilder.startJob(cdHitModule);
		} catch (InterruptedException intE) {
			System.err.println(intE.getMessage());
			intE.printStackTrace();
		}
		
		
		// Avoid Deadlock by allowing ownership of the pipe AFTER the other threads!
		synchronized (ModuleBuilder.getModule(inputModule).getOutputPort().getPipe()) {
			// Start first 2 threads. Wait to let threads finish first, then clean up pipes.
			try {	
				
				// Wait for inputModule and cdHitModule to finish data transfer.
				while (!ModuleBuilder.getModule(cdHitModule).getModuleState().equals(ModuleState.SUCCESS)) {
					ModuleBuilder.getModule(inputModule).getOutputPort().getPipe().wait();
				}
				ModuleBuilder.getModule(inputModule).getOutputPort().removePipe();
				ModuleBuilder.getModule(cdHitModule).getInputPort().removePipe();
			} catch (NotFoundException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (InterruptedException intE) {
				intE.printStackTrace();
			}
		}
		
		// Start the third thread.
		try {
			moduleBuilder.startJob(qPMS9Module);
		} catch (InterruptedException intE) {
			System.err.println(intE.getMessage());
			intE.printStackTrace();
		}
		
		// Avoid Deadlock by allowing ownership of the pipe AFTER the other threads!
		synchronized (ModuleBuilder.getModule(cdHitModule).getOutputPort().getPipe()) {
			// Start the third thread. Wait to let threads finish first, then clean up pipes.
			try {
				
				// Wait for cdHitModule and qPMS9Module to finish first.
				while (!ModuleBuilder.getModule(qPMS9Module).getModuleState().equals(ModuleState.SUCCESS)) {
					ModuleBuilder.getModule(qPMS9Module).getInputPort().getPipe().wait();
				}
				
				// Clean up the bridging pipe.
				ModuleBuilder.getModule(cdHitModule).getOutputPort().removePipe();
				ModuleBuilder.getModule(qPMS9Module).getInputPort().removePipe();
			
			} catch (NotFoundException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (InterruptedException intE) {
				intE.printStackTrace();
			}
		}
		
	}
}
