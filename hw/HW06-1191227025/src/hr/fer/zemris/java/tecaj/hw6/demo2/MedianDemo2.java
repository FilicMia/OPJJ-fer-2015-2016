package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * Program representing the usage of {@code Median} class.
 * 
 * @author Mia Filić
 *
 */
public class MedianDemo2 {

	/**
	 * Program called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		LikeMedian<String> likeMedian = new LikeMedian<String>();
		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");
		Optional<String> result = likeMedian.get();
		System.out.println(result); // Writes: Jane

	}

}
