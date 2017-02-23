package hr.fer.zemris.java.hw11.jnotepadpp.action;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadppFrame;
import hr.fer.zemris.java.hw11.jnotepadpp.document.DocumentTab;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.LocalizableAction;

/**
 * Abstract implementation of actions of type "sort".
 * 
 * @author mia
 */
public abstract class SortUniqueAction extends LocalizableAction {

	/** Version ID. */
	private static final long serialVersionUID = 7892275952601371071L;
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
	 */
	public SortUniqueAction(String key, ILocalizationProvider lp,
			JNotepadppFrame notepad) {
		super(key, lp);
		this.notepad = notepad;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DocumentTab currentTab = notepad.getCurrentTab();
		if (currentTab == null)
			return;

		JTextArea current = currentTab.getTextArea();
		Document doc = current.getDocument();

		int len = currentTab.getSelectedLength();
		int offset = 0;
		if (len != 0) {
			try {
				offset = Math.min(
						current.getLineStartOffset(currentTab.getCarretLine()),
						current.getLineStartOffset(
								currentTab.getCaretMarkLine()));
				if (currentTab.getCaretMarkLine() == current.getLineCount() - 1
						|| currentTab.getCarretLine() == current.getLineCount()
								- 1) {
					len = doc.getLength() - offset;
				} else {
					len = Math.max(
							current.getLineStartOffset(
									currentTab.getCarretLine() + 1),
							current.getLineStartOffset(
									currentTab.getCaretMarkLine()) + 1)
							- offset;
				}
			} catch (BadLocationException e1) {
				System.err.println("Bad Location error!");
				return;
			}

		} else {
			return;
		}

		try {
			String[] rows = doc.getText(offset, len).split("\\r?\\n");
			List<String> rowsList = Arrays.asList(rows);
			rowsList = process(rowsList);
			String text = merge(rowsList);
			text = text.length() == len ? text
					: text.substring(0, text.length() - 1);

			doc.remove(offset, len);
			doc.insertString(offset, text, null);

		} catch (BadLocationException ex) {
			System.err.println("Bad location error!");
			System.exit(-1);
		}
	}

	/**
	 * Process which is unique for certain action.
	 * 
	 * @param list
	 *            list of string lines that will be processed
	 * @return processed data
	 */
	protected abstract List<String> process(List<String> list);

	/**
	 * Merges all the elements of the String list to one string, separated by
	 * one '\n'.
	 * 
	 * @param list
	 *            list of strings that will be merged.
	 * @return merged string
	 */
	private String merge(List<String> list) {
		StringBuffer sb = new StringBuffer("");
		list.forEach((elem) -> sb.append(elem).append('\n'));
		return sb.toString();
	}

}
