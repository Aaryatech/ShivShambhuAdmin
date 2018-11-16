package com.ats.ssgs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.PaymentTerm;
import com.ats.ssgs.model.master.Uom;

@Controller
@Scope("session")
public class PaymentController {
	RestTemplate rest = new RestTemplate();
	List<PaymentTerm> payTermList;

	@RequestMapping(value = "/showAddPaymentTerm", method = RequestMethod.GET)
	public ModelAndView showAddPaymentTerm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addpayterm");

			PaymentTerm[] plantArray = rest.getForObject(Constants.url + "getAllPaymentTermList", PaymentTerm[].class);
			payTermList = new ArrayList<PaymentTerm>(Arrays.asList(plantArray));

			model.addObject("payTermList", payTermList);

			model.addObject("title", "Add Payment Term");

		} catch (Exception e) {

			System.err.println("exception In showAddPaymentTerm at Payment Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertPayTerm", method = RequestMethod.POST)
	public String insertPayTerm(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertPayTerm method");

			int payTermId = 0;
			try {
				payTermId = Integer.parseInt(request.getParameter("payTermId"));
			} catch (Exception e) {
				payTermId = 0;
			}

			String date = request.getParameter("date");
			String payTerm = request.getParameter("payTerm");

			PaymentTerm paymentTerm = new PaymentTerm();

			paymentTerm.setDate(DateConvertor.convertToYMD(date));
			paymentTerm.setDelStatus(1);
			paymentTerm.setIsUsed(1);
			paymentTerm.setPayTerm(payTerm);
			paymentTerm.setPayTermId(payTermId);

			PaymentTerm payTermInsertRes = rest.postForObject(Constants.url + "savePaymentTerm", paymentTerm,
					PaymentTerm.class);

		} catch (Exception e) {

			System.err.println("Exce in insert Uom " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddPaymentTerm";
	}

	@RequestMapping(value = "/editPaymentTerm/{payTermId}", method = RequestMethod.GET)
	public ModelAndView editPaymentTerm(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int payTermId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addpayterm");

			PaymentTerm[] plantArray = rest.getForObject(Constants.url + "getAllPaymentTermList", PaymentTerm[].class);
			payTermList = new ArrayList<PaymentTerm>(Arrays.asList(plantArray));

			model.addObject("payTermList", payTermList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("payTermId", payTermId);

			PaymentTerm editPayTerm = rest.postForObject(Constants.url + "getPTByPaymentTermId", map,
					PaymentTerm.class);

			model.addObject("title", "Edit Payment Term");
			model.addObject("editPayTerm", editPayTerm);

		} catch (Exception e) {

			System.err.println("exception In editPaymentTerm at PaymentContr Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deletePayTerm/{payTermId}", method = RequestMethod.GET)
	public String deletePayTerm(HttpServletRequest request, HttpServletResponse response, @PathVariable int payTermId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("payTermId", payTermId);

			Info errMsg = rest.postForObject(Constants.url + "deletePaymentTerm", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deletePayTerm @PaymentContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddPaymentTerm";
	}

}
