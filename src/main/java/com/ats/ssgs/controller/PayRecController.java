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
import com.ats.ssgs.model.GetDatewiseReport;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.rec.GetPayRecoveryHead;
import com.ats.ssgs.model.rec.PayRecoveryDetail;
import com.ats.ssgs.model.rec.PayRecoveryHead;
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
public class PayRecController {
	List<Plant> plantList;

	RestTemplate rest = new RestTemplate();
	int isError = 0;
	List<GetPayRecoveryHead> recList;

	@RequestMapping(value = "/showPaymentRecoveryList", method = RequestMethod.GET)
	public ModelAndView showPaymentRecoveryList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("payrec/payreclist");

			model.addObject("title", "Payment Recovery List");

			GetPayRecoveryHead[] recHeadArray = rest.getForObject(Constants.url + "getPayRecoveryByStatus",
					GetPayRecoveryHead[].class);
			recList = new ArrayList<GetPayRecoveryHead>(Arrays.asList(recHeadArray));
			model.addObject("recList", recList);
			
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);


		} catch (Exception e) {

			System.err.println("exception In showPaymentRecoveryList at Txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/getPayRecoveryBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetPayRecoveryHead> getPayRecoveryBetDate(HttpServletRequest request,HttpServletResponse response) {

		System.err.println(" in getWeighListBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String plantId=request.getParameter("plantId");
		System.out.println("plant id "+plantId);

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		map.add("plantId",plantId);
		// map.add("status", 0);

		GetPayRecoveryHead[] recHeadArray = rest.postForObject(Constants.url + "getPayRecoveryBetDate", map,
				GetPayRecoveryHead[].class);
		recList = new ArrayList<GetPayRecoveryHead>(Arrays.asList(recHeadArray));
		System.out.println("payrec data:"+recList.toString());
		return recList;
		
		
		/*List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Customer name");
		rowData.add("Bill No.");
		rowData.add("Bill Date");
		rowData.add("Creadit Start Date");
		rowData.add("Followup Date");
		rowData.add("Paid Amount");
		rowData.add("Pending Amount");
		rowData.add(" Status");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < recList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			

			rowData.add("" + recList.get(i).getCustName());
			rowData.add("" + recList.get(i).getBillNo());
			rowData.add("" + recList.get(i).getBillDate());
			rowData.add("" + recList.get(i).getCreditDate1());
			rowData.add("" + recList.get(i).getCreditDate2());
			rowData.add("" + recList.get(i).getPaidAmt());
			rowData.add("" + recList.get(i).getPendingAmt());
			rowData.add("" + recList.get(i).getStatus());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "GetPaymwntRecoveryReport");

		return recList;
*/
		
	}

	
	
	@RequestMapping(value = "/getPayRecoveryBetDateVyPlantId", method = RequestMethod.GET)
	public @ResponseBody List<GetPayRecoveryHead> getPayRecoveryBetDateVyPlantId(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getWeighListBetDate1");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String plantId=request.getParameter("plantId");
		System.out.println("plant id "+plantId);

		
		map.add("plantId",plantId);
		// map.add("status", 0);

		GetPayRecoveryHead[] recHeadArray = rest.postForObject(Constants.url + "getPayRecoveryByStatus1", map,
				GetPayRecoveryHead[].class);
		recList = new ArrayList<GetPayRecoveryHead>(Arrays.asList(recHeadArray));
		System.out.println("payrec data:"+recList.toString());

		return recList;
	}

	
	
	

	// pp

	@RequestMapping(value = "/showPayRecPdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showPayRecPdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showPayRecPdf");
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

		PdfPTable table = new PdfPTable(10);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
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
			hcell = new PdfPCell(new Phrase("Taxable Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Total Amount", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			/*for (		GetPayRecoveryHead work : recList) {
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

				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}*/
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
	@RequestMapping(value = "/deleteRecordofPayRec", method = RequestMethod.POST)
	public String deleteRecordofPayRec(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] payHeadIds = request.getParameterValues("payHeadIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < payHeadIds.length; i++) {
				sb = sb.append(payHeadIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("payHeadIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiPayRec", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofPayRec @txncontroller  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showPaymentRecoveryList";
	}

	GetPayRecoveryHead editRec;

	@RequestMapping(value = "/editPayRec/{payHeadId}", method = RequestMethod.GET)
	public ModelAndView editPayRec(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int payHeadId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("payrec/editpayrec");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("payHeadId", payHeadId);
			editRec = rest.postForObject(Constants.url + "getPayRecoveryByPayHeadId", map, GetPayRecoveryHead.class);

			model.addObject("title", "Edit Payment Recovery");
			model.addObject("editRec", editRec);
			model.addObject("editRecDetail", editRec.getPayRecoveryDetailList());

		} catch (Exception e) {
			System.err.println("exception In editMat at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/getDetailEditForPayRec", method = RequestMethod.GET)
	public @ResponseBody PayRecoveryDetail getDetailEditForPayRec(HttpServletRequest request,
			HttpServletResponse response) {

		// int matDetailId = Integer.parseInt(request.getParameter("matDetailId"));
		int index = Integer.parseInt(request.getParameter("index"));

		return editRec.getPayRecoveryDetailList().get(index);

	}

	@RequestMapping(value = "/editInAddPayRec", method = RequestMethod.GET)
	public @ResponseBody List<PayRecoveryDetail> editInAddPayRec(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			if (isDelete == 1) {

				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				PayRecoveryDetail deleteDetail = editRec.getPayRecoveryDetailList().get(key);
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map = new LinkedMultiValueMap<String, Object>();

				map.add("payDetailId", deleteDetail.getPayDetailId());

				Info errMsg = rest.postForObject(Constants.url + "deletePayRecDetail", map, Info.class);

				editRec.getPayRecoveryDetailList().remove(key);

			} else if (isEdit == 1) {

				int typeTx = Integer.parseInt(request.getParameter("txType"));
				String txNo = request.getParameter("txNo");
				String paymentDate = request.getParameter("paymentDate");

				int index = Integer.parseInt(request.getParameter("index"));
				float paidAmt = Float.parseFloat(request.getParameter("paidAmt"));

				editRec.getPayRecoveryDetailList().get(index).setTxNo(txNo);
				editRec.getPayRecoveryDetailList().get(index).setTypeTx(typeTx);
				editRec.getPayRecoveryDetailList().get(index).setPaymentDate(DateConvertor.convertToDMY(paymentDate));
				editRec.getPayRecoveryDetailList().get(index).setPaidAmt(paidAmt);

			}

			else {

				int payHeadId = Integer.parseInt(request.getParameter("payHeadId"));
				String paymentDate = request.getParameter("paymentDate");

				int typeTx = Integer.parseInt(request.getParameter("txType"));

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				float paidAmt = Float.parseFloat(request.getParameter("paidAmt"));
				// Calendar cal = Calendar.getInstance();
				String curDate = dateFormat.format(new Date());

				PayRecoveryDetail detail = new PayRecoveryDetail();
				detail.setDelStatus(1);
				detail.setExBool1(1);
				detail.setExDate1(curDate);
				detail.setExInt1(1);
				detail.setExInt2(1);
				detail.setExVarchar1("NA");
				detail.setExVarchar2("NA");
				detail.setPaidAmt(paidAmt);
				detail.setPayHeadId(payHeadId);
				detail.setPaymentDate(paymentDate);
				detail.setTypeTx(typeTx);

				try {
					detail.setTxNo(request.getParameter("txNo"));

				} catch (Exception e) {
					detail.setTxNo("0");

				}

				try {
					detail.setRemark(request.getParameter("remark"));
				} catch (Exception e) {
					detail.setRemark("NA");
				}

				editRec.getPayRecoveryDetailList().add(detail);
				// getMatIssueDetailList.add(matIssueDetail);

				System.out.println("Inside Edit Raw Material");
			}

		} catch (Exception e) {
			System.err.println("Exce In temp  editRec.getPayRecoveryDetailList() List " + e.getMessage());
			e.printStackTrace();
		}

		return editRec.getPayRecoveryDetailList();

	}

	@RequestMapping(value = "/editSubmitDetailPayRec", method = RequestMethod.POST)
	public String editSubmitDetailPayRec(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert editSubmitDetailPayRec method");

			int payHeadId = Integer.parseInt(request.getParameter("payHeadId"));
			String creditDate2 = request.getParameter("creditDate2");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());

			editRec.setCreditDate2(DateConvertor.convertToYMD(creditDate2));
			editRec.setBillDate(DateConvertor.convertToYMD(editRec.getBillDate()));
			editRec.setCreditDate1(DateConvertor.convertToYMD(editRec.getCreditDate1()));

			editRec.setCreditDate3(DateConvertor.convertToYMD(editRec.getCreditDate3()));

			float paidAmt1 = 0;

			for (int i = 0; i < editRec.getPayRecoveryDetailList().size(); i++) {

				editRec.getPayRecoveryDetailList().get(i).setPayHeadId(payHeadId);

				editRec.getPayRecoveryDetailList().get(i).setPaymentDate(
						DateConvertor.convertToYMD(editRec.getPayRecoveryDetailList().get(i).getPaymentDate()));
				editRec.getPayRecoveryDetailList().get(i).setTxNo(editRec.getPayRecoveryDetailList().get(i).getTxNo());
				editRec.getPayRecoveryDetailList().get(i)
						.setTypeTx(editRec.getPayRecoveryDetailList().get(i).getTypeTx());

				paidAmt1 = paidAmt1 + editRec.getPayRecoveryDetailList().get(i).getPaidAmt();

			}
			float billTotal = editRec.getBillTotal();
			editRec.setPaidAmt(paidAmt1);
			editRec.setPendingAmt(billTotal - paidAmt1);
			editRec.setPayRecoveryDetailList(editRec.getPayRecoveryDetailList());

			System.out.println("payRec" + editRec.toString());

			PayRecoveryHead matIssueInsertRes = rest.postForObject(Constants.url + "savePaymentRecovery", editRec,
					PayRecoveryHead.class);

			if (matIssueInsertRes != null) {
				isError = 2;

				if (matIssueInsertRes.getPendingAmt() == 0) {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					map.add("payHeadId", matIssueInsertRes.getPayHeadId());

					Info updateStatus = rest.postForObject(Constants.url + "updatePayRecStatus", map, Info.class);

				}
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return "redirect:/showPaymentRecoveryList";

	}

}
