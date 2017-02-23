package hr.fer.zemris.java.tecaj.hw07.shell.commands.walkDirTree;

import java.io.File;

/**
 * Interface implementing the processing of directories recursively.
 * 
 * @author Mia Filić
 *
 */
public interface TreeProcessing {
	/**
	 * Action to be done when entering directory {@code dir}.
	 * 
	 * @param dir
	 *            directory in which the enter gona be done
	 */
	void enterDir(File dir);

	/**
	 * Action to be done when exiting directory {@code dir}.
	 * 
	 * @param dir
	 *            directory in which the exit gona be done
	 */
	void exitDir(File dir);

	/**
	 * Action to be done when watching the file {@code f}.
	 * 
	 * @param f
	 *            watched file
	 */
	void wachingFile(File f);
}
