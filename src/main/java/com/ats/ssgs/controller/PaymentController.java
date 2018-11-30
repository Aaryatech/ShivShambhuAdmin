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
import com.ats.ssgs.model.enq.EnqGenFact;
import com.ats.ssgs.model.master.BankDetail;
import com.ats.ssgs.model.master.Company;
import com.ats.ssgs.model.master.CustType;
import com.ats.ssgs.model.master.GetBankDetail;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.PaymentTerm;

@Controller
@Scope("session")
public class PaymentController {
	RestTemplate rest = new RestTemplate();
	List<PaymentTerm> payTermList;
	List<Company> compList;
	List<GetBankDetail> bankList;
	List<CustType> custTypeList;
	List<EnqGenFact> enqGenFactList;
	int isError = 0;

	@RequestMapping(value = "/showAddEnqGenFact", method = RequestMethod.GET)
	public ModelAndView showAddEnqGenFact(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addEnqGenFact");
			model.addObject("isError", isError);
			isError = 0;

			EnqGenFact[] custTypeArray = rest.getForObject(Constants.url + "getAllEGFList", EnqGenFact[].class);
			enqGenFactList = new ArrayList<EnqGenFact>(Arrays.asList(custTypeArray));

			model.addObject("enqGenFactList", enqGenFactList);

			model.addObject("title", "Add Enquiry Source");

		} catch (Exception e) {

			System.err.println("exception In showAddEnqGenFact at Payment Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertEnqGenFact", method = RequestMethod.POST)
	public String insertEnqGenFact(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertEnqGenFact method");

			int enqGenId = 0;
			try {
				enqGenId = Integer.parseInt(request.getParameter("enqGenId"));
			} catch (Exception e) {
				enqGenId = 0;
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			String enqGenBy = request.getParameter("enqGenBy");
			EnqGenFact enqGenFact = new EnqGenFact();
			enqGenFact.setDate(date);
			enqGenFact.setDelStatus(1);
			enqGenFact.setEnqGenBy(enqGenBy);
			enqGenFact.setEnqGenId(enqGenId);

			EnqGenFact enqInsertRes = rest.postForObject(Constants.url + "saveEnqGenFact", enqGenFact,
					EnqGenFact.class);
			if (enqInsertRes.getEnqGenId() != 0) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce in insert Uom " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddEnqGenFact";
	}

	@RequestMapping(value = "/editEnqGenFact/{enqGenId}", method = RequestMethod.GET)
	public ModelAndView editEnqGenFact(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int enqGenId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addEnqGenFact");
			EnqGenFact[] custTypeArray = rest.getForObject(Constants.url + "getAllEGFList", EnqGenFact[].class);
			enqGenFactList = new ArrayList<EnqGenFact>(Arrays.asList(custTypeArray));

			model.addObject("enqGenFactList", enqGenFactList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("enqGenId", enqGenId);

			EnqGenFact editEnqGen = rest.postForObject(Constants.url + "getEnqGenFactByEnqGenFactId", map,
					EnqGenFact.class);

			model.addObject("title", "Edit Enquiry Source");
			model.addObject("editEnqGen", editEnqGen);

		} catch (Exception e) {

			System.err.println("exception In editCustType at PaymentContr Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteEnqGenFact/{enqGenId}", method = RequestMethod.GET)
	public String deleteEnqGenFact(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int enqGenId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("enqGenId", enqGenId);

			Info errMsg = rest.postForObject(Constants.url + "deleteEnqGenFact", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteEnqGenFact @PaymentContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddEnqGenFact";
	}

	@RequestMapping(value = "/showAddCustType", method = RequestMethod.GET)
	public ModelAndView showAddCustType(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addcusttype");

			CustType[] custTypeArray = rest.getForObject(Constants.url + "getAllCustTypeList", CustType[].class);
			custTypeList = new ArrayList<CustType>(Arrays.asList(custTypeArray));
			model.addObject("isError", isError);
			isError = 0;
			model.addObject("custTypeList", custTypeList);

			model.addObject("title", "Add Customer Type Name");

		} catch (Exception e) {

			System.err.println("exception In showAddCustType at Payment Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertCustType", method = RequestMethod.POST)
	public String insertCustType(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert insertCustType method");

			int custTypeId = 0;
			try {
				custTypeId = Integer.parseInt(request.getParameter("custTypeId"));
			} catch (Exception e) {
				custTypeId = 0;
			}

			String custTypeName = request.getParameter("custTypeName");

			CustType custType = new CustType();
			custType.setCustTypeId(custTypeId);
			custType.setCustTypeName(custTypeName);
			custType.setDelStatus(1);

			CustType custTermInsertRes = rest.postForObject(Constants.url + "saveCustType", custType, CustType.class);
			if (custTermInsertRes.getCustTypeId() != 0) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce in insert CustType " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddCustType";
	}

	@RequestMapping(value = "/editCustType/{custTypeId}", method = RequestMethod.GET)
	public ModelAndView editCustType(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int custTypeId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addcusttype");

			CustType[] custTypeArray = rest.getForObject(Constants.url + "getAllCustTypeList", CustType[].class);
			custTypeList = new ArrayList<CustType>(Arrays.asList(custTypeArray));

			model.addObject("custTypeList", custTypeList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("custTypeId", custTypeId);

			CustType editCustType = rest.postForObject(Constants.url + "getCustTypeByCustTypeId", map, CustType.class);

			model.addObject("title", "Edit Customer Type Name");
			model.addObject("editCustType", editCustType);

		} catch (Exception e) {

			System.err.println("exception In editCustType at PaymentContr Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteCustType/{custTypeId}", method = RequestMethod.GET)
	public String deleteCustType(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int custTypeId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("custTypeId", custTypeId);

			Info errMsg = rest.postForObject(Constants.url + "deleteCustType", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteCustType @PaymentContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddCustType";
	}

	@RequestMapping(value = "/showAddPaymentTerm", method = RequestMethod.GET)
	public ModelAndView showAddPaymentTerm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addpayterm");
			model.addObject("isError", isError);
			isError = 0;

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

			if (payTermInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

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

	@RequestMapping(value = "/showAddBankDetail", method = RequestMethod.GET)
	public ModelAndView showAddBankDetail(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("master/addbankdetail");
			model.addObject("isError", isError);
			isError = 0;
			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);

			GetBankDetail[] bankDetailArray = rest.getForObject(Constants.url + "getAllBankDetList",
					GetBankDetail[].class);
			bankList = new ArrayList<GetBankDetail>(Arrays.asList(bankDetailArray));

			model.addObject("bankList", bankList);

			model.addObject("title", "Add Bank Detail");

		} catch (Exception e) {

			System.err.println("exception In showAddBankDetail at Payment Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/insertBankDetail", method = RequestMethod.POST)
	public String insertBankDetail(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside  insertBankDetail method");

			int bankDetId = 0;
			try {
				bankDetId = Integer.parseInt(request.getParameter("bankDetId"));
			} catch (Exception e) {
				bankDetId = 0;
			}

			int companyId = Integer.parseInt(request.getParameter("companyId"));
			String bankName = request.getParameter("bankName");
			String bankIfsc = request.getParameter("bankIfsc");
			String bankAddress = request.getParameter("bankAddress");
			String accNo = request.getParameter("accNo");
			int accType = Integer.parseInt(request.getParameter("accType"));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
			String timeStamp = dateFormat.format(new Date());

			BankDetail bankDetail = new BankDetail();
			bankDetail.setAccNo(accNo);
			bankDetail.setAccType(accType);
			bankDetail.setBankAddress(bankAddress);
			bankDetail.setBankDetId(bankDetId);
			bankDetail.setBankIfsc(bankIfsc);
			bankDetail.setBankName(bankName);
			bankDetail.setCompanyId(companyId);
			bankDetail.setDelStatus(1);
			bankDetail.setExInt1(0);
			bankDetail.setExInt2(0);
			bankDetail.setExVar1("NA");
			bankDetail.setExVar2("NA");
			bankDetail.setIsUsed(1);
			bankDetail.setTimeStamp(timeStamp);

			BankDetail bankDetailInsertRes = rest.postForObject(Constants.url + "saveBankDetail", bankDetail,
					BankDetail.class);

			if (bankDetailInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			System.err.println("Exce in insert Uom " + e.getMessage());
			e.printStackTrace();

		}
		return "redirect:/showAddBankDetail";
	}

	@RequestMapping(value = "/editBankDetail/{bankDetId}", method = RequestMethod.GET)
	public ModelAndView editBankDetail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int bankDetId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("master/addbankdetail");
			GetBankDetail[] bankDetailArray = rest.getForObject(Constants.url + "getAllBankDetList",
					GetBankDetail[].class);
			bankList = new ArrayList<GetBankDetail>(Arrays.asList(bankDetailArray));

			model.addObject("bankList", bankList);

			Company[] compArray = rest.getForObject(Constants.url + "getAllCompList", Company[].class);
			compList = new ArrayList<Company>(Arrays.asList(compArray));

			model.addObject("compList", compList);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("bankDetId", bankDetId);

			BankDetail editBankDetail = rest.postForObject(Constants.url + "getBankDetailByBankDetailId", map,
					BankDetail.class);

			model.addObject("title", "Edit Bank detail");
			model.addObject("editBankDetail", editBankDetail);

		} catch (Exception e) {

			System.err.println("exception In editBankDetail at PaymentContr Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/deleteBankDetail/{bankDetId}", method = RequestMethod.GET)
	public String deleteBankDetail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int bankDetId) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("bankDetId", bankDetId);

			Info errMsg = rest.postForObject(Constants.url + "deleteBankDetail", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteBankDetail @PaymentContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddBankDetail";
	}

	@RequestMapping(value = "/deleteRecordofPaymentTerm", method = RequestMethod.POST)
	public String deleteRecordofPaymentTerm(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] payTermIds = request.getParameterValues("payTermIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < payTermIds.length; i++) {
				sb = sb.append(payTermIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("payTermIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiPaymentTerm", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofPaymentTerm @PaymentContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddPaymentTerm";
	}

	@RequestMapping(value = "/deleteRecordofBank", method = RequestMethod.POST)
	public String deleteRecordofBank(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] bankDetIds = request.getParameterValues("bankDetIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bankDetIds.length; i++) {
				sb = sb.append(bankDetIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("bankDetIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiBankDetail", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofBank @DocTermContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddBankDetail";
	}

	@RequestMapping(value = "/deleteRecordofEnqGenFact", method = RequestMethod.POST)
	public String deleteRecordofEnqGenFact(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] enqGenIds = request.getParameterValues("enqGenIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < enqGenIds.length; i++) {
				sb = sb.append(enqGenIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("enqGenIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiEnqGenFact", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteMultiEnqGenFact @DocTermContr  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showAddEnqGenFact";
	}

}
