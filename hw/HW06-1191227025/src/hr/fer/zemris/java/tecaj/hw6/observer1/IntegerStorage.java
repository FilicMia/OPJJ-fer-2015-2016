package hr.fer.zemris.java.tecaj.hw6.observer1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * The subjects preserving the single {@code int} data. Every time the state of
 * subject is changed it performs the subscribed actions which could be added or
 * removed at any time.
 * 
 * @author Mia Filić
 *
 */
public class IntegerStorage {
	/** Stored value. */
	private int value;
	/** List of subscribed actions. */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructor with one argument. Sets the property value on the value
	 * passed as argument.
	 * 
	 * @param initialValue
	 *            value on which the {@code value} will be set.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Subscribes the passed {@code observer}. If not jet added, adds the
	 * {@code observer} to internally stored list of {@code observers}.
	 * Otherwise, does nothing.If {@code null} observer occurs, it is considered
	 * irrelevant, so its not added, but no exception is thrown.
	 * 
	 * @param observer
	 *            observer to be subscribed
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer == null) {
			return;
		}
		if (observers == null) {
			observers = new ArrayList<>();
			observers.add(observer);

		} else {
			for (IntegerStorageObserver integerStorageObserver : observers) {
				if (integerStorageObserver.equals(observer)) {
					return;
				}
			}
			observers.add(observer);
		}

	}

	/**
	 * Desubscribes the observer if subscribed.
	 * 
	 * @param observer
	 *            to be desubscribed.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Desubscribes all {@code observers}.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Returns the value stored.
	 * 
	 * @return value stored
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Changes the value stored on passed argument {@code value}. It also
	 * notifies all prescribed observers about the change.
	 * 
	 * @param value
	 *            on which to change the stored value
	 */
	public void setValue(int value) {

		if (this.value != value) {
			this.value = value;

			if (observers != null) {
				Iterator<IntegerStorageObserver> iter = observers.iterator();
				IntegerStorageObserver observer = null;
				
				while(iter.hasNext()){
					observer = iter.next();
					observer.valueChanged(this);
					if(observer instanceof DoubleValue){
						if(((DoubleValue) observer).getLeftUntilDeregistration() <= 0){
							iter.remove();
						}
					}
				}
			}
		}
	}

}