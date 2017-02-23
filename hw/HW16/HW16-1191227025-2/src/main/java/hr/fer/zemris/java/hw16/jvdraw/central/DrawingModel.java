package hr.fer.zemris.java.hw16.jvdraw.central;

import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.GeometricalObject;

/**
 * @author mia
 *
 */
public interface DrawingModel {
	/**
	 * Returns the number of currently stored {@link GeometricalObject}.
	 * 
	 * @return the number of currently stored {@link GeometricalObject}.
	 */
	public int getSize();

	/**
	 * Fetches {@link GeometricalObject} on index {@code index}.
	 * 
	 * @param index
	 *            index of {@link GeometricalObject} to be returned.
	 * @return {@link GeometricalObject} on index {@code index}
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds {@link GeometricalObject}.
	 * 
	 * @param object
	 *            {@link GeometricalObject} to be added
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes {@link GeometricalObject}.
	 * 
	 * @param object
	 *            {@link GeometricalObject} to be removed.
	 */
	public void remove(GeometricalObject object);

	/**
	 * Adds {@link DrawingModelListener}.
	 * 
	 * @param l
	 *            listener of drawing model.
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes provided {@link DrawingModelListener}.
	 * 
	 * @param l
	 *            listener of drawing model.
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/** Removes all drawing objects from the {@link DrawingModel}. */
	public void removeAllObjects();

}
