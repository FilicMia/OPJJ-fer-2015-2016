package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction {@code iinput} which takes 1 argument, memory
 * address to where store the {@code Integer} value read for standard input. It
 * has form: {@code iinput address}
 * 
 * @author mia
 *
 */
public class InstrIinput implements Instruction {
	/** Number of arguments of instruction. */
	private final static int ARGC = 1;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;

	/**
	 * Address where to store read object.
	 */
	private int address;

	/**
	 * Constructor of instruction iinput.
	 * 
	 * @param arguments
	 *            arguments of instruction.
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		if (!arguments.get(ZERO).isNumber()) {
			throw new IllegalArgumentException(
					"Type mismatch for argument " + ZERO + "!");
		}
		this.address = (Integer) arguments.get(ZERO).getValue();

	}

	@Override
	public boolean execute(Computer computer) {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in)));
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e) {
			computer.getRegisters().setFlag(false);
			return false;
		}
		input = input.trim();
		
		if(input.matches("-?[0-9]+")){
			computer.getMemory().setLocation(address, Integer.parseInt(input));
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}