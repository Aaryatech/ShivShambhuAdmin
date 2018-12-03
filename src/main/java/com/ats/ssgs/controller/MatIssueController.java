package com.ats.ssgs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.TempDocDetail;
import com.ats.ssgs.model.mat.Contractor;
import com.ats.ssgs.model.mat.TempMatIssueDetail;

@Controller
public class MatIssueController {
	RestTemplate rest = new RestTemplate();
	int isError = 0;

	List<TempMatIssueDetail> tempList = new ArrayList<TempMatIssueDetail>();
	List<Contractor> conList;

	@RequestMapping(value = "/showAddMatIssueContractor", method = RequestMethod.GET)
	public ModelAndView showAddMatIssueContractor(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("matissue/addmatissue");
			model.addObject("isError", isError);
			isError = 0;
			tempList = new ArrayList<TempMatIssueDetail>();

			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

			model.addObject("title", "Add Material Issue Contractor");

		} catch (Exception e) {

			System.err.println("exception In showAddMatIssueContractor at MatContr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/addMatIssueDetail", method = RequestMethod.GET)
	public @ResponseBody List<TempMatIssueDetail> addMatIssueDetail(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			if (isDelete == 1) {
				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				tempList.remove(key);

			} else if (isEdit == 1) {
				int index = Integer.parseInt(request.getParameter("index"));

				int sortNoDetail = Integer.parseInt(request.getParameter("sortNoDetail"));

				String termDesc = request.getParameter("termDesc");
				/*
				 * tempList.get(index).setSortNo(sortNoDetail);
				 * tempList.get(index).setTermDesc(termDesc);
				 */
			}

			else {
				int sortNoDetail = Integer.parseInt(request.getParameter("sortNoDetail"));

				String termDesc = request.getParameter("termDesc");

				TempMatIssueDetail tempDoc = new TempMatIssueDetail();

			}

		} catch (Exception e) {
			System.err.println("Exce In temp  tempList List " + e.getMessage());
			e.printStackTrace();
		}
		System.err.println(" enq Item List " + tempList.toString());

		return tempList;

	}

}
