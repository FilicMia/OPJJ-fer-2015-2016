package hr.fer.zemris.java.gui.calc.impl.operations;

/**
 * Class wrapping all {@link IBinaryOperation} implementations.
 * 
 * @author mia
 *
 */
public class BinaryOperations {

	/**
	 * Adds given values.
	 * 
	 * @author mia
	 *
	 */
	public class Addition implements IBinaryOperation {

		@Override
		public double calculate(double first, double second) {
			return first + second;
		}

	}
	
	/**
	 * Subtracts first value with second.
	 * 
	 * @author mia
	 *
	 */
	public class Subtraction implements IBinaryOperation {

		@Override
		public double calculate(double first, double second) {
			return first-second;
		}

	}

	/**
	 * Multiplies first value with second.
	 * 
	 * @author mia
	 *
	 */
	public class Multiplication implements IBinaryOperation {

		@Override
		public double calculate(double first, double second) {
			return first*second;
		}

	}

	/**
	 * Divides first value with second.
	 * 
	 * @author mia
	 *
	 */
	public class Division implements IBinaryOperation {

		@Override
		public double calculate(double first, double second) {
			return first/second;
		}

	}

	/**
	 * Equal sign operation.
	 * 
	 * @author mia
	 *
	 */
	public class Equal implements IBinaryOperation {

		@Override
		public double calculate(double first, double second) {
			return second;
		}

	}

	
	/**
	 * Rises first on the power of second.
	 * 
	 * @author mia
	 *
	 */
	public class Power implements IBinaryOperation {

		@Override
		public double calculate(double first, double second) {
			return Math.pow(first, second);
		}

	}

}
