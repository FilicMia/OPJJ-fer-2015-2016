/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw07.crypto;

/**
 * Interface that implements all {@link Crypto} action's classes.
 * 
 * @author Mia FIlic
 *
 */
public interface Action {
	/**
	 * Updates the certain action with new stream of bytes.
	 * 
	 * @param byteFile
	 *            new byte stream.
	 */
	void update(byte[] byteFile);

	/** Finalizes the action. */
	void end();
}
