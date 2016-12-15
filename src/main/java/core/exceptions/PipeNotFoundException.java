package core.exceptions;

/**
 * Pipe associated exception. Thrown exception in case of a missing pipe (closed before associated port).
 * TODO
 * @author christopher
 *
 */
public class PipeNotFoundException extends Exception {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 8567554062081440863L;

	// Constructors.
	public PipeNotFoundException (String message) {
		super(message);
	}

}
