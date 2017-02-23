package hr.fer.zemris.java.tecaj.hw07.crypto;

/**
 * Class used for handling exceptions.
 * 
 * @author Mia Filic
 *
 */
public class ExcaptionHandler {
	/**
	 * Handles passed exception.
	 * 
	 * @param e
	 *            exception to be handle.
	 */
	public static void handle(Exception e) {
		System.err.println(e.getMessage());
		System.exit(-1);
	}
}
