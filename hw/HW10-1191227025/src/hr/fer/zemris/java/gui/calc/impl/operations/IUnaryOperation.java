package hr.fer.zemris.java.gui.calc.impl.operations;

/**
 * Interface for unary operations.
 * 
 * @author mia
 *
 */
public interface IUnaryOperation {
	/**
	 * Calculate the result of unary operation on provided double value.
	 * 
	 * @param value
	 *            value on which the calculation should be done.
	 * @return result
	 */
	public double calculate(double value);
}
