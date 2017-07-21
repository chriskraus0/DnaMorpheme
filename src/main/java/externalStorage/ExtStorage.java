package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

public abstract class ExtStorage implements ExtStorageInterface {
	
	// Constants.
	
	private final String EXT_STORAGE_ID;
	
	// Variables.
		
	// The name of the file.
	private String fileName;
	
	// The absolute path leading to the file.
	private String filePath;
	
	// The java I/O File object holding the file reference.
	private File file;
	
	// End variables.
	
	
	// Constructors.
	
	public ExtStorage (String extID, File file) {
		this.EXT_STORAGE_ID = extID;

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
	public String getExternalStorageID () {
		return this.EXT_STORAGE_ID;
	}
		
	@Override
	public FileState deleteFile () {
		this.file.delete();
		return FileState.DELETED;
	}

}
