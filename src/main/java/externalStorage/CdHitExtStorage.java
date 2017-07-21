package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

// Project-specific imports.

/**
 * This class saves the names an paths of Cd-Hit cluster files.
 * @author Christopher Kraus
 *
 */

public class CdHitExtStorage extends ExtStorage {
	
	// Constants.
	
	private final ExtStorageType EXT_TYPE = ExtStorageType.CDHIT_EXT_STORAGE;
	
	// Variables.
	
	// The current state of the external file.
	private FileState fileState;
	
	// CdHitClusterMap Object holding the current cluster.
	private CdHitClusterMap cdHitClusterMap;
	
	// End variables.
	
	
	// Constructors.
	public CdHitExtStorage (String extID, File file) {
		super(extID, file);
				
		// Initialize the file state.
		this.fileState = FileState.AVAILABLE;
		
	}
	
	// End constructors.
	
	// Methods.
	
	// Getters.
	
	/**
	 * Getter which returns the Map with all included clusters.
	 * @return CdHitClusterMap cdHitClusterMap
	 */
	public CdHitClusterMap getClusters() {
		return this.cdHitClusterMap;
	}
	
	// Setters.
	
	// End setters.
	
	/**
	 * Delete external file.
	 * @return FileState fileState
	 */
	public FileState deleteFile() {
		return super.deleteFile();
	}
	
	/**
	 * Method assesses the metrics of the saved clusters.
	 */
	public void assessClusters () {

		// Start assessing the saved clusters.
		// This step is necessary otherwise the boolean
		// if condition in areClustersTooSmall will throw
		// an error.
		this.cdHitClusterMap.assessClusters();
	}
	
	/**
	 * Method assesses the size of the saved clusters
	 * and decides whether there are to few clusters.
	 * @return boolean
	 */
	public boolean areClustersTooSmall() {
				
		// TODO: Here is a hard-coded threshold for cluster size!
		if (this.cdHitClusterMap.getGreatestFreq() > 0.5)
				return true;
		else 
			return false;
	}
	
	// Override methods.
	
	@Override
	public FileState checkFile() {
		FileState currState = parseCdHitClusterFile( super.getFile() );
		
		return currState;
	}
	
	
	@Override
	public ExtStorageType getStorageType() {
		return this.EXT_TYPE;
	}
	
	// Private methods.
	
	private FileState parseCdHitClusterFile(File file) {
		
		// Generate new Cd-Hit cluster parser for this cluster.
		CdHitClusterParser cdHitClusterParser = new CdHitClusterParser(file);
		
		// Save the parsed cluster as an appropriate object.
		this.cdHitClusterMap = cdHitClusterParser.parseCdHitCluster();
		
		return FileState.AVAILABLE;
	}
	
	// End methods.
}
