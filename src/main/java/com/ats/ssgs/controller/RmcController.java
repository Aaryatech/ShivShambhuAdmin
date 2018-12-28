package com.ats.ssgs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.chalan.ChalanHeader;
import com.ats.ssgs.model.master.Company;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.LoginResUser;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Setting;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.rmc.GetRmcOrders;

@Controller
@Scope("session")
public class RmcController {

	RestTemplate rest = new RestTemplate();
	String fromDate, toDate;
	List<GetRmcOrders> rmcOrdList;
	
	List<Plant> plantList;

	@RequestMapping(value = "/showRmcOrdList", method = RequestMethod.GET)
	public ModelAndView showrmcOrdList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			// String fromDate,toDate;
			int plantId = 67;
			if (request.getParameter("from_date") == null || request.getParameter("to_date") == null) {
				Date date = new Date();
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				fromDate = df.format(date);
				toDate = df.format(date);
				System.out.println("From Date And :" + fromDate + "ToDATE" + toDate);

				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));

				System.out.println("inside if ");
			} else {
				fromDate = request.getParameter("from_date");
				toDate = request.getParameter("to_date");
				plantId = Integer.parseInt(request.getParameter("plant_id"));
				System.out.println("inside Else ");

				System.out.println("fromDate " + fromDate);

				System.out.println("toDate " + toDate);

				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));

			}
			map.add("plantId", plantId);
			model = new ModelAndView("rmc/show_rmc_ord");
			GetRmcOrders[] rmcOrArray = rest.postForObject(Constants.url + "/getRmcOrdList", map, GetRmcOrders[].class);

			rmcOrdList = new ArrayList<GetRmcOrders>(Arrays.asList(rmcOrArray));

			System.out.println("rmcOrdList List using /showBOMReqests   " + rmcOrdList.toString());
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			model.addObject("rmcOrdList", rmcOrdList);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("Exception in showBOMReqests BomContrller" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}
	List<Vehicle> vehicleList;
	List<User> usrList;
	GetRmcOrders rmcOrd;
	@RequestMapping(value = "/showAddRmcChalan/{key}", method = RequestMethod.GET)
	public ModelAndView showAddChalan(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int key) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/generate_chalan_rmc");

	/*		Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);*/

			Vehicle[] vehArray = rest.getForObject(Constants.url + "getAllVehicleList", Vehicle[].class);
			vehicleList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehicleList", vehicleList);

			User[] usrArray = rest.getForObject(Constants.url + "getAllUserList", User[].class);
			usrList = new ArrayList<User>(Arrays.asList(usrArray));

			model.addObject("usrList", usrList);

			model.addObject("title", "Add Chalan");
			//model.addObject("orderId", orderId);
			
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

			System.out.println(sdf.format(cal.getTime()));

			String curTime = sdf.format(cal.getTime());

			model.addObject("curTime", curTime);
			String curDate = dateFormat.format(new Date());
			
			model.addObject("curDate" ,curDate);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 5);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			model.addObject("doc", doc
					);
			rmcOrd=new GetRmcOrders();
			rmcOrd=rmcOrdList.get(key);
			 
			System.err.println("rmcOrd " +rmcOrd);
			
			model.addObject("rmcOrd", rmcOrd);
			
			map = new LinkedMultiValueMap<String, Object>();

			map.add("keyList", "5");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map, Setting[].class);
			List<Setting> settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);



		} catch (Exception e) {

			System.err.println("exception In showAddChalan at Chalan Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	
	@RequestMapping(value = "/showBillRmc", method = RequestMethod.GET)
	public ModelAndView showBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("bill/add_rmc_bill");

			/*
			 * Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList",
			 * Plant[].class); List<Plant> plantList = new
			 * ArrayList<Plant>(Arrays.asList(plantArray));
			 * 
			 * model.addObject("plantList", plantList);
			 */DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			model.addObject("curDate", curDate);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 6);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			model.addObject("doc", doc);

			/*Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			List<Company> compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);*/
			
			HttpSession session = request.getSession();
			ChalanHeader chalan = (ChalanHeader) session.getAttribute("chalanRes");
			
			
			model.addObject("chalan",chalan);
			model.addObject("rmcOrd", rmcOrd);
			
			session.removeAttribute("chalanRes");
			
			model.addObject("title", "Add Bill");
		} catch (Exception e) {

			System.err.println("" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}


}
