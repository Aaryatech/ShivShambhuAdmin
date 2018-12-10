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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.prodrm.GetProdPlanHeader;
import com.ats.ssgs.model.prodrm.ProdReportDetail;
import com.ats.ssgs.model.prodrm.ProdReportHeader;


@Controller
@Scope("session")
public class ProdReportController {
	
	
	RestTemplate rest = new RestTemplate();
	DateFormat dateFormatdd = new SimpleDateFormat("dd-MM-yyyy");

	DateFormat dateFormatyy = new SimpleDateFormat("yyyy-MM-dd");

	List<Plant> plantList;
	int isError = 0;
	@RequestMapping(value = "/showProdHeadReport", method = RequestMethod.GET)
	public ModelAndView showProdHeadReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("prod/prod_head_report");

			model.addObject("title", "Production Report Between Date");
			
			model.addObject("isError", isError);
			isError = 0;

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			String fromDate = null, toDate = null;

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			fromDate = dateFormat.format(firstDate);

			toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {
			System.err.println("Exce in showing /showProdPlanList  " + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	
	//getProdHeadReport
	
	
	
	List<ProdReportHeader> prodHeadReport;

	@RequestMapping(value = "/getProdHeadReport", method = RequestMethod.GET)
	public @ResponseBody List<ProdReportHeader> getProdHeadReport(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getProdHeadersBetDate");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			int plantId = Integer.parseInt(request.getParameter("plantId"));

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			map.add("plantId", plantId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			ProdReportHeader[] prodHeadArray = rest.postForObject(Constants.url + "getProdReportHeader", map,
					ProdReportHeader[].class);
			prodHeadReport = new ArrayList<ProdReportHeader>(Arrays.asList(prodHeadArray));


		} catch (Exception e) {
			System.err.println("Exce in Ajax /getProdHeadReport  " + e.getMessage());
			e.printStackTrace();
		}
		return prodHeadReport;
	}
	
	List<ProdReportDetail> prodReportDetList;
	@RequestMapping(value = "/getProdReportDetail", method = RequestMethod.POST)
	public ModelAndView getProdReportDetail(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			prodReportDetList = new ArrayList<>();

			int itemId = Integer.parseInt(request.getParameter("itemId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("itemId", itemId);
			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			
			//System.err.println("From Date " +fromDate);
			
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("plantId", plantId);
			
			ProdReportDetail[] prodHeadArray = rest.postForObject(Constants.url + "getProdReportDetail", map,
					ProdReportDetail[].class);
			prodReportDetList = new ArrayList<ProdReportDetail>(Arrays.asList(prodHeadArray));

			model = new ModelAndView("prod/prod_detail_report");
			model.addObject("title", "Production Report Detail");
			model.addObject("prodReportDetList", prodReportDetList);

		} catch (Exception e) {
			System.err.println("Exce in view Prod Detail Report /getProdReportDetail  " + e.getMessage());
			e.printStackTrace();

		}
		return model;
	}


}
