package hr.fer.zemris.java.tecaj.hw6.observer2;
/**
 * Integer Observer which prints in standard output square of the value stored.
 * 
 * 
 * @author Mia Filić
 *
 */
public class SquareValue implements IntegerStorageObserver {
	
	/** Number on which the stored value should be powered. */
	private static final int TWO = 2;
	
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getAfter();
		System.out.println("Provided new value: " + value + ", square is "
				+ Math.pow(value,TWO));

	}

}
