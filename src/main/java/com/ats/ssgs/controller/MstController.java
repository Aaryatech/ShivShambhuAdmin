package com.ats.ssgs.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.model.master.Plant;

@Controller
public class MstController {
	
	RestTemplate rest = new RestTemplate();
	int isError = 0;

	@RequestMapping(value = "/showAddContractor", method = RequestMethod.GET)
	public ModelAndView showAddVendor(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("mst/contractor");
			model.addObject("isError", isError);
			isError = 0;

			/*Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);*/

			model.addObject("title", "Add Contractor");

		} catch (Exception e) {

			System.err.println("exception In showAddContactor at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}


}
