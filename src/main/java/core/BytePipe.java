package core;

//Imports.

// Java I/O imports.
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;


// Project specific imports.
import core.common.Pipe;
import core.common.PipeState;
import core.common.PipeType;

/**
 * This class sets up a byte pipe for inter-module communication. 
 * @author christopher
 *
 */
public class BytePipe implements Pipe {
	
	// Constants.
	private final PipeType pipeType = PipeType.BYTE;
		
	// Variables.
	private PipedOutputStream pipeOutput;
	private PipedInputStream pipeInput;
	private PipeState pipeState;
	// End variables.

	// Constructors.
	public BytePipe () throws IOException {
		// Create a new PipedInputStream and PipedOutputStream pair.
		this.pipeState = PipeState.STARTING;
		this.pipeReset();
	}
	// Methods.
	
	// Setters.
	
	@Override
	public void setPipeState(PipeState pState) {
		this.pipeState = pState;
	}
	
	// End setters.
	
	// Getters.
	
	@Override
	public PipeType getPipeType() {
		return this.pipeType;
	}
	
	@Override
	public PipeState getPipeState() {
		return this.pipeState;
	}
	
	// End getters.
	
	/**
	 * BytePipe reads data in form of buffered byte array.
	 * @param byte [] data - buffered byte array
	 * @param int offset - offset of the initial character in data 
	 * @param int length - length of the buffered char array
	 * @throws IOException
	 */
	public int read(byte[] data, int offset, int length) throws IOException {
		return this.pipeInput.read(data, offset, length);
	}
	
	/**
	 * Reads single byte.
	 * @return Integer of byte. 
	 * @throws IOException
	 */
	public int read() throws IOException {
		return this.pipeInput.read();
	}
	
	/**
	 * BytePipe writes single byte.
	 * @see java.io.Writer#write(String) Writer.write
	 * @param byte data
	 * @throws IOException
	 */
	public void write (byte data) throws IOException {
		this.pipeOutput.write(data);
	}
	
	/**
	 * BytePipe writes data in form of buffered byte arrays.
	 * @see java.io.PipedOutputStream#write(byte[], int, int) 
	 * @param byte[] data - write text as byte array of specific length
	 * @param int offset - the start offset of the buffered char array data
	 * @param int length - length of each buffered char array
	 * @throws IOException
	 */
	public void write(byte[] data, int offset, int length) throws IOException {
		this.pipeOutput.write(data, offset, length);
	}
	
	@Override
	public void pipeReset() throws IOException {
		// State of this pipe changed after being connected to 2 ports.
		this.pipeState = PipeState.PROCESSING;
		
		// Create new PipedWriter for this object.
		this.pipeOutput = new PipedOutputStream();
		
		// Create new PipedReader and connect it to the PipedWriter
		// hence forming the pipe.
		this.pipeInput = new PipedInputStream(this.pipeOutput);
	}

	@Override
	public void readClose() throws IOException {
		this.pipeInput.close();	
	}
	
	@Override
	public void writeClose() throws IOException {
		this.pipeOutput.close();	
	}
}
