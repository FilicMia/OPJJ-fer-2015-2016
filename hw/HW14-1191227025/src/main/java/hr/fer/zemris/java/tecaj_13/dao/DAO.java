package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.model.*;

import java.sql.Connection;
import java.util.List;

/**
 * Interface to the data persistence system.
 * 
 * @author mia
 *
 */
public interface DAO {

	/**
	 * Fetches all voting options for corresponding given pollID.
	 * 
	 * @param pollID
	 *            the id of poll for which options should be fetched.
	 * 
	 * @return list of all options corresponding the poll with given pollID
	 * @throws DAOException
	 *             if exception occurs
	 */
	public List<VotingOption> getOptions(long pollID) throws DAOException;

	/**
	 * Fetch {@link VotingOption} for given id. It the {@link VotingOption} for
	 * given id does not exists, <code>null</code> is returned.
	 * 
	 * @param id
	 *            id of the option to be fetched
	 * @return VotingOption for given id
	 * @throws DAOException
	 *             if fetching the VotingOption went wrong
	 */
	public VotingOption getOption(long id) throws DAOException;

	/**
	 * Method fetching the pool from the base whose primary id is {@code id}. It
	 * that kind of poll does not exsists, returns null.
	 * 
	 * @param id
	 *            primary key of poll.
	 * @return poll
	 */
	public Poll getPoll(long id);

	/**
	 * Method fetches all polls from the base.
	 * 
	 * @return list of {@link Poll}
	 */
	public List<Poll> getPolls();

	/**
	 * Method adds poll into base with name and massage recived.
	 * 
	 * @param title
	 *            name of the poll
	 * @param message
	 *            poll message
	 * @param con
	 *            conneciton used.
	 * @return id of poll added
	 */
	public long addPoll(String title, String message, Connection con);

	/**
	 * Adds the option with given params into base in poll with given id. *
	 * 
	 * @param title
	 *            option name
	 * @param link
	 *            link used for woting
	 * @param pollID
	 *            poll to which the option will be added
	 * @param votes
	 *            votes count of the option
	 * @param con
	 *            connection used.
	 * @return id of given connection
	 */
	public long addOption(String title, String link, long pollID, int votes,
			Connection con);

	/**
	 * Fetch the votes coutn of option {@code optionID} from poll{@code pollID}.
	 * 
	 * @param pollID
	 *            poll id
	 * @param optionID
	 *            option id.
	 * @return voting count.
	 */
	public Integer getOptionVotesCount(long pollID, long optionID);

	/**
	 * Updates the votes count of option {@code optionID} from poll
	 * {@code pollID}.
	 * 
	 * @param pollID
	 *            poll id
	 * @param optionID
	 *            option id.
	 * @param count
	 *            update option votes count to {@code count}.
	 */
	public void updateOptionVotesCount(long pollID, long optionID, int count);

	/**
	 * Checks if {@link poll} with given name exists.
	 * 
	 * @param title
	 *            poll's name
	 * @param con
	 *            connection used.
	 * @return true if poll exists, false otherwise.
	 */
	boolean PollExists(String title, Connection con);

}