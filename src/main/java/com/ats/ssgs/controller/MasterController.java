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

import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
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
import com.ats.ssgs.model.master.Company;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.CustType;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.Setting;
import com.ats.ssgs.model.master.User;

@Controller
@Scope("session")
public class MasterController {

	RestTemplate rest = new RestTemplate();
	List<User> usrList;
	List<Plant> plantList;

	@RequestMapping(value = "/showAddPlant", method = RequestMethod.GET)
	public ModelAndView showAddPlant(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addplant");

			model.addObject("title", "Add Plant");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllUserList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			User[] usrArray = rest.getForObject(Constants.url + "getAllUserList", User[].class);
			usrList = new ArrayList<User>(Arrays.asList(usrArray));

			model.addObject("usrList", usrList);

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);

		} catch (Exception e) {

			System.err.println("exception In showAddPlant at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	// insertPlant

	@RequestMapping(value = "/insertPlant", method = RequestMethod.POST)
	public String insertPlant(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert plant method");

			int plantId = 0;
			try {
				plantId = Integer.parseInt(request.getParameter("plant_id"));
			} catch (Exception e) {
				plantId = 0;
			}

			int compId = Integer.parseInt(request.getParameter("compId"));

			System.err.println("Comp Id " + compId);
			String plantName = request.getParameter("plant_name");

			String telNo = request.getParameter("tel_no");
			String mobNo = request.getParameter("mob_no");
			String faxNo = request.getParameter("fax");
			String email = request.getParameter("email");
			String plantAdd = request.getParameter("plant_add");

			int plantHeadId = Integer.parseInt(request.getParameter("plant_head"));

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			Plant plant = new Plant();
			String na = "NA";
			plant.setCompanyId(compId);
			plant.setDelStatus(0);
			plant.setExDate1(curDate);
			plant.setExDate2(curDate);
			plant.setExVar1(na);
			plant.setExVar2(na);
			plant.setExVar3(na);
			plant.setIsUsed(0);
			plant.setPlantAddress1(plantAdd);
			plant.setPlantAddress2(na);
			plant.setPlantContactNo1(mobNo);
			plant.setPlantContactNo2(mobNo);
			plant.setPlantEmail1(email);

			plant.setPlantEmail2(na);
			plant.setPlantFax1(faxNo);
			plant.setPlantFax2(na);
			plant.setPlantHead(plantHeadId);
			plant.setPlantId(plantId);
			plant.setPlantName(plantName);

			System.err.println("plant " + plant.toString());

			// saveItem

			Plant plantInsertRes = rest.postForObject(Constants.url + "savePlant", plant, Plant.class);
			System.err.println("plantInsertRes " + plantInsertRes.toString());

		} catch (Exception e) {
			System.err.println("EXCE in insertPlant " + e.getMessage());
			e.printStackTrace();

		}
		return null;

	}

	@RequestMapping(value = "/showAddCompany", method = RequestMethod.GET)
	public ModelAndView showAddCompany(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addcompany");

			model.addObject("title", "Add Company");
		} catch (Exception e) {

			System.err.println("exception In showAddCompany at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertCompany", method = RequestMethod.POST)
	public String insertCompany(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert company method");

			int compId = 0;
			try {
				compId = Integer.parseInt(request.getParameter("comp_id"));
			} catch (Exception e) {
				compId = 0;
			}

			String compName = request.getParameter("comp_name");

			System.err.println("Comp Name " + compName);
			String offAdd = request.getParameter("off_add");

			String location = request.getParameter("comp_loc");
			String factAdd = request.getParameter("fact_add");

			String license = request.getParameter("lic_no");
			String panNo = request.getParameter("pan_no");
			String gstNo = request.getParameter("gst_no");
			String mobNo = request.getParameter("mob_no");

			String telNo = request.getParameter("tel_no");
			String faxNo = request.getParameter("fax");
			String email = request.getParameter("email");
			String cinNo = request.getParameter("cin_no");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			Company comp = new Company();
			String NA = "NA";

			comp.setCompanyId(compId);
			comp.setCinNo(cinNo);
			comp.setCompanyId(compId);
			comp.setCompFactAdd(factAdd);
			comp.setCompGstNo(gstNo);
			comp.setCompLicence(license);
			comp.setCompLoc(location);
			comp.setCompLogo(NA);
			comp.setCompName(compName);
			comp.setContactNo1(mobNo);
			comp.setCompOfficeAdd(offAdd);
			comp.setCompOtherAdd(NA);
			comp.setCompPanNo(panNo);
			comp.setContactNo1(mobNo);
			comp.setContactNo2(telNo);
			comp.setDelStatus(0);
			comp.setEmail1(email);
			comp.setFaxNo1(faxNo);

			comp.setCompOtherAdd(NA);
			comp.setEmail2(NA);
			comp.setEmail3(NA);
			comp.setExDate1(curDate);
			comp.setExDate2(curDate);
			comp.setExVar1(NA);
			comp.setExVar2(NA);
			comp.setExVar3(NA);
			comp.setFromDate(curDate);
			comp.setLicence2(NA);
			comp.setToDate(curDate);

			System.err.println("comp " + comp.toString());

			// saveItem

			Company itemInsertRes = rest.postForObject(Constants.url + "saveCompany", comp, Company.class);

		} catch (Exception e) {

			System.err.println("Exc in insertCompany  " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddCompany";

	}

	List<Company> compList;
	List<Cust> custList;
	List<CustType> custTypeList;
	List<Setting> settingList;

	@RequestMapping(value = "/showCompList", method = RequestMethod.GET)
	public ModelAndView showCompList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/compList");
			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("title", "Company List");
			model.addObject("compList", compList);
		} catch (Exception e) {

			System.err.println("exception In showCompList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/editCompany/{compId}", method = RequestMethod.GET)
	public ModelAndView showEditCompany(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int compId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addcompany");
			// getCompByCompanyId

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("companyId", compId);

			Company editComp = rest.postForObject(Constants.url + "getCompByCompanyId", map, Company.class);

			model.addObject("title", "Edit Company");
			model.addObject("editComp", editComp);

		} catch (Exception e) {

			System.err.println("exception In editCompany at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteCompany/{compId}", method = RequestMethod.GET)
	public String deleteCategoryMethod(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int compId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("companyId", compId);

			Info errMsg = rest.postForObject(Constants.url + "deleteCompany", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteCompany @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showCompList";
	}

	@RequestMapping(value = "/showAddProject", method = RequestMethod.GET)
	public ModelAndView showAddProject(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addproject");

			model.addObject("title", "Add Project");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Cust[] custArray = rest.getForObject(Constants.url + "getAllCustList", Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			System.err.println("custList In showAddPlant at Master Contr" + custList);
			model.addObject("custList", custList);

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);

		} catch (Exception e) {

			System.err.println("exception In showAddPlant at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertProject", method = RequestMethod.POST)
	public String insertProject(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertProject method");

			int projId = 0;
			try {
				projId = Integer.parseInt(request.getParameter("proj_id"));
			} catch (Exception e) {
				projId = 0;
			}

			String projName = request.getParameter("proj_name");

			System.err.println("projName Name " + projName);

			String projLoc = request.getParameter("proj_loc");

			int custId = Integer.parseInt(request.getParameter("cust_id"));
			int plantId = Integer.parseInt(request.getParameter("plant_id"));

			String startDate = request.getParameter("start_date");
			String endDate = request.getParameter("end_date");

			Project proj = new Project();

			proj.setCustId(custId);
			proj.setDelStatus(0);
			proj.setEndDate(DateConvertor.convertToYMD(endDate));
			proj.setIsUsed(0);
			proj.setLocation(projLoc);
			proj.setProjId(projId);
			proj.setProjName(projName);
			proj.setStartDate(DateConvertor.convertToYMD(startDate));

			Project projInsertRes = rest.postForObject(Constants.url + "saveProject", proj, Project.class);

		} catch (Exception e) {

			System.err.println("Exce in insert Project " + e.getMessage());
			e.printStackTrace();

		}
		return null;
	}

	// showAddCustomer

	@RequestMapping(value = "/showAddCustomer", method = RequestMethod.GET)
	public ModelAndView showAddCustomer(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addcust");

			model.addObject("title", "Add Customer");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			List<Integer> keyList=new ArrayList<>();
			
			keyList.add(1);
			keyList.add(2);
			keyList.add(3);
			keyList.add(4);
			
			

			map.add("keyList", "1,2,3,4");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList",map, Setting[].class);
			settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);

			
			

			CustType[] custTypeArray = rest.getForObject(Constants.url + "getAllCustTypeList", CustType[].class);
			custTypeList = new ArrayList<CustType>(Arrays.asList(custTypeArray));
			System.err.println("custList In showAddPlant at Master Contr" + custTypeList);
			model.addObject("custTypeList", custTypeList);

		} catch (Exception e) {

			System.err.println("exception In showAddCustomer at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	// insertCust
	@RequestMapping(value = "/insertCust", method = RequestMethod.POST)
	public String insertCust(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertProject method");

			int custId = 0;
			try {
				custId = Integer.parseInt(request.getParameter("cust_id"));
			} catch (Exception e) {
				custId = 0;
			}

			int plantId = Integer.parseInt(request.getParameter("plant_id"));

			int custType = Integer.parseInt(request.getParameter("cust_type"));

			String custName = request.getParameter("cust_name");

			System.err.println("cust Name " + custName);
			
			String mobNo = request.getParameter("mob_no");
//

			String refName = request.getParameter("ref_name");
			String email = request.getParameter("email");

			String panNo = request.getParameter("pan_no");

			String gstNo = request.getParameter("gst_no");

			int custCate = Integer.parseInt(request.getParameter("cust_cate"));
			System.err.println("custCate " + custCate);
			String custAdd = request.getParameter("cust_add");

			String dob = request.getParameter("dob");

			String custCode = request.getParameter("cust_code");

			int isChequeRecv = Integer.parseInt(request.getParameter("cheque"));
			
			System.err.println("isChequeRecv " + isChequeRecv);

			String chequeRemark = request.getParameter("cheque_remark");
			String contPerName = request.getParameter("cont_per_name");
			String contPerMob = request.getParameter("con_per_mob");
			String telNo = request.getParameter("tel_no");
			String custVendor = request.getParameter("cust_vendor");

			int sameState = Integer.parseInt(request.getParameter("state"));

			System.err.println("refName Name " + refName);
			String dateOfReg=request.getParameter("reg_date");
			
			if(refName.equals("")) {
				System.err.println("null ref name");
				
				refName="NA";
				
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			Cust cust=new Cust();
			
			cust.setChequeRemark(chequeRemark);
			cust.setContactPerMob(contPerMob);
			cust.setContactPerName(contPerName);
			cust.setCustAddress(custAdd);
			
			cust.setCustCat(custCate);
			cust.setCustCode(custCode);
			cust.setCustDob(DateConvertor.convertToYMD(dob));
			cust.setCustEmail(email);
			cust.setCustGstNo(gstNo);
			
			
			cust.setCustId(custId);
			cust.setCustLandline(telNo);
			cust.setCustMobNo(mobNo);
			cust.setCustName(custName);
			
			cust.setCustPanNo(panNo);
			cust.setCustType(custType);
			
			cust.setCustVendor(Integer.parseInt(custVendor));
			
			if( custId==0) {
			cust.setDateOfReg(curDate);
			}else {
				cust.setDateOfReg(DateConvertor.convertToYMD(dateOfReg));
			}
			cust.setDelStatus(0);
			cust.setExDate1(curDate);
			
			cust.setExDate2(curDate);
			
			cust.setIsChequeRcvd(isChequeRecv);
			
			cust.setChequeRemark(chequeRemark);
			
			//cust.setToken("NA");
			cust.setRespPerson(refName);
			cust.setPlantId(plantId);
			cust.setIsSameState(sameState);
			
			Cust custInsertRes = rest.postForObject(Constants.url + "saveCust", cust, Cust.class);

			Project proj=new Project();
			
			proj.setCustId(custInsertRes.getCustId());
			proj.setDelStatus(0);
			proj.setIsUsed(0);
			proj.setLocation(custAdd);
			proj.setProjName(custName);
			proj.setStartDate(curDate);
			proj.setEndDate(curDate);
			
			Project projInsertRes = rest.postForObject(Constants.url + "saveProject", proj, Project.class);


		} catch (Exception e) {
			
			System.err.println("Exc in saving customet ->project  " +e.getMessage());
			e.printStackTrace();
		}
		return null;

	}
	
	
	@RequestMapping(value = "/showAddItem", method = RequestMethod.GET)
	public ModelAndView showAddItem(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/additem");

			model.addObject("title", "Add Item");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			List<Integer> keyList=new ArrayList<>();
			
			keyList.add(1);
			keyList.add(2);
			keyList.add(3);
			keyList.add(4);
			

			map.add("keyList", "1,2,3,4");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList",map, Setting[].class);
			settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);

			CustType[] custTypeArray = rest.getForObject(Constants.url + "getAllCustTypeList", CustType[].class);
			custTypeList = new ArrayList<CustType>(Arrays.asList(custTypeArray));
			System.err.println("custList In showAddPlant at Master Contr" + custTypeList);
			model.addObject("custTypeList", custTypeList);

		} catch (Exception e) {

			System.err.println("exception In showAddItem at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}
	
	
	@RequestMapping(value = "/insertItem", method = RequestMethod.POST)
	public String insertItem(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertProject method");

			int itemId = 0;
			try {
				itemId = Integer.parseInt(request.getParameter("item_id"));
			} catch (Exception e) {
				itemId = 0;
			}

			int plantId = Integer.parseInt(request.getParameter("plant_id"));

			int custType = Integer.parseInt(request.getParameter("cust_type"));

			String custName = request.getParameter("cust_name");

			System.err.println("cust Name " + custName);
			
			String mobNo = request.getParameter("mob_no");
//

			String refName = request.getParameter("ref_name");
			String email = request.getParameter("email");

			String panNo = request.getParameter("pan_no");

			String gstNo = request.getParameter("gst_no");

			int custCate = Integer.parseInt(request.getParameter("cust_cate"));
			System.err.println("custCate " + custCate);
			String custAdd = request.getParameter("cust_add");

			String dob = request.getParameter("dob");

			String custCode = request.getParameter("cust_code");

		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
