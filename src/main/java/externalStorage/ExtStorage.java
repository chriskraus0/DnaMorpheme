package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

// Project-specific imports.
import core.ModuleObserver;

public abstract class ExtStorage implements ExtStorageInterface {
	
	// Constants.
	
	private final int CALLING_MODULE_ID;
	
	// Variables.
	
	// Variable holding the module observer.
	private ModuleObserver mObserver;
	
	// The name of the file.
	private String fileName;
	
	// The absolute path leading to the file.
	private String filePath;
	
	// The java I/O File object holding the file reference.
	private File file;
	
	// End variables.
	
	
	// Constructors.
	
	public ExtStorage (int moduleID, ModuleObserver mObserver, File file) {
		this.CALLING_MODULE_ID = moduleID;

		// Save the observer.
		this.mObserver = mObserver;

		this.file = file;
		
		this.fileName = file.getName();
		
		this.filePath = file.getAbsolutePath();
	}
	
	// End constructors.
	
	// Methods.
	
	// Getters.
	
	/**
	 * Getter returns the external file.
	 * @return File file
	 */
	public File getFile() {
		return this.file;
	}
	
	// End getters.
	
	// Override methods.
	@Override 
	public int getCallingModuleID () {
		return this.CALLING_MODULE_ID;
	}
	
	@Override
	public void notifyObserver(FileState fileState) {
		mObserver.update(this.CALLING_MODULE_ID, fileState);
	}
	
	@Override
	public FileState deleteFile () {
		this.file.delete();
		return FileState.DELETED;
	}

}
