package hr.fer.zemris.java.tecaj.hw07.shell.enviroment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * Implements environment interface.
 * 
 * @author Mia Filić
 *
 */
public class MyEnvironment implements Environment {

	/**
	 * Input stream reader.
	 */
	private static BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

	/**
	 * Prompt symbol of the shell.
	 */
	private char PROMPT = '>';
	/**
	 * Symbol indicating the shell that more lines are coming.
	 */
	private char MORELINE = '\\';

	/**
	 * Symbol written on the beginning of each line in multi-line command.
	 */
	private char MULTILINE = '|';

	/**
	 * Instructions that are supported.
	 */
	Iterable<ShellCommand> commands;

	/**
	 * Constructor.
	 * 
	 * @param PROMPT
	 *            Prompt symbol of the shell.
	 * @param MORELINE
	 *            Symbol indicating the shell that more lines are coming.
	 * @param MULTILINE
	 *            Symbol written on the beginning of each line in multi-line
	 *            command.
	 * @param commands
	 *            Instructions that are supported.
	 */
	public MyEnvironment(char PROMPT, char MORELINE, char MULTILINE,
			Iterable<ShellCommand> commands) {
		super();
		this.PROMPT = PROMPT;
		this.MORELINE = MORELINE;
		this.MULTILINE = MULTILINE;
		this.commands = commands;
	}

	/**
	 * Constructor.
	 * 
	 * @param commands
	 *            Instructions that are supported.
	 */
	public MyEnvironment(Collection<ShellCommand> commands) {
		this.commands = commands;
	}

	@Override
	public String readLine() throws IOException {
		return br.readLine();
	}

	@Override
	public void write(String text) throws IOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws IOException {
		System.out.println(text);
	}

	@Override
	public Iterable<ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINE;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.MULTILINE = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPT;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.PROMPT = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINE;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.MORELINE = symbol;
	}

}
