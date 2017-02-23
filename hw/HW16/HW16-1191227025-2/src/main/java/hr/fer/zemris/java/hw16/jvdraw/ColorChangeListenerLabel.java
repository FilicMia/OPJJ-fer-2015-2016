package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

/**
 * Label listening to IColorProvider.
 * 
 * @author mia
 *
 */
public class ColorChangeListenerLabel extends JLabel
		implements ColorChangeListener {

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Subject on which the label listens mapped on its name. The label provides
	 * view subject_name1:color_selected , ...,subject_nameN:color_selected
	 */
	private Map<String, IColorProvider> subjects = Collections
			.synchronizedMap(new HashMap<>());

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		StringBuilder sb = new StringBuilder();

		subjects.forEach((k, v) -> {
			sb.append(k + " : (" + v.getCurrentColor().getRed() + ", "
					+ v.getCurrentColor().getGreen() + ", "
					+ v.getCurrentColor().getBlue() + ") ");
		});
		this.setText(sb.toString());
		System.out.println(sb.toString());

	}

	/**
	 * Adds the subject to this listener, if it has not been already attached.
	 * 
	 * @param name
	 *            name attached to this subject
	 * @param subject
	 *            subject to be listened
	 */
	public void addSubject(String name, IColorProvider subject) {
		subjects.putIfAbsent(name, subject);
		newColorSelected(subject, subject.getCurrentColor(),
				subject.getCurrentColor());
	}

	/**
	 * Removes the subject to this listener, if it has been previously attached.
	 * 
	 * @param name
	 *            name attached to this subject
	 * @param subject
	 *            subject to be listened
	 */
	public void removeSubject(String name, IColorProvider subject) {
		subjects.remove(name, subject);
		newColorSelected(subject, subject.getCurrentColor(),
				subject.getCurrentColor());
	}

}
