package hr.fer.zemris.java.hw13.webapp2.jBeans;

import java.util.Objects;

import hr.fer.zemris.java.hw13.webapp2.servlets.voting.GlasanjeServlet;

/**
 * JavaBean class representing the each bend that can be voted. Data about ban
 * will be loaded trough the {@link GlasanjeServlet} from the prepared file. The
 * file contains band id,name and link.
 */
public class Band {

	/** Id of the band. */
	private int id;
	/** Namer of the band. */
	private String name;
	/** Link to the band's song. */
	private String link;

	/** Number of votes. */
	private int vote = -1;

	/**
	 * Constructor setting fields.
	 * 
	 * @param id
	 *            id of the band.
	 * @param name
	 *            name of the band
	 * @param link
	 *            link to the band's song.
	 */
	public Band(int id, String name, String link) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(link);

		this.id = id;
		this.name = name;
		this.link = link;
	}

	/**
	 * @return the ID of the band.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return name of the band
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return link to the band's song.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return number of votes given to this band, -1 if {@link #setVote(int)
	 *         has never been called.}.
	 */
	public int getVote() {
		return vote;
	}

	/**
	 * @param vote
	 *            number of votes given to this band.
	 */
	public void setVote(int vote) {
		this.vote = vote;
	}

}
