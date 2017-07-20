package externalStorage;

public interface ExtStorageFactoryInterface {
	
	/**
	 * Creation of a Cd-Hit specific external storage.
	 * @param String extID
	 */
	public CdHitExtStorage createCdHitStorage(String extID);
	
	/**
	 * Creation of a QPMS9 specific external storage.
	 * @param String extID
	 */
	public QPMS9ExtStorage createQPMS9Storage(String extID);
	
	/**
	 * Creation of a bowtie2 specific external storage.
	 * @param String extID
	 */
	public Bowtie2ExtStorage createBowtie2Storage(String extID);
	
	/**
	 * Creation of a Samtools specific external storage.
	 * @param String extID
	 */
	public SamtoolsExtStorage createSamtoolsStorage(String extID);
	
	/**
	 * Creation of a TomTom specific external storage.
	 * @param String extID
	 */
	public TomTomExtStorage createTomTomExtStorage(String extID);
	
	/**
	 * This method creates a specific external storage.
	 * @param ExtStorageType exsType
	 * @param String extID
	 */
	public ExtStorage createNewStorage(ExtStorageType exsType, String extID);
}
