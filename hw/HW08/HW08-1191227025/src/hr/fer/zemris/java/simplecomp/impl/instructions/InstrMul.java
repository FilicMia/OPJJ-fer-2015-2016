package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction which takes 3 arguments. Arguments are registers
 * indexes. It multiplies values in last two and saves the result in the
 * register indicated with the first index.
 *
 */
public class InstrMul implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 3;
	/**
	 * Index of register where to store the result.
	 */
	private int indexRegister1;

	/**
	 * Index of register where the first muliplier is.
	 */
	private int indexRegister2;

	/**
	 * Index of register where the second muliplier is.
	 */
	private int indexRegister3;

	/**
	 * Constructor of instruction mul.
	 * 
	 * @param arguments
	 *            arguments of the instruction
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil
					.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegister1 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegister2 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegister3 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegister2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegister3);
		computer.getRegisters().setRegisterValue(indexRegister1,
				Integer.valueOf((Integer) value1 * (Integer) value2));
		return false;
	}
}
