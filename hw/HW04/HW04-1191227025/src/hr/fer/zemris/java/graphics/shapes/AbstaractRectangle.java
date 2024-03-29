/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Implements the geometric shape rectangle. Each rectangle is specified by its
 * upper left corner and its width and height.
 * Square is special form of ellipse.
 * 
 * @author Mia FIlić
 *
 */
public abstract class AbstaractRectangle extends GeometricShape {
	/** x coordinate of the upper left corner of the rectangle */
	private int x;
	/** y coordinate of the upper left corner of the rectangle */
	private int y;
	/** Height of this shape. */
	private int height;
	/** Width of this shape. */
	private int width;

	/**
	 * Constructor that sets properties of the rectangle.
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
	public AbstaractRectangle(int x, int y, int height, int width) {
		if (height <= 0 || width <= 0) {
			throw new IllegalArgumentException(
					"Wrong secification of rectangle.");
		}
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	/**
	 * Returns the x coordinate of the upper-left corner.
	 * 
	 * @return the x coordinate of the upper-left corner.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the upper-left corner.
	 * 
	 * @param x
	 *            the x coordinate of the upper-left corner.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y coordinate of the upper-left corner.
	 * 
	 * @return the y coordinate of the upper-left corner.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the upper-left corner.
	 * 
	 * @param y
	 *            the y coordinate of the upper-left corner.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the height.
	 * 
	 * @return the height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 * 
	 * @param height
	 *            the height.
	 */
	public void setHeight(int height) {
		if (height <= 0) {
			throw new IllegalArgumentException("Height can not be negative.");
		}
		this.height = height;
	}

	/**
	 * Returns the width.
	 * 
	 * @return the width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 * 
	 * @param width
	 *            the width.
	 */
	public void setWidth(int width) {
		if (width <= 0) {
			throw new IllegalArgumentException("Width can not be negative.");
		}
		this.width = width;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x)
			return false;
		if (y < this.y)
			return false;
		if (x >= this.x + this.width)
			return false;
		if (y >= this.y + this.height)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(BWRaster r) {
		int lowerBoundWidth = Math.max(0, this.x);
		int upperBoundWidth = (this.x + this.width) > 0
				? Math.min(this.x + this.width, r.getWidth()) : 0;
		int lowerBoundHeight = Math.max(0, this.y);
		int upperBoundHeight = (this.y + this.height) > 0
				? Math.min(this.y + this.height, r.getHeigth()) : 0;
		//r.disableFlipMode();
		for (int x = lowerBoundWidth; x < upperBoundWidth; ++x) {
			for (int y = lowerBoundHeight; y < upperBoundHeight; ++y) {
				r.turnOn(x, y);

			}
		}
	}

}
