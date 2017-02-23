/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implements the instruction halt which halts the processor.
 * 
 * @author mia
 *
 */
public class InstrHalt implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 0;

	/**
	 * Constructor of instruction halt.
	 * 
	 * @param arguments
	 *            arguments of instruction.
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if(arguments.size() != ARGC){
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.
	 * java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
