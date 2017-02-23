package hr.fer.zemris.java.tecaj_13;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Handles the connection the the database. When the web application
 * initialization process is starting the connection to database is also
 * starting. When the web application is shutting down, all created connections
 * to the data base are closed.
 * 
 * @author mia
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Initialization...");
		ResourceBundle configuration = ResourceBundle.getBundle("dbsettings");
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			try {
				cpds.setDriverClass(AppUtility.DRIVER);
				
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
				System.exit(0);
			}
			cpds.setJdbcUrl(createConnectionUrl(configuration));
			
			cpds.setUser(configuration.getString(AppUtility.USER));
			
			cpds.setPassword(configuration.getString(AppUtility.PASSWORD));
			
			cpds.setInitialPoolSize(5);
			cpds.setMinPoolSize(5);
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
		} catch (MissingResourceException e) {
			System.err.println("App should be stoped!!" + e.getMessage());
			System.exit(-1);
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
			System.exit(-2);
		}
	
		sce.getServletContext().setAttribute(AppUtility.spdsKEY, cpds);
		

		Connection con = null;
		try {
			con = cpds.getConnection();
		} catch (SQLException ignorable) {
		}
		// add if not exists.
		try {
			addTable("Polls", con);
			
			// wit for creation
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, "POLLS", null);
		
	
			while (!rs.next()) {
				rs.close();
				try {
					Thread.sleep(200);
				} catch (InterruptedException ignorable) {
				}
				rs = dbmd.getTables(null, null, "POLLS", null);
				
		
		
			}
			
			rs.close();
			addTable("PollOptions", con);
		
			con.close();

		} catch (SQLException e) {
		}
	}

	/**
	 * Method adding the table in the base. If table pools is empty, it is
	 * filled with data alongside the table PoolsOptions
	 * 
	 * @param tableName
	 *            name of the table
	 * @param con
	 *            connection used
	 * @return true if table is added, false otherwise.
	 * @throws SQLException
	 *             in case of error while adding the table in the base.
	 */
	private boolean addTable(String tableName, Connection con)
			throws SQLException {
		if (con != null) {
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(),
					null);
		

			if (rs.next()) {
				// if pools is empty
		
				PreparedStatement pst = null;
				try {
					pst = con.prepareStatement(
							"select id, title from Polls order by id");
					try {
						ResultSet polls = pst.executeQuery();
						if (!polls.next() && tableName.equalsIgnoreCase("Polls")) {
							InitUtility.fillTables(con);
						}
					} finally {
						try {
							pst.close();
						} catch (Exception ignorable) {
						}
					}
				} catch (Exception ignorable) {
				}
			} else {
				
				if (tableName.equalsIgnoreCase("Polls")) {
					InitUtility.createPollsTable(con);
				} else {
					InitUtility.createOptionsTable(con);
					InitUtility.fillTables(con);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Creates connection url form the data held in resource bundle. It is
	 * supposed that the bundle has at least provider, host, port and name
	 * property set. Example of connectionURL:
	 * jdbc:derby://localhost:1527/baza1DB
	 * 
	 * where 'derby' is provider, 'localhost' is host, '1527' is port, 'baza1DB'
	 * is database name.
	 * 
	 * @param bundle
	 *            bundle from which to read data.
	 * @return the URL from given data.
	 */
	private String createConnectionUrl(ResourceBundle bundle) {

		String url = "jdbc:" + "derby" + "://"
				+ bundle.getString(AppUtility.HOST) + ":"
				+ bundle.getString(AppUtility.PORT) + "/"
				+ bundle.getString(AppUtility.DBNAME);
		return url;

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce
				.getServletContext().getAttribute(AppUtility.spdsKEY);
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}