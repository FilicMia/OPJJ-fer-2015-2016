package hr.fer.zemris.java.hw13.webapp2.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet setting application pages background color due to parameter color.
 */

@WebServlet(urlPatterns = { "/setcolor" })
public class ColorChooserServlet extends HttpServlet {
	/** Name of the attribute in session holding the color of the background. */
	private static final String ATTR_NAME = "pickedBgColor";

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8265579904075070081L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String color = req.getParameter("color");
		req.getSession().setAttribute(ATTR_NAME, color);

		req.getRequestDispatcher("/colors.jsp").forward(req, resp);

	}

}
