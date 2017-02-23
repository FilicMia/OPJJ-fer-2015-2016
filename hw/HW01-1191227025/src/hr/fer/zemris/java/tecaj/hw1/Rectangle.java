package hr.fer.zemris.java.tecaj.hw1;

//import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Program which calculates area and perimeter of an specified rectangle.
 * Rectangle is specified by 2 command line arguments or trough standard input
 * by user(width and heigh). Width and height are nonnegative numbers.
 * 
 * @author Mia
 * @version 1.0
 */
public class Rectangle {
	
	/**
	 * Method which starts the program.
	 * @param args 
	 * 			width and heigth of the rectangle or nothing
	 */
	public static void main (String[] args) throws IOException  {
		int brojArgumenata = args.length;
		double width,heigth;
		
		if (brojArgumenata != 0) {
			if (args.length != 2) {
				System.out.println("Invalid number of arguments was provided.");
			} else {
				// we assume that they are numbers
				width = Double.parseDouble(args[0]);
				heigth = Double.parseDouble(args[1]);
				if(width < 0 || heigth < 0){
					System.err.println("Illegal arguments: "+width+" "+heigth);
					System.exit(1);
				}
				System.out
						.printf("You have specified a rectangle with width %f and height %f. Its area is %f and its perimeter is %f.",
								width, heigth, area(heigth, width),
								perimeter(heigth, width));

			}

		} else {
		
		// if there is no command line arguments,get informations from std input
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(System.in)
				);
				// providing width
				width = read(reader, "width");
				// providing height
				heigth = read(reader, "height");

				System.out.format(
						"You have specified a rectangle with width " + width
								+ " and height " + heigth + ". Its area is "
								+ area(heigth, width) + " and its perimeter is "
								+ perimeter(heigth, width) + ".", width, heigth,
						area(heigth, width), perimeter(heigth, width));

				reader.close();

			}
	}
	
	/**
	 * Method gets "width" or "height" (depends on string s) form user(standard input).
	 * Returns gotten number.
	 * Throws exception if there is a mistake in standard input reading.
	 * 
	 * @param reader
	 *            BufferedReader,reads from standard input
	 * @param s
	 *            string ,"width" or "height"
	 * @return double, real number
	 * @throws IOException
	 */
	private static double read(BufferedReader reader, String s)
			throws IOException {

		String line = null;
		String capitalLetterS = String.format("%s%s", s.substring(0, 1)
				.toUpperCase(), s.substring(1, s.length()));

		double number = 0;

		while (true) {
			// we don't stop until we got what we want
			System.out.format("Please provide %s:%n", s);
			line = reader.readLine();

			if (!line.trim().isEmpty()) {
				// if the line has some number
				number = Double.parseDouble(line);
				if (number < 0) {
					System.out.format("%s is negative.%n", capitalLetterS);
					continue;
				} else {
					return number;
				}
			} else {
				System.out.println("Nothing was given.");
				continue;
			}

		}

	}

	
	
	
	/**
	 * Method calculates rectangle's perimeter.
	 * 
	 * @param height
	 *            height of rectangle
	 * @param width
	 *            width of rectangle
	 */
	private static double perimeter(double heigth, double width) {
		return 2*width + 2* heigth;
	}

	/**
	 * 
	 * Method calculates rectangle's area.
	 * 
	 * @param height
	 *            height of rectangle
	 * @param width
	 *            width of rectangle
	 */
	private static double area(double heigth, double width) {
		
		return heigth * width;
	}
	

}
