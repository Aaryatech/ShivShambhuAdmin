<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>
<c:url var="getUniqueTaxCheck" value="/getUniqueTaxCheck" />


<c:url var="getUniqueHsnCodeCheck" value="/getUniqueHsnCodeCheck" />
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
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							<div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showTaxList"
									style="color: black"><strong>Tax List</strong></a>
							</div>

						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertTax"
								method="post" id="submitForm">


								<input type="hidden" name="taxId" id="taxId"
									value="${editTax.taxId}">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Tax Name*</div>
									<div class="col-md-4">
										<input type="text" id="taxName" name="taxName"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter correct Tax Name')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											value="${editTax.taxName}" class="form-control"
											maxlength="25" style="width: 100%;">
									</div>

									<div class="col-md-2">HSN Code*</div>

									<div class="col-md-4">
										<input type="text" id="hsnCode" name="hsnCode" maxLength="8"
											autocomplete="off" value="${editTax.hsnCode}"
											class="form-control" pattern="[0-9]+"
											onchange="getHsnCodeCheck();"
											oninvalid="setCustomValidity('Please enter correct hsn code')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;" required>
									</div>

								</div>
								

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">CGST*</div>
									<div class="col-md-4">
										<input type="text" id="cgst" name="cgst"
											value="${editTax.cgst}" class="form-control"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" onblur="calculateIgst()"
											oninvalid="setCustomValidity('Please enter correct Cgst')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;" required>
									</div>
									<div class="col-md-2">SGST*</div>
									<div class="col-md-4">
										<input type="text" id="sgst" name="sgst" autocomplete="off"
											value="${editTax.sgst}" class="form-control"
											oninvalid="setCustomValidity('Please enter correct Sgst')"
											onblur="calculateIgst()" pattern="[0-9]+(\.[0-9]{0,2})?%?"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;" required>
									</div>
								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">IGST</div>
									<div class="col-md-4">
										<input type="text" id="igst" name="igst"
											value="${editTax.igst}" class="form-control" readonly
											oninvalid="setCustomValidity('Please enter Cgst')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>
									<div class="col-md-2">CESS(Optional)</div>
									<div class="col-md-4">
										<input type="text" id="cess" name="cess"
											onblur="calculateTotalTax()"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" value="${editTax.cess}"
											class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter Cgst')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">


									<div class="col-md-2">Total Tax Per</div>
									<div class="col-md-4">
										<input type="text" id="totalTaxPer" name="totalTaxPer"
											value="${editTax.totalTaxPer}" class="form-control"
											pattern="[0-9]+(\.[0-9]{0,2})?%?"
											oninvalid="setCustomValidity('Please enter Sgst')"
											onchange="try{setCustomValidity('')}catch(e){}" readonly
											style="width: 100%;">
									</div>

									<div class="col-md-2">Sort No(Optional)</div>
									<div class="col-md-4">
										<input type="text" id="sortNo" name="sortNo"
											value="${editTax.sortNo}" class="form-control"
											pattern="[0-9]+"
											oninvalid="setCustomValidity('Please enter Sort No')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>
								</div>
								<div class="form-group"></div>
								<div class="col-lg-4"></div>
								<div class="col-lg-3">
									<input type="submit" class="btn btn-primary" value="Submit"
										id="submitButton" onclick="checkHSNcodeLength"
										style="align-content: center; width: 113px; margin-left: 40px;">

								</div>
								<div class="col-lg-3">
									<input type="reset" class="btn btn-primary" value="Clear"
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


	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=usrDob]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>

	<script>
		function calculateIgst() {

			var sgst = document.getElementById("sgst").value;
			var cgst = document.getElementById("cgst").value;
			var valid = true;

			if (sgst == null && cgst > 0) {

				valid = false;
			} else if (cgst == null && sgst > 0) {

				valid = false;
			}

			if (valid == true) {
				document.getElementById("igst").value = (parseFloat(sgst) + parseFloat(cgst));
				document.getElementById("totalTaxPer").value = (parseFloat(sgst) + parseFloat(cgst));

				var igst = document.getElementById("igst").value;
				var cess = document.getElementById("cess").value;
				var valid = true;

				if (igst == null && cess > 0) {

					valid = false;
				} else if (cess == null && igst > 0) {

					valid = false;
				}

				if (valid == true) {

					document.getElementById("totalTaxPer").value = (parseFloat(cess) + parseFloat(igst));
				}

			}

		}
	</script>


	<script>
		function calculateTotalTax() {

			var igst = document.getElementById("igst").value;
			var cess = document.getElementById("cess").value;
			var valid = true;

			if (igst == null && cess > 0) {

				valid = false;
			} else if (cess == null && igst > 0) {

				valid = false;
			}

			if (valid == true) {

				document.getElementById("totalTaxPer").value = (parseFloat(cess) + parseFloat(igst));
			}

		}
	</script>


	<script type="text/javascript">
		function getHsnCodeCheck() {
			
			
			var hsnCode1 = document.getElementById("hsnCode").value;
			//alert("hsnCode"+hsnCode1);
			//alert("hsnCode"+hsnCode1.length);

			if (hsnCode1.length < 2 || hsnCode1.length >8 ) {
				alert("Please enter correct Hsncode ");
				document.getElementById("hsnCode").value = "";
			}

			var hsnCode = $("#hsnCode").val();
			
			
			
			//alert(hsnCode);

			$.getJSON('${getUniqueTaxCheck}', {

				hsnCode : hsnCode,

				ajax : 'true',

			}, function(data) {
				if (data.error == true) {
					alert("HSN Code Already Exist");

					document.getElementById("hsnCode").value = "";
					document.getElementById("submitButton").disabled = true;

				} else {
					document.getElementById("submitButton").disabled = false;
					
					var hsnCode = document.getElementById("hsnCode").value;
				
					
				}
			}

			);
			
			

		}
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

<!-- 
	<script type="text/javascript">
		function checkHSNcodeLength {
			
			alert("hi........2222");
			var hsnCode = document.getElementById("hsnCode").value;
			alert("hsnCode"+hsnCode);

			if (hsnCode.length >=2 && hsnCode.length <=8 ) {
				alert("Please enter correct Hsncode ");
				document.getElementById("hsnCode").value = "";
			}
		}
	</script> -->

</body>
</html>