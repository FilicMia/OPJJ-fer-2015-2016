package hr.fer.zemris.java.tecaj_13.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet listing all polls available.
 * 
 * @author mia
 *
 */
@WebServlet("/index.html")
public class ListAllPolls extends HttpServlet {

	/**
	 * VersionID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", polls);

		req.getRequestDispatcher("/WEB-INF/pages/listAllPolls.jsp").forward(req,
				resp);
	}

}