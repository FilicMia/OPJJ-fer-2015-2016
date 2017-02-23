package hr.fer.zemris.java.hw11.jnotepadpp.clipboard;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadppFrame;

/**
 * @author mia
 *Represents the clipboard of the {@link JNotepadppFrame}
 */
public class Clipboard {
	/**
	 * Text of the clipboard.
	 */
	String text;

	/**
	 * Constructor.
	 * 
	 * @param text
	 *            content of clipboard.
	 */
	public Clipboard(String text) {
		super();
		this.text = text;
	}

	/**
	 * Returns text saved in clipboard.
	 * 
	 * @return Text saved in clipboard.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set text of the clipboard.
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	

}
