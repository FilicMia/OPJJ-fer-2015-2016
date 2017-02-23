package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type function.
 * 
 * @author mia
 *
 */
public class ElementFunction extends Element {
	/**
	 * A single read-only <code>String</code> property.
	 */
	private String name;

	/**
	 * Constructor. Sets <code>name</code> property to name.
	 * 
	 * @param name
	 *            on which <code>name</code> property should be set;
	 *            <code>String</code>
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * Returns read-only property <code>name</code>.
	 * 
	 * @return read-only property <code>name</code>; <code>String</code>
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return string property name; String
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 * @return name; String
	 */
	@Override
	public String toString(){
		return "@"+asText();
	}
}

