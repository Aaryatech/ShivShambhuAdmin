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
import com.ats.ssgs.model.quot.GetQuotHeads;
import com.ats.ssgs.model.quot.QuotHeader;

@Controller
@Scope("session")
public class QuotController {

	RestTemplate rest = new RestTemplate();
	List<User> usrList;
	List<GetQuotHeads> quotList;

	List<Cust> custList;

	List<GetItem> itemList;
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

	@RequestMapping(value = "/editQuot/{quotId}/{plantId}/{custId}", method = RequestMethod.GET)
	public ModelAndView editQuot(HttpServletRequest request, HttpServletResponse response, @PathVariable int quotId,
			@PathVariable int plantId,@PathVariable int custId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/editQuot");

			model.addObject("title", "Quotation Edit");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("statusList", "0,1");

			map = new LinkedMultiValueMap<String, Object>();
			map.add("plantId", plantId);

			GetItem[] itemArray = rest.postForObject(Constants.url + "getGetItemsByPlantId", map, GetItem[].class);
			itemList = new ArrayList<GetItem>(Arrays.asList(itemArray));
			model.addObject("itemList", itemList);


			Cust[] custArray = rest.postForObject(Constants.url + "getCustListByPlant", map, Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			model.addObject("custList", custList);

			System.err.println("cust List  " +custList.toString());

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			model.addObject("plantId", plantId);
			model.addObject("custId", custId);
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
			//getQuotHeaderByQuotHeadId
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

}
