package storage;

// Imports.

// Java utility imports.
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

// Java I/O imports.
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

// Java exceptions.
import java.io.IOException;

// Project-specific imports.
import core.PhysicalConstants;

// Import project-specific exceptions.
//import core.exceptions.ObserverNotRegisteredException;
import storage.exceptions.TruncatedFastaHeadException;

/**
 * 
 * InternalStorage implementation to store fasta data.
 * @author Christopher Kraus
 *
 */

public class SequenceStorage {

	// Constants.
	private final StorageType STORAGE_TYPE;
	
	// Variables.
	private Map <String, FastaFile> fastaMap;
	
	// Logger.
	private Logger logger;
	
	// Constructors.
	
	public SequenceStorage () {
		
		// Define the type of storage.
		this.STORAGE_TYPE = StorageType.FASTASEQUENCE;
				
		// Create HashMap holding all saved fasta files in this storage.
		this.fastaMap = new HashMap <String, FastaFile> ();
		
		// Call Logger to get new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	public SequenceStorage (int storageID, StorageObserver sObserver) {
		// Support for the super constructor is currently @deprecated.
		// super(storageID);
		
		// Define the type of storage.
		this.STORAGE_TYPE = StorageType.FASTASEQUENCE;
		
		// Register Observer.
		// This support is currently @deprecated.
		// super.registerStorageObserver(sObserver);
		
		// Create HashMap holding all saved fasta files in this storage.
		this.fastaMap = new HashMap <String, FastaFile> ();
		
		// Call Logger to get new logger.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Methods.
		
	/**
	 * @deprecated
	 * Method to notify the storage observer.
	 */
	/*public void notifyObserver() {
		try {
			super.notifyStorageObserver(this.STORAGE_TYPE);
		} catch (ObserverNotRegisteredException oe) {
			this.logger.log(Level.SEVERE, oe.getMessage());
			oe.printStackTrace();
		}
	}*/
	
	/**
	 * @deprecated
	 * Method to disconnect the storage observer.
	 */
	/*public void disconnectObserver () {
		try {
			super.disconnectStorageObserver();
		} catch (ObserverNotRegisteredException oe) {
			this.logger.log(Level.SEVERE, oe.getMessage());
			oe.printStackTrace();
		}
	}*/
	
	/**
	 * Method to parse fasta data and store it.
	 * @param fasta
	 */
	public void parseSingleFasta (String fasta) {
		
		// Split fasta header from sequence and save as ArrayList.
		ArrayList<String> fastaArrayList = new ArrayList <String> ();
		
		for (String i : fasta.split(PhysicalConstants.getNewLine()))
			fastaArrayList.add(i);
		
		// Get the fasta header.
		String fastaHeader = fastaArrayList.get(0);
		
		// Remove header from fastaArrayList.
		fastaArrayList.remove(0);
				
		// Create new FastaFile object.
		FastaFile fastaFile = new FastaFile (fastaHeader, fastaArrayList);
		
		// Save the new FastaFile object in the fastaMap.
		this.fastaMap.put(fastaHeader, fastaFile);
	}
	
	/**
	 * Method to parse fasta data and store it.
	 * @param fasta
	 */
	public void parseMultipleFasta (String fasta) throws TruncatedFastaHeadException {
		
		// Save all headers and sequences in a HashMap.
		Map <String, ArrayList<String>> fastaMap = new HashMap <String, ArrayList<String>> ();
				
		// Iterate over all lines. Save the last header.
		
		String lastHeader = "";
		
		for (String i : fasta.split(PhysicalConstants.getNewLine())) {
			
			// Check for matching headers.
						
			// If the first character is a ">" then it is a header line.
			if ( '>' == i.charAt(0) ) {
				fastaMap.put(i, new ArrayList<String> ());
				lastHeader = i;
			} 
			
			// If there is an entry for the lastHeader and the current sequence
			// is not a header then add the sequence entry to the ArrayList of 
			// the current entry of the the HashMap fastaMap.
			else if ( !('>' == i.charAt(0)) && (!lastHeader.isEmpty()) ) {
				fastaMap.get(lastHeader).add(i);
			} 
			
			// If there is no entry in the HashMap fastaMap there must be an 
			// error. Probably the head of the fasta file was truncated. Throw
			// a new exception.
			else if (lastHeader.isEmpty()) {
				throw new TruncatedFastaHeadException(
						"Could not detect fasta header. "
						+ "Fasta header probably truncated.");
			}	
			
		}
					
		// Create new FastaFile objects and store them.
		
		Iterator <String> fastaIt = fastaMap.keySet().iterator();
		
		// Create new FastaFile objects for each fasta sequence and save the entries
		// in the HashMap "fastaMap".
		while (fastaIt.hasNext()) {
			String currKey = fastaIt.next();
			this.fastaMap.put(
					currKey, 
					new FastaFile (currKey, fastaMap.get(currKey))
					);
		}
	}
	
	/**
	 * This method stores the fasta sequences in the provided file.
	 * @param File file
	 * @throws IOException
	 */
	public void storeOnSystem(File file) throws IOException {
		
		// Prepare FileWriter and BufferedWriter.
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		// Iterate through fastaMap and write then to file.
		Iterator <String> fastIt = this.fastaMap.keySet().iterator();
		
		while (fastIt.hasNext()) {
			String currKey = fastIt.next();
			bw.write(this.fastaMap.get(currKey).getWholeFastaSeq());
		}
		
		// Close writers.
		bw.close();
		fw.close();
	}
	
	// Getters.
	/**
	 * Getter which returns and ArrayList of all fasta sequences.
	 * @return ArrayList<String> fastaSequences
	 */
	public ArrayList<String> getFastaSeqs() {
		
		// Save headers and sequences in an ArrayList.
		ArrayList<String> fastaSeqs = new ArrayList<String>(); 
		
		// Iterate over the keys of the HashMap fastaMap.
		Iterator <String> it = this.fastaMap.keySet().iterator();
		
		while (it.hasNext()) {
			fastaSeqs.add(this.fastaMap.get(it.next()).getWholeFastaSeq());
		}
		
		return fastaSeqs;
	}
	
	/**
	 * Getter which returns one specific fasta sequence.
	 * @param String header
	 * @return String fastaSequence
	 */
	public String getFastaSeq(String header) {
		return this.fastaMap.get(header).getWholeFastaSeq();
	}
	
	// End Getters.
	
	/* Currently @deprecated.
	@Override
	public void destroyStorage() {
		this.fastaMap = null;
	}*/
}
