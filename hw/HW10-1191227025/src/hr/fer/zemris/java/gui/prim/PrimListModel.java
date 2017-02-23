/**
 * 
 */
package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class implementing the interface {@link ListModel}. It stores elements of
 * type {@link Integer}, prime numbers. It has the function {@link #next()}
 * which adds the next prime number in list.
 * 
 * @author mia
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/** List storing prime numbers. */
	private List<Integer> primes = new ArrayList<>();

	/** List storing current listeners of the {@link PrimListModel}. */
	private List<ListDataListener> listeners = new ArrayList<>();

	/** List of listeners that want to be removed. */
	private List<ListDataListener> removedListeners = new ArrayList<>();

	/** List of listeners that want to be added. */
	private List<ListDataListener> addedListeners = new ArrayList<>();

	/** Class used for fetching the next prime number. */
	private Prime prime = new Prime(1);

	/**
	 * Constructor. The initial number in the primes list is 1.(it is not prime,
	 * but the exercise said that it should be here.)
	 */
	public PrimListModel() {
		primes.add(1);
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener listener) {
		addedListeners.add(listener);
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		removedListeners.add(listener);

	}

	/**
	 * Adding the next prime number to the list. After adding, informs the
	 * observers about it.
	 * 
	 * @param prime
	 */
	public void next() {
		int pos = primes.size();
		primes.add(prime.next());

		listeners.removeAll(removedListeners);
		listeners.addAll(addedListeners);

		ListDataEvent event = new ListDataEvent(this,
				ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener listener : listeners) {
			listener.intervalAdded(event);
		}
	}

	/**
	 * Class used for fetching the prime numbers greater than the prime
	 * currently store in class. Every call of the {@link #next()}} changes the
	 * value of {@code currentPrim} to the first prime number grater then
	 * current value.
	 * 
	 */
	private class Prime {

		/** Currently stores prime number. */
		int currentPrim;

		/**
		 * Constructor.
		 * 
		 * @param prime
		 *            prime number which will be initially stored in class.
		 */
		public Prime(int prime) {
			currentPrim = prime;
		}

		/**
		 * Fetching and storing the next prime number.
		 * 
		 * @return next prime number.
		 */
		public int next() {
			for (int number = currentPrim + 1;; ++number) {
				if (isPrime(number)) {
					currentPrim = number;
					return number;
				}
			}
		}

		/**
		 * Checks it {@code number} is prime or not.
		 * 
		 * @param number
		 *            number to be checked for being prime
		 * @return {@code true} if number is prime, {@code false} otherwise.
		 */
		private boolean isPrime(int number) {
			int sqrtNumber = (int) Math.sqrt(number);
			for (int i = 2; i <= sqrtNumber; ++i) {
				if (number % i == 0)
					return false;
			}

			return true;
		}

	}

}
