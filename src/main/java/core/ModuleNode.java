package core;

// Imports.

// Java utility imports.

// Project specific imports.
import core.common.ModuleState;
import core.common.PipeType;
import core.common.ModuleNodeInterface;
import core.common.ModuleObserverInterface;

// Project specific exceptions.
import core.exceptions.NeitherConsumerProducerException;
import core.exceptions.OccupiedException;
import core.exceptions.PipeTypeNotSupportedException;
import externalStorage.ExtStorageType;

/**
 * This class creates nodes for each interacting thread pair. These nodes serve as queues 
 * which are used for the "Consumer-Producer" pattern/problem underlying multi-threading.
 * This class acts also a "View" in the MVC (Model-View-Controller) design pattern.
 * @see Model-View-Controller pattern.
 * @author Christopher Kraus
 *
 */
public class ModuleNode implements ModuleNodeInterface {

	// Variables.

	// ModuleObserver.
	private ModuleObserverInterface moduleObserver;
	
	// Name of this particular ModuleNode.
	private String moduleNodeName;
	
	// Integer holding the module ID of the first module.
	private int producerID;
	
	// Variable holding the ModuleState of the first module.
	private ModuleState producerState;
	
	// Integer holding the module ID of the second module.
	private int consumerID;
	
	// Variable holding the ModuleState of the second module.
	private ModuleState consumerState;
	
	// Constructors.
	public ModuleNode (int moduleID, ModuleObserverInterface moduleObserver) {
		this.moduleObserver = (ModuleObserver) moduleObserver;
		this.producerID = moduleID;
		this.producerState = ModuleBuilder.getModule(moduleID).getModuleState();
		this.consumerID = -1;
		this.consumerState = ModuleState.UNDEFINED;
		this.moduleNodeName = Integer.toString(this.producerID) + "." + Integer.toString(this.consumerID);
	}
	
	public ModuleNode (int producer, int consumer, ModuleObserver moduleObserver) {
		this.moduleObserver = moduleObserver;
		this.producerID = producer;
		this.producerState = ModuleBuilder.getModule(producer).getModuleState();
		this.consumerID = consumer;
		this.consumerState = ModuleBuilder.getModule(consumer).getModuleState();
		this.moduleNodeName = Integer.toString(this.producerID) + "." + Integer.toString(this.consumerID);
	}

	// End constructors.
	
	// Methods.
		
	// Setters.
	
	// End setters.
	
	// Getters.
	/**
	 * Returns the name of this moduleNode. The name consists of the moduleID of the Producer,
	 *  a dot "." and the moduleID of the Consumer.
	 *  If the Consumer ID turns out to be "-1", no Consumer was set.
	 * @return String moduleNodeName
	 */
	public String getModuleNodeName () {
		return this.moduleNodeName;
	}
	
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
	
	public ModuleState getConsumerState() {
		return this.consumerState;
	}
	
	public int getProducerID() {
		return this.producerID;
	}
	
	public ModuleState getProducerState() {
		return this.producerState;
	}
	// End getters.
	
	// Overridden methods.
	
	@Override 
	public void connect() throws PipeTypeNotSupportedException, OccupiedException{
		
		// Create Pipe for Producer.
		ModuleBuilder.getModule(this.producerID).getOutputPort().createNewPipe(PipeType.CHAR);
	
		// Create Pipe for Consumer.
		ModuleBuilder.getModule(this.consumerID).getInputPort().createNewPipe(PipeType.CHAR);
		
		// Connect Producer and Consumer.
		ModuleBuilder.getModule(this.producerID).getOutputPort().connectPorts(
			ModuleBuilder.getModule(this.consumerID).getInputPort().getPipe(), 
			ModuleBuilder.getModule(this.consumerID).getInputPort());
		
	}
	
	@Override 
	public void registerModuleObserver(ModuleObserverInterface moduleObserver) {
		this.moduleObserver = (ModuleObserver) moduleObserver;
	}
	
	@Override 
	public void removeFromModuleObserver() {
		this.moduleObserver = null;
	}
	
	@Override 
	public void notifyModuleObserver() {
		
		this.producerState = ModuleBuilder.getModule(this.producerID).getModuleState();
		
		this.consumerState = ModuleBuilder.getModule(this.consumerID).getModuleState();	
			
		this.moduleObserver.update(this.producerID, this.producerState, this.consumerID, this.consumerState);
	}
	
	@Override 
	public void notifyModuleObserverOutput(String outFile, ExtStorageType exsType) {
		this.moduleObserver.updateOutFile(outFile, exsType);
	}
	
	// End methods.
}
