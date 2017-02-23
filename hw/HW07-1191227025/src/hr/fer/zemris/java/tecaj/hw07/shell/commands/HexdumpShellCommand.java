package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.enviroment.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.status.ShellStatus;

/**
 * The hexdump command which expects a single argument: file name, and produces
 * hex-output. Only a standard subset of characters is shown; for all other
 * characters a '.' is printed instead (i.env. replace all bytes whose value is
 * less than 32 or greater than 127 with '.').
 * 
 * @author Mia Filic
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	/** Size of buffer while reading and writing. */
	public static final int BUFFERSIZE = 1024;
	/** New line sign. */
	public static final String NEWLINE = "\n";
	/** Column separator sign. */
	public static final String COLUMNSEPARATOR = "|";
	/** Dot sign. */
	public static final String DOT = ".";
	/** Hex = HEX */
	public static final int HEX = 16;
	/** Upper bound of standard subset characters. */
	public static final int UPPERBOUND = 127;
	/** Lower bound of standard subset characters. */
	public static final int LOWERBOUND = 32;

	/**
	 * Command name.
	 */
	private static final String COMMANDNAME = "hexdump";

	/** Command description. */
	private static List<String> COMMANDDESC;
	{

		COMMANDDESC = new ArrayList<>();
		COMMANDDESC.add(
				"The hexdump command which expects a single argument: file name, and produceshex-output.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments)
			throws IOException {
		List<String> args = Arrays.asList(arguments.split("\\s+"));
		if (args.size() != 1) {
			env.writeln("Usage: " + COMMANDNAME + " path");
			return ShellStatus.CONTINUE;
		}
		
		InputStream is = null;
		int sum = 0;
		try {
			is = new BufferedInputStream(new FileInputStream(args.get(0)));
			byte[] buff = new byte[BUFFERSIZE];
			while (true) {
				int bytesRead = is.read(buff);
				if (bytesRead < 1)
					break;
				for (int i = 0; i < bytesRead; ++i) {
					if (sum % HEX == 0) {
						env.write(String.format("%08X: ", sum));// starts
					}
					env.write(String.format("%02X", buff[i]));// elements
					++sum;
					if (sum % HEX == 0) {
						env.write(" " + COLUMNSEPARATOR + " ");
						for (int j = sum - HEX; j < sum; ++j) {
							if (buff[j] > UPPERBOUND || buff[j] < LOWERBOUND) {
								env.write(DOT);
							} else {
								env.write("" + (char) buff[j]);
							}
						}
						env.write(NEWLINE);
					} else if (sum % 8 == 0) {
						env.write(COLUMNSEPARATOR);
					} else {
						env.write(" ");
					}
				}
			}
			for (int i = sum; i % HEX != 0;) {
				env.write("  ");
				++i;
				if (i % HEX == 0) {
					env.write(" " + COLUMNSEPARATOR + " ");
					for (int j = i - HEX; j < sum; ++j) {
						if (buff[j] > UPPERBOUND || buff[j] < LOWERBOUND) {
							env.write(DOT);
						} else {
							env.write("" + (char) buff[j]);
						}
					}
					env.write(NEWLINE);
				} else if (i % 8 == 0) {
					env.write(COLUMNSEPARATOR);
				} else {
					env.write(" ");
				}
			}
		} catch (IOException ex) {
			env.writeln(ex.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
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
