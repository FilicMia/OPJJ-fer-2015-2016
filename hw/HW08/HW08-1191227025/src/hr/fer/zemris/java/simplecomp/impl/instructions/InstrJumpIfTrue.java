package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction {@code jumpIfTrue} which takes 1 argument. The
 * memory location form where to read next instruction if flag of the processor
 * is set to {@code true}, otherwise it does nothing.It does not allow indirect
 * addressing. It has form: {@code jumpIfTrue location}
 * 
 * @author mia
 *
 */
public class InstrJumpIfTrue implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 1;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Memory location to where to "jump" if processor flag is set to
	 * {@code true}.
	 */
	private int location;

	/**
	 * Constructor of instruction jumpIfTrue.
	 * 
	 * @param arguments
	 *            arguments of instruction.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		this.location = (Integer) arguments.get(ZERO).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(location);
		}
		return false;
	}
}