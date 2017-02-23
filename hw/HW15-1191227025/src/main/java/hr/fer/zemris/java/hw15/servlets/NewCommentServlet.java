package hr.fer.zemris.java.hw15.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

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
 * Servlet adds new comment..
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/servleti/comment/add" })
public class NewCommentServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String nick = req.getParameter("nick");
		Long id = Long.parseLong(req.getParameter("id"));
		List<String> errors = new ArrayList<>();

		if (req.getParameter("metoda").equals("Save")) {
			BlogEntry be = DAOProvider.getDAO().getBlogEntry(id);

			if (be != null) {
				BlogComment comment = new BlogComment();

				comment.setMessage(req.getParameter("message"));
				comment.setPostedOn(new Date(System.currentTimeMillis()));
				comment.setBlogEntry(be);
				String email = req.getParameter("email");
				if ((email.length() >= 3) && email.contains("@")) {
					comment.setUsersEMail(email);
					DAOProvider.getDAO().createBlogComment(comment);
				} else {
					errors.add("Valid email address required!");
				}
			} else {
				errors.add("Entry does not exist!");
			}
		} else {
			errors.add("Nothing's been saved!");
		}

		req.setAttribute("errors", errors);
		resp.sendRedirect("/blog/servleti/author/" + nick + "/" + id);
	}
}