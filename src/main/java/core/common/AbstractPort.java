package core.common;

// Imports.

// Java exceptions.
import java.io.IOException;

//Project specific imports.
import core.CharPipe;
import core.BytePipe;

//Project specific exceptions.
import core.exceptions.OccupiedException;
import core.exceptions.PipeTypeNotSupportedException;


public abstract class AbstractPort implements Port {
	
	// Constants.
	
	private final int MODULE_ID;
	private final int PORT_ID;
	
	// Variables.
	
	// Variable holding the status of the port.
	private PortState portState;
	
	// Variable holding the supported pipe.
	private Pipe pipe;
	
	// End variables.
	
	// Constructors.
	
	public AbstractPort (int portID, int moduleID) {
		super();
		this.PORT_ID = portID;
		this.MODULE_ID = moduleID;
		this.portState = PortState.READY;
	}
	
	public AbstractPort (int portID, int moduleID, Pipe pipe) {
		super();
		this.PORT_ID = portID;
		this.MODULE_ID = moduleID;
		this.pipe = pipe;
		this.portState = PortState.READY;
	}
	
	// Methods.
	
	// Setters.
	
	@Override 
	public void setPortState (PortState pState) {
		this.portState = pState;
	}
	
	@Override
	public void setPipe(Pipe pipe) {
		this.pipe = pipe;
	}

	// Getters.
	
	@Override
	public Pipe getPipe() {
		return this.pipe;
	}
	
	@Override
	public int getModuleID () {
		return this.MODULE_ID;
	}
	
	@Override 
	public int getPortID() {
		return this.PORT_ID;
	}
	

	@Override 
	public PipeType getPipeType() {
		return this.pipe.getPipeType();
	}
	
	// End getters.
		
	@Override
	public boolean supportsPipe(Pipe pipe) throws PipeTypeNotSupportedException {	
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
				
		// Check whether connectingPort is already connected
		else if (connectingPort.getPortState().equals(PortState.CONNECTED)) {
			throw new OccupiedException("Error: Port with portID \""
					+ connectingPort.getPortID() + "\" is already connected to another port");
		} 
		
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
		
			else {
				try {
					// Reset pipe.
					pipe.pipeReset();
					
					// Connect pipe to this port.
					this.setPipe(pipe);
					
					// Change state of both ports.
					connectingPort.setPortState(PortState.CONNECTED);
					this.setPortState(PortState.CONNECTED);
					
				} catch (IOException ie) {
					System.err.println(ie.getCause());
					ie.printStackTrace();
				}
			}
			
		} 
		
	}
	
	@Override
	public void createNewPipe (PipeType pType) throws OccupiedException {
		
		// Get information of the PortType.
		PortType portType = this.getPortType();
		
		// Throw a new OccupiedExceptinon if either the port is "CONNECTED" to another port 
		// or "OCCUPIED" with data transfer (in both cases pipes are already established).
		if (this.getPortState().equals(PortState.CONNECTED)) {
			if (portType.equals(PortType.INPUT)) {
			throw new OccupiedException("The input port with the ID \""
					+ this.getPortID() + "\" is currently connected with an output port."
					);
			} else if (portType.equals(PortType.OUTPUT)) {
					throw new OccupiedException("The output port with the ID \""
							+ this.getPortID() + "\" is currently connected with an input port."
							);
			}
			
		} else if (this.getPortState().equals(PortState.OCCUPIED)) {
			throw new OccupiedException("The input port with the ID \""
					+ this.getPortID() + "\" is currently transfering data.");
		}
		
		// Create new Pipe.
		try {
			
			// Decide which type of pipe to create (CHAR or BYTE).
			if (pType.equals(PipeType.CHAR))
					this.pipe = new CharPipe();
			else if (pType.equals(PipeType.BYTE))
					this.pipe = new BytePipe();
			
		} catch (IOException ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
		}
		
	}
	
	@Override
	public PortState getPortState() {
		return this.portState;
	}
	
	
		
	// End methods.
	
}
