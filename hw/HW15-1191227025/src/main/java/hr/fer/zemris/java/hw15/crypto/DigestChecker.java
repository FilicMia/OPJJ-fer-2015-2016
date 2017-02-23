package hr.fer.zemris.java.hw15.crypto;

import java.security.MessageDigest;

/**
 * Class used for calculating the digest. using SHA algorithm.
 * 
 * @author mia
 *
 */
public class DigestChecker {

	/**
	 * Instance of MessageDigest. It is used in calculation.
	 */
	private static MessageDigest md;

	/**
	 * Method calculating the digest, using SHA algorithm.
	 * 
	 * @param str
	 *            string which digest will be calculated.
	 * @return digest of the passed string.
	 */
	public static String calculateSHA(String str) {

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (Exception e) {
			return null;
		}
		md.reset();
		md.update(str.getBytes());

		byte[] digest = md.digest();
		String ret = "";
		for (byte d : digest) {
			ret += String.valueOf((int) d);
		}
		return ret;
	}

	/**
	 * Method checking if the digest of the first argument corresponds the
	 * digest passed as second argument.
	 * 
	 * @param str1
	 *            string which digest is calculated.
	 * @param str2
	 *            digest to which the digest of the str1 is compared
	 * @return true if digest are the samo, false otherwise.
	 */
	public static boolean check(String str1, String str2) {
		return calculateSHA(str1).equals(str2);
	}
}