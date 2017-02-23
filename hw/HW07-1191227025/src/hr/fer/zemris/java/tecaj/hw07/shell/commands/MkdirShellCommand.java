package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * The mkdir command takes a single argument: directory name, and creates the
 * appropriate directory structure.
 * 
 * @author Mia Filic
 *
 */
public class MkdirShellCommand implements ShellCommand {
	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "mkdir";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC.add(
				"The mkdir command takes a single argument: directory name, and creates the appropriate directorystructure.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		List<String> args = Arrays.asList(arguments.split("\\s+"));
		if (args.size() != 1) {
			env.writeln("Usage: " + COMMANDNAME + " dyrectory");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(args.get(0).replaceAll("\"", ""));
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				env.writeln("Dir cannot be created.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMANDNAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return COMMANDDESC;
	}

}
