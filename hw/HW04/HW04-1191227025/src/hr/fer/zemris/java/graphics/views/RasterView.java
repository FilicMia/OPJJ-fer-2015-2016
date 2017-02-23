/**
 * 
 */
package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Interface for classes that are responsible for visualization of
 * images.
 * 
 * @author mia
 *
 */
public interface RasterView {

	/**
	 * Creates the representation of image in {@code raster}.
	 * @param raster
	 *            raster whose picture/image will produce
	 * @return produced object
	 */
	public Object produce(BWRaster raster);

}
