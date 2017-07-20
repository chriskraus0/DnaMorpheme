package externalStorage;

import java.io.File;

import core.ModuleObserver;
import core.common.ModuleType;

public class TomTomExtStorage extends ExtStorage {

	public TomTomExtStorage(int moduleID, ModuleObserver mObserver, File file) {
		super(moduleID, mObserver, file);
		// TODO Auto-generated constructor stub
	}

	@Override
	public FileState checkFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModuleType getModuleType() {
		// TODO Auto-generated method stub
		return null;
	}

}
