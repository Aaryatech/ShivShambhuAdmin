<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getCustByPlantId" value="/getCustByPlantId" />
<c:url var="getPayRecoveryDoneBetDateCustWise"
	value="/getPayRecoveryDoneBetDateCustWise" />
<c:url var="getPayRecoveryBetDateVyPlantId"
	value="/getPayRecoveryBetDateVyPlantId" />


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
<body onload="getData1()">


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
							<div class="col-md-5"></div>

						</div>

						<div class="card-body card-block">



							<div class="form-group"></div>

							<div class="row">

								<div class="col-md-2">Select Plant</div>

								<div class="col-md-4">
									<select id="plant_id" name="plant_id" class="standardSelect"
										tabindex="1" required
										oninvalid="setCustomValidity('Please select plant name')"
										onchange="getData()">
										<c:if test="${sessionScope.plantId==0}">
											<option value="0">All</option>
										</c:if>
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
								<div class="col-md-2">Select Customer</div>
								<div class="col-md-4">
									<select id="cust_name" name="cust_name" class="standardSelect"
										tabindex="1" required
										oninvalid="setCustomValidity('Please select customer')"
										onchange="getCustInfo()">
										<option value="0">All</option>
									</select>
								</div>
							</div>


							<div class="form-group"></div>
							<div class="row">
								<div class="col-md-2">From Date</div>
								<div class="col-md-2">
									<input type="text" autocomplete="off" id="from_date"
										name="from_date" required style="width: 100%;"
										class="form-control" value="${fromDate}"> <span
										class="error" aria-live="polite"></span>
								</div>
								<div class="col-md-2">To Date</div>
								<div class="col-md-2">
									<input type="text" autocomplete="off" id="to_date"
										name="to_date" style="width: 100%;" class="form-control"
										value="${toDate}"> <span class="error"
										aria-live="polite"></span>
								</div>


								<div class="col-md-1"></div>

								<div class="col-md-2">
									<input type="button" class="btn btn-primary"
										onclick="showQuot()" value="Submit">
								</div>
							</div>
							<!-- 
							<div class="form-group"></div>


							<div class="row">
								<div class="col-md-6"></div>

							</div> -->

							<div class="form-group"></div>


							<div class="card-body card-block">
								<table id="bootstrap-data-table"
									class="table table-striped table-bordered"
									data-page-length='-1'>
									<thead>
										<tr>
											<!-- <th class="check" style="text-align: center; width: 5%;"> -->
											<th style="text-align: center; width: 5%;">Sr.</th>
											<th style="text-align: center">Customer Name</th>
											<th style="text-align: center">Mobile No.</th>
											<th style="text-align: center">Billing Amount</th>
											<th style="text-align: center">Received Amount</th>
											<th style="text-align: center">Pending Amount</th>
											<th style="text-align: center">Action</th>

										</tr>
									</thead>
									<tbody></tbody>

									<tr>
										<td></td>
										<td></td>


										<td><b>Total</b></td>
										<td><input type="text" id="totalBillAmt"
											name="totalBillAmt" value="0" readonly></td>

										<td><input type="text" id="totalReceivedAmt"
											name="totalReceivedAmt" value="0" readonly></td>
										<td><input type="text" id="totalPendingAmt"
											name="totalPendingAmt" value="0" readonly></td>
										<td></td>

									</tr>


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

							<!-- <input type="submit" class="btn btn-primary" value="Delete"
									id="deleteId"
									onClick="var checkedVals = $('.chk:checkbox:checked').map(function() { return this.value;}).get();checkedVals=checkedVals.join(',');if(checkedVals==''){alert('No Rows Selected');return false;	}else{   return confirm('Are you sure want to delete record');}"
									style="align-content: center; width: 113px; margin-left: 40px;"> -->


						</div>
					</div>

				</div>
			</div>
			<!-- disabled="disabled" -->
		</div>
	</div>
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
		// on plant change function 
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
					var html = '<option selected value="0"  >All</option>';

					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].custId + '">'
								+ data[i].custName + '</option>';

					}
					html += '</option>';

					$('#cust_name').html(html);
					$("#cust_name").trigger("chosen:updated");
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
		// on plant change function 
		function getData1() {
			var plantId = document.getElementById("plant_id").value;
			var valid = true;

			if (plantId == null || plantId == "") {
				valid = false;

			}

			if (valid == true) {

				$.getJSON('${getCustByPlantId}', {
					plantId : plantId,
					ajax : 'true',
				},

				function(data) {
					var html;
					var len = data.length;
					var html = '<option selected value="0"  >All</option>';

					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].custId + '">'
								+ data[i].custName + '</option>';

					}
					html += '</option>';

					$('#cust_name').html(html);
					$("#cust_name").trigger("chosen:updated");
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
		// onclick of submit to search order 
		function showQuot() {

			//alert("Hi View Orders  ");

			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plant_id").value;
			var custId = document.getElementById("cust_name").value;

			var valid = true;

			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please Select Plant");

				var dataTable = $('#bootstrap-data-table').DataTable();
				dataTable.clear().draw();

			} else if (plantId < 0) {
				valid = false;

			}

			else if (fromDate == null || fromDate == "") {
				valid = false;
				alert("Please select from date");
			}

			else if (toDate == null || toDate == "") {
				valid = false;
				alert("Please select to date");
			}

			if (fromDate > toDate) {
				valid = false;
				alert("from date greater than todate ");
			}
			if (valid == true) {

				$
						.getJSON(
								'${getPayRecoveryDoneBetDateCustWise}',
								{
									fromDate : fromDate,
									toDate : toDate,
									plantId : plantId,
									custId : custId,
									ajax : 'true',
								},

								function(data) {

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

									var totalBillAmt = 0;
									var totalReceivedAmt = 0;
									var totalPendingAmt = 0;

									$
											.each(
													data,
													function(i, v) {
														var chBox;

														totalBillAmt = totalBillAmt
																+ v.billTotal;

														totalReceivedAmt = totalReceivedAmt
																+ v.paidAmt;
														totalPendingAmt = totalPendingAmt
																+ v.pendingAmt;

														var acButton = '<a href="#" class="action_btn" onclick="callDateDetail('
																+ v.custId
																+ ')" style="color:black"><i class="fa fa-list" title="Detail"></i></a>'

														chBox = '<input  type="checkbox" class="chk" name="payHeadIds" id='+v.payHeadId+' class="check"  value='+v.payHeadId+'>'

														dataTable.row
																.add(
																		[

																				i + 1,
																				v.custName,
																				v.custMobNo,
																				v.billTotal
																						.toFixed(2),
																				v.paidAmt
																						.toFixed(2),
																				v.pendingAmt
																						.toFixed(2),
																				acButton ])

																.draw();

													});
									document.getElementById("totalBillAmt").value = totalBillAmt;
									document.getElementById("totalReceivedAmt").value = totalReceivedAmt;
									document.getElementById("totalPendingAmt").value = totalPendingAmt;

								});

			}//end of if valid ==true

		}
	</script>

	<script type="text/javascript">
		function callDateDetail(custId) {

			//alert("hello..");
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;

			window.open("${pageContext.request.contextPath}/custPayRec/"
					+ custId + '/' + fromDate + '/' + toDate + '/');

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
		function hideDiv(type) {

			if (type == 0) {

				document.getElementById("hide_div").style = "display:none"
			} else {
				document.getElementById("hide_div").style = "visible"

			}
		}
	</script>

	<script type="text/javascript">
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			//document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			//alert("hiii");
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;

			window
					.open('${pageContext.request.contextPath}/showPayRecDoneCustWisePdf/'
							+ fromDate + '/' + toDate);
			//document.getElementById("expExcel").disabled = true;

		}
	</script>
</body>
</html>