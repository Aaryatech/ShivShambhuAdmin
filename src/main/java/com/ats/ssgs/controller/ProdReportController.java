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
import com.ats.ssgs.model.prodrm.FgItemWiseRMReport;
import com.ats.ssgs.model.prodrm.GetProdPlanHeader;
import com.ats.ssgs.model.prodrm.ProdReportDetail;
import com.ats.ssgs.model.prodrm.ProdReportHeader;
import com.ats.ssgs.model.prodrm.RmReportDetail;
import com.ats.ssgs.model.prodrm.RmReportHeader;


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

			model.addObject("title", "Production Report");
			
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

	
	//shshowRMHeadReportowRMHeadReport
	
	@RequestMapping(value = "/showRMHeadReport", method = RequestMethod.GET)
	public ModelAndView showRMHeadReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("prod/rm_head_report");

			model.addObject("title", "RM Report Between Date");
			
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

	
	List<RmReportHeader> rmHeadReport;

	@RequestMapping(value = "/getRMHeadReport", method = RequestMethod.GET)
	public @ResponseBody List<RmReportHeader> getRMHeadReport(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getRMHeadReport");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			int plantId = Integer.parseInt(request.getParameter("plantId"));

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			map.add("plantId", plantId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			RmReportHeader[] prodHeadArray = rest.postForObject(Constants.url + "getRmReportHeader", map,
					RmReportHeader[].class);
			rmHeadReport = new ArrayList<RmReportHeader>(Arrays.asList(prodHeadArray));


		} catch (Exception e) {
			System.err.println("Exce in Ajax /getProdHeadReport  " + e.getMessage());
			e.printStackTrace();
		}
		return rmHeadReport;
	}
	
	List<RmReportDetail> rmDetailReportList;
	@RequestMapping(value = "/getRMReportDetail", method = RequestMethod.POST)
	public ModelAndView getRMReportDetail(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			rmDetailReportList = new ArrayList<>();

			int rmId = Integer.parseInt(request.getParameter("rmId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("rmId", rmId);
			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			
			//System.err.println("From Date " +fromDate);
			
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("plantId", plantId);
			
			RmReportDetail[] rmDetArray = rest.postForObject(Constants.url + "getRmReportDetail", map,
					RmReportDetail[].class);
			rmDetailReportList = new ArrayList<RmReportDetail>(Arrays.asList(rmDetArray));

			model = new ModelAndView("prod/rm_detail_report");
			model.addObject("title", "RM Report Detail");
			model.addObject("rmDetailReportList", rmDetailReportList);

		} catch (Exception e) {
			System.err.println("Exce in view RM Detail Report /getRMReportDetail  " + e.getMessage());
			e.printStackTrace();

		}
		return model;
	}
	
	
	//fg Item Report for RM ITems
	List<FgItemWiseRMReport> fgItemwiseReport;
	@RequestMapping(value = "/getFgItemWiseRMReport", method = RequestMethod.POST)
	public ModelAndView getFgItemWiseRMReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			fgItemwiseReport = new ArrayList<>();

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
			
			FgItemWiseRMReport[] fgArray = rest.postForObject(Constants.url + "getFgItemWiseRMReport", map,
					FgItemWiseRMReport[].class);
			fgItemwiseReport = new ArrayList<FgItemWiseRMReport>(Arrays.asList(fgArray));

			model = new ModelAndView("prod/prod_report_fg_rm");
			model.addObject("title", "Production Report RM Detail");
			model.addObject("fgItemwiseReport", fgItemwiseReport);
			
			model.addObject("plantId", plantId);

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {
			System.err.println("Exce in view Prod Detail Report /getProdReportDetail  " + e.getMessage());
			e.printStackTrace();

		}
		return model;
	}

}
