package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implements the {@link Registers} interface. Represents the registers with
 * ordinary array of objects.
 * 
 * @author mia
 *
 */
public class RegistersImpl implements Registers {
	/** Represents the registers of the processor. */
	private Object[] registers;

	/**
	 * The address of the next instruction. Instructions are saved in instance
	 * of {@link MemoryImpl} represented by field array.
	 */
	private int programCounter;

	/** Flag of the processor. */
	private boolean flag;

	/**
	 * Constructor creating the given number of registers.
	 * 
	 * @param regsLen
	 *            the number of registers that will be available to processor.
	 */
	public RegistersImpl(int regsLen) {
		// I think that there must be at least STACK_REGISTER_INDEX+1 registers
		// because the STACK_POINTER is in STACK_REGISTER_INDEX(=15) and it must exists
		registers = new Object[regsLen >= STACK_REGISTER_INDEX ? regsLen
				: (STACK_REGISTER_INDEX + 1)];
	}

	@Override
	public Object getRegisterValue(int index) {
		if (index >= registers.length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return registers[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		if (index >= registers.length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		registers[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
