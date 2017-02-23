/**
 * 
 */
package hr.fer.zemris.java.gui.layouts;

import javax.swing.JPanel;

/**
 * Constraints object. An object expressing layout constraints for
 * {@link JPanel} component.
 * 
 * @author mia
 *
 */
public class RCPosition {
	/**Row index of position in grid.*/
	private int row;
	/**Column index of position in grid.*/
	private int column;

	/**
	 * Constructor setting fields.
	 * Representing valid position in {@link RCPosition}.
	 * 
	 * @param row
	 *            row index of position in grid.
	 * @param column
	 *            column index of position in grid.
	 */
	public RCPosition(int row, int column) {
		validatePosition(row, column);
		this.row = row;
		this.column = column;
		
	}

	/**
	 * Creating the {@link RCPosition} instance getting the position in grid by
	 * string.
	 * 
	 * @param str
	 */
	public RCPosition(String str) {
		int[] position = validatePosition(str);
		if (position == null) {
			throw new IllegalArgumentException(
					"Arguments provided are not legal. There is not spot in layout enumerated with ("
							+ str + ")");
		}
		this.row = position[0];
		this.column = position[1];
		
	}

	/**
	 * Extracts the position of type (a,b) from string "a,b".
	 * 
	 * @param str
	 *            {@String} representation of the position
	 * @return position extracted into array of size 2 if the passed string is
	 *         of type explained, {@code null} otherwise.
	 */
	private int[] validatePosition(String str) {
		int position[] = new int[2];
		if (!str.matches("\\s*[1]\\s*,\\s*[167]\\s*")
				&& !str.matches("\\s*[2-5]\\s*,\\s*[1-7]\\s*")) {
			return null;
		}
		str = str.replaceAll(",", " ").trim();
		String[] numbers = str.split("\\s+");
		position[0] = Integer.parseInt(numbers[0]);
		position[1] = Integer.parseInt(numbers[1]);
		return position;
	}

	/**
	 * @return row of the grid where to put the component
	 */
	public int getRow() {

		return row;
	}

	/**
	 * @return column of the grid where to put the component
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Checks if given pair ({@code row}, {@code column}) is valid in grid
	 * position of {@link CalcLayout}.
	 * 
	 * @param row
	 *            row value
	 * @param column
	 *            column value
	 */
	private void validatePosition(int row, int column) {
		if (row == 1) {
			if (column != 1 && column != 6 && column != 7) {
				throw new IllegalArgumentException(
						"Arguments provided are not legal. There is not spot in layout enumerated with ("
								+ row + "," + column + ")");
			}
		}

		if (column <= 0 || column > 7) {
			throw new IllegalArgumentException(
					"Arguments provided are not legal. There is not spot in layout enumerated with ("
							+ row + "," + column + ")");
		}

	}
	
	@Override
	public String toString() {
		return "" + "(" + row + "," + column + ")";
	}

}
