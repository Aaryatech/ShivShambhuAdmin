package com.ats.ssgs.controller;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import com.ats.ssgs.model.GetTotalChalanQuantity;
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
import com.ats.ssgs.model.chalan.GetChalanHeader;
import com.ats.ssgs.model.chalan.GetChalanHeader2;
import com.ats.ssgs.model.master.Company;
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
			model.addObject("plantName", quotPrintData.get(0).getQuotDetPrint().get(0).getPlantName());
			model.addObject("plantId", quotPrintData.get(0).getQuotDetPrint().get(0).getPlantId());
			String quotNo = quotPrintData.get(0).getQuotDetPrint().get(0).getQuotNo();

			String[] parts = quotNo.split("-");
			String quotNo1 = parts[1].concat("-").concat(parts[2]);
			System.out.println("quot nois " + quotNo1);
			model.addObject("quotNo1", quotNo1);

			System.err.println("pdf data " + quotPrintData.get(0).getQuotDetPrint().toString());
			model.addObject("quotPrintData", quotPrintData);
			// quotIdList
			// model.addObject("quotIdList", quotIdList);
			/*
			 * map = new LinkedMultiValueMap<String, Object>();
			 * 
			 * map.add("termId",4]); DocTermHeader editDoc =
			 * rest.postForObject(Constants.url + "getDocHeaderByTermId", map,
			 * DocTermHeader.class);
			 * 
			 * model.addObject("supplyList", editDoc.getDetailList());
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "pdf/showChalanPdf/{chalanId}/{plantId}", method = RequestMethod.GET)
	public ModelAndView showChalanPdf(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("chalanId") int chalanId, @PathVariable("plantId") int plantId) {
		// List<ChalanPrintData> chPrintData=new ArrayList<>();

		ModelAndView model = null;

		System.err.println("in pdf/showChalanPdf/\", " + plantId + chalanId);
		try {
			if (plantId == 70) {
				model = new ModelAndView("print_page/chalan_print_rmc");
			} else if (plantId == 68) {
				model = new ModelAndView("print_page/chalan_print");

			} else {
				model = new ModelAndView("print_page/rm_chalan_print");
			}

			// model = new ModelAndView("print_page/chalan_print");

			RestTemplate rest = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("chalanId", chalanId);
			GetChalanHeader2 chPrint = rest.postForObject(Constants.url + "/getChalanHeaders2ByChalanId", map,
					GetChalanHeader2.class);

			int orderId = chPrint.getOrderId();
			System.err.println("Order id:::::" + orderId);
			map = new LinkedMultiValueMap<String, Object>();

			map.add("orderId", orderId);
			map.add("chalanId", chPrint.getChalanId());
			GetTotalChalanQuantity[] chArry = rest.postForObject(Constants.url + "/getChalanQuanPrintData", map,
					GetTotalChalanQuantity[].class);
			List<GetTotalChalanQuantity> rstList = new ArrayList<GetTotalChalanQuantity>(Arrays.asList(chArry));
			model.addObject("rstList", rstList);

			System.err.println("1 result " + rstList.toString());
			map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanId", chalanId);
			ChalanPrintData chPrintData = rest.postForObject(Constants.url + "/getChalanPrintData", map,
					ChalanPrintData.class);

			String ch_no = chPrintData.getChalanItemList().get(0).getChalanNo();

			String[] parts = ch_no.split("-");
			String ch_no1 = parts[1].concat("-").concat(parts[2]);
			model.addObject("ch_no1", ch_no1);
			String a = chPrintData.getChalanItemList().get(0).getVehTimeIn();
			float b = chPrintData.getChalanItemList().get(0).getInKm();

			if (a.equals("00:00:00")) {

				model.addObject("temp", "--:--:--");

			} else {

				model.addObject("temp", a);
			}

			if (b == 0.0) {

				model.addObject("temp1", "--");

			} else {

				model.addObject("temp1", b);
			}

			System.out.println(chPrint);
			model.addObject("printData", chPrintData);

			model.addObject("chPrint", chPrint);
			for (int i = 0; i < chPrintData.getChalanItemList().size(); i++) {
				System.out.println("inside for");
				System.err.println("2 result " + chPrintData.getChalanItemList().get(i).getItemId());
				System.err.println("3 result " + rstList.get(i).getItemId());
				for (int i1 = 0; i1 < rstList.size(); i1++) {

					if (chPrintData.getChalanItemList().get(i).getItemId() == rstList.get(i1).getItemId()) {
						System.out.println("matched");
						model.addObject("result", rstList);

					}
				}
			}
			chalanId = 0;
			plantId = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

}
