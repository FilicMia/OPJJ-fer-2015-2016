package hr.fer.zemris.java.webserver;

/**
 * Utilitiy class for request. Mostly used as holder of static final variables.
 * 
 * @author mia
 *
 */
public class RequestUtil {
	/** Status OK. */
	public static final String STATUS_OK = "OK";

	/** Status code for OK */
	public static final int STATUS_CODE_OK = 200;

	/** Accepted method. */
	public static final String METHOD_GET = "GET";
	/** HTTP/1.0 */
	public static final String HTTP_0 = "HTTP/1.0";
	/** HTTP/1.1 */
	public static final String HTTP_1 = "HTTP/1.1";

	/** Default path to folder containing definitions of all workers. */
	public static final String DEFAULT_FQCN = "hr.fer.zemris.java.webserver.workers";

	/** Indicating that the DEFAULT_FQCN should be used. */
	public static final String EXT = "ext";
}
