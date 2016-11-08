package core.common;

// Imports.

import java.lang.Exception;

/**
 * Interface defines critical abstract methods for each module.
 * @author christopher
 *
 */

public interface ModuleInterface {
	
	// Methods.
	
	// Setters.
	
	/**
	 * Set the state of a module defined by the enumeration ModuleState.
	 * @see enum ModuleState
	 * @param ModuleState mState
	 */
	public void setModuleState(ModuleState mState);
	
	// End setters.
	
	// Getters.
	
	/**
	 * Return the state of a module defined by the enumeration ModuleState.
	 * @see enum ModuleState
	 * @return ModuleState mState
	 */
	public ModuleState getModuleState();
	
	/**
	 * Return the unique moduleID of a module.
	 * @return String moduleID
	 */
	public String getModuleID();
	
	/**
	 * Return the type of this module defined by the enumeration ModuleType.
	 * @see enum ModuleType
	 * @return ModuleType mType
	 */
	public ModuleType getModuleType();
	
	/**
	 * Return the associated unique storageID for this module.
	 * @return
	 */
	public String getStorageID();
	
	// End getters.
	
	/**
	 * Calls an external command and returns the current state of the command.
	 * @param String command
	 * @param String storrageID
	 * @return CommandState cState
	 * @throws Exception
	 */
	public CommandState callCommand(String command, String storrageID) throws Exception;
	// TODO: Intergrate new Exception.
	
	// End methods.

}
