/**
 * 
 */
package hr.fer.zemris.java.hw16;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.hw16.commands.ExitSearchCommand;
import hr.fer.zemris.java.hw16.commands.QuerySearchCommand;
import hr.fer.zemris.java.hw16.commands.ResultsSearchCommand;
import hr.fer.zemris.java.hw16.commands.SearchCommand;
import hr.fer.zemris.java.hw16.commands.TypeSearchCommand;
import hr.fer.zemris.java.hw16.enviroment.Environment;
import hr.fer.zemris.java.hw16.enviroment.MyEnvironment;
import hr.fer.zemris.java.hw16.status.SearchStatus;

/**
 * Program which searches trough list of files saved in folder passed as command
 * line argument.
 * 
 * Trazilica saves the list of files last found to provide commands like:
 * 
 * “type”: gets results index and writes document on the screen with its
 * absolute path.
 * 
 * “results”: rewrites rang-list found in last “query” command.
 * 
 * @author mia
 *
 */
public class Trazilica {
	/**
	 * Enviroment for this trazilica. It will be initializaed in
	 * {@link #initApp(String)}
	 */
	private static Environment env;

	/**
	 * Method called when invoking the program.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 *             if initialization went wrong
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println(
					"Only one argument should be provided: absolute path to the "
							+ "folder with documents.");
			System.exit(-1);
		}

		startApp(args[0]);

	}

	/**
	 * Method initializing the application. Reads all document from folder,
	 * preparing the bag of words as well as environment of the application.
	 * 
	 * @param path
	 *            absolute path to the folder of documents used
	 * @throws IOException
	 *             if initialization went wrong
	 */
	private static void startApp(String path) throws IOException {
		BagOfWords bagOfWords = new BagOfWords(path);
		Map<String, SearchCommand> commands = new HashMap<>();
		commands.put("exit", new ExitSearchCommand());
		commands.put("query", new QuerySearchCommand());
		commands.put("type", new TypeSearchCommand());
		commands.put("results", new ResultsSearchCommand());

		env = new MyEnvironment(bagOfWords);

		sessionProcessor(env, commands);

	}

	/**
	 * Method processing the one user session.
	 * 
	 * @param e
	 *            type of communication between user and shell.
	 * @param commands
	 *            commands available in this application.
	 * @throws IOException
	 *             if IO fails - communication between command and user
	 */
	private static void sessionProcessor(Environment e,
			Map<String, SearchCommand> commands) throws IOException {
		SearchStatus status = SearchStatus.CONTINUE;
		do {
			try {
				e.write("EnterCommand>");
			} catch (IOException e2) {
				System.err.println("Error wrinting on standard ouput.");
			}
			String l = "";
			try {
				l = e.readLine()+" ";
			} catch (IOException e1) {
				System.err.println("Error while reading command.");
			}
			String commandName = l.substring(0, l.indexOf(" "));
			String arguments = l.substring(l.indexOf(" ") + 1, l.length())
					.trim();
			SearchCommand command = commands.get(commandName);
			
			if(command == null){
				e.writeln("Nepoznata naredba.");
				continue;
			} else{
				status = command.executeCommand(e, arguments);
			}
			
			
		} while (status != SearchStatus.TERMINATE);

	}

}
