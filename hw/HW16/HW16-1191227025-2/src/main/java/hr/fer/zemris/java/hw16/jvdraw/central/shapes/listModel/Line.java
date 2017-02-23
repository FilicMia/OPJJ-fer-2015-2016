package hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.ChangePanel;
import hr.fer.zemris.java.hw16.jvdraw.Util;

/**
 * Class implementing the geometrical shape of circle. Holds things relevant to
 * this {@link GeometricalObject} like x0 x coordinate of the beginning point
 * y0;y coordinate of the beginning point, x1;x coordinate of the end point y1;y
 * coordinate of the end point, {@link Color} in which the border of object is
 * painted.
 * 
 * @author mia
 *
 */
public class Line extends GeometricalObject {
	/** Name of the geometric shape. */
	public static final String NAME = "LINE";

	/** X coordinate of the beginning point. */
	private int x0;

	/** Y coordinate of the beginning point. */
	private int y0;

	/** X coordinate of the end point. */
	private int x1;

	/** Y coordinate of the end point. */
	private int y1;

	/** {@link Color} property of the border of object. */
	private Color color = Color.red;
	/**
	 * List of labels used when changing the object trough {@link JOptionPane}.
	 */
	private List<String> labels;

	/**
	 * Constructor.
	 */
	public Line() {
		color = Color.RED;
	}

	/**
	 * @return X coordinate of the beginning point.
	 */
	public int getX0() {
		return x0;
	}

	/**
	 * Sets the X coordinate of the beginning point.
	 * 
	 * @param x0
	 *            new X coordinate of the beginning point.
	 */
	public void setX0(int x0) {
		this.x0 = x0;
		fire();
	}

	/**
	 * @return Y coordinate of the beginning point.
	 */
	public int getY0() {
		return y0;
	}

	/**
	 * Sets the Y coordinate of the beginning point.
	 * 
	 * @param y0
	 *            new X coordinate of the beginning point.
	 */
	public void setY0(int y0) {
		this.y0 = y0;
		fire();
	}

	/**
	 * @return X coordinate of the end point.
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Sets the X coordinate of the end point.
	 * 
	 * @param x1
	 *            new X coordinate of the end point.
	 */
	public void setX1(int x1) {
		this.x1 = x1;
		fire();
	}

	/**
	 * @return Y coordinate of the end point.
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Sets the Y coordinate of the end point.
	 * 
	 * @param y1
	 *            new Y coordinate of the end point.
	 */
	public void setY1(int y1) {
		this.y1 = y1;
		fire();
	}

	/**
	 * @return {@link Color} in which the border of object is painted.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets {@link Color} in which the border of object is painted.
	 * 
	 * @param color
	 *            {@link Color} property of the border of object.
	 */
	public void setColor(Color color) {
		this.color = color;
		fire();
	}

	@Override
	public boolean isLine() {
		return true;
	}

	@Override
	public String toString() {
		return "LINE " + x0 + " " + y0 + " " + x1 + " " + y1 + " "
				+ color.getRed() + " " + color.getGreen() + " "
				+ color.getBlue();
	}

	@Override
	public void draw(Graphics2D g) {
		draw(g, 0, 0);
	}

	@Override
	public void draw(Graphics2D g, int xOffset, int yOffset) {
		Color colorBefore = g.getColor();

		g.setColor(color);
		Line2D line = new Line2D.Double(x0 - xOffset, y0 - yOffset,
				x1 - xOffset, y1 - yOffset);
		g.draw(line);

		g.setColor(colorBefore);
	}

	/**
	 * @return geometrical object in jvd form - LINE x0 y0 x1 y1 red green blue
	 */
	@Override
	public String getJVD() {
		return "LINE " + x0 + " " + y0 + " " + x1 + " " + y1 + " "
				+ color.getRed() + " " + color.getGreen() + " "
				+ color.getBlue();
	}

	@Override
	public void fromJVD(String jvdForm) {
		String[] items = jvdForm.split("\\s+");
		if (items[0].equals(NAME)) {
			x0 = Integer.parseInt(items[1]);
			y0 = Integer.parseInt(items[2]);
			x1 = Integer.parseInt(items[3]);
			y1 = Integer.parseInt(items[4]);
			color = new Color(Integer.parseInt(items[5]),
					Integer.parseInt(items[6]), Integer.parseInt(items[7]));
		}
	}

	@Override
	public int getX() {

		return Math.min(x0, x1);
	}

	@Override
	public int getY() {

		return Math.min(y0, y1);
	}

	@Override
	public int getWidth() {
		return Math.abs(x1 - x0) + 1;
	}

	@Override
	public int getHeight() {
		return Math.abs(y1 - y0) + 1;
	}

	@Override
	public void changeWithJOptionPane() {
		int input;

		input = Util
				.getInputInteger("Set x coordinete of starting point:");
		if (input != -1)
			setX0(input);
		input = Util
				.getInputInteger("Set y coordinete of starting point:");
		if (input != -1)
			setY0(input);
		input = Util.getInputInteger("Set x coordinete of enyd point:");
		if (input != -1)
			setX1(input);
		input = Util.getInputInteger("Set y coordinete of end point:");
		if (input != -1)
			setY1(input);

		int red = Util.getInputInteger("Set red of border color:");
		if (red == -1) {
			return;
		}

		int green = Util.getInputInteger("Set green of border color:");
		if (green == -1) {
			return;
		}

		int blue = Util.getInputInteger("Set blue of border color:");
		if (blue == -1) {
			return;
		}

		setColor(new Color(red, green, blue));
	}

	public void changeWithJOptionPane2() {
		if (labels == null) {
			createLabels();
		}
		List<JTextField> inputs = null;
		int input;

		String getInputed = null;
		ChangePanel change = new ChangePanel(labels.size(), labels);
		int decision = JOptionPane.showConfirmDialog(null, change,
				"Set parameters", JOptionPane.OK_CANCEL_OPTION);
		if (decision != JOptionPane.OK_OPTION) {
			return;
		}

		inputs = change.getField();

		getInputed = inputs.get(0).getText();
		input = Util.getInteger(getInputed);
		if (input != -1) {
			setX0(input);
		} else {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property x0 will be done.");
		}

		getInputed = inputs.get(1).getText();
		input = Util.getInteger(getInputed);
		if (input != -1) {
			setY0(input);
		} else {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property y0 will be done.");
		}

		getInputed = inputs.get(2).getText();
		input = Util.getInteger(getInputed);
		if (input != -1) {
			setX1(input);
		} else {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property x1 will be done.");
		}

		getInputed = inputs.get(3).getText();
		input = Util.getInteger(getInputed);
		if (input != -1) {
			setY1(input);
		} else {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property y1 will be done.");
		}

		getInputed = inputs.get(4).getText();
		int red = Util.getInteger(getInputed);
		if (red == -1) {

			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property color will be done.");
			return;
		}

		getInputed = inputs.get(5).getText();
		int green = Util.getInteger(getInputed);
		if (green == -1) {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property color will be done.");
			return;
		}

		getInputed = inputs.get(6).getText();
		int blue = Util.getInteger(getInputed);
		if (blue == -1) {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property color will be done.");
			return;
		}

		setColor(new Color(red, green, blue));
	}

	/**
	 * Create list of labels used in {@link JOptionPane} when changing the
	 * properties of object.
	 */
	private void createLabels() {
		labels = new ArrayList<>();
		labels.add("Set x coordinete of starting point:");
		labels.add("Set y coordinete of starting point:");
		labels.add("Set x coordinete of end point:");
		labels.add("Set y coordinete of end point:");

		labels.add("Set red of border color:");
		labels.add("Set green of border color:");
		labels.add("Set blue of border color:");

	}

}
