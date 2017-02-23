package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction {@code testEquals} which takes 2 arguments.
 * Arguments are registers indexes.By execution, if values in registers are
 * equal, it sets the computer flag on true, false otherwise.
 *
 */
public class InstrTestEquals implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 2;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Index of first register .
	 */
	private int indexRegister1;

	/**
	 * Index of second register.
	 */
	private int indexRegister2;

	/**
	 * Constructor of instruction {@code testEquals}.
	 * 
	 * @param arguments
	 *            arguments of the instruction
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		for (int i = 0; i < ARGC; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil
					.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegister1 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(ZERO).getValue());
		this.indexRegister2 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(ONE).getValue());
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegister1);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegister2);
		if(value1.equals(value2)){
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}