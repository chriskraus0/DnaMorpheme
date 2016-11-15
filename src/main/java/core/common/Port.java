package core.common;

// Imports.

// Java I/O imports.

// Java utility imports.
import java.util.Map;

// Project specific imports.
import core.exceptions.PipeTypeNotSupportedException;
import core.exceptions.OccupiedException;
import core.exceptions.NotFoundException;

public interface Port {
	
	// Methods.
	
	// Setters.
	
	/**
	 * Set state of the port.
	 * @param pState
	 */
	public void setPortState (PortState pState);
	
	/**
	 * Set new pipe.
	 * @param Pipe pipe
	 */
	public void setPipe(Pipe pipe);
	// End setters.
	
	// Getters.
	
	/**
	 * Return the connected pipe.
	 * @return
	 */
	public Pipe getPipe ();
	
	/**
	 * Returns the moduleID of the associated module.
	 * @return int moduleID
	 */
	public int getModuleID();
	
	/**
	 * Returns the portID of the specific port.
	 * @return
	 */
	public int getPortID();
	
	/**
	 * Get the supported PipeType.
	 * @return PipeType pipeType
	 */
	public PipeType getPipeType();
	
	/**
	 * Test to see whether a port is connected.
	 * @return boolean
	 */
	public PortState getPortState();
	
	// End getters.
	
	// Setters.

	// End setters.
	
	/**
	 * Test whether a Pipe object is supported by the current Port.
	 * @param Pipe pipe
	 * @return boolean
	 * @throws PipeTypeNotSupportedException 
	 */
	public boolean supportsPipe(Pipe pipe) throws PipeTypeNotSupportedException;
	
	
	/**
	 * Set a pipe to connect to the first or the second port.
	 * @param pipe
	 * @param connectingPort
	 * @throws PipeTypeNotSupportedException
	 * @throws OccupiedException
	 */
	public void connectPorts(Pipe pipe, Port connectingPort) throws
		PipeTypeNotSupportedException,  OccupiedException;
	
	/**
	 * Remove the pipe connecting two ports.
	 * @throws NotFoundException
	 */
	public void removePipe() throws NotFoundException;
	
	/**
	 * Reset all pipes between two ports. 
	 */
	public void reset();

	
	// End methods.
}
