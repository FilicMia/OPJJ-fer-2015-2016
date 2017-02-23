package hr.fer.zemris.java.gui.calc.impl.operations;

/**
 * Class wrapping all {@link IUnaryOperation} implementations.
 * 
 * @author mia
 *
 */
public class UnaryOperations {

	/**
	 * Calculating the reciprocal value of given value. Calculation of 1/x.
	 * 
	 * @author mia
	 *
	 */
	public class Reciprocal implements IUnaryOperation {

		@Override
		public double calculate(double value) {
			if (value == 0) {
				throw new ArithmeticException("0 has not its reciprocal.");
			}
			return 1 / value;
		}

	}

	/**
	 * Calculating the value of logarithm function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class Logarithm implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.log10(point);
		}

	}

	/**
	 * Calculating the value of sinus function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class Sinus implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.sin(point);
		}

	}

	/**
	 * Calculating the value of cosinus function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class Cosinus implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.cos(point);
		}

	}

	/**
	 * Calculating the value of natural logarithm function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class NaturalLogarithm implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.log(point);
		}

	}

	/**
	 * Calculating the value of natural tangens function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class Tangens implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.tan(point);
		}

	}

	/**
	 * Calculating the value of cotangens function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class Cotangens implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return 1 / Math.tan(point);
		}

	}

	/**
	 * Changes the sign of the value.
	 * 
	 * @author mia
	 *
	 */
	public class ChangeSign implements IUnaryOperation {

		@Override
		public double calculate(double value) {
			return -(value);
		}

	}

	/**
	 * Calculating the value of "ten on power of" function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class TenOnPower implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.pow(10, point);
		}

	}

	/**
	 * Calculating the value of natural exponent function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class NaturalExponent implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.exp(point);
		}

	}

	/**
	 * Calculating the value of arcus sinus function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class ArcusSinus implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.asin(point);
		}

	}

	/**
	 * Calculating the value of arcus cosinus function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class ArcusCosinus implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.acos(point);
		}

	}

	/**
	 * Calculating the value of arcus tangens function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class ArcusTangens implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.atan(point);
		}

	}

	/**
	 * Calculating the value of arcus tangens function at given point.
	 * 
	 * @author mia
	 *
	 */
	public class ArcusCotangens implements IUnaryOperation {

		@Override
		public double calculate(double point) {
			return Math.atan(1 / point);
		}

	}

}
