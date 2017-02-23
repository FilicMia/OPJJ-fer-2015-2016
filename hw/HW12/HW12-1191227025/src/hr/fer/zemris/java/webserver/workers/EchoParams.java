package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker EchoParams simply outputs back to user parameters it obtained in a
 * HTML table.
 * 
 * @author mia
 *
 */
public class EchoParams implements IWebWorker {
	/** Type of the content that will be sent. */
	public static final String MIMETYPE = "text/html";

	@Override
	public synchronized void processRequest(RequestContext context) {
		context.setMimeType(MIMETYPE);

		StringBuilder sb = new StringBuilder("<html>\r\n" + " <head>\r\n"
				+ "<title>Ispis parametara.</title>\r\n");
		sb.append("<style>th {color: #003366;}"
				+ "table{color: blue; font-variant: small-caps;}"
				+ "</style>");
		sb.append("</head>\r\n" + "<body>\r\n" + "<table border='1'>\r\n");
		sb.append("<tr><th>Name:</th><th>Value:</th></tr>\r\n");
		for (String name : context.getParameterNames()) {
			sb.append("   <tr><td>");
			sb.append(name);
			sb.append("   </td><td>");
			sb.append(context.getParameter(name));
			sb.append(" </td></tr>\r\n");

		}
		sb.append(" </table>\r\n");
		sb.append("  </body>\r\n");
		sb.append("</html>\r\n");
		try {
			context.write(sb.toString());
		} catch (IOException e) {
			try {
				context.write("Error occured while runninf EchoWorker.");
			} catch (IOException ignorable) {
			}
		}

	}

}
