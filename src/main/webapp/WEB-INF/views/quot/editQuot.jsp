<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getProjectByCustId" value="/getProjectByCustId" />

<c:url var="getItemsAndEnqItemList" value="/getItemsAndEnqItemList" />


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
<body>


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
							<strong>${title}</strong> Quotation No : <strong>${quotHeader.quotNo}</strong>
							Date : <strong>${quotHeader.quotDate}</strong>
						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/updateQuotation"
								method="post">

								<div class="row">

									<div class="col-md-2">Select Plant</div>

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
													<%-- <c:otherwise>
														<option value="${cust.custId}">${cust.custName}</option>

													</c:otherwise> --%>
												</c:choose>

											</c:forEach>
										</select>
									</div>

								</div>
								<input type="hidden" name="item_id" id="item_id" value="0">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Project</div>
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
									<div class="col-md-2">Payment Term</div>

									<div class="col-md-4">
										<select id="pay_term_id" name="pay_term_id"
											class="standardSelect" tabindex="1" required
											oninvalid="setCustomValidity('Please select payment term')"
											onchange="setData()">
											<option value="">Select Pay Term</option>

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
								<input type="hidden" id="quotHeadId" name="quotHeadId" value="${quotHeader.quotHeadId}">
								<input type="hidden" id="pay_term_name" name="pay_term_name" value="${quotHeader.payTerms}">


								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Quotation Term</div>

									<div class="col-md-4">
										<select id="quot_doc_term_id" name="quot_doc_term_id"
											class="standardSelect" tabindex="1" required
											oninvalid="setCustomValidity('Please select quotation term')">
											<option value="">Select Term</option>

											<c:forEach items="${docTermList}" var="qTerm">
											<c:choose>
											<c:when test="${quotHeader.quotTermId==qTerm.termId}">
												<option selected  value="${qTerm.termId}">${qTerm.termTitle}</option>
												</c:when>
												<c:otherwise>
												<option   value="${qTerm.termId}">${qTerm.termTitle}</option>
												</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>


									<div class="col-md-2">Transport Term</div>

									<div class="col-md-4">
										<textarea id="trans_term" name="trans_term"
											class="form-control" style="width: 100%;"
											oninvalid="setCustomValidity('Please enter transport term')"
											onchange="try{setCustomValidity('')}catch(e){}">${quotHeader.transportTerms}</textarea>
									</div>

								</div>


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Quotation Date</div>
									<div class="col-md-4">
										<input type="text" id="quot_date" name="quot_date"
											class="form-control" style="width: 100%;"
											value="${quotHeader.quotDate}">
									</div>

									<div class="col-md-2">Delivery Place</div>
									
										<c:choose>
									<c:when test="${quotHeader.noOfKm==0}">
									<div class="col-md-2">
										On Spot <input type="radio" name="del_place" checked
											onchange="setKM(this.value)" id="del_place" value="1">
									</div>

									<div class="col-md-2">
										Specific Place <input type="radio" name="del_place"
											onchange="setKM(this.value)" id="del_place" value="0">
									</div>
									</c:when>
									<c:otherwise>
									<div class="col-md-2">
										On Spot <input type="radio" name="del_place"
											onchange="setKM(this.value)" id="del_place" value="1">
									</div>

									<div class="col-md-2">
										Specific Place <input type="radio" name="del_place" checked
											onchange="setKM(this.value)" id="del_place" value="0">
									</div>
									
									</c:otherwise>
									</c:choose>


								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">No of KM</div>
									<div class="col-md-4">
										<input type="text" id="no_of_km" name="no_of_km"
											onchange="calcAll()" class="form-control" value="${quotHeader.noOfKm}"
											style="width: 100%;">
									</div>
									<div class="col-md-2">Toll Amount</div>
									<div class="col-md-4">
										<input type="text" id="toll_amt" name="toll_amt" value="${quotHeader.tollCost}"
											onchange="calcAll()" class="form-control">
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
										<input type="text" onchange="calcAll()" id="other_cost" value="${quotHeader.otherCost}"
											name="other_cost" class="form-control" style="width: 100%;">
									</div>

									<div class="col-md-2">No of Tolls</div>
									<div class="col-md-4">
										<input type="text" id="no_of_tolls" name="no_of_tolls" value="${quotHeader.noOfTolls}"
											class="form-control" style="width: 100%;">
									</div>

								</div>

								<div class="form-group"></div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">is Tax Included</div>
									
									<c:choose>
									<c:when test="${quotHeader.taxValue==0}">
										<div class="col-md-1">
										Yes<input type="radio"  name="is_tax_inc"
											id="is_tax_inc" value="1"
											onchange="changeTaxValue(this.value)">
									</div>

									<div class="col-md-1">
										No<input  checked type="radio" name="is_tax_inc" id="is_tax_inc"
											value="0" onchange="changeTaxValue(this.value)">
									</div>
									</c:when>
									<c:otherwise>
									<div class="col-md-1">
										Yes<input type="radio" checked name="is_tax_inc"
											id="is_tax_inc" value="1"
											onchange="changeTaxValue(this.value)">
									</div>

									<div class="col-md-1">
										No<input   type="radio" name="is_tax_inc" id="is_tax_inc"
											value="0" onchange="changeTaxValue(this.value)">
									</div>
									</c:otherwise>
									</c:choose>
								</div>
									<!-- <div class="col-md-2"></div>
									<div class="col-md-2">Location</div>

									<div class="col-md-4">
										<input type="text" id="location" name="location"
											class="form-control" style="width: 100%;" value="-">
									</div>
 -->

								
								<input type="checkbox" name="selAll" id="selAll" /> 
							<div class="card-body card-block">

									<table id="bootstrap-data-table" class="table table-striped table-bordered">
										
										<thead>
										
											<tr>

												<th style="text-align: center" width="2%">Sr </th>

												<th style="text-align: center" class="col-md-1">Item</th>
												<th style="text-align: center" class="col-md-1">Qty</th>
												<th style="text-align: center" class="col-md-1">UOM</th>

												<th style="text-align: center" class="col-md-1">Tran co</th>
												<th style="text-align: center" class="col-md-1">Toll
													Cost</th>
												<th style="text-align: center" class="col-md-1">Other
													Cost</th>

												<th style="text-align: center" class="col-md-1">Item
													Rate</th>
												<th style="text-align: center" class="col-md-1">Roy
													Rate</th>
												<th style="text-align: center" class="col-md-1">GST</th>

												<th style="text-align: center" class="col-md-1">Taxable</th>
												<th style="text-align: center" class="col-md-1">Tax</th>
												<th style="text-align: center" class="col-md-1">Other
													Cost After Tax</th>

												<th style="text-align: center" class="col-md-1">Final</th>

											</tr>
										</thead>
										<tbody>

											<c:forEach items="${itemList}" var="item" varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}<input type="checkbox" value="${item.itemId}"  name="selectItem"></td>


													<td style="text-align: left"><c:out
															value="${item.itemName}" /></td>
															
															<c:if test="${quotHeader.status==0}">
															<c:set var="qty" value="${item.enqQty}"></c:set>
															</c:if>
															
															<c:if test="${quotHeader.status > 0}">
															<c:set var="qty" value="${item.quotQty}"></c:set>
															</c:if>

													<td style="text-align: left"><input type="text"
														id="quot_qty${item.itemId}" name="quot_qty${item.itemId}"
														value="${qty}" class="form-control"></td>

													<td style="text-align: left"><c:out value="${item.uomName}" /></td>


													<td style="text-align: center"><input type="text"
														id="trans_cost${item.itemId}" value="${item.transCost}"
														onchange="itemCalc(${item.itemId},${item.freightRate},${item.itemRate1},${item.royaltyRate},${item.totalTaxPer})"
														name="trans_cost${item.itemId}" class="form-control"></td>

													<td style="text-align: center"><input type="text"
														id="toll_cost${item.itemId}" readonly value="${quotHeader.tollCost}"
														name="toll_cost${item.itemId}" class="form-control"></td>

													<td style="text-align: center"><input type="text" 
														onchange="itemCalc(${item.itemId},${item.freightRate},${item.itemRate1},${item.royaltyRate},${item.totalTaxPer})"
														id="other_cost${item.itemId}"  value="${item.otherCost}"
														name="other_cost${item.itemId}" class="form-control"></td>
													<!-- 
													<td style="text-align: center"><input type="text"
														id="trans_cost" name="trans_cost" class="form-control"
														style="width: 100%;"></td>
 -->


													<td style="text-align: left"><c:out
															value="${item.itemRate1}" /></td>

													<td style="text-align: left"><c:out
															value="${item.royaltyRate}" /></td>

													<td style="text-align: left"><c:out
															value="${item.totalTaxPer}%" /></td>

													<td style="text-align: right"><input type="text"
														readonly id="taxable_amt${item.itemId}" value="${item.taxableValue}"
														name="taxable_amt${item.itemId}" class="form-control"></td>


													<td style="text-align: right" width="100%"><input
														type="text" readonly id="tax_amt${item.itemId}" value="${item.taxValue}"
														name="tax_amt${item.itemId}" class="form-control"></td>


													<td style="text-align: center"><input type="text"
														onchange="itemCalc(${item.itemId},${item.freightRate},${item.itemRate1},${item.royaltyRate},${item.totalTaxPer})"
														id="oth_cost_aft_tax${item.itemId}"  value="${item.otherCostAfterTax}"
														name="oth_cost_aft_tax${item.itemId}" class="form-control"></td>

													<td style="text-align: right"><input type="text"
														readonly id="final_amt${item.itemId}" value="${item.finalTotal}"
														name="final_amt${item.itemId}" class="form-control"></td>


												</tr>
											</c:forEach>
										</tbody>

									</table>


								</div>
								<div class="form-group"></div>


								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Remark</div>

									<div class="col-md-9">
										<input type="text" id="quot_remark" name="quot_remark"
											class="form-control" style="width: 100%;" value="-" required>
									</div>

									<div class="col-md-1">
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
	function checkEdit(){
		calcAll();
	}
		function setKM(delPlace) {
		//	alert("delPlace " +delPlace)
			if(delPlace==1){
				document.getElementById("no_of_km").value="0";
				document.getElementById("toll_amt").value="0";
				calcAll();
			}
		
		}
		function changeTaxValue(value){
			calcAll();
		}
		
		function setData(){
			var payTerm= $("#pay_term_id option:selected").html();
			alert("payTerm " +payTerm);
			document.getElementById("pay_term_name").value=payTerm;
			
		}
		
		function toggle() {
			  checkboxes = document.getElementsByName('selectItem');
			  for(var i=0, n=checkboxes.length;i<n;i++) {
			    checkboxes[i].checked = source.checked;
			  }
			}
		</script>


	<!--  CalcAll function onchange of KM  -->

	<script type="text/javascript">
		function calcAll() {
			//alert("KM " +km);
			var isTaxInc;
			if (document.getElementById('is_tax_inc').checked) {
				isTaxInc = document.getElementById('is_tax_inc').value;
				}
			else{
				isTaxInc=0;
			}
			//alert("isTaxInc " +isTaxInc);
			
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
	//alert("Valid " +valid);
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
					 var transCost=frRate*km;
					 var itemRate=parseFloat(data[i].itemRate1);
					 var royRate=parseFloat(data[i].royaltyRate);
					 var taxPer=parseFloat(data[i].totalTaxPer);
					 
					 if(isTaxInc==0){
						 taxPer=0;
					 }
					// alert("transCost" +transCost);
					
					document.getElementById("trans_cost"+data[i].itemId).value=transCost.toFixed(2);
					document.getElementById("toll_cost"+data[i].itemId).value=tollCost;
					document.getElementById("other_cost"+data[i].itemId).value=otherCostHeader;
					
					 var otherCost= document.getElementById("other_cost"+data[i].itemId).value;
					 
					 if(otherCost==null || otherCost==""){
						 otherCost=0;
					 }
					 
					 var taxableAmt=parseFloat(itemRate)+parseFloat(tollCost)+parseFloat(transCost)+parseFloat(otherCost)+parseFloat(royRate);
					// alert("taxabelAmt " +taxableAmt); 
					 document.getElementById("taxable_amt"+data[i].itemId).value=taxableAmt;

					 var taxAmt=(taxableAmt*taxPer)/100;
					 document.getElementById("tax_amt"+data[i].itemId).value=taxAmt;
					 
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
	//alert("Hi");
	var isTaxInc;
			if (document.getElementById('is_tax_inc').checked) {
				isTaxInc = document.getElementById('is_tax_inc').value;
				}
			else{
				isTaxInc=0;
			}
			 if(isTaxInc==0){
				 taxPer=0;
			 }
	var tollCost =document.getElementById("toll_cost"+itemId).value;
	var transCost =document.getElementById("trans_cost"+itemId).value;
	var otherCost= document.getElementById("other_cost"+itemId).value;
	
	var otherCostAfterTax= document.getElementById("oth_cost_aft_tax"+itemId).value;
	 if(otherCostAfterTax==null || otherCostAfterTax==""){
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
	
	var taxableAmt=parseFloat(itemRate)+parseFloat(tollCost)+parseFloat(transCost)+parseFloat(otherCost)+parseFloat(royRate);
	
	document.getElementById("taxable_amt"+itemId).value=taxableAmt;

	var taxAmt=(taxableAmt*taxPer)/100;

	document.getElementById("tax_amt"+itemId).value=taxAmt;
	 var finalAmt=parseFloat(taxableAmt)+parseFloat(taxAmt)+parseFloat(otherCostAfterTax);
	 document.getElementById("final_amt"+itemId).value=finalAmt;
	 
}

</script>
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

</body>
</html>