package core.common;

// Imports.

// Project specific imports
import core.ModulePortLinker;
import core.ModuleNode;

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
	
	private ModuleNode moduleNode;
	
	// End Variables.
	
	// Constructors.
	
	public Module (int moduleID, int storageID, ModuleType mType, int iPortID, int oPortID) {
		this.MODULE_ID = moduleID;
		this.STORRAGE_ID = storageID;
		this.MODULE_TYPE = mType;
		this.INPUT_PORT_ID = iPortID;
		this.OUTPUT_PORT_ID = oPortID;

		// Signal that this module is set up and ready for work. 
		this.mState = ModuleState.READY;
	}
	
	// End Constructors.
	
	// Methods.
	
	// Setters.
	
	/**
	 * Sets a ModuleNode for the concrete inherited module of the type ModuleType. 
	 * @param ModuleNode mNode
	 */
	public void setSuperModuleNode (ModuleNode mNode) {
		this.moduleNode = mNode;
	}
	
	@Override
	public void setModuleState(ModuleState mState) {
		this.mState = mState;
	}
	
	/**
	 * Sets the producer (sending) specific ModuleNodeName.
	 * The moduleNodeName is a String composed of the moduleIDs of the sending
	 * (Producer) and receiving (Consumer) module.
	 * @param String moduleNodeName
	 */
	public void setProducerModuleNodeName (String moduleNodeName) {
		this.moduleProducerNodeName = moduleNodeName;
	}
	
	/**
	 * Sets the consumer (receiving) specific ModuleNodeName.
	 * The moduleNodeName is a String composed of the moduleIDs of the sending
	 * (Producer) and receiving (Consumer) module.
	 * @param String moduleNodeName
	 */
	public void setConsumerModuleNodeName (String moduleNodeName) {
		this.moduleConsumerNodeName = moduleNodeName;
	}
	// End setters.
	

	// Getters.
	
	/**
	 * Getter for the shared ModuleNode.
	 * @return ModuleNode
	 */
	public ModuleNode getSuperModuleNode () {
		return this.moduleNode;
	}
	
	/**
	 * Getter for the consumer ModuleNodeName.
	 * @return String ModuleNodeName
	 */
	public String getConsumerModuleNodeName () {
		return this.moduleConsumerNodeName;
	}
	
	/**
	 * Getter for the producer ModuleNodeName.
	 * @return String ModuleNodeName
	 */
	public String getProducerModuleNodeName () {
		return this.moduleProducerNodeName;
	}
	
	/**
	 * Getter for the InputPort.
	 * @return Port InputPort
	 */
	public Port getInputPort () {
		return ModulePortLinker.getInputPort(this.INPUT_PORT_ID);
	}
	

	/**
	 * Getter for the OutPutPort.
	 * @return Port OutputPort
	 */
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
	
	// End methods.

}
