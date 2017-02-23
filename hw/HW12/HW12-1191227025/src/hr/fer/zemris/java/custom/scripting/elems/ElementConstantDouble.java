package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type constant-double.
 * 
 * @author mia
 *
 */
public class ElementConstantDouble extends Element {
	/**
	 * A single read-only <code>double</code> property.
	 */
	private double value;

	/**
	 * Constructor. Sets <code>value</code> property to value.
	 * 
	 * @param d
	 *            on which <code>value</code> property should be set;
	 *            <code>double</code>
	 */
	public ElementConstantDouble(double d) {
		this.value = d;
	}

	/**
	 * Returns read-only property <code>value</code>.
	 * 
	 * @return read-only property <code>value</code>; <code>double</code>
	 */
	public double getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return string representation of <code>value</code> of type double; String
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementConstantDouble(this);
	}

}
