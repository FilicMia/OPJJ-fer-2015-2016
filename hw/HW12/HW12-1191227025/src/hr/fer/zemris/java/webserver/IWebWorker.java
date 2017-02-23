package hr.fer.zemris.java.webserver;

/**
 * An interface toward any object that can process current request: it gets
 * RequestContext as parameter and it is expected to create a content for
 * client.
 * 
 * @author mia
 *
 */
public interface IWebWorker {

	/**
	 * Method that processes the current request.
	 * 
	 * @param context
	 *            {@link RequestContext} of the contet that willbe created.
	 */
	public void processRequest(RequestContext context);
}
