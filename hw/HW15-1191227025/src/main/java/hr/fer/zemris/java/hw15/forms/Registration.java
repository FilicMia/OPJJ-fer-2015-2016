package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.crypto.DigestChecker;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class validating date from registration form.
 * 
 * @author mia
 *
 */
public class Registration {

	/**
	 * User's nickname.
	 */
	private String nick;

	/**
	 * User's first name.
	 */
	private String firstName;

	/**
	 * Users last name.
	 */
	private String lastName;

	/**
	 * User's email.
	 */
	private String email;

	/**
	 * Password of user.
	 */
	private String password;

	/**
	 * Error map found while validating.
	 */
	private Map<String, String> errors;

	/**
	 * Constructor.
	 */
	public Registration() {
		errors = new HashMap<String, String>();
	}

	/**
	 * 
	 * @return nick name found in form.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the nickname on the nick provided.
	 * 
	 * @param nick
	 *            user's nisck name
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * 
	 * @return User's first name found in the form.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets user's first name on parameter provided.
	 * 
	 * @param firstName
	 *            user's first
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return User's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets User's last name.
	 * 
	 * @param lastName
	 *            User's last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return user's mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets user's mail.
	 * 
	 * @param email
	 *            user's mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return user's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets user's password on digest of parameter passed.
	 * 
	 * @param password
	 *            user's password.
	 */
	public void setPassword(String password) {
		if (password == null || password.length() == 0) {
			this.password = "";
		} else {
			this.password = DigestChecker.calculateSHA(password);
		}
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
	 * Fills the class with params gotten from {@link HttpServletRequest}
	 * ,mapped on 'nick', 'first.name', 'last.name', 'email' i 'password'
	 * 
	 * @param req
	 *            {@link HttpServletRequest} from which the data will be
	 *            fetched.
	 */
	public void fillFromHttpReq(HttpServletRequest req) {
		nick = prepare(req.getParameter("nick"));
		firstName = prepare(req.getParameter("first.name"));
		lastName = prepare(req.getParameter("last.name"));
		email = prepare(req.getParameter("email"));
		setPassword(prepare(req.getParameter("password")));
	}

	/**
	 * Checks if errors exists.
	 * 
	 * @return true if errors exists, false otherwise
	 */
	public boolean checkErrors() {
		errors.clear();
		if (prepare(nick).length() == 0) {
			errors.put("nick", "Nickname is required!");
		}
		if (prepare(firstName).length() == 0) {
			errors.put("first.name", "First name is required!");
		}
		if (prepare(lastName).length() == 0) {
			errors.put("last.name", "Last name is required!");
		}

		if (prepare(email).length() < 3 || !email.contains("@")) {
			errors.put("email", "Valid email is required!");
		}
		if (password.length() == 0) {
			errors.put("password", "Password is required!");
		}

		return !errors.isEmpty();
	}

	/**
	 * Fetches new {@link BlogUser}, if there is no error, null otherwise.
	 * 
	 * @return new {@link BlogUser}, if there is no error, null otherwise.
	 */
	public BlogUser getNewBlogUser() {
		if (errors.isEmpty()) {
			return new BlogUser(firstName, lastName, nick, email, password);
		}
		return null;
	}

	/**
	 * @return errors map
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Restarts the instance setting all parameters on null.
	 */
	public void reset() {
		this.nick = null;
		this.email = null;
		this.firstName = null;
		this.lastName = null;
	}
}