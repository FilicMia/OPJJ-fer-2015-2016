package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.MyEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * Command-line program. When started, program writes a greeting message to user
 * (Welcome to MyShell v 1.0), writes a prompt symbol and wait for the user to
 * enter a command. The command can span across multiple lines. However, each
 * line that is not the last line of command must end with a special symbol that
 * is used to inform the shell that more lines as expected. These symbols are
 * refer as PROMPTSYMBOL and MORELINESSYMBOL. For each line that is part of
 * multi-line command (except for the first one) a shell writes MULTILINESYMBOL
 * at the beginning followed by a single whitespace. Shell provides a
 * command(symbol) symbol that can be used to change these symbols.
 * 
 * 
 * @author Mia Filić
 *
 */
public class MyShell {

	/**
	 * Method called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 * @throws IOException
	 *             if IO fails - communication between command and user
	 */
	public static void main(String[] args) throws IOException {
		Map<String, ShellCommand> commands = new HashMap<String, ShellCommand>();
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("help", new HelpShellCommand());

		Environment e = new MyEnvironment(commands.values());

		System.out.println("Welcome to MyShell v 1.0");

		sessionProcessor(e, commands);

	}

	/**
	 * Method processing the one user session.
	 * 
	 * @param e
	 *            type of communication between user and shell.
	 * @param commands
	 *            commands available in this shell.
	 * @throws IOException
	 *             if IO fails - communication between command and user
	 */
	private static void sessionProcessor(Environment e,
			Map<String, ShellCommand> commands) throws IOException {
		ShellStatus status = ShellStatus.CONTINUE;
		do {
			try {
				e.write(e.getPromptSymbol() + " ");
			} catch (IOException e2) {
				System.err.println("Error wrinting on standard ouput.");
			}
			String l = "";
			try {
				l = readCommand(e);
			} catch (IOException e1) {
				System.err.println("Error while reading command.");
			}
			String commandName = l.substring(0, l.indexOf(" "));
			String arguments = l.substring(l.indexOf(" ") + 1, l.length())
					.trim();
			ShellCommand command = commands.get(commandName);
			status = command.executeCommand(e, arguments);
		} while (status != ShellStatus.TERMINATE);

	}

	/**
	 * Reads one command written in shell.
	 * 
	 * @param e
	 *            environment used for communication between user and command.
	 * @return one line string representing the command.
	 * @throws IOException
	 *             if reading and writing trough the environment failed
	 */
	private static String readCommand(Environment e) throws IOException {
		String current;
		String command = "";
		current = e.readLine();
		if (current.endsWith("" + e.getMorelinesSymbol())) {
			command = current.substring(0, current.length() - 1);
			e.write("" + e.getMultilineSymbol() + " ");
			while ((current = e.readLine())
					.endsWith("" + e.getMorelinesSymbol())) {
				current = current.substring(0, current.length() - 2);// moves
																		// the
																		// space
				command = command + " " + current;
				e.write("" + e.getMultilineSymbol() + " ");
			}
			command = command + " " + current;
		} else {
			command = current;
		}
		return command + " ";
	}

}
