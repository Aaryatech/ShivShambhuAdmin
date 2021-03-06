<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>



<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>
<c:url var="getPlantDashboardCount" value="/getPlantDashboardCount" />

<link rel="apple-touch-icon" href="apple-icon.png">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/favicon.ico">
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<link
	href="${pageContext.request.contextPath}/resources/assets/css/lib/vector-map/jqvmap.min.css"
	rel="stylesheet">
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

<style type="text/css">
.bg-overlay {
	/*   background: linear-gradient(rgb(186, 178, 132), rgba(155, 208, 52, 0.4)),   url(/ssgs/resources/images/stone.jpeg), url("${pageContext.request.contextPath}/resources/images/stone.jpeg");
  background-repeat: no-repeat; */
	background-size: cover;
	background-position: center center;
	color: #fff;
	height: auto;
}
/* .card{
background-color: transparent;

} */
</style>
</head>


<!-- 
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script> -->


<body onload="setData()" class="bg-overlay">
	<c:url var="getChartData" value="/getGraphDataForDistwiseOrderHistory"></c:url>

	<c:url var="getCatOrdQty" value="/getCatOrdQty"></c:url>

	<c:url var="getCatwiseTrend" value="/getCatwiseTrend"></c:url>



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

					<div class="form-group"></div>

					<div class="row">
						<div class="col-md-2" style="color: black;">From Date*</div>
						<div class="col-md-2">
							<input type="text" autocomplete="off" id="from_date"
								name="from_date" required style="width: 100%;"
								class="form-control" value="${fromDate}"> <span
								class="error" aria-live="polite"></span>
						</div>
						<div class="col-md-2" style="color: black;">To Date*</div>
						<div class="col-md-2">
							<input type=text autocomplete="off" id="to_date" name="to_date"
								style="width: 100%;" class="form-control" value="${toDate}">
							<span class="error" aria-live="polite"></span>
						</div>

						<div class="col-md-1"></div>
						<div class="col-md-1">
							<input type="button" class="btn btn-primary" onclick="showQuot()"
								value="Submit">
						</div>

					</div>

					<div class="form-group"></div>

					<div class="card">
						<div class="card-header">
							<div class="col-md-2">
								<strong style="color: black">Crushing Plant</strong>
							</div>

						</div>
					</div>
					<div class="form-group"></div>

					<div class="col-xs-12 col-sm-12">
						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showEnqBetDate(${dashPlantCount.totalBillAmount})">
							<div class="card text-white bg-flat-color-2"
								style="background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="totalBillAmount">${dashPlantCount.totalBillAmount}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Billing Amount</font>
									</p>

								</div>
							</div>

						</div>



						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showQuotBetDate(${dashPlantCount.contrExpenses})">

							<div class="card text-white bg-flat-color-2"
								style="background: #59c9f2;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="contrExpenses">${dashPlantCount.contrExpenses}</span>
									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Contractor Expenses</font>
									</p>

								</div>
							</div>
						</div>


						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showQuotBetDate1(${dashPlantCount.vehExpenses})">

							<div class="card text-white bg-flat-color-2"
								style="background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;" id="vehExpenses">${dashPlantCount.vehExpenses}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Vehicle Expenses</font>
									</p>

								</div>
							</div>

						</div>

					</div>
					<div class="col-xs-12 col-sm-12">

						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2"
								style="background: #4f90b9;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="paymentRecPaid">${dashPlantCount.paymentRecPaid}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Amount recovery</font>
									</p>

								</div>
							</div>

						</div>






						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-3"
								style="background: #64c2de;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="paymentRecOutstandingPending">${dashPlantCount.paymentRecOutstandingPending}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Outstanding Amount</font>
									</p>

								</div>


							</div>
						</div>

						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showQuotBetDate1(${dashPlantCount.otherExpTotal})">

							<div class="card text-white bg-flat-color-2"
								style="background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="otherExpTotal">${dashPlantCount.otherExpTotal}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Other Expenses</font>
									</p>

								</div>
							</div>

						</div>

					</div>


					<div class="form-group"></div>

					<div class="card">
						<div class="card-header">
							<div class="col-md-2">
								<strong style="color: black">Rubber Mold Plant</strong>
							</div>

						</div>
					</div>
					<div class="form-group"></div>

					<div class="col-xs-12 col-sm-12">
						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showEnqBetDate(${dashPlantCount.totalBillAmtRubber})">
							<div class="card text-white bg-flat-color-2"
								style="background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="totalBillAmtRubber">${dashPlantCount.totalBillAmtRubber}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Billing Amount</font>
									</p>

								</div>
							</div>

						</div>



						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showQuotBetDate(${dashPlantCount.totalIssueExpenses})">

							<div class="card text-white bg-flat-color-2"
								style="background: #59c9f2;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="totalIssueExpenses">${dashPlantCount.totalIssueExpenses}</span>
									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Issue Expenses Rubber Mold</font>
									</p>

								</div>
							</div>
						</div>


						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showQuotBetDate1(${dashPlantCount.otherExpTotalRubber})">

							<div class="card text-white bg-flat-color-2"
								style="background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="otherExpTotalRubber">${dashPlantCount.otherExpTotalRubber}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Other Expenses</font>
									</p>

								</div>
							</div>

						</div>

					</div>
					<div class="col-xs-12 col-sm-12">

						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2"
								style="background: #4f90b9;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="paymentRecPaid">${dashPlantCount.paymentRecPaid}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Amount recovery</font>
									</p>

								</div>
							</div>

						</div>






						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-3"
								style="background: #64c2de;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="paymentRecOutstandingPending">${dashPlantCount.paymentRecOutstandingPending}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Outstanding Amount</font>
									</p>

								</div>


							</div>
						</div>
					</div>


					<div class="form-group"></div>

					<div class="card">
						<div class="card-header">
							<div class="col-md-2">
								<strong style="color: black">RMC Plant</strong>
							</div>

						</div>
					</div>
					<div class="form-group"></div>

					<div class="col-xs-12 col-sm-12">
						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showEnqBetDate(${dashPlantCount.totalBillAmtRmc})">
							<div class="card text-white bg-flat-color-2"
								style="background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="totalBillAmtRmc">${dashPlantCount.totalBillAmtRmc}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Billing Amount</font>
									</p>

								</div>
							</div>

						</div>



						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2"
								style="background: #59c9f2;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="totalIssueExpRmc">${dashPlantCount.totalIssueExpRmc}</span>
									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Issue Expenses</font>
									</p>

								</div>
							</div>
						</div>


						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;"
							onclick="showQuotBetDate1(${dashPlantCount.vehExpenses})">

							<div class="card text-white bg-flat-color-2"
								style="background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="otherExpTotalRmc">${dashPlantCount.otherExpTotalRmc}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Other Expenses</font>
									</p>

								</div>
							</div>

						</div>

					</div>
					<div class="col-xs-12 col-sm-12">

						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2"
								style="background: #4f90b9;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="paymentRecPaid">${dashPlantCount.paymentRecPaid}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Amount recovery</font>
									</p>

								</div>
							</div>

						</div>






						<div class="col-sm-6 col-lg-4"
							style="cursor: pointer; max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-3"
								style="background: #64c2de;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;"
											id="paymentRecOutstandingPending">${dashPlantCount.paymentRecOutstandingPending}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
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
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/plugins.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>


	<script
		src="${pageContext.request.contextPath}/resources/assets/js/dashboard.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/widgets.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/chosen/chosen.jquery.min.js"></script>


	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<!-- 	<script>
		jQuery(document).ready(function() {
			jQuery(".standardSelect").chosen({
				disable_search_threshold : 1,
				no_results_text : "Oops, nothing found!",
				width : "100%"
			});
		});
	</script> 
 -->
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

			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;


			var valid = true;

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
								'${getPlantDashboardCount}',
								{

									
									fromDate : fromDate,
									toDate : toDate,
									ajax : 'true',

								},

								function(data) {
									//alert("hi");

									document.getElementById("totalBillAmount").innerText = data.totalBillAmount.toFixed(2);
									document.getElementById("contrExpenses").innerText = data.contrExpenses.toFixed(2);
									document
											.getElementById("vehExpenses").innerText = data.vehExpenses.toFixed(2);
									document.getElementById("totalBillAmtRubber").innerText = data.totalBillAmtRubber.toFixed(2);
									document.getElementById("totalIssueExpenses").innerText = data.totalIssueExpenses.toFixed(2);
									document.getElementById("totalBillAmtRmc").innerText = data.totalBillAmtRmc.toFixed(2);
									document.getElementById("totalIssueExpRmc").innerText = data.totalIssueExpRmc.toFixed(2);
									document
											.getElementById("paymentRecPaid").innerText = data.paymentRecPaid.toFixed(2);
									document.getElementById("paymentRecOutstandingPending").innerText = data.paymentRecOutstandingPending.toFixed(2);
									document
											.getElementById("otherExpTotal").innerText = data.otherExpTotal.toFixed(2);
									document
									.getElementById("otherExpTotalRubber").innerText = data.otherExpTotalRubber.toFixed(2);
									document
									.getElementById("otherExpTotalRmc").innerText = data.otherExpTotalRmc.toFixed(2);

								});

			}//end of if valid ==true

		}
	</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
	<script>
	
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
						'${pageContext.request.contextPath}/showDashboardEnqList/'
								+ fromDate + '/' + toDate + '/' + plantId,
						"_self");
			}
		}
	</script>

	<script type="text/javascript">
		function showQuotBetDate(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;
			if (count > 0) {
			window.open(
					'${pageContext.request.contextPath}/showDashboardQuotList/'
							+ fromDate + '/' + toDate + '/' + plantId, "_self");
			}

		}
	</script>

	<script type="text/javascript">
		function showQuotBetDate1(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;
			if (count > 0) {
			window.open(
					'${pageContext.request.contextPath}/showDashboardQuotList1/'
							+ fromDate + '/' + toDate + '/' + plantId, "_self");
			}

		}
	</script>



	<script type="text/javascript">
		function showPoBetDate(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;
			if (count > 0) {
			window.open(
					'${pageContext.request.contextPath}/showDashboardPOList/'
							+ fromDate + '/' + toDate + '/' + plantId, "_self");
			}

		}
	</script>


	<script type="text/javascript">
		function showPoBetDate1(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;
			if (count > 0) {
			window.open(
					'${pageContext.request.contextPath}/showDashboardPOList1/'
							+ fromDate + '/' + toDate + '/' + plantId, "_self");
			}

		}
	</script>


	<script type="text/javascript">
		function showOrderBetDate(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;
			if (count > 0) {
			window.open(
					'${pageContext.request.contextPath}/showDashboardOrderList/'
							+ fromDate + '/' + toDate + '/' + plantId, "_self");
			}

		}
	</script>


	<script type="text/javascript">
		function showBillBetDate(count) {
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;
			if (count > 0) {
			window.open(
					'${pageContext.request.contextPath}/showDashboardBillList/'
							+ fromDate + '/' + toDate + '/' + plantId, "_self");
			}
		}
	</script>
</body>
</html>