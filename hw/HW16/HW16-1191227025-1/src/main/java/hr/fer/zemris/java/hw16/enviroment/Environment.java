package hr.fer.zemris.java.hw16.enviroment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw16.BagOfWords;
import hr.fer.zemris.java.hw16.Document;

/**
 * The abstraction that implements the communication between user and each
 * command.
 * 
 * @author mia
 *
 */
public interface Environment {

	/**
	 * Reads line form the stream input.
	 * 
	 * @return read text
	 * @throws IOException
	 *             if input stream broke while reading
	 */
	String readLine() throws IOException;

	/**
	 * Writes text on output stream.
	 * 
	 * @param text
	 *            text to be written.
	 * @throws IOException
	 *             if output stream broke.
	 */
	void write(String text) throws IOException;

	/**
	 * Writes text on output stream appended with new line.
	 * 
	 * @param text
	 *            text to be written.
	 * @throws IOException
	 *             if output stream broke.
	 */
	void writeln(String text) throws IOException;

	/**
	 * @return bag of words used in this enviroment.
	 */
	public BagOfWords getBagOfWords();

	/**
	 * Sets bag of words used in this enviroment.
	 * 
	 * @param bagOfWords
	 *            bag of words used in this enviroment.
	 */
	public void setBagOfWords(BagOfWords bagOfWords);

	/**
	 * Gets the list of documents gotten by the last query command.
	 * 
	 * @return List of documents gotten by the last query command.
	 */
	public List<Map.Entry<Document, Double>> getDocuments();

	/**
	 * List of documents gotten by the last query command.
	 * 
	 * @param documents
	 *            the list of documents gotten by the last query command.
	 */
	public void setDocuments(List<Map.Entry<Document, Double>> documents);

	/**
	 * Writes document on index index on output stream appended with new line.
	 * 
	 * @param index
	 *            document to be written.
	 * @throws IOException
	 *             if output stream broke.
	 */
	public void writeDocument(int index) throws IOException;

	/**
	 * Printing the document list currently store in {@code this}
	 * {@link Environment} instance.
	 * 
	 * @throws IOException
	 *             if output stream broke.
	 */

	public void printResult() throws IOException;
}
