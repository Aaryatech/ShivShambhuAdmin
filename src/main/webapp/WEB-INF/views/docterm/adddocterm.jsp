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
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showDocTermList"><strong>Doc
										Term List</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertDocTerm"
								method="post">

								<div class="row">

									<div class="col-md-2">Select Document</div>

									<div class="col-md-4">
										<select id="doc_id" name="doc_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select Document name')"
											onchange="getData()">
											<option value="">Select Document</option>

											<c:forEach items="${docList}" var="doc">
												<option value="${doc.docId}">${doc.docName}</option>
											</c:forEach>
										</select>
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Term Title</div>
									<div class="col-md-4">
										<input type="text" id="termTitle" name="termTitle"
											class="form-control" style="width: 100%;"
											oninvalid="setCustomValidity('Please enter term title')"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>



									<div class="col-md-2">Sort No</div>

									<div class="col-md-4">
										<input type="text" id="sortNo" name="sortNo"
											class="form-control" style="width: 100%;"
											oninvalid="setCustomValidity('Please enter Sort No')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											pattern="[0-9]+">
									</div>



								</div>

								<hr>

								<br>
								<section class="form-control" style="color: red;">
									<div class="form-group"></div>
									<div class="row">
										<div class="col-md-2">Term Desc</div>
										<div class="col-md-3">
											<input type="text" id="termDesc" name="termDesc"
												class="form-control" style="width: 100%;">
										</div>



										<div class="col-md-1">Sort No</div>

										<div class="col-md-3">
											<input type="text" id="sortNoDetail" name="sortNoDetail"
												class="form-control" style="width: 100%;">
										</div>
										<div class="col-md-1"></div>

										<div class="col-md-2">
											<input type="button" value="Add" class="btn btn-primary"
												style="align-content: center; width: 113px;" onclick="add()" />

										</div>


									</div>
									<input type="hidden" id="isDelete" name="isDelete" value="0">
									<input type="hidden" name="isEdit" id="isEdit" value="0">
									<input type="hidden" name="index" id="index" value="0">
									<div class="form-group"></div>

								</section>




								<div class="card-body card-block">

									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>

												<th style="text-align: center">Sr</th>
												<th style="text-align: center">Term Desc</th>
												<th style="text-align: center">Sort No</th>

												<th style="text-align: center; width: 5%;">Action</th>

											</tr>
										</thead>

									</table>


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
		function add() {
			//	alert("in add  ");
			var termDesc = document.getElementById("termDesc").value;
			var isDelete = document.getElementById("isDelete").value;
			var sortNoDetail = document.getElementById("sortNoDetail").value;
			var isEdit = document.getElementById("isEdit").value;
			var index = document.getElementById("index").value;

			//alert("Inside add ajax");
			$
					.getJSON(
							'${addDocTermDetail}',
							{

								isDelete : isDelete,
								isEdit : isEdit,
								index : index,
								termDesc : termDesc,
								sortNoDetail : sortNoDetail,
								ajax : 'true',

							},

							function(data) {
								var dataTable = $('#bootstrap-data-table')
										.DataTable();
								dataTable.clear().draw();

								$
										.each(
												data,
												function(i, v) {

													var str = '<a href="#" class="action_btn" onclick="callDelete('
															+ v.termDetailId
															+ ','
															+ i
															+ ')"><i class="fa fa-trash"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callEdit('
															+ v.termDetailId
															+ ','
															+ i
															+ ')"><i class="fa fa-edit"></i></a>'

													dataTable.row.add(
															[ i + 1,
																	v.termDesc,
																	v.sortNo,
																	str ])
															.draw();
												});

							});
			document.getElementById("termDesc").value = "";
			document.getElementById("sortNoDetail").value = "";
			document.getElementById("isDelete").value = 0;
			document.getElementById("isEdit").value = 0;
			document.getElementById("index").value = 0;

		}

		function callEdit(termDetailId, index) {

			document.getElementById("isEdit").value = "1";

			$.getJSON('${getDocTermForEdit}', {
				termDetailId : termDetailId,
				index : index,
				ajax : 'true',

			}, function(data) {

				document.getElementById("sortNoDetail").value = data.sortNo;
				document.getElementById("termDesc").value = data.termDesc;
				document.getElementById("index").value = index;

			});

		}

		function callDelete(termDetailId, index) {
			//document.getElementById("isEdit").value = 0;
			//alert("index" + index);
			$
					.getJSON(
							'${addDocTermDetail}',
							{
								isDelete : 1,
								key : index,
								ajax : 'true',

							},

							function(data) {
								var dataTable = $('#bootstrap-data-table')
										.DataTable();
								dataTable.clear().draw();
								$
										.each(
												data,
												function(i, v) {
													//	var str = '<input  type="button" value="callEdit" onclick="callEdit('+v.itemId+','+i+')" style="width:30%;"/>&nbsp<input  type="button" value="callDelete" onclick="callDelete('+v.itemId+','+i+')" style="width:30%;"/> ';
													var str = '<a href="#" class="action_btn" onclick="callDelete('
															+ v.termDetailId
															+ ','
															+ i
															+ ')"><i class="fa fa-trash"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callEdit('
															+ v.termDetailId
															+ ','
															+ i
															+ ')"><i class="fa fa-edit"></i></a>'

													dataTable.row.add(
															[ i + 1,
																	v.termDesc,
																	v.sortNo,

																	str ])
															.draw();
												});
							});

		}
		function validate(s) {
			var rgx = /^[0-9]*\.?[0-9]*$/;
			return s.match(rgx);
		}
		function callAlert(msg) {
			alert(msg);
		}
	</script>

	<!-- <script type="text/javascript">
		$(document).ready(function() {
			var dataTable = $('#bootstrap-data-table').DataTable();
				columnDefs : [ {
					targets : [ 1,2],
					className : "right"
				}, ]
			
		});
	</script> -->

</body>
</html>