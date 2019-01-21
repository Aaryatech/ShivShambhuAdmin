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
<c:url var="getStockItemByPlantId" value="/getStockItemByPlantId" />
<c:url var="getStockItemByPlantIdAndCurDate"
	value="/getStockItemByPlantIdAndCurDate" />

<c:url var="getStockBetDate" value="/getStockBetDate" />


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
						</div>

						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/updateStockDetail"
								id="submitForm" method="post">


								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Select Plant</div>

									<div class="col-md-3">
										<select id="plantId" name="plantId" class="standardSelect"
											tabindex="1" required onchange="callDate()"
											oninvalid="setCustomValidity('Please select Plant')">
											<option value="">Select</option>
											<c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select>
									</div>
									<!-- <input type="text" id="plantId" name="plantId"> <input
										type="text" id="stockId" name="stockId"> -->

									<div class="col-md-1">Select</div>

									<div class="col-md-3">
										<select id="selectDate" name="selectDate"
											class="standardSelect" tabindex="1" required
											onchange="hideDiv(this.value)"
											oninvalid="setCustomValidity('Please select Status')">
											<option value="">Select</option>
											<option value="1">Get Current Stock</option>
											<option value="2">Stock Between Date</option>

										</select>
									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">
										<input type="button" class="btn btn-primary" id="searchButton"
											onclick="showQuot()" value="Search">
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
											onclick="showQuotBetDate()" value="Search">
									</div>

								</div>

								<div class="form-group"></div>
								<div>
									<table id="bootstrap-data-table"
										class="table table-striped table-bordered"
										data-page-length='-1'>
										<thead>
											<tr>
												<th style="text-align: center; width: 5%;">Sr.</th>
												<th style="text-align: center">Item Name</th>
												<th style="text-align: center">Measurement Unit</th>
												<th style="text-align: center">Opening Quantity</th>
												<th style="text-align: center">Production Quantity</th>
												<th style="text-align: center">Chalan Quantity</th>
												<th style="text-align: center">Closing Quantity</th>
											</tr>
										</thead>

									</table>

									<div class="col-md-2"></div>
									<div class="col-md-2">
										<input type="submit" class="btn btn-primary" value="Day End">
									</div>

								</div>
							</form>
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
		// onclick of submit to search order 
		function showQuot() {
			//alert("hii");

			var plantId = document.getElementById("plantId").value;
			var valid = true;
			//alert("plantId"+plantId);
			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please Select Plant");

				 var dataTable = $('#bootstrap-data-table').DataTable();
				dataTable.clear().draw();

			} else if (plantId < 0) {
				valid = false;

			}

			if (valid == true) {

				$
						.getJSON(
								'${getStockItemByPlantIdAndCurDate}',
								{
									plantId : plantId,
									ajax : 'true',

								},
								function(data) {

								 	var dataTable = $('#bootstrap-data-table')
											.DataTable();
									dataTable.clear().draw(); 
									

									$
											.each(
													data,
													function(i, v) {
													
														
														var opQty1 = '<input  type="text" value="'+v.opQty+'"  class="form-control"  id="opQty'+v.itemId+'" name="opQty'+v.itemId+'"  onkeypress="return allowOnlyNumber(event);"/>'

														dataTable.row
																.add(
																		[
																				i + 1,
																				v.itemName,
																				v.uomName,
																				v.opQty,
																				v.prodQty,
																				v.chalanQty,v.closingQty
																				 ])
																.draw();
													});

								});
				
			
				

			}//end of if valid ==true

		}
		</script>

	<script type="text/javascript">
		// onclick of submit to search order 
		
				function showQuotBetDate() {

			alert("Hi View Orders  ");

			var plantId = document.getElementById("plantId").value;
			var fromDate = document.getElementById("from_date").value;
			var toDate = document.getElementById("to_date").value;

			alert(plantId);

			var valid = true;

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

				$.getJSON('${getStockBetDate}', {

					plantId : plantId,
					fromDate : fromDate,
					toDate : toDate,
					ajax : 'true',

				},

				function(data) {

					//alert(data);
					var dataTable = $('#bootstrap-data-table').DataTable();
					dataTable.clear().draw();

					$.each(data, function(i, v) {
						
						alert(data);

						dataTable.row.add(
								[ i + 1,
									v.itemName,
									v.uomName,
									v.opQty,
									v.prodQty,
									v.chalanQty,v.closingQty

								]).draw();
					});

				});

			}//end of if valid ==true

		}
	</script>


	<script type="text/javascript">
		function callDate() {
			alert(hii);
			var plantId = document.getElementById("plantId").value;
			$
			.getJSON(
					'${getStockHeaderByPlantId}',
					{
						plantId : plantId,
						ajax : 'true',

					},
					function(data) {
						
						document.getElementById("date").value = data.startDate;

						
					});
		}
		</script>

	<script type="text/javascript">
		
		function allowOnlyNumber(evt){
			
			var charCode = (evt.which) ? evt.which : event.keyCode
		    if (charCode == 46){
		        var inputValue = $("#floor").val();
		        var count = (inputValue.match(/'.'/g) || []).length;
		        
		        if(count<1){
		            if (inputValue.indexOf('.') < 1){
		                return true;
		            }
		            return false;
		        }else{
		            return false;
		        }
		    }
		    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
		        return false;
		    }
		    return true;
		}
	</script>

	<script type="text/javascript">
		function hideDiv(type) {

			if (type == 1) {

				document.getElementById("hide_div").style = "display:none"
					document.getElementById("searchButton").style = "visible"
			} else {
				document.getElementById("hide_div").style = "visible"
					document.getElementById("searchButton").style = "display:none"

			}
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

</body>
</html>