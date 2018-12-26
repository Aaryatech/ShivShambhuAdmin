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
	width: 50%;
	height: auto;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: left;
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
	width: 50%;
	height: auto;
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

<c:url var="getProjectByCustId" value="/getProjectByCustId" />

<c:url var="getItemsAndEnqItemList" value="/getItemsAndEnqItemList" />

<c:url var="getDocTermDetail" value="/getDocTermDetail" />
<c:url var="getNewItemsForQuotation" value="/getNewItemsForQuotation" />


<meta name="description" content="Sufee Admin - HTML5 Admin Template">
<meta name="viewport" content="width=device-width, initial-scale=1">

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

</head>
<body onload="callsetKM()">


	<!-- Left Panel -->
	<jsp:include page="/WEB-INF/views/common/left.jsp"></jsp:include>
	<!-- Left Panel -->

	<jsp:include page="/WEB-INF/views/common/right.jsp"></jsp:include>

	<!-- Header-->
	<!-- Header-->



	<div class="content mt-3">
		<div class="animated fadeIn">

			<div class="row">

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<div class="col-md-2"></div>
							<div class="col-md-3">
								Quotation No : <strong>${quotHeader.quotNo}</strong>
							</div>
							<div class="col-md-1"></div>
							<div class="col-md-2">
								Date : <strong>${quotHeader.quotDate}</strong>
							</div>
						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/updateQuotation"
								method="post" id="updateQuotation">

								<div class="row">

									<div class="col-md-2">Plant</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select plant name')">
											<c:forEach items="${plantList}" var="plant">
												<c:if test="${plantId==plant.plantId}">
													<option selected value="${plant.plantId}">${plant.plantName}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2">Customer</div>
									<div class="col-md-4">
										<select id="cust_name" name="cust_name" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')"
											onchange="try{setCustomValidity('')}catch(e){}">

											<c:forEach items="${custList}" var="cust">
												<c:choose>
													<c:when test="${custId==cust.custId}">

														<option selected value="${cust.custId}">${cust.custName}</option>
													</c:when>
													<c:otherwise>
														<option value="${cust.custId}">${cust.custName}</option>

													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>

								</div>
								<input type="hidden" name="item_id" id="item_id" value="0">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Select Project</div>
									<div class="col-md-4">
										<select id="proj_id" name="proj_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select project')"
											onchange="try{setCustomValidity('')}catch(e){}">

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
									<div class="col-md-2">Select Payment Term</div>

									<div class="col-md-4">
										<select id="pay_term_id" name="pay_term_id"
											class="standardSelect" tabindex="1" required
											oninvalid="setCustomValidity('Please select payment term')"
											onchange="setData()">
											<option value="-1">Select</option>

											<c:forEach items="${payTermList}" var="pTerm">
												<c:choose>

													<c:when test="${quotHeader.payTermId==pTerm.payTermId}">
														<option selected value="${pTerm.payTermId}">${pTerm.payTerm}</option>
													</c:when>

													<c:otherwise>
														<option value="${pTerm.payTermId}">${pTerm.payTerm}</option>
													</c:otherwise>

												</c:choose>
											</c:forEach>
										</select>
									</div>


								</div>
								<input type="hidden" id="quotHeadId" name="quotHeadId"
									value="${quotHeader.quotHeadId}"> <input type="hidden"
									id="pay_term_name" name="pay_term_name"
									value="${quotHeader.payTerms}">


								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Select</div>

									<div class="col-md-4">
										<select id="quot_doc_term_id" name="quot_doc_term_id"
											class="standardSelect" tabindex="1"
											onchange="showDocDetailPopup()">
											<option value="-1">Select</option>

											<c:forEach items="${docTermList}" var="qTerm">
												<c:choose>
													<c:when test="${quotHeader.quotTermId==qTerm.termId}">
														<option selected value="${qTerm.termId}">${qTerm.termTitle}</option>
													</c:when>
													<c:otherwise>
														<option value="${qTerm.termId}">${qTerm.termTitle}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>


									<div class="col-md-2">Enter Transport Terms</div>

									<div class="col-md-4">
										<textarea id="trans_term" name="trans_term"
											class="form-control" style="width: 100%;"
											oninvalid="setCustomValidity('Please enter transport term')"
											onchange="try{setCustomValidity('')}catch(e){}">${quotHeader.transportTerms}</textarea>
									</div>

								</div>


								<div id="myModal" class="modal">

									<div class="modal-content" style="color: black;">
										<span class="close" id="close">&times;</span>
										<h5 style="text-align: left;">Quotation Terms And
											Conditions</h5>
										<div class=" box-content">

											<div
												style="overflow: scroll; height: 50%; width: 50%; overflow: auto">
												<table style="width: 100%" id="table_grid1">
													<thead>
														<tr>
															<th class="col-md-10">Terms And Condition</th>
														</tr>
													</thead>
													<tbody>

													</tbody>
												</table>
											</div>


										</div>
										<br>

									</div>

								</div>
								<!-- end of myModal div -->

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Quotation Date</div>
									<div class="col-md-4">
										<input type="text" id="quot_date" name="quot_date" readonly
											autocomplete="off" class="form-control" style="width: 100%;"
											value="${quotHeader.quotDate}">
									</div>

									<div class="col-md-2">Delivery Place</div>

									<c:choose>
										<c:when test="${quotHeader.noOfKm==0}">
											<div class="col-md-2">
												On Spot <input type="radio" name="del_place" checked
													onchange="setKM(1)" id="del_place" value="1">
											</div>

											<div class="col-md-2">
												Specific Place <input type="radio" name="del_place"
													id="del_place" onchange="setKM(0)" value="0">
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-md-2">
												On Spot <input type="radio" name="del_place" id="del_place"
													onchange="setKM(1)" value="1">
											</div>

											<div class="col-md-2">
												Specific Place <input type="radio" name="del_place"
													id="del_place" checked onchange="setKM(0)" value="0"
													checked>
											</div>

										</c:otherwise>
									</c:choose>


								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">No of KM</div>
									<div class="col-md-4">
										<input type="text" id="no_of_km" name="no_of_km"
											onkeypress="return allowOnlyNumber(event);"
											oninput="calcAll()" class="form-control"
											value="${quotHeader.noOfKm}" style="width: 100%;">
									</div>
									<div class="col-md-2">Toll Amount</div>
									<div class="col-md-4">
										<input type="text" id="toll_amt" name="toll_amt"
											value="${quotHeader.tollCost}" oninput="calcAll()"
											class="form-control"
											onkeypress="return allowOnlyNumber(event);">
									</div>
								</div>

								<div class="form-group"></div>
								<div class="row">

									<!-- <div class="col-md-2">Calculate By</div>
									<div class="col-md-2">
										Automatic<input type="radio" checked name="calc_by"
											id="calc_by" value="1">
									</div>

									<div class="col-md-2">
										Manual<input type="radio" name="calc_by" id="calc_by"
											value="0">
									</div> -->

									<div class="col-md-2">Other Cost</div>
									<div class="col-md-4">
										<input type="text" oninput="calcAll()"
											onkeypress="return allowOnlyNumber(event);" min="0"
											id="other_cost" value="${quotHeader.otherCost}"
											name="other_cost" class="form-control" style="width: 100%;">
									</div>

									<div class="col-md-2">No of Tolls</div>
									<div class="col-md-4">
										<input type="text" id="no_of_tolls" name="no_of_tolls"
											value="${quotHeader.noOfTolls}" class="form-control"
											style="width: 100%;"
											onkeypress="return allowOnlyNumber1(event);">
									</div>

								</div>

								<div class="form-group"></div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Is Tax Included</div>

									<c:choose>
										<c:when test="${quotHeader.taxValue>0}">
											<div class="col-md-1">
												No<input type="radio" checked name="is_tax_inc" id="is_tax_inc"
													value="1" onchange="calcAll()">
											</div>

											<div class="col-md-1">
												Yes<input  type="radio" name="is_tax_inc"
													id="is_tax_inc" value="0"
													onchange="calcAll()">
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-md-1">
												No<input type="radio"   name="is_tax_inc"
													id="is_tax_inc" value="1" 
													onchange="calcAll()">
											</div>

											<div class="col-md-1">
												Yes<input type="radio" name="is_tax_inc" id="is_tax_inc"
													value="0" checked onchange="calcAll()">
											</div>
										</c:otherwise>
									</c:choose>
									<div class="col-md-2">
										<input type="button" id="newItemAdd" name="newItemAdd"
											class="btn btn-primary" style="width: 100%;"
											value="Add Items" onclick="getNewItems(0)">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row" id="newItemAddDiv" style="display: none;">


									<div class="col-md-2">Select Item</div>
									<div class="col-md-4">
										<select id="new_item_id" name="new_item_id"
											style="width: 100%;" class="standardSelect" tabindex="1"
											required
											oninvalid="setCustomValidity('Please select item name')">
											<c:forEach items="${newItemList}" var="newItem">
												<option value="${newItem.itemId}">${newItem.itemName}</option>
											</c:forEach>
										</select>
									</div>

									<div class="col-md-1">Quantity</div>
									<div class="col-md-1">
										<input type="text" id="new_item_qty" name="new_item_qty"
											value="1" class="form-control" style="width: 100%;" min="1"
											onkeypress="return allowOnlyNumber(event);">


									</div>
									<div class="col-md-2">
										<input type="button" id="newItemAdd" name="newItemAdd"
											class="btn btn-primary" style="width: 100%;"
											value="Submit Item" onclick="getNewItems(1)">
									</div>
									<div class="col-md-2"></div>

								</div>

								<div class="card-body card-block"
									style="overflow: auto; width: 100%;">

									<table id="bootstrap-data-table"
										class="table table-striped table-bordered"
										style="table-layout: none; width: 80%;">

										<thead>

											<tr>

												<th style="text-align: center" class="col-md-1">Sr</th>

												<th>Item name</th>
												<th style="text-align: center" class="col-md-1">Quantity</th>
												<th style="text-align: center" class="col-md-1">UOM</th>

												<th style="text-align: center" class="col-md-2">Trans
													cost</th>
												<th style="text-align: center" class="col-md-2">Toll
													Cost</th>
												<th style="text-align: center" class="col-md-2">Other
													Cost</th>

												<th style="text-align: center" class="col-md-2">Item
													Rate</th>
												<th style="text-align: center" class="col-md-2">Roy
													Rate</th>
												<th style="text-align: center" class="col-md-1">GST</th>

												<th style="text-align: center" class="col-md-2">Taxable</th>
												<th style="text-align: center" class="col-md-2">Tax
													value</th>
												<th style="text-align: center" class="col-md-2">Cost
													After Tax</th>

												<th style="text-align: center" class="col-md-2">Final</th>
												<th style="text-align: center" class="col-md-2">Action</th>


											</tr>
										</thead>
										<tbody>

											<c:forEach items="${itemList}" var="item" varStatus="count">

												<tr>


													<td class="col-md-1" style="text-align: center">${count.index+1}</td>


													<td class="col-md-2" style="text-align: left"><c:out
															value="${item.itemName}" /></td>

													<c:if test="${quotHeader.status==0}">
														<c:set var="qty" value="${item.enqQty}"></c:set>
													</c:if>

													<c:if test="${quotHeader.status > 0}">
														<c:set var="qty" value="${item.quotQty}"></c:set>
													</c:if>

													<td class="col-md-1" style="text-align: left"><input
														type="text" onkeypress="return allowOnlyNumber(event);"
														id="quot_qty${item.itemId}" name="quot_qty${item.itemId}"
														value="${qty}" class="form-control"></td>


													<c:if test="${item.enqQty==0}">
														<c:set var="uomName" value="${item.uomName}"></c:set>
													</c:if>

													<c:if test="${item.enqQty > 0}">
														<c:set var="uomName" value="${item.enqUomName}"></c:set>
													</c:if>
													<td class="col-md-1" style="text-align: left"><c:out
															value="${uomName}" /></td>


													<td class="col-md-2" style="text-align: center"><input
														type="text" id="trans_cost${item.itemId}"
														value="${item.transCost}"
														onkeypress="return allowOnlyNumber(event);"
														onchange="itemCalc(${item.itemId},${item.freightRate},${item.itemRate1},${item.royaltyRate},${item.totalTaxPer})"
														name="trans_cost${item.itemId}" class="form-control"></td>

													<td class="col-md-2" style="text-align: center"><input
														type="text" id="toll_cost${item.itemId}" readonly
														value="${quotHeader.tollCost}"
														name="toll_cost${item.itemId}" class="form-control"></td>

													<td class="col-md-2" style="text-align: center"><input
														type="text" min="0"
														onkeypress="return allowOnlyNumber(event);"
														onchange="itemCalc(${item.itemId},${item.freightRate},${item.itemRate1},${item.royaltyRate},${item.totalTaxPer})"
														id="other_cost${item.itemId}" value="${item.otherCost}"
														name="other_cost${item.itemId}" class="form-control"></td>
													<!-- 
													<td style="text-align: center"><input type="text"
														id="trans_cost" name="trans_cost" class="form-control"
														style="width: 100%;"></td>
 -->


													<td class="col-md-2" style="text-align: left"><c:out
															value="${item.itemRate1}" /></td>

													<td class="col-md-2" style="text-align: left"><c:out
															value="${item.royaltyRate}" /></td>

													<td class="col-md-2" style="text-align: left"><c:out
															value="${item.totalTaxPer}%" /></td>

													<td class="col-md-2" style="text-align: right"><input
														type="text" readonly id="taxable_amt${item.itemId}"
														value="${item.taxableValue}"
														name="taxable_amt${item.itemId}" class="form-control"></td>


													<td class="col-md-2" style="text-align: right" width="100%"><input
														type="text" readonly id='tax_amt${item.itemId}'
														value="${item.taxValue}" name="tax_amt${item.itemId}"
														class="form-control"></td>


													<td class="col-md-2" style="text-align: center"><input
														type="text" min="0"
														onkeypress="return allowOnlyNumber(event);"
														onchange="itemCalc(${item.itemId},${item.freightRate},${item.itemRate1},${item.royaltyRate},${item.totalTaxPer})"
														id="oth_cost_aft_tax${item.itemId}"
														value="${item.otherCostAfterTax}"
														name="oth_cost_aft_tax${item.itemId}" class="form-control"></td>

													<td class="col-md-2" style="text-align: right"><input
														type="text" readonly id="final_amt${item.itemId}"
														value="${item.finalTotal}" name="final_amt${item.itemId}"
														class="form-control"></td>
													<td class="col-md-2" style="text-align: center"><a
														href="#"
														onclick="callDelete(${item.itemId},${count.index})"
														class="action_btn"><i class="fa fa-trash-o"
															title="Delete"></i></a></td>


												</tr>
											</c:forEach>
										</tbody>

									</table>



									<div class="form-group"></div>


									<div class="form-group"></div>
									<div class="row">

										<div class="col-md-2">Remark</div>

										<div class="col-md-4">
											<input type="text" id="quot_remark" name="quot_remark"
												class="form-control" style="width: 100%;" value="${quotHeader.otherRemark1}" required>
										</div>

										<div class="col-md-1">
											<input type="button" class="btn btn-primary"
												onclick="valthisform()" value="Generate Quotation">

										</div>

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
			
			 $("#selAll").click(function () {
	              $('#bootstrap-data-table tbody input[type="checkbox"]').prop('checked', this.checked);
	          });
			 
			 
			
		});
	</script>



	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=quot_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>
	<script type="text/javascript">
	
	function getNewItems(args){
		//alert("Hi");
		if(args==0){
		document.getElementById('newItemAddDiv').style.display="block";
		}
		else if(args==1){
			document.getElementById('newItemAddDiv').style="display:none";
			var itemId=	document.getElementById('new_item_id').value;
			var quotQty=document.getElementById('new_item_qty').value;
			var tollCost=${quotHeader.tollCost};
			var quotHeaderId=${quotHeader.quotHeadId};
			var valid=true;
			if(quotQty<0 || quotQty==0){
				valid=false;
				alert("please enter valid quotation quantity ");
			}else if(itenmId=="" || itemId==null){
				valid=false;
				alert("please select item ");
			}
				if(valid==true){
				
			
				$
						.getJSON(
								'${getNewItemsForQuotation}',
								{
									quotHeaderId : quotHeaderId,
									itemId : itemId,
									quotQty : quotQty,
									isDelete : 0,
									index : 0,
									ajax : 'true',
								},

								function(data) {
									//alert("length " +data.length);
									//alert("Order Data " +JSON.stringify(data));
									//alert("length " +data.length);

									 var dataTable = $('#bootstrap-data-table')
									.DataTable();
							 dataTable.clear().draw();

							$.each(data,function(i, v) {
												//alert("hdjfh");
var quotQty = '<input  type="text"  class="form-control" onkeypress="return allowOnlyNumber(event);" id="quot_qty'+v.itemId+'" name="quot_qty'+v.itemId+'"  value="'+v.quotQty+'" />'
var finalAmt = '<input  type="text"   class="form-control"   id="final_amt'+v.itemId+'" name="final_amt'+v.itemId+'"/>'
var transCost='<input  type="text"  class="form-control" value='+v.transCost+'  onkeypress="return allowOnlyNumber(event);" id="trans_cost'+v.itemId+'" name="trans_cost'+v.itemId+'" oninput="itemCalc('+v.itemId+','+v.freightRate+','+v.itemRate1+','+v.royaltyRate+','+v.totalTaxPer+')"/>'
var tollCosta='<input  type="text" value='+tollCost+' readonly class="form-control"  onkeypress="return allowOnlyNumber(event);" id="toll_cost'+v.itemId+'" name="toll_cost'+v.itemId+'"/>'
var otherCost='<input  type="text" value='+v.otherCost+' class="form-control"  onkeypress="return allowOnlyNumber(event);" id="other_cost'+v.itemId+'" name="other_cost'+v.itemId+'" oninput="itemCalc('+v.itemId+','+v.freightRate+','+v.itemRate1+','+v.royaltyRate+','+v.totalTaxPer+')"/>'
var taxable='<input  type="text" value='+v.taxableValue+' readonly class="form-control"  onkeypress="return allowOnlyNumber(event);" id="taxable_amt'+v.itemId+'" name="taxable_amt'+v.itemId+'"/>'
var tax='<input  type="text" value='+v.taxValue+' readonly class="form-control"  onkeypress="return allowOnlyNumber(event);" id="tax_amt'+v.itemId+'" name="tax_amt'+v.itemId+'"/>'
var costAfTax='<input  type="text" value='+v.otherCostAfterTax+'   class="form-control"  onkeypress="return allowOnlyNumber(event);" id="oth_cost_aft_tax'+v.itemId+'" name="oth_cost_aft_tax'+v.itemId+'" oninput="itemCalc('+v.itemId+','+v.freightRate+','+v.itemRate1+','+v.royaltyRate+','+v.totalTaxPer+')"/>'
var finalAmt='<input  type="text" value='+v.finalTotal+' readonly class="form-control"  onkeypress="return allowOnlyNumber(event);" id="final_amt'+v.itemId+'" name="final_amt'+v.itemId+'"/>'
/*<a href="#" class="action_btn" onclick="callEdit('
												+ v.itemId
												+ ','
												+ i
												+ ')"><i class="fa fa-edit"></i></a>*/
var acButton = '<a href="#"  class="action_btn" onclick="callDelete('
														+ v.itemId
														+ ','
														+ i
														+ ')"><i class="fa fa-trash-o"></i></a>' 
 
												dataTable.row
														.add(
																[
																		i + 1,
																		v.itemName,
																		quotQty,
																		v.enqUomName,
																		transCost,
																		tollCosta,
																		otherCost,
																		v.itemRate1,
																		v.royaltyRate,
																		v.totalTaxPer,
																		taxable,
																		tax,
																		costAfTax,
																		finalAmt,
																		acButton
																		 ])
														.draw();
											}); 
							
							calcAll();

						 
								});	
				
		
				}	
	}
	}
	function callDelete(itemId,index){
		//alert("Item Id  " +itemId + "Index " +index);
		
		var x= confirm('Are you sure want to delete this record');
		//alert("X" +x);
		if(x==true){
		var tollCost=${quotHeader.tollCost};
		var quotHeaderId=${quotHeader.quotHeadId};
		
		$
		.getJSON(
				'${getNewItemsForQuotation}',
				{
					quotHeaderId : quotHeaderId,
					itemId : itemId,
					quotQty : 0,
					isDelete : 1,
					index : index,
					ajax : 'true',
				},

				function(data) {
					//alert("length " +data.length);
					
									//alert(data[0].tempMsg)

					//alert("Order Data " +JSON.stringify(data[0].totalTaxPer));
					//alert("length " +data.length);
					 var dataTable = $('#bootstrap-data-table')
					.DataTable();
			 dataTable.clear().draw();

			$.each(data,function(i, v) {
								//alert("hdjfh");
var quotQty = '<input  type="text"  class="form-control"  id="quotQty'+v.itemId+'" name="quotQty'+v.itemId+'"  value="'+v.quotQty+'" />'
var finalAmt = '<input  type="text"   class="form-control"   id="final_amt'+v.itemId+'" name="final_amt'+v.itemId+'"/>'
var transCost='<input  type="text"  class="form-control" value='+v.transCost+'  onkeypress="return allowOnlyNumber(event);" id="trans_cost'+v.itemId+'" name="trans_cost'+v.itemId+'" oninput="itemCalc('+v.itemId+','+v.freightRate+','+v.itemRate1+','+v.royaltyRate+','+v.totalTaxPer+')"/>'
var tollCosta='<input  type="text" value='+tollCost+' readonly class="form-control"  onkeypress="return allowOnlyNumber(event);" id="toll_cost'+v.itemId+'" name="toll_cost'+v.itemId+'"/>'
var otherCost='<input  type="text" value='+v.otherCost+' class="form-control"  onkeypress="return allowOnlyNumber(event);" id="other_cost'+v.itemId+'" name="other_cost'+v.itemId+'" oninput="itemCalc('+v.itemId+','+v.freightRate+','+v.itemRate1+','+v.royaltyRate+','+v.totalTaxPer+')"/>'
var taxable='<input  type="text" value='+v.taxableValue+' readonly class="form-control"  onkeypress="return allowOnlyNumber(event);" id="taxable_amt'+v.itemId+'" name="taxable_amt'+v.itemId+'"/>'
var tax='<input  type="text" value='+v.taxValue+' readonly class="form-control"  onkeypress="return allowOnlyNumber(event);" id="tax_amt'+v.itemId+'" name="tax_amt'+v.itemId+'"/>'
var costAfTax='<input  type="text" value='+v.otherCostAfterTax+'   class="form-control"  onkeypress="return allowOnlyNumber(event);" id="oth_cost_aft_tax'+v.itemId+'" name="oth_cost_aft_tax'+v.itemId+'" oninput="itemCalc('+v.itemId+','+v.freightRate+','+v.itemRate1+','+v.royaltyRate+','+v.totalTaxPer+')"/>'
var finalAmt='<input  type="text" value='+v.finalTotal+' readonly class="form-control"  onkeypress="return allowOnlyNumber(event);" id="final_amt'+v.itemId+'" name="final_amt'+v.itemId+'"/>'
var acButton = '<a href="#" class="action_btn" onclick="callEdit('
								+ v.itemId
								+ ','
								+ i
								+ ')"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete('
										+ v.itemId
										+ ','
										+ i
										+ ')"><i class="fa fa-trash"></i></a>' 

								dataTable.row
										.add(
												[
														i + 1,
														v.itemName,
														quotQty,
														v.enqUomName,
														transCost,
														tollCosta,
														otherCost,
														v.itemRate1,
														v.royaltyRate,
														v.totalTaxPer,
														taxable,
														tax,
														costAfTax,
														finalAmt,
														acButton
														 ])
										.draw();
							}); 
			
		 
				});	


		}
		
	}
	
	</script>


	<script type="text/javascript">
	function showDocDetailPopup(){
		
		var termId=	document.getElementById('quot_doc_term_id').value;
		//alert("Hi doc Detail " +termId);
	

		   $.getJSON('${getDocTermDetail}', {
			 termId : termId,
				ajax : 'true',

			},

			function(data) {

				//alert("Data " +JSON.stringify(data.detailList));
				
				//newwindow=window.open(data,'name','height=40,width=40');
				
				
				var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("quot_doc_term_id");

// Get the <span> element that closes the modal
 var span = document.getElementById("close");

// When the user clicks the button, open the modal 

	
		modal.style.display = "block";
	    //itemByIntendId(); 
	    //getValue();
	
    


// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none"; 
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        
    }
}
				var temp;
				var len = data.detailList.length;
					
							var dataTable = $('#table_grid1')
									.DataTable();
							dataTable.clear().draw();
var termTitle=data.termTitle
//$("term_title").text(termTitle);
//document.getElementById("term_title").value="Term Title :" +termTitle;
//document.getElementById("term_title").style.display="block";
							$
									.each(
											data.detailList,
											function(i, v) {
												var index=i+1;
												var desc1=index+") "+v.termDesc;
												dataTable.row
														.add(
																[
																	desc1])
														.draw();
											});
				// window.alert("Term Detail "+data.detailList[i].termDesc);
				
				// alert("Term Detail "+temp);

	}); 
		   
	}
	</script>


	<script type="text/javascript">
		
		function changeTaxValue1(value1){
			//alert("Inside changeTaxValue " +value1);
			calcAll();
		}
		</script>
	<script type="text/javascript">
		
		function setData(){
			var payTerm= $("#pay_term_id option:selected").html();
			//alert("payTerm " +payTerm);
			document.getElementById("pay_term_name").value=payTerm;
			//showDocDetailPopup();
			
		}
		</script>
	<!-- <script type="text/javascript">
		
		function toggle() {
			  checkboxes = document.getElementsByName('selectItem');
			  for(var i=0, n=checkboxes.length;i<n;i++) {
			    checkboxes[i].checked = source.checked;
			  }
			  }
				  
		</script> -->
	<script type="text/javascript">
		
		function callsetKM(){
			
			var x=${quotHeader.noOfKm};
			//alert("No of km " +x);
			if(x==0){
				
				setKM(1);
			}else{
				setKM(0);
			}
		}
		
		function setKM(delPlace) {
		//alert("hiii");
				 if(delPlace==1){
					document.getElementById("no_of_km").value="0";
					document.getElementById("toll_amt").value="0";
					document.getElementById("no_of_tolls").value="0";

					
					document.getElementById("no_of_km").readOnly = true; 
					document.getElementById("toll_amt").readOnly = true; 
					document.getElementById("no_of_tolls").readOnly = true; 
					calcAll();
				}else{
					document.getElementById("no_of_km").readOnly = false; 
					document.getElementById("toll_amt").readOnly = false; 
					document.getElementById("no_of_tolls").readOnly = false; 
					
					var noofkm=${quotHeader.noOfKm};
					var tollAmt=${quotHeader.tollCost};
					var noOfTolls=${quotHeader.noOfTolls};
					
					document.getElementById("no_of_km").value=noofkm;
					document.getElementById("toll_amt").value=tollAmt;
					document.getElementById("no_of_tolls").value=noOfTolls;
					
					calcAll();
					
				}
			 
			}
		
		</script>



	
	<!--  CalcAll function onchange of KM  -->

	<script type="text/javascript">
		function calcAll() {
			//alert("in call ");
				
		//	alert("isTaxInc2 " +isTaxInc);
			
			var otherCostHeader= document.getElementById("other_cost").value;
			var tollCost= document.getElementById("toll_amt").value;
			var km= document.getElementById("no_of_km").value;
			
			var valid=true;
			
			 if(otherCostHeader==null || otherCostHeader==""){
				 valid=false;
			 }
			 else if(tollCost==null || tollCost==""){
				 valid=false;
			 }
			 else if(km==null || km==""){
			 valid=false;
 			}
			 
			 
			 if(otherCostHeader<0){
				 valid=false;
				 alert("Please enter valid other cost");
			 }
			 else if(tollCost<0){
				 valid=false;
				 alert("Please enter valid toll cost");

			 }
			 else if(km<0){
			 valid=false;
			 alert("Please enter valid kilometer");

 			}
			 
			 if(valid==true){

		 var plantId=${quotHeader.plantIds};
		 var enqHeadId=${quotHeader.enqHeadId};
			
		 $.getJSON('${getItemsAndEnqItemList}', {

				plantId : plantId,
				enqHeadId : enqHeadId,
				ajax : 'true',

			},

			function(data) {
				//alert("data " +JSON.stringify(data));
				var len = data.length;
				 if(tollCost==null || tollCost==""){
					tollCost=0;
				 }
				 
				 for (var i = 0; i < len; i++) {
					 var frRate=parseFloat(data[i].freightRate);
					 var transCost=frRate * km;
					 var itemRate=parseFloat(data[i].itemRate1);
					 var royRate=parseFloat(data[i].royaltyRate);
					 var taxPer=parseFloat(data[i].totalTaxPer);
						//alert("data[i].royaltyRate" +data[i].royaltyRate);
						 //alert("taxPerfdfdvdv" +isTaxInc);
						 var isTaxInc = $("input[name=is_tax_inc]:checked").val()
				//alert("isTaxInc Yes No Per Item  " +isTaxInc);
					 if(isTaxInc==0){
						 taxPer=0;
					 }
					// alert("TAx pes" +taxPer);

					document.getElementById("trans_cost"+data[i].itemId).value=transCost.toFixed(2);
					document.getElementById("toll_cost"+data[i].itemId).value=tollCost;
					document.getElementById("other_cost"+data[i].itemId).value=otherCostHeader;
					
					 var otherCost= document.getElementById("other_cost"+data[i].itemId).value;
					 
					 if(otherCost==null || otherCost==""){
						 otherCost=0;
					 }
					 
					 var taxableAmt=parseFloat(itemRate)+parseFloat(tollCost)+parseFloat(transCost)+parseFloat(otherCost)+parseFloat(royRate);
					//alert("taxabelAmt " +taxableAmt); 
					 document.getElementById("taxable_amt"+data[i].itemId).value=taxableAmt;

					 var taxAmt=(taxableAmt*taxPer)/100;
					 document.getElementById("tax_amt"+data[i].itemId).value=taxAmt;
						//alert("taxAmt " +taxAmt); 

					var otherCostAfterTax= document.getElementById("oth_cost_aft_tax"+data[i].itemId).value;
					 
					 if(otherCostAfterTax==null || otherCostAfterTax==""){
						 otherCostAfterTax=0;
					 }
					 var finalAmt=parseFloat(taxableAmt)+parseFloat(taxAmt)+parseFloat(otherCostAfterTax);

					 document.getElementById("final_amt"+data[i].itemId).value=finalAmt;
					 
				 } 
				 
			});
 
			 }
		}
</script>
	<script type="text/javascript">
	function itemCalc(itemId,fRate,itemRate,royRate,taxPer){
		//alert("itemCalc taxPer 1" +taxPer)
	//alert("Hi");
	/* var isTaxInc;
			if (document.getElementById('is_tax_inc').checked) {
				isTaxInc = document.getElementById('is_tax_inc').value;
				}
			else{
				isTaxInc=0;
			}
			 if(isTaxInc==0){
				 taxPer=0;
			 } */
			 
		 var isTaxInc = $("input[name=is_tax_inc]:checked").val()
				 if(isTaxInc==0){
					 taxPer=0;
				 }
	var tollCost =document.getElementById("toll_cost"+itemId).value;
	var transCost =document.getElementById("trans_cost"+itemId).value;
	var otherCost= document.getElementById("other_cost"+itemId).value;
	var valid=true;
	 if(otherCost<0){
		 valid=false;
		 alert("Please enter valid other cost");
	 }
	 else if(tollCost<0){
		 valid=false;
		 alert("Please enter valid toll cost");

	 }
	 else if(transCost<0){
	 valid=false;
	 alert("Please enter valid transport cost");

		}
	 
	
	var otherCostAfterTax= document.getElementById("oth_cost_aft_tax"+itemId).value;
	 if(otherCostAfterTax==null || otherCostAfterTax=="" || otherCostAfterTax<0){
		 otherCostAfterTax=0;
	 }
	
	 if(otherCost==null || otherCost==""){
		 otherCost=0;
	 }
	 
	 if(tollCost==null || tollCost==""){
		 tollCost=0;
	 }
	 
	 if(transCost==null || transCost==""){
		 transCost=0;
	 }
	 
	 if(valid==true){
	
	 var taxableAmt=parseFloat(itemRate)+parseFloat(tollCost)+parseFloat(transCost)+parseFloat(otherCost)+parseFloat(royRate);
	
	 document.getElementById("taxable_amt"+itemId).value=taxableAmt;

	 var taxAmt=(taxableAmt*taxPer)/100;

	 document.getElementById("tax_amt"+itemId).value=taxAmt;
	 var finalAmt=parseFloat(taxableAmt)+parseFloat(taxAmt)+parseFloat(otherCostAfterTax);
	 document.getElementById("final_amt"+itemId).value=finalAmt;
	 
	 }
	 
}

</script>

	<script type="text/javascript">
		$(document).ready(function() {
			var dataTable = $('#table_grid1').DataTable();
				columnDefs : [ {
					targets : [ 1,2],
					className : "right"
				}, ]
			
		});
		
		
	</script>

	<script type="text/javascript">
	
	function valthisform()
	{
	    var okay=true;
/* 
	    var checkboxs=document.getElementsByName("selectItem");
	    for(var i=0,l=checkboxs.length;i<l;i++)
	    {
	        if(checkboxs[i].checked)
	        {
	            okay=true;
	            break;
	        }
	    } */
	   // alert("Okay " +okay);

	    var quotTerm=document.getElementById("quot_doc_term_id").value;
	    var payTerm=document.getElementById("pay_term_id").value;
	  
	     if(quotTerm<0 || quotTerm=="" || quotTerm==null){

	    	okay=false;

	    	alert("please select quotation term ");
	    }
	    else if(payTerm<0 || payTerm=="" || payTerm==null){
	    	okay=false;

		   // alert("Okay in pay term " +okay);

	    	alert("please select payment term ");
	    	
	    }
	    else if(okay){
		   // alert("Okay on submit" +okay);

	    	var form=document.getElementById("updateQuotation");
	    	form.submit();
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
	<script type="text/javascript">
	 function allowOnlyNumber1(evt){
		 var valid=true;
	  var charCode = (evt.which) ? evt.which : event.keyCode
	  if (charCode > 31 && charCode==46 && (charCode < 48 || charCode > 57)){
		  valid=false;
	  }
	  return valid;
	} 
	</script>
	
<!-- 	<script type="text/javascript">

        $(document).ready(function(){

            $('input[type="checkbox"]').click(function(){

                if($(this).prop("checked") == true){

                   // alert("Checkbox is checked.");

                }

                else if($(this).prop("checked") == false){

                    // alert("Checkbox is unchecked.");

                }

            });
            
            
            

        });

    </script> -->

	
	<!-- <script type="text/javascript">
		function getData() {
			var plantId = document.getElementById("plant_id").value;
			document.getElementById("isEdit").value = 0;
			var valid = true;

			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please Select plant");
			}

			if (valid == true) {

				$.getJSON('${getItemsByPlantId}', {

					plantId : plantId,
					ajax : 'true',

				},

				function(data) {
					var html;
					var len = data.length;
					var html = '<option value="-1"  >Select Item</option>';
					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].itemId + '">'
								+ data[i].itemName + '</option>';
					}
					html += '</option>';

					$('#item_name').html(html);
					$("#item_name").trigger("chosen:updated");

				});

				$.getJSON('${getCustByPlantId}', {
					plantId : plantId,
					ajax : 'true',
				},

				function(data) {
					var html;
					var len = data.length;
					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].custId + '">'
								+ data[i].custName + '</option>';

					}
					html += '</option>';

					$('#cust_name').html(html);
					$("#cust_name").trigger("chosen:updated");

				});

			}//end of if

		}
	</script>

	<script type="text/javascript">
		function setSelectedUom(itemId) {

			if (itemId == -1) {
				document.getElementById("qty").value = "";
				document.getElementById("item_remark").value = "";
				document.getElementById("item_name").options.selectedIndex = "0";
				document.getElementById("uomId").options.selectedIndex = "0";
				$("#uomId").trigger("chosen:updated");
				$("#item_name").trigger("chosen:updated");
				document.getElementById("isEdit").value = "0";
				document.getElementById("itemUomId").value = "0";
				document.getElementById("item_rate").value = "0"
			} else {
				$
						.getJSON(
								'${getItemByItemId}',
								{
									itemId : itemId,
									ajax : 'true',
								},

								function(data) {
									document.getElementById("uomId").value = data.uomId;
									$("#uomId").trigger("chosen:updated");
									document.getElementById("itemUomId").value = data.uomId;
									document.getElementById("item_rate").value = data.itemRate1;
								});
			}
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

	<!-- <script>
// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("quot_doc_term_id");

// Get the <span> element that closes the modal
 var span = document.getElementById("close");

// When the user clicks the button, open the modal 
btn.change = function() {
	
		modal.style.display = "block";
	    //itemByIntendId(); 
	    //getValue();
	
    
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none"; 
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        
    }
}

</script>
 -->
</body>
</html>