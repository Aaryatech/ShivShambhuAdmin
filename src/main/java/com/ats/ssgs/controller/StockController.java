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
import com.ats.ssgs.model.PoHeader;
import com.ats.ssgs.model.master.Company;
import com.ats.ssgs.model.master.DocTermDetail;
import com.ats.ssgs.model.master.DocTermHeader;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.stock.GetStockDetail;
import com.ats.ssgs.stock.StockDetail;
import com.ats.ssgs.stock.StockHeader;

import jdk.internal.joptsimple.util.DateConverter;

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

			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

		} catch (Exception e) {

			System.err.println("" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<Item> itemList;
	List<GetStockDetail> stockdetailList;

	@RequestMapping(value = "/getStockItemByPlantId", method = RequestMethod.GET)
	public @ResponseBody List<GetStockDetail> getStockItemByPlantId(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

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

			System.err.println("Inside insert insertStockDetail method");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int plantId = Integer.parseInt(request.getParameter("plantId"));

			String curDate = dateFormat.format(new Date());

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
			stockHeader.setMonth(1);
			stockHeader.setPlantId(plantId);
			stockHeader.setRemark("-");
			stockHeader.setStatus(0);
			stockHeader.setUserId(1);
			stockHeader.setStartDate(curDate);

			System.err.println("Detail");

			List<StockDetail> sList = new ArrayList<>();

			for (int i = 0; i < stockdetailList.size(); i++) {
				StockDetail dDetail = null;

				if (stockdetailList.get(i).getStockDetId() == 0) {

					dDetail = new StockDetail();

					float opQty = Float.parseFloat(request.getParameter("opQty" + stockdetailList.get(i).getItemId()));
					System.out.println("opQty" + opQty);

					dDetail.setDelStatus(1);
					dDetail.setExInt1(0);
					dDetail.setExVar1("NA");
					dDetail.setExVar2("NA");
					dDetail.setChalanQty(0);
					dDetail.setClosingQty(0);
					dDetail.setDetailDate(curDate);
					dDetail.setExBool1(0);
					dDetail.setExFloat1(1);
					dDetail.setExFloat2(1);
					dDetail.setExInt1(1);
					dDetail.setExInt2(1);
					dDetail.setExVar1("");
					dDetail.setItemId(stockdetailList.get(i).getItemId());
					dDetail.setOpQty(opQty);
					dDetail.setProdQty(0);
					dDetail.setUserId(1);
					dDetail.setExDate1(curDate);
					dDetail.setStockId(stockdetailList.get(i).getStockId());
					// dDetail.setStockDetId(stockdetailList.get(i).getStockDetId());
					sList.add(dDetail);
					System.err.println("dDetail" + dDetail.toString());
					System.out.println(sList.toString());
				} else {

					float opQty = Float.parseFloat(request.getParameter("opQty" + stockdetailList.get(i).getItemId()));
					System.out.println("opQty" + opQty);
					dDetail.setOpQty(opQty);
					dDetail.setClosingQty(stockdetailList.get(i).getClosingQty());
					dDetail.setDelStatus(stockdetailList.get(i).getDelStatus());
					dDetail.setDetailDate(stockdetailList.get(i).getDetailDate());
					dDetail.setExDate1(stockdetailList.get(i).getExDate1());
					dDetail.setChalanQty(stockdetailList.get(i).getChalanQty());
					dDetail.setItemId(stockdetailList.get(i).getItemId());

					sList.add(dDetail);

				}

			}

			stockHeader.setStockDetailList(sList);

			StockHeader docInsertRes = rest.postForObject(Constants.url + "saveStockHeaderDetail", stockHeader,
					StockHeader.class);

			if (docInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}
			System.out.println("docInsertRes" + docInsertRes.toString());

		} catch (Exception e) {

			System.err.println("Exce In insertStockDetail method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showStockHeader";

	}

}
