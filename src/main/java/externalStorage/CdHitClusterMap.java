package externalStorage;

// Imports.

// Java utility imports.
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * This class saves a HashMap holding all the Cd-Hit clusters. The keys of the 
 * Cd-Hit clusters are the cluster names.
 * @author Christopher Kraus
 *
 */

public class CdHitClusterMap {
	
	// Variables.
	
	private HashMap <String, CdHitCluster> cdHitClusterMap;
	
	// TreeMap which holds the percentage of entries per cluster.
	
	private TreeMap <String, Double> percentagePerCluster;
	
	// TreeMap which is the same as percentagePerCluster but sorted by the 
	// percentage in ascending order.
	private TreeMap <Double, String> ascendingClusters;
	
	// Total number of clusters.
	private int totalEntries;
	
	// Biggest cluster.
	private String biggestClName;
	
	private double biggestClPerc;
	
	private int biggestClSize;
	
	// Smallest cluster.
	private String smallestClName;
	
	private double smallestClPerc;
	
	private int smallestClSize;
	
	// Constructors.
	
	public CdHitClusterMap () {
		
		// Initialize the cdHitClusterMap with a new HashMap.
		this.cdHitClusterMap = new HashMap <String, CdHitCluster>();
	}
	
	// Methods.

	// Setters.
	
	public void setCdHitCluster(String name, CdHitCluster cCluster) {
		this.cdHitClusterMap.put(name, cCluster);
	}
	
	// Getters.
	
	public HashMap<String, CdHitCluster> getCdHitCluster() {
		return this.cdHitClusterMap;
	}
	
	public int getSmallestValue () {
		return this.smallestClSize;
	}
	
	public double getSmallestFreq () {
		return this.smallestClPerc;
	}
	
	public String getSmallestClusterName () {
		return this.smallestClName;
	}
	
	public int getGreatestValue () {
		return this.biggestClSize;
	}
	
	public double getGreatestFreq () {
		return this.biggestClPerc;
	}
	
	public String getGreatestClusterName () {
		return this.biggestClName;
	}
	
	// End Getters.
	
	/**
	 * Method iterates over all saved clusters and counts the entries.
	 * It later displays the relative frequency per cluster, the minimum
	 * and maximum.
	 */
	public void assessClusters() {
		
		// A TreeMap (or more a table here) in which the results will be saved.
		this.percentagePerCluster = new TreeMap <String, Double>();
		
		// Total number of entries in all clusters.
		this.totalEntries = 0;
		
		
		// Save the smallest entry.
		
		int smallest;
		
		// Save the biggest entry.
		
		int biggest;
		
		// Iterate over the keySet of the cluster map.
		Iterator <String> it = this.cdHitClusterMap.keySet().iterator();
		
		// Look at the first entry.
		
		String currCluster = it.next();
		
		int currEntriesNum = this.cdHitClusterMap.get(currCluster).getAllEntries().size();
		
		this.totalEntries += currEntriesNum;
		

		// Save the total amount per cluster as integer first.
		this.percentagePerCluster.put(currCluster, (double) currEntriesNum);
		
		// Initialize "smallest" and "biggest".
		smallest = currEntriesNum;
		biggest = currEntriesNum;
				
		// Start iteration.
		while (it.hasNext()) {
			currCluster = it.next();
			
			currEntriesNum = this.cdHitClusterMap.get(currCluster).getAllEntries().size();
			
			// Assess the lowest value.
			if (currEntriesNum < smallest)
				smallest = currEntriesNum;
			
			// Assess the greatest value.
			if (currEntriesNum > biggest)
				biggest = currEntriesNum;
			
			this.totalEntries += currEntriesNum;
			
			// Save the total amount per cluster as integer first.
			this.percentagePerCluster.put(currCluster, (double) currEntriesNum);
		}
		
		// Sort the percentPerCluster and save its result a new TreeMap which is sorted by the values.
		this.ascendingClusters = new TreeMap <Double, String>();
		
		// Iterate over the TreeMap percentagePerCluster and calculate percentages.
		Iterator <String>  pIt = percentagePerCluster.keySet().iterator();
		
		while (pIt.hasNext()) {
			String currentCluster = pIt.next();
			
			// Assign the percentages to each cluster.
			this.percentagePerCluster.put(currentCluster, 
					this.percentagePerCluster.get(currentCluster) / this.totalEntries);
			
			// Saved in ascending percentage.
			this.ascendingClusters.put(this.percentagePerCluster.get(currentCluster), currentCluster);
		}
		
		// Save values for the smallest cluster(s).
		this.smallestClName = this.ascendingClusters.firstEntry().getValue();
		
		this.smallestClSize = smallest;
		
		this.smallestClPerc = this.ascendingClusters.firstKey();
		
		// Save values for the biggest cluster(s).
		this.biggestClName = this.ascendingClusters.lastEntry().getValue();
		
		this.biggestClSize = biggest;
		
		this.biggestClPerc = this.ascendingClusters.lastKey();
		
	}
}
