/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Collection of objects which knows how to calculate median of internally
 * stored objects. Stored objects must implements {@link Comparable} so the
 * natural ordering is defined. If number of objects stored is odd the get()
 * method returns median element. If user provides an even number of elements,
 * the {@code get()} method returns the smaller from the two elements which
 * would usually be used to calculate median element.
 * 
 * @author Mia Filić
 * @param <T>
 *            type of stored objects
 *
 */
public class LikeMedian<T extends Comparable<T>>
		implements Median<T>, Iterable<T> {

	/**
	 * Internally stored list of objects in collection, sorted by natural
	 * ordering.
	 */
	private List<T> list = new ArrayList<>();

	/**
	 * Internally stored list of objects in collection, not sorted.Perserves the
	 * input oreder.
	 */
	private List<T> inputList = new ArrayList<>();

	@Override
	public Optional<T> get() {
		if(list.isEmpty()){
			return Optional.empty(); //ispravak
		}
		int size = list.size();

		return Optional.of(list.get((size-1) / 2));
	}

	@Override
	public Iterator<T> iterator() {
		return inputList.iterator();
	}

	/**
	 * Adds the given object to the collection.
	 * 
	 * @param Object
	 *            to be added to collection.
	 */
	public void add(T Object) {
		int index = 0;
		for (T iter : list) {
			if (iter.compareTo(Object) > 0) {
				break;
			}
			++index;
		}

		list.add(index, Object);
		inputList.add(Object);
	}

}
