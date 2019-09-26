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
<c:url var="getPayRecBetDateAndCat" value="/getPayRecBetDateAndCat" />
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

.new {
	background-color: red;
}

table-striped tbody tr:nth-of-type(2n+1) {
	background-color: rgba(255, 255, 255, 0.05);
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

							<form
								action="${pageContext.request.contextPath}/fillInfoForMultiplePayment"
								method="post">

								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Select Plant</div>

									<div class="col-md-2">
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
									<div class="col-md-2">
										<select id="cust_name" name="cust_name" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')"
											onchange="getCustInfo()">
											<option value="0">All</option>
										</select>
									</div>

									<div class="col-md-2">Select Category</div>

									<div class="col-md-2">
										<select id="cust_cate" name="cust_cate" class="standardSelect"
											tabindex="1"
											oninvalid="setCustomValidity('Please enter customer category')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="0">All</option>
											<c:forEach items="${settingList}" var="custCate">
												<option value="${custCate.settingId}">${custCate.settingValue}</option>
											</c:forEach>
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
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Selected Amount</div>
									<div class="col-md-2">
										<input type="text" id="selectedAmt" name="selectedAmt"
											class="form-control" value="0" readonly
											style="text-align: right;">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="card-body card-block">
									<table id="bootstrap-data-table" class="table  table-bordered"
										data-page-length='-1'>
										<thead>
											<tr>
												<th style="text-align: center; width: 5%;"></th>
												<th style="text-align: center; width: 5%;">Sr.</th>
												<th style="text-align: center; width: 10%;">Customer
													Name</th>
												<th style="text-align: center; width: 5%;">Class</th>
												<th style="text-align: center; width: 10%;">Bill No</th>
												<th style="text-align: center; width: 10%;">Bill Date</th>
												<th style="text-align: center; width: 10%;">Billing
													Amount</th>
												<th style="text-align: center; width: 10%;">Due Date</th>
												<th style="text-align: center; width: 5%;">Days</th>
												<th style="text-align: center; width: 10%;">Received
													Amt</th>
												<th style="text-align: center; width: 10%;">Pending Amt</th>
												<th style="text-align: center; width: 10%;">Status</th>
												<th style="text-align: center; width: 5%;">Action</th>
											</tr>
										</thead>

										<tbody></tbody>

										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td><b>Total</b></td>
											<td><input type="text" id="totalBillAmt"
												style="width: 80px;" name="totalBillAmt" value="0" readonly></td>
											<td></td>
											<td></td>


											<td><input type="text" id="totalReceivedAmt"
												style="width: 80px;" name="totalReceivedAmt" value="0"
												readonly></td>
											<td><input type="text" id="totalPendingAmt"
												style="width: 80px;" name="totalPendingAmt" value="0"
												readonly></td>
											<td></td>
											<td></td>
										</tr>

									</table>

									<div class="col-md-2"></div>

									<div class="col-md-3"></div>


									<div class="col-md-3">

										<input type="submit" class="btn btn-primary"
											style="align-content: center; margin-left: 80px;"
											value="Make Common Payment">

									</div>
									&nbsp;

								</div>

							</form>
						</div>


					</div>
				</div>

			</div>
		</div>
		<!-- disabled="disabled" -->
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
		// onclick of submit to search order 
		function showQuot() {

			//alert("Hi View reports  ");

			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plant_id").value;
			var custId = document.getElementById("cust_name").value;
			var category = document.getElementById("cust_cate").value;
			/* 	alert(fromDate);
				alert(toDate);
				alert(plantId);
				alert(custId);
				alert(category); */

			var valid = true;

			if (fromDate == null || fromDate == "") {
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
								'${getPayRecBetDateAndCat}',
								{
									fromDate : fromDate,
									toDate : toDate,
									plantId : plantId,
									custId : custId,
									category : category,
									ajax : 'true',
								},

								function(data) {

									//alert("Order Data " + JSON.stringify(data));

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

														var chBox = '<input type="checkbox" id="payId'
																+ v.payHeadId
																+ '" name="payId" value="'
																+ v.payHeadId
																+ '" onclick="calSum('
																+ v.pendingAmt
																+ ','
																+ v.payHeadId
																+ ')"> &nbsp;';

														var status1;
														if (v.status == 0) {
															status1 = "Pending";
														} else if (v.status == 1) {
															status1 = "Payment Done";
														}

														var acButton = '<a href="#" class="action_btn" onclick="callEdit('
																+ v.payHeadId
																+ ','
																+ i
																+ ')" style="color:black"><i class="fa fa-edit"  title="Edit"></i></a>'

														totalBillAmt = totalBillAmt
																+ v.billTotal;

														totalReceivedAmt = totalReceivedAmt
																+ v.paidAmt;
														totalPendingAmt = totalPendingAmt
																+ v.pendingAmt;

														var index = i + 1;
														if (v.days > 0) {
															dataTable.row
																	.add(
																			[
																					chBox,
																					index,
																					v.custName,
																					v.custClass,
																					v.billNo,
																					v.billDate,
																					v.billTotal
																							.toFixed(2),
																					v.creditDate2,
																					v.days,
																					v.paidAmt
																							.toFixed(2),
																					v.pendingAmt
																							.toFixed(2),
																					status1,
																					acButton ])
																	.draw()
																	.nodes()
																	.to$()
																	.addClass(
																			'new');

														} else {

															dataTable.row
																	.add(
																			[
																					chBox,
																					index,
																					v.custName,
																					v.custClass,
																					v.billNo,
																					v.billDate,
																					v.billTotal
																							.toFixed(2),
																					v.creditDate2,
																					v.days,
																					v.paidAmt
																							.toFixed(2),
																					v.pendingAmt
																							.toFixed(2),
																					status1,
																					acButton ])
																	.draw();

														}

													});

									document.getElementById("totalBillAmt").value = totalBillAmt;
									document.getElementById("totalReceivedAmt").value = totalReceivedAmt;
									document.getElementById("totalPendingAmt").value = totalPendingAmt;

								});

			}//end of if valid ==true

		}

		function callEdit(payHeadId) {

			window.open("${pageContext.request.contextPath}/editPayRec/"
					+ payHeadId);

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
			document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function calSum(ammnt, payId) {
			ammnt = parseFloat(ammnt);
			var selectedAmt = parseFloat(document.getElementById("selectedAmt").value);

			if (document.getElementById("payId" + payId).checked == true) {
				selectedAmt = selectedAmt + ammnt;
			} else {
				selectedAmt = selectedAmt - ammnt;
			}
			document.getElementById("selectedAmt").value = selectedAmt
					.toFixed(2);
		}
	</script>
	<script type="text/javascript">
		function genPdf() {
			//alert("hiii");

			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			//alert("from date" + fromDate);
			//alert("to date" + toDate);

			window.open('${pageContext.request.contextPath}/showPayRecPdfCat/'
					+ fromDate + '/' + toDate);

			document.getElementById("expExcel").disabled = true;

		}
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

</body>
</html>