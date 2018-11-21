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
							<div class="col-md-3">
								<strong>${title}</strong>
							</div>
							<div class="col-md-6"></div>
							<div class="col-md-3" align="left">
								<a href="${pageContext.request.contextPath}/showVendorList"><strong>Vendor
										List</strong></a>
							</div>
						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertVendor"
								method="post">

								<input type="hidden" name="vendId" id="vendId"
									value="${editVend.vendId}">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Vendor Name*</div>
									<div class="col-md-4">
										<input type="text" id="vendCompName" name="vendCompName"
											class="form-control" required style="width: 100%;"
											oninvalid="setCustomValidity('Please enter vend Comp Name')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editVend.vendCompName}">
									</div>

									<div class="col-md-2">Vendor Contact Name*</div>
									<div class="col-md-4">
										<input type="text" id="vendContactName" name="vendContactName"
											class="form-control" required style="width: 100%;"
											oninvalid="setCustomValidity('Please enter vend Contact Name')"
											value="${editVend.vendContactName}"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Mobile No*</div>

									<div class="col-md-4">
										<input type="text" id="vendContact1" name="vendContact1"
											required style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter mob no')"
											maxlength="10" value="${editVend.vendContact1}"
											pattern="[0-9]{10}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>
									</div>

									<div class="col-md-2">Landline No*</div>

									<div class="col-md-4">
										<input type="text" id="vendContact2" name="vendContact2"
											required style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter mob no')"
											maxlength="10" value="${editVend.vendContact2}"
											pattern="[0-9]{10}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>
								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Email Id*</div>

									<div class="col-md-4">
										<input type="text" id="vendEmail1" name="vendEmail1"
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter vendEmail1')"
											maxlength="50" value="${editVend.vendEmail1}"
											pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">Select Plant*</div>

									<div class="col-md-4">
										<select id="plantId" name="plantId" class="standardSelect"
											tabindex="1" required>
											<c:forEach items="${plantList}" var="plant">

												<c:choose>
													<c:when test="${plant.plantId==editVend.plantId}">
														<option value="${plant.plantId}" selected>${plant.plantName}</option>
													</c:when>
													<c:otherwise>
														<option value="${plant.plantId}">${plant.plantName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>



								</div>


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">GST No*</div>

									<div class="col-md-4">
										<input type="text" id="vendGst" name="vendGst"
											value="${editVend.vendGst}"
											oninvalid="setCustomValidity('Please enter GST no')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;" required>
									</div>

									<div class="col-md-2">PAN No*</div>
									<div class="col-md-4">
										<input type="text" id="vendPan" name="vendPan"
											value="${editVend.vendPan}" class="form-control"
											style="width: 100%;"
											oninvalid="setCustomValidity('Please enter PAN no')"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>



								</div>
								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">State*</div>
									<div class="col-md-4">
										<input type="text" id="vendState" name="vendState"
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter vend State')"
											value="${editVend.vendState}"
											onchange="try{setCustomValidity('')}catch(e){}" required />
										<span class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">City*</div>
									<div class="col-md-4">
										<input type="text" id="vendCity" name="vendCity"
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter vend City')"
											value="${editVend.vendCity}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Vendor Credit Limit*</div>
									<div class="col-md-4">
										<input type="text" id="vendCreditLimit" name="vendCreditLimit"
											class="form-control" style="width: 100%;"
											value="${editVend.vendCreditLimit}"
											oninvalid="setCustomValidity('Please enter vend Credit Limit')"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>
									<div class="col-md-2">Credit Days*</div>
									<div class="col-md-4">
										<input type="text" id="vendCreditDays" name="vendCreditDays"
											class="form-control" style="width: 100%;"
											value="${editVend.vendCreditDays}"
											oninvalid="setCustomValidity('Please enter creadit Days')"
											onchange="try{setCustomValidity('')}catch(e){}" required>
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Is same State?*</div>


									<c:choose>
										<c:when test="${editVend.isSameState==0}">
											<div class="col-md-2">

												<input type="radio" name="isSameState" id="isSameState"
													value="1">Yes

											</div>



											<div class="col-md-2">
												<input type="radio" name="isSameState" value="0" checked>
												No

											</div>
										</c:when>
										<c:when test="${editVend.isSameState==1}">
											<div class="col-md-2">

												<input type="radio" name="isSameState" id="isSameState"
													value="1" checked>Yes
											</div>
											<div class="col-md-2">
												<input type="radio" name="isSameState" value="0"> No

											</div>
										</c:when>
									</c:choose>

									<div class="col-md-2">Vendor Type*</div>

									<c:choose>
										<c:when test="${editVend.vendType==0}">
											<div class="col-md-2">

												<input type="radio" name="vendType" id="vendType" value="1">Yes

											</div>



											<div class="col-md-2">
												<input type="radio" name="vendType" id="vendType" value="0"
													checked> No

											</div>
										</c:when>
										<c:when test="${editVend.vendType==1}">
											<div class="col-md-2">

												<input type="radio" name="vendType" id="vendType" value="1"
													checked>Yes
											</div>
											<div class="col-md-2">
												<input type="radio" name="vendType" value="0"> No

											</div>
										</c:when>
									</c:choose>
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
			$('input[id$=dob]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>

</body>
</html>