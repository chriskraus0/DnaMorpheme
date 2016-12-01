package core;

import java.io.IOException;

// Imports.

// Project specific imports.
import core.common.AbstractPort;
import core.common.Pipe;
import core.common.PortType;
import core.common.PipeType;

// Project specific exceptions.
import core.exceptions.NotFoundException;
import core.exceptions.PipeTypeNotSupportedException;

public class InputPort extends AbstractPort {
	
	// Constants.
	private final PortType portType = PortType.INPUT;
	
	// Constructors.
	
	public InputPort (int portID, int moduleID) {
		super(portID, moduleID);
	}
	
	public InputPort (int portID, int moduleID, Pipe pipe) {
		super(portID, moduleID, pipe);
	}

	// End constructors.
	
	// Methods.
	
	/**
	 * Reads from CharBytes char by char and returns an integer which encodes the read char.
	 * @return int intergerCodingForChar
	 * @throws PipeTypeNotSupportedException
	 * @throws IOException
	 */
	public int readFromCharPipe () throws PipeTypeNotSupportedException, IOException {
		
		if (!this.getPipeType().equals(PipeType.CHAR)) {
			throw new PipeTypeNotSupportedException ("Pipe of type \""
					+ this.getPipeType()
					+ "\" is not supported by port with ID \""
					+ this.getPortID()
					+ "\".");
		}
		
		return ((CharPipe) this.getPipe()).read();
		
	}
	
	/**
	 * Reads the char array "data" from CharPipe and returns the number of read chars. 
	 * @param char[] data
	 * @param int offset
	 * @param int length
	 * @return int numberOfReadChars
	 * @throws PipeTypeNotSupportedException
	 * @see core.exceptions.PipeTypeNotSupportedException
	 * @throws IOException
	 */
	public int readFromCharPipe (char[] data, int offset, int length) throws PipeTypeNotSupportedException, IOException {
		
		if (!this.getPipeType().equals(PipeType.CHAR)) {
			throw new PipeTypeNotSupportedException ("Pipe of type \""
					+ this.getPipeType()
					+ "\" is not supported by port with ID \""
					+ this.getPortID()
					+ "\".");
		}
		
		return ((CharPipe) this.getPipe()).read(data, offset, length);
		
	}
	

	/**
	 * Reads a single byte and returns it value in form of an integer.
	 * @return int read byte
	 * @throws PipeTypeNotSupportedException
	 * @see core.exception.PipeTypeNotSupportedException
	 * @throws IOException
	 */
	public int readFromBytePipe() throws PipeTypeNotSupportedException, IOException {
		
		if (!this.getPipeType().equals(PipeType.BYTE)) {
			throw new PipeTypeNotSupportedException ("Pipe of type \""
					+ this.getPipeType()
					+ "\" is not supported by port with ID \""
					+ this.getPortID()
					+ "\".");
		}
		
		return ((BytePipe) this.getPipe()).read();
		
	}
	
	/**
	 * Reads bytes from BytePipe. It reads from a byte array (data).
	 * @see java.io.PipedOutputStream#write(byte[], int, int) 
	 * @param byte[] data 
	 * @param int offset
	 * @param int length
	 * @return int numberOfReadBytes
	 * @throws PipeTypeNotSupportedException
	 * @throws IOException
	 */
	public int readFromBytePipe (byte[] data, int offset, int length) throws PipeTypeNotSupportedException, IOException {
		
		if (!this.getPipeType().equals(PipeType.BYTE)) {
			throw new PipeTypeNotSupportedException ("Pipe of type \""
					+ this.getPipeType()
					+ "\" is not supported by port with ID \""
					+ this.getPortID()
					+ "\".");
		}
		
		return ((BytePipe) this.getPipe()).read(data, offset, length);
		
	}
	
	// Overridden methods.
	
	// Getters.
	
	@Override
	public PortType getPortType () {
		return this.portType;
	}
	
	// End getters.
	
	@Override
	public void removePipe() throws NotFoundException {
		try {
			this.getPipe().readClose();
			this.getPipe().writeClose();
		} catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		}
	}

	@Override
	public void reset() {
		try {
			this.getPipe().writeClose();
			this.getPipe().pipeReset();
		} catch (IOException ie) {
			System.err.println("ERROR: " + ie.getMessage());
			ie.printStackTrace();
		}
	}
	
	// End methods.

}
