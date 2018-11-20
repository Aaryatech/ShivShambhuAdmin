package com.ats.ssgs.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor; 
import com.ats.ssgs.model.quot.QuotHeader;

@Controller
@Scope("session")
public class PurchaseOrderController {
	
	RestTemplate rest = new RestTemplate();
	
	@RequestMapping(value = "/addPo/{quotId}", method = RequestMethod.GET)
	public ModelAndView editQuot(HttpServletRequest request, HttpServletResponse response, @PathVariable int quotId) {

		ModelAndView model = new ModelAndView("purchaseOrder/addPo");
		try {

			 

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotHeadId", quotId); 
			QuotHeader quotHeader=rest.postForObject(Constants.url + "/getQuotHeaderWithNameByQuotHeadId", map, QuotHeader.class);
			quotHeader.setQuotDate(DateConvertor.convertToDMY(quotHeader.getQuotDate()));
			model.addObject("quotHeader",quotHeader);
			
		} catch (Exception e) {
			System.err.println("Exce in /showQuotations" + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

}
