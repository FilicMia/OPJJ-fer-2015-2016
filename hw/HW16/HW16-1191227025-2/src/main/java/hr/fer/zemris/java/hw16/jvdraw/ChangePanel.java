package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Panel used for changing the elements in Geometric shape. */
public class ChangePanel extends JPanel {
	/**
	 * Fields in this {@link ChangePanel}.
	 */
	private List<JTextField> fields;

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = -7120687065518127623L;

	/**
	 * Constructor.
	 * 
	 * @param numberOfFields
	 *            required. Fields are {@link JTextField} placed in one column.
	 * @param labels
	 *            each field has it own labal provided in this list
	 */
	public ChangePanel(int numberOfFields, List<String> labels) {
		Objects.requireNonNull(labels);

		fields = new ArrayList<>();
		setLayout(new GridLayout(numberOfFields, 2));

		for (int i = 0; i < numberOfFields; ++i) {
			JTextField field = new JTextField();
			fields.add(field);
			add(new JLabel(labels.get(i)));
			add(field);
		}
	}

	/**
	 * @return the list of {@link JTextField}s of this {@link ChangePanel}.
	 */
	public List<JTextField> getField() {
		return fields;
	}

}
