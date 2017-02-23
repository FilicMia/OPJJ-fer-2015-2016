package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class of structure that will be used for representation of structured
 * documents.
 * 
 * @author mia
 *
 */
public class Node {

	private ArrayIndexedCollection children;

	/**
	 * Method that adds given <code>child</code> to an internally managed
	 * collection of children. Instance of <code>ArrayIndexedCollection</code>
	 * is used for that purpose, created on demend.
	 * 
	 * @param child
	 *            <code>Node</code> to be added as child
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}
		// adds on the end.
		children.add(child);
	}

	/**
	 * Method that returnd a number of (direct) children.
	 */
	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}

		return children.size();
	}

	/**
	 * Method that returns selected child(by <code>index</code>).
	 * 
	 * @param index
	 *            index of selected children
	 * @return the one on index {@code index}
	 * @throws IndexOutOfBoundsException
	 *             if index is invalid
	 */
	public Node getChild(int index) {
		if (children == null) {
			throw new IndexOutOfBoundsException(
					"Child od index " + index + " does not exists.");
		}

		// if illegal index occured, ArrayIndexedCol. throws exception.
		return (Node) children.get(index);
	}

}
