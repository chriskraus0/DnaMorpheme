package core;

// Imports.
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import core.common.Pipe;

/**
 * This class sets up a char pipe for inter-module communication. 
 * @author christopher
 *
 */

public class CharPipe implements Pipe {

	// Variables.
	private PipedWriter pipeOutput;
	private PipedReader pipeInput;
	// End variables.
	
	// Constructors.
	public CharPipe () throws IOException {
		// Create a new PipedWriter and PipedReader pair.
		this.pipeReset();
	}
	// End constructors.
	
	// Methods.
	
	/**
	 * CharPipe reads data in form of buffered char array.
	 * @param char [] data - buffered char array
	 * @param int offset - offset of the initial character in data 
	 * @param int length - length of the buffered char array
	 * @throws IOException
	 */
	public void read(char[] data, int offset, int length) throws IOException {
		this.pipeInput.read(data, offset, length);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeClose() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pipeReset() throws IOException {
		
		// Create new PipedWriter for this object.
		this.pipeOutput = new PipedWriter();
		
		// Create new PipedReader and connect it to the PipedWriter
		// hence forming the pipe.
		this.pipeInput = new PipedReader(this.pipeOutput);
		
	}
	// End methods.	
}
