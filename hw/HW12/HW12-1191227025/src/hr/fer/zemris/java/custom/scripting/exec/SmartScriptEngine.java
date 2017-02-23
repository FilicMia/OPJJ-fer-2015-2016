package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.elems.IElementVisitor;
import hr.fer.zemris.java.custom.scripting.elems.functions.FunctUtil;
import hr.fer.zemris.java.custom.scripting.elems.functions.IFunction;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementing engine which executes smart script.
 * 
 * @author mia
 *
 */
public class SmartScriptEngine {

	/**
	 * Class representing entire document. Each part of the document is its
	 * direct or indirect child.
	 */
	private DocumentNode documentNode;
	/** COnrext of the request. */
	private RequestContext requestContext;
	/** Multistack used in executing the smart script. */
	private ObjectMultistack multistack = new ObjectMultistack();

	/** Extension of smart script files. */
	public static final String EXTENSION = "smscr";

	/**
	 * Implementation of {@link INodeVisitor} It is used in execution the smart
	 * script reconstructing the information included in {@link Node} instances.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err.println("Streaming the text node " + node.getClass()
						+ " call for exception.");
				System.exit(-1);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().getName();

			String startExpression = node.getStartExpression().asText();
			String endExpression = node.getEndExpression().asText();
			String step = node.getStepExpression().asText();
			step = step == null ? "1" : step;

			multistack.push(variable, new ValueWrapper(startExpression));
			int numOfChildren = node.numberOfChildren();
			while (multistack.peek(variable).numCompare(endExpression) <= 0) {
				for (int i = 0; i < numOfChildren; ++i) {
					node.getChild(i).accept(this);
				}
				multistack.peek(variable).increment(step);
			}
			
			multistack.pop(variable);

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			ElementExecutionVisitor executionVisitor = new ElementExecutionVisitor();

			for (Element element : node.getElements()) {
				// System.out.println(executionVisitor.stack);
				element.accept(executionVisitor);
			}

			Stack<Object> stack = executionVisitor.getStack();
			ArrayList<Object> reverse = new ArrayList<>(stack);
			for (int i = 0, len = reverse.size(); i < len; ++i) {
				try {
					requestContext.write(reverse.get(i).toString());
				} catch (IOException ignorable) {
					System.err.println("Streaming the text " + node.getClass()
							+ " node call for exception.");
					System.exit(-1);
				}
			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int numOfChildren = node.numberOfChildren();

			for (int i = 0; i < numOfChildren; ++i) {
				node.getChild(i).accept(this);
			}
			try {
				requestContext.write("\n");
			} catch (IOException e) {
				System.err.println("Streaming the text " + node.getClass()
						+ " node call for exception.");
				System.exit(-1);
			}

		}
	};

	/**
	 * Constructor. Constructs the instance of {@link SmartScriptEngine}.
	 * 
	 * @param documentNode
	 *            root node of the document parsed form smatr script
	 * @param requestContext
	 *            the context to which output stream the document execution will
	 *            be performed.
	 */
	public SmartScriptEngine(DocumentNode documentNode,
			RequestContext requestContext) {
		Objects.requireNonNull(requestContext);
		Objects.requireNonNull(documentNode);

		this.requestContext = requestContext;
		this.documentNode = documentNode;
	}

	/**
	 * Executes the document associated with {@link #documentNode} property.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * Class implementing the visitor over variety instances of {@link Element}
	 * class.
	 * 
	 * @author mia
	 *
	 */
	public class ElementExecutionVisitor implements IElementVisitor {

		/**
		 * Internal stack. Help utility for performing the operation associated
		 * with this visitor.
		 */
		private Stack<Object> stack;

		/**
		 * Internal map storing functions available.
		 */
		private Map<String, IFunction> functionMap;

		/**
		 * Constructor.
		 */
		public ElementExecutionVisitor() {
			stack = new Stack<>();
			functionMap = new HashMap<>();
			FunctUtil.setFunctions(functionMap);
		}

		@Override
		public void visitElementConstantDouble(ElementConstantDouble element) {
			stack.push(element.getValue());
		}

		@Override
		public void visitElementConstantInteger(
				ElementConstantInteger element) {
			stack.push(element.getValue());

		}

		@Override
		public void visitElementOperator(ElementOperator element) {
			String operator = element.getSymbol().trim();
			if (stack.size() < 2) {
				System.err
						.println("There is error in performimng an operation.");
				System.exit(-1);
			}

			ValueWrapper leftOp = new ValueWrapper(stack.pop());
			ValueWrapper rightOp = new ValueWrapper(stack.pop());

			switch (operator) {
			case (FunctUtil.PLUSSIGN):
				leftOp.increment(rightOp.getValue());
				break;
			case (FunctUtil.MINUSSIGN):
				leftOp.decrement(rightOp.getValue());
				break;
			case (FunctUtil.MULSIGN):
				leftOp.multiply(rightOp.getValue());
				break;
			case (FunctUtil.DIVSIGN):
				leftOp.divide(rightOp.getValue());
				break;
			default:
				System.err.println("Operation not supported: " + operator);
				System.exit(-1);
			}

			stack.push(leftOp.getValue());

		}

		@Override
		public void visitElementString(ElementString element) {
			stack.push(element.asText());

		}

		@Override
		public void visitElementVariable(ElementVariable element) {
			ValueWrapper wrapper = multistack.peek(element.getName());
			stack.push(wrapper.getValue());

		}

		@Override
		public void visitElementFunction(ElementFunction element) {
			functionMap.get(element.getName()).calcualte(stack,
					SmartScriptEngine.this.requestContext);
			;
		}

		/**
		 * Returns the stack held at the moment.
		 * 
		 * @return the stack held at the moment.
		 */
		public Stack<Object> getStack() {
			return stack;
		}

	}

}
