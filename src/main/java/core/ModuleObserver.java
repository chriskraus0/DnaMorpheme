package core;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// Project specific imports.
import core.common.ModuleState;
import core.common.ModuleObserverInterface;
import externalStorage.FileState;

/**
 * This class updates the STATE of each module.
 * This class also acts as an "Model" (Observer) in the MVC (Model-View-Controller) design pattern.
 * @see Model-View-Controller pattern.
 * @author christopher
 *
 */
public class ModuleObserver implements ModuleObserverInterface {
	// TODO: Incorporate this class for the Producer-Consumer pattern or the Model-View-Controller pattern.
	// (here known as ModuleObserver.java, ModuleNode.java and JobState.java)
	
	// Variables.
	
	private int producerID;
	private int consumerID;
	private ModuleState producerState;
	private ModuleState consumerState;
	
	// Logger.
	private Logger logger;
		
	// Constructors.
	public ModuleObserver () {
		
		// Call Logger factory to create new logger.
		this.logger = Logger.getLogger(this.getClass().getName());

	}
	
	// Methods.
	
	// Getters.
	
	// End getters.
	
	/**
	 * Getter returns the ID of the currently updated producer.
	 * @return int producerID
	 */
	public int getProducerID() {
		return this.producerID;
	}
	
	/**
	 * Getter returns the ID of the currently updated consumer.
	 * @return int consumerID
	 */
	public int getConsumerID() {
		return this.consumerID;
	}
	
	/**
	 * Getter returns the state of the currently updated producer.
	 * @return ModuleState producerState
	 * @see core.common.ModuleState
	 */
	public ModuleState getProducerState() {
		return this.producerState;
	}
	
	/**
	 Getter returns the state of the currently updated consumer.
	 * @return ModuleState consumerState
	 * @see core.common.ModuleState
	 */
	public ModuleState getConsumerState() {
		return this.consumerState;
	}
	
	// Setters.
	
	// End setters.
	
	// Overridden methods.
	
	@Override
	public void update(int producerID, ModuleState producerState, int consumerID, ModuleState consumerState) {
		this.producerID = producerID;
		this.consumerID = consumerID;
		this.producerState = producerState;
		this.consumerState = consumerState;
		
		this.logger.log(Level.INFO, "ModuleNodes with IDs: \"" + producerID + "\" \"" + consumerID + "\"");
		this.logger.log(Level.INFO, "Producer with ID \"" + producerID + "\" has state: \"" + producerState.toString() + "\"");
		this.logger.log(Level.INFO, "Consumer with ID \"" + consumerID + "\" has state: \"" + consumerState.toString() + "\"");
		
	}
	
	@Override
	public void update(int moduleID, FileState fileState) {
		this.logger.log(Level.INFO, "External Storage of module with ID: \""
				+ moduleID + "\" has the state \""
				+ fileState + "\".");
	}
	
	//TODO: Inform ExtStorageController about newly created external file(s).
	
}
