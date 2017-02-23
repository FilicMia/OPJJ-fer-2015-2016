package hr.fer.zemris.java.graphics.shapes;

/**
 * Implements the geometric shape ellipse. Each ellipse is specified by its
 * center and horizontal and vertical radius.
 * 
 * @author Mia Filić
 *
 */
public class Ellipse extends AbstractEllipse {
	/** String representazion of the name of this geometric shape. */
	public static final String name = "ELLIPSE";
	
	/**
	 * Number of parameters that each {@code Ellipse} holds and if fully determined by
	 * them.
	 */
	public static final int noParam = 4;
	
	/**
	 * Constructor that sets the properties on given values.
	 * 
	 * @param x
	 *            x coordinate of the ellipse center
	 * @param y
	 *            y coordinate of the ellipse center
	 * @param rx
	 *            Horizontal radius
	 * @param ry
	 *            Vertical radius
	 */
	public Ellipse(int x, int y, int rx, int ry) {
		super(x, y, rx, ry);
	}

}
