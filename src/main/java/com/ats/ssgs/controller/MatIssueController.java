package com.ats.ssgs.controller;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.DocTermHeader;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.mat.Contractor;
import com.ats.ssgs.model.mat.GetMatIssueDetail;
import com.ats.ssgs.model.mat.GetMatIssueHeader;
import com.ats.ssgs.model.mat.GetVehDetail;
import com.ats.ssgs.model.mat.GetVehHeader;
import com.ats.ssgs.model.mat.ItemCategory;
import com.ats.ssgs.model.mat.MatIssueDetail;
import com.ats.ssgs.model.mat.MatIssueHeader;
import com.ats.ssgs.model.mat.MatIssueVehDetail;
import com.ats.ssgs.model.mat.MatIssueVehHeader;
import com.ats.ssgs.model.mat.RawMatItem;
import com.ats.ssgs.model.mat.TempMatIssueDetail;

@Controller
public class MatIssueController {
	RestTemplate rest = new RestTemplate();
	int isError = 0;

	List<TempMatIssueDetail> tempList = new ArrayList<TempMatIssueDetail>();
	List<Contractor> conList;
	List<Vehicle> vehList;
	List<RawMatItem> rawItemList;
	List<ItemCategory> catList;
	List<Uom> uomList;
	List<GetMatIssueDetail> getMatIssueDetailList = new ArrayList<GetMatIssueDetail>();

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

			int itemId = Integer.parseInt(request.getParameter("itemName"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("itemId", itemId);

			RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map, RawMatItem.class);

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			System.out.println("IsDelete" + isDelete);

			if (isDelete == 1) {
				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				tempList.remove(key);

			} else if (isEdit == 1) {
				int catId = Integer.parseInt(request.getParameter("catId"));
				int index = Integer.parseInt(request.getParameter("index"));

				float quantity = Float.parseFloat(request.getParameter("qty"));

				tempList.get(index).setQuantity(quantity);
				tempList.get(index).setItemName(getSingleItem.getItemDesc());
				tempList.get(index).setItemId(itemId);

				tempList.get(index).setValue(tempList.get(index).getItemRate() * quantity);
				tempList.get(index).setCatId(catId);

				System.out.println("templist  =====" + tempList.toString());

			}

			else {
				String catId = request.getParameter("catId");

				float qty = Float.parseFloat(request.getParameter("qty"));
				TempMatIssueDetail temp = new TempMatIssueDetail();
				temp.setItemName(getSingleItem.getItemDesc());
				temp.setItemId(getSingleItem.getItemId());
				temp.setQuantity(qty);
				temp.setItemRate(getSingleItem.getItemClRate());

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

	@RequestMapping(value = "/getMatIssueForEditMatHeader", method = RequestMethod.GET)
	public @ResponseBody GetMatIssueDetail getMatIssueForEditMatHeader(HttpServletRequest request,
			HttpServletResponse response) {

		// int matDetailId = Integer.parseInt(request.getParameter("matDetailId"));
		int index = Integer.parseInt(request.getParameter("index"));

		return editMat.getMatIssueDetailList().get(index);

	}

	// insertMatIssueContractor
	@RequestMapping(value = "/insertMatIssueContractor", method = RequestMethod.POST)
	public String insertMatIssueContractor(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertMatIssueContractor method");

			// int matHeaderId = Integer.parseInt(request.getParameter("matHeaderId"));

			int contrId = Integer.parseInt(request.getParameter("contr_id"));
			String issueNo = request.getParameter("issueNo");

			String date = request.getParameter("date");

			// int sortNo = Integer.parseInt(request.getParameter("sortNo"));

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// Calendar cal = Calendar.getInstance();
			String curDate = dateFormat.format(new Date());
			MatIssueHeader matIssue = new MatIssueHeader();

			// DateFormat dateTimeFrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			matIssue.setContrId(contrId);
			matIssue.setDate(DateConvertor.convertToYMD(date));
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

	List<GetMatIssueHeader> matIssueHeaderList;

	@RequestMapping(value = "/showMatIssueContractorList", method = RequestMethod.GET)
	public ModelAndView showMatIssueContractorList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/matissuelist");
			GetMatIssueHeader[] matArray = rest.getForObject(Constants.url + "getMatIssueContrHeaderList",
					GetMatIssueHeader[].class);
			matIssueHeaderList = new ArrayList<GetMatIssueHeader>(Arrays.asList(matArray));

			model.addObject("title", "Material Issue Contractor List");
			model.addObject("matIssueList", matIssueHeaderList);
		} catch (Exception e) {

			System.err.println("exception In showMatIssueContractorList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	GetMatIssueHeader editMat;

	@RequestMapping(value = "/editMatIssueCon/{matHeaderId}", method = RequestMethod.GET)
	public ModelAndView editDocHeader(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int matHeaderId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/editMatIssue");

			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			ItemCategory[] catArray = rest.getForObject(Constants.url + "getAllItemCategoryList", ItemCategory[].class);
			catList = new ArrayList<ItemCategory>(Arrays.asList(catArray));

			model.addObject("catList", catList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matHeaderId", matHeaderId);
			editMat = rest.postForObject(Constants.url + "getMatIssueContrByHeaderId", map, GetMatIssueHeader.class);
			model.addObject("title", "Edit Material Issue Contractor");
			model.addObject("editMat", editMat);
			model.addObject("editMatDetail", editMat.getMatIssueDetailList());

		} catch (Exception e) {
			System.err.println("exception In editMat at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteMatIssueCon/{matHeaderId}", method = RequestMethod.GET)
	public String deleteMatIssueCon(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int matHeaderId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matHeaderId", matHeaderId);

			Info errMsg = rest.postForObject(Constants.url + "deleteMatIssueHeader", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteMatIssueHeader @MatContr" + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showMatIssueContractorList";
	}

	@RequestMapping(value = "/deleteRecordofMatIssueList", method = RequestMethod.POST)
	public String deleteRecordofMatIssueList(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] matHeaderIds = request.getParameterValues("matHeaderIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < matHeaderIds.length; i++) {
				sb = sb.append(matHeaderIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matHeaderIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiMatIssueHeader", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteMultiMatIssueHeader MatContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showMatIssueContractorList";
	}

	@RequestMapping(value = "/editInAddMatIssueDetail", method = RequestMethod.GET)
	public @ResponseBody List<GetMatIssueDetail> editInAddMatIssueDetail(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int itemId = Integer.parseInt(request.getParameter("itemName"));
			int matHeaderId = Integer.parseInt(request.getParameter("matHeaderId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("itemId", itemId);

			RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map, RawMatItem.class);

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			if (isDelete == 1) {
				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				GetMatIssueDetail deleteDetail = editMat.getMatIssueDetailList().get(key);
				map = new LinkedMultiValueMap<String, Object>();

				map.add("matVehDetailId", deleteDetail.getMatDetailId());

				Info errMsg = rest.postForObject(Constants.url + "deleteMatContraDetail", map, Info.class);

				editMat.getMatIssueDetailList().remove(key);

			} else if (isEdit == 1) {

				System.out.println("isedit" + isEdit);
				int catId = Integer.parseInt(request.getParameter("catId"));
				int index = Integer.parseInt(request.getParameter("index"));
				float quantity = Float.parseFloat(request.getParameter("qty"));

				editMat.getMatIssueDetailList().get(index).setQuantity(quantity);
				editMat.getMatIssueDetailList().get(index)
						.setValue(editMat.getMatIssueDetailList().get(index).getItemRate() * quantity);
				editMat.getMatIssueDetailList().get(index).setItemId(itemId);
				editMat.getMatIssueDetailList().get(index).setItemDesc(getSingleItem.getItemDesc());
				editMat.getMatIssueDetailList().get(index).setExInt1(catId);

			}

			else {

				System.out.println("RawMatItem " + getSingleItem.toString());
				int catId = Integer.parseInt(request.getParameter("catId"));
				float qty = Float.parseFloat(request.getParameter("qty"));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// Calendar cal = Calendar.getInstance();
				String curDate = dateFormat.format(new Date());

				GetMatIssueDetail matIssueDetail = new GetMatIssueDetail();
				matIssueDetail.setDelStatus(1);
				matIssueDetail.setExBool1(1);
				matIssueDetail.setExDate1(curDate);
				matIssueDetail.setExInt1(catId);
				matIssueDetail.setExInt2(1);
				matIssueDetail.setExVar1("NA");
				matIssueDetail.setExVar2("NA");
				matIssueDetail.setItemCode(getSingleItem.getItemCode());
				matIssueDetail.setItemDesc(getSingleItem.getItemDesc());
				matIssueDetail.setItemRate(getSingleItem.getItemClRate());
				matIssueDetail.setMatHeaderId(matHeaderId);
				matIssueDetail.setQuantity(qty);
				matIssueDetail.setUomId(Integer.parseInt(getSingleItem.getItemUom2()));
				matIssueDetail.setValue(getSingleItem.getItemClRate() * qty);
				matIssueDetail.setItemId(itemId);

				Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
				uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

				for (int i = 0; i < uomList.size(); i++) {
					if (uomList.get(i).getUomId() == Integer.parseInt(getSingleItem.getItemUom2()))

					{
						matIssueDetail.setUomName(uomList.get(i).getUomName());
					}
				}
				editMat.getMatIssueDetailList().add(matIssueDetail);
				// getMatIssueDetailList.add(matIssueDetail);

				System.out.println("Inside Edit Raw Material");
			}

		} catch (Exception e) {
			System.err.println("Exce In temp  getMatIssueDetailList List " + e.getMessage());
			e.printStackTrace();
		}
		System.err.println("getMatIssueDetailList " + getMatIssueDetailList.toString());

		return editMat.getMatIssueDetailList();

	}

	@RequestMapping(value = "/updateMaterialContr", method = RequestMethod.POST)
	public String updateMaterialContr(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert updateMaterialContr method");

			int matHeaderId = Integer.parseInt(request.getParameter("matHeaderId"));

			int contrId = Integer.parseInt(request.getParameter("contr_id"));
			String issueNo = request.getParameter("issueNo");

			String date = request.getParameter("date");

			// int sortNo = Integer.parseInt(request.getParameter("sortNo"));

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// Calendar cal = Calendar.getInstance();
			String curDate = dateFormat.format(new Date());
			MatIssueHeader matIssue = new MatIssueHeader();

			// DateFormat dateTimeFrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			matIssue.setContrId(contrId);
			matIssue.setDate(DateConvertor.convertToYMD(date));
			matIssue.setDelStatus(1);
			matIssue.setExDate1(curDate);
			matIssue.setExDate2(curDate);

			matIssue.setUserId(1);

			matIssue.setIssueNo(issueNo);
			matIssue.setQtyTotal(0);

			matIssue.setMatHeaderId(matHeaderId);

			List<MatIssueDetail> detailList = new ArrayList<>();
			float totalValue = 0;
			float totalQty = 0;

			for (int i = 0; i < editMat.getMatIssueDetailList().size(); i++) {

				MatIssueDetail dDetail = new MatIssueDetail();

				dDetail.setDelStatus(1);
				dDetail.setMatHeaderId(matHeaderId);

				dDetail.setItemId(editMat.getMatIssueDetailList().get(i).getItemId());
				dDetail.setItemRate(editMat.getMatIssueDetailList().get(i).getItemRate());
				dDetail.setQuantity(editMat.getMatIssueDetailList().get(i).getQuantity());
				dDetail.setUomId(editMat.getMatIssueDetailList().get(i).getUomId());
				dDetail.setValue(editMat.getMatIssueDetailList().get(i).getValue());
				dDetail.setMatDetailId(editMat.getMatIssueDetailList().get(i).getMatDetailId());
				totalValue = totalValue + editMat.getMatIssueDetailList().get(i).getValue();
				totalQty = totalQty + editMat.getMatIssueDetailList().get(i).getQuantity();

				detailList.add(dDetail);

			}
			matIssue.setTotal(totalValue);
			matIssue.setQtyTotal(totalQty);
			matIssue.setMatIssueDetailList(detailList);

			System.out.println("detailList" + detailList.size());

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

	@RequestMapping(value = "/showAddMatIssueVehicle", method = RequestMethod.GET)
	public ModelAndView showAddMatIssueVehicle(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("matissue/addvehmat");
			model.addObject("isError", isError);
			isError = 0;
			tempList = new ArrayList<TempMatIssueDetail>();

			Vehicle[] vehArray = rest.getForObject(Constants.url + "getAllVehicleList", Vehicle[].class);
			vehList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehList", vehList);

			ItemCategory[] catArray = rest.getForObject(Constants.url + "getAllItemCategoryList", ItemCategory[].class);
			catList = new ArrayList<ItemCategory>(Arrays.asList(catArray));

			model.addObject("catList", catList);

			model.addObject("title", "Add Material Issue Vehicle");

		} catch (Exception e) {

			System.err.println("exception In showAddMatIssueVehicle at MatContr" + e.getMessage());

			e.printStackTrace();
		}
		return model;
	}

	// insertMatIssueVehicle
	@RequestMapping(value = "/insertMatIssueVehicle", method = RequestMethod.POST)
	public String insertMatIssueVehicle(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertMatIssueVehicle method");

			int vehId = Integer.parseInt(request.getParameter("vehId"));
			String vehNo = request.getParameter("vehNo");
			float reading = Float.parseFloat(request.getParameter("reading"));

			String date = request.getParameter("date");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());
			MatIssueVehHeader matIssue = new MatIssueVehHeader();

			matIssue.setVehId(vehId);
			matIssue.setDate(DateConvertor.convertToYMD(date));
			matIssue.setDelStatus(1);
			matIssue.setExBool1(0);
			matIssue.setExBool2(0);
			matIssue.setExDate1(curDate);
			matIssue.setExDate2(curDate);
			matIssue.setExInt1(1);
			matIssue.setExInt2(1);
			matIssue.setExInt3(1);
			matIssue.setUserId(1);

			matIssue.setVehNo(vehNo);
			matIssue.setReading(reading);

			matIssue.setExVar1("NA");
			matIssue.setExVar2("NA");
			matIssue.setExVar3("NA");

			List<MatIssueVehDetail> detailList = new ArrayList<>();
			float totalValue = 0;
			float totalQty = 0;

			for (int i = 0; i < tempList.size(); i++) {

				MatIssueVehDetail dDetail = new MatIssueVehDetail();

				dDetail.setDelStatus(1);
				dDetail.setExInt1(0);
				dDetail.setExBool1(1);
				dDetail.setExDate1(curDate);
				dDetail.setExInt1(1);
				dDetail.setExInt2(1);
				dDetail.setExVar1("NA");
				dDetail.setExVar2("NA");
				dDetail.setItemId(tempList.get(i).getItemId());
				dDetail.setRate(tempList.get(i).getItemRate());
				dDetail.setQuantity(tempList.get(i).getQuantity());
				dDetail.setUomId(tempList.get(i).getUomId());
				dDetail.setValue(tempList.get(i).getValue());
				totalValue = totalValue + tempList.get(i).getValue();
				totalQty = totalQty + tempList.get(i).getQuantity();

				detailList.add(dDetail);

			}
			matIssue.setVehTotal(totalValue);
			matIssue.setVehQtyTotal(totalQty);
			matIssue.setVehDetailList(detailList);

			MatIssueVehHeader matIssueInsertRes = rest.postForObject(Constants.url + "saveMatIssueVehicle", matIssue,
					MatIssueVehHeader.class);

			if (matIssueInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce In insertEnq method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showAddMatIssueVehicle";

	}

	List<GetVehHeader> matIssueVehList;

	@RequestMapping(value = "/showMatIssueVehicleList", method = RequestMethod.GET)
	public ModelAndView showMatIssueVehicleList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/matissuevehlist");
			GetVehHeader[] matArray = rest.getForObject(Constants.url + "getMatIssueVehHeaderList",
					GetVehHeader[].class);
			matIssueVehList = new ArrayList<GetVehHeader>(Arrays.asList(matArray));

			model.addObject("title", "Material Issue Vehicle List");
			model.addObject("matIssueList", matIssueVehList);
		} catch (Exception e) {

			System.err.println("exception In showMatIssueVehicleList at matissue Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/deleteMatVehicle/{matVehHeaderId}", method = RequestMethod.GET)
	public String deleteMatVehicle(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int matVehHeaderId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matVehHeaderId", matVehHeaderId);

			Info errMsg = rest.postForObject(Constants.url + "deleteMatVehHeader", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteMatVehicle @MatContr" + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showMatIssueVehicleList";
	}

	@RequestMapping(value = "/deleteRecordofMatVehList", method = RequestMethod.POST)
	public String deleteRecordofMatVehList(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] matVehHeaderIds = request.getParameterValues("matVehHeaderIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < matVehHeaderIds.length; i++) {
				sb = sb.append(matVehHeaderIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matVehHeaderIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiMatVehHeader", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteMultiMatIssueHeader MatContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showMatIssueVehicleList";
	}

	GetVehHeader editVeh;

	@RequestMapping(value = "/editMatIssueVeh/{matVehHeaderId}", method = RequestMethod.GET)
	public ModelAndView editMatIssueVeh(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int matVehHeaderId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/editmatveh");
			Vehicle[] vehArray = rest.getForObject(Constants.url + "getAllVehicleList", Vehicle[].class);
			vehList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehList", vehList);

			ItemCategory[] catArray = rest.getForObject(Constants.url + "getAllItemCategoryList", ItemCategory[].class);
			catList = new ArrayList<ItemCategory>(Arrays.asList(catArray));

			model.addObject("catList", catList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matVehHeaderId", matVehHeaderId);
			editVeh = rest.postForObject(Constants.url + "getMatIssueVehicleByHeaderId", map, GetVehHeader.class);
			model.addObject("title", "Edit Material Issue Vehicle");
			model.addObject("editVeh", editVeh);
			model.addObject("editVehDetail", editVeh.getVehDetailList());

		} catch (Exception e) {
			System.err.println("exception In editMatIssueVeh at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/editInAddMatVehicleDetail", method = RequestMethod.GET)
	public @ResponseBody List<GetVehDetail> editInAddMatVehicleDetail(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			if (isDelete == 1) {
				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				GetVehDetail delteDetail = editVeh.getVehDetailList().get(key);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("matVehDetailId", delteDetail.getMatVehDetailId());

				editVeh.getVehDetailList().remove(key);
				Info errMsg = rest.postForObject(Constants.url + "deleteMatVehDetail", map, Info.class);

			} else if (isEdit == 1) {

				int itemId = Integer.parseInt(request.getParameter("itemName"));
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);

				int catId = Integer.parseInt(request.getParameter("catId"));
				int index = Integer.parseInt(request.getParameter("index"));
				float quantity = Float.parseFloat(request.getParameter("qty"));

				editVeh.getVehDetailList().get(index).setQuantity(quantity);
				editVeh.getVehDetailList().get(index)
						.setValue(editVeh.getVehDetailList().get(index).getRate() * quantity);
				editVeh.getVehDetailList().get(index).setItemId(itemId);
				editVeh.getVehDetailList().get(index).setItemDesc(getSingleItem.getItemDesc());
				editVeh.getVehDetailList().get(index).setExInt1(catId);

			}

			else {

				int matVehHeaderId = Integer.parseInt(request.getParameter("matVehHeaderId"));

				int itemId = Integer.parseInt(request.getParameter("itemName"));
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("itemId", itemId);

				RawMatItem getSingleItem = rest.postForObject(Constants.url + "getRawItemLByItemId", map,
						RawMatItem.class);

				int catId = Integer.parseInt(request.getParameter("catId"));
				float qty = Float.parseFloat(request.getParameter("qty"));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				String curDate = dateFormat.format(new Date());

				GetVehDetail matIssueDetail = new GetVehDetail();
				matIssueDetail.setDelStatus(1);
				matIssueDetail.setExBool1(1);
				matIssueDetail.setExDate1(curDate);
				matIssueDetail.setExInt1(catId);
				matIssueDetail.setExInt2(1);
				matIssueDetail.setExVar1("NA");
				matIssueDetail.setExVar2("NA");
				matIssueDetail.setItemCode(getSingleItem.getItemCode());
				matIssueDetail.setItemDesc(getSingleItem.getItemDesc());
				matIssueDetail.setRate(getSingleItem.getItemClRate());
				matIssueDetail.setMatVehHeaderId(matVehHeaderId);
				matIssueDetail.setQuantity(qty);
				matIssueDetail.setUomId(Integer.parseInt(getSingleItem.getItemUom2()));
				matIssueDetail.setValue(getSingleItem.getItemClRate() * qty);
				matIssueDetail.setItemId(itemId);

				Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
				uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

				for (int i = 0; i < uomList.size(); i++) {
					if (uomList.get(i).getUomId() == Integer.parseInt(getSingleItem.getItemUom2()))

					{
						matIssueDetail.setUomName(uomList.get(i).getUomName());
					}
				}
				editVeh.getVehDetailList().add(matIssueDetail);

			}

		} catch (Exception e) {
			System.err.println("Exce In temp  getVehDetailList List " + e.getMessage());
			e.printStackTrace();
		}
		System.err.println("getVehDetailList " + editVeh.getVehDetailList().toString());

		return editVeh.getVehDetailList();

	}

	@RequestMapping(value = "/getIndexForEditMatVehicle", method = RequestMethod.GET)
	public @ResponseBody GetVehDetail getIndexForEditMatVehicle(HttpServletRequest request,
			HttpServletResponse response) {

		int index = Integer.parseInt(request.getParameter("index"));

		return editVeh.getVehDetailList().get(index);

	}

	@RequestMapping(value = "/updateMaterialVehicle", method = RequestMethod.POST)
	public String updateMaterialVehicle(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert updateMaterialVehicle method");

			int matVehHeaderId = Integer.parseInt(request.getParameter("matVehHeaderId"));

			int vehId = Integer.parseInt(request.getParameter("vehId"));
			String vehNo = request.getParameter("vehNo");
			float reading = Float.parseFloat(request.getParameter("reading"));

			String date = request.getParameter("date");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());
			MatIssueVehHeader matIssue = new MatIssueVehHeader();

			matIssue.setVehId(vehId);
			matIssue.setDate(DateConvertor.convertToYMD(date));
			matIssue.setDelStatus(1);
			matIssue.setExDate1(curDate);
			matIssue.setExDate2(curDate);
			matIssue.setUserId(1);
			matIssue.setVehNo(vehNo);
			matIssue.setMatVehHeaderId(matVehHeaderId);
			matIssue.setReading(reading);

			List<MatIssueVehDetail> detailList = new ArrayList<>();
			float vehTotal = 0;
			float vehQtyTotal = 0;

			for (int i = 0; i < editVeh.getVehDetailList().size(); i++) {

				MatIssueVehDetail dDetail = new MatIssueVehDetail();

				dDetail.setDelStatus(editVeh.getVehDetailList().get(i).getDelStatus());
				dDetail.setMatVehHeaderId(matVehHeaderId);

				dDetail.setItemId(editVeh.getVehDetailList().get(i).getItemId());
				dDetail.setRate(editVeh.getVehDetailList().get(i).getRate());
				dDetail.setQuantity(editVeh.getVehDetailList().get(i).getQuantity());
				dDetail.setUomId(editVeh.getVehDetailList().get(i).getUomId());
				dDetail.setValue(editVeh.getVehDetailList().get(i).getValue());
				dDetail.setMatVehDetailId(editVeh.getVehDetailList().get(i).getMatVehDetailId());
				vehTotal = vehTotal + editVeh.getVehDetailList().get(i).getValue();
				vehQtyTotal = vehQtyTotal + editVeh.getVehDetailList().get(i).getQuantity();

				detailList.add(dDetail);

			}
			matIssue.setVehTotal(vehTotal);
			matIssue.setVehQtyTotal(vehQtyTotal);
			matIssue.setVehDetailList(detailList);

			System.out.println("detailList" + detailList.size());

			MatIssueVehHeader matIssueInsertRes = rest.postForObject(Constants.url + "saveMatIssueVehicle", matIssue,
					MatIssueVehHeader.class);

			if (matIssueInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce In updateMaterialVehicle method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showAddMatIssueVehicle";

	}

}
