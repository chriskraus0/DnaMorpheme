package core.common;

// Imports.

// Project specific exceptions.
import core.exceptions.OccupiedException;
import core.exceptions.PipeTypeNotSupportedException;
import externalStorage.ExtStorageType;
import modules.SamtoolsJobType;

/**
 * ModuleNode interface to integrate required methods for the observer design pattern.
 * @author christopher
 *
 */
public interface ModuleNodeInterface {
	
	/**
	 * Register the ModuleObserver to this ModuleNode.
	 */
	public void registerModuleObserver(ModuleObserverInterface moduleObserver);
	
	/**
	 * Remove the ModuleNode from the ModuleObserver.
	 */
	public void removeFromModuleObserver();
	
	/**
	 * Notify the ModuleObserver.
	 */
	public void notifyModuleObserver();
	
	/**
	 * Notify the ModuleObserver about output file.
	 * This method takes an String array for all paths to the 
	 * output files. As second parameter this method takes the 
	 * type of storage in which the created output file should
	 * be stored.
	 * @param String outFiles
	 * @param ExtStorage exsType
	 * @param double parameter
	 */
	public void notifyModuleObserverOutput(String outFiles, ExtStorageType exsType, double parameter);
	
	/**
	 * This notification of the ModuleObserver is reserved only for different
	 * Samtools jobs.
	 * @param String outFile
	 * @param ExtStorageType exsType
	 * @param double parameter
	 * @param SamtoolsJobType samtoolsJobType
	 */
	public void notifyModuleObserverOutput(String outFile, ExtStorageType exsType, double parameter, SamtoolsJobType samtoolsJobType);
	
	/**
	 * Connects the Producer to the Consumer.
	 */
	public void connect() throws PipeTypeNotSupportedException, OccupiedException;
}
