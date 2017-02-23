/**
 * 
 */
package hr.fer.zemris.java.hw16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Class holding all words that are found in folder of documents.
 * 
 * @author mia
 *
 */
public class BagOfWords {

	/**
	 * Map storing all words found that are mapped to the number of documents in
	 * folder containing that word.
	 */
	private Map<String, Integer> bag;

	/** Index to word mapping. */
	private List<String> indexes;

	/**
	 * Path to file holding ignorable words.
	 */
	private static String PATH_IGNORABLE = "hrvatski_stoprijeci.txt";

	/**
	 * List of words that bag should not contain. Like conjunction
	 * words,prepositions ect. All words that has no much meaning when standing
	 * alone.
	 */
	private List<String> ignorableWords;

	/** Number of documents included. */
	private int documentCount;

	/** List of all documents in the given folder. */
	private List<Document> documents;

	{
		try {
			ignorableWords = SearchUtil.readIgnorableWords(PATH_IGNORABLE);
		} catch (IOException e) {
			System.err.println("Error while reading ignorable words.");
			System.exit(-1);
		}

		bag = new HashMap<>();
		documents = new ArrayList<>();
	}

	/**
	 * Creating the bag of words.
	 * 
	 * @param path
	 *            absolute path to the folder storing documents that should be
	 *            listed.
	 * @throws IOException
	 *             if reading went wrong
	 */
	public BagOfWords(String path) throws IOException {
		Map<Path, Map<String, Integer>> files = new HashMap<>();
		Files.walk(Paths.get(path)).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				Map<String, Integer> words = null;
				try {
					words = SearchUtil.extractFile(filePath,
							new HashSet<String>(ignorableWords));
					words.forEach((k, v) -> {
						bag.put(k, bag.getOrDefault(k, 0) + 1);// one doc more
																// contains
																// this word
					});

				} catch (Exception e) {
					System.err.println("Error while extracting files.");
					System.exit(-1);
				}

				files.put(filePath, words);
			}
		});
		documentCount = files.size();
		indexes = new ArrayList<>();

		bag.forEach((word, v) -> indexes.add(word));
		files.forEach((k, v) -> {
			System.err.println(k);
			Document document = new Document(k, v, BagOfWords.this);
			documents.add(document);
		});
		System.out.println("Veličina riječnika je "+indexes.size()+" riječi.");
		
		System.out.println("ZADNJI"+indexes.get(indexes.size()-1));
	}

	/**
	 * Returns the number of documents containing given word.
	 * 
	 * @param word
	 *            word which document count should be returned.
	 * @return count of documents containing given word.
	 */
	public int countOfDocsWith(String word) {
		return bag.getOrDefault(word, 0);
	}

	/**
	 * Returns all documents used in creation of bag of words.
	 * 
	 * @return list of all documents used in form of {@link Document}.
	 */
	public List<Document> getDocuments() {
		return documents;
	}
	

	/**
	 * Returns word stored in specific index.
	 * 
	 * @param index
	 *            index of the word returned
	 * @return word on provided index.
	 */
	public String get(int index) {
		return indexes.get(index);
	}

	/**
	 * Returns the count of documents containing word on specific index.
	 * 
	 * @param index
	 *            index of the word
	 * @return count of documents containing word on specific index.
	 */
	public int getCountOfDocsWith(int index) {
		return bag.get(indexes.get(index));
	}

	/**
	 * @return the list of all words in bag, the palace in list correspon the
	 *         index of the word in bag
	 */
	public List<String> getWords() {
		return indexes;
	}

	/**
	 * @return count of document considered.
	 */
	public int getDocumentCount() {
		return documentCount;
	}

	/**
	 * Calculates sim value of doc to each document stored in {@link #documents}
	 * . Results is map of documents mapped to its sim value due to passed doc.
	 * 
	 * @param doc
	 *            Document which will be check for similarity upon all document
	 *            in {@link #documents}
	 * @return map of documents mapped to its sim value due to passed doc.
	 */
	public List<Map.Entry<Document, Double>> findMatchingMapFor(Document doc) {
		Map<Document, Double> result = new HashMap<>();

		documents.forEach((document) -> {
			result.put(document, Document.getSim(doc, document));
		});

		return SearchUtil.entriesSortedByValues(result);

	}

}
