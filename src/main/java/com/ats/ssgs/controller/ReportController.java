package com.ats.ssgs.controller;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.common.ExportToExcel;
import com.ats.ssgs.model.master.GetWeighing;
import com.ats.ssgs.model.mat.Contractor;
import com.ats.ssgs.model.mat.GetMatIssueDetail;
import com.ats.ssgs.model.mat.GetMatIssueHeader;
import com.ats.ssgs.model.mat.GetVehDetail;
import com.ats.ssgs.model.mat.GetVehHeader;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@Scope("session")
public class ReportController {
	RestTemplate rest = new RestTemplate();
	int isError = 0;
	List<Contractor> conList;

	@RequestMapping(value = "/showContractReport", method = RequestMethod.GET)
	public ModelAndView showContractReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/contrareport");
			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			model.addObject("title", "Contractorwise Report");

		} catch (Exception e) {

			System.err.println("exception In showContraReport at Txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<GetMatIssueHeader> getMatList = new ArrayList<>();

	@RequestMapping(value = "/getContraReportBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetMatIssueHeader> getContraReportBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getContraReportBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetMatIssueHeader[] ordHeadArray = rest.postForObject(Constants.url + "getContractorBetweenDate", map,
				GetMatIssueHeader[].class);
		getMatList = new ArrayList<GetMatIssueHeader>(Arrays.asList(ordHeadArray));
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Date");
		rowData.add("Contractor Name");
		rowData.add("Issue No");

		rowData.add("Total");
		rowData.add("Total Quantity");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < getMatList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));
			rowData.add("" + getMatList.get(i).getDate());
			rowData.add("" + getMatList.get(i).getContrName());
			rowData.add("" + getMatList.get(i).getIssueNo());

			rowData.add("" + getMatList.get(i).getTotal());
			rowData.add("" + getMatList.get(i).getQtyTotal());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetMatIssueHeader");

		return getMatList;
	}

	GetMatIssueHeader editMat;
	List<GetWeighing> weighing;

	@RequestMapping(value = "/contractorDetailReport/{matHeaderId}/{contrId}", method = RequestMethod.GET)
	public ModelAndView contractorDetailReport(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int matHeaderId, @PathVariable int contrId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/contradetreport");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matHeaderId", matHeaderId);
			editMat = rest.postForObject(Constants.url + "getMatIssueContrByHeaderId", map, GetMatIssueHeader.class);
			model.addObject("title", "Contractorwise Report");
			model.addObject("editMat", editMat);
			model.addObject("editMatDetail", editMat.getMatIssueDetailList());

			map = new LinkedMultiValueMap<String, Object>();

			map.add("contrId", contrId);

			GetWeighing[] weighingArray = rest.postForObject(Constants.url + "getWeighByContraId", map,
					GetWeighing[].class);
			weighing = new ArrayList<GetWeighing>(Arrays.asList(weighingArray));

			model.addObject("weighing", weighing);

			System.out.println("=========" + weighing.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("Item Name");
			rowData.add("Measurement of unit");
			rowData.add("Item Rate");

			rowData.add("Item Quantity");
			rowData.add("Total Value");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int cnt = 1;
			for (int i = 0; i < editMat.getMatIssueDetailList().size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				cnt = cnt + i;
				rowData.add("" + (i + 1));
				rowData.add("" + editMat.getMatIssueDetailList().get(i).getItemCode());
				rowData.add("" + editMat.getMatIssueDetailList().get(i).getUomName());
				rowData.add("" + editMat.getMatIssueDetailList().get(i).getItemRate());
				rowData.add("" + editMat.getMatIssueDetailList().get(i).getQuantity());
				rowData.add("" + editMat.getMatIssueDetailList().get(i).getValue());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "GetMatIssueHeader");

		} catch (Exception e) {
			System.err.println("exception In contractorDetailReport at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/showContractorwisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showContractorwisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showContractorwisePdf");
		Document document = new Document(PageSize.A4);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(6);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 4.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Contractor Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Issue No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Quantity", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetMatIssueHeader work : getMatList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getContrName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIssueNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getQtyTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Contractorwise Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			Paragraph p1 = new Paragraph("From Date:" + fromDate + "  To Date:" + toDate, headFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(new Paragraph("\n"));
			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}

	@RequestMapping(value = "/showContraDetailPdf", method = RequestMethod.GET)
	public void showContraDetailPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showContraDetailPdf");
		Document document = new Document(PageSize.A4);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(6);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 4.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Measurement of Unit", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Rate", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Quantity", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Value", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetMatIssueDetail work : editMat.getMatIssueDetailList()) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getUomName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemRate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getQuantity(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getValue(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			PdfPTable table1 = new PdfPTable(6);
			try {
				System.out.println("Inside PDF Table1 try");
				table1.setWidthPercentage(100);
				table1.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 4.2f, 3.2f });
				Font headFontFirst = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
				Font headFont1Second = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
				headFont1Second.setColor(BaseColor.WHITE);
				// Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE,
				// BaseColor.BLUE);

				PdfPCell hcell1 = new PdfPCell();
				hcell1.setBackgroundColor(BaseColor.PINK);

				hcell1.setPadding(3);
				hcell1 = new PdfPCell(new Phrase("Sr.No.", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Vehicle Name", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Vehicle No", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Poklen Name", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Poklen No", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Quantity", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				int index1 = 0;
				for (GetWeighing work : weighing) {
					index1++;
					PdfPCell cell;

					cell = new PdfPCell(new Phrase(String.valueOf(index1), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPadding(3);
					cell.setPaddingRight(2);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getVehicleName(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getVehicleNo(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getPokeName(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase(work.getPokeNo(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getQuantity(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Contractorwise Item Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			Paragraph p1 = new Paragraph(
					"Contractor Name: " + editMat.getContrName() + "   Issue No: " + editMat.getIssueNo(), headFont);

			p1.setAlignment(Element.ALIGN_LEFT);
			document.add(p1);

			document.add(new Paragraph("\n"));
			document.add(table);
			document.add(new Paragraph(" "));
			document.add(table1);
			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}

			}
		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/showVehicleReport", method = RequestMethod.GET)
	public ModelAndView showVehicleReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/vehreport");
			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			model.addObject("title", "Vehiclewise Report");

		} catch (Exception e) {

			System.err.println("exception In showVehicleReport at Txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<GetVehHeader> getVehList = new ArrayList<>();

	@RequestMapping(value = "/getVehicleReportBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetVehHeader> getVehicleReportBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getVehicleReportBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetVehHeader[] ordHeadArray = rest.postForObject(Constants.url + "getVehicleBetweenDate", map,
				GetVehHeader[].class);
		getVehList = new ArrayList<GetVehHeader>(Arrays.asList(ordHeadArray));

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Date");
		rowData.add("Vehicle Name");
		rowData.add("Vehicle No");

		rowData.add("Total");
		rowData.add("Total Quantity");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < getVehList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));
			rowData.add("" + getVehList.get(i).getDate());
			rowData.add("" + getVehList.get(i).getVehicleName());
			rowData.add("" + getVehList.get(i).getVehNo());

			rowData.add("" + getVehList.get(i).getVehTotal());
			rowData.add("" + getVehList.get(i).getVehQtyTotal());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetVehHeader");

		return getVehList;
	}

	GetVehHeader editVeh;

	@RequestMapping(value = "/vehilceDetailReport/{matVehHeaderId}/{vehId}", method = RequestMethod.GET)
	public ModelAndView vehilceDetailReport(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int matVehHeaderId, @PathVariable int vehId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/vehdetreport");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matVehHeaderId", matVehHeaderId);

			editVeh = rest.postForObject(Constants.url + "getMatIssueVehicleByHeaderId", map, GetVehHeader.class);
			model.addObject("title", "Vehicle Report");
			model.addObject("editVeh", editVeh);
			model.addObject("editVehDetail", editVeh.getVehDetailList());

			map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleId", vehId);

			GetWeighing[] weighingArray = rest.postForObject(Constants.url + "getWeighByVehicleId", map,
					GetWeighing[].class);
			weighing = new ArrayList<GetWeighing>(Arrays.asList(weighingArray));

			model.addObject("weighing", weighing);

		} catch (Exception e) {
			System.err.println("exception In vehilceDetailReport at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/showVehwisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showVehwisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showVehwisePdf");
		Document document = new Document(PageSize.A4);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(7);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 4.2f, 3.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Vehicle Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Vehicle No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Quantity", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Reading", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetVehHeader work : getVehList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getVehicleName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getVehNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getVehTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getVehQtyTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getReading(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}

			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Vehiclewise Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			Paragraph p1 = new Paragraph("From Date:" + fromDate + "  To Date:" + toDate, headFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(new Paragraph("\n"));
			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}

	@RequestMapping(value = "/showVehDetailPdf", method = RequestMethod.GET)
	public void showVehDetailPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showVehDetailPdf");
		Document document = new Document(PageSize.A4);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(6);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 4.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Measurement of Unit", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Rate", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Quantity", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Value", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetVehDetail work : editVeh.getVehDetailList()) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getUomName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getRate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getQuantity(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getValue(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}

			PdfPTable table1 = new PdfPTable(6);
			try {
				System.out.println("Inside PDF Table1 try");
				table1.setWidthPercentage(100);
				table1.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 4.2f, 3.2f });
				Font headFontFirst = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
				Font headFont1Second = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
				headFont1Second.setColor(BaseColor.WHITE);
				// Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE,
				// BaseColor.BLUE);

				PdfPCell hcell1 = new PdfPCell();
				hcell1.setBackgroundColor(BaseColor.PINK);

				hcell1.setPadding(3);
				hcell1 = new PdfPCell(new Phrase("Sr.No.", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Vehicle Name", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Vehicle No", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Poklen Name", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Poklen No", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				hcell1 = new PdfPCell(new Phrase("Quantity", headFont1Second));
				hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell1.setBackgroundColor(BaseColor.PINK);

				table1.addCell(hcell1);

				int index1 = 0;
				for (GetWeighing work : weighing) {
					index1++;
					PdfPCell cell;

					cell = new PdfPCell(new Phrase(String.valueOf(index1), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPadding(3);
					cell.setPaddingRight(2);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getVehicleName(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getVehicleNo(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getPokeName(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase(work.getPokeNo(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getQuantity(), headFontFirst));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table1.addCell(cell);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Vehiclewise Item Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			Paragraph p1 = new Paragraph(
					"Vehicle Name: " + editVeh.getVehicleName() + "   Vehicle No: " + editVeh.getVehNo(), headFont);

			p1.setAlignment(Element.ALIGN_LEFT);
			document.add(p1);

			document.add(new Paragraph("\n"));
			document.add(table);
			document.add(new Paragraph(" "));
			document.add(table1);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}

}
