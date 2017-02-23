package hr.fer.zemris.java.hw16.jvdraw.central;

import java.awt.Color;
import java.nio.file.Path;

import hr.fer.zemris.java.hw16.jvdraw.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.Circle;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.FCircle;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.Line;

/**
 * Holding the enviroment for {@link JDrawingCanvas}. Which ToggleButton is
 * clicked or has the object been partial drawn etc(is in process of drawing).
 */
public class JDrawingCanvasEnviroment {
	/** Value of {@link #selectedObject} if line button is pressed. */
	public static final String LINE = "Line";
	/** Value of {@link #selectedObject} if circle button is pressed. */
	public static final String CIRCLE = "Circle";
	/** Value of {@link #selectedObject} if filled circle button is pressed. */
	public static final String FCIRCLE = "Filled circle";

	/**
	 * Path where the {@link JDrawingCanvas} should be saved as jvd file. jvd
	 * file saves {@link JDrawingCanvas} in form it is represented in
	 * {@link DrawingModelObjectList} on screen.
	 */
	private Path documentPath = null;

	/**
	 * Indicates if the curent version of {@link JDrawingCanvas} is save on disk
	 * in jvd form.
	 */
	private boolean saved = false;

	/** Currently selected Geometrical object. */
	private String selectedObject = "";

	/** Indicator of starting drawing of the {@link GeometricalObject} */
	private boolean drawingStarted;
	/**
	 * Null is no object is drawing, otherwise the object that is currently
	 * drawing(if drawingStarted is set to true).
	 */
	private GeometricalObject drawing;

	/**
	 * Sets the color of foreground of the object that is drawn at the moment.
	 */
	private ColorChangeListener colorForegroundListener = new ColorChangeListener() {

		@Override
		public void newColorSelected(IColorProvider source, Color oldColor,
				Color newColor) {
			if (drawing != null) {
				if (drawing.isCircle()) {
					((Circle) drawing).setColor(newColor);
				}
				if (drawing.isLine()) {
					((Line) drawing).setColor(newColor);
				}
				if (drawing.isFCircle()) {
					((FCircle) drawing).setColor(newColor);
				}
			}

		}
	};

	/** Sets the color of filled circle if it is drawn at the moment. */
	private ColorChangeListener colorBackgroundListener = new ColorChangeListener() {

		@Override
		public void newColorSelected(IColorProvider source, Color oldColor,
				Color newColor) {
			if (drawing != null) {
				if (drawing.isFCircle()) {
					((FCircle) drawing).setBackground(newColor);
				}
			}

		}
	};

	/**
	 * Constructor.
	 * 
	 * @param background
	 *            filling color of {@link GeometricalObject}
	 * @param foreground
	 *            line color of {@link GeometricalObject}
	 */
	public JDrawingCanvasEnviroment(JColorArea foreground,
			JColorArea background) {

		foreground.addColorChangeListener(colorForegroundListener);
		background.addColorChangeListener(colorBackgroundListener);

	}

	/**
	 * Sets the {@link #selectedObject} on {@link LINE}.
	 */
	public void setLine() {
		selectedObject = LINE;
	}

	/**
	 * Sets the {@link #selectedObject} on {@link CIRCLE}.
	 */
	public void setCircle() {
		selectedObject = CIRCLE;
	}

	/**
	 * Sets the {@link #selectedObject} on {@link FCIRCLE}.
	 */
	public void setFilledCircle() {
		selectedObject = FCIRCLE;
	}

	/**
	 * Sets the {@link #selectedObject} on "" if it maches {@code name}.
	 * 
	 * @param name
	 *            object to be disselected.
	 */
	public void disable(String name) {
		if (selectedObject == name) {
			selectedObject = "";
		}
	}

	/**
	 * Starting press for geometric object.
	 * 
	 * @param x
	 *            x coordinants of pressed point
	 * @param y
	 *            y coordinants of pressed point
	 * @param foreground
	 *            Currently set foreground color
	 * @param background
	 *            Currently set background color
	 * @return Geometric object in creation.
	 */
	public GeometricalObject firstPress(int x, int y, Color foreground,
			Color background) {// dissable buttons za
		// change

		switch (selectedObject) {
		case LINE: {
			Line line = new Line();
			line.setX0(x);
			line.setY0(y);
			line.setX1(x);
			line.setY1(y);

			line.setColor(foreground);

			drawing = line;
			drawingStarted = true;
			break;
		}
		case CIRCLE: {
			Circle circle = new Circle();
			circle.setCenterx(x);
			circle.setCentery(y);
			circle.setColor(foreground);

			drawing = circle;
			drawingStarted = true;
			break;
		}

		case FCIRCLE:
			FCircle filledCircle = new FCircle();
			filledCircle.setCenterx(x);
			filledCircle.setCentery(y);
			filledCircle.setColor(foreground);
			filledCircle.setBackground(background);

			drawing = filledCircle;
			drawingStarted = true;
			break;
		default:
			drawing = null;
		}

		return drawing;
	}

	/**
	 * Method called when passing with mouse over JDrawingCanvas. If first click
	 * has been done, currently stored object is resized, otherwise nothing
	 * happens.
	 * 
	 * @param x
	 *            x coordinate of the current point
	 * @param y
	 *            y coordinate of the current point
	 */
	public void resizeObject(int x, int y) {
		if (!drawingStarted)
			return;

		switch (selectedObject) {
		case LINE: {
			Line line = (Line) drawing;
			line.setX1(x);
			line.setY1(y);

			break;
		}
		case CIRCLE: {
			Circle circle = (Circle) drawing;
			circle.setRadius((int) Math
					.round(Math.sqrt(Math.pow(x - circle.getCenterx(), 2)
							+ Math.pow(y - circle.getCentery(), 2))));

			break;
		}

		case FCIRCLE:
			FCircle fcircle = (FCircle) drawing;
			fcircle.setRadius((int) Math
					.round(Math.sqrt(Math.pow(x - fcircle.getCenterx(), 2)
							+ Math.pow(y - fcircle.getCentery(), 2))));
			break;
		}
	}

	/**
	 * Ending press for Geometric object.
	 * 
	 * @param x
	 *            x coordinants of pressed point
	 * @param y
	 *            y coordinants of pressed point
	 */
	public void secondPress(int x, int y) {
		resizeObject(x, y);

		drawingStarted = false;
		drawing = null;
	}

	/**
	 * @return true if drawing of some object has started, false otherwise.
	 */
	public boolean isDrawingStarted() {
		return drawingStarted;
	}

	/**
	 * @return the path where the {@link JDrawingCanvas} whose this environment
	 *         is should be saved.
	 */
	public Path getDocumentPath() {
		return documentPath;
	}

	/**
	 * Sets the path where the {@link JDrawingCanvas} whose this environment is
	 * should be saved.
	 * 
	 * @param documentPath
	 *            the path where the {@link JDrawingCanvas} whose this
	 *            environment is should be saved.
	 */
	public void setDocumentPath(Path documentPath) {
		this.documentPath = documentPath;
	}

	/**
	 * @return true is current version of {@link JDrawingCanvas} whose this
	 *         environment is saved,false otherwise
	 */
	public boolean isSaved() {
		return saved;
	}

	/**
	 * Sets the indicator if the current version of {@link JDrawingCanvas} whose
	 * this environment is saved.
	 * 
	 * @param saved
	 *            indicator if current version of {@link JDrawingCanvas} whose
	 *            this environment is saved
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

}
