package core.exceptions;

/**
 * This Exception is thrown if a storage attempts to update its state
 * without prior registration to an observer.
 * @author Christopher Kraus
 *
 */

public class ObserverNotRegisteredException extends Exception {
	
	/**
	 * Auto-generated UID.
	 */
	private static final long serialVersionUID = -4958814715872723574L;

	public ObserverNotRegisteredException (String message) {
		super(message);
	}

}
