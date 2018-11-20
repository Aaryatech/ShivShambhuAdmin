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
			save.setDelStatus(1);
			
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
				poDetailList.add(poDetail);
				
			}
			 
			save.setPoDetailList(poDetailList);
 
			PoHeader res = rest.postForObject(Constants.url + "savePurchaseOrder", save,
					PoHeader.class);

			System.err.println("res  " + res.toString());
			
			if(res!=null) {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("quotHeadId", quotHeader.getQuotHeadId());
				Info info = rest.postForObject(Constants.url + "/updateQuatationStatus", map,
						Info.class);
			}

		} catch (Exception e) {
			 
			e.printStackTrace();

		}
		return "redirect:/showQuotations";
	}

}
