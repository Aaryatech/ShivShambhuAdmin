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
import com.ats.ssgs.model.master.GetItem;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.PaymentTerm;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.model.master.User;
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

			model.addObject("title", "Quotation List");

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

	// editQuot

	@RequestMapping(value = "/editQuot/{quotId}/{plantId}/{custId}/{enqHeadId}", method = RequestMethod.GET)
	public ModelAndView editQuot(HttpServletRequest request, HttpServletResponse response, @PathVariable int quotId,
			@PathVariable int plantId,@PathVariable int custId,@PathVariable int enqHeadId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/editQuot");

			model.addObject("title", "Quotation Edit");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);
			map.add("enqHeadId", enqHeadId);

			GetItemWithEnq[] itemArray = rest.postForObject(Constants.url + "getItemsAndEnqItemList", map, GetItemWithEnq[].class);
			itemList = new ArrayList<GetItemWithEnq>(Arrays.asList(itemArray));
			model.addObject("itemList", itemList);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("plantId", plantId);
			Cust[] custArray = rest.postForObject(Constants.url + "getCustListByPlant", map, Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			model.addObject("custList", custList);

			System.err.println("cust List  " +custList.toString());

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));
			System.err.println("Plant List  " +plantList.toString());

			model.addObject("plantList", plantList);

			model.addObject("plantId", plantId);
			model.addObject("custId", custId);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("custId", custId);
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));
			
			model.addObject("projList", projList);
			
			PaymentTerm[] payTermArray = rest.getForObject(Constants.url + "getAllPaymentTermList",  PaymentTerm[].class);
			payTermList = new ArrayList<PaymentTerm>(Arrays.asList(payTermArray));
			
			model.addObject("payTermList", payTermList);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("docId", 2);

			DocTermHeader[] docTermArray = rest.postForObject(Constants.url + "getDocHeaderByDocId", map, DocTermHeader[].class);
			docTermList = new ArrayList<DocTermHeader>(Arrays.asList(docTermArray));

			model.addObject("docTermList",docTermList);
			
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("quotHeadId", quotId);
			
			QuotHeader quotHeader=rest.postForObject(Constants.url + "getQuotHeaderByQuotHeadId", map, QuotHeader.class);
			quotHeader.setQuotDate(DateConvertor.convertToDMY(quotHeader.getQuotDate()));
			model.addObject("quotHeader",quotHeader);
			
		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	// Ajax call
	@RequestMapping(value = "/getProjectByCustId", method = RequestMethod.GET)
	public @ResponseBody List<Project> getProjectByCustId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int custId = Integer.parseInt(request.getParameter("custId"));

		map.add("custId", custId);

		Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
		projList = new ArrayList<Project>(Arrays.asList(projArray));

		System.err.println("Ajax Proj  List " + itemList.toString());

		return projList;

	}
	
	// Ajax call
		@RequestMapping(value = "/getItemsAndEnqItemList", method = RequestMethod.GET)
		public @ResponseBody List<GetItemWithEnq> getItemsAndEnqItemList(HttpServletRequest request, HttpServletResponse response) {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			int plantId = Integer.parseInt(request.getParameter("plantId"));
			int enqHeadId = Integer.parseInt(request.getParameter("enqHeadId"));

			map.add("plantId", plantId);
			map.add("enqHeadId", enqHeadId);

			GetItemWithEnq[] itemArray = rest.postForObject(Constants.url + "getItemsAndEnqItemList", map, GetItemWithEnq[].class);
			itemList = new ArrayList<GetItemWithEnq>(Arrays.asList(itemArray));
			
			System.err.println("Ajax Item list for km onchange  List " + itemList.toString());

			return itemList;

		}
		
		//updateQuotation Form Action 
		
		@RequestMapping(value = "/updateQuotation", method = RequestMethod.POST)
		public ModelAndView updateQuotationProcess(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String curDate = dateFormat.format(new Date());

				model = new ModelAndView("quot/quotList");
				model.addObject("title", "Quotation List");
				
				int quotHeadId = Integer.parseInt(request.getParameter("quotHeadId"));
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("quotHeadId", quotHeadId);
				
				QuotHeader quotHeader=rest.postForObject(Constants.url + "getQuotHeaderByQuotHeadId", map, QuotHeader.class);
				
				int plantId = Integer.parseInt(request.getParameter("plant_id"));
				int payTermId = Integer.parseInt(request.getParameter("pay_term_id"));
				String transportTerms = request.getParameter("trans_term");
				String otherRemark1 = request.getParameter("quot_remark");
				int projId = Integer.parseInt(request.getParameter("proj_id"));

				int noOfTolls = Integer.parseInt(request.getParameter("no_of_tolls"));
				Float tollCost = Float.parseFloat(request.getParameter("toll_amt"));
				Float otherCost=Float.parseFloat(request.getParameter("other_cost"));
				int quotTermId = Integer.parseInt(request.getParameter("quot_doc_term_id"));
				int noOfKm = Integer.parseInt(request.getParameter("no_of_km"));
				
				String payTerms = request.getParameter("pay_term_name");

				quotHeader.setUserId(1);//to be get from session who logged in to do this activity
				
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
				
				List<QuotDetail> quotDetList=quotHeader.getQuotDetailList();
				
				System.err.println("Header  " +quotHeader.toString());
				String [] selectItem=request.getParameterValues("selectItem");
				
				for(int i=0;i<selectItem.length;i++) {
					
					System.err.println("selItem " +selectItem[i]);
					
					for(int j=0;j<quotDetList.size();j++) {
						
						if(quotDetList.get(j).getItemId()==Integer.parseInt(selectItem[i])) {
							
							
							
							
						}
						
					}
					
				}
						

				
			}catch (Exception e) {
				System.err.println("Exce in upd qtn process " +e.getMessage());
				e.printStackTrace();
				
			}
			return model;
		}
}
