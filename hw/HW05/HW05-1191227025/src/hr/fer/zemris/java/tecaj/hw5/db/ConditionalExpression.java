package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.coperator.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.vgetter.IFieldValueGetter;

/**
 * Conditional expression implementer.
 * 
 * @author Mia FIlić
 *
 */
public class ConditionalExpression {
	/** Field getter. */
	private IFieldValueGetter fieldGetter;

	/** Corresponding comparison operator. */
	private IComparisonOperator comparisonOperator;

	/** Literal. */
	private String stringLiteral;

	/**
	 * Constructor. Sets the properties to given values.
	 * 
	 * @param fieldGetter
	 *            value field getter
	 * @param stringLiteral
	 *            literal
	 * @param comparisonOperator
	 *            instance of class implementing {@code IComparisonOperator}
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter,
			IComparisonOperator comparisonOperator, String stringLiteral) {
		super();
		
		this.fieldGetter = fieldGetter;
		this.comparisonOperator = comparisonOperator;
		this.stringLiteral = stringLiteral;
	}

	/**
	 * Returns corresponding field getter.
	 * 
	 * @return stored {@code IFieldValueGetter}
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns corresponding comparison operator.
	 * 
	 * @return stored comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Returns corresponding string literal.
	 * 
	 * @return stored string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

}
