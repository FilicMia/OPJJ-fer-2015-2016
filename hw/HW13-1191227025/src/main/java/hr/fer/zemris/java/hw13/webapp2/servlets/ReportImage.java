package hr.fer.zemris.java.hw13.webapp2.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;

import hr.fer.zemris.java.hw13.webapp2.jBeans.PieChart;

/**
 * Servlet setting application pages background color due to parameter color.
 */

@WebServlet(urlPatterns = { "/reportImage" })
public class ReportImage extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8211579904075070081L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/pngl; charset=UTF-8");
		
		byte[] image = getBytePieChart();
		resp.setBufferSize(image.length);
		
		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			out.write(image); 
			out.flush();
		}catch(IOException e) {}
		finally {
			if(out != null) {
				out.close();
			}
		}

	}

	/**
	 * Creates pie chart and returns it as byte array ussing {@link JFreeChart}
	 * and {@link ImageIO}.
	 * 
	 * @return byte representation of pie chart.
	 */
	private byte[] getBytePieChart() {
		JFreeChart chart = PieChart.getInstance().getChart();

		BufferedImage image = chart.createBufferedImage(400, 300);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "png", out);
		} catch (IOException e) {
		}
		byte[] bytes = out.toByteArray();

		try {
			out.close();
		} catch (IOException e) {
		}

		return bytes;
	}

}
