/**
 * 
 */
package hr.fer.zemris.java.hw16;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;
import java.util.Map;

/**
 * Class representing one document. It holds its absolute path, TF-IDF vector.
 * 
 * @author mia
 *
 */
public class Document {
	/** Path to the document. */
	private Path path;

	/** TF-IDF vector of document. */
	private double[] tfidfVector;

	/**
	 * My content. Map of all significant.
	 */
	private Map<String, Integer> myContent;

	/**
	 * COnstructor.
	 * 
	 * @param path
	 *            path to the file
	 * @param myContent
	 *            content of the file; all significant words mapped to its
	 *            number of occurrence.
	 * @param bagOfWords
	 *            bag of words due to which the tfidfVector should be calculated.
	 */
	public Document(Path path, Map<String, Integer> myContent,
			BagOfWords bagOfWords) {
		this.path = path;
		this.myContent = myContent;
		calculateVector(bagOfWords);
	}

	/**
	 * Writing file on provided standard output stream.
	 * 
	 * @throws IOException
	 *             if reading from file went wrong
	 * 
	 */
	public void writeDocument() throws IOException {
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		lines.forEach(line -> {
			System.out.println(line);
		});
	}

	/**
	 * Calculating and storing TF-IDF vector of document due to
	 * {@code bagOfWords}.
	 * 
	 * @param bagOfWords
	 *            bag of words due to which the tfidfVector should be calculated.
	 */
	private void calculateVector(BagOfWords bagOfWords) {
		List<String> words = bagOfWords.getWords();
		int count = words.size();

		tfidfVector = new double[count];
		for (int i = 0; i < count; ++i) {
			tfidfVector[i] = (double) myContent.getOrDefault(words.get(i), 0)
					* Math.log((double) (bagOfWords.getDocumentCount())
							/ (double) bagOfWords.countOfDocsWith(words.get(i)));
			
			//tfidfVector[i] = Math.round(tfidfVector[i]*10000);
			//tfidfVector[i] = tfidfVector[i]/10000;
			
		}

	}

	/**
	 * Returns the path of the document.
	 * 
	 * @return the path of the document.
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @return the {@link #tfidfVector} for this document
	 */
	public double[] getTfidfVector() {
		return tfidfVector;
	}

	/**
	 * Calculates the similarity of 2 provided documents.
	 * 
	 * @param doc1
	 *            first document which will be check for similarity
	 * @param doc2
	 *            second document which will be check for similarity
	 * @return the similarity measure.
	 */
	public static double getSim(Document doc1, Document doc2) {
		double sim = 0;
		double[] docTfidfVector = doc1.getTfidfVector();
		double[] tfidfVector = doc2.getTfidfVector();
		if (docTfidfVector.length != tfidfVector.length) {
			return 0;
		}
		double normA = 0;
		double normB = 0;

		for (int i = 0; i < tfidfVector.length; ++i) {
			sim += tfidfVector[i] * docTfidfVector[i];
			normA += tfidfVector[i] * tfidfVector[i];
			normB += docTfidfVector[i] * docTfidfVector[i];
		}

		sim = (double) sim / (Math.sqrt(normA) * Math.sqrt(normB));
		return sim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	

}
