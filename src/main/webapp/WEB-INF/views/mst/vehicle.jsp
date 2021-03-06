<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>
<c:url var="getUniqueUomNameCheck" value="/getUniqueUomNameCheck" />
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
							<strong>${title}</strong>
						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertVehicle"
								id="submitForm" method="post" enctype="multipart/form-data">




								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Vehicle Name*</div>
									<div class="col-md-4">
										<input type="text" id="vehName" name="vehName" maxlength="60"
											value="${editVeh.vehicleName}" class="form-control"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter correct Vehicle  Name')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>

									<div class="col-md-2">Company Name</div>

									<div class="col-md-4">
										<input type="text" id="compName" name="compName"
											value="${editVeh.vehCompName}" class="form-control"
											style="width: 100%;" autocomplete="off" maxlength="60"
											pattern="[a-zA-Z][a-zA-Z]*"
											oninvalid="setCustomValidity('Please enter Vehicle Company)"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

									<input type="hidden" id="vehId" name="vehId"
										value="${editVeh.vehicleId}">



								</div>
								<div class="form-group"></div>


								<div class="row">
									<div class="col-md-2">Load Capacity*</div>
									<div class="col-md-4">
										<input type="text" id="loadCapacity" name="loadCapacity"
											autocomplete="off" value="${editVeh.loadCapacity}"
											class="form-control"
											oninvalid="setCustomValidity('Please enter load capacity of Vehicle')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>


									<div class="col-md-2">Select UOM*</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required onchange="getData()">
											<option value="-1">Select</option>
											<c:forEach items="${uomList}" var="ulist">
												<c:choose>
													<c:when test="${ulist.uomId==editVeh.uomId}">
														<option value="${ulist.uomId}" selected>${ulist.uomName}</option>
													</c:when>
													<c:otherwise>
														<option value="${ulist.uomId}">${ulist.uomName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group"></div>


								<div class="row">
									<div class="col-md-2">Vehicle Number*</div>
									<div class="col-md-4">
										<input type="text" id="vehNum" name="vehNum"
											autocomplete="off" value="${editVeh.vehNo}"
											class="form-control"
											oninvalid="setCustomValidity('Please enter load capacity of Vehicle')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>


									<div class="col-md-2">Select Vehicle Type*</div>

									<div class="col-md-4">
										<select id="vehType" name="vehType" class="standardSelect"
											tabindex="1" required onchange="getData()">
											<option value="-1">Select</option>
											<c:forEach items="${vtypeList}" var="vtlist">
												<c:choose>
													<c:when test="${vtlist.vehTypeId==editVeh.vehicleType}">
														<option value="${vtlist.vehTypeId}" selected>${vtlist.vehTypeName}</option>
													</c:when>
													<c:otherwise>
														<option value="${vtlist.vehTypeId}">${vtlist.vehTypeName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>
								</div>


								<div class="form-group"></div>


								<div class="row">
									<div class="col-md-2">Vehicle Document 1</div>
									<div class="col-md-2">
										<input type="file" id="doc1" name="doc1"
											value="${editVeh.vehDoc1}" class="form-control"
											oninvalid="setCustomValidity('Please Select a Document')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">

									</div>


									<div class="col-md-2">
										<img id="Docc1" name="Docc1"
											<a href="${vehImgPath}${editVeh.vehDoc1}">Doc1 </a>>
									</div>


									<div class="col-md-2">Vehicle Document 2</div>
									<div class="col-md-2">
										<input type="file" id="doc2" name="doc2"
											value="${editVeh.vehDoc2}" class="form-control"
											oninvalid="setCustomValidity('Please Select a Document')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>



									<div class="col-md-2">
										<img id="Docc2" name="Docc2"
											<a
											href="${vehImgPath}${editVeh.vehDoc2}">Doc2</a>>
									</div>


								</div>
								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Vehicle Document 3</div>
									<div class="col-md-2">
										<input type="file" id="doc3" name="doc3"
											value="${editVeh.vehDoc3}" class="form-control"
											oninvalid="setCustomValidity('Please Select a Document')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>



									<div class="col-md-2">
										<img id="Docc3" name="Docc3"
											<a
											href="${vehImgPath}${editVeh.vehDoc3}">Doc3</a>>
									</div>

									<div class="col-md-2">Vehicle Document 4</div>
									<div class="col-md-2">
										<input type="file" id="doc4" name="doc4"
											value="${editVeh.vehDoc4}" class="form-control"
											oninvalid="setCustomValidity('Please Select a Document')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>

									<div class="col-md-2">
										<img id="Docc4" name="Docc4"
											<a
											href="${vehImgPath}${editVeh.vehDoc4}">Doc4</a>>
									</div>

								</div>
								<div class="form-group"></div>

								<div class="row">

									<div class="col-lg-2"></div>

									<div class="col-lg-2">

										<input type="submit" class="btn btn-primary" value="Submit"
											id="submitButton"
											style="align-content: center; width: 113px; margin-left: 40px;">
									</div>
									<div class="col-lg-2">
										<input type="reset" class="btn btn-primary" value="Clear"
											style="align-content: center; width: 113px; margin-left: 40px;">
									</div>
								</div>
							</form>
						</div>
						<div class="card-body card-block">

							<form
								action="${pageContext.request.contextPath}/deleteRecordofVeh"
								method="post">

								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">

									<thead>
										<tr>
											<th class="check" style="text-align: center; width: 5%;"><input
												type="checkbox" name="selAll" id="selAll" /></th>

											<th style="text-align: center; width: 5%;">Sr.</th>
											<th style="text-align: center">Vehicle Name</th>
											<th style="text-align: center">Vehicle Number</th>
											<th style="text-align: center">Company Name</th>
											<th style="text-align: center">Load Capacity</th>
											<th style="text-align: center">Vehicle Type</th>
											<th style="text-align: center; width: 5%;">Action</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${vehList}" var="veh" varStatus="count">
											<tr>

												<td><input type="checkbox" class="chk" name="vehIds"
													id="vehIds${count.index+1}" value="${veh.vehicleId}" /></td>

												<td style="text-align: center">${count.index+1}</td>


												<td style="text-align: left"><c:out
														value="${veh.vehicleName}" /></td>
												<td style="text-align: left"><c:out
														value="${veh.vehNo}" /></td>


												<td style="text-align: right"><c:out
														value="${veh.vehCompName}" /></td>




												<td style="text-align: right"><c:out
														value="${veh.loadCapacity}" /></td>

												<td style="text-align: left"><c:choose>
														<c:when test="${veh.vehicleType==1}">
														In
													</c:when>
														<c:when test="${veh.vehicleType==2}">
														Out
													</c:when>
														<c:when test="${veh.vehicleType==3}">
														Poklen
													</c:when>
													</c:choose></td>

												<td style="text-align: center"><a
													href="${pageContext.request.contextPath}/editVeh/${veh.vehicleId}"><i
														class="fa fa-edit" style="color: black"></i> <span
														class="text-muted"></span></a> &nbsp; <a
													href="${pageContext.request.contextPath}/deleteVeh/${veh.vehicleId}"
													onClick="return confirm('Are you sure want to delete this record');"><i
														class="fa fa-trash-o" style="color: black"></i></a></td>

											</tr>
										</c:forEach>
									</tbody>

								</table>
								<div class="col-lg-1">

									<input type="submit" class="btn btn-primary" value="Delete"
										id="deleteId"
										onClick="var checkedVals = $('.chk:checkbox:checked').map(function() { return this.value;}).get();checkedVals=checkedVals.join(',');if(checkedVals==''){alert('No Rows Selected');return false;	}else{   return confirm('Are you sure want to delete record');}"
										style="align-content: center; width: 113px; margin-left: 40px;">


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
			$('input[id$=enq_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

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
		function getUomNameCheck() {

			var uomName = $("#uomName").val();

			$.getJSON('${getUniqueUomNameCheck}', {

				uomName : uomName,

				ajax : 'true',

			}, function(data) {
				if (data.error == true) {
					alert("Measurement Unit Name Already Exist");

					document.getElementById("uomName").value = "";
					/* setTimeout(function() {
						document.getElementById("#deptName").focus();
					}, 100); */
					document.getElementById("submitButton").disabled = true;
				} else {
					document.getElementById("submitButton").disabled = false;

				}
			}

			);

		}
	</script>


	<script type="text/javascript">
		function readURL1(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#Docc1').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#doc1").change(function() {
			//	alert("Hi imgInp1");
			readURL1(this);
		});
	</script>

	<script type="text/javascript">
		function readURL2(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#Docc2').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#doc2").change(function() {
			//	alert("Hi imgInp1");
			readURL2(this);
		});
	</script>

	<script type="text/javascript">
		function readURL3(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#Docc3').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#doc3").change(function() {
			//	alert("Hi imgInp1");
			readURL3(this);
		});
	</script>

	<script type="text/javascript">
		function readURL4(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#Docc4').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#doc4").change(function() {
			//	alert("Hi imgInp1");
			readURL4(this);
		});
	</script>
</body>
</html>