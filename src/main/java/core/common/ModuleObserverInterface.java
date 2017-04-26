package core.common;

import externalStorage.FileState;

/**
 * This interface indicates the required methods for the ModuleObserver.
 * @author christopher
 *
 */
public interface ModuleObserverInterface {

	/**
	 * Updates the status of all views for the model (ModuleObserver).
	 * @param int producerID
	 * @param ModuleState producerState
	 * @param int consumerID
	 * @param ModuleState consumerState
	 * @see Model-View-Controller (MVC) design pattern.
	 */
	public void update (int producerID, ModuleState producerState, int consumerID, ModuleState consumerState);
	
	/**
	 * Updates the status of all views. In this case the external storages.
	 * @param moduleID
	 * @param fileState
	 */
	public void update(int moduleID, FileState fileState);
	
}
