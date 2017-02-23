package hr.fer.zemris.java.hw13.webapp2.servlets.voting;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.webapp2.application.VotingUtil;
import hr.fer.zemris.java.hw13.webapp2.jBeans.Band;

/**
 * Servlet preparing the voting surrounding. It loads data about bands that can
 * be voted in session under key {@code bands}.
 * 
 */

@WebServlet(urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8265579994075070081L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Band> bands = VotingUtil.readBands(req, resp);
		bands.sort(new Comparator<Band>() {

			@Override
			public int compare(Band band1, Band band2) {
				return Integer.compare(band1.getId(), band2.getId());
			}
		});

		req.getSession().setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
				.forward(req, resp);

	}

}
