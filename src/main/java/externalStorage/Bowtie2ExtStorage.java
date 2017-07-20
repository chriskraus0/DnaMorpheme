package externalStorage;

import java.io.File;

import core.ModuleObserver;
import core.common.ModuleType;

public class Bowtie2ExtStorage extends ExtStorage {

	public Bowtie2ExtStorage(int moduleID, ModuleObserver mObserver, File file) {
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
