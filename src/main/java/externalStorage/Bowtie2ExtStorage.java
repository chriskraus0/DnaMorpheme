package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

public class Bowtie2ExtStorage extends ExtStorage {
	
	// Constants.
	private final ExtStorageType EXT_TYPE = ExtStorageType.BOWTIE2_EXT_STORAGE;
	
	// Variables.
	
	// The current state of the external file.
	private FileState fileState;
	
	// SamtoolsFile holding the current content of the SAM table with all entries.
	// This SAM file was created during the mapping process through Bowtie2.
	private SamtoolsFile samtoolsFile;
	
	// End variables.
	
	// Constructors.
	public Bowtie2ExtStorage(String extID, File file) {
		super(extID, file);
		
		// Initialize the file state.
		this.fileState = FileState.AVAILABLE;
	}
	
	// End constructors.

	// Methods.
	
	// Getters.
	
	/**
	 * Getter returns the SAM file which was created by Bowtie2.
	 * @return SamtoolsFile samtoolsFile
	 */
	public SamtoolsFile getSamFile() {
		return this.samtoolsFile;
	}
	
	// Overridden methods.
	
	@Override
	public FileState checkFile() {
		this.samtoolsFile = new SamtoolsFile ( super.getFile() );
		this.fileState = this.samtoolsFile.parseSAM();
		return this.fileState;
	}

	@Override
	public ExtStorageType getStorageType() {
		return this.EXT_TYPE;
	}

}
