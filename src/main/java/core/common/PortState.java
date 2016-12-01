package core.common;

public enum PortState {
	// Ready to use. Port and pipe are created and are not connected, yet.
	READY, 
	
	// Port is connected to another Port via a Pipe and occupied with data transfer.
	OCCUPIED, 
	
	// Port is connected to another Port.
	CONNECTED
}
