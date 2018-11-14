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
							<form action="${pageContext.request.contextPath}/insertItem"
								method="post">

								<div class="row">

									<div class="col-md-2">Select Plant</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please enter Plant name')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<c:forEach items="${plantList}" var="plant">

												<c:choose>
													<c:when test="${plant.plantId==editItem.plantId}">
														<option value="${plant.plantId}" selected>${plant.plantName}</option>
													</c:when>
													<c:otherwise>
														<option value="${plant.plantId}">${plant.plantName}
													</c:otherwise>
												</c:choose>

												<%-- <option value="${plant.plantId}">${plant.plantName}</option> --%>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2">Item Type</div>
									<div class="col-md-4">
										<select id="item_type" name="item_type" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select item type')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<c:choose>

												<c:when test="${editItem.itemType==0}">
													<option selected value="0">Raw Material</option>
													<option value="1">Finished Good</option>

												</c:when>
												<c:otherwise>
													<option value="0">Raw Material</option>
													<option selected value="1">Finished Good</option>


												</c:otherwise>

											</c:choose>


										</select>
									</div>

								</div>
								<input type="hidden" name="item_id" id="item_id"
									value="${editItem.itemId}">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Item Name</div>
									<div class="col-md-4">
										<input type="text" id="item_name" name="item_name"
											value="${editItem.itemName}" class="form-control" required
											style="width: 100%;"
											oninvalid="setCustomValidity('Please enter customer name')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

									<div class="col-md-2">Item Code</div>

									<div class="col-md-4">
										<input type="text" id="item_code" name="item_code" required
											style="width: 100%;" class="form-control"
											oninvalid="setCustomValidity('Please enter item code')"
											value="${editItem.itemCode}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>

								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Select UOM</div>

									<div class="col-md-4">
										<select id="uomId" name="uomId" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select uom')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<c:forEach items="${uomList}" var="uom">

												<c:choose>
													<c:when test="${uom.uomId==editItem.uomId}">
														<option value="${uom.uomId}" selected>${uom.uomName}</option>
													</c:when>
													<c:otherwise>
														<option value="${uom.uomId}">${uom.uomName}
													</c:otherwise>
												</c:choose>


												<%-- <option value="${uom.uomId}">${uom.uomName}</option> --%>
											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Select Tax</div>

									<div class="col-md-4">
										<select id="taxId" name="taxId" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select tax')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<c:forEach items="${taxList}" var="tax">
												<option value="${tax.taxId}">${tax.taxName}</option>
											</c:forEach>
										</select>
									</div>
								</div>


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Short Name</div>
									<div class="col-md-4">
										<input type="text" id="short_name" name="short_name"
											value="${editItem.shortName}" class="form-control"
											style="width: 100%;">
									</div>

									<div class="col-md-2">Item Rate</div>

									<div class="col-md-4">
										<input type="text" id="rate" name="rate"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" style="width: 100%;"
											class="form-control"
											oninvalid="setCustomValidity('Please enter rate')"
											value="${editItem.itemRate1}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

								</div>
								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">Actual Measurement</div>

									<div class="col-md-4">
										<input type="text" id="act_weight" name="act_weight"
											class="form-control" style="width: 100%;"
											value="${editItem.actualWeight}">
									</div>

									<div class="col-md-2">Base Measurement</div>

									<div class="col-md-4">
										<input type="text" id="base_weight" name="base_weight"
											class="form-control" style="width: 100%;"
											value="${editItem.baseWeight}">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Vendors</div>

									<div class="col-md-4">
										<select id="vendor_ids" name="vendor_ids"
											class="standardSelect" tabindex="1"
											oninvalid="setCustomValidity('Please select vendors')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<c:forEach items="${vendList}" var="vendor">

												<%-- <c:choose>
													<c:when test="${vendor.vendId==editItem.vendorIds}">
														<option value="${vendor.vendId}" selected>${vendor.vendCompName}</option>
													</c:when>
													<c:otherwise>
														<option value="${vendor.vendId}">${vendor.vendCompName}
													</c:otherwise>
												</c:choose> --%>
												<option value="${vendor.vendId}">${vendor.vendCompName}</option>
											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Dispatch Limit</div>

									<div class="col-md-4">
										<input type="text" id="disp_limit" name="disp_limit"
											value="${editItem.dispatchLimit}" class="form-control"
											style="width: 100%;">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Min Stock</div>

									<div class="col-md-2">
										<input type="text" id="min_stock" name="min_stock"
											value="${editItem.minStock}" class="form-control"
											style="width: 100%;">
									</div>
									<div class="col-md-2">Max Stock</div>

									<div class="col-md-2">
										<input type="text" id="max_stock" name="max_stock"
											value="${editItem.maxStock}" class="form-control"
											style="width: 100%;">
									</div>
									<div class="col-md-2">ROL Stock</div>

									<div class="col-md-2">
										<input type="text" id="rol_stock" name="rol_stock"
											value="${editItem.rolStock}" class="form-control"
											style="width: 100%;">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Plant Min Stock</div>

									<div class="col-md-2">
										<input type="text" id="pmin_stock" name="pmin_stock"
											value="${editItem.plantMinStock}" class="form-control"
											style="width: 100%;">
									</div>
									<div class="col-md-2">Plant Max Stock</div>

									<div class="col-md-2">
										<input type="text" id="pmax_stock" name="pmax_stock"
											value="${editItem.plantMaxStock}" class="form-control"
											style="width: 100%;">
									</div>
									<div class="col-md-2">Plant ROL Stock</div>

									<div class="col-md-2">
										<input type="text" id="prol_stock" name="prol_stock"
											value="${editItem.plantRolStock}" class="form-control"
											style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Critical Item ?</div>




									<c:choose>
										<c:when test="${editItem.isCritical==0}">
											<div class="col-md-1">

												<input type="radio" name="is_crit" id="is_crit" value="1">Yes

											</div>



											<div class="col-md-1">
												<input type="radio" name="is_crit" value="0" checked>
												No

											</div>
										</c:when>
										<c:when test="${editItem.isCritical==1}">
											<div class="col-md-1">

												<input type="radio" name="is_crit" id="is_crit" value="1"
													checked>Yes
											</div>
											<div class="col-md-1">
												<input type="radio" name="is_crit" value="0"> No

											</div>
										</c:when>
									</c:choose>


									<!-- <div class="col-md-1">
										Yes <input type="radio" name="is_crit" id="is_crit" value="1">
									</div>

									<div class="col-md-1">
										NO <input type="radio" checked name="is_crit" id="is_crit"
											value="0">
									</div> -->

									<div class="col-md-2"></div>
									<div class="col-md-2">Sort No</div>

									<div class="col-md-4">
										<input type="text" id="sort_no" name="sort_no"
											value="${editItem.sortNo}" class="form-control"
											style="width: 100%;">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">


									<div class="col-md-2">Freight Rate</div>

									<div class="col-md-4">
										<input type="text" id="freight_rate" name="freight_rate"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" style="width: 100%;"
											class="form-control" value="${editItem.freightRate}"
											oninvalid="setCustomValidity('Please enter rate')"
											onchange="try{setCustomValidity('')}catch(e){}" />
									</div>

									<div class="col-md-2">Royalty Rate</div>

									<div class="col-md-4">
										<input type="text" id="royalty_rate" name="royalty_rate"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" style="width: 100%;"
											class="form-control" value="${editItem.royaltyRate}"
											oninvalid="setCustomValidity('Please enter rate')"
											onchange="try{setCustomValidity('')}catch(e){}" />
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

	<script type="text/javascript">
		$(document).ready(function() {
			$('#bootstrap-data-table-export').DataTable();
		});
	</script>

	<script>
		jQuery(document).ready(function() {
			jQuery(".standardSelect").chosen({
				disable_search_threshold : 1,
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