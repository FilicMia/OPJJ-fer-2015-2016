/**
 * 
 */
package hr.fer.zemris.java.simplecomp.simulator;

import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Program that simulates the usage of {@link ComputerImpl} class.
 * 
 * @author mia
 *
 */
public class Simulator {
	/** Relative path to implementation of instructions. */
	private static final String INSTRUCTION_IMPL = "hr.fer.zemris.java.simplecomp.impl.instructions";

	/**
	 * Method called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments.
	 */
	public static void main(String[] args) {
		// Create computer with 256 memory locations and 256 registers
		Computer comp = new ComputerImpl(256, 256);
		// Create object whose purpose is creation of instructions
		InstructionCreator creator = new InstructionCreatorImpl(
				INSTRUCTION_IMPL);
		// Load computer memory with program written in file; creation of
		// instructions is
		// done with class specified for that
		if (args.length < 1) {
			System.err.println(
					"Expected one command line argument: path to the program to be executed.");
			System.exit(-1);
		}
		if (!Files.exists(Paths.get(args[0]))) {
			System.err.println("File does not exists!");
			System.exit(-1);
		}
		
		// Create ExecutionUnit
				ExecutionUnit exec = new ExecutionUnitImpl();
		try {
			ProgramParser.parse(args[0], comp, creator);
			// Execute the program
			exec.go(comp);
		} catch (Exception e) {
			System.err.println("Error \"" + e.getMessage()
					+ "\" occured while executing program from: " + args[0]);
			e.printStackTrace(System.err);
			System.exit(-1);
		}
		

	}

}
