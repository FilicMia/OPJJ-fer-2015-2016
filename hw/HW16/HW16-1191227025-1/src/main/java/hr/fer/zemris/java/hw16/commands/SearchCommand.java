package hr.fer.zemris.java.hw16.commands;

import java.io.IOException;

import hr.fer.zemris.java.hw16.enviroment.Environment;
import hr.fer.zemris.java.hw16.status.SearchStatus;
/**
 * Interface of command's available in application.
 * 
 * @author mia
 *
 */
public interface SearchCommand {

	/**
	 * Method executing the command.
	 * 
	 * @param env
	 *            environment communication class.
	 * @param arguments
	 *            everything that user entered AFTER the command name.
	 * @return appStatus after the execution
	 * @throws IOException
	 *             if environment write/read error occurred
	 */
	SearchStatus executeCommand(Environment env,String arguments)
			throws IOException;

	/**
	 * Method getting the command name.
	 * 
	 * @return command name
	 * 
	 */
	String getCommandName();
}
