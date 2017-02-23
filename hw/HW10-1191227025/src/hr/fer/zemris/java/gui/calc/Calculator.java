/**
 * 
 */
package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import hr.fer.zemris.java.gui.calc.impl.ComponentNames;
import hr.fer.zemris.java.gui.calc.impl.controller.OperationProvider;
import hr.fer.zemris.java.gui.calc.impl.operations.IBinaryOperation;
import hr.fer.zemris.java.gui.calc.impl.operations.IUnaryOperation;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Simulating simple Windows calculator.
 * 
 * @author mia
 *
 */
public class Calculator extends JFrame {
	/**
	 * Serial versionID.
	 */
	private static final long serialVersionUID = 1L;
	/** Stack of the calculator */
	private Stack<Double> stack;
	/** Current number. */
	private String currentNum;
	/**
	 * Previous number that was showed in label {@link Component} as its value.
	 */
	private double previousNum;

	/**
	 * Indicates if the calculator has just started working and has no previous
	 * number.
	 */
	boolean start = true;

	/**
	 * Last binary operator clicked.
	 */
	private IBinaryOperation binaryOperation;

	/**
	 * Last unary operator clicked.
	 */
	private IUnaryOperation unaryOperation;

	/** Indicates if the last operator clicked was unary. */
	private boolean unary = false;

	/** Operation provider. */
	private OperationProvider opProvider;

	/**
	 * Indicates if the last button clicked was one of number construction
	 * elements.
	 */
	private boolean numberConstructionElement = false;

	/** Message to be displayed as label text when something went wrong. */
	private String errorMessage = "Error!";

	/** Constructor. */
	public Calculator() {
		stack = new Stack<>();
		opProvider = new OperationProvider();
		binaryOperation = null;
		unaryOperation = null;

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(20, 20);
		setSize(600, 300);
		setFont(new Font(Font.SANS_SERIF, 0, 8));
		initGUI();
	}

	/**
	 * Initialize the frame. Sets the components.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		Border b = BorderFactory.createLineBorder(Color.GRAY, 1);

		cp.setLayout(new CalcLayout(3));

		Color lightBlue = Color.CYAN;
		JLabel label = new JLabel("");
		label.setOpaque(true);
		label.setBackground(Color.YELLOW);
		label.setBorder(b);
		label.setHorizontalAlignment(SwingConstants.RIGHT);

		JCheckBox checkBox = new JCheckBox("Ivn");
		checkBox.setBackground(lightBlue);
		checkBox.setOpaque(true);
		checkBox.setBorder(b);
		checkBox.setBorderPainted(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox.addActionListener(new IvnListener(cp));

		JButton button = new JButton(ComponentNames.EQUAL);
		button.addActionListener(new EqualListener(label));
		button.setBackground(lightBlue);
		cp.add(button, "1,6");

		cp.add(label, "1,1");
		initUnary(cp, new UnaryOperationListener(label));
		initBinary(cp, new BinaryOperationListener(label));
		initNumberConstructionElements(cp,
				new NumberConstructionElementListener(label));
		initSupplementary(cp, new SupplemetaryOperationListener(label));
		cp.add(checkBox, "5,7");

	}

	/**
	 * Adds the unary operation buttons to the {@code con} {@link Container}.
	 * The buttons are listened by {@code listener}.
	 * 
	 * @param con
	 *            in which the buttons will be added
	 * @param listener
	 *            listener which will listen to added buttons
	 */
	private void initUnary(Container con, UnaryOperationListener listener) {
		Color lightBlue = Color.CYAN;

		JButton button = new JButton(ComponentNames.SIN);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "2,2");
		button = new JButton(ComponentNames.COS);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "3,2");
		button = new JButton(ComponentNames.TAN);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "4,2");
		button = new JButton(ComponentNames.CTG);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "5,2");
		button = new JButton(ComponentNames.RECIPROCAL);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "2,1");
		button = new JButton(ComponentNames.LOG);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "3,1");
		button = new JButton(ComponentNames.LN);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "4,1");
		button = new JButton(ComponentNames.CHANGESIGN);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "5,4");
	}

	/**
	 * Adds the binary operation buttons to the {@code con} {@link Container}.
	 * The buttons are listened by {@code listener}.
	 * 
	 * @param con
	 *            in which the buttons will be added
	 * @param listener
	 *            listener which will listen to added buttons
	 */
	private void initBinary(Container con, BinaryOperationListener listener) {
		Color lightBlue = Color.CYAN;

		JButton button = new JButton(ComponentNames.POW);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "5,1");
		button = new JButton(ComponentNames.DIV);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "2,6");
		button = new JButton(ComponentNames.MUL);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "3,6");
		button = new JButton(ComponentNames.SUB);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "4,6");
		button = new JButton(ComponentNames.ADD);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "5,6");
	}

	/**
	 * Adds the number construction elemen's buttons to the {@code con}
	 * {@link Container}.
	 * 
	 * @param con
	 *            in which the buttons will be added
	 * @param listener
	 *            listener which will listen to added buttons
	 */
	private void initNumberConstructionElements(Container con,
			NumberConstructionElementListener listener) {
		Color lightBlue = Color.CYAN;

		JButton button = new JButton("0");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "5,3");
		button = new JButton(ComponentNames.CHANGESIGN);
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "5,4");
		button = new JButton(".");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "5,5");
		button = new JButton("1");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "4,3");
		button = new JButton("2");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "4,4");
		button = new JButton("3");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "4,5");
		button = new JButton("4");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "3,3");
		button = new JButton("5");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "3,4");
		button = new JButton("6");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "3,5");
		button = new JButton("7");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "2,3");
		button = new JButton("8");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "2,4");
		button = new JButton("9");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "2,5");
	}

	

	/**
	 * Adds the supplementary element's buttons to the {@code con}
	 * {@link Container}. The buttons are listened by {@code listener}.
	 * 
	 * @param con
	 *            in which the buttons will be added
	 * @param listener
	 *            listener which will listen to added buttons
	 */
	private void initSupplementary(Container con,
			SupplemetaryOperationListener listener) {
		Color lightBlue = Color.CYAN;

		JButton button = new JButton("clr");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "1,7");
		button = new JButton("res");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "2,7");
		button = new JButton("push");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "3,7");
		button = new JButton("pop");
		button.addActionListener(listener);
		button.setBackground(lightBlue);
		con.add(button, "4,7");

	}

	/** Class listening buttons that performs unary operations when clicked. */
	private class UnaryOperationListener implements ActionListener {
		/** Label in which to save result. */
		JLabel label;

		/**
		 * Constructor.
		 * 
		 * @param label
		 *            label to write the result in.
		 * 
		 */
		public UnaryOperationListener(JLabel label) {
			this.label = label;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			process(button.getText());
			unary = true;
			numberConstructionElement = false;

		}

		/**
		 * Method processing the action.
		 * 
		 * @param name
		 *            of the operation
		 */
		public void process(String name) {
			if (currentNum == null) {
				if (start) {
					label.setText("Enter the number first!");
					return;
				} else {
					currentNum = Double.toString(previousNum);
				}
			}

			doBinaryIfNotProcessed();
			unaryOperation = opProvider.getUnaryOperation(name);
			if(currentNum == null && !start){
				currentNum = label.getText();
			}
			
			previousNum = unaryOperation
					.calculate(Double.parseDouble(currentNum));
			label.setText(Double.toString(previousNum));

			start = false;
			currentNum = null;

		}

	}

	/**
	 * Class listening buttons having names of binary operations. Performs the
	 * action connected to the button clicked.
	 */
	private class BinaryOperationListener implements ActionListener {
		/** Label in which to save result. */
		JLabel label;

		/**
		 * Constructor.
		 * 
		 * @param label
		 *            label to write the result in.
		 * 
		 */
		public BinaryOperationListener(JLabel label) {
			this.label = label;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			process(button.getText());
			numberConstructionElement = false;
			unary = false;
		}

		/**
		 * Method processing the action, operation of given name.
		 * 
		 * @param name
		 *            name of the operation to be processed
		 */
		public void process(String name) {
			if (currentNum == null) {
				if (start) {
					label.setText("Enter the number first!");
					return;
				} else {
					currentNum = Double.toString(previousNum);
				}
			}
			doBinaryIfNotProcessed();
			binaryOperation = opProvider.getBinaryOperation(name);
			previousNum = Double.parseDouble(currentNum);

			start = false;
			currentNum = null;

		}
	}

	/**
	 * Class listening button representing equal.
	 */
	private class EqualListener implements ActionListener {
		/** Label in which to save result. */
		JLabel label;

		/**
		 * Constructor.
		 * 
		 * @param label
		 *            label to write the result in.
		 * 
		 */
		public EqualListener(JLabel label) {
			this.label = label;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (currentNum != null) {

				doBinaryIfNotProcessed();
				previousNum = Double.parseDouble(currentNum);
				label.setText(Double.toString(previousNum));

				start = false;
				currentNum = null;

			}

		}

	}

	/**
	 * Class listening button representing equal.
	 */
	private class IvnListener implements ActionListener {
		/** Container in which to operate. */
		Container cp;

		/**
		 * Component in which certain button should change its text to
		 * "inversion" text representing the inverse of functions.
		 * 
		 * @param cp
		 *            Container in which to operate.
		 * 
		 */
		public IvnListener(Container cp) {
			this.cp = cp;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int n = cp.getComponentCount();
			for (int i = 0; i < n; ++i) {
				if (cp.getComponent(i) instanceof JButton) {
					JButton button = (JButton) cp.getComponent(i);
					if ((opProvider.getUnaryOperation(button.getText()) != null)
							|| button.getText().equals(ComponentNames.POW)
							|| button.getText().equals(ComponentNames.ROOT)) {
						button.setText(setInverseName(button.getText()));
					}
				}
			}

		}

		/**
		 * Sets the button text of specific button on its "inverse" specifying
		 * the inverse functions.
		 * 
		 * @param name
		 *            name of function for which the inverse function name is
		 *            shearched
		 * 
		 * @return the inverse function name if it should be inverted, name if
		 *         it shoulden't
		 */
		private String setInverseName(String name) {
			switch (name) {
			case ComponentNames.TAN:
				return ComponentNames.ARCTAN;
			case ComponentNames.ARCTAN:
				return ComponentNames.TAN;
			case ComponentNames.CTG:
				return ComponentNames.ARCCTG;
			case ComponentNames.ARCCTG:
				return ComponentNames.CTG;
			case ComponentNames.SIN:
				return ComponentNames.ARCSIN;
			case ComponentNames.ARCSIN:
				return ComponentNames.SIN;
			case ComponentNames.COS:
				return ComponentNames.ARCCOS;
			case ComponentNames.ARCCOS:
				return ComponentNames.COS;
			case ComponentNames.LOG:
				return ComponentNames.TEN;
			case ComponentNames.TEN:
				return ComponentNames.LOG;
			case ComponentNames.LN:
				return ComponentNames.EXP;
			case ComponentNames.EXP:
				return ComponentNames.LN;
			case ComponentNames.POW:
				return ComponentNames.ROOT;
			case ComponentNames.ROOT:
				return ComponentNames.POW;
			default:
				return name;
			}

		}

	}

	/**
	 * Class listening buttons representing elements from which numbers are
	 * constructed.
	 */
	private class NumberConstructionElementListener implements ActionListener {
		/** Label in which to save result. */
		JLabel label;

		/**
		 * Constructor.
		 * 
		 * @param label
		 *            label to write the result in.
		 * 
		 */
		public NumberConstructionElementListener(JLabel label) {
			this.label = label;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (currentNum == null) {
				currentNum = button.getText();
			} else {
				currentNum += button.getText();
			}
			numberConstructionElement = true;
			label.setText(currentNum);

		}

	}

	/** Class listening buttons of names clr,res,push,pop,Ivn. */
	private class SupplemetaryOperationListener implements ActionListener {
		/** Label in which to save result. */
		JLabel label;

		/**
		 * Constructor.
		 * 
		 * @param label
		 *            label to write the result in.
		 * 
		 */
		public SupplemetaryOperationListener(JLabel label) {
			this.label = label;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			switch (button.getText()) {
			case "clr":
				processClr();
				break;
			case "res":
				processRes();
				break;
			case "push":
				processPush();
				System.out.println("PUSH");
				break;
			case "pop":
				processPop();
				break;
			default:
				label.setText(errorMessage);
			}
		}

		/**
		 * Process the pop operation.
		 */
		private void processPop() {
			if (stack.isEmpty()) {
				label.setText(errorMessage);
			} else {
				if(currentNum != null)
					previousNum = Double.parseDouble(currentNum);
				start = false;

				currentNum = stack.pop().toString();
				System.out.println(currentNum);
				label.setText(currentNum);
			}
		}

		/**
		 * Process the push operation
		 */
		private void processPush() {
			if (label.getText() != null) {
				stack.push(Double.parseDouble(label.getText()));
				System.out.println("PUSHED "+label.getText());
			}

		}

		/**
		 * Process the res operation.
		 */
		private void processRes() {
			currentNum = null;
			start = true;
			numberConstructionElement = false;
			unary = false;
			stack.clear();
			unaryOperation = null;
			binaryOperation = null;

			label.setText("");

		}

		/**
		 * Process the clr operation.
		 */
		private void processClr() {
			currentNum = null;
			label.setText("");
		}

	}

	/**
	 * Do the binary operation if it hasn't been jet processed.
	 */
	private void doBinaryIfNotProcessed() {
		if (!unary && numberConstructionElement && binaryOperation != null) {
			currentNum = Double.toString(binaryOperation.calculate(previousNum,
					Double.parseDouble(currentNum)));

			binaryOperation = null;// it is calculated
		}
	}

	/**
	 * Program called when invoking the program.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}

}
