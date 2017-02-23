package hr.fer.zemris.java.tecaj_13.model;

/**
 * Entry for the table {@code Polls}. Held data significant for one poll:
 * title,message and poll id.
 * 
 * @author mia
 *
 */
public class Poll {
	/**
	 * Unique ID of the poll in the database table.
	 */
	private long id;
	/** Poll's title. */
	private String title;
	/** Message corresponding the poll. */
	private String message;

	/**
	 * COnstructor using fields.
	 * 
	 * @param id
	 *            id of the poll in the database table of all polls.
	 * @param title
	 *            title of the poll.
	 * @param message
	 *            massage corresponding poll.
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.setMessage(message);
	}

	/**
	 * @return id of the poll in table.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id of the poll.
	 * 
	 * @param id
	 *            id of the id of the poll.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return poll's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the {@link #title} of the option.
	 * 
	 * @param title
	 *            title
	 */
	public void setOptionTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Unos id=" + id;
	}

	/**
	 * @return the message of the poll.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the massage of the poll.
	 * 
	 * @param message
	 *            message of the poll.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}