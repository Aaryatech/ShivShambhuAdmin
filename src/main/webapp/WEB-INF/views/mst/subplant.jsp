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
							<form action="${pageContext.request.contextPath}/insertSubplant"
								id="submitForm" method="post">



								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Select Plant*</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required onchange="getData()">
											<option value="-1">Select</option>
											<c:forEach items="${plantList}" var="plant">
												<c:choose>
													<c:when test="${plant.plantId==editSP.plantId}">
														<option value="${plant.plantId}" selected>${plant.plantName}</option>
													</c:when>
													<c:otherwise>
														<option value="${plant.plantId}">${plant.plantName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>




									<div class="col-md-2">Subplant Location*</div>
									<div class="col-md-4">
										<input type="text" id="spLoc" name="spLoc" maxlength="60"
											pattern="[a-zA-Z][a-zA-Z]*" value="${editSP.location}"
											class="form-control" onblur="getUomNameCheck()"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter correct Location')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>


									<input type="hidden" id="spId" name="spId"
										value="${editSP.subplantId}">

								</div>
								<div class="form-group"></div>


								<div class="row">
									<div class="col-md-2">Subplant Name*</div>
									<div class="col-md-4">
										<input type="text" id="spName" name="spName" maxlength="60"
											value="${editSP.subplantName}" class="form-control"
											onblur="getUomNameCheck()" autocomplete="off"
											oninvalid="setCustomValidity('Please enter correct Name')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>


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
								action="${pageContext.request.contextPath}/deleteRecordofSP"
								method="post">

								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">

									<thead>
										<tr>
											<th class="check" style="text-align: center; width: 5%;"><input
												type="checkbox" name="selAll" id="selAll" /></th>

											<th style="text-align: center; width: 5%;">Sr</th>
											<th style="text-align: center">Sub Plant Name</th>
											<th style="text-align: center">Location</th>


											<th style="text-align: center; width: 5%;">Action</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${spList}" var="sp" varStatus="count">
											<tr>

												<td><input type="checkbox" class="chk" name="spIds"
													id="spIds${count.index+1}" value="${sp.subplantId}" /></td>

												<td style="text-align: center">${count.index+1}</td>


												<td style="text-align: left"><c:out
														value="${sp.subplantName}" /></td>

												<td style="text-align: left"><c:out
														value="${sp.location}" /></td>





												<td style="text-align: center"><a
													href="${pageContext.request.contextPath}/editSP/${sp.subplantId}"><i
														class="fa fa-edit" style="color:black"></i> <span class="text-muted"></span></a>
													&nbsp; <a
													href="${pageContext.request.contextPath}/deleteSP/${sp.subplantId}"
													onClick="return confirm('Are you sure want to delete this record');"><i
														class="fa fa-trash-o" style="color:black"></i></a></td>

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

</body>
</html>