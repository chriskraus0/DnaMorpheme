package storage;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// Project-specific imports.

/**
 * This class updates the STATE of each internal storage.
 *  
 * @author Christopher Kraus
 *
 */
public class StorageObserver implements StorageObserverInterface {
	
	// Variables.
	
	// Logger.
	private Logger logger;
	
	StorageObserver () {
		
		// Call Logger to get a new instance.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Methods.
	@Override
	public void update (int storageID, StorageType sType, StorageState sState) {
		this.logger.log(Level.INFO, "StorageID: \"" + storageID + "\" Type: \"" + sType.toString() + "\" State: \""+ sState.toString() + "\"");
	}

}
