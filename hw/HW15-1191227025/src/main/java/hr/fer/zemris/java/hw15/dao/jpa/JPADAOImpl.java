package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Concrete implementation of the interface DAO.
 * 
 * @author mia
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager()
				.find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException {
		EntityManager e = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = (List<BlogEntry>) e
				.createQuery(
						"select b from BlogEntry as b where b.creator=:creator")
				.setParameter("creator", user).getResultList();
		return entries;
	}

	@Override
	public BlogUser getBlogUser(String nickname) throws DAOException {
		EntityManager e = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) e
				.createQuery("select b from BlogUser as b where b.nick=:nick")
				.setParameter("nick", nickname).getResultList();
		if (users == null || users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		EntityManager e = JPAEMProvider.getEntityManager();
		System.out.println("Trazim users.");
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) e.createQuery("select u from BlogUser as u")
				.getResultList();
		System.out.println("Trazim users.");
		if (users == null) {
			return new ArrayList<BlogUser>();
		}
		return users;
	}

	@Override
	public List<BlogComment> getBlogComments(BlogEntry entry)
			throws DAOException {
		EntityManager e = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogComment> comments = (List<BlogComment>) e
				.createQuery(
						"select c from BlogComment as b where b.blogEntry=:blogEntry")
				.setParameter("blogEntry", entry).getResultList();
		if (comments == null) {
			return new ArrayList<BlogComment>();
		}
		return comments;
	}

	@Override
	public void createBlogUser(BlogUser user) throws DAOException {
		EntityManager e = JPAEMProvider.getEntityManager();
		e.persist(user);

	}

	@Override
	public void createBlogEntry(BlogEntry entry) throws DAOException {
		EntityManager e = JPAEMProvider.getEntityManager();
		e.persist(entry);

	}

	@Override
	public void createBlogComment(BlogComment comment) throws DAOException {
		EntityManager e = JPAEMProvider.getEntityManager();
		e.persist(comment);

	}

}