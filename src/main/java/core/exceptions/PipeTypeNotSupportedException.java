package core.exceptions;

// Imports.


/**
 * This Exception guarantees that two pipes are compatible with one another.
 * @author christopher
 *
 */
public class PipeTypeNotSupportedException extends Exception {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -4433268837772638246L;

	// Constructors.
	public PipeTypeNotSupportedException (String message) {
		super(message);
	}
	
}
