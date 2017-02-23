/**
 * 
 */
package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.actions.Actions;
import hr.fer.zemris.java.hw16.jvdraw.central.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.central.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.central.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.central.JDrawingCanvasEnviroment;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.model.DrawingObjectListModel;

/**
 * Frame of JVDraw application.
 * 
 * 
 * @author mia
 *
 */
public class Treci extends JFrame {

	/**
	 * Generated serial id.
	 */
	private static final long serialVersionUID = 1410963884728904827L;

	/** Providing the foreground color. */
	private IColorProvider foregroundColor;

	/** Providing the background color. */
	private IColorProvider backgroundColor;
	/**
	 * If the last selected tool is a line, the first click defined the start
	 * point for the line and the second click defines the end point for the
	 * line. Before the second click occurs, as user moves the mouse, the line
	 * is drawn with end-point tracking the mouse so that the user can see what
	 * will be the final result.
	 */
	private JToggleButton lineButton;

	/**
	 * If the last selected tool is a circle, first click defines the circle
	 * center and as user moves the mouse, a circle radius is defined. On second
	 * click, circle is added. Before the second click occurs, as user moves the
	 * mouse, the circle is drawn with radius tracking the mouse so that the
	 * user can see what will be the final result.
	 */
	private JToggleButton circleButton;

	/**
	 * If the last selected tool is a filled circle, first click defines the
	 * circle center and as user moves the mouse, a circle radius is defined. On
	 * second click,filled circle is added. Before the second click occurs, as
	 * user moves the mouse, the circle is drawn with radius tracking the mouse
	 * so that the user can see what will be the final result.
	 */
	private JToggleButton filledCircleButton;

	/** Action of this JVDrawFrame */
	private Actions actions;

	/**
	 * Action exiting the application. If the cavnas has not be saved, asks user
	 * if he wishes to save if.
	 */
	private AbstractAction exitAction = new AbstractAction() {

		/**
		 * Serial ID used in hash and equal function.
		 */
		private static final long serialVersionUID = -3356295127213421916L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Util.saveIfUnsaved(Treci.this);
			System.exit(0);
		}

	};

	/** Action removing the item rom the Jlist. */
	AbstractAction removeFromJList = new AbstractAction() {

		/**
		 * Serial ID used in hash and equal function.
		 */
		private static final long serialVersionUID = -3356295127213421916L;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (rightList.hasFocus()) {
				int selectedIndex = rightList.getSelectedIndex();
				if (selectedIndex != -1) {
					drawingModel.remove(drawingModel.getObject(selectedIndex));
				}
			}
		}

	};

	/**
	 * JList diyplayed on the right side of the central.
	 */
	private JList<GeometricalObject> rightList;

	/**
	 * Drawing canvas.
	 */
	private JDrawingCanvas drawingCanvas;
	/** Drawing model used in this JVDrawFrame. */
	private DrawingModel drawingModel;

	/** Constructor. */
	public Treci() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(100, 100);
		setSize(600, 400);
		getContentPane().setLayout(new BorderLayout());

		initGUI();

	}

	/**
	 * Initializing the GUI.
	 */
	private void initGUI() {
		createMenus();
		createToolBars();
		createCentralPane();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});

	}

	/**
	 * Method creates menus of the editor.
	 */
	private void createMenus() {
		actions = new Actions(this);

		JMenuBar menuBar = new JMenuBar();
		JMenu actionsMenu = new JMenu("Actions");
		actionsMenu.add(new JMenuItem(actions.getSaveDocumentAction()));
		actionsMenu.add(new JMenuItem(actions.getSaveDocumentAsAction()));
		actionsMenu.add(new JMenuItem(actions.getOpenDocumentAction()));
		actionsMenu.add(new JMenuItem(actions.getExportDocumentAction()));
		actionsMenu.add(new JMenuItem(removeFromJList));
		setActionVariables(removeFromJList, "Delete", "F2", KeyEvent.VK_DELETE,
				"Used to delete selected.");

		menuBar.add(actionsMenu);
		setJMenuBar(menuBar);

	}

	/**
	 * Supplementary method setting the Action.NAME,
	 * Action.ACCELERATOR_KEY,Action.MNEMONIC_KEY,Action.SHORT_DESCRIPTION of
	 * the action {@code action}
	 * 
	 * @param action
	 *            action which parameters should be set
	 * @param name
	 *            name of the action
	 * @param accKey
	 *            acceleration key for the action
	 * @param mnemonic
	 *            mnemonic key for the action
	 * @param shortDesc
	 *            short description of the action
	 */
	private void setActionVariables(Action action, String name, String accKey,
			int mnemonic, String shortDesc) {

		action.putValue(Action.NAME, name);
		action.putValue(Action.SHORT_DESCRIPTION, shortDesc);
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(accKey));
		action.putValue(Action.MNEMONIC_KEY, mnemonic);

	}

	/**
	 * Method creates toolbars of the editor.
	 */
	private void createToolBars() {
		JToolBar toolBar = new JToolBar("toolBar");
		toolBar.setLayout(new GridBagLayout());
		toolBar.setFloatable(true);
		JColorArea foregroundColor = new JColorArea(Color.BLUE);
		JColorArea backgroundColor = new JColorArea(Color.RED);
		foregroundColor.setOpaque(true);
		backgroundColor.setOpaque(true);

		ColorChangeListenerLabel label = new ColorChangeListenerLabel();
		label.addSubject("Background color", backgroundColor);
		label.addSubject("Foreground color", foregroundColor);
		foregroundColor.addColorChangeListener(label);
		backgroundColor.addColorChangeListener(label);

		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;

		toolBar.add(new JButton().add(foregroundColor));
		toolBar.add(new JButton().add(backgroundColor));
		toolBar.addSeparator();

		lineButton = new JToggleButton(JDrawingCanvasEnviroment.LINE);
		lineButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					drawingCanvas.getEnv().setLine();
					circleButton.setSelected(false);
					filledCircleButton.setSelected(false);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					drawingCanvas.getEnv()
							.disable(JDrawingCanvasEnviroment.LINE);
				}
			}
		});

		circleButton = new JToggleButton(JDrawingCanvasEnviroment.CIRCLE);
		circleButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					drawingCanvas.getEnv().setCircle();
					lineButton.setSelected(false);
					filledCircleButton.setSelected(false);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					drawingCanvas.getEnv()
							.disable(JDrawingCanvasEnviroment.CIRCLE);
				}
			}
		});

		filledCircleButton = new JToggleButton(
				JDrawingCanvasEnviroment.FCIRCLE);
		filledCircleButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					drawingCanvas.getEnv().setFilledCircle();
					lineButton.setSelected(false);
					circleButton.setSelected(false);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					drawingCanvas.getEnv()
							.disable(JDrawingCanvasEnviroment.FCIRCLE);
				}
			}
		});

		toolBar.add(lineButton);
		toolBar.add(circleButton);
		toolBar.add(filledCircleButton);

		add(toolBar, BorderLayout.PAGE_START);
		add(new JPanel().add(label), BorderLayout.PAGE_END);

	}

	/**
	 * Central drawing pane of {@link Treci}. it contains 2 panels. One
	 * for drawing component and another for listing all drawn components.
	 * 
	 * @author mia
	 *
	 */
	private void createCentralPane() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		drawingModel = new DrawingModelImpl();

		drawingCanvas = new JDrawingCanvas(drawingModel,
				(JColorArea) foregroundColor, (JColorArea) backgroundColor);
		drawingCanvas.setOpaque(true);
		drawingCanvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (drawingCanvas.getEnv().isDrawingStarted()) {
					drawingCanvas.getEnv().secondPress(e.getX(), e.getY());
				} else {
					GeometricalObject object = drawingCanvas.getEnv()
							.firstPress(e.getX(), e.getY(),
									foregroundColor.getCurrentColor(),
									backgroundColor.getCurrentColor());
					if (object != null) {
						drawingModel.add(object);
					}
				}

			}
		});
		drawingCanvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				drawingCanvas.getEnv().resizeObject(e.getX(), e.getY());

			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});

		drawingCanvas.setOpaque(true);

		Dimension dim = new Dimension();
		dim.setSize(getWidth() / 3 * 2, getHeight());
		drawingCanvas.setPreferredSize(dim);

		centerPanel.add(new JScrollPane(drawingCanvas), BorderLayout.CENTER);
		drawingModel.addDrawingModelListener(drawingCanvas);

		DrawingObjectListModel model = new DrawingObjectListModel(drawingModel);
		rightList = new JList<>(model);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = rightList.locationToIndex(e.getPoint());
					if (index == -1)
						return;
					GeometricalObject objectClicked = drawingModel
							.getObject(index);
					// objectClicked.changeWithJOptionPane();

					objectClicked.changeWithJOptionPane2();

				}
			}
		};
		rightList.addMouseListener(mouseListener);
		rightList.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removeFromJList.actionPerformed(null);
				}

			}
		});

		JScrollPane right = new JScrollPane(rightList);
		right.setSize(getWidth() / 3, getHeight());

		centerPanel.add(right, BorderLayout.LINE_END);
		centerPanel.validate();

		add(centerPanel, BorderLayout.CENTER);

	}

	/**
	 * Gets the drawing canvas.
	 * 
	 * @return drawing canvas for this JVDraw.
	 */
	public JDrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}

}
