package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * Load the icon needed in {@link JNotepadppFrame}. All icons are placed in
 * icons subdirectory. All icons available are listed in {@link IconsEnum}.
 * 
 * @author mia
 *
 */
public class IconsProvider {

	/**
	 * Gets the icon specified by {@code icon}, creates the {@link ImageIcon}
	 * representation of it and returns it.
	 * 
	 * @param icon
	 *            wanted icon
	 * @return the {@link ImageIcon} representation of {@code icon}
	 * @throws IOException
	 *             if fetching of the icon from icon path went wrong.
	 */
	public ImageIcon getIcon(IconsEnum icon) throws IOException {
		InputStream is = this.getClass()
				.getResourceAsStream(icon.relativePath());

		if (is == null) {
			throw new IllegalArgumentException("Icon form "
					+ icon.relativePath() + " can not be loaded. IOERROR!");
		}

		byte[] bytes = getBytesFromInputStream(is);
		is.close();

		return new ImageIcon(bytes);
	}

	/**
	 * Read and returns all bytes available in {@link InputStream}.
	 * 
	 * @param is
	 *            input strem
	 * @return all bytes available in {@link InputStream}, {@code null} if no
	 *         bytes are available
	 * @throws IOException
	 *             if reading from {@code in } goes wrong.
	 */
	public static byte[] getBytesFromInputStream(InputStream is)
			throws IOException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
			byte[] buffer = new byte[0xFFFF];

			for (int len; (len = is.read(buffer)) != -1;)
				os.write(buffer, 0, len);

			os.flush();

			return os.toByteArray();
		}
	}

}
