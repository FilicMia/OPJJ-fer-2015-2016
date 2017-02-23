/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.model.Galery;
import hr.fer.zemris.java.hw18.model.Picture;

/**
 * Sends the JSON form of picture of certain name
 * passed as parameter.
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet("/getPicture")
public class GetPicture extends HttpServlet {
	/**
	 * First parameter of this servlet.
	 */
	public static final String PARAMETER1 = "name";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter(PARAMETER1);
		Picture picture = Galery.getPicture(name);
		
		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(picture);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
	

}