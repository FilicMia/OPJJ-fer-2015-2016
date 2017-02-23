package hr.fer.zemris.java.hw13.webapp2.servlets.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.webapp2.application.VotingUtil;

/**
 * Servlet reading currently stored voting file. It loads currently stored
 * voting file and sends it to glasanjeRez.jsp trough session's attribute
 * {@code votes}. glasanjeRez.jsp will render the vote results.
 * 
 * @author mia
 */

@WebServlet(urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8265579994075070081L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().setAttribute("votes",
				VotingUtil.currentResults(req, resp));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);

	}

}