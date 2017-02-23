package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementing the instruction {@code pop} which takes 1 argument.The index of
 * the register. It pops the {@code Object} from the stack and saves in the
 * register with given index. It has form: {@code pop rX}
 * 
 * @author mia
 *
 */
public class InstrPop implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 1;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Index of the register to where to save pop object.
	 */
	private int indexRegister;

	/**
	 * Constructor of instruction pop.
	 * 
	 * @param arguments
	 *            arguments of the instruction
	 */
	public InstrPop(List<InstructionArgument> arguments) {
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
		int stackTop = (Integer) computer.getRegisters()
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);
		Object value1 = computer.getMemory().getLocation(stackTop + ONE);

		computer.getRegisters().setRegisterValue(indexRegister, value1);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX,
				Integer.valueOf(stackTop + ONE));
		return false;
	}

}
