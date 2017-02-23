package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Template for creating the actions on {@code Integer Storage } group of
 * subjects.
 * 
 * @author Mia Filić
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Performing the certain action assigned to this {@code StorageObserver} on
	 * subject {@code istorage}.
	 * 
	 * @param istorage
	 *            subject on which to perform action.
	 */
	public void valueChanged(IntegerStorageChange istorage);

}
