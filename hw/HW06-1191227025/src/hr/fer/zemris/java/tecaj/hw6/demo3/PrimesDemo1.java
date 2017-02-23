package hr.fer.zemris.java.tecaj.hw6.demo3;
/**
 * Program demonstrates the usage of {@code PrimesCollection} class.
 * 
 * @author Mia Filić
 *
 */
public class PrimesDemo1 {

	/**
	 * Program called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how
																		// many
																		// of
																		// them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}

}
