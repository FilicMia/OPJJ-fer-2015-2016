package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Set;

/**
 * Collection implemented as multistack. It allows multiple values to be
 * assigned to the same {@code String} key.
 * 
 * Values that will be associated with those keys will be instances of class
 * ValueWrapper.
 * 
 * @author Mia Filić
 *
 */
public class ObjectMultistack {

	/**
	 * Storage for (key,value) pairs. In this case ({@code String},
	 * {@code MultistackEntry} list) pairs. Each list acts as stack.
	 */
	private HashMap<String, MultistackEntry> storage = new HashMap<>();

	/**
	 * Integer number one. Used in incrementing the index of elements in
	 * specific stack.
	 */
	private final static int ONE = 1;

	/**
	 * Pushes the {@code valueWrapper} to the correct multistack.
	 * 
	 * @param name
	 *            "name" of the slot where the object should be added.
	 * @param valueWrapper
	 *            object to be added.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry head = storage.get(name);

		if (head == null) {
			head = new MultistackEntry(null, valueWrapper, ONE);
		} else {

			MultistackEntry newOne = new MultistackEntry(head, valueWrapper,
					head.getIndex() + ONE);
			head = newOne;
		}

		storage.put(name, head);
	}

	/**
	 * Returns and deletes the last element added to the stack corresponding
	 * given key {@code name}
	 * 
	 * @param name
	 *            "name" key value of the stack in which to peek for element.
	 * @return the last element added to the stack corresponding given key
	 *         {@code name}
	 * @throws EmptyStackException
	 *             if the stack corresponding given key {@code name} is empty
	 */
	public ValueWrapper pop(String name) {
		MultistackEntry head = storage.get(name);
		if (head == null) {
			throw new EmptyStackException();
		}

		MultistackEntry onReturn = head;
		head = head.getNext();
		storage.put(name, head);
		return onReturn.getValue();

	}

	/**
	 * Returns the last element added to the stack corresponding given key
	 * {@code name}
	 * 
	 * @param name
	 *            "name" key value of the stack in which to peek for element.
	 * @return the last element added to the stack corresponding given key
	 *         {@code name}
	 * @throws EmptyStackException
	 *             if the stack corresponding given key {@code name} is empty
	 */
	public ValueWrapper peek(String name) {
		MultistackEntry head = storage.get(name);
		if (head == null) {
			throw new EmptyStackException();
		}

		return head.getValue();
	}

	/**
	 * Checks if slot(stack) corresponding to given name is empty. We consider
	 * slot as empty if map method get returns {@code null}.
	 * 
	 * @param name
	 *            "name" of the slot to be checked
	 * @return {@code true } if slot is empty, {@code false} otherwise
	 */
	public boolean isEmpty(String name) {
		return storage.get(name) == null;
	}

	/**
	 * Static class representing the element of stack corresponding each key
	 * stored in map. Stack is represented as linked-list so this class is node
	 * of linked-list.
	 * 
	 * @author Mia Filić
	 *
	 */
	public static class MultistackEntry {
		/** Pointer to next element of the list (stack). */
		private MultistackEntry next;
		/** Value of the node. */
		private ValueWrapper value;

		/**
		 * Number of elements beneath this one in the stack including this one.
		 */
		private int index;

		/**
		 * Constructor. Sets the private properties on the values passed as
		 * arguments of the function.
		 * 
		 * @param next
		 *            next node of linked list
		 * @param value
		 *            value of the node.
		 * @param index
		 *            number of elements beneath this one in the stack including
		 *            this one
		 */
		public MultistackEntry(MultistackEntry next, ValueWrapper value,
				int index) {
			super();
			this.next = next;
			this.value = value;
			this.index = index;
		}

		/**
		 * Returns the {@link index} of the element.
		 * 
		 * @return {@link index} of the element.
		 */
		private int getIndex() {

			return index;
		}

		/**
		 * Returns the previous element in stack.
		 * 
		 * @return previous element in stack.
		 */
		public MultistackEntry getNext() {
			return next;
		}

		/**
		 * Returns the node {@code value}.
		 * 
		 * @return value stored in specific node.
		 */
		public ValueWrapper getValue() {
			return value;
		}

	}

	/**
	 * Returns the set of keys associated with multistack.
	 * 
	 * @return set of keys.
	 */
	public Set<String> keySet() {
		return storage.keySet();
	}
}
