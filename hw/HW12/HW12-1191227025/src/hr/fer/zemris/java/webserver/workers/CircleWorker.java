package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker job is to produce an PNG image with dimensions 200x200 and with a
 * single filled circle.
 * 
 * @author mia
 *
 */
public class CircleWorker implements IWebWorker {
	/** Type of the content that will be sent. */
	public static final String MIMETYPE = "image/png";

	@Override
	public synchronized void processRequest(RequestContext context) {

		BufferedImage bim = new BufferedImage(200, 200,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		Random rmd = new Random();
		context.setMimeType(MIMETYPE);

		g2d.setColor(new Color(rmd.nextInt(255), rmd.nextInt(255),
				rmd.nextInt(255)));
		g2d.fillOval(0, 0, bim.getWidth() / 2,
				bim.getWidth() / 2);

		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
