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
import com.ats.ssgs.model.master.Dept;
import com.ats.ssgs.model.master.GetCust;
import com.ats.ssgs.model.master.GetItem;
import com.ats.ssgs.model.master.GetPlant;
import com.ats.ssgs.model.master.GetProject;
import com.ats.ssgs.model.master.GetUser;
import com.ats.ssgs.model.master.GetVendor;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.ItemType;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.Setting;
import com.ats.ssgs.model.master.Tax;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.master.Vendor;

@Controller
@Scope("session")
public class MasterController {

	RestTemplate rest = new RestTemplate();
	List<User> usrList;
	List<Plant> plantList;
	List<Vendor> vendList;
	List<Dept> deptList;
	List<ItemType> itemTypeList;

	List<Tax> getTaxList;
	List<GetVendor> getVendList;

	@RequestMapping(value = "/showAddVendor", method = RequestMethod.GET)
	public ModelAndView showAddVendor(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addvendor");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			model.addObject("title", "Add Vendor");

		} catch (Exception e) {

			System.err.println("exception In showAddVendor at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertVendor", method = RequestMethod.POST)
	public String insertVendor(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert Vendor method");

			int vendId = 0;
			try {
				vendId = Integer.parseInt(request.getParameter("vendId"));
			} catch (Exception e) {
				vendId = 0;
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());

			int vendCreditDays = Integer.parseInt(request.getParameter("vendCreditDays"));

			int plantId = Integer.parseInt(request.getParameter("plantId"));

			float vendCreditLimit = Float.parseFloat(request.getParameter("vendCreditLimit"));
			String vendCompName = request.getParameter("vendCompName");
			String vendContactName = request.getParameter("vendContactName");

			String vendContact1 = request.getParameter("vendContact1");
			String vendContact2 = request.getParameter("vendContact2");

			String vendEmail1 = request.getParameter("vendEmail1");

			String vendGst = request.getParameter("vendGst");
			String vendPan = request.getParameter("vendPan");

			int vendType = Integer.parseInt(request.getParameter("vendType"));
			int isSameState = Integer.parseInt(request.getParameter("isSameState"));

			String vendCity = request.getParameter("vendCity");
			String vendState = request.getParameter("vendState");

			Vendor vend = new Vendor();
			vend.setDelStatus(1);
			vend.setExBool1(0);
			vend.setExBool2(0);
			vend.setExBool3(0);
			vend.setExDate1(curDate);
			vend.setExDate2(curDate);
			vend.setExInt1(0);
			vend.setExInt2(0);
			vend.setExInt3(0);
			vend.setExVar1("NA");
			vend.setExVar2("NA");
			vend.setPlantId(plantId);
			vend.setVendType(vendType);
			vend.setVendState(vendState);
			vend.setVendPan(vendPan);
			vend.setVendIsUsed(1);
			vend.setVendId(vendId);
			vend.setVendGst(vendGst);
			vend.setVendEmail3("NA");
			vend.setVendEmail2("NA");
			vend.setVendEmail1(vendEmail1);
			vend.setVendContactName(vendContactName);
			vend.setVendCreditDays(vendCreditDays);
			vend.setVendContact1(vendContact1);
			vend.setVendContact2(vendContact2);
			vend.setVendCompName(vendCompName);
			vend.setVendCity(vendCity);
			vend.setIsSameState(isSameState);
			vend.setVendCreditLimit(vendCreditLimit);
			vend.setVendCreditDays(vendCreditDays);

			Vendor vendInsertRes = rest.postForObject(Constants.url + "saveVendor", vend, Vendor.class);
			System.err.println("plantInsertRes " + vendInsertRes.toString());

		} catch (Exception e) {
			System.err.println("EXCE in vendInsertRes " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddVendor";

	}

	@RequestMapping(value = "/showVendorList", method = RequestMethod.GET)
	public ModelAndView showVendorList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/vendorList");
			GetVendor[] vendArray = rest.getForObject(Constants.url + "getAllVendorList", GetVendor[].class);
			getVendList = new ArrayList<GetVendor>(Arrays.asList(vendArray));

			model.addObject("title", "Vendor List");
			model.addObject("vendList", getVendList);
		} catch (Exception e) {

			System.err.println("exception In showVendorList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/editVendor/{vendId}", method = RequestMethod.GET)
	public ModelAndView editVendor(HttpServletRequest request, HttpServletResponse response, @PathVariable int vendId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/editvendor");
			model.addObject("title", "Edit Vendor");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			GetVendor[] vendArray = rest.getForObject(Constants.url + "getAllVendorList", GetVendor[].class);
			getVendList = new ArrayList<GetVendor>(Arrays.asList(vendArray));

			model.addObject("vendList", getVendList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vendId", vendId);

			Vendor editVend = rest.postForObject(Constants.url + "getVendorByVendorId", map, Vendor.class);

			model.addObject("editVend", editVend);

		} catch (Exception e) {

			System.err.println("exception In editVendor at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteVendor/{vendId}", method = RequestMethod.GET)
	public String deleteVendor(HttpServletRequest request, HttpServletResponse response, @PathVariable int vendId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vendId", vendId);

			Info errMsg = rest.postForObject(Constants.url + "deleteVendor", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteVendor @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showVendorList";
	}

	@RequestMapping(value = "/showAddTax", method = RequestMethod.GET)
	public ModelAndView showAddTax(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addtax");

			model.addObject("title", "Add Tax");

		} catch (Exception e) {

			System.err.println("exception In showAddTax at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertTax", method = RequestMethod.POST)
	public String insertTax(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert Tax method");

			int taxId = 0;
			try {
				taxId = Integer.parseInt(request.getParameter("taxId"));
			} catch (Exception e) {
				taxId = 0;
			}

			int sortNo = Integer.parseInt(request.getParameter("sortNo"));

			float cess = Float.parseFloat(request.getParameter("cess"));
			float cgst = Float.parseFloat(request.getParameter("cgst"));
			float igst = Float.parseFloat(request.getParameter("igst"));
			float sgst = Float.parseFloat(request.getParameter("sgst"));
			float totalTaxPer = Float.parseFloat(request.getParameter("totalTaxPer"));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());
			String hsnCode = request.getParameter("hsnCode");
			String taxName = request.getParameter("taxName");

			Tax tax = new Tax();
			tax.setCess(cess);
			tax.setCgst(cgst);
			tax.setDelStatus(1);
			tax.setExBool1(0);
			tax.setExBool2(0);
			tax.setExBool3(0);
			tax.setExDate1(curDate);
			tax.setExDate2(curDate);
			tax.setExInt1(0);
			tax.setExInt2(0);
			tax.setExInt3(0);
			tax.setExVar1("NA");
			tax.setExVar2("NA");
			tax.setExVar3("NA");
			tax.setHsnCode(hsnCode);
			tax.setIgst(igst);
			tax.setTotalTaxPer(totalTaxPer);
			tax.setTaxName(taxName);
			tax.setSortNo(sortNo);
			tax.setSgst(sgst);
			tax.setTaxId(taxId);

			// saveTax

			Tax plantInsertRes = rest.postForObject(Constants.url + "saveTax", tax, Tax.class);
			System.err.println("plantInsertRes " + plantInsertRes.toString());

		} catch (Exception e) {
			System.err.println("EXCE in insertPlant " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddTax";

	}

	@RequestMapping(value = "/showTaxList", method = RequestMethod.GET)
	public ModelAndView showTaxList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/taxList");
			Tax[] taxArray = rest.getForObject(Constants.url + "getTaxList", Tax[].class);
			getTaxList = new ArrayList<Tax>(Arrays.asList(taxArray));

			model.addObject("title", "Tax List");
			model.addObject("taxList", getTaxList);
		} catch (Exception e) {

			System.err.println("exception In showTaxList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/editTax/{taxId}", method = RequestMethod.GET)
	public ModelAndView editTax(HttpServletRequest request, HttpServletResponse response, @PathVariable int taxId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addtax");
			// getTaxByTaxId
			Tax[] taxArray = rest.getForObject(Constants.url + "getTaxList", Tax[].class);
			getTaxList = new ArrayList<Tax>(Arrays.asList(taxArray));

			model.addObject("taxList", getTaxList);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("taxId", taxId);

			Tax editTax = rest.postForObject(Constants.url + "getTaxByTaxId", map, Tax.class);

			model.addObject("title", "Edit Tax");
			model.addObject("editTax", editTax);

		} catch (Exception e) {

			System.err.println("exception In editTax at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteTax/{taxId}", method = RequestMethod.GET)
	public String deleteTax(HttpServletRequest request, HttpServletResponse response, @PathVariable int taxId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("taxId", taxId);

			Info errMsg = rest.postForObject(Constants.url + "deleteTax", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteTax @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showTaxList";
	}

	@RequestMapping(value = "/showAddPlant", method = RequestMethod.GET)
	public ModelAndView showAddPlant(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addplant");

			model.addObject("title", "Add Plant");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
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
			plant.setDelStatus(1);
			plant.setExDate1(curDate);
			plant.setExDate2(curDate);
			plant.setExVar1(na);
			plant.setExVar2(na);
			plant.setExVar3(na);
			plant.setIsUsed(1);
			plant.setPlantAddress1(plantAdd);
			plant.setPlantAddress2(na);
			plant.setPlantContactNo1(mobNo);
			plant.setPlantContactNo2(telNo);
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
		return "redirect:/showAddPlant";

	}

	List<GetPlant> getPlantList;

	@RequestMapping(value = "/showPlantList", method = RequestMethod.GET)
	public ModelAndView showPlantList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/plantList");
			GetPlant[] plantArray = rest.getForObject(Constants.url + "getAllCompanyPlantList", GetPlant[].class);
			getPlantList = new ArrayList<GetPlant>(Arrays.asList(plantArray));

			model.addObject("title", "Plant List");
			model.addObject("plantList", getPlantList);
		} catch (Exception e) {

			System.err.println("exception In showCompList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/editPlant/{plantId}", method = RequestMethod.GET)
	public ModelAndView editPlant(HttpServletRequest request, HttpServletResponse response, @PathVariable int plantId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addplant");
			// getPlantCompanyByPlantId

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);

			User[] usrArray = rest.getForObject(Constants.url + "getAllUserList", User[].class);
			usrList = new ArrayList<User>(Arrays.asList(usrArray));

			model.addObject("usrList", usrList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);

			Plant editPlant = rest.postForObject(Constants.url + "getPlantCompanyByPlantId", map, Plant.class);

			model.addObject("title", "Edit Plant");
			model.addObject("editPlant", editPlant);

		} catch (Exception e) {

			System.err.println("exception In editPlant at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deletePlant/{plantId}", method = RequestMethod.GET)
	public String deletePlant(HttpServletRequest request, HttpServletResponse response, @PathVariable int plantId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("plantId", plantId);

			Info errMsg = rest.postForObject(Constants.url + "deletePlant", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteCompany @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showPlantList";
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

			comp.setCompLogo(NA);
			comp.setCompName(compName);
			comp.setContactNo1(mobNo);
			comp.setCompOfficeAdd(offAdd);
			comp.setCompOtherAdd(NA);
			comp.setCompPanNo(panNo);
			comp.setContactNo1(mobNo);

			comp.setDelStatus(1);
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

			try {
				comp.setCompLoc(location);
				comp.setContactNo2(telNo);

			} catch (Exception e) {
				comp.setCompLoc("NA");
				comp.setContactNo2("NA");
			}

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
	private ArrayList<Uom> uomList;
	private ArrayList<Tax> taxList;

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

			String contactPerName = request.getParameter("contactPerName");
			String contactPerMob = request.getParameter("contactPerMob");

			String address = request.getParameter("address");
			String pincode = request.getParameter("pincode");
			String km = request.getParameter("km");

			Project proj = new Project();

			proj.setCustId(custId);
			proj.setDelStatus(1);
			proj.setEndDate(DateConvertor.convertToYMD(endDate));
			proj.setIsUsed(1);
			proj.setLocation(projLoc);
			proj.setProjId(projId);
			proj.setProjName(projName);
			proj.setStartDate(DateConvertor.convertToYMD(startDate));
			proj.setContactPerMob(contactPerMob);
			proj.setContactPerName(contactPerName);
			proj.setKm(Float.parseFloat(km));
			proj.setAddress(address);
			proj.setPincode(pincode);

			Project projInsertRes = rest.postForObject(Constants.url + "saveProject", proj, Project.class);

		} catch (Exception e) {

			System.err.println("Exce in insert Project " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddProject";

	}

	@RequestMapping(value = "/editProject/{projId}", method = RequestMethod.GET)
	public ModelAndView editProject(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int projId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addproject");
			// getProjectByProjId

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Cust[] custArray = rest.getForObject(Constants.url + "getAllCustList", Cust[].class);
			custList = new ArrayList<Cust>(Arrays.asList(custArray));
			System.err.println("custList In showAddPlant at Master Contr" + custList);
			model.addObject("custList", custList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("projId", projId);

			Project editPro = rest.postForObject(Constants.url + "getProjectByProjId", map, Project.class);

			model.addObject("title", "Edit Project");
			model.addObject("editPro", editPro);

		} catch (Exception e) {

			System.err.println("exception In editProject at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	List<GetProject> proList;

	@RequestMapping(value = "/showProjectList", method = RequestMethod.GET)
	public ModelAndView showProjectList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/projectlist");
			GetProject[] proArray = rest.getForObject(Constants.url + "getAllProList", GetProject[].class);
			proList = new ArrayList<GetProject>(Arrays.asList(proArray));

			model.addObject("title", "Project List");
			model.addObject("proList", proList);
		} catch (Exception e) {

			System.err.println("exception In showProjectList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/deleteProject/{projId}", method = RequestMethod.GET)
	public String deleteProject(HttpServletRequest request, HttpServletResponse response, @PathVariable int projId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("projId", projId);

			Info errMsg = rest.postForObject(Constants.url + "deleteProject", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteDept @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showProjectList";
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

			List<Integer> keyList = new ArrayList<>();

			keyList.add(1);
			keyList.add(2);
			keyList.add(3);
			keyList.add(4);

			map.add("keyList", "1,2,3,4");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map, Setting[].class);
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

			System.err.println("Inside insert insertCust method");

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

			String ownerName = request.getParameter("ownerName");
			String accPerson = request.getParameter("accPerson");
			String accPerMob = request.getParameter("accPerMob");

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

			String creaditDays = request.getParameter("creaditDays");

			String creaditLimit = request.getParameter("creaditLimit");

			String km = request.getParameter("km");
			String pincode = request.getParameter("pincode");

			System.err.println("refName Name " + refName);
			String dateOfReg = request.getParameter("reg_date");

			if (refName.equals("")) {
				System.err.println("null ref name");

				refName = "NA";

			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			Cust cust = new Cust();

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
			cust.setOwnerName(ownerName);
			cust.setAccPerMob(accPerMob);
			cust.setAccPerson(accPerson);
			cust.setPincode(pincode);
			cust.setKm(Float.parseFloat(km));

			cust.setCreaditDays(Float.parseFloat(creaditDays));

			cust.setCreaditLimit(Float.parseFloat(creaditLimit));

			cust.setCustVendor(Integer.parseInt(custVendor));

			if (custId == 0) {
				cust.setDateOfReg(curDate);
			} else {
				cust.setDateOfReg(DateConvertor.convertToYMD(dateOfReg));
			}
			cust.setDelStatus(1);
			cust.setExDate1(curDate);

			cust.setExDate2(curDate);

			cust.setIsChequeRcvd(isChequeRecv);

			cust.setChequeRemark(chequeRemark);

			// cust.setToken("NA");
			cust.setRespPerson(refName);
			cust.setPlantId(plantId);
			cust.setIsSameState(sameState);

			Cust custInsertRes = rest.postForObject(Constants.url + "saveCust", cust, Cust.class);

			Project proj = new Project();

			proj.setCustId(custInsertRes.getCustId());
			proj.setDelStatus(1);
			proj.setIsUsed(1);
			proj.setLocation(custAdd);
			proj.setProjName(custName);
			proj.setStartDate(curDate);
			proj.setEndDate(curDate);
			proj.setContactPerMob(mobNo);
			proj.setContactPerName(custName);
			proj.setAddress(custAdd);
			proj.setKm(Float.parseFloat(km));

			Project projInsertRes = rest.postForObject(Constants.url + "saveProject", proj, Project.class);

		} catch (Exception e) {

			System.err.println("Exc in saving customet ->project  " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showAddCustomer";

	}

	List<GetCust> getCustList;

	@RequestMapping(value = "/showCustList", method = RequestMethod.GET)
	public ModelAndView showCustList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/custList");

			GetCust[] custArray = rest.getForObject(Constants.url + "getAllCustomerList", GetCust[].class);
			getCustList = new ArrayList<GetCust>(Arrays.asList(custArray));

			model.addObject("title", "Customer List");
			model.addObject("custList", getCustList);

		} catch (Exception e) {

			System.err.println("exception In showCustList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/editCust/{custId}", method = RequestMethod.GET)
	public ModelAndView editCust(HttpServletRequest request, HttpServletResponse response, @PathVariable int custId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/editCust");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			CustType[] custTypeArray = rest.getForObject(Constants.url + "getAllCustTypeList", CustType[].class);
			custTypeList = new ArrayList<CustType>(Arrays.asList(custTypeArray));

			model.addObject("custTypeList", custTypeList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			List<Integer> keyList = new ArrayList<>();

			keyList.add(1);
			keyList.add(2);
			keyList.add(3);
			keyList.add(4);

			map.add("keyList", "1,2,3,4");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map, Setting[].class);
			settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);

			MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<String, Object>();

			map1.add("custId", custId);

			Cust editCust = rest.postForObject(Constants.url + "getCustByCustId", map1, Cust.class);

			model.addObject("title", "Edit Customer");
			model.addObject("editCust", editCust);

		} catch (Exception e) {

			System.err.println("exception In editCust at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteCust/{custId}", method = RequestMethod.GET)
	public String deleteCustMethod(HttpServletRequest request, HttpServletResponse response, @PathVariable int custId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", custId);

			Info errMsg = rest.postForObject(Constants.url + "deleteCust", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteCust @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showCustList";
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

			ItemType[] itemArray = rest.getForObject(Constants.url + "getAllItemTypeList", ItemType[].class);
			itemTypeList = new ArrayList<ItemType>(Arrays.asList(itemArray));

			model.addObject("itemTypeList", itemTypeList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			List<Integer> keyList = new ArrayList<>();

			keyList.add(1);
			keyList.add(2);
			keyList.add(3);
			keyList.add(4);

			map.add("keyList", "1,2,3,4");

			Setting[] settArray = rest.postForObject(Constants.url + "getSettingValueByKeyList", map, Setting[].class);
			settingList = new ArrayList<Setting>(Arrays.asList(settArray));

			model.addObject("settingList", settingList);

			CustType[] custTypeArray = rest.getForObject(Constants.url + "getAllCustTypeList", CustType[].class);
			custTypeList = new ArrayList<CustType>(Arrays.asList(custTypeArray));
			System.err.println("custList In showAddPlant at Master Contr" + custTypeList);
			model.addObject("custTypeList", custTypeList);

			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));
			model.addObject("uomList", uomList);

			Tax[] taxArray = rest.getForObject(Constants.url + "getTaxList", Tax[].class);
			taxList = new ArrayList<Tax>(Arrays.asList(taxArray));
			model.addObject("taxList", taxList);

			Vendor[] vendorArray = rest.getForObject(Constants.url + "getAllVendList", Vendor[].class);
			vendList = new ArrayList<Vendor>(Arrays.asList(vendorArray));
			model.addObject("vendList", vendList);

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

			int itemType = Integer.parseInt(request.getParameter("item_type"));

			String itemName = request.getParameter("item_name");

			System.err.println("item Name " + itemName);

			String itemCode = request.getParameter("item_code");
//
			int uomId = Integer.parseInt(request.getParameter("uomId"));

			int taxId = Integer.parseInt(request.getParameter("taxId"));

			String shortName = request.getParameter("short_name");
			String rate = request.getParameter("rate");

			String actWeight = request.getParameter("act_weight");

			String baseWeight = request.getParameter("base_weight");

			String[] vendorIds = request.getParameterValues("vendor_ids");

			String dispLimit = request.getParameter("disp_limit");

			String minStock = request.getParameter("min_stock");
			String maxStock = request.getParameter("max_stock");
			String rolStock = request.getParameter("rol_stock");

			String pminStock = request.getParameter("pmin_stock");
			String pmaxStock = request.getParameter("pmax_stock");
			String prolStock = request.getParameter("prol_stock");
			String freightRate = request.getParameter("freight_rate");
			String royaltyRate = request.getParameter("royalty_rate");

			int isCritItem = Integer.parseInt(request.getParameter("is_crit"));

			int sortNo = Integer.parseInt(request.getParameter("sort_no"));

			Item item = new Item();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < vendorIds.length; i++) {
				sb = sb.append(vendorIds[i] + ",");

			}

			String vendors = sb.toString();
			vendors = vendors.substring(0, vendors.length() - 1);
			System.out.println("vendors" + vendors);

			item.setActualWeight(Float.parseFloat(actWeight));
			item.setBaseWeight(Float.parseFloat(baseWeight));
			item.setDelStatus(1);
			item.setDispatchLimit(Float.parseFloat(dispLimit));
			item.setExDate1(curDate);
			item.setExDate2(curDate);
			item.setIsCritical(isCritItem);
			item.setItemCode(itemCode);
			item.setItemId(itemId);
			item.setItemImage("NA");
			item.setItemIsUsed(1);
			item.setItemName(itemName);

			item.setItemRate1(Float.parseFloat(rate));
			item.setItemRate2(Float.parseFloat(rate));
			item.setItemRate3(Float.parseFloat(rate));
			item.setItemRate4(Float.parseFloat(rate));

			item.setItemType(itemType);
			item.setMaxStock(Float.parseFloat(maxStock));
			item.setMinStock(Float.parseFloat(minStock));
			item.setPlantId(plantId);
			item.setPlantMaxStock(Float.parseFloat(pmaxStock));

			item.setPlantMinStock(Float.parseFloat(pminStock));

			item.setPlantRolStock(Float.parseFloat(prolStock));
			item.setRolStock(Float.parseFloat(rolStock));
			item.setShortName(shortName);
			item.setSortNo(sortNo);
			item.setTaxId(taxId);
			item.setUomId(uomId);
			item.setVendorIds(vendors);

			item.setExVar1("NA");
			item.setExVar2("NA");
			item.setExVar3("NA");
			item.setLength(0);
			item.setWidth(0);
			item.setHeight(0);
			item.setItemLocation("NA");
			item.setFreightRate(Float.parseFloat(freightRate));
			item.setRoyaltyRate(Float.parseFloat(royaltyRate));

			Item itemInsertRes = rest.postForObject(Constants.url + "saveItem", item, Item.class);

		} catch (Exception e) {

			System.err.println("Exce in item Insert Res " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddItem";
	}

	List<GetItem> getItemList;

	@RequestMapping(value = "/showItemList", method = RequestMethod.GET)
	public ModelAndView showItemList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/itemList");
			GetItem[] itemArray = rest.getForObject(Constants.url + "getAllItemList", GetItem[].class);
			getItemList = new ArrayList<GetItem>(Arrays.asList(itemArray));

			model.addObject("title", "Item List");
			model.addObject("itemList", getItemList);

			ItemType[] itemTypeArray = rest.getForObject(Constants.url + "getAllItemTypeList", ItemType[].class);
			itemTypeList = new ArrayList<ItemType>(Arrays.asList(itemTypeArray));

			model.addObject("itemTypeList", itemTypeList);

		} catch (Exception e) {

			System.err.println("exception In showCustList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/editItem/{itemId}", method = RequestMethod.GET)
	public ModelAndView editItem(HttpServletRequest request, HttpServletResponse response, @PathVariable int itemId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/editItem");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			ItemType[] itemArray = rest.getForObject(Constants.url + "getAllItemTypeList", ItemType[].class);
			itemTypeList = new ArrayList<ItemType>(Arrays.asList(itemArray));

			model.addObject("itemTypeList", itemTypeList);

			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));
			model.addObject("uomList", uomList);

			Tax[] taxArray = rest.getForObject(Constants.url + "getTaxList", Tax[].class);
			taxList = new ArrayList<Tax>(Arrays.asList(taxArray));
			model.addObject("taxList", taxList);

			Vendor[] vendorArray = rest.getForObject(Constants.url + "getAllVendList", Vendor[].class);
			vendList = new ArrayList<Vendor>(Arrays.asList(vendorArray));
			model.addObject("vendList", vendList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("itemId", itemId);

			Item editItem = rest.postForObject(Constants.url + "getItemByItemId", map, Item.class);

			model.addObject("title", "Edit Item");
			model.addObject("editItem", editItem);

		} catch (Exception e) {

			System.err.println("exception In editItem at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteItem/{itemId}", method = RequestMethod.GET)
	public String deleteItem(HttpServletRequest request, HttpServletResponse response, @PathVariable int itemId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("itemId", itemId);

			Info errMsg = rest.postForObject(Constants.url + "deleteItem", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteItem @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showItemList";
	}

	@RequestMapping(value = "/showAddDept", method = RequestMethod.GET)
	public ModelAndView showAddDept(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/adddept");
			Dept[] deptArray = rest.getForObject(Constants.url + "getAllDeptList", Dept[].class);
			deptList = new ArrayList<Dept>(Arrays.asList(deptArray));

			model.addObject("deptList", deptList);
			model.addObject("title", "Add Dept");

		} catch (Exception e) {

			System.err.println("exception In showAddDept at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertDept", method = RequestMethod.POST)
	public String insertDept(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertDept method");

			int deptId = 0;
			try {
				deptId = Integer.parseInt(request.getParameter("deptId"));
			} catch (Exception e) {
				deptId = 0;
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());

			String deptName = request.getParameter("deptName");

			String sortNo = request.getParameter("sortNo");

			Dept dept = new Dept();
			dept.setDelStatus(1);
			dept.setDeptId(deptId);
			dept.setDeptName(deptName);
			dept.setExBool1(0);
			dept.setExBool2(0);
			dept.setExDate1(curDate);
			dept.setExDate2(curDate);
			dept.setExInt1(0);
			dept.setExInt2(0);
			dept.setSortNo(Integer.parseInt(sortNo));
			dept.setIsUsed(1);
			dept.setExVar1("NA");
			dept.setExVar2("NA");

			Dept deptInsertRes = rest.postForObject(Constants.url + "saveDept", dept, Dept.class);

		} catch (Exception e) {

			System.err.println("Exce in insert Dept " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddDept";
	}

	@RequestMapping(value = "/editDept/{deptId}", method = RequestMethod.GET)
	public ModelAndView editDept(HttpServletRequest request, HttpServletResponse response, @PathVariable int deptId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/adddept");

			Dept[] deptArray = rest.getForObject(Constants.url + "getAllDeptList", Dept[].class);
			deptList = new ArrayList<Dept>(Arrays.asList(deptArray));

			model.addObject("deptList", deptList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("deptId", deptId);

			Dept editDept = rest.postForObject(Constants.url + "getDeptByDeptId", map, Dept.class);

			model.addObject("title", "Edit Dept");
			model.addObject("editDept", editDept);

		} catch (Exception e) {

			System.err.println("exception In editDept at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteDept/{deptId}", method = RequestMethod.GET)
	public String deleteDept(HttpServletRequest request, HttpServletResponse response, @PathVariable int deptId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("deptId", deptId);

			Info errMsg = rest.postForObject(Constants.url + "deleteDept", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteDept @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddDept";
	}

	@RequestMapping(value = "/showAddUom", method = RequestMethod.GET)
	public ModelAndView showAddUom(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/adduom");
			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

			model.addObject("uomList", uomList);
			model.addObject("title", "Add Uom");

		} catch (Exception e) {

			System.err.println("exception In showAddUom at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertUom", method = RequestMethod.POST)
	public String insertUom(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertUom method");

			int uomId = 0;
			try {
				uomId = Integer.parseInt(request.getParameter("uomId"));
			} catch (Exception e) {
				uomId = 0;
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());

			String uomName = request.getParameter("uomName");
			String uomShortName = request.getParameter("uomShortName");

			String sortNo = request.getParameter("sortNo");

			Uom uom = new Uom();
			uom.setDelStatus(1);
			uom.setUomName(uomName);
			uom.setSortNo(Integer.parseInt(sortNo));
			uom.setUomShortName(uomShortName);
			uom.setExBool1(0);
			uom.setExBool2(0);
			uom.setExBool3(0);
			uom.setExDate1(curDate);
			uom.setExDate2(curDate);
			uom.setExInt1(0);
			uom.setExInt2(0);
			uom.setExInt3(0);

			uom.setExVar1("NA");
			uom.setExVar2("NA");
			uom.setExVar3("NA");

			Uom uomInsertRes = rest.postForObject(Constants.url + "saveUom", uom, Uom.class);

		} catch (Exception e) {

			System.err.println("Exce in insert Uom " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddUom";
	}

	@RequestMapping(value = "/editUom/{uomId}", method = RequestMethod.GET)
	public ModelAndView editUom(HttpServletRequest request, HttpServletResponse response, @PathVariable int uomId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/adduom");

			Uom[] uomArray = rest.getForObject(Constants.url + "getAllUomList", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uomArray));

			model.addObject("uomList", uomList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("uomId", uomId);

			Uom editUom = rest.postForObject(Constants.url + "getUomByUomId", map, Uom.class);

			model.addObject("title", "Edit Uom");
			model.addObject("editUom", editUom);

		} catch (Exception e) {

			System.err.println("exception In editDept at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteUom/{uomId}", method = RequestMethod.GET)
	public String deleteUom(HttpServletRequest request, HttpServletResponse response, @PathVariable int uomId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("uomId", uomId);

			Info errMsg = rest.postForObject(Constants.url + "deleteUom", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteDept @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddUom";
	}

	@RequestMapping(value = "/showAddUser", method = RequestMethod.GET)
	public ModelAndView showAddUser(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/adduser");
			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Dept[] deptArray = rest.getForObject(Constants.url + "getAllDeptList", Dept[].class);
			deptList = new ArrayList<Dept>(Arrays.asList(deptArray));

			model.addObject("deptList", deptList);

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);
			model.addObject("title", "Add User");

		} catch (Exception e) {

			System.err.println("exception In showAddUser at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public String insertUser(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertUser method");

			int userId = 0;
			try {

				userId = Integer.parseInt(request.getParameter("userId"));
				System.out.println("User Id" + userId);
			} catch (Exception e) {
				userId = 0;
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int deptId = Integer.parseInt(request.getParameter("dept_id"));
			int plantId = Integer.parseInt(request.getParameter("plant_id"));

			int companyId = Integer.parseInt(request.getParameter("company_id"));
			String curDate = dateFormat.format(new Date());

			String usrName = request.getParameter("usrName");

			String usrMob = request.getParameter("usrMob");

			String usrEmail = request.getParameter("usrEmail");

			String usrDob = request.getParameter("usrDob");

			String userPass = request.getParameter("userPass");

			String sortNo = request.getParameter("sortNo");

			User user = new User();
			user.setCompanyId(companyId);
			user.setDelStatus(1);
			user.setDeviceToken("NA");
			user.setDeptId(deptId);
			user.setExDate1(curDate);
			user.setExDate2(curDate);
			user.setExInt1(0);
			user.setExInt2(0);
			user.setPlantId(plantId);
			user.setRoleId(0);
			user.setSortNo(Integer.parseInt(sortNo));
			user.setUserPass(userPass);
			user.setUsrEmail(usrEmail);
			user.setUsrDob(DateConvertor.convertToYMD(usrDob));
			user.setUsrMob(usrMob);
			user.setUsrName(usrName);
			user.setExVar1("NA");
			user.setExVar2("NA");
			user.setUserId(userId);

			User userInsertRes = rest.postForObject(Constants.url + "saveUser", user, User.class);

		} catch (Exception e) {

			System.err.println("Exce in insert Uom " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddUser";
	}

	List<GetUser> getUserList;

	@RequestMapping(value = "/showUserList", method = RequestMethod.GET)
	public ModelAndView showUserList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/userlist");
			GetUser[] userArray = rest.getForObject(Constants.url + "getUserList", GetUser[].class);
			getUserList = new ArrayList<GetUser>(Arrays.asList(userArray));

			model.addObject("title", "User List");
			model.addObject("userList", getUserList);

		} catch (Exception e) {

			System.err.println("exception In showUserList at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/editUser/{userId}", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request, HttpServletResponse response, @PathVariable int userId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/adduser");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			Dept[] deptArray = rest.getForObject(Constants.url + "getAllDeptList", Dept[].class);
			deptList = new ArrayList<Dept>(Arrays.asList(deptArray));

			model.addObject("deptList", deptList);

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);
			GetUser[] userArray = rest.getForObject(Constants.url + "getUserList", GetUser[].class);
			getUserList = new ArrayList<GetUser>(Arrays.asList(userArray));

			model.addObject("userList", getUserList);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("userId", userId);

			User editUser = rest.postForObject(Constants.url + "getUserByUserId", map, User.class);

			model.addObject("title", "Edit User");
			model.addObject("editUser", editUser);

		} catch (Exception e) {

			System.err.println("exception In editUser at Master Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.GET)
	public String deleteUser(HttpServletRequest request, HttpServletResponse response, @PathVariable int userId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("userId", userId);

			Info errMsg = rest.postForObject(Constants.url + "deleteUser", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteUser @MastContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showUserList";
	}

}