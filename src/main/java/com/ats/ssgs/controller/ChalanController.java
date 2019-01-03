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
import com.ats.ssgs.model.chalan.ChalanDetail;
import com.ats.ssgs.model.chalan.ChalanHeader;
import com.ats.ssgs.model.chalan.GetChalanDetail;
import com.ats.ssgs.model.chalan.GetChalanHeader;
import com.ats.ssgs.model.chalan.TempChalanItem;
import com.ats.ssgs.model.enq.EnqHeader;
import com.ats.ssgs.model.master.Cust;
//import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.GetCust;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.LoginResUser;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.Setting;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.order.GetOrder;
import com.ats.ssgs.model.order.GetOrderDetail;
import com.ats.ssgs.model.order.OrderHeader;
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
public class ChalanController {

	RestTemplate rest = new RestTemplate();
	List<Plant> plantList;
	List<Vehicle> vehicleList;
	List<User> usrList;

	@RequestMapping(value = "/showAddChalan", method = RequestMethod.GET)
	public ModelAndView showAddChalan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/generate_chalan");

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

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			model.addObject("curDate", curDate);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 5);
			com.ats.ssgs.model.master.Document doc = rest.postForObject(Constants.url + "getDocument", map, com.ats.ssgs.model.master.Document.class);
			model.addObject("doc", doc);

			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

			System.out.println(sdf.format(cal.getTime()));

			String curTime = sdf.format(cal.getTime());

			model.addObject("curTime", curTime);
			map = new LinkedMultiValueMap<String, Object>();

			map.add("keyList", "5");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map, Setting[].class);
			List<Setting> settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);


		} catch (Exception e) {

			System.err.println("exception In showAddChalan at Chalan Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<OrderHeader> ordHeadList;

	@RequestMapping(value = "/getOrdHeaderForChalan", method = RequestMethod.GET)
	public @ResponseBody List<OrderHeader> getItemsByPlantId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int projId = Integer.parseInt(request.getParameter("projId"));

		int custId = Integer.parseInt(request.getParameter("custId"));

		System.err.println("getOrdHeaderForChalan  " + "projId" + projId + "custId" + custId);

		map.add("custId", custId);
		map.add("projId", projId);

		map.add("statusList", "0,1");

		OrderHeader[] ordArray = rest.postForObject(Constants.url + "getOrdHeaderForChalan", map, OrderHeader[].class);
		ordHeadList = new ArrayList<OrderHeader>(Arrays.asList(ordArray));

		for (int i = 0; i < ordHeadList.size(); i++) {

			ordHeadList.get(i).setOrderDate(DateConvertor.convertToDMY(ordHeadList.get(i).getOrderDate()));
		}

		System.err.println("Ajax Order List " + ordHeadList.toString());

		return ordHeadList;

	}

	List<GetOrderDetail> ordDetailList;

	@RequestMapping(value = "/getOrderDetailForChalan", method = RequestMethod.GET)
	public @ResponseBody List<GetOrderDetail> getOrderDetail(HttpServletRequest request, HttpServletResponse response) {
		tempChItemList = null;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		System.err.println("orderHeaderId for getOrderDetailList  " + orderId);

		map.add("orderHeaderId", orderId);
		GetOrderDetail[] ordDetailArray = rest.postForObject(Constants.url + "getOrderDetailList", map,
				GetOrderDetail[].class);

		ordDetailList = new ArrayList<GetOrderDetail>(Arrays.asList(ordDetailArray));

		System.err.println("Ajax ordDetailList " + ordDetailList.toString());

		return ordDetailList;
	}

	// setChalanItem;
	List<TempChalanItem> tempChItemList;

	@RequestMapping(value = "/setChalanItem", method = RequestMethod.GET)
	public @ResponseBody List<TempChalanItem> setChalanItem(HttpServletRequest request, HttpServletResponse response) {

		float chalanQty = Float.parseFloat(request.getParameter("chalanQty"));

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int poId = Integer.parseInt(request.getParameter("itemId"));
		int poDetailId = Integer.parseInt(request.getParameter("poDetailId"));

		float remOrdQty = Float.parseFloat(request.getParameter("remOrdQty"));

		int orderDetId = Integer.parseInt(request.getParameter("orderDetId"));
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		int index = Integer.parseInt(request.getParameter("index"));

		System.err.println("chalanQty for setChalanItem  " + chalanQty);

		String itemName = request.getParameter("itemName");
		int uomId = Integer.parseInt(request.getParameter("uomId"));
		String uomName = "Na";
		try {
			uomName = request.getParameter("uomName");
			uomName = uomName.substring(1, uomName.length() - 1);

		} catch (Exception e) {
			uomName = "set";
		}

		if (itemName != null) {

			itemName = itemName.substring(1, itemName.length() - 1);
		}
		if (tempChItemList == null) {
			System.err.println("Inside tempChItemList null");

			tempChItemList = new ArrayList<>();

			TempChalanItem chItem = new TempChalanItem();

			chItem.setChalanQty(chalanQty);
			chItem.setItemId(itemId);
			chItem.setOrderDetId(orderDetId);
			chItem.setOrderId(orderId);
			chItem.setPoDetailId(poDetailId);
			chItem.setPoId(poId);
			chItem.setRemOrdQty(remOrdQty);

			chItem.setItemName(itemName);
			chItem.setUomId(uomId);
			chItem.setUomName(uomName);
			chItem.setIndex(index);

			tempChItemList.add(chItem);
		} else {
			try {
				System.err.println("Inside edit try block");
				TempChalanItem chItem = new TempChalanItem();

				chItem.setChalanQty(chalanQty);
				chItem.setItemId(itemId);
				chItem.setOrderDetId(orderDetId);
				chItem.setOrderId(orderId);
				chItem.setPoDetailId(poDetailId);
				chItem.setPoId(poId);
				chItem.setRemOrdQty(remOrdQty);
				chItem.setUomName(uomName);

				chItem.setItemName(itemName);
				chItem.setUomId(uomId);

				chItem.setIndex(index);
				tempChItemList.set(index, chItem);

			} catch (Exception e) {
				System.err.println("Inside new Item catch block");

				TempChalanItem chItem = new TempChalanItem();

				chItem.setChalanQty(chalanQty);
				chItem.setItemId(itemId);
				chItem.setOrderDetId(orderDetId);
				chItem.setOrderId(orderId);
				chItem.setPoDetailId(poDetailId);
				chItem.setPoId(poId);
				chItem.setRemOrdQty(remOrdQty);

				chItem.setItemName(itemName);
				chItem.setUomId(uomId);
				chItem.setUomName(uomName);

				chItem.setIndex(index);

				tempChItemList.add(chItem);
			}

			if (chalanQty <= 0) {
				try {
					System.err.println("inside chalanQty =0 try delete  ");
					tempChItemList.remove(index);

				} catch (Exception e) {
					System.err.println("inside Exception delete  ");

				}

			}
		}

		System.err.println("Ajax tempChItemList " + tempChItemList.toString());

		return tempChItemList;
	}

	// setChalanItem;
	//
	@RequestMapping(value = "/getChalanSelectedItems", method = RequestMethod.GET)
	public @ResponseBody List<TempChalanItem> getChalanSelectedItems(HttpServletRequest request,
			HttpServletResponse response) {

		return tempChItemList;

	}

	// insertChalan

	@RequestMapping(value = "/insertChalan", method = RequestMethod.POST)
	public String insertChalan(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			System.err.println("Inside insert insertChalan method");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("orderId", tempChItemList.get(0).getOrderId());
			GetOrder getOrder = rest.postForObject(Constants.url + "getOrderHeaderById", map, GetOrder.class);

			

			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int custId = Integer.parseInt(request.getParameter("cust_name"));
			int orderId = Integer.parseInt(request.getParameter("order_id"));

			String chalanDate = request.getParameter("chalan_date");
			String chalanRemark = request.getParameter("chalan_remark");
			String costSegment = request.getParameter("cost_segment");

			int driverId = Integer.parseInt(request.getParameter("driver_id"));
			int vehicleId = Integer.parseInt(request.getParameter("veh_id"));

			String outTime = request.getParameter("out_time");
			float outKm = Float.parseFloat(request.getParameter("out_km"));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());

			List<ChalanDetail> chDetailList = new ArrayList<>();
			for (int i = 0; i < tempChItemList.size(); i++) {

				float width = Float.parseFloat(request.getParameter("width" + tempChItemList.get(i).getItemId()));
				float height = Float.parseFloat(request.getParameter("height" + tempChItemList.get(i).getItemId()));
				float length = Float.parseFloat(request.getParameter("length" + tempChItemList.get(i).getItemId()));

				ChalanDetail chDetail = new ChalanDetail();

				chDetail.setDelStatus(1);
				chDetail.setExDate1(curDate);
				chDetail.setExFloat1(0);
				chDetail.setExVar1("NA");
				chDetail.setExVar2("NA");
				chDetail.setItemHeightPlant(height);
				chDetail.setItemHeightSite(0);
				chDetail.setItemId(tempChItemList.get(i).getItemId());
				chDetail.setItemLengthPlant(length);
				chDetail.setItemLengthSite(0);
				chDetail.setItemQty(tempChItemList.get(i).getChalanQty());
				chDetail.setItemTotalSite(0);
				chDetail.setItemUom(tempChItemList.get(i).getUomId());
				chDetail.setItemWidthPlant(width);
				chDetail.setItemWidthSite(0);
				chDetail.setStatus(0);
				chDetail.setItemTotalPlant(tempChItemList.get(i).getChalanQty());

				chDetail.setOrderDetailId(tempChItemList.get(i).getOrderDetId());
				chDetailList.add(chDetail);

			}

			Calendar cal = Calendar.getInstance();

			ChalanHeader chHeader = new ChalanHeader();

			chHeader.setProjId(getOrder.getProjId());

			map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 5);
			com.ats.ssgs.model.master.Document doc = rest.postForObject(Constants.url + "getDocument", map, com.ats.ssgs.model.master.Document.class);

			HttpSession session = request.getSession();
			LoginResUser login = (LoginResUser) session.getAttribute("UserDetail");

			chHeader.setChalanNo(doc.getDocPrefix() + "" + doc.getSrNo());

			chHeader.setChalanDate(DateConvertor.convertToYMD(chalanDate));
			chHeader.setChalanDetailList(chDetailList);
			chHeader.setChalanRemark(chalanRemark);
			chHeader.setCustId(custId);
			chHeader.setDriverId(driverId);
			chHeader.setExDate1(curDate);
			// chHeader.setExVar1("save time-" +dateFormat.format(cal.getTime().getTime()));
			chHeader.setInKm(0);
			chHeader.setOrderId(orderId);
			chHeader.setOrderNo(getOrder.getOrderNo());
			chHeader.setOutKm(outKm);
			chHeader.setPlantId(plantId);
			chHeader.setProjId(getOrder.getProjId());
			chHeader.setSitePersonMob("-");
			chHeader.setSitePersonName("-");
			chHeader.setStatus(0);
			chHeader.setVehicleId(vehicleId);
			chHeader.setVehInDate(curDate);
			chHeader.setVehTimeIn("-");
			chHeader.setVehTimeOut(outTime);
			chHeader.setChalanDate(DateConvertor.convertToYMD(chalanDate));
			chHeader.setCostSegment(costSegment);

			chHeader.setExVar1(String.valueOf(login.getUser().getUserId()));// userId
			chHeader.setExInt1(1);// delStatus
			chHeader.setExFloat1(0);// isClosed
			chHeader.setInKm(0.0f);

			ChalanHeader chHeadInserRes = rest.postForObject(Constants.url + "saveChalanHeaderDetail", chHeader,
					ChalanHeader.class);

			session.setAttribute("chalanRes", chHeadInserRes);

			System.err.println("chHeadInserRes " + chHeadInserRes.toString());

			if (chHeadInserRes != null) {

				map = new LinkedMultiValueMap<String, Object>();

				map.add("srNo", doc.getSrNo() + 1);
				map.add("docCode", doc.getDocCode());

				Info updateDocSr = rest.postForObject(Constants.url + "updateDocSrNo", map, Info.class);

			}

		} catch (Exception e) {
			System.err.println("Exe in  saveChalanHeaderDetail " + e.getMessage());

			e.printStackTrace();
		}
		try {
			int isRmcPage = Integer.parseInt(request.getParameter("isRmcPage"));

		} catch (Exception e) {
			System.err.println("I catch Is Rmc page ");
			return "redirect:/showAddChalan";
		}

		return "redirect:/showBillRmc";

	}

	@RequestMapping(value = "/showChalanList", method = RequestMethod.GET)
	public ModelAndView showChalanList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/chalan_list");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			model.addObject("title", "Chalan List");

		} catch (Exception e) {
			System.err.println("Exce in /showChalanList   " + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	// getChalanListByPlant
	List<GetChalanHeader> chalanHeadList;

	@RequestMapping(value = "/getChalanListByPlant", method = RequestMethod.GET)
	public @ResponseBody List<GetChalanHeader> getChalanListByPlant(HttpServletRequest request,
			HttpServletResponse response) {
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int plantId = Integer.parseInt(request.getParameter("plantId"));
		System.err.println("plantId for getChalanListByPlant  " + plantId);

		map.add("plantId", plantId);
		// map.add("chalanStatus", 0);
		GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getChalanHeadersByPlantAndStatus", map,
				GetChalanHeader[].class);

		chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));

		for (int i = 0; i < chalanHeadList.size(); i++) {

			chalanHeadList.get(i).setChalanDate(DateConvertor.convertToDMY(chalanHeadList.get(i).getChalanDate()));
		}

		System.err.println("Ajax chalanHeadList /getChalanListByPlant " + chalanHeadList.toString());
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Chalan No");
		rowData.add("Chalan Date");
		rowData.add("Customer Name");
		rowData.add("Project Name");
		rowData.add("Vehicle No");
		rowData.add("Driver Name");

		

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
			
			rowData.add("" + chalanHeadList.get(i).getCustName());
			rowData.add("" + chalanHeadList.get(i).getProjName());
			rowData.add("" + chalanHeadList.get(i).getVehNo());
			rowData.add("" + chalanHeadList.get(i).getDriverName());
			
			

			/*String status1 = null;
			int stat = getOrdList.get(i).getStatus();
			if (stat == 0) {
				status1 = "Pending";
			} else if (stat == 1) {
				status1 = "Partial Completed";
			} else {

				status1 = "Completed";
			}

			rowData.add("" + status1);
*/
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Chalan List");

		return chalanHeadList;
	}
	
	
	@RequestMapping(value = "/showChalanListPdf/{plantId}", method = RequestMethod.GET)
	public void showChalanListPdf(@PathVariable("plantId") int plantId,
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

			hcell = new PdfPCell(new Phrase("Chalan No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Chalan date ", headFont1));
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

			hcell = new PdfPCell(new Phrase("Vehicle No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Driver Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			float tot=0;
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

				/*cell = new PdfPCell(new Phrase("" + work.getTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				*/
				

			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu(Datewise Chalan List)\n", f);
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
			/*if(custId==0) {
				custName="All";
				
			}
			else {
				
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("custId", custId);

				GetCust getcus = rest.postForObject(Constants.url + "getCustomerByCustId", map, GetCust.class);
				custName=getcus.getCustName();
				System.out.println("custName"+custName);
				
			}*/
			Paragraph p2 = new Paragraph("Plant:" + plantname +"", headFont);
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p2);
			document.add(new Paragraph("\n"));
			
			
			
			document.add(table);
			
		/*	Paragraph p1 = new Paragraph("Total:"+tot, headFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(new Paragraph("\n"));
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

	// editChalan

	List<Project> projList;
	List<GetChalanDetail> chDetailList;

	@RequestMapping(value = "/closeChalan/{chalanId}", method = RequestMethod.GET)
	public ModelAndView closeChalan(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int chalanId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/chalan_edit");

			GetChalanHeader editChalan = new GetChalanHeader();
			/*
			 * for(int i=0;i<getOrdList.size();i++) {
			 * 
			 * if(getOrdList.get(i).getOrderId()==orderId) { editOrder=new GetOrder();
			 * editOrder=getOrdList.get(i); break; } }
			 */
			MultiValueMap<String, Object>

			map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanId", chalanId);
			editChalan = rest.postForObject(Constants.url + "getChalanHeadersByChalanId", map, GetChalanHeader.class);
			editChalan.setChalanDate(DateConvertor.convertToDMY(editChalan.getChalanDate()));

			map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", editChalan.getCustId());
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));

			model.addObject("projList", projList);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanId", chalanId);
			GetChalanDetail[] chDetailArray = rest.postForObject(Constants.url + "getGetChalanDetailByChalanId", map,
					GetChalanDetail[].class);
			chDetailList = new ArrayList<GetChalanDetail>(Arrays.asList(chDetailArray));

			model.addObject("chDetailList", chDetailList);

			model.addObject("editChalan", editChalan);

			model.addObject("title", "Close Chalan");
			Calendar cal = Calendar.getInstance();

			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

			System.out.println(sdf.format(cal.getTime()));

			String curTime = sdf.format(cal.getTime());

			model.addObject("curTime", curTime);

		} catch (Exception e) {
			System.err.println("Exce in edit Chalan " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	// closeChalan

	@RequestMapping(value = "/closeChalan", method = RequestMethod.POST)
	public String closeChalan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/chalan_edit");

			String inTime = request.getParameter("in_time");
			float inKm = Float.parseFloat(request.getParameter("in_km"));
			String chalanRemark = request.getParameter("chalan_remark");
			String costSegment = request.getParameter("cost_segment");
			String sitePerName = request.getParameter("site_per_name");
			String sitePerMob = request.getParameter("site_per_mob");

			int orderId = Integer.parseInt(request.getParameter("order_id"));
			int chalanId = Integer.parseInt(request.getParameter("chalan_id"));

			ChalanHeader chHeader = new ChalanHeader();

			chHeader.setChalanId(chalanId);
			chHeader.setChalanRemark(chalanRemark);
			chHeader.setVehTimeIn(inTime);

			chHeader.setInKm(inKm);
			chHeader.setCostSegment(costSegment);
			chHeader.setSitePersonName(sitePerName);
			chHeader.setSitePersonMob(sitePerMob);
			chHeader.setOrderId(orderId);

			chHeader.setStatus(1);
			chHeader.setExFloat1(1);// set to 1 while close

			List<ChalanDetail> chalanDList = new ArrayList<>();

			for (int i = 0; i < chDetailList.size(); i++) {

				try {

					float itemQty = Float
							.parseFloat(request.getParameter("chQty" + chDetailList.get(i).getChalanDetailId()));
					float siteWidth = Float
							.parseFloat(request.getParameter("width" + chDetailList.get(i).getChalanDetailId()));
					float siteHeight = Float
							.parseFloat(request.getParameter("height" + chDetailList.get(i).getChalanDetailId()));
					float siteLength = Float
							.parseFloat(request.getParameter("length" + chDetailList.get(i).getChalanDetailId()));
					float siteTotal = Float
							.parseFloat(request.getParameter("itemTotal" + chDetailList.get(i).getChalanDetailId()));

					ChalanDetail det = new ChalanDetail();

					det.setChalanDetailId(chDetailList.get(i).getChalanDetailId());
					det.setItemQty(itemQty);
					det.setItemHeightSite(siteHeight);
					det.setItemWidthSite(siteWidth);
					det.setItemLengthSite(siteLength);
					det.setItemTotalSite(siteTotal);
					det.setItemId(chDetailList.get(i).getItemId());
					det.setOrderDetailId(chDetailList.get(i).getOrderDetailId());

					det.setStatus(1);

					chalanDList.add(det);
				} catch (Exception e) {

					System.err.println("Exce in getting chalan detail " + e.getMessage());
					e.printStackTrace();
				}
			}
			chHeader.setChalanDetailList(chalanDList);
			System.err.println(" update bean  chHeader " + chHeader.toString());

			Info chHeadInserRes = rest.postForObject(Constants.url + "closeChalanApi", chHeader, Info.class);

		} catch (Exception e) {
			System.err.println("Exce in closeChalanApi " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showChalanList";

	}

	List<Cust> custList;
	private float pHeight;

	@RequestMapping(value = "/editChalan/{chalanId}", method = RequestMethod.GET)
	public ModelAndView editChalan(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int chalanId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/ch_edit_full");

			GetChalanHeader editChalan = new GetChalanHeader();
			/*
			 * for(int i=0;i<getOrdList.size();i++) {
			 * 
			 * if(getOrdList.get(i).getOrderId()==orderId) { editOrder=new GetOrder();
			 * editOrder=getOrdList.get(i); break; } }
			 */
			MultiValueMap<String, Object>

			map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanId", chalanId);
			editChalan = rest.postForObject(Constants.url + "getChalanHeadersByChalanId", map, GetChalanHeader.class);
			editChalan.setChalanDate(DateConvertor.convertToDMY(editChalan.getChalanDate()));

			map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", editChalan.getCustId());
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));

			model.addObject("projList", projList);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", editChalan.getCustId());
			map.add("projId", editChalan.getProjId());

			map.add("statusList", "0,1,2");

			OrderHeader[] ordArray = rest.postForObject(Constants.url + "getOrdHeaderForChalan", map,
					OrderHeader[].class);
			ordHeadList = new ArrayList<OrderHeader>(Arrays.asList(ordArray));

			for (int i = 0; i < ordHeadList.size(); i++) {

				ordHeadList.get(i).setOrderDate(DateConvertor.convertToDMY(ordHeadList.get(i).getOrderDate()));
			}

			System.err.println(" Order List " + ordHeadList.toString());

			model.addObject("ordHeadList", ordHeadList);
			map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanId", chalanId);
			GetChalanDetail[] chDetailArray = rest.postForObject(Constants.url + "getGetChalanDetailByChalanId", map,
					GetChalanDetail[].class);
			chDetailList = new ArrayList<GetChalanDetail>(Arrays.asList(chDetailArray));

			model.addObject("chDetailList", chDetailList);

			model.addObject("editChalan", editChalan);

			model.addObject("title", "Edit Chalan");

			map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", editChalan.getPlantId());

			Cust[] custArray = rest.postForObject(Constants.url + "getCustListByPlant", map, Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			model.addObject("custList", custList);

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Vehicle[] vehArray = rest.getForObject(Constants.url + "getAllVehicleList", Vehicle[].class);
			vehicleList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehicleList", vehicleList);

			User[] usrArray = rest.getForObject(Constants.url + "getDriverList", User[].class);
			usrList = new ArrayList<User>(Arrays.asList(usrArray));

			model.addObject("usrList", usrList);
			
			map = new LinkedMultiValueMap<String, Object>();

			map.add("keyList", "5");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map, Setting[].class);
			List<Setting> settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);


		} catch (Exception e) {
			System.err.println("Exce in edit Chalan " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/updateChalan", method = RequestMethod.POST)
	public String updateChalan(HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			System.err.println("Inside insert updateChalan method");

			int chalanId = Integer.parseInt(request.getParameter("chalanId"));

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());

			List<ChalanDetail> chDeList = new ArrayList<>();
			for (int i = 0; i < chDetailList.size(); i++) {

				float itemQty = Float
						.parseFloat(request.getParameter("chQty" + chDetailList.get(i).getChalanDetailId()));
				float pWidth = Float
						.parseFloat(request.getParameter("width" + chDetailList.get(i).getChalanDetailId()));
				float pHeight = Float
						.parseFloat(request.getParameter("height" + chDetailList.get(i).getChalanDetailId()));
				float pLength = Float
						.parseFloat(request.getParameter("length" + chDetailList.get(i).getChalanDetailId()));
				float pTotal = Float
						.parseFloat(request.getParameter("itemTotal" + chDetailList.get(i).getChalanDetailId()));

				ChalanDetail det = new ChalanDetail();

				det.setChalanDetailId(chDetailList.get(i).getChalanDetailId());
				det.setItemQty(itemQty);
				det.setItemHeightPlant(pHeight);
				det.setItemWidthPlant(pWidth);
				det.setItemLengthPlant(pLength);
				det.setItemTotalSite(pTotal);
				det.setItemId(chDetailList.get(i).getItemId());
				det.setOrderDetailId(chDetailList.get(i).getOrderDetailId());

				det.setChalanId(chDetailList.get(i).getChalanId());
				det.setExDate1(curDate);
				det.setExVar1("na");
				det.setExVar2("na");
				det.setItemHeightSite(chDetailList.get(i).getItemHeightSite());
				det.setItemLengthSite(chDetailList.get(i).getItemLengthSite());
				det.setItemQty(itemQty);
				det.setItemTotalPlant(pTotal);
				det.setItemTotalSite(chDetailList.get(i).getItemTotalSite());

				det.setItemUom(chDetailList.get(i).getItemUom());
				det.setItemWidthSite(chDetailList.get(i).getItemWidthSite());

				det.setStatus(0);
				det.setDelStatus(1);

				chDeList.add(det);
			}

			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int custId = Integer.parseInt(request.getParameter("cust_name"));
			int orderId = Integer.parseInt(request.getParameter("order_id"));

			String chalanDate = request.getParameter("chalan_date");
			String chalanRemark = request.getParameter("chalan_remark");
			String costSegment = request.getParameter("cost_segment");

			String chalanNo = request.getParameter("chalan_no");

			int driverId = Integer.parseInt(request.getParameter("driver_id"));
			int vehicleId = Integer.parseInt(request.getParameter("veh_id"));

			String outTime = request.getParameter("out_time");
			float outKm = Float.parseFloat(request.getParameter("out_km"));
			ChalanHeader chHeader = new ChalanHeader();

			chHeader.setChalanNo(chalanNo);

			chHeader.setChalanDetailList(chDeList);
			chHeader.setChalanId(chalanId);
			chHeader.setChalanRemark(chalanRemark);
			chHeader.setCustId(custId);
			chHeader.setDriverId(driverId);
			chHeader.setExDate1(curDate);

			chHeader.setInKm(0);
			chHeader.setOrderId(orderId);
			chHeader.setOrderNo(request.getParameter("orderNo"));
			chHeader.setOutKm(outKm);
			chHeader.setPlantId(plantId);
			chHeader.setProjId(Integer.parseInt(request.getParameter("projId")));
			chHeader.setSitePersonMob("-");
			chHeader.setSitePersonName("-");
			chHeader.setStatus(0);
			chHeader.setVehicleId(vehicleId);
			chHeader.setVehInDate(curDate);
			chHeader.setVehTimeIn("-");
			chHeader.setVehTimeOut(outTime);
			chHeader.setChalanDate(DateConvertor.convertToYMD(chalanDate));
			chHeader.setCostSegment(costSegment);

			chHeader.setExInt1(1);// delStatus
			chHeader.setExFloat1(0);// isClosed yes-1/no-0
			HttpSession session = request.getSession();
			LoginResUser login = (LoginResUser) session.getAttribute("UserDetail");

			chHeader.setExVar1(String.valueOf(login.getUser().getUserId()));// userId

			ChalanHeader chUpdateResponse = rest.postForObject(Constants.url + "saveChalanHeaderDetail", chHeader,
					ChalanHeader.class);

		} catch (Exception e) {

			System.err.println("Exce in update Chalan ");
		}

		return "redirect:/showChalanList";

	}

	@RequestMapping(value = "/deleteRecordofChalan", method = RequestMethod.POST)
	public String deleteRecordofCompany(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] chalanIds = request.getParameterValues("selectChalanToDelete");
			System.out.println("ch ids are" + chalanIds);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < chalanIds.length; i++) {
				sb = sb.append(chalanIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiChalan", map, Info.class);

			System.err.println("inside method");
		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofQuota @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showQuotations";
	}
	
	@RequestMapping(value = "/showOpenChalanList", method = RequestMethod.GET)
	public ModelAndView showOpenChalanList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/openChalan");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			
			model.addObject("title", "Open Chalan List");
			
			
		}
		catch (Exception e) {
			System.err.println("Exce in /showChalanList   " +e.getMessage());
			e.printStackTrace();
		}
		return model;
		
	}
	
	
	@RequestMapping(value = "/getOpenChalanListByPlant", method = RequestMethod.GET)
	public @ResponseBody List<GetChalanHeader> getOpenChalanListByPlant(HttpServletRequest request, HttpServletResponse response) {
		List<GetChalanHeader> chalanHeadList;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int plantId = Integer.parseInt(request.getParameter("plantId"));
		System.err.println("plantId for getChalanListByPlant  " +plantId);

		map.add("plantId", plantId);
		//map.add("chalanStatus", 0);
		GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getOpenChalanHeadersByPlantAndStatus", map,
				GetChalanHeader[].class);
		
		chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));
		
		for(int i=0;i<chalanHeadList.size();i++) {
			
			chalanHeadList.get(i).setChalanDate(DateConvertor.convertToDMY(chalanHeadList.get(i).getChalanDate()));
		}
				System.out.println("open chalan "+chalanHeadList.toString() );
				System.err.println("Ajax chalanHeadList /getChalanListByPlant " + chalanHeadList.toString());

				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Sr. No");
				rowData.add("Chalan No");
				rowData.add("Chalan Date");
				

				rowData.add("Customer Name");
				rowData.add("Project Name");
				rowData.add("Vehicle No");
				rowData.add("Driver Name");

				

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
					rowData.add("" + chalanHeadList.get(i).getCustName());
					rowData.add("" + chalanHeadList.get(i).getProjName());
					rowData.add("" + chalanHeadList.get(i).getVehNo());
					rowData.add("" + chalanHeadList.get(i).getDriverName());
					
					

					/*String status1 = null;
					int stat = getOrdList.get(i).getStatus();
					if (stat == 0) {
						status1 = "Pending";
					} else if (stat == 1) {
						status1 = "Partial Completed";
					} else {

						status1 = "Completed";
					}

					rowData.add("" + status1);
		*/
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				HttpSession session = request.getSession();
				session.setAttribute("exportExcelList", exportToExcelList);
				session.setAttribute("excelName", "Open Chalan List");

				return chalanHeadList ;
	}
	
	
	@RequestMapping(value = "/showOpenChalanListPdf/{plantId}", method = RequestMethod.GET)
	public void showOpenChalanListPdf(@PathVariable("plantId") int plantId,
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

			hcell = new PdfPCell(new Phrase("Chalan No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Chalan date ", headFont1));
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

			hcell = new PdfPCell(new Phrase("Vehicle No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Driver Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			float tot=0;
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

				/*cell = new PdfPCell(new Phrase("" + work.getTotal(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				*/
				

			}
			document.open();
			Paragraph name = new Paragraph("Shiv Shambhu(Datewise Chalan List)\n", f);
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
			/*if(custId==0) {
				custName="All";
				
			}
			else {
				
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("custId", custId);

				GetCust getcus = rest.postForObject(Constants.url + "getCustomerByCustId", map, GetCust.class);
				custName=getcus.getCustName();
				System.out.println("custName"+custName);
				
			}*/
			Paragraph p2 = new Paragraph("Plant:" + plantname +"", headFont);
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p2);
			document.add(new Paragraph("\n"));
			
			
			
			document.add(table);
			
		/*	Paragraph p1 = new Paragraph("Total:"+tot, headFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(new Paragraph("\n"));
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
	
}
