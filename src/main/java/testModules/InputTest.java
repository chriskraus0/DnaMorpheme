package testModules;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// Java I/O imports.
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

// Project specific imports.
import core.common.Module;
import core.common.ModuleState;
import core.common.ModuleType;
import core.common.CommandState;
import core.OutputPort;
import core.ModuleNode;
import core.CharPipe;

// Project specific exceptions.
import core.exceptions.CommandFailedException;

/**
 * Test module which retrieves data from a test file and transfers it 
 * to a sink via a output port and a char pipe.
 * @author christopher
 *
 */

public class InputTest extends Module {
	
	// Variables.
	private String[] command;
	private ModuleNode moduleNode;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public InputTest(int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID, String[] cmd) {
		super(moduleID, storageID, mType, iPortID, oPortID);
		this.command = cmd; 
		
		// Call the Logger factory to get a new logger.
		this.logger = Logger.getLogger(InputTest.class.getName());
	}
	
	// Methods.
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

	public synchronized CommandState callCommand() throws CommandFailedException {
		
		this.moduleNode = this.getSuperModuleNode();
		
		CommandState cState = CommandState.STARTING;
						
		try {

				InputStream fileInputStream = new FileInputStream (this.command[0]);
				
				// Define input buffer
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];
								
				// Read file data into buffer and write to output stream.
				
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
					
					// Re-set buffer to avoid set bytes from last iteration.
					buffer = new byte[bufferSize];
					readBytes = fileInputStream.read(buffer);
				}
							
				// close relevant I/O instances
				fileInputStream.close();
				
				//bReader.close();
				
				// Close the pipe after writing.
				((CharPipe) this.getOutputPort().getPipe()).writeClose();
				
				// Indicate that output was written.
				this.setModuleState(ModuleState.OUTPUT_DONE);
				
				this.moduleNode.notifyModuleObserver();
			
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
			cState = CommandState.FAIL;
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			cState = CommandState.FAIL;
		}
		
		cState = CommandState.SUCCESS;
						
		return cState;
	}
}
