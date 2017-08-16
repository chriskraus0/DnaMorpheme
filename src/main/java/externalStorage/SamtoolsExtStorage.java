package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

public class SamtoolsExtStorage extends ExtStorage {
	
	// Constants.
	private final ExtStorageType EXT_TYPE = ExtStorageType.SAMTOOLS_EXT_STORAGE;
	
	// Variables.
	
	// The current state of the external file.
	private FileState fileState;
	
	// SamtoolsFile holding the current content of the SAM table with all entries.
	private SamtoolsFile samtoolsFile;
	
	// Variable holding the provided hammingDistance.
	private double hammingDistance;
	
	// End variables.
	
	// Constructors.
	public SamtoolsExtStorage(String extID, File file, double hammingDistance) {
		super(extID, file);
		
		// Initialize the file state.
		this.fileState = FileState.AVAILABLE;
		
		// Initialize the Hamming distance.
		this.hammingDistance = hammingDistance;
	}

	// End constructors.
	
	// Methods.
	
	// Getters.
	
	/**
	 * Getter returns the file which contains the fields of a SAM file.
	 * @return SamtoolsFile samtoolsFile
	 */
	public SamtoolsFile getSamtoolsfile() {
		return this.samtoolsFile;
	}
	
	/**
	 * Getter returns the provided Hamming distance.
	 * @return double hammingDistance.
	 */
	public double getHammingDistance() {
		return this.hammingDistance;
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
