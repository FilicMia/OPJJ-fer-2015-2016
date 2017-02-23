package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type constant-integer.
 * 
 * @author mia
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * A single read-only <code>Integer</code> property.
	 */
	private int value;

	/**
	 * Constructor. Sets <code>value</code> property to value.
	 * 
	 * @param value
	 *            on which <code>value</code> property should be set;
	 *            <code>Integer</code>
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Returns read-only property <code>value</code>.
	 * 
	 * @return read-only property <code>value</code>; <code>Integer</code>
	 */
	public int getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return string representation of integer <code>value</code>; String
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}

}
