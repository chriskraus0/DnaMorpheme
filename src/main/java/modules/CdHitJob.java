package modules;

import core.common.CommandState;

// Imports.

import java.io.IOException;

// Project specific imports.
import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;
import core.JobController;
import core.InputPort;
import core.OutputPort;
import core.ModuleNode;

// Project specific exceptions.
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;

// TODO: Just a bare bones module. This must be extended!
public class CdHitJob extends Module {
	// Variables.
	
	private String command;
	private JobController jobController;
	
	// Constructors.
	public CdHitJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd, JobController jobController) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		this.jobController = jobController;
	}
	
	// Methods.
	
	@Override
	public void run () {
		try {
			CommandState cState = this.callCommand();
			if (cState.equals(CommandState.SUCCESS)) {
				this.setModuleState(ModuleState.SUCCESS);
			}
		} catch (CommandFailedException ce) {
			System.err.println(ce.getMessage());
			ce.printStackTrace();
		}
	}
	
	public synchronized CommandState callCommand() throws CommandFailedException {
		CommandState cState = CommandState.STARTING;
		
		ModuleNode consumerNode = this.jobController.getModuleNode(this.getConsumerModuleNodeName());
		
		// Test system output.
		System.out.println("CdHitJob with moduleID \"" + this.getModuleID() + "\" and storageID \"" + this.getStorageID() + "\" :");
		System.out.println("Command " + this.command + " called");
		System.out.println("Associated storageID " + this.getStorageID());
		
		// Checked exception. TODO: Add ExternalCommandHandler
		
		// Save input from pipe
		String input = "";
		
		
		
		// Start processing of the command.
		cState = CommandState.PROCESSING;
		
		// Save number of read characters.
		int charNumber = 0;
	
		// Prepare char buffer "data".
		int bufferSize = 1024;
		char[] data = new char[bufferSize];
		
		// Read from InputPort (via CharPipe).		
		try {
			
			// Synchronize the pipe.
			synchronized (consumerNode) {
				charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
				
				while (charNumber != -1) {
												
					// Check for interrupted threads.
					
					/*TODO:
					 * if (Thread.interrupted()) {
						throw new InterruptedException ("Thread interrupted");
					}*/
					
					// If the number of read characters is smaller than the buffer limit 
					// write the remaining characters in the String variable input.
					if (charNumber < bufferSize) {
						for (int i = 0; i < charNumber; i++)
						input += data[i];
					} else {
						input += new String(data);
					}
					
					charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
					
				}

				// Notify all threads that this thread is done reading.
							
				while (consumerNode.getProducerState().equals(ModuleState.OUTPUT_DONE)) {
					this.jobController.getModuleNode(this.getConsumerModuleNodeName()).notifyAll();
				}
				
				this.setModuleState(ModuleState.INPUT_DONE);
				this.jobController.getModuleNode(this.getConsumerModuleNodeName()).notifyModuleObserver();
			}
						
		} catch (PipeTypeNotSupportedException pe) {
			System.err.println(pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		} 
		
			
		input += "Here is a new modified additional line";
		
			
		
		// Notify the ModuleObserver.
		this.jobController.getModuleNode(this.getConsumerModuleNodeName()).notifyModuleObserver();
		
		// Synchronize the output pipe.
		synchronized (this.jobController.getModuleNode(this.getProducerModuleNodeName())) {
			// Write to OutputPort (via CharPipe).
			
			try {
				
				// Write to the output pipe.
				((OutputPort) this.getOutputPort()).writeToCharPipe(input);
				
				this.setModuleState(ModuleState.OUTPUT_DONE);
				this.jobController.getModuleNode(this.getProducerModuleNodeName()).notifyModuleObserver();
				
				// Wait unit reading from the pipe is finished.
				while (this.jobController.getModuleNode(this.getProducerModuleNodeName()).equals(ModuleState.INPUT_DONE)) {
					this.jobController.getModuleNode(this.getProducerModuleNodeName()).wait();
				}
				
			} catch (PipeTypeNotSupportedException pe) {
				System.err.println(pe.getMessage());
				pe.printStackTrace();
			} catch (IOException ie) {
				System.err.println(ie.getMessage());
				ie.printStackTrace();
			} catch (InterruptedException intE) {
				System.err.println(intE.getMessage());
				intE.printStackTrace();
			}
			
			
		
		}
		
		
		// If everything worked out return SUCCESS.
		cState = CommandState.SUCCESS;	
		
		return cState;
	}
	

}
