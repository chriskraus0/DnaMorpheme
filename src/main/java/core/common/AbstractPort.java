package core.common;

// Imports.

// Project specific exceptions.
import core.exceptions.NotFoundException;
import core.exceptions.OccupiedException;

// Imports.

// Java utility imports.

// Project specific imports.
import core.exceptions.PipeTypeNotSupportedException;
import core.exceptions.OccupiedException;

public abstract class AbstractPort implements Port {
	
	// Variables.
	
	private int moduleID;
	private int portID;
	
	// Variable holding the status of the port.
	private PortState portState;
	
	// Variable holding the supported pipe.
	private Pipe pipe;
	
	// End variables.
	
	// Constructors.
	
	public AbstractPort (int portID, int moduleID) {
		super();
		this.portID = portID;
		this.moduleID = moduleID;
		this.portState = PortState.READY;
	}
	
	public AbstractPort (int portID, int moduleID, Pipe pipe) {
		super();
		this.portID = portID;
		this.moduleID = moduleID;
		this.pipe = pipe;
		this.portState = PortState.READY;
	}
	
	// Methods.

	// Getters.
	@Override
	public int getModuleID () {
		return this.moduleID;
	}
	
	@Override 
	public int getPortID() {
		return this.getPortID();
	}
	

	@Override 
	public PipeType getPipeType() {
		return this.pipe.getPipeType();
	}
	
	// End getters.
	
	// Setters.
	
	public void setPipe (Pipe pipe) {
		this.pipe = pipe;
	}
	
	// End setters.
	
	@Override
	public boolean supportsPipe(Pipe pipe) throws PipeTypeNotSupportedException {
		// TODO: Throw appropriate exception?		
		if (pipe.getPipeType().equals(this.pipe.getPipeType())) {
			return true;
		} else {
			throw new PipeTypeNotSupportedException ("Error: The pipe type \"" + pipe.getPipeType() 
			+ "\" is not supported by port with the portID \""
			+ this.getPortID() 
			+ " of the module with the moduleID \""
			+ this.getModuleID());
		}
		
	}
		
	@Override
	public void connectPorts(Pipe pipe, Port connectingPort) throws
		PipeTypeNotSupportedException,  OccupiedException {
		
		// Check whether connectingPort is occupied.
		if (connectingPort.getPortState().equals(PortState.OCCUPIED)) {
			throw new OccupiedException("Error: Port with portID \"" 
					+ connectingPort.getPortID() + "\" is occupied");
		} 
		
		// TODO: Rewrite OccupiedException that its constructor takes an port as 
				// argument and automatically prints the above mentioned message. Also include 
				// this.port as cause for an exception!
		
		// Check whether connectingPort is already connected
		else if (connectingPort.getPortState().equals(PortState.CONNECTED)) {
			throw new OccupiedException("Error: Port with portID \""
					+ connectingPort.getPortID() + "\" is already connected to another port");
		} 
		// TODO: Write AlreadyConnectedException which has a constructor that takes an port as 
				// argument and automatically prints the above mentioned message. Also include 
				// this.port as cause for an exception!
		
		else if (connectingPort.getPortState().equals(PortState.READY)) {
			
			// Check whether both pipes are compatible.
			if (!pipe.getPipeType().equals(this.pipe.getPipeType())) {
				throw new PipeTypeNotSupportedException ("Error: Pipes are not compatible."
						+ "Pipe of the port with the ID"
						+ connectingPort.getPortID()
						+ "\" is of type \""
						+ pipe.getPipeType().toString()
						+ "while pipe of the port with the ID \""
						+ this.getPortID()
						+ "\" is of the type \""
						+ this.pipe.getPipeType().toString());
			}
			// TODO: Write PipeNotSupportedException which has a constructor that takes a port and
			// a pipe as argument and automatically prints the above mentioned message.
			else {
				// TODO: Continue here.
			}
			
		} 
		
	}
	
	@Override
	public void removePipe(Pipe pipe) throws NotFoundException {
		// TODO include remove sub here.
	}
	
	@Override
	public void reset() {
		// TODO include reset sub here.
	}
	
	@Override
	public PortState getPortState() {
		// TODO include is connected sub here.
		return this.portState;
	}
	
		
	// End methods.
	
}
