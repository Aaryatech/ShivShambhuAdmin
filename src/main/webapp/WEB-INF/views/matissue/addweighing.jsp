<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>
<c:url var="getContractrateById" value="/getContractrateById" />


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
								<strong>Data not Submitted</strong>
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
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showWeighList"><strong>Weighing
										List</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertWeighing"
								id="submitForm" method="post" enctype="multipart/form-data">
								<input type="hidden" name="weighId" id="weighId"
									value="${editWeigh.weighId}">

								<div class="row">

									<input type="hidden" id="url" value='${weighImageUrl}' /> <input
										type="hidden" name="prevImage1" id="prevImage1"> <input
										type="hidden" name="prevImage2" id="prevImage2">

									<div class="col-md-2">Select Vehicle*</div>

									<div class="col-md-4">
										<select id="vehId" name="vehId" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select Vehicle')">
											<option>Select</option>
											<c:forEach items="${vehList}" var="veh">

												<c:choose>
													<c:when test="${veh.vehicleId==editWeigh.vehId}">
														<option value="${veh.vehicleId}" selected>${veh.vehicleName}</option>
													</c:when>
													<c:otherwise>
														<option value="${veh.vehicleId}">${veh.vehicleName}
													</c:otherwise>
												</c:choose>


											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Select Poklen*</div>

									<div class="col-md-4">
										<select id="poklenId" name="poklenId" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select Poklen')">
											<option>Select</option>


											<c:forEach items="${vehPoklenList}" var="poklen">

												<c:choose>
													<c:when test="${poklen.vehicleId==editWeigh.poklenId}">
														<option value="${poklen.vehicleId}" selected>${poklen.vehicleName}</option>
													</c:when>
													<c:otherwise>
														<option value="${poklen.vehicleId}">${poklen.vehicleName}
													</c:otherwise>
												</c:choose>


											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Select Contractor*</div>

									<div class="col-md-4">
										<select id="contr_id" name="contr_id" class="standardSelect"
											tabindex="1" onchange="getContractRate()">
											<option value="-1">Select</option>
											<c:forEach items="${conList}" var="con">

												<c:choose>
													<c:when test="${con.contrId==editWeigh.contraId}">
														<option value="${con.contrId}" selected>${con.contrName}</option>
													</c:when>
													<c:otherwise>
														<option value="${con.contrId}">${con.contrName}
													</c:otherwise>
												</c:choose>


											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Contractor Rate*</div>
									<div class="col-md-4">
										<input type="text" id="rate" name="rate" class="form-control"
											autocomplete="off" style="width: 100%;"
											pattern="[0-9]+(\.[0-9]{0,2})?%?"
											value="${editWeigh.contRate}" readonly
											onkeypress="return allowOnlyNumber(event);">
									</div>


								</div>


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Quantity*</div>
									<div class="col-md-4">
										<input type="text" id="qty" name="qty" class="form-control"
											autocomplete="off" style="width: 100%;"
											value="${editWeigh.quantity}"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" required
											oninvalid="setCustomValidity('Please enter correct Quantity')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>
									<div class="col-md-2">Date*</div>
									<div class="col-md-4">
										<input type="text" id="date" name="date" autocomplete="off"
											oninvalid="setCustomValidity('Please select date')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editWeigh.date}" required class="form-control"
											required style="width: 100%;">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Vehicle Kilometer*</div>
									<div class="col-md-4">
										<input type="text" id="vehKm" name="vehKm"
											class="form-control" autocomplete="off" style="width: 100%;"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" required
											oninvalid="setCustomValidity('Please enter correct Vehicle Kilometer')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editWeigh.vehKm}">
									</div>

									<div class="col-md-2">Poklen Kilometer*</div>
									<div class="col-md-4">
										<input type="text" id="poklenKm" name="poklenKm"
											class="form-control" autocomplete="off" style="width: 100%;"
											pattern="[0-9]+(\.[0-9]{0,2})?%?"
											oninvalid="setCustomValidity('Please enter correct Vehicle Kilometer')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editWeigh.poklenKm}" required>
									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Add Photo1*</div>
									<div class="col-md-4">
										<input type="file" id="imgInp" name="imgInp"
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please Select photo')"
											value="${editWeigh.photo1}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">Add Photo2</div>
									<div class="col-md-4">
										<input type="file" id="imgInp1" name="imgInp1"
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter photo')"
											value="${editWeigh.photo2}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Remark</div>

									<div class="col-md-4">
										<textarea id="remark" name="remark" class="form-control"
											style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter remark')"
											maxlength="200"
											onchange="try{setCustomValidity('')}catch(e){}">${editWeigh.remark}</textarea>
									</div>


								</div>


								<div class="form-group"></div>
								<div class="col-lg-4"></div>
								<div class="col-lg-3">
									<input type="submit" class="btn btn-primary" value="Submit"
										id="submitButton"
										style="align-content: center; width: 113px; margin-left: 40px;">

								</div>
								<div class="col-lg-3">
									<input type="reset" class="btn btn-primary" value="Clear"
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
				disable_search_threshold : 2,
				no_results_text : "Oops, nothing found!",
				width : "100%"
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#bootstrap-data-table-export').DataTable();
		});
	</script>



	<script>
		function upperCaseF(a) {
			setTimeout(function() {
				a.value = a.value.toUpperCase();
			}, 1);
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
		function getContractRate() {
			//	alert("hiiiii");
			var contrId = document.getElementById("contr_id").value;
			//	alert(contrId);

			$.getJSON('${getContractrateById}', {

				contrId : contrId,
				ajax : 'true',

			},

			function(data) {

				document.getElementById("rate").value = data.contrRate;

			});

		}
	</script>

	<script type="text/javascript">
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#image1').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#imgInp").change(function() {
			readURL(this);
		});
	</script>

	<script type="text/javascript">
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#image2').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#imgInp1").change(function() {
			readURL(this);
		});
	</script>

</body>
</html>