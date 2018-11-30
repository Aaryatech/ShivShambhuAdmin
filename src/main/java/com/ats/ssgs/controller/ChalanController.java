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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.chalan.ChalanDetail;
import com.ats.ssgs.model.chalan.ChalanHeader;
import com.ats.ssgs.model.chalan.GetChalanDetail;
import com.ats.ssgs.model.chalan.GetChalanHeader;
import com.ats.ssgs.model.chalan.TempChalanItem;
import com.ats.ssgs.model.enq.EnqHeader;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.Item;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.master.User;
import com.ats.ssgs.model.master.Vehicle;
import com.ats.ssgs.model.order.GetOrder;
import com.ats.ssgs.model.order.GetOrderDetail;
import com.ats.ssgs.model.order.OrderHeader;

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
			
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());
			
			model.addObject("curDate" ,curDate);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("docCode", 5);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			model.addObject("doc", doc);


		} catch (Exception e) {

			System.err.println("exception In showAddChalan at Chalan Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

	List<OrderHeader> ordHeadList;

	@RequestMapping(value = "/getOrdHeaderForChalan", method = RequestMethod.GET)
	public @ResponseBody List<OrderHeader> getItemsByPlantId(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int projId = Integer.parseInt(request.getParameter("projId"));

		int custId = Integer.parseInt(request.getParameter("custId"));

		System.err.println("getOrdHeaderForChalan  " + "projId" + projId + "custId" + custId);

		map.add("custId", custId);
		map.add("projId", projId);

		map.add("statusList", "0,1");

		OrderHeader[] ordArray = rest.postForObject(Constants.url + "getOrdHeaderForChalan", map, OrderHeader[].class);
		ordHeadList = new ArrayList<OrderHeader>(Arrays.asList(ordArray));

		for (int i = 0; i < ordHeadList.size(); i++) {

			ordHeadList.get(i).setOrderDate(DateConvertor.convertToDMY(ordHeadList.get(i).getOrderDate()));
		}

		System.err.println("Ajax Order List " + ordHeadList.toString());

		return ordHeadList;

	}

	List<GetOrderDetail> ordDetailList;

	@RequestMapping(value = "/getOrderDetailForChalan", method = RequestMethod.GET)
	public @ResponseBody List<GetOrderDetail> getOrderDetail(HttpServletRequest request, HttpServletResponse response) {
		tempChItemList =null;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		System.err.println("orderHeaderId for getOrderDetailList  " +orderId);

		map.add("orderHeaderId", orderId);
		GetOrderDetail[] ordDetailArray = rest.postForObject(Constants.url + "getOrderDetailList", map,
				GetOrderDetail[].class);
		
				ordDetailList = new ArrayList<GetOrderDetail>(Arrays.asList(ordDetailArray));
				
				System.err.println("Ajax ordDetailList " + ordDetailList.toString());

		
				return ordDetailList ;
	}
	
	//setChalanItem;
	List<TempChalanItem> tempChItemList;
	
	
	@RequestMapping(value = "/setChalanItem", method = RequestMethod.GET)
	public @ResponseBody List<TempChalanItem> setChalanItem(HttpServletRequest request, HttpServletResponse response) {
		
		 float chalanQty =Float.parseFloat(request.getParameter("chalanQty"));	
		 
		 int itemId=Integer.parseInt(request.getParameter("itemId"));
		 int poId=Integer.parseInt(request.getParameter("itemId"));
		 int poDetailId=Integer.parseInt(request.getParameter("poDetailId"));
		 
		 float remOrdQty=Float.parseFloat(request.getParameter("remOrdQty"));
		 
		 int orderDetId=Integer.parseInt(request.getParameter("orderDetId"));
		 int orderId=Integer.parseInt(request.getParameter("orderId"));
		 int index=Integer.parseInt(request.getParameter("index"));

 		 System.err.println("chalanQty for setChalanItem  " +chalanQty);
 		 
 		String itemName=request.getParameter("itemName");
 		int uomId=Integer.parseInt(request.getParameter("uomId"));
 	
 		if(itemName!=null) {
 			
 			itemName=itemName.substring(1, itemName.length()-1);
 		}
		if(tempChItemList==null) {
			System.err.println("Inside tempChItemList null");

			tempChItemList=new ArrayList<>();
			 
			TempChalanItem chItem=new TempChalanItem();
			
			chItem.setChalanQty(chalanQty);
			chItem.setItemId(itemId);
			chItem.setOrderDetId(orderDetId);
			chItem.setOrderId(orderId);
			chItem.setPoDetailId(poDetailId);
			chItem.setPoId(poId);
			chItem.setRemOrdQty(remOrdQty);
			
			chItem.setItemName(itemName);
			chItem.setUomId(uomId);
			
			chItem.setIndex(index);
			
			tempChItemList.add(chItem);
		}
		else {
			try {
				System.err.println("Inside edit try block");
				TempChalanItem chItem=new TempChalanItem();
				
				chItem.setChalanQty(chalanQty);
				chItem.setItemId(itemId);
				chItem.setOrderDetId(orderDetId);
				chItem.setOrderId(orderId);
				chItem.setPoDetailId(poDetailId);
				chItem.setPoId(poId);
				chItem.setRemOrdQty(remOrdQty);
				
				chItem.setItemName(itemName);
				chItem.setUomId(uomId);
				
				chItem.setIndex(index);
				tempChItemList.set(index, chItem);
				
			}catch (Exception e) {
				System.err.println("Inside new Item catch block");

				TempChalanItem chItem=new TempChalanItem();
				
				chItem.setChalanQty(chalanQty);
				chItem.setItemId(itemId);
				chItem.setOrderDetId(orderDetId);
				chItem.setOrderId(orderId);
				chItem.setPoDetailId(poDetailId);
				chItem.setPoId(poId);
				chItem.setRemOrdQty(remOrdQty);
				
				chItem.setItemName(itemName);
				chItem.setUomId(uomId);
				
				chItem.setIndex(index);
				
				tempChItemList.add(chItem);
			}
			
			  if(chalanQty<=0) {
	 			 try {
	 				 System.err.println("inside chalanQty =0 try delete  "  );
	 				tempChItemList.remove(index);
	 				
	 				}catch (Exception e) {
	 					System.err.println("inside Exception delete  "  );
	 					
	 			 }
	 			 
	 		 }
		}
		
	     System.err.println("Ajax tempChItemList " + tempChItemList.toString());
		
				return tempChItemList ;
	}
	
	//setChalanItem;
	//
	@RequestMapping(value = "/getChalanSelectedItems", method = RequestMethod.GET)
	public @ResponseBody List<TempChalanItem> getChalanSelectedItems(HttpServletRequest request, HttpServletResponse response) {
		
		return tempChItemList;
		
	}
	
	//insertChalan
	
	
	@RequestMapping(value = "/insertChalan", method = RequestMethod.POST)
	public String insertChalan(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			MultiValueMap<String, Object>  map = new LinkedMultiValueMap<String, Object>();

			map.add("orderId", tempChItemList.get(0).getOrderId());
			 GetOrder getOrder = rest.postForObject(Constants.url + "getOrderHeaderById", map, GetOrder.class);

			
			System.err.println("Inside insert insertEnq method");

			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int custId = Integer.parseInt(request.getParameter("cust_name"));
			int orderId = Integer.parseInt(request.getParameter("order_id"));

			String chalanDate = request.getParameter("chalan_date");
			String chalanRemark = request.getParameter("chalan_remark");
			String costSegment = request.getParameter("cost_segment");

			
			int driverId = Integer.parseInt(request.getParameter("driver_id"));
			int vehicleId = Integer.parseInt(request.getParameter("veh_id"));

			
			String outTime = request.getParameter("out_time");
			float outKm=Float.parseFloat(request.getParameter("out_km"));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate=dateFormat.format(new Date());
			
			List<ChalanDetail> chDetailList=new ArrayList<>();
			for(int i=0;i<tempChItemList.size();i++) {
				
				float width=Float.parseFloat(request.getParameter("width"+tempChItemList.get(i).getItemId()));
				float height=Float.parseFloat(request.getParameter("height"+tempChItemList.get(i).getItemId()));
				float length=Float.parseFloat(request.getParameter("length"+tempChItemList.get(i).getItemId()));

				ChalanDetail chDetail=new ChalanDetail();
				
				chDetail.setDelStatus(1);
				chDetail.setExDate1(curDate);
				chDetail.setExFloat1(0);
				chDetail.setExVar1("NA");
				chDetail.setExVar2("NA");
				chDetail.setItemHeightPlant(height);
				chDetail.setItemHeightSite(0);
				chDetail.setItemId(tempChItemList.get(i).getItemId());
				chDetail.setItemLengthPlant(length);
				chDetail.setItemLengthSite(0);
				chDetail.setItemQty(tempChItemList.get(i).getChalanQty());
				chDetail.setItemTotalSite(0);
				chDetail.setItemUom(tempChItemList.get(i).getUomId());
				chDetail.setItemWidthPlant(width);
				chDetail.setItemWidthSite(0);
				chDetail.setStatus(0);
				chDetail.setItemTotalPlant(5);
				
				chDetail.setOrderDetailId(tempChItemList.get(i).getOrderDetailId());
				chDetailList.add(chDetail);
				
			}
			
			
			Calendar cal = Calendar.getInstance();
			
			ChalanHeader chHeader=new ChalanHeader();
			
			chHeader.setProjId(getOrder.getProjId());
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 5);
			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			
			chHeader.setChalanNo(doc.getDocPrefix()+""+doc.getSrNo());
			
			chHeader.setChalanDate(curDate);
			chHeader.setChalanDetailList(chDetailList);
			chHeader.setChalanRemark(chalanRemark);
			chHeader.setCustId(custId);
			chHeader.setDriverId(driverId);
			chHeader.setExDate1(curDate);
			chHeader.setExFloat1(0);
			chHeader.setExVar1("save time-" +dateFormat.format(cal.getTime().getTime()));
			chHeader.setInKm(0);
			chHeader.setOrderId(orderId);
			chHeader.setOrderNo(getOrder.getOrderNo());
			chHeader.setOutKm(outKm);
			chHeader.setPlantId(plantId);
			chHeader.setProjId(getOrder.getProjId());
			chHeader.setSitePersonMob("-");
			chHeader.setSitePersonName("-");
			chHeader.setStatus(0);
			chHeader.setVehicleId(vehicleId);
			chHeader.setVehInDate(curDate);
			chHeader.setVehTimeIn("-");
			chHeader.setVehTimeOut(outTime);
			chHeader.setChalanDate(DateConvertor.convertToYMD(chalanDate));
			chHeader.setCostSegment(costSegment);
			
			
			ChalanHeader chHeadInserRes = rest.postForObject(Constants.url + "saveChalanHeaderDetail", chHeader,
					ChalanHeader.class);

			if (chHeadInserRes != null) {

				map = new LinkedMultiValueMap<String, Object>();

				map.add("srNo", doc.getSrNo() + 1);
				map.add("docCode", doc.getDocCode());

				Info updateDocSr = rest.postForObject(Constants.url + "updateDocSrNo", map, Info.class);

			}


		}catch (Exception e) {
			System.err.println("Exe in  saveChalanHeaderDetail " +e.getMessage());
			
			e.printStackTrace();
		}
		
		return "redirect:/showAddChalan";
	
	}
	
	
	@RequestMapping(value = "/showChalanList", method = RequestMethod.GET)
	public ModelAndView showChalanList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/chalan_list");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			
			model.addObject("title", "Chalan List");
			
			
		}
		catch (Exception e) {
			System.err.println("Exce in /showChalanList   " +e.getMessage());
			e.printStackTrace();
		}
		return model;
		
	}
	
	//getChalanListByPlant
	
	@RequestMapping(value = "/getChalanListByPlant", method = RequestMethod.GET)
	public @ResponseBody List<GetChalanHeader> getChalanListByPlant(HttpServletRequest request, HttpServletResponse response) {
		List<GetChalanHeader> chalanHeadList;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int plantId = Integer.parseInt(request.getParameter("plantId"));
		System.err.println("plantId for getChalanListByPlant  " +plantId);

		map.add("plantId", plantId);
		map.add("chalanStatus", 0);
		GetChalanHeader[] chArray = rest.postForObject(Constants.url + "getChalanHeadersByPlantAndStatus", map,
				GetChalanHeader[].class);
		
		chalanHeadList = new ArrayList<GetChalanHeader>(Arrays.asList(chArray));
		
		for(int i=0;i<chalanHeadList.size();i++) {
			
			chalanHeadList.get(i).setChalanDate(DateConvertor.convertToDMY(chalanHeadList.get(i).getChalanDate()));
		}
				
				System.err.println("Ajax chalanHeadList " + chalanHeadList.toString());

		
				return chalanHeadList ;
	}
	
	
	//editChalan
	
	List<Project> projList;
	List<GetChalanDetail> chDetailList;
	@RequestMapping(value = "/editChalan/{chalanId}", method = RequestMethod.GET)
	public ModelAndView editChalan(HttpServletRequest request, HttpServletResponse response, @PathVariable int chalanId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/chalan_edit");

			GetChalanHeader editChalan = new GetChalanHeader();
			/*
			 * for(int i=0;i<getOrdList.size();i++) {
			 * 
			 * if(getOrdList.get(i).getOrderId()==orderId) { editOrder=new GetOrder();
			 * editOrder=getOrdList.get(i); break; } }
			 */
			MultiValueMap<String, Object>

			map = new LinkedMultiValueMap<String, Object>();

			map.add("chalanId", chalanId);
			editChalan = rest.postForObject(Constants.url + "getChalanHeadersByChalanId", map, GetChalanHeader.class);
			editChalan.setChalanDate(DateConvertor.convertToDMY(editChalan.getChalanDate()));

			map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", editChalan.getCustId());
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));

			model.addObject("projList", projList);
			
			map = new LinkedMultiValueMap<String, Object>();
			
			map.add("chalanId", chalanId);
			GetChalanDetail[] chDetailArray = rest.postForObject(Constants.url + "getGetChalanDetailByChalanId", map,
					GetChalanDetail[].class);
			chDetailList = new ArrayList<GetChalanDetail>(Arrays.asList(chDetailArray));

			model.addObject("chDetailList", chDetailList);

			model.addObject("editChalan", editChalan);

			model.addObject("title", "Close Chalan");

		} catch (Exception e) {
			System.err.println("Exce in edit Chalan " + e.getMessage());
			e.printStackTrace();
		}
		
		return model;
	}
	
	//closeChalan
	
	@RequestMapping(value = "/closeChalan", method = RequestMethod.POST)
	public String closeChalan(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("chalan/chalan_edit");
			
			
			String inTime = request.getParameter("in_time");
			float inKm=Float.parseFloat(request.getParameter("in_km"));
			String chalanRemark = request.getParameter("chalan_remark");
			String costSegment = request.getParameter("cost_segment");
			String sitePerName = request.getParameter("site_per_name");
			String sitePerMob = request.getParameter("site_per_mob");
			
			int orderId=Integer.parseInt(request.getParameter("order_id"));
			int chalanId=Integer.parseInt(request.getParameter("chalan_id"));
			
			ChalanHeader chHeader=new ChalanHeader();
			
			
			chHeader.setChalanId(chalanId);
			chHeader.setChalanRemark(chalanRemark);
			chHeader.setVehTimeIn(inTime);
			
			chHeader.setInKm(inKm);
			chHeader.setCostSegment(costSegment);
			chHeader.setSitePersonName(sitePerName);
			chHeader.setSitePersonMob(sitePerMob);
			chHeader.setOrderId(orderId);
			
			chHeader.setStatus(1);
		
			List<ChalanDetail> chalanDList=new ArrayList<>();
			
			for(int i=0;i<chDetailList.size();i++) {
				
				try { 
					
					float itemQty=Float.parseFloat(request.getParameter("chQty"+chDetailList.get(i).getChalanDetailId()));
					float siteWidth=Float.parseFloat(request.getParameter("width"+chDetailList.get(i).getChalanDetailId()));
					float siteHeight=Float.parseFloat(request.getParameter("height"+chDetailList.get(i).getChalanDetailId()));
					float siteLength=Float.parseFloat(request.getParameter("length"+chDetailList.get(i).getChalanDetailId()));
					float siteTotal=Float.parseFloat(request.getParameter("itemTotal"+chDetailList.get(i).getChalanDetailId()));
					
					
					ChalanDetail det=new ChalanDetail();
					
					det.setChalanDetailId(chDetailList.get(i).getChalanDetailId());
					det.setItemQty(itemQty);
					det.setItemHeightSite(siteHeight);
					det.setItemWidthSite(siteWidth);
					det.setItemLengthSite(siteLength);
					det.setItemTotalSite(siteTotal);
					det.setItemId(chDetailList.get(i).getItemId());
					det.setOrderDetailId(chDetailList.get(i).getOrderDetailId());
					
					det.setStatus(1);
					
					chalanDList.add(det);
				}catch (Exception e) {
					
					System.err.println("Exce in getting chalan detail " +e.getMessage());
					e.printStackTrace();
				}
			}
			chHeader.setChalanDetailList(chalanDList);
			System.err.println(" update bean  chHeader " +chHeader.toString());

			
			Info chHeadInserRes = rest.postForObject(Constants.url + "closeChalanApi", chHeader,
					Info.class);

		}catch (Exception e) {
			System.err.println("Exce in closeChalanApi " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/showChalanList";
	
	}
}
