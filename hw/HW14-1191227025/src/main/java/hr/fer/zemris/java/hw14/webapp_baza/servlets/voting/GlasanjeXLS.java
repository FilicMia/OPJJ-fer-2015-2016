package hr.fer.zemris.java.hw14.webapp_baza.servlets.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.webapp_baza.application.CreateExcelFile;
import hr.fer.zemris.java.hw14.webapp_baza.application.VotingUtil;

/**
 * Creates XLS format of voting results.
 * First column contains names of the band.
 * Second column contains results of voting corresponding to each band.
 * 
 * @author mia
 *
 */
@WebServlet(urlPatterns = { "/glasanje-xls" })
public class GlasanjeXLS extends HttpServlet {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 8265579333075070081L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HSSFWorkbook hfw = CreateExcelFile.createVoteResults(req, resp,
				VotingUtil.currentResults(req, resp));
		CreateExcelFile.write(req, resp, hfw);

	}
}
