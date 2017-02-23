package hr.fer.zemris.java.custom.scripting.elems.functions;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class internally holding names of all available functions.
 * 
 * @author mia
 *
 */
public class FunctUtil {

	/**
	 * Map of all suported functions mapped to its names.
	 */
	private static Map<String, IFunction> functions = null;
	/**
	 * Name associated with function sin.
	 */
	public static final String SIN = "sin";

	/**
	 * Name associated with function decfmt(x,f) which formats decimal number
	 * using given format f which is compatible with DecimalFormat; produces a
	 * string. X can be integer, double or string representation of a number.
	 * Conceptually, equals to: f = pop(), x = pop(), r = decfmt(x,f), push(r).
	 */
	public static final String DECFMT = "decfmt";
	/**
	 * Name associated with function dup which duplicates current top value from
	 * stack. Conceptually, equals to: x = pop(), push(x), push(x).
	 */
	public static final String DUP = "dup";
	/**
	 * Name associated with function swap which replaces the order of two
	 * topmost items on stack. Conceptually, equals to: a = pop(), b = pop(),
	 * push(a), push(b).
	 */
	public static final String SWAP = "swap";
	/**
	 * Name associated with function setMimeType which takes string x and calls
	 * requestContext.setMimeType(x). Does not produce any result.
	 */
	public static final String SETMIMETYPE = "setMimeType";
	/**
	 * Name associated with function paramGet which obtains from requestContext
	 * parameters map a value mapped for name and pushes it onto stack. If there
	 * is no such mapping, it pushes instead defValue onto stack. Conceptually,
	 * equals to: dv = pop(), name = pop(), value=reqCtx.getParam(name),
	 * push(value==null ? defValue : value).
	 */
	public static final String PARAMGET = "paramGet";
	/**
	 * Name associated with function pparamGet which does same as paramGet but
	 * reads from requestContext persistant parameters map. .
	 */
	public static final String PPARAMGET = "pparamGet";
	/**
	 * Name associated with function pparamSet(value, name) which stores a value
	 * into requestContext persistant parameters map. Conceptually, equals to:
	 * name = pop(), value = pop(), reqCtx.setPerParam(name, value).
	 */
	public static final String PPARAMSET = "pparamSet";
	/**
	 * Name associated with function pparamDel(name) which removes association
	 * for name from requestContext persistentParameters map..
	 */
	public static final String PPARAMDEL = "pparamDel";
	/**
	 * Name associated with function tparamGet(name, defValue) which same as
	 * paramGet but reads from requestContext temporaryParameters map.
	 */
	public static final String TPARAMGET = "tparamGet";

	/**
	 * Name associated with function tparamSet(value, name) which stores a value
	 * into requestContext temporaryParameters map. Conceptually, equals to:
	 * name = pop(), value = pop(), reqCtx.setTmpParam(name, value).
	 */
	public static final String TPARAMSET = "tparamSet";

	/**
	 * Name associated with function tparamDel(name)which removes association
	 * for name from requestContext temporaryParameters map.
	 */
	public static final String TPARAMDEL = "tparamDel";

	/**
	 * Creates the available functions and stores it into map.
	 * 
	 * @param functionMap
	 *            map where functions will be stored.
	 */
	public static void setFunctions(Map<String, IFunction> functionMap) {
		if (functionMap == null) {
			functionMap = new HashMap<>();
		} else {
			functionMap.clear();
		}

		if (functions != null) {
			functionMap.putAll(functions);
			return;
		}

		functionMap.putIfAbsent(DECFMT, new IFunction() {

			public static final int argsNo = 2;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Object f = stack.pop();
				Object x = stack.pop();
				DecimalFormat decfmt = new DecimalFormat(f.toString());
				stack.push(decfmt.format(x));
			}
		});
		functionMap.putIfAbsent(SIN, new IFunction() {

			public static final int argsNo = 1;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Double x = Double.parseDouble(stack.pop().toString());
				Double result = Math.sin(Math.toRadians(x));
				stack.push(result);
			}
		});
		functionMap.putIfAbsent(DUP, new IFunction() {

			public static final int argsNo = 1;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Object x = stack.pop();
				stack.push(x);
				stack.push(x);
			}
		});
		functionMap.putIfAbsent(SWAP, new IFunction() {

			public static final int argsNo = 2;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Object a = stack.pop();
				Object b = stack.pop();
				stack.push(a);
				stack.push(b);
			}
		});
		functionMap.putIfAbsent(SETMIMETYPE, new IFunction() {

			public static final int argsNo = 1;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Object x = stack.pop();
				context.setMimeType(x.toString());
			}
		});
		functionMap.putIfAbsent(DECFMT, new IFunction() {

			public static final int argsNo = 2;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Object f = stack.pop();
				Object x = stack.pop();
				DecimalFormat decfmt = new DecimalFormat(f.toString());
				stack.push(decfmt.format(x));
			}
		});
		functionMap.putIfAbsent(PARAMGET, new IFunction() {

			public static final int argsNo = 2;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Object dv = stack.pop();
				String name = stack.pop().toString();
				stack.push(context.getParameter(name) == null ? dv
						: context.getParameter(name));
			}
		});
		functionMap.putIfAbsent(PPARAMGET, new IFunction() {

			public static final int argsNo = 2;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Object dv = stack.pop();
				String name = stack.pop().toString();

				Object value = context.getPersistentParameter(name);
				stack.push(value == null ? dv : value);
			}
		});

		functionMap.putIfAbsent(PPARAMSET, new IFunction() {

			public static final int argsNo = 2;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				String name = stack.pop().toString();
				String value = stack.pop().toString();

				context.setPersistentParameter(name, value);
			}
		});

		functionMap.putIfAbsent(PPARAMDEL, new IFunction() {

			public static final int argsNo = 1;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				String name = stack.pop().toString();

				context.removePersistentParameter(name);
			}
		});
		functionMap.putIfAbsent(TPARAMGET, new IFunction() {

			public static final int argsNo = 2;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				Object dv = stack.pop();
				String name = stack.pop().toString();

				Object value = context.getTemporaryParameter(name);
				stack.push(value == null ? dv : value);
			}
		});
		functionMap.putIfAbsent(TPARAMSET, new IFunction() {

			public static final int argsNo = 2;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				String name = stack.pop().toString();
				String value = stack.pop().toString();

				context.setTemporaryParameter(name, value);
			}
		});
		functionMap.putIfAbsent(TPARAMDEL, new IFunction() {

			public static final int argsNo = 1;

			@Override
			public void calcualte(Stack<Object> stack, RequestContext context) {
				if (stack.size() < argsNo) {
					throw new IllegalArgumentException("There is only "
							+ stack.size()
							+ " elements on the stack, but needed " + argsNo);
				}
				String name = stack.pop().toString();

				context.removeTemporaryParameter(name);
			}
		});

		functions = new HashMap<>(functionMap);

	}

	/** Plus sign. */
	public static final String PLUSSIGN = "+";
	/** Minus sign. */
	public static final String MINUSSIGN = "-";
	/** Mul sign. */
	public static final String MULSIGN = "*";
	/** Div sign. */
	public static final String DIVSIGN = "/";

}
