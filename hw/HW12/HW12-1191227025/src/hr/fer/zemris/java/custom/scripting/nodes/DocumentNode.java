package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * (Class) Node representing a entire document.
 * It inherits form <code>Node</code>
 * @author mia
 *
 */
public class DocumentNode extends Node{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		String documentBody = "";
		documentBody = createStringTree(documentBody, this, "");
		return documentBody;
		}
	
	/**
	 * Creates a string-tree structure of {@link Node}.
	 * 
	 * @param documentBody
	 *            string representing the document tree
	 * @param document
	 *            parsed tree structure
	 * @param tab
	 *            used for better visualization child {@link Node}s
	 * @return string representing tree structure
	 */
	private static String createStringTree(String documentBody, final Node document, final String tab) {
		if (document instanceof DocumentNode) {
			documentBody += tab + "DocumentNode\n";
		} else if (document instanceof ForLoopNode) {
			documentBody += tab + "ForLoopNode\n";

		} else if (document instanceof EchoNode) {
			documentBody += tab + "EchoNode\n";

		} else {
			documentBody += tab + "TextNode\n";
		}

		for (int i = 0, size = document.numberOfChildren(); i < size; i++) {
			documentBody = createStringTree(documentBody, document.getChild(i), tab + "\t");
		}

		return documentBody;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
		
	}

}

