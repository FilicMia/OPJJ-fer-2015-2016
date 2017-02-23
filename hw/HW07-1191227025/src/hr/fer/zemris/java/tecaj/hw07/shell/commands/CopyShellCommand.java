package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * The copy command which expects two arguments: source file name and
 * destination file name (i.e. paths and names). Is destination file exists,
 * user is asked if it's allowed to overwrite it. Copy command works only with
 * files (no directories). If the second argument is directory, it is assumed
 * that user wants to copy the original file into that directory using the
 * original file name.
 * 
 * @author Mia Filic
 *
 */
public class CopyShellCommand implements ShellCommand {
	/** Size of buffer while reading and writing. */
	public static final int BUFFERSIZE = 1024;
	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "copy";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC
				.add("The copy command which expects two arguments: source file name and destination file"
						+ " file.");
		COMMANDDESC.add("Copy command works only with files (no directories).");
		COMMANDDESC.add("If the second argument is directory, it is assumed"
				+ " that user wants to copy the original file into that directory using the"
				+ " original file name.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		List<String> args = Arrays.asList(arguments.split("\\s+"));
		if (args.size() != 2) {
			env.writeln("Usage: " + COMMANDNAME + " path_form path_to");
			return ShellStatus.CONTINUE;
		}
		
		Path from = Paths.get(args.get(0).replaceAll("\"", ""));
		Path to = Paths.get(args.get(1).replaceAll("\"", ""));

		if (!from.toFile().isFile()) {
			env.writeln(from + " is not a file.");
			return ShellStatus.CONTINUE;
		}

		if (to.toFile().isDirectory()) {
			to = Paths.get(to.toFile().getAbsolutePath() + File.separator
					+ from.toFile().getName());
		}

		if (to.toFile().isFile()) {
			try {
				env.writeln("Do you want to overwrite existing file?(Y/N)");
			} catch (IOException e2) {
				return ShellStatus.CONTINUE;
			}
			try {
				if (!env.readLine().equals("Y")) {
					return ShellStatus.CONTINUE;
				}
			} catch (IOException e1) {
				return ShellStatus.CONTINUE;
			}
		}
		FileInputStream is = null;
		FileOutputStream os = null;

		try {
			is = new FileInputStream(from.toFile());
			os = new FileOutputStream(to.toFile());
			byte[] buff = new byte[1024];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(buff);
			}
		} catch (IOException e) {
			env.writeln(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignorable) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException ignorable) {
				}
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
