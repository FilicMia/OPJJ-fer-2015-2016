package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface implementing Visitors over {@link Node} objects. It will perform (usually)
 * single operation.
 * 
 * It contains methods for performing this operation on every different domain
 * object {@link Node}.
 * 
 */
public interface INodeVisitor {
	/**
	 * Method for performing certain operation on {@link TextNode}.
	 * 
	 * @param node
	 *            due to/on which the action will be performed
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Method for performing certain operation on {@link ForLoopNode}.
	 * 
	 * @param node
	 *            due to/on which the action will be performed
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Method for performing certain operation on {@link EchoNode}.
	 * 
	 * @param node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Method for performing certain operation on {@link DocumentNode}.
	 * 
	 * @param node
	 *            due to/on which the action will be performed
	 */
	public void visitDocumentNode(DocumentNode node);
}