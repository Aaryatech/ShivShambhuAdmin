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

							<form action="${pageContext.request.contextPath}/getPoList"
								method="get">

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">From Date</div>
									<div class="col-md-3">
										<input type="text" id="fromDate" name="fromDate"
											class="form-control"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											value="${fromDate}" required>
									</div>
									<div class="col-md-2">Payment Term</div>

									<div class="col-md-3">
										<input type="text" id="toDate" name="toDate"
											class="form-control"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											value="${toDate}" required>
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-12" style="text-align: center">

										<button type="submit" style="height: 35px; width: 80px">Submit</button>
									</div>
								</div>

							</form>

							<table id="bootstrap-data-table"
								class="table table-striped table-bordered">
								<thead>
									<tr>
										<th style="text-align: center">Sr.</th>
										<th style="text-align: center">Po No</th>
										<th style="text-align: center">Purchase Order Date</th>
										<th style="text-align: center">Customer Name</th>
										<th style="text-align: center">Plant Name</th>
										<th style="text-align: center">Quotation No</th>

										<th style="text-align: center">Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${poList}" var="poList" varStatus="count">
										<tr>

											<td style="text-align: center">${count.index+1}</td>
											<td style="text-align: left">${poList.poNo}</td>
											<td style="text-align: left">${poList.poDate}</td>
											<td style="text-align: left"><c:out
													value="${poList.custName}" /></td>

											<td style="text-align: left"><c:out
													value="${poList.plantName}" /></td>
											<td style="text-align: center">${poList.quatationNo}</td>


											<td><a
												href="${pageContext.request.contextPath}/editPo/${poList.poId}"><i
													class="fa fa-edit"  style="color:black" title="Edit"></i> <span
													class="text-muted"></span></a> <a
												href="${pageContext.request.contextPath}/deletePurchaseOrder/${poList.poId}"
												onClick="return confirm('Are you sure want to delete this record');"><i
													class="fa fa-trash-o"  style="color:black" title="Delete"></i></a>
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

	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {

			$('input[id$=fromDate]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$('input[id$=toDate]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
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


	<!-- <script type="text/javascript">
	
	function editComp(compId){
		
		//alert(catId);
		
		$.getJSON('${getEditCompany}',{
			
			compId : compId,
			
			ajax : 'true',

		},
		
		function(data){
			//document.getElementById('addDiv').style.display = "block";
			$("#usrname_mr").val(data.msMarName);
			$("#usrname_eng").val(data.msEngName);
        	
			//hidden field msId
			$("#ms_id").val(data.msId);
			
			$("#contact_no").val(data.msContactNo);
			 document.getElementById("contact_no").readOnly = true; 
			$("#usr_pass").val(data.msPwd); 
			$("#conf_pass").val(data.msPwd); 
			document.getElementById("usr_role").options.selectedIndex =data.isAdmin;
			$("#usr_role").trigger("chosen:updated");
			var temp=new Array();
			
			temp=(data.hubIds).split(",");
			//alert(temp);
			$("#sel_hub").val(temp); 
			$("#sel_hub").trigger("chosen:updated");

			//$('#sel_hub').formcontrol('refresh');
	 		document.getElementById('submitButton').disabled = false;


		});
		
	}
	
	</script>
 -->

</body>
</html>