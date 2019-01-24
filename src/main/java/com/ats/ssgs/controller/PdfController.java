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
import com.ats.ssgs.model.chalan.ChalanPrintData;
import com.ats.ssgs.model.master.DocTermHeader;
import com.ats.ssgs.model.quot.GetQuotHeads;
import com.ats.ssgs.model.quot.QuotPrintData;

@Controller
@Scope("session")
public class PdfController {

	@RequestMapping(value = "pdf/showQuotPdf/{quotIdList}", method = RequestMethod.GET)
	public ModelAndView showBillsPdf(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("quotIdList") String[] quotIdList) {
		List<QuotPrintData> quotPrintData = new ArrayList<>();

		ModelAndView model = null;
		System.err.println("in pdf/showQuotPdf/\", ");
		try {
			model = new ModelAndView("print_page/quot_print");
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
			quotPrintData = new ArrayList<QuotPrintData>(Arrays.asList(qPrintArray));
			model.addObject("plantName",quotPrintData.get(0).getQuotDetPrint().get(0).getPlantName());

			System.err.println("pdf data " + quotPrintData.get(0).getQuotDetPrint().toString());
			model.addObject("quotPrintData", quotPrintData);
			// quotIdList
			// model.addObject("quotIdList", quotIdList);
/*
			map = new LinkedMultiValueMap<String, Object>();

			map.add("termId",4]);
			DocTermHeader editDoc = rest.postForObject(Constants.url + "getDocHeaderByTermId", map,
					DocTermHeader.class);

			model.addObject("supplyList", editDoc.getDetailList());*/

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	
	
	@RequestMapping(value = "pdf/showChalanPdf/{chalanId}", method = RequestMethod.GET)
	public ModelAndView showChalanPdf(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("chalanId") int chalanId) {
		// List<ChalanPrintData> chPrintData=new ArrayList<>();

		ModelAndView model = null;
		System.err.println("in pdf/showQuotPdf/\", ");
		try {
			model = new ModelAndView("print_page/chalan_print");
			RestTemplate rest = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("chalanId", chalanId);
			ChalanPrintData chPrintData = rest.postForObject(Constants.url + "/getChalanPrintData", map,
					ChalanPrintData.class);

			System.err.println("pdf data /showChalanPdf " + chPrintData.toString());
			// quotIdList
			// model.addObject("quotIdList", quotIdList);

			/*
			 * map = new LinkedMultiValueMap<String, Object>();
			 * 
			 * map.add("termId", 4); DocTermHeader editDoc =
			 * rest.postForObject(Constants.url + "getDocHeaderByTermId", map,
			 * DocTermHeader.class);
			 * 
			 * model.addObject("supplyList", editDoc.getDetailList());
			 * 
			 */
			model.addObject("printData", chPrintData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

}
