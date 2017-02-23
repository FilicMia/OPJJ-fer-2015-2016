package hr.fer.zemris.java.hw11.jnotepadpp.locale;

import java.util.Objects;

/**
 * Decorator for some other {@link ILocalizationProvider}. It delegates all
 * calls of {@link #getString(String)} method to wrapped instance of
 * {@link ILocalizationProvider}.
 * 
 * It has two additional methods: {@link #connect()} and {@link #disconnect()}.
 * The idea is: this class is ILocalizationProvider which, when asked to resolve
 * a key delegates this request to wrapped (decorated) ILocalizationProvider
 * object. When user calls connect() on it, the method will register an instance
 * of anonimous ILocalizationListener on the decorated object. When user calls
 * disconnect(), this object will be deregistered from decorated object.
 * 
 * @author mia
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/** Wrapped /decorated {@link ILocalizationProvider}. */
	private ILocalizationProvider parent;

	/** Instance of annonimus {@link ILocalizationListener}. */
	private ILocalizationListener listener;

	/** Status of the bridge. If it is connected to the decorated object. */
	private boolean connected;

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            the object that will be decorated with this instance of
	 *            {@link LocalizationProviderBridge}, it can not be {@code null}
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		Objects.requireNonNull(parent);

		this.parent = parent;

	}

	@Override
	public String getString(String key) {
		Objects.requireNonNull(key);

		if (!connected) {
			connect();
		}

		return parent.getString(key);
	}

	/**
	 * Connect to the decorated object if not yet.
	 */
	public void connect() {
		if (!connected) {
			listener = new ILocalizationListener() {

				@Override
				public void localizationChanged() {
					fire();

				}
			};
			parent.addLocalizationListener(listener);
			connected = true;
		}
	}

	/**
	 * Disconnect form the decorated object if registered.
	 */
	public void disconnect() {
		if (connected) {
			parent.removeLocalizatonListener(listener);
			connected = false;
		}
	}

}
