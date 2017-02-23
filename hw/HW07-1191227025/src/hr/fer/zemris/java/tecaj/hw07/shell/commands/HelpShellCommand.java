package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * The help command that if started with no arguments, lists names of all
 * supported commands. If started with single argument, it prints name and the
 * description of selected command (or print appropriate error message if no
 * such command exists).
 * 
 * @author Mia Filic
 *
 */
public class HelpShellCommand implements ShellCommand {
	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "help";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC.add("Command take one or no arguments.");
		COMMANDDESC.add(
				"If started with no arguments, prints the all avaliable commands.");
		COMMANDDESC.add(
				"If started with one arguments, it must be the name of one of the available commands.");
		COMMANDDESC.add(
				"If the passed argument is not the name of one of the commands, user is acknoladge with appropriate message.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments)
			throws IOException {
		
		List<String> args = Arrays.asList(arguments.split("\\s+"));
		
		if (args.size() == 0 || args.get(0).isEmpty()) {
			Iterator<ShellCommand> iter = env.commands().iterator();
			while (iter.hasNext()) {
				ShellCommand command = iter.next();
				env.writeln(command.getCommandName());
			}
			
		} else if (args.size() == 1) {
			Iterator<ShellCommand> iter = env.commands().iterator();
			while (iter.hasNext()) {
				ShellCommand command = iter.next();
				if (command.getCommandName().equals(args.get(0))) {
					for (String desc : command.getCommandDescription()) {
						env.writeln(desc);
					}
					break;
				}

			}
			env.writeln("The command is not supported.");
		} else {
			env.writeln("Usage: "+COMMANDNAME+" ?command_name");
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
