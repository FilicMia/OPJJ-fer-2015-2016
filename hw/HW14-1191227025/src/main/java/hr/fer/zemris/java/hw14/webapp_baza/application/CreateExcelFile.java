package hr.fer.zemris.java.hw14.webapp_baza.application;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.tecaj_13.model.VotingOption;

import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Class implementing functions for dynamic creation of Microsoft Excel
 * documents.
 */
public class CreateExcelFile {

	/**
	 * Creates the Microsoft Excel document with n pages. On page i there is a
	 * table with two columns. The first column contains integer numbers from a
	 * to b. The second column contains i-th powers of these numbers.
	 * 
	 * @param a
	 *            lower limit of numbers that will be raised on the power
	 * @param b
	 *            upper limit of numbers that will be raised on the power
	 * @param n
	 *            powers that will be calculated are 1-n
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @return created excel workbook
	 * @throws IOException
	 *             if sending an error message collapses
	 * @throws ServletException
	 *             if sending an error message collapses
	 */
	public static HSSFWorkbook createPowers(HttpServletRequest req,
			HttpServletResponse resp, int a, int b, int n)
			throws ServletException, IOException {
		HSSFWorkbook hwb = null;

		try {
			hwb = new HSSFWorkbook();
			for (int power = 1; power <= n; ++power) {
				HSSFSheet sheet = hwb.createSheet("on the power of " + power);
				for (int number = a; number <= b; ++number) {
					HSSFRow rowhead = sheet.createRow(number - a);
					rowhead.createCell(0).setCellValue(number);
					rowhead.createCell(1).setCellValue(Math.pow(number, power));
				}
			}

		} catch (Exception ex) {
			ErrorMessage.send(req, resp,
					"Unable to create excel data to the output stream");

		}
		return hwb;
	}

	/**
	 * Method creates xml files from passed Bands. Each roe has 2 columns
	 * filled. First cell in each row is name of the band and the second is
	 * number of votes.
	 * 
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @param report
	 *            report containing bends available for voting
	 * @return created excel workbook
	 * @throws IOException
	 *             if sending an error message collapses
	 * @throws ServletException
	 *             if sending an error message collapses
	 */
	public static HSSFWorkbook createVoteResults(HttpServletRequest req,
			HttpServletResponse resp, List<VotingOption> report)
			throws ServletException, IOException {
		HSSFWorkbook hwb = null;

		try {
			hwb = new HSSFWorkbook();

			HSSFSheet sheet = hwb.createSheet("Voting results");
			int len = report.size();
			for (int i = 0; i < len; ++i) {
				HSSFRow rowhead = sheet.createRow(i);
				rowhead.createCell(0).setCellValue(report.get(i).getOptionTitle());
				rowhead.createCell(1).setCellValue(report.get(i).getVotesCount());
			}

		} catch (Exception ex) {
			ErrorMessage.send(req, resp,
					"Unable to create excel data to the output stream");

		}
		return hwb;
	}
	
	/**
	 * Writes the Microsoft Excel document to the output stream provided by
	 * {@code resp}.
	 * 
	 * @param req
	 *            the origin of the request.
	 * @param resp
	 *            the response servlet connected to the {@code req} servlet
	 * @param workBook
	 *            exel file that should be send to the client
	 * 
	 * @throws IOException
	 *             if sending an error message collapses
	 * @throws ServletException
	 *             if sending an error message collapses
	 */
	public synchronized static void write(HttpServletRequest req, HttpServletResponse resp,
			HSSFWorkbook workBook) throws ServletException, IOException {

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			workBook.write(byteOut);
			byte[] outByteArray = byteOut.toByteArray();

			OutputStream out = resp.getOutputStream();
			resp.setContentType("application/vnd.ms-excel");
			resp.setContentLength(outByteArray.length);
			resp.setHeader("Expires:", "0");
			resp.setHeader("Content-Disposition",
					"attachment;filename=poll.xls");

			out.write(outByteArray);
			out.flush();
		} catch (Exception e) {
			ErrorMessage.send(req, resp,
					"Unable to write excel data to the output stream");
		}
	}

}