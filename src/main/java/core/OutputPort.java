package core;

// Imports.

// Java exceptions.
import java.io.IOException;

// Project specific imports.
import core.common.Pipe;
import core.common.PipeType;
import core.common.PortType;
import core.common.AbstractPort;

// Project specific exceptions.
import core.exceptions.NotFoundException;
import core.exceptions.PipeTypeNotSupportedException;

public class OutputPort extends AbstractPort {
	
	// Constants.
	private final PortType portType = PortType.OUTPUT;
	
	// Variables.
	
	// Constructors.
	
	public OutputPort (int portID, int moduleID) {
		super(portID, moduleID);
	}
	
	public OutputPort (int portID, int moduleID, Pipe pipe) {
		super(portID, moduleID, pipe);
	}

	public void writeToCharPipe (char[] data, int offset, int length) throws PipeTypeNotSupportedException, IOException {
		
		// Catch non-compatible pipes error.
		if (!this.getPipeType().equals(PipeType.CHAR)) {
			throw new PipeTypeNotSupportedException ("Pipe of type \""
					+ this.getPipeType()
					+ "\" is not supported by port with ID \""
					+ this.getPortID()
					+ "\".");
		}
		
		// Write the data[] via the CharPipe.
		((CharPipe) this.getPipe()).write(data, offset, length);
		
		
	}
	
	public void writeToCharPipe (String data) throws PipeTypeNotSupportedException, IOException {
		
		// Catch non-compatible pipes error.
		if (!this.getPipeType().equals(PipeType.CHAR)) {
			throw new PipeTypeNotSupportedException ("Pipe of type \""
					+ this.getPipeType()
					+ "\" is not supported by port with ID \""
					+ this.getPortID()
					+ "\".");
		}
		
		// Write the String data via the CharPipe.
		((CharPipe) this.getPipe()).write(data);
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
