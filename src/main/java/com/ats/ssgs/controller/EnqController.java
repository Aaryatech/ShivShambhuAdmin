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
import org.springframework.expression.spel.ast.QualifiedIdentifier;
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
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.GetCust;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.model.quot.QuotDetail;
import com.ats.ssgs.model.quot.QuotHeader;

@Controller
@Scope("session")
public class EnqController {

	List<Plant> plantList;

	List<Cust> custList;

	List<Uom> uomList;

	List<Item> itemList;

	RestTemplate rest = new RestTemplate();

	List<EnqGenFact> enqGenFactList;

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
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 1);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			model.addObject("doc", doc);

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

	@RequestMapping(value = "/getCustInfoByCustId", method = RequestMethod.GET)
	public @ResponseBody GetCust getCustInfoByCustId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int custId = Integer.parseInt(request.getParameter("custId"));

		System.out.println("custId" + custId);

		map.add("custId", custId);

		GetCust cust = rest.postForObject(Constants.url + "getCustomerByCustId", map, GetCust.class);

		System.err.println("Ajax Customer  " + cust.toString());

		return cust;

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
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 1);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);

			enqHead.setEnqNo(doc.getDocPrefix() + "" + doc.getSrNo());
			enqHead.setEnqDetailList(enqDetList);

			EnqHeader enqInsertRes = rest.postForObject(Constants.url + "saveEnqHeaderAndDetail", enqHead,
					EnqHeader.class);

			if (enqInsertRes != null) {

				map = new LinkedMultiValueMap<String, Object>();

				map.add("srNo", doc.getSrNo() + 1);
				map.add("docCode", doc.getDocCode());

				Info updateDocSr = rest.postForObject(Constants.url + "updateDocSrNo", map, Info.class);

			}

			QuotHeader quotHeader = new QuotHeader();

			List<QuotDetail> quotDetailList = new ArrayList<>();

			quotHeader.setCompanyId(0);

			quotHeader.setCustId(enqInsertRes.getCustId());

			quotHeader.setDelStatus(1);
			quotHeader.setEnqHeadId(enqInsertRes.getEnqHeadId());
			quotHeader.setExDate1(curDate);
			quotHeader.setExDate2(curDate);
			quotHeader.setExVar1("NA");
			quotHeader.setExVar2("NA");
			quotHeader.setExVar3("NA");
			quotHeader.setNoOfTolls(0);
			quotHeader.setOtherCost(0);
			quotHeader.setOtherRemark1("NA");
			quotHeader.setOtherRemark3("NA");
			quotHeader.setOtherRemark2("NA");
			quotHeader.setOtherRemark4("NA");
			quotHeader.setOtherValueAfterTax1(0);
			quotHeader.setOtherValueAfterTax2(0);
			quotHeader.setPayTermId(0);
			quotHeader.setPayTerms("NA");
			quotHeader.setPlantIds(enqInsertRes.getPlantId());
			quotHeader.setProjId(0);
			quotHeader.setQuotDate(curDate);
			quotHeader.setUserId(1);
			

			map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 2);
			Document docs = rest.postForObject(Constants.url + "getDocument", map, Document.class);

			quotHeader.setQuotNo(docs.getDocPrefix() + "" + docs.getSrNo());

			quotHeader.setQuotTaxableAmt(0);
			quotHeader.setQuotTaxAmt(0);
			quotHeader.setQuotTermId(0);
			quotHeader.setQuotTotal(0);
			quotHeader.setQuotValue(0);
			quotHeader.setStatus(0);
			quotHeader.setTaxableValue(0);

			quotHeader.setTaxValue(0);
			quotHeader.setTollCost(0);
			quotHeader.setTransportCost(0);

			quotHeader.setTransportTermId(0);
			quotHeader.setTransportTerms("NA");

			quotHeader.setUserId(0);// get from session

			for (int i = 0; i < enqInsertRes.getEnqDetailList().size(); i++) {

				QuotDetail qDetail = new QuotDetail();
				qDetail.setCgstValue(0);
				qDetail.setConFactor(0);
				qDetail.setConvQty(0);
				qDetail.setDelStatus(1);
				qDetail.setEnqDetailId(enqInsertRes.getEnqDetailList().get(i).getEnqDetailId());
				qDetail.setExDate1(curDate);
				qDetail.setExDate2(curDate);
				qDetail.setExVar1("NA");
				qDetail.setExVar2("NA");
				qDetail.setExVar3("NA");
				qDetail.setIgstPer(0);
				qDetail.setIgstValue(0);
				qDetail.setItemId(enqInsertRes.getEnqDetailList().get(i).getItemId());
				qDetail.setOtherCost(0);
				qDetail.setQuotUomId(0);
				qDetail.setQuotQty(enqInsertRes.getEnqDetailList().get(i).getItemQty());
				qDetail.setRate(0);
				qDetail.setSgstValue(0);
				qDetail.setStatus(0);
				qDetail.setTaxableValue(0);
				qDetail.setTaxId(0);
				qDetail.setTaxValue(0);

				qDetail.setSgstPer(0);
				qDetail.setCgstPer(0);

				quotDetailList.add(qDetail);

			}

			quotHeader.setQuotDetailList(quotDetailList);

			System.err.println("enqInsertRes " + enqInsertRes.toString());

			QuotHeader quotHeadInsertRes = rest.postForObject(Constants.url + "saveQuotHeaderAndDetail", quotHeader,
					QuotHeader.class);

			System.err.println("quotHeadInsertRes  " + quotHeadInsertRes.toString());

			if (quotHeadInsertRes != null) {

				map = new LinkedMultiValueMap<String, Object>();

				map.add("srNo", docs.getSrNo() + 1);
				map.add("docCode", docs.getDocCode());

				Info updateDocSr = rest.postForObject(Constants.url + "updateDocSrNo", map, Info.class);

			}

		} catch (Exception e) {

			System.err.println("Exce In insertEnq method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showAddEnquiry";

	}

}
