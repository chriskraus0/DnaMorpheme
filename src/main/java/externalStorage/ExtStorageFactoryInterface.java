package externalStorage;

// Imports.

// Java I/O imports.
import java.io.File;

import modules.SamtoolsJobType;

/**
 * External Storage Interface defines the public methods for 
 * generation of new external storage.
 * @author Christopher Kraus
 *
 */

public interface ExtStorageFactoryInterface {
	
	/**
	 * Creation of a Cd-Hit specific external storage.
	 * @param String extID
	 * @param double identity
	 */
	public CdHitExtStorage createCdHitStorage(String extID, File file, double identity);
	
	/**
	 * Creation of a QPMS9 specific external storage.
	 * @param String extID
	 * @param double hammingDistance
	 */
	public QPMS9ExtStorage createQPMS9Storage(String extID, File file, double hammingDistance);
	
	/**
	 * Creation of a bowtie2 specific external storage.
	 * @param String extID
	 * @param double parameter
	 */
	public Bowtie2ExtStorage createBowtie2Storage(String extID, File file, double parameter);
	
	/**
	 * Creation of a Samtools specific external storage.
	 * @param String extID
	 * @param double hammingDistance
	 */
	public SamtoolsExtStorage createSamtoolsStorage(String extID, File file, double hammingDistance);
	
	/**
	 * Creation of a TomTom specific external storage.
	 * @param String extID
	 */
	public TomTomExtStorage createTomTomExtStorage(String extID, File file);
	
	/**
	 * This method creates a specific external storage.
	 * @param ExtStorageType exsType
	 * @param String extID
	 */
	public ExtStorage createNewStorage(ExtStorageType exsType, String extID, File file, double parameter);
	
	/**
	 * This method creates a specific BAM external storage.
	 * @param exsType
	 * @param extID
	 * @param file
	 * @param parameter
	 * @param samtoolsJobType
	 * @return ExtStorage
	 */
	public ExtStorage createNewStorage(ExtStorageType exsType, String extID, File file, double parameter, SamtoolsJobType samtoolsJobType);
	
	/**
	 * This method creates a specific external storage for BAM files.
	 * @param extID
	 * @param file
	 * @param hammingDistance
	 * @param samtoolsJobType
	 * @return BAMExtStorage 
	 */
	public BAMExtStorage createBAMStorage(String extID, File file, double hammingDistance, SamtoolsJobType samtoolsJobType);
}
