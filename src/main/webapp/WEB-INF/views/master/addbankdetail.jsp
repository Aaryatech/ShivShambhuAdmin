<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getUniqueAccNoCheck" value="/getUniqueAccNoCheck" />

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
							<%-- <div class="col-md-8"></div>
							<div class="col-md-2" align="left">
								<a href="${pageContext.request.contextPath}/showBankDetailList"><strong>Bank
										Detail List</strong></a>
							</div>
 --%>
						</div>
						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/insertBankDetail"
								id="submitForm" method="post">

								<div class="row">


									<div class="col-md-2">Select Company*</div>

									<div class="col-md-4">
										<select id="companyId" name="companyId" class="standardSelect"
											tabindex="1" required>
											<option value="">Select</option>
											<c:forEach items="${compList}" var="comp">

												<c:choose>
													<c:when test="${comp.companyId==editBankDetail.companyId}">
														<option value="${comp.companyId}" selected>${comp.compName}</option>
													</c:when>
													<c:otherwise>
														<option value="${comp.companyId}">${comp.compName}
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>


									<div class="col-md-2">Account Type*</div>

									<div class="col-md-4">
										<select id="accType" name="accType" class="standardSelect"
											tabindex="1" required>


											<c:choose>
												<c:when test="${editBankDetail.accType==1}">
													<option value="">Select</option>
													<option value="1" selected>Current Account</option>
													<option value="2">Savings Account</option>
													<option value="3">Recurring Deposit Account</option>
													<option value="4">Fixed Deposit Account</option>
												</c:when>

												<c:when test="${editBankDetail.accType==2}">
													<option value="">Select</option>
													<option value="1">Current Account</option>
													<option value="2" selected>Savings Account</option>
													<option value="3">Recurring Deposit Account</option>
													<option value="4">Fixed Deposit Account</option>
												</c:when>


												<c:when test="${editBankDetail.accType==3}">
													<option value="">Select</option>
													<option value="1">Current Account</option>
													<option value="2">Savings Account</option>
													<option value="3" selected>Recurring Deposit
														Account</option>
													<option value="4">Fixed Deposit Account</option>
												</c:when>

												<c:when test="${editBankDetail.accType==3}">
													<option value="">Select</option>
													<option value="1">Current Account</option>
													<option value="2">Savings Account</option>
													<option value="3">Recurring Deposit Account</option>
													<option value="4" selected>Fixed Deposit Account</option>
												</c:when>

												<c:otherwise>
													<option value="">Select</option>
													<option value="1">Current Account</option>
													<option value="2">Savings Account</option>
													<option value="3">Recurring Deposit Account</option>
													<option value="4">Fixed Deposit Account</option>
												</c:otherwise>
											</c:choose>


										</select>
									</div>

								</div>

								<input type="hidden" name="bankDetId" id="bankDetId"
									value="${editBankDetail.bankDetId}">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Bank Name*</div>
									<div class="col-md-4">
										<input type="text" id="bankName" name="bankName"
											pattern="^[A-Za-z\s]+$" autocomplete="off"
											oninvalid="setCustomValidity('Please enter correct Bank Name')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											value="${editBankDetail.bankName}" class="form-control"
											style="width: 100%;">
									</div>

									<div class="col-md-2">Bank IFSC Code*</div>

									<div class="col-md-4">
										<input type="text" id="bankIfsc" name="bankIfsc"
											autocomplete="off" pattern="[A-Z|a-z]{4}[0][\d]{6}$"
											value="${editBankDetail.bankIfsc}" class="form-control"
											oninvalid="setCustomValidity('Please enter correct IFSC Code')"
											onchange="try{setCustomValidity('')}catch(e){}"
											onkeydown="upperCaseF(this)" required style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">



									<div class="col-md-2">Bank Address*</div>

									<div class="col-md-4">
										<textarea id="bankAddress" name="bankAddress"
											class="form-control" style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter Bank address')"
											maxlength="200"
											onchange="try{setCustomValidity('')}catch(e){}" required>${editBankDetail.bankAddress}</textarea>
									</div>

									<div class="col-md-2">Account No*</div>
									<div class="col-md-4">
										<input type="text" id="accNo" name="accNo" autocomplete="off"
											value="${editBankDetail.accNo}" class="form-control"
											onblur="getCheck()"
											onchange="try{setCustomValidity('')}catch(e){}"
											pattern="[0-9]+" maxlength="15"
											oninvalid="setCustomValidity('Please enter account no')"
											required style="width: 100%;">
									</div>
								</div>



								<div class="form-group"></div>
								<div class="col-lg-4"></div>

								<div class="col-lg-2">


									<input type="submit" class="btn btn-primary" value="Submit"
										style="align-content: center; width: 113px; margin-left: 40px;">

								</div>

								<div class="col-lg-2">

									<input type="reset" class="btn btn-primary" value="Clear"
										style="align-content: center; width: 113px; margin-left: 40px;">

								</div>
							</form>
						</div>
						<div class="card-body card-block">

							<form
								action="${pageContext.request.contextPath}/deleteRecordofBank"
								method="post">

								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">

									<thead>
										<tr>
											<th class="check" style="text-align: center; width: 5%;"><input
												type="checkbox" name="selAll" id="selAll" />Select All</th>
											<th style="text-align: center; width: 5%;">Sr No</th>

											<th style="text-align: center">Company Name</th>
											<th style="text-align: center">Bank Name</th>
											<th style="text-align: center">Bank IFSC Code</th>
											<th style="text-align: center">Account No</th>
											<th style="text-align: center; width: 5%;">Action</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${bankList}" var="bankDet" varStatus="count">
											<tr>
												<td><input type="checkbox" class="chk"
													name="bankDetIds" id="bankDetIds${count.index+1}"
													value="${bankDet.bankDetId}" /></td>

												<td style="text-align: center">${count.index+1}</td>
												<td style="text-align: left"><c:out
														value="${bankDet.compName}" /></td>
												<td style="text-align: left"><c:out
														value="${bankDet.bankName}" /></td>

												<td style="text-align: left"><c:out
														value="${bankDet.bankIfsc}" /></td>

												<td style="text-align: left"><c:out
														value="${bankDet.accNo}" /></td>

												<td style="text-align: center"><a
													href="${pageContext.request.contextPath}/editBankDetail/${bankDet.bankDetId}"><i
														class="fa fa-edit"></i> <span class="text-muted"></span></a>
													&nbsp; <a
													href="${pageContext.request.contextPath}/deleteBankDetail/${bankDet.bankDetId}"
													onClick="return confirm('Are you sure want to delete this record');"><i
														class="fa fa-trash-o"></i></a></td>

											</tr>
										</c:forEach>
									</tbody>

								</table>
								<div class="col-lg-1">

									<input type="submit" class="btn btn-primary" value="Delete"
										id="deleteId"
										onClick="var checkedVals = $('.chk:checkbox:checked').map(function() { return this.value;}).get();checkedVals=checkedVals.join(',');if(checkedVals==''){alert('No Rows Selected');return false;	}else{   return confirm('Are you sure want to delete record');}"
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
			$('input[id$=usrDob]').datepicker({
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
		$(document)
				.ready(
						function() {
							$('#bootstrap-data-table-export').DataTable();

							$("#selAll")
									.click(
											function() {
												$(
														'#bootstrap-data-table tbody input[type="checkbox"]')
														.prop('checked',
																this.checked);
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

			var accNo = $("#accNo").val();

			$.getJSON('${getUniqueAccNoCheck}', {

				accNo : accNo,

				ajax : 'true',

			}, function(data) {
				if (data.error == true) {
					alert("Account Number Already Exist");

					document.getElementById("accNo").value = "";

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