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
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showCompList"><strong>Company
										List</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertCompany"
								method="post">
								<input type="hidden" name="comp_id" id="comp_id"
									value="${editComp.companyId}">

								<div class="row">

									<div class="col-md-2">Company Name*</div>
									<div class="col-md-10">
										<input type="text" id="comp_name" name="comp_name"
											oninvalid="setCustomValidity('Please enter company name')"
											onchange="try{setCustomValidity('')}catch(e){}"
											value="${editComp.compName}" style="width: 50%;"
											class="form-control" required>

									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Office Address*</div>
									<div class="col-md-10">
										<input type="text" id="off_add" name="off_add" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter office address')"
											maxlength="200" value="${editComp.compOfficeAdd}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Company Location*</div>
									<div class="col-md-10">
										<input type="text" id="comp_loc" name="comp_loc" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter location')"
											maxlength="200" value="${editComp.compLoc}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Factory Address*</div>
									<div class="col-md-10">
										<input type="text" id="fact_add" name="fact_add" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter factory address')"
											maxlength="200" value="${editComp.compFactAdd}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">License No*</div>
									<div class="col-md-4">
										<input type="text" id="lic_no" name="lic_no" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter License no')"
											maxlength="20" value="${editComp.compLicence}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">GST No*</div>
									<div class="col-md-4">
										<input type="text" id="gst_no" name="gst_no" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter GST no')"
											maxlength="20" value="${editComp.compGstNo}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Pan No*</div>
									<div class="col-md-4">
										<input type="text" id="pan_no" name="pan_no" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter PAN no')"
											maxlength="50" value="${editComp.compPanNo}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">CIN No*</div>
									<div class="col-md-4">
										<input type="text" id="cin_no" name="cin_no" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter CIN no')"
											maxlength="20" value="${editComp.cinNo}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Telephone No*</div>
									<div class="col-md-4">
										<input type="text" id="tel_no" name="tel_no" required
											style="width: 100%;" class="form-control"
											value="${editComp.contactNo2}"
											oninvalid="setCustomValidity('Please enter tel no')"
											onchange="try{setCustomValidity('')}catch(e){}"
											maxlength="10" pattern="[0-9]+" /> <span class="error"
											aria-live="polite"></span>

									</div>

									<div class="col-md-2">Mobile No*</div>
									<div class="col-md-4">
										<input type="text" id="mob_no" name="mob_no"
											style="width: 100%;" class="form-control"
											value="${editComp.contactNo1}"
											oninvalid="setCustomValidity('Please enter mob no')"
											onchange="try{setCustomValidity('')}catch(e){}"
											maxlength="10" pattern="[0-9]+" required /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Email Id*</div>
									<div class="col-md-4">
										<input type="text" id="email" name="email" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter email')"
											pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
											maxlength="50" value="${editComp.email1}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">FAX No*</div>
									<div class="col-md-4">
										<input type="text" id="fax" name="fax" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter FAX no')"
											maxlength="20" value="${editComp.faxNo1}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

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


</body>
</html>