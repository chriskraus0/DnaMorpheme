package core.common;

/**
 * This interface defines the core of the abstract factory (design pattern) "ModuleBuilder".
 * It includes a set of core classes which define how to build modules, communication and states.
 * Responsibilites: I/O port generation and inter-connection, module generation, state generation. 
 * @author christopher
 *
 */

// Imports.

public interface ModuleBuilderInterface {
	
	// Methods.
		
	/**
	* Create new module instance.
	 * @param String moduleID
	 * @param String storageID
	 * @param ModuleType mType
	 * @see enum ModuleType
	 * @return Module
	 */
	public Module createNewModule(int moduleID, int storageID, ModuleType mType, String command);
	
	/**
	 * Create new InputPort for given module.
	 * @param String moduleID
	 */
	public void createNewInputPort(String moduleID);
	
	/**
	 * Create new OutputPort for given module.
	 * @param moduleID
	 */
	public void createNewOutputPort(String moduleID);
	
	/**
	 * Request storage for a module and return the storage ID.
	 * @return String storageID
	 */
	public int requestStorage();
	
	/**
	 * Destroy given module if no longer needed.
	 * @param moduleD
	 */
	public void destroyModule(String moduleD);
	
	// End Methods.

}
