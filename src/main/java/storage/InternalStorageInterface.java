package storage;

import core.exceptions.ObserverNotRegisteredException;

public interface InternalStorageInterface {
	
	/**
	 * Getter which returns the storageID of a particular internal storage.
	 * @return int storageID
	 */
	public int getStorageID();
	
	/**
	 * Method to register internal storage with StorageObserver.
	 * @param StorageObserver observer
	 */
	public void registerStorageObserver(StorageObserver observer) throws ObserverNotRegisteredException;
	
	/**
	 * Method to register internal storage with StorageObserver.
	 */
	public void notifyStorageObserver(StorageType sType) throws ObserverNotRegisteredException;
	
	/**
	 * Method to register internal storage with StorageObserver.
	 */
	public void disconnectStorageObserver() throws ObserverNotRegisteredException;
	
	/**
	 * Destroy storage.
	 * @param int storageID
	 */
	public void destroyStorage();

}
