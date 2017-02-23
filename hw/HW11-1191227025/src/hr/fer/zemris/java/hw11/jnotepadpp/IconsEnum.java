package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * Storing available icons with its paths.
 * 
 * @author mia
 *
 */
public enum IconsEnum {

	/**
	 * Path to little red diskette icon.
	 */
	RED_DISKETTE("icons/Floppy-red-icon.png"),

	/**
	 * Path to little green diskette icon.
	 */
	GREEN_DISKETTE("icons/Floppy-green-icon.png");

	/**
	 * Relative path to the icon.
	 */
	private String relativePath;

	/** {@link ImageIcon} representation of this icon. */
	private ImageIcon imageIcon = null;

	/**
	 * Constructor.
	 * 
	 * @param relativePath
	 *            relative path to the icon from point in where this class is
	 *            put.
	 */
	private IconsEnum(String relativePath) {
		this.relativePath = relativePath;
	}

	/**
	 * @return relative path to the icon.
	 */
	public String relativePath() {
		return relativePath;
	}

	/**
	 * Gets the {@link ImageIcon} for this icon.
	 * 
	 * @param provider
	 *            gets the {@link ImageIcon} for this icon
	 * @return {@link ImageIcon} for this icon
	 */
	public ImageIcon getIcon(IconsProvider provider) {
		if (imageIcon == null) {

			try {
				imageIcon = provider.getIcon(this);
			} catch (IOException e) {
				return null;
			}
		}

		return imageIcon;
	}

}
