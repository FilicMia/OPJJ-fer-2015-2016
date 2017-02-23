package hr.fer.zemris.java.hw14.webapp_baza.servlets.voting;

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

import hr.fer.zemris.java.hw14.webapp_baza.application.PieChartCreator;
import hr.fer.zemris.java.hw14.webapp_baza.application.VotingUtil;

/**
 * Creates the pie chart of vote results. The chart displays current voting
 * result.
 * 
 * @author mia
 *
 */
@WebServlet(urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafikaServlet extends HttpServlet {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8265999994075070081L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("image/pngl; charset=UTF-8");

		byte[] image = getBytePieChart(req, resp);
		resp.setBufferSize(image.length);

		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			out.write(image);
			out.flush();
		} catch (IOException e) {
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * Creates pie chart and returns it as byte array using {@link JFreeChart}
	 * and {@link ImageIO}.
	 * 
	 * @param req
	 *            the origin of the request.
	 * 
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @return byte representation of pie chart.
	 * @throws IOException
	 *             while collecting data to get current voting state
	 * @throws ServletException
	 *             while collecting data to get current voting state
	 */
	private byte[] getBytePieChart(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		
		Long pollID = (Long) req.getSession().getAttribute("pollID");
		JFreeChart chart = PieChartCreator.getChart(VotingUtil.createData(req, resp,pollID), "");

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