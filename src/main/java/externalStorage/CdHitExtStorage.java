package externalStorage;

import core.ModuleBuilder;

// Imports.

// Java I/O imports.
import java.io.File;

// Project-specific imports.
import core.ModuleObserver;
import core.common.ModuleType;

public class CdHitExtStorage extends ExtStorage {

	// Constants.
	
	private final ModuleType mType;
	
	// Variables.
	
	// The current state of the external file.
	private FileState fileState;
	
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
		// TODO: include content.
		
		return FileState.AVAILABLE;
	}
	
	// End methods.
}
