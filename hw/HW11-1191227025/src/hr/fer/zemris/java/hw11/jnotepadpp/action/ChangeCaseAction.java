package hr.fer.zemris.java.hw11.jnotepadpp.action;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadppFrame;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.LocalizableAction;

/**
 * Abstract implementation of actions of type "change case".
 * 
 * @author mia
 *
 */
public abstract class ChangeCaseAction extends LocalizableAction {

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 7535265558046916633L;

	/** Notepad which action is this. */
	private JNotepadppFrame notepad;

	/**
	 * Constructor. Registeres the instance of anonimous class to the given
	 * {@link ILocalizationProvider}.
	 * 
	 * @param key
	 *            key over which the action-name is saved.
	 * @param lp
	 *            localization provider of {@code key}'s value when localization
	 *            is changed
	 * @param notepad
	 *            notepad which action is this.
	 * 
	 */
	public ChangeCaseAction(String key, ILocalizationProvider lp,
			JNotepadppFrame notepad) {
		super(key, lp);
		this.notepad = notepad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea current = notepad.getCurrent();
		if (current == null)
			return;

		Document doc = current.getDocument();
		int len = Math.abs(
				current.getCaret().getDot() - current.getCaret().getMark());
		int offset = 0;
		if (len != 0) {
			offset = Math.min(current.getCaret().getDot(),
					current.getCaret().getMark());
		} else {
			len = doc.getLength();
		}

		try {
			String text = doc.getText(offset, len);
			text = changeCase(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);

		} catch (BadLocationException ex) {
			System.err.println("Bad location error!");
			System.exit(-1);
		}
	}

	/**
	 * Changing the case of the string.
	 * 
	 * @param text
	 *            string which case will be changed.
	 * @return string with changed case.
	 */
	protected String changeCase(String text) {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; ++i) {
			znakovi[i] = process(znakovi[i]);
		}

		return new String(znakovi);
	}

	/**
	 * Change case due to action.
	 * 
	 * @param c
	 *            char to be processed
	 * @return output of process
	 */
	protected abstract char process(char c);

}
