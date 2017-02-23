package hr.fer.zemris.java.hw15.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Validation of form blog entry.
 * 
 * @author mia
 *
 */
public class BlogEntryForm {

	/** Title of the blog entry. */
	private String title;

	/** Text corresponding the blog entry. */
	private String text;

	/**
	 * Error map found while validating.
	 */
	private Map<String, String> errors;

	/**
	 * Constructor.
	 */
	public BlogEntryForm() {
		super();
		this.title = "";
		this.text = "";
		this.errors = new HashMap<String, String>();
	}

	/**
	 * @return blog entry title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets blog entry title.
	 * 
	 * @param title
	 *            blog entry title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return content of the blog entry
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets content of the blog entry.
	 * 
	 * @param text
	 *            content of the blog entry
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return errors map
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Sets errors map.
	 * 
	 * @param errors
	 *            errors found while validating.
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	/**
	 * Checks if errors exists in error map.
	 * 
	 * @return true if errors exists, false otherwise
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Checks if there is error mapped on string field.
	 * 
	 * @param field
	 *            string on which the error should be checked
	 * @return true if error exists, false otherwise.
	 */
	public boolean hasError(String field) {
		return errors.containsKey(field);
	}

	/**
	 * Gets error mapped on string field.
	 * 
	 * @param field
	 *            string on which the error (if it exists) will be returned
	 * @return error mapped, null if it doesn't exists.
	 */
	public String getError(String field) {
		return errors.get(field);
	}

	/**
	 * Fills the class with params gotten from {@link HttpServletRequest}
	 * ,mapped on 'title' and 'text.'
	 * 
	 * @param req
	 *            {@link HttpServletRequest} from which the data will be
	 *            fetched.
	 */
	public void fillFromHttpReq(HttpServletRequest req) {
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));
	}

	/**
	 * Returns null for empty string, trimmed last argument otherwise.
	 * 
	 * @param str
	 *            parameter modified.
	 * @return modified string.
	 */
	private String prepare(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * Checks if errors exists due to data.
	 * 
	 * @return true if errors exists, false otherwise
	 */
	public boolean checkErrors() {
		errors.clear();

		if (prepare(title).length() == 0) {
			errors.put("title", "Title required!");
		}
		if (prepare(text).length() == 0) {
			errors.put("text", "Text is required!");
		}

		return !errors.isEmpty();
	}

	/**
	 * Fetches new {@link BlogEntry}, if there is no error, null otherwise.
	 * 
	 * @return new {@link BlogEntry}, if there is no error, null otherwise.
	 */
	public BlogEntry getBlogEntry() {
		BlogEntry entry = new BlogEntry();
		entry.setTitle(this.title);
		entry.setText(this.text);
		return entry;
	}
}