package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet logaouts the user and redirects it to'/servleti/main'
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/servleti/logout" })
public class LogingOutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().invalidate();
		req.getRequestDispatcher("./main").forward(req, resp);
	}
}