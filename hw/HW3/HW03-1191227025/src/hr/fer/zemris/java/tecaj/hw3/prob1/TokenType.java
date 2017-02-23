/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Enum class of eligible token types.
 * 
 * @author mia
 *
 */
public enum TokenType {
	EOF, //oni su public final
	//mozemo ih usporedivati s ==
	WORD,
	NUMBER,
	SYMBOL;
}
