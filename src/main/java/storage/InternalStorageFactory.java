package storage;

// Imports.

// Java utiltiy imports.
import java.util.Map;
import java.util.HashMap;

// Project-specific imports.

/**
 * @deprecated
 * Momentarily this class poses too much overhead to be integrated.
 * 
 * 
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
	@Deprecated
	private static Map <Integer, Thread> storageThreadMap;
	
	// Keep track of newly occurring storages to define unique storageIDs.
	private static int storageCount;
	
	// StorageObserver.
	private StorageObserver sObserver;
	
	// Constructors.
	public InternalStorageFactory(StorageObserver sObserver) {
		storageMap = new HashMap <Integer, InternalStorage>();
		storageThreadMap = new HashMap <Integer, Thread>();
		storageCount = 0;
		this.sObserver = sObserver;
	}

	@Override
	public int createNewBowtie2Storage() {
		storageCount ++;
		
		// TODO Auto-generated method stub
		return storageCount;
	}

	@Override
	public int createNewCdhitStorage() {
		storageCount ++;
		// TODO create CdHitClusterStorage and CdHitCluster classes. 
		return storageCount;
	}

	@Override
	public int createNewQpms9Storage() {
		storageCount ++;
		// TODO create QPMS9candidateStorage class. 
		return storageCount;
	}

	@Override
	public int createNewSamTableStorage() {
		storageCount ++;
		// TODO create SamTableStorage and SamTable classes. 
		return storageCount;
	}
	
	@Override
	public int createNewBamTableStorage() {
		storageCount ++;
		// TODO create BamTableStorage and BamTable classes. 
		return storageCount;
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
		storageCount ++;
		// TODO create ConsensusMotifStorage and ProbabilityMatrix classes. 
		return storageCount;
	}

	@Override
	public int createNewSequenceLogoStorage() {
		storageCount ++;
		//TODO: storageMap.put(storageCount, new SequenceStorage(storageCount, this.sObserver));
		return storageCount;
	}
	
	@Override
	public int createNewFastaSeqStorage () {
		storageCount ++;
		// TODO create SequenceStorage and FastaFile classes. 
		return storageCount;
	}
	
	public int requestNewStorage (StorageType sType) {
		
		int newStorageID;
		
		// Request specific storage.
		switch (sType) {
		case FASTASEQUENCE:
			newStorageID = this.createNewFastaSeqStorage();
			break;
		case SEQLOGO:
			newStorageID = this.createNewSequenceLogoStorage();
			break;
		case CDHITCLUSTER:
			newStorageID = this.createNewCdhitStorage();
			break;
		case SAMTABLE:
			newStorageID = this.createNewSamTableStorage();
			break;
		case BAMTABLE:
			newStorageID = this.createNewBamTableStorage();
			break;
		case QPMS9CANDIDATES:
			newStorageID = this.createNewQpms9Storage();
			break;
		case CONSENSUSMOTIFS:
			newStorageID = this.createNewPMSconsensusStorage();
			break;
		case VERIFIEDDATABASEMOTIFS:
			newStorageID = this.createNewFastaSeqStorage();
			break;
		case UNDEFINED:
			newStorageID = -1;
			break;
		default:
			newStorageID = -1;
			break;
		}
		
		return newStorageID;
		
	}

	
	// Methods.
}
