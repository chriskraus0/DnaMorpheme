package storage;

// Imports.

// Java utiltiy imports.
import java.util.Map;
import java.util.HashMap;

// Project-specific imports.

/**
 * Abstract factory creating new individual storage compartments 
 * for different modules.
 * @author christopher
 *
 */
public class InternalStorageFactory implements InternalStorageFactoryInterface {

	// Constants.
	
	// Variables.
	
	// HashMap holding all the storageIDs as keys and the references to InternalStorage objects.
	private static Map <Integer, InternalStorage> storageMap;
	
	// HashMap holding all the storageIDs as keys and the references for threads which deal with
	// each storage separately.
	private static Map <Integer, Thread> storageThreadMap;
	
	// Keep track of newly occurring storages to define unique storageIDs.
	private static int storageCount;
	
	// Constructors.
	public InternalStorageFactory() {
		storageMap = new HashMap <Integer, InternalStorage>();
		storageThreadMap = new HashMap <Integer, Thread>();
		storageCount = 0;
	}

	@Override
	public int createNewBowtie2Storage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createNewCdhitStorage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createNewQpms9Storage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createNewSamtoolsStorage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createNewTomtomStorage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createNucfreqStorage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createNewPMSconsensusStorage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createNewSequenceLogoStorage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// Methods.
}
