/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type variable.
 * @author mia
 *
 */
public class ElementVariable extends Element{
	/**
	 * A single read-only String property.
	 */
	private String name;
	
	/**
	 * Constructor. Sets <code>name</code> property to name.
	 * 
	 * @param name
	 *            on which <code>name</code> property should be set;
	 *            <code>String</code>
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Returns read-only property name.
	 * 
	 * @return read-only property name; String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 * @return name; String
	 */
	@Override
	public String asText(){
		return name;
	}
	
	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementVariable(this);
	}
	
}
