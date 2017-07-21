package externalStorage;

import java.io.File;

import core.ModuleObserver;
import core.common.ModuleType;

public class TomTomExtStorage extends ExtStorage {

	// Constants.
	
	private final ExtStorageType EXT_TYPE = ExtStorageType.TOMTOM_EXT_STORAGE;
	
	public TomTomExtStorage(String extID, File file) {
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
