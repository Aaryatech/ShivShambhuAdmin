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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.PoHeader;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.order.GetPoForOrder;
import com.ats.ssgs.model.order.OrderDetail;
import com.ats.ssgs.model.order.OrderHeader;
import com.ats.ssgs.model.order.TempOrdDetail;
import com.ats.ssgs.model.order.TempOrdHeader;
import com.ats.ssgs.model.quot.QuotHeader;

@Controller
@Scope("session")
public class OrderController {

	List<Plant> plantList;

	List<Cust> custList;

	RestTemplate rest = new RestTemplate();

	List<PoHeader> poHeadList;

	List<GetPoForOrder> poDetailForOrdList;

	int isError=0;

	
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

			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);

			model.addObject("doc", doc);
			model.addObject("isError", isError);
			isError=0;

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
		map.add("statusList", "1,2");

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
		if (tempOrdDetail.size() == 0) {
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
	public String  insertOrder(HttpServletRequest request, HttpServletResponse response) {

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
			ordHeader.setExVar1(NA);
			ordHeader.setExVar2(NA);
			ordHeader.setExVar3(NA);
			ordHeader.setOrderDate(DateConvertor.convertToYMD(ordDate));
			ordHeader.setOrderId(0);
			ordHeader.setPlantId(plantId);
			ordHeader.setPoId(poId);
			ordHeader.setProdDate(DateConvertor.convertToYMD(delDate));
			ordHeader.setProjId(projId);

			float headerTotal = 0;

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
				ordDetailList.add(orDetail);

				headerTotal = orDetail.getTotal() + headerTotal;

			}
			ordHeader.setOrderValue(headerTotal);
			ordHeader.setTotal(headerTotal);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 3);

			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			ordHeader.setOrderNo(doc.getDocPrefix() + "" + doc.getSrNo());

			if (poDetailForOrdList.get(0).getTaxAmt() == 0) {

				ordHeader.setIsTaxIncluding(0);// tax not included

			} else {

				ordHeader.setIsTaxIncluding(1);//yes tax included

			}
			
			ordHeader.setOrderDetailList(ordDetailList);
			
			OrderHeader insertOrdHeadRes=rest.postForObject(Constants.url + "saveOrder", ordHeader,
					OrderHeader.class);
			
			if(insertOrdHeadRes!=null) {
				
				isError=2;
			}
			else {
				isError=1;
			}
			System.err.println("insertOrdHeadRes " +insertOrdHeadRes.toString());
			
		} catch (Exception e) {
			isError=1;
			System.err.println("exception In insertOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}
		

		return "redirect:/showAddOrder";
	}

}
