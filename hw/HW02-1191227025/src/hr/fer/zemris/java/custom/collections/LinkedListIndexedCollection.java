package hr.fer.zemris.java.custom.collections;

/**
 * Class implements list-backed collection of objects. List elements are defined
 * as instances of private static class ListNode.
 * 
 * Duplicate elements are allowed, but null references are not allowed.
 * 
 * @extends Collection
 * @author Mia Filić
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Private static class that implements the node of
	 * LinkedListIndexedCollection.
	 * 
	 * @author mia
	 *
	 */
	private static class ListNode {
		/**Pointer to previous element of the list.*/
		ListNode previous;
		/**Pointer to next element of the list.*/
		ListNode next;
		/**Value of the node.*/
		Object value;
	}

	/** Current size of list. */
	private int size;
	/** Pointer to last element of the list. */
	private ListNode first;
	/** Pointer to first element of the list. */
	private ListNode last;

	/**
	 * The default constructor that creates an empty collection.
	 */
	public LinkedListIndexedCollection() {
	}

	/**
	 * Constructor that creates new object, instance of this class and copied
	 * elements from Collection other to this newly constructed collection.
	 * 
	 * @param other
	 *            Collection which elements should be added; Collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		this.addAll(other);
	}

	/**
	 * Method checks if collection contains given value.
	 * 
	 * @param value
	 *            object to be checked for affiliation
	 * @return true if collection contains given value false otherwise; boolean
	 * 
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Method that returns the number of currently stored objects in this
	 * collections.
	 * 
	 * @return number of currently stored objects in collections; integer
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Method calls procesor.process() on each element of collection.
	 *
	 * @param processor
	 *            Instance of Processor on which process method should be called
	 */
	@Override
	public void forEach(Processor processor) {

		ListNode iter = first;
		for (int i = 0; i < size; ++i) {
			processor.process(iter.value);
			iter = iter.next;
		}
	}

	/**
	 * Method allocates new array with size equals to the size of this
	 * collections, fills it with collection content and returns the array.
	 * 
	 * @return array containing collection elements; Object[]
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];

		ListNode iter = first;
		for (int i = 0; i < size; ++i) {
			array[i] = iter.value;
			iter = iter.next;
		}

		return array;
	}

	/**
	 * Checks if the collection contains given value and removes one occurrence
	 * of it. In this class it is not specified which one. Equivalence is
	 * determined by equals method.
	 * 
	 * @param value
	 *            object to be removed
	 * @return true if collection contains given value, false otherwise; boolean
	 */
	@Override
	public boolean remove(Object value) {
		int index = this.indexOf(value);
		if (index != -1) {
			this.remove(index);
			return true;
		}

		return false;
	}

	/**
	 * Method adds given object into this collection at the end of collection.
	 * Complexity is O(1).
	 * 
	 * @param value
	 *            object to be added
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("This collection" + "doesnt support null references.");
		}
		if (first == null) {
			last = first = new ListNode();

			/*
			 * if(value instanceof Cloneable){ If I find the way I will add it
			 * first.value = value.clone(); }else{
			 */
			first.value = value;// }
		} else {
			// if there exactly one element
			if (first == last) {
				first.next = new ListNode();
				last = first.next;
				last.value = value;
				last.previous = first;
			} else {
				last.next = new ListNode();
				last.next.previous = last;
				last = last.next;
				last.value = value;
			}
		}
		++size;
	}

	/**
	 * Method returns the object that is stored in linked list at position
	 * index. Valid indexes are 0 to size-1.
	 * 
	 * @param index
	 *            position of an element to be returned; integer
	 * @return object at position index of list
	 * @throws IndexOutOfBoundsException
	 *             if argument is illegal
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Illegal argument index: " + index);
		}
		ListNode iter = first;
		int i = 0;

		while (i < index) {
			iter = iter.next;
			++i;
		}

		return iter.value;

	}

	/**
	 * Method removes all elements from the collection.
	 */
	public void clear() {
		if (last == null) {
			return; // if the list is empty
		}

		ListNode iter = last;
		while (iter != null) {
			iter.next = null;
			iter = iter.previous;
		}
		last = first = null;
		size = 0;
	}

	/**
	 * Method inserts the given value in linked-list at given position. Legal
	 * positions are 0 to size. COmplexity of this method is O(n).
	 * 
	 * @param value
	 *            to be inserted; Object
	 * @param position
	 *            index where to insert the value; integer
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Illegal argument index: " + position);
		}

		if(position == size){
			add(value);
			return;
		}
		
		ListNode iter;
		if(position == 0){
			iter = new ListNode();
			iter.value = value;
			first.previous = iter;
			iter.next = first;
			first = iter;
			size++;
			return;
		}
		
		iter = first;
		for (int i = 0; i < position-1; ++i) {
			iter = iter.next;
		}

		ListNode tmp = iter.next;
		iter.next = new ListNode();
		iter.next.value = value;
		iter.next.previous = iter;
		iter.next.next = tmp;
		tmp.previous = iter.next;
		++size;

	}

	/**
	 * Method that Searches the collection and returns the index of the first
	 * occurrence of the given value or -1 if the value is not found. The
	 * equality is determined using the equals method.
	 * 
	 * @param value
	 *            whose index should be found; Object
	 * @return the index of object value in linked-list; integer
	 */
	public int indexOf(Object value) {

		ListNode iter = first;
		int i = 0;
		while (iter != null) {
			if (value.equals(iter.value)) {
				break;
			}
			iter = iter.next;
			++i;
		}

		if (first == null) {
			return -1;
		}
		return i;
	}

	/**
	 * Removes element at specified index from collection. Legal indexes are 0
	 * to size-1.
	 * 
	 * @param index
	 *            of element that should be removed; integer
	 * @throws IndexOutOfBoundsException
	 *             if an index is illegal
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Illegal index :" + index);
		}
		if (index == 0) {
			first.previous = null;
			first = first.next;
			if (size == 1) {
				last = null;
			} else {
				first.previous = null;
			}
			size--;
			return;
		}

		if (index == size - 1) {// index is at least 2
			last.previous.next = null;
			last = last.previous;
			size--;
			return;
		}

		ListNode iter = first;
		int i = 0;
		while (i < (index - 1)) {
			iter = iter.next;
			++i;
		}

		ListNode tmp = iter.next.next;
		iter.next.next = null;
		iter.next.previous = null;
		iter.next = tmp;
		tmp.previous = iter;
		size--;
	}

}
