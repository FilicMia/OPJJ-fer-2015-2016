/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.locale;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;

/**
 * Action responsible form changing the action-name for GUI components based on
 * actions.
 * 
 * @author mia
 *
 */
public class LocalizableAction extends AbstractAction {

	/**
	 * Version ID used while creating hash.
	 */
	private static final long serialVersionUID = -3822447988824726234L;

	/** Key over which the action-name is saved. */
	private String key;

	/**
	 * Instance of {@code ILocalizationProvider.} on which the action will be
	 * registered.
	 */
	private ILocalizationProvider prov;

	/**
	 * Instance of {@code ILocalizationListener} whit which the action will be
	 * registered.
	 */
	private ILocalizationListener listener;
	/** Need to be appended to the key to get key for action description. */
	private static final String DESC = "Desc";

	/**
	 * Constructor. Registeres the instance of anonimous class to the given
	 * {@link ILocalizationProvider}.
	 * 
	 * @param key
	 *            key over which the action-name is saved.
	 * @param lp
	 *            localization provider of {@code key}'s value when localization
	 *            is changed
	 * 
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(lp);

		this.prov = lp;
		this.key = key;
		putValue(NAME, lp.getString(key));
		putValue(SHORT_DESCRIPTION, lp.getString(key) + DESC);

		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizableAction.this.putValue(NAME,
						prov.getString(LocalizableAction.this.key));
				LocalizableAction.this.putValue(SHORT_DESCRIPTION,
						prov.getString(LocalizableAction.this.key + DESC));
			}
		};

		prov.addLocalizationListener(listener);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
