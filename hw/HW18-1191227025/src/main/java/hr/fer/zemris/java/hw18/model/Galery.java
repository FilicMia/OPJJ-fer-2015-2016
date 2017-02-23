/**
 * 
 */
package hr.fer.zemris.java.hw18.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Galery of pictures holding all available pictures as the list. The list of
 * pictures is created on construction of this galery. The thumbnail of each
 * picture i s created on demand and saved in {@link Galery#THUMBNAIL_FOLDER}
 * for later use.
 * 
 * @author mia
 *
 */
public class Galery {
	/** Relative path to the folder holding original pictures. */
	public static final String PICTURE_FOLDER = "WEB-INF/slike";
	/** Relative path to the folder holding thumbnail's of pictures. */
	public static final String THUMBNAIL_FOLDER = "WEB-INF/thumbnails";
	/** Name of the file holding descriptive information on each picture. */
	public static final String DESCRIPTOR = "opisnik.txt";
	/** Thumbneil width. */
	public static final int THUMBNAIL_WIDTH = 150;
	/** Thumbneil heigth. */
	public static final int THUMBNAIL_HEIGTH = 150;

	/** Map holding all available pictures. */
	private static Map<String, Picture> pictures;

	/**
	 * Map holding all pictures mapped to its tags.
	 */
	private static Map<String, List<Picture>> tagPictureMap;

	/**
	 * Creating the thumbnail of the picture with provided name.
	 * 
	 * @param name
	 *            name of the picture
	 * @return picture with certain name
	 * @throws IOException
	 *             if reading the picture description went wrong.
	 */
	public static Picture getPicture(String name) throws IOException {
		if (pictures == null) {
			loadDescriptions(Paths.get(DESCRIPTOR));
		}
		return pictures.get(name);

	}

	/**
	 * Loading decsription of the pictures stored in {@link #DESCRIPTOR}.
	 * 
	 * @param realPath
	 *            real path to {@link #DESCRIPTOR}
	 * 
	 * @throws IOException
	 *             if loading descriptor form file broke
	 * 
	 */
	public static void loadDescriptions(Path realPath) throws IOException {
		if (pictures == null) {
			pictures = new HashMap<>();
			tagPictureMap = new HashMap<>();
		}

		if (!Files.isRegularFile(realPath) || !Files.isReadable(realPath)) {
			System.err
					.println("File can not be read: " + realPath.getFileName());
			System.exit(-1);
		}

		List<String> lines = Files.readAllLines(realPath,
				StandardCharsets.UTF_8);

		int len = lines.size();
		String name = null;
		List<String> tagsList = null;
		String desc = null;

		int indicator = 0;
		for (int i = 0; i < len; ++i) {
			if (indicator % 3 == 0 && lines.get(i).isEmpty()) {// name has to be
																// set
				continue;
			}

			switch (indicator % 3) {
			case 0:
				name = lines.get(i).trim();
				break;
			case 1:
				desc = lines.get(i).trim();
				break;
			case 2:

				if (!lines.get(i).isEmpty()) {
					String[] tags = lines.get(i).trim().split(",");
					tagsList = Arrays.asList(tags);
				} else {
					tagsList = new ArrayList<>();
				}

				Picture picture = new Picture(name, tagsList, desc);

				pictures.put(name, picture);
				addToTags(picture);

				break;

			default:
				break;
			}
			indicator++;
		}

	}

	/**
	 * Adds the picture to its tags.
	 * 
	 * @param picture
	 *            {@link Picture} to be add to its tags
	 */
	private static void addToTags(Picture picture) {
		List<String> tagsList = picture.getTagsAsList();

		tagsList.forEach(tag -> {
			List<Picture> pics = tagPictureMap.getOrDefault(tag.trim(),
					new ArrayList<>());
			pics.add(picture);
			tagPictureMap.put(tag.trim(), pics);
		});
	}

	/**
	 * Fetches the list of all tags of the gallery.
	 * 
	 * @return all tags of gallery.
	 */
	public static List<String> getAllTags() {
		return new ArrayList<>(tagPictureMap.keySet());
	}

	/**
	 * Fetches the pictures added to certain tag.
	 * 
	 * @param tag
	 *            tag whose pictures will be fetched.
	 * @return list of pictures of certain tag
	 */
	public static List<Picture> getPictures(String tag) {
		return tagPictureMap.getOrDefault(tag, new ArrayList<>());

	}

}
