package hr.fer.zemris.java.custom.collections;

/**
 * Class represents general collection of objects.
 * @author Mia FIlić
 *
 */

public class Collection {

	/**Protected default constructor.*/
	protected Collection(){}
	
	/**
	 * Method that checks if collection 
	 * contains no objects.
	 * 
	 * @return true if collection is empty,
	 * 			false otherwise; boolean
	 */
	public boolean isEmpty(){
		return size()==0;
	}
	
	/**
	 * Method that returns the number of 
	 * currently stored objects in this collections.
	 * 
	 * @return number of currently stored objects 
	 * 			in collections; integer
	 */
	public int size(){
		return 0;
		}
	
	/**
	 * Method adds the given object into this collection.
	 * 
	 * @param value object to be added; Object
	 */
	public void add(Object value){
		
	}
	
	/**
	 * Method checks if collection contains given value.
	 * 
	 * @param value object to be checked for affiliation
	 * @return true if collection contains given value
	 * 				false otherwise; boolean
	 * 
	 */
	public boolean contains(Object value){
		return false;
	}
	
	/**
	 * Checks if the collection contains 
	 * given value and removes one occurrence of it.
	 * In this class it is not specified which one.
	 * Equivalence is determined by equals method.
	 *  
	 * @param value object to be removed
	 * @return true if collection contains given value,
	 * 			false otherwise; boolean
	 */
	public boolean remove(Object value){
		return false;
		
	}
	
	/**
	 * Method allocates new array with size equals to
	 *  the size of this collections, fills it with collection 
	 *  content and returns the array.
	 *  
	 * @return array containing collection elements; Object[]
	 */
	public Object[] toArray(){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls procesor.process() on each element of collection.
	 *
	 * @param processor Instance of Processor on which process method
	 * 			should be called
	 */
	public void forEach(Processor processor){
		
	}
	
	/**
	 * Method adds into itself all elements from given collection.
	 * The other collection remains unchanged. 
	 * 
	 * @param other Collection whose elements should be added
	 */
	public void addAll(Collection other){
		
		class AddProcessor extends Processor{
			 /**
			  * Constructor.
			  */
			public AddProcessor() {
				
			}
			
			@Override
			public void process(Object value){
				add(value);
			}
		}
		
		AddProcessor adding = new AddProcessor();
		other.forEach(adding);
	}
	
	/**
	 * Method that removes all elements of the collection.
	 */
	public void clear(){
		
	}
	
	
	
	
}
