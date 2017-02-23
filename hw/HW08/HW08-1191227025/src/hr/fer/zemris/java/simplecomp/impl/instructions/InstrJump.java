/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction {@code jump} which takes 1 argument. The memory
 * location form where to read next instruction.It does not allow indirect
 * addressing. It has form: {@code jump location}
 * 
 * @author mia
 *
 */
public class InstrJump implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 1;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Memory location to where to "jump".
	 */
	private int location;

	/**
	 * Constructor of instruction jump.
	 * 
	 * @param arguments
	 *            arguments of instruction.
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		this.location = (Integer)arguments.get(ZERO).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(
				location);
		return false;
	}
}