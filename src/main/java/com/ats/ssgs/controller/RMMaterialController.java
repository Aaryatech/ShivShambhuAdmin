package com.ats.ssgs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.mat.ItemCategory;
import com.ats.ssgs.model.mat.RawMatItem;

@Controller
public class RMMaterialController {
	
	List<RawMatItem> rawItemList;
	List<ItemCategory> catList;
	RestTemplate rest = new RestTemplate();
	int isError = 0;
	@RequestMapping(value = "/addItemDetail/{itemId}/{itemName}/{itemCode}", method = RequestMethod.GET)
	public ModelAndView showAddVendor(HttpServletRequest request, HttpServletResponse response,@PathVariable int itemId,
			@PathVariable String itemName,@PathVariable String itemCode) {

		ModelAndView model = null;
		try {
			System.out.println("item deti; are"+itemId+itemName+itemCode);

			model = new ModelAndView("rawmaterial/additemdetail");
			
			model.addObject("isError", isError);
			model.addObject("itemId", itemId);
			model.addObject("itemName", itemName);
			model.addObject("itemCode", itemCode);
			
			ItemCategory[] catArray = rest.getForObject(Constants.url + "getAllItemCategoryList", ItemCategory[].class);
			catList = new ArrayList<ItemCategory>(Arrays.asList(catArray));

			model.addObject("catList", catList);

			

			
			isError = 0;

			model.addObject("title", "Add Item Detail");

		} catch (Exception e) {

			System.err.println("exception In showAddDetail at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	
	/*@RequestMapping(value = "/getRawItemByCatId1", method = RequestMethod.GET)
	public @ResponseBody List<RawMatItem> getRawItemByCatId1(HttpServletRequest request, HttpServletResponse response) {

		
		System.out.println("inside cat");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int catId = Integer.parseInt(request.getParameter("catId"));

		map.add("catId", catId);

		RawMatItem[] itemArray = rest.postForObject(Constants.url + "getRawItemListByCatId", map, RawMatItem[].class);
		rawItemList = new ArrayList<RawMatItem>(Arrays.asList(itemArray));

		return rawItemList;

	}
	
	*/

}
