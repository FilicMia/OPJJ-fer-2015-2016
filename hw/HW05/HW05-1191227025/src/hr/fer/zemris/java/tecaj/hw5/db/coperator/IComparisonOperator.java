/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db.coperator;

/**
 * Compares two strings. Way of comparison can be different due to
 * implementation.
 * 
 * @author mia
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Compares two strings.
	 * 
	 * @param value1
	 *            first string
	 * @param value2
	 *            second string
	 * @return {@code true} is strings are related due to comparison,
	 *         {@code false} otherwise
	 */
	public boolean satisfied(String value1, String value2);

}