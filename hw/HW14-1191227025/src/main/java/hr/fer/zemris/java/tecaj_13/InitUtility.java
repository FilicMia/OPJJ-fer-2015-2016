package hr.fer.zemris.java.tecaj_13;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Class implementing methods for creating the tables.
 * 
 * @author mia
 *
 */
public class InitUtility {

	/**
	 * Metoda kreira tablicu u koju se spremaju opcije za dostupne ankete,
	 * naziva PollOptions.
	 * 
	 * @param con
	 *            connection used.
	 */
	public static void createOptionsTable(Connection con) {

		String createString = "CREATE TABLE PollOptions"
				+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "optionTitle VARCHAR(100) NOT NULL, "
				+ "optionLink VARCHAR(150) NOT NULL, " + "pollID BIGINT, "
				+ "votesCount BIGINT, "
				+ "FOREIGN KEY (pollID) REFERENCES Polls(id))";

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createString);
		} catch (SQLException ignorable) {
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Method creating 'Pools' table.
	 * 
	 * @param con
	 *            Connection used
	 */
	public static void createPollsTable(Connection con) {

		String createString = "CREATE TABLE Polls"
				+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "title VARCHAR(150) NOT NULL, "
				+ "message CLOB(2048) NOT NULL)";

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createString);
		} catch (SQLException ignorable) {
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Method filling table Pools and VotingPolls with data if they do not
	 * exsists in base.
	 * 
	 * @param con
	 *            connection used
	 */
	public static void fillTables(Connection con) {
		// film poll
		if (!DAOProvider.getDao().PollExists("Glasanje za omiljeni bend:",
				con)) {
			long id = DAOProvider.getDao().addPoll("Glasanje za omiljeni bend:",
					"Od sljedećih bendova koji vam je najdraži? Kliknite na link kako biste glasali!",
					con);
			if (id != -1) {
				DAOProvider.getDao().addOption("The Beatles",
						"http://www.geocities.com/~goldenoldies/TwistAndShout-Beatles.mid",
						Long.valueOf(id), 150, con);
				DAOProvider.getDao().addOption("The Platters",
						"http://www.geocities.com/~goldenoldies/SmokeGetsInYourEyes-Platters-ver2.mid",
						Long.valueOf(id), 60, con);
				DAOProvider.getDao().addOption("The Beach Boys",
						"http://www.geocities.com/~goldenoldies/SurfinUSA-BeachBoys.mid",
						Long.valueOf(id), 150, con);
				DAOProvider.getDao().addOption("The Four Seasons",
						"http://www.geocities.com/~goldenoldies/BigGirlsDontCry-FourSeasons.mid",
						Long.valueOf(id), 20, con);
				DAOProvider.getDao().addOption("The Marcels",
						"http://www.geocities.com/~goldenoldies/Bluemoon-Marcels.mid",
						Long.valueOf(id), 33, con);
				DAOProvider.getDao().addOption("The Everly Brothers",
						"http://www.geocities.com/~goldenoldies/All.I.HaveToDoIsDream-EverlyBrothers.mid",
						Long.valueOf(id), 25, con);
				DAOProvider.getDao().addOption("The Mamas And The Papas",
						"http://www.geocities.com/~goldenoldies/CaliforniaDreaming-Mamas-Papas.mid",
						Long.valueOf(id), 20, con);
			}
		}
	}
}