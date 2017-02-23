package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class for storing and fetching the {@link EntityManagerFactory} used in
 * application.
 * 
 * @author mia
 *
 */
public class JPAEMFProvider {


	/**
	 * Instance of {@link EntityManagerFactory} used.
	 */
	public static EntityManagerFactory emf;

	/**
	 * @return instance of {@link EntityManagerFactory} used.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the instance of {@link EntityManagerFactory} used.
	 * @param emf instance of {@link EntityManagerFactory} used.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}