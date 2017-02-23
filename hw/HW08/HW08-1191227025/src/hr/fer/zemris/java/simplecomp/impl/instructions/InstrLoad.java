package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction {@code load} which takes 2 arguments. First is
 * index of the register, and second is memory location from where to take the
 * element which will be stored in given register. It does not allow indirect
 * addressing. It has form: {@code load rX, memoryAddress}
 * 
 * @author mia
 *
 */
public class InstrLoad implements Instruction {
	/** Number of arguments of instruction. */
	private final static int ARGC = 2;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;
	/** Number two. */
	public static final int TWO = 2;

	/**
	 * Index of the register where to load the object.
	 */
	private int indexRegister;

	/**
	 * Integer value of memory address from where to load the object.
	 */
	private int memoryAddress;

	/**
	 * Constructor of instruction load.
	 * 
	 * @param arguments
	 *            arguments of the instruction
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(ZERO).isRegister() || RegisterUtil
				.isIndirect((Integer) arguments.get(ZERO).getValue())) {
			throw new IllegalArgumentException(
					"Type mismatch for argument " + ZERO + "!");
		}
		this.indexRegister = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(ZERO).getValue());

		if (!arguments.get(ONE).isNumber()) {
			throw new IllegalArgumentException(
					"Type mismatch for argument " + ONE + "!");
		} else {
			this.memoryAddress = ((Integer) arguments.get(ONE).getValue())
					.intValue();
		}
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(indexRegister,
				computer.getMemory().getLocation(memoryAddress));
		return false;
	}

}
