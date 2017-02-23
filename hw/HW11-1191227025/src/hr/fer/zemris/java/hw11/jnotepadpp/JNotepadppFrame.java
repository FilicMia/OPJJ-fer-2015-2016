package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.action.ChangeCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.action.SortUniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.clipboard.Clipboard;
import hr.fer.zemris.java.hw11.jnotepadpp.document.DocumentTab;
import hr.fer.zemris.java.hw11.jnotepadpp.footer.Footer;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.LanguageName;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.LanguageTag;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.locale.keys.GUIItemKeys;
import hr.fer.zemris.java.hw11.jnotepadpp.localizationProvider.SelectedDocumentPartProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localizationProvider.SelectedDocumentPartProvider.DocumentPart;

/**
 * Implementing the a simple text file editor called JNotepad++.
 * 
 * @author mia
 *
 */
public class JNotepadppFrame extends JFrame {
	/**
	 * Version ID included in hash and equal function.
	 */
	private static final long serialVersionUID = 5817141089205527579L;
	/**
	 * {@link JTabbedPane} of documents that are opened.
	 */
	private JTabbedPane tabbedPane;

	/** Footer displaying status bar of this {@code JNotepadppFrame} */
	private Footer footer;

	/**
	 * List of paths to opened documents, saved in list {@code editor}. They are
	 * saved at the same index as the corresponding {@code JTextArea} is saved
	 * in {@code tabbedPane}.
	 */
	private List<DocumentTab> openedFileDocuments;
	/**
	 * LocalizationProvider which fire the change of labels in this
	 * {@link JFrame} when locale is changed.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);

	/** Data in the clipboard. Null if clipboard is empty. */
	private Clipboard clipboard;

	/** Provide the icons shown on tabs of notepad. */
	private IconsProvider iconProvider;

	/** Constructor. */
	public JNotepadppFrame() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(100, 100);
		setSize(600, 400);

		initGUI();

	}

	/**
	 * Initializing the GUI.
	 */
	private void initGUI() {

		tabbedPane = new JTabbedPane();
		setTitle("- JNotepad++");
		openedFileDocuments = new ArrayList<DocumentTab>();
		clipboard = new Clipboard(null);
		iconProvider = new IconsProvider();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});

		createActions();
		createMenus();
		createToolBars();

		footer = new Footer(this);
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int currentIndex = tabbedPane.getSelectedIndex();
				String title = "- JNotepad++";
				title = currentIndex == -1 ? "- JNotepad++"
						: openedFileDocuments.get(currentIndex).getTabPath()
								+ title;

				JNotepadppFrame.this.setTitle(title);
				if (currentIndex != -1) {
					footer.changeAll(openedFileDocuments.get(currentIndex));
				}

				repaint();
			}
		});

	}

	/**
	 * Action creating the new blank document.
	 */
	private Action openNewBlankDocument = new LocalizableAction(
			GUIItemKeys.openNewBlankDocument, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = -1010613411192335732L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadppFrame.this.addTab(new JTextArea(), null,
					IconsEnum.RED_DISKETTE);
			tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
		}
	};

	/**
	 * Action opens existing file form disk.
	 */
	private Action openDocumentAction = new LocalizableAction(
			GUIItemKeys.openDocumentAction, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = 3429308418627937568L;

		@Override
		public void actionPerformed(ActionEvent e) {
			NotepadppUtil.openChoosedDocument(JNotepadppFrame.this);
			tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
		}
	};

	/**
	 * Action that saves the certain Document.
	 */
	private Action saveDocumentAction = new LocalizableAction(
			GUIItemKeys.saveDocumentAction, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = -5609050084189535991L;

		@Override
		public void actionPerformed(ActionEvent e) {

			int selectedTab = tabbedPane.getSelectedIndex();
			LocalizationProvider ls = LocalizationProvider.getInstance();

			if (selectedTab == -1 || openedFileDocuments.get(selectedTab)
					.getDocumentPath() == null) {
				if (selectedTab == -1) {
					JOptionPane.showMessageDialog(JNotepadppFrame.this,
							ls.getString(GUIItemKeys.fileNotSelected));
					return;
				}

				Path path = NotepadppUtil.selectSavePath(JNotepadppFrame.this,
						ls.getString(GUIItemKeys.saveDocumentAsAction));
				if (path == null) {
					JOptionPane.showMessageDialog(JNotepadppFrame.this,
							ls.getString(GUIItemKeys.destinationNotChoosen));
					return;
				}

				openedFileDocuments.get(selectedTab).setDocumentPath(path);

			}
			NotepadppUtil.saveSelectedTab(JNotepadppFrame.this,
					openedFileDocuments.get(selectedTab));
			setIcon(selectedTab, IconsEnum.GREEN_DISKETTE);
		}

	};

	/**
	 * Action that saves the certain Document on new choosen destination.
	 */
	private Action saveDocumentAsAction = new LocalizableAction(
			GUIItemKeys.saveDocumentAsAction, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = -5609050084189535991L;

		@Override
		public void actionPerformed(ActionEvent e) {

			int selectedTab = tabbedPane.getSelectedIndex();
			LocalizationProvider ls = LocalizationProvider.getInstance();

			if (selectedTab == -1) {
				JOptionPane.showMessageDialog(JNotepadppFrame.this,
						ls.getString(GUIItemKeys.fileNotSelected));
				return;
			}

			Path path = NotepadppUtil.selectSavePath(JNotepadppFrame.this,
					ls.getString(GUIItemKeys.saveDocumentAsAction));
			if (path == null) {
				JOptionPane.showMessageDialog(JNotepadppFrame.this,
						ls.getString(GUIItemKeys.destinationNotChoosen));
				return;
			}

			openedFileDocuments.get(selectedTab).setDocumentPath(path);
			NotepadppUtil.saveSelectedTab(JNotepadppFrame.this,
					openedFileDocuments.get(selectedTab));

			setIcon(selectedTab, IconsEnum.GREEN_DISKETTE);
		}

	};

	/**
	 * Deleting the selected part of the document.
	 */
	private Action deletedSelectedPartAction = new LocalizableAction(
			GUIItemKeys.deletedSelectedPartAction, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = 255379719229230344L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea current = getCurrent();
			DocumentPart locale = SelectedDocumentPartProvider
					.getLocal(JNotepadppFrame.this);
			if (locale == null || locale.getLen() == 0) {
				return;
			}

			Document doc = current.getDocument();
			try {
				doc.remove(locale.getOffset(), locale.getLen());

			} catch (BadLocationException ex) {
				System.err.println("Bad location error!");
				System.exit(-1);
			}
		}
	};

	/**
	 * Action used to cut selected part of the document.
	 */
	private Action cutSelectedAction = new LocalizableAction(
			GUIItemKeys.cutSelectedAction, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = -2479368790559185515L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DocumentPart locale = SelectedDocumentPartProvider
					.getLocal(JNotepadppFrame.this);
			if (locale == null || locale.getLen() == 0) {
				return;
			}
			JTextArea current = locale.getTextArea();

			Document doc = current.getDocument();
			try {
				clipboard.setText(
						doc.getText(locale.getOffset(), locale.getLen()));
				doc.remove(locale.getOffset(), locale.getLen());

			} catch (BadLocationException ex) {
				System.err.println("Bad location error!");
				System.exit(-1);
			}
		}
	};

	/**
	 * Action used to copy selected part of the document.
	 */
	private Action copySelectedAction = new LocalizableAction(
			GUIItemKeys.copySelectedAction, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = 6150081094356866043L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DocumentPart locale = SelectedDocumentPartProvider
					.getLocal(JNotepadppFrame.this);
			if (locale == null || locale.getLen() == 0) {
				return;
			}
			JTextArea current = locale.getTextArea();

			Document doc = current.getDocument();
			try {
				clipboard.setText(
						doc.getText(locale.getOffset(), locale.getLen()));

			} catch (BadLocationException ex) {
				System.err.println("Bad location error!");
				System.exit(-1);
			}
		}
	};

	/**
	 * Action used to paste clipboard on the selected part of the document.
	 */
	private Action pasteSelectedAction = new LocalizableAction(
			GUIItemKeys.pasteSelectedAction, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = 6626893413686271464L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DocumentPart locale = SelectedDocumentPartProvider
					.getLocal(JNotepadppFrame.this);

			if (locale == null || clipboard.getText() == null) {
				return;
			}

			JTextArea current = locale.getTextArea();
			Document doc = current.getDocument();

			if (locale.getLen() != 0) {
				try {
					doc.remove(locale.getOffset(), locale.getLen());// erase
																	// selected
				} catch (BadLocationException e1) {
					System.err.println("Bad location error!");
					System.exit(-1);
				}
			}

			try {
				doc.insertString(current.getCaretPosition(), // insert
						clipboard.getText(), null);
				clipboard.setText(null);

			} catch (BadLocationException ex) {
				System.err.println("Bad location error!");
				System.exit(-1);
			}
		}
	};

	/**
	 * Action that changes the case of case sensitive parts of the selected
	 * document part.
	 */
	private ChangeCaseAction toggleCaseAction = new ChangeCaseAction(
			GUIItemKeys.toggleCaseAction, flp, this) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -112301974049963191L;

		@Override
		protected char process(char c) {
			if (Character.isLowerCase(c)) {
				return Character.toUpperCase(c);
			}
			if (Character.isUpperCase(c)) {
				return Character.toLowerCase(c);
			}
			return c;
		}
	};

	/**
	 * Action that changes the case of case sensitive parts of the selected
	 * document part to upperCase.
	 */
	private ChangeCaseAction upperCaseAction = new ChangeCaseAction(
			GUIItemKeys.upperCaseAction, flp, this) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -112331974049963191L;

		@Override
		protected char process(char c) {
			return Character.toUpperCase(c);
		}
	};

	/**
	 * Action that changes the case of case sensitive parts of the selected
	 * document part to lowerCase.
	 */
	private ChangeCaseAction lowerCaseAction = new ChangeCaseAction(
			GUIItemKeys.lowerCaseAction, flp, this) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -112301974049763191L;

		@Override
		protected char process(char c) {
			return Character.toLowerCase(c);
		}
	};

	/** Action closing current tab. */
	private Action closeCurrentTabAction = new LocalizableAction(
			GUIItemKeys.closeCurrentTabAction, flp) {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = 8172817152308587486L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedTab = tabbedPane.getSelectedIndex();
			if (selectedTab == -1) {
				return;
			}
			DocumentTab document = openedFileDocuments.get(selectedTab);

			if (document.isEdited()) {
				int decision = JOptionPane.showConfirmDialog(
						JNotepadppFrame.this,
						LocalizationProvider.getInstance()
								.getString(GUIItemKeys.saveChangeFor)
								+ document.getTabName() + " ?",
						LocalizationProvider.getInstance()
								.getString(GUIItemKeys.systemMessage),
						JOptionPane.YES_NO_OPTION);
				if (decision == JOptionPane.YES_OPTION) {
					NotepadppUtil.saveSelectedTab(JNotepadppFrame.this,
							openedFileDocuments.get(selectedTab));
				}
			}
			tabbedPane.remove(selectedTab);
			openedFileDocuments.remove(selectedTab);
			repaint();

		}
	};

	/**
	 * Action displays the statistical infor about the currently selected tab.
	 */
	private Action statisticalInfo = new LocalizableAction(
			GUIItemKeys.statisticalInfo, flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1560339843717220273L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = getCurrent();
			if (textArea == null)
				return;

			int lines = textArea.getText().length()
					- textArea.getText().replaceAll("\\n+", "").length() + 1;
			int nonBlank = textArea.getText().replaceAll("\\s+", "").length();
			LocalizationProvider lp = LocalizationProvider.getInstance();

			JOptionPane.showMessageDialog(JNotepadppFrame.this,
					lp.getString(GUIItemKeys.statMessage + "1") + " "
							+ textArea.getText().length() + " "
							+ lp.getString(GUIItemKeys.statMessage + "2")
							+ nonBlank + " "
							+ lp.getString(GUIItemKeys.statMessage + "3")
							+ lines + " "
							+ lp.getString(GUIItemKeys.statMessage + "4"));
		}
	};

	/**
	 * Action exiting the currently selected tab.
	 */
	private Action exitAction = new LocalizableAction(GUIItemKeys.exitAction,
			flp) {

		/**
		 * Serial ID used in hash and equal function.
		 */
		private static final long serialVersionUID = -3356295127213421916L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int decision = JOptionPane.showConfirmDialog(JNotepadppFrame.this,
					LocalizationProvider.getInstance()
							.getString(GUIItemKeys.closeWindowMessage),
					LocalizationProvider.getInstance()
							.getString(GUIItemKeys.systemMessage),
					JOptionPane.YES_NO_OPTION);
			if (decision != JOptionPane.YES_OPTION) {
				return;
			}

			int noTabs = tabbedPane.getTabCount();
			for (int i = 0; i < noTabs; ++i) {
				tabbedPane.setSelectedIndex(0);
				closeCurrentTabAction.actionPerformed(null);
			}

			tabbedPane.removeAll();
			dispose();
			System.exit(0);
		}
	};

	/** English language {@link JMenuItem} listener. */
	private Action languageEngListener = new AbstractAction() {

		/**
		 * Serial ID used in hash and equal function.
		 */
		private static final long serialVersionUID = 4304025946645028973L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage(LanguageTag.EN);

		}
	};

	/** Deutsch language {@link JMenuItem} listener. */
	private Action languageDeListener = new AbstractAction() {

		/**
		 * Serial ID used in hash and equal function.
		 */
		private static final long serialVersionUID = 139969277040122982L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage(LanguageTag.DE);

		}
	};

	/** Croatian language {@link JMenuItem} listener. */
	private Action languageHrListener = new AbstractAction() {

		/**
		 * Serial ID used in hash and equal function.
		 */
		private static final long serialVersionUID = 6081854534611405688L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage(LanguageTag.HR);

		}
	};
	/** Ascending ordering action. */
	private SortUniqueAction ascendingAction = new SortUniqueAction(
			GUIItemKeys.ascendingSort, flp, this) {

		/** Version ID: */
		private static final long serialVersionUID = -3697271092177141370L;

		@Override
		protected List<String> process(List<String> list) {

			Comparator<String> descendingComparator = new Comparator<String>() {// uzlazno
				@Override
				public int compare(String o1, String o2) {
					Locale locale = new Locale(
							LocalizationProvider.getInstance().getLocale());
					Collator collorator = Collator.getInstance(locale);
					return collorator.compare(o1, o2);
				}

			};
			list.sort(descendingComparator);
			return list;
		}
	};

	/** Descending ordering action. */
	private SortUniqueAction descendingAction = new SortUniqueAction(
			GUIItemKeys.descendingSort, flp, this) {

		/** Version ID. */
		private static final long serialVersionUID = 4860600800648990506L;

		@Override
		protected List<String> process(List<String> list) {

			Comparator<String> ascendingComparator = new Comparator<String>() {// uzlazno
				@Override
				public int compare(String o1, String o2) {
					Locale locale = new Locale(
							LocalizationProvider.getInstance().getLocale());
					Collator collorator = Collator.getInstance(locale);
					return collorator.compare(o2, o1);
				}

			};
			list.sort(ascendingComparator);
			return list;
		}
	};

	/** Unique action which removes all duplicate-line from the selection.. */
	private SortUniqueAction uniqueAction = new SortUniqueAction(
			GUIItemKeys.uniqueAction, flp, this) {

		/** Version ID. */
		private static final long serialVersionUID = 4860600800648990506L;

		@Override
		protected List<String> process(List<String> list) {
			Set<String> set = new HashSet<>(list);
			return Arrays.asList(set.toArray(new String[set.size()]));
		}
	};

	/**
	 * Setting the actions parameters.
	 */
	private void createActions() {
		languageEngListener.putValue(Action.NAME, LanguageName.EN);
		languageDeListener.putValue(Action.NAME, LanguageName.DE);
		languageHrListener.putValue(Action.NAME, LanguageName.HR);

		setActionVariables(openNewBlankDocument, "Open new", "control N",
				KeyEvent.VK_N, "Used to open new blank document.");
		setActionVariables(openDocumentAction, "Open", "control O",
				KeyEvent.VK_O, "Used to open existing file from disk.");

		setActionVariables(saveDocumentAction, "Save", "control S",
				KeyEvent.VK_S, "Used to save currently selected document.");
		setActionVariables(saveDocumentAsAction, "Save as", "shift control S",
				KeyEvent.VK_Y, "Used to save as currently selected document.");

		setActionVariables(closeCurrentTabAction, "Close", "shift control Y",
				KeyEvent.VK_1, "Used to close current tab.");

		setActionVariables(deletedSelectedPartAction, "Delete", "F2",
				KeyEvent.VK_D, "Used to delete selected.");
		setActionVariables(toggleCaseAction, "Toggle case", "control F3",
				KeyEvent.VK_T,
				"Used for toggle selected part of the text, or whole if nothing selected.");
		setActionVariables(exitAction, "Exit", "control X", KeyEvent.VK_X,
				"Used for exiting the application.");

		setActionVariables(copySelectedAction, "Copy", "control C",
				KeyEvent.VK_C, "Used to copy selected.");
		setActionVariables(pasteSelectedAction, "Paste", "control V",
				KeyEvent.VK_V, "Used to paste clipboard on selected.");
		setActionVariables(cutSelectedAction, "Cut", "shift control C",
				KeyEvent.VK_2, "Used to cut selected.");

		setActionVariables(statisticalInfo, "Statistics", "control Z",
				KeyEvent.VK_Z,
				"Used to show the statistical info abou the currently selected tab");

	}

	/**
	 * Supplementary method setting the Action.NAME,
	 * Action.ACCELERATOR_KEY,Action.MNEMONIC_KEY,Action.SHORT_DESCRIPTION of
	 * the action {@code action}
	 * 
	 * @param action
	 *            action which parameters should be set
	 * @param name
	 *            name of the action
	 * @param accKey
	 *            acceleration key for the action
	 * @param mnemonic
	 *            mnemonic key for the action
	 * @param shortDesc
	 *            short description of the action
	 */
	private void setActionVariables(Action action, String name, String accKey,
			int mnemonic, String shortDesc) {
		
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(accKey));
		action.putValue(Action.MNEMONIC_KEY, mnemonic);
		
	}

	/**
	 * Method creates toolbars of the editor.
	 */
	private void createToolBars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(openNewBlankDocument));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveDocumentAsAction));
		toolBar.add(new JButton(closeCurrentTabAction));
		toolBar.add(new JButton(statisticalInfo));
		toolBar.addSeparator();
		toolBar.add(new JButton(copySelectedAction));
		toolBar.add(new JButton(pasteSelectedAction));
		toolBar.add(new JButton(cutSelectedAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(deletedSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));

		add(toolBar, BorderLayout.PAGE_START);

	}

	/**
	 * Method creates menus of the editor.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(
				new LocalizableAction(GUIItemKeys.fileMenu, flp));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openNewBlankDocument));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAsAction));
		fileMenu.addSeparator();

		fileMenu.add(new JMenuItem(closeCurrentTabAction));
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new JMenu(
				new LocalizableAction(GUIItemKeys.editMenu, flp));
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copySelectedAction));
		editMenu.add(new JMenuItem(pasteSelectedAction));
		editMenu.add(new JMenuItem(cutSelectedAction));

		editMenu.add(new JMenuItem(deletedSelectedPartAction));

		JMenu changeCaseMenu = new JMenu(
				new LocalizableAction(GUIItemKeys.changeCaseMenu, flp));
		menuBar.add(changeCaseMenu);
		changeCaseMenu.add(new JMenuItem(upperCaseAction));
		changeCaseMenu.add(new JMenuItem(lowerCaseAction));
		changeCaseMenu.add(new JMenuItem(toggleCaseAction));

		JMenu sortMenu = new JMenu(
				new LocalizableAction(GUIItemKeys.sortMenu, flp));
		menuBar.add(sortMenu);
		sortMenu.add(new JMenuItem(ascendingAction));
		sortMenu.add(new JMenuItem(descendingAction));
		sortMenu.add(new JMenuItem(uniqueAction));

		JMenu otherMenu = new JMenu(
				new LocalizableAction(GUIItemKeys.otherMenu, flp));
		menuBar.add(otherMenu);
		otherMenu.add(new JMenuItem(statisticalInfo));

		JMenu languageMenu = new JMenu(
				new LocalizableAction(GUIItemKeys.languageMenu, flp));
		menuBar.add(languageMenu);
		languageMenu.add(new JMenuItem(languageEngListener));
		languageMenu.add(new JMenuItem(languageDeListener));
		languageMenu.add(new JMenuItem(languageHrListener));

		setJMenuBar(menuBar);
		selectionDone(false);

	}

	/**
	 * Creating a new tab in the existing {@link JNotepadppFrame}.
	 * 
	 * @param textArea
	 *            the {@code JTextArea representing the opened document}
	 * @param path
	 *            path to the opened document
	 * @param icon
	 *            icon of new tab
	 */
	public void addTab(JTextArea textArea, Path path, IconsEnum icon) {
		Objects.requireNonNull(textArea);
		JPanel textAreaPane = new JPanel(new BorderLayout());
		textAreaPane.add(new JScrollPane(textArea), BorderLayout.CENTER);
		int position = tabbedPane.getTabCount();
		DocumentTab documentTab = new DocumentTab(path, textArea,
				JNotepadppFrame.this, position);
		documentTab.setEdited(icon == IconsEnum.GREEN_DISKETTE ? false : true);

		openedFileDocuments.add(documentTab);

		tabbedPane.add(textAreaPane);
		tabbedPane.setIconAt(position, icon.getIcon(iconProvider));
		tabbedPane.setTitleAt(position, documentTab.getTabName());
		tabbedPane.setToolTipTextAt(position, documentTab.getTabPath());

	}

	/**
	 * Gets the currently selected text area.
	 * 
	 * @return currently selected text area.
	 * 
	 */
	public JTextArea getCurrent() {
		int selectedTab = tabbedPane.getSelectedIndex();
		return selectedTab == -1 ? null
				: openedFileDocuments.get(selectedTab).getTextArea();
	}

	/**
	 * Sets the icon for tab at index {@code index} on {@code icon}.
	 * 
	 * @param index
	 *            of the tab which icon will be saved.
	 * @param icon
	 *            on which to set the icon of the tab.
	 */
	public void setIcon(int index, IconsEnum icon) {
		Objects.requireNonNull(icon);
		tabbedPane.setIconAt(index, icon.getIcon(iconProvider));
		openedFileDocuments.get(index)
				.setEdited(icon == IconsEnum.GREEN_DISKETTE ? false : true);
	}

	/**
	 * Updates the footer.
	 * 
	 * @param document
	 *            due to which update should be done.
	 */
	public void updateFooter(DocumentTab document) {
		Objects.requireNonNull(document);
		repaint();
		footer.changeAll(document);
	}

	/**
	 * Change buttons to enabled or disabled and otherwise.
	 * 
	 * @param enabled
	 */
	public void selectionDone(boolean enabled) {
		toggleCaseAction.setEnabled(enabled);
		upperCaseAction.setEnabled(enabled);
		lowerCaseAction.setEnabled(enabled);
		ascendingAction.setEnabled(enabled);
		descendingAction.setEnabled(enabled);
		uniqueAction.setEnabled(enabled);

	}

	/**
	 * Returns the currently selected tab.
	 * 
	 * @return currently selected tab.
	 */
	public DocumentTab getCurrentTab() {
		int selectedTab = tabbedPane.getSelectedIndex();
		return selectedTab == -1 ? null : openedFileDocuments.get(selectedTab);
	}

}
