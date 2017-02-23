package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * Interface of each shell command.
 * 
 * @author Mia Filić
 *
 */
public interface ShellCommand {

	/**
	 * Method executing the command.
	 * 
	 * @param env
	 *            environment communication class.
	 * @param arguments
	 *            everything that user entered AFTER the command name. It is
	 *            expected that in case of multiline input, the shell has
	 *            already concatenated all lines into a single line and removed
	 *            MORELINES symbol from line endings (before concatenation)
	 * @return shellStatus after the execution
	 * @throws IOException
	 *             if environment write/read error occurred
	 */
	ShellStatus executeCommand(Environment env, String arguments)
			throws IOException;

	/**
	 * Method getting the command name.
	 * 
	 * @return command name
	 * 
	 */
	String getCommandName();

	/**
	 * Method returns the description of the command.
	 * 
	 * @return string representing the command description.
	 */
	List<String> getCommandDescription();

}
