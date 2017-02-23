package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Subject saving the color instance.
 * 
 * @author mia
 *
 */
public interface IColorProvider {
	/**
	 * Returns the current color held in this subject.
	 * 
	 * @return the current color held in this subject.
	 */
	public Color getCurrentColor();
}
