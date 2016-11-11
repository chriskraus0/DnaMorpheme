package core.common;

// Imports.

// Java I/O imports.

// Java utility imports.
import java.util.Map;

// Project specific imports.
import core.exceptions.NotSupportedException;
import core.exceptions.OccupiedException;
import core.exceptions.NotFoundException;

public interface Port {
	
	// Methods.
	
	// Getters.
	
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
	 * Returns the PortType of the specific port.
	 * @return PortType pType
	 */
	public PortType getPortType();
	
	/**
	 * Getter to get a map of all supported Pipes.
	 * @return Map<String, Class<? extends Pipe>> pipeMap
	 */
	public Map<String, Class<? extends Pipe>> getSupportedPipeClass();
	
	// End getters.
	
	// Setters.

	// End setters.
	
	/**
	 * Test to see whether pipeClass is suported by current Pipe.
	 * @param pipeClass
	 * @return boolean
	 */
	public boolean supportsPipeClass(Class<? extends Pipe> pipeClass);
	
	/**
	 * Add a pipe to connect to the first or the second port.
	 * @param pipe
	 * @param connectingPort
	 * @throws NotSupportedException
	 * @throws OccupiedException
	 */
	public void addPipe(Pipe pipe, Port connectingPort) throws
		NotSupportedException,  OccupiedException;
	
	/**
	 * Remove the pipe connecting two ports.
	 * @param pipe
	 * @throws NotFoundException
	 */
	public void removePipe(Pipe pipe) throws NotFoundException;
	
	/**
	 * Reset all pipes between two ports. 
	 */
	public void reset();
	
	/**
	 * Test to see whether a port is connected.
	 * @return boolean
	 */
	public boolean isConnected();
	// End methods.
}
