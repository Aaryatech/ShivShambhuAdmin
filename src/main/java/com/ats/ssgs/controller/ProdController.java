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
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.order.GetOrder;
import com.ats.ssgs.model.prodrm.GetProdPlanDetail;
import com.ats.ssgs.model.prodrm.GetProdPlanHeader;
import com.ats.ssgs.model.prodrm.ProdPlanDetail;
import com.ats.ssgs.model.prodrm.ProdPlanHeader;

@Controller
@Scope("session")
public class ProdController {

	RestTemplate rest = new RestTemplate();
	DateFormat dateFormatdd = new SimpleDateFormat("dd-MM-yyyy");

	DateFormat dateFormatyy = new SimpleDateFormat("yyyy-MM-dd");

	List<Plant> plantList;
	int isError = 0;
	@RequestMapping(value = "/showProdPlanList", method = RequestMethod.GET)
	public ModelAndView showProdPlanList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("prod/prod_plan_list");

			model.addObject("title", "Production Plan List");
			
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

	List<GetProdPlanHeader> prodHeadList;

	@RequestMapping(value = "/getProdHeadersBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetProdPlanHeader> getProdHeadersBetDate(HttpServletRequest request,
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

			GetProdPlanHeader[] prodArray = rest.postForObject(Constants.url + "getProdPlanHeadersBetDate", map,
					GetProdPlanHeader[].class);
			prodHeadList = new ArrayList<GetProdPlanHeader>(Arrays.asList(prodArray));

			for (int i = 0; i < prodHeadList.size(); i++) {

				prodHeadList.get(i)
						.setProductionDate(DateConvertor.convertToDMY(prodHeadList.get(i).getProductionDate()));

				prodHeadList.get(i).setProductionStartDate(
						DateConvertor.convertToDMY(prodHeadList.get(i).getProductionStartDate()));
				prodHeadList.get(i)
						.setProductionEndDate(DateConvertor.convertToDMY(prodHeadList.get(i).getProductionEndDate()));

			}

		} catch (Exception e) {
			System.err.println("Exce in Ajax /getProdHeadersBetDate  " + e.getMessage());
			e.printStackTrace();
		}
		return prodHeadList;
	}

	GetProdPlanHeader prodHeader;

	@RequestMapping(value = "/getProdDetail", method = RequestMethod.POST)
	public ModelAndView getProdDetail(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			prodHeader = new GetProdPlanHeader();
			System.err.println(" in getProdDetail prodHeaderId " + request.getParameter("prodHeaderId"));

			int prodHeaderId = Integer.parseInt(request.getParameter("prodHeaderId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("prodHeaderId", prodHeaderId);
			prodHeader = rest.postForObject(Constants.url + "getProdPlanDetail", map, GetProdPlanHeader.class);

			prodHeader.setProductionDate(DateConvertor.convertToDMY(prodHeader.getProductionDate()));
			prodHeader.setProductionStartDate(DateConvertor.convertToDMY(prodHeader.getProductionStartDate()));
			prodHeader.setProductionEndDate(DateConvertor.convertToDMY(prodHeader.getProductionEndDate()));

			model = new ModelAndView("prod/prod_plan_detail");
			model.addObject("title", "Production Plan Detail");
			model.addObject("prodHeader", prodHeader);

		} catch (Exception e) {
			System.err.println("Exce in view Prod Detail /getProdDetail  " + e.getMessage());
			e.printStackTrace();

		}
		return model;
	}

	// completeProd
	@RequestMapping(value = "/completeProd", method = RequestMethod.POST)
	public String completeProd(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<GetProdPlanDetail> proDList = prodHeader.getGetProdPlanDetList();
			List<ProdPlanDetail> planDetails = new ArrayList<>();
			
			System.err.println("proDList size  " +proDList.size() + "\n  " +"toSting  " +proDList.toString());

			for (int i = 0; i < proDList.size(); i++) {
					System.err.println("string "+"prodQty"+proDList.get(i).getProductionDetailId());
				
				float prodQty = Float
						.parseFloat(request.getParameter("prodQty"+proDList.get(i).getProductionDetailId()));
				float rejQty = Float
						.parseFloat(request.getParameter("rejQty"+proDList.get(i).getProductionDetailId()));

				ProdPlanDetail det = new ProdPlanDetail();

				det.setProductionDetailId(proDList.get(i).getProductionDetailId());
				det.setStatus(3);
				det.setProductionQty(prodQty);
				det.setRejectedQty(rejQty);

				planDetails.add(det);

			}

			ProdPlanHeader planHeader = new ProdPlanHeader();

			planHeader.setProductionStatus(3);
			planHeader.setProductionHeaderId(prodHeader.getProductionHeaderId());
			planHeader.setProdPlanDetailList(planDetails);
			planHeader.setProductionEndDate(dateFormatyy.format(new Date()));

			Info completeProd = rest.postForObject(Constants.url + "completeProd", planHeader, Info.class);
			
			if(completeProd.isError()==false) {
				isError=2;
			}else {
				isError=1;
			}
			
			System.err.println("completeProd  " + completeProd.toString());

		} catch (Exception e) {
			
			System.err.println("Exce in complete Prod  " +e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showProdPlanList";

	}

}
