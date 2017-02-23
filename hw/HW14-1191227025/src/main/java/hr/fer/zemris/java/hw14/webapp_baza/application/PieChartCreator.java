package hr.fer.zemris.java.hw14.webapp_baza.application;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Class creating pie chart from {@link DefaultPieDataset} data. Each entry in
 * the map is pair key-name of entity and value-number connected to that entity.
 */
public class PieChartCreator {

	/**
	 * Creates a pie-chart due to given data with title {@code title}.
	 * 
	 * @param pieDataset
	 *            dataset
	 * @param title
	 *            title of the pie-chart
	 * @return created {@link JFreeChart}
	 */
	public static JFreeChart getChart(PieDataset pieDataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title,
				pieDataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

}