package hr.fer.zemris.java.hw15.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.LogIn;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet resolving log in process. If there is error in data the person can
 * log in again. .
 * 
 * @author mia
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/servleti/main" })
public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LogIn login = new LogIn();
		generatePage(req, resp, login);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.removeAttribute("errors");
		LogIn form = new LogIn();
		form.fillFromHttpReq(req);

		if (!form.checkErrors()) {
			BlogUser current = DAOProvider.getDAO().getBlogUser(form.getNick());
			if ((current != null)
					&& current.getPasswordHash().equals(form.getPassword())) {
				req.getSession().setAttribute("current.user", current);
				req.getSession().setAttribute("current.user.nick",
						current.getNick());
				req.getSession().setAttribute("current.user.id",
						current.getId());
				req.getSession().setAttribute("current.user.first",
						current.getFirstName());
				req.getSession().setAttribute("current.user.last",
						current.getLastName());

				req.setAttribute("status", current.getFirstName() + " "
						+ current.getLastName() + " logged in.");
				form.getErrors().put("errors",
						"wrong username or password, try again!");
			}
		}
		if (form.hasErrors()) {
			req.setAttribute("errors", form.getErrors().values());
		}
		generatePage(req, resp, form);
	}

	/**
	 * Method sets session parameters connected to the data gotten form the log
	 * in form. Forwards the request to the jsp mapped to the
	 * '/WEB-INF/pages/main.jsp'.
	 * 
	 * @param req
	 *             {@link HttpServletRequest}
	 * @param resp
	 *             {@link HttpServletResponse}
	 * @param login
	 *            login form
	 * @throws ServletException
	 *             if fulfilling the request goes wrong
	 * @throws IOException
	 *             if fulfilling the request goes wrong
	 */
	private void generatePage(HttpServletRequest req, HttpServletResponse resp,
			LogIn login) throws ServletException, IOException {
		req.getSession().setAttribute("zapis", login);
		List<BlogUser> authors = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("authors", authors);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
}