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
<c:url var="getQuotListBetDate" value="/getQuotListBetDate" />
<%-- 

<c:url var="getCustInfoByCustId" value="/getCustInfoByCustId" />

<c:url var="getProjectByCustId" value="/getProjectByCustId" />


<c:url var="getPoDetailForOrderByPoId"
	value="/getPoDetailForOrderByPoId" />
	
	
<c:url var="getTempOrderHeader"
	value="/getTempOrderHeader" /> --%>


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
<body onload="showQuot()">


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

						<div class="alert">

							<span class="closebtn"
								onclick="this.parentElement.style.display='none';">&times;</span>
							<strong>Failed !</strong> Data not submitted !!
						</div>

					</c:when>

					<c:when test="${isError==2}">

						<div class="alert1">

							<span class="closebtn"
								onclick="this.parentElement.style.display='none';">&times;</span>
							<strong>Success</strong> Data Submitted !!
						</div>

					</c:when>

				</c:choose>



				<div class="col-xs-12 col-sm-12">
					<div class="card">

						<div class="card-header">
							<div class="col-md-4">
								<strong>${title}</strong>
							</div>


						</div>
						<form
							action="${pageContext.request.contextPath}/deleteRecordofQuotations"
							method="post">

							<div class="card-body card-block">

								<div class="row">

									<div class="col-md-1">Select Plant</div>

									<div class="col-md-3">
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
														<c:when test="${plant.plantId==plantId1}">
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
									<div class="col-md-3">
										<select id="cust_name" name="cust_name" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')"
											onchange="getCustInfo()">
											<option value="0">All</option>
										</select>
									</div>

									<div class="col-md-1">Status</div>
									<div class="col-md-2">
										<select id="statusList" name="statusList"
											class="standardSelect" tabindex="1" required
											oninvalid="setCustomValidity('Please select customer')">


											<c:choose>
												<c:when test="${status==0}">
													<option value="-1">All</option>
													<option value="0" Selected>Quotation Pending</option>
													<option value="1">Quotation Generated</option>
													<option value="2">PO Generated</option>
												</c:when>

												<c:when test="${status==1}">
													<option value="-1">All</option>
													<option value="0">Quotation Pending</option>
													<option value="1" Selected>Quotation Generated</option>
													<option value="2">PO Generated</option>
												</c:when>
												<c:otherwise>
													<option value="-1">All</option>
													<option value="0">Quotation Pending</option>
													<option value="1">Quotation Generated</option>
													<option value="2">PO Generated</option>
												</c:otherwise>
											</c:choose>




										</select>
									</div>

								</div>
								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">From Date</div>
									<div class="col-md-3">
										<input type="text" autocomplete="off" id="from_date"
											name="from_date" required style="width: 100%;"
											class="form-control" value="${fromDate}"> <span
											class="error" aria-live="polite"></span>
									</div>
									<div class="col-md-1">To Date</div>
									<div class="col-md-3">
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


								<div class="card-body card-block">
									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>
												<th style="text-align: center"><input type="checkbox"
													name="selAll" id="selAll" /></th>
												<th style="text-align: center">Sr.</th>
												<th style="text-align: center">Company Name</th>
												<th style="text-align: center">Quotation No</th>
												<th style="text-align: center">Quotation Date</th>
												<th style="text-align: center">Employee Name</th>
												<th style="text-align: center">Status</th>
												<th style="text-align: center">Action</th>
											</tr>
										</thead>


										<c:forEach items="${getQuotList}" var="quot" varStatus="count">
											<tr>
												<td><input type="checkbox" class="chk"
													name="selectQuatationToDelete"
													id="selectQuatationToDelete${count.index+1}"
													value="${quot.quotHeadId}" /></td>
												<td style="text-align: center">${count.index+1}</td>


												<td style="text-align: center"><c:out
														value="${quot.compName}" /></td>

												<td style="text-align: center"><c:out
														value="${quot.quotNo}" /></td>

												<td style="text-align: center"><c:out
														value="${quot.quotDate}" /></td>


												<td style="text-align: center"><c:out
														value="${quot.usrName}" /></td>






												<td style="text-align: left"><c:choose>
														<c:when test="${quot.status==0}">
														 Pending
													</c:when>
														<c:when test="${quot.status==1}">
														Quotation Generated
													</c:when>
														<c:otherwise>
														PO Generated
													</c:otherwise>

													</c:choose></td>
												<td><a
													href="${pageContext.request.contextPath}/editQuotationDetail/${quot.quotHeadId}/${quot.plantIds}/${quot.custId}/${quot.enqHeadId}"><i
														class="fa fa-edit" style="color: black"></i> <span
														class="text-muted"></span></a></td>


											</tr>
										</c:forEach>




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

								<input type="submit" class="btn btn-primary" value="Delete"
									id="deleteId"
									onClick="var checkedVals = $('.chk:checkbox:checked').map(function() { return this.value;}).get();checkedVals=checkedVals.join(',');if(checkedVals==''){alert('No Rows Selected');return false;	}else{   return confirm('Are you sure want to delete record');}"
									style="align-content: center; width: 113px; margin-left: 40px;">

								<input type="button" margin-right: 5px;" id="btn_submit"
									class="btn btn-primary" onclick="billPdf()" value="Pdf" />
							</div>
						</form>

						<!-- form -->
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

	<script type="text/javascript">
		function billPdf() {
			var checkedVals = $('input:checkbox:checked').map(function() {
				return this.value;
			}).get();
			//checkedVals=checkedVals.slice(0,- 1);alert(checkedVals);
			checkedVals = checkedVals.join(",");
			var quotIdList = checkedVals.replace('/', "");

			if (checkedVals == "") {
				alert("Please Select Quotation")
			} else {
				window
						.open('${pageContext.request.contextPath}/pdfQuot?url=pdf/showQuotPdf/'
								+ quotIdList + '/' + plantId);
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
					var html = '<option selected value="0">All</option>';
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

			var plantId = document.getElementById("plant_id").value;
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var statusList = document.getElementById("statusList").value;

			var valid = true;

			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please select plant");
			}

			var custId = document.getElementById("cust_name").value;
			var valid = true;
			if (custId == null || custId == "") {
				valid = false;
				alert("Please Select Customer");

				var dataTable = $('#bootstrap-data-table').DataTable();
				dataTable.clear().draw();

			} else if (custId < 0) {
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
								'${getQuotListBetDate}',
								{
									plantId : plantId,
									custId : custId,
									fromDate : fromDate,
									toDate : toDate,
									statusList : statusList,
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

									//alert("Order Data " +JSON.stringify(data));

									var dataTable = $('#bootstrap-data-table')
											.DataTable();
									dataTable.clear().draw();

									$
											.each(
													data,
													function(i, v) {

														var status1;
														if (v.status == 0) {
															status1 = "Pending";
															var acButton = '<a href="#" class="action_btn" onclick="callEdit('
																	+ v.quotHeadId
																	+ ','
																	+ v.plantIds
																	+ ','
																	+ v.custId
																	+ ','
																	+ v.enqHeadId
																	+ ','
																	+ v.status
																	+ ','
																	+ i
																	+ ')" style="color:black"><i class="fa fa-edit"  title="Edit"></i></a>'
														} else if (v.status == 1) {
															status1 = "Quotation Generated";
															var acButton = '<a href="#" class="action_btn" onclick="callEdit('
																	+ v.quotHeadId
																	+ ','
																	+ v.plantIds
																	+ ','
																	+ v.custId
																	+ ','
																	+ v.enqHeadId
																	+ ','
																	+ v.status
																	+ ','
																	+ i
																	+ ')" style="color:black"><i class="fa fa-edit"  title="Edit"></i></a>&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callSinglePdf('
																	+ v.quotHeadId
																	+ ','
																	+ i
																	+ ')" style="color:black"><i class="fa fa-file"  title="PDF"></i></a>'
														} else {

															status1 = "PO Generated";
															var acButton = '<a href="#" class="action_btn" onclick="callSinglePdf('
																	+ v.quotHeadId
																	+ ','
																	+ i
																	+ ')" style="color:black"><i class="fa fa-file"  title="PDF"></i></a>'
														}
														var chBox;

														chBox = '<input  type="checkbox" class="chk" name="selectQuatationToDelete" id='+v.quotHeadId+' class="check"  value='+v.quotHeadId+'>'

														//var chBox='<input type="checkbox" id="orderId" class="chk" name="quotIds" value='+v.orderId+'/>'
														dataTable.row
																.add(
																		[
																				chBox,
																				i + 1,
																				v.compName,
																				v.quotNo,
																				v.quotDate,
																				v.usrName,
																				status1,
																				acButton ])
																.draw();
													});

								});

			}//end of if valid ==true
			getData();

		}

		function callEdit(quotHeadId, plantId, custId, enqHeadId, stat) {

			window
					.open("${pageContext.request.contextPath}/editQuotationDetail/"
							+ quotHeadId
							+ '/'
							+ plantId
							+ '/'
							+ custId
							+ '/'
							+ enqHeadId + '/' + stat);

		}
		function callSinglePdf(quotIdList) {

			window
					.open('${pageContext.request.contextPath}/pdfQuot?url=pdf/showQuotPdf/'
							+ quotIdList);

		}
	</script>


	<script type="text/javascript">
		$(document)
				.ready(

						function() {
							$('#bootstrap-data-table-export').DataTable();

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
			//alert("hiii");
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;
			var plantId = document.getElementById("plant_id").value;
			var custId = document.getElementById("cust_name").value;
			var statusList = document.getElementById("statusList").value;

			window.open('${pageContext.request.contextPath}/showQuotListPdf/'
					+ fromDate + '/' + toDate + '/' + custId + '/' + plantId);
			document.getElementById("expExcel").disabled = true;

		}
	</script>


</body>
</html>