package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Program representig the usage of {@code IntegerStorege} with
 * {@code IntageStorageObserver}.
 * 
 * @author Mia Filić
 *
 */
public class ObserverExample {
	
	/**
	 * Method called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(5);
		System.out.println();
		istorage.setValue(2);
		System.out.println();
		istorage.setValue(25);
		System.out.println();

		istorage.removeObserver(observer);

		istorage.setValue(13);
		System.out.println();
		istorage.setValue(22);
		System.out.println();
		istorage.setValue(15);
	}
}
