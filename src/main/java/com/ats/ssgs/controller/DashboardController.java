package com.ats.ssgs.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.model.DashSaleCount;

@Controller
public class DashboardController {
	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("home");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(now.getTime());
			
	/*		
			Calendar cal = Calendar.getInstance();
			cal.set(year, monthNo - 1, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String firstDate = sdf.format(cal.getTimeInMillis());
			cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
			String endDate = sdf.format(cal.getTimeInMillis());

			System.out.println("sd " + firstDate);
			System.out.println("ed " + endDate);

			model.addObject("firstDate", firstDate);
			model.addObject("endDate", endDate);*/

			map.add("fromDate", date);

			map.add("toDate", date);
			map.add("plantId", 0);

			DashSaleCount dashBoard = rest.postForObject(Constants.url + "/getDashboardCountBetDate", map,
					DashSaleCount.class);

			model.addObject("dashBoard", dashBoard);

			System.out.println("dashBoard" + dashBoard.toString());

		} catch (Exception e) {

			System.err.println("Exce ing etHubDashBoard  " + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

}
