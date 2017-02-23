package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker will create a HTML page with current time displayed and it will give a
 * different message depending if a parameter called “name” was provided in URL
 * that started this worker.
 */
public class HelloWorker implements IWebWorker {
	/** Type of the content that will be sent. */
	public static final String MIMETYPE = "text/html";
	@Override
	public synchronized void processRequest(RequestContext context) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		context.setMimeType(MIMETYPE);
		String name = context.getParameter("name");
		try {
			context.write("<html><body>");
			context.write("<h1>Hello!!!</h1>");
			context.write("<p>Now is: " + sdf.format(now) + "</p>");
			if (name == null || name.trim().isEmpty()) {
				context.write("<p>You did not send me your name!</p>");
			} else {
				context.write("<p>Your name has " + name.trim().length()
						+ " letters.</p>");
			}
			context.write("</body></html>");
		} catch (IOException ex) {
			try {
				context.write("Error occured while runninf HelloWorker.");
			} catch (IOException ignorable) {
			}
			ex.printStackTrace();
		}
	}
}