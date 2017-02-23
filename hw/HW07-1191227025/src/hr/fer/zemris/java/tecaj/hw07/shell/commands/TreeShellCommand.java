package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.walkDirTree.Write;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.walkDirTree.dirUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * The tree command which expects a single argument: directory name and prints a tree
 * (each directory level shifts output two charatcers to the right).
 * 
 * @author Mia Filic
 *
 */
public class TreeShellCommand implements ShellCommand {
	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "tree";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC.add(
				"Command expects a single argument: directory name and prints a tree.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		List<String> args = Arrays.asList(arguments.split("\\s+"));
		if (args.size() != 1) {
			env.writeln("Usage: " + COMMANDNAME + " directory");
			return ShellStatus.CONTINUE;
		}
		
		
		File dir = new File(args.get(0));
		if(!dir.isDirectory()){
			env.writeln("One argument - name of directory.");
			return ShellStatus.CONTINUE;
		}
		dirUtil.processDirectory(dir, new Write());
		
		
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
