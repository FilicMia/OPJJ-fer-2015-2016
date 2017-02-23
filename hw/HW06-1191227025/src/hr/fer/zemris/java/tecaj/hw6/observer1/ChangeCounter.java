package hr.fer.zemris.java.tecaj.hw6.observer1;


/**
 * Integer Observer which prints in standard output number of changes of the
 * storage since subscribing.
 * 
 * 
 * @author Mia Filić
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** Counter of changes of subscribed storages. */
	private int counter = 0;

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Number of value changes since tracking: " + ++counter);

	}

}
