package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * JComponent holding the instance of color to be provided.
 * 
 * @author mia
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Default version ID.
	 */
	private static final long serialVersionUID = 1L;
	/** Saving currently selected color. */
	private Color currentColor;
	/** Observers attached to this subject. */
	private List<ColorChangeListener> observers;

	/**
	 * Constructor.
	 * 
	 * @param selectedColor
	 *            selected color stored in this {@link JColorArea}.
	 */
	public JColorArea(Color selectedColor) {
		this.currentColor = selectedColor;
		setSize(getPreferredSize());
		observers = Collections.synchronizedList(new ArrayList<>());
		setAlignmentX(5);
		setAlignmentY(1);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				 

			}

			@Override
			public void mousePressed(MouseEvent e) {
				setCurrentColor(JColorChooser.showDialog(null, "Choose color",
						currentColor));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				 
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				 
				
			}

		});

	}

	/**
	 * Methods for observer registration. Registers the provided observer to
	 * {@link this} subject.
	 * 
	 * @param l
	 *            observer to be registered
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		observers.add(l);
	}

	/**
	 * Methods for observer deregistration. Deegisters the provided observer
	 * from {@link this} subject.
	 * 
	 * 
	 * @param l
	 *            observer to be deregistered
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		observers.remove(l);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension dim = getPreferredSize();
		setSize(dim);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(currentColor);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public Color getCurrentColor() {
		return currentColor;
	}

	/**
	 * Changes the private value {@link #currentColor} and notifies all
	 * observers listening to this.
	 * 
	 * @param selectedColor
	 *            to which to change the private value {@link #currentColor}
	 */
	public void setCurrentColor(Color selectedColor) {
		if(selectedColor == null) return;
		this.currentColor = selectedColor;
		observers.forEach(
				o -> o.newColorSelected(this, currentColor, selectedColor));
		repaint();
		
	}

}
