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
							<div class="col-md-4">
								<strong>${title}</strong>
							</div>
							<div class="col-md-4"></div>
							<div class="col-md-4" align="left">
								<a
									href="${pageContext.request.contextPath}/showPoklenReadingList"
									style="color: black"><strong>Poklen Reading List</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/insertEditedPoklenReading"
								id="submitForm" method="post">
								<input type="hidden" name="readingId" id="readingId"
									value="${editPRead.readingId}">

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Select Poklen*</div>

									<div class="col-md-4">
										<select id="poklenId" name="poklenId" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select Poklen')">
											<option>Select</option>


											<c:forEach items="${vehPoklenList}" var="poklen">

												<c:choose>
													<c:when test="${poklen.vehicleId==editPRead.poklenId}">
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
									<div class="col-md-2">Start Date*</div>
									<div class="col-md-4">
										<input type="text" id="start_date" name="start_date"
											autocomplete="off" value="${editPRead.startDate}"
											oninvalid="setCustomValidity('Please select date')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" required style="width: 100%;">
									</div>

									<div class="col-md-2">End Date</div>

									<div class="col-md-4">
										<input type="text" id="end_date" name="end_date"
											autocomplete="off" value="${endDate}"
											oninvalid="setCustomValidity('Please select date')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Poke Type*</div>

									<c:choose>
										<c:when test="${editPRead.pokType==0}">
											<div class="col-md-2">

												<input type="radio" name="pokeType" id="pokeType" value="0"
													checked>Breaking

											</div>

											<div class="col-md-2">
												<input type="radio" name="pokeType" value="1">
												Loading
											</div>
										</c:when>
										<c:when test="${editPRead.pokType==1}">
											<div class="col-md-2">

												<input type="radio" name="pokeType" id="pokeType" value="0">Breaking
											</div>
											<div class="col-md-2">
												<input type="radio" name="pokeType" value="1" checked>
												Loading

											</div>
										</c:when>
										<c:otherwise>
											<div class="col-md-2">
												Breaking <input type="radio" checked name="pokeType"
													id="pokeType" value="0">
											</div>

											<div class="col-md-2">
												Loading <input type="radio" name="pokeType" id="pokeType"
													value="1">
											</div>

										</c:otherwise>
									</c:choose>



									<div class="col-md-2">Shift Type*</div>

									<c:choose>
										<c:when test="${editPRead.shiftType==0}">
											<div class="col-md-2">

												<input type="radio" name="sType" id="sType" value="0"
													checked>Day

											</div>

											<div class="col-md-2">
												<input type="radio" name="sType" value="1"> Night
											</div>
										</c:when>
										<c:when test="${editPRead.shiftType==1}">
											<div class="col-md-1">

												<input type="radio" name="sType" id="sType" value="0">Day
											</div>
											<div class="col-md-1">
												<input type="radio" name="sType" value="1" checked>
												Night
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-md-1">
												Day <input type="radio" checked name="sType" id="sType"
													value="0">
											</div>

											<div class="col-md-1">
												Night <input type="radio" name="sType" id="sType" value="1">
											</div>

										</c:otherwise>
									</c:choose>


								</div>


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Start Reading*</div>
									<div class="col-md-4">
										<input type="text" id="startReading" name="startReading"
											class="form-control" autocomplete="off" style="width: 100%;"
											value="${editPRead.startReading}" onchange="checkReading()"
											oninvalid="setCustomValidity('Please enter correct reading')"
											onchange="try{setCustomValidity('')}catch(e){}"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" required
											onkeypress="return allowOnlyNumber(event);">
									</div>
									<div class="col-md-2">End Reading</div>
									<div class="col-md-4">
										<input type="text" id="endReading" name="endReading"
											value="${endReading}" class="form-control" autocomplete="off"
											style="width: 100%;"
											oninvalid="setCustomValidity('Please enter correct reading')"
											onchange="try{setCustomValidity('')}catch(e){}"
											onkeypress="return allowOnlyNumber(event);">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Start Time*</div>
									<div class="col-md-4">
										<input type="time" id="startTime" name="startTime"
											class="form-control" autocomplete="off" style="width: 100%;"
											oninvalid="setCustomValidity('Please enter time')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											value="${editPRead.startTime}">
									</div>

									<div class="col-md-2">End Time</div>
									<div class="col-md-4">
										<input type="time" id="endTime" name="endTime"
											value="${endTime}" class="form-control" autocomplete="off"
											style="width: 100%;"
											oninvalid="setCustomValidity('Please enter endtime')"
											onchange="try{setCustomValidity('')}catch(e){}">
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

	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=start_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$('input[id$=end_date]').datepicker({
				dateFormat : 'dd-mm-yy'

			});
		});
	</script>

	<script type="text/javascript">
		function checkDate() {
			//In javascript
			var start_date = document.getElementById("start_date").value;
			var end_date = document.getElementById("end_date").value;
			// In JQuery

			if (start_date > end_date) {
				alert("Please enter startdate less than end Date ");
				document.getElementById('start_date').value = "";
				document.getElementById('end_date').value = "";

			}

		}
	</script>




	<!-- <script type="text/javascript">
		function checkReading() {
			//In javascript
			var startReading = document.getElementById("startReading").value;
			var endReading = document.getElementById("endReading").value;
			// In JQuery

			if (startReading > endReading) {
				alert("Please enter correct Start Reading  ");
				document.getElementById('startReading').value = "";
				document.getElementById('endReading').value = "";

			}

		}
	</script>
 -->

</body>
</html>