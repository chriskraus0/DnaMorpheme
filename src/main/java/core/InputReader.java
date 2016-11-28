package core;

// Imports.

// Java I/O imports.
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

// Project specific imports.
import core.common.Module;
import core.common.PipeState;
import core.common.ModuleState;
import core.common.ModuleType;
import core.common.CommandState;
import core.OutputPort;

// Project specific exceptions.
import core.exceptions.CommandFailedException;

public class InputReader extends Module {
	
	// Variables.
	private String command;
	
	// Constructors.
	public InputReader(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String cmd, JobController jobController) {
		super(moduleID, storageID, mType, iPortID, oPortID, jobController);
		this.command = cmd;
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
		
		
		try {
						
			//File inFile = new File (this.command);
			
			//FileReader fReader = new FileReader(inFile);
			//BufferedReader bReader = new BufferedReader(fReader);
			
			//String data = "";
			
			// Instantiate a new input stream.
			InputStream fileInputStream = new FileInputStream (this.command);
			
			// Define input buffer
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			
			/*while ( (data = (bReader.readLine())) != null) {
				
				if (Thread.interrupted()) {
					bReader.close();
					throw new InterruptedException(
							"Thread has been interrupted.");
				
								
				//((OutputPort) this.getOutputPort()).writeToCharPipe(data);
				
			}*/
			
			// Read file data into buffer and write to outputstream.
			
			// If the read information already reaches the end after first chunk,
			// directly show it to "readBytes" to avoid repeated display of the same
			// bytes.
			int readBytes = fileInputStream.read(buffer);
			while (readBytes != -1) {

				// Look for interrupted threads
				if (Thread.interrupted()) {
					fileInputStream.close();
					throw new InterruptedException(
							"Thread has been interrupted.");
				}
				
				String data = new String(buffer);

				((OutputPort) this.getOutputPort()).writeToCharPipe(data);
				readBytes = fileInputStream.read(buffer);
			}
						
			// close relevant I/O instances
			fileInputStream.close();
			
			//bReader.close();
		
			// Indicate that output was written.
			this.setModuleState(ModuleState.OUTPUT_DONE);
			
			this.getJobController().getModuleNode(this.getProducerModuleNodeName()).notifyModuleObserver();
			
			// This thread owns the pipe now.
			synchronized(this.getJobController().getModuleNode(this.getProducerModuleNodeName())) {	
				// Wait until the reading thread is finished reading.
				while (this.getJobController().getModuleNode(this.getProducerModuleNodeName()).getCosumerState().equals(ModuleState.INPUT_DONE)) {
					this.getJobController().getModuleNode(this.getProducerModuleNodeName()).wait();
				}
			}
				
			} catch (IOException ie) {
				System.err.println("IOException in " + this.getClass().toString() 
						+ "#callCommand()" + ": " + ie.getMessage());
				ie.printStackTrace();
				cState = CommandState.FAIL;
			} catch (InterruptedException intE) {
				System.err.println(intE.getMessage());
				intE.printStackTrace();
			} catch (Exception e) {
				System.err.println("Exception in " + this.getClass().toString() 
						+ "#callCommand()" + ": " + e.getMessage());
				e.printStackTrace();
				cState = CommandState.FAIL;
		}
			
		
		cState = CommandState.SUCCESS;
						
		return cState;
	}

}
