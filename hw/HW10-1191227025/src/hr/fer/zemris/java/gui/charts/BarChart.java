/**
 * 
 */
package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class storing values that will be used in drawing the specified bar-chart.
 * 
 * @author mia
 *
 */
public class BarChart {
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
	 * @param values
	 *            Value pairs that will be shown on diagram.
	 * @param xText
	 *            Text of x-axis.
	 * @param yText
	 *            Text of y-axis.
	 * @param yMin
	 *            Minimal number that will be shown on y-axis.
	 * @param yMax
	 *            Maximal number that will be shown on y-axis.
	 * @param gap
	 *            Gap between numbers on y-axis.
	 */
	public BarChart(List<XYValue> values, String xText, String yText, int yMin,
			int yMax, int gap) {
		super();
		this.values = values;
		this.xText = xText;
		this.yText = yText;
		this.yMin = yMin;
		this.yMax = yMax;
		this.gap = gap;
	}

	/**
	 * @return list of value pairs
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return Text of x-axis.
	 */
	public String getxText() {
		return xText;
	}

	/**
	 * @return Text of y-axis.
	 */
	public String getyText() {
		return yText;
	}

	/**
	 * @return Minimal number that will be shown on y-axis.
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return Maximal number that will be shown on y-axis.
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return Gap between numbers on y-axis.
	 */
	public int getGap() {
		return gap;
	}

}
