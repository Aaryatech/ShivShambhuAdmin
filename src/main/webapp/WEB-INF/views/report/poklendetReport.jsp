<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getRawItemByCatId" value="/getRawItemByCatId" />

<c:url var="editInAddMatVehicleDetail"
	value="/editInAddMatVehicleDetail" />

<c:url var="getIndexForEditMatVehicle"
	value="/getIndexForEditMatVehicle" />

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
				<c:choose>
					<c:when test="${isError==1}">
						<div class="col-sm-12">
							<div
								class="sufee-alert alert with-close alert-danger alert-dismissible fade show">

								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>Data not submitted</strong>
							</div>
						</div>
					</c:when>

					<c:when test="${isError==2}">
						<div class="col-sm-12">
							<div
								class="sufee-alert alert with-close alert-success alert-dismissible fade show">

								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>Data Submitted Successfully</strong>
							</div>
						</div>
					</c:when>

				</c:choose>

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<div class="col-md-4">
								<strong>${title}</strong>
							</div>
							<div class="col-md-4"></div>
							<%-- <div class="col-md-4" align="left">
								<a
									href="${pageContext.request.contextPath}/showMatIssueVehicleList"><strong>Material
										Issue Vehicle List</strong></a>
							</div>
 --%>
						</div>
						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/updateMaterialVehicle"
								id="submitForm" method="post">
								<div class="row">

									<div class="col-md-2">From Date</div>


									<div class="col-md-4">
										<input type="text" id="fromDate" name="fromDate"
											value="${fromDate}" class="form-control" style="width: 100%;"
											autocomplete="off" readonly
											oninvalid="setCustomValidity('Please enter Issue No')"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>


									<div class="col-md-2">To Date</div>
									<div class="col-md-4">
										<input type="text" id="toDate" name="toDate"
											autocomplete="off" value="${toDate}" required
											class="form-control" readonly required style="width: 100%;">
									</div>
								</div>


								<div class="card-body card-block">

									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<th style="text-align: center; width: 5%;">Sr No</th>

												<th style="text-align: center">Item Name</th>
												<th style="text-align: center">Measurement of Unit</th>
												<th style="text-align: center">Item Rate</th>
												<th style="text-align: center">Item Quantity</th>
												<th style="text-align: center">Value</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${detailList}" var="matDetail"
												varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}</td>


													<td style="text-align: left"><c:out
															value="${matDetail.itemDesc}" /></td>


													<td style="text-align: center"><c:out
															value="${matDetail.uomName}" /></td>

													<td style="text-align: right"><c:out
															value="${matDetail.rate}" /></td>


													<td style="text-align: right"><c:out
															value="${matDetail.quantity}" /></td>


													<td style="text-align: right"><c:out
															value="${matDetail.value}" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div class="col-md-2"></div>

									<div class="col-md-3">

										<button type="button" class="btn btn-primary"
											onclick="exportToExcel();" id="expExcel"
											style="align-content: center; width: 200px; margin-left: 80px;">
											Export To Excel</button>
									</div>

									&nbsp;&nbsp;&nbsp;
									<%-- <div class="form-group"></div>
									<div class="row">

										<div class="col-md-8"></div>
										<div class="col-md-1">Total</div>
										<div class="col-md-3">
											<input type="text" value="${editVeh.vehTotal}"
												class="form-control" style="width: 100%;" autocomplete="off"
												readonly>
										</div>
									</div>
									&nbsp;&nbsp;&nbsp; --%>


									<table class="table table-striped table-bordered">
										<thead>
											<tr>

												<th style="text-align: center; width: 5%;">Sr No</th>

												<th style="text-align: center">Quantity</th>
												<th style="text-align: center">Contractor Name</th>
												<th style="text-align: center">Poklen Name</th>
												<th style="text-align: center">Poklen No</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${weighing}" var="weighing"
												varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}</td>
													<td style="text-align: right"><c:out
															value="${weighing.quantity}" /></td>

													<td style="text-align: left"><c:out
															value="${weighing.contrName}" /></td>


													<td style="text-align: left"><c:out
															value="${weighing.vehicleName}" /></td>

													<td style="text-align: left"><c:out
															value="${weighing.vehicleNo}" /></td>

												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div class="col-md-2"></div>

									<div class="col-md-3">

										<button type="button" class="btn btn-primary"
											onclick="exportToExcel2();" id="expExcel1"
											style="align-content: center; width: 200px; margin-left: 80px;">
											Export To Excel</button>
									</div>
									&nbsp;&nbsp;&nbsp;

									<table class="table table-striped table-bordered">
										<thead>
											<tr>

												<th style="text-align: center; width: 5%;">Sr No</th>
												<th style="text-align: center">Start Date</th>
												<th style="text-align: center">End Date</th>

												<th style="text-align: center">Start Time</th>
												<th style="text-align: center">End Time</th>
												<th style="text-align: center">Poklen Name</th>
												<th style="text-align: center">Poklen No</th>



											</tr>
										</thead>

										<tbody>
											<c:forEach items="${readingList}" var="reading"
												varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}</td>

													<td style="text-align: left"><c:out
															value="${reading.startDate}" /></td>

													<td style="text-align: left"><c:out
															value="${reading.endDate}" /></td>


													<td style="text-align: left"><c:out
															value="${reading.startTime}" /></td>

													<td style="text-align: left"><c:out
															value="${reading.endTime}" /></td>

													<td style="text-align: left"><c:out
															value="${reading.vehicleName}" /></td>

													<td style="text-align: left"><c:out
															value="${reading.vehNo}" /></td>


												</tr>
											</c:forEach>
										</tbody>
									</table>


									<div class="col-md-2"></div>

									<div class="col-md-3">

										<button type="button" class="btn btn-primary"
											onclick="exportToExcel1();" id="expExcel1"
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




	<script type="text/javascript">
		$(document).ready(function() {
			var dataTable = $('#bootstrap-data-table').DataTable();
			columnDefs: [ {
				targets : [ 2 ],
				className : "right"
			}, ]

		});
	</script>
	<script type="text/javascript">
		$(function() {
			$('#submitForm').submit(
					function() {
						$("input[type='submit']", this).val("Please Wait...")
								.attr('disabled', 'disabled');
						return true;
					});
		});
	</script>

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
			$('input[id$=date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>


	<script type="text/javascript">
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}

		function exportToExcel1() {

			window.open("${pageContext.request.contextPath}/exportToExcel1");
			document.getElementById("expExcel1").disabled = true;
		}

		function exportToExcel2() {

			window.open("${pageContext.request.contextPath}/exportToExcel2");
			document.getElementById("expExcel2").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			alert("hiii");
			var fromDate = document.getElementById("fromDate").value;
			var toDate = document.getElementById("toDate").value;

			window
					.open('${pageContext.request.contextPath}/showPoklenDetailPdf/'
							+ fromDate + '/' + toDate);
			document.getElementById("expExcel").disabled = true;

		}
	</script>

</body>
</html>