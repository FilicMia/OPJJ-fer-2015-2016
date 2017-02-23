/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;

/**
 * Class implementing action that calculates the digest of the given file.
 * 
 * @author Mia Filic
 *
 */
public class Digest implements Action {
	/** Used algorithm. */
	public static final String ALGORITHM = "SHA-256";

	/**
	 * Digest calculator.
	 */
	MessageDigest md;
	/**
	 * Calculated digest.
	 */
	byte[] mdbytes;
	/**
	 * String representation of calculated digest.
	 */
	String stringMdBytes;

	{
		try {
			md = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

	}

	@Override
	public void update(byte[] byteFile) {
		md.update(byteFile, 0, byteFile.length);
	}

	/**
	 * Returns the String representation of calculated digest.
	 * 
	 * @return calculated digest.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		stringMdBytes = sb.toString();
		return stringMdBytes;
	}

	@Override
	public void end() {
		mdbytes = md.digest();
	}

	/**
	 * Checks if given digest matches the current one stored.
	 * 
	 * @param expectedDigest
	 *            string representation of expected digest.
	 * @return {@code true} if current one matches the {@code expectedDigest},
	 *         {@code false} otherwise
	 */
	public boolean matches(String expectedDigest) {
		if (expectedDigest == null) {
			return false;
		}
		Comparator<byte[]> comparator = new Comparator<byte[]>() {

			@Override
			public int compare(byte[] o1, byte[] o2) {
				if (o1.length != o2.length) {
					return -1;
				}
				for (int i = 0; i < o1.length; ++i) {
					if (o1[i] != o2[i]) {
						return -1;
					}
				}
				return 0;
			}
		};

		return comparator.compare(mdbytes, Converter.hextobyte(expectedDigest)) == 0;
	}

}
