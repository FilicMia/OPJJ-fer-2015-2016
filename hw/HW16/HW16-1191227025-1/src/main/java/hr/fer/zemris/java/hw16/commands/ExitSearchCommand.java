package hr.fer.zemris.java.hw16.commands;

import hr.fer.zemris.java.hw16.enviroment.Environment;
import hr.fer.zemris.java.hw16.status.SearchStatus;

/**
 * Command termminates the program. Takes no arguments. If arguments are
 * provided, they are ignored.
 * 
 * @author mia
 *
 */
public class ExitSearchCommand implements SearchCommand {

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "exit";

	@Override
	public SearchStatus executeCommand(Environment env,String arguments) {
		return SearchStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		
		return COMMANDNAME;
	}

}
