/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum class of eligible token types.
 * 
 * @author mia
 *
 */
public enum TokenType {
	/** End of the file. */
	EOF,
	/** Token name. */
	NAME,
	/** Token function. */
	FUNCT_NAME,
	/** Token operator. */
	OPERATOR,
	/** Token string. */
	STRING,
	/** Token word. */
	WORD,
	/** Token number. */
	NUMBER,
	/** Token text. */
	TEXT;
}
