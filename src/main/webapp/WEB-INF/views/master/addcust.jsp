<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getUniqueCustomerCheck" value="/getUniqueCustomerCheck" />

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
								<strong>Data not Submitted</strong>
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
							<div class="col-md-3">
								<strong>${title}</strong>
							</div>
							<div class="col-md-6"></div>
							<div class="col-md-3" align="left">
								<a href="${pageContext.request.contextPath}/showCustList"><strong>Customer
										List</strong></a>
							</div>
						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertCust"
								id="submitForm" method="post">

								<div class="row">

									<div class="col-md-2">Select Plant*</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please enter customer name')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="">Select</option>
											<c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Customer Type*</div>

									<div class="col-md-4">
										<select id="cust_type" name="cust_type" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please enter customer name')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="">Select</option>
											<c:forEach items="${custTypeList}" var="custType">
												<option value="${custType.custTypeId}">${custType.custTypeName}</option>
											</c:forEach>
										</select>
									</div>

								</div>
								<input type="hidden" name="cust_id" id="cust_id" value="0">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Customer Name*</div>
									<div class="col-md-4">
										<input type="text" id="cust_name" name="cust_name"
											pattern="^[A-Za-z\s]+$" class="form-control" maxlength="50" required
											style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter customer name')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

									<div class="col-md-2">Mobile No.*</div>

									<div class="col-md-4">
										<input type="text" id="mob_no" name="mob_no" required
											onblur="getCheck()" style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter correct mob no')"
											maxlength="10" value="${editComp.contactNo1}"
											pattern="^[1-9]{1}[0-9]{9}$" autocomplete="off"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>
								<hr style="color: pink; background: pink;"></hr>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Reference Name</div>
									<div class="col-md-4">
										<input type="text" id="ref_name" name="ref_name"
											pattern="^[A-Za-z\s]+$" class="form-control"
											autocomplete="off" style="width: 100%;"
											oninvalid="setCustomValidity('Please enter correct refrence name')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

									<div class="col-md-2">Email</div>

									<div class="col-md-4">
										<input type="email" id="email" name="email"
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter correct email')"
										maxlength="50" autocomplete="off"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

								</div>
								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">PAN No</div>

									<div class="col-md-4">
										<input type="text" id="pan_no" name="pan_no"
											autocomplete="off" pattern="[A-Za-z]{5}\d{4}[A-Za-z]{1}"
											onkeydown="upperCaseF(this)" class="form-control"
											oninvalid="setCustomValidity('Please enter correct pan no')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>

									<div class="col-md-2">GST No</div>

									<div class="col-md-4">
										<input type="text" id="gst_no" name="gst_no"
											autocomplete="off"
											pattern="^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$"
											onkeydown="upperCaseF(this)" class="form-control"
											oninvalid="setCustomValidity('Please enter correct GST no')"
											onchange="try{setCustomValidity('')}catch(e){}"
											style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Category</div>

									<div class="col-md-4">
										<select id="cust_cate" name="cust_cate" class="standardSelect"
											tabindex="1"
											oninvalid="setCustomValidity('Please enter customer category')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="">Select</option>
											<c:forEach items="${settingList}" var="custCate">
												<option value="${custCate.settingId}">${custCate.settingValue}</option>
											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Security CHEQUE?</div>


									<div class="col-md-1">
										Yes <input type="radio" checked name="cheque" id="cheque"
											value="1">
									</div>

									<div class="col-md-1">
										NO <input type="radio" name="cheque" id="cheque" value="0">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Date Of Birth</div>

									<div class="col-md-4">
										<input type="text" id="dob" name="dob" class="form-control"
										onchange="checkDOB()"
											autocomplete="off" style="width: 100%;">
									</div>

									<div class="col-md-2">Customer Code</div>

									<div class="col-md-4">
										<input type="text" id="cust_code" name="cust_code"
											pattern="[0-9]+" maxlength="6" autocomplete="off"
											oninvalid="setCustomValidity('Please enter Customer code')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;">
									</div>

								</div>


								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Credit Limit</div>
									<div class="col-md-4">
										<input type="text" id="creaditLimit" name="creaditLimit"
											class="form-control" style="width: 100%;" autocomplete="off"
											pattern="[0-9]+(\.[0-9]{0,2})?%?"
											oninvalid="setCustomValidity('Please enter creadit limit')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>
									<div class="col-md-2">Credit Days</div>
									<div class="col-md-4">
										<input type="text" id="creaditDays" name="creaditDays"
											autocomplete="off" class="form-control" style="width: 100%;"
											pattern="[0-9]+(\.[0-9]{0,2})?%?"
											oninvalid="setCustomValidity('Please enter creadit days')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>
								</div>

								<div class="form-group"></div>

								<div class="row">


									<div class="col-md-2">CHEQUE Remark</div>

									<div class="col-md-4">
										<textarea id="cheque_remark" name="cheque_remark"
											autocomplete="off" class="form-control" style="width: 100%;"
											oninvalid="setCustomValidity('Please enter cheque remark')"
											onchange="try{setCustomValidity('')}catch(e){}"></textarea>
									</div>

									<div class="col-md-2">Address</div>

									<div class="col-md-4">
										<textarea id="cust_add" name="cust_add" class="form-control"
											autocomplete="off" style="width: 100%;"></textarea>
									</div>
									<div class="col-md-2"></div>
								</div>
								<div class="form-group"></div>

								<div class="row">

									
									<div class="col-md-2">Kilometer</div>
									<div class="col-md-4">
										<input type="text" id="km" name="km" class="form-control"
											autocomplete="off" style="width: 100%;"
											pattern="[0-9]+(\.[0-9]{0,2})?%?"
											oninvalid="setCustomValidity('Please enter Kilometer')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Contact Person Name</div>
									<div class="col-md-4">
										<input type="text" id="cont_per_name" name="cont_per_name"
											class="form-control" style="width: 100%;" 
											pattern="^[A-Za-z\s]+$"  maxlength="50" autocomplete="off"
											oninvalid="setCustomValidity('Please enter customer name')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

									<div class="col-md-2">Contact Person Mobile No</div>

									<div class="col-md-4">
										<input type="text" id="con_per_mob" name="con_per_mob"
											autocomplete="off" style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter mob no')"
											maxlength="10" value="${editComp.contactNo1}"
											pattern="^[1-9]{1}[0-9]{9}$"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>

								<div class="row">
									<div class="col-md-2">Telephone No</div>
									<div class="col-md-4">
										<input type="text" id="tel_no" name="tel_no"
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter tel no')"
											maxlength="10" pattern="^[1-9]{1}[0-9]{9}$"
											value="${editComp.contactNo2}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

									<div class="col-md-2">Vendor Code</div>
									<div class="col-md-4">
										<input type="text" id="cust_vendor" name="cust_vendor"
											style="width: 100%;" class="form-control" autocomplete="off"
											pattern="[0-9]+"
											oninvalid="setCustomValidity('Please enter vendor code')"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>
								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Owner Name</div>
									<div class="col-md-4">
										<input type="text" id="ownerName" name="ownerName"
											autocomplete="off" class="form-control" style="width: 100%;"
											pattern="^[A-Za-z\s]+$"
											oninvalid="setCustomValidity('Please enter owner name')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

									<div class="col-md-2">Is same State?</div>


									<div class="col-md-1">
										Yes <input type="radio" checked name="state" id="state"
											value="1">
									</div>

									<div class="col-md-1">
										NO <input type="radio" name="state" id="state" value="0">
									</div>


								</div>
								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Accountant Name</div>
									<div class="col-md-4">
										<input type="text" id="accPerson" name="accPerson"
											class="form-control" style="width: 100%;" autocomplete="off"
											pattern="^[A-Za-z\s]+$"
											oninvalid="setCustomValidity('Please enter Account name')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>
									<div class="col-md-2">Accountant Mobile No</div>
									<div class="col-md-4">
										<input type="text" id="accPerMob" name="accPerMob"
											class="form-control" style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter Mobile no')"
											pattern="^[1-9]{1}[0-9]{9}$" onchange="checkMobileNo()"
											maxlength="10"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

								</div>


								<div class="form-group"></div>
								<div class="col-lg-4"></div>
								<div class="col-lg-3">
									<input type="submit" class="btn btn-primary" id="submit"
										value="Submit"
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

	<script>
		function upperCaseF(a) {
			setTimeout(function() {
				a.value = a.value.toUpperCase();
			}, 1);
		}
	</script>

	<script type="text/javascript">
		function getCheck() {

			var mobNo = $("#mob_no").val();

			$.getJSON('${getUniqueCustomerCheck}', {

				mobNo : mobNo,

				ajax : 'true',

			}, function(data) {
				if (data.error == true) {
					alert("Customer Already Exist");

					document.getElementById("mob_no").value = "";

					document.getElementById("submitButton").disabled = true;
				} else {
					document.getElementById("submitButton").disabled = false;

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
		
		
		 function  checkDOB() {
		        //In javascript
		        var dateEntered = document.getElementById("dob").value; 
		        // In JQuery
		        var dateEntered = $("#dob").val();
		     
		        var date = dateEntered.substring(0, 2);
		        var month = dateEntered.substring(3, 5);
		        var year = dateEntered.substring(6, 10);
		     
		        var dateToCompare = new Date(year, month - 1, date);
		        var currentDate = new Date();
		     
		        if (dateToCompare > currentDate) {
		            alert("Please enter DOB less than Current Date ");
		            document.getElementById('dob').value="";
		        }
		        
		    }

	</script>
	

</body>
</html>