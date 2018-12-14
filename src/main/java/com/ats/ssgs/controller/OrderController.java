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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.common.DateConvertor;
import com.ats.ssgs.model.PoHeader;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.Document;
import com.ats.ssgs.model.master.Info;
import com.ats.ssgs.model.master.LoginResUser;
import com.ats.ssgs.model.master.Plant;
import com.ats.ssgs.model.master.Project;
import com.ats.ssgs.model.order.GetOrder;
import com.ats.ssgs.model.order.GetOrderDetail;
import com.ats.ssgs.model.order.GetPoForOrder;
import com.ats.ssgs.model.order.OrderDetail;
import com.ats.ssgs.model.order.OrderHeader;
import com.ats.ssgs.model.order.TempOrdDetail;
import com.ats.ssgs.model.order.TempOrdHeader;
import com.ats.ssgs.model.quot.QuotHeader;

@Controller
@Scope("session")
public class OrderController {

	List<Plant> plantList;

	List<Cust> custList;

	RestTemplate rest = new RestTemplate();

	List<PoHeader> poHeadList;

	List<GetPoForOrder> poDetailForOrdList;

	int isError = 0;

	@RequestMapping(value = "/showAddOrder", method = RequestMethod.GET)
	public ModelAndView showAddOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("order/addOrder");

			model.addObject("title", "Add Order");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 3);

			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);

			model.addObject("doc", doc);
			model.addObject("isError", isError);
			isError = 0;
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			String curDate = dateFormat.format(new Date());
			
			model.addObject("curDate" ,curDate);

		} catch (Exception e) {

			System.err.println("exception In showAddOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/getPOHeaderByCustId", method = RequestMethod.GET)
	public @ResponseBody List<PoHeader> getPOHeaderById(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int custId = Integer.parseInt(request.getParameter("custId"));

		map.add("custId", custId);
		map.add("statusList", "0,1");

		PoHeader[] poHeadArray = rest.postForObject(Constants.url + "getPoHeaderByCustIdAndStatus", map,
				PoHeader[].class);
		poHeadList = new ArrayList<PoHeader>(Arrays.asList(poHeadArray));

		for (int i = 0; i < poHeadList.size(); i++) {

			poHeadList.get(i).setPoDate(DateConvertor.convertToDMY(poHeadList.get(i).getPoDate()));
		}

		System.err.println("Ajax PoHeader  List by cust Id" + poHeadList.toString());

		return poHeadList;

	}

	@RequestMapping(value = "/getPoDetailForOrderByPoId", method = RequestMethod.GET)
	public @ResponseBody List<GetPoForOrder> getPoDetailForOrderByPoId(HttpServletRequest request,
			HttpServletResponse response) {

		ordHeader = null;
		tempOrdDetail = new ArrayList<>();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int poId = Integer.parseInt(request.getParameter("poId"));

		map.add("poId", poId);

		GetPoForOrder[] poHeadArray = rest.postForObject(Constants.url + "getPoDetailForOrderByPoId", map,
				GetPoForOrder[].class);
		poDetailForOrdList = new ArrayList<GetPoForOrder>(Arrays.asList(poHeadArray));

		System.err.println("Ajax poDetailForOrdList  List by po Id" + poDetailForOrdList.toString());

		return poDetailForOrdList;

	}

	TempOrdHeader ordHeader;

	List<TempOrdDetail> tempOrdDetail;

	@RequestMapping(value = "/getTempOrderHeader", method = RequestMethod.GET)
	public @ResponseBody List<TempOrdDetail> getTempOrderHeader(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println(" in getTempOrderHeader");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int poDetailId = Integer.parseInt(request.getParameter("poDetailId"));

		float qty = Float.parseFloat(request.getParameter("qty"));
		float poRemainingQty = Float.parseFloat(request.getParameter("poRemainingQty"));
		float itemTotal = Float.parseFloat(request.getParameter("itemTotal"));
		float orderRate = Float.parseFloat(request.getParameter("poRate"));
		
		int orderDetId =Integer.parseInt(request.getParameter("orderDetId"));
		if (tempOrdDetail == null) {
			System.err.println("Ord Head =null ");
			/*
			 * ordHeader=new TempOrdHeader();
			 * 
			 * 
			 * ordHeader.setOrderTotal(itemTotal); ordHeader.setOtherCostAfterTax(0);
			 * ordHeader.setTaxableValue(itemTotal); ordHeader.setTaxValue(0)
			 */;
			 
			TempOrdDetail detail = new TempOrdDetail();
			tempOrdDetail = new ArrayList<>();

			detail.setItemId(itemId);
			detail.setOrderQty(qty);
			detail.setPoDetailId(poDetailId);
			detail.setTotal(itemTotal);
			detail.setOrderRate(orderRate);
detail.setOrderDetId(orderDetId);
			tempOrdDetail.add(detail);
			// ordHeader.setOrdDetailList(tempOrdDetail);
			// return tempOrdDetail;
		} else {

			int flag = 0;
			System.err.println("Else ord detail  not  null ");
			for (int i = 0; i < tempOrdDetail.size(); i++) {

				System.err.println("inside for loop");
				if (tempOrdDetail.get(i).getItemId() == itemId) {
					flag = 1;
					System.err.println("item id matched ");
					tempOrdDetail.get(i).setOrderQty(qty);
					tempOrdDetail.get(i).setTotal(itemTotal);
					break;
					/*
					 * ordHeader.setOrderTotal(ordHeader.getOrdDetailList().get(i).getTotal());
					 * ordHeader.setOtherCostAfterTax(0);
					 * ordHeader.setTaxableValue(ordHeader.getOrdDetailList().get(i).getTotal());
					 * ordHeader.setTaxValue(0);
					 */
				}
			}

			if (flag == 0) {
				System.err.println("Else new Item");
				TempOrdDetail detail = new TempOrdDetail();

				detail.setItemId(itemId);
				detail.setOrderQty(qty);
				detail.setPoDetailId(poDetailId);
				detail.setTotal(itemTotal);
				detail.setOrderRate(orderRate);
				detail.setOrderDetId(orderDetId);


				/*
				 * ordHeader.setOrderTotal(ordHeader.getOrderTotal()+itemTotal);
				 * ordHeader.setOtherCostAfterTax(0);
				 * ordHeader.setTaxableValue(ordHeader.getTaxableValue()+itemTotal);
				 * ordHeader.setTaxValue(0);
				 */

				tempOrdDetail.add(detail);
				// ordHeader.setOrdDetailList(tempOrdDetail);
				// return tempOrdDetail;
			}

		}
		System.err.println("temp Ord Detail " + tempOrdDetail.toString());
		return tempOrdDetail;

	}

	// insertOrder
	@RequestMapping(value = "/insertOrder", method = RequestMethod.POST)
	public String insertOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = dateFormat.format(new Date());

			model = new ModelAndView("order/addOrder");

			model.addObject("title", "Add Order");

			int plantId = Integer.parseInt(request.getParameter("plant_id"));
			int custId = Integer.parseInt(request.getParameter("cust_name"));
			int projId = Integer.parseInt(request.getParameter("proj_id"));
			int poId = Integer.parseInt(request.getParameter("po_id"));

			String ordDate = request.getParameter("ord_date");
			String delDate = request.getParameter("del_date");

			// float itemTotal = Float.parseFloat(request.getParameter("itemTotal"));

			OrderHeader ordHeader = new OrderHeader();

			List<OrderDetail> ordDetailList = new ArrayList<>();
			String NA = "NA";
			ordHeader.setCustId(custId);
			ordHeader.setDeliveryDate(DateConvertor.convertToYMD(delDate));
			ordHeader.setDelStatus(1);
			ordHeader.setExDate1(curDate);
			ordHeader.setExDate2(curDate);
			ordHeader.setExVar1(NA);
			ordHeader.setExVar2(NA);
			ordHeader.setExVar3(NA);
			ordHeader.setOrderDate(DateConvertor.convertToYMD(ordDate));
			ordHeader.setOrderId(0);
			ordHeader.setPlantId(plantId);
			ordHeader.setPoId(poId);
			ordHeader.setProdDate(DateConvertor.convertToYMD(delDate));
			ordHeader.setProjId(projId);

			float headerTotal = 0;
			

			HttpSession session = request.getSession();
			LoginResUser login = (LoginResUser) session.getAttribute("UserDetail");

			for (int i = 0; i < tempOrdDetail.size(); i++) {

				OrderDetail orDetail = new OrderDetail();

				orDetail.setDelStatus(1);
				orDetail.setExDate1(curDate);
				orDetail.setExDate2(curDate);
				orDetail.setExVar1(NA);
				orDetail.setExVar2(NA);
				orDetail.setExVar3(NA);
				orDetail.setItemId(tempOrdDetail.get(i).getItemId());
				orDetail.setOrderQty(tempOrdDetail.get(i).getOrderQty());
				orDetail.setOrderRate(tempOrdDetail.get(i).getOrderRate());

				orDetail.setPoDetailId(tempOrdDetail.get(i).getPoDetailId());
				orDetail.setPoId(poId);

				orDetail.setTotal(tempOrdDetail.get(i).getTotal());
				orDetail.setRemOrdQty(orDetail.getOrderQty());

				ordDetailList.add(orDetail);

				headerTotal = orDetail.getTotal() + headerTotal;

			}
			ordHeader.setOrderValue(headerTotal);
			ordHeader.setTotal(headerTotal);
			ordHeader.setExInt1(login.getUser().getUserId());
			

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("docCode", 3);

			Document doc = rest.postForObject(Constants.url + "getDocument", map, Document.class);
			ordHeader.setOrderNo(doc.getDocPrefix() + "" + doc.getSrNo());

			if (poDetailForOrdList.get(0).getTaxAmt() == 0) {

				ordHeader.setIsTaxIncluding(0);// tax not included

			} else {

				ordHeader.setIsTaxIncluding(1);// yes tax included

			}

			ordHeader.setOrderDetailList(ordDetailList);

			OrderHeader insertOrdHeadRes = rest.postForObject(Constants.url + "saveOrder", ordHeader,
					OrderHeader.class);

			if (insertOrdHeadRes != null) {

				isError = 2;

				map = new LinkedMultiValueMap<String, Object>();

				map.add("srNo", doc.getSrNo() + 1);
				map.add("docCode", doc.getDocCode());

				Info updateDocSr = rest.postForObject(Constants.url + "updateDocSrNo", map, Info.class);

			} else {

				isError = 1;
			}
			System.err.println("insertOrdHeadRes " + insertOrdHeadRes.toString());

		} catch (Exception e) {
			isError = 1;
			System.err.println("exception In insertOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return "redirect:/showAddOrder";
	}

	// ***********************************//

	// showOrderList and options for edit and delete

	@RequestMapping(value = "/showOrderList", method = RequestMethod.GET)
	public ModelAndView showOrderList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("order/orderlist");

			model.addObject("title", "Order List");

			Plant[] plantArray = rest.getForObject(Constants.url + "getAllPlantList", Plant[].class);
			plantList = new ArrayList<Plant>(Arrays.asList(plantArray));

			model.addObject("plantList", plantList);

			String fromDate = null, toDate = null;

			if (request.getParameter("fromDate") == null || request.getParameter("fromDate") == "") {

				System.err.println("onload call  ");

				Calendar date = Calendar.getInstance();
				date.set(Calendar.DAY_OF_MONTH, 1);

				Date firstDate = date.getTime();

				DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

				fromDate = dateFormat.format(firstDate);

				toDate = dateFormat.format(new Date());
				System.err.println("cu Date  " + fromDate + "todays date   " + toDate);

			} else {

				System.err.println("After page load call");
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");

			}

			// getOrderListBetDate

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("exception In showAddOrder at OrderController " + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	List<GetOrder> getOrdList = new ArrayList<>();

	@RequestMapping(value = "/getOrderListBetDate", method = RequestMethod.GET)
	public @ResponseBody List<GetOrder> getOrderListBetDate(HttpServletRequest request, HttpServletResponse response) {

		System.err.println(" in getTempOrderHeader");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int plantId = Integer.parseInt(request.getParameter("plantId"));
		int custId = Integer.parseInt(request.getParameter("custId"));

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		map.add("plantId", plantId);
		map.add("custId", custId);
		map.add("fromDate", DateConvertor.convertToYMD(fromDate));
		map.add("toDate", DateConvertor.convertToYMD(toDate));
		//map.add("status", 0);

		GetOrder[] ordHeadArray = rest.postForObject(Constants.url + "getOrderListBetDate", map, GetOrder[].class);
		getOrdList = new ArrayList<GetOrder>(Arrays.asList(ordHeadArray));

		return getOrdList;
	}

	List<GetOrderDetail> ordDetailList;

	@RequestMapping(value = "/editOrder/{orderId}", method = RequestMethod.GET)
	public ModelAndView editOrder(HttpServletRequest request, HttpServletResponse response, @PathVariable int orderId) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("order/editOrder");

			GetOrder editOrder = null;
			/*
			 * for(int i=0;i<getOrdList.size();i++) {
			 * 
			 * if(getOrdList.get(i).getOrderId()==orderId) { editOrder=new GetOrder();
			 * editOrder=getOrdList.get(i); break; } }
			 */
			MultiValueMap<String, Object>

			map = new LinkedMultiValueMap<String, Object>();

			map.add("orderId", orderId);
			editOrder = rest.postForObject(Constants.url + "getOrderHeaderById", map, GetOrder.class);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("custId", editOrder.getCustId());
			List<Project> projList;
			Project[] projArray = rest.postForObject(Constants.url + "getProjectByCustId", map, Project[].class);
			projList = new ArrayList<Project>(Arrays.asList(projArray));

			map = new LinkedMultiValueMap<String, Object>();

			map.add("orderHeaderId", orderId);
			GetOrderDetail[] ordDetailArray = rest.postForObject(Constants.url + "getOrderDetailList", map,
					GetOrderDetail[].class);
			ordDetailList = new ArrayList<GetOrderDetail>(Arrays.asList(ordDetailArray));

			model.addObject("orderDetailList", ordDetailList);

			model.addObject("editOrder", editOrder);
			model.addObject("projList", projList);

			model.addObject("title", "Edit Order");

		} catch (Exception e) {
			System.err.println("Exce in edit Order " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}
	
	//
	
	
	// updateOrder
		@RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
		public String updateOrder(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String curDate = dateFormat.format(new Date());

				model = new ModelAndView("order/addOrder");

			//	model.addObject("title", "Add Order");

				int orderId = Integer.parseInt(request.getParameter("orderId"));
				//int custId = Integer.parseInt(request.getParameter("cust_name"));
				int projId = Integer.parseInt(request.getParameter("proj_id"));
				int poId = Integer.parseInt(request.getParameter("po_id"));

				//String ordDate = request.getParameter("ord_date");
				String delDate = request.getParameter("del_date");

				// float itemTotal = Float.parseFloat(request.getParameter("itemTotal"));

				OrderHeader ordHeader = new OrderHeader();
				
				MultiValueMap<String, Object>

				map = new LinkedMultiValueMap<String, Object>();
					map.add("orderHeaderId", orderId);
				ordHeader = rest.postForObject(Constants.url + "getOrdHeaderByOrdId", map, OrderHeader.class);


				List<OrderDetail> orDetList = new ArrayList<>();
				String NA = "NA";
				ordHeader.setDeliveryDate(DateConvertor.convertToYMD(delDate));
				ordHeader.setProdDate(DateConvertor.convertToYMD(delDate));
				ordHeader.setProjId(projId);

				float headerTotal = 0;

				for (int i = 0; i < ordDetailList.size(); i++) {

					OrderDetail orDetail = new OrderDetail();
					
					
					float orderQty=Float.parseFloat(request.getParameter("ordQty"+ordDetailList.get(i).getItemId()));
					float itemTotal=Float.parseFloat(request.getParameter("itemTotal"+ordDetailList.get(i).getItemId()));

					 orDetail.setOrderDetId(ordDetailList.get(i).getOrderDetId());
					orDetail.setDelStatus(1);
					orDetail.setExDate1(curDate);
					orDetail.setExDate2(curDate);
					orDetail.setExVar1(NA);
					orDetail.setExVar2(NA);
					orDetail.setExVar3(NA);
					orDetail.setItemId(ordDetailList.get(i).getItemId());
					orDetail.setOrderQty(orderQty);
					orDetail.setOrderRate(ordDetailList.get(i).getOrderRate());

					orDetail.setPoDetailId(ordDetailList.get(i).getPoDetailId());
					orDetail.setPoId(poId);
					orDetail.setRemOrdQty((orDetail.getRemOrdQty()-orDetail.getOrderQty()));
					orDetail.setTotal(itemTotal);
					orDetail.setOrderId(ordDetailList.get(i).getOrderId());
					orDetail.setStatus(ordDetailList.get(i).getStatus());
					orDetList.add(orDetail);

					headerTotal = orDetail.getTotal() + headerTotal;

				}
				ordHeader.setOrderValue(headerTotal);
				ordHeader.setTotal(headerTotal);


				/*if (poDetailForOrdList.get(0).getTaxAmt() == 0) {

					ordHeader.setIsTaxIncluding(0);// tax not included

				} else {

					ordHeader.setIsTaxIncluding(1);// yes tax included

				}
*/
				ordHeader.setOrderDetailList(orDetList);

				OrderHeader updateOrdRes = rest.postForObject(Constants.url + "saveOrder", ordHeader,
						OrderHeader.class);

				if (updateOrdRes != null) {

					isError = 2;


				} else {

					isError = 1;
				}
				System.err.println("updateOrdRes " + updateOrdRes.toString());

			} catch (Exception e) {
				isError = 1;
				System.err.println("exception In updateOrder at OrderController " + e.getMessage());

				e.printStackTrace();

			}

			return "redirect:/showOrderList";
		}

		
		
		
		@RequestMapping(value = "/deleteRecordofOrders", method = RequestMethod.POST)
		public String deleteRecordofCompany(HttpServletRequest request, HttpServletResponse response) {
			try {

				String[] orderIds = request.getParameterValues("selectOrderToDelete");
				System.out.println("id are"+orderIds);

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < orderIds.length; i++) {
					sb = sb.append(orderIds[i] + ",");

				}
				String items = sb.toString();
				items = items.substring(0, items.length() - 1);
				
				System.err.println("orderIds "+items.toString());

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("orderIds", items);

				Info errMsg = rest.postForObject(Constants.url + "deleteMultiOrder", map, Info.class);
				System.err.println("inside method /deleteRecordofOrders");
			} catch (Exception e) {

				System.err.println("Exception in /deleteRecordofOrders @OrderController  " + e.getMessage());
				e.printStackTrace();
			}

			return "redirect:/showOrderList";
		}

}
