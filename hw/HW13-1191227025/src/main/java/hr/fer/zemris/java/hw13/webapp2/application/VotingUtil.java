package hr.fer.zemris.java.hw13.webapp2.application;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.webapp2.jBeans.Band;

/**
 * Class implementing the methods needed in voting system. Reading of the band's
 * data from disk, reading voting results from disk, adding the vote to specific
 * band.
 * 
 * @author mia
 *
 */
public class VotingUtil {
	/** Relative path to the bands file. */
	public final static String BANDS = "/WEB-INF/glasanje-definicija.txt";
	/** Relative path to the votes file. */
	public final static String VOTES = "/WEB-INF/glasanje-rezultati.txt";

	/**
	 * Reads all bands data from file {@code filePath}. One line corresponds one
	 * band. Different values in one line are separated by tab.
	 * 
	 * @param filePath
	 *            file from where the data should be read.
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @return list containing all bands data.
	 * @throws IOException
	 *             if sending error message went wrong
	 * @throws ServletException
	 *             if sending error message went wrong
	 */
	public synchronized static List<Band> readBands(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		List<Band> bands = Collections.synchronizedList(new ArrayList<Band>());

		String filePath = req.getServletContext().getRealPath(BANDS);
		try (Stream<String> lines = Files.lines(Paths.get(filePath),
				StandardCharsets.UTF_8)) {
			lines.forEachOrdered(line -> {
				if (!line.isEmpty()) {
					String[] data = line.split("\\t+");
					
					bands.add(new Band(Integer.parseInt(data[0].trim()),
							data[1].trim(), data[2].trim()));
				}
			});
		} catch (IOException e) {
			ErrorMessage.send(req, resp,
					"Data aboud bands can not be read.%n" + e.getMessage());
		}

		return bands;
	}

	/**
	 * Reads all votes data from file {@code filePath}. One line corresponds one
	 * band votes. Different values are separated by tab. Each band is
	 * represented by its id number.
	 * 
	 * @param filePath
	 *            file from where the data should be read.
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @return map containing all votes data.
	 * @throws IOException
	 *             if sending error message went wrong
	 * @throws ServletException
	 *             if sending error message went wrong
	 */
	public synchronized static Map<Integer, Integer> readVotes(
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<Integer, Integer> votes = Collections
				.synchronizedMap(new TreeMap<>());

		String filePath = req.getServletContext().getRealPath(VOTES);
		File f = new File(filePath);
		if (!f.exists() || !f.isFile()) {
			return newVoteFile(req, resp);
		}

		try (Stream<String> lines = Files.lines(Paths.get(filePath),
				StandardCharsets.UTF_8)) {
			lines.forEachOrdered(line -> {
				if (!line.isEmpty()) {
					String[] data = line.split("\\t+");
					votes.put(Integer.parseInt(data[0].trim()),
							Integer.parseInt(data[1].trim()));
				}
			});
		} catch (IOException e) {
			ErrorMessage.send(req, resp,
					"Data aboud bands can not be read.%n" + e.getMessage());
		}

		return votes;
	}

	/**
	 * Creates the new vote file with id's of bands saved in {@code fileBands}.
	 * 
	 * @param fileBands
	 *            name of file containing data about bands.
	 * @param filePath
	 *            file which should be created.
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @return map containing all votes data.
	 * @throws IOException
	 *             if sending error message went wrong
	 * @throws ServletException
	 *             if sending error message went wrong
	 */
	public synchronized static Map<Integer, Integer> newVoteFile(
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Band> bands = readBands(req, resp);
		Map<Integer, Integer> votes = Collections
				.synchronizedMap(new TreeMap<>());

		String filePath = req.getServletContext().getRealPath(VOTES);
		try {
			File file = new File(filePath);
			if (!file.createNewFile())
				ErrorMessage.send(req, resp, "Error creating file.");
		} catch (IOException ignorable) {

		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			bands.forEach(band -> {
				try {
					bw.write(band.getId() + "\t" + "0\n");
					votes.putIfAbsent(band.getId(), 0);
				} catch (Exception e) {
					try {
						ErrorMessage.send(req, resp, e.getMessage());
					} catch (Exception e1) {
					}
				}
			});
		} catch (IOException ignorable) {
		}

		return votes;
	}

	/**
	 * Updating the votes file stored on the disk du eto map {@code votes}.
	 * 
	 * @param votes
	 *            current vote results.
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req}, the origin
	 *            of the request.
	 */
	public synchronized static void writeVotes(Map<Integer, Integer> votes,
			HttpServletRequest req, HttpServletResponse resp) {

		String filePath = req.getServletContext().getRealPath(VOTES);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			votes.forEach((k, v) -> {
				try {
					bw.write(k + "\t" + v + "\n");
				} catch (Exception e) {
					try {
						ErrorMessage.send(req, resp, e.getMessage());
					} catch (Exception e1) {
					}
				}
			});
		} catch (IOException ignorable) {
		}
	}

	/**
	 * Creates list of {@link Band} with property {@link Band#vote} set to sum
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
	public synchronized static List<Band> currentResults(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		Map<Integer, Integer> votes = readVotes(req, resp);
		List<Band> bands = readBands(req, resp);
		bands.forEach(band -> {
			band.setVote(votes.get(band.getId()));
		});

		bands.sort(new Comparator<Band>() {

			@Override
			public int compare(Band band1, Band band2) {
				return -Integer.compare(band1.getVote(), band2.getVote());
			}
		});

		return bands;
	}

}
