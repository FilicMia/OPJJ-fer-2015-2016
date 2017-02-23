package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * Shell command which changes used symbols.
 * 
 * @author Mia Filic
 *
 */
public class SymbolShellCommand implements ShellCommand {
	/**
	 * Prompt.
	 */
	private static final String PROMPT = "PROMPT";
	/**
	 * Morelines.
	 */
	private static final String MORELINES = "MORELINES";

	/**
	 * Multiline.
	 */
	private static final String MULTILINE = "MULTILINE";

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "symbol";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC.add(
				"Commmand symbol changes the current symbols used in {@link MyShell}.");
		COMMANDDESC.add(
				"It takes two command line arguments: name of symbol ; new value for symbol.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {

		List<String> args = Arrays.asList(arguments.split("\\s+"));
		if (args.size() != 2) {
			env.writeln(
					"Usage: " + COMMANDNAME + " symbol_name new_value");
			return ShellStatus.CONTINUE;
		}
		
		char symbol = args.get(1).trim().charAt(0);
		switch (args.get(0).toUpperCase()) {
		case PROMPT:
			env.setPromptSymbol(symbol);
			break;
		case MORELINES:
			env.setMorelinesSymbol(symbol);
			break;
		case MULTILINE:
			env.setMultilineSymbol(symbol);
			break;
		default:
			break;
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
