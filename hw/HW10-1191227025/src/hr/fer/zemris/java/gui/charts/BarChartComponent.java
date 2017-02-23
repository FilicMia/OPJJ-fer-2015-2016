/**
 * 
 */
package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * Component representing the bar-chart.
 * 
 * @author mia
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Free space between elements of the graph.
	 */
	private static final int FREE = 10;

	/**
	 * Width of the axis in graph.
	 */
	private static final int WIDTH_AXIS = 3;

	/** The length of the arrow. */
	private static final int ARROW_LEN = 6;

	/** Arrow semi width. */
	private static final int ARROW_SEMI_WIDTH = 2;

	/** Grid color. */
	Color gridCol = new Color(255, 230, 204);

	/**
	 * (X,Y) value pairs that will be shown on diagram.
	 */
	private List<XYValue> values;

	/**
	 * Text of x-axis.
	 */
	private String xText;

	/**
	 * Text of y-axis.
	 */
	private String yText;

	/**
	 * Minimal number that will be shown on y-axis.
	 */
	private int yMin;

	/**
	 * Maximal number that will be shown on y-axis.
	 */
	private int yMax;

	/**
	 * 
	 * Gap between numbers on y-axis.
	 */
	private int gap;

	/**
	 * Constructor. Stores the bar chart.
	 * 
	 * @param barChart
	 *            specific BarChart, null is not excepted
	 */
	public BarChartComponent(BarChart barChart) {
		super();
		Objects.requireNonNull(barChart);

		values = barChart.getValues();
		xText = barChart.getxText();
		yText = barChart.getyText();
		yMin = barChart.getyMin();
		gap = barChart.getGap();
		yMax = (barChart.getyMax() - yMin) % gap == 0 ? barChart.getyMax()
				: barChart.getyMax()
						+ (gap - (barChart.getyMax() - yMin) % gap);
	}

	@Override
	protected void paintComponent(Graphics graphic) {
		Graphics2D g2d = (Graphics2D) graphic.create();
		FontMetrics fm = getFontMetrics(new Font("Serif", Font.BOLD, 12));
		Insets insets = getInsets();
		int namesTook = 2 * fm.getDescent() + 2 * fm.getAscent() + FREE;
		int numbersTookFromLeft = fm.stringWidth(Integer.toString(yMax)) + FREE;
		int numbersTookFromBottom = fm.getAscent() + FREE;

		drawNames(g2d, fm);
		int fromLeft = insets.left + namesTook + numbersTookFromLeft;
		int fromBottom = insets.bottom + fm.getAscent() + numbersTookFromBottom
				+ ARROW_SEMI_WIDTH;

		int xPointChartBox = fromLeft + ARROW_SEMI_WIDTH;
		int yPointChartBox = insets.top + 2 * FREE + ARROW_LEN;
		int heightChartBox = getSize().height - fromBottom - yPointChartBox
				- ARROW_LEN;
		int widthChartBox = getSize().width - xPointChartBox - insets.right
				- ARROW_LEN - FREE;

		drawRows(g2d, fm, xPointChartBox, yPointChartBox, widthChartBox,
				heightChartBox);

		drawColumns(g2d, fm, xPointChartBox, yPointChartBox, widthChartBox,
				heightChartBox);
		drawAxis(g2d, xPointChartBox, yPointChartBox, widthChartBox,
				heightChartBox);
		graphic.dispose();
	}

	/**
	 * Drawing the axis.
	 * 
	 * @param g2d
	 *            {@link Graphics2D} that will be used
	 * @param xStart
	 *            x coordinate of the upper left corner of the area on which
	 *            columns can be drawn
	 * @param yStart
	 *            y coordinate of the upper left corner of the area on which
	 *            columns can be drawn
	 * @param width
	 *            width of the area on which columns can be drawn
	 * @param height
	 *            height of the area on which columns can be drawn
	 */
	private void drawAxis(Graphics2D g2d, int xStart, int yStart, int width,
			int height) {
		Stroke oldStroke = g2d.getStroke();
		Color oldColor = g2d.getColor();

		g2d.setStroke(new BasicStroke(WIDTH_AXIS));
		g2d.setColor(Color.GRAY);

		g2d.drawLine(xStart, yStart, // vertical
				xStart, yStart + height);

		g2d.drawLine(xStart, yStart + height, xStart + width + ARROW_LEN,
				yStart + height);
		g2d.drawPolygon(
				new int[] { xStart, xStart - ARROW_SEMI_WIDTH,
						xStart + ARROW_SEMI_WIDTH },
				new int[] { -ARROW_LEN + yStart, yStart, yStart }, 3);
		g2d.drawPolygon(
				new int[] { xStart + width + ARROW_LEN, xStart + width,
						xStart + width },
				new int[] { yStart + height, yStart + height - ARROW_SEMI_WIDTH,
						yStart + height + ARROW_SEMI_WIDTH },
				3);

		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);
	}

	/**
	 * Drawing the row grid with labels of each row on the chart.
	 * 
	 * @param g2d
	 *            {@link Graphics2D} that will be used
	 * @param fm
	 *            font metrics of numbers to be written
	 * @param xStart
	 *            x coordinate of the upper left corner of the area on which
	 *            columns can be drawn
	 * @param yStart
	 *            y coordinate of the upper left corner of the area on which
	 *            columns can be drawn
	 * @param width
	 *            width of the area on which columns can be drawn
	 * @param height
	 *            height of the area on which columns can be drawn
	 */
	private void drawRows(Graphics2D g2d, FontMetrics fm, int xStart,
			int yStart, int width, int height) {

		int yRange = yMax - yMin;
		int heighPerUnit = height / yRange;

		int yPoint = yStart + height;
		int widthNum = 0;
		int heightNum = fm.getAscent();
		for (int i = 0; i <= yRange; i += gap) {
			g2d.setColor(gridCol);
			g2d.drawLine(xStart, yPoint, xStart + width, yPoint);

			g2d.setColor(Color.BLACK);
			widthNum = fm.stringWidth(Integer.toString(i));
			g2d.drawString(Integer.toString(i),
					xStart - WIDTH_AXIS - FREE - widthNum,
					yPoint + heightNum / 2 - 1);
			yPoint -= heighPerUnit * gap;
		}

	}

	/**
	 * Draws the columns of the chart using the provided {@link Graphics2D}.
	 * 
	 * @param g2d
	 *            {@link Graphics2D} that will be used
	 * @param fm
	 *            font metrics of numbers to be written
	 * @param xStart
	 *            x coordinate of the upper left corner of the area on which
	 *            columns can be drawn
	 * @param yStart
	 *            y coordinate of the upper left corner of the area on which
	 *            columns can be drawn
	 * @param width
	 *            width of the area on which columns can be drawn
	 * @param height
	 *            height of the area on which columns can be drawn
	 */
	private void drawColumns(Graphics2D g2d, FontMetrics fm, int xStart,
			int yStart, int width, int height) {

		int noColumns = values.size();
		int yRange = yMax - yMin;
		int heighPerUnit = height / yRange;
		int widthPerUnit = width / noColumns;

		int xPoint = xStart;
		int yPoint = yStart;
		for (int i = 0; i < noColumns; ++i) {
			yPoint = yStart + height - values.get(i).getY() * heighPerUnit;
			g2d.setColor(Color.ORANGE);
			g2d.drawRect(xPoint, yPoint, widthPerUnit,
					values.get(i).getY() * heighPerUnit);
			
			g2d.fillRect(xPoint, yPoint, widthPerUnit,
					values.get(i).getY() * heighPerUnit);
			g2d.setColor(gridCol);
			if (i != 0) {
				g2d.drawLine(xPoint, yStart, xPoint, yStart + height);
			}

			g2d.drawLine(xPoint, yPoint, xPoint + widthPerUnit, yPoint);

			int widthNum = fm
					.stringWidth(Integer.toString(values.get(i).getX()));

			g2d.setColor(Color.BLACK);
			g2d.drawString(Integer.toString(values.get(i).getX()),
					xPoint + widthPerUnit / 2 - widthNum / 2,
					yStart + height + WIDTH_AXIS + FREE + fm.getAscent());

			xPoint += widthPerUnit;
		}
		// the rightest
		g2d.setColor(gridCol);
		g2d.drawLine(xPoint, yStart, xPoint, yStart + height);

	}

	/**
	 * Write the names beside x and y axis.
	 * 
	 * @param g
	 *            Graphics2D
	 * @param fm
	 *            FontMetrics
	 */
	private void drawNames(Graphics2D g, FontMetrics fm) {

		int widthx = fm.stringWidth(xText);
		g.setColor(Color.BLACK);
		g.drawString(xText, getInsets().left + getSize().width / 2 - widthx / 2,
				getSize().height - getInsets().bottom - 2 * fm.getDescent());
		int widthy = fm.stringWidth(yText);
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g.setTransform(at);
		g.drawString(yText,
				-getInsets().top - getSize().height / 2 - widthy / 2,
				2 * fm.getAscent() + getInsets().left);
		at.rotate(Math.PI / 2);
		g.setTransform(at);
	}

}
