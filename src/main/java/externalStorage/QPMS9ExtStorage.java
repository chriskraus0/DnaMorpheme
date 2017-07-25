package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

public class QPMS9ExtStorage extends ExtStorage {
	
	// Constants.
	private final ExtStorageType EXT_TYPE = ExtStorageType.QPMS9_EXT_STORAGE;

	// Variables.
	
	// The current state of the external file.
	private FileState fileState;
	
	// QPMS9file object to hold path and get information about the quorum planted motifs.
	private QPMS9File qPMS9File;
	
	// End variables.
	
	// Constructors.
	public QPMS9ExtStorage(String extID, File file) {
		super(extID, file);
		
		// Initialize the file state.
		this.fileState = FileState.AVAILABLE;
	}
	
	// End constructors.
	
	// Methods.

	@Override
	public FileState checkFile() {
		this.qPMS9File = new QPMS9File( super.getFile() );
		this.fileState = qPMS9File.getQPMSFileState();
		return this.fileState;
	}

	@Override
	public ExtStorageType getStorageType() {
		return this.EXT_TYPE;
	}
	
	// Getters.
	
	/**
	 * Getter returns the number of predicted quorum planted motifs (qPMs)
	 * @return int numberOfqPMs
	 */
	int getNumberOfQPMs() {
		return this.qPMS9File.getNumberOfQPMs();
	}
	
	/**
	 * Delete external File and destroy qPMSFile object.
	 * @return FileState fileState.
	 * @see externalStorage.FileState
	 */
	public FileState deleteFile() {
		this.qPMS9File = null;
		return super.deleteFile();
	}

}
