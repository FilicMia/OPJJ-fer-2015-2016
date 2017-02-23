/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.locale;

/**
 * Interface for the subject which notifys all registered listeners when a
 * selected language has changed.
 * 
 * @author mia
 *
 */
public interface ILocalizationProvider {
	/**
	 * Adds the listener. Null values are not allowed.
	 * 
	 * @param l
	 *            listener
	 */
	public void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes the listener.
	 * 
	 * @param l
	 *            listener
	 */
	public void removeLocalizatonListener(ILocalizationListener l);

	/**
	 * Method which takes the key and returns the localization for that key.
	 * 
	 * @param key
	 *            key of the wanted localization
	 * @return localization of the key provided
	 */
	public String getString(String key);

}
