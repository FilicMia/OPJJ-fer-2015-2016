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
 * Class implementing the geometrical shape of filled circle. Holds things
 * relevant to this {@link GeometricalObject} like centerx, centery, radius,
 * foreground color;{@link Color} in which the border of object is painted,
 * background color;{@link Color} with which the object is filled.
 * 
 * @author mia
 *
 */
public class FCircle extends Circle {
	/** Name of the geometric shape. */
	public static final String NAME = "FCIRCLE";

	/**
	 * {@link Color} for filling the object with.
	 */
	private Color background;

	/**
	 * @return {@link Color} with which the object is filled.
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Sets {@link Color} with which the object is filled.
	 * 
	 * @param background
	 *            {@link Color} with which the object will be filled.
	 */
	public void setBackground(Color background) {
		this.background = background;
		fire();
	}

	@Override
	public boolean isFCircle() {
		return true;
	}

	@Override
	public String toString() {
		return "FCIRCLE " + super.getCenterx() + " " + super.getCenterx() + " "
				+ super.getRadius() + " " + super.getColor().getRed() + " "
				+ super.getColor().getGreen() + " " + super.getColor().getBlue()
				+ " " + background.getRed() + " " + background.getGreen() + " "
				+ background.getBlue();
	}

	@Override
	public void draw(Graphics2D g) {
		draw(g, 0, 0);
	}

	@Override
	public void draw(Graphics2D g, int xOffset, int yOffset) {
		Color colorBefore = g.getColor();

		g.setColor(background);
		Shape drawCircle = new Ellipse2D.Double(
				getCenterx() - getRadius() - xOffset,
				getCentery() - getRadius() - yOffset, 2 * getRadius(),
				2 * getRadius());
		g.fill(drawCircle);
		g.setColor(super.getColor());
		g.draw(drawCircle);

		g.setColor(colorBefore);
	}

	/**
	 * @return the geometrical object in jvd form - FCIRCLE centerx centery
	 *         radius red green blue red green blue
	 * 
	 *         First three color components define outline color and last three
	 *         color components fill color.
	 */
	@Override
	public String getJVD() {
		return "FCIRCLE " + super.getCenterx() + " " + super.getCenterx() + " "
				+ super.getRadius() + " " + super.getColor().getRed() + " "
				+ super.getColor().getGreen() + " " + super.getColor().getBlue()
				+ " " + background.getRed() + " " + background.getGreen() + " "
				+ background.getBlue();
	}

	@Override
	public void fromJVD(String jvdForm) {
		String[] items = jvdForm.split("\\s+");
		if (items[0].equals(NAME)) {
			super.setCenterx(Integer.parseInt(items[1]));
			super.setCentery(Integer.parseInt(items[2]));
			super.setRadius(Integer.parseInt(items[3]));
			super.setColor(new Color(Integer.parseInt(items[4]),
					Integer.parseInt(items[5]), Integer.parseInt(items[6])));
			background = new Color(Integer.parseInt(items[7]),
					Integer.parseInt(items[8]), Integer.parseInt(items[9]));
		}
	}

	@Override
	public void changeWithJOptionPane() {
		super.changeWithJOptionPane();
		int red = Util.getInputInteger("Set red of inner color:");
		if (red == -1) {
			return;
		}

		int green = Util.getInputInteger("Set green of inner color:");
		if (green == -1) {
			return;
		}

		int blue = Util.getInputInteger("Set blue of inner color:");
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

		getInputed = inputs.get(3).getText();
		red = Util.getInteger(getInputed);
		if (red == -1) {

			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property background color will be done.");
			return;
		}

		getInputed = inputs.get(4).getText();
		green = Util.getInteger(getInputed);
		if (green == -1) {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property background color  will be done.");
			return;
		}

		getInputed = inputs.get(5).getText();
		blue = Util.getInteger(getInputed);
		if (blue == -1) {
			JOptionPane.showMessageDialog(null, getInputed
					+ " is not an integer number. No change of property background color  will be done.");
			return;
		}

		setBackground(new Color(red, green, blue));
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

		labels.add("Set red of inner color:");
		labels.add("Set green of inner color:");
		labels.add("Set blue of inner color:");

	}
}
