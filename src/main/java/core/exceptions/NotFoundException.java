package core.exceptions;

/**
 * Pipe associated exception. Honestly, I currently do not know why will need this one.
 * TODO
 * @author christopher
 *
 */
public class NotFoundException extends Exception {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 8567554062081440863L;

	// Constructors.
	public NotFoundException (String message) {
		super(message);
	}

}
