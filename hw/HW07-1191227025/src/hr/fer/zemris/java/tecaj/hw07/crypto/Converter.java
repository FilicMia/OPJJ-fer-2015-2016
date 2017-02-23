package hr.fer.zemris.java.tecaj.hw07.crypto;

import javax.xml.bind.DatatypeConverter;

/**
 * Converters the data type form one type to another.
 * 
 * @author Mia Filic
 *
 */
public class Converter {
	
	/**
	 * Method that converts the string representation of
	 * hex-digits into byte array.
	 * 
	 * @param keyText
	 *            string to be converter into bytes
	 * @return byte representation of input string of hex-digits
	 */
	public static byte[] hextobyte(String keyText) {
		int len = keyText.length();
		if (len % 2 != 0) {
			throw new IllegalArgumentException("Argument should be divided by 2.");
		}

		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}
		return data;
		//return DatatypeConverter.parseHexBinary(keyText);
	}


}
