package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * (Class) Node representing a command that generates some textual output
 * dynamically. It inherits form <code>Node</code>
 * 
 * @author Mia Filic
 *
 */

// =
public class EchoNode extends Node {
	/**
	 * Arguments of echo node.
	 */
	private Element[] elements;

	/**
	 * Constructor. Sets read-only property elements.
	 * 
	 * @param elements
	 *            the value of property elements; <code>Elements</code>
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Getting arguments of EchoNode(Tag).
	 *
	 * @return elements; <code>Element</code>[]
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Getting the index-th argument of EchoNode(Tag).
	 * 
	 * @param index
	 *            of the element that should be returned
	 *
	 * @return elements[index]; <code>Element</code>
	 */
	public Element getElement(int index) {
		if ((index < 0 || index >= elements.length) || elements == null) {
			throw new IndexOutOfBoundsException(
					"There is no " + index + "-th argument.");
		}
		return elements[index];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ = ");
		for (Element element : elements) {
			sb.append(element.toString() + " ");
		}
		return sb.append("$}").toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);

	}
}
