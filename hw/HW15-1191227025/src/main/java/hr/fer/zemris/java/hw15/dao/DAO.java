package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface for date persistention.
 * 
 * @author mia
 *
 */
public interface DAO {

	/**
	 * Fetches the blog entry with id id.
	 * 
	 * @param id
	 *            id of the blog to be fetched.
	 * @return the blog entry with id id.
	 * @throws DAOException
	 *             if fetching of information gets wrong.
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Fetches all blog entries done by user {@code user}.
	 * 
	 * @param user
	 *            whose blog entries are fetched.
	 * @return all blog entries done by user {@code user}.
	 * @throws DAOException
	 *             if fetching of information gets wrong.
	 */
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException;

	/**
	 * Fetches {@link BlogUser} with nick nickname.
	 * 
	 * @param nickname
	 *            nick of {@link BlogUser} to be returned
	 * @return {@link BlogUser} with nick nickname, null otherwise
	 * @throws DAOException
	 *             if fetching of information gets wrong.
	 */
	public BlogUser getBlogUser(String nickname) throws DAOException;

	/**
	 * Fetchs all {@link BlogUser}'s.
	 * 
	 * @return list of all {@link BlogUser}'s.
	 * @throws DAOException
	 *             if fetching of information gets wrong.
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Fetchs all comments of the passed {@link BlogEntry}.
	 * 
	 * @param entry
	 *            {@link BlogEntry} whose comment will be fetched.
	 * @return list of corresponding {@link BlogComment}
	 * @throws DAOException
	 *             if fetching of information gets wrong.
	 */
	public List<BlogComment> getBlogComments(BlogEntry entry)
			throws DAOException;

	/**
	 * Add new user, the way of adding is independent of {@link DAOProvider}'s.
	 * 
	 * @param user
	 *            {@link BlogUser} which will ne created.
	 * @throws DAOException
	 *             if creation went wrong.
	 */
	public void createBlogUser(BlogUser user) throws DAOException;

	/**
	 * Adds new blog entry, on way the specific implementation says.
	 * 
	 * @param entry
	 *            entry due to which the new blog entry will be created.
	 * @throws DAOException
	 *             if creation went wrong.
	 */
	public void createBlogEntry(BlogEntry entry) throws DAOException;

	/**
	 * Adds new comment to the pased blog, on way the specific implementation
	 * says.
	 * 
	 * @param comment
	 *            comment which will be added.
	 * @throws DAOException
	 *             if creation went wrong.
	 */
	public void createBlogComment(BlogComment comment) throws DAOException;

}