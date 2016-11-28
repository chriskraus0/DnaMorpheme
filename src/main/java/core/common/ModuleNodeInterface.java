package core.common;

// Imports.

// Project specific exceptions.
import core.exceptions.OccupiedException;
import core.exceptions.PipeTypeNotSupportedException;

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
	 * Connects the Producer to the Consumer.
	 */
	public void connect() throws PipeTypeNotSupportedException, OccupiedException;
}
