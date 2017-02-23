package hr.fer.zemris.java.hw11.jnotepadpp.localizationProvider;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadppFrame;

/**
 * Provides the selected part of the {@link JTextArea}.
 * 
 * @author mia
 *
 */
public class SelectedDocumentPartProvider {

	/**
	 * Representing the locale of the {@link JTextArea}.
	 * 
	 * @author mia
	 *
	 */
	public static class DocumentPart {
		/**
		 * Offset of the beginnig of the JTextArea where the selected locale
		 * starting.
		 */
		private int offset;

		/**
		 * Length of the selected part of the text.
		 */
		private int len;

		/**
		 * Currently selected text area.(tab)
		 */
		private JTextArea textArea;

		/**
		 * Constructor.
		 * 
		 * @param offset
		 *            of the selected part form the beginning
		 * @param len
		 *            of the selected part.
		 * @param textArea
		 *            text area in which the part is selected.
		 */
		private DocumentPart(int offset, int len, JTextArea textArea) {
			super();
			this.offset = offset;
			this.len = len;
			this.textArea = textArea;
		}

		/**
		 * @return offset of the selected part from the beginning
		 */
		public int getOffset() {
			return offset;
		}

		/**
		 * @return length of the selected part
		 */
		public int getLen() {
			return len;
		}

		/**
		 * @return currenly selected {@link JTextArea}.
		 */
		public JTextArea getTextArea() {
			return textArea;
		}
	}

	/**
	 * Searches and returns the locale of the current {@link JTextArea}.
	 * 
	 * @param notepad
	 *            over which the selected locale is searched.
	 * @return locale which is selected
	 */
	public static DocumentPart getLocal(JNotepadppFrame notepad) {
		JTextArea current = notepad.getCurrent();
		if(current == null)
			return null;

		int len = Math.abs(
				current.getCaret().getDot() - current.getCaret().getMark());

		int offset = Math.min(current.getCaret().getDot(),
				current.getCaret().getMark());

		return new DocumentPart(offset, len, current);
	}

}
