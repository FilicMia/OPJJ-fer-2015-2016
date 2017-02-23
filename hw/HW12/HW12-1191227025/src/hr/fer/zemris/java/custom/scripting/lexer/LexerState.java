package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum class of eligible Lexer states.
 * 
 * @author mia
 *
 */
public enum LexerState {
	/**Basic lexer analyze.Only Text tags and Word tags "{$" are produced.*/
	BASIC,
	/**Special lexer analyze. Used for in {$ $} analyze.*/
	EXTENDED;

}
