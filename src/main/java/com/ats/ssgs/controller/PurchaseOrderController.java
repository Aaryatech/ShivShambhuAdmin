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
import com.ats.ssgs.model.GetPoHeader;
import com.ats.ssgs.model.GetQuotHeader;
import com.ats.ssgs.model.PoDetail;
import com.ats.ssgs.model.PoHeader;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.quot.QuotDetail;
import com.ats.ssgs.model.quot.QuotHeader;

@Controller
@Scope("session")
public class PurchaseOrderController {
	
	RestTemplate rest = new RestTemplate();
	GetQuotHeader quotHeader = new GetQuotHeader();
	
	
	@RequestMapping(value = "/addPo/{quotId}", method = RequestMethod.GET)
	public ModelAndView editQuot(HttpServletRequest request, HttpServletResponse response, @PathVariable int quotId) {

		ModelAndView model = new ModelAndView("purchaseOrder/addPo");
		try {

			 

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("quotHeadId", quotId); 
			quotHeader=rest.postForObject(Constants.url + "/getQuotHeaderWithNameByQuotHeadId", map, GetQuotHeader.class);
			quotHeader.setQuotDate(DateConvertor.convertToDMY(quotHeader.getQuotDate()));
			model.addObject("quotHeader",quotHeader);
			
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			model.addObject("todayDate",sf.format(date));
			
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return model;

	}
	
	@RequestMapping(value = "/submitPurchaseOrder", method = RequestMethod.POST)
	public String submitPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		 
		try {
 
			String poRemark = request.getParameter("poRemark");
			String poDate = request.getParameter("poDate");
			String poValidityDate = request.getParameter("poValidityDate");
			String delivery =  request.getParameter("delivery") ;
			String poNo =  request.getParameter("poNo") ;
			float taxIncl = Float.parseFloat(request.getParameter("taxIncl"));

			PoHeader save = new PoHeader();
			
			save.setCustId(quotHeader.getCustId());
			save.setPoNo(poNo);
			save.setRemark(poRemark);
			save.setPoDate(DateConvertor.convertToYMD(poDate));
			save.setCustProjectId(quotHeader.getProjId());
			save.setQuatationId(quotHeader.getQuotHeadId());
			save.setQuatationNo(quotHeader.getQuotNo());
			save.setPoValidityDate(DateConvertor.convertToYMD(poValidityDate));
			save.setPoTermId(quotHeader.getPayTermId());
			save.setPlantId(Integer.parseInt(quotHeader.getPlantIds()));
			save.setVarchar1(delivery);
			save.setQutDate(DateConvertor.convertToYMD(quotHeader.getQuotDate()));
			save.setDelStatus(1);
			save.setExtra1((int)taxIncl);
			
			List<PoDetail> poDetailList = new ArrayList<PoDetail>();
			
			for(int i=0 ; i< quotHeader.getGetQuotDetailList().size() ; i++) {
				
				PoDetail poDetail = new PoDetail();
				poDetail.setItemId(quotHeader.getGetQuotDetailList().get(i).getItemId());
				poDetail.setQuDetailId(quotHeader.getGetQuotDetailList().get(i).getQuotDetailId()); 
				poDetail.setPoQty(Float.parseFloat(request.getParameter("pOqty"+quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setPoRate(Float.parseFloat(request.getParameter("taxableAmt"+quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setTaxableAmt(poDetail.getPoRate());
				poDetail.setTaxAmt(Float.parseFloat(request.getParameter("taxAmt"+quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setOtherCharges(Float.parseFloat(request.getParameter("othCostAftTax"+quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setTotal(Float.parseFloat(request.getParameter("finalAmt"+quotHeader.getGetQuotDetailList().get(i).getItemId())));
				poDetail.setTaxPer(quotHeader.getGetQuotDetailList().get(i).getSgstPer()+quotHeader.getGetQuotDetailList().get(i).getSgstPer()+
						quotHeader.getGetQuotDetailList().get(i).getIgstPer());
				poDetail.setPoRemainingQty(poDetail.getPoQty());
				poDetailList.add(poDetail);
				
			}
			 
			save.setPoDetailList(poDetailList);
 
			PoHeader res = rest.postForObject(Constants.url + "savePurchaseOrder", save,
					PoHeader.class);

			System.err.println("res  PoHeader insert " + res.toString());
			
			if(res!=null) {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("quotHeadId", quotHeader.getQuotHeadId());
				Info info = rest.postForObject(Constants.url + "/updateQuatationStatus", map,
						Info.class);
			}

		} catch (Exception e) {
			 
			e.printStackTrace();

		}
		return "redirect:/getPoList";
	}
	
	@RequestMapping(value = "/getPoList", method = RequestMethod.GET)
	public ModelAndView getPoList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/poList");
		try {

			 Date date = new Date();
			 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			 SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
			 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			 
			 if(request.getParameter("fromDate")!=null | request.getParameter("toDate")!=null) {
				 
				 String fromDate = request.getParameter("fromDate");
				 String toDate = request.getParameter("toDate");
				 
				 map.add("fromDate", DateConvertor.convertToYMD(fromDate)); 
				 map.add("toDate",  DateConvertor.convertToYMD(toDate)); 
					
					GetPoHeader[] getPoHeader=rest.postForObject(Constants.url + "/getPoListByDate", map, GetPoHeader[].class);
					 
					List<GetPoHeader> poList = new ArrayList<GetPoHeader>(Arrays.asList(getPoHeader));
					
					model.addObject("poList",poList); 
					model.addObject("fromDate",fromDate);
					model.addObject("toDate",toDate);
			 }
			 else{
				 map.add("fromDate", sf.format(date)); 
					map.add("toDate", sf.format(date)); 
					
					GetPoHeader[] getPoHeader=rest.postForObject(Constants.url + "/getPoListByDate", map, GetPoHeader[].class);
					 
					List<GetPoHeader> poList = new ArrayList<GetPoHeader>(Arrays.asList(getPoHeader));
					
					model.addObject("poList",poList); 
					model.addObject("fromDate",dd.format(date));
					model.addObject("toDate",dd.format(date));
			 }
			 
			

			
			
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return model;

	}
	
	@RequestMapping(value = "/deletePurchaseOrder/{poId}", method = RequestMethod.GET)
	public String deletePurchaseOrder(@PathVariable int poId, HttpServletRequest request, HttpServletResponse response) {

		 
		try {
			
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				 map.add("poId",poId);  
					
				 Info info=rest.postForObject(Constants.url + "/deletePurchaseOrder", map, Info.class);
				 
				 System.out.println(info);
			
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return "redirect:/getPoList";

	}
	
	GetPoHeader editPo = new GetPoHeader();
	
	@RequestMapping(value = "/editPo/{poId}", method = RequestMethod.GET)
	public ModelAndView editPo(@PathVariable int poId, HttpServletRequest request, HttpServletResponse response) {
 
		ModelAndView model = new ModelAndView("purchaseOrder/editPo");
		try {
			
				 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				 map.add("poId",poId);  
					
				 editPo =rest.postForObject(Constants.url + "/getPoHeaderWithDetail", map, GetPoHeader.class);
				 model.addObject("editPo",editPo);
			
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		return model;

	}
	
	@RequestMapping(value = "/submitEditPurchaseOrder", method = RequestMethod.POST)
	public String submitEditPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		 
		try {
 
			 String poRemark = request.getParameter("poRemark");
			String poDate = request.getParameter("poDate");
			String poValidityDate = request.getParameter("poValidityDate");
			String delivery =  request.getParameter("delivery") ;
			String poNo =  request.getParameter("poNo") ; 
  

			PoHeader save = new PoHeader();
			
			 
			editPo.setPoNo(poNo);
			editPo.setRemark(poRemark);
			editPo.setPoDate(DateConvertor.convertToYMD(poDate)); 
			editPo.setPoValidityDate(DateConvertor.convertToYMD(poValidityDate)); 
			editPo.setVarchar1(delivery);
			editPo.setQutDate(DateConvertor.convertToYMD(editPo.getQutDate()));
			try {
			editPo.setExtraDate1(DateConvertor.convertToYMD(editPo.getExtraDate1()));
			editPo.setExtraDate2(DateConvertor.convertToYMD(editPo.getExtraDate2()));
			}catch(Exception e) {
				 
			}
			//List<PoDetail> poDetailList = new ArrayList<PoDetail>();
			
			 for(int i=0 ; i< editPo.getGetPoDetailList().size() ; i++) {
				 
				 editPo.getGetPoDetailList().get(i).setPoQty(Float.parseFloat(request.getParameter("pOqty"+editPo.getGetPoDetailList().get(i).getItemId())));
				 editPo.getGetPoDetailList().get(i).setPoRate(Float.parseFloat(request.getParameter("taxableAmt"+editPo.getGetPoDetailList().get(i).getItemId())));
				 editPo.getGetPoDetailList().get(i).setTaxableAmt(editPo.getGetPoDetailList().get(i).getPoRate());
				 editPo.getGetPoDetailList().get(i).setTaxAmt(Float.parseFloat(request.getParameter("taxAmt"+editPo.getGetPoDetailList().get(i).getItemId())));
				 editPo.getGetPoDetailList().get(i).setOtherCharges(Float.parseFloat(request.getParameter("othCostAftTax"+editPo.getGetPoDetailList().get(i).getItemId())));
				 editPo.getGetPoDetailList().get(i).setTotal(Float.parseFloat(request.getParameter("finalAmt"+editPo.getGetPoDetailList().get(i).getItemId())));
				 
			}
			 
			 editPo.setPoDetailList(editPo.getGetPoDetailList());
 
			PoHeader res = rest.postForObject(Constants.url + "savePurchaseOrder", editPo,
					PoHeader.class);

			System.err.println("res  " + res.toString()); 
			 

		} catch (Exception e) {
			 
			e.printStackTrace();

		}
		return "redirect:/getPoList";
	}

}
