package hr.fer.zemris.java.tecaj_13.model;

/**
 * Entry for the table {@code VOtingOptions}. Held data significant for one
 * voting option: optionTitle,optionLink, pollID(referees the poll to which this
 * option is dedicated), voteCount.
 * 
 * @author mia
 *
 */
public class VotingOption {
	/**
	 * Unique ID of the voting option in the database table.
	 */
	private long id;
	/** Option title. */
	private String optionTitle;
	/** Link corresponding the given option. */
	private String optionLink;
	/**
	 * Referees the poll to which this option is dedicated.
	 */
	private long pollID;

	/** Sum of votes given to this option. */
	private int votesCount;

	/**
	 * Constructor using fields.
	 * 
	 * @param id
	 *            id of the option given in table.
	 * @param optionTitle
	 *            title
	 * @param optionLink
	 *            link corresponding the option
	 * @param pollID
	 *            referees the poll to which this option is dedicated.
	 * @param votesCount
	 *            sum of votes given to this option at this point
	 */
	public VotingOption(long id, String optionTitle, String optionLink,
			long pollID, int votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * @return id of the option given in table.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id of the option.
	 * 
	 * @param id
	 *            id of the option given in table.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return option title.
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the {@link #optionTitle} of the option.
	 * 
	 * @param optionTitle
	 *            title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * @return link corresponding the option
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the {@link #optionLink} of the option.
	 * 
	 * @param optionLink
	 *            link corresponding the option.
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * @return id of the poll to which this option is dedicated.
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets the {@link #pollID} of the option.
	 * 
	 * @param pollID
	 *            referees the poll to which this option is dedicated.
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * @return {@link VotingOption#votesCount}.
	 */
	public int getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the {@link #votesCount} of the option.
	 * 
	 * @param voteCount
	 *            sum of votes given to this option at this point.
	 */
	public void setVotesCount(int voteCount) {
		this.votesCount = voteCount;
	}

	@Override
	public String toString() {
		return "Unos id=" + id;
	}
}