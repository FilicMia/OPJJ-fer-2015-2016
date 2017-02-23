package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.document.DocumentTab;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.keys.GUIItemKeys;

/**
 * Utility function for handling the documents/tabs opened in some
 * {@link JNotepadppFrame}.
 * 
 * @author mia
 *
 */
public class NotepadppUtil {
	/** Localization provider. */
	private static LocalizationProvider lp = LocalizationProvider.getInstance();

	/**
	 * Java file chooser used for selecting of document and its paths.
	 */
	private static JFileChooser jfc = new JFileChooser() {
		/**
		 * Version ID.
		 */
		private static final long serialVersionUID = 4537434676287558951L;

		@Override
		public void approveSelection() {
			File f = getSelectedFile();
			if (f.exists() && getDialogType() == SAVE_DIALOG) {
				int result = JOptionPane.showConfirmDialog(this,
						lp.getString(GUIItemKeys.existingFile),
						lp.getString(GUIItemKeys.systemMessage),
						JOptionPane.YES_NO_CANCEL_OPTION);
				switch (result) {
				case JOptionPane.YES_OPTION:
					super.approveSelection();
					return;
				case JOptionPane.NO_OPTION:
					return;
				case JOptionPane.CLOSED_OPTION:
					return;
				case JOptionPane.CANCEL_OPTION:
					cancelSelection();
					return;
				}
			}
			super.approveSelection();
		}
	};

	/**
	 * Saving the content of {@code textArea} in document specified with path
	 * {@code path}.
	 * 
	 * @param notepad
	 *            notepad which {@code textArea}.
	 * @param document
	 *            {@link DocumentTab} which text will be saved.
	 * 
	 */
	public static void saveSelectedTab(JNotepadppFrame notepad,
			DocumentTab document) {
		Objects.requireNonNull(document);
		if (document.getDocumentPath() == null) {
			document.setDocumentPath(selectSavePath(notepad,
					lp.getString(GUIItemKeys.saveDocumentAction)));
		}

		byte[] podatci = (document.getTextArea()).getText().getBytes();
		if (document.getDocumentPath() == null) {
			JOptionPane.showMessageDialog(notepad,
					lp.getString(GUIItemKeys.destinationNotChoosen));
			return;
		}

		try {
			Files.write(document.getDocumentPath(), podatci);

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(notepad,
					lp.getString(GUIItemKeys.error));
			return;

		}

		JOptionPane.showMessageDialog(notepad,
				lp.getString(GUIItemKeys.dataSaved), "Info",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Opens an existing file in {@code notepad} using {@link JFileChooser}. The
	 * new tab is added with {@link JNotepadppFrame#addTab(JTextArea, Path)}
	 * function.
	 * 
	 * @param notepad
	 *            instance of {@link JNotepadpp} where the file will be opened
	 * 
	 */
	public static void openChoosedDocument(JNotepadppFrame notepad) {

		jfc.setDialogTitle(lp.getString(GUIItemKeys.openDocumentAction));

		if (jfc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = jfc.getSelectedFile();
		Path filePathe = fileName.toPath();

		if (!Files.isReadable(filePathe)) {
			JOptionPane.showMessageDialog(notepad,
					lp.getString(GUIItemKeys.unreadableError),
					lp.getString(GUIItemKeys.error), JOptionPane.ERROR_MESSAGE);
			return;
		}

		byte[] okteti;
		try {
			okteti = Files.readAllBytes(filePathe);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(notepad,
					lp.getString(GUIItemKeys.readingError));
			return;
		}

		String text = new String(okteti, StandardCharsets.UTF_8);
		JTextArea editor = new JTextArea();
		editor.setText(text);
		notepad.addTab(editor, filePathe, IconsEnum.GREEN_DISKETTE);
	}

	/**
	 * Process of selecting a path where to save the file.
	 * 
	 * @param notepad
	 *            notepad
	 * @param title
	 *            title of the box.
	 * @return the path selected
	 */
	public static Path selectSavePath(JNotepadppFrame notepad, String title) {
		jfc.setDialogTitle(title);

		if (jfc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return jfc.getSelectedFile().toPath();
	}

}
