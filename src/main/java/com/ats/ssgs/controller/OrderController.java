package com.ats.ssgs.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ats.ssgs.model.order.TempOrdDetail;
import com.ats.ssgs.model.order.TempOrdHeader;

@Controller
@Scope("session")
public class OrderController {
	
	List<Plant> plantList;
	
	List<Cust> custList;

	RestTemplate rest = new RestTemplate();

	List<PoHeader> poHeadList;

	List<GetPoForOrder> poDetailForOrdList;

	
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

		PoHeader[] poHeadArray = rest.postForObject(Constants.url + "getPoHeaderByCustIdAndStatus", map, PoHeader[].class);
		poHeadList = new ArrayList<PoHeader>(Arrays.asList(poHeadArray));
		
		for(int i=0;i<poHeadList.size();i++) {
			
			poHeadList.get(i).setPoDate(DateConvertor.convertToDMY(poHeadList.get(i).getPoDate()));
		}

		System.err.println("Ajax PoHeader  List by cust Id" + poHeadList.toString());

		return poHeadList;

	}

	@RequestMapping(value = "/getPoDetailForOrderByPoId", method = RequestMethod.GET)
	public @ResponseBody List<GetPoForOrder> getPoDetailForOrderByPoId(HttpServletRequest request, HttpServletResponse response) {

		ordHeader=null;
		tempOrdDetail=new  ArrayList<>();
		
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int poId = Integer.parseInt(request.getParameter("poId"));

		map.add("poId", poId);

		GetPoForOrder[] poHeadArray = rest.postForObject(Constants.url + "getPoDetailForOrderByPoId", map, GetPoForOrder[].class);
		poDetailForOrdList = new ArrayList<GetPoForOrder>(Arrays.asList(poHeadArray));
		
	
		System.err.println("Ajax poDetailForOrdList  List by po Id" + poDetailForOrdList.toString());

		return poDetailForOrdList;

	}
	
	TempOrdHeader ordHeader;
	
	List<TempOrdDetail> tempOrdDetail;
	@RequestMapping(value = "/getTempOrderHeader", method = RequestMethod.GET)
	public @ResponseBody TempOrdHeader getTempOrderHeader(HttpServletRequest request, HttpServletResponse response) {

		
		System.err.println(" in getTempOrderHeader");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int poDetailId = Integer.parseInt(request.getParameter("poDetailId"));
		
		float qty=Float.parseFloat(request.getParameter("qty"));
		float poRemainingQty=Float.parseFloat(request.getParameter("poRemainingQty"));
		float itemTotal=Float.parseFloat(request.getParameter("itemTotal"));

		
		if(ordHeader==null) {
			System.err.println("Ord Head =null ");
			ordHeader=new TempOrdHeader();
			
			
			ordHeader.setOrderTotal(itemTotal);
			ordHeader.setOtherCostAfterTax(0);
			ordHeader.setTaxableValue(itemTotal);
			ordHeader.setTaxValue(0);
			
			TempOrdDetail detail=new TempOrdDetail();
			tempOrdDetail=new ArrayList<>();
			detail.setItemId(itemId);
			detail.setOrderQty(qty);
			detail.setPoDetailId(poDetailId);
			detail.setTotal(itemTotal);
			
			tempOrdDetail.add(detail);
			ordHeader.setOrdDetailList(tempOrdDetail);
			return ordHeader;
		}else {
		System.err.println("Else ord head not  null ");
			for(int i=0;i<ordHeader.getOrdDetailList().size();i++) {
			System.err.println("inside for loop");
			if(ordHeader.getOrdDetailList().get(i).getItemId()==itemId) {
				System.err.println("item id matched ");
				ordHeader.getOrdDetailList().get(i).setOrderQty(qty);
				ordHeader.getOrdDetailList().get(i).setTotal(itemTotal);
				
				break;
				
			}else {
				System.err.println("Else new Item");
				TempOrdDetail detail=new TempOrdDetail();
				
				detail.setItemId(itemId);
				detail.setOrderQty(qty);
				detail.setPoDetailId(poDetailId);
				detail.setTotal(itemTotal);
				
				ordHeader.setOrderTotal(ordHeader.getOrderTotal()+itemTotal);
				ordHeader.setOtherCostAfterTax(0);
				ordHeader.setTaxableValue(ordHeader.getTaxableValue()+itemTotal);
				ordHeader.setTaxValue(0);
				
				tempOrdDetail.add(detail);
				ordHeader.setOrdDetailList(tempOrdDetail);
				
			}
			System.err.println("Ajax ordHeader &&&&& " + ordHeader.toString());

		}
			return ordHeader;

		}


		
	}





}
