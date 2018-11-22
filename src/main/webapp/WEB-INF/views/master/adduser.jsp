<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>
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
								method="post">

								<div class="row">

									<div class="col-md-2">Select Plant*</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required>
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

									<div class="col-md-2">Select Company*</div>

									<div class="col-md-4">
										<select id="company_id" name="company_id"
											class="standardSelect" tabindex="1" required>
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

								</div>
								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Select Dept*</div>

									<div class="col-md-4">
										<select id="dept_id" name="dept_id" class="standardSelect"
											tabindex="1" required>
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



								</div>
								<input type="hidden" name="userId" id="userId"
									value="${editUser.userId}">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">User Name*</div>
									<div class="col-md-4">
										<input type="text" id="usrName" name="usrName"
											oninvalid="setCustomValidity('Please enter User Name')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											value="${editUser.usrName}" class="form-control"
											style="width: 100%;">
									</div>

									<div class="col-md-2">User Mobile No*</div>

									<div class="col-md-4">
										<input type="text" id="usrMob" name="usrMob"
											value="${editUser.usrMob}" class="form-control"
											oninvalid="setCustomValidity('Please enter mob no')"
											maxlength="10" pattern="[0-9]+"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Date of Birth*</div>
									<div class="col-md-4">
										<input type="text" id="usrDob" name="usrDob"
											value="${editUser.usrDob}" class="form-control"
											oninvalid="setCustomValidity('Please enter Date')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>
									<div class="col-md-2">Sort No*</div>
									<div class="col-md-4">
										<input type="text" id="sortNo" name="sortNo"
											value="${editUser.sortNo}" class="form-control"
											oninvalid="setCustomValidity('Please enter Sort No')"
											onchange="try{setCustomValidity('')}catch(e){}"
											pattern="[0-9]+" required style="width: 100%;">
									</div>
								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">User Email*</div>
									<div class="col-md-4">
										<input type="text" id="usrEmail" name="usrEmail"
											value="${editUser.usrEmail}" class="form-control"
											oninvalid="setCustomValidity('Please enter email')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;" style="width: 100%;" required>
									</div>

									<div class="col-md-2">User Password*</div>

									<div class="col-md-4">
										<input type="password" id="userPass" name="userPass"
											oninvalid="setCustomValidity('Please enter Password')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editUser.userPass}" class="form-control" required
											style="width: 100%;">
									</div>

								</div>

								<div class="form-group"></div>


								<div class="col-lg-12" align="center">


									<button type="submit" class="btn btn-primary"
										style="align-content: center; width: 226px; margin-left: 80px;">
										Submit</button>
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






</body>
</html>