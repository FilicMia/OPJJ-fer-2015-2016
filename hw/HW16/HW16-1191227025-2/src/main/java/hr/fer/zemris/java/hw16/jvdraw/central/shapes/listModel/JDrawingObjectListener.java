package hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel;

/**
 * Interface for {@link GeometricalObject} listeners. The listener will be
 * notified if the object over which it listens change.
 * 
 * @author mia
 *
 */
public interface JDrawingObjectListener {

	/**
	 * Called if the change has heppened.
	 */
	public void fireChange();

}
