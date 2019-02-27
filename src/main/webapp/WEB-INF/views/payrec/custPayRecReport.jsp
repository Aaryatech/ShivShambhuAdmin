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
<c:url var="getPayRecoveryDoneBetDate"
	value="/getPayRecoveryDoneBetDate" />
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
							<div class="col-md-5"></div>

						</div>

						<div class="card-body card-block">

							<div class="form-group"></div>
							<div class="row">
								<div class="col-md-2">Customer Name*</div>
								<div class="col-md-2">
									<input type="text" id="custName" name="custName" maxlength="60"
										value="${editCust.custName}" class="form-control" readonly
										onblur="getUomNameCheck()" autocomplete="off"
										oninvalid="setCustomValidity('Please enter correct Contractor Name')"
										onchange="try{setCustomValidity('')}catch(e){}" required
										style="width: 100%;">
								</div>


								<div class="col-md-2">From Date</div>
								<div class="col-md-2">
									<input type="text" autocomplete="off" id="from_date" readonly
										name="from_date" required style="width: 100%;"
										class="form-control" value="${fromDate}"> <span
										class="error" aria-live="polite"></span>
								</div>
								<div class="col-md-2">To Date</div>
								<div class="col-md-2">
									<input type="text" autocomplete="off" id="to_date" readonly
										name="to_date" style="width: 100%;" class="form-control"
										value="${toDate}"> <span class="error"
										aria-live="polite"></span>
								</div>

							</div>

							<div class="form-group"></div>


							<!-- <div class="row">
								<div class="col-md-6"></div>
								<div class="col-md-2">
									<input type="button" class="btn btn-primary"
										onclick="showQuot()" value="Submit">
								</div>
							</div>
							
 -->


							<div class="card-body card-block">
								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">
									<thead>
										<tr>
											<!-- <th class="check" style="text-align: center; width: 5%;"> -->
											<th style="text-align: center; width: 5%;">Sr.</th>
											<th style="text-align: center">Customer Name</th>
											<th style="text-align: center">Bill No</th>
											<th style="text-align: center">Bill Date</th>
											<th style="text-align: center">Billing Amount</th>
											<th style="text-align: center">Received Amount</th>
											<th style="text-align: center">Pending Amount</th>

										</tr>


									</thead>


									<tbody>
										<c:forEach items="${recList}" var="rec" varStatus="count">
											<tr>

												<td style="text-align: center">${count.index+1}</td>

												<td style="text-align: left"><c:out
														value="${rec.custName}" /></td>


												<td style="text-align: left"><c:out
														value="${rec.billNo}" /></td>


												<td style="text-align: left"><c:out
														value="${rec.billDate}" /></td>



												<td style="text-align: right"><fmt:formatNumber
														type="number" maxFractionDigits="2"
														value="${rec.billTotal}" /></td>


												<td style="text-align: right"><fmt:formatNumber
														type="number" maxFractionDigits="2" value="${rec.paidAmt}" /></td>

												<td style="text-align: right"><fmt:formatNumber
														type="number" maxFractionDigits="2"
														value="${rec.pendingAmt}" /></td>





											</tr>
										</c:forEach>

									</tbody>

									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td><b>Total</b></td>
										<td style="text-align: right"><fmt:formatNumber
												type="number" maxFractionDigits="2" value="${totalBillAmt}" /></td>
										<td style="text-align: right"><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${totalReceivedAmt}" /></td>
										<td style="text-align: right"><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${totalPendingAmt}" /></td>
									</tr>

								</table>




								<div class="col-md-2"></div>

								<div class="col-md-3">

									<button type="button" class="btn btn-primary"
										onclick="exportToExcel();" id="expExcel"
										style="align-content: center; width: 200px; margin-left: 80px;">
										Export To Excel</button>
								</div>


								<div class="col-md-3">

									<button type="button" class="btn btn-primary"
										onclick="genPdf()" id="PDFButton"
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
		<!-- disabled="disabled" -->
	</div>



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
			var custName = document.getElementById("custName").value;

			/* alert(fromDate);
			alert(toDate);
			alert(custName); */

			window
					.open('${pageContext.request.contextPath}/showPayRecDoneCustPdf/'
							+ fromDate + '/' + toDate + '/' + custName);
			//document.getElementById("expExcel").disabled = true;

		}
	</script>

</body>
</html>