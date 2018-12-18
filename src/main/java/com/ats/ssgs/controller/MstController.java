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

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.common.VpsImageUpload;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Subplant;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.master.Weighing;
import com.ats.ssgs.model.mat.Contractor;

@Controller
public class MstController {
	
	private ArrayList<Vehicle> vehList;
	private ArrayList<Subplant> spList;
	private ArrayList<Contractor> conList;
	private ArrayList<Plant> plantList;
	RestTemplate rest = new RestTemplate();
	int isError = 0;

	private ArrayList<Uom> uomList;
	
	
	//*************************************Contractor************************************//
	@RequestMapping(value = "/showAddContractor", method = RequestMethod.GET)
	public ModelAndView showAddVendor(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("mst/contractor");
			model.addObject("isError", isError);
			isError = 0;

			model.addObject("title", "Add Contractor");
			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList );

		} catch (Exception e) {

			System.err.println("exception In showAddContactor at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	
	
	@RequestMapping(value = "/insertContractor", method = RequestMethod.POST)
	public String insertUom(HttpServletRequest request, HttpServletResponse response) {

		try {

			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());
			System.err.println("Inside insert insertCon method");

			int conId = 0;
			try {
				conId = Integer.parseInt(request.getParameter("contrId"));
			} catch (Exception e) {
				conId  = 0;
			}
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			//String curDate = dateFormat.format(new Date());

			String conName = request.getParameter("conName");
			String conMob = request.getParameter("mobNo");
			float rate=Float.parseFloat(request.getParameter("contrRate"));

			Contractor con=new Contractor();
			con.setContrId(conId);
			con.setContrName(conName);
			con.setContrMob( conMob);
			con.setContrRate(rate);
			con.setContrType(1);
			con.setDelStatus(1);
			con.setExBool1(0);
			con.setExDate1(curDate);
			con.setExInt1(0);
			con.setExInt2(0);
			con.setExVar1("NA");
			con.setExVar2("NA");
			System.out.println("con data is "+con.toString());
			
			
			Contractor conInsertRes = rest.postForObject(Constants.url + "saveContractor",con, Contractor.class);

			if (conInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce in insert Contractor " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddContractor";
	}


	@RequestMapping(value = "/editCon/{contrId}", method = RequestMethod.GET)
	public ModelAndView editCon(HttpServletRequest request, HttpServletResponse response, @PathVariable int contrId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("mst/contractor");

			Contractor[] conArray = rest.getForObject(Constants.url + "getAllContractorList", Contractor[].class);
			conList = new ArrayList<Contractor>(Arrays.asList(conArray));

			model.addObject("conList", conList );

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("contrId", contrId);

			Contractor editCon = rest.postForObject(Constants.url + "getContractorById", map, Contractor.class);

			model.addObject("title", "Edit Contractor");
			model.addObject("editCon", editCon );

		} catch (Exception e) {

			System.err.println("exception In editCon at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}
	
	@RequestMapping(value = "/deleteCon/{contrId}", method = RequestMethod.GET)
	public String deleteCon(HttpServletRequest request, HttpServletResponse response, @PathVariable int contrId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("contrId", contrId);

			Info errMsg = rest.postForObject(Constants.url + "deleteContractor", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteCon @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddContractor";
	}
	
	
	

	@RequestMapping(value = "/deleteRecordofCon", method = RequestMethod.POST)
	public String deleteRecordofUom(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[]contrIds = request.getParameterValues("contrIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < contrIds.length; i++) {
				sb = sb.append(contrIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("contrIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiContractor", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofCon @MstContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddContractor";
	}

	
	
	//*************************************Vehicle************************************//
	@RequestMapping(value = "/showAddVehicle", method = RequestMethod.GET)
	public ModelAndView showAddVehicle(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			
			model = new ModelAndView("mst/vehicle");
			model.addObject("isError", isError);
			isError = 0;

			model.addObject("title", "Add Vehicle");
			Vehicle[] vehArray = rest.getForObject(Constants.url + "getAllVehicleList", Vehicle[].class);
			vehList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehList", vehList);
			
			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));
			model.addObject("uomList", uomList);


		} catch (Exception e) {

			System.err.println("exception In showAddVehicle at M Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	
	
	@RequestMapping(value = "/insertVehicle", method = RequestMethod.POST)
	public String insertWeighing(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc1") List<MultipartFile> file1, @RequestParam("doc2") List<MultipartFile> file2, @RequestParam("doc3") List<MultipartFile> file3, @RequestParam("doc4") List<MultipartFile> file4) {

		try {
			System.err.println("Inside insertVehicle method");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date now = new Date();
			Calendar cal = Calendar.getInstance();

		

			int vehId1= 0;
			try {
				vehId1 = Integer.parseInt(request.getParameter("vehId"));
			} catch (Exception e) {
				vehId1 = 0;
			}

			String curDate = dateFormat.format(new Date());
			String VehName = request.getParameter("vehName");
			String company = request.getParameter("compName");
			int uom = Integer.parseInt(request.getParameter("plant_id"));
			float l_capacity = Float.parseFloat(request.getParameter("loadCapacity"));
			String vehNum=request.getParameter("vehNum");

			
			System.out.println("Previous Image1" + file1.get(0).getOriginalFilename());
			System.out.println("Previous Image2" + file2.get(0).getOriginalFilename());
			System.out.println("Previous Image3" + file3.get(0).getOriginalFilename());
			System.out.println("Previous Image4" + file4.get(0).getOriginalFilename());

			
			String prevImage1 = request.getParameter("doc11");
			String prevImage2 =request.getParameter("doc12");
			String prevImage3 = request.getParameter("doc13");
			String prevImage4 = request.getParameter("doc14");
			try {
				if (!file1.get(0).getOriginalFilename().equalsIgnoreCase("")) {
					VpsImageUpload imgUpload = new VpsImageUpload();

					String tStamp = "" + System.currentTimeMillis();
					String extension = FilenameUtils.getExtension(file1.get(0).getOriginalFilename());

					prevImage1 = tStamp + "_1." + extension;
					imgUpload.saveUploadedFiles(file1.get(0), Constants.CAT_FILE_TYPE, prevImage1);

					System.out.println("prevImage1" + prevImage1);
				}
			} catch (Exception e) {
				System.err.println("Exc in uploading WorkType Imag " + e.getMessage());
				e.printStackTrace();

			}
			try {

				if (!file2.get(0).getOriginalFilename().equalsIgnoreCase("")) {
					VpsImageUpload imgUpload = new VpsImageUpload();

					// String tStamp = dateFormat.format(cal.getTime());
					String tStamp = "" + System.currentTimeMillis();
					String extension = FilenameUtils.getExtension(file1.get(0).getOriginalFilename());

					prevImage2 = tStamp + "_2." + extension;
					imgUpload.saveUploadedFiles(file2.get(0), Constants.CAT_FILE_TYPE, prevImage2);
					System.out.println("prevImage2" + prevImage2);

				}
			} catch (Exception e) {
				System.err.println("Exc in uploading WorkType Imag " + e.getMessage());
				e.printStackTrace();

			}

			try {

				if (!file3.get(0).getOriginalFilename().equalsIgnoreCase("")) {
					VpsImageUpload imgUpload = new VpsImageUpload();

					// String tStamp = dateFormat.format(cal.getTime());
					String tStamp = "" + System.currentTimeMillis();
					String extension = FilenameUtils.getExtension(file3.get(0).getOriginalFilename());

					prevImage3 = tStamp + "_3." + extension;
					imgUpload.saveUploadedFiles(file3.get(0), Constants.CAT_FILE_TYPE, prevImage3);
					System.out.println("prevImage3" + prevImage3);

				}
			} catch (Exception e) {
				System.err.println("Exc in uploading WorkType Imag " + e.getMessage());
				e.printStackTrace();

			}

			try {

				if (!file4.get(0).getOriginalFilename().equalsIgnoreCase("")) {
					VpsImageUpload imgUpload = new VpsImageUpload();

					// String tStamp = dateFormat.format(cal.getTime());
					String tStamp = "" + System.currentTimeMillis();
					String extension = FilenameUtils.getExtension(file4.get(0).getOriginalFilename());

					prevImage4 = tStamp + "_4." + extension;
					imgUpload.saveUploadedFiles(file4.get(0), Constants.CAT_FILE_TYPE, prevImage4);
					System.out.println("prevImage4" + prevImage4);

				}
			} catch (Exception e) {
				System.err.println("Exc in uploading WorkType Imag " + e.getMessage());
				e.printStackTrace();

			}

			Vehicle veh = new Vehicle();
			veh.setVehicleId(vehId1);
			veh.setVehicleName(VehName);
			veh.setVehCompName(company);
			veh.setLoadCapacity(l_capacity);
			veh.setUomId(uom);
			veh.setVehNo(vehNum);
			veh.setDelStatus(1);
			veh.setExBool1(1);
			veh.setExBool2(1);
			veh.setExDate1(curDate);
			veh.setExDate2(curDate);
			veh.setExInt1(1);
			veh.setExInt2(1);
			veh.setExInt3(1);
			veh.setExVar1("NA");
			veh.setExVar2("NA");
			
			try {
				veh.setVehDoc1(prevImage1);
				veh.setVehDoc2(prevImage2);
				veh.setVehDoc3(prevImage3);
				veh.setVehDoc4(prevImage4);
				

			} catch (Exception e) {
				veh.setVehDoc1("NA");
				veh.setVehDoc2("NA");
				veh.setVehDoc3("NA");
				veh.setVehDoc4("NA");
				
			}

			System.out.println("veh " +veh.toString());
			Vehicle vehInsertRes = rest.postForObject(Constants.url + "saveVehicle",veh, Vehicle.class);
			
			if (vehInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

			System.err.println("vehInsertRes " + vehInsertRes.toString());

		} catch (Exception e) {
			System.err.println("EXCE in vehInsertRes " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddVehicle";

	}
	
	@RequestMapping(value = "/editVeh/{vehicleId}", method = RequestMethod.GET)
	public ModelAndView editVeh(HttpServletRequest request, HttpServletResponse response, @PathVariable int vehicleId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("mst/vehicle");

			Vehicle[] vehArray= rest.getForObject(Constants.url + "getAllVehicleList", Vehicle[].class);
			vehList = new ArrayList<Vehicle>(Arrays.asList(vehArray));

			model.addObject("vehList", vehList );

			
			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));
			model.addObject("uomList", uomList);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehId",vehicleId);

			Vehicle editVeh = rest.postForObject(Constants.url + "getVehicleById", map, Vehicle.class);

			model.addObject("title", "Edit Vehicle");
			model.addObject("editVeh",editVeh);
			
			model.addObject("vehImgPath", Constants.VEH_IMG_URL);

		} catch (Exception e) {

			System.err.println("exception In editCon at Master Contr" + e.getMessage());

			e.printStackTrace();

		}
		

		return model;
	}
	
	
	

	@RequestMapping(value = "/deleteVeh/{vehicleId}", method = RequestMethod.GET)
	public String deleteVeh(HttpServletRequest request, HttpServletResponse response, @PathVariable int vehicleId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehId",vehicleId);

			Info errMsg = rest.postForObject(Constants.url + "deleteVehicle", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteCon @MastVeh  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddVehicle";
	}
	
	
	
	@RequestMapping(value = "/deleteRecordofVeh", method = RequestMethod.POST)
	public String deleteRecordofVeh(HttpServletRequest request, HttpServletResponse response) {
		try {

			System.out.println("inside multi veh delete");
			String[] vehIds = request.getParameterValues("vehIds");
			System.out.println("veh ids to del"+vehIds);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < vehIds.length; i++) {
				sb = sb.append(vehIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vehIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiVehicle", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofCon @MstContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddVehicle";
	}

	
	
	//*************************************Vehicle************************************//
	@RequestMapping(value = "/showAddSP", method = RequestMethod.GET)
	public ModelAndView showAddSP(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("mst/subplant");
			model.addObject("isError", isError);
			isError = 0;

			model.addObject("title", "Add Subplant");
			Subplant[] sbArray = rest.getForObject(Constants.url + "getAllSubPlantList",Subplant[].class);
			spList = new ArrayList<Subplant>(Arrays.asList(sbArray));

			model.addObject("spList", spList );
			
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);


		} catch (Exception e) {

			System.err.println("exception In showAddContactor at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	
	
	@RequestMapping(value = "/insertSubplant", method = RequestMethod.POST)
	public String insertSubplant(HttpServletRequest request, HttpServletResponse response) {

		try {

			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());
			System.err.println("Inside insert insertCon method");

			int spId = 0;
			try {
				spId  = Integer.parseInt(request.getParameter("spId"));
			} catch (Exception e) {
				spId   = 0;
			}
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			//String curDate = dateFormat.format(new Date());

			String spName = request.getParameter("spName");
			String spLoc = request.getParameter("spLoc");
			int pid=Integer.parseInt(request.getParameter("plant_id"));

			Subplant sp=new Subplant();
			
			sp.setPlantId(pid);
			sp.setSubplantName(spName);
			sp.setLocation(spLoc);
			sp.setDelStatus(1);
			sp.setSubplantId(spId);
			
			sp.setExBool1(0);
			sp.setExDate1(curDate);
			sp.setExInt1(0);
			sp.setExInt2(0);
			sp.setExVar1("NA");
			sp.setExVar2("NA");
			
		
			
			System.out.println("sp data is "+sp.toString());
			
			
			Subplant conInsertRes = rest.postForObject(Constants.url + "saveSubPlant",sp,Subplant.class);

			if (conInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce in insert Contractor " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddSP";
	}


	@RequestMapping(value = "/editSP/{spId}", method = RequestMethod.GET)
	public ModelAndView editSP(HttpServletRequest request, HttpServletResponse response, @PathVariable int spId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("mst/subplant");

			Subplant[] conArray = rest.getForObject(Constants.url + "getAllSubPlantList", Subplant[].class);
			spList = new ArrayList<Subplant>(Arrays.asList(conArray));

			model.addObject("spList",spList );
			
			
			

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("spId", spId);
			

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Subplant editSP = rest.postForObject(Constants.url + "getSubPlantById", map, Subplant.class);

			model.addObject("title", "Edit Subplant");
			model.addObject("editSP", editSP );

		} catch (Exception e) {

			System.err.println("exception In editCon at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}
	
	@RequestMapping(value = "/deleteSP/{spId}", method = RequestMethod.GET)
	public String deleteSP(HttpServletRequest request, HttpServletResponse response, @PathVariable int spId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("spId", spId);

			Info errMsg = rest.postForObject(Constants.url + "deleteSubPlant", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteCon @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddSP";
	}
	
	
	

	@RequestMapping(value = "/deleteRecordofSP", method = RequestMethod.POST)
	public String deleteRecordofSP(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[]spIds = request.getParameterValues("spIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < spIds.length; i++) {
				sb = sb.append(spIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("spIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiSubPlant", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofCon @MstContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddSP";
	}

	
	
	
}
