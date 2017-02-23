package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.sql.SQLDAO;

/**
 * Singleton class which knows which implementation of the {@link DAO} should be
 * returned.
 * 
 * @author mia
 *
 */
public class DAOProvider {

	/**
	 * Class which implements {@link DAO}
	 */
	private static DAO dao = new SQLDAO();

	/**
	 * Getter of the implementation of the interface{@link DAO}.
	 * 
	 * @return object encapsulation the access to the persistence data system.
	 */
	public static DAO getDao() {
		return dao;
	}

}