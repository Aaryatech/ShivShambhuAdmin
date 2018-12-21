<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getItemsByPlantId" value="/getItemsByPlantId" />

<c:url var="getCustByPlantId" value="/getCustByPlantId" />

<c:url var="getItemByItemId" value="/getItemByItemId" />

<c:url var="addDocTermDetail" value="/addDocTermDetail" />

<c:url var="getDocTermForEdit" value="/getDocTermForEdit" />
<c:url var="getUniqueEnqGenBy" value="/getUniqueEnqGenBy" />
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
								<strong> Enquiry Source Already Exist</strong>
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
							<form
								action="${pageContext.request.contextPath}/insertEnqGenFact"
								id="submitForm" method="post"
								onsubmit="return onEnqSourceInput()">



								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-3">Enquiry Source*</div>
									<div class="col-md-4">
										<input type="text" id="enqGenBy" name="enqGenBy"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter correct enquiry source')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editEnqGen.enqGenBy}" class="form-control" required
											style="width: 100%;">
									</div>
									<div class="col-lg-2">
										<input type="submit" class="btn btn-primary" value="Submit"
											style="align-content: center; width: 113px; margin-left: 20px;">

									</div>
									<div class="col-lg-2">

										<input type="reset" class="btn btn-primary" value="Clear"
											style="align-content: center; width: 113px; margin-left: 20px;">
									</div>
									<input type="hidden" id="enqGenId" name="enqGenId"
										value="${editEnqGen.enqGenId}">
								</div>
							</form>
						</div>
						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/deleteRecordofEnqGenFact"
								method="post">

								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">

									<thead>
										<tr>
											<th class="check" style="text-align: center; width: 5%;"><input
												type="checkbox" name="selAll" id="selAll" /></th>
											<th style="text-align: center; width: 5%;">Sr.</th>
											<th style="text-align: center">Enquiry Source</th>
											<th style="text-align: center; width: 5%;">Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${enqGenFactList}" var="enqGenFact"
											varStatus="count">
											<tr>
												<td><input type="checkbox" class="chk" name="enqGenIds"
													id="enqGenIds${count.index+1}"
													value="${enqGenFact.enqGenId}" /></td>
												<td style="text-align: center">${count.index+1}</td>

												<td style="text-align: left"><c:out
														value="${enqGenFact.enqGenBy}" /></td>

												<td style="text-align: center"><a
													href="${pageContext.request.contextPath}/editEnqGenFact/${enqGenFact.enqGenId}"><i
														class="fa fa-edit"></i> <span class="text-muted"></span></a>
													&nbsp; <a
													href="${pageContext.request.contextPath}/deleteEnqGenFact/${enqGenFact.enqGenId}"
													onClick="return confirm('Are you sure want to delete this record');"><i
														class="fa fa-trash-o"></i></a></td>

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
		function onEnqSourceInput() {

			var enqGenBy = $("#enqGenBy").val();

			$.getJSON('${getUniqueEnqGenBy}', {

				enqGenBy : enqGenBy,

				ajax : 'true',

			}, function(data) {
				if (data.error == true) {
					alert("Enquiry Source Already Exist");

					document.getElementById("enqGenBy").value = "";
					/* setTimeout(function() {
						document.getElementById("#deptName").focus();
					}, 100); */
					//document.getElementById("submitButton").disabled = true;
				} else {
					//document.getElementById("submitButton").disabled = false;

				}
			}

			);

		}
	</script>

</body>
</html>