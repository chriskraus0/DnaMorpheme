package core;

import java.io.IOException;

// Imports.

// Project specific imports.
import core.common.AbstractPort;
import core.common.Pipe;
import core.common.PortState;
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
	
	// TODO read from byte pipe.
	
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
		// TODO Auto-generated method stub
		
	}

}
