package hr.fer.zemris.java.custom.scripting.elems.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents the operation that accepts double values.
 * 
 * @author mia
 *
 */
public interface IFunction {
	/**
	 * Performs this operation on the given value.
	 * @param stack 
	 * @param context 
	 * 
	 * @param arguments
	 *            value on which the operation will be performed.
	 */
	public void calcualte(Stack<Object> stack, RequestContext context);
}