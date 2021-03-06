<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>


<c:url var="getPlantByCompId" value="/getPlantByCompId" />
<c:url var="getBillListBetweenDate" value="/getBillListBetweenDate" />

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
						</div>

						<div class="card-body card-block">


							<div class="form-group"></div>

							<div class="row">

								<div class="col-md-2">Select Company</div>

								<div class="col-md-4">
									<select id="compId" name="compId" class="standardSelect"
										tabindex="1" required
										oninvalid="setCustomValidity('Please select company')"
										onchange="getData()">
										<option value="">Select</option>
										<option value="0">All</option>

										<c:forEach items="${compList}" var="comp">
											<option value="${comp.companyId}">${comp.compName}</option>
										</c:forEach>


									</select>
								</div>
								<div class="col-md-2">Select Plant</div>
								<div class="col-md-4">
									<select id="plantId" name="plantId" class="standardSelect"
										tabindex="1" required
										oninvalid="setCustomValidity('Please select plant')">


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
								<div class="col-md-2"></div>
								<div class="col-md-2">
									<input type="button" class="btn btn-primary"
										onclick="showQuot()" value="Submit">
								</div>

							</div>


							<!-- 
							<div class="form-group"></div>
							<div class="row">
								<div class="col-md-6"></div>
							
							</div>
 -->

							<div class="form-group"></div>

							<div class="card-body card-block">
								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">
									<thead>
										<tr>
											<th style="text-align: center; width: 5%;">Sr.</th>
											<th width="5%" style="text-align: center">Bill Date</th>
											<th width="10%" style="text-align: center">Bill No</th>
											<th width="10%" style="text-align: center">Customer Name</th>
											<!-- 	<th width="10%" style="text-align: center">Project Name</th> -->
											<th width="10%" style="text-align: center">Taxable
												Amount</th>
											<th width="10%" style="text-align: center">CGST Amount</th>
											<th width="10%" style="text-align: center">SGST Amount</th>
											<th width="10%" style="text-align: center">IGST Amount</th>
											<th width="10%" style="text-align: center">Tax Amount</th>
											<th width="10%" style="text-align: center">TCS Amount</th>
											<th width="10%" style="text-align: center">Total Amount</th>

										</tr>
									</thead>
									<tbody></tbody>
									<!-- <tr>
										<td width="5%"></td>
										<td width="5%"></td>
										<td width="10%"></td>
											<td width="10%"></td>




										<td><b>Total</b></td>
										<td><input type="text" id="totalTaxableAmt" width="10%"
											name="totalTaxableAmt" value="0" readonly></td>

										<td><input type="text" id="totalCgstAmt" width="10%"
											name="totalCgstAmt" value="0" readonly></td>
										<td><input type="text" id="totalSgstAmt" width="10%"
											name="totalSgstAmt" value="0" readonly></td>
										<td><input type="text" id="totalIgstAmt" width="10%"
											name="totalIgstAmt" value="0" readonly></td>
										<td><input type="text" id="totalTax" name="totalTax"
											width="10%" value="0" readonly></td>
										<td><input type="text" id="tcsAmt" name="tcsAmt"
											width="10%" value="0" readonly></td>
										<td><input type="text" id="totalAmt" name="totalAmt"
											width="10%" value="0" readonly></td>

									</tr> -->



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
		function showQuot() {

			//alert("Hi View Orders  ");

			var compId = document.getElementById("compId").value;
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;

			var valid = true;

			if (compId == null || compId == "") {
				valid = false;
				alert("Please select company");
			}

			var plantId = document.getElementById("plantId").value;

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

			if(!validateDates(fromDate, toDate)){
				valid = false;
				alert("from date must be smaller than to date");
			}
			if (valid == true) {

				$
						.getJSON(
								'${getBillListBetweenDate}',
								{
									companyId : compId,
									plantId : plantId,
									fromDate : fromDate,
									toDate : toDate,
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

									var totalTaxableAmt = 0;
									var totalCgstAmt = 0;
									var totalSgstAmt = 0;
									var totalIgstAmt = 0;
									var totalTax = 0;
									var totalAmt = 0;
									var space = " ";
									var total = "Total";
									var ttlTcs = 0;

									$
											.each(
													data,
													function(i, v) {

														dataTable.row
																.add(
																		[
																				i + 1,
																				v.billDate,
																				v.billNo,
																				v.custName,
																				/* v.projName, */
																				v.taxableAmt
																						.toFixed(2),
																				v.cgstAmt
																						.toFixed(2),
																				v.sgstAmt
																						.toFixed(2),
																				v.igstAmt
																						.toFixed(2),
																				v.taxAmt
																						.toFixed(2),
																				v.tcsAmt
																						.toFixed(2),
																				v.totalAmt
																						.toFixed(2)

																		])
																.draw();

														totalTaxableAmt = totalTaxableAmt
																+ v.taxableAmt;
														totalCgstAmt = totalCgstAmt
																+ v.cgstAmt;
														totalSgstAmt = totalSgstAmt
																+ v.sgstAmt;
														totalIgstAmt = totalIgstAmt
																+ v.igstAmt;
														totalTax = totalTax
																+ v.taxAmt;
														totalAmt = totalAmt
																+ v.totalAmt;
														
														ttlTcs = ttlTcs+v.tcsAmt;
													});

									/* document.getElementById("totalTaxableAmt").value = totalTaxableAmt
											.toFixed(2);
									document.getElementById("totalCgstAmt").value = totalCgstAmt
											.toFixed(2);
									document.getElementById("totalSgstAmt").value = totalSgstAmt
											.toFixed(2);
									document.getElementById("totalIgstAmt").value = totalIgstAmt
											.toFixed(2);
									document.getElementById("totalTax").value = totalTax
											.toFixed(2);
									document.getElementById("totalAmt").value = totalAmt
											.toFixed(2);									
									document.getElementById("tcsAmt").value = ttlTcs
											.toFixed(2); */
									var tr1 = $('<tr></tr>');
									tr1
									.append($(
											'<td></td>')
											.html(''));
									tr1
									.append($(
											'<td></td>')
											.html(''));
									tr1
									.append($(
											'<td></td>')
											.html(''));

									tr1
											.append($(
													'<td></td>')
													.html('Total'));									
									tr1
									.append($(
											'<td></td>')
											.html(totalTaxableAmt.toFixed(2)));
									tr1
									.append($(
											'<td></td>')
											.html(totalCgstAmt.toFixed(2)));
									tr1
									.append($(
											'<td></td>')
											.html(totalSgstAmt.toFixed(2)));
									tr1
									.append($(
											'<td></td>')
											.html(totalIgstAmt.toFixed(2)));
									tr1
									.append($(
											'<td></td>')
											.html(totalTax.toFixed(2)));
									tr1
									.append($(
											'<td></td>')
											.html(ttlTcs.toFixed(2)));
									tr1
									.append($(
											'<td></td>')
											.html(totalAmt.toFixed(2)));
									$(
									'#bootstrap-data-table tbody')
									.append(
											tr1);

								});

			}//end of if valid ==true

		}

		function callEdit(weighId) {

			window.open("${pageContext.request.contextPath}/editWeighing/"
					+ weighId);

		}

		function callDelete(weighId) {
			window.open("${pageContext.request.contextPath}/deleteWeighing/"
					+ weighId);

		}
		
		function validateDates(from_date, to_date) {			
			var fromdate = from_date.split('-');
			from_date = new Date();
			from_date.setFullYear(fromdate[2], fromdate[1] - 1, fromdate[0]);
			
			var todate = to_date.split('-');
			to_date = new Date();
			to_date.setFullYear(todate[2], todate[1] - 1, todate[0]);
			
			if (from_date > to_date) {
						
				return false;
			} else {
				return true;				
			}
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
		// on plant change function 
		function getData() {
			var compId = document.getElementById("compId").value;
			var valid = true;

			if (compId == null || compId == "") {
				valid = false;
				alert("Please select company");
			}

			if (valid == true) {

				$.getJSON('${getPlantByCompId}', {
					compId : compId,
					ajax : 'true',
				},

				function(data) {
					var html;
					var len = data.length;

					var html = '<option selected value="0">All</option>';

					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].plantId + '">'
								+ data[i].plantName + '</option>';

					}
					html += '</option>';

					$('#plantId').html(html);
					$("#plantId").trigger("chosen:updated");

					var dataTable = $('#bootstrap-data-table').DataTable();
					dataTable.clear().draw();

				});
			}//end of if

		}
	</script>

	<script type="text/javascript">
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			//alert("hiii");
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;

			window.open('${pageContext.request.contextPath}/showBillwisePdf/'
					+ fromDate + '/' + toDate);
			document.getElementById("expExcel").disabled = true;

		}
	</script>


</body>
</html>