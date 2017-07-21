package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;


public class ExtStorageFactory implements ExtStorageFactoryInterface {
	
	// Variables.
	
	// Constructors.
	public ExtStorageFactory () {
		
	}

	// Methods.
	
	// Overridden methods.
	@Override
	public CdHitExtStorage createCdHitStorage(String extID, File file) {
		return new CdHitExtStorage(extID, file);
	}

	@Override
	public QPMS9ExtStorage createQPMS9Storage(String extID, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bowtie2ExtStorage createBowtie2Storage(String extID, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SamtoolsExtStorage createSamtoolsStorage(String extID, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TomTomExtStorage createTomTomExtStorage(String extID, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtStorage createNewStorage(ExtStorageType exsType, String extID, File file) {
				
		ExtStorage extStorage;
		
		switch (exsType) {
			case CDHIT_EXT_STORAGE:
				extStorage = this.createCdHitStorage(extID, file);
				break;
			case QPMS9_EXT_STORAGE:
				extStorage = this.createQPMS9Storage(extID, file);
				break;
			case BOWTIE2_EXT_STORAGE:
				extStorage = this.createBowtie2Storage(extID, file);
				break;
			case SAMTOOLS_EXT_STORAGE:
				extStorage = this.createSamtoolsStorage(extID, file);
				break;
			case TOMTOM_EXT_STORAGE:
				extStorage = this.createSamtoolsStorage(extID, file);
				break;
			default:
				extStorage = null;
		}
		
		return extStorage;
	}	
	
	// End methods.
}
