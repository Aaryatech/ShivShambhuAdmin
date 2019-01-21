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
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.LoginResUser;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.stock.GetStockDetail;
import com.ats.ssgs.stock.StockDetail;
import com.ats.ssgs.stock.StockHeader;

@Controller
public class StockController {
	RestTemplate rest = new RestTemplate();
	List<Plant> plantList;
	List<Uom> uomList;
	int isError = 0;

	@RequestMapping(value = "/showStockHeader", method = RequestMethod.GET)
	public ModelAndView showStockHeader(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("stock/addStock");
			model.addObject("title", "Add Stock");
			isError = 0;

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

		} catch (Exception e) {

			System.err.println("" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/showStockEndProcess", method = RequestMethod.GET)
	public ModelAndView showStockEndProcess(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("stock/endprocess");
			model.addObject("title", "Production Day End");
			isError = 0;

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

			Calendar cal = Calendar.getInstance();

			Calendar cal1 = Calendar.getInstance();
			cal.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), 1);

			String firstDate1 = dd.format(cal.getTimeInMillis());

			String curDate = dd.format(new Date());
			model.addObject("fromDate", firstDate1);
			model.addObject("toDate", curDate);
		} catch (Exception e) {

			System.err.println("" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<Item> itemList;
	List<GetStockDetail> stockdetailList;

	// Ajax call
	@RequestMapping(value = "/getStockHeaderByPlantId", method = RequestMethod.GET)
	public @ResponseBody StockHeader getStockHeaderByPlantId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));

		map.add("plantId", plantId);

		StockHeader stockHeader = rest.postForObject(Constants.url + "getStockHeaderByPlantId", map, StockHeader.class);

		return stockHeader;

	}

	// Ajax call
	@RequestMapping(value = "/getStockItemByPlantIdAndCurDate", method = RequestMethod.GET)
	public @ResponseBody List<GetStockDetail> getStockItemByPlantIdAndCurDate(HttpServletRequest request,
			HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));

		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");

		String curDate = dd.format(new Date());

		map.add("plantId", plantId);
		map.add("currDate", curDate);

		GetStockDetail[] chArray = rest.postForObject(Constants.url + "getStockDetailByPlantIdAndCurDate", map,
				GetStockDetail[].class);
		stockdetailList = new ArrayList<GetStockDetail>(Arrays.asList(chArray));

		return stockdetailList;

	}

	@RequestMapping(value = "/getStockBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetStockDetail> getStockBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantId", plantId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetStockDetail[] chArray = rest.postForObject(Constants.url + "getStockDetailByPlantIdAndBetDate", map,
				GetStockDetail[].class);
		stockdetailList = new ArrayList<GetStockDetail>(Arrays.asList(chArray));

		System.out.println("stockdetailList" + stockdetailList.toString());

		return stockdetailList;

	}

	@RequestMapping(value = "/getStockItemByPlantId", method = RequestMethod.GET)
	public @ResponseBody List<GetStockDetail> getStockItemByPlantId(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int plantId = Integer.parseInt(request.getParameter("plantId"));
			System.err.println("getStockItemByPlantId " + plantId);

			map.add("plantId", plantId);

			Item[] itemListArray = rest.postForObject(Constants.url + "getItemsByPlantId", map, Item[].class);

			itemList = new ArrayList<Item>(Arrays.asList(itemListArray));

			System.out.println("itemList" + itemList.toString());

			GetStockDetail[] chArray = rest.postForObject(Constants.url + "getStockDetailByPlantId", map,
					GetStockDetail[].class);
			stockdetailList = new ArrayList<GetStockDetail>(Arrays.asList(chArray));

			// System.out.println("stockdetailList" + stockdetailList.toString());

			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));
			if (stockdetailList.isEmpty()) {
				stockdetailList = new ArrayList<GetStockDetail>();

				for (int i = 0; i < itemList.size(); i++) {

					GetStockDetail sDetail = new GetStockDetail();
					System.out.println("in else if");
					sDetail.setItemCode(itemList.get(i).getItemCode());
					sDetail.setItemName(itemList.get(i).getItemName());
					sDetail.setOpQty(0);
					sDetail.setDelStatus(1);
					sDetail.setChalanQty(0);
					sDetail.setClosingQty(0);
					sDetail.setDetailDate(curDate);
					sDetail.setExDate1(curDate);
					sDetail.setItemId(itemList.get(i).getItemId());

					System.out.println("sdetail" + sDetail.toString());

					for (int j = 0; j < uomList.size(); j++) {
						if (uomList.get(j).getUomId() == itemList.get(i).getUomId())

						{
							sDetail.setUomName(uomList.get(j).getUomName());
						}
					}

					stockdetailList.add(sDetail);

				}

			} else {

				for (int i = 0; i < itemList.size(); i++) {
					int flag = 0;
					for (int k = 0; k < stockdetailList.size(); k++) {
						if (stockdetailList.get(k).getItemId() == itemList.get(i).getItemId()) {
							flag = 1;
						}
					}
					if (flag == 0) {
						GetStockDetail sDetail = new GetStockDetail();
						sDetail.setItemCode(itemList.get(i).getItemCode());
						sDetail.setItemId(itemList.get(i).getItemId());
						sDetail.setOpQty(0);
						sDetail.setDelStatus(1);
						sDetail.setChalanQty(0);
						sDetail.setClosingQty(0);
						sDetail.setDetailDate(curDate);
						sDetail.setExBool1(0);
						sDetail.setExDate1(curDate);
						sDetail.setExFloat1(0);
						sDetail.setExFloat2(0);
						sDetail.setExInt1(0);
						sDetail.setExInt2(0);

						sDetail.setExDate1(curDate);
						sDetail.setItemName(itemList.get(i).getItemName());

						System.out.println("sdetail" + sDetail.toString());

						for (int j = 0; j < uomList.size(); j++) {
							if (uomList.get(j).getUomId() == itemList.get(i).getUomId())

							{
								sDetail.setUomName(uomList.get(j).getUomName());
							}
						}

						stockdetailList.add(sDetail);

					}

					System.out.println("*****************" + stockdetailList.toString());

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return stockdetailList;
	}

	// insertStockDetail
	@RequestMapping(value = "/insertStockDetail", method = RequestMethod.POST)
	public String insertStockDetail(HttpServletRequest request, HttpServletResponse response) {

		try {

			HttpSession session = request.getSession();
			LoginResUser login = (LoginResUser) session.getAttribute("UserDetail");

			int flag = 0;
			for (int i = 0; i < stockdetailList.size(); i++) {
				if (stockdetailList.get(i).getStockDetId() > 0) {
					flag = 1;
				}
				float opQty = Float.parseFloat(request.getParameter("opQty" + stockdetailList.get(i).getItemId()));
				System.out.println("opQty" + opQty);
				stockdetailList.get(i).setOpQty(opQty);
			}
			List<GetStockDetail> detailList = stockdetailList;
			if (flag == 1) {
				List<StockDetail> docInsertRes = rest.postForObject(Constants.url + "saveStockDetailList", detailList,
						List.class);
			} else {

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				int plantId = Integer.parseInt(request.getParameter("plantId"));

				String curDate = dateFormat.format(new Date());
				Calendar cal = Calendar.getInstance();
				int month = cal.get(Calendar.MONTH);
				System.out.println("month" + month + 1);

				StockHeader stockHeader = new StockHeader();
				stockHeader.setClosingDate(curDate);
				stockHeader.setDelStatus(1);
				stockHeader.setExBool1(1);
				stockHeader.setExDate1(curDate);
				stockHeader.setExFloat1(0);
				stockHeader.setExFloat2(0);
				stockHeader.setExInt1(0);
				stockHeader.setExInt2(0);
				stockHeader.setExVar1("");
				stockHeader.setExVar2("");
				stockHeader.setMonth(month + 1);
				stockHeader.setPlantId(plantId);
				stockHeader.setRemark("-");
				stockHeader.setStatus(0);
				stockHeader.setUserId(login.getUser().getUserId());
				stockHeader.setStartDate(curDate);
				List<StockDetail> sList = new ArrayList<>();

				for (int i = 0; i < stockdetailList.size(); i++) {

					StockDetail dDetail = new StockDetail();
					dDetail.setStockId(stockdetailList.get(i).getStockId());
					dDetail.setChalanQty(stockdetailList.get(i).getChalanQty());
					dDetail.setClosingQty(stockdetailList.get(i).getClosingQty());
					dDetail.setDelStatus(stockdetailList.get(i).getDelStatus());
					dDetail.setDetailDate(stockdetailList.get(i).getDetailDate());
					dDetail.setExBool1(stockdetailList.get(i).getExBool1());
					dDetail.setExDate1(stockdetailList.get(i).getExDate1());
					dDetail.setExFloat1(stockdetailList.get(i).getExFloat1());
					dDetail.setExFloat2(stockdetailList.get(i).getExFloat2());
					dDetail.setExInt1(stockdetailList.get(i).getExInt1());
					dDetail.setExInt2(stockdetailList.get(i).getExInt2());
					dDetail.setExVar1(stockdetailList.get(i).getExVar1());
					dDetail.setExVar2(stockdetailList.get(i).getExVar2());
					dDetail.setItemId(stockdetailList.get(i).getItemId());
					dDetail.setOpQty(stockdetailList.get(i).getOpQty());
					dDetail.setProdQty(stockdetailList.get(i).getProdQty());
					dDetail.setStockDetId(stockdetailList.get(i).getStockDetId());
					dDetail.setUserId(stockdetailList.get(i).getUserId());
					sList.add(dDetail);
				}
				stockHeader.setStockDetailList(sList);

				StockHeader docInsertRes = rest.postForObject(Constants.url + "saveStockHeaderDetail", stockHeader,
						StockHeader.class);

			}

		} catch (Exception e) {

			System.err.println("Exce In insertStockDetail method  " + e.getMessage());
			e.printStackTrace();

		}

		/*
		 * try {
		 * 
		 * System.err.println("Inside insert insertStockDetail method"+stockdetailList.
		 * toString());
		 * 
		 * DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); int plantId =
		 * Integer.parseInt(request.getParameter("plantId"));
		 * 
		 * String curDate = dateFormat.format(new Date());
		 * 
		 * StockHeader stockHeader = new StockHeader();
		 * stockHeader.setClosingDate(curDate); stockHeader.setDelStatus(1);
		 * stockHeader.setExBool1(1); stockHeader.setExDate1(curDate);
		 * stockHeader.setExFloat1(0); stockHeader.setExFloat2(0);
		 * stockHeader.setExInt1(0); stockHeader.setExInt2(0);
		 * stockHeader.setExVar1(""); stockHeader.setExVar2("");
		 * stockHeader.setMonth(1); stockHeader.setPlantId(plantId);
		 * stockHeader.setRemark("-"); stockHeader.setStatus(0);
		 * stockHeader.setUserId(1); stockHeader.setStartDate(curDate);
		 * 
		 * System.err.println("Detail");
		 * 
		 * List<StockDetail> sList = new ArrayList<>();
		 * 
		 * for (int i = 0; i < stockdetailList.size(); i++) { StockDetail dDetail =
		 * null;
		 * 
		 * if (stockdetailList.get(i).getStockDetId() == 0) {
		 * 
		 * dDetail = new StockDetail(); System.out.println("opQty" +
		 * stockdetailList.get(i).getItemId()); float opQty =
		 * Float.parseFloat(request.getParameter("opQty" +
		 * stockdetailList.get(i).getItemId())); System.out.println("opQty" + opQty);
		 * 
		 * dDetail.setDelStatus(1); dDetail.setExInt1(0); dDetail.setExVar1("NA");
		 * dDetail.setExVar2("NA"); dDetail.setChalanQty(0); dDetail.setClosingQty(0);
		 * dDetail.setDetailDate(curDate); dDetail.setExBool1(0);
		 * dDetail.setExFloat1(1); dDetail.setExFloat2(1); dDetail.setExInt1(1);
		 * dDetail.setExInt2(1); dDetail.setExVar1("");
		 * dDetail.setItemId(stockdetailList.get(i).getItemId());
		 * dDetail.setOpQty(opQty); dDetail.setProdQty(0); dDetail.setUserId(1);
		 * dDetail.setExDate1(curDate);
		 * dDetail.setStockId(stockdetailList.get(i).getStockId()); //
		 * dDetail.setStockDetId(stockdetailList.get(i).getStockDetId());
		 * sList.add(dDetail); System.err.println("dDetail" + dDetail.toString());
		 * System.out.println(sList.toString()); } else {
		 * 
		 * float opQty = Float.parseFloat(request.getParameter("opQty" +
		 * stockdetailList.get(i).getItemId())); System.out.println("opQty" + opQty);
		 * dDetail.setOpQty(opQty);
		 * dDetail.setClosingQty(stockdetailList.get(i).getClosingQty());
		 * dDetail.setDelStatus(stockdetailList.get(i).getDelStatus());
		 * dDetail.setDetailDate(stockdetailList.get(i).getDetailDate());
		 * dDetail.setExDate1(stockdetailList.get(i).getExDate1());
		 * dDetail.setChalanQty(stockdetailList.get(i).getChalanQty());
		 * dDetail.setItemId(stockdetailList.get(i).getItemId());
		 * 
		 * sList.add(dDetail);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * stockHeader.setStockDetailList(sList);
		 * 
		 * StockHeader docInsertRes = rest.postForObject(Constants.url +
		 * "saveStockHeaderDetail", stockHeader, StockHeader.class);
		 * 
		 * if (docInsertRes != null) { isError = 2; } else { isError = 1; }
		 * System.out.println("docInsertRes" + docInsertRes.toString());
		 * 
		 * } catch (Exception e) {
		 * 
		 * System.err.println("Exce In insertStockDetail method  " + e.getMessage());
		 * e.printStackTrace();
		 * 
		 * }
		 */

		return "redirect:/showStockHeader";

	}

	// updateStockDetail
	@RequestMapping(value = "/updateStockDetail", method = RequestMethod.POST)
	public String updateStockDetail(HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("stockId", stockdetailList.get(0).getStockId());

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());

			StockHeader stockHeader = rest.postForObject(Constants.url + "getStockHeaderByStockId", map,
					StockHeader.class);
			stockHeader.setStatus(1);
			stockHeader.setClosingDate(curDate);

			List<GetStockDetail> detailList = stockdetailList;

			List<StockDetail> docInsertResDetail = rest.postForObject(Constants.url + "saveStockDetailList", detailList,
					List.class);

			stockHeader.setStockDetailList(docInsertResDetail);

			StockHeader docInsertRes = rest.postForObject(Constants.url + "saveStockHeaderDetail", stockHeader,
					StockHeader.class);

			System.out.println("docInsertRes" + docInsertRes.toString());

		} catch (Exception e) {

			System.err.println("Exce In insertStockDetail method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showStockEndProcess";

	}

}