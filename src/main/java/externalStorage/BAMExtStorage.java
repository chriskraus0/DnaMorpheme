package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

public class BAMExtStorage extends ExtStorage {

	// Constants.
	
	private final ExtStorageType EXT_TYPE = ExtStorageType.BAM_EXT_STORAGE;
	
	// Variables.
	
	// The current state of the external file.
	private FileState fileState;
	
	// Variable holding the current location of the BAM file. 
	private File bamFile;
	
	// End variables.
	
	// Constructors.
	public BAMExtStorage (String extID, File file) {
		super(extID, file);
		
		// Initialize the file state.
		this.fileState = FileState.AVAILABLE;
	}

	
	
	// End constructors.
	
	// Methods.
	
	// Getters.
	
	/**
	 * Getter returns the BAM file as File.
	 * @return File bamFile
	 */
	public File getBAMfile() {
		return super.getFile();
	}
	
	/**
	 * Getter returns the absolute path name as String.
	 * @return String bamPath
	 */
	public String getBAMpath() {
		return super.getFile().getAbsolutePath();
	}
	
	/**
	 * Getter returns the BAM file name.
	 * @return String bamFileName
	 */
	public String getBAMfileName() {
		return super.getFile().getName();
	}
	
	// End getters.
	
	@Override
	public FileState checkFile() {
		if (super.getFile().exists()) 
			return FileState.AVAILABLE;
		else {
			this.fileState = FileState.NOT_FOUND;
			return this.fileState;
		}
	}

	@Override
	public ExtStorageType getStorageType() {
		return this.EXT_TYPE;
	}
}
