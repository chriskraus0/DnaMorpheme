package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

public class Bowtie2ExtStorage extends ExtStorage {
	
	// Constants.
	
	private final ExtStorageType EXT_TYPE = ExtStorageType.BOWTIE2_EXT_STORAGE;
	// Variables.
	
	// Constructors.
	public Bowtie2ExtStorage(String extID, File file) {
		super(extID, file);
		// TODO Auto-generated constructor stub
	}

	// Methods.
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
