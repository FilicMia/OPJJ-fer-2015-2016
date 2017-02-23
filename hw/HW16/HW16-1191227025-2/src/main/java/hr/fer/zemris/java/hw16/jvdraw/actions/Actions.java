package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.Treci;
import hr.fer.zemris.java.hw16.jvdraw.Util;

/**
 * Class holding actions for {@link JDrawFrame}. Action provided are Open, Save,
 * SaveAs.
 * 
 * @author mia
 *
 */
public class Actions {

	/** JVDrawFrame used for reproducing actions. */
	private Treci jvdFrame;

	/**
	 * Action that saves the certain Document.
	 */
	private Action saveDocumentAction = new AbstractAction() {

		/**
		 * Version ID included in hash and equal function.
		 */
		private static final long serialVersionUID = -5609050084189535991L;

		{
			putValue(NAME, "Save");
			putValue(SHORT_DESCRIPTION,
					"Saves the curent canvas on new choosen destination in jvd form");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Util.saveAction(jvdFrame);
		}

	};

	/**
	 * Action that saves as the curent canvas on new choosen destination in jvd
	 * form.
	 */
	private Action saveDocumentAsAction = new AbstractAction() {

		/**
		 * Version ID.
		 */
		private static final long serialVersionUID = -672940025581578256L;

		{
			putValue(NAME, "Save as");
			putValue(SHORT_DESCRIPTION,
					"Saves as the curent canvas on new choosen destination in jvd form");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			jvdFrame.getDrawingCanvas().getEnv().setDocumentPath(null);
			Util.saveAction(jvdFrame);

		}
	};
	/**
	 * Action opens existing file form disk.
	 */
	private Action openDocumentAction = new AbstractAction() {

		/**
		 * Version ID.
		 */
		private static final long serialVersionUID = 2471627873043442386L;

		{
			putValue(NAME, "Open");
			putValue(SHORT_DESCRIPTION, "Opens existing file form disk.");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Util.openChoosedDocument(jvdFrame);

		}
	};

	/**
	 * Action exports the JVDrawFrame into selected picture format.
	 */
	private Action exportDocumentAction = new AbstractAction() {

		/**
		 * Version ID.
		 */
		private static final long serialVersionUID = 2471627873043442386L;

		{
			putValue(NAME, "Export");
			putValue(SHORT_DESCRIPTION,
					"Exports the JVDrawFrame into selected picture format.");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Util.exportAction(jvdFrame);
			} catch (IOException e1) {
				JOptionPane.showConfirmDialog(jvdFrame, JOptionPane.ERROR_MESSAGE);
			}

		}
	};

	/**
	 * Constructor.
	 * 
	 * @param jvdFrame
	 *            drawing jvdFrame used for reproducing actions.
	 */
	public Actions(Treci jvdFrame) {
		this.jvdFrame = jvdFrame;
	}

	/**
	 * @return action that saves the canvas data in jvd from on disk.
	 */
	public Action getSaveDocumentAction() {
		return saveDocumentAction;
	}

	/**
	 * @return action that saves as the canvas data in jvd from on disk.
	 */
	public Action getSaveDocumentAsAction() {
		return saveDocumentAsAction;
	}

	/**
	 * @return action that opens the jvd file from disk on canvas.
	 */
	public Action getOpenDocumentAction() {
		return openDocumentAction;
	}

	/**
	 * @return action which exports the JVDrawFrame into selected picture
	 *         format.
	 */
	public Action getExportDocumentAction() {
		return exportDocumentAction;
	}

}
