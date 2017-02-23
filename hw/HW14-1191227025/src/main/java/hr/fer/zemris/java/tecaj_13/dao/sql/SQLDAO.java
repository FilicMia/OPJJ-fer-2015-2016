package hr.fer.zemris.java.tecaj_13.dao.sql;

import hr.fer.zemris.java.tecaj_13.ConnectionSetterFilter;
import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.VotingOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link DAO} interface using SQL technology.
 * Implementation expects the connection to be provided through the
 * {@link SQLConnectionProvider} class. Filter {@link ConnectionSetterFilter}
 * will make it possible. it intercepts the call for servlet and inserts the
 * connection from connection-pool into LocalThread. When the servlet is done,
 * it removes the connection.
 * 
 * @author mia
 */
public class SQLDAO implements DAO {

	@Override
	public List<VotingOption> getOptions(long pollID)
			throws DAOException {
		List<VotingOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();// get my
																// conneciton

		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from PollOptions order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						VotingOption option = new VotingOption(rs.getLong(1),
								rs.getString(2), rs.getString(3), rs.getLong(4),
								rs.getInt(5));

						options.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while fetching the user request.",
					ex);
		}
		return options;
	}

	@Override
	public VotingOption getOption(long id) throws DAOException {
		VotingOption option = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from PollOptions where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						option = new VotingOption(rs.getLong(1),
								rs.getString(2), rs.getString(3), rs.getLong(4),
								rs.getInt(5));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Error while fetching the option: " + id + ".", ex);
		}
		return option;
	}

	@Override
	public Poll getPoll(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		Poll entry = null;
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				
				try {
					if (rs != null && rs.next()) {
						entry = new Poll(rs.getLong(1), rs.getString(2),
								rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {}
			}
		} catch (Exception ex) {
			throw new DAOException("Error obtaining poll", ex);
		}
		return entry;
	}

	@Override
	public long addPoll(String title, String message, Connection conection) {
		Connection con = SQLConnectionProvider.getConnection();
		
		//kod inicijalizacije baze
		if(conection != null) {
			con = conection;
		}
		
		PreparedStatement pst = null;
		long newid = -1;
		
		try {
			pst = con.prepareStatement(
					"INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, title);
			pst.setString(2, message);
			try {
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
				try {
					if (rs != null && rs.next()) {
						newid = rs.getLong(1);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {}
			}
		} catch (Exception ex) {
			throw new DAOException("Error obtaining poll", ex);
		}
		return newid;
	}

	@Override
	public long addOption(String title, String link, long pollID, int votes, Connection conection) {

		Connection con = SQLConnectionProvider.getConnection();
		//kod inicijalizacije baze
		if(conection != null) {
			con = conection;
		}
		
		PreparedStatement pst = null;
		long noviID = -1;
		try {
			pst = con
					.prepareStatement(
							"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, title);
			pst.setString(2, link);
			pst.setLong(3, pollID);
			pst.setInt(4, votes);
			try {
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
				try {
					if (rs != null && rs.next()) {
						noviID = rs.getLong(1);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {System.out.print(ignorable.getMessage());}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
					System.out.print(ignorable.getMessage());
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		System.out.print(noviID);
		return noviID;
	}

	@Override
	public Integer getOptionVotesCount(long pollID, long optionID) {
		Integer data = null;
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, votesCount from PollOptions where pollID=? and id=?");
			pst.setLong(1, Long.valueOf(pollID));
			pst.setLong(2, optionID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						data = Integer.valueOf(rs.getInt(4));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Error obtaining poll option", ex);
		}
		return data;
	}

	@Override
	public void updateOptionVotesCount(long pollID, long optionID, int count) {
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("update pollOptions set votesCount=? where id=? and pollID=?");
			
			pst.setLong(1, Integer.valueOf(count));
			pst.setLong(2, Long.valueOf(optionID));
			pst.setLong(3, pollID);
			
			try {
				pst.executeUpdate();
			} catch (Exception ex) {
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"error obtaining poll option", ex);
		}
	}

	@Override
	public boolean PollExists(String title, Connection conection) {
		
		Connection con = SQLConnectionProvider.getConnection();
		
		//base init
		if(conection != null) {
			con = conection;
		}
		
		//false for non-existance
		boolean exists = false;
		System.out.print("conn" + con.toString());
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title from Polls where title=?");
			
			pst.setString(1, title);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						exists = true;
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Error obtaining poll", ex);
		}
		return exists;
	}
	
	@Override
	public List<Poll> getPolls() {
		
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				
				try {
					while (rs != null && rs.next()) {
						Poll entry = new Poll(rs.getLong(1),
								rs.getString(2), "");
						polls.add(entry);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"error obtaining polls", ex);
		}
		return polls;
	}
}