package core;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// Project specific imports.
import core.common.ModuleState;
import core.common.ModuleObserverInterface;

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
	
	// Setters.
	
	// End setters.
	
	// Overridden methods.
	@Override
	public void update(int producerID, ModuleState producerState, int consumerID, ModuleState consumerState) {
		this.logger.log(Level.INFO, "ModuleNodes with IDs: \"" + producerID + "\" \"" + consumerID + "\"");
		this.logger.log(Level.INFO, "Producer with ID \"" + producerID + "\" has state: \"" + producerState.toString() + "\"");
		this.logger.log(Level.INFO, "Consumer with ID \"" + consumerID + "\" has state: \"" + consumerState.toString() + "\"");
		
	}
	
}
