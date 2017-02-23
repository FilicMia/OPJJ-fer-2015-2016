package hr.fer.zemris.java.hw15.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet prepares data needed to show the blog entry..
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/servleti/entry/show" })
public class ShowingEntryServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		Long entryid = null;

		try {
			entryid = Long.parseLong(idStr);
		} catch (NumberFormatException ignorable) {
		}

		List<String> errors = new ArrayList<String>();

		if (entryid != null) {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryid);
			if (blogEntry != null) {
				req.getSession().setAttribute("entry", blogEntry);
			} else {
				errors.add("Entry with this id does not exist!");
			}
		} else {
			errors.add("Id of the entry not given!");
		}
		req.setAttribute("errors", errors);
		req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req,
				resp);
	}
}