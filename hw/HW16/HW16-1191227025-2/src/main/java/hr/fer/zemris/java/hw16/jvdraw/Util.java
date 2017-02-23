package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.central.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.central.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.Circle;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.FCircle;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.central.shapes.listModel.Line;

/**
 * Utility function for handling the documents/tabs opened in some
 * {@link JNotepadppFrame}.
 * 
 * @author mia
 *
 */
public class Util {

	/**
	 * Java file chooser used for selecting of document and its paths.
	 */
	private static JFileChooser jfc = new JFileChooser() {
		/**
		 * Version ID.
		 */
		private static final long serialVersionUID = 4537434676287558951L;

		@Override
		public void approveSelection() {
			File f = getSelectedFile();
			if (f.exists() && getDialogType() == SAVE_DIALOG) {
				int result = JOptionPane.showConfirmDialog(this,
						"The file exists, overwrite?", "System message: ",
						JOptionPane.YES_NO_CANCEL_OPTION);
				switch (result) {
				case JOptionPane.YES_OPTION:
					super.approveSelection();
					return;
				case JOptionPane.NO_OPTION:
					return;
				case JOptionPane.CLOSED_OPTION:
					return;
				case JOptionPane.CANCEL_OPTION:
					cancelSelection();
					return;
				}
			}
			super.approveSelection();
		}
	};

	static {
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JVDraw files", "jvd");
		jfc.setFileFilter(filter);
	}

	/**
	 * Saving the content of {@code JDrawingCanvas} in document specified with
	 * path {@code JDrawingCanvasEnviroment#getDocumentPath()}.
	 * 
	 * @param jvdFrame
	 *            {@link Treci} that should be saved in *.jdv format.
	 * 
	 */
	public static void saveAction(Treci jvdFrame) {
		Path path = jvdFrame.getDrawingCanvas().getEnv().getDocumentPath();

		if (path == null) {
			jvdFrame.getDrawingCanvas().getEnv()
					.setDocumentPath(selectSavePath(jvdFrame, "Select"));
			path = jvdFrame.getDrawingCanvas().getEnv().getDocumentPath();
		}

		byte[] podatci = (jvdFrame.getDrawingCanvas().getJVS()).getBytes();
		if (path == null) {
			JOptionPane.showMessageDialog(jvdFrame,
					"Destination has not been choosen!");
			return;
		} else {
			if (!path.toFile().getName().toLowerCase().endsWith(".jvd")) {
				path = path.resolveSibling(path.getFileName() + ".jvd");
			}
		}

		try {
			Files.write(path, podatci);

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(jvdFrame, "Error!");
			return;

		}

		jvdFrame.getDrawingCanvas().getEnv().setSaved(true);
		JOptionPane.showMessageDialog(jvdFrame, "Data has been saved!", "Info",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method exporting the objects drawn on {@code jvdFrame} in selected
	 * picture format. Avaliabe formats are jpg, png, gif.
	 * 
	 * @param jvdFrame
	 *            whose content will be exported.
	 * @throws IOException
	 *             if exporting as image collapses
	 */
	public static void exportAction(Treci jvdFrame) throws IOException {
		JDrawingCanvas canvas = jvdFrame.getDrawingCanvas();
		DrawingModel drawingModel = canvas.getDrawingModel();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Exporting files", "png jpg gif");
		jfc.setFileFilter(filter);

		Path path = selectSavePath(jvdFrame,
				"Select picture format path file.");
		String pathName = path.toFile().getName().toLowerCase();
		int index = pathName.lastIndexOf('.');

		String extension = pathName.substring(
				index == -1 ? pathName.length() : index + 1, pathName.length());
		System.out.println(extension);

		if (!extension.equals("png") && !extension.equals("jpg")
				&& !extension.equals("gif")) {
			path = path.resolveSibling(path.getFileName() + ".png");
			extension = "png";
		}

		int count = drawingModel.getSize();
		int upperX = canvas.getBounds().width;
		int upperY = canvas.getBounds().height;
		int lowerX = 0;
		int lowerY = 0;
		GeometricalObject object;

		for (int i = 0; i < count; ++i) {
			object = drawingModel.getObject(i);

			upperX = Math.min(object.getX(), upperX);
			upperY = Math.min(object.getY(), upperY);
			lowerX = Math.max(object.getWidth() + object.getX(), lowerX);
			lowerY = Math.max((int) object.getHeight() + object.getY(), lowerY);

		}

		BufferedImage image = new BufferedImage(lowerX - upperX,
				lowerY - upperY, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		for (int i = 0; i < count; ++i) {
			drawingModel.getObject(i).draw(g, upperX, upperY);
		}
		g.dispose();
		File file = path.toFile();
		ImageIO.write(image, extension, file);
		JOptionPane.showMessageDialog(jvdFrame, "Data has been exported!",
				"Info", JOptionPane.INFORMATION_MESSAGE);

		jfc.removeChoosableFileFilter(filter);

	}

	/**
	 * Opens an existing file in {@code canvas} using {@link JFileChooser}. The
	 * file must be in jvd format. The drawing with geometrical objects is shown
	 * on {@code JVDrawFrame}.
	 * 
	 * @param jvdFrame
	 *            instance of {@link Treci} where the file will be opened
	 * 
	 */
	public static void openChoosedDocument(Treci jvdFrame) {

		jfc.setDialogTitle("Open document");

		if (jfc.showOpenDialog(jvdFrame) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = jfc.getSelectedFile();
		Path filePathe = fileName.toPath();

		if (!Files.isReadable(filePathe)
				|| !fileName.getName().toLowerCase().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(jvdFrame, "File can not be read",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		byte[] okteti;
		try {
			okteti = Files.readAllBytes(filePathe);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(jvdFrame, "Error");
			return;
		}

		String text = new String(okteti, StandardCharsets.UTF_8);
		parseJVDFile(text, jvdFrame);
		jvdFrame.getDrawingCanvas().getEnv().setSaved(true);
	}

	/**
	 * Method loads given text to {@link Treci}.
	 * 
	 * @param text
	 *            file to be loaded
	 * @param jvdFrame
	 *            frame on which the file will be loaded.
	 */
	private static void parseJVDFile(String text, Treci jvdFrame) {
		JDrawingCanvas canvas = jvdFrame.getDrawingCanvas();
		saveIfUnsaved(jvdFrame);

		canvas.getDrawingModel().removeAllObjects();
		String[] lines = text.split("(\\r?\\n)+");
		int count = lines.length;
		for (int i = 0; i < count; ++i) {
			GeometricalObject object;

			String items[] = lines[i].split("\\s+");
			switch (items[0]) {
			case "LINE":
				object = new Line();
				break;
			case "CIRCLE":
				object = new Circle();
				break;
			case "FCIRCLE":
				object = new FCircle();
				break;

			default:
				throw new IllegalArgumentException(
						"Invalid jvd form " + lines[i]);
			}

			object.fromJVD(lines[i]);
			canvas.getDrawingModel().add(object);

		}

	}

	/**
	 * Process of selecting a path where to save the file.
	 * 
	 * @param jvDrawFrame
	 *            {@link Treci}
	 * @param title
	 *            title of the box.
	 * @return the path selected
	 */
	public static Path selectSavePath(Treci jvDrawFrame, String title) {
		jfc.setDialogTitle(title);

		if (jfc.showSaveDialog(jvDrawFrame) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return jfc.getSelectedFile().toPath();
	}

	/**
	 * Saves the canvas data if it hasn't been saved.
	 * 
	 * @param jvdFrame
	 *            frame whose canvas will be checked
	 * 
	 */
	public static void saveIfUnsaved(Treci jvdFrame) {
		JDrawingCanvas canvas = jvdFrame.getDrawingCanvas();

		if (!canvas.getEnv().isSaved()) {
			int decision = JOptionPane.showConfirmDialog(canvas,
					"Do you want to save changes for currnet document?",
					"System message", JOptionPane.YES_NO_OPTION);
			if (decision == JOptionPane.YES_OPTION) {
				saveAction(jvdFrame);
			}
		}
	}

	/**
	 * Gets the number from JOptionInput. If no number is provided, method shows
	 * message to the user and returns -1.
	 * 
	 * @param descOfWantedInteger
	 *            Description of integer wanted.
	 * @return the integer provided, if no integer is provided, returnes -1.
	 */
	public static int getInputInteger(String descOfWantedInteger) {
		Object selected;
		String recivedInput;

		selected = JOptionPane.showInputDialog(descOfWantedInteger);
		if (selected != null) {
			recivedInput = (String) selected;
			if (recivedInput.matches(GeometricalObject.INTEGER)) {

				return Integer.parseInt((String) selected);
			} else {
				JOptionPane.showMessageDialog(null, recivedInput
						+ " is not an integer number. Changes for this property are discarded.");
			}
		}

		return -1;

	}

	/**
	 * Gets the number from {@code integer}. If no number is provided, method
	 * shows message to the user and returns -1.
	 * 
	 * @param integer
	 *            string representation of an integer.
	 * @return the integer provided, if no integer is provided, returnes -1.
	 */
	public static int getInteger(String integer) {
		if (integer.trim().matches(GeometricalObject.INTEGER)) {

			return Integer.parseInt((String) integer);
		} 

		return -1;

	}

}
