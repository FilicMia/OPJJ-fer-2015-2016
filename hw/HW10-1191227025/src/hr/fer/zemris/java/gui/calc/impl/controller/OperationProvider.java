package hr.fer.zemris.java.gui.calc.impl.controller;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.gui.calc.impl.ComponentNames;
import hr.fer.zemris.java.gui.calc.impl.operations.IBinaryOperation;
import hr.fer.zemris.java.gui.calc.impl.operations.BinaryOperations;
import hr.fer.zemris.java.gui.calc.impl.operations.IUnaryOperation;
import hr.fer.zemris.java.gui.calc.impl.operations.UnaryOperations;

/** Instances of this class control fetching the right operation by its name. */
public class OperationProvider {

	/**
	 * Names mapped to the unary operation.
	 */
	private Map<String, IUnaryOperation> unary;
	/**
	 * Names mapped to the binary operation.
	 */
	private Map<String, IBinaryOperation> binary;

	{
		unary = new HashMap<>();
		UnaryOperations unaryOp = new UnaryOperations();
		unary.putIfAbsent(ComponentNames.RECIPROCAL, unaryOp.new Reciprocal());
		unary.putIfAbsent(ComponentNames.LOG, unaryOp.new Logarithm());
		unary.putIfAbsent(ComponentNames.LN, unaryOp.new NaturalLogarithm());
		unary.putIfAbsent(ComponentNames.SIN, unaryOp.new Sinus());
		unary.putIfAbsent(ComponentNames.COS, unaryOp.new Cosinus());
		unary.putIfAbsent(ComponentNames.TAN, unaryOp.new Tangens());
		unary.putIfAbsent(ComponentNames.CTG, unaryOp.new Cotangens());
		unary.putIfAbsent(ComponentNames.TEN, unaryOp.new TenOnPower());
		unary.putIfAbsent(ComponentNames.ARCSIN, unaryOp.new ArcusSinus());
		unary.putIfAbsent(ComponentNames.ARCCOS, unaryOp.new ArcusCosinus());
		unary.putIfAbsent(ComponentNames.ARCTAN, unaryOp.new ArcusTangens());
		unary.putIfAbsent(ComponentNames.ARCCTG, unaryOp.new ArcusCotangens());
		unary.putIfAbsent(ComponentNames.CHANGESIGN, unaryOp.new ChangeSign());
		unary.putIfAbsent(ComponentNames.EXP, unaryOp.new NaturalExponent());

		binary = new HashMap<>();
		BinaryOperations binaryOp = new BinaryOperations();
		binary.putIfAbsent(ComponentNames.EQUAL, binaryOp.new Equal());
		binary.putIfAbsent(ComponentNames.ADD, binaryOp.new Addition());
		binary.putIfAbsent(ComponentNames.SUB, binaryOp.new Subtraction());
		binary.putIfAbsent(ComponentNames.MUL, binaryOp.new Multiplication());
		binary.putIfAbsent(ComponentNames.DIV, binaryOp.new Division());
		binary.putIfAbsent(ComponentNames.POW, binaryOp.new Power());
	}

	/**
	 * Returns the right unary operation instance specified by its name from the
	 * internal storage. Legible names are names of unary operations names
	 * defined in {@link ComponentsNames}.
	 * 
	 * @param name
	 *            of the operation the be returned
	 * @return unary operation of given name or {@code null} if unary operation
	 *         with given name does not exists
	 * 
	 */
	public IUnaryOperation getUnaryOperation(String name) {
		IUnaryOperation operation = unary.get(name.trim());
		return operation;
	}

	/**
	 * Returns the right binary operation instance specified by its name from
	 * the internal storage. Legible names are names of binary operations names
	 * defined in {@link ComponentsNames}.
	 * 
	 * @param name
	 *            of the operation the be returned
	  * @return binary operation of given name or {@code null} if unary operation
	 *         with given name does not exists
	 */
	public IBinaryOperation getBinaryOperation(String name) {
		IBinaryOperation operation = binary.get(name.trim());
		return operation;
	}
}
