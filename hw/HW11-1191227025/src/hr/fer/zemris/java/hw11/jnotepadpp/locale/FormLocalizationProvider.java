package hr.fer.zemris.java.hw11.jnotepadpp.locale;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class derived form {@link LocalizationProviderBridge}. It call
 * {@link #connect()} when window is open and {@link #disconnect()} when closed.
 * 
 * @author mia
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            the object that will be decorated with this instance of
	 *            {@link LocalizationProviderBridge}, it can not be {@code null}
	 * @param window
	 *            {@link JFrame} instance due to which opening and closing the
	 *            connect status change.
	 */
	public FormLocalizationProvider(ILocalizationProvider parent,
			JFrame window) {
		super(parent);
		window.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				FormLocalizationProvider.this.connect();

			}

			@Override
			public void windowClosed(WindowEvent e) {
				FormLocalizationProvider.this.disconnect();

			}

		});

	}

}
