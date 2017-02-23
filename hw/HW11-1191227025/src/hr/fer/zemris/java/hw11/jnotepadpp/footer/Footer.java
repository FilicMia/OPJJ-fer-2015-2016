package hr.fer.zemris.java.hw11.jnotepadpp.footer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadppFrame;
import hr.fer.zemris.java.hw11.jnotepadpp.clock.Clock;
import hr.fer.zemris.java.hw11.jnotepadpp.document.DocumentTab;

/**
 * Class representing the footer of the {@link JNotepadppFrame}. It shows the
 * current length of the document beside with the information about the carret
 * position: line and row in which it is, the length of the selected part.
 * 
 * @author mia
 *
 */
public class Footer extends JPanel {

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 2040647774212869767L;

	/** Label showing the length of the currently selected tab. */
	private JLabel lengthLabel;
	/** Label showing the line in which the carret is positioned. */
	private JLabel carretLineLabel;
	/** Label showing the line in which the carret is positioned. */
	private JLabel carretColumnLabel;
	/** Label showing the length of currently selected part of the document. */
	private JLabel selectedLengthLabel;
	/** LAbel displaying the clock. */
	private JLabel clockLabel;

	/**
	 * Constructor.
	 * 
	 * @param notepad
	 *            {@link JNotepadppFrame} whose footer is this.
	 */

	public Footer(JNotepadppFrame notepad) {
		initGUI();

		notepad.add(this, BorderLayout.PAGE_END);
	}

	/**
	 * Initialization of the {@link Footer}.
	 */
	private void initGUI() {
		setLayout(new GridLayout(1, 7));
		setBackground(new Color(23, 114, 222));

		lengthLabel = new JLabel("length: 0", SwingConstants.LEFT);
		carretLineLabel = new JLabel("Ln: 0");
		carretColumnLabel = new JLabel("Col: 0");
		selectedLengthLabel = new JLabel("Sel: 0");
		Clock clock = new Clock();
		clock.start();

		clockLabel = clock.get();

		add(lengthLabel, "1,1");
		add(carretLineLabel, "1,4");
		add(carretColumnLabel, "1,5");
		add(selectedLengthLabel, "1,6");
		add(clockLabel, "1,7");

	}

	/**
	 * Set the text of the {@code lengthLabel} that is shows that the length of
	 * currently selected document is {@code length}.
	 * 
	 * @param length
	 *            size of the document
	 */
	public void setLengthLabel(int length) {
		lengthLabel.setText("length: " + length);
	}

	/**
	 * Set the text of the {@code carretLineLabel} that is shows that the
	 * carret's line is {@code line}.
	 * 
	 * @param line
	 *            carret's line
	 */
	public void setCarretLineLabel(int line) {
		carretLineLabel.setText("Ln: " + line);
	}

	/**
	 * Set the text of the {@code carretLineLabel} that is shows that the
	 * carret's column is {@code col}.
	 * 
	 * @param col
	 *            carret's column
	 */
	public void setCarretColumnLabel(int col) {
		carretColumnLabel.setText("Col: " + col);
	}

	/**
	 * Set the text of the {@code selectedLengthLabel} that is shows that the
	 * length of the selected part is {@code length}.
	 * 
	 * @param length
	 *            size of the selected part
	 */
	public void setSelectedLengthLabel(int length) {
		selectedLengthLabel.setText("Sel: " + length);
	}

	/**
	 * Changes all the labels.
	 * 
	 * @param documentTab
	 *            documet due to which the labels should be changed.
	 */
	public void changeAll(DocumentTab documentTab) {
		setLengthLabel(documentTab.getLength());
		setCarretColumnLabel(documentTab.getCarretColumn());
		setCarretLineLabel(documentTab.getCarretLine());
		setSelectedLengthLabel(documentTab.getSelectedLength());

	}

}
