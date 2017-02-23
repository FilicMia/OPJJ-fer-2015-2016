package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;

/**
 * Program create user defined geometric shapes, draw it on raster and displays
 * on standard output. Program expects user to provide either a single
 * command-line argument or two command-lines arguments. In case that user
 * provides a single argument, its value is interpreted as width and height of
 * raster. In case that user provides two arguments, first is treated as width
 * of raster and second as height of raster. In case there are zero arguments or
 * more than two arguments program writes appropriate message end terminate. In
 * case arguments can not be interpreted as numbers (or are inappropriate),
 * again a message is written and program terminates.
 * 
 * @author Mia FIlić
 *
 */
public class Demo {
	/**
	 * Method invoked when starting the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		if (args.length > 2) {
			System.err.println("There is no more than 2 arguments expected.");
			System.exit(-1);
		}
		int width = 0, height = 0;
		try {
			width = Integer.parseInt(args[0]);
			height = args.length == 2 ? Integer.parseInt(args[1]) : width;
		} catch (NumberFormatException e) {
			System.err.println("Integers are expected.");
			System.exit(0);
		}

		BWRaster raster = createRaster(width, height);
		GeometricShape[] shapes = null;
		try {
			shapes = UserInputHandler.handleInput();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		RanderShapes.randerFromArrayToStdout(raster, shapes);
		RasterView view = new SimpleRasterView();
		view.produce(raster);

	}

	/**
	 * Supplementary method that cares about creating raster of specified
	 * dimensions.
	 * 
	 * @param width
	 *            width of raster
	 * @param height
	 *            height of raster
	 * @return raster of specific dimensions
	 */
	private static BWRaster createRaster(int width, int height) {
		BWRaster raster = null;
		try {
			raster = new BWRasterMem(width, height);

		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		return raster;
	}

}
