package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

/**
 * Program testing the LayoutManager {@link CalcLayout}.
 * 
 * @author mia
 *
 */
public class Window extends JFrame {

	/**
	 * Default ID.
	 */
	private static final long serialVersionUID = 1L;

	/** Constructor of the window. */
	public Window() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Window");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();

	}

	/**
	 * Initialize the frame. Sets the components
	 */
	private void initGUI() {
		Container cp = getContentPane();
		Border b = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
		cp.setLayout(new CalcLayout(3));

		Color lightBlue = new Color(39, 50, 214, 155);

		JLabel l = new JLabel("x");
		l.setBorder(b);
		l.setBackground(lightBlue);
		l.setOpaque(true);
		cp.add(l, new RCPosition(1, 6));

		l = new JLabel("y");
		l.setBorder(b);
		l.setBackground(lightBlue);
		l.setOpaque(true);
		cp.add(l, "1,1");

		l = new JLabel("z");
		l.setBorder(b);
		l.setBackground(lightBlue);
		l.setOpaque(true);
		cp.add(l, new RCPosition(2, 7));
		l = new JLabel("k");
		l.setBorder(b);
		l.setBackground(lightBlue);
		l.setOpaque(true);
		cp.add(l, new RCPosition(4, 2));
		l = new JLabel("l");
		l.setBorder(b);
		l.setBackground(lightBlue);
		l.setOpaque(true);
		cp.add(l, new RCPosition(4, 5));
		l = new JLabel("k");
		l.setBorder(b);
		l.setBackground(Color.CYAN);
		l.setOpaque(true);
		cp.add(l, new RCPosition(4, 7));

	}

	/**
	 * Program called when invoking the program.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Window().setVisible(true));
	}
}
