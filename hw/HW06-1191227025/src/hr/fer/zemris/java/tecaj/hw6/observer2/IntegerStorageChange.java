package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Remembers the most accurate change of internally stored
 * {@code IntegerStorage}.
 * 
 * @author Mia Filić
 *
 */
public class IntegerStorageChange {
	/** Instance of {@code IntegerStorage} object. */
	private final IntegerStorage istorage;

	/** The value of stored integer before change. */
	private final int before;

	/** The value of stored integer after change. */
	private final int after;

	/**
	 * Constructor which sets the stored properties on initaial values passed as
	 * arguments
	 * 
	 * @param istorage
	 * @param before
	 * @param after
	 */
	public IntegerStorageChange(IntegerStorage istorage, int before,
			int after) {
		super();
		if (istorage == null) {
			throw new IllegalArgumentException("Storage cannot be null.");
		}
		this.istorage = istorage;
		this.before = before;
		this.after = after;
	}

	/**
	 * Returns the stored {@code IntegerStorage}.
	 * 
	 * @return stored {@code IntegerStorage}.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}
	
	/**
	 * Returns the stored {@code before} value.
	 * 
	 * @return stored {@code before} value.
	 */
	public int getBefore() {
		return before;
	}
	
	/**
	 * Returns the stored {@code after} value.
	 * 
	 * @return stored {@code after} value.
	 */
	public int getAfter() {
		return after;
	}

}
