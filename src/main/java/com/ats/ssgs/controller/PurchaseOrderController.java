package com.ats.ssgs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.GetPoHeader;
import com.ats.ssgs.model.GetQuotHeader;
import com.ats.ssgs.model.PoDetail;
import com.ats.ssgs.model.PoHeader;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.quot.QuotDetail;
import com.ats.ssgs.model.quot.QuotHeader;

@Controller
@Scope("session")
public class PurchaseOrderController {

	RestTemplate rest = new RestTemplate();
	GetQuotHeader quotHeader = new GetQuotHeader();

	@RequestMapping(value = "/addPo/{quotId}/{plantId}", method = RequestMethod.GET)
	public ModelAndView editQuot(HttpServletRequest request, HttpServletResponse response, @PathVariable int quotId,
			@PathVariable int plantId) {

		ModelAndView model = new ModelAndView("purchaseOrder/addPo");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotHeadId", quotId);
			quotHeader = rest.postForObject(Constants.url + "/getQuotHeaderWithNameByQuotHeadId", map,
					GetQuotHeader.class);
			quotHeader.setQuotDate(DateConvertor.convertToDMY(quotHeader.getQuotDate()));
			model.addObject("quotHeader", quotHeader);

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			model.addObject("todayDate", sf.format(date));

			int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
			int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);
			String financiyalYearFrom = "";
			String financiyalYearTo = "";
			if (CurrentMonth < 4) {
				financiyalYearFrom = "" + (CurrentYear - 1);
				financiyalYearTo = "" + (CurrentYear);
			} else {
				financiyalYearFrom = "" + (CurrentYear);
				financiyalYearTo = "" + (CurrentYear + 1);
			}

			int ab = (Integer.parseInt(financiyalYearFrom)) % 2000;
			int ab1 = (Integer.parseInt(financiyalYearTo)) % 2000;

			System.out.println("year sc:" + ab + ab1);

			System.out.println("year:" + financiyalYearFrom + financiyalYearTo);

			model.addObject("fyf", ab);
			model.addObject("fyt", ab1);
			String var = null;
			map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 7);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			model.addObject("doc", doc);
			System.out.println("doc data is" + doc);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("plantId", plantId);

			Plant pl = rest.postForObject(Constants.url + "getPlantByPlantId", map, Plant.class);
			String shortName = pl.getPlantFax1();
			System.out.println("pl is " + pl.toString());
			System.out.println("short name  " + shortName);

			model.addObject("shortName", shortName);

			int a = doc.getSrNo();
			if (String.valueOf(a).length() == 1) {
				var = "0000".concat(String.valueOf(a));

			} else if (String.valueOf(a).length() == 2) {
				var = "000".concat(String.valueOf(a));

			} else if (String.valueOf(a).length() == 3) {
				var = "00".concat(String.valueOf(a));

			}
			System.out.println("doc data is" + doc);
			model.addObject("var", var);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/submitPurchaseOrder", method = RequestMethod.POST)
	public ModelAndView submitPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model =null;
		int plantId =0;
		try {
			 model = new ModelAndView("purchaseOrder/poListDash");

			 model.addObject("title", "PO List");
			 
				Calendar date = Calendar.getInstance();
				date.set(Calendar.DAY_OF_MONTH, 1);

				Date firstDate = date.getTime();

				DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

				String fromDate = dateFormat.format(firstDate);

				String toDate = dateFormat.format(new Date());
				
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
			
			String poRemark = request.getParameter("poRemark");
			String poDate = request.getParameter("poDate");
			String poValidityDate = request.getParameter("poValidityDate");
			String delivery = request.getParameter("delivery");
			String poNo = request.getParameter("poNo");
			System.out.println("ponum is" + poNo);
			float taxIncl = Float.parseFloat(request.getParameter("taxIncl"));

			PoHeader save = new PoHeader();

			save.setCustId(quotHeader.getCustId());
			save.setPoNo(poNo);
			save.setRemark(poRemark);
			save.setPoDate(DateConvertor.convertToYMD(poDate));
			save.setCustProjectId(quotHeader.getProjId());
			save.setQuatationId(quotHeader.getQuotHeadId());
			save.setQuatationNo(quotHeader.getQuotNo());
			save.setPoValidityDate(DateConvertor.convertToYMD(poValidityDate));
			save.setPoTermId(quotHeader.getPayTermId());
			save.setPlantId(Integer.parseInt(quotHeader.getPlantIds()));
			save.setVarchar1(delivery);
			save.setQutDate(DateConvertor.convertToYMD(quotHeader.getQuotDate()));
			save.setDelStatus(1);
			save.setExtra1((int) taxIncl);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 7);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);

			List<PoDetail> poDetailList = new ArrayList<PoDetail>();

			for (int i = 0; i < quotHeader.getGetQuotDetailList().size(); i++) {

				PoDetail poDetail = new PoDetail();
				poDetail.setItemId(quotHeader.getGetQuotDetailList().get(i).getItemId());
				poDetail.setQuDetailId(quotHeader.getGetQuotDetailList().get(i).getQuotDetailId());
				poDetail.setPoQty(Float.parseFloat(
						request.getParameter("pOqty" + quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setPoRate(Float.parseFloat(
						request.getParameter("taxableAmt" + quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setTaxableAmt(poDetail.getPoRate());
				poDetail.setTaxAmt(Float.parseFloat(
						request.getParameter("taxAmt" + quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setOtherCharges(Float.parseFloat(
						request.getParameter("othCostAftTax" + quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setTotal(Float.parseFloat(
						request.getParameter("finalAmt" + quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setTaxPer(quotHeader.getGetQuotDetailList().get(i).getSgstPer()
						+ quotHeader.getGetQuotDetailList().get(i).getSgstPer()
						+ quotHeader.getGetQuotDetailList().get(i).getIgstPer());
				poDetail.setPoRemainingQty(poDetail.getPoQty());
				poDetailList.add(poDetail);

			}

			save.setPoDetailList(poDetailList);

			PoHeader res = rest.postForObject(Constants.url + "savePurchaseOrder", save, PoHeader.class);

			System.err.println("res  PoHeader insert " + res.toString());

			if (res != null) {
                
				map = new LinkedMultiValueMap<String, Object>();
				map.add("quotHeadId", quotHeader.getQuotHeadId());
				Info info = rest.postForObject(Constants.url + "/updateQuatationStatus", map, Info.class);
				map = new LinkedMultiValueMap<String, Object>();
				map.add("srNo", doc.getSrNo() + 1);
				map.add("docCode", 7);
				Info updateDocSr = rest.postForObject(Constants.url + "updateDocSrNo", map, Info.class);
				System.out.println("info is   updateDocSr " + updateDocSr);
				
				
				plantId=res.getPlantId();
				System.out.println("plantId in loop ::::"+plantId);
				
				MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<String, Object>();
				map1.add("plantId", plantId);
	            Plant pl = rest.postForObject(Constants.url + "getPlantByPlantId", map1, Plant.class);
				
				
				model.addObject("plantId1", plantId);
				//model.addObject("pname", pl);
				Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
				List<Plant> plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

				System.out.println("plant is" + plantList);

				model.addObject("plantList", plantList);
	     
			

				for (int i = 0; i < quotHeader.getGetQuotDetailList().size(); i++) {
					map = new LinkedMultiValueMap<String, Object>();
					map.add("quotDetailId", quotHeader.getGetQuotDetailList().get(i).getQuotDetailId());
					map.add("quotNo", quotHeader.getQuotHeadId());
					map.add("poNo", res.getPoId());

					Info updateQuotNo = rest.postForObject(Constants.url + "/updateQuotNoAndPoNo", map, Info.class);

					 plantId = Integer.parseInt(quotHeader.getPlantIds());
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return model;
		//return new ModelAndView("redirect:" + "showPoListByStatus", "/",plantId);
		//return "redirect:/showPoListByStatus/plantId";
	}

	@RequestMapping(value = "/getPoList", method = RequestMethod.GET)
	public ModelAndView getPoList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/poList");
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			if (request.getParameter("fromDate") != null | request.getParameter("toDate") != null) {

				String fromDate = request.getParameter("fromDate");
				String toDate = request.getParameter("toDate");

				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));

				GetPoHeader[] getPoHeader = rest.postForObject(Constants.url + "/getPoListByDate", map,
						GetPoHeader[].class);

				List<GetPoHeader> poList = new ArrayList<GetPoHeader>(Arrays.asList(getPoHeader));

				model.addObject("poList", poList);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
			} else {
				map.add("fromDate", sf.format(date));
				map.add("toDate", sf.format(date));

				GetPoHeader[] getPoHeader = rest.postForObject(Constants.url + "/getPoListByDate", map,
						GetPoHeader[].class);

				List<GetPoHeader> poList = new ArrayList<GetPoHeader>(Arrays.asList(getPoHeader));

				model.addObject("poList", poList);
				model.addObject("fromDate", dd.format(date));
				model.addObject("toDate", dd.format(date));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/deletePurchaseOrder/{poId}", method = RequestMethod.GET)
	public String deletePurchaseOrder(@PathVariable int poId, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("poId", poId);

			Info info = rest.postForObject(Constants.url + "/deletePurchaseOrder", map, Info.class);

			System.out.println(info);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return "redirect:/getPoList";

	}

	GetPoHeader editPo = new GetPoHeader();

	@RequestMapping(value = "/editPo/{poId}", method = RequestMethod.GET)
	public ModelAndView editPo(@PathVariable int poId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/editPo");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("poId", poId);

			editPo = rest.postForObject(Constants.url + "/getPoHeaderWithDetail", map, GetPoHeader.class);
			model.addObject("editPo", editPo);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/submitEditPurchaseOrder", method = RequestMethod.POST)
	public String submitEditPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		try {

			String poRemark = request.getParameter("poRemark");
			String poDate = request.getParameter("poDate");
			String poValidityDate = request.getParameter("poValidityDate");
			String delivery = request.getParameter("delivery");
			String poNo = request.getParameter("poNo");

			PoHeader save = new PoHeader();

			editPo.setPoNo(poNo);
			editPo.setRemark(poRemark);
			editPo.setPoDate(DateConvertor.convertToYMD(poDate));
			editPo.setPoValidityDate(DateConvertor.convertToYMD(poValidityDate));
			editPo.setVarchar1(delivery);
			editPo.setQutDate(DateConvertor.convertToYMD(editPo.getQutDate()));
			try {
				editPo.setExtraDate1(DateConvertor.convertToYMD(editPo.getExtraDate1()));
				editPo.setExtraDate2(DateConvertor.convertToYMD(editPo.getExtraDate2()));
			} catch (Exception e) {

			}
			// List<PoDetail> poDetailList = new ArrayList<PoDetail>();

			for (int i = 0; i < editPo.getGetPoDetailList().size(); i++) {

				editPo.getGetPoDetailList().get(i).setPoQty(Float
						.parseFloat(request.getParameter("pOqty" + editPo.getGetPoDetailList().get(i).getItemId())));
				editPo.getGetPoDetailList().get(i).setPoRate(Float.parseFloat(
						request.getParameter("taxableAmt" + editPo.getGetPoDetailList().get(i).getItemId())));
				editPo.getGetPoDetailList().get(i).setTaxableAmt(editPo.getGetPoDetailList().get(i).getPoRate());
				editPo.getGetPoDetailList().get(i).setTaxAmt(Float
						.parseFloat(request.getParameter("taxAmt" + editPo.getGetPoDetailList().get(i).getItemId())));
				editPo.getGetPoDetailList().get(i).setOtherCharges(Float.parseFloat(
						request.getParameter("othCostAftTax" + editPo.getGetPoDetailList().get(i).getItemId())));
				editPo.getGetPoDetailList().get(i).setTotal(Float
						.parseFloat(request.getParameter("finalAmt" + editPo.getGetPoDetailList().get(i).getItemId())));

			}

			editPo.setPoDetailList(editPo.getGetPoDetailList());

			PoHeader res = rest.postForObject(Constants.url + "savePurchaseOrder", editPo, PoHeader.class);

			System.err.println("res  " + res.toString());

		} catch (Exception e) {

			e.printStackTrace();

		}
		return "redirect:/showPoListByStatus";
	}

	@RequestMapping(value = "/showPoListByStatus", method = RequestMethod.GET)
	public ModelAndView showPoListByStatus(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("purchaseOrder/poListDash");

			model.addObject("title", "PO List");
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			List<Plant> plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);
			
			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());
			
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {
			System.err.println("Exce in /Po" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}
	
/*	
	@RequestMapping(value = "/showPoListByStatus/plantId", method = RequestMethod.GET)
	public ModelAndView showPoListByStatus1(HttpServletRequest request, HttpServletResponse response, @PathVariable int plantId) {

		ModelAndView model = null;
		try {

			System.out.println("plant is new   ::"+plantId);
			model = new ModelAndView("purchaseOrder/poListDash");

			model.addObject("title", "PO List");
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			List<Plant> plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);
			
			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			String fromDate = dateFormat.format(firstDate);

			String toDate = dateFormat.format(new Date());
			
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("plantId", plantId);

			Plant pl = rest.postForObject(Constants.url + "getPlantByPlantId", map, Plant.class);
			
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			model.addObject("plantId1", plantId);
			model.addObject("pname", pl);

		} catch (Exception e) {
			System.err.println("Exce in /Po" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}*/

	// getPoListByDateAndStatus

	List<GetPoHeader> getPoList = new ArrayList<>();

	@RequestMapping(value = "/getPOListBetDateAndPlantId", method = RequestMethod.GET)
	public @ResponseBody List<GetPoHeader> getPOListBetDateAndPlantId(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getPOListBetDateAndPlantId");
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

		GetPoHeader[] ordHeadArray = rest.postForObject(Constants.url + "getPoListByDateAndStatus" + "", map,
				GetPoHeader[].class);
		getPoList = new ArrayList<GetPoHeader>(Arrays.asList(ordHeadArray));

		System.out.println("quot list data " + getPoList.toString());

		return getPoList;
	}

	@RequestMapping(value = "/deleteRecordofPO", method = RequestMethod.POST)
	public String deleteRecordofPO(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] poIds = request.getParameterValues("poIds");
			System.out.println("id are" + poIds);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < poIds.length; i++) {
				sb = sb.append(poIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			System.err.println("quotIds" + items.toString());

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("poIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiPO", map, Info.class);
			System.err.println("inside method /deleteMultiPO");
		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofPO @POController  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showPoListByStatus";
	}

}
