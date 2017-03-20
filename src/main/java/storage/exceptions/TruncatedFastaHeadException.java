package storage.exceptions;

/**
 * Unchecked exception for truncated fasta files.
 * @author Christopher Kraus
 *
 */
public class TruncatedFastaHeadException extends Exception {
	/**
	 * Auto-generated UID.
	 */
	private static final long serialVersionUID = -763489184542995597L;

	public TruncatedFastaHeadException(String message) {
		super(message);
	}
}
