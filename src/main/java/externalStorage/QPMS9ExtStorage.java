package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

public class QPMS9ExtStorage extends ExtStorage {
	
	// Constants.
	private final ExtStorageType EXT_TYPE = ExtStorageType.QPMS9_EXT_STORAGE;

	public QPMS9ExtStorage(String extID, File file) {
		super(extID, file);
		// TODO Auto-generated constructor stub
	}

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
