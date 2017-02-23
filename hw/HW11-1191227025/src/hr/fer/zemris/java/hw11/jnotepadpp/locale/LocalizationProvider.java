package hr.fer.zemris.java.hw11.jnotepadpp.locale;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Localization provider implements the {@link LocalizationProvider} trough
 * {@link AbstractLocalizationProvider}.
 * 
 * @author mia
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Path to the locale's source used for fetching the key's values.
	 */
	private static final String PATH = "hr.fer.zemris.java.hw11.jnotepadpp.locale.prijevodi";

	/**
	 * Currently selected locale.
	 */
	private String language;

	/** Private {@link ResoureceBundle} for handling the translation. */
	private ResourceBundle bundle;

	/**
	 * Instance of {@link LocalizationProvider}.
	 */
	private static LocalizationProvider ls;

	/** Constructor. */
	private LocalizationProvider() {
		super();
		language = "en";
		bundle = ResourceBundle.getBundle(PATH,
				Locale.forLanguageTag(language));
	}

	/**
	 * Static method used to provide the created localization provider.
	 * 
	 * @return instance of {@link LocalizationProvider}.
	 * 
	 **/
	public static LocalizationProvider getInstance() {
		if (ls == null) {
			ls = new LocalizationProvider();
		}

		return ls;
	}

	/**
	 * Function setting the language/locale.
	 * 
	 * @param language
	 *            new language tag
	 * 
	 */
	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle(PATH,
				Locale.forLanguageTag(language));
		fire();
	}

	/**
	 * Returns the locale currently defined.
	 * 
	 * @return String representation of locale currently used.
	 * 
	 */
	public String getLocale() {
		return language;
	}

	@Override
	public String getString(String key) {
		Objects.requireNonNull(key);

		return bundle.getString(key);
	}

}
