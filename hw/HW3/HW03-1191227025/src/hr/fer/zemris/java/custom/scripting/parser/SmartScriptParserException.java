package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that is thrown if any exception occurs during parsing.
 * 
 * @author mia
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Serial Id of Exception.
	 */
	private static final long serialVersionUID = -7146405271722626823L;

	/**
	 * Default constructor.
	 */
	public SmartScriptParserException() {

	}

	/**
	 * Constructor that has one argument, <code>String</code> message describing
	 * the error that has occurred.
	 * 
	 * @param message string describing an error that has occurred; <code>String</code>
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
