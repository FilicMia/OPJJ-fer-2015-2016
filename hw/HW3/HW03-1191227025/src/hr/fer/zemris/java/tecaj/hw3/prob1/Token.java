/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Token. Lexical unit that groups one or more consecutive characters from input
 * program .
 * 
 * @author mia
 *
 */
public class Token {

	/** type of token. */
	TokenType type;
	/** Value of token */
	Object value;

	/**
	 * Constructor,constructs token of type type with value value.
	 * 
	 * @param type
	 *            token type; TokenType
	 * @param value
	 *            token value; Object
	 */
	public Token(TokenType type, Object value) {// ...
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns token value.
	 * 
	 * @return token value; Object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets token type.
	 * 
	 * @return token type; TokenType
	 */
	public TokenType getType() {
		return type;
	}
}
