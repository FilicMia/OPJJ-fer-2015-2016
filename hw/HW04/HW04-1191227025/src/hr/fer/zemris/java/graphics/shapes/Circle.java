/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

/**
 * @author mia
 *
 */
public class Circle extends AbstractEllipse {
	/** String representation of the name of this geometric shape. */
	public static final String name = "CIRCLE";

	/**
	 * Number of parameters that each circle holds and if fully determined by
	 * them.
	 */
	public static final int noParam = 3;

	/**
	 * Constructor that sets the properties on given values.
	 * 
	 * @param x
	 *            x coordinate of the ellipse center
	 * @param y
	 *            y coordinate of the ellipse center
	 * @param r
	 *            radius
	 */
	public Circle(int x, int y, int r) {
		super(x, y, r, r);
	}

	/**
	 * Sets the radius of circle on given value.
	 * 
	 * @param r
	 *            radius
	 */
	public void setRadius(int r) {
		super.setRx(r);
		super.setRy(r);
	}

	@Override
	public void setRx(int rx) {
		super.setRx(rx);
		super.setRy(rx);
	}

	@Override
	public void setRy(int ry) {
		super.setRy(ry);
		super.setRx(ry);
	}

}
