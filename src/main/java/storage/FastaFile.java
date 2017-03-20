package storage;

// Imports.

// Java utility imports.
import java.util.ArrayList;

// Project-specific imports.
import core.PhysicalConstants;

/**
 * This class saves fasta header and fasta sequences. Fasta sequences are saved line-wise
 * in the form of an ArrayList.
 * @author Christopher Kraus
 *
 */

public class FastaFile {
	
	// Variables.
	private String fastaHeader;
	
	private ArrayList<String> sequences;
	
	// Constructors.
	public FastaFile (String header, ArrayList<String> sequences) {
		this.fastaHeader = header;
		this.sequences = sequences;
	}
	
	// Methods.
	
	// Setters.
	
	// Getters.
	
	/**
	 * Getter which returns the header line of a fasta entry.
	 * @return String fastaHeader
	 */
	public String getHeader() {
		return this.fastaHeader;
	}
	
	/**
	 * Getter which returns the sequence of a fasta entry.
	 * @return ArrayList<String> sequence.
	 */
	public ArrayList<String> getSequence() {
		return this.sequences;
	}
	
	
	/**
	 * Getter which returns the fasta data (header and sequence) as one string.
	 * @return String fasta
	 */
	public String getWholeFastaSeq() {
		
		// Concatenate the whole sequence to one string.
		
		String myFasta = this.fastaHeader + PhysicalConstants.getNewLine();
		
		for (String i : this.sequences)
			myFasta = myFasta + i + PhysicalConstants.getNewLine();
		
		return myFasta;
	}
}
