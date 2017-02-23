/**
 * 
 */
package hr.fer.zemris.java.collections;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class storing implementation of 3 static classes that enables working with
 * complex polynomials and calculation of its root.
 * 
 * @author mia
 *
 */
public class ComplexUtil {

	/**
	 * <p>
	 * Represents immutable complex number. It allows multiple operations:
	 * calculation of module, subtraction, addition, division, multiplication
	 * with another {@code Complex} number, negation of number, calculation of
	 * its n-th root.
	 * </p>
	 */
	public static class Complex {
		/** Real part of complex number of rectangular form. */
		private double real;
		/** Imaginary part of complex number of rectangular form. */
		private double imaginary;

		/** Complex representation of number zero. */
		public static final Complex ZERO = new Complex(0, 0);
		/** Complex representation of number one. */
		public static final Complex ONE = new Complex(1, 0);
		/** Complex representation of number minus one. */
		public static final Complex ONE_NEG = new Complex(-1, 0);
		/** Complex representation of number i. */
		public static final Complex IM = new Complex(0, 1);
		/** Complex representation of number -i. */
		public static final Complex IM_NEG = new Complex(0, -1);

		/**
		 * Default constructor creating the complex number of zero.
		 */
		public Complex() {
			this(0, 0);
		}

		/**
		 * Constructor.
		 * 
		 * @param real
		 *            real part of complex number; double
		 * @param imaginary
		 *            imaginary part of complex number; double
		 */
		public Complex(double real, double imaginary) {
			this.real = real;
			this.imaginary = imaginary;
		}

		/**
		 * Method that parses string to create new instance of Complex. Accepts
		 * strings such as: "3.51", "-3.17", "-2.71i", "i", "1", "-2.71-3.15i"
		 * but not "7+7","i+36","i+i","hsd","".
		 * 
		 * @param s
		 *            sting representing complex number; string
		 * @return complex number represented by string s; Complex
		 */
		public static Complex parse(String s) {
			double real = 0;
			double imaginary = 0;

			String string = s.replaceAll("[-]{1}[ ]*", " -");
			string = string.replaceAll("[+]{1}[ ]*", " +");
			String[] rectangularForm = string.trim().split("[ ]+");
			int size = rectangularForm.length;

			if (size <= 0 || size > 2) {
				throw new IllegalArgumentException(
						"You have" + "passed wrong argument " + s);
			}
			if (size == 2) {
				if (rectangularForm[0].endsWith("i")
						|| !rectangularForm[1].endsWith("i")) {
					throw new IllegalArgumentException(
							"You have" + "passed wrong argument " + s);
				}
			}

			for (String string2 : rectangularForm) {
				DecimalFormatSymbols dfs = new DecimalFormatSymbols();
				char delimiter = dfs.getDecimalSeparator();
				if (string2.matches(
						"^[+-]?[0-9]*[" + delimiter + "]{0,1}[0-9]*[i]{1}$")) {
					string2 = string2.replace("i", "");
					if (string2.matches("^[+-]?$")) {
						imaginary = Double.parseDouble(string2 + 1);
					} else {
						imaginary = Double.parseDouble(string2);
					}
					break;
				} else {
					if (string2.matches(
							"^[+-]?[0-9]+[" + delimiter + "]{0,1}[0-9]*$")) {
						real = Double.parseDouble(string2);
					} else {
						throw new IllegalArgumentException(
								"You have" + " passed wrong argument: " + s);
					}
				}
			}

			return new Complex(real, imaginary);

		}

		/**
		 * Public method that returns the real part of complex number of
		 * rectangular form
		 * 
		 * @return real part of complex number; double
		 */
		public double getReal() {
			return real;
		}

		/**
		 * Public method that returns the imaginary part of complex number of
		 * rectangular form
		 * 
		 * @return imaginary part of complex number; double
		 */
		public double getImaginary() {
			return imaginary;
		}

		/**
		 * Calculates the module of number.
		 * 
		 * @return module of number
		 */
		public double module() {
			return Math.hypot(real, imaginary);
		}

		/**
		 * Public method that returns the angle of complex number of polar form.
		 * Angle is between 0 an 2*PI.
		 * 
		 * @return angle; double
		 */
		private double getAngle() {
			double angle = Math.atan2(imaginary, real);
			return (angle < 0) ? 2 * Math.PI + angle : angle;
		}

		/**
		 * Public method that calculate multiplication of two complex numbers.
		 * The instance of complex number on that the addition is called is
		 * multiple by complex number passed as argument of the function.
		 * 
		 * @param c
		 *            complex number to be multiple with
		 * @return result; Complex
		 */
		public Complex multiply(Complex c) {
			double real = this.real * c.getReal()
					- this.imaginary * c.getImaginary();
			double im = this.real * c.getImaginary()
					+ this.imaginary * c.getReal();

			return new Complex(real, im);
		}

		/**
		 * Divides two complex numbers.
		 *
		 * @param c
		 * @return result
		 */
		public Complex divide(Complex c) {
			if (c == null) {
				throw new NullPointerException("Value given can not be null.");
			}
			if (c.getReal() == 0 && c.getImaginary() == 0) {
				throw new IllegalArgumentException(
						"Value given can not be zero.");
			}
			double module = this.module() / c.module();
			double angle = this.getAngle() - c.getAngle();
			return new Complex(module * Math.cos(angle),
					module * Math.sin(angle));

		}

		/**
		 * Public method that calculates addition of two complex numbers. The
		 * instance of complex number on that the addition is called is added to
		 * complex number passed as argument of the function.
		 * 
		 * @param c
		 *            complex number to be added
		 * @return result; Complex
		 */
		public Complex add(Complex c) {
			return new Complex(this.real + c.getReal(),
					this.imaginary + c.getImaginary());
		}

		/**
		 * Public method that calculates subtraction of two complex numbers. The
		 * instance of complex number on that the subtraction is called is
		 * subtracted by complex number that is passed as argument of the
		 * function.
		 * 
		 * @param c
		 *            complex number, subtrahend
		 * @return result; Complex
		 */
		public Complex sub(Complex c) {
			return new Complex(this.real - c.getReal(),
					this.imaginary - c.getImaginary());
		}

		/**
		 * Calculates the negation of the number.
		 * 
		 * @return result
		 */
		public Complex negate() {
			return new Complex(-this.real, -this.imaginary);
		}

		/**
		 * Public method that calculates n-th power of complex number. n is
		 * passed as function argument.
		 * 
		 * @param n
		 *            power of complex number that should be calculated; int
		 * @return result
		 */
		public Complex power(int n) {
			if (n < 0) {

				throw new IllegalArgumentException();
			}
			double newMagnitude = Math.pow(module(), n);

			return new Complex(newMagnitude * Math.cos(n * getAngle()),
					newMagnitude * Math.sin(n * getAngle()));

		}

		/**
		 * Public method that calculates n-th roots of complex number. n is
		 * passed as function argument.
		 * 
		 * @param n
		 *            positive integer saying which roots of complex number
		 *            should be calculated
		 * @return list of n-th roots
		 */
		public List<Complex> root(int n) {
			if (n <= 0) {
				throw new IllegalArgumentException();
			}

			double r = module();
			double fi = getAngle();
			double imaginary, real;
			List<Complex> roots = Collections
					.synchronizedList(new ArrayList<>());

			fi /= n;
			r = Math.pow(r, 1 / (double) n);

			for (int i = 0; i < n; i++) {
				real = r * Math.cos(fi);
				imaginary = r * Math.sin(fi);
				roots.add(new Complex(real, imaginary));
				fi += (2.0 * Math.PI) / (double) n;
			}

			return roots;

		}

		/**
		 * Public method that creates string representation of complex number.
		 * 
		 * @return string representation; String
		 */
		@Override
		public String toString() {
			DecimalFormat formaterIm = new DecimalFormat(
					"0.0#######i; - 0.0#######i");
			DecimalFormat formaterR = new DecimalFormat(
					"0.0#######; - 0.0#######");
			String imaginary = this.imaginary == 0.0 ? ""
					: formaterIm.format(this.imaginary);
			String real = this.real == 0.0 && !imaginary.equals("") ? ""
					: formaterR.format(this.real);
			String operator = this.imaginary > 0.0 && this.real != 0.0 ? " + "
					: "";

			return real + operator + imaginary;
		}

		

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Complex other = (Complex) obj;
			if (Double.doubleToLongBits(imaginary) != Double
					.doubleToLongBits(other.imaginary))
				return false;
			if (Double.doubleToLongBits(real) != Double
					.doubleToLongBits(other.real))
				return false;
			return true;
		}
		
	}

	/**
	 * Represents immutable complex polynomial.
	 * 
	 * @author mia
	 *
	 */
	public static class ComplexPolynomial {
		/**
		 * Factors of the polynomial.
		 */
		private Complex[] factors;

		/**
		 * Constructor of {@code ComplexPolinomial}. It gets the field of
		 * complex number .On i-th number of the field is factor corresponding
		 * the i-th potency of the polynomial. All null factors are replaced
		 * with ZERO.
		 * 
		 * @param factors
		 *            of the polynomial
		 * 
		 */
		public ComplexPolynomial(Complex... factors) {
			if (factors == null) {
				factors = new Complex[10]; // aloceted onzero
			}

			this.factors = Arrays.copyOf(factors, factors.length);
			for (int i = 0; i < factors.length; ++i) {
				if (factors[i] == null) {
					factors[i] = Complex.ZERO;
				}
			}

		}

		/**
		 * Returns order of this polynomial; eg. For (7+2i)z^3+2z^2+5z+1 returns
		 * 3
		 * 
		 * @return order of this polynomial
		 */
		public short order() {
			for (int i = 0; i < factors.length; ++i) {
				if (!factors[i].equals(Complex.ZERO)) {
					return (short) (factors.length-1-i);
				}
			}
			return 0;
		}

		/**
		 * Computes a new polynomial {@code this}*p.
		 * 
		 * @param p
		 *            polynomial to be multiplied with.
		 * @return result
		 */
		public ComplexPolynomial multiply(ComplexPolynomial p) {
			int order1 = order();
			int order2 = p.order();
			int newOrder = order1 + order2;
			Complex[] newFactors = new Complex[newOrder + 1];
			for (int i = 0; i < newOrder + 1; ++i) {
				newFactors[i] = Complex.ZERO;
			}

			for (int i = 0; i < order1 + 1; ++i) {
				for (int j = 0; j < order2 + 1; ++j) {
					newFactors[i + j] = newFactors[i + j]
							.add(factors[i].multiply(p.factors[j]));
				}
			}

			return new ComplexPolynomial(newFactors);
		}

		/**
		 * Computes first derivative of this polynomial. For example, for:
		 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
		 * 
		 * @return result
		 */
		public ComplexPolynomial derive() {
			Complex[] derivedFactors = new Complex[factors.length - 1];
			int order = this.order();
			for (int i = 0; i < order; i++) {
				derivedFactors[i] = this.factors[i]
						.multiply(new Complex(this.order() - i, 0));
			}

			return new ComplexPolynomial(derivedFactors);
		}

		/**
		 * @return Factors of this ComplexPolynomial.
		 */
		public Complex[] getFactors() {
			return this.factors;
		}

		/**
		 * Computes polynomial value at given point z.
		 * 
		 * @param z
		 *            point in which the polynomial should be computed.
		 * @return result
		 */
		public Complex apply(Complex z) {
			Complex result = Complex.ZERO;
			Complex exponent = Complex.ONE;

			for (int i = factors.length - 1; i >= 0; i--) {
				result = result.add(exponent.multiply(factors[i]));
				exponent = exponent.multiply(z);
			}

			return result;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < factors.length; ++i) {
				if (factors[i] == null || factors[i].equals(Complex.ZERO)) {
					continue;
				}
				int exponent = factors.length-1-i;
				sb.append("(");
				sb.append(factors[i].toString());
				sb.append(exponent == 0 ? ")" : (")z^" + exponent));
				sb.append(exponent == 0 ? "" : " + ");
			}
			return sb.toString();
		}

	}

	/**
	 * Representation of complex polynomial represented by its roots.
	 * 
	 * @author mia
	 *
	 */
	public static class ComplexRootedPolynomial {
		/**
		 * Roots of the polynomial.
		 */
		private Complex[] roots;

		/**
		 * Constructs the complex polynomial represented by its roots.
		 * 
		 * @param roots
		 *            roots of the polynomial
		 * @throws if
		 *             at least one of thr roots is null
		 */
		public ComplexRootedPolynomial(Complex... roots) {
			for (int i = 0; i < roots.length; ++i) {
				if (roots[i] == null) {
					throw new IllegalArgumentException("Root can not be null.");
				}
			}
			this.roots = Arrays.copyOf(roots, roots.length);
		}

		/**
		 * Computes polynomial value at given point z.
		 * 
		 * @param z
		 *            point in which the polynomial value will be calculated
		 * @return result
		 */
		public Complex apply(Complex z) {
			Complex result = Complex.ONE;

			for (Complex root : roots) {
				result = result.multiply(z.sub(root));
			}

			return result;
		}

		/**
		 * Converts this representation to {@code ComplexPolynomial} type.
		 * 
		 * @return converted polynomial
		 */
		public ComplexPolynomial toComplexPolynom() {
			ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);

			for (Complex root : roots) {
				result = result.multiply(
						new ComplexPolynomial(Complex.ONE, root.negate()));
			}

			return result;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < roots.length; ++i) {
				if (roots[i] == null) {
					continue;
				}
				sb.append("(z");
				sb.append(" - (");
				sb.append(roots[i].toString());
				sb.append("))");
				sb.append(i == roots.length - 1 ? "" : " + ");
			}
			return sb.toString();
		}

		/**
		 * Finds index of closest root for given complex number z that is within
		 * treshold if there is no such root, returns -1.
		 * 
		 * @param z
		 *            number for which index of the closest root will be found
		 * @param treshold
		 *            accetable variance form the one of the roots
		 * @return index of closest root to given complex number that is within
		 *         treshold if there is no such root, returns -1.
		 */
		public int indexOfClosestRootFor(Complex z, double treshold) {
			int index = -1;
			double minDis = Double.MAX_VALUE;
			for (int i = 0; i < roots.length; ++i) {
				double distance = z.sub(roots[i]).module();
				if (Double.compare(distance, minDis) < 0
						&& Double.compare(distance, treshold) < 0) {
					index = i;
					minDis = distance;
				}
			}
			return index;
		}

		/**
		 * @return Returns roots of this CompleRootedPolynomial.
		 */
		public Complex[] getRoots() {
			return this.roots;
		}

		/**
		 * @return Returns order of this ComplexRootedPolynomial.
		 */
		public int order() {
			return this.roots.length;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ComplexRootedPolynomial other = (ComplexRootedPolynomial) obj;
			if (!Arrays.equals(roots, other.roots))
				return false;
			return true;
		}

	}

}
