package hr.fer.zemris.java.tecaj.hw5.vgetter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Responsible for obtaining lastName field value form given
 * {@code StudentRecord}
 * 
 * @author Mia FIlić
 * @version 1.0
 */
public class GetLastName implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		if (record == null) {
			throw new NullPointerException();
		}

		return record.getLastName();
	}

}
