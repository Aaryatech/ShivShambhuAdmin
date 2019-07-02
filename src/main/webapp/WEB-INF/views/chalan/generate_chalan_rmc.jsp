<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<style>
.tooltip {
	position: relative;
	display: inline-block;
	border-bottom: 1px dotted black;
}

.tooltip .tooltiptext {
	visibility: hidden;
	width: 120px;
	background-color: black;
	color: #fff;
	text-align: center;
	border-radius: 6px;
	padding: 5px 0;
	/* Position the tooltip */
	position: absolute;
	z-index: 1;
}

.tooltip:hover .tooltiptext {
	visibility: visible;
}
</style>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
	height: 80%;
	overflow: scroll;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

#overlay {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(101, 113, 119, 0.5);
	z-index: 2;
	cursor: pointer;
}

#text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 25px;
	color: white;
	transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
}

.bg-overlay {
	background: linear-gradient(rgba(0, 0, 0, .7), rgba(0, 0, 0, .7)),
		url("${pageContext.request.contextPath}/resources/images/smart.jpeg");
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center center;
	color: #fff;
	height: auto;
	width: auto;
	padding-top: 10px;
	padding-left: 20px;
}
</style>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getCustByPlantId" value="/getCustByPlantId" />
<c:url var="getCustInfoByCustId" value="/getCustInfoByCustId" />
<c:url var="getProjectByCustId" value="/getProjectByCustId" />
<c:url var="getOrdHeaderForChalan" value="/getOrdHeaderForChalan" />
<c:url var="getOrderDetailForChalan" value="/getOrderDetailForChalan" />
<c:url var="setChalanItem" value="/setChalanItem" />
<c:url var="getChalanSelectedItems" value="/getChalanSelectedItems" />
<c:url var="getEnqNumber" value="/getEnqNumber" />

<meta name="description" content="Sufee Admin - HTML5 Admin Template">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="apple-touch-icon" href="apple-icon.png">
<link rel="shortcut icon" href="favicon.ico">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/normalize.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/themify-icons.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/flag-icon.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/cs-skin-elastic.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/lib/chosen/chosen.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/scss/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/lib/chosen/chosen.min.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/lib/datatable/dataTables.bootstrap.min.css">
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800'
	rel='stylesheet' type='text/css'>

<style type="text/css">
.right {
	text-align: right;
}

.left {
	text-align: left;
}
</style>

<style>
.alert {
	padding: 20px;
	background-color: red;
	color: white;
}

.alert1 {
	padding: 20px;
	background-color: green;
	color: white;
}

.closebtn {
	margin-left: 15px;
	color: white;
	font-weight: bold;
	float: right;
	font-size: 22px;
	line-height: 20px;
	cursor: pointer;
	transition: 0.3s;
}

.closebtn:hover {
	color: black;
}
</style>

<style type="text/css">
.right {
	text-align: right;
}

.left {
	text-align: left;
}
</style>
</head>
<body onload="showOrderItemPopupNew()">


	<!-- Left Panel -->
	<jsp:include page="/WEB-INF/views/common/left.jsp"></jsp:include>
	<!-- Left Panel -->


	<!-- Header-->
	<jsp:include page="/WEB-INF/views/common/right.jsp"></jsp:include>
	<!-- Header-->



	<div class="content mt-3">
		<div class="animated fadeIn">

			<div class="row">

				<c:choose>
					<c:when test="${isError==1}">

						<div class="alert">

							<span class="closebtn"
								onclick="this.parentElement.style.display='none';">&times;</span>
							<strong>Failed !</strong> Data not submitted !!
						</div>

					</c:when>

					<c:when test="${isError==2}">

						<div class="alert1">

							<span class="closebtn"
								onclick="this.parentElement.style.display='none';">&times;</span>
							<strong>Success</strong> Data Submitted !!
						</div>

					</c:when>

				</c:choose>

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<%-- <div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showAddCustomer"><strong>Add
										Customer</strong></a>
							</div> --%>


						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertChalan"
								id="insertChalan" method="post">

								<div class="row">

									<div class="col-md-2">Plant*</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select plant name')"
											onchange="getData()">
											<option value="${rmcOrd.plantId}">${rmcOrd.plantName}</option>


											<%-- <c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach> --%>
										</select>
									</div>
									<div class="col-md-2">Customer*</div>
									<div class="col-md-4">
										<select id="cust_name" name="cust_name" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')"
											onchange="getCustInfo()">
											<option value="${rmcOrd.custId}">${rmcOrd.custName}</option>

										</select>
									</div>

								</div>
								<div class="form-group"></div>
								<input type="hidden" id="isRmcPage" name="isRmcPage" value="1">

								<!-- <div id="divCheckbox" style="display: none;">
									<div class="form-group"></div>
									<div class="row">

										<div class="col-md-2">Cust Type Name</div>

										<div class="col-md-4">
											<input type="text" id="custTypeName" name="custTypeName"
												readonly required style="width: 100%;" class="form-control">
											<span class="error" aria-live="polite"></span>
										</div>

										<div class="col-md-2">Cust Mobile No</div>

										<div class="col-md-4">
											<input type="text" readonly id="custMobNo" name="custMobNo"
												style="width: 100%;" class="form-control"> <span
												class="error" aria-live="polite"></span>
										</div>

									</div>
								</div> -->
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Select Project*</div>

									<div class="col-md-4">
										<select id="proj_id" name="proj_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select project')"
											onchange="getOrderHeaders()">
											<option value="${rmcOrd.projId}">${rmcOrd.projName}</option>

											<%-- <c:forEach items="${projList}" var="proj">
											
											<c:choose>
											<c:when test="${quotHeader.projId==proj.projId}">
												<option selected value="${proj.projId}">${proj.projName}</option>
											</c:when>
											<c:otherwise>
												<option value="${proj.projId}">${proj.projName}</option>
											</c:otherwise>
											</c:choose>
											
											</c:forEach> --%>

										</select>
									</div>

									<div class="col-md-2">Select Order*</div>

									<div class="col-md-2">
										<select id="order_id" name="order_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select order')"
											onchange="showOrderItemPopup(this.value)">
											<option value="${rmcOrd.orderId}">${rmcOrd.orderNo}</option>

											<%-- <c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach> --%>
										</select>
									</div>

									<div class="col-md-2">
										<input type="button" onclick="showPopupForGetData()"
											class="btn btn-primary" value="View Item">

									</div>




								</div>


								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Batch No*</div>

									<div class="col-md-4">
										<input type="text" id="batchNo" name="batchNo"
											autocomplete="off" required value="-" style="width: 100%;"
											class="form-control"> <span class="error"
											aria-live="polite"></span>
									</div>

									<div class="col-md-2">RST/Royalty No*</div>

									<div class="col-md-4">
										<input type="text" id="rstNo" name="rstNo" required
											autocomplete="off" style="width: 100%;" value="-"
											class="form-control"> <span class="error"
											aria-live="polite"></span>
									</div>

								</div>

								<div class="row"></div>
								<div class="form-group"></div>

								<div id="myModal" class="modal">

									<div class="modal-content" style="color: black;">
										<span class="close" id="close">&times;</span>
										<h5 style="text-align: left;">Add Challan Quantity</h5>
										<div class=" box-content">

											<div
												style="overflow: scroll; height: auto; width: auto; overflow: auto">
												<table style="width: 100%" id="table_grid1"
													class="table table-striped table-bordered"
													data-page-length='-1'>
													<thead>
														<tr>
															<th style="width: 5%;" align="center">Sr</th>
															<th style="width: 20%;" align="center">Item Name</th>
															<th style="width: 8%;" align="center">UOM</th>
															<th style="width: 10%;" align="center">Order
																Quantity</th>
															<th style="width: 10%;" align="center">Order
																Remaining Quantity</th>
															<th style="width: 10%;" align="center">PO Quantity</th>
															<th style="width: 10%;" align="center">PO Remaining
																Quantity</th>
															<th style="width: 20%;" align="center">Challan
																Quantity</th>
														</tr>
													</thead>
													<tbody>

													</tbody>
												</table>
											</div>

											<input type="button" id="poupSubButton"
												onclick="showChalnItems()" class="btn btn-primary"
												value="Submit">

										</div>
										<br>

									</div>

								</div>
								<!-- end of myModal div -->

								<input type="hidden" name="item_id" id="item_id" value="0">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Challan Date*</div>
									<div class="col-md-4">
										<input type="text" autocomplete="off" id="chalan_date"
											name="chalan_date" required style="width: 100%;"
											class="form-control" value="${curDate}"> <span
											class="error" aria-live="polite"></span>
									</div>
									<div class="col-md-2">Challan No*</div>
									<div class="col-md-4">
										<input type="text" readonly id="chalan_no" name="chalan_no"
											style="width: 100%;" class="form-control"> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>

								<input type="hidden" id="itemName" value="${rmcOrd.itemName}">

								<input type="hidden" id="uomName" value="${rmcOrd.uomName}">


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Select Driver</div>
									<div class="col-md-4">
										<select id="driver_id" name="driver_id" style="width: 100%;"
											class="standardSelect" tabindex="1" required>
											<option value="-1">Select</option>
											<c:forEach items="${usrList}" var="usr">
												<c:choose>
													<c:when test="${editChalan.driverId== usr.userId}">

														<option selected value="${usr.userId}">${usr.usrName}</option>

													</c:when>
													<c:otherwise>
														<option value="${usr.userId}">${usr.usrName}</option>


													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>
									<div class="col-md-2">Select Vehicle</div>

									<div class="col-md-4">
										<select id="veh_id" name="veh_id" style="width: 100%;"
											class="standardSelect" tabindex="1" required
											onchange="checkVehicle(this.value)">
											<option value="-1">Select</option>
											<c:forEach items="${vehicleList}" var="veh">

												<option value="${veh.vehicleId}">${veh.vehNo}-${veh.vehicleName}</option>

											</c:forEach>
										</select>
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">


									<div class="col-md-2" id="out_div"
										style="display: none; width: 100%">Outside Vehicle</div>

									<div class="col-md-10" id="newdriver"></div>
								</div>




								<div class="form-group"></div>
								<div class="row">


									<div class="col-md-2">Out Time</div>

									<div class="col-md-4">
										<input type="time" id="out_time" name="out_time"
											style="width: 100%;" value="${curTime}" class="form-control">
									</div>
									<div class="col-md-2">Out Kilometer</div>

									<div class="col-md-4">
										<input type="text" id="out_km" name="out_km" required
											autocomplete="off"
											onkeypress="return allowOnlyNumber(event);"
											style="width: 100%;" class="form-control" maxlength="10">
										<span class="error" aria-live="polite"></span>
									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Challan Remark</div>

									<div class="col-md-4">
										<input type="text" id="chalan_remark" name="chalan_remark"
											required style="width: 100%;" class="form-control" value="-">
										<span class="error" aria-live="polite"></span>
									</div>
									<!-- <div class="col-md-2">Cost Segment</div>

									<div class="col-md-4">
										<input type="text" id="cost_segment" name="cost_segment"
											value="-"   style="width: 100%;" class="form-control">
										<span class="error" aria-live="polite"></span>
									</div> -->

								</div>

								<%-- <input type="checkbox" value="${item.itemId}" name="selectItem"> --%>

								<div class="card-body card-block">
									<table id="bootstrap-data-table"
										class="table table-striped table-bordered"
										data-page-length='-1'>
										<thead>
											<tr>
												<th style="text-align: center">Sr</th>
												<th style="text-align: center">Item Name</th>
												<th style="text-align: center">UOM</th>
												<th style="text-align: center">PO Qty</th>
												<th style="text-align: center">PO Rem Qty</th>
												<th style="text-align: center">Order Qty</th>
												<th style="text-align: center">Order Rem Qty</th>
												<th style="text-align: center">Challan Qty</th>
												<th style="text-align: center">Width</th>
												<th style="text-align: center">Height</th>
												<th style="text-align: center">Length</th>
												<th style="text-align: center">Challan Total</th>
											</tr>
										</thead>

										<tbody>


										</tbody>

									</table>
								</div>
								<div class="form-group"></div>

								<!-- <div class="row">

									<div class="col-md-2">Taxable Value</div>
									<div class="col-md-4">1444</div>

									<div class="col-md-2">Tax Value</div>
									<div class="col-md-4">88.36</div>

								</div> -->
								<div class="form-group"></div>
								<div class="row">

									<!-- 	<div class="col-md-2">Other Cost After Tax</div>

									<div class="col-md-3">845</div> -->
									<!-- <div class="col-md-2">Total</div>

									<div class="col-md-3" id="ordTotal">0</div>
 -->
									<div class="col-md-2">
										<input type="button" onclick="validateForm()"
											class="btn btn-primary" value="Submit">

									</div>

								</div>

							</form>
						</div>
					</div>
				</div>
			</div>


		</div>
		<!-- .animated -->
	</div>
	<!-- .content -->


	<!-- .animated -->
	<!-- .content -->


	<!-- Footer -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	<!-- Footer -->



	<script
		src="${pageContext.request.contextPath}/resources/assets/js/vendor/jquery-2.1.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/plugins.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>

	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/datatables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/dataTables.bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/dataTables.buttons.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/buttons.bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/jszip.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/pdfmake.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/vfs_fonts.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/buttons.html5.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/buttons.print.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/buttons.colVis.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/data-table/datatables-init.js"></script>

	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/chosen/chosen.jquery.min.js"></script>

	<script type="text/javascript">
		
		function checkVehicle(vehId){
		//	alert(vehId);
		document.getElementById("chalan_remark").value="";
			var outVeh=${settingList[0].settingValue};
			//alert("outVeh" +outVeh);
			
			if(vehId==outVeh){
				
				$("#newdriver").text('');
		         $("#out_div").show();
		         $("#newdriver").show();
		         
	           $("#newdriver").append("<input type='text' id='new_dri_veh' name='new_dri_veh' style='width: 100%' onchange='setRemark()'/><br/>");
			}else{
				document.getElementById("chalan_remark").value="-";
				  $("#out_div").hide();
				  $("#newdriver").hide();
			}
			         
		}
		function setRemark(){
			//alert("fkdsf");
			var remark=document.getElementById("new_dri_veh").value;
			//alert("remark " +remark);
			document.getElementById("chalan_remark").value=remark;
			//$("#chalan_remark").value=remark;
		}
		</script>

	<script>
		jQuery(document).ready(function() {
			jQuery(".standardSelect").chosen({
				disable_search_threshold : 1,
				no_results_text : "Oops, nothing found!",
				width : "100%"
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#bootstrap-data-table').DataTable();
			
			 $("#selChalanItem").click(function () {
	              $('#table_grid1-data-table tbody input[type="checkbox"]').prop('checked', this.checked);
	          });
			 
		});
	</script>

	<script type="text/javascript">
	
	function validateForm()
	{
	    //var checkboxs=document.getElementsByName("selectItem");
	    var okay=true;
	   /*  for(var i=0,l=checkboxs.length;i<l;i++)
	    {
	        if(checkboxs[i].checked)
	        {
	            okay=true;
	            break;
	        }
	    } */
	   // alert("Okay " +okay);

	    var plantId=document.getElementById("plant_id").value;
	    var custId=document.getElementById("cust_name").value;
		var orderId=document.getElementById("order_id").value;
		
		 var driverId=document.getElementById("driver_id").value;
		 var vehId=document.getElementById("veh_id").value;
		
		 var chalanDate=document.getElementById("chalan_date").value;
		// var costSegment=document.getElementById("cost_segment").value;
		 var outTime=document.getElementById("out_time").value;
		 var outKm=document.getElementById("out_km").value;
		 var chalanRemark =document.getElementById("chalan_remark").value;

	 
	      if(plantId<0 || plantId=="" || plantId==null){
	    	okay=false;
	    	alert("Please select plant name");
	    }
	    else if(custId<0 || custId=="" || custId==null){
	    	okay=false;
	    	alert("Please select customer ");
	    }
	      
	    else if(orderId<0 || orderId=="" || orderId==null){
	    	okay=false;
	    	alert("Please select order ");
	    }
	      
	    else if(driverId<0 || driverId=="" || driverId==null){
	    	okay=false;
	    	alert("Please select driver ");
	    }
	      
	    else if(vehId<0 || vehId=="" || vehId==null){
	    	okay=false;
	    	alert("Please select vehicle ");
	    }
	      
	      
	      //
	      
	      
	    else if(chalanDate<0 || chalanDate=="" || chalanDate==null){
	    	okay=false;
	    	alert("Please select chalan date ");
	    }
	      
	   
	      
	    else if(outTime<0 || outTime=="" || outTime==null){
	    	okay=false;
	    	alert("Please select out time ");
	    }
	      
	    else if(outKm<0 || outKm=="" || outKm==null){
	    	okay=false;
	    	alert("Please enter out kilometer ");
	    }
	    else if(chalanRemark<0 || chalanRemark=="" || chalanRemark==null){
	    	okay=false;
	    	alert("Please enter chalan remark ");
	    }
	      
	    else if(okay){

	    	var form=document.getElementById("insertChalan");
	    	form.submit();
	    }
	   
	}
	</script>



	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=chalan_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			
			$('input[id$=del_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>
	<script type="text/javascript">
	
		function allowOnlyNumber(evt){
	    var charCode = (evt.which) ? evt.which : event.keyCode
	    if (charCode == 46){
	        var inputValue = $("#floor").val();
	        var count = (inputValue.match(/'.'/g) || []).length;
	        
	        if(count<1){
	            if (inputValue.indexOf('.') < 1){
	                return true;
	            }
	            return false;
	        }else{
	            return false;
	        }
	    }
	    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
	        return false;
	    }
	    return true;
	}
	
	</script>
	<script type="text/javascript">
function allowOnlyNumber1(evt)
	{
	  var charCode = (evt.which) ? evt.which : event.keyCode
	  if (charCode > 31 && charCode==46 && (charCode < 48 || charCode > 57))
		  	    return false;
	  return true;
	} 
	 </script>

	<script type="text/javascript">
	
	// on plant change function 
		function getData() { 
			var plantId = document.getElementById("plant_id").value;
			var valid = true;

			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please select plant");
			}

			if (valid == true) {

				$.getJSON('${getCustByPlantId}', {
					plantId : plantId,
					ajax : 'true',
				},

				function(data) {
					var html;
					var len = data.length;
					var html = '<option selected value="-1"  >Select</option>';

					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].custId + '">'
								+ data[i].custName + '</option>';

					}
					html += '</option>';

					$('#cust_name').html(html);
					$("#cust_name").trigger("chosen:updated");
					getOrderHeaders();
					//getCustInfo();
				
					var dataTable = $('#bootstrap-data-table')
					.DataTable();
					dataTable.clear().draw();

				});
			}//end of if
		
		}
	</script>

	<script type="text/javascript">
	// on cust change function 
		function getCustInfo() {
			$('#divCheckbox').show();
			var custId = document.getElementById("cust_name").value;
			var valid = true;
			if (custId == null || custId == "") {
				valid = false;
				alert("Please Select Customer");
				
			
				var dataTable = $('#bootstrap-data-table')
				.DataTable();
		dataTable.clear().draw();

			}
			else if(custId<0){
				valid = false;
							
				var dataTable = $('#bootstrap-data-table')
				.DataTable();
		dataTable.clear().draw();

			}
			if (valid == true) {

				$
						.getJSON(
								'${getCustInfoByCustId}',
								{
									custId : custId,
									ajax : 'true',

								},
								function(data) {
									document.getElementById("custTypeName").value = data.custTypeName;
									document.getElementById("custMobNo").value = data.custMobNo;
								});

				
				$	.getJSON(
						'${getProjectByCustId}',
						{
							custId : custId,
							ajax : 'true',
						},
						function(data) {
							//var html;
							var len = data.length;
							//alert("data " +JSON.stringify(data));
							var html='<option value="-1">Select All</option>';

							for (var i = 0; i < len; i++) {
								var projData=data[i].projName+"-"+data[i].address

								html += '<option value="' + data[i].projId + '">'
										+projData+ '</option>';

							}
							html += '</option>';
							$('#proj_id').html(html);
							$("#proj_id").trigger("chosen:updated");
							getOrderHeaders();

						});
			}// end of if valid= true
			
		}
	</script>

	<script type="text/javascript">
	// on proj_id  change function show Order Header Select Option List 
		function getOrderHeaders() {
			var projId=document.getElementById("proj_id").value;
			var custId = document.getElementById("cust_name").value;
		
			
				$
						.getJSON(
								'${getOrdHeaderForChalan}',
								{
									projId : projId,
									custId : custId,
									ajax : 'true',
								},

								function(data) {
									
									var len = data.length;
									//alert("data " +JSON.stringify(data));
									var html='<option value="-1">Select</option>';
									for (var i = 0; i < len; i++) {
										var orderData=data[i].orderNo+"__"+data[i].orderDate

										html += '<option value="' + data[i].orderId + '">'
												+orderData+ '</option>';

									}
									html += '</option>';
									$('#order_id').html(html);
									$("#order_id").trigger("chosen:updated");
								});
		}
	
	</script>

	<script type="text/javascript">
	function showOrderItemPopupNew(){
		var isValid=true;
		var orderId=${rmcOrd.orderId};
		var itemId=${rmcOrd.itemId};
		if(orderId==-1){
			isValid=false;
		}
		if(isValid==true){ 
			 
			 
		  $.getJSON('${getOrderDetailForChalan}', {
			  orderId : orderId,
					ajax : 'true',

				},

				function(data) { 
					// alert("detail  " +JSON.stringify(data));
					
					if(data[0].alertMsg==" ")
					{
					}
				else
					{
				alert(data[0].alertMsg);
					}
				 
					var len = data.length;
					// alert("Len " +len);
								var dataTable = $('#bootstrap-data-table')
										.DataTable();
								dataTable.clear().draw();
								$
										.each(
												data,
												function(i, v) {
													
													if(itemId==v.itemId)
														{
													
													
													//var checkB = '<input  type="checkbox" name="selChalanItem" id='+v.itemId+' class="check"  value='+v.itemId+'/>'
		var width = '<input  type="text" value="0"  class="form-control"  id="width'+v.itemId+'" name="width'+v.itemId+'"   oninput="calcChalanTotal('+v.itemId+')"  onkeypress="return allowOnlyNumber(event);"/>'
		var height = '<input  type="text" value="0"  class="form-control"  id="height'+v.itemId+'" name="height'+v.itemId+'" oninput="calcChalanTotal('+v.itemId+')" onkeypress="return allowOnlyNumber(event);"/>'
		var length = '<input  type="text" value="0"  class="form-control"  id="length'+v.itemId+'" name="length'+v.itemId+'" oninput="calcChalanTotal('+v.itemId+')" onkeypress="return allowOnlyNumber(event);"/>'
		 var itemChalanTotal = '<input  type="text" value="0" readonly class="form-control"  id="itemChalanTotal'+v.itemId+'" name="itemChalanTotal'+v.itemId+'"/>'
		 var chalanQty = '<input  type="text" value="0"   class="form-control"  id="chalanQty'+v.itemId+'" name="chalanQty'+v.itemId+'" oninput="chalanItemValidation(this.value,'+v.remOrdQty+','+v.itemId+')"  onkeypress="return allowOnlyNumber(event);"/>'
			
			
		  
			var index=i+1;
			dataTable.row
					.add(
							[
								index,v.itemName,v.uomName,v.poQty,v.poRemainingQty,v.orderQty,v.remOrdQty,chalanQty,width,height,length,itemChalanTotal]).node().id='tr'+v.itemId;
			dataTable.draw(false);	
			
														}
			});
								
								
					 
			} );
		
		}
		getEnqNum();
	}
	</script>
	<script type="text/javascript">
		function chalanItemValidation(chalanQty,remOrdQty,itemId){
		//alert("chalanQty Id" +remOrdQty  );
		//alert("Item Id" +itemId + "poId" +poId+ "detail PO Id " +poDetailId + "rem Ord Qty  " +remOrdQty );
	  
			if(chalanQty<0){
				 
				document.getElementById("chalanQty"+itemId).value="0";
				alert("Please Enter Correct Chalan Quantity");
			}
			
			if(chalanQty>0){ 
				if(remOrdQty<chalanQty){
					document.getElementById("tr"+itemId).style.backgroundColor ="white";
					document.getElementById("chalanQty"+itemId).value="0";
					alert("Chalan Quantity can not be greater than  Remaining Order Quantity ");
				
				}else
					{
				document.getElementById("tr"+itemId).style.backgroundColor ="#66ff66";
					}
			}
			if(chalanQty==0){
				document.getElementById("tr"+itemId).style.backgroundColor ="white";
				
			}
		}
		</script>



	<!-- 	<script type="text/javascript">
	function showOrderItemPopup(orderId){
		
		var itemId=${rmcOrd.itemId};
		var isValid=true;
		if(orderId==-1){
			isValid=false;
		}
		if(isValid==true){
		 var span = document.getElementById("close");
			var modal = document.getElementById('myModal');

			modal.style.display = "block";
			span.onclick = function() {
			    modal.style.display = "none"; 
			}

			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
			    if (event.target == modal) {
			        modal.style.display = "none";
			        
			    }
			}

		  $.getJSON('${getOrderDetailForChalan}', {
			  orderId : orderId,
					ajax : 'true',

				},

				function(data) {
					
					//alert("detail  " +JSON.stringify(data));
					if(data==null){
						document.getElementById('poupSubButton').disabled=true;
						
					}else{
						document.getElementById('poupSubButton').disabled=false;
					}
					
					
					

					if(data[0].alertMsg==" ")
					{
					}
				else
					{
				alert(data[0].alertMsg);
					}
					var len = data.length;
					//alert("Len " +len);
								var dataTable = $('#table_grid1')
										.DataTable();
								dataTable.clear().draw();
								$
										.each(
												data,
												function(i, v) {
													
													if(itemId==v.itemId)
														{
													//var checkB = '<input  type="checkbox" name="selChalanItem" id='+v.itemId+' class="check"  value='+v.itemId+'/>'
		var chalanQty = '<input  type="text" value="0"  class="form-control"  id="chalanQty'+v.itemId+'" name="chalanQty'+v.itemId+'" onchange="setChalanItem(this.value,'+v.itemId+','+v.poId+','+v.poDetailId+','+v.remOrdQty+','+v.orderDetId+','+v.orderId+','+i+','+v.uomId+',/'+v.itemName+'/,/'+v.uomName+'/)" onkeypress="return allowOnlyNumber(event);"/>'
													//var itemTotal = '<input  type="text" readonly  class="form-control"  id="itemTotal'+v.itemId+'" name='+v.itemId+'/>'
													var index=i+1;
													dataTable.row
															.add(
																	[
																		index,v.itemName,v.uomName,v.orderQty,v.remOrdQty,v.poQty,v.poRemainingQty,chalanQty])
															.draw();
													
														}
												});
				
		} );
		
		}
		getEnqNum();
	}
	</script>
 -->



	<script type="text/javascript">
	function allowOnlyNumber(evt){
	    var charCode = (evt.which) ? evt.which : event.keyCode
	    if (charCode == 46){
	        var inputValue = $("#floor").val();
	        var count = (inputValue.match(/'.'/g) || []).length;
	        
	        if(count<1){
	            if (inputValue.indexOf('.') < 1){
	                return true;
	            }
	            return false;
	        }else{
	            return false;
	        }
	    }
	    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
	        return false;
	    }
	    return true;
	}
	
	function setChalanItem(chalanQty,itemId,poId,poDetailId,remOrdQty,orderDetId,orderId,index,uomId,itemName,uomName){
		//alert("Item Id" +itemId + "poId" +poId+ "detail PO Id " +poDetailId + "rem Ord Qty  " +remOrdQty );
		//alert("in  setChalanItem");
		var isValid=true;
		
		if(remOrdQty<chalanQty){
			isValid=false;
			document.getElementById("chalanQty"+itemId).value="0";
			alert("Chalan Quantity can not be greater than  Remaining Order Quantity ");
		}
	if(chalanQty<0){
		isValid=false;
		document.getElementById("chalanQty"+itemId).value="0";
		alert("Please Enter Correct Chalan Quantity");
	}
	
		if(isValid==true){
	 	$
		.getJSON(
				'${setChalanItem}',
				{
					chalanQty : chalanQty,
					itemId : itemId,
					poId : poId,
					poDetailId : poDetailId,
					remOrdQty : remOrdQty,
					orderDetId : orderDetId,
					orderId : orderId,
					index : index,
					uomId : uomId,
					itemName :itemName,
					uomName: uomName,
					
					ajax : 'true',
				},

				function(data) {
					//alert("Data  " +JSON.stringify(data));
					
				}); 
	 	
		}//end if 
	}
	
	</script>
	<script type="text/javascript">
	function showChalnItems(){
		//alert("hii....");
		
		  $.getJSON('${getChalanSelectedItems}', {
					ajax : 'true',

				},

				function(data) {
					var modal = document.getElementById('myModal');
			        modal.style.display = "none";

					//alert("detail  " +JSON.stringify(data));
					
					var len = data.length;
					//alert("Len " +len);
								var dataTable = $('#bootstrap-data-table')
										.DataTable();
								dataTable.clear().draw();
								$
										.each(
												data,
												function(i, v) {
													//var checkB = '<input  type="checkbox" name="selChalanItem" id='+v.itemId+' class="check"  value='+v.itemId+'/>'
		var width = '<input  type="text" value="0"  class="form-control"  id="width'+v.itemId+'" name="width'+v.itemId+'"   oninput="calcChalanTotal('+v.itemId+')"  onkeypress="return allowOnlyNumber(event);"/>'
		var height = '<input  type="text" value="0"  class="form-control"  id="height'+v.itemId+'" name="height'+v.itemId+'" oninput="calcChalanTotal('+v.itemId+')" onkeypress="return allowOnlyNumber(event);"/>'
		var length = '<input  type="text" value="0"  class="form-control"  id="length'+v.itemId+'" name="length'+v.itemId+'" oninput="calcChalanTotal('+v.itemId+')" onkeypress="return allowOnlyNumber(event);"/>'
	    var itemChalanTotal = '<input  type="text"  value="'+v.chalanQty+'" readonly class="form-control"  id="itemChalanTotal'+v.itemId+'" name="itemChalanTotal'+v.itemId+'"/>'
	    var chalanQty = '<input  type="text" value="'+v.chalanQty+'" readonly class="form-control"  id="chalanQty'+i+''+v.itemId+'" name="chalanQty'+i+''+v.itemId+'"  onkeypress="return allowOnlyNumber(event);"/>'
	  
		
		//alert(chalanQty);
													var index=i+1;
													dataTable.row
															.add(
																	[
																		index,v.itemName,v.uomName,chalanQty,width,height,length,itemChalanTotal])
															.draw();
												});
				 
		} );
	}
	
	</script>
	<script type="text/javascript">
	
	function calcChalanTotal(itemId){
		alert("Hi  inside calcChalanTotal(itemId) method ");

		var width=document.getElementById("width"+itemId).value;
		var height=document.getElementById("height"+itemId).value;
		var length=document.getElementById("length"+itemId).value;

		//alert("Hi height " +height + "length  "+length + "width  " +width);;
	}
	
	</script>

	<script type="text/javascript">
	function showPopupForGetData(){
		
		var orderId=document.getElementById("order_id").value;
		var itemId=${rmcOrd.itemId};
		var isValid=true;
		
		
		if(orderId==-1){
			alert("Please select order");
			isValid=false;
		}
		if(isValid==true){
		 var span = document.getElementById("close");
			var modal = document.getElementById('myModal');

			modal.style.display = "block";
			span.onclick = function() {
			    modal.style.display = "none"; 
			}

			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
			    if (event.target == modal) {
			        modal.style.display = "none";
			        
			    }
			}

		  $.getJSON('${getOrderDetailForChalan}', {
			  orderId : orderId,
					ajax : 'true',

				},

				function(data) {
					
					//alert("detail  " +JSON.stringify(data));
					if(data==null){
						document.getElementById('poupSubButton').disabled=true;
						
					}else{
						document.getElementById('poupSubButton').disabled=false;
					}
					

					if(data[0].alertMsg==" ")
					{
					}
				else
					{
				alert(data[0].alertMsg);
					}
					var len = data.length;
					//alert("Len " +len);
								var dataTable = $('#table_grid1')
										.DataTable();
								dataTable.clear().draw();
								$
										.each(
												data,
												function(i, v) {
													
													if(itemId==v.itemId)
													{
													//var checkB = '<input  type="checkbox" name="selChalanItem" id='+v.itemId+' class="check"  value='+v.itemId+'/>'
		var chalanQty = '<input  type="text" value="0"  class="form-control"  id="chalanQty'+v.itemId+'" name="chalanQty'+v.itemId+'" onchange="setChalanItem(this.value,'+v.itemId+','+v.poId+','+v.poDetailId+','+v.remOrdQty+','+v.orderDetId+','+v.orderId+','+i+','+v.uomId+',/'+v.itemName+'/,/'+v.uomName+'/)" onkeypress="return allowOnlyNumber(event);"/>'
													//var itemTotal = '<input  type="text" readonly  class="form-control"  id="itemTotal'+v.itemId+'" name='+v.itemId+'/>'
													var index=i+1;
													dataTable.row
															.add(
																	[
																		index,v.itemName,v.uomName,v.orderQty,v.remOrdQty,v.poQty,v.poRemainingQty,chalanQty])
															.draw();
													
													}
												});
				
		} );
		
		}
	}
	</script>


	<script type="text/javascript">
		$(function() {
			$('#submitForm').submit(
					function() {
						$("input[type='submit']", this).val("Please Wait...")
								.attr('disabled', 'disabled');
						return true;
					});
		});
	</script>


	<script type="text/javascript">
function toggle() {
			  checkboxes = document.getElementsByName('selChalanItem');
			  for(var i=0, n=checkboxes.length;i<n;i++) {
			    checkboxes[i].checked = source.checked;
			  }
			  }
				  
			
			</script>



	<script>
		function getEnqNum() {
			
			var plantId = document.getElementById("plant_id").value;
			//alert("Plant Id " +plantId);
		
			var valid = true;
			if (valid == true) {

				$.getJSON('${getEnqNumber}', {

					plantId : plantId,
					ajax : 'true',

				},

				function(data) {
				
					var sn=data.plantFax1;
					var count=data.exVar1;
					
					//alert("sn"+sn);
					//lert("count"+count);
					var c;
					var len1=count.length;
			//alert("len"+len1);
					
					
					
					if (len1 == 1) {
						var c= "000"+count;

					} else if (len1 == 2) {
						var c= "00"+count;
					} else if (len1 == 3) {
						var c= "0"+count;

					}
					else
					{
					var c= count;
					}
				//alert("var c:"+c);
					var enqNum="CH"+ "-" +sn+"-"+c;
				//alert("enqNum"+enqNum);
					
					document.getElementById("chalan_no").value=enqNum;
				});

				
			}//end of if

		}
	
	
	
		</script>
</body>
</html>