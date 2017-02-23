/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementing the {@link Computer} interface.
 * 
 * @author mia
 *
 */
public class ComputerImpl implements Computer {
	/** Memory of the computer. */
	private Memory memory;
	/** Registers of the computer. */
	private Registers registers;

	/**
	 * Constructs the instance of implementation of {@link ComputerImpl} with
	 * certain memory capacity and certain number of register.
	 * 
	 * @param regsLen
	 *            number of registers in computer
	 * @param size
	 *            memory capacity of the computer
	 */
	public ComputerImpl(int regsLen, int size) {
		memory = new MemoryImpl(size);
		registers = new RegistersImpl(regsLen);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.simplecomp.models.Computer#getRegisters()
	 */
	@Override
	public Registers getRegisters() {
		return registers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.simplecomp.models.Computer#getMemory()
	 */
	@Override
	public Memory getMemory() {
		return memory;
	}

}
