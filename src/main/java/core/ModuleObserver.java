package core;

// Imports.

// Java utility imports.

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
	// TODO: Incorporate this class for the Producer-Consumer pattern or the Model-View-Controller pattern
	// (here known as ModuleObserver.java, ModuleNode.java and JobState.java)
	
	// Variables.
		
	// Constructors.
	public ModuleObserver () {

	}
	
	// Methods.
	
	// Getters.
	
	// End getters.
	
	// Setters.
	
	// End setters.
	
	// Overridden methods.
	@Override
	public void update(int producerID, ModuleState producerState, int consumerID, ModuleState consumerState) {
		System.out.println("LOG: ModuleNodes with IDs: \"" + producerID + "\" \"" + consumerID + "\"");
		System.out.println("LOG: Producer with ID \"" + producerID + "\" has state: \"" + producerState.toString() + "\"");
		System.out.println("LOG: Consumer with ID \"" + consumerID + "\" has state: \"" + consumerState.toString() + "\"");
		
	}
	
}
