<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getPOHeaderByCustId" value="/getPOHeaderByCustId" />

<c:url var="getCustByPlantId" value="/getCustByPlantId" />

<c:url var="getCustInfoByCustId" value="/getCustInfoByCustId" />

<c:url var="getProjectByCustId" value="/getProjectByCustId" />


<c:url var="getPoDetailForOrderByPoId"
	value="/getPoDetailForOrderByPoId" />


<c:url var="getTempOrderHeader" value="/getTempOrderHeader" />


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
<body onload="getData()">


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
						<div class="col-sm-12">
							<div
								class="sufee-alert alert with-close alert-danger alert-dismissible fade show">

								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>Data not submitted</strong>
							</div>
						</div>
					</c:when>

					<c:when test="${isError==2}">
						<div class="col-sm-12">
							<div
								class="sufee-alert alert with-close alert-success alert-dismissible fade show">

								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>Data Submitted Successfully</strong>
							</div>
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
							<form action="${pageContext.request.contextPath}/insertOrder"
								method="post" onsubmit="disableButton()">

								<div class="row">

									<div class="col-md-2">Select Plant*</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select plant name')"
											onchange="getData()">
											<option value="">Select</option>

											<c:forEach items="${plantList}" var="plant">
												<c:if test="${sessionScope.plantId==0}">
													<option value="${plant.plantId}">${plant.plantName}</option>
												</c:if>
												<c:if test="${sessionScope.plantId!=0}">
													<c:choose>
														<c:when test="${sessionScope.plantId==plant.plantId}">
															<option value="${plant.plantId}" selected>${plant.plantName}</option>
														</c:when>
														<c:otherwise>
															<option value="${plant.plantId}" disabled>${plant.plantName}</option>
														</c:otherwise>
													</c:choose>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2">Customer*</div>
									<div class="col-md-4">
										<select id="cust_name" name="cust_name" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')"
											onchange="getCustInfo()">
											<option selected value="-1">Select</option>

										</select>
									</div>

								</div>
								<!-- <div class="form-group"></div>
								
								<div class="row">

									
								</div> -->


								<div class="form-group"></div>



								<div class="row">

									<div class="col-md-2">Select Project*</div>

									<div class="col-md-4">
										<select id="proj_id" name="proj_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select project')"
											onchange="try{setCustomValidity('')}catch(e){}">
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

									<div class="col-md-2">Select Purchase Order*</div>

									<div class="col-md-4">
										<select id="po_id" name="po_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select po ')"
											onchange="getPoDetailItem()">
											<option value="-1">Select</option>

										</select>
									</div>
								</div>

								<div id="divCheckbox" style="display: none;">
									<div class="form-group"></div>
									<div class="row">



										<div class="col-md-2">Customer Type*</div>

										<div class="col-md-4">
											<input type="text" id="custTypeName" name="custTypeName"
												readonly required style="width: 100%;" class="form-control">
											<span class="error" aria-live="polite"></span>
										</div>

										<div class="col-md-2">Customer Mobile No.*</div>

										<div class="col-md-4">
											<input type="text" readonly id="custMobNo" name="custMobNo"
												style="width: 100%;" class="form-control"> <span
												class="error" aria-live="polite"></span>
										</div>

									</div>
								</div>
								<input type="hidden" name="item_id" id="item_id" value="0">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Order Date*</div>
									<div class="col-md-4">
										<input type="text" id="ord_date" autocomplete="off" required
											readonly name="ord_date" required style="width: 100%;"
											class="form-control" value="${curDate}"> <span
											class="error" aria-live="polite"></span>
									</div>
									<div class="col-md-2">Order No*</div>
									<div class="col-md-4">
										<input type="text" readonly id="ord_no" name="ord_no"
											style="width: 100%;" class="form-control"
											value="${doc.docPrefix}${doc.srNo}"> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>

								<input type="hidden" id="isEdit" name="isEdit" value="0">

								<input type="hidden" id="itemUomId" name="itemUomId" value="0">


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Delivery Date*</div>
									<div class="col-md-4">
										<input type="text" id="del_date" name="del_date"
											autocomplete="off" required readonly style="width: 100%;"
											class="form-control" value="${curDate}"> <span
											class="error" aria-live="polite"></span>
									</div>

									<div class="col-md-2">Dispatch Time*</div>
									<div class="col-md-4">
										<input type="time" id="disp_time" name="disp_time"
											autocomplete="off" required style="width: 100%;"
											class="form-control"
											onkeypress="return allowOnlyNumber(event);" value="0">
										<span class="error" aria-live="polite"></span>
									</div>

								</div>


								<div class="form-group"></div>

								<%-- <input type="checkbox" value="${item.itemId}" name="selectItem"> --%>

								<div class="card-body card-block">
									<table id="bootstrap-data-table1"
										class="table table-striped table-bordered">
										<thead>
											<tr>
												<th style="text-align: center">Sr &nbsp;</th>
												<th style="text-align: center">Item Name</th>
												<th style="text-align: center">Code</th>
												<th style="text-align: center">PO Qty</th>
												<th style="text-align: center">Remaining Qty</th>
												<th style="text-align: center">PO Rate</th>
												<th style="text-align: center">Order Qty</th>
												<th style="text-align: center">Item Total</th>
											</tr>
										</thead>

									</table>
								</div>
								<div class="form-group"></div>


								<div class="form-group"></div>
								<div class="row">

									<!-- 	<div class="col-md-2">Other Cost After Tax</div>

									<div class="col-md-3">845</div> -->
									<div class="col-md-2">Order Total</div>

									<div class="col-md-3" id="ordTotal">0</div>

									<div class="col-md-2">
										<input type="submit" disabled id="submitButton"
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
			$('#bootstrap-data-table1').DataTable();
		});
	</script>



	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=ord_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			
			$('input[id$=del_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>

	<script type="text/javascript">
	function disableButton(){
		  document.getElementById("submitButton").disabled=true;
	}
	// on plant change function 
		function getData() { 
			var plantId = document.getElementById("plant_id").value;
			document.getElementById("isEdit").value = 0;
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
					getCustInfo();

					//$('#po_id').html("-1");
					//$("#po_id").trigger("chosen:updated");
				
					
					var dataTable = $('#bootstrap-data-table1')
					.DataTable();
			dataTable.clear().draw();
			
			//getPoDetailItem();

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
				
				$('#po_id').html("-1");
				$("#po_id").trigger("chosen:updated");
				
				var dataTable = $('#bootstrap-data-table1')
				.DataTable();
		dataTable.clear().draw();

			}
			else if(custId<0){
				valid = false;
				
				$('#po_id').html("-1");
				$("#po_id").trigger("chosen:updated");
				
				var dataTable = $('#bootstrap-data-table1')
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
						'${getPOHeaderByCustId}',
						{
							custId : custId,
							ajax : 'true',
						},
						function(data) {
							var html;
							var len = data.length;
							//alert("data " +JSON.stringify(data));
												var html = '<option selected value="-1"  >Select</option>';

							for (var i = 0; i < len; i++) {
								var PNo=data[i].poNo+"-"+ data[i].poDate 

								html += '<option value="' + data[i].poId + '">'
										+PNo+ '</option>';

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
							var html;
							var len = data.length;
							//alert("data " +JSON.stringify(data));
												var html = '<option selected value="-1"  >Select All</option>';

							for (var i = 0; i < len; i++) {
								var projData=data[i].projName+"-"+data[i].address

								html += '<option value="' + data[i].projId + '">'
										+projData+ '</option>';

							}
							html += '</option>';
							$('#proj_id').html(html);
							$("#proj_id").trigger("chosen:updated");
						});
				
				
				
				
				
			}// end of if valid= true
			
		}
	</script>

	<script type="text/javascript">
	// on poId c change function 
		function getPoDetailItem() {
			var poId=document.getElementById("po_id").value;
			
		
			var valid=true;
			
			if(poId==-1 || poId=="" || poId==null){
				valid=false;
			}
			if(valid==true){
			
			
			
				$
						.getJSON(
								'${getPoDetailForOrderByPoId}',
								{
									poId : poId,
									ajax : 'true',
								},

								function(data) {
									
									var dataTable = $('#bootstrap-data-table1')
									.DataTable();
							dataTable.clear().draw();

							$
									.each(
											data,
											function(i, v) {
												//alert("hdjfh");
var checkB = '<input  type="checkbox" name="selOrdItem" id='+v.itemId+' class="check"  value='+v.itemId+'/>'
var ordQty = '<input  type="text"  value="0" class="form-control" onkeypress="return allowOnlyNumber(event);" id="ordQty'+v.itemId+'" name="ordQty'+v.itemId+'" oninput="placeTempOrder('+v.itemId+','+v.total+','+v.poDetailId+','+v.poRemainingQty+')"/>'

var itemTotal = '<input  type="text" readonly  class="form-control"  id="itemTotal'+v.itemId+'" name='+v.itemId+'/>'
										/* var str = '<a href="#" class="action_btn" onclick="callDelete('
														+ v.itemId
														+ ','
														+ i
														+ ')"><i class="fa fa-trash"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callEdit('
														+ v.itemId
														+ ','
														+ i
														+ ')"><i class="fa fa-edit"></i></a>'
 */
												dataTable.row
														.add(
																[
																		i + 1,
																		v.itemName,
																		v.itemCode,
																		v.poQty,
																		v.poRemainingQty,
																		v.total,
																		ordQty,
																		itemTotal
																		 ])
														.draw();
											});
						
								});	
			}	
		}
	
	
	</script>

	<script type="text/javascript">
	function placeTempOrder(itemId,total,poDetailId,poRemainingQty){
		//alert("Hi");
		
		var valid=true;
		var qty=document.getElementById("ordQty"+itemId).value;
		if(qty<0 || qty=="" || qty==null ){
			valid=false;
			alert("Please enter valid quantity");
			document.getElementById("itemTotal"+itemId).value="0";
			document.getElementById("ordQty"+itemId).value="0";
			  document.getElementById("submitButton").disabled=true;


		}
		else if(qty>poRemainingQty){
			valid=false;
			alert("Order quantity can not be greater than Po Remaining quantity");
			document.getElementById("itemTotal"+itemId).value="0";
			document.getElementById("ordQty"+itemId).value="0";
			  document.getElementById("submitButton").disabled=true;


		}
		
		else if(qty<=0){
			valid=false;
		}
		if(valid==true){
			
			var itemTotal=parseFloat(qty)*parseFloat(total);
			document.getElementById("itemTotal"+itemId).value=itemTotal;
		$.getJSON('${getTempOrderHeader}', {
			
			
			qty : qty,
			itemTotal : itemTotal,
			poDetailId : poDetailId,
			itemId : itemId,
			poRemainingQty : poRemainingQty,
			poRate : poRate,
			orderDetId : 0,
			ajax : 'true',
		},

		function(data) {
			  document.getElementById("submitButton").disabled=false;

			var len = data.length;
			//alert("orderTotal " +data.orderTotal);
			var tot=0;
			for (var i = 0; i < len; i++) {
				//alert("data[]" +data[i].total)
			tot=data[i].total+tot;
				//alert("Json " +JSON.stringify(data.ordDetailList[i]));

			}
			//alert("total " +tot);
			tot=tot.toFixed(2);
			document.getElementById("ordTotal").innerHTML=tot;
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
	
	</script>

	<!-- <script type="text/javascript">
		function addItem() {
			//alert("in add Item ");
			var itemId = document.getElementById("item_name").value;
			var itemName = $("#item_name option:selected").html();
			var uomId = document.getElementById("uomId").value;
			var uomName = $("#uomId option:selected").html();
			var qty = document.getElementById("qty").value;
			var isEdit = document.getElementById("isEdit").value;
			var itemRemark = document.getElementById("item_remark").value;
			var itemUomId = document.getElementById("itemUomId").value;
			var x = false;
			var y = false;
			x = isNaN(qty);

			var plantId = document.getElementById("plant_id").value;
			var valid = false;

			if (plantId == null || plantId == "") {
				valid = true;
				var msg = "Please Select plant";
				callAlert(msg);
			}

			else if (itemId == "" || itemId < 0) {
				valid = true;
				var msg = "Please Select Item Name";
				callAlert(msg);
			} else if ((x == true) || (qty == null) || (qty == "") || (qty < 0)) {
				var msg = "Please Enter Valid Quantity";
				valid = true;
				callAlert(msg);
			}

			//alert("x=" +x + "y= " +y);
			if (valid == false) {
				alert("Inside add ajax");
				$
						.getJSON(
								'${addEnqItem}',
								{
									isEdit : isEdit,
									key : -1,
									itemId : itemId,
									itemName : itemName,
									uomId : uomId,
									uomName : uomName,
									qty : qty,
									itemRemark : itemRemark,
									itemUomId : itemUomId,
									ajax : 'true',

								},

								function(data) {
									var dataTable = $('#bootstrap-data-table1')
											.DataTable();
									dataTable.clear().draw();

									$
											.each(
													data,
													function(i, v) {
														if (v.isDuplicate == 1) {
															alert("Item Already Added in Enquiry");
														}
														//var str = '<input  type="button"  class="fa  fa-stack-exchange" onclick="callEdit('+v.itemId+','+i+')" style="width:100%;"/>&nbsp<input  type="button" value="callDelete" onclick="callDelete('+v.itemId+','+i+')" style="width:100%;"/> ';

														var str = '<a href="#" class="action_btn" onclick="callDelete('
																+ v.itemId
																+ ','
																+ i
																+ ')"><i class="fa fa-trash"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callEdit('
																+ v.itemId
																+ ','
																+ i
																+ ')"><i class="fa fa-edit"></i></a>'

														dataTable.row
																.add(
																		[
																				i + 1,
																				v.itemName,
																				v.uomName,
																				v.enqQty,
																				str ])
																.draw();
													});
									document.getElementById("qty").value = "";
									document.getElementById("item_remark").value = "";
									document.getElementById("item_name").options.selectedIndex = "0";
									document.getElementById("uomId").options.selectedIndex = "0";
									$("#uomId").trigger("chosen:updated");
									$("#item_name").trigger("chosen:updated");
									document.getElementById("isEdit").value = 0;
									document.getElementById("itemUomId").value = "0";
									document.getElementById("item_rate").value = "0"
								});

			}//end of if
			else {

			}
		}

		function callEdit(itemId, index) {
			$
					.getJSON(
							'${getItemForEdit}',
							{
								itemId : itemId,
								index : index,
								ajax : 'true',

							},
							function(data) {

								document.getElementById("uomId").value = data.uomId;
								$("#uomId").trigger("chosen:updated");
								document.getElementById("qty").value = data.enqQty;
								document.getElementById("item_remark").value = data.itemEnqRemark;
								document.getElementById("item_name").value = data.itemId;
								$("#item_name").trigger("chosen:updated");
								document.getElementById("isEdit").value = 1;
								document.getElementById("itemUomId").value = data.itemUomId;
								document.getElementById("item_rate").value = data.itemRate1;
							});

		}

		function callDelete(itemId, index) {
			document.getElementById("isEdit").value = 0;

			$
					.getJSON(
							'${addEnqItem}',
							{
								isEdit : 0,
								key : index,
								ajax : 'true',

							},

							function(data) {
								var dataTable = $('#bootstrap-data-table1')
										.DataTable();
								dataTable.clear().draw();
								$
										.each(
												data,
												function(i, v) {
													//	var str = '<input  type="button" value="callEdit" onclick="callEdit('+v.itemId+','+i+')" style="width:30%;"/>&nbsp<input  type="button" value="callDelete" onclick="callDelete('+v.itemId+','+i+')" style="width:30%;"/> ';
													var str = '<a href="#" class="action_btn" onclick="callDelete('
															+ v.itemId
															+ ','
															+ i
															+ ')"><abbr title="Delete"><i class="fa fa-trash"></i></abbr></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callEdit('
															+ v.itemId
															+ ','
															+ i
															+ ')"><abbr title="Edit"><i class="fa fa-edit"></i></abbr></a>'

													dataTable.row.add(
															[ i + 1,
																	v.itemName,
																	v.uomName,
																	v.enqQty,
																	str ])
															.draw();
												});
							});

			document.getElementById("qty").value = "";
			document.getElementById("item_remark").value = "";
			document.getElementById("item_name").options.selectedIndex = "0";
			document.getElementById("uomId").options.selectedIndex = "0";
			$("#uomId").trigger("chosen:updated");
			$("#item_name").trigger("chosen:updated");
			document.getElementById("item_rate").value = "0";
		}
		function validate(s) {
			var rgx = /^[0-9]*\.?[0-9]*$/;
			return s.match(rgx);
		}
		function callAlert(msg) {
			alert(msg);
		}
	</script> -->

	<!-- <script type="text/javascript">
		$(document).ready(function() {
			var dataTable = $('#bootstrap-data-table1').DataTable();
				columnDefs : [ {
					targets : [ 1,2],
					className : "right"
				}, ]
			
		});
	</script> -->


	<script type="text/javascript">
		$(document).ready(function() {
			$('#bootstrap-data-table1').DataTable({
				columnDefs : [ {
					targets : [ 3,4,5,6,7 ],
					className : "right"
				}, ]

			});

		);}
	</script>
</body>
</html>