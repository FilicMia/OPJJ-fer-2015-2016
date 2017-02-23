package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

/**
 * GUI application called JVDraw, which is a simple application for vector
 * graphics.
 *
 */
public class JVDraw{
	/**
	 * Method called when invoking the program/application.
	 * 
	 * @param args
	 *            command-line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Treci jvdraw = new Treci();
			Dimension d = jvdraw.getSize();
			d.setSize(600, 400);
			jvdraw.setSize(d);
			jvdraw.setVisible(true);
			
		});
	}
}
