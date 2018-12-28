package com.ats.ssgs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.ats.ssgs.common.Currency;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.GetBillHeaderPdf;
import com.ats.ssgs.model.quot.GetQuotHeads;
import com.ats.ssgs.model.quot.QuotPrintData;

@Controller
@Scope("session")
public class PdfController {
	
	
	@RequestMapping(value = "pdf/showQuotPdf/{quotIdList}", method = RequestMethod.GET)
	public ModelAndView showBillsPdf( HttpServletRequest request,
			HttpServletResponse response,@PathVariable("quotIdList") String[] quotIdList) {

		ModelAndView model = new ModelAndView("print_page/quot_print");
System.err.println("in pdf/showQuotPdf/\", ");
		try {
			RestTemplate rest = new RestTemplate();
			String strQuotIdList = new String();
			for (int i = 0; i < quotIdList.length; i++) {
				strQuotIdList = strQuotIdList + "," + quotIdList[i];
			}
			strQuotIdList = strQuotIdList.substring(1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("quotIdList", strQuotIdList);
			QuotPrintData[] qPrintArray = rest.postForObject(Constants.url + "/getQuotPrintData", map,
				
					QuotPrintData[].class);
			List<QuotPrintData> quotPrintData=new ArrayList<QuotPrintData>(Arrays.asList(qPrintArray));

			System.err.println("pdf data "+quotPrintData.toString());
			//quotIdList
			model.addObject("quotIdList", quotIdList);


			model.addObject("quotPrintData", quotPrintData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

}
