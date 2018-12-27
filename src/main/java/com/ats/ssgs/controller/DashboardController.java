package com.ats.ssgs.controller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.i18n.LocaleContextHolder;
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
import com.ats.ssgs.common.ExportToExcel;
import com.ats.ssgs.model.DashSaleCount;
import com.ats.ssgs.model.GetBillHeader;
import com.ats.ssgs.model.GetBillReport;
import com.ats.ssgs.model.GetPoHeader;
import com.ats.ssgs.model.GetQuotHeader;
import com.ats.ssgs.model.enq.GetEnqHeader;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.order.GetOrder;
import com.ats.ssgs.model.quot.GetQuotHeads;

@Controller
public class DashboardController {
	RestTemplate rest = new RestTemplate();
	List<Plant> plantList;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("home");
		try {

			// System.out.println("hiiiiiiii");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

			Calendar cal = Calendar.getInstance();

			Calendar cal1 = Calendar.getInstance();
			cal.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), 1);

			String firstDate = sdf.format(cal.getTimeInMillis());
			String firstDate1 = dd.format(cal.getTimeInMillis());
			cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
			String endDate = sdf.format(cal.getTimeInMillis());
			String endDate1 = dd.format(cal.getTimeInMillis());

			System.out.println("sd " + firstDate);
			System.out.println("ed " + endDate);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("fromDate", firstDate);
			map.add("toDate", endDate);
			map.add("plantId", 0);

			DashSaleCount dashBoard = rest.postForObject(Constants.url + "/getDashboardCountBetDate", map,
					DashSaleCount.class);

			model.addObject("dashBoard", dashBoard);

			model.addObject("fromDate", firstDate1);
			model.addObject("toDate", endDate1);

			System.out.println("dashBoard" + dashBoard.toString());

		} catch (Exception e) {

			System.err.println("Exce ing etHubDashBoard  " + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/getDashboardCount", method = RequestMethod.GET)
	public @ResponseBody DashSaleCount getDashboardCount(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String plantId = request.getParameter("plantId");

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantId", plantId);

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		DashSaleCount dashBoard = rest.postForObject(Constants.url + "getDashboardCountBetDate", map,
				DashSaleCount.class);

		return dashBoard;
	}

	/*
	 * @RequestMapping(value = "/showDashEnqBetDate", method = RequestMethod.GET)
	 * public ModelAndView showDashEnqBetDate(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * ModelAndView model = null; try {
	 * 
	 * model = new ModelAndView("dashboard/enqbetdate");
	 * 
	 * model.addObject("title", "Enquiry List Between Date");
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
	 * Object>();
	 * 
	 * map.add("statusList", "0,1"); Plant[] plantArray =
	 * rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
	 * plantList = new ArrayList<Plant>(Arrays.asList(plantArray));
	 * 
	 * System.out.println("plant is" + plantList);
	 * 
	 * model.addObject("plantList", plantList);
	 * 
	 * } catch (Exception e) { System.err.println("Exce in /showQ" +
	 * e.getMessage()); e.printStackTrace(); } return model;
	 * 
	 * }
	 */

	List<GetEnqHeader> getEnqList = new ArrayList<>();
	List<GetQuotHeader> getQuotList = new ArrayList<>();
	List<GetPoHeader> getPoList = new ArrayList<>();

	@RequestMapping(value = "/showDashboardEnqList/{fromDate}/{toDate}/{plantId}", method = RequestMethod.GET)
	public ModelAndView showDashboardEnqList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fromDate, @PathVariable String toDate, @PathVariable int plantId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("enq/enqList");

			model.addObject("title", "Enquiry List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

			model.addObject("plantId1", plantId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("custId", 0);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			GetEnqHeader[] ordHeadArray = rest.postForObject(Constants.url + "getEnqListByPlantIdAndCustId", map,
					GetEnqHeader[].class);
			getEnqList = new ArrayList<GetEnqHeader>(Arrays.asList(ordHeadArray));

			model.addObject("getEnqList", getEnqList);

		} catch (Exception e) {
			System.err.println("Exce in /showQ" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showDashboardQuotList/{fromDate}/{toDate}/{plantId}", method = RequestMethod.GET)
	public ModelAndView showDashboardQuotList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fromDate, @PathVariable String toDate, @PathVariable int plantId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotListNew");

			model.addObject("title", "Quotation List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

			model.addObject("plantId1", plantId);
			model.addObject("status", 0);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("custId", 0);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("statusList", 0);

			GetQuotHeader[] ordHeadArray = rest.postForObject(Constants.url + "getQuotListByPlantIdAndCustIdAndStatus",
					map, GetQuotHeader[].class);
			getQuotList = new ArrayList<GetQuotHeader>(Arrays.asList(ordHeadArray));

			System.out.println("quot list data " + getQuotList.toString());

			model.addObject("getQuotList", getQuotList);

		} catch (Exception e) {
			System.err.println("Exce in /showQ" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showDashboardQuotList1/{fromDate}/{toDate}/{plantId}", method = RequestMethod.GET)
	public ModelAndView showDashboardQuotList1(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fromDate, @PathVariable String toDate, @PathVariable int plantId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotListNew");

			model.addObject("title", "Quotation List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

			model.addObject("plantId1", plantId);

			model.addObject("status", 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("custId", 0);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("statusList", 1);
			GetQuotHeader[] ordHeadArray = rest.postForObject(Constants.url + "getQuotListByPlantIdAndCustIdAndStatus",
					map, GetQuotHeader[].class);
			getQuotList = new ArrayList<GetQuotHeader>(Arrays.asList(ordHeadArray));

			System.out.println("quot list data " + getQuotList.toString());

			model.addObject("getQuotList", getQuotList);

		} catch (Exception e) {
			System.err.println("Exce in /showQ" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showDashboardPOList/{fromDate}/{toDate}/{plantId}", method = RequestMethod.GET)
	public ModelAndView showDashboardPOList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fromDate, @PathVariable String toDate, @PathVariable int plantId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("purchaseOrder/poListDash");

			model.addObject("title", "Purchase Order List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

			model.addObject("plantId1", plantId);
			model.addObject("status", 0);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("custId", 0);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("statusList", 0);

			GetPoHeader[] ordHeadArray = rest.postForObject(Constants.url + "getPoListByDateAndStatus" + "", map,
					GetPoHeader[].class);
			getPoList = new ArrayList<GetPoHeader>(Arrays.asList(ordHeadArray));

			System.out.println("getPoList list data " + getPoList.toString());

			model.addObject("getPoList", getPoList);

		} catch (Exception e) {
			System.err.println("Exce in /showQ" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showDashboardPOList1/{fromDate}/{toDate}/{plantId}", method = RequestMethod.GET)
	public ModelAndView showDashboardPOList1(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fromDate, @PathVariable String toDate, @PathVariable int plantId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("purchaseOrder/poListDash");

			model.addObject("title", "Purchase Order List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

			model.addObject("plantId1", plantId);
			model.addObject("status", 0);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("custId", 0);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("statusList", 1);

			GetPoHeader[] ordHeadArray = rest.postForObject(Constants.url + "getPoListByDateAndStatus" + "", map,
					GetPoHeader[].class);
			getPoList = new ArrayList<GetPoHeader>(Arrays.asList(ordHeadArray));

			System.out.println("getPoList list data " + getPoList.toString());

			model.addObject("getPoList", getPoList);

		} catch (Exception e) {
			System.err.println("Exce in /showQ" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	List<GetOrder> getOrdList = new ArrayList<>();

	@RequestMapping(value = "/showDashboardOrderList/{fromDate}/{toDate}/{plantId}", method = RequestMethod.GET)
	public ModelAndView showDashboardOrderList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fromDate, @PathVariable String toDate, @PathVariable int plantId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("order/orderlist");

			model.addObject("title", "Order List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

			model.addObject("plantId1", plantId);
			model.addObject("status", 0);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("custId", 0);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("statusList", 0);

			GetOrder[] ordHeadArray = rest.postForObject(Constants.url + "getOrderListBetDateAndStatus", map,
					GetOrder[].class);
			getOrdList = new ArrayList<GetOrder>(Arrays.asList(ordHeadArray));

			System.out.println("getOrdList list data " + getOrdList.toString());

			model.addObject("getOrdList", getOrdList);

		} catch (Exception e) {
			System.err.println("Exce in /showQ" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	List<GetBillHeader> getBillList = new ArrayList<>();

	@RequestMapping(value = "/showDashboardBillList/{fromDate}/{toDate}/{plantId}", method = RequestMethod.GET)
	public ModelAndView showDashboardBillList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fromDate, @PathVariable String toDate, @PathVariable int plantId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("bill/billList");

			model.addObject("title", "Bill List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

			model.addObject("plantId1", plantId);
			model.addObject("status", 0);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("custId", 0);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			GetBillHeader[] ordHeadArray = rest.postForObject(Constants.url + "getBillHeadersByDateAndCustAndPlant",
					map, GetBillHeader[].class);
			getBillList = new ArrayList<GetBillHeader>(Arrays.asList(ordHeadArray));
			System.out.println("getBillList" + getBillList.toString());

			model.addObject("getBillList", getBillList);

		} catch (Exception e) {
			System.err.println("Exce in /showQ" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

}
