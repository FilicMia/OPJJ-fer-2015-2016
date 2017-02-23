package hr.fer.zemris.java.hw13.webapp2.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.webapp2.jBeans.Trigonometric;

/**
 * Servlet gets the parameters a and b and makes the list of
 * {@link Trigonometric} instances which storing integer values
 * form a to b.
 */
@WebServlet(urlPatterns = { "/trigonometric" })
public class TrigonometricFunctionCalculator extends HttpServlet {

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = -6491819044052859756L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String param1 = req.getParameter("a");
		String param2 = req.getParameter("b");

		int a = param1 == null ? 0 : Integer.parseInt(param1);
		int b = param2 == null ? 360 : Integer.parseInt(param2);

		if (a > b) {
			int tmp = b;
			b = a > b + 720 ? b + 720 : a;
			a = tmp;
		}

		List<Trigonometric> trigonometricList = new ArrayList<>();
		for (int i = a; i <= b; ++i) {
			trigonometricList.add(new Trigonometric(i));
		}

		req.getSession().setAttribute("trigonometricList", trigonometricList);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp")
				.forward(req, resp);
	}

}
