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
<c:url var="getPendingBillListBetDate" value="/getPendingBillListBetDate" />
<%-- 

<c:url var="getCustInfoByCustId" value="/getCustInfoByCustId" />

<c:url var="getProjectByCustId" value="/getProjectByCustId" />
//hii

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

<style>
.alert {
	padding: 20px;
	background-color: red;
	color: white;
}

.alert1 {
	padding: 20px;
	background-color: green;
	color: white;
}

.closebtn {
	margin-left: 15px;
	color: white;
	font-weight: bold;
	float: right;
	font-size: 22px;
	line-height: 20px;
	cursor: pointer;
	transition: 0.3s;
}

.closebtn:hover {
	color: black;
}
</style>


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.buttonload {
	background-color: white; /* Green background */
	border: none; /* Remove borders */
	color: #ec268f; /* White text */
	padding: 12px 15px; /* Some padding */
	font-size: 13px; /* Set a font-size */
	display: none;
}

/* Add a right margin to each icon */
.fa {
	margin-left: -12px;
	margin-right: 8px;
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
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<%-- <div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showAddCustomer"><strong>Add
										Customer</strong></a>
							</div> --%>


						</div>
						<div class="card-body card-block">

							
						
							
							<div class="row">

								<div class="col-md-2">Select Plant</div>

								<div class="col-md-4">
									<select id="plant_id" name="plant_id" class="standardSelect"
										tabindex="1" required
										oninvalid="setCustomValidity('Please select plant name')"
										onchange="getData()">
										<option value="0">All</option>

										<c:forEach items="${plantList}" var="plant">
											<option value="${plant.plantId}">${plant.plantName}</option>
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
								<div class="col-md-6"></div>
								<div class="col-md-3">
									<input type="button" class="btn btn-primary"
										onclick="showBill()" value="Submit">
									<button class="buttonload" id="loader">
										<i class="fa fa-spinner fa-spin"></i>Loading
									</button>
								</div>
							</div>


							<div class="form-group"></div>

						</div>

						<%-- <input type="checkbox" value="${item.itemId}" name="selectItem"> --%>

						<div class="card-body card-block">
							<table id="bootstrap-data-table"
								class="table table-striped table-bordered">
								<thead>
									<tr>

										<th style="text-align: center"><input type="checkbox"
											id="selectAll" /></th>
												<th style="text-align: center">Sr.</th>
										<th style="text-align: center">Chalan No.</th>
										<th style="text-align: center">Chalan Date</th>
										<th style="text-align: center">Customer Name</th>

										<th style="text-align: center">Customer Mobile</th>
										<th style="text-align: center">Vehicle No</th>
										<th style="text-align: center">Driver Name</th>
										<th style="text-align: center">Action</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${getBillList}" var="bill" varStatus="count">
										<tr>
											<td><input type="checkbox" class="chk" name="quotIds"
												id="quotIds${count.index+1}" value="${bill.billHeadId}" /></td>

													<td style="text-align: center">${count.index+1}</td> 


											<td style="text-align: left"><c:out
													value="${bill.billNo}" /></td>
											<td style="text-align: center">${bill.billDate}</td>

											<td style="text-align: left"><c:out
													value="${bill.custName}" /></td>

											<td style="text-align: left"><c:out
													value="${bill.totalAmt}" /></td>

											<td style="text-align: left"><c:out
													value="${bill.taxableAmt}" /></td>

											<td style="text-align: left"><c:out
													value="${bill.taxAmt}" /></td>




											<td><a
												href="${pageContext.request.contextPath}/editBill/${bill.billHeadId}"><i
													class="fa fa-edit" style="color:black" title="Generate Quotation"></i> <span
													class="text-muted"></span></a> &nbsp;<input type="button"
												id="btn_submit" class="btn btn-primary"
												onclick="singleBillPdf(${bill.billHeadId})" value="Pdf" /></td>
										</tr>
									</c:forEach>
								</tbody>

							</table>
						</div>

						<center>
							<input type="button" margin-right: 5px;" id="btn_submit"
								class="btn btn-primary" onclick="billPdf()" value="Bill Pdf" />
						</center>


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
		$(document).ready(function() {
			$('#bootstrap-data-table').DataTable();
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
		// onclick of submit to search order 
		function showBill() {

			//alert("Hi View Orders  ");

			var plantId = document.getElementById("plant_id").value;
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;

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
				$('#loader').show();
				$
						.getJSON(
								'${getPendingBillListBetDate}',
								{
									plantId : plantId,
									custId : custId,
									fromDate : fromDate,
									toDate : toDate,
									ajax : 'true',
								},

								function(data) {
									$('#loader').hide();
									//alert("Order Data " +JSON.stringify(data));

									var dataTable = $('#bootstrap-data-table')
											.DataTable();
									dataTable.clear().draw();

									$
											.each(
													data,
													function(i, v) {
														//alert("hdjfh");

														var checkB = '<input  type="checkbox" name=select_to_print id=select_to_print'+v.billHeadId+' class="chk"  value='+v.billHeadId+'/>'
													
														var acButton = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn"  onclick="callEdit('
																+ v.billHeadId
																+ ','
																+ i
																+ ')" style="color:black"><i class="fa fa-edit"></i>&nbsp;&nbsp;&nbsp;<a href="#" class="fa fa-file" title="Bill Pdf" onclick="singleBillPdf('
																+ v.billHeadId
																+ ','
																+ i
																+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
																
																

														dataTable.row
																.add(
																		[
																				checkB,
																						
																			 (i + 1),
																				v.billNo,
																				v.billDate,
																				v.custName,
																				v.taxableAmt,
																				v.taxAmt,
																				v.totalAmt,
																				acButton
																		 ])
																.draw();
													});

								});

			}//end of if valid ==true

		}

		function callEdit(billHeadId) {

			window.open("${pageContext.request.contextPath}/editBill/"
					+ billHeadId);

		}
	</script>

	<!-- <script type="text/javascript">
	// on cust change function 
		function showOrder() {
			$('#divCheckbox').show();
			var custId = document.getElementById("cust_name").value;
			var valid = true;
			if (custId == null || custId == "") {
				valid = false;
				alert("Please Select Customer");
				
				$('#po_id').html("-1");
				$("#po_id").trigger("chosen:updated");
				
				var dataTable = $('#bootstrap-data-table')
				.DataTable();
		dataTable.clear().draw();

			}
			else if(custId<0){
				valid = false;
				
				$('#po_id').html("-1");
				$("#po_id").trigger("chosen:updated");
				
				var dataTable = $('#bootstrap-data-table')
				.DataTable();
		dataTable.clear().draw();

			}
			if (valid == true) {

				$
						.getJSON(
								'${getCustInfoByCustId}',
								{
									custId : custId,
									ajax : 'true',

								},
								function(data) {
									document.getElementById("custTypeName").value = data.custTypeName;
									document.getElementById("custMobNo").value = data.custMobNo;
								});

				$	.getJSON(
						'${getPOHeaderByCustId}',
						{
							custId : custId,
							ajax : 'true',
						},
						function(data) {
							var html;
							var len = data.length;
							//alert("data " +JSON.stringify(data));
							for (var i = 0; i < len; i++) {
								var PNo=data[i].poNo+"-"+ data[i].poDate 

								html += '<option value="' + data[i].poId + '">'
										+PNo+ '</option>';

							}
							html += '</option>';
							$('#po_id').html(html);
							$("#po_id").trigger("chosen:updated");
						});
				
				
				$	.getJSON(
						'${getProjectByCustId}',
						{
							custId : custId,
							ajax : 'true',
						},
						function(data) {
							var html;
							var len = data.length;
							//alert("data " +JSON.stringify(data));
							for (var i = 0; i < len; i++) {
								var projData=data[i].projName+"-"+data[i].address

								html += '<option value="' + data[i].projId + '">'
										+projData+ '</option>';

							}
							html += '</option>';
							$('#proj_id').html(html);
							$("#proj_id").trigger("chosen:updated");
						});
				
				
			}// end of if valid= true
			
		}
	</script> -->
	<script type="text/javascript">
		function singleBillPdf(id) {
			window
			.open('${pageContext.request.contextPath}/pdf?url=pdf/showBillsPdf/'
					+ id);
		}
		</script>
	<script type="text/javascript">
		function billPdf() {
			var checkedVals = $('input:checkbox:checked').map(function() {
				return this.value;
			}).get();
			//checkedVals=checkedVals.slice(0,- 1);alert(checkedVals);
			checkedVals = checkedVals.join(",");
			var str2 = checkedVals.replace('/', "");

			if (checkedVals == "") {
				alert("Please Select Bill")
			} else {
				window
						.open('${pageContext.request.contextPath}/pdf?url=pdf/showBillsPdf/'
								+ str2);
			}
		}
	</script>
	<script type="text/javascript">
		$('#selectAll').click(
				function(e) {
					$(this).closest('table').find('td input:checkbox').prop(
							'checked', this.checked);
				});
	</script>


</body>
</html>