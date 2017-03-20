package core;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Map;
import java.util.HashMap;

// Project-specific imports.
import storage.InternalStorageFactory;
import storage.StorageState;
import storage.StorageType;
import storage.StorageObserver;

public class StorageController {
	
	// Enumerations.
	StorageState sState;
	
	// Variables.
	
	// Map holding the storageID and the state of each storage.
	private static Map <Integer, StorageState> storageState;
	
	// Variable holding the instance of InternalStorageFactory.
	private InternalStorageFactory sFactory;
	
	// Logger.
	private Logger logger;
	
	// Storage Observer.
	private StorageObserver sObserver;
	
	// Constructors.
	
	public StorageController () {

		// Create new StorageObserver.
		this.sObserver = new StorageObserver();
		
		// Initialize the InternalStorageFactory.
		this.sFactory = new InternalStorageFactory (sObserver);
		
		// Initialize the storageState map.
		storageState = new HashMap <Integer, StorageState> ();
		
		// Call Logger to get a new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
		
	}
	
	// Methods.
	
	// Setters.
	
	// End setters.
	
	// Getters.
	
	/**
	 * Getter for the state of a specific storage with a specific storageID.
	 * @param int storageID
	 * @return StorageState
	 */
	public StorageState getInternalStorageID(int storageID) {
		return this.storageState.get(storageID);
	}
	
	// End getters.
	
	public void requestInternalStorage(StorageType sType) {
		
		// Select the specific storage.
		this.sFactory.requestNewStorage(sType);
	}
	
	
	
	// End methods.
	
	

}
