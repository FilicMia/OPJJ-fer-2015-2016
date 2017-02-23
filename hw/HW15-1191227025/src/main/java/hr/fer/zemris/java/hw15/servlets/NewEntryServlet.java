package hr.fer.zemris.java.hw15.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet is in use for adding new entry.
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/servleti/entry/add" })
public class NewEntryServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nickname = (String) req.getParameter("author");

		BlogUser author = DAOProvider.getDAO().getBlogUser(nickname);

		if (req.getParameter("metoda").equals("Save")) {
			BlogEntry be = new BlogEntry();

			be.setCreatedAt(new Date(System.currentTimeMillis()));
			be.setLastModifiedAt(new Date(System.currentTimeMillis()));
			be.setCreator(author);
			be.setTitle(req.getParameter("title"));
			be.setText(req.getParameter("text"));

			DAOProvider.getDAO().createBlogEntry(be);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Entry is not saved!");

			req.setAttribute("errors", errors);
		}

		resp.sendRedirect("/blog/servleti/author/" + nickname);
	}
}