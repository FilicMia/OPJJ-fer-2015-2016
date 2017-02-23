package hr.fer.zemris.java.hw13.webapp2.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.webapp2.application.CreateExcelFile;
import hr.fer.zemris.java.hw13.webapp2.application.ErrorMessage;

/**
 * Servlet dynamically create a Microsoft Excel document with n pages. On page i
 * there is a table with two columns. The first column contains integer numbers
 * from a to b. The second column contains i-th powers of these numbers.
 */
@WebServlet(urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1793698994160730099L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int a, b, n;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			checkIntegerRange(a, -100, 100, req, resp);

			b = Integer.parseInt(req.getParameter("b"));
			checkIntegerRange(b, -100, 100, req, resp);

			n = Integer.parseInt(req.getParameter("n"));
			checkIntegerRange(n, 1, 5, req, resp);

			HSSFWorkbook hfw = CreateExcelFile.createPowers(req, resp, a, b, n);
			CreateExcelFile.write(req, resp, hfw);

		} catch (Exception e) {
			ErrorMessage.send(req, resp, e.getMessage());
		}

	}

	/**
	 * Check if the passed number {@code number} is within the given range [
	 * {@code lower},{@code upper}]. If the number does not pass the validation
	 * error message is sent to the client.
	 * 
	 * @param number
	 *            number that will be validated.
	 * @param lower
	 *            lower limit for number
	 * @param upper
	 *            upper limit for number
	 * @param req
	 *            the origin of the number.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @return true if validation passed, otherwise the error message is sent
	 * @throws IOException
	 *             if sending an error message collapses
	 * @throws ServletException
	 *             if sending an error message collapses
	 */
	private boolean checkIntegerRange(int number, int lower, int upper,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (number < lower || number > upper) {
			req.getSession().setAttribute("errorMessage", "Parametar " + number
					+ " is out of range: [" + lower + "," + upper + "].");
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
		}
		return true;
	}

}
