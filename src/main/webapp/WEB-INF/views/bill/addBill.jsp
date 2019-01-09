<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>
<c:url var="getPlantByCompanyId" value="/getPlantByCompanyId" />
<c:url var="getChalansByPO" value="/getChalanByPO" />
<c:url var="getPoByCust" value="/getPoByCust" />
<c:url var="getCustByPlantId" value="/getCustByPlantId" />
<c:url var="getCustInfoByCustId" value="/getCustInfoByCustId" />
<c:url var="getProjectByCustId" value="/getProjectByCustId" />
<c:url var="getOrdHeaderForChalan" value="/getOrdHeaderForChalan" />
<c:url var="getOrderDetailForChalan" value="/getOrderDetailForChalan" />
<c:url var="getChalanItems" value="/getChalanItems" />
<c:url var="getChalanSelectedItems" value="/getChalanSelectedItems" />




<c:url var="getChalanByCustAndProj" value="/getChalanByCustAndProj" />

<c:url var="sendEmailByBillId" value="/sendEmailByBillId" />

<c:url var="getItemsForBill" value="/getItemsForBill" />
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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.buttonload {
	background-color: white; /* Green background */
	border: none; /* Remove borders */
	color: #ec268f; /* White text */
	padding: 12px 15px; /* Some padding */
	font-size: 13px; /* Set a font-size */
	display: none;
}

/* Add a right margin to each icon */
.fa {
	margin-left: -12px;
	margin-right: 8px;
}
</style>
</head>
<body onload="sendEmailByBillHeadId(${billHeadId},${custId})">


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
							<div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showBillList"
									style="color: black"><strong>All Bills</strong></a>
							</div>


						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertBill"
								id="addBill" onsubmit="validate()" method="post">

								<div class="row">
									<div class="col-md-2">Select Company*</div>

									<div class="col-md-4">
										<select id="companyId" name="companyId" class="standardSelect"
											tabindex="1" onchange="onCompanyChange(this.value)">
											<option value="">Select</option>
											<c:forEach items="${compList}" var="comp">

												<c:choose>
													<c:when test="${comp.companyId==editPlant.companyId}">
														<option value="${comp.companyId}" selected>${comp.compName}</option>
													</c:when>
													<c:otherwise>
														<option value="${comp.companyId}">${comp.compName}
													</c:otherwise>
												</c:choose>
												<%-- 	<option value="${comp.companyId}">${comp.compName}</option> --%>
											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Select Plant</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select plant')"
											onchange="getData()">
											<option value="-1">Select</option>

											<c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group"></div>


								<div class="row">
									<div class="col-md-2">Bill Date</div>
									<div class="col-md-4">
										<input type="text" autocomplete="off" id="bill_date"
											name="bill_date" required style="width: 100%;"
											class="form-control" value="${curDate}"> <span
											class="error" aria-live="polite"></span>
									</div>
									<div class="col-md-2">Bill No</div>
									<div class="col-md-4">
										<input type="text" readonly id="bill_no" name="bill_no"
											style="width: 100%;" class="form-control"
											value="${doc.docPrefix}-${doc.srNo}"> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Customer</div>
									<div class="col-md-4">
										<select id="cust_name" name="cust_name" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')"
											onchange="getCustInfo()">

										</select>
									</div>
									<div class="col-md-2">Select Project</div>

									<div class="col-md-4">
										<select id="proj_id" name="proj_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select project')"
											onchange="getChalanByCustAndProj()">
											<option selected value="-1">Select</option>

											<c:forEach items="${projList}" var="proj">

												<c:choose>
													<c:when test="${quotHeader.projId==proj.projId}">
														<option selected value="${proj.projId}">${proj.projName}</option>
													</c:when>
													<c:otherwise>
														<option value="${proj.projId}">${proj.projName}</option>
													</c:otherwise>
												</c:choose>

											</c:forEach>

										</select>
									</div>
								</div>
								<div class="form-group"></div>


								<div id="divCheckbox" style="display: none;">
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
								</div>
								<!-- <div class="form-group"></div> -->

								<%-- <div class="row">

									<div class="col-md-2">Select PO</div>

									<div class="col-md-10">
										<select id="po_id" name="po_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select PO')"
											onchange="getChalansByPo(this.value)">
											<option selected value="-1">Select</option>

											<c:forEach items="${projList}" var="proj">

												<option value="${proj.projId}">${proj.projName}</option>

											</c:forEach>

										</select>
									</div>
								</div> --%>
								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Select Chalan</div>

									<div class="col-md-10">
										<select id="chalan_id" name="chalan_id" class="standardSelect"
											tabindex="1" required multiple="multiple"
											oninvalid="setCustomValidity('Please select Challan')">
											<option value="-1">Select</option>

											<%-- <c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach> --%>
										</select>
									</div>
								</div>
								<div class="form-group"></div>

								<div class="row"></div>
								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Remark</div>

									<div class="col-md-6">
										<input type="text" id="bill_remark" name="bill_remark"
											required style="width: 100%;" class="form-control" value="-">
										<span class="error" aria-live="polite"></span>
									</div>
									<div class="col-md-3">
										<input type="button" onclick="getChalanItems()"
											class="btn btn-primary" value="Search">
										<button class="buttonload" id="loader">
											<i class="fa fa-spinner fa-spin"></i>Loading
										</button>
									</div>
									<!-- <div class="col-md-2">Cost Segment</div>

										<div class="col-md-4">
											<input type="text" id="cost_segment" name="cost_segment"
												 value="-" required style="width: 100%;" class="form-control">
											<span class="error" aria-live="polite"></span>
										</div> -->

								</div>

								<%-- <input type="checkbox" value="${item.itemId}" name="selectItem"> --%>

								<div class="card-body card-block">
									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>
												<th style="text-align: center">Sr</th>
												<th style="text-align: center">Item Name</th>
												<th style="text-align: center">UOM</th>
												<th style="text-align: center">Rate</th>
												<th style="text-align: center">Chalan Qty</th>
												<th style="text-align: center">Qty</th>
												<th style="text-align: center">Disc %</th>
												<th style="text-align: center">Taxable Amt</th>
												<th style="text-align: center">Disc Amt</th>
												<th style="text-align: center">Tax %</th>
												<th style="text-align: center">Tax Amt</th>
												<th style="text-align: center">Total</th>
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
										<!-- onclick="validateForm()" -->
										<input type="Submit" class="btn btn-primary" value="Submit">

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
		
			/*  $("#selChalanItem").click(function () {
	              $('#table_grid1-data-table tbody input[type="checkbox"]').prop('checked', this.checked);
	          }); */
			 
		});
	</script>

	<script type="text/javascript">
	
	function validate()
	{
	    var okay=true;
	    var companyId=document.getElementById("companyId").value;
	    var plantId=document.getElementById("plant_id").value;
	    var custId=document.getElementById("cust_name").value;
	    var projId=document.getElementById("proj_id").value;
	  //  var poId=document.getElementById("po_id").value;
		var chalanId=document.getElementById("chalan_id").value;
		
		
		 var billDate=document.getElementById("bill_date").value;
		 //var costSegment=document.getElementById("cost_segment").value;
		var billRemark =document.getElementById("bill_remark").value;

		  if(companyId<0 || companyId=="" || companyId==null){
		    	okay=false;
		    	alert("Please select company");
		    }  else
	      if(plantId<0 || plantId=="" || plantId==null){
	    	okay=false;
	    	alert("Please select plant");
	    }
	    else if(custId<0 || custId=="" || custId==null){
	    	okay=false;
	    	alert("Please select customer ");
	    }
	    else if(projId<0 || projId=="" || projId==null){
	    	okay=false;
	    	alert("Please select project ");
	    }
	   /*  else if(poId<0 || poId=="" || poId==null){
	    	okay=false;
	    	alert("Please select PO ");
	    } */
	  
	    else if(chalanId<0 || chalanId=="" || chalanId==null){
	    	okay=false;
	    	alert("Please select chalan ");
	    }
	  
	    else if(billDate<0 || billDate=="" || billDate==null){
	    	okay=false;
	    	alert("Please select bill date ");
	    }
	 
	    else if(billRemark<0 || billRemark=="" || billRemark==null){
	    	okay=false;
	    	alert("Please enter bill remark ");
	    }
	    return okay;
	   
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
function onCompanyChange(companyId) { 
	var valid = true;

	if (companyId == null || companyId == "") {
		valid = false;
		alert("Please Select Company");
	}

	if (valid == true) {

		$.getJSON('${getPlantByCompanyId}', {
			companyId : companyId,
			ajax : 'true',
		},

		function(data) {
			var html;
			var len = data.length;
			var html = '<option selected value="-1"  >Select</option>';

			for (var i = 0; i < len; i++) {

				html += '<option value="' + data[i].plantId + '">'
						+ data[i].plantName + '</option>';

			}
			html += '</option>';

			$('#plant_id').html(html);
			$("#plant_id").trigger("chosen:updated");
			
		
			var dataTable = $('#bootstrap-data-table')
			.DataTable();
			dataTable.clear().draw();

		});
	}//end of if

}
</script>
	<script type="text/javascript">
function getChalansByPo(poId)
{
	
$
.getJSON(
		'${getChalansByPO}',
		{
			poId : poId,
			ajax : 'true',

		},
		function(data) {
			var len = data.length;
			//alert("data " +JSON.stringify(data));
			var html='<option value="-1">All</option>';

			for (var i = 0; i < len; i++) {
				var challanData=data[i].chalanNo+"--"+data[i].chalanDate

				html += '<option value="' + data[i].chalanId + '">'
						+challanData+ '</option>';

			}
			html += '</option>';
			$('#chalan_id').html(html);
			$("#chalan_id").trigger("chosen:updated");
		});
}
</script>
	<script type="text/javascript">
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
				$
				.getJSON(
						'${getPoByCust}',
						{
							custId : custId,
							ajax : 'true',

						},
						function(data) {
							var len = data.length;
							//alert("data " +JSON.stringify(data));
							var html='<option value="-1">Select</option>';

							for (var i = 0; i < len; i++) {
								var poData=data[i].poNo+"--"+data[i].poDate

								html += '<option value="' + data[i].poId + '">'
										+poData+ '</option>';

							}
							html += '</option>';
							$('#po_id').html(html);
							$("#po_id").trigger("chosen:updated");
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
	function getChalanItems(){
		alert("hii");
		var chalanId = document.getElementById("chalan_id").value;
		var isValid = validate();
		alert("chalanId"+chalanId);
		

		if (isValid) {
		$('#loader').show();
		$.getJSON('${getItemsForBill}', {
			 
			  chalanId : chalanId,
					ajax : 'true',

				},

				function(data) {
				
					if(data==null){
						alert("data not found")
					}else{
					}
					var len = data.length;
								var dataTable = $('#bootstrap-data-table').DataTable();
								
			dataTable.clear().draw();
			$.each(data,function(i, v) {

		var chalanQty = '<input  type="text" value="0.0"  oninput="calculation('+i+','+v.itemId+')"   class="form-control"  id="chalanQty'+i+''+v.itemId+'" name="chalanQty'+i+''+v.itemId+'"  onkeypress="return allowOnlyNumber(event);"/>'
		var discPer = '<input  type="text" value="0.0" oninput="calculation('+i+','+v.itemId+')" class="form-control"  id="discPer'+i+''+v.itemId+'" name="discPer'+i+''+v.itemId+'"  onkeypress="return allowOnlyNumber(event);"/>'
		var taxPer = '<input  type="text" readonly value="'+(v.cgstPer+v.sgstPer)+'" oninput="calculation('+i+','+v.itemId+')" class="form-control"  id="taxPer'+i+''+v.itemId+'" name="taxPer'+i+''+v.itemId+'"  onkeypress="return allowOnlyNumber(event);"/>'
		var rate = '<input  type="hidden" value="'+v.orderRate+'" class="form-control"  id="orderRate'+i+''+v.itemId+'" name="orderRate'+i+''+v.itemId+'"  onkeypress="return allowOnlyNumber(event);"/>'
		var isTaxIncluding = '<input  type="hidden" value="'+v.isTaxIncluding+'" class="form-control"  id="isTaxIncluding'+i+''+v.itemId+'" name="isTaxIncluding'+i+''+v.itemId+'"  onkeypress="return allowOnlyNumber(event);"/>'

		var discAmt = '<p id="discAmt'+i+''+v.itemId+'">0.0</p>'
		var taxAmt = '<p id="taxAmt'+i+''+v.itemId+'">0.0</p>'
		var total = '<p id="total'+i+''+v.itemId+'">0.0</p>'
		var taxableAmt = '<p id="taxableAmt'+i+''+v.itemId+'">0.0</p>'

		var index=i+1;
		dataTable.row.add([ index,v.itemName,v.itemUom,v.orderRate+""+rate+""+isTaxIncluding,v.itemQty,chalanQty,discPer,taxableAmt,discAmt,taxPer,taxAmt,total]).draw();
		$('#loader').hide();
			
			});
		});
		}
		}
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
	
	function setChalanItem(chalanQty,itemId,poId,poDetailId,remOrdQty,orderDetId,orderId,index,uomId,itemName){
		//alert("Item Id" +itemId + "poId" +poId+ "detail PO Id " +poDetailId + "rem Ord Qty  " +remOrdQty );
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
		
		  $.getJSON('${getChalanSelectedItems}', {
					ajax : 'true',

				},

				function(data) {
				
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

													var itemChalanTotal = '<input  type="text" readonly  class="form-control"  id="itemChalanTotal'+v.itemId+'" name="itemChalanTotal'+v.itemId+'"/>'
													var index=i+1;
													dataTable.row
															.add(
																	[
																		index,v.itemName,v.uomName,v.chalanQty,width,height,length,itemChalanTotal])
															.draw();
												});
				 
		} );
	}
	
	</script>
	<script type="text/javascript">
	
	function calculation(key,itemId){
		var orderRate=parseFloat(document.getElementById("orderRate"+key+''+itemId).value); //alert(orderRate+"orderRate");
		var chalanQty=parseFloat(document.getElementById("chalanQty"+key+''+itemId).value); //alert(chalanQty+"chalanQty");
		var discPer=parseFloat(document.getElementById("discPer"+key+''+itemId).value); //alert(discPer+"discPer");
		var taxPer=parseFloat(document.getElementById("taxPer"+key+''+itemId).value); //alert(taxPer+"taxPer");
        var isTaxIncluding=document.getElementById("isTaxIncluding"+key+''+itemId).value; //alert(isTaxIncluding+"isTaxIncluding");
        
        if(isTaxIncluding==0)
        	{
        	  var taxableAmt=(orderRate*chalanQty);
        	  //alert("taxableAmt"+taxableAmt);
        	
        	 
         	 if(discPer>0){
         	  var discAmt=((taxableAmt*discPer)/100); //alert("discAmt"+discAmt);
          	  document.getElementById("discAmt"+key+''+itemId).innerHTML=discAmt.toFixed(2);
         	 
         	 taxableAmt=taxableAmt-discAmt;
         	 var taxAmt=((taxableAmt*taxPer)/100);
         	 var total=(taxableAmt+taxAmt);//alert(total+"total");
     
        	 document.getElementById("taxAmt"+key+''+itemId).innerHTML=taxAmt.toFixed(2);
        	 
         	 document.getElementById("total"+key+''+itemId).innerHTML=total.toFixed(2);
         	 document.getElementById("taxableAmt"+key+''+itemId).innerHTML=taxableAmt.toFixed(2);
         	 }
         	 else
         		 {
         		  var discAmt=0.0;
         		 document.getElementById("discAmt"+key+''+itemId).innerHTML=discAmt.toFixed(2);
         		 var taxAmt=((taxableAmt*taxPer)/100);
         		 var total=(taxableAmt+taxAmt);//alert("total"+total);
         		 document.getElementById("taxAmt"+key+''+itemId).innerHTML=taxAmt.toFixed(2);
         		 document.getElementById("total"+key+''+itemId).innerHTML=total.toFixed(2);
         		 document.getElementById("taxableAmt"+key+''+itemId).innerHTML=taxableAmt.toFixed(2);
         		 }
         	  
         	 

        	}else
        		{
        		//alert((orderRate+100)+"Order Rate");alert((100 + taxPer));
        		var baseRate = ((orderRate * 100) / (100 + taxPer));
        		 //alert("baseRate"+baseRate);
        		 var taxableAmt=(baseRate*chalanQty);//alert("taxableAmt"+taxableAmt);
            
            	 
             	 if(discPer>0){
             	   var discAmt=((taxableAmt*discPer)/100);
             	  document.getElementById("discAmt"+key+''+itemId).innerHTML=discAmt.toFixed(2);
             	 
             	  taxableAmt=taxableAmt-discAmt;
             	  var taxAmt=((taxableAmt*taxPer)/100);
             	  var total=(taxableAmt+taxAmt);//alert("total"+total);
            	  document.getElementById("taxAmt"+key+''+itemId).innerHTML=taxAmt.toFixed(2);
             	  document.getElementById("total"+key+''+itemId).innerHTML=total.toFixed(2);
            	  document.getElementById("taxableAmt"+key+''+itemId).innerHTML=taxableAmt.toFixed(2);
             	 }
             	 else
             		 {
             		  var discAmt=0.0;
             		 document.getElementById("discAmt"+key+''+itemId).innerHTML=discAmt.toFixed(2);
             		 var taxAmt=((taxableAmt*taxPer)/100);
             		 var total=(taxableAmt+taxAmt);//alert("total"+total);
             		 document.getElementById("total"+key+''+itemId).innerHTML=total.toFixed(2);
             		  document.getElementById("taxableAmt"+key+''+itemId).innerHTML=taxableAmt.toFixed(2);
             		 }
             	  
             	 // document.getElementById("taxAmt"+key+''+itemId).innerHTML=taxAmt;
             	 

        		}
	}
	
	</script>




	<script type="text/javascript">
	function sendEmailByBillHeadId(billHeadId,custId){
		var isValid=true;
		if(billHeadId==0){
			
		}
		else{
			
			
		
				window
				.open('${pageContext.request.contextPath}/pdf?url=pdf/showBillsPdf/'
						+ billHeadId);
				
				$
				/* .getJSON(
						'${sendEmailByBillId}',
						{
							billHeadId : billHeadId,
							custId : custId,
							ajax : 'true',

						},
						function(data) {
							
						});
				
				
				 */
			
			
			
			
		}
	}
	</script>

	<script type="text/javascript">
		function singleBillPdf(id) {
			window
			.open('${pageContext.request.contextPath}/pdf?url=pdf/showBillsPdf/'
					+ id);
		}
		</script>

	<script type="text/javascript">
function toggle() {
			  checkboxes = document.getElementsByName('selChalanItem');
			  for(var i=0, n=checkboxes.length;i<n;i++) {
			    checkboxes[i].checked = source.checked;
			  }
			  }
				  
			
			</script>

	<script
		src="${pageContext.request.contextPath}/resources/assets/js/init.js"
		type="text/javascript" charset="utf-8"></script>



	<script type="text/javascript">
	// on proj_id  change function show Order Header Select Option List 
		function getChalanByCustAndProj() {
			var projId=document.getElementById("proj_id").value;
			var custId = document.getElementById("cust_name").value;
		
			
				$
						.getJSON(
								'${getChalanByCustAndProj}',
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
										var challanData=data[i].chalanNo+"--"+data[i].chalanDate

										html += '<option value="' + data[i].chalanId + '">'
												+challanData+ '</option>';

									}
									html += '</option>';
									$('#chalan_id').html(html);
									$("#chalan_id").trigger("chosen:updated");
								});
		}
	
	</script>

</body>
</html>