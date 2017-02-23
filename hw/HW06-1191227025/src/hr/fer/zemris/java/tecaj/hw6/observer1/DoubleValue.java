package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Integer Observer which leftUntilDeregistration in standard output double of
 * value stored in storage only for n times.
 * 
 * 
 * @author Mia Filić
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Stores how much times was {@code valueChange} method called on specific
	 * {@code storage}.
	 */
	private int leftUntilDeregistration;

	/**
	 * Number of times the double value of specific storage is printed on
	 * standard output.
	 */
	private int n;

	/** Number whit the stored value should be multiplied. */
	private static final int TWO = 2;

	/**
	 * Constructor, sets the property value on passed value.
	 *
	 * @param n
	 *            number of times that the double value should be called.
	 */
	public DoubleValue(int n) {
		this.n = n;
		this.leftUntilDeregistration = n;
	}

	/**
	 * Gets the number of changes that can be occurred until this observer will
	 * be deregistred.
	 * 
	 * @return the number of changes that can be occurred until deregistration.
	 */
	public int getLeftUntilDeregistration() {
		return leftUntilDeregistration;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int number = leftUntilDeregistration - 1;
		if (number >= 0) {
			System.out.println("Double value: " + TWO * istorage.getValue());

			leftUntilDeregistration = number;

		}

	}

}
