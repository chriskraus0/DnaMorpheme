package core.common;

// Imports.

// Project-specific imports.
import externalStorage.FileState;
import externalStorage.ExtStorageType;

/**
 * This interface indicates the required methods for the ModuleObserver.
 * @author Christopher Kraus
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
	
	/**
	 * Updates the ExtStorageController with new file which should be read
	 * and saved. This method takes the path to the file and the type 
	 * of external storage required. 
	 * @param String outFile
	 * @param ExtStorageType exsType
	 */
	public void updateOutFile (String outFile, ExtStorageType exsType);
	
}
