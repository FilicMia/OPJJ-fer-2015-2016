package hr.fer.zemris.java.hw13.webapp2.jBeans;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Singleton object creating a pie-chart. Pie-chart represents the usage of
 * OS-systems.
 */
public class PieChart {

	/**
	 * Single instance of pieChart.
	 */
	private static PieChart pieChart;

	/** Internally saved chart of OS-usage. */
	private JFreeChart chart;

	/**
	 * Private constructor creating an instance of pie-chart.
	 */
	private PieChart() {
		chart = PieChartCreator.getChart(createDataset(), "");
	}

	/**
	 * Gets an instance of {@link PieChart}. If the instance hasn't been
	 * created, the method creates it.
	 * 
	 * @return instance of {@link PieChart}
	 */
	public static PieChart getInstance() {
		if (pieChart == null) {
			pieChart = new PieChart();
		}

		return pieChart;
	}

	/**
	 * Method creates pie-chart and returns it.
	 * 
	 * @return created pie-chart.
	 */
	public JFreeChart getChart() {
		return chart;
	}

	/**
	 * Creates a sample dataset for pieChart.
	 * 
	 * @return created data for pieChart
	 */

	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;

	}
}
