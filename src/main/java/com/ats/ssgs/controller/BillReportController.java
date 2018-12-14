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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.GetBillReport;
import com.ats.ssgs.model.master.Company;
import com.ats.ssgs.model.master.GetWeighing;
import com.ats.ssgs.model.master.Plant;

@Controller
@Scope("session")
public class BillReportController {

	RestTemplate rest = new RestTemplate();

	List<Company> compList;
	List<Plant> plantList;

	@RequestMapping(value = "/showBillwiseReport", method = RequestMethod.GET)
	public ModelAndView showBillwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("report/billwisereport");

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);

			model.addObject("title", "Billwise Report");

		} catch (Exception e) {

			System.err.println("exception In showBillwiseReport at billreport Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	// Ajax call
	@RequestMapping(value = "/getPlantByCompId", method = RequestMethod.GET)
	public @ResponseBody List<Plant> getPlantByCompId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int compId = Integer.parseInt(request.getParameter("compId"));

		map.add("companyId", compId);

		Plant[] plantArray = rest.postForObject(Constants.url + "getPlantListByCompId", map, Plant[].class);
		plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

		System.err.println("Ajax Plant List " + plantList.toString());

		return plantList;

	}

	List<GetBillReport> billList;

	@RequestMapping(value = "/getBillListBetweenDate", method = RequestMethod.GET)
	public @ResponseBody List<GetBillReport> getBillListBetweenDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getBillListBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String[] companyIdList = request.getParameterValues("companyId");
		String[] plantIdList = request.getParameterValues("plantId");

		System.out.println("plantIdList lengtr" + plantIdList.toString());

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < companyIdList.length; i++) {
			sb = sb.append(companyIdList[i] + ",");

		}
		String items = sb.toString();
		items = items.substring(0, items.length() - 1);

		StringBuilder sb1 = new StringBuilder();

		for (int i = 0; i < plantIdList.length; i++) {
			sb1 = sb1.append(plantIdList[i] + ",");

		}
		String items1 = sb1.toString();
		items1 = items1.substring(0, items1.length() - 1);

		System.out.println("plantIdList" + items1);

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantIdList", items1);
		map.add("companyIdList", items);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetBillReport[] ordHeadArray = rest.postForObject(Constants.url + "getBillwiseReport", map,
				GetBillReport[].class);
		billList = new ArrayList<GetBillReport>(Arrays.asList(ordHeadArray));

		System.out.println("billList-----" + billList.toString());

		return billList;
	}

}
