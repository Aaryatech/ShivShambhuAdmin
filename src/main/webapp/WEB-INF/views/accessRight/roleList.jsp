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

						</div>
						<div class="card-body card-block">



							<table id="bootstrap-data-table"
								class="table table-striped table-bordered">
								<thead>
									<tr>

										<th style="text-align: center; width: 10%;">Sr No.</th>
										<th style="text-align: center">Role Name</th>
										<th style="text-align: center; width: 5%;">Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${createdRoleList}" var="createdRoleList"
										varStatus="count">
										<tr>

											<td style="text-align: center">${count.index+1}</td>

											<td style="text-align: left"><c:out
													value="${createdRoleList.roleName}" /></td>

											<td style="text-align: center"><a
												href="${pageContext.request.contextPath}/deleteRole/${createdRoleList.roleId}"
												onClick="return confirm('Are you sure want to delete this record');"><i
													class="fa fa-trash-o"  style="color:black" title="Delete"></i></a></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>

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


	<%-- 
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
							<form action="addRouteProcess" class="form-horizontal"
								method="post" id="validation-form"></form>
							<!-- newly added /form to be tested -->


							<div class="box">
								<div class="box-title">
									<h3>
										<i class="fa fa-table"></i> Roles List
									</h3>
									<div class="box-tool">
										<a data-action="collapse" href="#"><i
											class="fa fa-chevron-up"></i></a>
										<!--<a data-action="close" href="#"><i class="fa fa-times"></i></a>-->
									</div>
								</div>

								<div class="box-content">
									<div class="clearfix"></div>
									<div class="table-responsive" style="border: 0">
										<table width="100%" class="table table-advance" id="table1">
											<thead>
												<tr>
													<th width="45" style="width: 18px">#</th>
													<th width="939" align="left">Role Name</th>
													<th width="81" align="left">Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${createdRoleList}" var="createdRoleList"
													varStatus="count">
													<tr>
														<td><c:out value="${count.index+1}" /></td>
														<td align="left"><c:out
																value="${createdRoleList.roleName}"></c:out></td>
														<td align="left"><a
															href="${pageContext.request.contextPath}/deleteRole/${createdRoleList.roleId}"
															onClick="return confirm('Are you sure want to delete this record');"><span
																class="glyphicon glyphicon-remove"></span></a></td>
													</tr>

												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>


		</div>
		<!-- .animated -->
	</div> --%>


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


</body>
</html>