package externalStorage;

// Imports.

// Java utility imports.
import java.util.logging.Logger;
import java.util.logging.Level;

// Java I/O imports.
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Java exceptions.
import java.io.IOException;

/**
 * This class parses a Cd-Hit cluster file and stores its results
 * in a CdHitClusterMap.
 * @see externalStorage.CdHitClusterMap
 * @author Christopher Kraus
 *
 */
public class CdHitClusterParser {
	
	// Variables.
	
	private File file;
	private Logger logger;
	
	// Constructors.
	
	public CdHitClusterParser (File file) {
		this.file = file;
		
		// Request a new logger for this class.
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	// Methods.
	public CdHitClusterMap parseCdHitCluster() {
		CdHitClusterMap cdHitClusterMap = new CdHitClusterMap();
		
		try {
			
			// Define a pattern for a cluster name.
			Pattern nPat = Pattern.compile("\\A>(Cluster\\s+\\d+)");
			
			// Define a pattern for a cluster entry.
			Pattern ePat = Pattern.compile("\\A\\d+\\s+\\w+,\\s+(>.+)");
			
			FileReader fr = new FileReader(this.file);
			
			BufferedReader br = new BufferedReader (fr);
			
			// Save each line separately.
			String line = "";
			
			// Save the cluster name.
			String clusterName = "";
			
			// Save the cluster entry.
			String clusterEntry = "";
			
			// Save Cd-Hit cluster.
			CdHitCluster cdHitCluster = new CdHitCluster();
			
			// Read initial line.
			line = br.readLine();
			
			while (line != null) {
				
				if (nPat.matcher(line).matches()) {
					Matcher nMatch = nPat.matcher(line);
					
					// If in the last iteration the name and the entries of the cluster
					// were set then add it to the cdHitClusterMap.
					if (cdHitCluster.hasNameAndEntries())
						cdHitClusterMap.setCdHitCluster(
								cdHitCluster.getClusterName(), cdHitCluster);
					
					// Find the pattern.
					nMatch.find();
					// Save the group as name.
					clusterName = nMatch.group(1);
					
					cdHitCluster = new CdHitCluster(clusterName);
				} else if (ePat.matcher(line).matches()) {
					Matcher eMatch = ePat.matcher(line);
					
					// Find the matching pattern.
					eMatch.find();
					clusterEntry = eMatch.group(1);
					cdHitCluster.addEntryToCdHitCluster(clusterEntry);
				}
				line = br.readLine();
			}
			
			// Add the last cluster to the cdHitClusterMap.
			if (cdHitCluster.hasNameAndEntries())
				cdHitClusterMap.setCdHitCluster(
						cdHitCluster.getClusterName(), cdHitCluster);
			
			br.close();
			
		} catch (IOException ie) {
			this.logger.log(Level.SEVERE, ie.getMessage());
			ie.printStackTrace();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		
		return cdHitClusterMap;
	}

}
