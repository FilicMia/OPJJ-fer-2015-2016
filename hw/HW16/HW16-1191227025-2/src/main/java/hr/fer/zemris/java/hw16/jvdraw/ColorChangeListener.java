package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Listener interface for {@link IColorProvider}.
 * 
 * @author mia
 *
 */
public interface ColorChangeListener {
	/**
	 * Action fired when the new color has been selected in
	 * {@link IColorProvider} on which this listener is attached. That instance
	 * of {@link IColorProvider} is also passed as argument {@code source}.
	 * 
	 * @param source
	 *            Color provider instance.
	 * @param oldColor
	 *            old/replaced color.
	 * @param newColor
	 *            new color with which has been the old one replaced
	 */
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor);
}
