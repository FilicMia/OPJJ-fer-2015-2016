package hr.fer.zemris.java.hw14.webapp_baza.servlets.voting;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.VotingOption;

/**
 * Servlet preparing the voting surrounding. It loads data about options for
 * voting of given poll with {@code pollID} that can be voted in session under
 * key {@code options}.
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
		Long pollid = Long.parseLong(req.getParameter("pollID"));
		List<VotingOption> options = DAOProvider.getDao().getOptions(pollid);

		options.sort(new Comparator<VotingOption>() {

			@Override
			public int compare(VotingOption option1, VotingOption option2) {
				return Long.compare(option1.getId(), option2.getId());
			}
		});

		req.getSession().setAttribute("options", options);
		req.getSession().setAttribute("pollID", pollid);
		Poll poll = DAOProvider.getDao().getPoll(pollid);
		req.getSession().setAttribute("poll", poll);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
				.forward(req, resp);

	}

}
