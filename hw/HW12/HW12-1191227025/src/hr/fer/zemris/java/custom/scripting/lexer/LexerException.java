package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Iznimka koja se generira ukoliko dođe do pogreške u generiranju novog tokena
 * instance klase Lexer.
 * 
 * @author mia
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructor that accepts one argument , a string that describes the error
	 * That caused the exception .
	 * 
	 * @param message
	 *            exception message explaining the cause.
	 */
	public LexerException(String message) {
		super(message);
	}
}
