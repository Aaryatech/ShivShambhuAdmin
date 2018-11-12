package com.ats.ssgs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.el.ELContext;
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
import com.ats.ssgs.model.enq.EnqDetail;
import com.ats.ssgs.model.enq.EnqGenFact;
import com.ats.ssgs.model.enq.EnqHeader;
import com.ats.ssgs.model.enq.TempEnqItem;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.Info;
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

	private ArrayList<EnqGenFact> enqGenFactList;

	@RequestMapping(value = "/showAddEnquiry", method = RequestMethod.GET)
	public ModelAndView showAddItem(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			enqItemList = new ArrayList<>();
			model = new ModelAndView("enq/addenquiry");

			model.addObject("title", "Add Enquiry");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

			model.addObject("uomList", uomList);

			EnqGenFact[] enqFactArray = rest.getForObject(Constants.url + "getAllEGFList", EnqGenFact[].class);
			enqGenFactList = new ArrayList<EnqGenFact>(Arrays.asList(enqFactArray));

			model.addObject("enqGenFactList", enqGenFactList);

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

		/*
		 * int itemId = Integer.parseInt(request.getParameter("itemId")); int uomId =
		 * Integer.parseInt(request.getParameter("uomId"));
		 * 
		 * float qty = Float.parseFloat(request.getParameter("qty"));
		 * 
		 * String itemName = request.getParameter("itemName"); String uomName =
		 * request.getParameter("uomName"); String itemRemark =
		 * request.getParameter("itemRemark");
		 * 
		 * TempEnqItem enqItem=new TempEnqItem();
		 * 
		 * enqItem.setItemId(itemId); enqItem.setItemName(itemName);
		 * enqItem.setUomId(uomId); enqItem.setUomName(uomName); enqItem.setEnqQty(qty);
		 * enqItem.setItemEnqRemark(itemRemark);
		 * 
		 * enqItemList.add(enqItem);
		 * 
		 * System.err.println("Ajax enqItem List size  " + enqItemList.size());
		 * 
		 * System.err.println("Ajax enqItem List " + enqItemList.toString());
		 */

		// new code

		int key = Integer.parseInt(request.getParameter("key"));

		int isEdit = Integer.parseInt(request.getParameter("isEdit"));
		try {

			if (isEdit == 1) {

				System.err.println("Is Edit ==1");

				int itemId = Integer.parseInt(request.getParameter("itemId"));
				int uomId = Integer.parseInt(request.getParameter("uomId"));

				float qty = Float.parseFloat(request.getParameter("qty"));

				String itemName = request.getParameter("itemName");
				String uomName = request.getParameter("uomName");
				String itemRemark = request.getParameter("itemRemark");

				TempEnqItem enqItem = new TempEnqItem();

				enqItem.setItemId(itemId);
				enqItem.setItemName(itemName);
				enqItem.setUomId(uomId);
				enqItem.setUomName(uomName);
				enqItem.setEnqQty(qty);
				enqItem.setItemEnqRemark(itemRemark);
				int itemUomId = Integer.parseInt(request.getParameter("itemUomId"));
				enqItem.setItemUomId(itemUomId);

				for (int i = 0; i < enqItemList.size(); i++) {
					System.err.println("i value " + i);

					if (enqItemList.get(i).getItemId() == itemId) {
						enqItemList.set(i, enqItem);
						System.err.println("called break");
						break;

					}
				}
			}

			else if (key == -1) {
				System.err.println("else if (key == -1)");
				System.err.println("Add Call enq");

				int itemId = Integer.parseInt(request.getParameter("itemId"));

				if (enqItemList.size() > 0) {
					int flag = 0;
					for (int i = 0; i < enqItemList.size(); i++) {
						enqItemList.get(i).setIsDuplicate(0);
						if (enqItemList.get(i).getItemId() == itemId) {
							enqItemList.get(i).setIsDuplicate(1);
							flag = 1;

						} // end of if item exist

					} // end of for
					if (flag == 0) {
						System.err.println("New Item added to existing list");

						int uomId = Integer.parseInt(request.getParameter("uomId"));

						float qty = Float.parseFloat(request.getParameter("qty"));

						String itemName = request.getParameter("itemName");
						String uomName = request.getParameter("uomName");
						String itemRemark = request.getParameter("itemRemark");

						TempEnqItem enqItem = new TempEnqItem();

						enqItem.setItemId(itemId);
						enqItem.setItemName(itemName);
						enqItem.setUomId(uomId);
						enqItem.setUomName(uomName);
						enqItem.setEnqQty(qty);
						enqItem.setItemEnqRemark(itemRemark);
						int itemUomId = Integer.parseInt(request.getParameter("itemUomId"));
						enqItem.setItemUomId(itemUomId);

						enqItemList.add(enqItem);
					}
				} // end of if tempIndentList.size>0

				else {

					System.err.println("New Item added first time : list is empty");

					int uomId = Integer.parseInt(request.getParameter("uomId"));

					float qty = Float.parseFloat(request.getParameter("qty"));

					String itemName = request.getParameter("itemName");
					String uomName = request.getParameter("uomName");
					String itemRemark = request.getParameter("itemRemark");

					TempEnqItem enqItem = new TempEnqItem();

					enqItem.setItemId(itemId);
					enqItem.setItemName(itemName);
					enqItem.setUomId(uomId);
					enqItem.setUomName(uomName);
					enqItem.setEnqQty(qty);
					enqItem.setItemEnqRemark(itemRemark);

					int itemUomId = Integer.parseInt(request.getParameter("itemUomId"));
					enqItem.setItemUomId(itemUomId);

					enqItemList.add(enqItem);

				} // else it is first item
			} // end of if key==-1

			else {
				System.err.println("remove call enq");
				enqItemList.remove(key);
			}
		} catch (Exception e) {
			System.err.println("Exce In addEnqItem  temp List " + e.getMessage());
			e.printStackTrace();

		}
		System.err.println(" enq Item List " + enqItemList.toString());
		// end of new code

		return enqItemList;

	}

	// getItemForEdit

	@RequestMapping(value = "/getItemForEdit", method = RequestMethod.GET)
	public @ResponseBody TempEnqItem getItemForEdit(HttpServletRequest request, HttpServletResponse response) {

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int index = Integer.parseInt(request.getParameter("index"));

		return enqItemList.get(index);

	}

	// insertEnq
	@RequestMapping(value = "/insertEnq", method = RequestMethod.POST)
	public String insertEnq(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertEnq method");

			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int custId = Integer.parseInt(request.getParameter("cust_name"));

			System.err.println("plantId Id " + plantId);

			String enqDate = request.getParameter("enq_date");
			String enqNo = request.getParameter("enq_no");
			String enqRemark = request.getParameter("enq_remark");

			int enqGenId = Integer.parseInt(request.getParameter("enq_gen_fact"));
			
			int enqPrio = Integer.parseInt(request.getParameter("enq_prio"));


			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			EnqHeader enqHead = new EnqHeader();

			DateFormat dateTimeFrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			enqHead.setCustId(custId);
			enqHead.setEnqDate(DateConvertor.convertToYMD(enqDate));
			enqHead.setEnqDateTime(dateTimeFrmt.format(cal.getTime()));
			enqHead.setEnqGenId(enqGenId);// to be filled from form select list enq_gen_fact table
			enqHead.setEnqHeadId(0);
			enqHead.setEnqHRemark(enqRemark);
			enqHead.setEnqNo(enqNo);
			enqHead.setEnqPriority(enqPrio);
			enqHead.setEnqStatus(0);
			enqHead.setEnqUsrId(0);
			enqHead.setEnqUsrId2(0);
			enqHead.setExDate1(curDate);
			enqHead.setExDate2(curDate);
			enqHead.setExVar1("NA");
			enqHead.setExVar2("NA");
			enqHead.setExVar3("NA");

			enqHead.setPlantId(plantId);
			enqHead.setQuotId(0);

			List<EnqDetail> enqDetList = new ArrayList<>();

			for (int i = 0; i < enqItemList.size(); i++) {

				EnqDetail eDetail = new EnqDetail();

				eDetail.setDelStatus(1);
				eDetail.setEnqDRemark(enqItemList.get(i).getItemEnqRemark());
				eDetail.setEnqUomId(enqItemList.get(i).getUomId());
				eDetail.setExDate1(curDate);
				eDetail.setExDate2(curDate);
				eDetail.setExVar1("NA");
				eDetail.setExVar2("NA");
				eDetail.setExVar3("NA");
				eDetail.setItemId(enqItemList.get(i).getItemId());
				eDetail.setItemQty(enqItemList.get(i).getEnqQty());
				eDetail.setItemUom(enqItemList.get(i).getUomName());
				eDetail.setItemUomId(enqItemList.get(i).getItemUomId());
				eDetail.setStatus(0);
				enqDetList.add(eDetail);

			}

			enqHead.setEnqDetailList(enqDetList);

			Info enqInsertRes = rest.postForObject(Constants.url + "saveEnqHeaderAndDetail", enqHead, Info.class);

			System.err.println("enqInsertRes " + enqInsertRes.toString());

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exce In insertEnq method  " + e.getMessage());
			e.printStackTrace();

		}
		return null;

	}

}
