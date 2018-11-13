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
							<form action="${pageContext.request.contextPath}/insertProject"
								method="post">

								<div class="row">

									<div class="col-md-2">Select Plant</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required>
											<c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Select Customer</div>

									<div class="col-md-4">
										<select id="cust_id" name="cust_id" class="standardSelect"
											tabindex="1" required>
											<c:forEach items="${custList}" var="cust">
												<option value="${cust.custId}">${cust.custName}</option>
											</c:forEach>
										</select>
									</div>

								</div>
								<input type="hidden" name="proj_id" id="proj_id" value="0">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Project Name</div>
									<div class="col-md-4">
										<input type="text" id="proj_name" name="proj_name"
											class="form-control" required style="width: 100%;">
									</div>

									<div class="col-md-2">Project Location</div>

									<div class="col-md-4">
										<input type="text" id="proj_loc" name="proj_loc"
											class="form-control" required style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Start Date</div>
									<div class="col-md-4">
										<input type="text" id="start_date" name="start_date"
											class="form-control" required style="width: 100%;">
									</div>

									<div class="col-md-2">End Date</div>

									<div class="col-md-4">
										<input type="text" id="end_date" name="end_date"
											class="form-control" required style="width: 100%;">
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
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#image1').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#imgInp").change(function() {
			readURL(this);
		});
	</script>

	<script type="text/javascript">
		function readURL2(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#image2').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#imgInp1").change(function() {
			readURL2(this);
		});
	</script>

	<script type="text/javascript">
		function callSel() {
			//alert("Call me");
			var workType = document.getElementById("workTypeId").value;
			if (workType == 1) {
				//alert("In work Type  " +workType);
				document.getElementById('rc_book_div').style.display = "flow-root";
				document.getElementById('ins1_div').style.display = "flow-root";
				document.getElementById('ins2_div').style.display = "flow-root";
				document.getElementById('puc_div').style.display = "flow-root";
				document.getElementById('add_pf_div').style.display = "flow-root";

				document.getElementById('bank_noc_div').style = "display:none";
				document.getElementById('bank_letter_div').style = "display:none";
				document.getElementById('form_17_div').style = "display:none";
				document.getElementById('orig_lic_div').style = "display:none";

			}

			else if (workType == 2) {
				//alert("In work Type  " +workType);
				document.getElementById('rc_book_div').style.display = "flow-root";
				document.getElementById('ins1_div').style.display = "flow-root";
				document.getElementById('ins2_div').style.display = "flow-root";
				document.getElementById('puc_div').style.display = "flow-root";
				document.getElementById('bank_noc_div').style = "flow_root";

				document.getElementById('add_pf_div').style.display = "display:none";

				document.getElementById('bank_letter_div').style = "display:none";
				document.getElementById('form_17_div').style = "display:none";
				document.getElementById('orig_lic_div').style = "display:none";

			}

			else if (workType == 3) {
				//alert("In work Type  " +workType);
				document.getElementById('rc_book_div').style.display = "flow-root";
				document.getElementById('ins1_div').style.display = "flow-root";
				document.getElementById('ins2_div').style.display = "flow-root";
				document.getElementById('puc_div').style.display = "flow-root";
				document.getElementById('bank_letter_div').style = "flow_root";
				document.getElementById('form_17_div').style = "flow_root";

				document.getElementById('add_pf_div').style.display = "display:none";
				document.getElementById('orig_lic_div').style = "display:none";
				document.getElementById('bank_noc_div').style = "display:none";

			} else if (workType == 4) {
				//alert("In work Type  " +workType);
				document.getElementById('rc_book_div').style.display = "flow-root";
				document.getElementById('ins1_div').style.display = "flow-root";
				document.getElementById('ins2_div').style.display = "flow-root";
				document.getElementById('puc_div').style.display = "flow-root";
				document.getElementById('add_pf_div').style.display = "flow-root";

				document.getElementById('bank_noc_div').style = "display:none";
				document.getElementById('bank_letter_div').style = "display:none";
				document.getElementById('form_17_div').style = "display:none";
				document.getElementById('orig_lic_div').style = "display:none";

			}

			else if (workType == 5) {
				//alert("workType " +workType)
				$('#rc_book_div').hide();
				$('#ins1_div').hide();
				$('#ins2_div').hide();
				$('#puc_div').hide();
				$('#bank_noc_div').hide();

				$('#add_pf_div').hide();

				$('#bank_letter_div').hide();
				$('#form_17_div').hide();
				document.getElementById('orig_lic_div').style.display = "flow-root";

			}

		}
	</script>
	<script type="text/javascript">
		function onLoadCall() {
			//alert("onload Call")
			var workType = $
			{
				getWork.workTypeTd
			}
			;
			//$('#workTypeId').prop('readoly', true);
			//document.getElementById("pets").options[2].disabled = true;

			document.getElementById('workTypeId').disabled = true;
			document.getElementById('ac').style.color = 'white';

			document.getElementById('imgInp').style.color = 'white';
			document.getElementById('imgInp1').style.color = 'white';

			document.getElementById('rc_book').style.color = 'white';
			document.getElementById('puc').style.color = 'white';
			document.getElementById('ins1').style.color = 'white';
			document.getElementById('ins2').style.color = 'white';
			document.getElementById('add_proof').style.color = 'white';
			document.getElementById('bank_noc').style.color = 'white';
			document.getElementById('bank_letter').style.color = 'white';
			document.getElementById('form_no17').style.color = 'white';
			document.getElementById('orig_lic').style.color = 'white';

			document.getElementById("ac").required = false;
			if (workType == 1) {
				//alert("In work Type  " +workType);
				document.getElementById('rc_book_div').style.display = "flow-root";
				document.getElementById('ins1_div').style.display = "flow-root";
				document.getElementById('ins2_div').style.display = "flow-root";
				document.getElementById('puc_div').style.display = "flow-root";
				document.getElementById('add_pf_div').style.display = "flow-root";

				document.getElementById('bank_noc_div').style = "display:none";
				document.getElementById('bank_letter_div').style = "display:none";
				document.getElementById('form_17_div').style = "display:none";
				document.getElementById('orig_lic_div').style = "display:none";

			}

			else if (workType == 2) {
				//alert("In work Type  " +workType);
				document.getElementById('rc_book_div').style.display = "flow-root";
				document.getElementById('ins1_div').style.display = "flow-root";
				document.getElementById('ins2_div').style.display = "flow-root";
				document.getElementById('puc_div').style.display = "flow-root";
				document.getElementById('bank_noc_div').style = "flow_root";

				document.getElementById('add_pf_div').style.display = "display:none";

				document.getElementById('bank_letter_div').style = "display:none";
				document.getElementById('form_17_div').style = "display:none";
				document.getElementById('orig_lic_div').style = "display:none";

			}

			else if (workType == 3) {
				//alert("In work Type  " +workType);
				document.getElementById('rc_book_div').style.display = "flow-root";
				document.getElementById('ins1_div').style.display = "flow-root";
				document.getElementById('ins2_div').style.display = "flow-root";
				document.getElementById('puc_div').style.display = "flow-root";
				document.getElementById('bank_letter_div').style = "flow_root";
				document.getElementById('form_17_div').style = "flow_root";

				document.getElementById('add_pf_div').style.display = "display:none";
				document.getElementById('orig_lic_div').style = "display:none";
				document.getElementById('bank_noc_div').style = "display:none";

			} else if (workType == 4) {
				//alert("In work Type  " +workType);
				document.getElementById('rc_book_div').style.display = "flow-root";
				document.getElementById('ins1_div').style.display = "flow-root";
				document.getElementById('ins2_div').style.display = "flow-root";
				document.getElementById('puc_div').style.display = "flow-root";
				document.getElementById('add_pf_div').style.display = "flow-root";

				document.getElementById('bank_noc_div').style = "display:none";
				document.getElementById('bank_letter_div').style = "display:none";
				document.getElementById('form_17_div').style = "display:none";
				document.getElementById('orig_lic_div').style = "display:none";

			}

			else if (workType == 5) {
				document.getElementById('rc_book_div').style.display = "display:none";
				document.getElementById('ins1_div').style.display = "display:none";
				document.getElementById('ins2_div').style.display = "display:none";
				document.getElementById('puc_div').style.display = "display:none";
				document.getElementById('bank_noc_div').style = "display:none";

				document.getElementById('add_pf_div').style.display = "display:none";

				document.getElementById('bank_letter_div').style = "display:none";
				document.getElementById('form_17_div').style = "display:none";
				document.getElementById('orig_lic_div').style = "flow-root";

			}

		}
	</script>

	<script type="text/javascript">
		$(function() {

			// document.getElementById('ac').style.color = 'black';
			var workType = $
			{
				getWork.workTypeTd
			}
			;
			if (workType != null) {
				$('#ac').change(function() {
					document.getElementById('ac').style.color = 'black';
				});

				$('#imgInp').change(function() {
					document.getElementById('imgInp').style.color = 'black';
				});

				$('#imgInp1').change(function() {
					document.getElementById('imgInp1').style.color = 'black';
				});

				$('#rc_book').change(function() {
					document.getElementById('rc_book').style.color = 'black';
				});

				$('#puc').change(function() {
					document.getElementById('puc').style.color = 'black';
				});

				$('#ins1').change(function() {
					document.getElementById('ins1').style.color = 'black';
				});

				$('#ins2').change(function() {
					document.getElementById('ins2').style.color = 'black';
				});

				$('#add_proof').change(function() {
					document.getElementById('add_proof').style.color = 'black';
				});

				$('#bank_noc').change(function() {
					document.getElementById('bank_noc').style.color = 'black';
				});

				$('#bank_letter')
						.change(
								function() {
									document.getElementById('bank_letter').style.color = 'black';
								});

				$('#form_no17').change(function() {
					document.getElementById('form_no17').style.color = 'black';
				});

				$('#orig_lic').change(function() {
					document.getElementById('orig_lic').style.color = 'black';
				});

			}//end Of if

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