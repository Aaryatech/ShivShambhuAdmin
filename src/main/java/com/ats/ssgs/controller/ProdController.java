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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.prodrm.GetItemDetail;
import com.ats.ssgs.model.prodrm.GetProdPlanDetail;
import com.ats.ssgs.model.prodrm.GetProdPlanHeader;
import com.ats.ssgs.model.prodrm.ItemDetail;
import com.ats.ssgs.model.prodrm.ProdPlanDetail;
import com.ats.ssgs.model.prodrm.ProdPlanHeader;
import com.ats.ssgs.model.prodrm.ReqBomDetail;
import com.ats.ssgs.model.prodrm.ReqBomHeader;

@Controller
@Scope("session")
public class ProdController {

	RestTemplate rest = new RestTemplate();
	DateFormat dateFormatdd = new SimpleDateFormat("dd-MM-yyyy");

	DateFormat dateFormatyy = new SimpleDateFormat("yyyy-MM-dd");

	List<Plant> plantList;
	int isError = 0;
	@RequestMapping(value = "/showProdPlanList", method = RequestMethod.GET)
	public ModelAndView showProdPlanList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("prod/prod_plan_list");

			model.addObject("title", "Production Plan List");
			
			model.addObject("isError", isError);
			isError = 0;
			

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			String fromDate = null, toDate = null;

			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

			fromDate = dateFormat.format(firstDate);

			toDate = dateFormat.format(new Date());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {
			System.err.println("Exce in showing /showProdPlanList  " + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}

	List<GetProdPlanHeader> prodHeadList;

	@RequestMapping(value = "/getProdHeadersBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetProdPlanHeader> getProdHeadersBetDate(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getProdHeadersBetDate");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			int plantId = Integer.parseInt(request.getParameter("plantId"));

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			map.add("plantId", plantId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			GetProdPlanHeader[] prodArray = rest.postForObject(Constants.url + "getProdPlanHeadersBetDate", map,
					GetProdPlanHeader[].class);
			prodHeadList = new ArrayList<GetProdPlanHeader>(Arrays.asList(prodArray));

			for (int i = 0; i < prodHeadList.size(); i++) {

				prodHeadList.get(i)
						.setProductionDate(DateConvertor.convertToDMY(prodHeadList.get(i).getProductionDate()));

				prodHeadList.get(i).setProductionStartDate(
						DateConvertor.convertToDMY(prodHeadList.get(i).getProductionStartDate()));
				prodHeadList.get(i)
						.setProductionEndDate(DateConvertor.convertToDMY(prodHeadList.get(i).getProductionEndDate()));

			}

		} catch (Exception e) {
			System.err.println("Exce in Ajax /getProdHeadersBetDate  " + e.getMessage());
			e.printStackTrace();
		}
		
		return prodHeadList;
	}

	GetProdPlanHeader prodHeader;

	@RequestMapping(value = "/getProdDetail", method = RequestMethod.POST)
	public ModelAndView getProdDetail(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			prodHeader = new GetProdPlanHeader();
			System.err.println(" in getProdDetail prodHeaderId " + request.getParameter("prodHeaderId"));

			int prodHeaderId = Integer.parseInt(request.getParameter("prodHeaderId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("prodHeaderId", prodHeaderId);
			prodHeader = rest.postForObject(Constants.url + "getProdPlanDetail", map, GetProdPlanHeader.class);

			prodHeader.setProductionDate(DateConvertor.convertToDMY(prodHeader.getProductionDate()));
			prodHeader.setProductionStartDate(DateConvertor.convertToDMY(prodHeader.getProductionStartDate()));
			prodHeader.setProductionEndDate(DateConvertor.convertToDMY(prodHeader.getProductionEndDate()));

			model = new ModelAndView("prod/prod_plan_detail");
			model.addObject("title", "Production Plan Detail");
			model.addObject("prodHeader", prodHeader);

		} catch (Exception e) {
			System.err.println("Exce in view Prod Detail /getProdDetail  " + e.getMessage());
			e.printStackTrace();

		}
		return model;
	}

	// completeProd
	@RequestMapping(value = "/completeProd", method = RequestMethod.POST)
	public String completeProd(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<GetProdPlanDetail> proDList = prodHeader.getGetProdPlanDetList();
			List<ProdPlanDetail> planDetails = new ArrayList<>();
			
			System.err.println("proDList size  " +proDList.size() + "\n  " +"toSting  " +proDList.toString());

			for (int i = 0; i < proDList.size(); i++) {
					System.err.println("string "+"prodQty"+proDList.get(i).getProductionDetailId());
				
				float prodQty = Float
						.parseFloat(request.getParameter("prodQty"+proDList.get(i).getProductionDetailId()));
				float rejQty = Float
						.parseFloat(request.getParameter("rejQty"+proDList.get(i).getProductionDetailId()));

				ProdPlanDetail det = new ProdPlanDetail();

				det.setProductionDetailId(proDList.get(i).getProductionDetailId());
				det.setStatus(3);
				det.setProductionQty(prodQty);
				det.setRejectedQty(rejQty);

				planDetails.add(det);

			}

			ProdPlanHeader planHeader = new ProdPlanHeader();

			planHeader.setProductionStatus(3);
			planHeader.setProductionHeaderId(prodHeader.getProductionHeaderId());
			planHeader.setProdPlanDetailList(planDetails);
			planHeader.setProductionEndDate(dateFormatyy.format(new Date()));

			Info completeProd = rest.postForObject(Constants.url + "completeProd", planHeader, Info.class);
			
			if(completeProd.isError()==false) {
				isError=2;
			}else {
				isError=1;
			}
			
			System.err.println("completeProd  " + completeProd.toString());

		} catch (Exception e) {
			
			System.err.println("Exce in complete Prod  " +e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showProdPlanList";

	}
	//List<ItemDetail> rmItemList;
	@RequestMapping(value = "/showManBOM", method = RequestMethod.GET)
	public ModelAndView showManBOM(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			model = new ModelAndView("prod/man_bom");

			model.addObject("title", "Manual BOM");
			
			/*String itemIdList = new String();
			//List<String> itemIdList=new ArrayList<>();
			
			List<GetProdPlanDetail> proDList = prodHeader.getGetProdPlanDetList();
			
			for(GetProdPlanDetail detail: proDList ) {
				itemIdList=itemIdList.concat(""+detail.getItemId()+",");
			}
			//getItemDetailByItemIds
			itemIdList=itemIdList.substring(0, itemIdList.length()-1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("delStatus", 1);
			map.add("itemIdList", itemIdList);
			System.err.println("itemIdList " +itemIdList);
*/
			/*ItemDetail[] rmItemArray = rest.postForObject(Constants.url + "getItemDetailByItemIds", map,
					ItemDetail[].class);
			rmItemList = new ArrayList<ItemDetail>(Arrays.asList(rmItemArray));
			
			System.err.println("rmItemList " +rmItemList.toString());
			
			model.addObject("rmItemList", rmItemList);*/
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("prodHeaderId", prodHeader.getProductionHeaderId());

			GetItemDetail[] rmItemArray = rest.postForObject(Constants.url + "getItemDetailForBOM", map,
					GetItemDetail[].class);
			getRmItemList = new ArrayList<GetItemDetail>(Arrays.asList(rmItemArray));
			
			System.err.println("rmItemList " +getRmItemList.toString());
			
			model.addObject("rmItemList", getRmItemList);
			model.addObject("prodHeader", prodHeader);

			model.addObject("isError", isError);
			isError = 0;
			
		}catch (Exception e) {
			
			System.err.println("Exce in showManBOM   " +e.getMessage());
			e.printStackTrace();

		}
		return model;
	}
	
	//insertManualBOM
	@RequestMapping(value = "/insertManualBOM", method = RequestMethod.POST)
	public String insertManualBOM(HttpServletRequest request, HttpServletResponse response) {

		try {

			String curDate=dateFormatyy.format(new Date());
			
			ReqBomHeader bomHeader=new ReqBomHeader();
			
			List<ReqBomDetail> reqBomDetail=new ArrayList<>();
			
			String NA="NA";
			for (int i=0; i<getRmItemList.size(); i++) {
				
				System.err.println("I " +i);
				
				float rmQty = Float.parseFloat(request.getParameter("rmQty"+getRmItemList.get(i).getItemDetailId()));
				System.err.println("string at  "+i+" " +"rmQty"+getRmItemList.get(i).getRmName() +"rm qty "+rmQty);

				ReqBomDetail bomDet = new ReqBomDetail();
				
				bomDet.setAutoRmReqQty(rmQty);
				bomDet.setDelStatus(1);
				bomDet.setExVar1(NA);
				bomDet.setExVar2(NA);
				bomDet.setExVar3(NA);
				bomDet.setItemValue(0);
				bomDet.setMrnBatch(NA);
				bomDet.setMrnItemRate(0);
				bomDet.setRejectedQty(0);
				bomDet.setReturnQty(0);
				bomDet.setRmId(getRmItemList.get(i).getRmId());
				bomDet.setRmIssueQty(0);
				bomDet.setRmReqQty(rmQty);
				bomDet.setStatus(1);
				bomDet.setUom(getRmItemList.get(i).getRmUomName());
				
				reqBomDetail.add(bomDet);
				
			}

			bomHeader.setApprovedDate(curDate);
			bomHeader.setApprovedUserId(0);
			bomHeader.setBomReqDate(curDate);
			bomHeader.setDelStatus(1);
			bomHeader.setExVar1(NA);
			bomHeader.setExVar2(NA);
			bomHeader.setIsManual(1);
			bomHeader.setPlantId(prodHeader.getPlantId());
			bomHeader.setProductionDate(DateConvertor.convertToYMD(prodHeader.getProductionDate()));
			bomHeader.setProductionId(prodHeader.getProductionHeaderId());
			bomHeader.setSenderUserId(prodHeader.getUserId());
			bomHeader.setStatus(1);
			bomHeader.setSubPlantId(prodHeader.getSubPlantId());
			
			bomHeader.setReqBomDetailsList(reqBomDetail);

			ReqBomHeader bomInsertRes = rest.postForObject(Constants.url + "saveBomHeaderDetail", bomHeader, ReqBomHeader.class);
			
			if(bomInsertRes!=null) {
				isError=2;
			}else {
				isError=1;
			}
			
			System.err.println("bomInsertRes  " + bomInsertRes.toString());

		} catch (Exception e) {
			
			System.err.println("Exce in bom Insert Manual BOM Prod  " +e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/showProdPlanList";
	}
	
	List<GetItemDetail> getRmItemList;
	@RequestMapping(value = "/showBOM", method = RequestMethod.GET)
	public ModelAndView showBOM(HttpServletRequest request, HttpServletResponse response) {
//			//get response in session and get to showBom Disp Controller

		
		if(prodHeader==null) {
			
			System.err.println("prodHeader==null" );
			
			HttpSession session=request.getSession();
			
			
			
			
			ProdPlanHeader insertHeader=(ProdPlanHeader) session.getAttribute("prodHeader");
			
			
			MultiValueMap<String, Object>   map = new LinkedMultiValueMap<String, Object>();

			map.add("prodHeaderId", insertHeader.getProductionHeaderId());
			prodHeader = rest.postForObject(Constants.url + "getProdPlanDetail", map, GetProdPlanHeader.class);
			
			System.err.println("prodHeader " +prodHeader.toString());

			prodHeader.setProductionDate(DateConvertor.convertToDMY(prodHeader.getProductionDate()));
			prodHeader.setProductionStartDate(DateConvertor.convertToDMY(prodHeader.getProductionStartDate()));
			prodHeader.setProductionEndDate(DateConvertor.convertToDMY(prodHeader.getProductionEndDate()));

			
			System.err.println("prodHeader from session  " +prodHeader.toString() );

			
		}
		ModelAndView model = null;
		try {
			model = new ModelAndView("prod/auto_bom");

			model.addObject("title", "Automatic BOM");
			
		
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("prodHeaderId", prodHeader.getProductionHeaderId());

			GetItemDetail[] rmItemArray = rest.postForObject(Constants.url + "getItemDetailForBOM", map,
					GetItemDetail[].class);
			getRmItemList = new ArrayList<GetItemDetail>(Arrays.asList(rmItemArray));
			
			System.err.println("rmItemList " +getRmItemList.toString());
			
			model.addObject("rmItemList", getRmItemList);
			model.addObject("prodHeader", prodHeader);

			model.addObject("isError", isError);
			isError = 0;
			
		}catch (Exception e) {
			
			System.err.println("Exce in showBOM   " +e.getMessage());
			e.printStackTrace();

		}
		return model;
	}
	
	
	//insertBOM
		@RequestMapping(value = "/insertBOM", method = RequestMethod.POST)
		public String insertBOM(HttpServletRequest request, HttpServletResponse response) {

			try {

				String curDate=dateFormatyy.format(new Date());
				
				ReqBomHeader bomHeader=new ReqBomHeader();
				
				List<ReqBomDetail> reqBomDetail=new ArrayList<>();
				
				String NA="NA";
				for (int i=0; i<getRmItemList.size(); i++) {
					
					System.err.println("I " +i);
					
					float rmQty = Float.parseFloat(request.getParameter("rmQty"+getRmItemList.get(i).getItemDetailId()));
					System.err.println("string at  "+i+" " +"rmQty"+getRmItemList.get(i).getRmName() +"rm qty "+rmQty);
					float rmEditQty = Float.parseFloat(request.getParameter("rmEditQty"+getRmItemList.get(i).getItemDetailId()));

					ReqBomDetail bomDet = new ReqBomDetail();
					
					bomDet.setAutoRmReqQty(rmQty);
					bomDet.setDelStatus(1);
					bomDet.setExVar1(NA);
					bomDet.setExVar2(NA);
					bomDet.setExVar3(NA);
					bomDet.setItemValue(0);
					bomDet.setMrnBatch(NA);
					bomDet.setMrnItemRate(0);
					bomDet.setRejectedQty(0);
					bomDet.setReturnQty(0);
					bomDet.setRmId(getRmItemList.get(i).getRmId());
					bomDet.setRmIssueQty(0);
					bomDet.setRmReqQty(rmEditQty);
					bomDet.setStatus(1);
					bomDet.setUom(getRmItemList.get(i).getRmUomName());
					
					reqBomDetail.add(bomDet);
					
				}

				bomHeader.setApprovedDate(curDate);
				bomHeader.setApprovedUserId(0);
				bomHeader.setBomReqDate(curDate);
				bomHeader.setDelStatus(1);
				bomHeader.setExVar1(NA);
				bomHeader.setExVar2(NA);
				bomHeader.setIsManual(0);
				bomHeader.setPlantId(prodHeader.getPlantId());
				bomHeader.setProductionDate(DateConvertor.convertToYMD(prodHeader.getProductionDate()));
				bomHeader.setProductionId(prodHeader.getProductionHeaderId());
				bomHeader.setSenderUserId(prodHeader.getUserId());
				bomHeader.setStatus(1);
				bomHeader.setSubPlantId(prodHeader.getSubPlantId());
				
				bomHeader.setReqBomDetailsList(reqBomDetail);

				ReqBomHeader bomInsertRes = rest.postForObject(Constants.url + "saveBomHeaderDetail", bomHeader, ReqBomHeader.class);
				
				if(bomInsertRes!=null) {
					isError=2;
				}else {
					isError=1;
				}
				
				System.err.println("bomInsertRes  " + bomInsertRes.toString());
				
				
				prodHeader=null;
				HttpSession session=request.getSession();
				
				session.removeAttribute("prodHeader");				

			} catch (Exception e) {
				
				System.err.println("Exce in bom Insert Manual BOM Prod  " +e.getMessage());
				e.printStackTrace();
			}

			return "redirect:/showProdPlanList";
		}
	
}
