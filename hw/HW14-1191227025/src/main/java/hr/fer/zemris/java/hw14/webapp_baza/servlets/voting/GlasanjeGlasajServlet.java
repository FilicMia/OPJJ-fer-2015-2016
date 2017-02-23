package hr.fer.zemris.java.hw14.webapp_baza.servlets.voting;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.webapp_baza.application.VotingUtil;

/**
 * Saves the vote to the option which {@code id} is stored under key id in 
 * requests session map.
 * It loads data about polles from databese.
 * 
 * @author mia
 *
 */
@WebServlet(urlPatterns = { "/glasanjeGlasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8265579994075070081L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Map<Long, Integer> votes = VotingUtil.readVotes(req, resp);
		Long id = Integer.toUnsignedLong(Integer.parseInt(req.getParameter("id")));
		votes.put(id, votes.getOrDefault(id, 0) + 1);

		VotingUtil.writeVote(id,votes, req, resp);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

	}
}