package hr.fer.zemris.java.hw14.webapp_baza.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.VotingOption;

/**
 * Class implementing the methods needed in voting system. Reading of the band's
 * data from disk, reading voting results from disk, adding the vote to specific
 * option.
 * 
 * @author mia
 *
 */
public class VotingUtil {
	/**
	 * Reads all votes data for given poll from database. Saves only option ID
	 * and votesCount. Different values are separated by tab. Each option is
	 * represented by its id number.
	 * 
	 * @param filePath
	 *            file from where the data should be read.
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @param pollID
	 *            id of the poll on which the data should be produced
	 * @return map containing all votes data.
	 * @throws IOException
	 *             if sending error message went wrong
	 * @throws ServletException
	 *             if sending error message went wrong
	 */
	public synchronized static Map<Long, Integer> readVotes(
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long pollID = (Long) req.getSession().getAttribute("pollID");
		Map<Long, Integer> votes = Collections.synchronizedMap(new TreeMap<>());
		List<VotingOption> options = DAOProvider.getDao().getOptions(pollID);

		options.forEach(new Consumer<VotingOption>() {

			@Override
			public void accept(VotingOption t) {
				votes.putIfAbsent(t.getId(), t.getVotesCount());

			}
		});

		return votes;
	}

	/**
	 * Updating the votes in database due to map {@code votes}.
	 * 
	 * @param optionID
	 *            id of updated option.
	 * 
	 * @param votes
	 *            current vote results.
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req}, the origin
	 *            of the request.
	 */
	public synchronized static void writeVote(Long optionID,
			Map<Long, Integer> votes, HttpServletRequest req,
			HttpServletResponse resp) {

		Long pollID = (Long) req.getSession().getAttribute("pollID");
		DAOProvider.getDao().updateOptionVotesCount(pollID, optionID,
				votes.get(optionID));
	}

	/**
	 * Creates list of {@link VotingOption} with property {@link VotingOption#votesCount} set to sum
	 * of votes given to band represented by this instance of class Band.
	 * 
	 * 
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @return list of bands currently available in voting system with its
	 *         number of votes.
	 * @throws IOException
	 *             if error occurs while reading votes or bands data from file.
	 * @throws ServletException
	 *             if error occurs while reading votes or bands data from file.
	 */
	public synchronized static List<VotingOption> currentResults(
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<VotingOption> options = DAOProvider.getDao()
				.getOptions((Long) req.getSession().getAttribute("pollID"));

		options.sort(new Comparator<VotingOption>() {

			@Override
			public int compare(VotingOption option1, VotingOption option2) {
				return -Integer.compare(option1.getVotesCount(),
						option2.getVotesCount());
			}
		});

		return options;
	}

	/**
	 * Creates the data for pie chart due to current result data.
	 * 
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @param pollID
	 *            id of the poll on which the data should be produced
	 * @return created dataset
	 * @throws IOException
	 *             while collecting data to get current voting state
	 * @throws ServletException
	 *             while collecting data to get current voting state
	 */
	public static PieDataset createData(HttpServletRequest req,
			HttpServletResponse resp, Long pollID)
			throws ServletException, IOException {
		DefaultPieDataset result = new DefaultPieDataset();
		List<VotingOption> votes = DAOProvider.getDao().getOptions(pollID);

		votes.forEach(vote -> {
			result.setValue(vote.getOptionTitle(), vote.getVotesCount());
		});

		return result;

	}

	/**
	 * Displays links for winner option.
	 * 
	 * @param reports
	 *            list containing options with its vote count.
	 * @return list of currently leading options
	 * @throws java.io.IOException
	 *             if writing the link on out colapses
	 **/
	public static List<VotingOption> getLeadingOptionsLink(List<VotingOption> reports)
			throws java.io.IOException {
		List<VotingOption> leading = new ArrayList<>();
		
		if (reports.isEmpty())
			return leading;

		int score = reports.get(0).getVotesCount();
		int counter = 0;

		while (true) {
			VotingOption option = reports.get(counter);
			if (option.getVotesCount() != score)
				break;
			leading.add(option);
			counter++;
		}
		return leading;

	}

}
