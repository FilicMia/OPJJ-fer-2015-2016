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
	EOF, //oni su public final
	//mozemo ih usporedivati s ==
	Name,// letter+leters/digits/underscore and //Var name or = 
	FunctName,//starts after @ ,alse the same as tah name
	Operator, //+ - / * ^
	STRING,//inside "", \\ and \" is good only here
	WORD,//evrything elese(no spacing)
	NUMBER,//starting with number or -
	TEXT;//evrything outside {$ $},\\=\ and \$ = $
}
