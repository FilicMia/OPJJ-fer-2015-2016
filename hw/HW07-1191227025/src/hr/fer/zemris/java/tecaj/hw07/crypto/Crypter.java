/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class implementing action that encrypts the given file.
 * 
 * @author Mia Filic
 *
 */
public class Crypter implements Action {
	/**
	 * Chiper used in encription of the goven file.
	 */
	private Cipher cipher = null;

	/**
	 * Output stream used for storing encrypted data.
	 */
	private OutputStream os;

	/**
	 * Constructor sets the key specification and initialization vector of the
	 * encrypter.
	 * 
	 * @param keyText
	 *            initialization key in text representation
	 * @param ivText
	 *            initialization vector in text representation
	 * @param os
	 *            output stream for storing created data.
	 * @param encrypt
	 *            indicator of encrypt mode of cryptor
	 * @param cipherMode
	 *            {@link Cipher.ENCRYPT_MODE} or {@link Cipher.DECRYPT_MODE}
	 * 
	 * 
	 * @throws IllegalArgumentException
	 *             if illegal arguments are passed
	 * @throws NoSuchAlgorithmException
	 *             if wrong algorithm is passed while instancing {@link Cipher}
	 * @throws NoSuchPaddingException
	 *             if wrong padding is passed while instancing {@link Cipher}
	 * @throws InvalidKeyException
	 *             if invalid key is passed when initiating cipher
	 * @throws InvalidAlgorithmParameterException
	 *             if invalid algorithm spec is passed when initiating cipher
	 * 
	 */
	public Crypter(String keyText, String ivText, OutputStream os,
			boolean encrypt) {
		if (keyText == null || ivText == null || os == null) {
			throw new IllegalArgumentException(
					"Wrong initializaion arguments.");
		}

		SecretKeySpec keySpec = new SecretKeySpec(Converter.hextobyte(keyText),
				"AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(
				Converter.hextobyte(ivText));
		cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			ExcaptionHandler.handle(e);
		} catch (NoSuchPaddingException e) {
			ExcaptionHandler.handle(e);
		}
		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
					keySpec, paramSpec);
		} catch (InvalidKeyException e) {
			ExcaptionHandler.handle(e);
		} catch (InvalidAlgorithmParameterException e) {
			ExcaptionHandler.handle(e);
		}
		this.os = os;
	}

	@Override
	public void update(byte[] byteFile) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteFile.length; i++) {
			sb.append(Integer.toString((byteFile[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		//System.out.println(sb.toString());

		byte[] encrypted = cipher.update(byteFile, 0, byteFile.length);
		try {
			os.write(encrypted);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	@Override
	public void end() {
		try {
			WriteInFIle.write(cipher.doFinal(), os);

		} catch (IllegalBlockSizeException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (BadPaddingException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

	}

}
