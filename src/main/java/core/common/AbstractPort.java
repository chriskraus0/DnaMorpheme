package core.common;

// Imports.

// Project specific imports.
import core.BytePipe;

public abstract class AbstractPort implements Port {
	
	// Variables.
	
	private int moduleID;
	private PortType portType;
	private int portID;
	
	// End variables.
	
	// Constructors.
	public AbstractPort (int portID, int moduleID, PortType pType) {
		super();
		this.portID = portID;
		this.moduleID = moduleID;
		this.portType = pType;
	}
	
	// Methods.
	
	@Override
	public int getModuleID () {
		return this.moduleID;
	}
	
	@Override 
	public int getPortID() {
		return this.getPortID();
	}
	
	@Override 
	public PortType getPortType() {
		return this.portType;
	}
	
	@Override
	public boolean supportsPipeClass (Class <? extends Pipe> pipeClass) {
		
		// TODO: Integrate check for supported pipes.
		if (pipeClass.equals(BytePipe.class.toString())) {
			return true;
		} else {
			return false;
		}
	}
	// Setters.
	
	// End setters.
	
	// Getters.
	
	// End getters.
	
	// End methods.
	
}
