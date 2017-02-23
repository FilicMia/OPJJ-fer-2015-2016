package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.crypto.DigestChecker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Validation of the data passed over user's log in form.
 * 
 * @author mia
 *
 */
public class LogIn {

	/**
	 * User password.
	 */
	private String password;

	/**
	 * User's nickname.
	 */
	private String nick;

	/**
	 * Errors found
	 */
	private Map<String, String> errors;

	/**
	 * Constructor.
	 */
	public LogIn() {
		errors = new HashMap<String, String>();
	}

	/**
	 * Fetches the password.
	 * 
	 * @return password recived
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password on the dogest of the value given.
	 * 
	 * @param password
	 *            password
	 */
	public void setPassword(String password) {
		if (password != null && password.length() > 0) {
			this.password = DigestChecker.calculateSHA(password);
		} else {
			this.password = "";
		}
	}

	/**
	 * Fetches the user's name.
	 * 
	 * @return user's name
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the user's name.
	 * 
	 * @param nick
	 *            the user's name.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return errors map
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Checks if there is errors found.
	 * 
	 * @return true if errors exists, false otherwise.
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
	 * ,mapped on'nick' and 'password'.
	 * 
	 * @param req
	 *            {@link HttpServletRequest} from which the data will be
	 *            fetched.
	 */
	public void fillFromHttpReq(HttpServletRequest req) {
		setNick(prepare(req.getParameter("nick")));
		setPassword(prepare(req.getParameter("password")));
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
	 * Checks if errors exists.
	 * 
	 * @return true if errors exists, false otherwise
	 */
	public boolean checkErrors() {
		errors.clear();

		if (nick == null || nick.length() == 0) {
			errors.put("nick", "No nickname given!");
		}
		if (password == null || password.length() == 0) {
			errors.put("password", "No password given!");
		}

		return !errors.isEmpty();
	}
}