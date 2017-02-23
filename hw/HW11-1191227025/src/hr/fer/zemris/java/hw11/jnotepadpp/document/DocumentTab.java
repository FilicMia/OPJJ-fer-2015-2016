package hr.fer.zemris.java.hw11.jnotepadpp.document;

import java.awt.Rectangle;
import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.IconsEnum;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadppFrame;
import hr.fer.zemris.java.hw11.jnotepadpp.footer.Footer;

/**
 * Represents the opened document. Contains its path and text area.
 * 
 * @author mia
 *
 */
public class DocumentTab {
	/**
	 * Path to the document on the disc. Null is the document is not saved jet.
	 */
	private Path documentPath;
	/**
	 * Component of text area containing the text written in the document.
	 */
	private JTextArea textArea;

	/** Indicates if file is edited after saving or not. */
	private boolean edited;

	/**
	 * Constructor.
	 * 
	 * @param documentPath
	 *            document path; null values are allowed
	 * @param textArea
	 *            text area containing text of the document; null values are not
	 *            allowed
	 * @param notepad
	 *            notepad in which the tab is.
	 * @param index
	 *            index of tab in which the textArea is .
	 */
	public DocumentTab(Path documentPath, JTextArea textArea,
			JNotepadppFrame notepad, int index) {
		super();
		Objects.requireNonNull(textArea);
		Objects.requireNonNull(notepad);

		this.documentPath = documentPath;
		this.textArea = textArea;

		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				DocumentTab.this.setEdited(true);
				notepad.setIcon(index, IconsEnum.RED_DISKETTE);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				DocumentTab.this.setEdited(true);
				notepad.setIcon(index, IconsEnum.RED_DISKETTE);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				DocumentTab.this.setEdited(true);
				notepad.setIcon(index, IconsEnum.RED_DISKETTE);
			}
		});

		textArea.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				notepad.updateFooter(DocumentTab.this);
				int selected = Math.abs(e.getDot() - e.getMark());
				if (selected == 0) {
					notepad.selectionDone(false);
				} else {
					notepad.selectionDone(true);
				}

			}
		});
	}

	/**
	 * @return document path
	 */
	public Path getDocumentPath() {
		return documentPath;
	}

	/**
	 * @return text area
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Set the document path to {@code path}
	 * 
	 * @param path
	 *            path of the document.
	 */
	public void setDocumentPath(Path path) {
		this.documentPath = path;
	}

	/**
	 * @return {@link String} representation of the path to the document shown
	 *         in this tab.
	 */
	public String getTabPath() {
		return documentPath != null ? documentPath.toString() : "Untitled";
	}

	/**
	 * @return {@link String} representation of the document name.
	 */
	public String getTabName() {
		return documentPath != null ? documentPath.getFileName().toString()
				: "Untitled";
	}

	/**
	 * Indicator if file is edited after saving.
	 * 
	 * @return {@code true} if file is not edited after saving, {@code false}
	 *         otherwise.
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets the {@code edited} indicator.
	 * 
	 * @param edited
	 *            indicates if changes are done after saving.
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Returns the line in which the caret's mark is located.
	 * 
	 * @return line line in which the caret's mark is located
	 */
	public int getCaretMarkLine() {
		int carretMark = textArea.getCaret().getMark();
		String before = textArea.getText().substring(0,
				carretMark >= textArea.getText().length()
						? textArea.getText().length() : carretMark);
		return numberOfLine(before);
	}

	/**
	 * Returns the line in which the carret is located.
	 * 
	 * @return line line in which the carret is located
	 */
	public int getCarretLine() {
		int carret = textArea.getCaret().getDot();
		String before = textArea.getText().substring(0,
				carret >= textArea.getText().length()
						? textArea.getText().length() : carret);
		return numberOfLine(before);
	}

	/**
	 * Returns the number of lines in string.
	 * 
	 * @param string
	 *            string which lines will be calcualted
	 * @return number of lines in string
	 */
	private int numberOfLine(String string) {
		Matcher m = Pattern.compile("(\r\n)|(\r)|(\n)").matcher(string);
		int lines = 0;
		while (m.find()) {
			lines++;
		}
		return lines;
	}

	/**
	 * Returns the column in which the carret is located.
	 * 
	 * @return line line in which the carret is located
	 */
	public int getCarretColumn() {
		int line = getCarretLine();
		String text = textArea.getText();
		String currentLineFromBegining = null;
		try {
			currentLineFromBegining = text.substring(
					textArea.getLineStartOffset(line),
					textArea.getCaret().getDot());
		} catch (BadLocationException e1) {

		}

		return currentLineFromBegining == null ? 0
				: currentLineFromBegining.length();
	}

	/**
	 * Returns the length of the selected part of the document.
	 * 
	 * @return the length of the selected part of the document.
	 * 
	 */
	public int getSelectedLength() {
		return Math.abs(
				textArea.getCaret().getDot() - textArea.getCaret().getMark());
	}

	/**
	 * Returns the length of the document.
	 * 
	 * @return the length of the document.
	 */
	public int getLength() {
		return textArea.getText().length();
	}

}
