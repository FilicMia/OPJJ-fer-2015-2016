package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementing the instruction {@code ret} which takes 0 arguments. It pops the
 * address from the stack and saves in the program counter. It
 * has form: {@code ret}
 * 
 * @author mia
 *
 */
public class InstrRet implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 0;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Constructor of instruction ret.
	 * 
	 * @param arguments
	 *            arguments of the instruction
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}

	}

	@Override
	public boolean execute(Computer computer) {
		int stackTop = (Integer) computer.getRegisters()
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);
		int value1 = ((Integer) computer.getMemory()
				.getLocation(stackTop + ONE));

		computer.getRegisters().setProgramCounter(value1);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX,
				Integer.valueOf(stackTop + ONE));
		return false;
	}

}
