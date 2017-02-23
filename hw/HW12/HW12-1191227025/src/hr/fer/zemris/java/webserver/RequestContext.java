/**
 * 
 */
package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class implementing a context of the request. It has output stream trough
 * which the answer should be send, charset, encoding, type of the content. It
 * also stores context parameters as well as session cookies.
 * 
 * @author mia
 *
 */
public class RequestContext {
	/** Stream used for output streaming in this context. */
	private OutputStream outputStream;
	/** Charset used in this context. */
	private Charset charset;
	/*** Encoding used in this context. */
	private String encoding = "UTF-8";

	/** Status code of the message({@link RequestContext}). */
	private int statusCode = 200;

	/** Text that will be send next to the status code in the header. */
	private String statusText = "OK";

	/** Mime-Type of the context. */
	private String mimeType = "text/html";

	/** Map of parameters of this context */
	private Map<String, String> parameters;

	/** Map of temporary parameters of this context */
	private Map<String, String> temporaryParameters = new HashMap<>();

	/** Map of persistent parameters of this context */
	private Map<String, String> persistentParameters;

	/** List of output cookies. */
	private List<RCCookie> outputCookies;

	/**
	 * Indicates if the header is generated.
	 */
	private boolean headerGenerated = false;

	/**
	 * Class representing some kind of web cookie. It stores the name of the
	 * value stored, value, domain and path of the cookie and maximum duration
	 * of the cookie.
	 * 
	 * @author mia
	 *
	 */
	public static class RCCookie {
		/** Name of the value. */
		private String name;
		/** Value of the{@link #name}. */
		private String value;
		/** Domain of the cookie. */
		private String domain;
		/** Path on the {@code domain} of the cookie. */
		private String path;
		/** Maximum duration of the cookie. */
		private Integer maxAge;
		/** Type of the cookie. */
		private String type;

		/**
		 * Constructor setting fields.
		 * 
		 * @param name
		 *            name of the {@code value} stored
		 * @param value
		 *            value stored
		 * @param maxAge
		 *            maximum duration of the cookie
		 * @param domain
		 *            domain of the cookie
		 * @param path
		 *            path on domain
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path) {
			super();
			Objects.requireNonNull(name);
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.type = null;
		}

		/**
		 * Gets read-only value property.
		 * 
		 * @return read-only value property.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets read-only value property.
		 * 
		 * @return read-only value property.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets read-only domain property.
		 * 
		 * @return read-only domain property.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets read-only path property.
		 * 
		 * @return read-only path property.
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets read-only maxAge property.
		 * 
		 * @return read-only maxAge property.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Sets the type of the cookie.
		 * 
		 * @param type
		 */
		public void setType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			String line = "Set-Cookie: " + getName() + "=\"" + getValue()
					+ "\"";
			sb.append(line);
			addIfNotNull(sb, getDomain(), "Domain");
			addIfNotNull(sb, getPath(), "Path");
			addIfNotNull(sb,
					getMaxAge() == null ? null : getMaxAge().toString(),
					"Max-Age");
			addIfNotNull(sb, type, "Type");
			sb.append("\r\n");

			return sb.toString();
		}

		/**
		 * Appends the string "; "+name+"="+"=\""+property+"\"" to the
		 * {@link StringBuilder} {@code sb} if {@code property} is not
		 * {@code null}.
		 * 
		 * @param sb
		 *            StringBuilder on which the string will be appended
		 * @param property
		 *            property value
		 * @param name
		 *            name of the property
		 *
		 */
		private void addIfNotNull(StringBuilder sb, String property,
				String name) {
			if (property != null) {
				sb.append("; ").append(name).append("=").append(property)
						.append("");
			}
		}

	}

	/**
	 * Constructor creating an instance of {@link RequestContext} and setting
	 * the initial values of stream and collection properties.
	 * 
	 * @param outputStream
	 *            Stream used for output streaming in this context.
	 * @param parameters
	 *            collection of parameters in this context
	 * @param persistentParameters
	 *            collection of persistent parameters in this context
	 * @param outputCookies
	 *            collection of cookies used in this context
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		super();
		Objects.requireNonNull(outputStream);
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<String, String>()
				: parameters;
		this.persistentParameters = persistentParameters == null
				? new HashMap<String, String>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<RCCookie>()
				: outputCookies;
	}

	/**
	 * Sets the encoding of this context.
	 * 
	 * @param encoding
	 *            encoding to be set.
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated)
			throw new RuntimeException(
					"The encoding can not be changed after the header is generated.");
		this.encoding = encoding;
	}

	/**
	 * Sets the statusCOde of this context.
	 * 
	 * @param statusCode
	 *            status code to be set.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated)
			throw new RuntimeException(
					"The status code can not be changed after the header is generated.");
		this.statusCode = statusCode;
	}

	/**
	 * Sets the statusText of this context.
	 * 
	 * @param statusText
	 *            status text to be set.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated)
			throw new RuntimeException(
					"The status text can not be changed after the header is generated.");
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type of this context.
	 * 
	 * @param mimeType
	 *            mime type to be set.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated)
			throw new RuntimeException(
					"The mime type can not be changed after the header is generated.");
		this.mimeType = mimeType;
	}

	/**
	 * Method adds a cookie to the outputCookie.
	 * 
	 * @param cookie
	 *            that will be added to {@link #outputCookies}
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated)
			throw new RuntimeException(
					"The cookieListt can not be changed after the header is generated.");
		Objects.requireNonNull(cookie);
		outputCookies.add(cookie);
	}

	/**
	 * Method that retrieves value from parameters map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            key of the value that should be returned from
	 *            {@link #parameters}.
	 * 
	 * @return value from parameters map or null if no association exists
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map (set is
	 * read-only).
	 * 
	 * @return set containing names of parameters in parameters map
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            key of the value that should be returned from
	 *            {@link #persistentParameters}.
	 * 
	 * @return value from persistentParameters map or null if no association
	 *         exists
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves unmodifiable {@link Set} containing names of all
	 * parameters in persistent parameters map .
	 * 
	 * @return unmodifiable {@link Set} containing names of all parameters in
	 *         persistent parameters map
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Method that stores a value to persistentParameters {@link Map}.
	 * 
	 * @param name
	 *            key value of the entry
	 * @param value
	 *            value value of the entry
	 * 
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map.
	 * 
	 * @param name
	 *            key associated with the value that should be removed
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            key of the value that should be returned from
	 *            {@link #temporaryParameters}.
	 * @return value associated with key {@code key} from temporaryParameters
	 *         map or null if no association exists
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in
	 * {@link #temporaryParameters} (set is read-only).
	 * 
	 * @return set containing names of parameters in
	 *         {@link #temporaryParameters}.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Method that stores a value to {@link #temporaryParameters}.
	 * 
	 * @param name
	 *            key value of the entry
	 * @param value
	 *            value value of the entry
	 * 
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from {@link #temporaryParameters}.
	 * 
	 * 
	 * @param name
	 *            key associated with the value that should be removed
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Method write its data into {@link #outputStream} that was given to
	 * RequestContext in constructor.
	 * 
	 * First time that this or {@link #write(String)} is called, a special
	 * header is written to underlying {@link #outputStream} and only then can
	 * given data be written.
	 * 
	 * At the moment the header is created and written all attempts to change
	 * any of properties {@link #encoding}, {@link #statusCode},
	 * {@link #statusText}, {@link #mimeType}, {@link #outputCookies} must throw
	 * RuntimeException.
	 * 
	 * @param data
	 *            bytes to be writen into {@link #outputStream}
	 * @return {@code this} when writing is over.
	 * @throws IOException
	 *             if error with writing occurs.
	 */
	public RequestContext write(byte[] data) throws IOException {
		Objects.requireNonNull(data);
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data);
		return this;
	}

	/**
	 * Method write its data into {@link #outputStream} that was given to
	 * RequestContext in constructor. It first converts given data into bytes
	 * using previously configured encoding (i.e. {@link #charset}). First time
	 * that this or {@link #write(byte[])} is called, a special header is
	 * written to underlying {@link #outputStream} and only then can given data
	 * be written.
	 * 
	 * At the moment the header is created and written all attempts to change
	 * any of properties {@link #encoding}, {@link #statusCode},
	 * {@link #statusText}, {@link #mimeType}, {@link #outputCookies} must throw
	 * RuntimeException.
	 * 
	 * @param text
	 *            text to be writen into {@link #outputStream}
	 * @return {@code this} when writing is over.
	 * @throws IOException
	 *             if error with writing occurs.
	 */
	public RequestContext write(String text) throws IOException {
		Objects.requireNonNull(text);
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Generates the header and writes it to the {@link OutputStream}
	 * {@code #outputStream}.
	 * 
	 * At the moment of header construction it create a value for charset
	 * property: charset = {@link #Charset.forName(encoding)}; .
	 * 
	 * @throws IOException
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder(
				"HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.trim().startsWith("text/"))
			sb.append(" ;charset=" + encoding);
		sb.append("\r\n");

		outputCookies.forEach((cookie) -> {
			sb.append(cookie.toString());
		});
		sb.append("\r\n");

		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));

		headerGenerated = true;

	}

}
