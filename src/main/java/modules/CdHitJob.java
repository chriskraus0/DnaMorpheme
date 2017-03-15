package modules;

// Imports.

// Java exception imports.
import java.io.IOException;
import java.util.logging.Level;
// Java utility imports.
import java.util.logging.Logger;

// Project specific imports.
import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;
import core.common.CommandState;

import core.InputPort;
import core.OutputPort;
import core.ModuleNode;

// Project specific exceptions.
import core.exceptions.CommandFailedException;
import core.exceptions.PipeTypeNotSupportedException;


public class CdHitJob extends Module {
	// Variables.
	
	private Logger logger;
	
	private String[] command;
	
	private ModuleNode moduleNode;
	
	private String input;
	
	// Constructors.
	public CdHitJob(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String[] cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd;
		this.logger = Logger.getLogger(CdHitJob.class.getName());
	}
	
	// Methods.
	
	// Setters.
		
	// End setters.
	
	@Override
	public void run () {
		try {
			CommandState cState = this.callCommand();
			if (cState.equals(CommandState.SUCCESS)) {
				this.setModuleState(ModuleState.SUCCESS);
				this.moduleNode.notifyModuleObserver();
			}
		} catch (CommandFailedException ce) {
			this.logger.log(Level.SEVERE, ce.getMessage());
			ce.printStackTrace();
		}
	}
		
	/**
	 * Calls an external command and processes data.
	 * @return CommandState commandState
	 * @throws CommandFailedException
	 */
	public synchronized CommandState callCommand() throws CommandFailedException {
		
		this.moduleNode = this.getSuperModuleNode();
		
		CommandState cState = CommandState.STARTING;
					
		// Checked exception. TODO: Add ExternalCommandHandler
		
		// Save input from pipe
		this.input = "";
				
		// Start processing of the command.
		cState = CommandState.PROCESSING;
		
		// Save number of read characters.
		int charNumber = 0;
	
		// Prepare char buffer "data".
		int bufferSize = 1024;
		char[] data = new char[bufferSize];
		
		// Read from InputPort (via CharPipe).		
		try {
			
				charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
				
				while (charNumber != -1) {
												
					// Check for interrupted threads.
					
					if (Thread.interrupted()) {
						this.getInputPort().getPipe().readClose();
						this.getOutputPort().getPipe().writeClose();
						throw new InterruptedException ("Thread interrupted");
					}
					
					// If the number of read characters is smaller than the buffer limit 
					// write the remaining characters in the String variable input.
					if (charNumber < bufferSize) {
						for (int i = 0; i < charNumber; i++)
						this.input += data[i];
					} else {
						this.input += new String(data);
					}
					
					charNumber = ((InputPort) this.getInputPort()).readFromCharPipe(data, 0, bufferSize);
					
				}

				this.getInputPort().getPipe().readClose();
		
						
		} catch (PipeTypeNotSupportedException pe) {
			this.logger.log(Level.SEVERE, pe.getMessage());
			pe.printStackTrace();
		}
		
		catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		} 
		
		catch (InterruptedException intE) {
			this.logger.log(Level.SEVERE, intE.getMessage());
			intE.printStackTrace();
		}
	
		this.setModuleState(ModuleState.INPUT_DONE);
		this.moduleNode.notifyModuleObserver();
		
		String output = this.input;
		// Write to OutputPort (via CharPipe).
		
		try {
			
			// Write to the output pipe.
			((OutputPort) this.getOutputPort()).writeToCharPipe(output);
			
			this.getOutputPort().getPipe().writeClose();
			
			this.setModuleState(ModuleState.OUTPUT_DONE);
			this.getSuperModuleNode().notifyModuleObserver();
			
			
		} catch (PipeTypeNotSupportedException pe) {
			this.logger.log(Level.SEVERE, pe.getMessage());
			pe.printStackTrace();
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		} 
			
				
		// If everything worked out return SUCCESS.
		cState = CommandState.SUCCESS;	
		
		return cState;
	}
	

}
