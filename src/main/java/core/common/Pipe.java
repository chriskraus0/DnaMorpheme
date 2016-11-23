package core.common;

// Imports.
import java.io.IOException;

/**
 * This interface is the template for all I/O pipes.
 * @author christopher
 *
 */

public interface Pipe {
	
	// Abstract methods.
	/**
	 * Closes the pipe's input.
	 * @throws IOException
	 */
	public void readClose() throws IOException;
	
	/**
	 * Closes the pipe's output.
	 * @throws IOException
	 */
	public void writeClose() throws IOException;
	
	/**
	 * Resets the input and output of this pipe for re-use.
	 * @throws IOExcpetion
	 */
	public void pipeReset() throws IOException;
	
	/**
	 * Returns the PipeType of a Pipe.
	 * @return PipeType
	 */
	public PipeType getPipeType ();
	
}
