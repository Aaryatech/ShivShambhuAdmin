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

<c:url var="addItemInEditBill" value="/addItemInEditBill" />

<c:url var="getPoDetailForOrderByPoId"	value="/getPoDetailForOrderByPoId" />


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


</head>
<body>


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
							<form action="${pageContext.request.contextPath}/updateBill"
								method="post">

								<div class="row">

									<div class="col-md-2">Plant</div>

									<div class="col-md-4">

										<input type="text" readonly value="${editBill.plantName}"
											style="width: 100%;" class="form-control"> <span
											class="error" aria-live="polite"></span>
										<%-- <select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select plant name')"
											onchange="getData()">
											<option value="">Select Plant</option>

											<c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select> --%>
									</div>
									<div class="col-md-2">Customer</div>
									<div class="col-md-4">

										<input type="text" readonly value="${editBill.custName}"
											style="width: 100%;" class="form-control"> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>
								<div class="form-group"></div>


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

								<input type="hidden" name="item_id" id="item_id" value="0">

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Bill Date</div>
									<div class="col-md-4">
										<input type="text" id="ord_date" name="ord_date" required
											style="width: 100%;" class="form-control"
											value="${editBill.billDate}"> <span class="error"
											aria-live="polite"></span>
									</div>
									<div class="col-md-2">Bill No</div>
									<div class="col-md-4">
										<input type="text" readonly id="ord_no" name="ord_no"
											style="width: 100%;" class="form-control"
											value="${editBill.billNo}"> <span class="error"
											aria-live="polite"></span>
									</div>

								</div>
								<div class="form-group"></div>

								<input type="hidden" id="billHeadId" name="billHeadId"
									value="${editBill.billHeadId}">
								<div class="row">

									<div class="col-md-2">Select Project</div>

									<div class="col-md-10">
										<select id="proj_id" name="proj_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select project')"
											onchange="try{setCustomValidity('')}catch(e){}">

											<c:forEach items="${projList}" var="proj">

												<c:choose>
													<c:when test="${editBill.projId==proj.projId}">
														<option selected value="${proj.projId}">${proj.projName}-${proj.address}</option>
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
								<div class="row">
									<div class="col-md-2">Challans</div>
									<div class="col-md-4">
										<select id="chalan_id" name="chalan_id" class="standardSelect"
											tabindex="1" multiple="multiple"
											oninvalid="setCustomValidity('Please select Challan')">
											<c:forEach items="${chalanHeadList}" var="chalanHead">

												<option value="${chalanHead.chalanId}" selected>${chalanHead.chalanNo}</option>
											</c:forEach>

										</select>
									</div>
								</div>


								<div class="form-group"></div>
									<div class="row">

									<div class="col-md-2">Item</div>

									<div class="col-md-3">
										<select id="itemId" name="itemId" class="standardSelect"
											tabindex="1" 
											oninvalid="setCustomValidity('Please select item')"
											onchange="try{setCustomValidity('')}catch(e){}">
														<option  value="" selected>Select Item</option>

											<c:forEach items="${itemList}" var="item">
														<option value="${item.itemId}">${item.itemName}</option>
											</c:forEach>

										</select>
									</div>
										<div class="col-md-1">Qty</div>
										<div class="col-md-1"><input type="text" style="text-align: center;" name="qty" id="qty" value="0" class="form-control"/></div>
										<div class="col-md-1">Rate</div>
										<div class="col-md-1"><input type="text" style="text-align: center;"  name="rate" id="rate" value="0" class="form-control"/></div>
										<div class="col-md-1"><input type="button" class="btn btn-primary" value="Add Item" onclick="addItem()"></div>
								</div>
								<%--	<div class="row">

									<div class="col-md-2">Select PO</div>

									<div class="col-md-10">
										<select id="po_id" name="po_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select po ')"
											onchange="getPoDetailItem()">
											<option value="-1">Select PO</option>

										</select>
									</div>
								</div>--%>

								<%-- <input type="checkbox" value="${item.itemId}" name="selectItem"> --%>

								<div class="card-body card-block">
									<table id="bootstrap-data-table"
										class="table table-striped table-bordered" >
										<thead>
											<tr>
												<th style="text-align: center">Sr</th>
												<th style="text-align: center">Item Name</th>
												<!-- 	<th style="text-align: center">UOM</th> -->
												<th style="text-align: center">Rate</th>
												<th style="text-align: center">Qty</th>
												<th style="text-align: center">Taxable Amt</th>
												<th style="text-align: center">Disc %</th>
												<th style="text-align: center">Disc Amt</th>
												<th style="text-align: center">Tax %</th>
												<th style="text-align: center">Tax Amt</th>
												<th style="text-align: center">Total</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${billDetailList}" var="billDetail"
												varStatus="count">
												<tr>
													<input type="hidden" class="form-control"
														value="${billDetail.rate}"
														id="orderRate${count.index}${billDetail.itemId}"
														name="orderRate${count.index}${billDetail.itemId}"
														oninput="calculation(${count.index},${billDetail.itemId})" />
													<input type="hidden" class="form-control"
														value="${billDetail.exInt1}"
														id="isTaxIncluding${count.index}${billDetail.itemId}"
														name="isTaxIncluding${count.index}${billDetail.itemId}"
														oninput="calculation(${count.index},${billDetail.itemId})" />
													<input type="hidden" class="form-control"
														value="${billDetail.cgstPer+billDetail.sgstPer}"
														id="taxPer${count.index}${billDetail.itemId}"
														name="taxPer${count.index}${billDetail.itemId}"
														oninput="calculation(${count.index},${billDetail.itemId})" />

													<td style="text-align: center">${count.index+1}</td>


													<td style="text-align: left"><c:out
															value="${billDetail.itemName}" /></td>
													<%-- <td style="text-align: center"><c:out
													value="${billDetail.itemUom}" /></td>
 --%>
													<%-- 	<td style="text-align: left"><c:out
															value="${billDetail.rate}" /></td> --%>

													<td style="text-align: center"><input type="text"
														class="form-control" value="${billDetail.rate}"
														id="billRate${count.index}${billDetail.itemId}"
														name="billRate${count.index}${billDetail.itemId}"
														oninput="calculation(${count.index},${billDetail.itemId})" /></td>
													<td style="text-align: center"><input type="text"
														class="form-control" value="${billDetail.qty}"
														id="chalanQty${count.index}${billDetail.itemId}"
														name="chalanQty${count.index}${billDetail.itemId}"
														oninput="calculation(${count.index},${billDetail.itemId})" /></td>
													<td style="text-align: center"><p
															id="taxableAmt${count.index}${billDetail.itemId}">
															<c:out value="${billDetail.taxableAmt}" />
														</p></td>


													<td style="text-align: center"><input type="text"
														class="form-control" value="${billDetail.discPer}"
														id="discPer${count.index}${billDetail.itemId}"
														name="discPer${count.index}${billDetail.itemId}"
														oninput="calculation(${count.index},${billDetail.itemId})" />
													</td>
													<td style="text-align: center"><p
															id="discAmt${count.index}${billDetail.itemId}">
															<c:out value="${billDetail.discAmt}" />
														</p></td>
													<td style="text-align: center"><c:out
															value="${billDetail.cgstPer+billDetail.sgstPer}" /></td>
													<td style="text-align: center"><p
															id="taxAmt${count.index}${billDetail.itemId}">
															<c:out value="${billDetail.taxAmt}" />
														</p></td>
													<td style="text-align: center"><p
															id="total${count.index}${billDetail.itemId}">
															<c:out value="${billDetail.totalAmt}" />
														</p></td>

												</tr>
											</c:forEach>
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
								<div class="row" >

									<!-- 	<div class="col-md-2">Other Cost After Tax</div>

									<div class="col-md-3">845</div> -->
									<%-- <div class="col-md-2">Bill Total</div>

									<div class="col-md-3" id="ordTotal">${editBill.totalAmt}</div> --%>

									<div class="col-md-6" style="text-align: right;">
										<input type="submit" class="btn btn-primary" value="Submit">

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
	function calTotal(itemId,poRate,poDetailId,poRemainingQty,orderDetId){
		//alert("Hi");
		
		var valid=true;
		var qty=document.getElementById("ordQty"+itemId).value;
		if(qty<0 || qty=="" || qty==null || qty==0){
			valid=false;
			alert("Please enter valid quantity");
			document.getElementById("itemTotal"+itemId).value="0";

		}
		else if(qty>poRemainingQty){
			valid=false;
			alert("Order quantity can not be greater than Po Remaining quantity");
			document.getElementById("itemTotal"+itemId).value="0";

		}
		if(valid==true){
			var itemTotal=parseFloat(qty)*parseFloat(poRate);
			document.getElementById("itemTotal"+itemId).value=itemTotal;
		$.getJSON('${getTempOrderHeader}', {
			
			
			qty : qty,
			itemTotal : itemTotal,
			poDetailId : poDetailId,
			itemId : itemId,
			poRemainingQty : poRemainingQty,
			poRate : poRate,
			orderDetId :orderDetId,
			
			ajax : 'true',
		},

		function(data) {
			
			var len = data.length;
			//alert("orderTotal " +data.orderTotal);
			var tot=0;
			for (var i = 0; i < len; i++) {
				
			tot=data[i].total+tot;

			}
			//alert("total " +tot);
			document.getElementById("ordTotal").innerHTML=tot;
		});
		}
	}
	
	</script>

	<script type="text/javascript">
	
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
					var html = '<option selected value="-1"  >Select Customer</option>';

					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].custId + '">'
								+ data[i].custName + '</option>';

					}
					html += '</option>';

					$('#cust_name').html(html);
					$("#cust_name").trigger("chosen:updated");
					getCustInfo();

					$('#po_id').html("-1");
					$("#po_id").trigger("chosen:updated");
					
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
				
				$('#po_id').html("-1");
				$("#po_id").trigger("chosen:updated");
				
				var dataTable = $('#bootstrap-data-table')
				.DataTable();
		dataTable.clear().draw();

			}
			else if(custId<0){
				valid = false;
				
				$('#po_id').html("-1");
				$("#po_id").trigger("chosen:updated");
				
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
						'${getPOHeaderByCustId}',
						{
							custId : custId,
							ajax : 'true',
						},
						function(data) {
							var html;
							var len = data.length;
							//alert("data " +JSON.stringify(data));
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
	function addItem() {
		//alert("in add Item ");
		var itemId=$('#itemId').val();

		var qty = document.getElementById("qty").value;
		var rate = document.getElementById("rate").value;
		//var discPer = document.getElementById("discPerc").value;
		var	billHeadId= document.getElementById("billHeadId").value;
		var x = false;
		x = isNaN(qty);
		
		var valid = false;
       if (itemId == "" || itemId < 0) {
			valid = true;
			var msg = "Please Select Item";
			callAlert(msg);
		} else if ((x == true) || (qty == null) || (qty == "") || (qty < 0)) {
			var msg = "Please Enter Valid Quantity";
			valid = true;
			callAlert(msg);
		}

		if (valid == false) {
			$
					.getJSON(
							'${addItemInEditBill}',
							{
								itemId : itemId,
								billHeadId:billHeadId,
								qty : qty,
								rate:rate,
								ajax : 'true',

							},

							function(data) {
								var dataTable = $('#bootstrap-data-table')
										.DataTable();
								dataTable.clear().draw();

								$
										.each(
												data,
												function(i, v) {
												
													/* var str = '<a href="#" class="action_btn" onclick="callDelete('
															+ v.itemId
															+ ','
															+ i
															+ ')"><i class="fa fa-trash"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callEdit('
															+ v.itemId
															+ ','
															+ i
															+ ')"><i class="fa fa-edit"></i></a>' */
               var billRate='<input type="text" class="form-control" value="'+v.rate+'" id="billRate'+i+''+v.itemId+'" name="billRate'+i+''+v.itemId+'"'
             										+'	oninput="calculation('+i+','+v.itemId+')" />'
               var chalanQty='<input type="text" class="form-control" value="'+v.qty+'" id="chalanQty'+i+''+v.itemId+'"	name="chalanQty'+i+''+v.itemId+'"'
						+'oninput="calculation('+i+','+v.itemId+')" />'
			   var taxableAmt='<p id="taxableAmt'+i+''+v.itemId+'">'+v.taxableAmt+'</p>'					
			   var discPer='<input type="text"	class="form-control" value="'+v.discPer+'"	id="discPer'+i+''+v.itemId+'"	name="discPer'+i+''+v.itemId+'"'
						+'oninput="calculation('+i+','+v.itemId+')" />	'	
				var discAmt='<p id="discAmt'+i+''+v.itemId+'">'+v.discAmt+'</p>'
				var sgstPer=v.cgstPer+v.sgstPer	
				var taxAmt='<p	id="taxAmt'+i+''+v.itemId+'">'+v.taxAmt+'</p>'
				var totalAmt='<p	id="total'+i+''+v.itemId+'">'+v.totalAmt+'</p>'
						
				var hidden='<input type="hidden" class="form-control"	value="'+v.rate+'"	id="orderRate'+i+''+v.itemId+'" name="orderRate'+i+''+v.itemId+'"	oninput="calculation('+i+','+v.itemId+')" />'
				+'<input type="hidden" class="form-control"	value="'+v.exInt1+'"	id="isTaxIncluding'+i+''+v.itemId+'"	name="isTaxIncluding'+i+''+v.itemId+'"	oninput="calculation('+i+','+v.itemId+')" />'
				+'<input type="hidden" class="form-control"	value="'+(v.cgstPer+v.sgstPer)+'"	id="taxPer'+i+''+v.itemId+'"	name="taxPer'+i+''+v.itemId+'"	oninput="calculation('+i+','+v.itemId+')" />'
													dataTable.row
															.add(
																	[
																			i + 1,
																			v.itemName,
																			billRate,
																			chalanQty,
																			taxableAmt,
																			discPer,
																			discAmt,
																			sgstPer,
																			taxAmt,
																			totalAmt+""+hidden
																			 ])
															.draw();
												});
								document.getElementById("qty").value = "0";
								document.getElementById("rate").value = "0";
								document.getElementById("itemId").options.selectedIndex = "";
								$("#itemId").trigger("chosen:updated");
							
							});

		}//end of if
		
	}
   
   </script>
	<!-- <script type="text/javascript">
	// on poId c change function 
		function getPoDetailItem() {
			var poId=document.getElementById("po_id").value;
			//alert("Po Id " +poId);

			
				$
						.getJSON(
								'${getPoDetailForOrderByPoId}',
								{
									poId : poId,
									ajax : 'true',
								},

								function(data) {
									
									var dataTable = $('#bootstrap-data-table')
									.DataTable();
							dataTable.clear().draw();

							$
									.each(
											data,
											function(i, v) {
												//alert("hdjfh");
var checkB = '<input  type="checkbox" name="selOrdItem" id='+v.itemId+' class="check"  value='+v.itemId+'/>'
var ordQty = '<input  type="text"  class="form-control"  id="ordQty'+v.itemId+'" name="ordQty'+v.itemId+'" onchange="calTotal('+v.itemId+','+v.poRate+','+v.poDetailId+','+v.poRemainingQty+')"/>'

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
																		v.poRate,
																		ordQty,
																		itemTotal
																		 ])
														.draw();
											});
						
								});	
						
		}
	
	
	</script> -->



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
									var dataTable = $('#bootstrap-data-table')
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
								var dataTable = $('#bootstrap-data-table')
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
			var dataTable = $('#bootstrap-data-table').DataTable();
				columnDefs : [ {
					targets : [ 1,2],
					className : "right"
				}, ]
			
		});
	</script> -->
	<script type="text/javascript">

function calculation(key,itemId){
	var orderRate=parseFloat(document.getElementById("orderRate"+key+''+itemId).value); //alert(orderRate+"orderRate");
	var chalanQty=parseFloat(document.getElementById("chalanQty"+key+''+itemId).value); //alert(chalanQty+"chalanQty");
	var discPer=parseFloat(document.getElementById("discPer"+key+''+itemId).value); //alert(discPer+"discPer");
	var taxPer=parseFloat(document.getElementById("taxPer"+key+''+itemId).value); //alert(taxPer+"taxPer");
    var isTaxIncluding=document.getElementById("isTaxIncluding"+key+''+itemId).value; //alert(isTaxIncluding+"isTaxIncluding");
    var billRate=document.getElementById("billRate"+key+''+itemId).value; //alert(billRate+"billRate");
    
   /*  if(isTaxIncluding==0)
    	{ */
    	  var taxableAmt=(billRate*chalanQty);
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
     	  
     	 

    	/*} else
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
         	 

    		} */
}
	
	</script>
</body>
</html>