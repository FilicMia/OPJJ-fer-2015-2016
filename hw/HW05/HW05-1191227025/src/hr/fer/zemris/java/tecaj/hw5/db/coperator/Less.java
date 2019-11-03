package hr.fer.zemris.java.tecaj.hw5.db.coperator;

/**
 * Compares two strings.
 * 
 * @author MiaFIlić
 *
 */
public class Less implements IComparisonOperator {

	/**
	 * Checks if first value is less than second one.
	 * 
	 * @param value1
	 *            first string
	 * @param value2
	 *            second string
	 * @return {@code true} is strings are related due to relation
	 *         less, {@code false} otherwise
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		if(value1 == null || value2 == null){
			throw new NullPointerException();
		}
		return value1.compareTo(value2) <= 0;
	}

}