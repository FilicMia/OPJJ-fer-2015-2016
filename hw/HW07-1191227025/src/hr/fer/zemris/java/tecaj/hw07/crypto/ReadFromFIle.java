/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Class reads bytes from file
 * 
 * @author Mia Filic
 *
 */
public class ReadFromFIle {

	/** Initial size of byte array in which to put read data. */
	private static final int SIZE = 1024;

	/**
	 * Reads from input stream {@code is} and sends read bytes to {@code action}.
	 * 
	 * @param is
	 *            input stream to read bytes from
	 * @param action
	 *            object to whom to send that byes to be processed.
	 */
	public static void read(InputStream is, Action action) {
		int len = SIZE;
		byte[] bytes = new byte[len];
		int read = 1;
		try {
			while((read = is.read(bytes, 0, len)) > 0){
				action.update(Arrays.copyOfRange(bytes, 0, read));
			}
		} catch (IOException e) {
			ExcaptionHandler.handle(e);
		}
		
		action.end();
	}

}
