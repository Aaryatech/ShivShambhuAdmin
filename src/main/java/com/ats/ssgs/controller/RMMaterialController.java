package com.ats.ssgs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.ats.ssgs.model.AddItemDetail;
import com.ats.ssgs.model.GetAddItemDetail;
import com.ats.ssgs.model.GetExistingItemDetail;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.LoginResUser;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.model.mat.GetMatIssueDetail;
import com.ats.ssgs.model.mat.ItemCategory;
import com.ats.ssgs.model.mat.MatIssueDetail;
import com.ats.ssgs.model.mat.MatIssueHeader;
import com.ats.ssgs.model.mat.MatIssueVehDetail;
import com.ats.ssgs.model.mat.RawMatItem;
import com.ats.ssgs.model.mat.TempMatIssueDetail;

@Controller
public class RMMaterialController {
	
	List<GetAddItemDetail> tempList = new ArrayList<GetAddItemDetail>();
	
	
	List<RawMatItem> rawItemList;
	List<ItemCategory> catList;
	List<Uom> uomList;
	
	RestTemplate rest = new RestTemplate();
	int isError = 0;
	@RequestMapping(value = "/addItemDetail/{itemId}/{itemName}/{itemCode}", method = RequestMethod.GET)
	public ModelAndView showAddVendor(HttpServletRequest request, HttpServletResponse response,@PathVariable int itemId,
			@PathVariable String itemName,@PathVariable String itemCode) {

		ModelAndView model = null;
		try {
			/* tempList = new ArrayList<GetAddItemDetail>();*/
			System.out.println("item id are"+itemId);

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
	
	@RequestMapping(value = "/addTempItemDetail", method = RequestMethod.GET)
	public @ResponseBody List<GetAddItemDetail> addTempItemDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String catName=request.getParameter("categoryName") ;

		try {
			System.out.println("inside addTempItemDetail");

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			System.out.println("IsDelete" + isDelete);

			if (isDelete == 1) {
				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				tempList.remove(key);
				
				
				
				/*
				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				GetMatIssueDetail deleteDetail = editMat.getMatIssueDetailList().get(key);
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map = new LinkedMultiValueMap<String, Object>();

				map.add("matDetailId", deleteDetail.getMatDetailId());

				Info errMsg = rest.postForObject(Constants.url + "deleteMatContraDetail", map, Info.class);

				editMat.getMatIssueDetailList().remove(key);*/

			} else if (isEdit == 1) {

				int itemId = Integer.parseInt(request.getParameter("rmName"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);
				
				System.out.println("List is"+getSingleItem.toString());

				int catId = Integer.parseInt(request.getParameter("catId"));
				int index = Integer.parseInt(request.getParameter("index"));

				float quantity = Float.parseFloat(request.getParameter("qty"));
				
				System.out.println("q is"+quantity);
				tempList.get(index).setQty(quantity);
				tempList.get(index).setRmName(getSingleItem.getItemDesc());
				tempList.get(index).setRmId(getSingleItem.getItemId());
				tempList.get(index).setItemId(itemId);
				

				
				tempList.get(index).setCatId(catId);
				tempList.get(index).setCategoryName(catName);

				System.out.println("templist  =====" + tempList.toString());

			}

			else {

				int itemId = Integer.parseInt(request.getParameter("rmName"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);
				System.out.println("List is"+getSingleItem.toString());

				if (tempList.size() > 0) {
					int flag = 0;
					for (int i = 0; i < tempList.size(); i++) {
						tempList.get(i).setIsDuplicate(0);
						if (tempList.get(i).getItemId() == itemId) {
							tempList.get(i).setIsDuplicate(1);
							flag = 1;

						} // end of if item exist

					} // end of for
					if (flag == 0) {

						String catId = request.getParameter("catId");

						float qty = Float.parseFloat(request.getParameter("qty"));
						
						GetAddItemDetail temp = new GetAddItemDetail();
						temp.setRmName(getSingleItem.getItemDesc());
						temp.setItemId(getSingleItem.getItemId());
						temp.setRmId(getSingleItem.getItemId());
						temp.setQty(qty);
						temp.setUomId(Integer.parseInt(getSingleItem.getItemUom2()));
						temp.setCatId(Integer.parseInt(catId));
						temp.setCategoryName(catName);
						
						Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
						uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

						for (int i = 0; i < uomList.size(); i++) {
							if (uomList.get(i).getUomId() == Integer.parseInt(getSingleItem.getItemUom2()))
								;
							{
								temp.setUomName(uomList.get(i).getUomName());
							}
						}

						
						tempList.add(temp);

					}
					System.out.println("templist  =====" + tempList.toString());

					System.out.println("Inside Add Raw Material");

				} else {

					String catId = request.getParameter("catId");

					float qty = Float.parseFloat(request.getParameter("qty"));
					GetAddItemDetail temp = new GetAddItemDetail();
					temp.setRmName(getSingleItem.getItemDesc());
					temp.setItemId(getSingleItem.getItemId());
					temp.setQty(qty);
					temp.setCategoryName(catName);;
					temp.setRmId(getSingleItem.getItemId());
					
					temp.setUomId(Integer.parseInt(getSingleItem.getItemUom2()));
					temp.setCatId(Integer.parseInt(catId));
					Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
					uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

					for (int i = 0; i < uomList.size(); i++) {
						if (uomList.get(i).getUomId() == Integer.parseInt(getSingleItem.getItemUom2()))
							;
						{
							temp.setUomName(uomList.get(i).getUomName());
						}
					}

					
					tempList.add(temp);

				}

			}
			System.out.println("templist  =====" + tempList.toString());


		} catch (Exception e) {
			System.err.println("Exce In temp  tempList List " + e.getMessage());
			e.printStackTrace();
		}
		System.err.println("tempList " + tempList.toString());

		return tempList;

	}


	@RequestMapping(value = "/getItemDetailForEdit", method = RequestMethod.GET)
	public @ResponseBody GetAddItemDetail getItemDetailForEdit(HttpServletRequest request,
			HttpServletResponse response) {

		// int matDetailId = Integer.parseInt(request.getParameter("matDetailId"));
		int index = Integer.parseInt(request.getParameter("index"));

		return tempList.get(index);

	}
	
	
	@RequestMapping(value = "/insertItemDetail", method = RequestMethod.POST)
	public String insertItemDetail(HttpServletRequest request, HttpServletResponse response) {

		try {
		
			System.err.println("Inside insert insertItemDetail method");

		

			String itemId =request.getParameter("itemIdnew");
			System.out.println("item id"+itemId);
			/*String itemName = request.getParameter("itemName");
			String itemCode=request.getParameter("itemCode");*/

			Integer result = Integer.parseInt(itemId);

			List<AddItemDetail> detailList = new ArrayList<>();
			

			for (int i = 0; i < tempList.size(); i++) {

				 AddItemDetail dDetail = new  AddItemDetail();

				dDetail.setDelStatus(1);
				dDetail.setInt1(0);
				dDetail.setBoll1(1);
				dDetail.setBoll2(1);
				dDetail.setInt2(1);
				dDetail.setVarchar1("NA");
				
				dDetail.setItemId(result);
				dDetail.setRmType(1);
				dDetail.setNoPiecesPerItem(1);
				dDetail.setRmWeight(1);
				
				
				dDetail.setRmName(tempList.get(i).getRmName());
				dDetail.setRmUomId(tempList.get(i).getUomId());
				dDetail.setRmQty(tempList.get(i).getQty());
				dDetail.setRmId(tempList.get(i).getRmId());
				
				
				
				detailList.add(dDetail);
				
				

			}
			
			System.out.println("detail list "+detailList);
			AddItemDetail[] matIssueInsertRes = rest.postForObject(Constants.url + "saveItemDetail",detailList, AddItemDetail[].class);

			if (matIssueInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce In insertEnq method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showItemList";

	}

	List<GetExistingItemDetail> itemList;
	
	
	@RequestMapping(value = "/getExistingItemDetail/{itemId}/{itemName}/{itemCode}", method = RequestMethod.GET)
	public ModelAndView getExistingItemDetail(HttpServletRequest request, HttpServletResponse response,@PathVariable int itemId,
			@PathVariable String itemName,@PathVariable String itemCode) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("rawmaterial/editItemDetail");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("itemId", itemId);
			
		

			GetExistingItemDetail[] itemArray = rest.postForObject(Constants.url + "getItemDetailByItemDetailId",map, GetExistingItemDetail[].class);
			itemList = new ArrayList<GetExistingItemDetail>(Arrays.asList(itemArray));
			
			System.out.println("item detail are"+itemList.toString());

	       // itemList.add(tempList);
			model.addObject("editItemDetail", itemList);
			GetAddItemDetail dDetail = new GetAddItemDetail();
			
			
			for (int i = 0; i < itemList.size(); i++) {

			

				dDetail.setRmName(itemList.get(i).getRmName());

				dDetail.setQty(itemList.get(i).getRmQty());
				
				dDetail.setUomName(itemList.get(i).getUomName());
				
				dDetail.setCategoryName(itemList.get(i).getCatDesc());
				
				

				tempList.add(dDetail);

			}
			
			
			model.addObject("itemId", itemId);
			model.addObject("itemName", itemName);
			model.addObject("itemCode", itemCode);

			ItemCategory[] catArray = rest.getForObject(Constants.url + "getAllItemCategoryList", ItemCategory[].class);
			catList = new ArrayList<ItemCategory>(Arrays.asList(catArray));

			model.addObject("catList", catList);

		

		} catch (Exception e) {
			System.err.println("exception In editMat at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}
	
	
	
	/*@RequestMapping(value = "/addItemDetailInExisting", method = RequestMethod.GET)
	public @ResponseBody List<GetExistingItemDetail> addItemDetailInExisting(HttpServletRequest request,
			HttpServletResponse response) {
		String catName=request.getParameter("categoryName") ;

		try {
			System.out.println("inside addTempItemDetail");

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			System.out.println("IsDelete" + isDelete);

			if (isDelete == 1) {
				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				tempList.remove(key);
				
				
				

			} else if (isEdit == 1) {

				int itemId = Integer.parseInt(request.getParameter("rmName"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);
				
				System.out.println("List is"+getSingleItem.toString());

				int catId = Integer.parseInt(request.getParameter("catId"));
				int index = Integer.parseInt(request.getParameter("index"));

				float quantity = Float.parseFloat(request.getParameter("qty"));
				
				System.out.println("q is"+quantity);
				itemList.get(index).setRmQty(quantity);
				itemList.get(index).setRmName(getSingleItem.getItemDesc());
				//itemList.get(index).setRmId(getSingleItem.getItemId());
				//itemList.get(index).setItemId(itemId);
				

				
				//tempList.get(index).setCatId(catId);
				//itemList.get(index).setCategoryDesc(catName);

				System.out.println("templist  =====" + tempList.toString());

			}

			else {

				int itemId = Integer.parseInt(request.getParameter("rmName"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);
				System.out.println("List is"+getSingleItem.toString());

				if (tempList.size() > 0) {
					int flag = 0;
					for (int i = 0; i < tempList.size(); i++) {
						tempList.get(i).setIsDuplicate(0);
						if (tempList.get(i).getItemId() == itemId) {
							tempList.get(i).setIsDuplicate(1);
							flag = 1;

						} // end of if item exist

					} // end of for
					if (flag == 0) {

						String catId = request.getParameter("catId");

						float qty = Float.parseFloat(request.getParameter("qty"));
						
						GetAddItemDetail temp = new GetAddItemDetail();
						temp.setRmName(getSingleItem.getItemDesc());
						temp.setItemId(getSingleItem.getItemId());
						temp.setRmId(getSingleItem.getItemId());
						temp.setQty(qty);
						temp.setUomId(Integer.parseInt(getSingleItem.getItemUom2()));
						temp.setCatId(Integer.parseInt(catId));
						temp.setCategoryName(catName);
						
						Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
						uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

						for (int i = 0; i < uomList.size(); i++) {
							if (uomList.get(i).getUomId() == Integer.parseInt(getSingleItem.getItemUom2()))
								;
							{
								temp.setUomName(uomList.get(i).getUomName());
							}
						}

						
						tempList.add(temp);

					}
					System.out.println("templist  =====" + tempList.toString());

					System.out.println("Inside Add Raw Material");

				} else {

					String catId = request.getParameter("catId");

					float qty = Float.parseFloat(request.getParameter("qty"));
					GetAddItemDetail temp = new GetAddItemDetail();
					temp.setRmName(getSingleItem.getItemDesc());
					temp.setItemId(getSingleItem.getItemId());
					temp.setQty(qty);
					temp.setCategoryName(catName);;
					temp.setRmId(getSingleItem.getItemId());
					
					temp.setUomId(Integer.parseInt(getSingleItem.getItemUom2()));
					temp.setCatId(Integer.parseInt(catId));
					Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
					uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

					for (int i = 0; i < uomList.size(); i++) {
						if (uomList.get(i).getUomId() == Integer.parseInt(getSingleItem.getItemUom2()))
							;
						{
							temp.setUomName(uomList.get(i).getUomName());
						}
					}

					
					tempList.add(temp);

				}

			}
			System.out.println("templist  =====" + tempList.toString());


		} catch (Exception e) {
			System.err.println("Exce In temp  tempList List " + e.getMessage());
			e.printStackTrace();
		}
		System.err.println("tempList " + tempList.toString());

		//return tempList;

	}*/



	
}
