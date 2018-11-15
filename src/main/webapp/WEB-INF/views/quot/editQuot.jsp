<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin3333</title>

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
							<strong>${title}</strong>
						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/updateQuotation"
								method="post">

								<div class="row">

									<div class="col-md-2">Select Plant</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select plant name')"
											onchange="getData()">
											<option value="">Select Plant</option>

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
												<option value="${proj.projId}">${proj.projName}</option>
											</c:forEach>

										</select>
									</div>
									<div class="col-md-2">Payment Term</div>

									<div class="col-md-4">
										<select id="pay_term_id" name="pay_term_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select payment term')"
											onchange="getData()">
											<option value="">Select Pay Term</option>

											<c:forEach items="${payTermList}" var="pTerm">
												<option value="${pTerm.payTermId}">${pTerm.payTerm}</option>
											</c:forEach>
										</select>
									</div>


								</div>
								<input type="hidden" id="isEdit" name="isEdit" value="0">


								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Quotation Term</div>

									<div class="col-md-4">
										<select id="quot_doc _term_id" name="quot_doc _term_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select quotation term')"
											onchange="getData()">
											<option value="">Select Term</option>

											<c:forEach items="${docTermList}" var="qTerm">
												<option value="${qTerm.termId}">${qTerm.termTitle}</option>
											</c:forEach>
										</select>
									</div>


									<div class="col-md-2">Transport Term</div>

									<div class="col-md-4">
										<textarea id="trans_term" name="trans_term"
											class="form-control" style="width: 100%;"
											oninvalid="setCustomValidity('Please enter transport term')"
											onchange="try{setCustomValidity('')}catch(e){}"></textarea>
									</div>

								</div>


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Quotation Date</div>
									<div class="col-md-4">
										<input type="text" id="quot_date" name="quot_date"
											class="form-control" style="width: 100%;"
											value="${quotHeader.quotDate }">
									</div>

									<div class="col-md-2">Delivery Place</div>
									<div class="col-md-2">
										On Spot <input type="radio" checked name="del_place"
											id="del_place" value="1">
									</div>

									<div class="col-md-2">
										Specific Place <input type="radio" name="del_place"
											id="del_place" value="0">
									</div>


								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">No of KM</div>
									<div class="col-md-4">
										<input type="text" id="no_of_km" name="no_of_km" onchange="calcAll(this.value)"
											class="form-control" style="width: 100%;" value="0">
									</div>

									<div class="col-md-2">Location</div>

									<div class="col-md-4">
										<input type="text" id="location" name="location"
											class="form-control" style="width: 100%;" value="-">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Calculate By</div>
									<div class="col-md-2">
										Automatic<input type="radio" checked name="calc_by"
											id="calc_by" value="1">
									</div>

									<div class="col-md-2">
										Manual<input type="radio" name="calc_by" id="calc_by"
											value="0">
									</div>

									<div class="col-md-2">No of Tolls</div>
									<div class="col-md-4">
										<input type="text" id="no_of_tolls" name="no_of_tolls"
											class="form-control" style="width: 100%;">
									</div>


								</div>

								<div class="form-group"></div>


								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">is Tax Included</div>
									<div class="col-md-1">
										Yes<input type="radio" checked name="is_tax_inc"
											id="is_tax_inc" value="1">
									</div>

									<div class="col-md-1">
										No<input type="radio" name="is_tax_inc" id="is_tax_inc"
											value="0">
									</div>
									<div class="col-md-2"></div>

									<div class="col-md-2">Toll  Amount</div>
									<div class="col-md-4">
										<input type="text" id="toll_amt" name="toll_amt"
											class="form-control" >
									</div>

								</div>

								<div class="card-body card-block">

									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>

												<th style="text-align: center" width="2%">Sr</th>

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
												<th style="text-align: center" class="col-md-1">GSt%</th>

												<th style="text-align: center" class="col-md-1">Taxable</th>
												<th style="text-align: center" class="col-md-1">Tax</th>
												<th style="text-align: center" class="col-md-1">Final</th>

											</tr>
										</thead>
										<tbody>

											<c:forEach items="${itemList}" var="item" varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}</td>


													<td style="text-align: left"><c:out
															value="${item.itemName}" /></td>

													<td style="text-align: left"><input type="text"
														id="quot_qty${item.itemId}" name="quot_qty${item.itemId}" value="${item.enqQty}" class="form-control"></td>

													<td style="text-align: left"><c:out
															value="${item.uomName}" /></td>


													<td style="text-align: center"><input type="text"
														id="trans_cost${item.itemId}" name="trans_cost${item.itemId}" class="form-control"></td>

													<td style="text-align: center"><input type="text"
														id="toll_cost${item.itemId}" name="toll_cost${item.itemId}" class="form-control"></td>

													<td style="text-align: center"><input type="text"
														id="other_cost${item.itemId}" name="other_cost${item.itemId}" class="form-control"></td>
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
															value="${item.taxName}" /></td>

													<td style="text-align: center"><input type="text"
														id="taxable_amt${item.itemId}" name="taxable_amt${item.itemId}" class="form-control"></td>


													<td style="text-align: left"><input type="text"
														id="tax_amt${item.itemId}" name="tax_amt${item.itemId}" class="form-control"></td>

													<td style="text-align: center"><input type="text"
														id="final_amt${item.itemId}" name="final_amt${item.itemId}" class="form-control"></td>


												</tr>
											</c:forEach>
										</tbody>

									</table>


								</div>
								<div class="form-group"></div>

								<%-- <div class="row">


									<div class="col-md-2">Priority</div>
									<div class="col-md-4">
										<select id="enq_prio" name="enq_prio" class="standardSelect"
											tabindex="1" required>

											<option value="0">Low</option>
											<option selected value="1">Medium</option>
											<option value="2">High</option>
										</select>
									</div>


									<div class="col-md-2">Enquiry From</div>

									<div class="col-md-4">
										<select id="enq_gen_fact" name="enq_gen_fact"
											class="standardSelect" tabindex="1" required
											oninvalid="setCustomValidity('Please select enq gen fact')"
											onchange="try{setCustomValidity('')}catch(e){}">

											<c:forEach items="${enqGenFactList}" var="enqFrom">
												<option value="${enqFrom.enqGenId}">${enqFrom.enqGenBy}</option>
											</c:forEach>
										</select>
									</div>

								</div> --%>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Remark</div>

									<div class="col-md-9">
										<input type="text" id="enq_remark" name="enq_remark"
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
	
	<!--  CalcAll function onchange of KM  -->
	
		<script type="text/javascript">
		function calcAll(km) {
			//alert("KM " +km);
			
		 var plantId=${quotHeader.plantIds};
		 var enqHeadId=${quotHeader.enqHeadId};
		// alert("Plant " +plantId);
			
			$.getJSON('${getItemsAndEnqItemList}', {

				plantId : plantId,
				enqHeadId : enqHeadId,
				ajax : 'true',

			},

			function(data) {
				//alert("data " +JSON.stringify(data));
				var len = data.length;
				//	alert("len " +len);
				// var qty = document.getElementById(data[1].itemId+"quot_qty").value;
				// alert("qty " +qty);
										 var tollCost= document.getElementById("toll_amt").value;

//alert("tollCost" +tollCost);
				 for (var i = 0; i < len; i++) {
					 var frRate=parseFloat(data[i].freightRate);
					 var transCost=frRate*km;
					 var itemRate=parseFloat(data[i].itemRate1);
					 var royRate=parseFloat(data[i].royaltyRate);
					// alert("transCost" +transCost);
					 document.getElementById("trans_cost"+data[i].itemId).value=transCost.toFixed(2);
					 document.getElementById("toll_cost"+data[i].itemId).value=tollCost;
					 var otherCost= document.getElementById("other_cost"+data[i].itemId).value;
					 
					 var taxableAmt=(itemRate+tollCost)+(transCost+otherCost)+royRate;
					 alert("taxabelAmt " +taxableAmt); 
					 document.getElementById("taxable_amt"+data[i].itemId).value=taxableAmt;

				 } 
				 
				 
			});
 
			
		}
</script>
	<script type="text/javascript">
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
	</script>

	<script type="text/javascript">
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
	</script>

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