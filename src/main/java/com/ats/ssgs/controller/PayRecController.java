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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Uom;
import com.ats.ssgs.model.mat.Contractor;
import com.ats.ssgs.model.mat.GetMatIssueDetail;
import com.ats.ssgs.model.mat.GetMatIssueHeader;
import com.ats.ssgs.model.mat.ItemCategory;
import com.ats.ssgs.model.mat.MatIssueDetail;
import com.ats.ssgs.model.mat.MatIssueHeader;
import com.ats.ssgs.model.mat.RawMatItem;
import com.ats.ssgs.model.rec.GetPayRecoveryHead;
import com.ats.ssgs.model.rec.PayRecoveryDetail;
import com.ats.ssgs.model.rec.PayRecoveryHead;

@Controller
@Scope("session")
public class PayRecController {

	RestTemplate rest = new RestTemplate();
	int isError = 0;
	List<GetPayRecoveryHead> recList;

	@RequestMapping(value = "/showPaymentRecoveryList", method = RequestMethod.GET)
	public ModelAndView showPaymentRecoveryList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("payrec/payreclist");

			model.addObject("title", "Payment Recovery List");
		} catch (Exception e) {

			System.err.println("exception In showPaymentRecoveryList at Txn Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	@RequestMapping(value = "/getPayRecoveryBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetPayRecoveryHead> getPayRecoveryBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getWeighListBetDate");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		// map.add("status", 0);

		GetPayRecoveryHead[] recHeadArray = rest.postForObject(Constants.url + "getPayRecoveryBetDate", map,
				GetPayRecoveryHead[].class);
		recList = new ArrayList<GetPayRecoveryHead>(Arrays.asList(recHeadArray));

		return recList;
	}

	@RequestMapping(value = "/deleteRecordofPayRec", method = RequestMethod.POST)
	public String deleteRecordofPayRec(HttpServletRequest request, HttpServletResponse response) {
		try {

			String[] payHeadIds = request.getParameterValues("payHeadIds");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < payHeadIds.length; i++) {
				sb = sb.append(payHeadIds[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("payHeadIds", items);

			Info errMsg = rest.postForObject(Constants.url + "deleteMultiPayRec", map, Info.class);

		} catch (Exception e) {

			System.err.println("Exception in /deleteRecordofPayRec @txncontroller  " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showPaymentRecoveryList";
	}

	GetPayRecoveryHead editRec;

	@RequestMapping(value = "/editPayRec/{payHeadId}", method = RequestMethod.GET)
	public ModelAndView editPayRec(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int payHeadId) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("payrec/editpayrec");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("payHeadId", payHeadId);
			editRec = rest.postForObject(Constants.url + "getPayRecoveryByPayHeadId", map, GetPayRecoveryHead.class);

			model.addObject("title", "Edit Payment Recovery");
			model.addObject("editRec", editRec);
			model.addObject("editRecDetail", editRec.getPayRecoveryDetailList());

		} catch (Exception e) {
			System.err.println("exception In editMat at Mat Contr" + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/getDetailEditForPayRec", method = RequestMethod.GET)
	public @ResponseBody PayRecoveryDetail getDetailEditForPayRec(HttpServletRequest request,
			HttpServletResponse response) {

		// int matDetailId = Integer.parseInt(request.getParameter("matDetailId"));
		int index = Integer.parseInt(request.getParameter("index"));

		return editRec.getPayRecoveryDetailList().get(index);

	}

	@RequestMapping(value = "/editInAddPayRec", method = RequestMethod.GET)
	public @ResponseBody List<PayRecoveryDetail> editInAddPayRec(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int isDelete = Integer.parseInt(request.getParameter("isDelete"));

			int isEdit = Integer.parseInt(request.getParameter("isEdit"));

			if (isDelete == 1) {

				System.out.println("IsDelete" + isDelete);
				int key = Integer.parseInt(request.getParameter("key"));

				PayRecoveryDetail deleteDetail = editRec.getPayRecoveryDetailList().get(key);
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map = new LinkedMultiValueMap<String, Object>();

				map.add("payDetailId", deleteDetail.getPayDetailId());

				Info errMsg = rest.postForObject(Constants.url + "deletePayRecDetail", map, Info.class);

				editRec.getPayRecoveryDetailList().remove(key);

			} else if (isEdit == 1) {

				int typeTx = Integer.parseInt(request.getParameter("txType"));
				String txNo = request.getParameter("txNo");
				String paymentDate = request.getParameter("paymentDate");

				int index = Integer.parseInt(request.getParameter("index"));
				float paidAmt = Float.parseFloat(request.getParameter("paidAmt"));

				editRec.getPayRecoveryDetailList().get(index).setTxNo(txNo);
				editRec.getPayRecoveryDetailList().get(index).setTypeTx(typeTx);
				editRec.getPayRecoveryDetailList().get(index).setPaymentDate(paymentDate);
				editRec.getPayRecoveryDetailList().get(index).setPaidAmt(paidAmt);

			}

			else {

				int payHeadId = Integer.parseInt(request.getParameter("payHeadId"));
				String paymentDate = request.getParameter("paymentDate");

				int typeTx = Integer.parseInt(request.getParameter("txType"));

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				float paidAmt = Float.parseFloat(request.getParameter("paidAmt"));
				// Calendar cal = Calendar.getInstance();
				String curDate = dateFormat.format(new Date());

				PayRecoveryDetail detail = new PayRecoveryDetail();
				detail.setDelStatus(1);
				detail.setExBool1(1);
				detail.setExDate1(curDate);
				detail.setExInt1(1);
				detail.setExInt2(1);
				detail.setExVarchar1("NA");
				detail.setExVarchar2("NA");
				detail.setPaidAmt(paidAmt);
				detail.setPayHeadId(payHeadId);
				detail.setPaymentDate(DateConvertor.convertToYMD(paymentDate));
				detail.setTypeTx(typeTx);

				try {
					detail.setTxNo(request.getParameter("txNo"));

				} catch (Exception e) {
					detail.setTxNo("0");

				}

				try {
					detail.setRemark(request.getParameter("remark"));
				} catch (Exception e) {
					detail.setRemark("NA");
				}

				editRec.getPayRecoveryDetailList().add(detail);
				// getMatIssueDetailList.add(matIssueDetail);

				System.out.println("Inside Edit Raw Material");
			}

		} catch (Exception e) {
			System.err.println("Exce In temp  editRec.getPayRecoveryDetailList() List " + e.getMessage());
			e.printStackTrace();
		}

		return editRec.getPayRecoveryDetailList();

	}

	@RequestMapping(value = "/editSubmitDetailPayRec", method = RequestMethod.POST)
	public String editSubmitDetailPayRec(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside insert editSubmitDetailPayRec method");

			int payHeadId = Integer.parseInt(request.getParameter("payHeadId"));
			String creditDate2 = request.getParameter("creditDate2");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());
			
			editRec.setCreditDate2(DateConvertor.convertToYMD(creditDate2));
			editRec.setBillDate(DateConvertor.convertToYMD(editRec.getBillDate()));
			editRec.setCreditDate1(DateConvertor.convertToYMD(editRec.getCreditDate1()));

			editRec.setCreditDate3(DateConvertor.convertToYMD(editRec.getCreditDate3()));

			List<PayRecoveryDetail> detailList = new ArrayList<>();
			float paidAmt1 = 0;

			for (int i = 0; i < editRec.getPayRecoveryDetailList().size(); i++) {


				editRec.getPayRecoveryDetailList().get(i).setPayHeadId(payHeadId);

				editRec.getPayRecoveryDetailList().get(i).setPaymentDate(
						DateConvertor.convertToYMD(editRec.getPayRecoveryDetailList().get(i).getPaymentDate()));
				editRec.getPayRecoveryDetailList().get(i).setTxNo(editRec.getPayRecoveryDetailList().get(i).getTxNo());
				editRec.getPayRecoveryDetailList().get(i).setTypeTx(editRec.getPayRecoveryDetailList().get(i).getTypeTx());

				paidAmt1 = paidAmt1 + editRec.getPayRecoveryDetailList().get(i).getPaidAmt();
				editRec.getPayRecoveryDetailList().get(i).setPaidAmt(paidAmt1);

			}
			editRec.setPaidAmt(paidAmt1);
			editRec.setPayRecoveryDetailList(editRec.getPayRecoveryDetailList());

			System.out.println("payRec" + editRec.toString());

			System.out.println("detailList" + detailList.size());

			PayRecoveryHead matIssueInsertRes = rest.postForObject(Constants.url + "savePaymentRecovery", editRec,
					PayRecoveryHead.class);

			if (matIssueInsertRes != null) {
				isError = 2;
			} else {
				isError = 1;
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return "redirect:/showPaymentRecoveryList";

	}

}
