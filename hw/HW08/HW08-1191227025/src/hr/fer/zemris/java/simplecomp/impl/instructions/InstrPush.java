package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementing the instruction {@code push} which takes 1 argument.The index of
 * the register which content will be pushed onto stack. It has form:
 * {@code push rX}
 * 
 * @author mia
 *
 */
public class InstrPush implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 1;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Index of the register whose content will be saved on the stack.
	 */
	private int indexRegister;

	/**
	 * Constructor of instruction push.
	 * 
	 * @param arguments
	 *            arguments of the instruction
	 */
	public InstrPush(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (!arguments.get(ZERO).isRegister() || RegisterUtil
				.isIndirect((Integer) arguments.get(ZERO).getValue())) {
			throw new IllegalArgumentException(
					"Type mismatch for argument " + ZERO + "!");
		}
		indexRegister = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(ZERO).getValue());

	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegister);
		int stackTop = (Integer)computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		
		computer.getMemory().setLocation(stackTop,
				value1);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX,Integer.valueOf(stackTop - ONE));
		return false;
	}

}
