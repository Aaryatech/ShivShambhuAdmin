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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.quot.GetQuotHeads;

@Controller
@Scope("session")
public class QuotController {
	
	RestTemplate rest = new RestTemplate();
	List<User> usrList;
	List<GetQuotHeads> quotList;

	@RequestMapping(value = "/showQuotations", method = RequestMethod.GET)
	public ModelAndView showAddPlant(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("quot/quotList");

			model.addObject("title", "Quotation List");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("statusList", "0,1");

			GetQuotHeads[] quotArray = rest.postForObject(Constants.url + "getQuotHeaders",map, GetQuotHeads[].class);
			quotList = new ArrayList<GetQuotHeads>(Arrays.asList(quotArray));

			model.addObject("quotList", quotList);
			
		}catch (Exception e) {
			System.err.println("Exce in /showQuotations" +e.getMessage());
			e.printStackTrace();
		}
		return model;
	
	}
}
