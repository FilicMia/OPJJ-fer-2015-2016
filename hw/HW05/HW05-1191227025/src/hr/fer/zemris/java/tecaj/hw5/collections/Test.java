package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.Iterator;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

/**
 * Example of usage of SimpleHashTable collection.
 * 
 */
public class Test {

	/**
	 * Method called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes:
																			// 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes:

		Iterator<SimpleHashtable.TableEntry<String, Integer>> it = examMarks
				.iterator();
		while (it.hasNext()) {
			TableEntry<String, Integer> curr = it.next();
			System.out.println(
					"(" + curr.getKey() + ", " + curr.getValue() + ")");
		}
		System.out.println(examMarks.toString());

	}

}
