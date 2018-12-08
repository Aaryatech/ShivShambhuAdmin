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
	
	
<c:url var="getTempOrderHeader"
	value="/getTempOrderHeader" />
	

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
					<strong>Failed !</strong>     Data not submitted  !!
				</div>
							
							</c:when>
							
							<c:when test="${isError==2}">
							
							<div class="alert1">
							
							<span class="closebtn"
						onclick="this.parentElement.style.display='none';">&times;</span>
					<strong>Success</strong>     Data Submitted !!
				</div>
							
							</c:when>
							
							</c:choose>

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<div class="col-md-3">
								<strong>${title}</strong>
							</div>
							<%-- <div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showAddCustomer"><strong>Add
										Customer</strong></a>
							</div> --%>
							

						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertBOM"
								method="post">

								<div class="row">

									<div class="col-md-2">Plant</div>

									<div class="col-md-4">
									
									<input type="text" readonly value="${prodHeader.plantName}"
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
									<div class="col-md-2">Production ID</div>
									<div class="col-md-4">
										
									<input type="text" readonly value="${prodHeader.productionHeaderId}"
												style="width: 100%;" class="form-control"> <span
												class="error" aria-live="polite"></span>
									</div>

								</div>
								<div class="form-group"></div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Production Date</div>
									<div class="col-md-4">
										<input type="text" disabled id="ord_date" name="ord_date" required
											style="width: 100%;" class="form-control"
											value="${prodHeader.productionDate}"> <span class="error"
											aria-live="polite"></span>
									</div>
									<div class="col-md-2">Start Date</div>
									<div class="col-md-4">
										<input type="text" readonly id="ord_no" name="ord_no"
											style="width: 100%;" class="form-control"
											value="${prodHeader.productionStartDate}"> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>
								<div class="form-group"></div>
								
								<div class="row">
									<div class="col-md-2">End Date</div>
									<div class="col-md-4">
										<input type="text" disabled id="ord_date" name="ord_date" required
											style="width: 100%;" class="form-control"
											value="${prodHeader.productionEndDate}"> <span class="error"
											aria-live="polite"></span>
									</div>
									
								
								<c:if test="${prodHeader.productionStatus==1}">
									<c:set var="status" value="Planned"></c:set>
								</c:if>
								
								<c:if test="${prodHeader.productionStatus==2}">
									<c:set var="status" value="Started"></c:set>
								</c:if>
								
								<c:if test="${prodHeader.productionStatus==3}">
									<c:set var="status" value="Completed"></c:set>
								</c:if>
									<div class="col-md-2">Production Status</div>
									<div class="col-md-4">
										<input type="text" readonly id="ord_no" name="ord_no"
											style="width: 100%;" class="form-control"
											value="${status}"> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>

								<div class="form-group"></div>
			
								<div class="card-body card-block">
									<table id="table1"
										class="table table-striped table-bordered">
										<thead>
											<tr>
												<th style="text-align: center">Sr.No.</th>
												<th style="text-align: center">RM Name</th>
<!-- 												<th style="text-align: center">Unit of Measurement</th>
 -->												<th style="text-align: center">Quantity</th>
											</tr>
										</thead>
										<tbody>
									<c:forEach items="${rmItemList}" var="itemDetail" varStatus="count">
										<tr>

											<td style="text-align: center">${count.index+1}</td>


											<td style="text-align: left"><c:out
													value="${itemDetail.rmName}" /></td>
										<%-- 	<td style="text-align: center"><c:out
													value="${itemDetail.rmName}" /></td>
 --%>
												
										<td style="text-align: center">
										<input  type="text"  class="form-control" value="${itemDetail.rmQuantity}"  id="rmQty${itemDetail.itemDetailId}" name="rmQty${itemDetail.itemDetailId}" />
										</td>
										
										<%-- <td style="text-align: center">
											<input  type="number"   class="form-control"  id="prodQty${prodDetail.productionDetailId}" value="${prodDetail.planQty}" name="prodQty${prodDetail.productionDetailId}"/>
										</td>
													
										<td style="text-align: center">
											<input  type="text"   class="form-control"  id="rejQty${prodDetail.productionDetailId}" value="0" name="rejQty${prodDetail.productionDetailId}"/>
										</td> --%>
													
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
								<div class="row">

								<!-- 	<div class="col-md-2">Other Cost After Tax</div>

									<div class="col-md-3">845</div> -->

									<div class="col-md-2">
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
			$('#table_grid1').DataTable();
			
			var status=${prodHeader.productionStatus}
			
			if(status==1){
				alert("Status" +status);
				 $('#table_grid1 tbody input[type="number"]').prop('readonly', true);			
				 }
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
	function showManualBOM(){
		
		window.open("${pageContext.request.contextPath}/showManBOM","_self");
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

</body>
</html>