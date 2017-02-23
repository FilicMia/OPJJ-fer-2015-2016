/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * Interface for classes that should calculate median of stored objects. Stored
 * object of type T must implement {@code Comparable} interface.
 * 
 * @author Mia Filić
 * @param <T>
 *            type of stored objects
 *
 */
public interface Median<T extends Comparable<T>> {

	/**
	 * Returns the median value of stored objects wrapped in instance of
	 * {@link Optional} class.
	 * 
	 * @return median value of stored objects wrapped in instance of
	 *         {@link Optional} class
	 */
	public Optional<T> get();

}
