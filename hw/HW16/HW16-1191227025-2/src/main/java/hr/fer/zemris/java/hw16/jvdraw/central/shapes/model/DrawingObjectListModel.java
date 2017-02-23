/**
 * 
 */
package hr.fer.zemris.java.hw16.jvdraw.central.shapes.model;

import java.util.Objects;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.central.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.central.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.GeometricalObject;

/**
 * Class extends {@link AbstractListModel}. It stores {@link GeometricalObject}s
 * . The {@link GeometricalObject}s are fetched trough internally stored
 * {@link DrawingModel}. It is an object adapter for the DrawingModel.
 * 
 * @author mia
 *
 */
public class DrawingObjectListModel
		extends AbstractListModel<GeometricalObject> {

	/** Internally stored {@link DrawingModel}. */
	private DrawingModel drawingModel;
	/** Listener over drawing model. */
	private DrawingModelListener drawingModelListener;

	/**
	 * Constructor.
	 * 
	 * @param drawingModel
	 *            which adapter will this be
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		Objects.requireNonNull(drawingModel);
		this.drawingModel = drawingModel;

		drawingModelListener = new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0,
					int index1) {
				DrawingObjectListModel.this.fireIntervalRemoved(source, index0,
						index1);

			}

			@Override
			public void objectsChanged(DrawingModel source, int index0,
					int index1) {
				DrawingObjectListModel.this.fireContentsChanged(source, index0,
						index1);

			}

			@Override
			public void objectsAdded(DrawingModel source, int index0,
					int index1) {
				DrawingObjectListModel.this.fireIntervalAdded(source, index0,
						index1);

			}
		};

		drawingModel.addDrawingModelListener(drawingModelListener);
	}

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 736691840280076690L;

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

}
