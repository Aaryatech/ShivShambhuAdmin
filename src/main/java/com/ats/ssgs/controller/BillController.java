package com.ats.ssgs.controller;

import java.awt.Dimension;

import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.ats.ssgs.common.Currency;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.common.ExportToExcel;
import com.ats.ssgs.model.BillDetail;
import com.ats.ssgs.model.BillHeader;
import com.ats.ssgs.model.GetBillDetByHsn;
import com.ats.ssgs.model.GetBillDetail;
import com.ats.ssgs.model.GetBillHeader;
import com.ats.ssgs.model.GetBillHeaderPdf;
import com.ats.ssgs.model.GetItemsForBill;
import com.ats.ssgs.model.PoHeader;
import com.ats.ssgs.model.chalan.GetChalanHeader;
import com.ats.ssgs.model.master.BankDetail;
import com.ats.ssgs.model.master.Company;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.GetCust;
import com.ats.ssgs.model.master.GetItem;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.LoginResUser;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.Setting;
import com.ats.ssgs.model.master.Vendor;
import com.ats.ssgs.model.prodrm.RmcQuotTemp;
import com.ats.ssgs.model.rec.PayRecoveryHead;

@Controller
@Scope("session")
public class BillController {

	/*
	 * <dependency> <groupId>javax.mail</groupId>
	 * <artifactId>javax.mail-api</artifactId> <version>1.5.5</version>
	 * </dependency> <dependency> <groupId>javax.mail</groupId>
	 * <artifactId>mail</artifactId> <version>1.4.7</version> </dependency>
	 * 
	 * <dependency> <groupId>org.springframework</groupId>
	 * <artifactId>spring-context-support</artifactId>
	 * <version>${org.springframework-version}</version> </dependency>
	 */

	RestTemplate rest = new RestTemplate();
	List<GetItemsForBill> billItems;
	int billHeadId = 0;
	int pdfCustId = 0;
	List<RmcQuotTemp> rmcQuotTempList;
	List<Setting> settingList;
	List<Plant> plantList;

	// hii

	@RequestMapping(value = "/showBillByMultiChalanId", method = RequestMethod.POST)
	public ModelAndView showBillByMultiChalanId(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		try {

			model = new ModelAndView("bill/addBill");
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String curDate = dateFormat.format(new Date());
			model.addObject("curDate", curDate);

			String[] chalanIdList = request.getParameterValues("chalanIdList");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < chalanIdList.length; i++) {
				sb = sb.append(chalanIdList[i] + ",");
			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanIdList", items);

			GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getChalanHeadersByChalanIdList", map,
					GetChalanHeader[].class);

			List<GetChalanHeader> chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));
			model.addObject("chalanHeadList", chalanHeadList);
			System.out.println("chalanId" + chalanHeadList.get(0).getChalanId());
			map = new LinkedMultiValueMap<String, Object>();
			map.add("chalanId", chalanHeadList.get(0).getChalanId());
			GetChalanHeader addBill = rest.postForObject(Constants.url + "getChalanHeadersByChalanId", map,
					GetChalanHeader.class);
			System.err.println(addBill.toString());

			model.addObject("addBill", addBill);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 6);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			model.addObject("doc", doc);

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			List<Company> compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);
			model.addObject("title", "Add Bill");
			model.addObject("billHeadId", billHeadId);
			model.addObject("custId", pdfCustId);

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

		} catch (Exception e) {

			System.err.println("Exception in /showBillByMultiChalanId @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/showBill", method = RequestMethod.GET)
	public ModelAndView showBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("bill/addBill");

			HttpSession httpSession = request.getSession();
			LoginResUser login = (LoginResUser) httpSession.getAttribute("UserDetail");

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			model.addObject("curDate", curDate);

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			List<Company> compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);
			model.addObject("title", "Add Bill");
			model.addObject("billHeadId", billHeadId);
			model.addObject("custId", pdfCustId);

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", login.getUser().getPlantId());

			Plant editPlant = rest.postForObject(Constants.url + "getPlantByPlantId", map, Plant.class);
			model.addObject("editPlant", editPlant);
			String var = null;
			String a = editPlant.getExVar2();
			if (String.valueOf(a).length() == 1) {
				var = "000".concat(String.valueOf(a));

			} else if (String.valueOf(a).length() == 2) {
				var = "00".concat(String.valueOf(a));

			}

			model.addObject("var", var);
			System.out.println("Var" + var);

		} catch (Exception e) {

			System.err.println("" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/getPoByCust", method = RequestMethod.GET)
	public @ResponseBody List<PoHeader> getPoByCust(HttpServletRequest request, HttpServletResponse response) {
		List<PoHeader> poHeaderList = null;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int custId = Integer.parseInt(request.getParameter("custId"));
			System.err.println("custId for getPoByCust  " + custId);

			map.add("custId", custId);
			map.add("statusList", 1);
			PoHeader[] chArray = rest.postForObject(Constants.url + "getPoHeaderByCustIdAndStatus", map,
					PoHeader[].class);

			poHeaderList = new ArrayList<PoHeader>(Arrays.asList(chArray));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return poHeaderList;
	}

	//

	@RequestMapping(value = "/getChalanByCustAndProj", method = RequestMethod.GET)
	public @ResponseBody List<GetChalanHeader> getChalanByCustAndProj(HttpServletRequest request,
			HttpServletResponse response) {
		List<GetChalanHeader> chalanHeadList;

		int projId = Integer.parseInt(request.getParameter("projId"));
		int custId = Integer.parseInt(request.getParameter("custId"));

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		/*
		 * map.add("keyList", "10");
		 * 
		 * Setting settArray = rest.postForObject(Constants.url +
		 * "getSettingValueByKeyList", map, Setting.class); settingList = new
		 * ArrayList<Setting>(Arrays.asList(settArray));
		 */

		map = new LinkedMultiValueMap<String, Object>();

		map.add("projId", projId);
		map.add("custId", custId);
		/* map.add("chalanStatus", 0); */

		/*
		 * GetChalanHeader[] chArray = rest.postForObject(Constants.url +
		 * "getChalanHeadersByCustAndStatusAndProj", map, GetChalanHeader[].class);
		 */
		GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getChalanHeadersByCustAndCostSegAndProj", map,
				GetChalanHeader[].class);

		chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));

		System.err.println("Ajax chalanHeadList " + chalanHeadList.toString());

		return chalanHeadList;
	}

	/*
	 * @RequestMapping(value = "/getChalanByPO", method = RequestMethod.GET)
	 * public @ResponseBody List<GetChalanHeader>
	 * getChalanListByCustId(HttpServletRequest request, HttpServletResponse
	 * response) { List<GetChalanHeader> chalanHeadList; MultiValueMap<String,
	 * Object> map = new LinkedMultiValueMap<String, Object>(); int poId =
	 * Integer.parseInt(request.getParameter("poId"));
	 * System.err.println("poId for getChalanListByPlant  " + poId);
	 * 
	 * map.add("poId", poId); map.add("chalanStatus", "1,2"); map.add("billStatus",
	 * 1); GetChalanHeader[] chArray = rest.postForObject(Constants.url +
	 * "getChalanHeadersByCustAndStatus", map, GetChalanHeader[].class);
	 * 
	 * chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));
	 * 
	 * for (int i = 0; i < chalanHeadList.size(); i++) {
	 * 
	 * chalanHeadList.get(i).setChalanDate(DateConvertor.convertToDMY(chalanHeadList
	 * .get(i).getChalanDate())); }
	 * 
	 * System.err.println("Ajax chalanHeadList " + chalanHeadList.toString());
	 * 
	 * return chalanHeadList; }
	 */

	@RequestMapping(value = "/getItemsForBill", method = RequestMethod.GET)
	public @ResponseBody List<GetItemsForBill> getItemsForBill(HttpServletRequest request,
			HttpServletResponse response) {
		System.err.println("HI");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String selectedFr = request.getParameter("values");

		selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
		selectedFr = selectedFr.replaceAll("\"", "");

		System.out.println("cust:::::" + selectedFr);

		map.add("chalanId", selectedFr);

		GetItemsForBill[] chArray = rest.postForObject(Constants.url + "getItemsForBill", map, GetItemsForBill[].class);

		billItems = new ArrayList<GetItemsForBill>(Arrays.asList(chArray));
		System.err.println("=======================" + billItems.toString());
		return billItems;
	}

	// sachin
	@RequestMapping(value = "/getItemsForRmcBill", method = RequestMethod.GET)
	public @ResponseBody List<GetItemsForBill> getItemsForRmcBill(HttpServletRequest request,
			HttpServletResponse response) {
		System.err.println("HI");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		String selectedFr = request.getParameter("values");

		selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
		selectedFr = selectedFr.replaceAll("\"", "");

		System.out.println("cust:::::" + selectedFr);

		map.add("chalanId", selectedFr);

		GetItemsForBill[] chArray = rest.postForObject(Constants.url + "getItemsForRmcBill", map,
				GetItemsForBill[].class);

		billItems = new ArrayList<GetItemsForBill>(Arrays.asList(chArray));
		System.err.println(billItems.toString());
		return billItems;
	}

	@RequestMapping(value = "/insertBill", method = RequestMethod.POST)
	public String insertBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			HttpSession httpSession = request.getSession();
			LoginResUser login = (LoginResUser) httpSession.getAttribute("UserDetail");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());
			List<Integer> chalanDetailList = new ArrayList<Integer>();
			model = new ModelAndView("bill/addBill");

			model.addObject("title", "Add Bill");
			String[] chalanId = request.getParameterValues("chalanId");
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int custId = Integer.parseInt(request.getParameter("cust_name"));
			int projId = Integer.parseInt(request.getParameter("proj_id"));
			int gstNo = Integer.parseInt(request.getParameter("gstNo"));

			// int poId = Integer.parseInt(request.getParameter("po_id"));

			String billDate = request.getParameter("bill_date");
			String remark = request.getParameter("bill_remark");
			String billNo = request.getParameter("bill_no");

			BillHeader billHeader = new BillHeader();
			MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<String, Object>();
			map1.add("companyId", companyId);

			BankDetail editComp = rest.postForObject(Constants.url + "getBankDetailByBankId", map1, BankDetail.class);

			List<BillDetail> billDetailList = new ArrayList<BillDetail>();
			billHeader.setAccId(editComp.getBankDetId());// hardcoded
			billHeader.setBillDate(billDate);
			billHeader.setBillHeadId(0);
			billHeader.setBillNo(billNo);

			billHeader.setCompanyId(companyId);
			billHeader.setCostSegment("");
			billHeader.setCustId(custId);
			billHeader.setDeliveryTerm(billItems.get(0).getDeliveryTerm());
			billHeader.setDelStatus(1);
			billHeader.setExFloat1(0);
			billHeader.setExFloat2(0);
			billHeader.setExInt1(plantId);
			billHeader.setExInt2(gstNo);
			billHeader.setExInt3(0);
			billHeader.setExVar1("");
			billHeader.setExVar2(remark);
			billHeader.setExVar3("");

			billHeader.setPaymentTermId(billItems.get(0).getPoTermId());
			billHeader.setPoId(1);
			billHeader.setProjId(projId);

			HashMap<Integer, String> orderId = new HashMap<Integer, String>();
			HashMap<Integer, String> chalanIdList = new HashMap<Integer, String>();
			float totalTaxable = 0.0f;
			float totalTaxAmt = 0.0f;
			float grandTotalAmt = 0.0f;
			for (int i = 0; i < billItems.size(); i++) {

				System.err.println("chalanQty" + i + "" + billItems.get(i).getItemId());
				System.out.println("hi.........................hey ..........");

				float qty = Float.parseFloat(request.getParameter("chalanQty" + i + "" + billItems.get(i).getItemId()));
				float rate = billItems.get(i).getOrderRate();
				float discountPer = Float
						.parseFloat(request.getParameter("discPer" + i + "" + billItems.get(i).getItemId()));
				float taxPer = Float.parseFloat(request.getParameter("taxPer" + i + "" + billItems.get(i).getItemId()));
				int isTaxIncluding = billItems.get(i).getIsTaxIncluding();
				float tax1 = billItems.get(i).getSgstPer();
				float tax2 = billItems.get(i).getCgstPer();
				float tax3 = billItems.get(i).getIgstPer();

				if (qty > 0) {

					BillDetail billDetail = new BillDetail();
					billDetail.setItemId(billItems.get(i).getItemId());
					if (orderId.isEmpty()) {
						orderId.put(billItems.get(i).getOrderId(), "");
						System.err.println("orderId" + orderId);
					} else {
						orderId.put(billItems.get(i).getOrderId(), "");
						System.err.println("orderId" + orderId);

					}
					if (chalanIdList.isEmpty()) {
						chalanIdList.put(billItems.get(i).getChalanId(), "");
						System.err.println("orderId" + chalanIdList);
					} else {
						chalanIdList.put(billItems.get(i).getChalanId(), "");
						System.err.println("chalanIdList" + chalanIdList);

					}
					MultiValueMap<String, Object> ma = new LinkedMultiValueMap<String, Object>();
					ma = new LinkedMultiValueMap<String, Object>();
					ma.add("custId", custId);
					Cust editCust = rest.postForObject(Constants.url + "getCustByCustId", ma, Cust.class);

					if (gstNo == 0) {
						System.out.println("Mrp: " + rate);
						System.out.println("Tax1 : " + tax1);
						System.out.println("tax2 : " + tax2);

						Float taxableAmt = (float) (qty * rate);
						taxableAmt = roundUp(taxableAmt);

						float discAmt = ((taxableAmt * discountPer) / 100);
						taxableAmt = taxableAmt - discAmt;

						float sgstRs = (taxableAmt * tax1) / 100;
						float cgstRs = (taxableAmt * tax2) / 100;
						float igstRs = (taxableAmt * tax3) / 100;

						sgstRs = roundUp(sgstRs);
						cgstRs = roundUp(cgstRs);
						igstRs = roundUp(igstRs);

						Float totalTax = sgstRs + cgstRs;
						totalTax = roundUp(totalTax);

						Float grandTotal = totalTax + taxableAmt;
						grandTotal = roundUp(grandTotal);

						grandTotalAmt = grandTotalAmt + grandTotal;
						// -------------------------------------------
						billDetail.setQty(qty);
						billDetail.setRate(rate);

						billDetail.setCgstPer(0);
						billDetail.setSgstPer(0);

						billDetail.setDiscAmt(discAmt);
						billDetail.setDiscPer(discountPer);

						if (editCust.getIsSameState() == 1) {
							billDetail.setCgstAmt(0);
							billDetail.setSgstAmt(0);
							billDetail.setIgstAmt(0);
						} else {

							billDetail.setCgstAmt(0);
							billDetail.setSgstAmt(0);
							billDetail.setIgstAmt(igstRs);
						}

						billDetail.setIgstPer(tax3);

						billDetail.setTaxableAmt(taxableAmt);
						billDetail.setTaxAmt(0);
						billDetail.setTotalAmt(taxableAmt);
						totalTaxable = totalTaxable + taxableAmt;
						totalTaxAmt = totalTaxAmt + totalTax;
					} else {
						Float mrpBaseRate = (rate * 100) / (100 + (tax1 + tax2));
						mrpBaseRate = roundUp(mrpBaseRate);

						System.out.println("Mrp: " + rate);
						System.out.println("Tax1 : " + tax1);
						System.out.println("tax2 : " + tax2);

						Float taxableAmt = (float) (mrpBaseRate * qty);
						taxableAmt = roundUp(taxableAmt);

						float discAmt = ((taxableAmt * discountPer) / 100);
						taxableAmt = taxableAmt - discAmt;

						float sgstRs = (taxableAmt * tax1) / 100;
						float cgstRs = (taxableAmt * tax2) / 100;
						float igstRs = (taxableAmt * tax3) / 100;

						sgstRs = roundUp(sgstRs);
						cgstRs = roundUp(cgstRs);
						igstRs = roundUp(igstRs);

						Float totalTax = sgstRs + cgstRs;
						totalTax = roundUp(totalTax);

						Float grandTotal = totalTax + taxableAmt;
						grandTotal = roundUp(grandTotal);

						totalTaxable = totalTaxable + taxableAmt;
						totalTaxAmt = totalTaxAmt + totalTax;
						grandTotalAmt = grandTotalAmt + grandTotal;
						// -------------------------------------------
						billDetail.setQty(qty);
						billDetail.setRate(mrpBaseRate);

						if (editCust.getIsSameState() == 1) {
							billDetail.setCgstAmt(cgstRs);
							billDetail.setSgstAmt(sgstRs);
							billDetail.setIgstAmt(0);
						} else {

							billDetail.setCgstAmt(0);
							billDetail.setSgstAmt(0);
							billDetail.setIgstAmt(igstRs);
						}

						billDetail.setCgstPer(tax2);

						billDetail.setDiscAmt(discAmt);
						billDetail.setDiscPer(discountPer);

						billDetail.setSgstPer(tax1);

						billDetail.setIgstPer(tax3);

						billDetail.setTaxableAmt(taxableAmt);
						billDetail.setTaxAmt(totalTax);

						billDetail.setTotalAmt(grandTotal);
					}

					billDetail.setBillDetailId(0);
					billDetail.setBillHeadId(0);
					billDetail.setDelStatus(1);
					billDetail.setExFloat1(0);
					billDetail.setExFloat2(0);
					billDetail.setExInt1(billItems.get(i).getIsTaxIncluding());
					billDetail.setExInt2(0);
					billDetail.setExVar1("");
					billDetail.setExVar2("");
					billDetail.setHsnCode(billItems.get(i).getHsnCode());
					billDetailList.add(billDetail);
					System.err.println("billDetail" + billDetail.toString());
					chalanDetailList.add(billItems.get(i).getChalanDetailId());
				}

			}
			String cIdList = "";
			for (Map.Entry m : chalanIdList.entrySet()) {
				cIdList = cIdList + m.getKey() + ",";
			}
			billHeader.setChallanId(removeComma(cIdList));
			billHeader.setBillDetailList(billDetailList);
			String orderIdList = "";
			for (Map.Entry m : orderId.entrySet()) {
				orderIdList = orderIdList + m.getKey() + ",";
			}
			billHeader.setOrderId(removeComma(orderIdList));
			billHeader.setTaxableAmt(totalTaxable);
			billHeader.setTaxAmt(totalTaxAmt);
			billHeader.setTotalAmt(grandTotalAmt);

			System.err.println("billHeader" + billHeader.toString());
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			billHeader.setBillNo(billNo);
			billHeader.setBillDetailList(billDetailList);

			MultiValueMap<String, Object> mapObj = new LinkedMultiValueMap<String, Object>();
			System.err.println("custId for getPoByCust  " + custId);
			System.err.println("custId for getPoByCust  " + removeComma(cIdList));

			mapObj.add("custId", custId);
			mapObj.add("chalanIdList", removeComma(cIdList));

			PoHeader[] poHeaderListArray = rest.postForObject(Constants.url + "getPoNoByBill", mapObj,
					PoHeader[].class);

			List<PoHeader> poHeaderList = new ArrayList<PoHeader>(Arrays.asList(poHeaderListArray));

			System.out.println("poHeaderList====" + poHeaderList.toString());
			ArrayList<String> poNOList = new ArrayList<>();

			if (poHeaderList.size() > 0) {
				for (int i = 0; i < poHeaderList.size(); i++) {
					poNOList.add(poHeaderList.get(i).getPoNo());
				}
			}

			System.out.println("poNOLIST : --------------------------------------"
					+ poNOList.toString().substring(1, poNOList.toString().length() - 1));

			billHeader.setExVar1(poNOList.toString().substring(1, poNOList.toString().length() - 1));

			if (grandTotalAmt > 0) {

				BillHeader insertBillHeadRes = rest.postForObject(Constants.url + "saveBills", billHeader,
						BillHeader.class);
				billHeadId = insertBillHeadRes.getBillHeadId();
				custId = insertBillHeadRes.getCustId();
				pdfCustId = insertBillHeadRes.getCustId();
				map = new LinkedMultiValueMap<String, Object>();
				map.add("custId", insertBillHeadRes.getCustId());
				Cust editCust = rest.postForObject(Constants.url + "getCustByCustId", map, Cust.class);

				System.out.println("Send To Email Address" + editCust.getCustEmail());

				if (insertBillHeadRes != null) {

					PayRecoveryHead payRecoveryHead = new PayRecoveryHead();

					payRecoveryHead.setBillNo(billNo);
					payRecoveryHead.setBillTotal(grandTotalAmt);
					payRecoveryHead.setBillDate(DateConvertor.convertToYMD(billDate));
					payRecoveryHead.setCustId(custId);
					payRecoveryHead.setDelStatus(1);
					payRecoveryHead.setExBool1(1);
					payRecoveryHead.setUserId(login.getUser().getUserId());
					payRecoveryHead.setExBool2(1);
					payRecoveryHead.setStatus(0);
					payRecoveryHead.setRemark("NA");
					payRecoveryHead.setPendingAmt(grandTotalAmt);
					payRecoveryHead.setBillHeadId(insertBillHeadRes.getBillHeadId());

					payRecoveryHead.setExDate1(curDate);
					payRecoveryHead.setExInt1(0);
					payRecoveryHead.setExInt2(0);
					payRecoveryHead.setExInt3(0);
					payRecoveryHead.setExVarchar1("NA");
					payRecoveryHead.setExVarchar2("NA");
					payRecoveryHead.setPaidAmt(0);

					MultiValueMap<String, Object> map2 = new LinkedMultiValueMap<String, Object>();

					map2.add("custId", custId);

					Cust cust = rest.postForObject(Constants.url + "getCustByCustId", map2, Cust.class);

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					Calendar c = Calendar.getInstance();
					Calendar c1 = Calendar.getInstance();

					c.setTime(sdf.parse(billDate));
					c1.setTime(sdf.parse(billDate));

					System.out.println("cust credit days" + cust.getCreaditDays());

					c.add(Calendar.DAY_OF_MONTH, (int) cust.getCreaditDays());
					c1.add(Calendar.DAY_OF_MONTH, (int) (cust.getCreaditDays() - 5));
					String creaditDate2 = sdf.format(c.getTimeInMillis());

					String creaditDate1 = sdf.format(c1.getTimeInMillis());

					payRecoveryHead.setCreditDate2(DateConvertor.convertToYMD(creaditDate2));
					System.out.println("creaditDate2" + creaditDate2);

					payRecoveryHead.setCreditDate3(DateConvertor.convertToYMD(creaditDate1));
					payRecoveryHead.setCreditDate1(DateConvertor.convertToYMD(creaditDate1));

					PayRecoveryHead insertHeadRes = rest.postForObject(Constants.url + "savePaymentRecoveryHeader",
							payRecoveryHead, PayRecoveryHead.class);
					System.out.println(insertHeadRes.toString());

					map = new LinkedMultiValueMap<String, Object>();

					map.add("plantId", plantId);

					Plant updatePlant = rest.postForObject(Constants.url + "getPlantByPlantId", map, Plant.class);
					System.out.println("updatePlant-----------------------" + updatePlant.toString());

					if (gstNo == 0) {
						map = new LinkedMultiValueMap<String, Object>();
						int billCount = 1 + Integer.parseInt(updatePlant.getExVar3());

						map.add("billCount", billCount);
						map.add("plantId", plantId);

						Info updateplant = rest.postForObject(Constants.url + "updateBillNonGSTCounter", map,
								Info.class);
						System.out.println(updateplant.toString());
					} else {

						int billCount = 1 + Integer.parseInt(updatePlant.getExVar2());

						map = new LinkedMultiValueMap<String, Object>();

						map.add("billCount", billCount);
						map.add("plantId", plantId);

						Info updateCompany1 = rest.postForObject(Constants.url + "updateBillGSTCounter", map,
								Info.class);
						System.out.println("2222222" + updateCompany1.toString());
					}
					System.out.println(chalanDetailList.toString());
					map = new LinkedMultiValueMap<String, Object>();
					String idList = chalanDetailList.toString();
					String chList = idList.substring(1, idList.length() - 1).replace(", ", ",");
					map.add("chalanDetailId", chList);

					Info updateChalanStatus = rest.postForObject(Constants.url + "updateCostSegAfterBillGen", map,
							Info.class);

					System.err.println(updateChalanStatus.toString());

					map = new LinkedMultiValueMap<String, Object>();

					map.add("chalanIdList", insertBillHeadRes.getChallanId());

					RmcQuotTemp[] rmcItemQuot = rest.postForObject(Constants.url + "getTempItemDetailByChalanId", map,
							RmcQuotTemp[].class);
					rmcQuotTempList = new ArrayList<RmcQuotTemp>(Arrays.asList(rmcItemQuot));

					for (int i = 0; i < rmcQuotTempList.size(); i++) {
						map = new LinkedMultiValueMap<String, Object>();
						map.add("chalanNoList", insertBillHeadRes.getChallanId());
						map.add("billNo", insertBillHeadRes.getBillHeadId());

						Info updateBillNo = rest.postForObject(Constants.url + "/updateBillNo", map, Info.class);

					}

				} else {

				}
				System.err.println("insertBillHeadRes " + insertBillHeadRes.toString());
			}

		} catch (Exception e) {
			// isError = 1;
			System.err.println("exception In insertBillHeadRes " + e.getMessage());

			e.printStackTrace();

		}

		return "redirect:/showBill";
	}

	public String removeComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	@RequestMapping(value = "/showBillList", method = RequestMethod.GET)
	public ModelAndView showBillList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("bill/billList");

			model.addObject("title", "Bill List");
			model.addObject("status", -1);

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			List<Plant> plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

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

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("keyList", "11,12,13");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map, Setting[].class);
			settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);

		} catch (Exception e) {

			System.err.println("exception In showBillList at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/showCancleBillList", method = RequestMethod.GET)
	public ModelAndView showCancleBillList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("bill/cancleBillList");

			model.addObject("title", "Bill List");
			model.addObject("status", -1);

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			List<Plant> plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

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

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("keyList", "11,12,13");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map, Setting[].class);
			settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);

		} catch (Exception e) {

			System.err.println("exception In showBillList at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<GetBillHeader> getBillList = new ArrayList<>();

	@RequestMapping(value = "/getBillListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetBillHeader> getBillListBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));
		int statusList = Integer.parseInt(request.getParameter("statusList"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantId", plantId);
		map.add("tax", statusList);
		map.add("custId", custId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		map.add("delStatus", 1);

		GetBillHeader[] ordHeadArray = rest.postForObject(Constants.url + "getBillHeadersByDateAndCustAndPlant", map,
				GetBillHeader[].class);
		getBillList = new ArrayList<GetBillHeader>(Arrays.asList(ordHeadArray));

		System.out.println("getBillList" + getBillList.toString());
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		List<ExportToExcel> exportToExcelList1 = new ArrayList<ExportToExcel>();
		List<ExportToExcel> exportToExcelList2 = new ArrayList<ExportToExcel>();
		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Voucher Type");
		rowData.add("Voucher No.");
		rowData.add("Date");
		rowData.add("Reference No");
		rowData.add("Party Name");
		rowData.add("Dispatch Doc No.");
		rowData.add("Dispatch Through");
		rowData.add("Destination");
		rowData.add("Item Name");
		rowData.add("Qty");
		rowData.add("Rate");
		rowData.add("Taxable Amount");
		rowData.add("Sales Ledger");
		rowData.add("CGST Ledger");
		rowData.add("CGST Amt");
		rowData.add("SGST Ledger");
		rowData.add("SGST Amt");
		rowData.add("IGST Ledger");
		rowData.add("IGST Amt");
		rowData.add("Roundoff");
		rowData.add("Invoice Amt");
		rowData.add("Narration");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		System.out.println("hello...................");
		int cnt = 1;
		for (int i = 0; i < getBillList.size(); i++) {
			// 6
			System.out.println("item len is " + getBillList.get(i).getGetBillDetails().size());

			cnt = cnt + i;

			for (int j = 0; j < getBillList.get(i).getGetBillDetails().size(); j++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add("" + "Sales Voucher");
				rowData.add("" + getBillList.get(i).getBillNo());
				rowData.add("" + getBillList.get(i).getBillDate());
				rowData.add("" + getBillList.get(i).getBillNo());
				rowData.add("" + getBillList.get(i).getCustName());
				rowData.add("" + "-");
				rowData.add("" + "-");
				rowData.add("" + "-");
				rowData.add("" + getBillList.get(i).getGetBillDetails().get(j).getItemName());
				rowData.add("" + getBillList.get(i).getGetBillDetails().get(j).getQty());
				rowData.add("" + getBillList.get(i).getGetBillDetails().get(j).getTaxableAmt()
						/ getBillList.get(i).getGetBillDetails().get(j).getQty());
				rowData.add("" + getBillList.get(i).getGetBillDetails().get(j).getTaxableAmt());
				rowData.add("" + "GST Sales".concat(" ")
						.concat(String.valueOf(getBillList.get(i).getGetBillDetails().get(j).getCgstPer()
								+ getBillList.get(i).getGetBillDetails().get(j).getSgstPer()))
						.concat("%"));
				rowData.add("" + "CGST".concat(" ")
						.concat(String.valueOf(getBillList.get(i).getGetBillDetails().get(j).getCgstPer()))
						.concat("%"));
				rowData.add("" + getBillList.get(i).getGetBillDetails().get(j).getCgstAmt());
				rowData.add("" + "SGST".concat(" ")
						.concat(String.valueOf(getBillList.get(i).getGetBillDetails().get(j).getSgstPer()))
						.concat("%"));
				rowData.add("" + getBillList.get(i).getGetBillDetails().get(j).getSgstAmt());
				rowData.add("" + "IGST".concat(" ")
						.concat(String.valueOf(getBillList.get(i).getGetBillDetails().get(j).getSgstPer()))
						.concat("%"));
				rowData.add("" + getBillList.get(i).getGetBillDetails().get(j).getIgstAmt());

				float roundOff = (Math.round(getBillList.get(i).getTotalAmt()) - getBillList.get(i).getTotalAmt());

				DecimalFormat df = new DecimalFormat("#.##");
				String decimalRndOff = df.format(roundOff);

				rowData.add("" + decimalRndOff);
				rowData.add("" + Math.round(getBillList.get(i).getTotalAmt()));

				rowData.add("" + "-");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Bill List");

		map = new LinkedMultiValueMap<String, Object>();
		map.add("plantId", plantId);
		GetItem[] itemArray = rest.postForObject(Constants.url + "getGetItemsByPlantId", map, GetItem[].class);
		List<GetItem> getItemList = new ArrayList<GetItem>(Arrays.asList(itemArray));
		System.out.println("hello...................1111" + getItemList.toString());

		ExportToExcel expoExcel1 = new ExportToExcel();
		List<String> rowData1 = new ArrayList<String>();

		rowData1.add("Name");
		rowData1.add("Group");
		rowData1.add("HSN");
		rowData1.add("GST%");
		rowData1.add("Op.Qty");
		rowData1.add("Rate");
		rowData1.add("Amount");

		expoExcel1.setRowData(rowData1);
		exportToExcelList1.add(expoExcel1);

		int cnt1 = 1;

		for (int i = 0; i < getItemList.size(); i++) {
			expoExcel1 = new ExportToExcel();
			rowData1 = new ArrayList<String>();

			cnt1 = cnt1 + i;

			rowData1.add("" + getItemList.get(i).getItemName());

			rowData1.add("" + "Ready Mix Concrete");
			rowData1.add("" + getItemList.get(i).getHsnCode());
			rowData1.add("" + (getItemList.get(i).getCgst() + getItemList.get(i).getSgst()));
			rowData1.add("" + getItemList.get(i).getMaxStock());
			rowData1.add("" + getItemList.get(i).getItemRate1());
			rowData1.add("" + (getItemList.get(i).getMaxStock() * getItemList.get(i).getItemRate1()));

			expoExcel1.setRowData(rowData1);
			exportToExcelList1.add(expoExcel1);

		}

		session = request.getSession();
		session.setAttribute("exportExcelList1", exportToExcelList1);
		session.setAttribute("excelName1", "Item List");

		map = new LinkedMultiValueMap<String, Object>();
		map.add("plantId", plantId);

		GetCust[] custArray = rest.postForObject(Constants.url + "getAllCustomerList", map, GetCust[].class);
		List<GetCust> getCustList = new ArrayList<GetCust>(Arrays.asList(custArray));
		System.out.println("hello...................1111" + getCustList.toString());

		ExportToExcel expoExcel2 = new ExportToExcel();
		List<String> rowData2 = new ArrayList<String>();

		rowData2.add("Name");
		rowData2.add("Group");
		rowData2.add("GSTIN");
		rowData2.add("Default Credit Period");
		rowData2.add("Address1");
		rowData2.add("Address2");
		rowData2.add("Address3");
		rowData2.add("Address4");
		rowData2.add("Country");
		rowData2.add("State");
		rowData2.add("Contact Person");
		rowData2.add("Phone No.");
		rowData2.add("Mobile No.");
		rowData2.add("Fax No.");
		rowData2.add("Email ID");
		rowData2.add("CC");
		rowData2.add("Website");
		rowData2.add("Registration");

		expoExcel2.setRowData(rowData2);
		exportToExcelList2.add(expoExcel2);

		int cnt2 = 1;

		for (int i = 0; i < getCustList.size(); i++) {
			expoExcel2 = new ExportToExcel();
			rowData2 = new ArrayList<String>();

			cnt2 = cnt2 + i;

			rowData2.add("" + getCustList.get(i).getCustName());

			rowData2.add("" + "Sundry Debtor");
			rowData2.add("" + getCustList.get(i).getCustGstNo());
			rowData2.add("" + "-");
			rowData2.add("" + getCustList.get(i).getCustAddress());
			rowData2.add("" + "-");
			rowData2.add("" + "-");
			rowData2.add("" + "-");
			rowData2.add("" + "India");
			rowData2.add("" + "Maharashtra");
			rowData2.add("" + "-");
			rowData2.add("" + "-");

			rowData2.add("" + getCustList.get(i).getCustMobNo());
			rowData2.add("" + "-");
			rowData2.add("" + "-");
			rowData2.add("" + "-");
			rowData2.add("" + "-");
			expoExcel2.setRowData(rowData2);
			exportToExcelList2.add(expoExcel2);

		}

		session = request.getSession();
		session.setAttribute("exportExcelList2", exportToExcelList2);
		session.setAttribute("excelName2", "Customer List");

		return getBillList;
	}

	@RequestMapping(value = "/getCancleBillListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetBillHeader> getCancleBillListBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));
		int statusList = Integer.parseInt(request.getParameter("statusList"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantId", plantId);
		map.add("tax", statusList);
		map.add("custId", custId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		map.add("delStatus", 0);

		GetBillHeader[] ordHeadArray = rest.postForObject(Constants.url + "getBillHeadersByDateAndCustAndPlant", map,
				GetBillHeader[].class);
		getBillList = new ArrayList<GetBillHeader>(Arrays.asList(ordHeadArray));

		System.out.println("getBillList" + getBillList.toString());

		return getBillList;
	}

	List<GetBillDetail> billDetailList;

	@RequestMapping(value = "/editBill/{billHeadId}", method = RequestMethod.GET)
	public ModelAndView editBill(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int billHeadId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("bill/editBill");
			GetBillHeader editBill = null;

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("billHeadId", billHeadId);
			editBill = rest.postForObject(Constants.url + "getBillHeaderById", map, GetBillHeader.class);
			System.err.println(editBill.toString());
			map = new LinkedMultiValueMap<String, Object>();

			String chalanIdList = editBill.getChallanId();

			System.out.println("chalanIdList" + chalanIdList);

			map.add("chalanIdList", chalanIdList);

			GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getChalanHeadersByChalanIdList", map,
					GetChalanHeader[].class);

			List<GetChalanHeader> chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));
			model.addObject("chalanHeadList", chalanHeadList);

			System.out.println("chalanHeadList" + chalanHeadList.toString());

			map.add("custId", editBill.getCustId());
			List<Project> projList;
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));
			System.err.println(projList.toString());
			map = new LinkedMultiValueMap<String, Object>();

			map.add("billHeadId", billHeadId);
			GetBillDetail[] billDetailArray = rest.postForObject(Constants.url + "getBillDetailsById", map,
					GetBillDetail[].class);
			billDetailList = new ArrayList<GetBillDetail>(Arrays.asList(billDetailArray));
			System.err.println(billDetailList.toString());

			model.addObject("billDetailList", billDetailList);

			model.addObject("editBill", editBill);
			model.addObject("projList", projList);

			model.addObject("title", "Edit Bill");

		} catch (Exception e) {
			System.err.println("Exce in edit Bill " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/addBillByChalan/{chalanId}", method = RequestMethod.GET)
	public ModelAndView addBillByChalan(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int chalanId) {

		ModelAndView model = null;
		try {

			HttpSession httpSession = request.getSession();
			LoginResUser login = (LoginResUser) httpSession.getAttribute("UserDetail");

			model = new ModelAndView("bill/addBill1");
			GetChalanHeader addBill = null;

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanId", chalanId);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			model.addObject("curDate", curDate);

			addBill = rest.postForObject(Constants.url + "getChalanHeadersByChalanId", map, GetChalanHeader.class);
			System.err.println(addBill.toString());

			map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 6);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			model.addObject("doc", doc);

			model.addObject("addBill", addBill);

			model.addObject("title", "Add Bill");

			System.out.println("Generte Bill" + addBill.toString());

			map = new LinkedMultiValueMap<String, Object>();

			map.add("projId", addBill.getProjId());
			map.add("custId", addBill.getCustId());
			map.add("chalanStatus", "0,1");

			GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getChalanHeadersByCustAndStatusAndProj",
					map, GetChalanHeader[].class);

			List<GetChalanHeader> chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));

			model.addObject("chalanHeadList", chalanHeadList);

			map.add("companyId", addBill.getCompanyId());

			Company editComp = rest.postForObject(Constants.url + "getCompByCompanyId", map, Company.class);
			model.addObject("editComp", editComp);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", addBill.getPlantId());

			Plant editPlant = rest.postForObject(Constants.url + "getPlantByPlantId", map, Plant.class);
			model.addObject("editPlant", editPlant);

			String var = null;

			String b = editPlant.getExVar2();
			if (String.valueOf(b).length() == 1) {
				var = "000".concat(String.valueOf(b));

			} else if (String.valueOf(b).length() == 2) {
				var = "00".concat(String.valueOf(b));

			}

			model.addObject("var", var);
			System.out.println("Var" + var);

		} catch (Exception e) {
			System.err.println("Exce in Add Bill By Chalan " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/updateBill", method = RequestMethod.POST)
	public String updateOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// String curDate = dateFormat.format(new Date());
			List<Integer> chalanDetailList = new ArrayList<Integer>();
			model = new ModelAndView("bill/editBill");

			// model.addObject("title", "Add Order");

			String billDate = request.getParameter("ord_date");

			int billHeadId = Integer.parseInt(request.getParameter("billHeadId"));
			// int custId = Integer.parseInt(request.getParameter("cust_name"));
			// int projId = Integer.parseInt(request.getParameter("proj_id"));
			// int poId = Integer.parseInt(request.getParameter("po_id"));

			// String ordDate = request.getParameter("ord_date");
			// String delDate = request.getParameter("del_date");

			// float itemTotal = Float.parseFloat(request.getParameter("itemTotal"));
			List<BillDetail> billDetailsList = new ArrayList<>();
			BillHeader billHeader = new BillHeader();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billHeadId", billHeadId);
			billHeader = rest.postForObject(Constants.url + "getBillHeaderByBillHeadId", map, BillHeader.class);

			float totalTaxable = 0.0f;
			float totalTaxAmt = 0.0f;
			float grandTotalAmt = 0.0f;
			for (int i = 0; i < billDetailList.size(); i++) {

				float qty = Float
						.parseFloat(request.getParameter("chalanQty" + i + "" + billDetailList.get(i).getItemId()));

				float rate = Float
						.parseFloat(request.getParameter("billRate" + i + "" + billDetailList.get(i).getItemId()));
				/* float rate = billDetailList.get(i).getRate(); */
				float discountPer = Float
						.parseFloat(request.getParameter("discPer" + i + "" + billDetailList.get(i).getItemId()));
				float taxPer = Float
						.parseFloat(request.getParameter("taxPer" + i + "" + billDetailList.get(i).getItemId()));
				int isTaxIncluding = billDetailList.get(i).getExInt1();
				float tax1 = billDetailList.get(i).getSgstPer();
				float tax2 = billDetailList.get(i).getCgstPer();
				float tax3 = billDetailList.get(i).getIgstPer();

				System.out.println("rateraterate" + rate);

				MultiValueMap<String, Object> ma = new LinkedMultiValueMap<String, Object>();
				ma = new LinkedMultiValueMap<String, Object>();
				ma.add("custId", billHeader.getCustId());
				Cust editCust = rest.postForObject(Constants.url + "getCustByCustId", ma, Cust.class);

				if (qty > 0) {

					BillDetail billDetail = new BillDetail();
					billDetail.setItemId(billDetailList.get(i).getItemId());

					if (billHeader.getExInt2() == 0) {
						System.out.println("IN IF" + billHeader.getExInt2());
						System.out.println("Mrp: " + rate);
						System.out.println("Tax1 : " + tax1);
						System.out.println("tax2 : " + tax2);

						Float taxableAmt = (float) (qty * rate);
						taxableAmt = roundUp(taxableAmt);

						float discAmt = ((taxableAmt * discountPer) / 100);
						taxableAmt = taxableAmt - discAmt;

						float sgstRs = (taxableAmt * tax1) / 100;
						float cgstRs = (taxableAmt * tax2) / 100;
						float igstRs = (taxableAmt * tax3) / 100;

						sgstRs = roundUp(sgstRs);
						cgstRs = roundUp(cgstRs);
						igstRs = roundUp(igstRs);

						Float totalTax = sgstRs + cgstRs;
						totalTax = roundUp(totalTax);

						Float grandTotal = totalTax + taxableAmt;
						grandTotal = roundUp(grandTotal);

						grandTotalAmt = grandTotalAmt + grandTotal;
						// -------------------------------------------
						billDetail.setQty(qty);
						billDetail.setRate(rate);

						billDetail.setCgstPer(0);
						billDetail.setSgstPer(0);

						billDetail.setDiscAmt(discAmt);
						billDetail.setDiscPer(discountPer);

						if (editCust.getIsSameState() == 1) {
							billDetail.setCgstAmt(0);
							billDetail.setSgstAmt(0);
							billDetail.setIgstAmt(0);
						} else {

							billDetail.setCgstAmt(0);
							billDetail.setSgstAmt(0);
							billDetail.setIgstAmt(igstRs);
						}

						billDetail.setIgstPer(tax3);

						billDetail.setTaxableAmt(taxableAmt);
						billDetail.setTaxAmt(0);
						billDetail.setTotalAmt(taxableAmt);
						totalTaxable = totalTaxable + taxableAmt;
						totalTaxAmt = totalTaxAmt + totalTax;
					} else {

						System.out.println("IN ELSE" + billHeader.getExInt2());

						System.out.println("Mrp: " + rate);
						System.out.println("Tax1 : " + tax1);
						System.out.println("tax2 : " + tax2);

						Float taxableAmt = (float) (rate * qty);
						taxableAmt = roundUp(taxableAmt);

						float discAmt = ((taxableAmt * discountPer) / 100);
						taxableAmt = taxableAmt - discAmt;

						float sgstRs = (taxableAmt * tax1) / 100;
						float cgstRs = (taxableAmt * tax2) / 100;
						float igstRs = (taxableAmt * tax3) / 100;

						sgstRs = roundUp(sgstRs);
						cgstRs = roundUp(cgstRs);
						igstRs = roundUp(igstRs);

						Float totalTax = sgstRs + cgstRs;
						totalTax = roundUp(totalTax);

						Float grandTotal = totalTax + taxableAmt;
						grandTotal = roundUp(grandTotal);

						totalTaxable = totalTaxable + taxableAmt;
						totalTaxAmt = totalTaxAmt + totalTax;
						grandTotalAmt = grandTotalAmt + grandTotal;
						// -------------------------------------------
						billDetail.setQty(qty);
						billDetail.setRate(rate);

						if (editCust.getIsSameState() == 1) {
							billDetail.setCgstAmt(cgstRs);
							billDetail.setSgstAmt(sgstRs);
							billDetail.setIgstAmt(0);
						} else {

							billDetail.setCgstAmt(0);
							billDetail.setSgstAmt(0);
							billDetail.setIgstAmt(igstRs);
						}

						billDetail.setCgstPer(tax2);

						billDetail.setDiscAmt(discAmt);
						billDetail.setDiscPer(discountPer);

						billDetail.setSgstPer(tax1);

						billDetail.setIgstPer(tax3);

						billDetail.setTaxableAmt(taxableAmt);
						billDetail.setTaxAmt(totalTax);

						billDetail.setTotalAmt(grandTotal);
					}

					billDetail.setBillDetailId(billDetailList.get(i).getBillDetailId());
					billDetail.setBillHeadId(billDetailList.get(i).getBillHeadId());
					billDetail.setDelStatus(billDetailList.get(i).getDelStatus());
					billDetail.setExFloat1(billDetailList.get(i).getExFloat1());
					billDetail.setExFloat2(billDetailList.get(i).getExFloat2());
					billDetail.setExInt1(billDetailList.get(i).getExInt1());
					billDetail.setExInt2(billDetailList.get(i).getExInt2());
					billDetail.setExVar1(billDetailList.get(i).getExVar1());
					billDetail.setExVar2(billDetailList.get(i).getExVar2());
					billDetail.setHsnCode(billDetailList.get(i).getHsnCode());
					billDetailsList.add(billDetail);
					System.err.println("billDetail" + billDetail.toString());
				}

			}

			// billHeader.setChallanId(billHeader.getChallanId());
			billHeader.setBillDetailList(billDetailsList);

			// billHeader.setOrderId(billHeader.getOrderId());
			billHeader.setTaxableAmt(totalTaxable);
			billHeader.setTaxAmt(totalTaxAmt);
			billHeader.setTotalAmt(grandTotalAmt);
			billHeader.setBillDate(billDate);

			System.err.println("billHeader" + billHeader.toString());

			// billHeader.setBillNo(billHeader.getBillNo());

			BillHeader insertBillHeadRes = rest.postForObject(Constants.url + "saveBills", billHeader,
					BillHeader.class);

			if (insertBillHeadRes != null) {
				// isError = 2;

				map = new LinkedMultiValueMap<String, Object>();

				map.add("chalanDetailId", billHeader.getChallanId());

				Info updateChalanStatus = rest.postForObject(Constants.url + "updateChalanStatus", map, Info.class);

				System.err.println(updateChalanStatus.toString());

				map = new LinkedMultiValueMap<String, Object>();

				map.add("billHeadId", billHeadId);

				PayRecoveryHead editPaymentRec = rest.postForObject(Constants.url + "getPayRecByPayHeadId", map,
						PayRecoveryHead.class);

				editPaymentRec.setBillTotal(grandTotalAmt);
				editPaymentRec.setBillDate(DateConvertor.convertToYMD(billDate));
				editPaymentRec.setBillHeadId(billHeadId);
				editPaymentRec.setPendingAmt(grandTotalAmt);

				editPaymentRec.setPaidAmt(0);

				System.out.println(editPaymentRec.toString());

				MultiValueMap<String, Object> map2 = new LinkedMultiValueMap<String, Object>();

				map2.add("custId", insertBillHeadRes.getCustId());
				Cust cust = rest.postForObject(Constants.url + "getCustByCustId", map2, Cust.class);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Calendar c = Calendar.getInstance();
				Calendar c1 = Calendar.getInstance();

				c.setTime(sdf.parse(billDate));
				c1.setTime(sdf.parse(billDate));

				System.out.println("cust credit days" + cust.getCreaditDays());

				c.add(Calendar.DAY_OF_MONTH, (int) cust.getCreaditDays());
				c1.add(Calendar.DAY_OF_MONTH, (int) (cust.getCreaditDays() - 5));
				String creaditDate2 = sdf.format(c.getTimeInMillis());

				String creaditDate1 = sdf.format(c1.getTimeInMillis());
				editPaymentRec.setCreditDate2(DateConvertor.convertToYMD(creaditDate2));
				System.out.println("creaditDate2" + creaditDate2);

				editPaymentRec.setCreditDate3(DateConvertor.convertToYMD(creaditDate1));
				editPaymentRec.setCreditDate1(DateConvertor.convertToYMD(creaditDate1));

				PayRecoveryHead insertHeadRes = rest.postForObject(Constants.url + "savePaymentRecoveryHeader",
						editPaymentRec, PayRecoveryHead.class);

				System.out.println(insertHeadRes.toString());
			} else {

				// isError = 1;
			}
			System.err.println("insertBillHeadRes " + insertBillHeadRes.toString());

		} catch (Exception e) {
			// isError = 1;
			System.err.println("exception In updateBill at BillController " + e.getMessage());

			e.printStackTrace();

		}

		return "redirect:/showBillList";
	}

	@RequestMapping(value = "pdf/showBillsPdf/{billHeadId}/{settingsVal}", method = RequestMethod.GET)
	public ModelAndView showBillsPdf(@PathVariable("billHeadId") String[] billTempIds,
			@PathVariable("settingsVal") String[] settingsVal, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("bill/allBillPdf");

		try {

			RestTemplate rest = new RestTemplate();
			String strBillTempIds = new String();
			for (int i = 0; i < billTempIds.length; i++) {
				strBillTempIds = strBillTempIds + "," + billTempIds[i];
			}
			strBillTempIds = strBillTempIds.substring(1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billTempIds", strBillTempIds);
			GetBillHeaderPdf[] billHeaderRes = rest.postForObject(Constants.url + "/findBillsByHeaderId", map,
					GetBillHeaderPdf[].class);
			ArrayList<GetBillHeaderPdf> billHeaders = new ArrayList<GetBillHeaderPdf>(Arrays.asList(billHeaderRes));
			System.out.println("Complete order data is::::" + billHeaders.get(0).getGetBillDetails().get(0).toString());
			for (int i = 0; i < billHeaders.size(); i++) {
				map = new LinkedMultiValueMap<String, Object>();
				map.add("billHeadIds", billHeaders.get(i).getBillHeadId());

				GetBillDetByHsn[] getBillDetByHsnRes = rest
						.postForObject(Constants.url + "/getBillDetailsByIdAndHsnReport", map, GetBillDetByHsn[].class);
				ArrayList<GetBillDetByHsn> hsnpdf = new ArrayList<GetBillDetByHsn>(Arrays.asList(getBillDetByHsnRes));
				billHeaders.get(i).setGetBillDetByHsn(hsnpdf);

				String printWord = Currency
						.convertToIndianCurrency(String.valueOf(Math.round(billHeaders.get(i).getTotalAmt())));
				billHeaders.get(i).setPrintWord(printWord);
			}

			String settingsValues = new String();
			for (int i = 0; i < settingsVal.length; i++) {
				settingsValues = settingsValues + "," + settingsVal[i];
			}
			settingsValues = settingsValues.substring(1);

			System.out.println("settingsValues============" + settingsValues);

			MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<String, Object>();

			map1.add("keyList", settingsValues);

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map1, Setting[].class);
			settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);

			String a = billHeaders.get(0).getGetBillDetails().get(0).getRefNo();
			model.addObject("ref", a);
			System.out.println("999" + a);
			String b = billHeaders.get(0).getGetBillDetails().get(0).getOrderNo();
			model.addObject("b", b);
			System.out.println("999" + b);

			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("Currency", new Currency());
			model.addObject("billHeaderList", billHeaders);

			model.addObject("val", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
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

	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	public void showPDF(HttpServletRequest request, HttpServletResponse response) {

		String url = request.getParameter("url");
		System.out.println("URL " + url);
		// http://monginis.ap-south-1.elasticbeanstalk.com
		// File f = new File("/report.pdf");
		// File f = new File("/home/lenovo/Desktop/bill.pdf");
		// File f = new File("E:\\bill.pdf");
		File f = new File("apache-tomcat-8.5.40/webapps/uploads/shiv/bill.pdf");

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

		// String filename = "/home/lenovo/Desktop/bill.pdf";
		// String filename = "E:\\bill.pdf";
		String filename = "apache-tomcat-8.5.40/webapps/uploads/shiv/bill.pdf";
		// String filePath = "/home/lenovo/Desktop/bill.pdf";
		String filePath = "apache-tomcat-8.5.40/webapps/uploads/shiv/bill.pdf";

		// "/Users/MIRACLEINFOTAINMENT/ATS/uplaods/reports/ordermemo221.pdf";
		// String filePath = "E:\\bill.pdf";
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
			if (billHeadId != 0) {
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

						System.out.println("custId======================================" + pdfCustId);
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
						billHeadId = 0;
						pdfCustId = 0;
						Transport.send(mimeMessage);
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

	/// --------------------------Pending Bill--------------------------

	@RequestMapping(value = "/showPendingBillList", method = RequestMethod.GET)
	public ModelAndView showPendingBillList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("bill/pendingBillList");

			model.addObject("title", "Pending Bill List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			List<Plant> plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

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

	@RequestMapping(value = "/getPendingBillListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetChalanHeader> getPendingBillListBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));

		map.add("plantId", plantId);
		map.add("custId", custId);

		GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getPendingBillListByPlantAndCust", map,
				GetChalanHeader[].class);

		List<GetChalanHeader> chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));
		System.out.println("chalanHeadList" + chalanHeadList.toString());

		return chalanHeadList;
	}

	// Ajax call
	@RequestMapping(value = "/getCompanyByCompanyId", method = RequestMethod.GET)
	public @ResponseBody Company getCompanyByCompanyId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int companyId = Integer.parseInt(request.getParameter("companyId"));

		map.add("companyId", companyId);

		Company editComp = rest.postForObject(Constants.url + "getCompByCompanyId", map, Company.class);

		System.err.println("Ajax editComp List " + editComp.toString());

		return editComp;

	}

	@RequestMapping(value = "/deleteBill/{billHeadId}", method = RequestMethod.GET)
	public String deleteBill(HttpServletRequest request, HttpServletResponse response, @PathVariable int billHeadId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("billHeadId", billHeadId);

			Info errMsg = rest.postForObject(Constants.url + "deleteBill", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteBill @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showBillList";
	}

}