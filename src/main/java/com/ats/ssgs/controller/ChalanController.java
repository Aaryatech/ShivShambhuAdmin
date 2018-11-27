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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.order.GetOrderDetail;
import com.ats.ssgs.model.order.OrderHeader;

@Controller
@Scope("session")
public class ChalanController {

	RestTemplate rest = new RestTemplate();
	List<Plant> plantList;
	List<Vehicle> vehicleList;
	List<User> usrList;

	@RequestMapping(value = "/showAddChalan", method = RequestMethod.GET)
	public ModelAndView showAddChalan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/generate_chalan");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Vehicle[] vehArray = rest.getForObject(Constants.url + "getAllVehicleList", Vehicle[].class);
			vehicleList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehicleList", vehicleList);

			User[] usrArray = rest.getForObject(Constants.url + "getAllUserList", User[].class);
			usrList = new ArrayList<User>(Arrays.asList(usrArray));

			model.addObject("usrList", usrList);

			model.addObject("title", "Add Chalan");

		} catch (Exception e) {

			System.err.println("exception In showAddChalan at Chalan Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<OrderHeader> ordHeadList;

	@RequestMapping(value = "/getOrdHeaderForChalan", method = RequestMethod.GET)
	public @ResponseBody List<OrderHeader> getItemsByPlantId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int projId = Integer.parseInt(request.getParameter("projId"));

		int custId = Integer.parseInt(request.getParameter("custId"));

		System.err.println("getOrdHeaderForChalan  " + "projId" + projId + "custId" + custId);

		map.add("custId", custId);
		map.add("projId", projId);

		map.add("statusList", "0,1");

		OrderHeader[] ordArray = rest.postForObject(Constants.url + "getOrdHeaderForChalan", map, OrderHeader[].class);
		ordHeadList = new ArrayList<OrderHeader>(Arrays.asList(ordArray));

		for (int i = 0; i < ordHeadList.size(); i++) {

			ordHeadList.get(i).setOrderDate(DateConvertor.convertToDMY(ordHeadList.get(i).getOrderDate()));
		}

		System.err.println("Ajax Order List " + ordHeadList.toString());

		return ordHeadList;

	}

	List<GetOrderDetail> ordDetailList;

	@RequestMapping(value = "/getOrderDetailForChalan", method = RequestMethod.GET)
	public @ResponseBody List<GetOrderDetail> getOrderDetail(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int orderId = Integer.parseInt(request.getParameter("orderId"));

		map.add("orderHeaderId", orderId);
		GetOrderDetail[] ordDetailArray = rest.postForObject(Constants.url + "getOrderDetailList", map,
				GetOrderDetail[].class);

		return ordDetailList = new ArrayList<GetOrderDetail>(Arrays.asList(ordDetailArray));
	}

}
