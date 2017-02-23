package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Program demonstrates the usage of {@code PrimesCollection} class.
 * 
 * @author Mia Filić
 *
 */
public class PrimesDemo2 {

	/**
	 * Program called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
