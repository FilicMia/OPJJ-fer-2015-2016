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

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.model.Galery;
import hr.fer.zemris.java.hw18.model.Picture;

/**
 * Sends the JSON form of all available pictures of certain tag.
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet("/getTagsPics")
public class GetTagPictures extends HttpServlet {
	/**
	 * First parameter of this servlet.
	 */
	public static final String PARAMETER1 = "tag";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String tag = req.getParameter(PARAMETER1);
		List<Picture> pics = Galery.getPictures(tag);
		
		Picture[] array = new Picture[pics.size()];
		pics.toArray(array);
		
		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
	

}
