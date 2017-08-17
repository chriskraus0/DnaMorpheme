package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

// Project-specific imports.
import modules.SamtoolsJobType;

public class BAMExtStorage extends ExtStorage {

	// Constants.
	
	private final ExtStorageType EXT_TYPE = ExtStorageType.BAM_EXT_STORAGE;
	
	// Variables.
	
	// The current state of the external file.
	private FileState fileState;
	
	// Variable holding the current location of the BAM file. 
	private File bamFile;
	
	// Provided hamming distance.
	private double hammingDistance;
	
	// Provided samtools specific job type.
	private SamtoolsJobType samtoolsJobType;
	
	// End variables.
	
	// Constructors.
	public BAMExtStorage (String extID, File file, double hamDistance, SamtoolsJobType samtoolsJobType) {
		super(extID, file);
		
		// Initialize the file state.
		this.fileState = FileState.AVAILABLE;
		
		// Initialize the hamming distance.
		this.hammingDistance = hamDistance;
		
		// Initialize the samtools specific job type.
		this.samtoolsJobType = samtoolsJobType;
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
	
	/**
	 * Getter returns the provided value for the hamming distance.
	 * @return double hamming distance
	 */
	public double getHammingDistance() {
		return this.hammingDistance;
	}
	
	/**
	 * Getter returns the job-type which was completed by Samtools.
	 * @return SamtoolsJobType samtoolsJobType
	 */
	public SamtoolsJobType getSamtoolsJobType() {
		return this.samtoolsJobType;
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
