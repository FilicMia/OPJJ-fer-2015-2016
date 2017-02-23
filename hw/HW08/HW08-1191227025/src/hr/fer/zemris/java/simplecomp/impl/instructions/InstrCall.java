package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementing the instruction {@code call} which takes 1 argument. The memory
 * address form where to read next instruction. It simulates the call of
 * subprogram. First, it saves the content of program counter to the stack and
 * than set the program counter to the given value {@code address}. It has form:
 * {@code call address}
 * 
 * @author mia
 *
 */
public class InstrCall implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 1;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Memory address from where to read next instuction.
	 */
	private int address;

	/**
	 * Constructor of instruction call.
	 * 
	 * @param arguments
	 *            arguments of instruction.
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if(!arguments.get(ZERO).isNumber()){
			throw new IllegalArgumentException("Wrong argument "+ZERO+" passed.");
		}
		
		this.address = (Integer) arguments.get(ZERO).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		int oldProgramCounter = computer.getRegisters().getProgramCounter();
		int stackTop = (Integer) computer.getRegisters()
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getMemory().setLocation(stackTop,
				Integer.valueOf(oldProgramCounter));
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX,
				Integer.valueOf(stackTop - ONE));
		computer.getRegisters().setProgramCounter(address);
		return false;
	}
}