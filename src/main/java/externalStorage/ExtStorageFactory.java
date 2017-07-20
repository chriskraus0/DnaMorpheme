package externalStorage;

// Imports.



public class ExtStorageFactory implements ExtStorageFactoryInterface {
	
	// Variables.
	
	// Constructors.
	public ExtStorageFactory () {
		
	}

	@Override
	public CdHitExtStorage createCdHitStorage(String extID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QPMS9ExtStorage createQPMS9Storage(String extID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bowtie2ExtStorage createBowtie2Storage(String extID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SamtoolsExtStorage createSamtoolsStorage(String extID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TomTomExtStorage createTomTomExtStorage(String extID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtStorage createNewStorage(ExtStorageType exsType, String extID) {
		// TODO Auto-generated method stub
		return null;
	}	
}
