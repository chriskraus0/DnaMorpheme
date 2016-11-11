package core.common;

// Imports.

/**
 * Abstract class defining the common methods among all modules.
 * @author christopher
 *
 */

public abstract class Module implements ModuleInterface {
	// Enumerations.
	
	private ModuleState mState;
	
	// End enumerations.
	
	// Constants.
	
	private final int MODULE_ID;
	private final int STORRAGE_ID;
	private final ModuleType MODULE_TYPE;
	
	// End Constants.
	
	// Variables.
	// End Variables.
	
	// Constructors.
	
	public Module (int moduleID, int storageID, ModuleType mType) {
		super();
		this.MODULE_ID = moduleID;
		this.STORRAGE_ID = storageID;
		this.MODULE_TYPE = mType;
		this.mState = ModuleState.STARTING;
	}
	
	// End Constructors.
	
	// Methods.
	
	// Setters.
	
	@Override
	public void setModuleState(ModuleState mState) {
		this.mState = mState;
	}
	
	
	// End setters.
	
	// Getters.
	
	@Override
	public ModuleState getModuleState() {
		return this.mState;
	}
	
	@Override
	public int getModuleID() {
		return this.MODULE_ID;
	}
	
	@Override
	public ModuleType getModuleType() {
		return this.MODULE_TYPE;
	}
	
	@Override
	public int getStorageID() {
		return this.STORRAGE_ID;
	}
	// End getters.
	
	@Override
	public abstract CommandState callCommand(String command, int storageID) throws CommandFailedException;
	// End methods.

}
