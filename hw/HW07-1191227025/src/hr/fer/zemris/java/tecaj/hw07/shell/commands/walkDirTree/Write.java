package hr.fer.zemris.java.tecaj.hw07.shell.commands.walkDirTree;

import java.io.File;

/**
 * Implements the one way how to process a directory tree structure.
 * 
 * @author Mia Filic
 *
 */
public class Write implements TreeProcessing {
	/**
	 * Level of the given file form the root directory.
	 */
	private int level;

	/**
	 * Part of file tree corresponding the file {@code file}.
	 * 
	 * @param file
	 *            which part of the tree is written(name).
	 */
	public void write(File file) {
		if (level == 0) {
			System.out.println(file.getAbsolutePath());
		} else {
			System.out.printf("%" + (2 * level) + "s%s%n", "", file.getName());
		}
		return;

	}

	@Override
	public void enterDir(File dir) {
		write(dir);
		level++;

	}

	@Override
	public void exitDir(File dir) {
		level--;

	}

	@Override
	public void wachingFile(File f) {
		write(f);

	}

}
