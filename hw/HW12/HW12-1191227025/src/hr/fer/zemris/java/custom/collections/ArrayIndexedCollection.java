/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Class implements an resizable array-backed collection
 * of objects. Duplicate elements are allowed. null references
 * are not allowed.
 * 
 * @author Mia Filić
 *
 */
public class ArrayIndexedCollection extends Collection{
	
	 /** Current size of collection*/
	private int size;
	/**Current capacity of allocated array of Objects */
	private int capacity;
	/**An array of objects references which length is determinated
	 * by capacity variable*/
	private Object[] elements;
	
	/**Default capacity of instance ArrayIndexedColection.*/
	private static int DEFAULT_CAPACITY = 16;
	
	/**
	 * Default constructor that creates an 
	 * instance with capacity 16.
	 */
	public ArrayIndexedCollection() {
		capacity = DEFAULT_CAPACITY;
		elements = new Object[capacity];
	}
	
	/**
	  * Constructor that creates an 
	 * instance with capacity initialCapacity.
	 * 
	 * @param initialCapacity of the array
	 */
	public ArrayIndexedCollection(int initialCapacity){
		if(initialCapacity < 1){
			throw new IllegalArgumentException("Initial capacity should be grater than one, but"
					+ "but you said: initialCapacity = "+initialCapacity);
		}
	
		capacity = initialCapacity;
		elements = new Object[capacity];
	}
	
	/**
	 * Constructor that creates an 
	 * instance with capacity 16 and than adds all
	 * elements from the collection other.
	 * Adding is done by calling the addAll function which calls
	 * add method on every object in given collection. That way 
	 * every Object in collection is added into first empty 
	 * place in the elements array.
	 * If the array is full (at some point of adding)it is reallocated 
	 * by doubling the size. 
	 * 
	 * @param other collection which elements should be added
	 */
	public ArrayIndexedCollection(Collection other){
		this();
		this.addAll(other);
	}
	
	/**
	  * Constructor that creates an 
	 * instance with capacity initialCapacityand and than adds all
	 * elements from the collection. Adding is done by calling
	 * the addAll function which calls add method on every object
	 * in given collection. That way every Object in collection
	 *  is added into first empty 
	 * place in the elements array.
	 * If the array is full (at some point of adding)it is reallocated 
	 * by doubling the size. 
	 * 
	 * 
	 * @param other collection which elements should be added
	 * @param initialCapacity of the array
	 */
	public ArrayIndexedCollection(Collection other,int initialCapacity){
		this(initialCapacity);
		
		this.addAll(other);
	}
	
	
	/**
	 * Method checks if collection contains given value.
	 * 
	 * @param value object to be checked for affiliation
	 * @return true if collection contains given value
	 * 				false otherwise; boolean
	 * 
	 */
	@Override
	public boolean contains(Object value) {
		return !(indexOf(value) ==-1) ;
	}
	
	/**
	 * Method allocates new array with size equals to
	 *  the size of this collections, fills it with collection 
	 *  content and returns the array.
	 *  
	 * @return array containing collection elements; Object[]
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		for(int i = 0;i < size;++i){
			array[i] = elements[i];
		}
		
		return array;
	}
	
	/**
	 * Method that returns the number of 
	 * currently stored objects in this collections.
	 * 
	 * @return number of currently stored objects 
	 * 			in collections; integer
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Method calls procesor.process() on each element of collection.
	 *
	 * @param processor Instance of Processor on which process method
	 * 			should be called
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0;i < size;++i){
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Method removes element at specified index from collection.
	 * Legal indexes are 0 to size-1.
	 * 
	 * @param index 
	 * @throws IllegalArgumentException if an illegal index occured.
	 */
	public void remove(int index){
		if(index < 0 || index >= size){
			throw new IllegalArgumentException("Legal indexes are 0 to size-1");
		}
		
		for(int i = index;i < (size-1);++i){
			elements[i] = elements[i+1];
		}
		
		elements[size-1] = null;
		size--;
		
	}
	
	/**
	 * Method Adds given object into this collection.
	 * Object is added into first empty place in the elements array.
	 * If the array is full it if reallocated by doubling the size.
	 * 
	 * @param value object to be added; Object
	 */
	@Override
	public void add(Object value) {
		this.insert(value,size);
	}
	
	/**
	 * Method that returns an object stored in backing array at position index.
	 * Valid indexes are 0 to size-1. If index
	 * is invalid, it throws exception.
	 * Average complexity of this method is O(1);
	 * 
	 * @param index position of the object to be returned; integer
	 * @return object stored at position index
	 */
	public Object get(int index){
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException("Illegal argument index: "+index);
		}
		
		return elements[index];
	}
	
	/**
	 * Method that removes all elements of the collection.
	 */
	@Override
	public void clear(){
		size = 0;
		for(int i = 0;i < capacity;++i){
			elements[i] = null;
		}
	}
	
	/**
	 * Checks if the collection contains 
	 * given value and removes first occurrence of it.
	 * Equivalence is determined by equals method.
	 *  
	 * @param value object to be removed
	 * @return true if collection contains given value,
	 * 			false otherwise; boolean
	 */
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index == -1){
			return false;
		} else {
			this.remove(index);
			return true; 
		}
	}
	
	/**
	 *   Method inserts the given value at the given position in array.
	 *   Before actual insertion elements at position and
	 *   at greater positions will be shifted one place toward the end,
	 *   so that an empty place is created at position. 
	 *   The legal positions are 0 to size. If position is invalid, the exception is 
	 *   thrown. Except the difference in position at witch the given object will be inserted,
	 *   it corresponds the add method.
	 *   The complexity of this method is O(n).
	 * 
	 * @param value object to be inserted;Object
	 * @param position position on which the value object should be inserted; integer
	 */
	public void insert(Object value, int position){
		if(value == null){
			throw new IllegalArgumentException("Null elements are not allowed");
		}
		if(position < 0 || position > size){
			throw new IllegalArgumentException("Position must be between(included) 0 and "+size);
		}
		
		//We must allocate sufficiant memory to add element
		if(size == capacity){
			Object[] newElem = new Object[2*capacity];
			for(int i = 0;i < capacity;++i){
				newElem[i] = elements[i];
			}
			elements = newElem;
			capacity *= 2;
		}
		
		for(int i = size-1 ;i >= position;--i){
			elements[i+1] = elements[i];
		}
		
		elements[position] = value;
		size++;
		
	}
	
	/**
	 * Method returns the index of the first occurrence of the given 
	 * value or -1 if the value is not found.
	 * Equivalence is determined by equals method.
	 * The complexity of this method is O(n).
	 * 
	 * @param value object which index should be found; Object
	 * @return first occurrence of the given 
	 *				 value or -1 if the value is not found; integer
	 */
	public int indexOf(Object value){
		for(int i =0;i< size;++i){
			if(value.equals(elements[i])){
				return i;
			}
		}
		
		return -1;
	}
	
}
