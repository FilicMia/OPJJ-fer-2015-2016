package hr.fer.zemris.java.hw16.jvdraw.central;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.GeometricalObject;

/**
 * Canvas used for drawing objects. Lines, circles and filled circles.
 * 
 * @author mia
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** Enviroment for drawing canvas. */
	private JDrawingCanvasEnviroment env;

	/**
	 * Drawing model used for holding the objects that will be drawn in this
	 * JDrawingCanvas.
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructor.
	 * 
	 * @param drawingModel
	 *            drawing model used.
	 * 
	 * @param background
	 *            filling color of {@link GeometricalObject}
	 * @param foreground
	 *            line color of {@link GeometricalObject}
	 */
	public JDrawingCanvas(DrawingModel drawingModel, JColorArea foreground,
			JColorArea background) {
		this.drawingModel = drawingModel;
		env = new JDrawingCanvasEnviroment(foreground, background);
		env.setSaved(true);
	}

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = -4115242712655452526L;

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		env.setSaved(false);
		repaint();

	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		env.setSaved(false);
		repaint();

	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		env.setSaved(false);
		repaint();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		if (drawingModel == null)
			return;

		int count = drawingModel.getSize();
		for (int i = 0; i < count; ++i) {
			drawingModel.getObject(i).draw(g2d);
		}

	}

	/**
	 * @return enviroment of this JDrawingCanvas.
	 */
	public JDrawingCanvasEnviroment getEnv() {
		return env;
	}

	/**
	 * Gets the drawing model for this JVDrawFrame.
	 * 
	 * @return drawing model for this canvas.
	 */
	public DrawingModel getDrawingModel() {
		return drawingModel;
	}

	/**
	 * @return String representation of {@link DrawingModel} i jvd format.
	 */
	public String getJVS() {
		StringBuilder sb = new StringBuilder();

		int count = drawingModel.getSize();
		for (int i = 0; i < count; ++i) {
			sb.append(drawingModel.getObject(i).getJVD());
			sb.append("\n");
		}

		return sb.toString();
	}

}
