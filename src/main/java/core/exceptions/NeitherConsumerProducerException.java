package core.exceptions;

/**
 * This Exception is thrown if a module is neither a Consumer or Producer.
 * @see Consumer Producer design pattern.
 * @see Model-View-Observer (MVC) design pattern.
 * @author christopher
 *
 */
public class NeitherConsumerProducerException extends Exception {
	
	/**
	 * Auto-generated UID.
	 */
	private static final long serialVersionUID = -89609569410942971L;

	public NeitherConsumerProducerException (String message) {
		super(message);
	}

}
