package core.common;

// Imports.

// Project specific imports.
import core.ModuleBuilder;

public class AbstractThreading implements ThreadingInterface{

	// Variables.
	
	private final int MODULE_ID;
	private final ModuleType MODULE_TYPE;
	
	// Constructors.
	public AbstractThreading (int mID, ModuleType mType) {
		this.MODULE_ID = mID;
		this.MODULE_TYPE = mType;
	}
	
	@Override
	public void run() {
		
		Thread newThread = new Thread (ModuleBuilder.getModule(this.MODULE_ID));
		newThread.start();
		// TODO Auto-generated method stub
		
	}

}
