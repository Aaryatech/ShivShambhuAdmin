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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.rmc.GetRmcOrders;

@Controller
@Scope("session")
public class RmcController {

	RestTemplate rest = new RestTemplate();
	String fromDate, toDate;
	List<GetRmcOrders> rmcOrdList;
	
	List<Plant> plantList;

	@RequestMapping(value = "/showRmcOrdList", method = RequestMethod.GET)
	public ModelAndView showrmcOrdList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			// String fromDate,toDate;
			int plantId = 67;
			if (request.getParameter("from_date") == null || request.getParameter("to_date") == null) {
				Date date = new Date();
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				fromDate = df.format(date);
				toDate = df.format(date);
				System.out.println("From Date And :" + fromDate + "ToDATE" + toDate);

				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));

				System.out.println("inside if ");
			} else {
				fromDate = request.getParameter("from_date");
				toDate = request.getParameter("to_date");
				plantId = Integer.parseInt(request.getParameter("plantId"));
				System.out.println("inside Else ");

				System.out.println("fromDate " + fromDate);

				System.out.println("toDate " + toDate);

				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));

			}
			map.add("plantId", plantId);
			model = new ModelAndView("rmc/show_rmc_ord");
			GetRmcOrders[] rmcOrArray = rest.postForObject(Constants.url + "/getRmcOrdList", map, GetRmcOrders[].class);

			rmcOrdList = new ArrayList<GetRmcOrders>(Arrays.asList(rmcOrArray));

			System.out.println("rmcOrdList List using /showBOMReqests   " + rmcOrdList.toString());
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			model.addObject("rmcOrdList", rmcOrdList);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("Exception in showBOMReqests BomContrller" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

}
