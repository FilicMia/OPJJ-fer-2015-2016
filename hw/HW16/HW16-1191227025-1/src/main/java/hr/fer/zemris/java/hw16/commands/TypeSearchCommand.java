package hr.fer.zemris.java.hw16.commands;

import java.io.IOException;

import hr.fer.zemris.java.hw16.enviroment.Environment;
import hr.fer.zemris.java.hw16.status.SearchStatus;

/**
 * Command query. Takes one argument. String containing integer representing
 * index of document found in last "query" command and stored in
 * {@link Environment#getDocuments()} variable. COmmand prints the document
 * trough {@link Environment} variable.
 * 
 * @author mia
 *
 */
public class TypeSearchCommand implements SearchCommand {

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "type";

	@Override
	public SearchStatus executeCommand(Environment env, String arguments)
			throws IOException {
		arguments = arguments.trim();
		if (arguments.matches("[0-9]+")) {
			int index = Integer.parseInt(arguments);
			env.writeDocument(index);
		} else {
			env.writeln("Wrong input for commant type: " + arguments);
		}

		return SearchStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {

		return COMMANDNAME;
	}

}
