package storage;

// Imports.

// Import project-specific exceptions.
import core.exceptions.ObserverNotRegisteredException;

/**
 * @deprecated
 * Due to the unnecessary overhead at this point this implementation 
 * is currently not required. All underlying storage modules will be
 * de-coupled.
 * 
 * 
 * Abstract class which provides the backbone for all storage modules
 * which will run on independent threads.
 * @author Christopher Kraus
 *
 */
public abstract class InternalStorage implements InternalStorageInterface {
	
	// Constants.
	private final int STORAGE_ID;
	
	// Variables.
	private StorageState sState;
	
	// Variable holding the StorageObserver.
	private StorageObserver sObserver;
	
	// Constructors.
	public InternalStorage (int storageID) {
		
		// Give storage its permanent storageID.
		this.STORAGE_ID = storageID;
		
		// Initialize the StorageState.
		this.sState = StorageState.AVAILABLE;
	}
	
	// Methods.
	
	// Setters.
	
	// End setters.
	
	// Getters.
	
	@Override
	public int getStorageID () {
		return this.STORAGE_ID;
	}
	
	@Override
	public void registerStorageObserver(StorageObserver observer) {
		this.sObserver = observer;
	}
	
	@Override
	public void notifyStorageObserver(StorageType sType) throws ObserverNotRegisteredException {
		if (this.sObserver == null) {
			throw new ObserverNotRegisteredException(
					"Storage with ID:\"" + this.STORAGE_ID 
					+ "\" is not registered to an observer.");
		} else {
			this.sObserver.update(this.STORAGE_ID, sType, this.sState);
		}
	}
	
	@Override
	public void disconnectStorageObserver() throws ObserverNotRegisteredException {
		if (this.sObserver == null) {
			throw new ObserverNotRegisteredException(
					"Storage with ID:\"" + this.STORAGE_ID 
					+ "\" is not registered to an observer.");
		} else {
			this.sObserver = null;
		}
	}
	
	// End getters.
	
	// End methods.
}
