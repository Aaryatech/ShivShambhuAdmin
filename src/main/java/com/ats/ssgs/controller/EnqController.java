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
import com.ats.ssgs.model.enq.TempEnqItem;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Uom;

@Controller
@Scope("session")
public class EnqController {

	List<Plant> plantList;

	List<Cust> custList;

	List<Uom> uomList;

	List<Item> itemList;
	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/showAddEnquiry", method = RequestMethod.GET)
	public ModelAndView showAddItem(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			enqItemList=new ArrayList<>();
			model = new ModelAndView("enq/addenquiry");

			model.addObject("title", "Add Enquiry");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

			model.addObject("uomList", uomList);

		} catch (Exception e) {

			System.err.println("Exce in showing add Enq page " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	// Ajax call
	@RequestMapping(value = "/getItemsByPlantId", method = RequestMethod.GET)
	public @ResponseBody List<Item> getItemsByPlantId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));

		map.add("plantId", plantId);

		Item[] itemArray = rest.postForObject(Constants.url + "getItemsByPlantId", map, Item[].class);
		itemList = new ArrayList<Item>(Arrays.asList(itemArray));

		System.err.println("Ajax Item List " + itemList.toString());

		return itemList;

	}

	@RequestMapping(value = "/getItemByItemId", method = RequestMethod.GET)
	public @ResponseBody Item getItemsByItemId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int itemId = Integer.parseInt(request.getParameter("itemId"));

		map.add("itemId", itemId);

		Item item = rest.postForObject(Constants.url + "getItemByItemId", map, Item.class);

		System.err.println("Ajax Item  " + item.toString());

		return item;

	}

	// Ajax call
	@RequestMapping(value = "/getCustByPlantId", method = RequestMethod.GET)
	public @ResponseBody List<Cust> getCustByPlantId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));

		map.add("plantId", plantId);

		Cust[] custArray = rest.postForObject(Constants.url + "getCustListByPlant", map, Cust[].class);
		custList = new ArrayList<Cust>(Arrays.asList(custArray));

		System.err.println("Ajax custList List " + custList.toString());

		return custList;

	}
	// addEnqItem Ajax

	List<TempEnqItem> enqItemList = new ArrayList<TempEnqItem>();

	@RequestMapping(value = "/addEnqItem", method = RequestMethod.GET)
	public @ResponseBody List<TempEnqItem> addEnqItem(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int uomId = Integer.parseInt(request.getParameter("uomId"));

		float qty = Float.parseFloat(request.getParameter("qty"));

		String itemName = request.getParameter("itemName");
		String uomName = request.getParameter("uomName");
		String itemRemark = request.getParameter("itemRemark");
		
		TempEnqItem enqItem=new TempEnqItem();
		
		enqItem.setItemId(itemId);
		enqItem.setItemName(itemName);
		enqItem.setUomId(uomId);
		enqItem.setUomName(uomName);
		enqItem.setEnqQty(qty);
		enqItem.setItemEnqRemark(itemRemark);
		
		enqItemList.add(enqItem);
		
		System.err.println("Ajax enqItem List size  " + enqItemList.size());

		System.err.println("Ajax enqItem List " + enqItemList.toString());

		return enqItemList;

	}

}
