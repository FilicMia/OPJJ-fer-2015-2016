package hr.fer.zemris.java.custom.collections;

/**
 * Class represents a stack-like collection. It has all methods that stack-like
 * collection should have. Null values are not allowed, but duplicates are.
 * 
 * @author Mia Filić
 *
 */
public class ObjectStack {

	/**
	 * Instance of ArrayIndexedCollection that is used for storing elements.
	 * Behind the stage, it performs stack stack-like methods like
	 * pop,push,peek.
	 */
	private ArrayIndexedCollection storage;

	{
		storage = new ArrayIndexedCollection();
	}

	/**
	 * Method that checks if collection contains no objects
	 * 
	 * @return true if collection is empty, false otherwise; boolean
	 */
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	/**
	 * Method that returns the number of currently stored objects in this
	 * collections.
	 * 
	 * @return number of currently stored objects in collections; integer
	 */
	public int size() {
		return storage.size();

	}

	/**
	 * Method pushes given value on the stack. Null values are not allowed.
	 * 
	 * @param value
	 *            object to be pushed next on the stack.
	 */
	public void push(Object value) {
		storage.add(value);
	}

	/**
	 * Method that removes last value pushed on the stack from stack and returns
	 * it.
	 * 
	 * @return last value pushed on the stack; Object
	 */
	public Object pop() {
		int size = storage.size();
		//peek throw EmptyStack Collection if size == 0
		Object tmp = peek();

		storage.remove(size - 1);

		return tmp;
	}

	/**
	 * Method that returns last value pushed on the stack.
	 * 
	 * @return last value pushed on the stack; Object
	 */
	public Object peek() {
		int size = storage.size();
		if (size == 0) {
			throw new EmptyStackException("Cannot to get an element from an " + "empty stack.");
		}
		return storage.get(size - 1);
	}

	/**
	 * Method removes all elements from the stack.
	 */
	public void clear() {
		storage.clear();
	}

	/**
	 * Method prints stacks elements on standard output.
	 * 
	 */
	public void printStack() {
		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		};
		System.out.println("Stack...");
		storage.forEach(new P());
	}
}
