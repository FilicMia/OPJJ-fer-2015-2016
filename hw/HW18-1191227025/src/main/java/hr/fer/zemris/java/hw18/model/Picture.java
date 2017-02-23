/**
 * 
 */
package hr.fer.zemris.java.hw18.model;

import java.util.List;

/**
 * Class representing the single picture of this galery. It holds all its
 * attributes saved in opisnik txt, as well as the indicator if the Thumbnail of
 * this picture has been created.
 * 
 * Pictures are saved in WEB-INF/slike and thumbnails of pictures is saved in
 * WEB-INF/thumbnails.
 * 
 * @author mia
 *
 */
public class Picture {

	/** Name of the picture. */
	private String name;
	/** List of tags for picture */
	private List<String> tags;
	/** Short desc of the picture. */
	private String desc;
	/** Indicator if the thumbnail for this picture has been created. */
	private boolean thumbnail;

	/**
	 * COnstructor.
	 * 
	 * @param name
	 *            name of picture
	 * @param tags
	 *            tags of picture
	 * @param desc
	 *            description of picture.
	 * 
	 */
	public Picture(String name, List<String> tags, String desc) {
		super();
		setName(name);
		setTags(tags);
		setDesc(desc);
		setThumbnail(false);
	}

	/**
	 * @return name of the picture
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return array of tags connected to this picture.
	 */
	public String[] getTags() {
		return tags.toArray(new String[tags.size()]);
	}

	/**
	 * @return string describing the picture.
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @return true if thumbnail of this picture has been created, false
	 *         otherwise.
	 */
	public boolean isThumbnail() {
		return thumbnail;
	}

	/**
	 * Sets the name of the picture.
	 * 
	 * @param name
	 *            name of the picture
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the list of the tags for this picture.
	 * 
	 * @param tags
	 *            list of tags.
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * Sets the description of the picture.
	 * 
	 * @param desc
	 *            description of the picture.
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Sets the thumbnail.
	 * 
	 * @param thumbnail
	 *            indicator.
	 */
	public void setThumbnail(boolean thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return list of tags connected to this picture.
	 */
	public List<String> getTagsAsList() {
		return tags;
	}

}
