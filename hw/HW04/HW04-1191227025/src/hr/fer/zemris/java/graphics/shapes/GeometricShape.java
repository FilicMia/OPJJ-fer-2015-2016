/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Root of geometric shapes inheritance tree.
 * 
 * @author Mia FIlić
 *
 */
public abstract class GeometricShape {
	
	/**
	 * Checks if this shape contains given point (x,y).
	 * 
	 * @param x
	 *            x coordinate of the poin
	 * @param y
	 *            y coordinate of the point
	 * @return {@code true} if shape contains given point, {@code false}
	 *         otherwise
	 */
	public abstract boolean containsPoint(int x, int y);

	/**
	 * Method draws the this geometric shape on raster. It over writes the
	 * current draw in raster.
	 * 
	 * @param r
	 *            raster where the shape will be drawn
	 */
	public void draw(BWRaster r) {
		int rasterWidth = r.getWidth();
		int rasterHeigth = r.getHeigth();
		//r.disableFlipMode();
		for (int x = 0; x < rasterHeigth; ++x) {
			for (int y = 0; y < rasterWidth; ++y) {
				if (containsPoint(x, y)) {
					r.turnOn(x, y);
				}

			}
		}
	}
	
	
}