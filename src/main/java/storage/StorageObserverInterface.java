package storage;

/**
 * Interface for the StorageObserver. The method update() is used to notify
 * the program about current state of the different internal storage objects.
 * @author Christopher Kraus
 *
 */

public interface StorageObserverInterface {
	
	/**
	 * Notifies the whole project about the state of the internal storages
	 * when their states change.
	 * @param storageID
	 */
	public void update(int storageID, StorageType sType, StorageState sState);

}
