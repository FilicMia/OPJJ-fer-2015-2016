package hr.fer.zemris.java.graphics.views;


import java.util.InputMismatchException;
import java.util.Scanner;

import hr.fer.zemris.java.graphics.shapes.*;

/**
 * Handles with the user input.
 * 
 * User will tell what he wants to create by typing, one shape per line. First
 * line will contain a number of shapes that follow. If user provided more than
 * said(in first input) additional lines, everything is discard but first line
 * In each line arguments are separated by one or more spaces (ascii 32).
 * 
 * 
 * Your program should create an array with references to instances of specified
 * objects. When you see “FLIP”, add null reference.
 * 
 * @author MIa Filić
 *
 */
public class UserInputHandler {

	/**
	 * Method that handles the input stream.
	 * 
	 * @return string array of lines of user input
	 * @throws Exception
	 *             when user provides an inappropriate input
	 */
	public static GeometricShape[] handleInput() throws Exception {
		Scanner sc = new Scanner(System.in);
		int numLines = sc.nextInt();
		GeometricShape[] shapes = new GeometricShape[numLines];

		sc.nextLine();
		for (int line = 0; line < numLines; ++line) {
			shapes[line] = handleLine(sc);
		}

		return shapes;
	}

	/**
	 * Method that handles one line of user input read by scanner. Every
	 * parametar in lines separated with one or more spaces.
	 * 
	 * @param sc
	 *            scanner
	 * @return geometric shape defined by specific line
	 * @throws Exception
	 *             if the user input is invalid
	 */
	private static GeometricShape handleLine(Scanner sc) throws Exception {
		String nameOfShape = null;
		String pattern = "[a-zA-Z]+";
		if (sc.hasNext(pattern)) {
			nameOfShape = sc.next(pattern);
		} else {
			throw new IllegalArgumentException("Invalid input stream.");
		}

		switch (nameOfShape.toUpperCase()) {
		case Rectangle.name:
			return new Rectangle(parseInt(sc), parseInt(sc), parseInt(sc),
					parseInt(sc));
		case Square.name:
			return new Square(parseInt(sc), parseInt(sc), parseInt(sc));

		case Ellipse.name:
			return new Ellipse(parseInt(sc), parseInt(sc), parseInt(sc),
					parseInt(sc));

		case Circle.name:
			return new Circle(parseInt(sc), parseInt(sc), parseInt(sc));
		case "FLIP":
			return null;
		default:
			System.err.println("Unknown shape!");
			System.exit(-2);

		}
		return null;
	}

	/**
	 * Supplementary method that reads the int from the {@code Scanner} sc. If
	 * the next thing to read is not valid {@code int}, exception is thrown.
	 * 
	 * @param sc
	 *            Scanner by that the integer parsing is done.
	 * @return read integer value
	 * 
	 * @throws InputMismatchException
	 *             if the next thing to read is not valid {@code int}.
	 */
	public static int parseInt(Scanner sc) {
		if (sc.hasNextInt()) {
			return sc.nextInt();
		} else {
			throw new IllegalArgumentException(
					"Invalid input stream line with arguments.");
		}
	}
}
