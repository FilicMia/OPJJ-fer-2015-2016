package hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.ChangePanel;
import hr.fer.zemris.java.hw16.jvdraw.Util;

/**
 * Class implementing the geometrical shape of circle. Holds things relevant to
 * this {@link GeometricalObject} like centerx, centery, radius, {@link Color}
 * in which the border of object is painted.
 * 
 * @author mia
 *
 */
public class Circle extends GeometricalObject {
	/** Name of the geometric shape. */
	public static final String NAME = "CIRCLE";

	/** X coordinate of circle center. */
	private int centerx;
	/** Y coordinate of circle center. */
	private int centery;
	/** Radius of circle. */
	private int radius;
	/**
	 * {@link Color} in which the border of object is painted.
	 */
	private Color color;

	/**
	 * List of labels used when changing the object trough {@link JOptionPane}.
	 */
	protected List<String> labels;

	/**
	 * @return X coordinate of circle center.
	 */
	public int getCenterx() {
		return centerx;
	}

	/**
	 * Sets new X coordinate of circle center.
	 * 
	 * @param centerx
	 *            X coordinate of circle center.
	 */
	public void setCenterx(int centerx) {
		this.centerx = centerx;
		fire();
	}

	/**
	 * @return Y coordinate of circle center.
	 */
	public int getCentery() {
		return centery;
	}

	/**
	 * Sets new Y coordinate of circle center.
	 * 
	 * @param centery
	 *            Y coordinate of circle center.
	 */
	public void setCentery(int centery) {
		this.centery = centery;
		fire();
	}

	/**
	 * @return radius of the circle
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets the new radius of the circle.
	 * 
	 * @param radius
	 *            new radius of the circle.
	 */
	public void setRadius(int radius) {
		this.radius = radius;
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
	public boolean isCircle() {
		return true;
	}

	@Override
	public String toString() {
		return "CIRCLE " + centerx + " " + centery + " " + radius + " "
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
		Shape drawCircle = new Ellipse2D.Double(
				getCenterx() - getRadius() - xOffset,
				getCentery() - getRadius() - yOffset, 2 * getRadius(),
				2 * getRadius());
		g.draw(drawCircle);

		g.setColor(colorBefore);
	}

	/**
	 * @return the geometrical object in jvd form - CIRCLE centerx centery
	 *         radius red green blue
	 */
	@Override
	public String getJVD() {
		return "CIRCLE " + centerx + " " + centery + " " + radius + " "
				+ color.getRed() + " " + color.getGreen() + " "
				+ color.getBlue();
	}

	@Override
	public void fromJVD(String jvdForm) {
		String[] items = jvdForm.split("\\s+");
		if (items[0].equals(NAME)) {
			centerx = Integer.parseInt(items[1]);
			centery = Integer.parseInt(items[2]);
			radius = Integer.parseInt(items[3]);
			color = new Color(Integer.parseInt(items[4]),
					Integer.parseInt(items[5]), Integer.parseInt(items[6]));
		}
	}

	@Override
	public int getX() {

		return centerx - radius;
	}

	@Override
	public int getY() {

		return centery - radius;
	}

	@Override
	public int getWidth() {
		return 2 * radius;
	}

	@Override
	public int getHeight() {
		return 2 * radius;
	}

	@Override
	public void changeWithJOptionPane() {
		int input = Util.getInputInteger("Set centerx:");
		if (input != -1)
			setCenterx(input);
		input = Util.getInputInteger("Set centery:");
		if (input != -1)
			setCentery(input);
		input = Util.getInputInteger("Set radius:");
		if (input != -1)
			setRadius(input);

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
	@Override
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
			setCenterx(input);
		} else {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property centerx will be done.");
		}

		getInputed = inputs.get(1).getText();
		input = Util.getInteger(getInputed);
		if (input != -1) {
			setCentery(input);
		} else {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property centery will be done.");
		}

		getInputed = inputs.get(2).getText();
		input = Util.getInteger(getInputed);
		if (input != -1) {
			setRadius(input);
		} else {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property radiu will be done.");
		}

		getInputed = inputs.get(3).getText();
		int red = Util.getInteger(getInputed);
		if (red == -1) {

			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property color will be done.");
			return;
		}

		getInputed = inputs.get(4).getText();
		int green = Util.getInteger(getInputed);
		if (green == -1) {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property color will be done.");
			return;
		}

		getInputed = inputs.get(5).getText();
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
		labels.add("Set x coordinete of center:");
		labels.add("Set y coordinete of center:");
		labels.add("Set radius:");

		labels.add("Set red of border color:");
		labels.add("Set green of border color:");
		labels.add("Set blue of border color:");

	}

}
