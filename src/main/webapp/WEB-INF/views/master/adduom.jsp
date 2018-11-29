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

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<strong>${title}</strong>
						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertUom"
								id="submitForm" method="post">



								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Measurement Unit Name*</div>
									<div class="col-md-4">
										<input type="text" id="uomName" name="uomName"
											value="${editUom.uomName}" class="form-control"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter Uom Name')"
											onchange="getUomNameCheck()"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>

									<div class="col-md-2">Sort No(Optional)</div>

									<div class="col-md-4">
										<input type="text" id="sortNo" name="sortNo"
											value="${editUom.sortNo}" class="form-control"
											style="width: 100%;" autocomplete="off" maxlength="3"
											pattern="[0-9]+"
											oninvalid="setCustomValidity('Please enter Sort No')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

									<input type="hidden" id="uomId" name="uomId"
										value="${editUom.uomId}">

								</div>
								<div class="form-group"></div>


								<div class="row">
									<div class="col-md-2">Measurement Unit Short Name*</div>
									<div class="col-md-4">
										<input type="text" id="uomShortName" name="uomShortName"
											autocomplete="off" value="${editUom.uomShortName}"
											class="form-control"
											oninvalid="setCustomValidity('Please enter Uom Short Name')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>
									<div class="col-lg-2"></div>

									<div class="col-lg-2">

										<button type="submit" class="btn btn-primary"
											id="submitButton" name="submitButton"
											style="align-content: center; width: 113px; margin-left: 40px;">
											Submit</button>
									</div>

									<div class="col-lg-2">

										<button type="reset" class="btn btn-primary"
											style="align-content: center; width: 113px; margin-left: 40px;">
											Clear</button>
									</div>


								</div>

							</form>
						</div>



						<div class="card-body card-block">

							<table id="bootstrap-data-table"
								class="table table-striped table-bordered">
								<thead>
									<tr>

										<th style="text-align: center">Sr</th>
										<th style="text-align: center">Measurement Unit Name</th>
										<th style="text-align: center">Measurement Unit Short
											Name</th>
										<th style="text-align: center">Sort No</th>

										<th style="text-align: center; width: 5%;">Action</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach items="${uomList}" var="uom" varStatus="count">
										<tr>

											<td style="text-align: center">${count.index+1}</td>


											<td style="text-align: left"><c:out
													value="${uom.uomName}" /></td>

											<td style="text-align: left"><c:out
													value="${uom.uomShortName}" /></td>


											<td style="text-align: right"><c:out
													value="${uom.sortNo}" /></td>


											<td style="text-align: center"><a
												href="${pageContext.request.contextPath}/editUom/${uom.uomId}"><i
													class="fa fa-edit"></i> <span class="text-muted"></span></a>
												&nbsp; <a
												href="${pageContext.request.contextPath}/deleteUom/${uom.uomId}"
												onClick="return confirm('Are you sure want to delete this record');"><i
													class="fa fa-trash-o"></i></a></td>

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