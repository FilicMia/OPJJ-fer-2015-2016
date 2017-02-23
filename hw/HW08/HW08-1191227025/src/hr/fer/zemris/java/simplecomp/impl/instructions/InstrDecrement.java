package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction {@code decrement} which takes 1 argument. The
 * index of register form where to read the object decrement it by one. It does
 * not allow indirect addressing. It has form: {@code decrement rX}
 * 
 * @author mia
 *
 */
public class InstrDecrement implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 1;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Index of register whose value will be decremented.
	 */
	private int indexRegister;

	/**
	 * Constructor of instruction decrement.
	 * 
	 * @param arguments
	 *            arguments of instruction.
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		if (!arguments.get(ZERO).isRegister()) {
			throw new IllegalArgumentException(
					"Type mismatch for argument " + ZERO + "!");
		}
		this.indexRegister = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(ZERO).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object registerValue = computer.getRegisters()
				.getRegisterValue(indexRegister);

		computer.getRegisters().setRegisterValue(indexRegister,
				Integer.valueOf((Integer) registerValue - ONE));

		return false;
	}
}
