package com.ats.ssgs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.master.Vehicle;

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

	
	
	
	
	
	

}
