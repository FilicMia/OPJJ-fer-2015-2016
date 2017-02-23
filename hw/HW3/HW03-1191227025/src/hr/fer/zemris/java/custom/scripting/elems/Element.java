/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Base class. Elements will be used for the representation of expressions.
 * 
 * @author mia
 *
 */
public class Element {

	/**
	 * String representation of expression held by this instance of class
	 * elements.
	 * 
	 * @return string representation of expression; string
	 */
	public String asText() {

		return "";
	}
	
	/**
	 * {@inheritDoc}
	 * @return string representation
	 */
	@Override
	public String toString(){
		return asText();
	}
	
	
}
