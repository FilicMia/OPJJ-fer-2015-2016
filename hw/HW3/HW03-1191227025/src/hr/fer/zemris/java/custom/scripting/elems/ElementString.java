package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type string.
 * 
 * @author mia
 *
 */
public class ElementString extends Element {
	/**
	 * A single read-only <code>String</code> property.
	 */
	private String value;

	/**
	 * Constructor. Sets <code>value</code> property to value.
	 * 
	 * @param value
	 *            on which <code>value</code> property should be set;
	 *            <code>String</code>
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Returns read-only property <code>value</code>.
	 * 
	 * @return read-only property <code>value</code>; <code>String</code>
	 */
	public String getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return string property value; String
	 */
	@Override
	public String asText() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return "original" string representation of String.
	 */
	@Override
	public String toString() {
		String str = "";
		char[] charSq = value.toCharArray();
		int size = charSq.length;
		for (int i = 0; i < size; ++i) {
			if (charSq[i] == '\\'
					|| (i != 0 && i != size - 1 && charSq[i] == '\"')) {
				str += '\\';
			}
			str += charSq[i];
		}
		return str;
	}

}
