/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.views;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface for classes that are responsible for visualization of records.
 * 
 * @author Mia Filić
 *
 */
public interface View {

	/**
	 * Creates the Object representation of records.
	 * 
	 * @param records
	 *            records whose representation to produce
	 * @return produced representation
	 */
	public String produce(List<StudentRecord> records);

}
