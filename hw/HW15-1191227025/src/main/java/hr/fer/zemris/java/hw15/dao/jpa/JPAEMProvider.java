package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Storing the methods for fetching {@link EntityManager}, and local constant
 * {@link ThreadLocal} which is used.
 * 
 * @author mia
 *
 */
public class JPAEMProvider {
	/**
	 * Static instance of {@link ThreadLocal}, works with instances of
	 * {@link LocalData}.
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * @return instance of {@link EntityManager}.
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Closes the communication with {@link EntityManager}.
	 * 
	 * @throws DAOException
	 *             due to error in closing the connection.
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * Wrapper of {@link EntityManager}.
	 * 
	 * @author mia
	 */
	private static class LocalData {
		/**
		 * Instance of {@link EntityManager}.
		 */
		EntityManager em;
	}

}