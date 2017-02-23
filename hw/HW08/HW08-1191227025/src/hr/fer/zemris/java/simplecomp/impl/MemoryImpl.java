/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Implementing the {@link Memory} interface. It implements the memory as
 * array of {@link Object} values..
 * 
 * @author mia
 *
 */
public class MemoryImpl implements Memory {
	/** Array representing the memory. */
	private Object[] memory;

	/**
	 * Constructor. Creates the memory of certain size.
	 * 
	 * @param size
	 *            size of the memory
	 */
	public MemoryImpl(int size) {
		memory = new Object[size];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.simplecomp.models.Memory#setLocation(int,
	 * java.lang.Object)
	 */
	@Override
	public void setLocation(int location, Object value) {
		if (location >= memory.length || location < 0) {
			throw new IndexOutOfBoundsException(
					"Memory location does not exists.");
		}
		memory[location] = value;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.simplecomp.models.Memory#getLocation(int)
	 */
	@Override
	public Object getLocation(int location) {
		if (location >= memory.length || location < 0) {
			throw new IndexOutOfBoundsException(
					"Memory location does not exists.");
		}
		return memory[location];
	}

}
