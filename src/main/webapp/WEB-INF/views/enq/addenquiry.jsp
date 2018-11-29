<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getItemsByPlantId" value="/getItemsByPlantId" />

<c:url var="getCustByPlantId" value="/getCustByPlantId" />

<c:url var="getItemByItemId" value="/getItemByItemId" />

<c:url var="addEnqItem" value="/addEnqItem" />

<c:url var="getItemForEdit" value="/getItemForEdit" />

<c:url var="getCustInfoByCustId" value="/getCustInfoByCustId" />

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

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showAddCustomer"><strong>Add
										Customer</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertEnq"
								method="post" onsubmit="disableSubmitButton()">

								<div class="row">

									<div class="col-md-2">Select Plant</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select plant name')"
											onchange="getData()">
											<option value="">Select Plant</option>

											<c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2">Customer</div>
									<div class="col-md-4">
										<select id="cust_name" name="cust_name" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')"
											onchange="getCustInfo()">


										</select>
									</div>

								</div>
								<div id="divCheckbox" style="display: none;">
									<div class="form-group"></div>
									<div class="row">



										<div class="col-md-2">Customer Type</div>

										<div class="col-md-4">
											<input type="text" id="custTypeName" name="custTypeName"
												readonly required style="width: 100%;" class="form-control">
											<span class="error" aria-live="polite"></span>
										</div>

										<div class="col-md-2">Customer Mobile No</div>

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



									<div class="col-md-2">Enquiry Date</div>

									<div class="col-md-4">
										<input type="text" id="enq_date" name="enq_date" required readonly="readonly"
											style="width: 100%;" class="form-control" autocomplete="off"
												oninvalid="setCustomValidity('Please select enquiry date')"
											value="${curDate}"> <span class="error"
											aria-live="polite"></span>
									</div>



									<div class="col-md-2">Enquiry No</div>

									<div class="col-md-4">
										<input type="text" readonly id="enq_no" name="enq_no"
											style="width: 100%;" class="form-control"
											value="${doc.docPrefix}${doc.srNo}"> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>
								<input type="hidden" id="isEdit" name="isEdit" value="0">

								<input type="hidden" id="itemUomId" name="itemUomId" value="0">

								<div class="form-group"></div>
								<section class="form-control" style="background: orange;">

									<div class="row">


										<div class="col-md-2">Select Item</div>
										<div class="col-md-4">
											<select id="item_name" name="item_name"
												class="standardSelect" tabindex="1"
												onchange="setSelectedUom(this.value)">
											</select>
										</div>


										<div class="col-md-2">Unit Of Measure</div>

										<div class="col-md-4">
											<select id="uomId" name="uomId" class="standardSelect"
												tabindex="1"
												oninvalid="setCustomValidity('Please select uom')"
												onchange="try{setCustomValidity('')}catch(e){}">

												<option value="0">Select Unit Of Measurement</option>
												<c:forEach items="${uomList}" var="uom">
													<option value="${uom.uomId}">${uom.uomName}</option>
												</c:forEach>
											</select>
										</div>

									</div>


									<div class="form-group"></div>
									<div class="row">
										<div class="col-md-2">Quantity</div>
										<div class="col-md-4">
											<input type="text" id="qty" name="qty" class="form-control"
												style="width: 100%;" pattern="[0-9]+(\.[0-9]{0,2})?%?" onkeypress="return allowOnlyNumber(event);">
										</div>



										<div class="col-md-2">Remark</div>

										<div class="col-md-4">
											<input type="text" id="item_remark" name="item_remark"
												class="form-control" style="width: 100%;" value="-">
										</div>

									</div>
									<div class="form-group"></div>

									<div class="row">
									<!-- 	<div class="col-md-2">Rate</div>
										<div class="col-md-4">
											<input type="text" readonly="readonly" id="item_rate"
												name="item_rate" class="form-control" style="width: 100%;"
												value="0">
										</div> -->

										<div class="col-md-2"></div>

										<div class="col-md-4">
											<input type="button" value="Add Item" class="btn btn-primary"
												style="align-content: center; width: 226px;"
												onclick="addItem()" />

										</div>

									</div>
								</section>
								<div class="card-body card-block">

									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>

												<th style="text-align: center">Sr</th>
												<th style="text-align: center">Item Name</th>
												<th style="text-align: center">Unit Of Measurement</th>
												<th style="text-align: center">Quantity</th>
												<th style="text-align: center; width: 5%;">Action</th>

											</tr>
										</thead>

									</table>


								</div>
								<div class="form-group"></div>

								<div class="row">


									<div class="col-md-2">Priority</div>
									<div class="col-md-4">
										<select id="enq_prio" name="enq_prio" class="standardSelect"
											tabindex="1" required>

											<option value="0">Low</option>
											<option selected value="1">Medium</option>
											<option value="2">High</option>
										</select>
									</div>


									<div class="col-md-2">Enquiry Source</div>

									<div class="col-md-4">
										<select id="enq_gen_fact" name="enq_gen_fact"
											class="standardSelect" tabindex="1" required
											oninvalid="setCustomValidity('Please select enquiry source')"
											onchange="try{setCustomValidity('')}catch(e){}">

											<c:forEach items="${enqGenFactList}" var="enqFrom">
												<option value="${enqFrom.enqGenId}">${enqFrom.enqGenBy}</option>
											</c:forEach>
										</select>
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Enquiry Remark</div>

									<div class="col-md-4">
										<input type="text" id="enq_remark" name="enq_remark"
											class="form-control" style="width: 100%;" value="-" required>
									</div>

									<div class="col-md-2">
										<input type="submit" disabled class="btn btn-primary" id="submitButton" value="Submit">

									</div>
										<div class="col-md-2">
										<input type="reset"   value="Clear" class="btn btn-primary" >

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
			$('input[id$=enq_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
		function disableSubmitButton(){
			  document.getElementById('submitButton').innerHTML='Please Wait';
			  document.getElementById("submitButton").disabled=true;
		}
	</script>

	<script type="text/javascript">
		function getData() {
			document.getElementById("submitButton").disabled=true;
			
			//alert("Item Id ")
			var plantId = document.getElementById("plant_id").value;
			//alert("Plant Id " +plantId);
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
					getCustInfo();

				});
			}//end of if

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
			}
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
				//alert("Inside add ajax");
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
									
									//alert("in add  " +JSON.stringify(data));

									if(data==null){
										document.getElementById("submitButton").disabled=true;
									}
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
									//document.getElementById("submitButton").disabled=false;
									//$('#submitButton').prop('disabled', false);

								});
				
				document.getElementById("submitButton").disabled=false;

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
							//	alert("in edit  " +JSON.stringify(data));

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
								//alert("in delete  " +JSON.stringify(data));
								if(data==null || data==""){
									document.getElementById("submitButton").disabled=true;
								}
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