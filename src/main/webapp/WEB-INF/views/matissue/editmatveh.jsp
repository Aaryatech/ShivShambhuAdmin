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
							<div class="col-md-4" align="left">
								<a
									href="${pageContext.request.contextPath}/showMatIssueVehicleList"
									style="color: black"><strong>Material Issue
										Vehicle List</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/updateMaterialVehicle"
								id="submitForm" method="post">


								<div class="row">

									<div class="col-md-2">Select Vehicle*</div>

									<div class="col-md-4">
										<select id="vehId" name="vehId" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select vehicle')"
											onchange="getData()">
											<option>Select</option>
											<c:forEach items="${vehList}" var="veh">

												<c:choose>
													<c:when test="${veh.vehicleId==editVeh.vehId}">
														<option value="${veh.vehicleId}" selected>${veh.vehicleName}</option>
													</c:when>
													<c:otherwise>
														<option value="${veh.vehicleId}">${veh.vehicleName}
													</c:otherwise>
												</c:choose>


											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Date*</div>
									<div class="col-md-4">
										<input type="text" id="date" name="date" autocomplete="off"
											value="${editVeh.date}" required class="form-control"
											required style="width: 100%;">
									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Vehicle No*</div>
									<div class="col-md-4">
										<input type="text" id="vehNo" name="vehNo" maxlength="10"
											value="${editVeh.vehNo}" class="form-control" readonly
											style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter Vehicle No')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											pattern="[0-9]+">
									</div>

									<input type="hidden" value="${editVeh.matVehHeaderId}"
										name="matVehHeaderId" id="matVehHeaderId">

									<div class="col-md-2">Reading*</div>
									<div class="col-md-4">
										<input type="text" id="reading" name="reading" maxlength="10"
											value="${editVeh.reading}" class="form-control"
											style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter correct Reading ')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											pattern="[0-9]+(\.[0-9]{0,2})?%?">
									</div>

								</div>
								<hr>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Select Category*</div>
									<div class="col-md-4">
										<select id="catId" name="catId" class="standardSelect"
											tabindex="1" onchange="getItemByCatId()">

											<option>Select</option>
											<c:forEach items="${catList}" var="cat">
												<option value="${cat.catId}">${cat.catDesc}</option>
											</c:forEach>
										</select>
									</div>


									<div class="col-md-2">Select Item*</div>

									<div class="col-md-4">
										<select id="itemName" name="itemName" class="standardSelect"
											tabindex="1">
											<option>Select</option>

										</select>
									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Quantity</div>
									<div class="col-md-4">
										<input type="text" id="qty" name="qty" class="form-control"
											autocomplete="off" style="width: 100%;"
											pattern="[0-9]+(\.[0-9]{0,2})?%?"
											onkeypress="return allowOnlyNumber(event);">
									</div>
									<div class="col-md-2"></div>
									<div class="col-md-2">
										<input type="button" value="Add" class="btn btn-primary"
											style="align-content: center; width: 113px;" onclick="add()" />
									</div>
								</div>

								<input type="hidden" id="isDelete" name="isDelete" value="0">
								<input type="hidden" name="isEdit" id="isEdit" value="0">
								<input type="hidden" name="index" id="index" value="0">
								<div class="form-group"></div>




								<div class="card-body card-block">

									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>

												<th style="text-align: center; width: 5%;">Sr No</th>
												<th style="text-align: center">Item Name</th>
												<th style="text-align: center">Measurement Unit</th>
												<th style="text-align: center">Item Quantity</th>
												<th style="text-align: center">Item Rate</th>

												<th style="text-align: center">Value</th>
												<th style="text-align: center; width: 5%;">Action</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${editVehDetail}" var="matDetail"
												varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}</td>

													<td style="text-align: left"><c:out
															value="${matDetail.itemDesc}" /></td>


													<td style="text-align: left"><c:out
															value="${matDetail.uomName}" /></td>

													<td style="text-align: left"><c:out
															value="${matDetail.quantity}" /></td>


													<td style="text-align: left"><c:out
															value="${matDetail.rate}" /></td>

													<td style="text-align: left"><c:out
															value="${matDetail.value}" /></td>




													<%-- <td align="right"><input class="form-control"
														id="itemDesc${matDetail.matDetailId}"
														placeholder="Term Desc" type="text"
														name="itemDesc${docDetail.matDetailId}"
														value="${matDetail.itemDesc}" /></td> --%>




													<%-- <td align="right"><input class="form-control"
														id="detailSortNo${matDetail.uomName}"
														placeholder="Sort No" type="text"
														name="detailSortNo${matDetail.uomName}"
														value="${matDetail.sortNo}" /></td>
 --%>
													<%-- 	 --%>




													<td style="text-align: center"><a href="#"
														onclick="callEdit(${matDetail.matVehDetailId},${count.index})"><i
															class="fa fa-edit"></i> <span class="text-muted"
															style="color: black"></span></a> <a href="#"
														onclick="callDelete(${matDetail.matVehDetailId},${count.index})"><i
															class="fa fa fa-trash-o"></i> <span class="text-muted"
															style="color: black"></span></a></td>




												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>

								<div class="form-group"></div>
								<div class="col-lg-4"></div>
								<div class="col-lg-2">

									<input type="submit" class="btn btn-primary" value="Submit"
										style="align-content: center; width: 113px; margin-left: 20px;">

								</div>
								<div class="col-lg-2">

									<input type="reset" class="btn btn-primary" value="Clear"
										style="align-content: center; width: 113px; margin-left: 20px;">

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
		function add() {
			alert("in add  ");
			var itemName = document.getElementById("itemName").value;
			var qty = document.getElementById("qty").value;
			var isEdit = document.getElementById("isEdit").value;
			var isDelete = document.getElementById("isDelete").value;
			var index = document.getElementById("index").value;
			var matVehHeaderId = document.getElementById("matVehHeaderId").value;
			var catId = document.getElementById("catId").value;

			
			$
					.getJSON(
							'${editInAddMatVehicleDetail}',
							{

								isDelete : isDelete,
								isEdit : isEdit,
								index : index,
								itemName : itemName,
								qty : qty,
								matVehHeaderId : matVehHeaderId,
								catId : catId,
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
													
													if (v.exInt2 == 1) {
														alert("Item Already Added");
													}


													var str = '<a href="#" class="action_btn" onclick="callEdit('
															+ v.matVehDetailId
															+ ','
															+ i
															+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete('
															+ v.matVehDetailId
															+ ','
															+ i
															+ ')" style="color:black"><i class="fa fa-trash"></i></a>'

													dataTable.row.add(
															[ i + 1,
																	v.itemDesc,
																	v.uomName,
																	v.quantity,
																	v.rate,
																	
																	v.value,
																	str ])
															.draw();
												});

							});
			document.getElementById("itemName").value = "";
			document.getElementById("qty").value = "";
			document.getElementById("isDelete").value = 0;
			document.getElementById("isEdit").value = 0;
			document.getElementById("index").value = 0;

		}

		function callEdit(matVehDetailId, index) {
 
			document.getElementById("isEdit").value = "1";
			$.getJSON('${getIndexForEditMatVehicle}', {
				matVehDetailId : matVehDetailId,
				index : index,
				ajax : 'true',

			}, function(data) {
				$("#catId").val(data.exInt1);
				$("#catId").trigger("chosen:updated");
				document.getElementById("itemName").value = data.itemId;
				document.getElementById("qty").value = data.quantity;
				document.getElementById("index").value = index;
				
				$.getJSON('${getRawItemByCatId}', {

					catId : data.exInt1,
					ajax : 'true',

				},

				function(data1) {
					//alert("hiii");
					var html;
					var len = data1.length;
					var html = '<option value="-1"  >Select</option>';
					for (var i = 0; i < len; i++) {

						html += '<option value="' + data1[i].itemId + '">'
								+ data1[i].itemDesc + '</option>';
					}
					//html += '</option>';

					$('#itemName').html(html);
					//$("#itemName").trigger("chosen:updated");
					
					$("#itemName").val(data.itemId);
					$("#itemName").trigger("chosen:updated");

				});

			});

		}

		function callDelete(matVehDetailId, index) {

			//alert("hii");
			
			document.getElementById("isEdit").value = 0;
			//alert("index" + index);
			$
					.getJSON(
							'${editInAddMatVehicleDetail}',
							{
								isDelete : 1,
								isEdit : 0,
								key : index,
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

													var str = '<a href="#" class="action_btn" onclick="callEdit('
														+ v.matVehDetailId
														+ ','
														+ i
														+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete('
														+ v.matVehDetailId
														+ ','
														+ i
														+ ')" style="color:black"><i class="fa fa-trash"></i></a>'

													dataTable.row.add(
															[ i + 1,
																	v.itemDesc,
																	v.uomName,
																	v.rate,
																	v.quantity,
																	v.value,
																	str ])
															.draw();
												});
							});

		}

		
	</script>

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





	<script type="text/javascript">
		function getItemByCatId() {
			//document.getElementById("submitButton").disabled=true;

			var catId = document.getElementById("catId").value;
			//	alert("catId Id " + catId);

			var valid = true;

			if (catId == null || catId == "") {
				valid = false;
				alert("Please Select Category");
			}

			if (valid == true) {

				$.getJSON('${getRawItemByCatId}', {

					catId : catId,
					ajax : 'true',

				},

				function(data) {
					//alert("hiii");
					var html;
					var len = data.length;
					var html = '<option value="-1"  >Select</option>';
					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].itemId + '">'
								+ data[i].itemDesc + '</option>';
					}
					html += '</option>';

					$('#itemName').html(html);
					$("#itemName").trigger("chosen:updated");

				});

			}

		}
	</script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>

</body>
</html>