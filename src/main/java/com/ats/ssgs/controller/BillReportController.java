package com.ats.ssgs.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import com.ats.ssgs.model.GetBillReport;
import com.ats.ssgs.model.GetDailySalesReport;
import com.ats.ssgs.model.GetDateWiseDetailBill;
import com.ats.ssgs.model.GetDatewiseReport;
import com.ats.ssgs.model.GetItenwiseBillReport;
import com.ats.ssgs.model.GetPoChallan;
import com.ats.ssgs.model.GetPoReport;
import com.ats.ssgs.model.MonthWiseBill;
import com.ats.ssgs.model.PoChallanDetails;
import com.ats.ssgs.model.TaxSummery;
import com.ats.ssgs.model.TaxWiseBill;
import com.ats.ssgs.model.chalan.GetChalanDetail;
import com.ats.ssgs.model.chalan.GetChalanHeader;
import com.ats.ssgs.model.master.Company;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Setting;
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
public class BillReportController {

	RestTemplate rest = new RestTemplate();

	List<Company> compList;
	List<Plant> plantList;
	List<Cust> custList;
	List<Item> itemList;
	List<MonthWiseBill> monthList;

	@RequestMapping(value = "/showPOReport", method = RequestMethod.GET)
	public ModelAndView showPOReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/poreport");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

			Calendar cal = Calendar.getInstance();

			Calendar cal1 = Calendar.getInstance();
			cal.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), 1);

			String firstDate = sdf.format(cal.getTimeInMillis());
			String endDate = sdf.format(cal.getTimeInMillis());
			System.out.println("sd " + firstDate);
			System.out.println("ed " + endDate);
			model.addObject("fromDate", DateConvertor.convertToDMY(firstDate));
			model.addObject("toDate", DateConvertor.convertToDMY(endDate));

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			model.addObject("title", "PO Report");

		} catch (Exception e) {

			System.err.println("exception In showPOReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<GetPoReport> poReportList;

	@RequestMapping(value = "/getPOReportBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetPoReport> getPOReportBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int type = Integer.parseInt(request.getParameter("type"));

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		map.add("plantId", plantId);
		map.add("type", type);

		GetPoReport[] poReportArray = rest.postForObject(Constants.url + "getPoReportBetDateAndType", map,
				GetPoReport[].class);
		poReportList = new ArrayList<GetPoReport>(Arrays.asList(poReportArray));

		System.out.println("LIST : -----------------------" + poReportList);

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("PO Date");
		rowData.add("PO NO");
		rowData.add("Item");
		rowData.add("PO Remaining Qty");
		rowData.add("Customer Name");
		rowData.add("Customer Mobile");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < poReportList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + poReportList.get(i).getPoDate());

			rowData.add("" + poReportList.get(i).getPoNo());
			rowData.add("" + poReportList.get(i).getItemName());
			rowData.add("" + poReportList.get(i).getPoRemainingQty());
			rowData.add("" + poReportList.get(i).getCustName());
			rowData.add("" + poReportList.get(i).getCustMobNo());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetPOReport");

		return poReportList;
	}

	@RequestMapping(value = "/showPOReportPdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showPOReportPdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showPOReportPdf");
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
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("PO No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("PO Date ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Remaining Qty ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Customer Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("CMobile No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (GetPoReport work : poReportList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getPoNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getPoDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getPoRemainingQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustMobNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("PO Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			Paragraph p1 = new Paragraph("From Date:" + fromDate + "  To Date:" + toDate, headFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			/*
			 * document.add(p1); document.add(new Paragraph("\n"));
			 */
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

	@RequestMapping(value = "/showDatewiseBillReport", method = RequestMethod.GET)
	public ModelAndView showDatewiseBillReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/datewisebillreport");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			
			Cust[] custArray = rest.getForObject(Constants.url + "getAllCustList", Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			
			model.addObject("custList", custList);

			model.addObject("title", "Datewise Bill Report");

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showDatewiseBillReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/showDatewiseBillReport1/{monthNo}/{year}/{plantId}/{custId}", method = RequestMethod.GET)
	public ModelAndView showDatewiseBillReport1(@PathVariable("monthNo") int monthNo, @PathVariable("year") int year,
			@PathVariable("plantId") int plantId, @PathVariable("custId") int custId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/datewisebillreport");
			model.addObject("title", "Datewise Bill Report");

			Calendar cal = Calendar.getInstance();
			cal.set(year, monthNo - 1, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String firstDate = sdf.format(cal.getTimeInMillis());
			cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
			String endDate = sdf.format(cal.getTimeInMillis());

			model.addObject("fromDate", firstDate);
			model.addObject("toDate", endDate);

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Cust[] custArray = rest.getForObject(Constants.url + "getAllCustList", Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			model.addObject("custList", custList);

			model.addObject("plantId", plantId);
			model.addObject("custId", custId);

		} catch (Exception e) {

			System.err.println("exception In showDatewiseBillReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	// Ajax call//
	@RequestMapping(value = "/getCustomerByPlantId", method = RequestMethod.GET)
	public @ResponseBody List<Cust> getCustomerByPlantId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));

		map.add("plantId", plantId);

		Cust[] plantArray = rest.postForObject(Constants.url + "getCustListByPlantId", map, Cust[].class);
		custList = new ArrayList<Cust>(Arrays.asList(plantArray));

		System.err.println("Ajax custList List " + custList.toString());

		return custList;

	}

	List<GetDatewiseReport> dateBillList;

	@RequestMapping(value = "/getDatewiseBillList", method = RequestMethod.GET)
	public @ResponseBody List<GetDatewiseReport> getDatewiseBillList(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getDatewiseBillList");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String custId = request.getParameter("custId");
		String plantId = request.getParameter("plantId");

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		
		
		map.add("plantId", plantId);
		map.add("custId", custId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetDatewiseReport[] ordHeadArray = rest.postForObject(Constants.url + "getDatewiseBillReport", map,
				GetDatewiseReport[].class);
		dateBillList = new ArrayList<GetDatewiseReport>(Arrays.asList(ordHeadArray));
		
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Bill Date");
		rowData.add("Taxable Amt");
		rowData.add("CGST");
		rowData.add("IGST");
		rowData.add("SGST");
		rowData.add("Tax Amount");
		rowData.add("TCS Amount");
		rowData.add("Total Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < dateBillList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + dateBillList.get(i).getBillDate());
			rowData.add("" + dateBillList.get(i).getTaxableAmt());
			rowData.add("" + dateBillList.get(i).getCgstAmt());
			rowData.add("" + dateBillList.get(i).getIgstAmt());
			rowData.add("" + dateBillList.get(i).getSgstAmt());
			rowData.add("" + dateBillList.get(i).getTaxAmt());
			rowData.add("" + dateBillList.get(i).getTcsAmt());
			rowData.add("" + dateBillList.get(i).getGrandTotal());
			
			ttlTaxable = ttlTaxable + dateBillList.get(i).getTaxableAmt();
			ttlCgst = ttlCgst + dateBillList.get(i).getCgstAmt();
			ttlSgst = ttlSgst + dateBillList.get(i).getSgstAmt();
			ttlTax = ttlTax + dateBillList.get(i).getTaxAmt();
			ttlGrand = ttlGrand + dateBillList.get(i).getGrandTotal();
			ttlTcs = ttlTcs + roundUp(dateBillList.get(i).getTcsAmt());
			ttlIgst = ttlIgst + dateBillList.get(i).getIgstAmt();

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}
		
		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();
		rowData.add("");
		rowData.add("Total");
		rowData.add("" + roundUp(ttlTaxable));	
		rowData.add("" + roundUp(ttlCgst));
		rowData.add("" + roundUp(ttlIgst));
		rowData.add("" + roundUp(ttlSgst));
		rowData.add("" + roundUp(ttlTax));
		rowData.add("" + roundUp(ttlTcs));	
		rowData.add("" + roundUp(ttlGrand));
		
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetDateWiseBillReport");

		return dateBillList;
	}

	// pp

	@RequestMapping(value = "/showDateWisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showDateWisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showDatewisePdf");
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
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;

		PdfPTable table = new PdfPTable(9);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("Bill Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("SGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("IGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tax Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("TCS Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			int index = 0;
			for (GetDatewiseReport work : dateBillList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getBillDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + work.getTcsAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getGrandTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell); 
				
				ttlTaxable = ttlTaxable + work.getTaxableAmt();
				ttlCgst = ttlCgst + work.getCgstAmt();
				ttlSgst = ttlSgst + work.getSgstAmt();
				ttlTax = ttlTax + work.getTaxAmt();
				ttlGrand = ttlGrand + work.getGrandTotal();
				ttlTcs = ttlTcs + work.getTcsAmt();
				ttlIgst = ttlIgst + work.getIgstAmt();
			}
			
			PdfPCell cell1;

			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlCgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlSgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlIgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTax)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTcs)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);		
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);			
			
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Datewise Bill Report\n", f);
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

	// detailDatewise
	List<GetDateWiseDetailBill> dateBillDetailList;

	/*@RequestMapping(value = "/showDateBillDetailReport/{billHeadId}/{billDate}/{fromDate}/{toDate}", method = RequestMethod.GET)
	public ModelAndView showDateBillDetailReport(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int billHeadId, @PathVariable String billDate, @PathVariable String fromDate,
			@PathVariable String toDate) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/datebilldetailreport");
			model.addObject("title", "Datewise Detail Report");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("billDate", DateConvertor.convertToYMD(billDate));

			GetDateWiseDetailBill[] ordHeadArray = rest.postForObject(Constants.url + "getDatewiseDetailBillReport",
					map, GetDateWiseDetailBill[].class);
			dateBillDetailList = new ArrayList<GetDateWiseDetailBill>(Arrays.asList(ordHeadArray));

			System.out.println("DatebillDetailList" + dateBillDetailList.toString());

			model.addObject("dateBillList", dateBillDetailList);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("Bill Date");
			rowData.add("Bill No");
			rowData.add("Customer Name");
			rowData.add("Taxable Amount");
			rowData.add("CGST");
			rowData.add("IGST");
			rowData.add("SGST");
			rowData.add("Tax Amount");
			rowData.add("Total Amount");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int cnt = 1;
			for (int i = 0; i < dateBillDetailList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				cnt = cnt + i;
				rowData.add("" + (i + 1));

				rowData.add("" + dateBillDetailList.get(i).getBillDate());
				rowData.add("" + dateBillDetailList.get(i).getBillNo());
				rowData.add("" + dateBillDetailList.get(i).getCustName());
				rowData.add("" + dateBillDetailList.get(i).getTaxableAmt());
				rowData.add("" + dateBillDetailList.get(i).getCgstAmt());
				rowData.add("" + dateBillDetailList.get(i).getIgstAmt());
				rowData.add("" + dateBillDetailList.get(i).getSgstAmt());
				rowData.add("" + dateBillDetailList.get(i).getTaxAmt());
				rowData.add("" + dateBillDetailList.get(i).getTotalAmt());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "GetDatewiseReport");

		} catch (Exception e) {
			System.err.println("exception In contractorDetailReport at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}*/
	
	@RequestMapping(value = "/showDateBillDetailReport", method = RequestMethod.GET)
	public ModelAndView showDateBillDetailReport(HttpServletRequest request, HttpServletResponse response ) {

		ModelAndView model = null;
		try {
			
			
			
			int custId = Integer.parseInt(request.getParameter("custId"));
			int plantId = Integer.parseInt(request.getParameter("plantId"));
			String billDate = request.getParameter("billDate");
			
			 
			
			model = new ModelAndView("report/datebilldetailreport");
			model.addObject("title", "Datewise Detail Report");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("custIdList", custId);
			map.add("plantIdList", plantId);
			map.add("billDate", DateConvertor.convertToYMD(billDate));

			GetDateWiseDetailBill[] ordHeadArray = rest.postForObject(Constants.url + "getDatewiseDetailBillReport",
					map, GetDateWiseDetailBill[].class);
			dateBillDetailList = new ArrayList<GetDateWiseDetailBill>(Arrays.asList(ordHeadArray));

			System.out.println("DatebillDetailList" + dateBillDetailList.toString());
		
			model.addObject("dateBillList", dateBillDetailList); 
			model.addObject("billDate", billDate); 
			
			float ttlTaxable = 0;
			float ttlCgst= 0;
			float ttlSgst = 0;
			float ttlTax = 0;
			float ttlGrand = 0;
			float ttlTcs = 0;
			float ttlIgst = 0;

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("Bill Date");
			rowData.add("Bill No");
			rowData.add("Customer Name");
			rowData.add("Taxable Amount");
			rowData.add("CGST");
			rowData.add("IGST");
			rowData.add("SGST");
			rowData.add("Tax Amount");
			rowData.add("TCS Amount");
			rowData.add("Total Amount");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int cnt = 1;
			for (int i = 0; i < dateBillDetailList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				cnt = cnt + i;
				rowData.add("" + (i + 1));

				rowData.add("" + dateBillDetailList.get(i).getBillDate());
				rowData.add("" + dateBillDetailList.get(i).getBillNo());
				rowData.add("" + dateBillDetailList.get(i).getCustName());
				rowData.add("" + dateBillDetailList.get(i).getTaxableAmt());
				rowData.add("" + dateBillDetailList.get(i).getCgstAmt());
				rowData.add("" + dateBillDetailList.get(i).getIgstAmt());
				rowData.add("" + dateBillDetailList.get(i).getSgstAmt());
				rowData.add("" + dateBillDetailList.get(i).getTaxAmt());
				rowData.add("" + dateBillDetailList.get(i).getTcsAmt());
				rowData.add("" + dateBillDetailList.get(i).getGrandTotal());
				
				ttlTaxable = ttlTaxable + dateBillDetailList.get(i).getTaxableAmt();
				ttlCgst = ttlCgst + dateBillDetailList.get(i).getCgstAmt();
				ttlSgst = ttlSgst + dateBillDetailList.get(i).getSgstAmt();
				ttlTax = ttlTax + dateBillDetailList.get(i).getTaxAmt();
				ttlGrand = ttlGrand + dateBillDetailList.get(i).getGrandTotal();
				ttlTcs = ttlTcs + roundUp(dateBillDetailList.get(i).getTcsAmt());
				ttlIgst = ttlIgst + dateBillDetailList.get(i).getIgstAmt();

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("Total");
			rowData.add("" + roundUp(ttlTaxable));	
			rowData.add("" + roundUp(ttlCgst));
			rowData.add("" + roundUp(ttlIgst));
			rowData.add("" + roundUp(ttlSgst));
			rowData.add("" + roundUp(ttlTax));
			rowData.add("" + roundUp(ttlTcs));	
			rowData.add("" + roundUp(ttlGrand));
			
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "GetDatewiseReport");

		} catch (Exception e) {
			System.err.println("exception In contractorDetailReport at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/showDatewiseDetailPdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showDatewiseDetailPdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showDateWiseDetailllwisePdf");
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
		
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;

		PdfPTable table = new PdfPTable(11);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.9f, 3.2f, 3.9f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("Bill Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Bill No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Customer Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("SGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("IGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tax Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("TCS Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (GetDateWiseDetailBill work : dateBillDetailList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getBillDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getBillNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + work.getTcsAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getGrandTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				ttlTaxable = ttlTaxable + work.getTaxableAmt();
				ttlCgst = ttlCgst + work.getCgstAmt();
				ttlSgst = ttlSgst + work.getSgstAmt();
				ttlTax = ttlTax + work.getTaxAmt();
				ttlGrand = ttlGrand + work.getGrandTotal();
				ttlTcs = ttlTcs + work.getTcsAmt();
				ttlIgst = ttlIgst + work.getIgstAmt();

			}
			
			PdfPCell cell1;

			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlCgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlSgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlIgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTax)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTcs)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Date Wise Detail Bill Report\n", f);
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

	@RequestMapping(value = "/showBillwiseReport", method = RequestMethod.GET)
	public ModelAndView showBillwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/billwisereport");

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);

			model.addObject("title", "Billwise Report");

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showBillwiseReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	// Ajax call
	@RequestMapping(value = "/getPlantByCompId", method = RequestMethod.GET)
	public @ResponseBody List<Plant> getPlantByCompId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int compId = Integer.parseInt(request.getParameter("compId"));

		map.add("companyId", compId);

		Plant[] plantArray = rest.postForObject(Constants.url + "getPlantListByCompId", map, Plant[].class);
		plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

		System.err.println("Ajax Plant List " + plantList.toString());

		return plantList;

	}

	List<GetBillReport> billList;

	@RequestMapping(value = "/getBillListBetweenDate", method = RequestMethod.GET)
	public @ResponseBody List<GetBillReport> getBillListBetweenDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getBillListBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String[] companyIdList = request.getParameterValues("companyId");
		String[] plantIdList = request.getParameterValues("plantId");

		System.out.println("plantIdList lengtr" + plantIdList.toString());

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < companyIdList.length; i++) {
			sb = sb.append(companyIdList[i] + ",");

		}
		String items = sb.toString();
		items = items.substring(0, items.length() - 1);

		StringBuilder sb1 = new StringBuilder();

		for (int i = 0; i < plantIdList.length; i++) {
			sb1 = sb1.append(plantIdList[i] + ",");

		}
		String items1 = sb1.toString();
		items1 = items1.substring(0, items1.length() - 1);

		System.out.println("plantIdList" + items1);

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantIdList", items1);
		map.add("companyIdList", items);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetBillReport[] ordHeadArray = rest.postForObject(Constants.url + "getBillwiseReport", map,
				GetBillReport[].class);
		billList = new ArrayList<GetBillReport>(Arrays.asList(ordHeadArray));
		
		for (int i = 0; i < billList.size(); i++) {			
			billList.get(i).setTcsAmt(billList.get(i).getExFloat1());
		}

		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;
		
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Bill Date");
		rowData.add("Bill No");
		rowData.add("Customer Name");
		rowData.add("Project Name");
		rowData.add("Taxable Amount");
		rowData.add("Sgst Amount");
		rowData.add("Cgst Amount");
		rowData.add("Igst Amount");
		rowData.add("Tax Amount");
		rowData.add("TCS Amount");
		rowData.add("Total Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < billList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + billList.get(i).getBillDate());
			rowData.add("" + billList.get(i).getBillNo());
			rowData.add("" + billList.get(i).getCustName());
			rowData.add("" + billList.get(i).getProjName());
			rowData.add("" + billList.get(i).getTaxableAmt());
			rowData.add("" + billList.get(i).getSgstAmt());
			rowData.add("" + billList.get(i).getCgstAmt());
			rowData.add("" + billList.get(i).getIgstAmt());
			rowData.add("" + billList.get(i).getTaxAmt());
			rowData.add("" + billList.get(i).getTcsAmt());
			rowData.add("" + billList.get(i).getTotalAmt());
			
			ttlTaxable = ttlTaxable + billList.get(i).getTaxableAmt();
			ttlCgst = ttlCgst + billList.get(i).getCgstAmt();
			ttlSgst = ttlSgst + billList.get(i).getSgstAmt();
			ttlTax = ttlTax + billList.get(i).getTaxAmt();
			ttlGrand = ttlGrand + billList.get(i).getTotalAmt();
			ttlTcs = ttlTcs + roundUp(billList.get(i).getTcsAmt());
			ttlIgst = ttlIgst + billList.get(i).getIgstAmt();

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}
		
		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("Total");
		rowData.add("" + roundUp(ttlTaxable));	
		rowData.add("" + roundUp(ttlSgst));
		rowData.add("" + roundUp(ttlCgst));
		rowData.add("" + roundUp(ttlIgst));
		rowData.add("" + roundUp(ttlTax));
		rowData.add("" + roundUp(ttlTcs));	
		rowData.add("" + roundUp(ttlGrand));
		
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetBillReport");

		return billList;
	}

	@RequestMapping(value = "/showBillwisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showBillwisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showBillwisePdf");
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
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;
		
		PdfPTable table = new PdfPTable(12);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 4.2f, 4.2f, 4.2f, 4.2f, 4.2f, 4.2f, 4.2f, 4.2f, 4.2f, 4.2f, 4.2f });
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

			hcell = new PdfPCell(new Phrase("Bill Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Bill No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Customer Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Project Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Sgst Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Cgst Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Igst Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Tax Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("TCS Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (GetBillReport work : billList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getBillDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getBillNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getProjName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" +	roundUp(work.getTcsAmt()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				

				ttlTaxable = ttlTaxable + work.getTaxableAmt();
				ttlCgst = ttlCgst + work.getCgstAmt();
				ttlSgst = ttlSgst + work.getSgstAmt();
				ttlTax = ttlTax + work.getTaxAmt();
				ttlGrand = ttlGrand + work.getTotalAmt();
				ttlTcs = ttlTcs + work.getTcsAmt();
				ttlIgst = ttlIgst + work.getIgstAmt();

			}
			
			PdfPCell cell1;

			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlSgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlCgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlIgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTax)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTcs)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Billwise Report\n", f);
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

	@RequestMapping(value = "/showCustomerwiseReport", method = RequestMethod.GET)
	public ModelAndView showCustomerwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/custwisereport");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Cust[] custArray = rest.getForObject(Constants.url + "getAllCustList", Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));

			model.addObject("custList", custList);

			model.addObject("title", "Customerwise Report");

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showCustomerwiseReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/getCustListBetweenDate", method = RequestMethod.GET)
	public @ResponseBody List<GetBillReport> getCustListBetweenDate(HttpServletRequest request,
			HttpServletResponse response) {

		String selectedFr = request.getParameter("values");

		selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
		selectedFr = selectedFr.replaceAll("\"", "");

		System.out.println("cust:::::" + selectedFr);

		// String[] custIdList = request.getParameterValues("values");
		// System.out.println("cust:::::"+arr1);

		// String[] custIdList1 = arr1.split(",");
		// System.out.println("custId is:::::"+arr1[0]+""+arr1[1]);

		/*
		 * for(int i=0;i<arr1.length;i++) { System.err.println("arr [] " +arr1[i]); }
		 */

		System.err.println(" in getCustListBetweenDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		// String rs=request.getParameter("values");
		// String[] custIdList = request.getParameterValues("values");

		int plantId = Integer.parseInt(request.getParameter("plantId"));

		System.out.println("plantIdList lengtr" + plantId);

		/*
		 * StringBuilder sb = new StringBuilder();
		 * 
		 * for (int i = 0; i < selectedFr.length; i++) { sb = sb.append(selectedFr[i] +
		 * ",");
		 * 
		 * } String items = sb.toString(); items = items.substring(0, items.length() -
		 * 1);
		 */
		// StringBuilder sb1 = new StringBuilder();

		/*
		 * for (int i = 0; i < plantIdList.length; i++) { sb1 =
		 * sb1.append(plantIdList[i] + ",");
		 * 
		 * } String items1 = sb1.toString(); items1 = items1.substring(0,
		 * items1.length() - 1);
		 * 
		 * System.out.println("plantIdList" + items1);
		 */

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantId", plantId);
		map.add("custIdList", selectedFr);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetBillReport[] ordHeadArray = rest.postForObject(Constants.url + "getCustomerwiseReport", map,
				GetBillReport[].class);
		
		billList = new ArrayList<GetBillReport>(Arrays.asList(ordHeadArray));
		
		for (int i = 0; i < billList.size(); i++) {			
			billList.get(i).setTcsAmt(billList.get(i).getExFloat1());
		}

		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;
		
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Customer Name");
		rowData.add("Customer Mobile No");
		rowData.add("Project Name");
		rowData.add("Tax Amount");
		rowData.add("Taxable Amount");
		rowData.add("TCS Amount");
		rowData.add("Total Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < billList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + billList.get(i).getCustName());
			rowData.add("" + billList.get(i).getCustMobNo());
			rowData.add("" + billList.get(i).getProjName());
			rowData.add("" + billList.get(i).getTaxAmt());
			rowData.add("" + billList.get(i).getTaxableAmt());
			rowData.add("" + billList.get(i).getTcsAmt());
			rowData.add("" + billList.get(i).getTotalAmt());
			
			ttlTaxable = ttlTaxable + billList.get(i).getTaxableAmt();
			ttlTax = ttlTax + billList.get(i).getTaxAmt();
			ttlGrand = ttlGrand + billList.get(i).getTotalAmt();
			ttlTcs = ttlTcs + roundUp(billList.get(i).getTcsAmt());


			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}
		
		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("Total");
		rowData.add("" + roundUp(ttlTax));
		rowData.add("" + roundUp(ttlTaxable));
		rowData.add("" + roundUp(ttlTcs));	
		rowData.add("" + roundUp(ttlGrand));
		
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetBillReport");

		return billList;
	}

	@RequestMapping(value = "/showCustwisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showCustwisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showCustwisePdf");
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

		float ttlTaxable = 0;		
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		
		PdfPTable table = new PdfPTable(8);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("Customer Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Mobile No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Project Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Tax Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("TCS Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (GetBillReport work : billList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustMobNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getProjName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + roundUp(work.getTcsAmt()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				ttlTaxable = ttlTaxable + work.getTaxableAmt();				
				ttlTax = ttlTax + work.getTaxAmt();
				ttlGrand = ttlGrand + work.getTotalAmt();
				ttlTcs = ttlTcs + work.getTcsAmt();

			}
			
			PdfPCell cell1;

			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTax)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTcs)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Customerwise Bill Report\n", f);
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

	@RequestMapping(value = "/showCustBillDetailReport/{custId}/{fromDate}/{toDate}", method = RequestMethod.GET)
	public ModelAndView showCustBillDetailReport(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int custId, @PathVariable String fromDate, @PathVariable String toDate) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/custbilldetailreport");
			model.addObject("title", "Customerwise Detail Report");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", custId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			GetBillReport[] ordHeadArray = rest.postForObject(Constants.url + "getBillDetailByCustIdAndDate", map,
					GetBillReport[].class);
			billList = new ArrayList<GetBillReport>(Arrays.asList(ordHeadArray));
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("key", 75);
			Setting tcsSetting = rest.postForObject(Constants.url + "getSettingValueByKey", map, Setting.class);
			float tcsPer = Float.parseFloat(tcsSetting.getSettingValue());
			float tcsAmt = 0;
			for (int i = 0; i < billList.size(); i++) {
				tcsAmt = ((billList.get(i).getTaxableAmt()+billList.get(i).getTaxAmt())*tcsPer)/100;
				billList.get(i).setTcsAmt(tcsAmt);
			}

			model.addObject("billList", billList);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			
			float ttlTaxable = 0;
			float ttlTax = 0;
			float ttlGrand = 0;
			float ttlTcs = 0;

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("Bill Date");
			rowData.add("Bill No");
			rowData.add("Customer Name");
			rowData.add("Project Name");
			rowData.add("Tax Amount");
			rowData.add("Taxable Amount");
			rowData.add("TCS Amount");
			rowData.add("Total Amount");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int cnt = 1;
			for (int i = 0; i < billList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				cnt = cnt + i;
				rowData.add("" + (i + 1));

				rowData.add("" + billList.get(i).getBillDate());
				rowData.add("" + billList.get(i).getBillNo());
				rowData.add("" + billList.get(i).getCustName());
				rowData.add("" + billList.get(i).getProjName());
				rowData.add("" + billList.get(i).getTaxAmt());
				rowData.add("" + billList.get(i).getTaxableAmt());
				rowData.add("" + billList.get(i).getTcsAmt());
				rowData.add("" + billList.get(i).getTotalAmt());
				
				ttlTaxable = ttlTaxable + billList.get(i).getTaxableAmt();
				ttlTax = ttlTax + billList.get(i).getTaxAmt();
				ttlGrand = ttlGrand + billList.get(i).getTotalAmt();
				ttlTcs = ttlTcs + roundUp(billList.get(i).getTcsAmt());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("Total");
			rowData.add("" + roundUp(ttlTax));
			rowData.add("" + roundUp(ttlTaxable));	
			rowData.add("" + roundUp(ttlTcs));	
			rowData.add("" + roundUp(ttlGrand));
			
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "GetBillReport");

		} catch (Exception e) {
			System.err.println("exception In contractorDetailReport at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	// ************************************Item****************************************

	@RequestMapping(value = "/showItemwiseReport", method = RequestMethod.GET)
	public ModelAndView showItemwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/itemwisebillreport");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			model.addObject("title", "Itemwise Bill Report");

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showItemwiseReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<GetItenwiseBillReport> itemList1;

	@RequestMapping(value = "/getItemListBetweenDate", method = RequestMethod.GET)
	public @ResponseBody List<GetItenwiseBillReport> getItemListBetweenDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getItemListBetweenDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String[] plantIdList = request.getParameterValues("plantId");

		System.out.println("plantIdList lengtr" + plantIdList.toString());

		StringBuilder sb1 = new StringBuilder();

		for (int i = 0; i < plantIdList.length; i++) {
			sb1 = sb1.append(plantIdList[i] + ",");

		}
		String items1 = sb1.toString();
		items1 = items1.substring(0, items1.length() - 1);

		System.out.println("plantIdList" + items1);

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		System.out.println("data is: " + fromDate + toDate);
		map.add("plantIdList", items1);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetItenwiseBillReport[] itemHeadArray = rest.postForObject(Constants.url + "getItemwiseReport", map,
				GetItenwiseBillReport[].class);
		itemList1 = new ArrayList<GetItenwiseBillReport>(Arrays.asList(itemHeadArray));

		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlIgst = 0;
		
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Item Code");
		rowData.add("Item Name");
		rowData.add("Qty");
		rowData.add("Uom");
		rowData.add("Hsn Code");
		rowData.add("Taxable Amount");
		rowData.add("CGST");
		rowData.add("SGST");
		rowData.add("IGST");
		rowData.add("Tax Amount");
		rowData.add("Total Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < itemList1.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + itemList1.get(i).getItemCode());
			rowData.add("" + itemList1.get(i).getItemName());
			rowData.add("" + itemList1.get(i).getQty());
			rowData.add("" + itemList1.get(i).getUomName());
			rowData.add("" + itemList1.get(i).getHsnCode());
			rowData.add("" + itemList1.get(i).getTaxableAmt());
			rowData.add("" + itemList1.get(i).getCgstAmt());
			rowData.add("" + itemList1.get(i).getSgstAmt());
			rowData.add("" + itemList1.get(i).getIgstAmt());
			rowData.add("" + itemList1.get(i).getTaxAmt());
			rowData.add("" + itemList1.get(i).getTotalAmt());
			
			
			ttlTaxable = ttlTaxable + itemList1.get(i).getTaxableAmt();
			ttlCgst = ttlCgst + itemList1.get(i).getCgstAmt();
			ttlSgst = ttlSgst + itemList1.get(i).getSgstAmt();
			ttlTax = ttlTax + itemList1.get(i).getTaxAmt();
			ttlGrand = ttlGrand + itemList1.get(i).getTotalAmt();
			ttlIgst = ttlIgst + itemList1.get(i).getIgstAmt();

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
		}
		
		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("Total");
		rowData.add("" + roundUp(ttlTaxable));	
		rowData.add("" + roundUp(ttlCgst));
		rowData.add("" + roundUp(ttlSgst));
		rowData.add("" + roundUp(ttlIgst));
		rowData.add("" + roundUp(ttlTax));
		rowData.add("" + roundUp(ttlGrand));
		
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);


		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetItemWiseBillReport");

		return itemList1;
	}

	// PDF for item

	@RequestMapping(value = "/showItemwisePdf/{fromDate}/{toDate}/{plantId}", method = RequestMethod.GET)
	public void showItemwisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			@PathVariable("plantId") int plantId, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;

		System.out.println("Inside Pdf showTaxwisePdf");

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
		
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlIgst = 0;

		PdfPTable table = new PdfPTable(12);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 2.4f});
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

			hcell = new PdfPCell(new Phrase("Item Code", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Uom", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Hsn Code", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Total Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("CGST Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("SGST Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("IGST Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tax Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);			
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			int index = 0;
			for (GetItenwiseBillReport work : itemList1) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemCode(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getUomName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getHsnCode(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + work.getCgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);				

				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				ttlTaxable = ttlTaxable + work.getTaxableAmt();
				ttlCgst = ttlCgst + work.getCgstAmt();
				ttlSgst = ttlSgst + work.getSgstAmt();
				ttlTax = ttlTax + work.getTaxAmt();
				ttlGrand = ttlGrand + work.getTotalAmt();
				ttlIgst = ttlIgst + work.getIgstAmt();

			}
			
			PdfPCell cell1;

			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);			
						
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlCgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlSgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlIgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTax)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Itemwise Report\n", f);
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

	// ************************************Taxwise****************************************

	@RequestMapping(value = "/showTaxwiseReport", method = RequestMethod.GET)
	public ModelAndView showTaxwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/taxwisebillreport");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Cust[] custArray = rest.getForObject(Constants.url + "getAllCustList", Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));

			model.addObject("custList", custList);

			model.addObject("title", "Taxwise Bill Report");

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showTaxwiseReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<TaxWiseBill> taxList;

	@RequestMapping(value = "/getTaxListBetweenDate", method = RequestMethod.GET)
	public @ResponseBody List<TaxWiseBill> getTaxListBetweenDate(HttpServletRequest request,
			HttpServletResponse response) {

		String selectedFr = request.getParameter("values");

		selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
		selectedFr = selectedFr.replaceAll("\"", "");

		System.out.println("cust:::::" + selectedFr);

		System.err.println(" in getTaxListBetweenDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		System.out.println("data is: " + fromDate + toDate);
		map.add("custIdList", selectedFr);
		map.add("plantId", plantId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		TaxWiseBill[] taxHeadArray = rest.postForObject(Constants.url + "getTaxwiseReport", map, TaxWiseBill[].class);
		taxList = new ArrayList<TaxWiseBill>(Arrays.asList(taxHeadArray));
		
		
		for (int i = 0; i < taxList.size(); i++) {			
			taxList.get(i).setTcsAmt(taxList.get(i).getExFloat1());
		}

		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;
		
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Bill No");
		rowData.add("Customer GST No.");
		rowData.add("Customer Name");
		rowData.add("Total Taxable Amount");
		rowData.add("CGST");
		rowData.add("SGST");
		rowData.add("IGST");
		rowData.add("Tax Amount");
		rowData.add("TCS Amount");
		rowData.add("Total Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < taxList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;

			rowData.add("" + (i + 1));

			rowData.add("" + taxList.get(i).getBillNo());
			rowData.add("" + taxList.get(i).getCustGstNo());
			rowData.add("" + taxList.get(i).getCustName());
			rowData.add("" + taxList.get(i).getTaxableAmt());
			rowData.add("" + taxList.get(i).getCgstAmt());
			rowData.add("" + taxList.get(i).getSgstAmt());
			rowData.add("" + taxList.get(i).getIgstAmt());
			rowData.add("" + taxList.get(i).getTaxAmt());
			rowData.add("" + taxList.get(i).getTcsAmt());
			rowData.add("" + taxList.get(i).getGrandTotal());
			
			ttlTaxable = ttlTaxable + taxList.get(i).getTaxableAmt();
			ttlCgst = ttlCgst + taxList.get(i).getCgstAmt();
			ttlSgst = ttlSgst + taxList.get(i).getSgstAmt();
			ttlTax = ttlTax + taxList.get(i).getTaxAmt();
			ttlGrand = ttlGrand + taxList.get(i).getGrandTotal();
			ttlTcs = ttlTcs + roundUp(taxList.get(i).getTcsAmt());
			ttlIgst = ttlIgst + taxList.get(i).getIgstAmt();

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}
		
		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();
		
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("Total");
		rowData.add("" + roundUp(ttlTaxable));	
		rowData.add("" + roundUp(ttlCgst));
		rowData.add("" + roundUp(ttlSgst));
		rowData.add("" + roundUp(ttlIgst));
		rowData.add("" + roundUp(ttlTax));
		rowData.add("" + roundUp(ttlTcs));	
		rowData.add("" + roundUp(ttlGrand));
		
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetTaxWiseBillReport");

		return taxList;

	}

	// PDF for tax

	@RequestMapping(value = "/showTaxwisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showTaxwisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showTaxwisePdf");
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
		
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;

		PdfPTable table = new PdfPTable(11);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 0.4f, 0.7f, 1.2f, 1.2f, 0.6f, 0.6f, 0.6f, 1.0f, 0.8f, 0.8f, 0.8f });
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

			hcell = new PdfPCell(new Phrase("Bill No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Customer Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("GST No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);			

			hcell = new PdfPCell(new Phrase("CGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("SGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("IGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tax Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("TCS Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (TaxWiseBill work : taxList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getBillNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				

				cell = new PdfPCell(new Phrase("" + work.getCustGstNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + work.getTcsAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getGrandTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				
				ttlTaxable = ttlTaxable + work.getTaxableAmt();
				ttlCgst = ttlCgst + work.getCgstAmt();
				ttlSgst = ttlSgst + work.getSgstAmt();
				ttlTax = ttlTax + work.getTaxAmt();
				ttlGrand = ttlGrand + work.getGrandTotal();
				ttlTcs = ttlTcs + work.getTcsAmt();
				ttlIgst = ttlIgst + work.getIgstAmt();

			}
			
			PdfPCell cell1;

			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlCgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlSgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlIgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTax)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTcs)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Taxwise Bill Report\n", f);
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

	// ************************************Monthwise****************************************

	@RequestMapping(value = "/showMonthwiseReport", method = RequestMethod.GET)
	public ModelAndView showMonthwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/monthwisebillreport");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			model.addObject("title", "Monthwise Bill Report");
			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showMonthwiseBillReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	/*
	 * @RequestMapping(value = "/getMonthwiseBillList", method = RequestMethod.GET)
	 * public @ResponseBody List<MonthWiseBill>
	 * getMonthwiseBillList(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * System.err.println(" in getMonthWiseBillList"); MultiValueMap<String, Object>
	 * map = new LinkedMultiValueMap<String, Object>();
	 * 
	 * String custId = request.getParameter("custId"); String plantId =
	 * request.getParameter("plantId");
	 * 
	 * String fromDate = request.getParameter("fromDate"); String toDate =
	 * request.getParameter("toDate");
	 * 
	 * 
	 * 
	 * map.add("plantId", plantId); map.add("custId", custId); map.add("fromDate",
	 * DateConvertor.convertToYMD(fromDate)); map.add("toDate",
	 * DateConvertor.convertToYMD(toDate));
	 * 
	 * MonthWiseBill[] ordHeadArray = rest.postForObject(Constants.url +
	 * "getMonthwiseBillReport", map, MonthWiseBill[].class); monthList = new
	 * ArrayList<MonthWiseBill>(Arrays.asList(ordHeadArray)); ModelAndView model =
	 * null; model.addObject("monthList",monthList); List<ExportToExcel>
	 * exportToExcelList = new ArrayList<ExportToExcel>();
	 * 
	 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
	 * ArrayList<String>();
	 * 
	 * rowData.add("Sr. No"); rowData.add("Bill Month"); rowData.add("CGST");
	 * rowData.add("IGST"); rowData.add("SGST"); rowData.add("Tax Amount");
	 * rowData.add("Taxable Amount"); rowData.add("Total Amount");
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel); int cnt = 1;
	 * for (int i = 0; i < monthList.size(); i++) { expoExcel = new ExportToExcel();
	 * rowData = new ArrayList<String>(); cnt = cnt + i; rowData.add("" + (i + 1));
	 * 
	 * rowData.add("" + monthList.get(i).getMonth());
	 * 
	 * rowData.add("" + monthList.get(i).getCgstAmt()); rowData.add("" +
	 * monthList.get(i).getIgstAmt()); rowData.add("" +
	 * monthList.get(i).getSgstAmt()); rowData.add("" +
	 * monthList.get(i).getTaxAmt()); rowData.add("" +
	 * monthList.get(i).getTaxableAmt()); rowData.add("" +
	 * monthList.get(i).getTotalAmt());
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * }
	 * 
	 * HttpSession session = request.getSession();
	 * session.setAttribute("exportExcelList", exportToExcelList);
	 * session.setAttribute("excelName", "GetBillReport");
	 * 
	 * return monthList; }
	 */

	/// ****************************Monthwise Excel**********************

	@RequestMapping(value = "/getMonthWiseListBetweenDate", method = RequestMethod.GET)
	public @ResponseBody List<MonthWiseBill> getMonthWiseListBetweenDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getTMonthWiseBill");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String[] plantIdList = request.getParameterValues("plantId");
		String[] custIdList = request.getParameterValues("custId");

		System.out.println("plantIdList lengtr" + plantIdList.toString());
		System.out.println("custIdList lengtr" + custIdList.toString());

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < custIdList.length; i++) {
			sb = sb.append(custIdList[i] + ",");

		}
		String items = sb.toString();
		items = items.substring(0, items.length() - 1);

		StringBuilder sb1 = new StringBuilder();

		for (int i = 0; i < plantIdList.length; i++) {
			sb1 = sb1.append(plantIdList[i] + ",");

		}
		String items1 = sb1.toString();
		items1 = items1.substring(0, items1.length() - 1);

		System.out.println("plantIdList" + items1.toString());
		System.out.println("custIdList" + items.toString());
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		System.out.println("data is: " + fromDate + toDate);
		map.add("custIdList", items);
		map.add("plantIdList", items1);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		MonthWiseBill[] monthHeadArray = rest.postForObject(Constants.url + "getMonthwiseBillReport", map,
				MonthWiseBill[].class);
		monthList = new ArrayList<MonthWiseBill>(Arrays.asList(monthHeadArray));
		
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;
		
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Bill Month");
		rowData.add("CGST");
		rowData.add("IGST");
		rowData.add("SGST");
		rowData.add("Tax Amount");
		rowData.add("Taxable Amount");
		rowData.add("TCS Amount");
		rowData.add("Total Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < monthList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + monthList.get(i).getMonth());

			rowData.add("" + monthList.get(i).getCgstAmt());
			rowData.add("" + monthList.get(i).getIgstAmt());
			rowData.add("" + monthList.get(i).getSgstAmt());
			rowData.add("" + monthList.get(i).getTaxAmt());
			rowData.add("" + monthList.get(i).getTaxableAmt());
			rowData.add("" + roundUp(monthList.get(i).getTcsAmt()));
			rowData.add("" + monthList.get(i).getGrandTotal());
			
			ttlTaxable = ttlTaxable + monthList.get(i).getTaxableAmt();
			ttlCgst = ttlCgst + monthList.get(i).getCgstAmt();
			ttlSgst = ttlSgst + monthList.get(i).getSgstAmt();
			ttlTax = ttlTax + monthList.get(i).getTaxAmt();
			ttlGrand = ttlGrand + monthList.get(i).getGrandTotal();
			ttlTcs = ttlTcs + monthList.get(i).getTcsAmt();
			ttlIgst = ttlIgst + monthList.get(i).getIgstAmt();
			
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}
		
		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();
		rowData.add("");
		rowData.add("Total");
		rowData.add("" + roundUp(ttlCgst));
		rowData.add("" + roundUp(ttlIgst));
		rowData.add("" + roundUp(ttlSgst));
		rowData.add("" + roundUp(ttlTax));
		rowData.add("" + roundUp(ttlTaxable));
		rowData.add("" + roundUp(ttlTcs));		
		rowData.add("" + roundUp(ttlGrand));
		
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetMonthWiseBillReport");

		return monthList;

	}

	@RequestMapping(value = "/showMonthwisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showMonthwisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showTaxwisePdf");
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
		
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;

		PdfPTable table = new PdfPTable(9);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 0.4f, 0.7f, 0.6f, 0.6f, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f});
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

			hcell = new PdfPCell(new Phrase("Bill Month", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("SGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("IGST ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tax Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("TCS Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);		
			

			int index = 0;
			for (MonthWiseBill work : monthList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getMonth(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + roundUp(work.getTcsAmt()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getGrandTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);				
				
				ttlTaxable = ttlTaxable + work.getTaxableAmt();
				ttlCgst = ttlCgst + work.getCgstAmt();
				ttlSgst = ttlSgst + work.getSgstAmt();
				ttlTax = ttlTax + work.getTaxAmt();
				ttlGrand = ttlGrand + work.getGrandTotal();
				ttlTcs = ttlTcs + work.getTcsAmt();
				ttlIgst = ttlIgst + work.getIgstAmt();

			}
			
			PdfPCell cell1;

			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlCgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlSgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlIgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTax)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTcs)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			 
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Monthwise Bill Report\n", f);
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

	@RequestMapping(value = "/showCustomerwiseDetailPdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showCustomerwiseDetailPdf(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showBillwisePdf");
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

		float ttlTaxable = 0;
		float ttlTax = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		
		PdfPTable table = new PdfPTable(9);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f});
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

			hcell = new PdfPCell(new Phrase("Bill Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Bill No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Customer Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Project Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Tax Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("TCS Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (GetBillReport work : billList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getBillDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getBillNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getProjName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + work.getTcsAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				ttlTaxable = ttlTaxable + work.getTaxableAmt();
				ttlTax = ttlTax + work.getTaxAmt();
				ttlGrand = ttlGrand + work.getTotalAmt();
				ttlTcs = ttlTcs + work.getTcsAmt();
			}
			
			PdfPCell cell1;

			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTax)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);				
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTcs)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Customerwise Detail Report\n", f);
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

	@RequestMapping(value = "/showTaxSummeryReport", method = RequestMethod.GET)
	public ModelAndView showTaxSummeryReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/taxSumReport");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

			Calendar cal = Calendar.getInstance();

			Calendar cal1 = Calendar.getInstance();
			cal.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), 1);

			String firstDate = sdf.format(cal.getTimeInMillis());

			String endDate = sdf.format(cal.getTimeInMillis());
			model.addObject("fromDate", DateConvertor.convertToDMY(firstDate));
			model.addObject("toDate", DateConvertor.convertToDMY(endDate));

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			model.addObject("title", "Tax Summary Report");

		} catch (Exception e) {

			System.err.println("exception In showTaxSummeryReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<TaxSummery> taxSumList;

	@RequestMapping(value = "/getTaxSummeryBetDate", method = RequestMethod.GET)
	public @ResponseBody List<TaxSummery> getTaxSummeryBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		int plantId = Integer.parseInt(request.getParameter("plantId"));

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		map.add("plantId", plantId);

		TaxSummery[] taxReportArray = rest.postForObject(Constants.url + "getTaxSummeryBetweenDate", map,
				TaxSummery[].class);
		taxSumList = new ArrayList<TaxSummery>(Arrays.asList(taxReportArray));
		
		map = new LinkedMultiValueMap<String, Object>();
		map.add("key", 75);
		Setting tcsSetting = rest.postForObject(Constants.url + "getSettingValueByKey", map, Setting.class);
		float tcsPer = Float.parseFloat(tcsSetting.getSettingValue());
		float tcsAmt = 0;
		for (int i = 0; i < taxSumList.size(); i++) {
			tcsAmt = ((taxSumList.get(i).getTaxableAmt()+taxSumList.get(i).getTaxAmt())*tcsPer)/100;
			taxSumList.get(i).setTcsAmt(tcsAmt);
		}
		System.out.println(taxSumList);

		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;
		
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("GST");
		rowData.add("Taxable Amount");
		rowData.add("CGST");
		rowData.add("SGST");
		rowData.add("IGST");
		rowData.add("TCS");
		rowData.add("Total");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < taxSumList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));
			rowData.add("" + taxSumList.get(i).getGstPer());
			rowData.add("" + taxSumList.get(i).getTaxableAmt());
			rowData.add("" + taxSumList.get(i).getCgstAmt());
			rowData.add("" + taxSumList.get(i).getSgstAmt());
			rowData.add("" + taxSumList.get(i).getIgstAmt());
			rowData.add("" + taxSumList.get(i).getTcsAmt());
			rowData.add("" + taxSumList.get(i).getTotalAmt());
			
			ttlTaxable = ttlTaxable + taxSumList.get(i).getTaxableAmt();
			ttlCgst = ttlCgst + taxSumList.get(i).getCgstAmt();
			ttlSgst = ttlSgst + taxSumList.get(i).getSgstAmt();
			ttlGrand = ttlGrand + taxSumList.get(i).getTotalAmt();
			ttlTcs = ttlTcs + roundUp(taxSumList.get(i).getTcsAmt());
			ttlIgst = ttlIgst + taxSumList.get(i).getIgstAmt();

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}
		
		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();
		rowData.add("");
		rowData.add("Total");
		rowData.add("" + roundUp(ttlTaxable));	
		rowData.add("" + roundUp(ttlSgst));
		rowData.add("" + roundUp(ttlCgst));
		rowData.add("" + roundUp(ttlIgst));
		rowData.add("" + roundUp(ttlTcs));	
		rowData.add("" + roundUp(ttlGrand));
		
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "TaxSummery");

		return taxSumList;
	}

	@RequestMapping(value = "/showTaxSummeryPdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showTaxSummeryPdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showBillwisePdf");
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
		
		float ttlTaxable = 0;
		float ttlCgst= 0;
		float ttlSgst = 0;
		float ttlGrand = 0;
		float ttlTcs = 0;
		float ttlIgst = 0;

		PdfPTable table = new PdfPTable(8);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("GST", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("CGST", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("SGST", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("IGST", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("TCS Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (TaxSummery work : taxSumList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getGstPer(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIgstAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase("" + roundUp(work.getTcsAmt()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				ttlTaxable = ttlTaxable + work.getTaxableAmt();
				ttlCgst = ttlCgst + work.getCgstAmt();
				ttlSgst = ttlSgst + work.getSgstAmt();
				ttlGrand = ttlGrand + work.getTotalAmt();
				ttlTcs = ttlTcs + work.getTcsAmt();
				ttlIgst = ttlIgst + work.getIgstAmt();

			}
			PdfPCell cell1;			
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(""), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf("Total"), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTaxable)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlCgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlSgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);

			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlIgst)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlTcs)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);	
			
			cell1 = new PdfPCell(new Phrase(String.valueOf(roundUp(ttlGrand)), headFont));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setPadding(3);
			cell1.setPaddingRight(2);
			table.addCell(cell1);
			
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Tax Summery Report\n", f);
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

	@RequestMapping(value = "/showChalanReport", method = RequestMethod.GET)
	public ModelAndView showChalanReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/chalanReport");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

			Calendar cal = Calendar.getInstance();

			Calendar cal1 = Calendar.getInstance();
			cal.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), 1);

			String firstDate = dd.format(cal.getTimeInMillis());

			// String endDate = dd.format(cal.getTimeInMillis());
			String toDate = dd.format(new Date());
			model.addObject("fromDate", firstDate);
			model.addObject("toDate", toDate);

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			model.addObject("title", "Chalan Report");

		} catch (Exception e) {

			System.err.println("exception In showChalanReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<GetChalanHeader> chalanHeadList = new ArrayList<>();

	@RequestMapping(value = "/getChalanReportBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetChalanHeader> getChalanReportBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("1.....");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		System.err.println("plantId for getChalanListByPlant  " + plantId + "from " + fromDate + "to Date " + toDate);
//hiiffg
		map.add("plantId", plantId);
		map.add("custId", custId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		System.out.println("1.....");
		GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getChalanHeadersByPlantAndStatus", map,
				GetChalanHeader[].class);

		chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));

		System.out.println("2.....");

		System.out.println("chalan list is:" + chalanHeadList.toString());
		/*
		 * for (int i = 0; i < chalanHeadList.size(); i++) {
		 * 
		 * chalanHeadList.get(i).setChalanDate(DateConvertor.convertToDMY(chalanHeadList
		 * .get(i).getChalanDate())); }
		 */
		System.out.println("3.....");
		System.err.println("Ajax chalanHeadList /getChalanListByPlant " + chalanHeadList.toString());
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Chalan No");
		rowData.add("Chalan Date");

		rowData.add("Vehicle No");
		rowData.add("Driver Name");
		rowData.add("Vehicle In Time");
		rowData.add("Vehicle Out Time");
		rowData.add("Customer Name");
		rowData.add("Project Name");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < chalanHeadList.size(); i++) {

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + chalanHeadList.get(i).getChalanNo());
			rowData.add("" + chalanHeadList.get(i).getChalanDate());
			rowData.add("" + chalanHeadList.get(i).getVehNo());
			rowData.add("" + chalanHeadList.get(i).getDriverName());
			rowData.add("" + chalanHeadList.get(i).getVehTimeIn());
			rowData.add("" + chalanHeadList.get(i).getVehTimeOut());
			rowData.add("" + chalanHeadList.get(i).getCustName());
			rowData.add("" + chalanHeadList.get(i).getProjName());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Chalan Report");

		return chalanHeadList;
	}

	@RequestMapping(value = "/showChalanReportPdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showChalanReportPdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
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

		PdfPTable table = new PdfPTable(9);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("Chalan No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Chalan date ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Vehicle No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Driver Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Vehicle In Time", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Vehicle Out Time", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Customer Name ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Project Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetChalanHeader work : chalanHeadList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getChalanNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getChalanDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getVehNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getDriverName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getVehTimeIn(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getVehTimeOut(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getProjName(), headFont));
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
			Paragraph company = new Paragraph("Tax Summery Report\n", f);
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

	List<GetChalanDetail> chDetailList;

	@RequestMapping(value = "/detailItemChalan/{chalanId}/{fromDate}/{toDate}", method = RequestMethod.GET)
	public ModelAndView editChalan(HttpServletRequest request, HttpServletResponse response, @PathVariable int chalanId,
			@PathVariable String fromDate, @PathVariable String toDate) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/chalanDetail");

			MultiValueMap<String, Object>

			map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanId", chalanId);
			GetChalanDetail[] chDetailArray = rest.postForObject(Constants.url + "getGetChalanDetailByChalanId", map,
					GetChalanDetail[].class);
			chDetailList = new ArrayList<GetChalanDetail>(Arrays.asList(chDetailArray));

			model.addObject("chDetailList", chDetailList);

			model.addObject("title", "Chalan Detail report");
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("Item Name");
			rowData.add("Measurement Unit");

			rowData.add("Order Quantity");
			rowData.add("Item Quantity");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int cnt = 1;
			for (int i = 0; i < chDetailList.size(); i++) {

				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				cnt = cnt + i;
				rowData.add("" + (i + 1));

				rowData.add("" + chDetailList.get(i).getItemName());
				rowData.add("" + chDetailList.get(i).getUomName());
				rowData.add("" + chDetailList.get(i).getOrderQty());
				rowData.add("" + chDetailList.get(i).getItemQty());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "Chalan Detail Report");

		} catch (Exception e) {
			System.err.println("Exce in detailItemChalan Report " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/showChalanDetailReportPdf", method = RequestMethod.GET)
	public void showChalanDetailReportPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
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

		PdfPTable table = new PdfPTable(5);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("Measurement Unit", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Order Quantity", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Quantity", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetChalanDetail work : chDetailList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getUomName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getOrderQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemQty(), headFont));
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
			Paragraph company = new Paragraph("Chalan Detail Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());

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
/*************************************************************************************/
	//Mahendra
	//06-01-2020
	
//showCustomerwiseReport
	@RequestMapping(value = "/showPOChallanReport", method = RequestMethod.GET)
	public ModelAndView showPOChallanReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/pochallanreport");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Cust[] custArray = rest.getForObject(Constants.url + "getAllCustList", Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));

			model.addObject("custList", custList);

			model.addObject("title", "PO Challan Report");

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showCustomerwiseReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	
List<GetPoChallan> poList = null;	
	@RequestMapping(value = "/getPoInfoByCustList", method = RequestMethod.GET)
	public @ResponseBody List<GetPoChallan> getPoInfoByCustList(HttpServletRequest request,
			HttpServletResponse response) {

		String selectedFr = request.getParameter("values");

		selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
		selectedFr = selectedFr.replaceAll("\"", "");

		System.out.println("cust:::::" + selectedFr);

		System.err.println(" in getCustListBetweenDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));

		System.out.println("plantIdList lengtr" + plantId);


		map.add("plantId", plantId);
		map.add("custIds", selectedFr);

		GetPoChallan[] ordHeadArray = rest.postForObject(Constants.url + "getPoChallanReportInfo", map,
				GetPoChallan[].class);
		poList = new ArrayList<GetPoChallan>(Arrays.asList(ordHeadArray));
System.out.println("----------"+poList);
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");

		rowData.add("PO No.");
		rowData.add("PO Date");
		rowData.add("Item Name");
		rowData.add("PO Qty.");
		rowData.add("PO Challan Qty.");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < poList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + poList.get(i).getPoNo());
			rowData.add("" + poList.get(i).getPoDate());
			rowData.add("" + poList.get(i).getItemDesc());
			rowData.add("" + poList.get(i).getPoQty());
			rowData.add("" + poList.get(i).getPoChallanQty());
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "PoChallanReport");

		return poList;
	}

	@RequestMapping(value = "/showPOChallanPdf", method = RequestMethod.GET)
	public void showCustwisePdf(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showCustwisePdf");
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
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("PO No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("PO Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("PO Qty.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("PO Challan Qty.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			int index = 0;
			for (GetPoChallan work : poList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getPoNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getPoDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getPoQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getPoChallanQty(), headFont));
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
			Paragraph company = new Paragraph("PO Challan Report\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			/*Paragraph p1 = new Paragraph("From Date:" + fromDate + "  To Date:" + toDate, headFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);*/
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
	
	List<PoChallanDetails> chalanList=null;
	@RequestMapping(value = "/getChallanDetails/{challanId}/{poNo}/{poNoItm}", method = RequestMethod.GET)
	public ModelAndView getChallan(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int challanId, @PathVariable String poNo, @PathVariable String poNoItm) {
		
		String str = poNoItm;
		String[] values = str.split("\\s*,\\s*");
		System.out.println(Arrays.toString(values));
		
		String poDate = values[0];
		String itemDesc = values[1];
		
		ModelAndView model = null;
		try {
			model = new ModelAndView("report/challanDetails");
			model.addObject("title", "PO Challan Details");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("challanId", challanId);
			PoChallanDetails[] chalanArray = rest.postForObject(Constants.url + "getChalanDetailInfo", map,
					PoChallanDetails[].class);
			chalanList = new ArrayList<PoChallanDetails>(Arrays.asList(chalanArray));

			model.addObject("poNo", poNo);
			model.addObject("poDate", poDate);
			model.addObject("itemName", itemDesc);
			model.addObject("chalanList", chalanList);

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("Challan No");
			rowData.add("Challan Date");
			rowData.add("Challan Qty.");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			
			rowData.add("");
			rowData.add("PO No : "+poNo);
			rowData.add("PO Date : "+poDate);
			rowData.add("Item Name : "+itemDesc);
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			
			int cnt = 1;
			for (int i = 0; i < chalanList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				cnt = cnt + i;
				rowData.add("" + (i + 1));

				rowData.add("" + chalanList.get(i).getChalanNo());
				rowData.add("" + chalanList.get(i).getChalanDate());
				rowData.add("" + chalanList.get(i).getItemQty());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "PO Challan Details");

		} catch (Exception e) {
			System.err.println("exception In getChallanDetails at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}
	
	@RequestMapping(value = "/showPOChallanDetailPdf/{poNo}/{poDate}/{itemName}", method = RequestMethod.GET)
	public void showPOChallanDetailPdf( @PathVariable String poNo, @PathVariable String poDate, @PathVariable String itemName,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showPOChallanDetailPdf");
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

		PdfPTable table = new PdfPTable(4);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f});
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

			hcell = new PdfPCell(new Phrase("Challan No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Challan", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Challan Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			
			int index = 0;
			for (PoChallanDetails work : chalanList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getChalanNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getChalanDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemQty(), headFont));
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
			Paragraph company = new Paragraph("PO Challan Details\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			
			Paragraph p1 = new Paragraph("PO No:" + poNo , headFont);
			p1.setAlignment(Element.ALIGN_LEFT);
			document.add(p1);
			
			Paragraph p2 = new Paragraph("PO Date:" + poDate, headFont);
			p1.setAlignment(Element.ALIGN_LEFT);
			document.add(p2);
			
			//document.add(new Paragraph("\n"));
			
			Paragraph p3 = new Paragraph("Item Name:" + itemName, headFont);
			p1.setAlignment(Element.ALIGN_LEFT);
			document.add(p3);
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
	
	/************************************************************************************/
	@RequestMapping(value = "/showDailySalesReport", method = RequestMethod.GET)
	public ModelAndView showDailySalesReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/dailysalesreport");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			
			model.addObject("title", "Daily Sales Report");

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In dailySalesReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	
	List<GetDailySalesReport> dailySales;
	@RequestMapping(value = "/getDailySalesBetweenDate", method = RequestMethod.GET)
	public @ResponseBody List<GetDailySalesReport> getDailySalesBetweenDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getCustListBetweenDate");
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		
		int plantId = Integer.parseInt(request.getParameter("plantId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		
		System.out.println("plantIdList lengtr" + plantId+" "+fromDate+" "+toDate);

		map.add("plantId", plantId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetDailySalesReport[] dailySalesArray = rest.postForObject(Constants.url + "getDailySalesReport", map,
				GetDailySalesReport[].class);
		dailySales = new ArrayList<GetDailySalesReport>(Arrays.asList(dailySalesArray));
		System.err.println("Daily Sales Rep-------------"+dailySales);
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");

		rowData.add("Customer Name");
		rowData.add("Site Name");
		rowData.add("Product Name");
		rowData.add("Rate");
		rowData.add("Qty");		
		rowData.add("Taxable Amount");
		rowData.add("Total Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < dailySales.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + dailySales.get(i).getCustName());
			rowData.add("" + dailySales.get(i).getSiteName());
			rowData.add("" + dailySales.get(i).getItemDesc());
			rowData.add("" + dailySales.get(i).getRate());
			rowData.add("" + dailySales.get(i).getQty());			
			rowData.add("" + dailySales.get(i).getTaxableAmt());
			rowData.add("" + dailySales.get(i).getTotalAmt());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetDailySalesReport");

		return dailySales;
	}
	
	@RequestMapping(value = "/showDailySalesPdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showDailySalesPdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showDailySalesPdf");
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

		PdfPTable table = new PdfPTable(8);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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

			hcell = new PdfPCell(new Phrase("Customer Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Site Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Product Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Rate", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			
			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Qty.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (GetDailySalesReport work : dailySales) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSiteName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemDesc(), headFont));
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
				
				cell = new PdfPCell(new Phrase("" + work.getQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTaxableAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
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
			Paragraph company = new Paragraph("Daily Sales Report\n", f);
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
	
	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
}
