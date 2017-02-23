package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * Command charsets that takes no arguments.By executing lists names of supported charsets for
 * current Java platform. A single charset name
 * is written per line.
 * 
 * @author Mia Filić
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "charset";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC
				.add("Command charsets takes no arguments and lists names of supported"
						+ " charsets for your Java platform.");
		COMMANDDESC.add("A single charset name is written per line.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments)
			throws IOException {
		for (String charsetName : Charset.availableCharsets().keySet()) {
			env.writeln(charsetName);
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
