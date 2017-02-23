package hr.fer.zemris.java.hw14.webapp_baza.application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Class implementing functions for sending error messages. */
public class ErrorMessage {

	/**
	 * Static function sending the error message trought session of {@code req}
	 * {@link HttpServletRequest}.
	 * 
	 * @param req
	 *            request that provided error
	 * @param resp
	 *            response servlet of the message
	 * @param message
	 *            message about the error that will be forwarded to the client
	 * @throws IOException
	 *             if IO error occurs
	 * @throws ServletException
	 *             if IO error occurs
	 */
	public static void send(HttpServletRequest req, HttpServletResponse resp,
			String message) throws ServletException, IOException {
		req.getSession().setAttribute("errorMessage", message);
		req.getRequestDispatcher("/error.jsp").forward(req, resp);
	}
}
