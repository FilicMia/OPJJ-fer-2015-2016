package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

/**
 * Program starting the {@code JNotepad++}, simple text editor.
 * 
 * @author mia
 *
 */
public class JNotepadpp{

	/**
	 * Method invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JNotepadppFrame notepad = new JNotepadppFrame();
			notepad.setVisible(true);
			Dimension d = notepad.getSize();
			d.setSize(notepad.getPreferredSize().getWidth(), 600);
			notepad.setSize(d);
			
		});
	}

}
