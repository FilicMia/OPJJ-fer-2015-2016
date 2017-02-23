package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class sends the given data in output stream.
 * 
 */
public class WriteInFIle {
	/**
	 * Sends the given data in output stream.
	 * 
	 * @param output
	 *            bytes that should be sent to output stream {@code os}}
	 * @param os
	 *            output stream
	 * @throws IOException
	 *             if output stream broke
	 * 
	 */
	public static void write(byte[] output, OutputStream os) {
		try {
			os.write(output);
		} catch (IOException e) {
			ExcaptionHandler.handle(e);
		}

	}
}
