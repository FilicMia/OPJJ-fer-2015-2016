/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.model.Galery;
import hr.fer.zemris.java.hw18.model.Picture;

/**
 * @author mia
 *
 */
/**
 * Streams the picture. Name of the picture which is requested, is passed as
 * parameter name({@link #PARAMETER1}). If the second parameter thumbpath is
 * provided, the picture streamed is thumbnail picture of picture named
 * {@code name}.
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet("/picture")
public class PictureProvider extends HttpServlet {

	/**
	 * First parameter of this servlet.
	 */
	public static final String PARAMETER1 = "name";
	/**
	 * Second parameter of this servlet.
	 * It is optional.
	 */
	public static final String PARAMETER2 = "thumbnail";


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter(PARAMETER1);

		Picture picture = Galery.getPicture(name);
		String fileName = req.getServletContext().getRealPath("/");
		Path path = null;
		
		if (req.getParameter(PARAMETER2) == null) {
			path = Paths.get(fileName, Galery.PICTURE_FOLDER)
					.resolve(picture.getName());
		} else {
			path = Paths.get(fileName, Galery.THUMBNAIL_FOLDER)
					.resolve(picture.getName());
		}

		byte[] image = Files.readAllBytes(path);
		resp.setBufferSize(image.length);

		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			out.write(image);
			out.flush();
		} catch (IOException e) {
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}
}
