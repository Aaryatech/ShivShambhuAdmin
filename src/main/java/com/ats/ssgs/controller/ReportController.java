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
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.mat.Contractor;
import com.ats.ssgs.model.mat.GetMatIssueHeader;
import com.ats.ssgs.model.mat.GetVehHeader;
import com.ats.ssgs.model.mat.ItemCategory;
import com.ats.ssgs.model.order.GetOrder;

@Controller
@Scope("session")
public class ReportController {
	RestTemplate rest = new RestTemplate();
	int isError = 0;
	List<Contractor> conList;

	@RequestMapping(value = "/showContractReport", method = RequestMethod.GET)
	public ModelAndView showContractReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/contrareport");
			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			model.addObject("title", "Contractorwise Report");

		} catch (Exception e) {

			System.err.println("exception In showContraReport at Txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/showVehicleReport", method = RequestMethod.GET)
	public ModelAndView showVehicleReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/vehreport");
			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			model.addObject("title", "Vehiclewise Report");

		} catch (Exception e) {

			System.err.println("exception In showVehicleReport at Txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<GetMatIssueHeader> getMatList = new ArrayList<>();

	@RequestMapping(value = "/getContraReportBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetMatIssueHeader> getContraReportBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getContraReportBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetMatIssueHeader[] ordHeadArray = rest.postForObject(Constants.url + "getContractorBetweenDate", map,
				GetMatIssueHeader[].class);
		getMatList = new ArrayList<GetMatIssueHeader>(Arrays.asList(ordHeadArray));

		return getMatList;
	}

	List<GetVehHeader> getVehList = new ArrayList<>();

	@RequestMapping(value = "/getVehicleReportBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetVehHeader> getVehicleReportBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getContraReportBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));

		GetVehHeader[] ordHeadArray = rest.postForObject(Constants.url + "getVehicleBetweenDate", map,
				GetVehHeader[].class);
		getVehList = new ArrayList<GetVehHeader>(Arrays.asList(ordHeadArray));

		return getVehList;
	}

	@RequestMapping(value = "/vehilceDetailReport/{matVehHeaderId}", method = RequestMethod.GET)
	public ModelAndView vehilceDetailReport(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int matVehHeaderId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/vehdetreport");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matVehHeaderId", matVehHeaderId);

			GetVehHeader editVeh = rest.postForObject(Constants.url + "getMatIssueVehicleByHeaderId", map,
					GetVehHeader.class);
			model.addObject("title", "Vehicle Report");
			model.addObject("editVeh", editVeh);
			model.addObject("editVehDetail", editVeh.getVehDetailList());

		} catch (Exception e) {
			System.err.println("exception In vehilceDetailReport at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/contractorDetailReport/{matHeaderId}", method = RequestMethod.GET)
	public ModelAndView contractorDetailReport(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int matHeaderId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("report/contradetreport");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("matHeaderId", matHeaderId);
			GetMatIssueHeader editMat = rest.postForObject(Constants.url + "getMatIssueContrByHeaderId", map,
					GetMatIssueHeader.class);
			model.addObject("title", "Contractorwise Report");
			model.addObject("editMat", editMat);
			model.addObject("editMatDetail", editMat.getMatIssueDetailList());

		} catch (Exception e) {
			System.err.println("exception In contractorDetailReport at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

}
