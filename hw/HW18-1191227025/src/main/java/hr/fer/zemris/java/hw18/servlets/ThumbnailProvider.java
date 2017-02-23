/**
 * 
 */
package hr.fer.zemris.java.hw18.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.model.Galery;
import hr.fer.zemris.java.hw18.model.Picture;

/**
 * Streams the thumbnail picture. Name of the picture which thumbnail is
 * requested, is passed as parameter name({@link #PARAMETER1}).
 * 
 * @author mia
 *
 */
@SuppressWarnings("serial")
@WebServlet("/thumbnail")
public class ThumbnailProvider extends HttpServlet {
	/**
	 * First parameter of this servlet.
	 */
	public static final String PARAMETER1 = "name";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter(PARAMETER1);

		Picture picture = Galery.getPicture(name);
		String fileName = req.getServletContext().getRealPath("/");

		if (!picture.isThumbnail()) {
			createThumbnail(picture, Paths.get(fileName));
			picture.setThumbnail(true);// thumbnail is created
		}
		
		resp.sendRedirect("picture?name="+name+"&thumbnail=true");
	}

	/**
	 * Method creating the thumbnail of the provided picture.
	 * 
	 * @param picture
	 *            picture of which the thumbnail will be created.
	 * @param rootPath
	 *            root path for this webapp
	 * @param thumbnailPath
	 *            path where to save picture.
	 */
	private void createThumbnail(Picture picture, Path rootPath) {
		BufferedImage img = null;
		Path path = rootPath.resolve(Galery.PICTURE_FOLDER)
				.resolve(picture.getName());
		Path thumbPath = rootPath.resolve(Galery.THUMBNAIL_FOLDER)
				.resolve(picture.getName());
		try {
			byte[] fileData = Files.readAllBytes(path);
			ByteArrayInputStream bi = new ByteArrayInputStream(fileData);

			img = ImageIO.read(bi);
			BufferedImage resized = resize(img, Galery.THUMBNAIL_WIDTH,
					Galery.THUMBNAIL_WIDTH);

			ImageIO.write(resized, "jpg", thumbPath.toFile());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resizes the buffered image passed to dimensions passed.
	 * 
	 * @param img
	 *            buffered image to be resized.
	 * @param newW
	 *            new width of the picture
	 * @param newH
	 *            new height of the image
	 * @return resized image
	 */
	private BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}
}
