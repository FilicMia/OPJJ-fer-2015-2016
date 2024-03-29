package hr.fer.zemris.java.graphics.raster;

/**
 * Interface for a raster device. This is an abstraction for all raster devices
 * of fixed width and height for which each pixel can be painted with only two
 * colors: black (when pixel is turned off) and white (when pixel is turned on)
 * The coordinate system for raster has (0,0) at the top-left corner of raster;
 * positive x-axis is to the right and positive y-axis is toward the bottom. It
 * has 2 modes: flipping and unflipping mode(disabled flipping mode).
 * 
 * If flipping mode of raster is disabled, then the call of the {@code turnOn}
 * method turns on the pixel at specified location (again, if location is
 * valid). However, if flipping mode is enabled, then the call of the
 * {@code turnOn} method flips the pixel at the specified location (if it was
 * turned on, it must be turned off, and if it was turned off, it must be turned
 * on).
 * 
 * It is Initially disabled.
 * 
 * @author Mia Filić
 *
 */
public interface BWRaster {

	/**
	 * Method that returns the appropriate dimension of used rasted.
	 * 
	 * @return width of used raster
	 */
	public int getWidth();

	/**
	 * Method that returns the appropriate dimension of used rasted.
	 * 
	 * @return high of used raster
	 */
	public int getHeigth();

	/**
	 * Method clear turns off all pixels in raster.
	 */
	public void clear();

	/**
	 * Methods turns the pixel on, at specified location (x, y) of the raster.
	 * See {@link BWRaster} for the specification of the x and y axis of the
	 * raster.
	 * 
	 * @param x
	 *            x coordinate of the specified pixel
	 * @param y
	 *            y coordinate of the specified pixel
	 * @throws throw
	 *             {@code IllegalArgumentException} if (x,y) is invalid with
	 *             respect to raster dimensions.
	 */
	public void turnOn(int x, int y);

	/**
	 * Methods turns the pixel off, at specified location (x, y) of the raster.
	 * See {@link BWRaster} for the specification of the x and y axis of the
	 * raster.
	 * 
	 * @param x
	 *            x coordinate of the specified pixel
	 * @param y
	 *            y coordinate of the specified pixel
	 * @throws throw
	 *             {@code IllegalArgumentException} if (x,y) is invalid with
	 *             respect to raster dimensions.
	 */
	public void turnOff(int x, int y);

	/**
	 * Enable the flipping mode.
	 */
	public void enableFlipMode();

	/**
	 * Disable the flipping mode.
	 */
	public void disableFlipMode();

	/**
	 * Checks if pixel at given position is Turned on.
	 * 
	 * @param x
	 *            x coordinate of pixel
	 * 
	 * @param y
	 *            y coordinate of the pixel
	 * 
	 * @return {@code true} if pixel is turned on, {@code false} otherwise
	 * 
	 * @throws IllegalArgumentException
	 *             if(x,y) is invalid with raster dimensions
	 */
	public boolean isTurnedOn(int x, int y);
	
	/**
	 * Checks if flipped mode is turned on.
	 * 
	 * @return {@code true} if flipped mode is turned on
	 */
	public boolean flippedMode();

}
