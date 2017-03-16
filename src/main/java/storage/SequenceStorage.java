package storage;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

// Import project-specific exceptions.
import core.exceptions.ObserverNotRegisteredException;

public class SequenceStorage extends InternalStorage {

	// Constants.
	private final StorageType STORAGE_TYPE;
	
	// Variables.
	private Map <String, FastaFile> fastaMap;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	public SequenceStorage (int storageID, StorageObserver sObserver) {
		super(storageID);
		
		// Define the type of storage.
		this.STORAGE_TYPE = StorageType.FASTASEQUENCE;
		
		// Register Observer.
		super.registerStorageObserver(sObserver);
		
		// Create HashMap holding all saved fasta files in this storage.
		this.fastaMap = new HashMap <String, FastaFile> ();
		
		// Call Logger to get new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	public void notifyObserver() {
		try {
			super.notifyStorageObserver(this.STORAGE_TYPE);
		} catch (ObserverNotRegisteredException oe) {
			this.logger.log(Level.SEVERE, oe.getMessage());
			oe.printStackTrace();
		}
	}
	
	public void disconnectObserver () {
		try {
			super.disconnectStorageObserver();
		} catch (ObserverNotRegisteredException oe) {
			this.logger.log(Level.SEVERE, oe.getMessage());
			oe.printStackTrace();
		}
	}
	
	public void parseFasta () {
		// TODO: Allow inter-module connection to parse in-comming fasta data.
	}
	
	public String getFastaSeqs() {
		// TODO: retrieve saved fasta data.
		return "";
	}
	
	
	@Override
	public void destroyStorage() {
		this.fastaMap = null;
	}
}
