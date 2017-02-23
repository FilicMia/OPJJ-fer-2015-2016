/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.Charset;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Utility used from reading files.
 * 
 * @author mia
 *
 */
public class ReadUtil {

	/**
	 * Reads the file from the disk from the {@code path} path.
	 * 
	 * @param path
	 *            path from where the certain file shold be read.
	 * @param charset
	 *            charset due to which the file is read.
	 * @return {@code String} representing the read file.
	 */
	public static String readFromDisk(String path, Charset charset) {
		Objects.requireNonNull(path);
		String docBody = null;

		try {
			docBody = new String(Files.readAllBytes(Paths.get(path)), charset);
		} catch (IOException e) {
			System.err.println("File can not be read!");
			System.exit(-1);
		}
		return docBody;
	}

}
