package externalStorage;

import core.ModuleBuilder;

// Imports.

// Java I/O imports.
import java.io.File;

// Project-specific imports.
import core.ModuleObserver;
import core.common.ModuleType;

/**
 * This class saves the names an paths of Cd-Hit cluster files.
 * @author Christopher Kraus
 *
 */

public class CdHitExtStorage extends ExtStorage {

	// Constants.
	private final ModuleType mType;
	
	// Variables.
	
	// The current state of the external file.
	private FileState fileState;
	
	// CdHitClusterMap Object holding the current cluster.
	private CdHitClusterMap cdHitClusterMap;
	
	// End variables.
	
	
	// Constructors.
	public CdHitExtStorage (int moduleID, ModuleObserver mObserver, File file) {
		super(moduleID, mObserver, file);
		
		// Set the module type of the calling module.
		this.mType = ModuleBuilder.getModule(moduleID).getModuleType();
		
		
		// Initialize the file state.
		this.fileState = FileState.AVAILABLE;
		
	}
	
	// End constructors.
	
	// Methods.
	
	// Getters.
	
	// Setters.
	
	// End setters.
	
	/**
	 * Method notifies Observer over current state of the file.
	 */
	public void notifyObserver() {
		 super.notifyObserver(this.fileState);
	}

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
	public ModuleType getModuleType() {
		return this.getModuleType();
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
