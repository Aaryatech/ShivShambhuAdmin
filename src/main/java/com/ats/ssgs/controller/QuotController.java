package com.ats.ssgs.controller;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
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
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.DocTermHeader;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.PaymentTerm;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.order.GetOrder;
import com.ats.ssgs.model.quot.GetItemWithEnq;
import com.ats.ssgs.model.quot.GetQuotHeads;
import com.ats.ssgs.model.quot.QuotDetail;
import com.ats.ssgs.model.quot.QuotHeader;

@Controller
@Scope("session")
public class QuotController {

	RestTemplate rest = new RestTemplate();
	List<User> usrList;
	List<GetQuotHeads> quotList;

	List<Cust> custList;

	List<GetItemWithEnq> itemList;
	List<Project> projList;
	List<Plant> plantList;
	List<PaymentTerm> payTermList;
	List<DocTermHeader> docTermList;

	@RequestMapping(value = "/showQuotations", method = RequestMethod.GET)
	public ModelAndView showQuotations(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotList");

			model.addObject("title", "Generate Quotation");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("statusList", "0,1");

			GetQuotHeads[] quotArray = rest.postForObject(Constants.url + "getQuotHeaders", map, GetQuotHeads[].class);
			quotList = new ArrayList<GetQuotHeads>(Arrays.asList(quotArray));
			System.err.println("quotList" + quotList.toString());

			model.addObject("quotList", quotList);

		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showQuotations1", method = RequestMethod.GET)
	public ModelAndView showQuotations1(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotList");

			model.addObject("title", "Add Customer P.O.");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("statusList", "1");

			GetQuotHeads[] quotArray = rest.postForObject(Constants.url + "getQuotHeaders", map, GetQuotHeads[].class);
			quotList = new ArrayList<GetQuotHeads>(Arrays.asList(quotArray));
			System.err.println("quotList" + quotList.toString());

			model.addObject("quotList", quotList);

		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/showQuotationsCustWise", method = RequestMethod.GET)
	public ModelAndView showQuotationsCustWise(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotListNew");

			model.addObject("title", "Quotation List CustomerWise");
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			System.out.println("plant is" + plantList);

			model.addObject("plantList", plantList);

		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	List<GetQuotHeads> getQuotList = new ArrayList<>();

	@RequestMapping(value = "/getQuotListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetQuotHeads> getOrderListBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getTempQuotHeader");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		System.out.println("values are" + plantId + custId + fromDate + toDate);

		map.add("plantId", plantId);
		map.add("custId", custId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		// map.add("status", 0);

		GetQuotHeads[] ordHeadArray = rest.postForObject(Constants.url + "getQuotListByPlantIdAndCustId", map,
				GetQuotHeads[].class);
		getQuotList = new ArrayList<GetQuotHeads>(Arrays.asList(ordHeadArray));

		
		System.out.println("quot list data "+getQuotList.toString());
		
		return getQuotList;
	}

	// editQuot
	List<GetItemWithEnq> newItemList;// items that are not in quotation ie enquiry but in m_item table

	List<GetItemWithEnq> enqItemList;

	
	
	

	@RequestMapping(value = "/editQuotation/{quotHeadId}/plantId}/{custId}/{enqHeadId}", method = RequestMethod.GET)
	public ModelAndView editQuot(HttpServletRequest request, HttpServletResponse response, @PathVariable int quotHeadId,
			@PathVariable int plantId, @PathVariable int custId, @PathVariable int enqHeadId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/editQuot");

			model.addObject("title", "Quotation Edit");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("enqHeadId", enqHeadId);

			GetItemWithEnq[] itemArray = rest.postForObject(Constants.url + "getItemsAndEnqItemList", map,
					GetItemWithEnq[].class);
			itemList = new ArrayList<GetItemWithEnq>(Arrays.asList(itemArray));
			newItemList = new ArrayList<GetItemWithEnq>();
			enqItemList = new ArrayList<GetItemWithEnq>();
			System.err.println(" Original Item List " + itemList.toString());

			List<Integer> indexList = new ArrayList<>();
			for (int i = 0; i < itemList.size(); i++) {

				if (itemList.get(i).getQuotQty() == 0) {

					newItemList.add(itemList.get(i));
				} else {

					enqItemList.add(itemList.get(i));
				}

			}

			System.err.println("enqItemList " + enqItemList.toString());

			System.err.println("newItemList " + newItemList.toString());

			model.addObject("itemList", enqItemList);
			model.addObject("newItemList", newItemList);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("plantId", plantId);
			
			
			Cust[] custArray = rest.postForObject(Constants.url + "getCustListByPlant", map, Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			model.addObject("custList", custList);

			// System.err.println("cust List " + custList.toString());

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));
			model.addObject("plantList", plantList);
			
			// System.err.println("Plant List " + plantList.toString());

			

			model.addObject("plantId", plantId);
			model.addObject("custId", custId);
			/*model.addObject("custName", custName);
			model.addObject("plantName",plantName);*/
			map = new LinkedMultiValueMap<String, Object>();
			map.add("custId", custId);
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));

			model.addObject("projList", projList);

			PaymentTerm[] payTermArray = rest.getForObject(Constants.url + "getAllPaymentTermList",
					PaymentTerm[].class);
			payTermList = new ArrayList<PaymentTerm>(Arrays.asList(payTermArray));

			model.addObject("payTermList", payTermList);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("docId", 2);

			DocTermHeader[] docTermArray = rest.postForObject(Constants.url + "getDocHeaderByDocId", map,
					DocTermHeader[].class);
			docTermList = new ArrayList<DocTermHeader>(Arrays.asList(docTermArray));

			model.addObject("docTermList", docTermList);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("quotHeadId", quotHeadId);

			QuotHeader quotHeader = rest.postForObject(Constants.url + "getQuotHeaderByQuotHeadId", map,
					QuotHeader.class);
			quotHeader.setQuotDate(DateConvertor.convertToDMY(quotHeader.getQuotDate()));
			model.addObject("quotHeader", quotHeader);

		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}
	// getNewItemsForQuotation

	@RequestMapping(value = "/getNewItemsForQuotation", method = RequestMethod.GET)
	public @ResponseBody List<GetItemWithEnq> getNewItemsForQuotation(HttpServletRequest request,
			HttpServletResponse response) {

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		float quotQty = Float.parseFloat(request.getParameter("quotQty"));

		int isDelete = Integer.parseInt(request.getParameter("isDelete"));
		int index = Integer.parseInt(request.getParameter("index"));

		int quotHeaderId = Integer.parseInt(request.getParameter("quotHeaderId"));

		if (isDelete == 0) {
			for (int i = 0; i < newItemList.size(); i++) {
				if (!enqItemList.contains(newItemList.get(i))) {

					if (newItemList.get(i).getItemId() == itemId) {
						System.err.println("Item Id matched");

						newItemList.get(i).setEnqUomId(newItemList.get(i).getUomId());
						newItemList.get(i).setEnqUomName(newItemList.get(i).getUomName());
						newItemList.get(i).setQuotQty(quotQty);
						newItemList.get(0).setTempMsg("Item Added Successfully");
						enqItemList.add(newItemList.get(i));

						System.err.println("Newly added item in quot " + newItemList.get(i).toString());
						// newItemList.remove(i);
						break;
					}
				} else {

					System.err.println("Already added " + itemId);

				}
			}
		} // end of if isDelete=0
		else {
			System.err.println("IS delete ==1");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotHeadId", quotHeaderId);
			map.add("itemId", itemId);

			Info errMsg = rest.postForObject(Constants.url + "deleteQuotDetail", map, Info.class);
			enqItemList.get(0).setTempMsg("Item Deleted Successfully");

			enqItemList.remove(index);
		}
		System.err.println("Ajax getNewItemsForQuotation  List size " + enqItemList.size());

		System.err.println("Ajax getNewItemsForQuotation  List " + enqItemList.toString());

		return enqItemList;

	}

	// Ajax call
	@RequestMapping(value = "/getProjectByCustId", method = RequestMethod.GET)
	public @ResponseBody List<Project> getProjectByCustId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int custId = Integer.parseInt(request.getParameter("custId"));

		map.add("custId", custId);

		Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
		projList = new ArrayList<Project>(Arrays.asList(projArray));

		System.err.println("Ajax Proj  List " + projList.toString());

		return projList;

	}

	// Ajax call
	@RequestMapping(value = "/getItemsAndEnqItemList", method = RequestMethod.GET)
	public @ResponseBody List<GetItemWithEnq> getItemsAndEnqItemList(HttpServletRequest request,
			HttpServletResponse response) {

		/*
		 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
		 * Object>();
		 * 
		 * int plantId = Integer.parseInt(request.getParameter("plantId")); int
		 * enqHeadId = Integer.parseInt(request.getParameter("enqHeadId"));
		 * 
		 * map.add("plantId", plantId); map.add("enqHeadId", enqHeadId);
		 * 
		 * GetItemWithEnq[] itemArray = rest.postForObject(Constants.url +
		 * "getItemsAndEnqItemList", map, GetItemWithEnq[].class); itemList = new
		 * ArrayList<GetItemWithEnq>(Arrays.asList(itemArray));
		 * 
		 * System.err.println("Ajax Item list for km onchange  List " +
		 * itemList.toString());
		 */
		return enqItemList;

	}

	@RequestMapping(value = "/getDocTermDetail", method = RequestMethod.GET)
	public @ResponseBody DocTermHeader getDocTermDetail(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int termId = Integer.parseInt(request.getParameter("termId"));

		map.add("termId", termId);
		DocTermHeader docTerm = rest.postForObject(Constants.url + "getDocHeaderByTermId", map, DocTermHeader.class);

		System.err.println("Ajax getDocTermDetail " + docTerm.toString());

		return docTerm;

	}

	// updateQuotation Form Action

	@RequestMapping(value = "/updateQuotation", method = RequestMethod.POST)
	public String updateQuotationProcess(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());

			model = new ModelAndView("quot/quotList");
			model.addObject("title", "Quotation List");

			int quotHeadId = Integer.parseInt(request.getParameter("quotHeadId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotHeadId", quotHeadId);

			QuotHeader quotHeader = rest.postForObject(Constants.url + "getQuotHeaderByQuotHeadId", map,
					QuotHeader.class);
			int custId = Integer.parseInt(request.getParameter("cust_name"));
			map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", custId);

			Cust cust = rest.postForObject(Constants.url + "getCustByCustId", map, Cust.class);

			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int payTermId = Integer.parseInt(request.getParameter("pay_term_id"));
			String transportTerms = request.getParameter("trans_term");
			String otherRemark1 = request.getParameter("quot_remark");
			int projId = Integer.parseInt(request.getParameter("proj_id"));

			int noOfTolls = Integer.parseInt(request.getParameter("no_of_tolls"));
			int is_tax_inc = Integer.parseInt(request.getParameter("is_tax_inc"));
			Float tollCost = Float.parseFloat(request.getParameter("toll_amt"));
			Float otherCost = Float.parseFloat(request.getParameter("other_cost"));
			int quotTermId = Integer.parseInt(request.getParameter("quot_doc_term_id"));
			float noOfKm = Float.parseFloat(request.getParameter("no_of_km"));

			String payTerms = request.getParameter("pay_term_name");

			quotHeader.setUserId(1);// to be get from session who logged in to do this activity

			quotHeader.setStatus(1);

			quotHeader.setPayTermId(payTermId);
			quotHeader.setTransportTerms(transportTerms);

			quotHeader.setPayTerms(payTerms);
			quotHeader.setOtherRemark1(otherRemark1);
			quotHeader.setProjId(projId);
			quotHeader.setNoOfTolls(noOfTolls);
			quotHeader.setTollCost(tollCost);
			quotHeader.setOtherCost(otherCost);
			quotHeader.setQuotTermId(quotTermId);
			quotHeader.setNoOfKm(noOfKm);
			quotHeader.setExDate2(curDate);
			quotHeader.setTaxValue(is_tax_inc);
			// quotHeader.setCompanyId(companyId);

			List<QuotDetail> quotDetList = quotHeader.getQuotDetailList();

			System.err.println("Header  " + quotHeader.toString());
			String[] selectItem = request.getParameterValues("selectItem");

			List<QuotDetail> tempQDetailList = new ArrayList<>();

			//
			int flag;

			for (int j = 0; j < enqItemList.size(); j++) {

				flag = 0;
				for (int i = 0; i < quotDetList.size(); i++) {

					if (enqItemList.get(j).getItemId() == quotDetList.get(i).getItemId()) {

						flag = 1;

						float quotQty = Float
								.parseFloat(request.getParameter("quot_qty" + quotDetList.get(i).getItemId()));
						float transCost = Float
								.parseFloat(request.getParameter("trans_cost" + quotDetList.get(i).getItemId()));
						float otherCostDetail = Float
								.parseFloat(request.getParameter("other_cost" + quotDetList.get(i).getItemId()));
						float taxableValue = Float
								.parseFloat(request.getParameter("taxable_amt" + quotDetList.get(i).getItemId()));
						float taxValue = Float
								.parseFloat(request.getParameter("tax_amt" + quotDetList.get(i).getItemId()));
						float total = Float
								.parseFloat(request.getParameter("final_amt" + quotDetList.get(i).getItemId()));
						float otherCostAfterTax = Float
								.parseFloat(request.getParameter("oth_cost_aft_tax" + quotDetList.get(i).getItemId()));

						QuotDetail detail;

						detail = quotDetList.get(i);

						detail.setQuotDetailId(quotDetList.get(i).getQuotDetailId());
						detail.setQuotHeadId(quotDetList.get(i).getQuotHeadId());
						detail.setItemId(quotDetList.get(i).getItemId());
						detail.setQuotQty(quotQty);
						detail.setRate(enqItemList.get(j).getItemRate1());
						detail.setTaxableValue(taxableValue);
						detail.setTaxValue(taxValue);
						detail.setTaxId(enqItemList.get(j).getTaxId());
						// detail.setCgstPer(enqItemList.get(j).getCgst());
						// detail.setSgstPer(enqItemList.get(j).getSgst());
						// detail.setCgstValue(cgstValue);
						// detail.setSgstValue(sgstValue);

						detail.setStatus(1);

						detail.setEnqDetailId(quotDetList.get(i).getEnqDetailId());
						detail.setDelStatus(1);
						detail.setOtherCost(otherCostDetail);
						// exint1,2,3
						detail.setExVar1("Na");
						detail.setExVar2("Na");
						detail.setExVar3("Na");
						detail.setExDate1(curDate);
						detail.setExDate2(curDate);

						// exBool1,2,3
						detail.setConFactor(1);
						detail.setConvQty(1);
						detail.setQuotUomId((int) enqItemList.get(j).getEnqUomId());

						// detail.setIgstPer(enqItemList.get(j).getIgst());
						// detail.setIgstValue(igstValue);

						detail.setTotal(total);
						detail.setTollCost(tollCost);
						detail.setTransCost(transCost);
						detail.setOtherCostBeforeTax(0);
						detail.setOtherCostAfterTax(otherCostAfterTax);

						detail.setRoyaltyRate(enqItemList.get(j).getRoyaltyRate());
						detail.setNoOfKm(noOfKm);

						if (taxValue > 0) {
							if (cust.getIsSameState() == 1) {

								detail.setCgstPer(enqItemList.get(j).getCgst());
								detail.setSgstPer(enqItemList.get(j).getSgst());

								detail.setCgstValue((taxValue / 2));
								detail.setSgstValue((taxValue / 2));

								detail.setIgstValue(0);
								// detail.setIgstPer(0);
							} else {
								detail.setIgstValue(taxValue);
								detail.setIgstPer(enqItemList.get(j).getIgst());

								detail.setCgstValue(0);
								detail.setSgstValue(0);

								// detail.setCgstPer(0);
								// detail.setSgstPer(0);

							}

						} else {

							if (cust.getIsSameState() == 1) {

								detail.setCgstPer(enqItemList.get(j).getCgst());
								detail.setSgstPer(enqItemList.get(j).getSgst());

								detail.setCgstValue(0);
								detail.setSgstValue(0);

								detail.setIgstValue(0);
								// detail.setIgstPer(0);
							} else {
								detail.setIgstValue(taxValue);
								detail.setIgstPer(enqItemList.get(j).getIgst());

								detail.setCgstValue(0);
								detail.setSgstValue(0);
								detail.setIgstValue(0);
								// detail.setCgstPer(0);
								// detail.setSgstPer(0);

							}

						}

						tempQDetailList.add(detail);

					} // end of if

				} // end of inner for

				if (flag == 0) {
					float quotQty = Float.parseFloat(request.getParameter("quot_qty" + enqItemList.get(j).getItemId()));
					float transCost = Float
							.parseFloat(request.getParameter("trans_cost" + enqItemList.get(j).getItemId()));
					float otherCostDetail = Float
							.parseFloat(request.getParameter("other_cost" + enqItemList.get(j).getItemId()));
					float taxableValue = Float
							.parseFloat(request.getParameter("taxable_amt" + enqItemList.get(j).getItemId()));
					float taxValue = Float.parseFloat(request.getParameter("tax_amt" + enqItemList.get(j).getItemId()));
					float total = Float.parseFloat(request.getParameter("final_amt" + enqItemList.get(j).getItemId()));
					float otherCostAfterTax = Float
							.parseFloat(request.getParameter("oth_cost_aft_tax" + enqItemList.get(j).getItemId()));

					QuotDetail detail = new QuotDetail();

					detail.setQuotQty(quotQty);
					detail.setTransCost(transCost);
					detail.setOtherCost(otherCostDetail);
					detail.setTaxableValue(taxableValue);
					detail.setTaxValue(taxValue);
					detail.setTotal(total);
					detail.setOtherCostAfterTax(otherCostAfterTax);
					detail.setTollCost(tollCost);

					detail.setNoOfKm(noOfKm);
					detail.setRate(enqItemList.get(j).getItemRate1());

					detail.setExDate2(curDate);

					detail.setStatus(1);

					if (taxValue > 0) {
						if (cust.getIsSameState() == 1) {

							detail.setCgstPer(enqItemList.get(j).getCgst());
							detail.setSgstPer(enqItemList.get(j).getSgst());

							detail.setCgstValue((taxValue / 2));
							detail.setSgstValue((taxValue / 2));

							detail.setIgstValue(0);
							// detail.setIgstPer(0);
						} else {
							detail.setIgstValue(taxValue);
							detail.setIgstPer(enqItemList.get(j).getIgst());

							detail.setCgstValue(0);
							detail.setSgstValue(0);

							// detail.setCgstPer(0);
							// detail.setSgstPer(0);

						}

					} else {

						if (cust.getIsSameState() == 1) {

							detail.setCgstPer(enqItemList.get(j).getCgst());
							detail.setSgstPer(enqItemList.get(j).getSgst());

							detail.setCgstValue(0);
							detail.setSgstValue(0);

							detail.setIgstValue(0);
							// detail.setIgstPer(0);
						} else {
							detail.setIgstValue(taxValue);
							detail.setIgstPer(enqItemList.get(j).getIgst());

							detail.setCgstValue(0);
							detail.setSgstValue(0);
							detail.setIgstValue(0);
							// detail.setCgstPer(0);
							// detail.setSgstPer(0);

						}

					}

					detail.setCgstPer(enqItemList.get(j).getCgst());
					detail.setSgstPer(enqItemList.get(j).getSgst());

					detail.setConFactor(1);
					detail.setDelStatus(1);
					detail.setConvQty(1);
					detail.setIgstPer(enqItemList.get(j).getIgst());
					detail.setItemId(enqItemList.get(j).getItemId());
					detail.setQuotUomId(enqItemList.get(j).getUomId());

					detail.setRoyaltyRate(enqItemList.get(j).getRoyaltyRate());
					detail.setStatus(1);
					detail.setTaxId(enqItemList.get(j).getTaxId());
					detail.setOtherCostBeforeTax(0);

					detail.setExVar1("na");
					detail.setExVar2("na");
					detail.setExVar3("na");

					detail.setExDate1(curDate);
					detail.setExDate2(curDate);

					tempQDetailList.add(detail);

				} // end of if flag=0
			} // end of outer for

			quotHeader.setQuotDetailList(tempQDetailList);

			QuotHeader quotHeadUpdateRes = rest.postForObject(Constants.url + "saveQuotHeaderAndDetail", quotHeader,
					QuotHeader.class);

			System.err.println("quotHeadUpdateRes  " + quotHeadUpdateRes.toString());

			// new COde

			/*
			 * for (int i = 0; i < selectItem.length; i++) {
			 * 
			 * System.err.println("selItem " + selectItem[i]);
			 * 
			 * for (int j = 0; j < itemList.size(); j++) {
			 * 
			 * if (itemList.get(j).getItemId() == Integer.parseInt(selectItem[i])) {
			 * 
			 * System.err.println("Item Ids Matched ");
			 * 
			 * QuotDetail detail = new QuotDetail();
			 * 
			 * float quotQty = Float .parseFloat(request.getParameter("quot_qty" +
			 * itemList.get(j).getItemId())); float transCost = Float
			 * .parseFloat(request.getParameter("trans_cost" +
			 * itemList.get(j).getItemId())); float otherCostDetail = Float
			 * .parseFloat(request.getParameter("other_cost" +
			 * itemList.get(j).getItemId())); float taxableValue = Float
			 * .parseFloat(request.getParameter("taxable_amt" +
			 * itemList.get(j).getItemId())); float taxValue = Float
			 * .parseFloat(request.getParameter("tax_amt" + itemList.get(j).getItemId()));
			 * float total = Float.parseFloat(request.getParameter("final_amt" +
			 * itemList.get(j).getItemId())); float otherCostAfterTax = Float
			 * .parseFloat(request.getParameter("oth_cost_aft_tax" +
			 * itemList.get(j).getItemId()));
			 * 
			 * detail.setCgstPer(itemList.get(j).getCgst()); detail.setConFactor(1);
			 * detail.setDelStatus(1); detail.setConvQty(1);
			 * detail.setIgstPer(itemList.get(j).getIgst());
			 * detail.setItemId(itemList.get(j).getItemId()); detail.setNoOfKm(noOfKm);
			 * detail.setOtherCost(otherCostDetail);
			 * detail.setOtherCostAfterTax(otherCostAfterTax); detail.setQuotQty(quotQty);
			 * detail.setQuotUomId(itemList.get(j).getUomId()); detail.setRate(total);
			 * detail.setRoyaltyRate(itemList.get(j).getRoyaltyRate());
			 * detail.setSgstPer(itemList.get(j).getSgst()); detail.setStatus(1);
			 * detail.setTaxableValue(taxableValue);
			 * detail.setTaxId(itemList.get(j).getTaxId()); detail.setTaxValue(taxValue);
			 * detail.setTollCost(tollCost); detail.setTotal(total);
			 * detail.setTransCost(transCost); detail.setExDate2(curDate);
			 * detail.setOtherCostBeforeTax(0);
			 * 
			 * if (taxValue > 0) { if(cust.getIsSameState()==1) {
			 * 
			 * detail.setCgstValue((taxValue / 2)); detail.setIgstValue(0);
			 * detail.setIgstPer(0); detail.setSgstValue((taxValue / 2)); }else {
			 * detail.setIgstValue(taxValue); detail.setIgstPer(itemList.get(i).getIgst());
			 * 
			 * detail.setCgstValue(0); detail.setSgstValue(0);
			 * 
			 * detail.setCgstPer(0); detail.setSgstPer(0);
			 * 
			 * } quotHeader.setTaxValue(1);
			 * 
			 * } else { detail.setCgstValue(0); detail.setIgstValue(0);
			 * detail.setSgstValue(0);
			 * 
			 * detail.setCgstPer(0); detail.setSgstPer(0); detail.setIgstPer(0);
			 * 
			 * 
			 * }
			 * 
			 * tempQDetailList.add(detail);
			 * 
			 * } // End of If
			 * 
			 * } // End of Item List For
			 * 
			 * } // End of Selected Item For Loop
			 * 
			 * for (int i = 0; i < tempQDetailList.size(); i++) {
			 * 
			 * int flag = 0;
			 * 
			 * for (int j = 0; j < quotDetList.size(); j++) { if
			 * (tempQDetailList.get(i).getItemId() == quotDetList.get(j).getItemId()) {
			 * 
			 * flag = 1; quotDetList.get(j).setCgstPer(tempQDetailList.get(i).getCgstPer());
			 * quotDetList.get(j).setCgstValue(tempQDetailList.get(i).getCgstValue());
			 * quotDetList.get(j).setConFactor(tempQDetailList.get(i).getConFactor());
			 * quotDetList.get(j).setConvQty(tempQDetailList.get(i).getConvQty());
			 * quotDetList.get(j).setDelStatus(tempQDetailList.get(i).getDelStatus()); //
			 * quotDetList.get(j).setEnqDetailId(tempQDetailList.get(i).get);
			 * quotDetList.get(j).setExDate2(tempQDetailList.get(i).getExDate2());
			 * quotDetList.get(j).setIgstPer(tempQDetailList.get(i).getIgstPer());
			 * quotDetList.get(j).setIgstValue(tempQDetailList.get(i).getIgstValue()); //
			 * quotDetList.get(j).setItemId(itemId);
			 * quotDetList.get(j).setNoOfKm(tempQDetailList.get(i).getNoOfKm());
			 * quotDetList.get(j).setOtherCost(tempQDetailList.get(i).getOtherCost());
			 * quotDetList.get(j).setOtherCostAfterTax(tempQDetailList.get(i).
			 * getOtherCostAfterTax()); // quotDetList.get(j).setQuotDetailId(quotDetailId);
			 * // quotDetList.get(j).setQuotHeadId(quotHeadId);
			 * quotDetList.get(j).setQuotQty(tempQDetailList.get(i).getQuotQty());
			 * quotDetList.get(j).setQuotUomId(tempQDetailList.get(i).getQuotUomId());
			 * quotDetList.get(j).setRate(tempQDetailList.get(i).getTotal());
			 * quotDetList.get(j).setRoyaltyRate(tempQDetailList.get(i).getRoyaltyRate());
			 * quotDetList.get(j).setSgstPer(tempQDetailList.get(i).getSgstPer());
			 * quotDetList.get(j).setSgstValue(tempQDetailList.get(i).getSgstValue());
			 * quotDetList.get(j).setStatus(tempQDetailList.get(i).getStatus());
			 * quotDetList.get(j).setTaxableValue(tempQDetailList.get(i).getTaxableValue());
			 * quotDetList.get(j).setTaxId(tempQDetailList.get(i).getTaxId());
			 * quotDetList.get(j).setTaxValue(tempQDetailList.get(i).getTaxValue());
			 * 
			 * quotDetList.get(j).setTollCost(tempQDetailList.get(i).getTollCost());
			 * quotDetList.get(j).setTotal(tempQDetailList.get(i).getTotal());
			 * quotDetList.get(j).setTransCost(tempQDetailList.get(i).getTransCost());
			 * 
			 * }
			 * 
			 * } if (flag == 0) {
			 * 
			 * System.err.println("inside flag==0"); tempQDetailList.get(i).setExVar1("NA");
			 * tempQDetailList.get(i).setExVar2("NA");
			 * tempQDetailList.get(i).setExVar3("NA");
			 * tempQDetailList.get(i).setExDate1(curDate);
			 * tempQDetailList.get(i).setExDate2(curDate);
			 * tempQDetailList.get(i).setQuotHeadId(quotHeadId);
			 * quotDetList.add(tempQDetailList.get(i)); }
			 * 
			 * }
			 */

		} catch (Exception e) {
			System.err.println("Exce in upd qtn process " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showQuotations";
	}

	// Deletall done by harsha

	@RequestMapping(value = "/deleteRecordofQuatation", method = RequestMethod.POST)
	public String deleteRecordofCompany(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] quotIds = request.getParameterValues("quotIds");
			System.out.println("id are" + quotIds);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < quotIds.length; i++) {
				sb = sb.append(quotIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiQuot", map, Info.class);

			System.err.println("inside method");
		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofQuota @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showQuotations";
	}

	@RequestMapping(value = "/deleteRecordofQuotations", method = RequestMethod.POST)
	public String deleteRecordofQuotations(HttpServletRequest request, HttpServletResponse response) {
		try {

			System.out.println("Hello            oooooooooooo");
			String[] quotIds = request.getParameterValues("selectQuatationToDelete");
			System.out.println("id are" + quotIds);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < quotIds.length; i++) {
				sb = sb.append(quotIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			System.err.println("quotIds" + items.toString());

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiQuot", map, Info.class);
			System.err.println("inside method /deleteRecordofQuotations");
		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofQuotations @OrderController  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showQuotationsCustWise";
	}

}
