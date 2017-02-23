package hr.fer.zemris.java.hw16.jvdraw.central;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.JDrawingObjectListener;

/**
 * Implementation of drawing model.
 * 
 * @author mia
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/** List of {@link GeometricalObject} stored. */
	private List<GeometricalObject> objects;

	/** Listeners over this model. */
	private List<DrawingModelListener> listeners;

	{
		objects = Collections.synchronizedList(new ArrayList<>());
		listeners = Collections.synchronizedList(new ArrayList<>());

	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index > objects.size())
			return null;
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		Objects.requireNonNull(object);
		int changed = listeners.size();
		object.addGeometricalObjectListener(new JDrawingObjectListener() {

			@Override
			public void fireChange() {
				listeners.forEach(listener -> {
					listener.objectsChanged(DrawingModelImpl.this, changed,
							changed);
				});

			}
		});

		objects.add(object);

		listeners.forEach(listener -> {
			listener.objectsAdded(this, changed, changed);
		});

	}

	@Override
	public void remove(GeometricalObject object) {
		Objects.requireNonNull(object);
		objects.remove(object);

		int changed = listeners.size();
		listeners.forEach(listener -> {
			listener.objectsRemoved(this, changed, changed);
		});

	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);

	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);

	}

	@Override
	public void removeAllObjects() {
		objects.clear();

	}

}
