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
								method="post">

								<div class="row">


									<div class="col-md-2">Select Company*</div>

									<div class="col-md-4">
										<select id="companyId" name="companyId" class="standardSelect"
											tabindex="1" required>
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
													<option value="1" selected>Current Account</option>
													<option value="2">Savings Account</option>
													<option value="3">Recurring Deposit Account</option>
													<option value="4">Fixed Deposit Account</option>
												</c:when>
												<c:when test="${editBankDetail.accType==2}">
													<option value="1">Current Account</option>
													<option value="2" selected>Savings Account</option>
													<option value="3">Recurring Deposit Account</option>
													<option value="4">Fixed Deposit Account</option>
												</c:when>

												<c:when test="${editBankDetail.accType==3}">
													<option value="1">Current Account</option>
													<option value="2">Savings Account</option>
													<option value="3" selected>Recurring Deposit
														Account</option>
													<option value="4">Fixed Deposit Account</option>
												</c:when>
												<c:when test="${editBankDetail.accType==3}">
													<option value="1">Current Account</option>
													<option value="2">Savings Account</option>
													<option value="3">Recurring Deposit Account</option>
													<option value="4" selected>Fixed Deposit Account</option>
												</c:when>
												<c:otherwise>
													<option value="1" selected>Current Account</option>
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
											oninvalid="setCustomValidity('Please enter Bank Name')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											value="${editBankDetail.bankName}" class="form-control"
											style="width: 100%;">
									</div>

									<div class="col-md-2">Bank IFSC Code*</div>

									<div class="col-md-4">
										<input type="text" id="bankIfsc" name="bankIfsc"
											value="${editBankDetail.bankIfsc}" class="form-control"
											oninvalid="setCustomValidity('Please enter IFSC Code')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Bank Address*</div>
									<div class="col-md-4">
										<input type="text" id="bankAddress" name="bankAddress"
											value="${editBankDetail.bankAddress}" class="form-control"
											oninvalid="setCustomValidity('Please Enter Bank Address')"
											onchange="try{setCustomValidity('')}catch(e){}" required
											style="width: 100%;">
									</div>
									<div class="col-md-2">Account No*</div>
									<div class="col-md-4">
										<input type="text" id="accNo" name="accNo"
											value="${editBankDetail.accNo}" class="form-control"
											onchange="try{setCustomValidity('')}catch(e){}"
											oninvalid="setCustomValidity('Please Enter account no')"
											required style="width: 100%;">
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
						<div class="card-body card-block">

							<table id="bootstrap-data-table"
								class="table table-striped table-bordered">
								<thead>
									<tr>

										<th style="text-align: center">Sr</th>
										<th style="text-align: center">Bank Name</th>
										<th style="text-align: center">Company Name</th>
										<th style="text-align: center">Account No</th>

										<th style="text-align: center">Address</th>
										<th style="text-align: center">Bank IFSC</th>

										<th style="text-align: center; width: 5%;">Action</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach items="${bankList}" var="bankDet" varStatus="count">
										<tr>

											<td style="text-align: center">${count.index+1}</td>

											<td style="text-align: left"><c:out
													value="${bankDet.bankName}" /></td>

											<td style="text-align: left"><c:out
													value="${bankDet.compName}" /></td>


											<td style="text-align: left"><c:out
													value="${bankDet.accNo}" /></td>


											<td style="text-align: left"><c:out
													value="${bankDet.bankAddress}" /></td>


											<td style="text-align: left"><c:out
													value="${bankDet.bankIfsc}" /></td>





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






</body>
</html>