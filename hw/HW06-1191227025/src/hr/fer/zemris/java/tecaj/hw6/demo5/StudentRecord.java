package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Storage of one Student record at the time.
 */
public class StudentRecord {

	/** Number of stored values on given student. */
	public static final int ARGC = 7;

	/** Regex maches the jmbag property. */
	public static final String JMBG_REGEX = "[0-9]{10}";

	/** Regex maches the lastName property. */
	public static final String LASTNAME_REGEX = "([A-ZČĆŽŠĐ]{1}[a-zčćžšđ0-9]*[- ]{0,1})+";// must
																							// start
																							// with
																							// uppercase

	/** Regex maches the firstName property. */
	public static final String FIRSTNAME_REGEX = "([A-ZČĆŽŠĐ]{1}[a-zčćžšđ0-9]*[- ]{0,1})+";

	/** Regex maches the finalGrade property. */
	public static final String FINALGRADE_REGEX = "[1-5]{1}";

	/** Regex maches the score properties. */
	public static final String SCORE = "[1-9]+[.]?[0-9]*";

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
	 * Score on mid exam.
	 */
	private double scoreMidExam;

	/**
	 * Score on end exam.
	 */
	private double scoreEndExam;

	/**
	 * Score for laboratory achievements.
	 */
	private double scoreLab;

	/**
	 * Final grade of the student.
	 */
	private int finalGrade;

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
	 * @param scoreMidExam
	 *            score on mid exam
	 * @param scoreEndExam
	 *            score on end exam
	 * @param scoreLab
	 *            score for laboratory achievements
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			double scoreMidExam, double scoreEndExam, double scoreLab,
			int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.scoreMidExam = scoreMidExam;
		this.scoreEndExam = scoreEndExam;
		this.scoreLab = scoreLab;
		this.finalGrade = finalGrade;
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
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Sets the final grade for the student.
	 * 
	 * @param finalGrade
	 *            final grade
	 */
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns the mid exam score.
	 * 
	 * @return mid exam score
	 */
	public double getScoreMidExam() {
		return scoreMidExam;
	}

	/**
	 * Sets the mid exam score.
	 * 
	 * @param scoreMidExam
	 *            mid exam score
	 */
	public void setScoreMidExam(int scoreMidExam) {
		this.scoreMidExam = scoreMidExam;
	}

	/**
	 * Returns the end exam score.
	 * 
	 * @return end exam score
	 */
	public double getScoreEndExam() {
		return scoreEndExam;
	}

	/**
	 * Sets the end exam score.
	 * 
	 * @param scoreEndExam
	 *            end exam score
	 */
	public void setScoreEndExam(int scoreEndExam) {
		this.scoreEndExam = scoreEndExam;
	}

	/**
	 * Returns the lab score.
	 * 
	 * @return lab score
	 */
	public double getScoreLab() {
		return scoreLab;
	}

	/**
	 * Sets the score for laboratory achievements.
	 * 
	 * @param scoreLab
	 *            score for laboratory achievements.
	 */
	public void setScoreLab(int scoreLab) {
		this.scoreLab = scoreLab;
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
		sb.append(scoreMidExam).append(DELIMITER);
		sb.append(scoreEndExam).append(DELIMITER);
		sb.append(scoreLab).append(DELIMITER);
		sb.append(finalGrade);
		return sb.toString();
	}

}
