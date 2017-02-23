package hr.fer.zemris.java.hw16.jvdraw.central;

/**
 * Drawing model listener listeners to the objects draw on
 * {@link JDrawingCanvas} trough changes in DrawingObjectListModel
 * 
 * @author mia
 *
 */
public interface DrawingModelListener {
	/**
	 * To notify the drawing model listener that specific drawing model has been
	 * changed. New components has been added.
	 * 
	 * @param source
	 *            source which has been changed
	 * @param index0
	 *            start of interval that has been changed.
	 * @param index1
	 *            end of interval that has been changed.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * To notify the drawing model listener that specific drawing model has been
	 * changed. some components has been deleted.
	 * 
	 * @param source
	 *            source which has been changed
	 * @param index0
	 *            start of interval that has been changed.
	 * @param index1
	 *            end of interval that has been changed.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * To notify the drawing model listener that specific drawing model has been
	 * changed. Components has been changed..
	 * 
	 * @param source
	 *            source which has been changed
	 * @param index0
	 *            start of interval that has been changed.
	 * @param index1
	 *            end of interval that has been changed.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}