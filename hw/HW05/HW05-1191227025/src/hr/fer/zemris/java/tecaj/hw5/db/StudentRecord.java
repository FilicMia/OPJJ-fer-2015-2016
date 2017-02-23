package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Storage of one Student record at the time.
 */
public class StudentRecord {

	/** Number of stored values on given student. */
	public static final int ARGC = 4;
	
	/**Regex maches the jmbag property.*/
	public static final String JMBG_REGEX = "[0-9]{10}";
	
	/**Regex maches the lastName property.*/
	public static final String LASTNAME_REGEX = "([A-ZČĆŽŠĐ]{1}[a-zčćžšđ]*[- ]{0,1})+";//must start with uppercase
	
	/**Regex maches the firstName property.*/
	public static final String FIRSTNAME_REGEX = "([A-ZČĆŽŠĐ]{1}[a-zčćžšđ]*[- ]{0,1})+";
	
	/**Regex maches the finalGrade property.*/
	public static final String FINALGRADE_REGEX = "[1-5]{1}";

	/**
	 * {@code String} representation of the student's jmbag without leading and
	 * following spaces.
	 */
	private String jmbag;

	/**
	 * Last name of the student without leading and following spaces.
	 */
	private String lastName;

	/**
	 * First name of the student without leading and following spaces.
	 */
	private String firstName;

	/**
	 * Final grade of the student without leading and following spaces.
	 */
	private String finalGrade;

	/**
	 * Delimiters each value of the student record in {@code String}
	 * representation of string record.
	 */
	public final static String DELIMITER = "\t";
	
	/**
	 * Constructor. Sets properties on the given values.
	 * 
	 * @param jmbag
	 *            {@code String} representation of the student's jmbag
	 * @param lastName
	 *            last name of the student
	 * @param firstName
	 *            first name of the student
	 * @param finalGrade
	 *            final grade of the student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			String finalGrade) {
		super();
		this.jmbag = jmbag.trim();
		this.lastName = lastName.trim();
		this.firstName = firstName.trim();
		this.finalGrade = finalGrade.trim();
	}

	/**
	 * Gets the jmbag of the student.
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets the last name of the student.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the first name of the student.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the final grade of the student.
	 * 
	 * @return final grade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Sets the final grade for the student.
	 * 
	 * @param finalGrade
	 *            final grade
	 */
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag).append(DELIMITER);
		sb.append(lastName).append(DELIMITER);
		sb.append(firstName).append(DELIMITER);
		sb.append(finalGrade);
		return sb.toString();
	}

}
