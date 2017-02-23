/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Collection of finite number of first primes. The number of first prime
 * numbers that will be stored in collection is passed trough constructor.
 * Primes are not fiscally stored, Specific number is calculated on demand.
 * 
 * @author Mia Filić
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/** Number of primes stored in collection. */
	private final int n;

	/**
	 * Constructor accepting a number of consecutive primes that must be in this
	 * collection.
	 * 
	 * @param n
	 *            finite number, number of primes that will be stored in
	 *            collection.
	 */
	public PrimesCollection(int n) {
		this.n = n;
	}

	@Override
	public Iterator<Integer> iterator() {

		return new IteratorImpl();
	}

	/**
	 * Implements the iterator trough the Collection.
	 */
	private class IteratorImpl implements Iterator<Integer> {

		/** Current element in iterator. */
		private Integer current = 2;

		/** Which prime number is current prime number. */
		private int index = 1;

		/**
		 * Checks if the end of collection is reached.
		 * 
		 * @return {@code true} if element after {@code current} exists,
		 *         {@code false} otherwise.
		 * 
		 */
		@Override
		public boolean hasNext() {

			if (PrimesCollection.this.n < index) {
				return false;
			}
			return true;
		}

		/**
		 * {@inheritDoc}
		 * 
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("End is reached.");
			}

			int number = current + 1;
			while (!isPrime(number)) {
				++number;
			}
			
			++index;
			int ret = current;
			current = number;
			return ret;

		}

		/**
		 * Supplementary method checks if given number is prime.
		 * 
		 * @param number
		 *            natural number to be check for primacy.
		 * @return {@code true} if number is prime, {@code false} otherwise
		 */

		private boolean isPrime(int number) {

			if (number < 0) {
				throw new IllegalArgumentException(
						"Number must be natural number.");
			}

			for (int i = 2; i <= Math.sqrt(number); ++i) {
				if (number % i == 0) {
					return false;
				}
			}

			return true;
		}
	}
}
