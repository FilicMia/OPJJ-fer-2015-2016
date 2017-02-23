/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction {@code echo} which takes 1 argument. The index
 * of register or indirect memory address form where to read the object and
 * print it on standard input. It allows also indirect addressing. It has form:
 * {@code echo rX}
 * 
 * @author mia
 *
 */
public class InstrEcho implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 1;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Location of the object to be printed on standard input.
	 */
	private int location;

	/**
	 * Indicates if indirect addressing is used.
	 */
	private boolean indirect = false;

	/**
	 * Constructor of instruction echo.
	 * 
	 * @param arguments
	 *            arguments of instruction.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		if (!arguments.get(ZERO).isRegister()) {
			throw new IllegalArgumentException(
					"Type mismatch for argument " + ZERO + "!");
		}
		this.location = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(ZERO).getValue());
		if (RegisterUtil.isIndirect((Integer) arguments.get(ZERO).getValue())) {
			this.location = (Integer)arguments.get(ZERO).getValue();
			this.indirect = true;
		}
	}

	@Override
	public boolean execute(Computer computer) {
		if (!indirect) {
			System.out.print(
					computer.getRegisters().getRegisterValue(location));
		} else {
			int memoryLocation = RegisterUtil.getMemoryLocation(location, computer);
			System.out.print(computer.getMemory().getLocation(memoryLocation));
		}
		return false;
	}
}
