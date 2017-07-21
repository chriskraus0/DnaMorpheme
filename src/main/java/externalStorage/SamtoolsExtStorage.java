package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

public class SamtoolsExtStorage extends ExtStorage {
	
	// Constants.
	private final ExtStorageType EXT_TYPE = ExtStorageType.SAMTOOLS_EXT_STORAGE;
	
	// Constructors.
	public SamtoolsExtStorage(String extID, File file) {
		super(extID, file);
		// TODO Auto-generated constructor stub
	}

	// Methods.
	
	// Overridden methods.
	
	@Override
	public FileState checkFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtStorageType getStorageType() {
		return this.EXT_TYPE;
	}

}
