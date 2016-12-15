package core;

// Imports.

// Java I/O imports.
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

// Project specific imports.
import core.common.Pipe;
import core.common.PipeType;
import core.common.PipeState;

/**
 * This class sets up a char pipe for inter-module communication. 
 * @author christopher
 *
 */

public class CharPipe implements Pipe {

	// Constants.
	private final PipeType pipeType = PipeType.CHAR;
	
	// Variables.
	private PipedWriter pipeOutput;
	private PipedReader pipeInput;
	private PipeState pipeState;
	
	// End variables.
	
	// Constructors.
	public CharPipe () throws IOException {
		// Create a new PipedWriter and PipedReader pair.
		this.pipeState = PipeState.STARTING;
		this.pipeReset();
	}
	// End constructors.
	
	// Methods.
	
	// Getters.
	@Override
	public PipeType getPipeType() {
		return this.pipeType;
	}
	
	@Override 
	public PipeState getPipeState() {
		return this.pipeState;
	}
	
	// End Getters.
	
	// Setters.
	@Override 
	public void setPipeState(PipeState pState) {
		this.pipeState = pState;
	}
	
	// End setters.
	
	/**
	 * CharPipe reads data in form of buffered char array.
	 * @param char [] data - buffered char array
	 * @param int offset - offset of the initial character in data 
	 * @param int length - length of the buffered char array
	 * @throws IOException
	 */
	public int read(char[] data, int offset, int length) throws IOException {
		return this.pipeInput.read(data, offset, length);
	}
	
	/**
	 * Reads single character.
	 * @return Char Integer of character. 
	 * @throws IOException
	 */
	public int read() throws IOException {
		return this.pipeInput.read();
	}
	
	/**
	 * CharPipe writes whole strings.
	 * @see java.io.Writer#write(String) Writer.write
	 * @param data.
	 * @throws IOException.
	 */
	public void write (String data) throws IOException {
		this.pipeOutput.write(data);
	}
	
	/**
	 * CharPipe writes data in form of buffered char arrays.
	 * @see java.io.PipedWrite#write(char[], int, int) PipedReader.write 
	 * @param char[] data - write text as char array of specific length
	 * @param int offset - the start offset of the buffered char array data
	 * @param int length - length of each buffered char array
	 * @throws IOException
	 */
	public void write(char[] data, int offset, int length) throws IOException {
		this.pipeOutput.write(data, offset, length);
	}
	
	@Override
	public void readClose() throws IOException {
		this.pipeInput.close();
	}

	@Override
	public void writeClose() throws IOException {
		this.pipeOutput.close();
	}

	@Override
	public void pipeReset() throws IOException {
		
		// State of this pipe changed after being connected to 2 ports.
		this.pipeState = PipeState.PROCESSING;
		
		// Create new PipedWriter for this object.
		this.pipeOutput = new PipedWriter();
		
		// Create new PipedReader and connect it to the PipedWriter
		// hence forming the pipe.
		this.pipeInput = new PipedReader(this.pipeOutput);
		
	}
	
	// End methods.	
}
