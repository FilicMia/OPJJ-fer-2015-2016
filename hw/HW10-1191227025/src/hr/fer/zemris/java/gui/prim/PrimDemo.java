package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * Extends {@link JFrame} and shows 2 lists over its area with button on the
 * bottom.
 * 
 * @author mia
 *
 */
public class PrimDemo extends JFrame {
	/**
	 * Default serialization ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 */
	public PrimDemo() {
		setLocation(50, 50);
		setSize(300, 300);
		setTitle("Prim Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Method initializing the {@link JFrame}.
	 * 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.setBackground(Color.WHITE);

		JButton button = new JButton("Next!");
		button.setHorizontalAlignment(SwingConstants.CENTER);

		cp.add(button, BorderLayout.PAGE_END);
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));

		
		PrimListModel model = new PrimListModel();
		JList<Integer> leftList = new JList<>(model);
		JList<Integer> rightList = new JList<>(model);

		button.addActionListener(e -> {
			model.next();
		});
		
		centerPanel.add(new JScrollPane(leftList));
		centerPanel.add(new JScrollPane(rightList));
		
		cp.add(new JScrollPane(centerPanel), BorderLayout.CENTER);

	}

	/**
	 * Program called when invoking the program.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {

		if (args.length != 0) {
			System.err.println("No arguments required.");
			System.exit(-1);
		}

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			//frame.pack();
			frame.setVisible(true);
		});
	}

}
