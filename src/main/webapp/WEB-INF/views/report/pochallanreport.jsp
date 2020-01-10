<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>


<c:url var="getPoInfoByCustList" value="/getPoInfoByCustList" />
<c:url var="getCustByPlantId" value="/getCustByPlantId" />


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

.buttonload {
	background-color: white; /* Green background */
	border: none; /* Remove borders */
	color: #ec268f; /* White text */
	padding: 12px 15px; /* Some padding */
	font-size: 13px; /* Set a font-size */
	display: none;
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
							<div class="col-md-4">
								<strong>${title}</strong>
							</div>
						</div>

						<div class="card-body card-block">

							<div class="form-group"></div>

							<div class="row">


								<div class="col-md-2">Select Plant</div>

								<div class="col-md-4">
									<select id="plantId" name="plantId" class="standardSelect"
										tabindex="1" required
										oninvalid="setCustomValidity('Please select plant name')"
										onchange="getData()">
										<option value="">Select Plant</option>
										<c:forEach items="${plantList}" var="plant">
											<c:choose>
												<c:when test="${plant.plantId==plantId1}">
													<option value="${plant.plantId}" selected>${plant.plantName}</option>
												</c:when>
												<c:otherwise>
													<option value="${plant.plantId}">${plant.plantName}
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-2">Select Customer</div>
								<div class="col-md-4">
									<select id="custId" name="custId" class="standardSelect"
										tabindex="1" required
										oninvalid="setCustomValidity('Please select Challan')">
										<option value="">Select Customer</option>

										<%-- <c:forEach items="${custList}" var="cust">
											<option value="${cust.custId}">${cust.custName}</option>
										</c:forEach> --%>


									</select>
								</div>

							</div>

							<div class="form-group"></div>

							<div class="row">
								<div class="col-md-6"></div>

								<div class="col-md-2">
									<input type="button" class="btn btn-primary"
										onclick="showPoInfo()" value="Submit">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6"></div>
								<div class="buttonload" id="loader">
										<i class="fa fa-spinner fa-spin"></i>Loading
									</div>
								</div>



							<div class="form-group"></div>

							<div class="card-body card-block">
								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">
									<thead>
										<tr>
											<th style="text-align: center; width: 5%;">Sr.</th>
											<th style="text-align: center">Po No.</th>
											<th style="text-align: center">PO Date</th>
											<th style="text-align: center">Item Name</th>
											<th style="text-align: center">PO Qty.</th>
											<th style="text-align: center">PO Challan Qty.</th>
											<th style="text-align: center">Action</th>

										</tr>
									</thead>

								</table>
								<div class="col-md-2"></div>

								<div class="col-md-3">

									<button type="button" class="btn btn-primary"
										onclick="exportToExcel();" disabled="disabled" id="expExcel"
										style="align-content: center; width: 200px; margin-left: 80px;">
										Export To Excel</button>
								</div>


								<div class="col-md-3">

									<button type="button" class="btn btn-primary"
										onclick="genPdf()" disabled="disabled" id="PDFButton"
										style="align-content: center; width: 100px; margin-left: 80px;">
										PDF</button>
								</div>
								&nbsp;

							</div>


						</div>


					</div>
				</div>

			</div>
		</div>

	</div>
	<!-- .animated -->
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




	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=from_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

			$('input[id$=to_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>





	<script type="text/javascript">
		// onclick of submit to search order 
		function showPoInfo() {

		//alert("Hi View Orders  ");

			var custId = document.getElementById("custId").value;

			var values = $('#custId').val();

			var valid = true;

			if (custId == null || custId == "") {
				valid = false;
				alert("Please select company");
			}

			var plantId = document.getElementById("plantId").value;
			//alert("plant id are" + plantId);

			//alert("plantId" + plantId);
			var valid = true;
			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please Select Plant");

				var dataTable = $('#bootstrap-data-table').DataTable();
				dataTable.clear().draw();

			} else if (plantId < 0) {
				valid = false;

			}

			if (valid == true) {
				$('#loader').show();
				$
						.getJSON(
								'${getPoInfoByCustList}',
								{
									values : values,
									plantId : plantId,
									values : JSON.stringify(values),
									ajax : 'true',

								},

								function(data) {
									$('#loader').hide();
									document.getElementById("expExcel").disabled = false;
									document.getElementById("PDFButton").disabled = false;

									if (data == "") {
										alert("No records found !!");
										document.getElementById("expExcel").disabled = true;
										document.getElementById("PDFButton").disabled = true;

									}

									var dataTable = $('#bootstrap-data-table')
											.DataTable();
									dataTable.clear().draw();

									$
											.each(
													data,
													function(i, v) {

														var acButton = '<a href="#" class="action_btn" onclick="getChallan('
																+ v.orderDetailId+',\''+v.poNo+'\',\''+v.poDate+'\',\''+v.itemDesc+
																'\')" style="color:black"><i class="fa fa-list" title="Challan Detail"></i></a>'

														dataTable.row
																.add(
																		[
																				i + 1,
																				v.poNo,
																				v.poDate,
																				v.itemDesc,
																				v.poQty
																						.toFixed(2),
																				v.poChallanQty
																						.toFixed(2),																				
																				acButton

																		])
																.draw();
													});

								});

			}//end of if valid ==true

		}
		function getChallan(challanId, poNo, poDate, itemDesc) {
			
			var poNoItm = poDate+','+itemDesc;
			window
					.open("${pageContext.request.contextPath}/getChallanDetails/"
							+ challanId+'/' + poNo+'/' + poNoItm);

		}
	</script>


	<script type="text/javascript">
		// on plant change function 
		function getData() {
			var plantId = document.getElementById("plantId").value;
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

					$('#custId').html(html);
					$("#custId").trigger("chosen:updated");
					/* getCustInfo();

					$('#po_id').html("-1");
					$("#po_id").trigger("chosen:updated");
					 */
					var dataTable = $('#bootstrap-data-table').DataTable();
					dataTable.clear().draw();

				});
			}//end of if

		}
	</script>



	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#bootstrap-data-table').DataTable();

							$("#selAll")
									.click(
											function() {
												$(
														'#bootstrap-data-table tbody input[type="checkbox"]')
														.prop('checked',
																this.checked);
											});
						});
	</script>


	<script type="text/javascript">
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {

			window.open('${pageContext.request.contextPath}/showPOChallanPdf');
			document.getElementById("expExcel").disabled = true;

		}
	</script>


</body>
</html>