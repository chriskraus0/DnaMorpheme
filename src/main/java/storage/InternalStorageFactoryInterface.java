package storage;

/**
 * Interface to define the different create methods for an abstract factory.
 * The abstract factory will provide specific storage for requesting modules.
 * @author christopher
 * 
 */
public interface InternalStorageFactoryInterface {

	/**
	 * Creates a new bowtie2 storage object with a constant ID.
	 * @return int storageID
	 */
	public int createNewBowtie2Storage();
	
	/**
	 * Creates a new cdhit storage object with a constant ID.
	 * @return int storageID
	 */
	public int createNewCdhitStorage();
	
	/**
	 * Creates a new qpms9 storage object with a constant ID.
	 * @return int storageID
	 */
	public int createNewQpms9Storage();
	
	/**
	 * Creates a new SAM table storage object with a constant ID.
	 * @return int storageID
	 */
	public int createNewSamTableStorage();
	
	/**
	 * Creates a new BAM table object with a constant ID.
	 * @return int storageID
	 */
	public int createNewBamTableStorage();
	
	/**
	 * Creates a new tomtom storage object with a constant ID.
	 * @return int storageID
	 */
	public int createNewTomtomStorage();
	
	/**
	 * Creates a new nucleotide frequency storage object with 
	 * a constant ID.
	 * @return int storageID
	 */
	public int createNucfreqStorage();
	
	/**
	 * Creates a new planted moftif search (PMS) consensus sequence 
	 * storage object with a constant ID.
	 * @return int storageID
	 */
	public int createNewPMSconsensusStorage();
	
	/**
	 * Creates a new sequence logo storage object with a constant ID.
	 * @return int storageID
	 */
	public int createNewSequenceLogoStorage();
	
	/**
	 * Creates a new fasta sequence storage object with a constant ID.
	 * @return int storageID
	 */
	public int createNewFastaSeqStorage ();
}
