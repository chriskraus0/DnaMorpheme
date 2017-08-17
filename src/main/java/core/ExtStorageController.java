package core;

// Imports.

// Java I/O imports.
import java.io.File;

// Java utility imports.
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

// Project-specific imports.
import externalStorage.ExtStorage;
import externalStorage.ExtStorageFactory;
import externalStorage.ExtStorageType;
import modules.SamtoolsJobType;

public class ExtStorageController {

	// Variables.
	
	// Variable which holds the reference to the ExtStorageFactory instance.
	private ExtStorageFactory extStorageFactory;
	
	// Counter which holds the current number of created external storages.
	private int extStorageCounter;
	
	// Map which holds all entries of all created external storages.
	private HashMap <String, ExtStorage> extStorageMap;
	
	// Local logger.
	private Logger logger;
	
	// End variables.
	
	// Constructors.
	
	public ExtStorageController () {
		
		// Initialize the extStoargeCounter.
		this.extStorageCounter = 1;
		
		// Initialize the storage HashMap.
		this.extStorageMap = new HashMap <String, ExtStorage>();
		
		// Create new logger for this class.
		this.logger = Logger.getLogger(this.getClass().getName());
		
		// Create new ExtStorageFactory instance.
		this.extStorageFactory = new ExtStorageFactory();
	}
	
	// End Constructors.
	
	// Methods.
	
	// This method is unnecessary at the moment.
	// TODO: Integrate an observer.
	/*public void generateObserver () {
		// TODO: empty stub
	}*/
	
	/**
	 * Request a new external storage for a specific module.
	 * This method returns the newest ID external storage.
	 * @param String filePath
	 * @param ExtStorageType exsType
	 * @param double parameter
	 * @return String newExtID
	 */
	public String requestStorage(String filePath, ExtStorageType exsType, double parameter) {
		
		// Parse the file path properly.
		File file = new File(filePath);
		
		// Parse new extID.
		String newExtID = exsType.toString() + ":" + this.extStorageCounter;
		
		// Increment the extStorageCounter.
		this.extStorageCounter ++;
		
		// Create the new external storage.
		ExtStorage newExtStorage = this.extStorageFactory.createNewStorage(exsType, newExtID, file, parameter);
		
		// Check the file, its state and save its content.
		newExtStorage.checkFile();
		
		// Inform about the creation of a new external storage.
		this.logger.log(Level.INFO, "External Storage of type: " + exsType.toString() + " ID: " +  newExtID 
				+ " created.");
		
		// Push the newExtStorage and its newExtID into the extStorageMap.
		this.extStorageMap.put(newExtID, newExtStorage);
		
		// Return the newest addition to the storage.
		return newExtID;
	}
	
	public String requestStorage(String filePath, ExtStorageType exsType, double parameter, SamtoolsJobType samtoolsJobType) {
		
		// Parse the file path properly.
		File file = new File(filePath);
		
		// Parse new extID.
		String newExtID = exsType.toString() + ":" + this.extStorageCounter;
		
		// Increment the extStorageCounter.
		this.extStorageCounter ++;
		
		// Create the new external storage.
		ExtStorage newExtStorage = this.extStorageFactory.createNewStorage(exsType, newExtID, file, parameter, samtoolsJobType);
		
		// Check the file, its state and save its content.
		newExtStorage.checkFile();
		
		// Inform about the creation of a new external storage.
		this.logger.log(Level.INFO, "External Storage of type: " + exsType.toString() + " ID: " +  newExtID 
				+ " created.");
		
		// Push the newExtStorage and its newExtID into the extStorageMap.
		this.extStorageMap.put(newExtID, newExtStorage);
		
		// Return the newest addition to the storage.
		return newExtID;
	}
	
	/**
	 * Getter returns the external storage which is saved by the ID extID.
	 * @param String extID
	 * @return ExtStorage extStorage
	 */
	public ExtStorage getExtStorage(String extID) {
		return this.extStorageMap.get(extID);
	}
	
}
