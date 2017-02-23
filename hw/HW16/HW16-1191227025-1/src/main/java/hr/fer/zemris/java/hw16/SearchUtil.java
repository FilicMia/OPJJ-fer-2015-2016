/**
 * 
 */
package hr.fer.zemris.java.hw16;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * Class holding the utility methods for application {@link Trazilica}.
 * 
 * @author mia
 *
 */
public class SearchUtil {

	/**
	 * Reads file from provided path and forms the map of words found in file,
	 * ignoring fords gotten in {@code ignorable} set.
	 * 
	 * Creating map of all words in file(excluding ones in ignorable set) in
	 * lower case form.
	 * 
	 * @param path
	 *            path to the file that should be processed
	 * @param ignorable
	 *            word that should be ignored.
	 * @return map of words it contains(which shouldn't be ignored) mapped to
	 *         it's occurrence number
	 * @throws IOException
	 *             if reading from file went wrong.
	 */
	public static Map<String, Integer> extractFile(Path path,
			Set<String> ignorable) throws IOException {
		
		Map<String, Integer> map = new HashMap<>();
		if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
			System.err.println("File can not be read: " + path.getFileName());
			System.exit(-1);
		}

		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		lines.forEach(line -> {
			String[] words = line.split("[^a-zA-ZčČćĆšŠđĐžŽ]+");
			List<String> coutingWords = Arrays.asList(words);
			coutingWords.forEach(word -> {
				String lowerCase = word.toLowerCase();
				if (!word.isEmpty() && !ignorable.contains(lowerCase)) {
					map.put(lowerCase, map.getOrDefault(word, 0) + 1);
				}
			});
		});
		return map;
	}

	/**
	 * Method reading ignorable words and saving it into list in lower case.
	 * 
	 * @param name
	 *            name of file holding ignorable words in lower case.
	 * @return list of words that should be ignored.
	 * @throws IOException
	 *             if reading file went wrong.
	 */
	public static List<String> readIgnorableWords(String name)
			throws IOException {
		Path path = Paths.get(name);
		List<String> ignorable = Files.readAllLines(path,
				StandardCharsets.UTF_8);
		
		List<String> trimmed = new ArrayList<>();
		ignorable.forEach(line -> {
			if (!line.isEmpty()){
				trimmed.add(line.trim().toLowerCase());
			}
		});

		return trimmed;

	}

	/**
	 * Sorting the map my its values.
	 * 
	 * @param map
	 *            map to be sorted.
	 * @return list of sorted entries..
	 */
	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(
			Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(
				map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}

}
