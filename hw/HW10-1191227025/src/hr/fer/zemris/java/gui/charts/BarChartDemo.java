package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * Extends {@link JFrame} and shows only one {@link BarChart} on its all area.
 * Arguments for creating the BarChart instance is gotten trough file which path
 * is given as one command line argument.
 * 
 * @author mia
 *
 */
public class BarChartDemo extends JFrame {
	/**
	 * Default serialization ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param barChart
	 *            which will be drawn.
	 */
	public BarChartDemo(BarChart barChart) {
		setLocation(100, 100);
		setSize(600, 600);
		setTitle("BarChart");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI(barChart);
	}

	/**
	 * Method initializing the {@link JFrame}.
	 * 
	 * @param barChart
	 *            chart that will be drawn int the frame.
	 */
	private void initGUI(BarChart barChart) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.setBackground(Color.WHITE);
		
		BarChartComponent component = new BarChartComponent(barChart);
		JLabel label = new JLabel("Bar Chart:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		component.setOpaque(true);
		component.setBackground(Color.ORANGE);
		component.setForeground(Color.BLACK);
		
		cp.add(label, BorderLayout.PAGE_START);
		cp.add(component, BorderLayout.CENTER);
		
	}

	/**
	 * Program called when invoking the program.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Path to a file required.");
			System.exit(-1);
		}

		BarChart barChart = readFile(args[0]);
		System.out.println(barChart.getGap());

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo(barChart);
			frame.setVisible(true);
		});
	}

	/**
	 * Reads the first five lines of the file. Due to the read, crates the
	 * {@link BarChart} object.
	 * 
	 * @param fileName
	 * @return created BarChartObject
	 */
	private static BarChart readFile(String fileName) {
		List<XYValue> comp = new ArrayList<XYValue>();
		String xText = null;
		String yText = null;
		int Ymin = 0;
		int Ymax = 0;
		int gap = 0;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(fileName)),
				StandardCharsets.UTF_8))) {
			xText = br.readLine().trim();
			yText = br.readLine().trim();
			String[] parameters = br.readLine().trim().split(" ");
			try {
				Ymin = Integer.parseInt(br.readLine().trim());
				Ymax = Integer.parseInt(br.readLine().trim());
				gap = Integer.parseInt(br.readLine().trim());
			} catch (NumberFormatException e) {
				throw new IOException();
			}
			for (int i = 0; i < parameters.length; ++i) {
				try {
					String[] value = parameters[i].split(",");
					if (value.length != 2)
						throw new IOException();
					int x = Integer.parseInt(value[0]);
					int y = Integer.parseInt(value[1]);
					comp.add(new XYValue(x, y));
				} catch (NumberFormatException e) {
					throw new IOException();
				}
			}
		} catch (IOException e) {
			System.err.println("Error while reading : " + fileName);
			System.exit(-1);
		}

		return new BarChart(comp, xText, yText, Ymin, Ymax, gap);
	}

}
