package hr.fer.zemris.java.tecaj.hw5.db.filter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface for filtering base on specific filter.
 * 
 * @author Mia Filić
 *
 */
public interface IFilter {
	/**
	 * Checks if student's record satisfies the filter.
	 * 
	 * @param record
	 *            student's record to be evaluated if its satisfies the filter.
	 * @return {@code true} if given record satisfied the given filter,
	 *         {@code false} otherwise
	 */
	public boolean accepts(StudentRecord record);
}