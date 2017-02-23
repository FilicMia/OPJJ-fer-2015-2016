package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * Command termminates the program. Takes no arguments. If arguments are
 * provided, they are ignored.
 * 
 * @author Mia Filic
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "exit";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC
				.add("Command exit takes no arguments terminates the program.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		
		return COMMANDNAME;
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return COMMANDDESC;
	}

}
