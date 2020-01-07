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

									<div class="col-md-2">PO NO</div>
									<div class="col-md-2">
										<input type="text" id="poNo" name="poNo"
											value="${poNo}" class="form-control" style="width: 100%;"
											autocomplete="off" readonly
											oninvalid="setCustomValidity('Please enter Issue No')"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>


									<div class="col-md-2">PO Date</div>
									<div class="col-md-2">
										<input type="text" id="poDate" name="poDate"
											autocomplete="off" value="${poDate}" required
											class="form-control" readonly required style="width: 100%;">
									</div>
									
									<div class="col-md-2">Item Name</div>
									<div class="col-md-2">
										<input type="text" id="itemName" name="itemName"
											autocomplete="off" value="${itemName}" required
											class="form-control" readonly required style="width: 100%;">
									</div>
								</div>
							
								<div class="card-body card-block">

									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<th style="text-align: center; width: 5%;">Sr.</th>
												<th style="text-align: center">Challan No.</th>
												<th style="text-align: center">Challan Date</th>
												<th style="text-align: center">Challan Qty.</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${chalanList}" var="challan" varStatus="count">
												<tr>

													<td style="text-align: left">${count.index+1}</td>
													<td style="text-align: center"><c:out
															value="${challan.chalanNo}" /></td>

													<td style="text-align: center"><c:out
															value="${challan.chalanDate}" /></td>


													<td style="text-align: right"><c:out
															value="${challan.itemQty}" /></td>
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
	</script>

	<script type="text/javascript">
		function genPdf() {
			var itemName = document.getElementById("itemName").value;
			var poNo = document.getElementById("poNo").value;
			var poDate = document.getElementById("poDate").value;
			//alert(itemName+" "+poNo+" "+poDate)
			window
					.open('${pageContext.request.contextPath}/showPOChallanDetailPdf/'
							+ poNo + '/' + poDate + '/' +itemName );
			document.getElementById("expExcel").disabled = true;

		}
	</script>

</body>
</html>