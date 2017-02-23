package hr.fer.zemris.java.tecaj_13.dao.sql;

import java.sql.Connection;

/**
 * Stores the connection to the data base in ThreadLocal object. ThreadLocal is
 * map whose keys are thread id which does which currently operates the map.
 * 
 * @author mia
 *
 */
public class SQLConnectionProvider {

	/**
	 * Connections storage.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets the connction for current thread or deletes the connection from the
	 * map if argument is <code>null</code>.
	 * 
	 * @param con
	 *            connection to the base.
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Fetches the connection which can be used by current therad.
	 * 
	 * @return connection to the database.
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}