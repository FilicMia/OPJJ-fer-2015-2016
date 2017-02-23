package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type operator.
 * 
 * @author mia
 *
 */
public class ElementOperator extends Element {
	/**
	 * A single read-only <code>String</code> property.
	 */
	private String symbol;

	/**
	 * Constructor. Sets <code>symbol</code> property to symbol.
	 * 
	 * @param symbol
	 *            on which <code>symbol</code> property should be set;
	 *            <code>String</code>
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns read-only property <code>symbol</code>.
	 * 
	 * @return read-only property <code>symbol</code>; <code>Strign</code>
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return string of property <code>symbol</code>; String
	 */
	@Override
	public String asText() {
		return symbol;
	}

}

