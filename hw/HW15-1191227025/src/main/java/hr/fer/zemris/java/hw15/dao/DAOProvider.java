package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Class used for fetching of instance of implementation of {@link DAO} which
 * will be used in application.
 * 
 * @author mia
 *
 */
public class DAOProvider {

	/**
	 * Static instance of {@link DAO} used.
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * @return static instance of {@link DAO} used.
	 */
	public static DAO getDAO() {
		return dao;
	}

}