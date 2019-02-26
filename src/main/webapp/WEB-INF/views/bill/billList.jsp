<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

/* The Modal (background) */
.modal1 {
	display: none; /* Hidden by default */
	position: absolute; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 20px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content1 {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 50%;
	height: auto;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: left;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

#overlay {
	position: absolute;
	display: none;
	width: 100%;
	height: auto;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(101, 113, 119, 0.5);
	z-index: 2;
	cursor: pointer;
}

#text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 25px;
	color: white;
	transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
}

.bg-overlay {
	background: linear-gradient(rgba(0, 0, 0, .7), rgba(0, 0, 0, .7)),
		url("${pageContext.request.contextPath}/resources/images/smart.jpeg");
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center center;
	color: #fff;
	height: auto;
	width: auto;
	padding-top: 10px;
	padding-left: 20px;
}
</style>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>


<c:url var="getCustByPlantId" value="/getCustByPlantId" />
<c:url var="getBillListBetDate" value="/getBillListBetDate" />
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
<body onload="showBill()">


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
								<div class="col-md-2">
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
											<c:when test="${status==-1}">
												<option value="-1" Selected>All</option>
												<option value="0">Without GST</option>
												<option value="1">With GST</option>

											</c:when>

											<c:when test="${status==0}">

												<option value="-1">All</option>
												<option value="0" Selected>Without GST</option>
												<option value="1">With GST</option>
											</c:when>
											<c:otherwise>
												<option value="-1">All</option>
												<option value="0">Without GST</option>
												<option value="1" Selected>With GST</option>
											</c:otherwise>
										</c:choose>




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
								<div class="col-md-1">To Date</div>
								<div class="col-md-2">
									<input type="text" autocomplete="off" id="to_date"
										name="to_date" style="width: 100%;" class="form-control"
										value="${toDate}"> <span class="error"
										aria-live="polite"></span>
								</div>

								<input type="hidden" name="billHeaderIdTemp"
									id="billHeaderIdTemp">


								<div class="col-md-1"></div>
								<div class="col-md-1">
									<input type="button" class="btn btn-primary"
										onclick="showBill()" value="Submit">
									<button class="buttonload" id="loader">
										<i class="fa fa-spinner fa-spin"></i>Loading
									</button>
								</div>

							</div>




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
										<th style="text-align: center">Bill No</th>
										<th style="text-align: center">Bill Date</th>
										<th style="text-align: center">Customer Name</th>

										<th style="text-align: center">Taxable Amount</th>
										<th style="text-align: center">Tax Amount</th>
										<th style="text-align: center">Total Amount</th>

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
													class="fa fa-edit" style="color: black"
													title="Generate Quotation"></i> <span class="text-muted"></span></a>
												&nbsp;<input type="button" id="btn_submit"
												class="btn btn-primary"
												onclick="singleBillPdf(${bill.billHeadId})" value="Pdf" /></td>
										</tr>
									</c:forEach>
								</tbody>

							</table>


							<center>
								<input type="button" margin-right: 5px;" id="btn_submit"
									class="btn btn-primary" onclick="billPdf()" value="Bill Pdf" />


								<button type="button" class="btn btn-primary"
									onclick="exportToExcel();" id="expExcel"
									style="align-content: center; width: 200px; margin-left: 80px;">
									Export Bills(Talley)</button>



								<button type="button" class="btn btn-primary"
									onclick="exportToExcel1();" id="expExcel1"
									style="align-content: center; width: 200px; margin-left: 80px;">
									Export Item(Talley)</button>



								<button type="button" class="btn btn-primary"
									onclick="exportToExcel2();" id="expExcel2"
									style="align-content: center; width: 200px; margin-left: 80px;">
									Export Customer(Talley)</button>
							</center>

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
			var statusList = document.getElementById("statusList").value;

			var valid = true;
			getData();

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
								'${getBillListBetDate}',
								{
									plantId : plantId,
									custId : custId,
									fromDate : fromDate,
									toDate : toDate,
									statusList : statusList,
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

														var checkB = '<input  type="checkbox" name=select_to_print id=select_to_print'+v.billHeadId+' class="chk"  value='+v.billHeadId+'>'
													
														var acButton = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn"  onclick="callEdit('
																+ v.billHeadId
																+ ','
																+ i
																+ ')" style="color:black"><i class="fa fa-edit"></i>&nbsp;&nbsp;&nbsp;<a href="#" class="fa fa-file-pdf-o" title="Bill Pdf" onclick="singleBillPdf('
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

	<div class="row"></div>
	<div class="form-group"></div>

	<div id="myModal" class="modal1">

		<div class="modal-content1" style="color: black;">
			<span class="close" id="close">&times;</span>
			<h4 style="text-align: left;">Select PDF Options</h4>


			<div style="height: 100%; width: 100%; overflow: auto">
				<table style="width: 100%" id="table_grid1"
					class="table table-striped table-bordered">
					<thead>
						<tr>
							<th class="check" style="text-align: left; width: 5%;"
								align="left"><input type="checkbox" name="selectAll1"
								id="selectAll1" /></th>
							<th style="width: 5%;" align="center">Sr</th>
							<th style="width: 20%;" align="center">Name</th>

						</tr>
					</thead>
					<tbody>

						<c:forEach items="${settingList}" var="custCate">
							<tr>
								<td><input type="checkbox" class="chk" name="settingIds"
									id="settingIds${count.index+1}" value="${custCate.settingId}" /></td>
								<td style="text-align: center">${count.index+1}</td>


								<td style="text-align: left"><c:out
										value="${custCate.settingValue}" /></td>
							</tr>
						</c:forEach>



					</tbody>
				</table>
			</div>

			<input type="button" id="poupSubButton" onClick="showSinglePdf()"
				class="btn btn-primary" value="Submit"
				style="align-content: center; width: 113px; margin-left: 40px;">
			<br>
		</div>

	</div>


	<script type="text/javascript">
		function singleBillPdf(id) {
			
			//alert("hi"+id);
			
			var isValid=true;
			if(id==null){
				isValid=false;
			}
			if(isValid==true){
			 var span = document.getElementById("close");
				var modal = document.getElementById('myModal');

				modal.style.display = "block";
				span.onclick = function() {
				    modal.style.display = "none"; 
				}

				// When the user clicks anywhere outside of the modal, close it
				window.onclick = function(event) {
				    if (event.target == modal) {
				        modal.style.display = "none";
				        
				    }
				}
				
				document.getElementById("billHeaderIdTemp").value=id;
			
			}
		}
			
		
		
		
		</script>


	<script type="text/javascript">
		function showSinglePdf() {
			
			
			var modal = document.getElementById('myModal');
	        modal.style.display = "none";
	        
	        
	        var id =document.getElementById("billHeaderIdTemp").value; 
	      
	       
	        
	        var checkedVals = $('input:checkbox:checked').map(function() {
				return this.value;
			}).get();
			
			checkedVals = checkedVals.join(",");
			
			
			var str2 = checkedVals.replace('/', ',');
			var str3=str2.replace('on,', "");
			
			if (checkedVals == "") {
				alert("Please Select")
			} else {
				window
						.open('${pageContext.request.contextPath}/pdf?url=pdf/showBillsPdf/'
								+ id+ '/' + str3 );
			}
	      
		}
			
		 	/* window
			.open('${pageContext.request.contextPath}/pdf?url=pdf/showBillsPdf/'
					+ id);   */
		
		
	
		</script>



	<script type="text/javascript">
		function billPdf() {
			var checkedVals = $('input:checkbox:checked').map(function() {
				return this.value;
			}).get();
			//checkedVals=checkedVals.slice(0,- 1);alert(checkedVals);
			checkedVals = checkedVals.join(",");
			
			
			var str2 = checkedVals.replace('/', ',');
			var str3=str2.replace('on,', "");
			
			//alert("Ids : ------------ "+str3);

			if (checkedVals == "") {
				alert("Please Select Bill")
			} else {
				window
						.open('${pageContext.request.contextPath}/pdf?url=pdf/showBillsPdf/'
								+ str3);
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

	<script type="text/javascript">
		$('#selectAll1').click(
				function(e) {
					$(this).closest('table').find('td input:checkbox').prop(
							'checked', this.checked);
				});
	</script>

	<script type="text/javascript">
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
	 	document.getElementById("expExcel").disabled = true; 
		}
	</script>

	<script type="text/javascript">
		function exportToExcel1() {

			window.open("${pageContext.request.contextPath}/exportToExcel1");
			 document.getElementById("expExcel1").disabled = true; 
		}
	</script>
	<script type="text/javascript">
		function exportToExcel2() {

			window.open("${pageContext.request.contextPath}/exportToExcel2");
			 document.getElementById("expExcel2").disabled = true; 
		}
	</script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#bootstrap-data-table-export').DataTable();

							$("#selAll1")
									.click(
											function() {
												$(
														'#bootstrap-data-table tbody input[type="checkbox"]')
														.prop('checked',
																this.checked);
											});
						});
	</script>
</body>
</html>