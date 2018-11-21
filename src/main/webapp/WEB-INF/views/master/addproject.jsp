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
								<a href="${pageContext.request.contextPath}/showProjectList"><strong>Project
										List</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertProject"
								method="post">

								<div class="row">

									<div class="col-md-2">Select Plant*</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required>
											<c:forEach items="${plantList}" var="plant">

												<c:choose>
													<c:when test="${plant.plantId==editItem.plantId}">
														<option value="${plant.plantId}" selected>${plant.plantName}</option>
													</c:when>
													<c:otherwise>
														<option value="${plant.plantId}">${plant.plantName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Select Customer*</div>

									<div class="col-md-4">
										<select id="cust_id" name="cust_id" class="standardSelect"
											tabindex="1" required>
											<c:forEach items="${custList}" var="cust">

												<c:choose>
													<c:when test="${cust.custId==editPro.custId}">
														<option value="${cust.custId}" selected>${cust.custName}</option>
													</c:when>
													<c:otherwise>
														<option value="${cust.custId}">${cust.custName}
													</c:otherwise>
												</c:choose>
												<%-- 	<option value="${cust.custId}">${cust.custName}</option> --%>
											</c:forEach>
										</select>
									</div>

								</div>
								<input type="hidden" name="proj_id" id="proj_id"
									value="${editPro.projId}">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Contact Person Name*</div>
									<div class="col-md-4">
										<input type="text" id="contactPerName" name="contactPerName"
											oninvalid="setCustomValidity('Please enter contact person Name')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editPro.contactPerName}" class="form-control"
											required style="width: 100%;">
									</div>

									<div class="col-md-2">Mobile No*</div>

									<div class="col-md-4">
										<input type="text" id="contactPerMob" name="contactPerMob"
											value="${editPro.contactPerMob}"
											oninvalid="setCustomValidity('Please enter tel no')"
											maxlength="10" pattern="[0-9]+"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" required style="width: 100%;">
									</div>

								</div>


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Project Name*</div>
									<div class="col-md-4">
										<input type="text" id="proj_name" name="proj_name"
											oninvalid="setCustomValidity('Please enter project name')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editPro.projName}" class="form-control" required
											style="width: 100%;">
									</div>

									<div class="col-md-2">Project Location*</div>

									<div class="col-md-4">
										<input type="text" id="proj_loc" name="proj_loc"
											oninvalid="setCustomValidity('Please enter project location')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editPro.location}" class="form-control" required
											style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Start Date*</div>
									<div class="col-md-4">
										<input type="text" id="start_date" name="start_date"
											value="${editPro.startDate}" class="form-control" required
											style="width: 100%;">
									</div>

									<div class="col-md-2">End Date*</div>

									<div class="col-md-4">
										<input type="text" id="end_date" name="end_date"
											value="${editPro.endDate}" class="form-control" required
											style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Pincode No*</div>
									<div class="col-md-4">
										<input type="text" id="pincode" name="pincode"
											class="form-control" style="width: 100%;"
											value="${editPro.pincode}"
											oninvalid="setCustomValidity('Please enter Pincode')"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>
									<div class="col-md-2">Kilometer*</div>
									<div class="col-md-4">
										<input type="text" id="km" name="km" class="form-control"
											style="width: 100%;" value="${editPro.km}"
											oninvalid="setCustomValidity('Please enter Kilometer')"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>

								</div>
								<div class="form-group"></div>

								<div class="col-md-2">Address*</div>

								<div class="col-md-4">
									<textarea id="address" name="address" class="form-control"
										oninvalid="setCustomValidity('Please enter address')"
										onchange="try{setCustomValidity('')}catch(e){}" required
										style="width: 100%;">${editPro.address}</textarea>
								</div>

								<div class="col-lg-2" align="left">


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


	<!-- 	<script type="text/javascript">
	
	function editMsUser(msId){
		
		//alert(catId);
		
		$.getJSON('${getEditMsUser}',{
			
			msId : msId,
			
			ajax : 'true',

		},
		
		function(data){
			document.getElementById('addDiv').style.display = "block";
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
	
	</script> -->

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
			$('input[id$=start_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$('input[id$=end_date]').datepicker({
				dateFormat : 'dd-mm-yy'

			});
		});
	</script>


</body>
</html>