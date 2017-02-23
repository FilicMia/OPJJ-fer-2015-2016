package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Layout manager which splits panel into fixed grid 5x7.Enumerating starts with
 * with 1. It works with constrains of type {@link RCPanel}.
 * 
 * @author mia
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Components which this {@link LayoutManager2} must handle.
	 */
	private List<Component> components;
	/**
	 * Constrains of the components which this {@link LayoutManager2} must
	 * handle.
	 */
	private List<RCPosition> constrains;
	/**
	 * Number of rows in fixed grid.
	 */
	public static final int noRows = 5;
	/** Gap between components on horizontal and vertical line. */
	int gap;
	/**
	 * Number of columns in fixed grid.
	 */
	public static final int noColums = 7;
	/** Minimum width of the container. */
	private int minimumWidth = 0;
	/** Minimum height of the container. */
	private int minimumHeight = 0;
	/** Preferred width of the container. */
	private int preferredWidth = 0;
	/** Preferred height of the container. */
	private int preferredHeight = 0;
	/** Indicates that size of the container is unknown. */
	private boolean sizeUnknown = true;
	/** Indicates if layingout is in process */
	boolean layingOut = false;

	/**
	 * Default constructor setting gap between components on 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Gap between components on vertical and horizontal line.
	 * 
	 * @param gap
	 *            gap between components
	 */
	public CalcLayout(int gap) {
		constrains = new ArrayList<>();
		this.gap = gap;
	}

	@Override
	public synchronized void addLayoutComponent(String name, Component comp) {
	}

	/**
	 * Removes the specified component from this border layout. This method is
	 * called when a container calls its remove or removeAll methods. Most
	 * applications do not call this method directly.
	 */
	@Override
	public synchronized void removeLayoutComponent(Component comp) {
		int index = constrains.indexOf(comp);
		if (index == -1)
			return;
		components.remove(index);
		constrains.remove(index);
	}

	@Override
	public synchronized Dimension preferredLayoutSize(Container parent) {
		Objects.requireNonNull(parent);

		if (sizeUnknown)
			setSizes(parent);
		sizeUnknown = false;
		Insets insets = parent.getInsets();

		return new Dimension(preferredWidth + insets.left + insets.right,
				preferredHeight + insets.bottom + insets.top);
	}

	@Override
	public synchronized Dimension minimumLayoutSize(Container parent) {
		Objects.requireNonNull(parent);

		if (sizeUnknown)
			setSizes(parent);
		sizeUnknown = false;
		Insets insets = parent.getInsets();

		return new Dimension(minimumWidth + insets.left + insets.right,
				minimumHeight + insets.bottom + insets.top);
	}

	/**
	 * This is called when the panel is first displayed, and every time its size
	 * changes.
	 */
	@Override
	public synchronized void layoutContainer(Container parent) {
		Objects.requireNonNull(parent);

		layingOut = true;
		int noOfComponents = parent.getComponentCount();

		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
		int compWidth = (int) ((maxWidth - (noColums + 1) * gap)
				/ (double) noColums);
		int compHeigh = (int) ((maxHeight - (noRows + 1) * gap)
				/ (double) noRows);

		int topLeftX = insets.left;
		int topLeftY = insets.top;

		int row = 0;
		int col = 0;

		for (int i = 0; i < noOfComponents; ++i) {
			Component c = parent.getComponent(i);
			row = constrains.get(i).getRow() - 1;
			col = constrains.get(i).getColumn() - 1;

			if (c != null && c.isVisible()) {

				if (row == 0 && col == 0) {
					resize(gap + topLeftX, gap + topLeftY, c,
							compWidth * 5 + 4 * gap, compHeigh);
				} else {

					resize(gap + topLeftX + col * (compWidth + gap),
							gap + topLeftY + row * (compHeigh + gap), c,
							compWidth, compHeigh);
				}
			}
		}
		layingOut = false;
	}

	/**
	 * Resizes and moves the component.
	 * 
	 * @param topLeftX
	 *            x coordinate of the top left corner of the position of the
	 *            component
	 * @param topLeftY
	 *            y coordinate of the top left corner of the position of the
	 *            component
	 * @param component
	 *            component to be resized
	 * @param compWidth
	 *            width of the box where to draw the component
	 * @param compHeigh
	 *            height of the box where to draw the component
	 */
	private synchronized void resize(int topLeftX, int topLeftY,
			Component component, int compWidth, int compHeigh) {
		Dimension d = null;

		d = component.getPreferredSize();
		if (compHeigh < d.height) {
			d.width = (int) ((double) compHeigh / d.height * d.width);
			d.height = compHeigh;
		}
		if (compWidth < d.width) {
			d.height = (int) ((double) compWidth / d.width * d.height);
			d.width = compWidth;
		}

		d.width = compWidth;
		d.height = compHeigh;
		component.setBounds(topLeftX, topLeftY, d.width, d.height);
	}

	/**
	 * Adding the layout component with its constrains in the internal storage.
	 * 
	 * @param comp
	 *            component to handle
	 * @param constraints
	 *            of the component {@code comp}
	 */
	@Override
	public synchronized void addLayoutComponent(Component comp,
			Object constraints) {
		Objects.requireNonNull(comp, "Components cannot be null!");
		Objects.requireNonNull(comp, "Costrain cannot be null!");

		if ((constraints instanceof RCPosition)) {
			this.constrains.add((RCPosition) constraints);
		} else {

			if ((constraints instanceof String)) {
				this.constrains.add(new RCPosition((String) (constraints)));
			} else {
				throw new IllegalArgumentException(
						"Constrains of invalid object type: "
								+ constraints.getClass());
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		Objects.requireNonNull(target);

		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return 0
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return (float) 0.5;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return 0
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return (float) 0.5;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void invalidateLayout(Container target) {
		if (!layingOut) {
			sizeUnknown = true;
			preferredHeight = 0;
			preferredWidth = 0;

			minimumHeight = 0;
			minimumWidth = 0;
		}
	}

	/**
	 * Sets the sizes of the layout due to {@link Container} {@code parent}
	 * 
	 * @param parent
	 *            parent container, container that is managed by this manager
	 */
	private synchronized void setSizes(Container parent) {
		Objects.requireNonNull(parent);

		Dimension d = null;
		int noOfComponents = parent.getComponentCount();

		int row;
		int col;

		int maxPreferedWidth = 0;
		int maxPreferredHeight = 0;
		int maxMinimumWidth = 0;
		int maxMinimumHeight = 0;
		int maxMaximumWidth = 0;
		int maxMaximumHeight = 0;

		for (int i = 0; i < noOfComponents; ++i) {
			Component c = parent.getComponent(i);
			RCPosition position = constrains.get(i);
			row = position.getRow() - 1;
			col = position.getColumn() - 1;

			if (c.isVisible()) {
				//////////// Prefered size
				d = c.getPreferredSize();
				if (d.height > maxPreferredHeight) {
					maxPreferredHeight = d.height;
				}

				// special case
				if (row == 0 && col == 0) {
					d.width = (int) Math.ceil((double) (d.width - 4 * gap) / 5);
				}

				if (d.width > maxPreferedWidth) {
					maxPreferedWidth = d.width;
				}

				//////////// Minimum size
				d = c.getMinimumSize();
				if (d.height > maxMinimumHeight) {
					maxMinimumHeight = d.height;
				}

				// special case
				if (row == 0 && col == 0) {
					d.width = (int) Math.ceil((double) (d.width - 4 * gap) / 5);
				}

				if (d.width > maxMinimumWidth) {
					maxMinimumWidth = d.width;
				}

				/////////////////// Maximum size
				d = c.getMaximumSize();
				if (d.height > maxMaximumHeight) {
					maxMaximumHeight = d.height;
				}

				// special case
				if (row == 0 && col == 0) {
					d.width = (int) Math.ceil((double) (d.width - 4 * gap) / 5);
				}

				if (d.width > maxMaximumWidth) {
					maxMaximumWidth = d.width;
				}

			}
		}
		preferredHeight = noRows * maxPreferredHeight + (noRows - 1) * gap;
		preferredWidth = noColums * maxPreferedWidth + (noRows - 1) * gap;

		minimumHeight = noRows * maxMinimumHeight + (noRows - 1) * gap;
		minimumWidth = noColums * maxMinimumWidth + (noRows - 1) * gap;
	}

}
