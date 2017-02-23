package hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class implementing geometrical object that will be drawn on
 * 
 * @author mia
 *
 */
public class GeometricalObject {

	/** List of all listeners over this class. */
	private List<JDrawingObjectListener> listeners = Collections
			.synchronizedList(new ArrayList<>());

	/** Regex for integers wrapped in string. */
	public static final String INTEGER = "[0-9]+";

	/**
	 * Function determinating if this object is line.
	 * 
	 * @return false if object isn't line, true otherwise.
	 */
	public boolean isLine() {
		return false;
	}

	/**
	 * Function determinating if this object is circle.
	 * 
	 * @return false if object isn't circle, true otherwise.
	 */
	public boolean isCircle() {
		return false;
	}

	/**
	 * Function determinating if this object is filled circle.
	 * 
	 * @return false if object isn't filled circle, true otherwise.
	 */
	public boolean isFCircle() {
		return false;
	}

	/**
	 * Method drawing the 2D image of object trough given graphics2D.
	 * 
	 * @param g
	 *            {@link Graphics2D} trough which the component will be draw.
	 */
	public void draw(Graphics2D g) {

	}

	/**
	 * Method drawing the 2D image of object trough given graphics2D.
	 * 
	 * @param g
	 *            {@link Graphics2D} trough which the component will be draw.
	 * @param xOffset
	 *            xoffset of the object
	 * @param yoffset
	 *            yOffset of the object
	 */
	public void draw(Graphics2D g, int xOffset, int yoffset) {

	}

	/**
	 * Adds {@link JDrawingObjectListener}.
	 * 
	 * @param l
	 *            listener of geometrical object.
	 */
	public void addGeometricalObjectListener(JDrawingObjectListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	/**
	 * Removes provided {@link JDrawingObjectListener}.
	 * 
	 * @param l
	 *            listener of geometrical object.
	 */
	public void removeGeometricalObjectListener(JDrawingObjectListener l) {
		Objects.requireNonNull(l);
		listeners.remove(l);
	}

	/**
	 * Fire all listeners.
	 */
	public void fire() {
		listeners.forEach(listener -> {
			listener.fireChange();
		});
	}

	/**
	 * @return the geometrical object in jvd form - formatted string
	 *         representation of an object
	 */
	public String getJVD() {
		return toString();

	}

	/**
	 * Fills data from the data found in passed jvd form of an object. THe first
	 * word of string is name of object that shoul be created, if that is
	 * incorrect, object is not changed. If number of arguments passed is
	 * greater than it should be, only n needed arguments is considerd. If the
	 * number of arguments passed is less than it should be, exception it
	 * thrown. All words passes, excluding the first one, are must be integers,
	 * otherwise the exception is passed.
	 * 
	 * @param jvdForm
	 *            jvd form of an object
	 */
	public void fromJVD(String jvdForm) {

	}

	/**
	 * Gets the bounding rectangle left upper corner x coordinate.
	 * 
	 * @return the bounding rectangle left upper corner x coordinate.
	 * 
	 */
	public int getX() {
		return 0;
	}

	/**
	 * Gets the bounding rectangle left upper corner y coordinate.
	 * 
	 * @return the bounding rectangle left upper corner y coordinate.
	 * 
	 */
	public int getY() {
		return 0;
	}

	/**
	 * Gets the bounding rectangle width.
	 * 
	 * @return the bounding rectangle width.
	 * 
	 */
	public int getWidth() {
		return 0;
	}

	/**
	 * Gets the bounding rectangle height.
	 * 
	 * @return the bounding rectangle height.
	 * 
	 */
	public int getHeight() {
		return 0;
	}

	/** Function called when want to change properties held. */
	public void changeWithJOptionPane() {

	}

	/** Function called when want to change properties held using JPanel. */
	public void changeWithJOptionPane2() {

	}

}
