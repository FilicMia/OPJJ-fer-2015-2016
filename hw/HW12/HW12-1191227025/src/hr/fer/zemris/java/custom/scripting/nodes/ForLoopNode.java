package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * (Class) Node representing a single for-loop construct. It inherits form
 * <code>Node</code>
 * 
 * @author mia
 *
 */
public class ForLoopNode extends Node {
	/**
	 * Read-only property, representing the variable on which the for-loop is
	 * iterated.
	 */
	private ElementVariable variable;
	/**
	 * Read-only property, representing the start expression of for loop
	 * iterations.
	 */
	private Element startExpression;

	/**
	 * Read-only property, representing the end expression of for loop
	 * iterations. When endExpression is reached, for-loop should stop
	 * iterating.
	 */
	private Element endExpression;

	/**
	 * Elective(can be <code>null</code>), read-only property representitng the
	 * step of iterations.
	 */
	private Element stepExpression;

	/**
	 * Minimum number of no-null elements that instance of ForLoopNode can have.
	 */
	public final static int minElements = 3;

	/**
	 * Maximum number of no-null elements that instance of ForLoopNode can have.
	 */
	public final static int maxElements = 4;

	/**
	 * Constructor which sets read-only property of ForLoopNode.
	 * 
	 * @param variable
	 *            on which the for-loop iterations will go;
	 *            <code>ElementVariable</code>
	 * @param startExpression
	 *            of for-loop iterations; <code>Element</code>
	 * @param endExpression
	 *            of for-loop iterations; <code>Element</code>
	 * @param stepExpression
	 *            represents the step of iterations; <code>Element</code>
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
			Element endExpression, Element stepExpression) {
		if (variable == null || startExpression == null
				|| endExpression == null) {
			throw new IllegalArgumentException(
					"Only the 4-th argument can be null.");
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;

	}

	/**
	 * Gets the read-only property <code>variable</code>
	 * 
	 * @return property <code>variable</code>; <code>ElementVariable</code>
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Gets the read-only property <code>startExpression</code>
	 * 
	 * @return property <code>startExpression</code>; <code>Element</code>
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Gets the read-only property <code>endExpression</code>
	 * 
	 * @return property <code>endExpression</code>; <code>Element</code>
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Gets the read-only property <code>stepExpression</code>
	 * 
	 * @return property <code>stepExpression</code>; <code>Element</code>
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {

		return "{$ FOR " + variable.toString() + " "
				+ startExpression.toString() + " " + endExpression.toString()
				+ " "
				+ (stepExpression == null ? "" : stepExpression.toString())
				+ " " + "$}";
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);

	}
}
