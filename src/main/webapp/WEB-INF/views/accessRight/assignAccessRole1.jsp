<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>
<link rel="apple-touch-icon" href="apple-icon.png">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/favicon.ico">

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
	href="${pageContext.request.contextPath}/resources/assets/scss/style.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/menu.css">

<link
	href="${pageContext.request.contextPath}/resources/assets/css/lib/vector-map/jqvmap.min.css"
	rel="stylesheet">

<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800'
	rel='stylesheet' type='text/css'>


<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>


</head>


<!-- 
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script> -->


<body onload="setData()">
	<c:url var="getChartData" value="/getGraphDataForDistwiseOrderHistory"></c:url>

	<c:url var="getCatOrdQty" value="/getCatOrdQty"></c:url>

	<c:url var="getCatwiseTrend" value="/getCatwiseTrend"></c:url>


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

						</div>
						<div class="card-body card-block">
							<form id="validation-form" action="submitAssignedRole"
								method="post">

								<div class="box-content">

									<!-- <div class="col-md-2">Select User*</div>
									<div class="col-md-4" style="text-align: center">
										<input type="text" name="empName" id="empName"
											class="form-control" required data-rule-required="true"
											readonly>

									</div> -->

									<div class="col-md-2">Select User*</div>
									<div class="col-md-2">
										<select name="empId" id="empId" class="standardSelect"
											tabindex="1" required>


											<option value="" disabled selected>Select</option>


											<c:forEach items="${userList}" var="user" varStatus="count">
												<option value="${user.userId}"><c:out
														value="${user.usrName}" /></option>
											</c:forEach>

										</select>
									</div>

									<div class="col-md-2">Select Role*</div>
									<div class="col-md-2">
										<select name="role" id="role" class="standardSelect"
											tabindex="1" required>


											<option value="" disabled selected>Select</option>


											<c:forEach items="${createdRoleList}" var="createdRoleList"
												varStatus="count">
												<option value="${createdRoleList.roleId}"><c:out
														value="${createdRoleList.roleName}" /></option>
											</c:forEach>

										</select>
									</div>
									<div class="col-md-2"></div>
									<div class="col-md-4" style="text-align: center">
										<input type="submit" class="btn btn-primary" value="Submit"
											style="align-content: center; width: 113px; margin-left: 40px;">

									</div>

									<input type="hidden" id="empId" name="empId">
								</div>

								<%-- 	<div class="box-content">

									<div class="col-md-2">Select Role*</div>
									<div class="col-md-4" style="text-align: center">
										<select name="role" id="role" class="form-control"
											tabindex="6" required data-rule-required="true">


											<option value="" disabled selected>Select Role</option>


											<c:forEach items="${createdRoleList}" var="createdRoleList"
												varStatus="count">
												<option value="${createdRoleList.roleId}"><c:out
														value="${createdRoleList.roleName}" /></option>
											</c:forEach>

										</select>
									</div>


								</div> --%>
								<br /> <br /> <br />
								<!-- <div class="box-content">

									<div class="col-md-2"></div>
									<div class="col-md-4" style="text-align: center">
										<input type="submit" value="Submit" class="btn btn-info">

									</div>
								</div> -->
								<br /> <br />

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-12 table-responsive">
										<table class="table table-bordered table-striped fill-head "
											style="width: 70%" id="table_grid">
											<thead>
												<tr>
													<th>Sr.No.</th>
													<th>User Name</th>
													<th>Assigned Role</th>
													<!-- 	<th>Add/Edit/View</th> -->

												</tr>
											</thead>

											<tbody>

												<c:forEach items="${userList}" var="userList"
													varStatus="count">

													<tr>

														<c:set var="rId" value="" />
														<c:set var="empRoll" value="" />
														<c:set var="btnClass" value="glyphicon glyphicon-plus" />
														<c:set var="detail" value="" />
														<c:set var="add" value="Assign" />


														<td><c:out value="${count.index+1}" /></td>

														<td align="left"><c:out value="${userList.usrName}" /></td>



														<c:forEach items="${createdRoleList}"
															var="createdRoleList" varStatus="count">
															<c:choose>
																<c:when
																	test="${createdRoleList.roleId==userList.roleId}">
																	<c:set var="empRoll"
																		value="${createdRoleList.roleName}" />
																	<c:set var="rId" value="${createdRoleList.roleId}" />

																	<c:set var="btnClass" value="fa fa-edit" />
																	<c:set var="detail" value="fa fa-list" />
																	<c:set var="add" value="Edit" />
																</c:when>
															</c:choose>
														</c:forEach>

														<td align="left"><c:out value="${empRoll}" /></td>


														<%-- <td><span class='<c:out value="${btnClass}" />'
															data-toggle="tooltip" title='<c:out value="${add}" />'
															onclick="editRole('${userList.usrName}', ${userList.userId})"></span>
															<a
															href="${pageContext.request.contextPath}/showAssignUserDetail/<c:out value="${userList.userId}" />/${rId}/<c:out value="${userList.usrName}" />/${empRoll}"
															data-toggle="tooltip" title="Access Detail"
															style="color: black"> <span
																class='<c:out value="${detail}" />'></span>
														</a></td> --%>
													</tr>

												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>


							</form>
						</div>

					</div>
				</div>
			</div>

		</div>
	</div>

	<!-- END Main Content -->
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
		function editRole(empName, empId) {

			//alert(empId);
			document.getElementById("empId").value = empId;
			document.getElementById("empName").value = empName;

		}
	</script>



</body>
</html>