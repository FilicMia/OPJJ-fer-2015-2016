package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * Implements the execution unit of the computer, implements
 * {@link ExecutionUnit}. It has only one public method.
 * 
 * @author mia
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);

		int programCounter;
		Instruction instr;

		while (true) {
			programCounter = computer.getRegisters().getProgramCounter();
			if (computer.getMemory()
					.getLocation(programCounter) instanceof Instruction) {
				instr = (Instruction) computer.getMemory()
						.getLocation(programCounter);
				computer.getRegisters().setProgramCounter(programCounter + 1);
				if (instr.execute(computer)) {
					break;
				}
			} else {
				return false;
			}
		}
		return true;
	}

}
