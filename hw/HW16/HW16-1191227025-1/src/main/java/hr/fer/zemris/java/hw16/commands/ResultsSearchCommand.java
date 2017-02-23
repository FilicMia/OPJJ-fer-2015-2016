package hr.fer.zemris.java.hw16.commands;

import java.io.IOException;

import hr.fer.zemris.java.hw16.enviroment.Environment;
import hr.fer.zemris.java.hw16.status.SearchStatus;

/**
 * Command query. Takes no argument. When thos command is called all documents
 * found in last "query" command and stored in
 * {@link Environment#getDocuments()} variable are printed in form:
 * 
 * Najboljih 10 rezultata:
 * 
 * [ 0] (0.1222) d:\clanci\vjesnik-1999-12-7-kul-5
 * 
 * [ 1] (0.0151)d:\clanci\vjesnik-1999-12-3-kul-3
 * 
 * [ 2](0.0104)d:\clanci\vjesnik-1999-9-5-kul-2
 * 
 * @author mia
 *
 */
public class ResultsSearchCommand implements SearchCommand {

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "results";

	@Override
	public SearchStatus executeCommand(Environment env, String arguments) throws IOException {
		env.printResult();
		return SearchStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {

		return COMMANDNAME;
	}

}
