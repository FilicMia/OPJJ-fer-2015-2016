package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * (Class) Node representing a command that generates some textual output
 * dynamically. It inherits form <code>Node</code>
 * 
 * @author Mia Filić
 *
 */

//=
public class EchoNode extends Node {
	/**
	 * Arguments of echo node.
	 */
	private Element[] elements;
	
	/**
	 * Constructor. Sets read-only property elements.
	 * 
	 * @param elements the value of property elements; <code>Elements</code>
	 */
	public EchoNode(Element[] elements){
		this.elements = elements;
	}
	
	/**
	 *Getting arguments of EchoNode(Tag).
	 *
	 * @return elements; <code>Element</code>[]
	 */
	public Element[] getElements(){
		return elements;
	}
	
	/**
	 *Getting the index-th argument of EchoNode(Tag).
	 *
	 * @return elements[index]; <code>Element</code>
	 */
	public Element getElement(int index){
		if((index < 0 || index >= elements.length) || elements == null){
			throw new IndexOutOfBoundsException("There is no "+index+"-th argument.");
		}
		return elements[index];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		String string = "{$ = ";
		for(Element element : elements){
			string += element.toString()+" ";
		}
		return string+"$}";
		}

}
