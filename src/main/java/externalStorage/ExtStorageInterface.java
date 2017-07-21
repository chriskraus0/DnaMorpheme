package externalStorage;

// Imports.

// Project-specific imports.
import core.common.ModuleType;
import externalStorage.ExtStorageType;

/**
 * Interface which defines the public methods for all external storage classes. 
 * @author Christopher Kraus
 *
 */

public interface ExtStorageInterface {
	
	/**
	 * Notifies registered observer about current status of the external storage.
	 *
	public void notifyObserver(FileState fileState);*/
	
	// TODO: The method above may be included in a future version.
	
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
	 * Getter returns the external storage ID.
	 * @return int moduleID
	 */
	public String getExternalStorageID ();
	
	/**
	 * Getter returns the type of the external storage.
	 * @return ExtStorageType exsType
	 * @see externalStorage.ExtStorageType
	 */
	public ExtStorageType getStorageType();
	
}
