package hr.fer.zemris.java.hw15.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.Registration;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet registers the new user.
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/servleti/register" })
public class RegistrationServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Registration form = new Registration();
		req.getSession().setAttribute("zapis", form);
		// set form
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.removeAttribute("errors");
		req.removeAttribute("error");

		Registration form = new Registration();

		form.fillFromHttpReq(req);

		if (!form.checkErrors()) {
			BlogUser user = form.getNewBlogUser();
			if (user != null) {
				BlogUser nickExists = DAOProvider.getDAO()
						.getBlogUser(user.getNick());
				// nick taken
				if (nickExists != null) {
					form.getErrors().put("nick",
							"Please choose some other nick name, "
									+ "this one already exists.");
				} else {
					DAOProvider.getDAO().createBlogUser(user);
					req.setAttribute("status",
							"Registration is a success! Enjoy using your blog!");
					// main
					req.getRequestDispatcher("./main").forward(req, resp);
					return;
				}
			}
		}

		if (form.hasErrors()) {
			Collection<String> errors = form.getErrors().values();
			req.setAttribute("errors", errors);
			req.setAttribute("error",
					"Unable to proceed with registration, try again.");
		}
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req,
				resp);
	}
}