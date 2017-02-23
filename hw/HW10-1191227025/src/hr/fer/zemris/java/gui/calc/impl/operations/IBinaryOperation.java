package hr.fer.zemris.java.gui.calc.impl.operations;

/**
 * Interface for binary operations.
 * 
 * @author mia
 *
 */
public interface IBinaryOperation {
	/**
	 * Calculate the result of binary operation on provided double values.
	 * 
	 * @param first
	 *            first argument of the operation.
	 * @param second
	 *            second argument of the operation.
	 * @return result
	 */
	public double calculate(double first, double second);
}
