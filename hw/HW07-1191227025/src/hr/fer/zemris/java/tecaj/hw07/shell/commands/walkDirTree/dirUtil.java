package hr.fer.zemris.java.tecaj.hw07.shell.commands.walkDirTree;

import java.io.File;

/**
 * Utility for working with directories.
 * 
 * @author Mia Filic
 *
 */
public class dirUtil {

	/**
	 * Method processing the given directory.
	 * 
	 * @param dir
	 *            directory to be processed
	 * @param o
	 *            instance of {@link TreeProcessing}
	 */
	public static void processDirectory(File dir, TreeProcessing o) {
		o.enterDir(dir);
		File[] childern = dir.listFiles();

		if (childern != null) {
			for (File f : childern) {
				if (f.isFile()) {
					o.wachingFile(f);
				} else if (f.isDirectory()) {
					processDirectory(f, o);
				}
			}
		}
		o.exitDir(dir);
	}

}
