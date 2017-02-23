package hr.fer.zemris.java.graphics.shapes;

/**
 * Implements the geometric shape rectangle. Each rectangle is specified by its
 * upper left corner and its width and height.
 * 
 * @author Mia Filić
 *
 */
public class Rectangle extends AbstaractRectangle{
	/** String representation of the name of this geometric shape. */
	public static final String name = "RECTANGLE";
	
	/**
	 * Number of parameters that each {@code Rectangle} holds and if fully determined by
	 * them.
	 */
	public static final int noParam = 4;
	
	/**
	 * COnstructor that sets properties of the rectangle.
	 * 
	 * @param x
	 *            x coordinate of the upper left corner of the rectangle
	 * @param y
	 *            y coordinate of the upper left corner of the rectangle
	 * @param height
	 *            Height of the rectangle.
	 * @param width
	 *            Width of the rectangle.
	 */
	public Rectangle(int x, int y, int height, int width) {
		super(x,y,height,width);
	}

}
