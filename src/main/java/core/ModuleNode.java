package core;

// Imports.

// Java utility imports.

// Project specific imports.
import core.common.ModuleState;

// Project specific exceptions.
import core.exceptions.NeitherConsumerProducerException;

/**
 * This class creates nodes for each interacting thread pair. These nodes serve as queues 
 * which are used for the "Consumer-Producer" pattern/problem underlying multi-threading.
 * @author christopher
 *
 */
public class ModuleNode {
	
	// Variables.
	
	// Integer holding the module ID of the first module.
	private int producerID;
	
	// Variable holding the ModuleState of the first module.
	private ModuleState producerState;
	
	// Integer holding the module ID of the second module.
	private int consumerID;
	
	// Variable holding the ModuleState of the second module.
	private ModuleState consumerState;
	
	// Constructors.
	public ModuleNode (int moduleID) {
		this.producerID = moduleID;
		this.producerState = ModuleBuilder.getModule(moduleID).getModuleState();
		this.consumerID = -1;
		this.producerState = ModuleState.UNDEFINED;
	}
	
	public ModuleNode (int module1ID, int module2ID) {
		this.producerID = module1ID;
		this.producerState = ModuleBuilder.getModule(module1ID).getModuleState();
		this.consumerID = module2ID;
		this.consumerState = ModuleBuilder.getModule(module2ID).getModuleState();
	}

	// End constructors.
	
	// Methods.
	
	/**
	 * This method updates the state of the producer-consumer pair for the observer.
	 * @throws NeitherConsumerProducerException
	 * @see core.exceptions.NeitherConsumerProducerException
	 */
	public void updateModuleStates() throws NeitherConsumerProducerException {
		
		if (this.consumerID == -1) 
			throw new NeitherConsumerProducerException("Cannot update thread-queue for modules:" 
					+ "Consumer not registered for producer with moduleID \""
					+ this.producerID + "\".");
		
		this.consumerState = ModuleBuilder.getModule(this.consumerID).getModuleState();	
		
		this.producerState = ModuleBuilder.getModule(this.producerID).getModuleState();
					
	}
	
	// Setters.
	
	// End setters.
	
	// Getters.
	/**
	 * This method returns the state of one components of the consumer-producer pair (depending on its ID).
	 * This method throws a NeitherConsumerProducerException.
	 * @param int moduleID
	 * @return ModuleState moduleState
	 * @throws NeitherConsumerProducerException
	 * @see core.exceptions.NeitherConsumerProducerException
	 */
	public ModuleState getModuleState(int moduleID) throws NeitherConsumerProducerException {
		
		// TODO: Create custom exception.
		
		if (this.producerID == moduleID) 
			return this.producerState;
		
		if (this.consumerID == moduleID) 
			return this.consumerState;
		
		if (this.producerID != moduleID && this.consumerID != moduleID) 
			throw new NeitherConsumerProducerException("Cannot retrieve node for module with ID:\""
				+ moduleID + "\". No such module registered for this thread-queue.");
		
		return ModuleState.UNDEFINED;
	}
	
	public int getConsumerID() {
		return this.consumerID;
	}
	
	public ModuleState getCosumerState() {
		return this.consumerState;
	}
	
	public int getProducerID() {
		return this.producerID;
	}
	
	public ModuleState getProducerState() {
		return this.producerState;
	}
	// End getters.
	
	
	// End methods.
}
