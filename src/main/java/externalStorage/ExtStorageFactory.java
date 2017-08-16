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
	public CdHitExtStorage createCdHitStorage(String extID, File file, double identity) {
		return new CdHitExtStorage(extID, file, identity);
	}

	@Override
	public QPMS9ExtStorage createQPMS9Storage(String extID, File file, double hammingDistance) {
		return new QPMS9ExtStorage(extID, file, hammingDistance);
	}

	@Override
	public Bowtie2ExtStorage createBowtie2Storage(String extID, File file) {
		return new Bowtie2ExtStorage(extID, file);
	}

	@Override
	public SamtoolsExtStorage createSamtoolsStorage(String extID, File file, double hammingDistance) {
		return new SamtoolsExtStorage(extID, file, hammingDistance);
	}

	@Override
	public TomTomExtStorage createTomTomExtStorage(String extID, File file) {
		// TODO Auto-generated method stub
		// TomTom is not implemented in this version of the program!
		return null;
	}

	@Override
	public ExtStorage createNewStorage(ExtStorageType exsType, String extID, File file, double parameter) {
				
		ExtStorage extStorage;
		
		switch (exsType) {
			case CDHIT_EXT_STORAGE:
				extStorage = this.createCdHitStorage(extID, file, parameter);
				break;
			case QPMS9_EXT_STORAGE:
				extStorage = this.createQPMS9Storage(extID, file, parameter);
				break;
			case BOWTIE2_EXT_STORAGE:
				extStorage = this.createBowtie2Storage(extID, file);
				break;
			case SAMTOOLS_EXT_STORAGE:
				extStorage = this.createSamtoolsStorage(extID, file, parameter);
				break;
			case TOMTOM_EXT_STORAGE:
				extStorage = this.createSamtoolsStorage(extID, file, parameter);
				break;
			default:
				extStorage = null;
		}
		
		return extStorage;
	}	
	
	// End methods.
}
