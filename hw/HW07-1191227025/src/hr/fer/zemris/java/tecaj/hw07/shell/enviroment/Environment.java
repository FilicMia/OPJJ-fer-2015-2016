package hr.fer.zemris.java.tecaj.hw07.shell.enviroment;

import java.io.IOException;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * The abstraction that implements the communication between user and each
 * command.
 * 
 * @author Mia Filić
 *
 */
public interface Environment {

	/**
	 * Reads line form the stream input.
	 * 
	 * @return read text
	 * @throws IOException
	 *             if input stream broke while reading
	 */
	String readLine() throws IOException;

	/**
	 * Writes text on output stream.
	 * 
	 * @param text
	 *            text to be written.
	 * @throws IOException
	 *             if output stream broke.
	 */
	void write(String text) throws IOException;

	/**
	 * Writes text on output stream appended with new line.
	 * 
	 * @param text
	 *            text to be written.
	 * @throws IOException
	 *             if output stream broke.
	 */
	void writeln(String text) throws IOException;

	/**
	 * Iterator trought currently stored commands.
	 * @return iterator of ShellComand
	 */
	Iterable<ShellCommand> commands();

	/**
	 * @return multiline symbol of the shell.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the multiline symbol of the shell.
	 * 
	 * @param symbol
	 *            to be set as multiline symbol in shell.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * @return prompt symbol of the shell.
	 */
	Character getPromptSymbol();

	/**
	 * Sets the prompt symbol of the shell.
	 * 
	 * @param symbol
	 *            to be set as prompt symbol in shell.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * @return moreline symbol of the shell.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the moreline symbol of the shell.
	 * 
	 * @param symbol
	 *            to be set as moreline symbol in shell.
	 */
	void setMorelinesSymbol(Character symbol);
}
