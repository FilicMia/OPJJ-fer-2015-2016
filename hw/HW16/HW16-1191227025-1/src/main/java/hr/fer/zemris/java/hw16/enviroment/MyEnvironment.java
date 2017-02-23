package hr.fer.zemris.java.hw16.enviroment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw16.BagOfWords;
import hr.fer.zemris.java.hw16.Document;

/**
 * Implements environment interface.
 * 
 * @author mia
 *
 */
public class MyEnvironment implements Environment {

	/**
	 * Input stream reader.
	 */
	private static BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

	/** Bag of words for this enviroment. */
	private BagOfWords bagOfWords;
	/** List of documents gotten by the last query command. */
	private List<Map.Entry<Document, Double>> documents;

	/**
	 * Constructor.
	 * 
	 * @param bagOfWords
	 *            bag of words used in this enviroment.
	 * 
	 */
	public MyEnvironment(BagOfWords bagOfWords) {
		this.bagOfWords = bagOfWords;

	}

	@Override
	public String readLine() throws IOException {
		return br.readLine();
	}

	@Override
	public void write(String text) throws IOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws IOException {
		System.out.println(text);
	}

	@Override
	public BagOfWords getBagOfWords() {
		return bagOfWords;
	}

	@Override
	public void setBagOfWords(BagOfWords bagOfWords) {
		this.bagOfWords = bagOfWords;
	}

	@Override
	public List<Map.Entry<Document, Double>> getDocuments() {
		return documents;
	}

	@Override
	public void setDocuments(List<Map.Entry<Document, Double>> documents) {
		this.documents = documents;
	}

	@Override
	public void writeDocument(int index) throws IOException {
		if (documents != null && index < documents.size()) {
			writeln("Document: "+documents.get(index).getKey().getPath().toAbsolutePath());
			documents.get(index).getKey().writeDocument();
		} else {
			writeln("No document with index provided: " + index);
		}

	}

	@Override
	public void printResult() throws IOException {
		if (documents == null) {
			return;
		}
		int count = documents.size();

		for (int i = 0; i < count; ++i) {
			Map.Entry<Document, Double> entry = documents.get(i);
			writeln("[" + i + "]" + "(" + String.format("%.4f",entry.getValue()) + ")"
					+ entry.getKey().getPath().toAbsolutePath());
		}

	}

}
