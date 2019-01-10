package com.ats.ssgs.controller;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
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
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.common.ExportToExcel;
import com.ats.ssgs.model.GetQuotHeader;
import com.ats.ssgs.model.enq.GetEnqHeader;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.DocTermHeader;
import com.ats.ssgs.model.master.GetCust;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.PaymentTerm;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.order.GetOrder;
import com.ats.ssgs.model.prodrm.RmcQuotItemDetail;
import com.ats.ssgs.model.prodrm.RmcQuotTemp;
import com.ats.ssgs.model.quot.GetItemWithEnq;
import com.ats.ssgs.model.quot.GetQuotHeads;
import com.ats.ssgs.model.quot.QuotDetail;
import com.ats.ssgs.model.quot.QuotHeader;
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
public class QuotController {

	RestTemplate rest = new RestTemplate();
	List<User> usrList;
	List<GetQuotHeads> quotList;

	List<Cust> custList;

	List<GetItemWithEnq> itemList;
	List<Project> projList;
	List<Plant> plantList;
	List<PaymentTerm> payTermList;
	List<DocTermHeader> docTermList;
	int quotHeadIdPdf = 0;
	int pdfCustId = 0;
	int quotDetailId = 0;

	List<RmcQuotItemDetail> rmcQuotItemList;
	List<RmcQuotTemp> rmcQuotTempList;

	// Ajax call 4 Jan 2019
	@RequestMapping(value = "/getRmcQuotItemDetail", method = RequestMethod.GET)
	public @ResponseBody List<RmcQuotItemDetail> getRmcQuotItemDetail(HttpServletRequest request,
			HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int indexKey = Integer.parseInt(request.getParameter("indexKey"));
		quotDetailId = Integer.parseInt(request.getParameter("quotDetailId"));

		System.out.println("quotDetailId" + quotDetailId);
		rmcQuotItemList = new ArrayList<>();
		if (indexKey >= 0) {
			map = new LinkedMultiValueMap<String, Object>();
			map.add("quotDetailId", quotDetailId);

			RmcQuotTemp[] rmcItemQuot = rest.postForObject(Constants.url + "getTempItemDetailByQuotDetailId", map,
					RmcQuotTemp[].class);
			rmcQuotTempList = new ArrayList<RmcQuotTemp>(Arrays.asList(rmcItemQuot));

			for (int i = 0; i < rmcQuotTempList.size(); i++) {

				RmcQuotItemDetail rmc = new RmcQuotItemDetail();
				rmc.setAmt(rmcQuotTempList.get(i).getAmt());
				rmc.setItemCode(rmcQuotTempList.get(i).getItemCode());
				rmc.setItemDesc(rmcQuotTempList.get(i).getItemDesc());
				rmc.setItemDetailId(rmcQuotTempList.get(i).getItemDetailId());
				rmc.setItemId(rmcQuotTempList.get(i).getItemId());
				rmc.setItemName(rmcQuotTempList.get(i).getItemName());
				rmc.setItemOpRate(rmcQuotTempList.get(i).getItemOpRate());
				rmc.setItemWt(rmcQuotTempList.get(i).getItemWt());
				rmc.setRmId(rmcQuotTempList.get(i).getRmId());
				rmc.setRmQty(rmcQuotTempList.get(i).getRmQty());
				rmc.setUnitRate(rmcQuotTempList.get(i).getUnitRate());
				rmc.setUom(rmcQuotTempList.get(i).getUom());
				rmcQuotItemList.add(rmc);

			}

			if (rmcQuotItemList.isEmpty()) {

				map = new LinkedMultiValueMap<String, Object>();
				map.add("itemId", itemId);

				RmcQuotItemDetail[] rmcItem = rest.postForObject(Constants.url + "getRmcQuotItemDetail", map,
						RmcQuotItemDetail[].class);
				rmcQuotItemList = new ArrayList<RmcQuotItemDetail>(Arrays.asList(rmcItem));
			}
		}
		System.err.println("Ajax rmcQuotItemList  List " + rmcQuotItemList.toString());

		return rmcQuotItemList;

	}

	// setRmcQuotItemDetail 4 Jan 2019 if changes in rm quantity and opRate
	@RequestMapping(value = "/setRmcQuotItemDetail", method = RequestMethod.GET)
	public void setRmcQuotItemDetail(HttpServletRequest request, HttpServletResponse response) {

		int detailId = Integer.parseInt(request.getParameter("detailId"));
		int index = Integer.parseInt(request.getParameter("index"));
		float opRate = Float.parseFloat(request.getParameter("opRate"));
		float rmQty = Float.parseFloat(request.getParameter("rmQty"));
		float unitRate = Float.parseFloat(request.getParameter("unitRate"));
		float amt = Float.parseFloat(request.getParameter("amt"));

		rmcQuotItemList.get(index).setAmt(amt);
		rmcQuotItemList.get(index).setItemOpRate(opRate);
		rmcQuotItemList.get(index).setRmQty(rmQty);
		rmcQuotItemList.get(index).setUnitRate(unitRate);
		rmcQuotItemList.get(index).setAmt(amt);
		System.err.println("Setted ");
	}

	// getMixItemRate

	@RequestMapping(value = "/getMixItemRate", method = RequestMethod.GET)
	public @ResponseBody GetItemWithEnq getMixItemRate(HttpServletRequest request, HttpServletResponse response) {
		GetItemWithEnq itemEnq = null;
		try {
			System.err.println("Mix Rate get ");
			float itemRate = 0;

			int itemId = rmcQuotItemList.get(0).getItemId();
			System.err.println("Item itemId " + itemId);

			for (int i = 0; i < rmcQuotItemList.size(); i++) {
				itemRate = itemRate + rmcQuotItemList.get(i).getAmt();
			}
			System.err.println("Item Rate " + itemRate);
			for (int i = 0; i < enqItemList.size(); i++) {

				if (enqItemList.get(i).getItemId() == itemId) {
					itemEnq = enqItemList.get(i);
					System.err.println("itemEnq  matched setted " + itemEnq.toString());
					enqItemList.get(i).setItemRate1(itemRate);
					break;
				}
			}

			itemEnq.setItemRate1(itemRate);

			if (quotDetailId != 0) {

				for (int i = 0; i < rmcQuotItemList.size(); i++) {
					RmcQuotTemp rmc = new RmcQuotTemp();

					rmc.setAmt(rmcQuotItemList.get(i).getAmt());
					rmc.setItemCode(rmcQuotItemList.get(i).getItemCode());
					rmc.setItemDesc(rmcQuotItemList.get(i).getItemDesc());
					rmc.setItemDetailId(rmcQuotItemList.get(i).getItemDetailId());
					rmc.setItemId(rmcQuotItemList.get(i).getItemId());
					rmc.setItemName(rmcQuotItemList.get(i).getItemName());
					rmc.setItemOpRate(rmcQuotItemList.get(i).getItemOpRate());
					rmc.setItemWt(rmcQuotItemList.get(i).getItemWt());
					rmc.setRmId(rmcQuotItemList.get(i).getRmId());
					rmc.setRmQty(rmcQuotItemList.get(i).getRmQty());
					rmc.setUnitRate(rmcQuotItemList.get(i).getUnitRate());
					rmc.setUom(rmcQuotItemList.get(i).getUom());
					rmc.setQuotDetailId(quotDetailId);
					rmc.setDelStatus(1);

					rmcQuotTempList.add(rmc);

				}

				System.out.println(rmcQuotTempList.toString());

				List<RmcQuotTemp> rmcItemQuot = rest.postForObject(Constants.url + "saveTempItemDetail",
						rmcQuotTempList, List.class);
				System.out.println("rmcItemQuot" + rmcItemQuot.toString());
			}

		} catch (Exception e) {
			System.err.println("Exce in getMixItemRate " + e.getMessage());
			e.printStackTrace();

		}
		return itemEnq;

	}

	@RequestMapping(value = "/showQuotations", method = RequestMethod.GET)
	public ModelAndView showQuotations(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotList");

			model.addObject("title", "Generate Quotation");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("statusList", "0");

			GetQuotHeads[] quotArray = rest.postForObject(Constants.url + "getQuotHeaders", map, GetQuotHeads[].class);
			quotList = new ArrayList<GetQuotHeads>(Arrays.asList(quotArray));
			System.err.println("quotList" + quotList.toString());

			model.addObject("quotList", quotList);

			model.addObject("quotHeadIdPdf", quotHeadIdPdf);
			model.addObject("pdfCustId", pdfCustId);

		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showQuotations1", method = RequestMethod.GET)
	public ModelAndView showQuotations1(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotList");

			model.addObject("title", "Add Customer P.O.");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("statusList", "1");

			GetQuotHeads[] quotArray = rest.postForObject(Constants.url + "getQuotHeaders", map, GetQuotHeads[].class);
			quotList = new ArrayList<GetQuotHeads>(Arrays.asList(quotArray));
			System.err.println("quotList" + quotList.toString());

			model.addObject("quotList", quotList);

		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showQuotationsCustWise", method = RequestMethod.GET)
	public ModelAndView showQuotationsCustWise(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotListNew");

			model.addObject("title", "Quotation List");
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	List<GetQuotHeader> getQuotList = new ArrayList<>();

	@RequestMapping(value = "/getQuotListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetQuotHeader> getOrderListBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getTempQuotHeader");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		// int stat=Integer.parseInt(request.getParameter("statusList"));

		String[] statusList = request.getParameterValues("statusList");

		System.out.println("values are" + plantId + custId + fromDate + toDate);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < statusList.length; i++) {
			sb = sb.append(statusList[i] + ",");

		}
		String items = sb.toString();
		items = items.substring(0, items.length() - 1);

		map.add("statusList", items);

		map.add("plantId", plantId);
		map.add("custId", custId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		// map.add("status", 0);

		GetQuotHeader[] ordHeadArray = rest.postForObject(Constants.url + "getQuotListByPlantIdAndCustIdAndStatus", map,
				GetQuotHeader[].class);
		getQuotList = new ArrayList<GetQuotHeader>(Arrays.asList(ordHeadArray));

		System.out.println("quot list data " + getQuotList.toString());

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Company Name");
		rowData.add("Quotation No");
		rowData.add("Quotation Date");
		rowData.add("Employee Name");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < getQuotList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + getQuotList.get(i).getCompName());

			rowData.add("" + getQuotList.get(i).getQuotNo());
			rowData.add("" + getQuotList.get(i).getQuotDate());
			rowData.add("" + getQuotList.get(i).getUsrName());

			/*
			 * String status1=null; int stat=getEnqList.get(i).getEnqStatus(); if (stat== 0)
			 * { status1 = "Enquiry Generated"; } else if (stat == 1) { status1 =
			 * "Quotation Generated"; } else {
			 * 
			 * status1 = "PO Generated"; }
			 * 
			 * rowData.add("" + status1 );
			 */

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Customerwise Quotation List");

		return getQuotList;
	}

	@RequestMapping(value = "/showQuotListPdf/{fromDate}/{toDate}/{custId}/{plantId}", method = RequestMethod.GET)
	public void showDateWisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			@PathVariable("custId") int custId, @PathVariable("plantId") int plantId, HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException {
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

			hcell = new PdfPCell(new Phrase("Company Name ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Quotation No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Quotation date ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Employee Name ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetQuotHeader work : getQuotList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCompName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getQuotNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getQuotDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getUsrName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				/*
				 * String status1=null; int stat=work.getEnqStatus(); if (stat== 0) { status1 =
				 * "Enquiry Generated"; } else if (stat == 1) { status1 = "Quotation Generated";
				 * } else {
				 * 
				 * status1 = "PO Generated"; }
				 * 
				 * cell = new PdfPCell(new Phrase("" + status1, headFont));
				 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPaddingRight(2);
				 * cell.setPadding(3); table.addCell(cell);
				 */

			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu(Datewise Quotation List)\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());

			String plantname = null;
			String custName = null;

			if (plantId == 0) {
				plantname = "All";

			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("plantId", plantId);

				Plant getPlant = rest.postForObject(Constants.url + "getPlantByPlantId", map, Plant.class);
				plantname = getPlant.getPlantName();
				System.out.println("plantname" + plantname);

			}
			if (custId == 0) {
				custName = "All";

			} else {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				System.out.println();

				map.add("custId", custId);

				GetCust getcus1 = rest.postForObject(Constants.url + "getCustomerByCustId", map, GetCust.class);
				custName = getcus1.getCustName();
				System.out.println("custName" + custName);

			}
			Paragraph p2 = new Paragraph(
					"FromDate:" + fromDate + " ToDate:" + toDate + "  Plant:" + plantname + "  Customer:" + custName,
					headFont);
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p2);
			document.add(new Paragraph("\n"));

			document.add(table);

			/*
			 * Paragraph p1 = new Paragraph("Total:"+tot, headFont);
			 * p1.setAlignment(Element.ALIGN_CENTER); document.add(p1); document.add(new
			 * Paragraph("\n"));
			 */

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

	// editQuot
	List<GetItemWithEnq> newItemList;// items that are not in quotation ie enquiry but in m_item table

	List<GetItemWithEnq> enqItemList;

	/*
	 * @RequestMapping(value =
	 * "/editQuot/{quotHeadId}/plantId}/{custId}/{enqHeadId}", method =
	 * RequestMethod.GET) public ModelAndView editQuot(HttpServletRequest request,
	 * HttpServletResponse response, @PathVariable int quotHeadId,
	 * 
	 * @PathVariable int plantId, @PathVariable int custId, @PathVariable int
	 * enqHeadId) {
	 * 
	 * ModelAndView model = null; try {
	 * 
	 * model = new ModelAndView("quot/editQuot");
	 * 
	 * model.addObject("title", "Quotation Edit");
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
	 * Object>();
	 * 
	 * map.add("plantId", plantId); map.add("enqHeadId", enqHeadId);
	 * 
	 * GetItemWithEnq[] itemArray = rest.postForObject(Constants.url +
	 * "getItemsAndEnqItemList", map, GetItemWithEnq[].class); itemList = new
	 * ArrayList<GetItemWithEnq>(Arrays.asList(itemArray)); newItemList = new
	 * ArrayList<GetItemWithEnq>(); enqItemList = new ArrayList<GetItemWithEnq>();
	 * System.err.println(" Original Item List " + itemList.toString());
	 * 
	 * List<Integer> indexList = new ArrayList<>(); for (int i = 0; i <
	 * itemList.size(); i++) {
	 * 
	 * if (itemList.get(i).getQuotQty() == 0) {
	 * 
	 * newItemList.add(itemList.get(i)); } else {
	 * 
	 * enqItemList.add(itemList.get(i)); }
	 * 
	 * }
	 * 
	 * System.err.println("enqItemList " + enqItemList.toString());
	 * 
	 * System.err.println("newItemList " + newItemList.toString());
	 * 
	 * model.addObject("itemList", enqItemList); model.addObject("newItemList",
	 * newItemList);
	 * 
	 * map = new LinkedMultiValueMap<String, Object>(); map.add("plantId", plantId);
	 * 
	 * Cust[] custArray = rest.postForObject(Constants.url + "getCustListByPlant",
	 * map, Cust[].class); custList = new ArrayList<Cust>(Arrays.asList(custArray));
	 * model.addObject("custList", custList);
	 * 
	 * // System.err.println("cust List " + custList.toString());
	 * 
	 * Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList",
	 * Plant[].class); plantList = new ArrayList<Plant>(Arrays.asList(plantArray));
	 * model.addObject("plantList", plantList);
	 * 
	 * // System.err.println("Plant List " + plantList.toString());
	 * 
	 * model.addObject("plantId", plantId); model.addObject("custId", custId);
	 * 
	 * model.addObject("custName", custName);
	 * model.addObject("plantName",plantName);
	 * 
	 * map = new LinkedMultiValueMap<String, Object>(); map.add("custId", custId);
	 * Project[] projArray = rest.postForObject(Constants.url +
	 * "getProjectByCustId", map, Project[].class); projList = new
	 * ArrayList<Project>(Arrays.asList(projArray));
	 * 
	 * model.addObject("projList", projList);
	 * 
	 * PaymentTerm[] payTermArray = rest.getForObject(Constants.url +
	 * "getAllPaymentTermList", PaymentTerm[].class); payTermList = new
	 * ArrayList<PaymentTerm>(Arrays.asList(payTermArray));
	 * 
	 * model.addObject("payTermList", payTermList);
	 * 
	 * map = new LinkedMultiValueMap<String, Object>(); map.add("docId", 2);
	 * 
	 * DocTermHeader[] docTermArray = rest.postForObject(Constants.url +
	 * "getDocHeaderByDocId", map, DocTermHeader[].class); docTermList = new
	 * ArrayList<DocTermHeader>(Arrays.asList(docTermArray));
	 * 
	 * model.addObject("docTermList", docTermList);
	 * 
	 * map = new LinkedMultiValueMap<String, Object>(); map.add("quotHeadId",
	 * quotHeadId);
	 * 
	 * QuotHeader quotHeader = rest.postForObject(Constants.url +
	 * "getQuotHeaderByQuotHeadId", map, QuotHeader.class);
	 * quotHeader.setQuotDate(DateConvertor.convertToDMY(quotHeader.getQuotDate()));
	 * model.addObject("quotHeader", quotHeader);
	 * 
	 * } catch (Exception e) { System.err.println("Exce in /showQuotations" +
	 * e.getMessage()); e.printStackTrace(); } return model;
	 * 
	 * }
	 */

	@RequestMapping(value = "/editQuotationDetail/{quotHeadId}/{plantId}/{custId}/{enqHeadId}", method = RequestMethod.GET)
	public ModelAndView editQuot(HttpServletRequest request, HttpServletResponse response, @PathVariable int quotHeadId,
			@PathVariable int plantId, @PathVariable int custId, @PathVariable int enqHeadId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/editQuot");

			model.addObject("title", "Quotation Edit");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("enqHeadId", enqHeadId);

			GetItemWithEnq[] itemArray = rest.postForObject(Constants.url + "getItemsAndEnqItemList", map,
					GetItemWithEnq[].class);
			itemList = new ArrayList<GetItemWithEnq>(Arrays.asList(itemArray));
			newItemList = new ArrayList<GetItemWithEnq>();
			enqItemList = new ArrayList<GetItemWithEnq>();
			System.err.println(" Original Item List " + itemList.toString());

			List<Integer> indexList = new ArrayList<>();
			for (int i = 0; i < itemList.size(); i++) {

				if (itemList.get(i).getQuotQty() == 0) {

					newItemList.add(itemList.get(i));
				} else {

					enqItemList.add(itemList.get(i));
				}

			}

			System.err.println("enqItemList " + enqItemList.toString());

			System.err.println("newItemList " + newItemList.toString());

			model.addObject("itemList", enqItemList);
			model.addObject("newItemList", newItemList);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("plantId", plantId);

			Cust[] custArray = rest.postForObject(Constants.url + "getCustListByPlant", map, Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			model.addObject("custList", custList);

			// System.err.println("cust List " + custList.toString());

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));
			model.addObject("plantList", plantList);

			// System.err.println("Plant List " + plantList.toString());

			model.addObject("plantId", plantId);
			model.addObject("custId", custId);
			/*
			 * model.addObject("custName", custName);
			 * model.addObject("plantName",plantName);
			 */
			map = new LinkedMultiValueMap<String, Object>();
			map.add("custId", custId);
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));

			model.addObject("projList", projList);

			PaymentTerm[] payTermArray = rest.getForObject(Constants.url + "getAllPaymentTermList",
					PaymentTerm[].class);
			payTermList = new ArrayList<PaymentTerm>(Arrays.asList(payTermArray));

			model.addObject("payTermList", payTermList);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("docId", 2);

			DocTermHeader[] docTermArray = rest.postForObject(Constants.url + "getDocHeaderByDocId", map,
					DocTermHeader[].class);
			docTermList = new ArrayList<DocTermHeader>(Arrays.asList(docTermArray));

			model.addObject("docTermList", docTermList);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("quotHeadId", quotHeadId);

			QuotHeader quotHeader = rest.postForObject(Constants.url + "getQuotHeaderByQuotHeadId", map,
					QuotHeader.class);
			quotHeader.setQuotDate(DateConvertor.convertToDMY(quotHeader.getQuotDate()));
			model.addObject("quotHeader", quotHeader);

		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}
	// getNewItemsForQuotation

	@RequestMapping(value = "/getNewItemsForQuotation", method = RequestMethod.GET)
	public @ResponseBody List<GetItemWithEnq> getNewItemsForQuotation(HttpServletRequest request,
			HttpServletResponse response) {
		System.err.println(" Inside IgetNewItemsForQuotation ");
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		float quotQty = Float.parseFloat(request.getParameter("quotQty"));

		int isDelete = Integer.parseInt(request.getParameter("isDelete"));
		int index = Integer.parseInt(request.getParameter("index"));

		int quotHeaderId = Integer.parseInt(request.getParameter("quotHeaderId"));

		if (isDelete == 0) {
			for (int i = 0; i < newItemList.size(); i++) {
				if (!enqItemList.contains(newItemList.get(i))) {

					if (newItemList.get(i).getItemId() == itemId) {
						System.err.println("Item Id matched");

						newItemList.get(i).setEnqUomId(newItemList.get(i).getUomId());
						newItemList.get(i).setEnqUomName(newItemList.get(i).getUomName());
						newItemList.get(i).setQuotQty(quotQty);
						enqItemList.get(0).setTempMsg("Item Added Successfully");
						enqItemList.add(newItemList.get(i));

						System.err.println("Newly added item in quot " + newItemList.get(i).toString());
						// newItemList.remove(i);
						break;
					}
				} else {

					System.err.println("Already added " + itemId);

				}
			}
		} // end of if isDelete=0
		else {
			System.err.println("IS delete ==1");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotHeadId", quotHeaderId);
			map.add("itemId", itemId);

			Info errMsg = rest.postForObject(Constants.url + "deleteQuotDetail", map, Info.class);
			enqItemList.get(0).setTempMsg("Item Deleted Successfully");

			enqItemList.remove(index);
		}

		System.err.println("Ajax getNewItemsForQuotation  List size " + enqItemList.size());

		System.err.println("Ajax getNewItemsForQuotation  List " + enqItemList.toString());

		return enqItemList;

	}

	// Ajax call
	@RequestMapping(value = "/getProjectByCustId", method = RequestMethod.GET)
	public @ResponseBody List<Project> getProjectByCustId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int custId = Integer.parseInt(request.getParameter("custId"));

		map.add("custId", custId);

		Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
		projList = new ArrayList<Project>(Arrays.asList(projArray));

		System.err.println("Ajax Proj  List " + projList.toString());

		return projList;

	}

	// Ajax call
	@RequestMapping(value = "/getItemsAndEnqItemList", method = RequestMethod.GET)
	public @ResponseBody List<GetItemWithEnq> getItemsAndEnqItemList(HttpServletRequest request,
			HttpServletResponse response) {

		/*
		 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
		 * Object>();
		 * 
		 * int plantId = Integer.parseInt(request.getParameter("plantId")); int
		 * enqHeadId = Integer.parseInt(request.getParameter("enqHeadId"));
		 * 
		 * map.add("plantId", plantId); map.add("enqHeadId", enqHeadId);
		 * 
		 * GetItemWithEnq[] itemArray = rest.postForObject(Constants.url +
		 * "getItemsAndEnqItemList", map, GetItemWithEnq[].class); itemList = new
		 * ArrayList<GetItemWithEnq>(Arrays.asList(itemArray));
		 * 
		 * System.err.println("Ajax Item list for km onchange  List " +
		 * itemList.toString());
		 */
		return enqItemList;

	}

	@RequestMapping(value = "/getDocTermDetail", method = RequestMethod.GET)
	public @ResponseBody DocTermHeader getDocTermDetail(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int termId = Integer.parseInt(request.getParameter("termId"));

		map.add("termId", termId);
		DocTermHeader docTerm = rest.postForObject(Constants.url + "getDocHeaderByTermId", map, DocTermHeader.class);

		System.err.println("Ajax getDocTermDetail " + docTerm.toString());

		return docTerm;

	}

	// updateQuotation Form Action

	@RequestMapping(value = "/updateQuotation", method = RequestMethod.POST)
	public String updateQuotationProcess(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());

			model = new ModelAndView("quot/quotList");
			model.addObject("title", "Quotation List");

			int quotHeadId = Integer.parseInt(request.getParameter("quotHeadId"));
			int quotHeadStatus = Integer.parseInt(request.getParameter("quotHeadStatus"));
			System.err.println("quotHeadStatus" + quotHeadStatus);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotHeadId", quotHeadId);

			QuotHeader quotHeader = rest.postForObject(Constants.url + "getQuotHeaderByQuotHeadId", map,
					QuotHeader.class);
			int custId = Integer.parseInt(request.getParameter("cust_name"));
			map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", custId);

			Cust cust = rest.postForObject(Constants.url + "getCustByCustId", map, Cust.class);

			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int payTermId = Integer.parseInt(request.getParameter("pay_term_id"));
			String transportTerms = request.getParameter("trans_term");
			String otherRemark1 = request.getParameter("quot_remark");
			int projId = Integer.parseInt(request.getParameter("proj_id"));

			int noOfTolls = Integer.parseInt(request.getParameter("no_of_tolls"));
			int is_tax_inc = Integer.parseInt(request.getParameter("is_tax_inc"));
			Float tollCost = Float.parseFloat(request.getParameter("toll_amt"));
			Float otherCost = Float.parseFloat(request.getParameter("other_cost"));
			int quotTermId = Integer.parseInt(request.getParameter("quot_doc_term_id"));
			float noOfKm = Float.parseFloat(request.getParameter("no_of_km"));
			String quotDate = request.getParameter("quot_date");
			String payTerms = request.getParameter("pay_term_name");

			quotHeader.setUserId(1);// to be get from session who logged in to do this activity

			quotHeader.setStatus(quotHeadStatus);

			quotHeader.setPayTermId(payTermId);
			quotHeader.setTransportTerms(transportTerms);

			quotHeader.setPayTerms(payTerms);
			quotHeader.setOtherRemark1(otherRemark1);
			quotHeader.setProjId(projId);
			quotHeader.setNoOfTolls(noOfTolls);
			quotHeader.setTollCost(tollCost);
			quotHeader.setOtherCost(otherCost);
			quotHeader.setQuotTermId(quotTermId);
			quotHeader.setNoOfKm(noOfKm);
			quotHeader.setExDate2(curDate);
			quotHeader.setTaxValue(is_tax_inc);
			// quotHeader.setCompanyId(companyId);
			quotHeader.setQuotDate(DateConvertor.convertToYMD(quotDate));
			List<QuotDetail> quotDetList = quotHeader.getQuotDetailList();

			System.err.println("Header  " + quotHeader.toString());
			String[] selectItem = request.getParameterValues("selectItem");

			List<QuotDetail> tempQDetailList = new ArrayList<>();

			//
			int flag;

			for (int j = 0; j < enqItemList.size(); j++) {

				flag = 0;
				for (int i = 0; i < quotDetList.size(); i++) {

					if (enqItemList.get(j).getItemId() == quotDetList.get(i).getItemId()) {

						flag = 1;

						float quotQty = Float
								.parseFloat(request.getParameter("quot_qty" + quotDetList.get(i).getItemId()));
						float transCost = Float
								.parseFloat(request.getParameter("trans_cost" + quotDetList.get(i).getItemId()));
						float otherCostDetail = Float
								.parseFloat(request.getParameter("other_cost" + quotDetList.get(i).getItemId()));
						float taxableValue = Float
								.parseFloat(request.getParameter("taxable_amt" + quotDetList.get(i).getItemId()));
						float taxValue = Float
								.parseFloat(request.getParameter("tax_amt" + quotDetList.get(i).getItemId()));
						float total = Float
								.parseFloat(request.getParameter("final_amt" + quotDetList.get(i).getItemId()));
						float otherCostAfterTax = Float
								.parseFloat(request.getParameter("oth_cost_aft_tax" + quotDetList.get(i).getItemId()));

						QuotDetail detail;

						detail = quotDetList.get(i);

						detail.setQuotDetailId(quotDetList.get(i).getQuotDetailId());
						detail.setQuotHeadId(quotDetList.get(i).getQuotHeadId());
						detail.setItemId(quotDetList.get(i).getItemId());
						detail.setQuotQty(quotQty);
						detail.setRate(enqItemList.get(j).getItemRate1());
						detail.setTaxableValue(taxableValue);
						detail.setTaxValue(taxValue);
						detail.setTaxId(enqItemList.get(j).getTaxId());
						// detail.setCgstPer(enqItemList.get(j).getCgst());
						// detail.setSgstPer(enqItemList.get(j).getSgst());
						// detail.setCgstValue(cgstValue);
						// detail.setSgstValue(sgstValue);

						detail.setStatus(1);

						detail.setEnqDetailId(quotDetList.get(i).getEnqDetailId());
						detail.setDelStatus(1);
						detail.setOtherCost(otherCostDetail);
						// exint1,2,3
						detail.setExVar1("Na");
						detail.setExVar2("Na");
						detail.setExVar3("Na");
						detail.setExDate1(curDate);
						detail.setExDate2(curDate);

						// exBool1,2,3
						detail.setConFactor(1);
						detail.setConvQty(1);
						detail.setQuotUomId((int) enqItemList.get(j).getEnqUomId());

						// detail.setIgstPer(enqItemList.get(j).getIgst());
						// detail.setIgstValue(igstValue);

						detail.setTotal(total);
						detail.setTollCost(tollCost);
						detail.setTransCost(transCost);
						detail.setOtherCostBeforeTax(0);
						detail.setOtherCostAfterTax(otherCostAfterTax);

						detail.setRoyaltyRate(enqItemList.get(j).getRoyaltyRate());
						detail.setNoOfKm(noOfKm);

						if (taxValue > 0) {
							if (cust.getIsSameState() == 1) {

								detail.setCgstPer(enqItemList.get(j).getCgst());
								detail.setSgstPer(enqItemList.get(j).getSgst());

								detail.setCgstValue((taxValue / 2));
								detail.setSgstValue((taxValue / 2));

								detail.setIgstValue(0);
								// detail.setIgstPer(0);
							} else {
								detail.setIgstValue(taxValue);
								detail.setIgstPer(enqItemList.get(j).getIgst());

								detail.setCgstValue(0);
								detail.setSgstValue(0);

								// detail.setCgstPer(0);
								// detail.setSgstPer(0);

							}

						} else {

							if (cust.getIsSameState() == 1) {

								detail.setCgstPer(enqItemList.get(j).getCgst());
								detail.setSgstPer(enqItemList.get(j).getSgst());

								detail.setCgstValue(0);
								detail.setSgstValue(0);

								detail.setIgstValue(0);
								// detail.setIgstPer(0);
							} else {
								detail.setIgstValue(taxValue);
								detail.setIgstPer(enqItemList.get(j).getIgst());

								detail.setCgstValue(0);
								detail.setSgstValue(0);
								detail.setIgstValue(0);
								// detail.setCgstPer(0);
								// detail.setSgstPer(0);

							}

						}

						tempQDetailList.add(detail);

					} // end of if

				} // end of inner for

				if (flag == 0) {
					float quotQty = Float.parseFloat(request.getParameter("quot_qty" + enqItemList.get(j).getItemId()));
					float transCost = Float
							.parseFloat(request.getParameter("trans_cost" + enqItemList.get(j).getItemId()));
					float otherCostDetail = Float
							.parseFloat(request.getParameter("other_cost" + enqItemList.get(j).getItemId()));
					float taxableValue = Float
							.parseFloat(request.getParameter("taxable_amt" + enqItemList.get(j).getItemId()));
					float taxValue = Float.parseFloat(request.getParameter("tax_amt" + enqItemList.get(j).getItemId()));
					float total = Float.parseFloat(request.getParameter("final_amt" + enqItemList.get(j).getItemId()));
					float otherCostAfterTax = Float
							.parseFloat(request.getParameter("oth_cost_aft_tax" + enqItemList.get(j).getItemId()));

					QuotDetail detail = new QuotDetail();

					detail.setQuotQty(quotQty);
					detail.setTransCost(transCost);
					detail.setOtherCost(otherCostDetail);
					detail.setTaxableValue(taxableValue);
					detail.setTaxValue(taxValue);
					detail.setTotal(total);
					detail.setOtherCostAfterTax(otherCostAfterTax);
					detail.setTollCost(tollCost);

					detail.setNoOfKm(noOfKm);
					detail.setRate(enqItemList.get(j).getItemRate1());

					detail.setExDate2(curDate);

					detail.setStatus(1);

					if (taxValue > 0) {
						if (cust.getIsSameState() == 1) {

							detail.setCgstPer(enqItemList.get(j).getCgst());
							detail.setSgstPer(enqItemList.get(j).getSgst());

							detail.setCgstValue((taxValue / 2));
							detail.setSgstValue((taxValue / 2));

							detail.setIgstValue(0);
							// detail.setIgstPer(0);
						} else {
							detail.setIgstValue(taxValue);
							detail.setIgstPer(enqItemList.get(j).getIgst());

							detail.setCgstValue(0);
							detail.setSgstValue(0);

							// detail.setCgstPer(0);
							// detail.setSgstPer(0);

						}

					} else {

						if (cust.getIsSameState() == 1) {

							detail.setCgstPer(enqItemList.get(j).getCgst());
							detail.setSgstPer(enqItemList.get(j).getSgst());

							detail.setCgstValue(0);
							detail.setSgstValue(0);

							detail.setIgstValue(0);
							// detail.setIgstPer(0);
						} else {
							detail.setIgstValue(taxValue);
							detail.setIgstPer(enqItemList.get(j).getIgst());

							detail.setCgstValue(0);
							detail.setSgstValue(0);
							detail.setIgstValue(0);
							// detail.setCgstPer(0);
							// detail.setSgstPer(0);

						}

					}

					detail.setCgstPer(enqItemList.get(j).getCgst());
					detail.setSgstPer(enqItemList.get(j).getSgst());

					detail.setConFactor(1);
					detail.setDelStatus(1);
					detail.setConvQty(1);
					detail.setIgstPer(enqItemList.get(j).getIgst());
					detail.setItemId(enqItemList.get(j).getItemId());
					detail.setQuotUomId(enqItemList.get(j).getUomId());

					detail.setRoyaltyRate(enqItemList.get(j).getRoyaltyRate());
					detail.setStatus(1);
					detail.setTaxId(enqItemList.get(j).getTaxId());
					detail.setOtherCostBeforeTax(0);

					detail.setExVar1("na");
					detail.setExVar2("na");
					detail.setExVar3("na");

					detail.setExDate1(curDate);
					detail.setExDate2(curDate);

					tempQDetailList.add(detail);

				} // end of if flag=0
			} // end of outer for

			quotHeader.setQuotDetailList(tempQDetailList);

			QuotHeader quotHeadUpdateRes = rest.postForObject(Constants.url + "saveQuotHeaderAndDetail", quotHeader,
					QuotHeader.class);

			System.err.println("quotHeadUpdateRes  " + quotHeadUpdateRes.toString());

			if (quotHeadUpdateRes != null) {

				quotHeadIdPdf = quotHeadUpdateRes.getQuotHeadId();

				pdfCustId = quotHeadUpdateRes.getCustId();
			}

		} catch (Exception e) {
			System.err.println("Exce in upd qtn process " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showQuotations";
	}

	// Deletall done by harsha

	@RequestMapping(value = "/deleteRecordofQuatation", method = RequestMethod.POST)
	public String deleteRecordofCompany(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] quotIds = request.getParameterValues("quotIds");
			System.out.println("id are" + quotIds);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < quotIds.length; i++) {
				sb = sb.append(quotIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiQuot", map, Info.class);

			System.err.println("inside method");
		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofQuota @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showQuotations";
	}

	@RequestMapping(value = "/deleteRecordofQuotations", method = RequestMethod.POST)
	public String deleteRecordofQuotations(HttpServletRequest request, HttpServletResponse response) {
		try {

			System.out.println("Hello            oooooooooooo");
			String[] quotIds = request.getParameterValues("selectQuatationToDelete");
			System.out.println("id are" + quotIds);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < quotIds.length; i++) {
				sb = sb.append(quotIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			System.err.println("quotIds" + items.toString());

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiQuot", map, Info.class);
			System.err.println("inside method /deleteRecordofQuotations");
		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofQuotations @OrderController  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showQuotationsCustWise";
	}

	private Dimension format = PD4Constants.A4;
	private boolean landscapeValue = false;
	private int topValue = 8;
	private int leftValue = 0;
	private int rightValue = 0;
	private int bottomValue = 8;
	private String unitsValue = "m";
	private String proxyHost = "";
	private int proxyPort = 0;

	private int userSpaceWidth = 750;
	private static int BUFFER_SIZE = 1024;

	@RequestMapping(value = "/pdfQuot", method = RequestMethod.GET)
	public void showPDF(HttpServletRequest request, HttpServletResponse response) {

		String url = request.getParameter("url");
		System.out.println("URL " + url);

		// File f = new File("/home/lenovo/quot.pdf");

		File f = new File("/opt/apache-tomcat-9.0.4/webapps/uploads/quotation.pdf");

		// File f = new
		// File("/Users/MIRACLEINFOTAINMENT/ATS/uplaods/reports/ordermemo221.pdf");

		System.out.println("I am here " + f.toString());
		try {
			runConverter(Constants.ReportURL + url, f, request, response);
			System.out.println("Come on lets get ");
		} catch (IOException e) {
			// TODO Auto-generated catch block

			System.out.println("Pdf conversion exception " + e.getMessage());
		}

		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");

		// String filename = "/home/lenovo/quot.pdf";
		String filename = "/opt/apache-tomcat-9.0.4/webapps/uploads/quotation.pdf";

		String filePath = "/opt/apache-tomcat-9.0.4/webapps/uploads/quotation.pdf";
		// String filePath = "/home/lenovo/quot.pdf";
		// "/Users/MIRACLEINFOTAINMENT/ATS/uplaods/reports/ordermemo221.pdf";

		// construct the complete absolute path of the file
		String fullPath = appPath + filePath;
		File downloadFile = new File(filePath);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(downloadFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/pdf";
			}
			System.out.println("MIME type: " + mimeType);

			String headerKey = "Content-Disposition";

			// response.addHeader("Content-Disposition", "attachment;filename=report.pdf");
			response.setContentType("application/pdf");

			// get output stream of the response
			OutputStream outStream;

			outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (quotHeadIdPdf != 0) {
				try {

					final String emailSMTPserver = "smtp.gmail.com";
					final String emailSMTPPort = "587";
					final String mailStoreType = "imaps";
					final String username = "atsinfosoft@gmail.com";
					final String password = "atsinfosoft@123";

					System.out.println("username" + username);
					System.out.println("password" + password);

					Properties props = new Properties();
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.socketFactory.port", "465");
					props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port", "587");

					Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					});

					try {
						Store mailStore = session.getStore(mailStoreType);
						mailStore.connect(emailSMTPserver, username, password);

						String mes = " Hello Sir";

						System.out.println("pdfcustId======================================" + pdfCustId);
						MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

						map.add("custId", pdfCustId);

						Cust editCust = rest.postForObject(Constants.url + "getCustByCustId", map, Cust.class);

						String address = editCust.getCustEmail();
						System.out.println("Email Send To" + editCust.getCustEmail());

						String subject = "  ";

						Message mimeMessage = new MimeMessage(session);
						mimeMessage.setFrom(new InternetAddress(username));
						mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
						mimeMessage.setSubject(subject);
						mimeMessage.setText(mes);

						mimeMessage.setFileName(filename);

						BodyPart mbodypart = new MimeBodyPart();
						Multipart multipart = new MimeMultipart();
						DataSource source = new FileDataSource(filename);
						mbodypart.setDataHandler(new DataHandler(source));
						mbodypart.setFileName(filename);
						multipart.addBodyPart(mbodypart);
						mimeMessage.setContent(multipart);

						Transport.send(mimeMessage);
						pdfCustId = 0;
						quotHeadIdPdf = 0;
					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}
	}

	private void runConverter(String urlstring, File output, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (urlstring.length() > 0) {
			if (!urlstring.startsWith("http://") && !urlstring.startsWith("file:")) {
				urlstring = "http://" + urlstring;
			}
			System.out.println("PDF URL " + urlstring);
			java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

			PD4ML pd4ml = new PD4ML();

			try {

				PD4PageMark footer = new PD4PageMark();
				footer.setPageNumberTemplate("page $[page] of $[total]");
				footer.setTitleAlignment(PD4PageMark.LEFT_ALIGN);
				footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN);
				footer.setInitialPageNumber(1);
				footer.setFontSize(8);
				footer.setAreaHeight(15);

				pd4ml.setPageFooter(footer);

			} catch (Exception e) {
				System.out.println("Pdf conversion method excep " + e.getMessage());
			}
			try {
				pd4ml.setPageSize(landscapeValue ? pd4ml.changePageOrientation(format) : format);
			} catch (Exception e) {
				System.out.println("Pdf conversion ethod excep " + e.getMessage());
			}

			if (unitsValue.equals("mm")) {
				pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));
			} else {
				pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue, rightValue));
			}

			pd4ml.setHtmlWidth(userSpaceWidth);

			pd4ml.render(urlstring, fos);

		}
	}
}
