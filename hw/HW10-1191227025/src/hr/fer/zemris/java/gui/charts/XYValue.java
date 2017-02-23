/**
 * 
 */
package hr.fer.zemris.java.gui.charts;

/**
 * Class for storing (x,y) integer pairs. It has getter for each element of the
 * pair.
 * 
 * @author mia
 *
 */
public class XYValue {

	/**
	 * Saved x value.
	 */
	int x;

	/**
	 * Saved y value.
	 */
	int y;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            x value
	 * @param y
	 *            y value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y value
	 */
	public int getY() {
		return y;
	}

}
