package hr.fer.zemris.java.tecaj.hw5.views;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class responsible for textual visualization of records as strings.
 * 
 * @author Mia Filić
 *
 */
public class StringView implements View {
	/** Character that will be used to represent upper border. */
	private char upper;
	/** Character that will be used to represent side border. */
	private char side;
	/** Character that will be used to represent edge. */
	private char edge;
	/** Jmbag size. */
	public static final int JMBAGSIZE = 10;
	/** FinalGrade size. */
	public static final int GRADESIZE = 1;
	/**
	 * Each box should be for 2 greater than the maximum length of property
	 * which is in that column.
	 */
	public static final int TWO = 2;

	/**
	 * Default constructor. It sets properties on default values: '=' for
	 * {@code upper}, '|' for {@code side}, '+' for {@code edge}.
	 * 
	 */
	public StringView() {
		this('=', '|', '+');
	}

	/**
	 * Constructor sets properties on given values.
	 * 
	 * @param upper
	 *            Character that will be used to represent upper border.
	 * @param side
	 *            Character that will be used to represent side border.
	 * @param edge
	 *            Character that will be used to represent edge border.
	 */

	public StringView(char upper, char side, char edge) {
		super();
		this.upper = upper;
		this.side = side;
		this.edge = edge;
	}

	/**
	 * Returns the string textual representation of records.
	 * 
	 * @param records
	 *            records whose representation will produce
	 * @return {@code String}
	 */
	@Override
	public String produce(List<StudentRecord> records) {
		int size = records.size();
		StringBuilder sb = new StringBuilder(size * size);
		int firstNameSize = 0, lastNameSize = 0;

		for (StudentRecord record : records) {

			int firstName = record.getFirstName().length();
			int lastName = record.getLastName().length();

			if (firstName > firstNameSize) {
				firstNameSize = firstName;
			}
			if (lastName > lastNameSize) {
				lastNameSize = lastName;
			}

		}

		upperBorder(sb, lastNameSize, firstNameSize);
		for (StudentRecord record : records) {

			drawMyBox(sb, record.getJmbag(), JMBAGSIZE);
			drawMyBox(sb, record.getLastName(), lastNameSize);
			drawMyBox(sb, record.getFirstName(), firstNameSize);
			drawMyBox(sb, record.getFinalGrade(), GRADESIZE);
			sb.append(side);
			sb.append('\n');
		}

		upperBorder(sb, lastNameSize, firstNameSize);

		return sb.toString();
	}

	/**
	 * Appends the upper and lower border to string builder.
	 * 
	 * @param sb
	 *            {@code StringBuilder}
	 * @param lastNameSize
	 *            length of last name column
	 * @param firstNameSize
	 *            length of first name column
	 */
	private void upperBorder(StringBuilder sb, int lastNameSize,
			int firstNameSize) {
		sb.append(edge);

		for (int i = 0; i < JMBAGSIZE + TWO; ++i) {
			sb.append(upper);
		}
		sb.append(edge);
		for (int i = 0; i < lastNameSize + TWO; ++i) {
			sb.append(upper);
		}
		sb.append(edge);
		for (int i = 0; i < firstNameSize + TWO; ++i) {
			sb.append(upper);
		}
		sb.append(edge);
		for (int i = 0; i < GRADESIZE + TWO; ++i) {
			sb.append(upper);
		}
		sb.append(edge);
		sb.append('\n');
	}

	/**
	 * Appends the box for specific {@code String} of size 1*boxSize to
	 * {@code sb}. String is positioned in the left corner of the box after the
	 * '|' ane one ' '.
	 * 
	 * @param sb
	 *            {@code StringBuilder} on which to append the box filled with
	 *            passed data
	 * @param data
	 *            data of which to create the box
	 * @param boxSize
	 *            length of the box (inside | |)
	 */
	private void drawMyBox(StringBuilder sb, String data, int boxSize) {
		int difference = boxSize - data.length();
		if (difference < 0 || data == null || sb == null) {
			throw new IllegalArgumentException(
					"Cannot draw the box with this arguments. ");
		}
		sb.append(side);
		sb.append(' ');
		sb.append(data);

		for (int i = 0; i <= difference; ++i) {
			sb.append(' ');
		}
	}
}
