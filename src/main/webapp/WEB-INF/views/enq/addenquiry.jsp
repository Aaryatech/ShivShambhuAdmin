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
							<form action="${pageContext.request.contextPath}/insertEnq"
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
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2">Customer</div>
									<div class="col-md-4">
										<select id="cust_name" name="cust_name" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')"
											onchange="try{setCustomValidity('')}catch(e){}">


										</select>
									</div>

								</div>
								<input type="hidden" name="item_id" id="item_id" value="0">
								<div class="form-group"></div>
								<div class="row">



									<div class="col-md-2">Enquiry Date</div>

									<div class="col-md-4">
										<input type="text" id="enq_date" name="enq_date" required
											style="width: 100%;" class="form-control"
											value="${editComp.contactNo1}"> <span class="error"
											aria-live="polite"></span>
									</div>



									<div class="col-md-2">Enquiry No</div>

									<div class="col-md-4">
										<input type="text" readonly id="enq_no" name="enq_no"
											style="width: 100%;" class="form-control"
											value="${editComp.contactNo1}"> <span class="error"
											aria-live="polite"></span>
									</div>

								</div>

								<div class="form-group"></div>
								<section class="form-control" style="background: orange;">

									<div class="row">


										<div class="col-md-2">Item Name</div>
										<div class="col-md-4">
											<select id="item_name" name="item_name"
												class="standardSelect" tabindex="1"
												onchange="setSelectedUom(this.value)">
											</select>
										</div>


										<div class="col-md-2">Select UOM</div>

										<div class="col-md-4">
											<select id="uomId" name="uomId" class="standardSelect"
												tabindex="1"
												oninvalid="setCustomValidity('Please select uom')"
												onchange="try{setCustomValidity('')}catch(e){}">

												<option value="0">Select UOM</option>
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
												style="width: 100%;" pattern="[0-9]+(\.[0-9]{0,2})?%?">
										</div>

										<div class="col-md-2">Item Remark</div>

										<div class="col-md-4">
											<input type="text" id="item_remark" name="item_remark"
												class="form-control" style="width: 100%;">
										</div>

									</div>
									<div class="form-group"></div>

									<div class="row">


										<div class="col-md-1"></div>

										<div class="col-md-4">
											<input type="button" value="Add Item" class="btn btn-primary"
												style="align-content: center; width: 226px; margin-left: 80px;"
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
												<th style="text-align: center">UOM</th>
												<th style="text-align: center">Qty</th>
												<th style="text-align: center">Action</th>

											</tr>
										</thead>

									</table>


								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Enquiry Remark</div>

									<div class="col-md-4">
										<input type="text" id="enq_remark" name="enq_remark"
											class="form-control" style="width: 100%;">
									</div>

									<div class="col-md-4">
										<input type="submit" value="Submit">

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
	</script>


	<script type="text/javascript">
		function getData() {
		//alert("in getData()");
			var plantId = document.getElementById("plant_id").value;
			//alert("plant" +plantId);
			
			var valid = true;
			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please Select plant");
			}

			if (valid == true) {

				$
						.getJSON(
								'${getItemsByPlantId}',
								{

									plantId : plantId,
									ajax : 'true',

								},

								function(data) {


									var html ;

									var len = data.length;
									
									//alert(JSON.stringify(data));

									//alert(selLang);
									var html = '<option value="0"  >Select Item</option>';
									for (var i = 0; i < len; i++) {

										 html += '<option value="' + data[i].itemId + '">'
												+data[i].itemName+ '</option>'; 
												
										/* html += '<option value="' + data[i].itemId + '">'
										+ data[i].itemName + '</option>'; */
									}
									html += '</option>';

									$('#item_name').html(html);
									$("#item_name").trigger("chosen:updated");

									//$('#item_name').formcontrol('refresh');

								});

				$
				.getJSON(
						'${getCustByPlantId}',
						{

							plantId : plantId,
							ajax : 'true',

						},

						function(data) {


							var html ;

							var len = data.length;
							
							//alert(JSON.stringify(data));

							//alert(selLang);
							for (var i = 0; i < len; i++) {

								 html += '<option value="' + data[i].custId + '">'
										+data[i].custName+ '</option>'; 
										
								/* html += '<option value="' + data[i].itemId + '">'
								+ data[i].itemName + '</option>'; */
							}
							html += '</option>';

							$('#cust_name').html(html);
							$("#cust_name").trigger("chosen:updated");

							//$('#item_name').formcontrol('refresh');

						});

			}//end of if

		}
	</script>

	<script type="text/javascript">
	
	function setSelectedUom(itemId){
		//alert(" In setSelectedUom() : Item Id " +itemId);
		
		$
		.getJSON(
				'${getItemByItemId}',
				{
					itemId : itemId,
					ajax : 'true',

				},

				function(data) {

					document.getElementById("uomId").value =data.uomId;
					$("#uomId").trigger("chosen:updated");
					
				});
	}
	
	</script>

	<script type="text/javascript">
	
	function addItem(){
		
		alert("in add Item ");
		var itemId=document.getElementById("item_name").value;

		var itemName=$("#item_name option:selected").html();

		var uomId=document.getElementById("uomId").value;

		var uomName=$("#uomId option:selected").html();

		var qty=document.getElementById("qty").value;

		var itemRemark=document.getElementById("item_remark").value;
		
		//alert("Itemm Name  " +itemName + "uomName " +uomName);
//alert("itemId" +itemId + " uomId" +uomId + " qty " +qty + "remark  " +itemRemark);

		$
		.getJSON(
				'${addEnqItem}',
				{

					itemId : itemId,
					itemName : itemName,
					uomId : uomId,
					uomName : uomName,
					qty : qty,
					itemRemark : itemRemark,
					
					ajax : 'true',

				},

				function(data) {
//alert(data);table
  		var dataTable = $('#bootstrap-data-table').DataTable();
					dataTable.clear().draw();
					
	
					$.each(data, function(i, v) {
						//alert(v.itemName)
						
			var str = '<input  type="button" value="callEdit" onclick="callEdit('+v.itemId+')" style="width:30%;"/>&nbsp<input  type="button" value="callDelete" onclick="callDelete('+v.itemId+')" style="width:30%;"/> ';

						dataTable.row.add([ i + 1, v.itemName, v.uomName,v.enqQty,str]).draw();
					});
				});
		
		document.getElementById("qty").value="";
		document.getElementById("item_remark").value="";
		document.getElementById("item_name").options.selectedIndex = "0";
		document.getElementById("uomId").options.selectedIndex = "0";
		$("#uomId").trigger("chosen:updated");
		$("#item_name").trigger("chosen:updated");
	
	}
	
	function callEdit(itemId){
		
		alert("callEdit" +itemId);
		
		document.getElementById("qty").value="";
		document.getElementById("item_remark").value="";
		document.getElementById("item_name").options.selectedIndex = "0";
		document.getElementById("uomId").options.selectedIndex = "0";
		$("#uomId").trigger("chosen:updated");
		$("#item_name").trigger("chosen:updated");
		
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