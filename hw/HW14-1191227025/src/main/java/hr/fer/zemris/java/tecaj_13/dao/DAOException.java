package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Implements the exception thrown by the {@link DAO} implementations.
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
	 * Empty default constructor.
	 */
	public DAOException() {
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            explanation of the error occurred.
	 * @param cause
	 *            cause of the exception.
	 * @param enableSuppression
	 *            indicates if the suppression should be enabled.
	 * @param writableStackTrace
	 *            indicate if the stack three should be popped
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            explanation of the error occurred.
	 * @param cause
	 *            cause of the exception.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constuctor.
	 * 
	 * @param message
	 *            explanation of the error occurred.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            cause of the exception.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}