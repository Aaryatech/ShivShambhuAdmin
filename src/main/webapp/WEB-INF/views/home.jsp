<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getDashboardCount" value="/getDashboardCount" />


<link rel="apple-touch-icon" href="apple-icon.png">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/favicon.ico">

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
	href="${pageContext.request.contextPath}/resources/assets/scss/style.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/menu.css">

<link
	href="${pageContext.request.contextPath}/resources/assets/css/lib/vector-map/jqvmap.min.css"
	rel="stylesheet">

<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800'
	rel='stylesheet' type='text/css'>


<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>


</head>


<!-- 
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script> -->


<body onload="setData()">
	<c:url var="getChartData" value="/getGraphDataForDistwiseOrderHistory"></c:url>

	<c:url var="getCatOrdQty" value="/getCatOrdQty"></c:url>

	<c:url var="getCatwiseTrend" value="/getCatwiseTrend"></c:url>



	<!-- Left Panel -->
	<jsp:include page="/WEB-INF/views/common/left.jsp"></jsp:include>
	<!-- Left Panel -->


	<%-- <!-- Header-->
	<jsp:include page="/WEB-INF/views/common/right.jsp"></jsp:include>
	<!-- Header--> --%>


	<div class="content mt-3">
		<div class="animated fadeIn">

			<div class="row">

				<div class="col-xs-12 col-sm-12">




					<div class="form-group"></div>

					<div class="row">
						<div class="col-md-2">From Date*</div>
						<div class="col-md-2">
							<input type="text" autocomplete="off" id="from_date"
								name="from_date" required style="width: 100%;"
								class="form-control" value="${fromDate}"> <span
								class="error" aria-live="polite"></span>
						</div>
						<div class="col-md-1">To Date*</div>
						<div class="col-md-2">
							<input type="text" autocomplete="off" id="to_date" name="to_date"
								style="width: 100%;" class="form-control" value="${toDate}">
							<span class="error" aria-live="polite"></span>
						</div>

						<div class="col-md-2">Select Plant*</div>
						<div class="col-md-2">
							<select id="plantId" name="plantId" class="standardSelect"
								tabindex="1" required
								oninvalid="setCustomValidity('Please select plant')">
								<option value="0">All</option>
								<c:forEach items="${plantList}" var="plant">
									<option value="${plant.plantId}">${plant.plantName}</option>
								</c:forEach>

							</select>
						</div>

						<div class="col-md-1">
							<input type="button" class="btn btn-primary" onclick="showQuot()"
								value="Submit">
						</div>

					</div>
					<div class="form-group"></div>

					<div class="col-xs-12 col-sm-12">




						<div class="col-sm-6 col-lg-4" style="cursor: pointer;"
							onclick="showEnqBetDate(${dashBoard.totalEnq})">
							<div class="card text-white bg-flat-color-2">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;" id="totalEnq">${dashBoard.totalEnq}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total No of Enquiry</font>
									</p>

								</div>
							</div>

						</div>



						<div class="col-sm-6 col-lg-4" style="cursor: pointer;"
							onclick="showQuotBetDate(${dashBoard.totalQuotPending})">

							<div class="card text-white bg-flat-color-2">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="totalQuotPending">${dashBoard.totalQuotPending}</span>
									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Pending Quotations</font>
									</p>

								</div>
							</div>
						</div>


						<div class="col-sm-6 col-lg-4" style="cursor: pointer;"
							onclick="showQuotBetDate1(${dashBoard.totalQuotPending})">

							<div class="card text-white bg-flat-color-2">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="totalQuotGenerated">${dashBoard.totalQuotGenerated}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Generated Quotations</font>
									</p>

								</div>
							</div>

						</div>

					</div>

					<div class="col-xs-12 col-sm-12">

						<div class="col-sm-6 col-lg-4" style="cursor: pointer;">
							<div class="card text-white bg-flat-color-3">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="totalPoPending">${dashBoard.totalPoPending}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total Pending Customer PO</font>
									</p>


								</div>
							</div>

						</div>

						<div class="col-sm-6 col-lg-4" style="cursor: pointer;">
							<div class="card text-white bg-flat-color-3">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="totalPoPending">${dashBoard.totalPoPending}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total Pending Customer PO</font>
									</p>


								</div>
							</div>

						</div>






						<div class="col-sm-6 col-lg-4" style="cursor: pointer;">

							<div class="card text-white bg-flat-color-3">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="totalOrderAmount">${dashBoard.totalOrderAmount}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total Order Amount</font>
									</p>

								</div>
							</div>

						</div>



						<div class="col-sm-6 col-lg-4" style="cursor: pointer;">

							<div class="card text-white bg-flat-color-2">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="totalBillAmount">${dashBoard.totalBillAmount}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total Bill Amount</font>
									</p>

								</div>
							</div>

						</div>

						<div class="col-sm-6 col-lg-4" style="cursor: pointer;">

							<div class="card text-white bg-flat-color-2">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="totalTaxBillAmt">${dashBoard.totalTaxBillAmt}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total Tax Bill Amount</font>
									</p>

								</div>
							</div>

						</div>




						<div class="col-sm-6 col-lg-4" style="cursor: pointer;">

							<div class="card text-white bg-flat-color-2">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="totalTaxableBillAmt">${dashBoard.totalTaxableBillAmt}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total Taxable Bill Amount</font>
									</p>

								</div>
							</div>

						</div>





						<div class="col-sm-6 col-lg-4" style="cursor: pointer;">

							<div class="card text-white bg-flat-color-3">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="paymentRecPaid">${dashBoard.paymentRecPaid}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total Amount recovery</font>
									</p>

								</div>
							</div>

						</div>






						<div class="col-sm-6 col-lg-4" style="cursor: pointer;">

							<div class="card text-white bg-flat-color-3">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 50px;"
											id="paymentRecOutstandingPending">${dashBoard.paymentRecOutstandingPending}</span>

									</h4>
									<p style="font-size: 18px; font-weight: bold; color: white;">
										<font color="white">Total Outstanding Amount</font>
									</p>

								</div>
							</div>

						</div>







					</div>




				</div>
			</div>
		</div>
	</div>






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


	<script type="text/javascript">
		// onclick of submit to search order 
		function showQuot() {

			alert("Hi View Orders  ");

			var plantId = document.getElementById("plantId").value;
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;

			alert(plantId);

			var valid = true;

			var plantId = document.getElementById("plantId").value;

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
								'${getDashboardCount}',
								{

									plantId : plantId,
									fromDate : fromDate,
									toDate : toDate,
									ajax : 'true',

								},

								function(data) {
									alert("hi");

									document.getElementById("totalEnq").innerText = data.totalEnq;
									document.getElementById("totalQuotPending").innerText = data.totalQuotPending;
									document
											.getElementById("totalQuotGenerated").innerText = data.totalQuotGenerated;
									document.getElementById("totalPoPending").innerText = data.totalPoPending;
									document.getElementById("totalOrderAmount").innerText = data.totalOrderAmount;
									document.getElementById("totalBillAmount").innerText = data.totalBillAmount;
									document.getElementById("totalTaxBillAmt").innerText = data.totalTaxBillAmt;
									document
											.getElementById("totalTaxableBillAmt").innerText = data.totalTaxableBillAmt;
									document.getElementById("paymentRecPaid").innerText = data.paymentRecPaid;
									document
											.getElementById("paymentRecOutstandingPending").innerText = data.paymentRecOutstandingPending;

								});

			}//end of if valid ==true

		}
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
		function showEnqBetDate(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;

			if (count > 0) {

				window.open(
						'${pageContext.request.contextPath}/showDashboardEnqList/'+ fromDate
						+ '/' + toDate + '/' + plantId,
						"_self");
			}
		}
	</script>

	<script type="text/javascript">
		function showQuotBetDate(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;

			

				window.open(
						'${pageContext.request.contextPath}/showDashboardQuotList/'+ fromDate
						+ '/' + toDate + '/' + plantId,
						"_self");
		
		}
	</script>

	<script type="text/javascript">
		function showQuotBetDate1(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;

			

				window.open(
						'${pageContext.request.contextPath}/showDashboardQuotList/'+ fromDate
						+ '/' + toDate + '/' + plantId,
						"_self");
		
		}
	</script>

</body>
</html>
