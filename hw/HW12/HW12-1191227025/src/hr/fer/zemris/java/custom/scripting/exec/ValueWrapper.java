package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Value wrapper. It wrappers single read-write instance of {@code Object}.
 * 
 * If arithmetic and comparison operations will be called, we define that
 * allowed values for current content of ValueWrapper object and for argument
 * are {@code null} or instances of {@code Integer}, {@code String},
 * {@code Double} classes.
 * 
 * @author Mia Filić
 * @version 1.0
 *
 */
public class ValueWrapper {
	/** Reference to object stored/wrapped. */
	private Object value;

	/**
	 * Constructor setting the property value on it initial value.
	 * 
	 * @param value
	 *            initial value of {@code value}.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Increments the current content of {@link ValueWrapper} object for
	 * {@code incValue}.
	 * 
	 * Allowed values for current content of ValueWrapper object and for
	 * argument are {@code null} or instances of {@code Integer}, {@code String}
	 * , {@code Double} classes.
	 * 
	 * @param incValue
	 *            value with which the content should be incremented
	 * @throws RuntimeException
	 *             if current value is not instance of {@code Integer},
	 *             {@code String}, {@code Double} or {@code null}
	 */
	public void increment(Object incValue) {
		Number value = getCheck(this.value);
		Number increment = getCheck(incValue);

		if (value instanceof Double || increment instanceof Double) {
			this.value = value.doubleValue() + increment.doubleValue();
		} else {
			this.value = value.intValue() + increment.intValue();
		}

	}

	/**
	 * Supplementary method that checks the restrictions of the value property
	 * and arguments when calling the arithmetic and comparison operations.
	 * 
	 * @param value
	 * 
	 * @return integer of double value corresponding property value.
	 */
	private Number getCheck(Object value) {
		if (value == null)
			return 0;
		if (value instanceof Integer)
			return (Number) value;
		if (value instanceof Double)
			return (Number) value;
		if (value instanceof String)
			return numberValueOf((String) value);
		throw new NumberFormatException(
				"Illegal value of argument of the operation.");
	}

	/**
	 * Creates the number value of passed string.
	 * 
	 * @param value
	 *            string to be converted to number.
	 * @return the number value of passed string.
	 * @throws NumberFormatException
	 *             if conversion fails.
	 */
	private Number numberValueOf(String value) {
		if (value.contains(".") || value.contains("E")) {
			return Double.valueOf(value);
		}
		return Integer.valueOf(value);
	}

	/**
	 * Decrements the current content of {@link ValueWrapper} object for
	 * {@code decValue}.
	 * 
	 * Allowed values for current content of ValueWrapper object and for
	 * argument are {@code null} or instances of {@code Integer}, {@code String}
	 * , {@code Double} classes.
	 * 
	 * @param decValue
	 *            value with which the content should be decremented
	 * @throws RuntimeException
	 *             if current value is not instance of {@code Integer},
	 *             {@code String}, {@code Double} or {@code null}
	 */
	public void decrement(Object decValue) {
		Number value = getCheck(this.value);
		Number decrement = getCheck(decValue);

		if (value instanceof Double || decrement instanceof Double) {
			this.value = value.doubleValue() - decrement.doubleValue();
		} else {
			this.value = value.intValue() - decrement.intValue();
		}
	}

	/**
	 * Multiplies the current content of {@link ValueWrapper} object with
	 * {@code mulValue}.
	 * 
	 * Allowed values for current content of ValueWrapper object and for
	 * argument are {@code null} or instances of {@code Integer}, {@code String}
	 * , {@code Double} classes.
	 * 
	 * @param mulValue
	 *            value with which the content should be multiplied
	 * @throws RuntimeException
	 *             if current value is not instance of {@code Integer},
	 *             {@code String}, {@code Double} or {@code null}
	 */
	public void multiply(Object mulValue) {
		Number value = getCheck(this.value);
		Number multiplier = getCheck(mulValue);

		if (value instanceof Double || multiplier instanceof Double) {
			this.value = value.doubleValue() * multiplier.doubleValue();
		} else {
			this.value = value.intValue() * multiplier.intValue();
		}
	}

	/**
	 * Divides the current content of {@link ValueWrapper} object with
	 * {@code divValue}.
	 * 
	 * Allowed values for current content of ValueWrapper object and for
	 * argument are {@code null} or instances of {@code Integer}, {@code String}
	 * , {@code Double} classes.
	 * 
	 * @param divValue
	 *            value with which the content should be divided
	 * @throws RuntimeException
	 *             if current value is not instance of {@code Integer},
	 *             {@code String}, {@code Double} or {@code null}
	 * @throws ArithmeticException
	 *             if property value is tried to be divided by zero.
	 */
	public void divide(Object divValue) {
		Number value = getCheck(this.value);
		Number divisor = getCheck(divValue);
		if (divisor.doubleValue() == 0.0) {
			throw new ArithmeticException("Tried to divide by zero.");
		}

		if (value instanceof Double || divisor instanceof Double) {
			this.value = value.doubleValue() / divisor.doubleValue();
		} else {
			this.value = value.intValue() / divisor.intValue();
		}
	}

	/**
	 * Compares the current content of {@link ValueWrapper} object with
	 * {@code withValue}.
	 * 
	 * Allowed values for current content of ValueWrapper object and for
	 * argument are {@code null} or instances of {@code Integer}, {@code String}
	 * , {@code Double} classes.
	 * 
	 * @param withValue
	 *            value with which the content should be multiplied
	 * @return an integer less than zero if currently stored value is smaller
	 *         than argument, an integer greater than zero if currently stored
	 *         value is larger than argument or an integer 0 if they are equal.
	 * @throws RuntimeException
	 *             if current value is not instance of {@code Integer},
	 *             {@code String}, {@code Double} or {@code null}
	 */
	public int numCompare(Object withValue) {
		Double value = getCheck(this.value).doubleValue();
		Double compareTo = getCheck(withValue).doubleValue();

		// System.out.println(value.toString());
		return value.compareTo(compareTo);
	}

	/**
	 * Returns the value stored.
	 * 
	 * @return the value stored.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Changes the value stored.
	 * 
	 * @param value
	 *            new value of the {@code value} stored.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
