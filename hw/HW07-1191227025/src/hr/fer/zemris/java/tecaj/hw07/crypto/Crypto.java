package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * Program that allows the user to check checksha,encrypt,decrypt files. It uses
 * the AES cryptography algorithm. It accepts two command line arguments: action
 * (checksha,encrypt,decrypt) and the relative path to the file.
 * 
 * @author Mia Filić
 *
 */
public class Crypto {

	/** Signals to start the decryption on given file. */
	private static final String DECRYPT = "decrypt";

	/** Signals to start the encryption on given file. */
	private static final String ENCRYPT = "encrypt";

	/** Signals to check the digest of given file. */
	private static final String DIGEST = "checksha";

	/** Used crypting algorithm. */
	public static final String ALGORITHM = "AES";

	/**
	 * Method invoked when running the program.
	 * 
	 * @param args
	 *            command-lines arguments
	 * @throws NoSuchAlgorithmException
	 *             if wrong Algorithm is passed in crypto while initializing
	 * @throws IOException
	 *             reader broke
	 * @throws NoSuchAlgorithmException
	 *             if wrong algorithm is passed while instancing {@link Cipher}
	 * @throws NoSuchPaddingException
	 *             if wrong padding is passed while instancing {@link Cipher}
	 * @throws InvalidKeyException
	 *             if invalid key is passed when initiating cipher
	 * @throws InvalidAlgorithmParameterException
	 *             if invalid algorithm spec is passed when initiating cipher
	 */
	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));

		if (args.length < 1) {
			throw new IllegalArgumentException(
					"Action need to be specificied.");
		}
		boolean encrypter = false;

		try (InputStream is = new BufferedInputStream(
				new FileInputStream(args[1]))) {

			switch (args[0].toLowerCase()) {
			case (ENCRYPT):
				encrypter = true;
			case (DECRYPT):
				if (args.length != 3) {
					throw new IllegalArgumentException("Usage: action file");
				}
				crypter(is, br, args[1], args[2], encrypter);
				break;
			case (DIGEST):
				if (args.length != 2) {
					throw new IllegalArgumentException("Usage: action file");
				}
				digest(is, br, args[1]);
				break;
			default:
				throw new IllegalArgumentException("Invalit action is passed.");

			}
			is.close();

		} catch (FileNotFoundException e) {
			ExcaptionHandler.handle(e);
		} catch (IOException e) {
			ExcaptionHandler.handle(e);
		}

	}

	/**
	 * Method process the digest action.
	 * 
	 * @param is
	 *            input stream
	 * @param br
	 *            reader; getting the information about expected digest.
	 * @param fileName
	 *            name of file on which the action is done
	 * @throws IOException
	 *             if reader broke
	 */
	private static void digest(InputStream is, BufferedReader br,
			String fileName) {
		Digest myDigest = new Digest();
		System.out.println(
				"Please provide expected sha-256 digest for " + fileName + ":");
		System.out.print("> ");

		String expectedDigest = null;
		try {
			expectedDigest = br.readLine();
		} catch (IOException e) {
			ExcaptionHandler.handle(e);
		}

		ReadFromFIle.read(is, myDigest);

		if (myDigest.matches(expectedDigest)) {
			System.out.println("Digesting completed. Digest of  " + fileName
					+ " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + fileName
					+ " does not match the expected digest. Digest");
			System.out.println("was: " + myDigest);
		}

	}

	/**
	 * Method process the dencrypt action.
	 * 
	 * @param is
	 *            input stream
	 * @param br
	 *            reader; getting the information about initial vector and
	 *            expected digest.
	 * @param inputFile
	 *            input file name
	 * @param outputFile
	 *            name of file where to store output of criptography action
	 * @param encrypter
	 *            indicates if the action called is encryption or
	 *            not(decryption)
	 * 
	 * 
	 * 
	 * @throws IOException
	 *             reader broke
	 * @throws NoSuchAlgorithmException
	 *             if wrong algorithm is passed while instancing {@link Cipher}
	 * @throws NoSuchPaddingException
	 *             if wrong padding is passed while instancing {@link Cipher}
	 * @throws InvalidKeyException
	 *             if invalid key is passed when initiating cipher
	 * @throws InvalidAlgorithmParameterException
	 *             if invalid algorithm spec is passed when initiating cipher
	 */
	private static void crypter(InputStream is, BufferedReader br,
			String inputFile, String outputFile, boolean encrypter) {
		System.out.println("Please provide password as hex-encoded "
				+ "text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = null;
		String ivText = null;
		try {
			keyText = br.readLine();
		} catch (IOException e1) {
			ExcaptionHandler.handle(e1);
		}

		System.out.println("Please provide initialization vector as "
				+ "hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		try {
			ivText = br.readLine();
		} catch (IOException e) {
			ExcaptionHandler.handle(e);
		}
		// keyText = "a52217e3ee213ef1ffdee3a192e2ac7e";
		// ivText = "000102030405060708090a0b0c0d0e0f";

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(outputFile));
		} catch (FileNotFoundException e) {
			ExcaptionHandler.handle(e);
		}

		Crypter myDecrypter = new Crypter(keyText, ivText, os, encrypter);
		ReadFromFIle.read(is, myDecrypter);

		try {
			os.close();
		} catch (IOException e) {
			ExcaptionHandler.handle(e);
		}
		System.out.println("Decryption completed. Generated " + outputFile
				+ " based on file " + inputFile);
	}

}
