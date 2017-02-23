package hr.fer.zemris.java.hw11.jnotepadpp.locale;

import java.util.Objects;

import javax.swing.JLabel;

/**
 * Label responsible form changing the label-name for GUI components based on
 * labels.
 * 
 * @author mia
 *
 */
public class LJLabel extends JLabel {

	/**
	 * Version ID used while creating hash.
	 */
	private static final long serialVersionUID = -5638347611815781326L;
	/** Key over which the label-name is saved. */
	private String key;

	/**
	 * Instance of {@code ILocalizationProvider.} on which the label will be
	 * registered.
	 */
	private ILocalizationProvider prov;

	/**
	 * Instance of {@code ILocalizationListener} whit which the label will be
	 * registered.
	 */
	private ILocalizationListener listener;

	/**
	 * Constructor. Registeres the instance of anonimous class to the given
	 * {@link ILocalizationProvider}.
	 * 
	 * @param key
	 *            key over which the label-name is saved.
	 * @param lp
	 *            localization provider of {@code key}'s value when localization
	 *            is changed
	 * 
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(lp);

		this.prov = lp;
		this.key = key;
		setText(lp.getString(key));

		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LJLabel.this.setText(prov.getString(LJLabel.this.key));

			}
		};

		prov.addLocalizationListener(listener);

	}
}
