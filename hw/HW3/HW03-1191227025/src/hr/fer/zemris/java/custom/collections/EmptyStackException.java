package hr.fer.zemris.java.custom.collections;


/**
 * Class that implements exception that is thrown when an instance
 * of the ObjectStack calls pop or peek on an empty stack.
 * It inherits from the RuntimeException.
 *  
 * @author Mia Filić
 *
 */

public class EmptyStackException extends RuntimeException{
	
	/**Serial Id number of the Exception.*/
	private static final long serialVersionUID = 8649440350758699570L;

	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
	}
	
	/**
	 * Constructor that prints Error message.
	 * 
	 * @param message Error message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
