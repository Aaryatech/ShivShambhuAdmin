<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getPlantByCompanyId" value="/getPlantByCompanyId" />
<c:url var="getUniqueUserMobCheck" value="/getUniqueUserMobCheck" />

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

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- css for date picker proper UI -->

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
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showUserList"><strong>User
										List</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertUser"
								id="submitForm" method="post">

								<div class="row">

									<div class="col-md-2">Select Company*</div>

									<div class="col-md-4">
										<select id="company_id" name="company_id"
											class="standardSelect" tabindex="1" onchange="getData()">
											<option value="">Select</option>
											<c:forEach items="${compList}" var="comp">

												<c:choose>
													<c:when test="${comp.companyId==editUser.companyId}">
														<option value="${comp.companyId}" selected>${comp.compName}</option>
													</c:when>
													<c:otherwise>
														<option value="${comp.companyId}">${comp.compName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>

								<%-- 	<div class="col-md-2">Select Department*</div>

									<div class="col-md-4">
										<select id="dept_id" name="dept_id" class="standardSelect"
											tabindex="1" required>
											<option value="">Select</option>
											<c:forEach items="${deptList}" var="dept">

												<c:choose>
													<c:when test="${dept.deptId==editUser.deptId}">
														<option value="${dept.deptId}" selected>${dept.deptName}</option>
													</c:when>
													<c:otherwise>
														<option value="${dept.deptId}">${dept.deptName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>
 --%>
									<%-- 
									<div class="col-md-2">Select Plant*</div>

									<div class="col-md-4">
										<select id="plant_name" name="plant_id" class="standardSelect"
											tabindex="1" required>
											<option value="">Select</option>
											<c:forEach items="${plantList}" var="plant">

												<c:choose>
													<c:when test="${plant.plantId==editUser.plantId}">
														<option value="${plant.plantId}" selected>${plant.plantName}</option>
													</c:when>
													<c:otherwise>
														<option value="${plant.plantId}">${plant.plantName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>

 --%>
								</div>








								<input type="hidden" name="userId" id="userId"
									value="${editUser.userId}">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">User Name*</div>
									<div class="col-md-4">
										<input type="text" id="usrName" name="usrName"
											autocomplete="off" pattern="^[A-Za-z\s]+$"  maxlength="60"
											oninvalid="setCustomValidity('Please enter correct user name')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											value="${editUser.usrName}" class="form-control"
											style="width: 100%;">
									</div>

									<div class="col-md-2">User Mobile No*</div>

									<div class="col-md-4">
										<input type="text" id="usrMob" name="usrMob"
											autocomplete="off" value="${editUser.usrMob}"
											class="form-control"
											oninvalid="setCustomValidity('Please enter correct mob no')"
											pattern="^[1-9]{1}[0-9]{9}$" maxlength="10"
											onchange="getMobileNoCheck()"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Date of Birth*</div>
									<div class="col-md-4">
										<input type="text" id="usrDob" name="usrDob" required
											autocomplete="off" value="${editUser.usrDob}"
											class="form-control" onchange="checkDOB()"
											oninvalid="setCustomValidity('Please select Date')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>

									<div class="col-md-2">Sort No(Optional)</div>
									<div class="col-md-4">
										<input type="text" id="sortNo" name="sortNo"
											autocomplete="off" value="${editUser.sortNo}"
											class="form-control" maxlength="3"
											oninvalid="setCustomValidity('Please enter correct Sort No')"
											onchange="try{setCustomValidity('')}catch(e){}"
											pattern="[0-9]+" style="width: 100%;">
									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">User Email*</div>
									<div class="col-md-4">
										<input type="email" autocomplete="off" id="usrEmail"
											name="usrEmail" value="${editUser.usrEmail}"
											class="form-control" maxlength="50"
											oninvalid="setCustomValidity('Please enter correct email')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;" required>
										<!-- 	pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"	 -->
									</div>
									<div class="col-md-2">User Password*</div>
									<div class="col-md-4">
										<input type="password" id="userPass" name="userPass"
											autocomplete="off" maxlength="20"
											oninvalid="setCustomValidity('Please enter Password')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editUser.userPass}" class="form-control" required
											style="width: 100%;">
									</div>
								</div>
								<div class="form-group"></div>

								<div class="col-lg-4"></div>
								<div class="col-lg-2">
									<input type="submit" class="btn btn-primary" value="Submit"
										id="submitButton"
										style="align-content: center; width: 113px; margin-left: 40px;">

								</div>
								<div class="col-lg-2">
									<button type="reset" class="btn btn-primary"
										style="align-content: center; width: 113px; margin-left: 40px;">
										Clear</button>
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
		jQuery(document).ready(function() {
			jQuery(".standardSelect").chosen({
				disable_search_threshold : 2,
				no_results_text : "Oops, nothing found!",
				width : "100%"
			});
		});
	</script>


	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=usrDob]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>
	<script type="text/javascript">
		function getData() {
			var companyId = document.getElementById("company_id").value;
			var valid = true;

			if (companyId == null || companyId == "") {
				valid = false;
				alert("Please Select Company");
			}

			if (valid == true) {

				$.getJSON('${getPlantByCompanyId}', {

					companyId : companyId,
					ajax : 'true',

				},

				function(data) {
					var html;
					var len = data.length;
					var html = '<option value="-1"  >Select</option>';
					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].plantId + '">'
								+ data[i].plantName + '</option>';
					}
					html += '</option>';

					$('#plant_name').html(html);
					$("#plant_name").trigger("chosen:updated");

				});

			}
		}
	</script>



	<script type="text/javascript">
		function getMobileNoCheck() {

			var usrMob = $("#usrMob").val();

			$.getJSON('${getUniqueUserMobCheck}', {

				usrMob : usrMob,

				ajax : 'true',

			}, function(data) {
				if (data.error == true) {
					alert("User Mobile No Already Exist");

					document.getElementById("usrMob").value = "";
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
		function checkDOB() {
			//In javascript
			var dateEntered = document.getElementById("usrDob").value;
			// In JQuery
			var dateEntered = $("#usrDob").val();

			var date = dateEntered.substring(0, 2);
			var month = dateEntered.substring(3, 5);
			var year = dateEntered.substring(6, 10);

			var dateToCompare = new Date(year, month - 1, date);
			var currentDate = new Date();

			if (dateToCompare > currentDate) {
				alert("Please enter DOB less than Current Date ");
				document.getElementById('usrDob').value = "";
			}

		}
	</script>


</body>
</html>