package core;

// Imports.

// Java utility imports.
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

// Project-specific imports.
import externalStorage.ExtStorage;
import externalStorage.ExtStorageFactory;
import externalStorage.ExtStorageType;

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
	 * @param ExtStorageType exsType
	 */
	public void requestStorage(ExtStorageType exsType) {
		
		// Parse new extID.
		String newExtID = exsType.toString() + ":" + this.extStorageCounter;
		
		// Increment the extStorageCounter.
		this.extStorageCounter ++;
		
		// Create the new external storage.
		ExtStorage newExtStorage = this.extStorageFactory.createNewStorage(exsType, newExtID);
		
		// Inform about the creation of a new external storage.
		this.logger.log(Level.INFO, "External Storage of type: " + exsType.toString() + " ID: " +  newExtID 
				+ " created.");
		
		// Push the newExtStorage and its newExtID into the extStorageMap.
		this.extStorageMap.put(newExtID, newExtStorage);
	}
}
