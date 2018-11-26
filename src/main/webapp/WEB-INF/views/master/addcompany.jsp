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
											oninvalid="setCustomValidity('Please enter correct company name')"
											onchange="try{setCustomValidity('')}catch(e){}"
											pattern="^[A-Za-z\s]+$" value="${editComp.compName}"
											style="width: 50%;" autocomplete="off" class="form-control"
											required>

									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">



									<div class="col-md-2">Office Address*</div>

									<div class="col-md-4">
										<textarea id="off_add" name="off_add" class="form-control"
											style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter office address')"
											maxlength="200"
											onchange="try{setCustomValidity('')}catch(e){}" required>${editComp.compOfficeAdd}</textarea>
									</div>


									<div class="col-md-2">Company Location(Optional)</div>

									<div class="col-md-4">
										<textarea id="comp_loc" name="comp_loc" class="form-control"
											style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter location')"
											maxlength="200"
											onchange="try{setCustomValidity('')}catch(e){}">${editComp.compLoc}</textarea>
									</div>
								</div>


								<div class="form-group"></div>
								<div class="row">


									<div class="col-md-2">Factory Address*</div>

									<div class="col-md-4">
										<textarea id="fact_add" name="fact_add" class="form-control"
											style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter factory address')"
											maxlength="200"
											onchange="try{setCustomValidity('')}catch(e){}" required>${editComp.compFactAdd}</textarea>
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">License No*</div>
									<div class="col-md-4">
										<input type="text" id="lic_no" name="lic_no" required
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter License no')"
											maxlength="20" value="${editComp.compLicence}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">GST No*</div>
									<div class="col-md-4">
										<input type="text" id="gst_no" name="gst_no" required
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter GST no')"
											maxlength="20" value="${editComp.compGstNo}"
											pattern="^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$"
											onkeydown="upperCaseF(this)"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">PAN No*</div>
									<div class="col-md-4">
										<input type="text" id="pan_no" name="pan_no" required
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter PAN no')"
											maxlength="10" value="${editComp.compPanNo}"
											pattern="[A-Za-z]{5}\d{4}[A-Za-z]{1}"
											onkeydown="upperCaseF(this)"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">CIN No*</div>
									<div class="col-md-4">
										<input type="text" id="cin_no" name="cin_no" required
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter CIN no')"
											maxlength="21" value="${editComp.cinNo}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Mobile No*</div>
									<div class="col-md-4">
										<input type="text" id="mob_no" name="mob_no"
											style="width: 100%;" class="form-control"
											value="${editComp.contactNo1}" autocomplete="off"
											oninvalid="setCustomValidity('Please enter correct mob no')"
											pattern="^[1-9]{1}[0-9]{9}$" maxlength="10"
											onchange="try{setCustomValidity('')}catch(e){}" required />
										<span class="error" aria-live="polite"></span>

									</div>
									<div class="col-md-2">Telephone No(Optional)</div>
									<div class="col-md-4">
										<input type="text" id="tel_no" name="tel_no"
											style="width: 100%;" class="form-control"
											value="${editComp.contactNo2}" autocomplete="off"
											oninvalid="setCustomValidity('Please enter tel no')"
											onchange="try{setCustomValidity('')}catch(e){}"
											pattern="^[1-9]{1}[0-9]{9}$" /> <span class="error"
											aria-live="polite"></span>

									</div>


								</div>

								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Email Id*</div>
									<div class="col-md-4">
										<input type="text" id="email" name="email" required
											style="width: 100%;" class="form-control" autocomplete="off"
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
											pattern="/[\+? *[1-9]+]?[0-9 ]+/" maxlength="20"
											value="${editComp.faxNo1}" autocomplete="off"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>
								<div class="form-group"></div>
								<div class="col-lg-4"></div>
								<div class="col-lg-3">
									<button type="submit" class="btn btn-primary"
										style="align-content: center; width: 113px; margin-left: 40px;">
										Submit</button>
								</div>
								<div class="col-lg-3">
									<button type="reset" class="btn btn-primary"
										style="align-content: center; width: 113px; margin-left: 40px;">
										Clear</button>
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
		function upperCaseF(a) {
			setTimeout(function() {
				a.value = a.value.toUpperCase();
			}, 1);
		}
	</script>


	<!-- 
	<!-- <script>
		function checkMobileNo() {
			alert("hiii");

			var mobNo = document.getElementById("mob_no").value;

			if (mobNo == 0000000000) {
				document.getElementById("mob_no").reset();

			}
		}
	</script>
 -->
	-->
</body>
</html>