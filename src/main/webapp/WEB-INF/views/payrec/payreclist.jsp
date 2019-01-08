<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getCustByPlantId" value="/getCustByPlantId" />
<c:url var="getPayRecoveryBetDate" value="/getPayRecoveryBetDate" />
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

									<div class="col-md-2">Select Type*</div>

									<div class="col-md-4">
										<select id="txType" name="txType" class="standardSelect"
											onchange="hideDiv(this.value)" tabindex="1">

											<option value="0">All</option>
											<option value="1">Follow up Date</option>

										</select>
									</div>
									<div class="col-md-2">Select Plant*</div>
									<div class="col-md-4">
										<select id="plantId" name="plantId" class="standardSelect"
											tabindex="1" required onchange="showDetail()">
											<option value="">Select</option>

											<option selected value="0">All</option>
											<c:forEach items="${plantList}" var="plant">

												<option value="${plant.plantId}">${plant.plantName}</option>

											</c:forEach>
										</select>
									</div>


								</div>



								<div class="form-group"></div>

								<div class="row" id="hide_div" style="visibility: hidden">
									<div class="col-md-2">From Date</div>
									<div class="col-md-3">
										<input type="text" autocomplete="off" id="from_date"
											name="from_date" required style="width: 100%;"
											class="form-control" value="${fromDate}"> <span
											class="error" aria-live="polite"></span>
									</div>
									<div class="col-md-2">To Date</div>
									<div class="col-md-3">
										<input type="text" autocomplete="off" id="to_date"
											name="to_date" style="width: 100%;" class="form-control"
											value="${toDate}"> <span class="error"
											aria-live="polite"></span>
									</div>
									<div class="col-md-2">
										<input type="button" class="btn btn-primary"
											onclick="showQuot()" value="Submit">
									</div>

								</div>

								<div class="form-group"></div>
								<!-- 	<div class="form-group"></div>
								<div class="row">
									<div class="col-md-6"></div>
									
								</div>
 -->


								<div class="card-body card-block">
									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>
												
												<th style="text-align: center; width: 5%;">Sr.</th>
												<th style="text-align: center">Customer Name</th>
												<th style="text-align: center">Bill No</th>
												<th style="text-align: center">Bill Date</th>
												<th style="text-align: center">Credit Start Date</th>
												<th style="text-align: center">Follow up Date</th>
												<th style="text-align: center">Billing Amount</th>
												<th style="text-align: center">Paid Amount</th>
												<th style="text-align: center">Pending Amount</th>
												<th style="text-align: center">Status</th>
												<th style="text-align: center">Action</th>
											</tr>
										</thead>


										<tbody>
											<c:forEach items="${recList}" var="rec" varStatus="count">
												<tr>
													<%-- <td><input type="checkbox" class="chk"
														name="payHeadIds" id="payHeadIds${count.index+1}"
														value="${rec.payHeadId}" /></td>
 --%>
													<td style="text-align: center">${count.index+1}</td>

													<td style="text-align: left"><c:out
															value="${rec.custName}" /></td>

													<td style="text-align: left"><c:out
															value="${rec.billNo}" /></td>


													<td style="text-align: left"><c:out
															value="${rec.billDate}" /></td>



													<td style="text-align: left"><c:out
															value="${rec.creditDate1}" /></td>


													<td style="text-align: left"><c:out
															value="${rec.creditDate2}" /></td>


													<td style="text-align: left"><c:out
															value="${rec.billTotal}" /></td>


													<td style="text-align: left"><c:out
															value="${rec.paidAmt}" /></td>

													<td style="text-align: left"><c:out
															value="${rec.pendingAmt}" /></td>


													<td style="text-align: left"><c:choose>
															<c:when test="${rec.status==0}">
													Pending
													</c:when>
															<c:when test="${rec.status==1}">
													Done
													</c:when>
														</c:choose></td>


													<td style="text-align: center"><a href="#"
														onclick="callEdit(${rec.payHeadId},${count.index})"><i
															class="fa fa-edit" style="color: black"></i> <span
															class="text-muted"></span></a></td>




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

			//alert("Hi View Orders  ");

			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plantId").value;

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
								'${getPayRecoveryBetDate}',
								{
									fromDate : fromDate,
									toDate : toDate,
									plantId:plantId,
									ajax : 'true',
								},

								function(data) {

									//alert("Order Data " + JSON.stringify(data));

									var dataTable = $('#bootstrap-data-table')
											.DataTable();
									dataTable.clear().draw();

									$
											.each(
													data,
													function(i, v) {
														var chBox;

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


														dataTable.row
																.add(
																		[
																				
																				i + 1,
																				v.custName,
																				v.billNo,
																				v.billDate,
																				v.billTotal,
																				v.creditDate1,
																				v.creditDate2,
																				v.paidAmt,
																				v.pendingAmt,
																				status1,
																				acButton ])
																.draw();
													});

								});

			}//end of if valid ==true

		}

		function callEdit(payHeadId) {

			window.open("${pageContext.request.contextPath}/editPayRec/"
					+ payHeadId);

		}
	</script>




	<script type="text/javascript">
		// onclick of submit to search order 
		function showDetail() {

			//alert("Hi View Orders  ");

			
			var plantId = document.getElementById("plantId").value;

			var valid = true;
			if (valid == true) {

				$
						.getJSON(
								'${getPayRecoveryBetDateVyPlantId}',
								{
									
									plantId:plantId,
									ajax : 'true',
								},

								function(data) {

									//alert("Order Data " + JSON.stringify(data));

									var dataTable = $('#bootstrap-data-table')
											.DataTable();
									dataTable.clear().draw();

									$
											.each(
													data,
													function(i, v) {
														var chBox;

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


														dataTable.row
																.add(
																		[
																				
																				i + 1,
																				v.custName,
																				v.billNo,
																				v.billDate,
																				
																				v.creditDate1,
																				v.creditDate2,
																				v.billTotal,
																				v.paidAmt,
																				v.pendingAmt,
																				status1,
																				acButton ])
																.draw();
													});

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
		function genPdf() {
			//alert("hiii");
			
			
			var temp= document.getElementById("txType").value;
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			alert("from date"+fromDate);
			alert("to date"+toDate);
			
			
			if(temp==1){
			window.open('${pageContext.request.contextPath}/showPayRecPdf/'
					+ fromDate + '/' + toDate);
			}
			else{
				window.open('${pageContext.request.contextPath}/showPayRecPdf1/');
			}
		document.getElementById("expExcel").disabled = true;
			

		}
		
		/* function genPdf1() {
			//alert("hiii");
			

			window.open('${pageContext.request.contextPath}/showPayRecPdf1/'
					+ fromDate + '/' + toDate);
		document.getElementById("expExcel").disabled = true;

		}
		
		
		
		 */
	</script>

</body>
</html>