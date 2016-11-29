package core.common;

import core.exceptions.CommandFailedException;

// Imports.

// Project specific imports
import core.ModulePortLinker;
import core.JobController;

/**
 * Abstract class defining the common methods among all modules.
 * @author christopher
 *
 */

public abstract class Module implements ModuleInterface, Runnable {
	
	// Enumerations.
	
	private ModuleState mState;
	
	// End enumerations.
	
	// Constants.
	
	private final int MODULE_ID;
	private final int STORRAGE_ID;
	private final ModuleType MODULE_TYPE;
	
	private final int INPUT_PORT_ID;
	private final int OUTPUT_PORT_ID;
	
	// End Constants.
	
	// Variables.
	
	private String moduleConsumerNodeName;
	private String moduleProducerNodeName;
	
	// End Variables.
	
	// Constructors.
	
	public Module (int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID) {
		this.MODULE_ID = moduleID;
		this.STORRAGE_ID = storageID;
		this.MODULE_TYPE = mType;
		this.mState = ModuleState.STARTING;
		this.INPUT_PORT_ID = iPortID;
		this.OUTPUT_PORT_ID = oPortID;
	}
	
	// End Constructors.
	
	// Methods.
	
	// Setters.
	
	@Override
	public void setModuleState(ModuleState mState) {
		this.mState = mState;
	}
	
	public void setProducerModuleNodeName (String moduleNodeName) {
		this.moduleProducerNodeName = moduleNodeName;
	}
	
	public void setConsumerModuleNodeName (String moduleNodeName) {
		this.moduleConsumerNodeName = moduleNodeName;
	}
	// End setters.
	
	// Getters.
		
	public String getConsumerModuleNodeName () {
		return this.moduleConsumerNodeName;
	}
	

	public String getProducerModuleNodeName () {
		return this.moduleProducerNodeName;
	}
	
	public Port getInputPort () {
		return ModulePortLinker.getInputPort(this.INPUT_PORT_ID);
	}
	
	public Port getOutputPort () {
		return ModulePortLinker.getOutputPort(this.OUTPUT_PORT_ID);
	}
	
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
	
		
	/*@Override
	public abstract CommandState callCommand(String command, int storageID) throws CommandFailedException;*/
	// End methods.

}
