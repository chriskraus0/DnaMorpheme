package core.common;

/**
 * This interface indicates the required methods for the ModuleObserver.
 * @author christopher
 *
 */
public interface ModuleObserverInterface {

	/**
	 * Updates the status of all views for the model (ModuleObserver).
	 * @see Model-View-Controller (MVC) design pattern.
	 */
	public void update (int producerID, ModuleState producerState, int consumerID, ModuleState consumerState);
	
}
