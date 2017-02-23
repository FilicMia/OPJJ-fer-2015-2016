package hr.fer.zemris.java.hw16.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw16.Document;
import hr.fer.zemris.java.hw16.enviroment.Environment;
import hr.fer.zemris.java.hw16.status.SearchStatus;

/**
 * Command query. Takes one argument. String containing words. words are parsed
 * by all non-letter symbols. Then new {@link Document} is created of that words
 * which will be check for similarity with already parsed documents saved in
 * {@link Trazilica#getBagOfWords}.
 * 
 * @author mia
 *
 */
public class QuerySearchCommand implements SearchCommand {

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "query";

	@Override
	public SearchStatus executeCommand(Environment env, String arguments)
			throws IOException {
		List<String> words = Arrays
				.asList(arguments.split("[^a-zA-ZčČćĆšŠđĐžŽ]+"));
		Map<String, Integer> docContent = new HashMap<>();

		words.forEach(word -> {
			if (!word.isEmpty()) {
				docContent.put(word.trim().toLowerCase(),
						docContent.getOrDefault(word, 0) + 1);
				System.out.println(word+" "+docContent.get(word));
			}
		});

		StringBuffer sb = new StringBuffer("Query is:[");
		docContent.forEach((k, v) -> {
			sb.append(k + ",");
		});

		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");

		try {
			env.writeln(sb.toString());
		} catch (IOException e1) {
		}

		List<Map.Entry<Document, Double>> queryResult = env.getBagOfWords()
				.findMatchingMapFor(
						new Document(null, docContent, env.getBagOfWords()));
		queryResult.removeIf(entry -> {
			return entry.getValue() == 0;
		});
		if (queryResult.size() > 10) {
			queryResult.subList(10, queryResult.size()).clear();
		}
		env.setDocuments(queryResult);
		System.out.println("Najboljih 10 rezultata:");
		env.printResult();

		return SearchStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {

		return COMMANDNAME;
	}

}
