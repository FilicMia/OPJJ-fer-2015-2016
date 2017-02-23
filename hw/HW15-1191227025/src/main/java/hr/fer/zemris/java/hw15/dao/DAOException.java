package hr.fer.zemris.java.hw15.dao;

/**
 * Exception of corresponding the interface {@link DAO}.
 * 
 * @author mia
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            message of exception
	 * @param cause
	 *            cause of exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            message of exception
	 */
	public DAOException(String message) {
		super(message);
	}
}