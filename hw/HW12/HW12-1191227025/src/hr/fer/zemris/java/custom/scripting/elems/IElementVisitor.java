package hr.fer.zemris.java.custom.scripting.elems;


/**
 * Interface implementing Visitors over {@link Element} objects. It will perform
 * (usually) single operation.
 * 
 * It contains methods for performing this operation on every different domain
 * object {@link Element}.
 * 
 */
public interface IElementVisitor {
	/**
	 * Method for performing certain operation on {@link ElementConstantDouble}.
	 * 
	 * @param element
	 *            due to/on which the action will be performed
	 */
	public void visitElementConstantDouble(ElementConstantDouble element);

	/**
	 * Method for performing certain operation on {@link ElementConstantInteger}
	 * .
	 * 
	 * @param element
	 *            due to/on which the action will be performed
	 */
	public void visitElementConstantInteger(ElementConstantInteger element);

	/**
	 * Method for performing certain operation on {@link ElementFunction} .
	 * 
	 * @param element
	 *            due to/on which the action will be performed
	 */
	public void visitElementFunction(ElementFunction element);

	/**
	 * Method for performing certain operation on {@link ElementOperator}.
	 * 
	 * @param element
	 *            due to/on which the action will be performed
	 */
	public void visitElementOperator(ElementOperator element);

	/**
	 * Method for performing certain operation on {@link ElementString}.
	 * 
	 * @param element
	 *            due to/on which the action will be performed
	 */
	public void visitElementString(ElementString element);

	/**
	 * Method for performing certain operation on {@link ElementVariable}.
	 * 
	 * @param element
	 *            due to/on which the action will be performed
	 */
	public void visitElementVariable(ElementVariable element);


}
