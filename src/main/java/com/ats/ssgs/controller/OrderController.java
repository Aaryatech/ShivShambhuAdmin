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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

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
import com.ats.ssgs.model.PoHeader;
import com.ats.ssgs.model.chalan.ChalanDetail;
import com.ats.ssgs.model.chalan.ChalanHeader;
import com.ats.ssgs.model.chalan.GetChalanDetail;
import com.ats.ssgs.model.chalan.GetChalanHeader;
import com.ats.ssgs.model.enq.GetEnqHeader;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.GetCust;
//import com.ats.ssgs.model.master.Document;
//import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.LoginResUser;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.order.GetOrder;
import com.ats.ssgs.model.order.GetOrderDetail;
import com.ats.ssgs.model.order.GetPoForOrder;
import com.ats.ssgs.model.order.OrderDetail;
import com.ats.ssgs.model.order.OrderHeader;
import com.ats.ssgs.model.order.TempOrdDetail;
import com.ats.ssgs.model.order.TempOrdHeader;
import com.ats.ssgs.model.quot.QuotHeader;
import com.itextpdf.text.BaseColor;
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
public class OrderController {

	List<Vehicle> vehicleList;
	List<User> usrList;
	List<Plant> plantList;

	List<Cust> custList;

	RestTemplate rest = new RestTemplate();

	List<PoHeader> poHeadList;

	List<GetPoForOrder> poDetailForOrdList;

	int isError = 0;

	@RequestMapping(value = "/showAddOrder", method = RequestMethod.GET)
	public ModelAndView showAddOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("order/addOrder");

			model.addObject("title", "Add Order");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 3);

			com.ats.ssgs.model.master.Document doc = rest.postForObject(Constants.url + "getDocument", map,
					com.ats.ssgs.model.master.Document.class);

			model.addObject("doc", doc);
			model.addObject("isError", isError);
			isError = 0;

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			model.addObject("curDate", curDate);

		} catch (Exception e) {

			System.err.println("exception In showAddOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/getPOHeaderByCustId", method = RequestMethod.GET)
	public @ResponseBody List<PoHeader> getPOHeaderById(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int custId = Integer.parseInt(request.getParameter("custId"));

		map.add("custId", custId);
		map.add("statusList", "0,1");

		PoHeader[] poHeadArray = rest.postForObject(Constants.url + "getPoHeaderByCustIdAndStatus", map,
				PoHeader[].class);
		poHeadList = new ArrayList<PoHeader>(Arrays.asList(poHeadArray));

		for (int i = 0; i < poHeadList.size(); i++) {

			poHeadList.get(i).setPoDate(DateConvertor.convertToDMY(poHeadList.get(i).getPoDate()));
		}

		System.err.println("Ajax PoHeader  List by cust Id" + poHeadList.toString());

		return poHeadList;

	}

	@RequestMapping(value = "/getPoDetailForOrderByPoId", method = RequestMethod.GET)
	public @ResponseBody List<GetPoForOrder> getPoDetailForOrderByPoId(HttpServletRequest request,
			HttpServletResponse response) {

		ordHeader = null;
		tempOrdDetail = new ArrayList<>();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int poId = Integer.parseInt(request.getParameter("poId"));

		map.add("poId", poId);

		GetPoForOrder[] poHeadArray = rest.postForObject(Constants.url + "getPoDetailForOrderByPoId", map,
				GetPoForOrder[].class);
		poDetailForOrdList = new ArrayList<GetPoForOrder>(Arrays.asList(poHeadArray));

		System.err.println("Ajax poDetailForOrdList  List by po Id" + poDetailForOrdList.toString());

		return poDetailForOrdList;

	}

	TempOrdHeader ordHeader;

	List<TempOrdDetail> tempOrdDetail;

	@RequestMapping(value = "/getTempOrderHeader", method = RequestMethod.GET)
	public @ResponseBody List<TempOrdDetail> getTempOrderHeader(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getTempOrderHeader");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int poDetailId = Integer.parseInt(request.getParameter("poDetailId"));

		float qty = Float.parseFloat(request.getParameter("qty"));
		float poRemainingQty = Float.parseFloat(request.getParameter("poRemainingQty"));
		float itemTotal = Float.parseFloat(request.getParameter("itemTotal"));
		float orderRate = Float.parseFloat(request.getParameter("poRate"));

		int orderDetId = Integer.parseInt(request.getParameter("orderDetId"));

		itemTotal = Math.round(itemTotal);
		if (tempOrdDetail == null) {
			System.err.println("Ord Head =null ");
			/*
			 * ordHeader=new TempOrdHeader();
			 * 
			 * 
			 * ordHeader.setOrderTotal(itemTotal); ordHeader.setOtherCostAfterTax(0);
			 * ordHeader.setTaxableValue(itemTotal); ordHeader.setTaxValue(0)
			 */;

			TempOrdDetail detail = new TempOrdDetail();
			tempOrdDetail = new ArrayList<>();

			detail.setItemId(itemId);
			detail.setOrderQty(qty);
			detail.setPoDetailId(poDetailId);
			detail.setTotal(itemTotal);
			detail.setOrderRate(orderRate);
			detail.setOrderDetId(orderDetId);
			tempOrdDetail.add(detail);
			// ordHeader.setOrdDetailList(tempOrdDetail);
			// return tempOrdDetail;
		} else {

			int flag = 0;
			System.err.println("Else ord detail  not  null ");
			for (int i = 0; i < tempOrdDetail.size(); i++) {

				System.err.println("inside for loop");
				if (tempOrdDetail.get(i).getItemId() == itemId) {
					flag = 1;
					System.err.println("item id matched ");
					tempOrdDetail.get(i).setOrderQty(qty);
					tempOrdDetail.get(i).setTotal(itemTotal);
					break;
					/*
					 * ordHeader.setOrderTotal(ordHeader.getOrdDetailList().get(i).getTotal());
					 * ordHeader.setOtherCostAfterTax(0);
					 * ordHeader.setTaxableValue(ordHeader.getOrdDetailList().get(i).getTotal());
					 * ordHeader.setTaxValue(0);
					 */
				}
			}

			if (flag == 0) {
				System.err.println("Else new Item");
				TempOrdDetail detail = new TempOrdDetail();

				detail.setItemId(itemId);
				detail.setOrderQty(qty);
				detail.setPoDetailId(poDetailId);
				detail.setTotal(itemTotal);
				detail.setOrderRate(orderRate);
				detail.setOrderDetId(orderDetId);

				/*
				 * ordHeader.setOrderTotal(ordHeader.getOrderTotal()+itemTotal);
				 * ordHeader.setOtherCostAfterTax(0);
				 * ordHeader.setTaxableValue(ordHeader.getTaxableValue()+itemTotal);
				 * ordHeader.setTaxValue(0);
				 */

				tempOrdDetail.add(detail);
				// ordHeader.setOrdDetailList(tempOrdDetail);
				// return tempOrdDetail;
			}

		}
		System.err.println("temp Ord Detail " + tempOrdDetail.toString());
		return tempOrdDetail;

	}

	// insertOrder
	@RequestMapping(value = "/insertOrder", method = RequestMethod.POST)
	public String insertOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());

			model = new ModelAndView("order/addOrder");

			model.addObject("title", "Add Order");

			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int custId = Integer.parseInt(request.getParameter("cust_name"));
			int projId = Integer.parseInt(request.getParameter("proj_id"));
			int poId = Integer.parseInt(request.getParameter("po_id"));
			String dispTime = request.getParameter("disp_time");
			String ordDate = request.getParameter("ord_date");
			String delDate = request.getParameter("del_date");

			// float itemTotal = Float.parseFloat(request.getParameter("itemTotal"));

			OrderHeader ordHeader = new OrderHeader();

			List<OrderDetail> ordDetailList = new ArrayList<>();
			String NA = "NA";
			ordHeader.setCustId(custId);
			ordHeader.setDeliveryDate(DateConvertor.convertToYMD(delDate));
			ordHeader.setDelStatus(1);
			ordHeader.setExDate1(curDate);
			ordHeader.setExDate2(curDate);

			ordHeader.setExVar1(dispTime);// Delivery time

			ordHeader.setExVar2(NA);
			ordHeader.setExVar3(NA);
			ordHeader.setOrderDate(DateConvertor.convertToYMD(ordDate));
			ordHeader.setOrderId(0);
			ordHeader.setPlantId(plantId);
			ordHeader.setPoId(poId);
			ordHeader.setProdDate(DateConvertor.convertToYMD(delDate));
			ordHeader.setProjId(projId);

			float headerTotal = 0;

			HttpSession session = request.getSession();
			LoginResUser login = (LoginResUser) session.getAttribute("UserDetail");

			for (int i = 0; i < tempOrdDetail.size(); i++) {

				OrderDetail orDetail = new OrderDetail();

				orDetail.setDelStatus(1);
				orDetail.setExDate1(curDate);
				orDetail.setExDate2(curDate);
				orDetail.setExVar1(NA);
				orDetail.setExVar2(NA);
				orDetail.setExVar3(NA);
				orDetail.setItemId(tempOrdDetail.get(i).getItemId());
				orDetail.setOrderQty(tempOrdDetail.get(i).getOrderQty());
				orDetail.setOrderRate(tempOrdDetail.get(i).getOrderRate());

				orDetail.setPoDetailId(tempOrdDetail.get(i).getPoDetailId());
				orDetail.setPoId(poId);

				orDetail.setTotal(tempOrdDetail.get(i).getTotal());
				orDetail.setRemOrdQty(orDetail.getOrderQty());

				ordDetailList.add(orDetail);

				headerTotal = orDetail.getTotal() + headerTotal;

			}
			ordHeader.setOrderValue(headerTotal);
			ordHeader.setTotal(headerTotal);
			ordHeader.setExInt1(login.getUser().getUserId());

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 3);

			com.ats.ssgs.model.master.Document doc = rest.postForObject(Constants.url + "getDocument", map,
					com.ats.ssgs.model.master.Document.class);
			ordHeader.setOrderNo(doc.getDocPrefix() + "" + doc.getSrNo());

			if (poDetailForOrdList.get(0).getTaxAmt() == 0) {

				ordHeader.setIsTaxIncluding(0);// tax not included

			} else {

				ordHeader.setIsTaxIncluding(1);// yes tax included

			}

			ordHeader.setOrderDetailList(ordDetailList);

			OrderHeader insertOrdHeadRes = rest.postForObject(Constants.url + "saveOrder", ordHeader,
					OrderHeader.class);

			if (insertOrdHeadRes != null) {

				isError = 2;

				map = new LinkedMultiValueMap<String, Object>();

				map.add("srNo", doc.getSrNo() + 1);
				map.add("docCode", doc.getDocCode());

				Info updateDocSr = rest.postForObject(Constants.url + "updateDocSrNo", map, Info.class);

				for (int i = 0; i < poDetailForOrdList.size(); i++) {
					map = new LinkedMultiValueMap<String, Object>();
					map.add("quotDetailId", poDetailForOrdList.get(i).getQuDetailId());
					map.add("orderNo", doc.getDocPrefix() + "" + doc.getSrNo());

					Info updateQuotNo = rest.postForObject(Constants.url + "/updateOrderNo", map, Info.class);

				}


			} else {

				isError = 1;
			}
			System.err.println("insertOrdHeadRes " + insertOrdHeadRes.toString());

		} catch (Exception e) {
			isError = 1;
			System.err.println("exception In insertOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return "redirect:/showAddOrder";
	}

	// ***********************************//

	// showOrderList and options for edit and delete

	@RequestMapping(value = "/showOrderList", method = RequestMethod.GET)
	public ModelAndView showOrderList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("order/orderlist");

			model.addObject("title", "Order List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			String fromDate = null, toDate = null;

			if (request.getParameter("fromDate") == null || request.getParameter("fromDate") == "") {

				System.err.println("onload call  ");

				Calendar date = Calendar.getInstance();
				date.set(Calendar.DAY_OF_MONTH, 1);

				Date firstDate = date.getTime();

				DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

				fromDate = dateFormat.format(firstDate);

				toDate = dateFormat.format(new Date());
				System.err.println("cu Date  " + fromDate + "todays date   " + toDate);

			} else {

				System.err.println("After page load call");
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");

			}

			// getOrderListBetDate

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showAddOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	List<GetOrder> getOrdList = new ArrayList<>();

	@RequestMapping(value = "/getOrderListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetOrder> getOrderListBetDate(HttpServletRequest request, HttpServletResponse response) {

		System.err.println(" in getTempOrderHeader");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

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

		GetOrder[] ordHeadArray = rest.postForObject(Constants.url + "getOrderListBetDateAndStatus", map,
				GetOrder[].class);
		getOrdList = new ArrayList<GetOrder>(Arrays.asList(ordHeadArray));

		System.out.println("order list is" + getOrdList.toString());

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Order No");
		rowData.add("Order Date");
		rowData.add("Delivery Date");

		rowData.add("Customer Name");
		rowData.add("Mobile No");
		rowData.add("Total");

		rowData.add("Status");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < getOrdList.size(); i++) {

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + getOrdList.get(i).getOrderNo());

			rowData.add("" + getOrdList.get(i).getOrderDate());
			// rowData.add("" + getOrdList.get(i).getCustName());
			rowData.add("" + getOrdList.get(i).getDeliveryDate());

			rowData.add("" + getOrdList.get(i).getCustName());
			rowData.add("" + getOrdList.get(i).getCustMobNo());
			rowData.add("" + getOrdList.get(i).getTotal());

			String status1 = null;
			int stat = getOrdList.get(i).getStatus();
			if (stat == 0) {
				status1 = "Pending";
			} else if (stat == 1) {
				status1 = "Partial Completed";
			} else {

				status1 = "Completed";
			}

			rowData.add("" + status1);

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Order List");

		return getOrdList;
	}

	@RequestMapping(value = "/showOrderListPdf/{fromDate}/{toDate}/{custId}/{plantId}", method = RequestMethod.GET)
	public void showDateWisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,@PathVariable("custId") int custId,@PathVariable("plantId") int plantId,
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

			hcell = new PdfPCell(new Phrase("Order No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Order date ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Delivery date ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Customer Name ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Mobile No ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Status", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			float tot=0;
			int index = 0;
			for (GetOrder work : getOrdList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getOrderNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getOrderDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getDeliveryDate(), headFont));
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

				cell = new PdfPCell(new Phrase("" + work.getCustMobNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				
				tot=tot+work.getTotal();
				System.out.println("total is"+tot);

				String status1 = null;
				int stat = work.getStatus();
				if (stat == 0) {
					status1 = "Pending";
				} else if (stat == 1) {
					status1 = "Partial Completed";
				} else {

					status1 = "Completed";
				}

				cell = new PdfPCell(new Phrase("" + status1, headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu(Datewise Order List)\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			
			String plantname=null;
			String custName=null;
			
			if(plantId==0) {
				plantname="All";
				
			}
			else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("plantId", plantId);

				Plant getPlant = rest.postForObject(Constants.url + "getPlantByPlantId", map, Plant.class);
				plantname=getPlant.getPlantName();
				System.out.println("plantname"+plantname);
				
				
				
			}
			if(custId==0) {
				custName="All";
				
			}
			else {
				
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("custId", custId);

				GetCust getcus = rest.postForObject(Constants.url + "getCustomerByCustId", map, GetCust.class);
				custName=getcus.getCustName();
				System.out.println("custName"+custName);
				
			}
			Paragraph p2 = new Paragraph("FromDate:"+fromDate +" ToDate:"+toDate+"  Plant:" + plantname + "  Customer:" + custName, headFont);
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p2);
			document.add(new Paragraph("\n"));
			
			
			
			document.add(table);
			
			Paragraph p1 = new Paragraph("Total:"+tot, headFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(new Paragraph("\n"));

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

	List<GetOrderDetail> ordDetailList;

	@RequestMapping(value = "/editOrder/{orderId}", method = RequestMethod.GET)
	public ModelAndView editOrder(HttpServletRequest request, HttpServletResponse response, @PathVariable int orderId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("order/editOrder");

			GetOrder editOrder = null;
			/*
			 * for(int i=0;i<getOrdList.size();i++) {
			 * 
			 * if(getOrdList.get(i).getOrderId()==orderId) { editOrder=new GetOrder();
			 * editOrder=getOrdList.get(i); break; } }
			 */
			MultiValueMap<String, Object>

			map = new LinkedMultiValueMap<String, Object>();

			map.add("orderId", orderId);
			editOrder = rest.postForObject(Constants.url + "getOrderHeaderById", map, GetOrder.class);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", editOrder.getCustId());
			List<Project> projList;
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));

			map = new LinkedMultiValueMap<String, Object>();

			map.add("orderHeaderId", orderId);
			GetOrderDetail[] ordDetailArray = rest.postForObject(Constants.url + "getOrderDetailList", map,
					GetOrderDetail[].class);
			ordDetailList = new ArrayList<GetOrderDetail>(Arrays.asList(ordDetailArray));

			model.addObject("orderDetailList", ordDetailList);

			model.addObject("editOrder", editOrder);
			model.addObject("projList", projList);

			model.addObject("title", "Edit Order");

		} catch (Exception e) {
			System.err.println("Exce in edit Order " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}

	//

	// updateOrder
	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
	public String updateOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());

			model = new ModelAndView("order/addOrder");

			// model.addObject("title", "Add Order");

			int orderId = Integer.parseInt(request.getParameter("orderId"));
			// int custId = Integer.parseInt(request.getParameter("cust_name"));
			int projId = Integer.parseInt(request.getParameter("proj_id"));
			int poId = Integer.parseInt(request.getParameter("po_id"));
			String dispTime = request.getParameter("disp_time");
			// String ordDate = request.getParameter("ord_date");
			String delDate = request.getParameter("del_date");

			// float itemTotal = Float.parseFloat(request.getParameter("itemTotal"));

			OrderHeader ordHeader = new OrderHeader();

			MultiValueMap<String, Object>

			map = new LinkedMultiValueMap<String, Object>();
			map.add("orderHeaderId", orderId);
			ordHeader = rest.postForObject(Constants.url + "getOrdHeaderByOrdId", map, OrderHeader.class);

			List<OrderDetail> orDetList = new ArrayList<>();
			String NA = "NA";
			ordHeader.setDeliveryDate(DateConvertor.convertToYMD(delDate));
			ordHeader.setProdDate(DateConvertor.convertToYMD(delDate));
			ordHeader.setProjId(projId);

			ordHeader.setExVar1(dispTime);// Delivery time

			float headerTotal = 0;

			for (int i = 0; i < ordDetailList.size(); i++) {

				OrderDetail orDetail = new OrderDetail();

				float orderQty = Float.parseFloat(request.getParameter("ordQty" + ordDetailList.get(i).getItemId()));
				float itemTotal = Float
						.parseFloat(request.getParameter("itemTotal" + ordDetailList.get(i).getItemId()));

				orDetail.setOrderDetId(ordDetailList.get(i).getOrderDetId());
				orDetail.setDelStatus(1);
				orDetail.setExDate1(curDate);
				orDetail.setExDate2(curDate);
				orDetail.setExVar1(NA);
				orDetail.setExVar2(NA);
				orDetail.setExVar3(NA);
				orDetail.setItemId(ordDetailList.get(i).getItemId());
				orDetail.setOrderQty(orderQty);
				orDetail.setOrderRate(ordDetailList.get(i).getOrderRate());

				orDetail.setPoDetailId(ordDetailList.get(i).getPoDetailId());
				orDetail.setPoId(poId);
				// orDetail.setRemOrdQty((orDetail.getRemOrdQty()-orDetail.getOrderQty()));
				orDetail.setRemOrdQty(orDetail.getOrderQty());
				orDetail.setTotal(itemTotal);
				orDetail.setOrderId(ordDetailList.get(i).getOrderId());
				orDetail.setStatus(ordDetailList.get(i).getStatus());
				orDetList.add(orDetail);

				headerTotal = orDetail.getTotal() + headerTotal;

			}
			ordHeader.setOrderValue(headerTotal);
			ordHeader.setTotal(headerTotal);

			/*
			 * if (poDetailForOrdList.get(0).getTaxAmt() == 0) {
			 * 
			 * ordHeader.setIsTaxIncluding(0);// tax not included
			 * 
			 * } else {
			 * 
			 * ordHeader.setIsTaxIncluding(1);// yes tax included
			 * 
			 * }
			 */
			ordHeader.setOrderDetailList(orDetList);

			OrderHeader updateOrdRes = rest.postForObject(Constants.url + "saveOrder", ordHeader, OrderHeader.class);

			if (updateOrdRes != null) {

				isError = 2;

			} else {

				isError = 1;
			}
			System.err.println("updateOrdRes " + updateOrdRes.toString());

		} catch (Exception e) {
			isError = 1;
			System.err.println("exception In updateOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return "redirect:/showOrderList";
	}

	@RequestMapping(value = "/deleteRecordofOrders", method = RequestMethod.POST)
	public String deleteRecordofCompany(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] orderIds = request.getParameterValues("selectOrderToDelete");
			System.out.println("id are" + orderIds);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < orderIds.length; i++) {
				sb = sb.append(orderIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			System.err.println("orderIds " + items.toString());

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("orderIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiOrder", map, Info.class);
			System.err.println("inside method /deleteRecordofOrders");
		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofOrders @OrderController  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showOrderList";
	}

	// *********************Pending Order*******************************

	@RequestMapping(value = "/showPendingOrderList", method = RequestMethod.GET)
	public ModelAndView showPendingOrderList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("order/pendingOrder");

			model.addObject("title", "Pending Chalan List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			String fromDate = null, toDate = null;

			if (request.getParameter("fromDate") == null || request.getParameter("fromDate") == "") {

				System.err.println("onload call  ");

				Calendar date = Calendar.getInstance();
				date.set(Calendar.DAY_OF_MONTH, 1);

				Date firstDate = date.getTime();

				DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

				fromDate = dateFormat.format(firstDate);

				toDate = dateFormat.format(new Date());
				System.err.println("cu Date  " + fromDate + "todays date   " + toDate);

			} else {

				System.err.println("After page load call");
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");

			}

			// getOrderListBetDate

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showAddOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/getOrderPendingListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetOrder> getOrderPendingListBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getTempOrderHeader");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantId", plantId);
		map.add("custId", custId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		// map.add("status", 0);

		GetOrder[] ordHeadArray = rest.postForObject(Constants.url + "getPendingOrderListBetDate", map,
				GetOrder[].class);
		getOrdList = new ArrayList<GetOrder>(Arrays.asList(ordHeadArray));

		System.out.println("order list is" + getOrdList.toString());

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Order No");
		rowData.add("Order Date");
		rowData.add("Delivery Date");

		rowData.add("Customer Name");
		rowData.add("Mobile No");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		int cnt = 1;
		for (int i = 0; i < getOrdList.size(); i++) {

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			cnt = cnt + i;
			rowData.add("" + (i + 1));

			rowData.add("" + getOrdList.get(i).getOrderNo());

			rowData.add("" + getOrdList.get(i).getOrderDate());
			// rowData.add("" + getOrdList.get(i).getCustName());
			rowData.add("" + getOrdList.get(i).getDeliveryDate());

			rowData.add("" + getOrdList.get(i).getCustName());
			rowData.add("" + getOrdList.get(i).getCustMobNo());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Pending Chalan List");

		return getOrdList;
	}

	@RequestMapping(value = "/showPendingOrderListPdf/{fromDate}/{toDate}/{custId}/{plantId}", method = RequestMethod.GET)
	public void showOrderDateWisePdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,@PathVariable("custId") int custId,@PathVariable("plantId") int plantId,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showDatewisePdf");
		Document document = new Document(PageSize.A4);
		System.out.println("custId "+custId);

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

			hcell = new PdfPCell(new Phrase("Order No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Order date ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Delivery date ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Customer Name ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Mobile No ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			for (GetOrder work : getOrdList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getOrderNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getOrderDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getDeliveryDate(), headFont));
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

				cell = new PdfPCell(new Phrase("" + work.getCustMobNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				
				//tot=tot+work.getTotal();
				//System.out.println("total is"+tot);

			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu(Datewise Pending Chalan List)\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			
			String plantname=null;
			String custName=null;
			
			
		

			
			if(plantId==0) {
				plantname="All";
				
			}
			else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("plantId", plantId);

				Plant getPlant = rest.postForObject(Constants.url + "getPlantByPlantId", map, Plant.class);
				plantname=getPlant.getPlantName();
				System.out.println("plantname"+plantname);
				
				
				
			}
			if(custId==0) {
				custName="All";
				
			}
			else {
				
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				System.out.println();

				map.add("custId", custId);

				GetCust getcus1 = rest.postForObject(Constants.url + "getCustomerByCustId", map, GetCust.class);
				custName=getcus1.getCustName();
				System.out.println("custName"+custName);
				
			}
			Paragraph p2 = new Paragraph("FromDate:"+fromDate +" ToDate:"+toDate+"  Plant:" + plantname + "  Customer:" + custName, headFont);
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p2);
			document.add(new Paragraph("\n"));
			
			
			
			document.add(table);
			
			/*Paragraph p1 = new Paragraph("Total:"+tot, headFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(new Paragraph("\n"));*/

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

	// List<GetOrder> getOrdList = new ArrayList<>();

	@RequestMapping(value = "/showGenerateChalanForPendingOrder", method = RequestMethod.POST)
	public ModelAndView showGenerateChalan1(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();
			
			System.out.println("inside aaa");
			model = new ModelAndView("chalan/generateChalan1");

			model.addObject("title", "Add Chalan");
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

			String curTime = sdf.format(cal.getTime());

			model.addObject("curTime", curTime);
			


			int id = Integer.parseInt(request.getParameter("orderId"));
			int key = Integer.parseInt(request.getParameter("key"));

			System.out.println("key are" + id + key);


			String curDate = dateFormat.format(new Date());

			
			model.addObject("curDate", curDate);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 5);
			com.ats.ssgs.model.master.Document doc = rest.postForObject(Constants.url + "getDocument", map,
					com.ats.ssgs.model.master.Document.class);
			model.addObject("doc", doc);

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Vehicle[] vehArray = rest.getForObject(Constants.url + "getAllVehicleList", Vehicle[].class);
			vehicleList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehicleList", vehicleList);

			User[] usrArray = rest.getForObject(Constants.url + "getDriverList", User[].class);
			usrList = new ArrayList<User>(Arrays.asList(usrArray));

			model.addObject("usrList", usrList);


			model.addObject("title", "Add Chalan");

			map.add("docCode", 3);
			System.out.println("before");

			com.ats.ssgs.model.master.Document doc1 = rest.postForObject(Constants.url + "getDocument", map,
					com.ats.ssgs.model.master.Document.class);

			model.addObject("doc", doc1);
			System.out.println("after");
			System.out.println("open chalan data :" + getOrdList.get(key).toString());

			model.addObject("custName", getOrdList.get(key).getCustName());
			model.addObject("plantName", getOrdList.get(key).getPlantName());
			model.addObject("projName", getOrdList.get(key).getProjName());
			model.addObject("orderNo", getOrdList.get(key).getOrderNo());
			model.addObject("custId", getOrdList.get(key).getCustId());
			model.addObject("plantId", getOrdList.get(key).getPlantId());
			model.addObject("projId", getOrdList.get(key).getProjId());
			model.addObject("orderId", getOrdList.get(key).getOrderId());

			model.addObject("curDate", curDate);

		} catch (Exception e) {

			System.err.println("exception In showAddOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	

	
}
