/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.locale;

/**
 * Observer that listens for the change of locale.
 * 
 * @author mia
 *
 */
public interface ILocalizationListener {
	/**
	 * Action that performed if the locale has changed.
	 */
	public void localizationChanged();

}
