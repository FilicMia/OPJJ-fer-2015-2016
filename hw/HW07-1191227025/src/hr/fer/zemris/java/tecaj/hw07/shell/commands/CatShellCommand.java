package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * Command which opens given file and writes its content to console.
 * 
 * Command cat takes one or two arguments. The first argument is path to some
 * file and is mandatory. The second argument is charset name that is be used to
 * interpret chars from bytes. If not provided, a default platform charset is
 * used.
 * 
 * @author Mia Filic
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "cat";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC.add(
				"Command which opens given file and writes its content to console.");
		COMMANDDESC.add("Command cat takes one or two arguments.");
		COMMANDDESC.add("The first argument is path to"
				+ "some file and is mandatory.");
		COMMANDDESC.add("The second argument is charset name that is be"
				+ "used to interpret chars from bytes.");
		COMMANDDESC.add(
				"If not provided, a default platform" + "charset is used.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments)
			throws IOException {
		List<String> args = Arrays.asList(arguments.split("\\s+"));
		BufferedReader br = null;
		if (new File(args.get(0).replaceAll("\"", "")).exists()) {

			if (args.size() == 1) {
				try {
					br = new BufferedReader(new InputStreamReader(
							new BufferedInputStream(new FileInputStream(
									args.get(0).replaceAll("\"", ""))),
							Charset.defaultCharset()));
				} catch (FileNotFoundException e1) {
					env.writeln("Cannot read from file");
					return ShellStatus.CONTINUE;
				}
			} else {
				try {
					br = new BufferedReader(new InputStreamReader(
							new BufferedInputStream(new FileInputStream(
									args.get(0).replaceAll("\"", ""))),
							args.get(1)));
				} catch (UnsupportedEncodingException e1) {
					env.writeln("Cannot read from file");
					return ShellStatus.CONTINUE;
				} catch (FileNotFoundException e1) {
					env.writeln("Cannot read from file");
					return ShellStatus.CONTINUE;
				}

			}

			String line;
			try {
				while ((line = br.readLine()) != null) {
					env.writeln(line);
				}
			} catch (IOException e1) {
				env.writeln("Cannot read from file");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Unabble to print file");
			return ShellStatus.CONTINUE;
		}
		try {
			if (br != null)
				br.close();
		} catch (IOException e1) {
			return ShellStatus.CONTINUE;
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
