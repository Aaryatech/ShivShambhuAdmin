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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.GetVendor;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.master.Weighing;
import com.ats.ssgs.model.mat.Contractor;

@Controller
@Scope("session")
public class TxnController {
	RestTemplate rest = new RestTemplate();
	int isError = 0;

	List<Vehicle> vehList;
	List<Contractor> conList;
	List<Vehicle> vehPoklenList;

	@RequestMapping(value = "/showAddWeighing", method = RequestMethod.GET)
	public ModelAndView showAddWeighing(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("matissue/addweighing");
			model.addObject("isError", isError);
			isError = 0;
			model.addObject("title", "Add Weighing");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 1);

			Vehicle[] vehArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map, Vehicle[].class);
			vehList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehList", vehList);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("vehicleType", 3);

			Vehicle[] vehPoklenArray = rest.postForObject(Constants.url + "getVehListByVehicleType", map,
					Vehicle[].class);
			vehPoklenList = new ArrayList<Vehicle>(Arrays.asList(vehPoklenArray));

			model.addObject("vehPoklenList", vehPoklenList);

			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList);

		} catch (Exception e) {

			System.err.println("exception In showAddWeighing at TxnController Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertWeighing", method = RequestMethod.POST)
	public String insertWeighing(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insertWeighing method");

			int weighId = 0;
			try {
				weighId = Integer.parseInt(request.getParameter("weighId"));
			} catch (Exception e) {
				weighId = 0;
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());

			int vehId = Integer.parseInt(request.getParameter("vehId"));
			int contraId = Integer.parseInt(request.getParameter("contr_id"));

			int poklenId = Integer.parseInt(request.getParameter("poklenId"));

			float qty = Float.parseFloat(request.getParameter("qty"));

			float vehKm = Float.parseFloat(request.getParameter("vehKm"));
			float poklenKm = Float.parseFloat(request.getParameter("poklenKm"));

			float contRate = Float.parseFloat(request.getParameter("rate"));
			String date = request.getParameter("date");
			String remark = request.getParameter("remark");

			Weighing weigh = new Weighing();
			weigh.setContraId(contraId);
			weigh.setContRate(contRate);
			weigh.setDate(DateConvertor.convertToYMD(date));
			weigh.setDelStatus(1);
			weigh.setExBool1(1);
			weigh.setExBool2(1);
			weigh.setExDate1(curDate);
			weigh.setExDate2(curDate);
			weigh.setExInt1(1);
			weigh.setExInt2(1);
			weigh.setExInt3(1);
			weigh.setExVar1("NA");
			weigh.setExVar2("NA");
			weigh.setExVar3("NA");
			weigh.setPoklenId(poklenId);
			weigh.setPoklenKm(poklenKm);
			weigh.setVehKm(vehKm);
			weigh.setQuantity(qty);
			weigh.setRemark(remark);
			weigh.setVehId(vehId);
			weigh.setUserId(1);

			try {
				weigh.setPhoto1(request.getParameter("photo1"));
				weigh.setPhoto2(request.getParameter("photo2"));

			} catch (Exception e) {
				weigh.setPhoto1("NA");
				weigh.setPhoto2("NA");
			}

			Weighing weighInsertRes = rest.postForObject(Constants.url + "saveWeighing", weigh, Weighing.class);
			if (weighInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

			System.err.println("plantInsertRes " + weighInsertRes.toString());

		} catch (Exception e) {
			System.err.println("EXCE in weighInsertRes " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddWeighing";

	}
	
	@RequestMapping(value = "/showWeighingList", method = RequestMethod.GET)
	public ModelAndView showWeighingList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("matissue/weighList");
			
			model.addObject("title", "Weighing List");
			/*GetVendor[] vendArray = rest.getForObject(Constants.url + "getAllVendorList", GetVendor[].class);
			getVendList = new ArrayList<GetVendor>(Arrays.asList(vendArray));

			
			model.addObject("vendList", getVendList);*/
		} catch (Exception e) {

			System.err.println("exception In showVendorList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

}
