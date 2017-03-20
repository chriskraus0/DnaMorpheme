package storage;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Iterator;

// Import project-specific exceptions.
import core.exceptions.ObserverNotRegisteredException;

public class LogoStorage extends InternalStorage {

	// Constants.
	private final StorageType STORAGE_TYPE;
	
	// Variables.
	private Map <String, SeqLogo> logoMap;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public LogoStorage(int storageID, StorageObserver sObserver) {
		super(storageID);
		
		// Define the type of storage.
		this.STORAGE_TYPE = StorageType.SEQLOGO;
		
		// Register Observer.
		super.registerStorageObserver(sObserver);
		
		// Create HashMap holding all saved sequence logos in this storage.
		this.logoMap = new HashMap <String, SeqLogo>();
		
		// Call Logger to get new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}

	// Methods.
	
	public void run () {
		// TODO: include synchronized method and new Thread Stack.
	}
	
	/**
	 * This method creates a new SeqLogo object and stores it in the private HashMap.
	 * @param String logoHeader
	 * @param double[][] entropyMatrix
	 */
	public void parseSeqLogo (String header, double[][] matrix) {
		this.logoMap.put(header, new SeqLogo(header, matrix));
	}
	
	/**
	 * Getter which returns a HashMap holding the header information and the matrices for each 
	 * storred sequence logo.
	 * @return HashMap<String, double[][]> logoMap
	 */
	HashMap<String, double[][]> getSeqLogos () {
		HashMap<String, double[][]> newLogoMap = new HashMap<String, double[][]> ();
		
		Iterator <String> it = this.logoMap.keySet().iterator();
		
		while (it.hasNext())
			newLogoMap.put(it.next(), this.logoMap.get(it.next()).getLogoMatrix());

		return newLogoMap;
	}
	
	/**
	 * Getter which returns the entropy matrix for a specific sequence logo.
	 * @param header
	 * @return
	 */
	double[][] getSeqLogo(String header) {
		return this.logoMap.get(header).getLogoMatrix();
	}
	
	/**
	 * Method to notify the storage observer.
	 */
	public void notifyObserver() {
		try {
			super.notifyStorageObserver(this.STORAGE_TYPE);
		} catch (ObserverNotRegisteredException oe) {
			this.logger.log(Level.SEVERE, oe.getMessage());
			oe.printStackTrace();
		}
	}
	
	/**
	 * Method to disconnect the storage observer.
	 */
	public void disconnectObserver () {
		try {
			super.disconnectStorageObserver();
		} catch (ObserverNotRegisteredException oe) {
			this.logger.log(Level.SEVERE, oe.getMessage());
			oe.printStackTrace();
		}
	}
	
	@Override
	public void destroyStorage() {
		this.logoMap = null;
	}

}
