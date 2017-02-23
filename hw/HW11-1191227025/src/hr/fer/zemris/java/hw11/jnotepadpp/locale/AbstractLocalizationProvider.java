/**
 * 
 */
package hr.fer.zemris.java.hw11.jnotepadpp.locale;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provide the implementation of most of the {@link ILocalizationProvider}'s
 * methods. We suppose that the same listener will be no more than once
 * registered to this Subject.
 * 
 * @author mia
 *
 */
public abstract class AbstractLocalizationProvider
		implements ILocalizationProvider {

	/** List of registered listeners. */
	private List<ILocalizationListener> listeners;

	/** List of listeners that wants to deregister. */
	private List<ILocalizationListener> deregistred;

	/** List of newly added listeners. */
	private List<ILocalizationListener> newlyRegistred;

	/**
	 * Constructor.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
		deregistred = new ArrayList<>();
		newlyRegistred = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		Objects.requireNonNull(l);

		newlyRegistred.add(l);
	}

	@Override
	public void removeLocalizatonListener(ILocalizationListener l) {
		Objects.requireNonNull(l);

		deregistred.add(l);
	}

	/**
	 * Fire to change to all registered listeners.
	 */
	public void fire() {
		listeners.removeAll(deregistred);
		deregistred.clear();

		listeners.addAll(newlyRegistred);
		newlyRegistred.clear();

		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}

	}

}
