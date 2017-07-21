package externalStorage;

// Imports.

// Java utility imports.
import java.util.ArrayList;

/**
 * This class holds all information about a single Cd-Hit cluster.
 * @author Christopher Kraus
 *
 */
public class CdHitCluster {
	
	// Variables.
	
	private String name="";
	ArrayList <String> entries;
	
	// Constructors.
	public CdHitCluster() {
		
		// Initialize the ArrayList entries.
		this.entries = new ArrayList <String>();
	}

	public CdHitCluster(String name) {
		
		// Define the cluster name.
		this.name = name;
		
		// Initialize the ArrayList entries.
		this.entries = new ArrayList <String>();
	}
	
	// End constructors.
	
	// Methods.
	
	/**
	 * Method adds another entry to the existing CdHitCluster.
	 * @param entry
	 */
	public void addEntryToCdHitCluster (String entry) {
		this.entries.add(entry);
	}
	
	
	// Getters.
	
	/**
	 * Method returns all Cd-Hit cluster entries.
	 * @return ArrayList<String> entries
	 */
	public ArrayList <String> getAllEntries () {
		return this.entries;
	}
	
	/**
	 * Method returns the name of the Cd-Hit cluster.
	 * @return String name
	 */
	public String getClusterName() {
		return this.name;
	}
	
	/**
	 * Method returns true if cluster name and entries were set.
	 * @return boolean setNameAndEntries
	 */
	public boolean hasNameAndEntries() {
		if (!this.name.isEmpty() && !this.entries.isEmpty())
			return true;
		
		return false;
	}
}
