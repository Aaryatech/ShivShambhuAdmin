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
import com.ats.ssgs.model.master.GetDocTermHeader;
import com.ats.ssgs.model.mat.Contractor;
import com.ats.ssgs.model.mat.ItemCategory;
import com.ats.ssgs.model.mat.MatIssueDetail;
import com.ats.ssgs.model.mat.MatIssueHeader;
import com.ats.ssgs.model.mat.RawMatItem;
import com.ats.ssgs.model.mat.TempMatIssueDetail;

@Controller
public class MatIssueController {
	RestTemplate rest = new RestTemplate();
	int isError = 0;

	List<TempMatIssueDetail> tempList = new ArrayList<TempMatIssueDetail>();
	List<Contractor> conList;
	List<RawMatItem> rawItemList;
	List<ItemCategory> catList;

	@RequestMapping(value = "/showAddMatIssueContractor", method = RequestMethod.GET)
	public ModelAndView showAddMatIssueContractor(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("matissue/addmatissue");
			model.addObject("isError", isError);
			isError = 0;
			tempList = new ArrayList<TempMatIssueDetail>();

			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			ItemCategory[] catArray = rest.getForObject(Constants.url + "getAllItemCategoryList", ItemCategory[].class);
			catList = new ArrayList<ItemCategory>(Arrays.asList(catArray));

			model.addObject("catList", catList);

			model.addObject("title", "Add Material Issue Contractor");

		} catch (Exception e) {

			System.err.println("exception In showAddMatIssueContractor at MatContr" + e.getMessage());

			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/addMatIssueDetail", method = RequestMethod.GET)
	public @ResponseBody List<TempMatIssueDetail> addMatIssueDetail(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			System.out.println("IsDelete" + isDelete);

			if (isDelete == 1) {
				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				tempList.remove(key);

			} else if (isEdit == 1) {

				int index = Integer.parseInt(request.getParameter("index"));
				String itemName = request.getParameter("itemName");
				float quantity = Float.parseFloat(request.getParameter("qty"));

				tempList.get(index).setQuantity(quantity);
				tempList.get(index).setItemName(itemName);
				tempList.get(index).setValue(tempList.get(index).getItemRate() * quantity);

			}

			else {

				int itemId = Integer.parseInt(request.getParameter("itemName"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);

				float qty = Float.parseFloat(request.getParameter("qty"));

				TempMatIssueDetail temp = new TempMatIssueDetail();
				temp.setItemName(getSingleItem.getItemDesc());
				temp.setItemId(getSingleItem.getItemId());
				temp.setQuantity(qty);
				temp.setItemRate(getSingleItem.getItemClRate());

				temp.setUomName(getSingleItem.getItemUom());
				temp.setValue(getSingleItem.getItemClRate() * qty);
				tempList.add(temp);

				System.out.println("Inside Add Raw Material");
			}

		} catch (Exception e) {
			System.err.println("Exce In temp  tempList List " + e.getMessage());
			e.printStackTrace();
		}
		System.err.println("tempList " + tempList.toString());

		return tempList;

	}

	// Ajax call
	@RequestMapping(value = "/getRawItemByCatId", method = RequestMethod.GET)
	public @ResponseBody List<RawMatItem> getRawItemByCatId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int catId = Integer.parseInt(request.getParameter("catId"));

		map.add("catId", catId);

		RawMatItem[] itemArray = rest.postForObject(Constants.url + "getRawItemListByCatId", map, RawMatItem[].class);
		rawItemList = new ArrayList<RawMatItem>(Arrays.asList(itemArray));

		return rawItemList;

	}

	@RequestMapping(value = "/getMatIssueForEdit", method = RequestMethod.GET)
	public @ResponseBody TempMatIssueDetail getMatIssueForEdit(HttpServletRequest request,
			HttpServletResponse response) {

		// int matDetailId = Integer.parseInt(request.getParameter("matDetailId"));
		int index = Integer.parseInt(request.getParameter("index"));

		return tempList.get(index);

	}

	// insertMatIssueContractor
	@RequestMapping(value = "/insertMatIssueContractor", method = RequestMethod.POST)
	public String insertMatIssueContractor(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertMatIssueContractor method");

			// int matHeaderId = Integer.parseInt(request.getParameter("matHeaderId"));

			int contrId = Integer.parseInt(request.getParameter("contr_id"));
			String issueNo = request.getParameter("issueNo");

			// String date = request.getParameter("date");

			// int sortNo = Integer.parseInt(request.getParameter("sortNo"));

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// Calendar cal = Calendar.getInstance();
			String curDate = dateFormat.format(new Date());
			MatIssueHeader matIssue = new MatIssueHeader();

			// DateFormat dateTimeFrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			matIssue.setContrId(contrId);
			matIssue.setDate(curDate);
			matIssue.setDelStatus(1);
			matIssue.setExBool1(0);
			matIssue.setExBool2(0);
			matIssue.setExDate1(curDate);
			matIssue.setExDate2(curDate);
			matIssue.setExInt1(1);
			matIssue.setExInt2(1);
			matIssue.setExInt3(1);
			matIssue.setUserId(1);

			matIssue.setIssueNo(issueNo);
			matIssue.setQtyTotal(0);
			matIssue.setExVar1("NA");
			matIssue.setExVar2("NA");
			matIssue.setExVar3("NA");
			// matIssue.setMatHeaderId(matHeaderId);

			List<MatIssueDetail> detailList = new ArrayList<>();
			float totalValue = 0;
			float totalQty = 0;

			for (int i = 0; i < tempList.size(); i++) {

				MatIssueDetail dDetail = new MatIssueDetail();

				dDetail.setDelStatus(1);
				dDetail.setExInt1(0);
				dDetail.setExBool1(1);
				dDetail.setExDate1(curDate);
				dDetail.setExInt1(1);
				dDetail.setExInt2(1);
				dDetail.setExVar1("NA");
				dDetail.setExVar2("NA");
				dDetail.setItemId(tempList.get(i).getItemId());
				dDetail.setItemRate(tempList.get(i).getItemRate());
				dDetail.setQuantity(tempList.get(i).getQuantity());
				dDetail.setUomId(tempList.get(i).getUomId());
				dDetail.setValue(tempList.get(i).getValue());
				totalValue = totalValue + tempList.get(i).getValue();
				totalQty = totalQty + tempList.get(i).getQuantity();

				detailList.add(dDetail);

			}
			matIssue.setTotal(totalValue);
			matIssue.setQtyTotal(totalQty);
			matIssue.setMatIssueDetailList(detailList);

			MatIssueHeader matIssueInsertRes = rest.postForObject(Constants.url + "saveMatIssueHeaderAndDetail",
					matIssue, MatIssueHeader.class);

			if (matIssueInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce In insertEnq method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showAddMatIssueContractor";

	}
	
	List<GetDocTermHeader> docTermHeaderList;

	@RequestMapping(value = "/showDocTermList", method = RequestMethod.GET)
	public ModelAndView showDocTermList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("docterm/doctermlist");
			GetDocTermHeader[] docArray = rest.getForObject(Constants.url + "getAllDocHeaderList",
					GetDocTermHeader[].class);
			docTermHeaderList = new ArrayList<GetDocTermHeader>(Arrays.asList(docArray));

			model.addObject("title", "Terms & Conditions List");
			model.addObject("docHeaderList", docTermHeaderList);
		} catch (Exception e) {

			System.err.println("exception In showDocTermList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}


}
