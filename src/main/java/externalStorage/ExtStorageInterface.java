package externalStorage;

// Imports.

// Project-specific imports.
import core.common.ModuleType;

/**
 * Interface which defines the public methods for all external storage classes. 
 * @author Christopher Kraus
 *
 */

public interface ExtStorageInterface {
	
	/**
	 * Notifies registered observer about current status of the external storage.
	 */
	public void notifyObserver(FileState fileState);
	
	/**
	 * Checks the status of the external file.
	 * @return FileState fileState
	 */
	public FileState checkFile();
	
	/**
	 * Deletes the file saved in the storage.
	 * @return
	 */
	public FileState deleteFile();
	
	/**
	 * Getter returns the module ID of the calling module.
	 * @return int moduleID
	 */
	public int getCallingModuleID();
	
	/**
	 * Getter returns the type of the module which called the storage.
	 * @return ModuleType moduleType
	 * @see core.common.ModuleType
	 */
	public ModuleType getModuleType();
	
}
