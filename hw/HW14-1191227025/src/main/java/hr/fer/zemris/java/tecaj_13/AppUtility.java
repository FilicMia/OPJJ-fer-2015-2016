package hr.fer.zemris.java.tecaj_13;

/**
 * Class handling some constants. Stores it.
 * 
 * @author mia
 *
 */
public class AppUtility {

	/**
	 * Key name under which the connection to the database done by
	 * ComboPooledDataSource will be stored in contextRequest session.
	 */
	public static final String spdsKEY = "hr.fer.zemris.dbpool";

	// dbsettings properties names.
	/**
	 * Key in {@code dbsettings.properties} under which the name of database
	 * provider used is held.
	 */
	public static final String PROVIDER = "provider";

	/**
	 * Key in {@code dbsettings.properties} under which the name of host used is
	 * held.
	 */
	public static final String HOST = "host";

	/**
	 * Key in {@code dbsettings.properties} under which the host's port used is
	 * held.
	 */
	public static final String PORT = "port";

	/**
	 * Key in {@code dbsettings.properties} under which the database's name used
	 * is held.
	 */
	public static final String DBNAME = "name";

	/**
	 * Key in {@code dbsettings.properties} under which the database user's name
	 * used for connecting to the base is held.
	 */
	public static final String USER = "user";

	/**
	 * Key in {@code dbsettings.properties} under which the database user's
	 * password used for connecting to the base is held.
	 */
	public static final String PASSWORD = "password";

	/**
	 * Key in {@code dbsettings.properties} under which the driver's menager
	 * implementaton used for connecting to the base is held.
	 */
	public static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";

}
