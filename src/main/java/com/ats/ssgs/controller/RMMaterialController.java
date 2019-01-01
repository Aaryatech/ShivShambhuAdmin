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
import com.ats.ssgs.model.rec.PayRecoveryDetail;

@Controller
public class RMMaterialController {

	List<GetAddItemDetail> tempList = new ArrayList<GetAddItemDetail>();
	RestTemplate rest = new RestTemplate();
	int isError = 0;
	List<GetExistingItemDetail> itemList;

	List<RawMatItem> rawItemList;
	List<ItemCategory> catList;
	List<Uom> uomList;
	List<GetExistingItemDetail> existItemList;

	GetAddItemDetail dDetail;

	@RequestMapping(value = "/getExistingItemDetail/{itemId}/{itemName}/{itemCode}", method = RequestMethod.GET)
	public ModelAndView getExistingItemDetail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int itemId, @PathVariable String itemName, @PathVariable String itemCode) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("rawmaterial/editItemDetail");

			model.addObject("title", "Add Item Detail");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("itemId", itemId);

			GetExistingItemDetail[] itemArray = rest.postForObject(Constants.url + "getItemDetailByItemDetailId", map,
					GetExistingItemDetail[].class);
			itemList = new ArrayList<GetExistingItemDetail>(Arrays.asList(itemArray));

			model.addObject("editItemDetail", itemList);

			dDetail = new GetAddItemDetail();
			for (int i = 0; i < itemList.size(); i++) {
				dDetail.setRmName(itemList.get(i).getRmName());

				dDetail.setQty(itemList.get(i).getRmQty());

				dDetail.setUomName(itemList.get(i).getUomName());

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

	@RequestMapping(value = "/editInAddItemDetail", method = RequestMethod.GET)
	public @ResponseBody List<GetExistingItemDetail> editInAddItemDetail(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			if (isDelete == 1) {

				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				GetExistingItemDetail deleteDetail = itemList.get(key);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map = new LinkedMultiValueMap<String, Object>();

				map.add("itemDetailId", deleteDetail.getItemDetailId());

				Info errMsg = rest.postForObject(Constants.url + "deleteItemDetail", map, Info.class);

				itemList.remove(key);

			} else if (isEdit == 1) {

				int itemId = Integer.parseInt(request.getParameter("rmName"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);

				System.out.println("List is" + getSingleItem.toString());

				int catId = Integer.parseInt(request.getParameter("catId"));
				int index = Integer.parseInt(request.getParameter("index"));

				float quantity = Float.parseFloat(request.getParameter("qty"));

				System.out.println("q is" + quantity);
				itemList.get(index).setRmQty(quantity);
				itemList.get(index).setRmName(getSingleItem.getItemDesc());
				itemList.get(index).setRmId(getSingleItem.getItemId());
				itemList.get(index).setItemId(itemId);
				itemList.get(index).setInt1(catId);

			}

			else {

				int itemId = Integer.parseInt(request.getParameter("rmName"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);
				System.out.println("List is" + getSingleItem.toString());

				if (itemList.size() > 0) {
					int flag = 0;
					for (int i = 0; i < itemList.size(); i++) {
						itemList.get(i).setIsDuplicate(0);
						if (itemList.get(i).getItemId() == itemId) {
							itemList.get(i).setIsDuplicate(1);
							flag = 1;

						} // end of if item exist

					} // end of for
					if (flag == 0) {

						int catId = Integer.parseInt(request.getParameter("catId"));

						float qty = Float.parseFloat(request.getParameter("qty"));

						GetExistingItemDetail temp = new GetExistingItemDetail();
						temp.setRmName(getSingleItem.getItemDesc());
						temp.setItemId(getSingleItem.getItemId());
						temp.setRmId(getSingleItem.getItemId());
						temp.setRmQty(qty);
						temp.setRmUomId(Integer.parseInt(getSingleItem.getItemUom2()));
						temp.setCatDesc("NA");
						temp.setInt1(catId);

						Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
						uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

						for (int i = 0; i < uomList.size(); i++) {
							if (uomList.get(i).getUomId() == Integer.parseInt(getSingleItem.getItemUom2()))
								;
							{
								temp.setUomName(uomList.get(i).getUomName());
							}
						}

						itemList.add(temp);

					}
					System.out.println("templist  =====" + tempList.toString());

					System.out.println("Inside Add Raw Material");

				} else {

					int catId = Integer.parseInt(request.getParameter("catId"));

					float qty = Float.parseFloat(request.getParameter("qty"));
					GetExistingItemDetail temp = new GetExistingItemDetail();
					temp.setRmName(getSingleItem.getItemDesc());
					temp.setItemId(getSingleItem.getItemId());
					temp.setRmQty(qty);
					temp.setCatDesc("NA");
					temp.setRmId(getSingleItem.getItemId());
					temp.setInt1(catId);

					temp.setRmUomId(Integer.parseInt(getSingleItem.getItemUom2()));

					Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
					uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

					for (int i = 0; i < uomList.size(); i++) {
						if (uomList.get(i).getUomId() == Integer.parseInt(getSingleItem.getItemUom2()))
							;
						{
							temp.setUomName(uomList.get(i).getUomName());
						}
					}

					itemList.add(temp);

				}

			}
			System.out.println("itemList  =====" + itemList.toString());

		} catch (Exception e) {

			e.printStackTrace();
		}
		System.err.println("itemList " + itemList.toString());

		return itemList;

	}

	@RequestMapping(value = "/getDetailEditForItemDetail", method = RequestMethod.GET)
	public @ResponseBody GetExistingItemDetail getDetailEditForItemDetail(HttpServletRequest request,

			HttpServletResponse response) {

		int index = Integer.parseInt(request.getParameter("index"));

		return itemList.get(index);

	}

	@RequestMapping(value = "/insertItemDetail", method = RequestMethod.POST)
	public String insertItemDetail(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertItemDetail method");

			String itemId = request.getParameter("itemIdnew");
			System.out.println("item id" + itemId);
			/*
			 * String itemName = request.getParameter("itemName"); String
			 * itemCode=request.getParameter("itemCode");
			 */

			Integer result = Integer.parseInt(itemId);

			List<AddItemDetail> detailList = new ArrayList<>();

			for (int i = 0; i < itemList.size(); i++) {

				AddItemDetail dDetail = new AddItemDetail();

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

				dDetail.setRmName(itemList.get(i).getRmName());
				dDetail.setRmUomId(itemList.get(i).getRmUomId());
				dDetail.setRmQty(itemList.get(i).getRmQty());
				dDetail.setRmId(itemList.get(i).getRmId());
				dDetail.setItemDetailId(itemList.get(i).getItemDetailId());
				dDetail.setInt1(itemList.get(i).getInt1());

				detailList.add(dDetail);

			}

			System.out.println("detail list " + detailList);
			AddItemDetail[] matIssueInsertRes = rest.postForObject(Constants.url + "saveItemDetail", detailList,
					AddItemDetail[].class);

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

}
