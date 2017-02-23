/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.model.Galery;
import com.google.gson.Gson;

/**
 * Servlet returns JSON list of all available tags of the gallery.
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet("/tags")
public class TagsGetter extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<String> list = Galery.getAllTags();
		String[] array = new String[list.size()];
		list.toArray(array);
		
		resp.setContentType("application/json;charset=UTF-8"); 
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(array);
		
		resp.getWriter().write(jsonText);
		
		resp.getWriter().flush();
	}

}