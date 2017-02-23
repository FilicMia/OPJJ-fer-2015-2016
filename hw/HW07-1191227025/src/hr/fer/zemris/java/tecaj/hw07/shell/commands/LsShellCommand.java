package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * Command ls takes a single argument – directory- and writes a directory
 * listing(not recursive).
 * 
 * @author Mia Filić
 *
 */
public class LsShellCommand implements ShellCommand {
	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "ls";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC
				.add("Command ls takes a single argument – directory- and writes a directory"
						+ "listing(not recursive).");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = Arrays.asList(arguments.split("\\s+"));
		if (args.size() != 1) {
			System.err.println("Usage: " + COMMANDNAME + " dyrectory");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(args.get(0).replaceAll("\"", ""));
		if (!path.toFile().isDirectory()) {
			System.err.println("Usage: " + COMMANDNAME + " dyrectory");
			return ShellStatus.CONTINUE;
		}

		File[] children = path.toFile().listFiles();
		for (File child : children) {
			try {
				env.writeln(formattedAtributesString(child));
			} catch (IOException e1) {
				System.err.println(e1.getMessage());
				return ShellStatus.CONTINUE;
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Creates formatted string of attributes of passed file.
	 * 
	 * @param file
	 *            file which attributes should be formatted.
	 * @return String of formatted file attributes
	 */
	private String formattedAtributesString(File file) {
		String all = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path pathC = Paths.get(file.getAbsolutePath());
		BasicFileAttributeView faView = Files.getFileAttributeView(pathC,
				BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		if (file.isDirectory()) {
			all += "d";
		} else {
			all += "-";
		}
		if (file.canRead()) {
			all += "r";
		} else {
			all += "-";
		}
		if (file.canWrite()) {
			all += "w";
		} else {
			all += "-";
		}
		if (file.canExecute()) {
			all += "x";
		} else {
			all += "-";
		}
		String length = "" + file.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10 - length.length(); i++) {
			sb.append(' ');
		}
		sb.append(length);
		all += sb.toString() + " ";
		all += formattedDateTime + " ";
		all += file.getName();
		return all;

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
