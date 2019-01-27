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
							<div class="col-md-3">
								<strong>${title}</strong>
							</div>
							<div class="col-md-6"></div>
							<div class="col-md-3" align="left">
								<a href="${pageContext.request.contextPath}/showItemList"
									style="color: black"><strong>Item List</strong></a>
							</div>
						</div>
						<div class="card-body card-block">
							<form action="${pageContext.request.contextPath}/insertItem"
								method="post" id="submitForm">

								<div class="row">

									<div class="col-md-2">Select Plant*</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please enter customer name')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="">Select</option>
											<c:forEach items="${plantList}" var="plant">
												<c:if test="${sessionScope.plantId==0}">
													<option value="${plant.plantId}">${plant.plantName}</option>
												</c:if>
												<c:if test="${sessionScope.plantId!=0}">
													<c:choose>
														<c:when test="${sessionScope.plantId==plant.plantId}">
															<option value="${plant.plantId}" selected>${plant.plantName}</option>
														</c:when>
														<c:otherwise>
															<option value="${plant.plantId}" disabled>${plant.plantName}</option>
														</c:otherwise>
													</c:choose>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2">Item Type*</div>
									<div class="col-md-4">
										<select id="item_type" name="item_type" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select item type')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="">Select</option>
											<c:forEach items="${itemTypeList}" var="item">
												<option value="${item.itemTypeId}">${item.itemTypeName}</option>
											</c:forEach>

										</select>
									</div>

								</div>
								<input type="hidden" name="item_id" id="item_id" value="0">
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Item Name*</div>
									<div class="col-md-4">
										<input type="text" id="item_name" name="item_name"
											class="form-control" required style="width: 100%;"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter item name')"
											onchange="try{setCustomValidity('')}catch(e){}">
									</div>

									<div class="col-md-2">Item Code*</div>

									<div class="col-md-4">
										<input type="text" id="item_code" name="item_code" required
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter item code')"
											value="${editComp.contactNo1}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>
									</div>

								</div>

								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Select UOM*</div>

									<div class="col-md-4">
										<select id="uomId" name="uomId" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select uom')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="">Select</option>
											<c:forEach items="${uomList}" var="uom">
												<option value="${uom.uomId}">${uom.uomName}</option>
											</c:forEach>
										</select>
									</div>

									<div class="col-md-2">Select Tax*</div>

									<div class="col-md-4">
										<select id="taxId" name="taxId" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select tax')"
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="">Select</option>
											<c:forEach items="${taxList}" var="tax">
												<option value="${tax.taxId}">${tax.taxName}</option>
											</c:forEach>
										</select>
									</div>
								</div>


								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Short Name*</div>
									<div class="col-md-4">
										<input type="text" id="short_name" name="short_name"
											pattern="^[A-Za-z\s]+$" autocomplete="off" required
											oninvalid="setCustomValidity('Please enter short name')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;">
									</div>

									<div class="col-md-2">Item Rate*</div>

									<div class="col-md-4">
										<input type="text" id="rate" name="rate"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" style="width: 100%;"
											class="form-control" autocomplete="off" required
											oninvalid="setCustomValidity('Please enter rate')"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>

									</div>

								</div>


								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Plant Min Stock*</div>

									<div class="col-md-2">
										<input type="text" id="pmin_stock" name="pmin_stock"
											autocomplete="off" pattern="[0-9]+(\.[0-9]{0,2})?%?"
											onchange="checkstock() "
											onkeypress="return allowOnlyNumber(event);"
											oninvalid="setCustomValidity('Please enter plan min stock')"
											class="form-control" style="width: 100%;">
									</div>
									<div class="col-md-2">Plant Max Stock*</div>

									<div class="col-md-2">
										<input type="text" id="pmax_stock" name="pmax_stock"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" autocomplete="off"
											oninvalid="setCustomValidity('Please enter plan max stock')"
											onchange="checkstock() "
											onkeypress="return allowOnlyNumber(event);"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;" required>
									</div>
									<div class="col-md-2">Plant ROL Stock*</div>

									<div class="col-md-2">
										<input type="text" id="prol_stock" name="prol_stock"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" autocomplete="off"
											oninvalid="setCustomValidity('Please enter prol stock')"
											onkeypress="return allowOnlyNumber(event);"
											onchange="checkstockvalue()"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;" required>
									</div>

								</div>


								<div class="form-group"></div>
								<div class="row">


									<div class="col-md-2">Freight Rate*</div>

									<div class="col-md-4">
										<input type="text" id="freight_rate" name="freight_rate"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" style="width: 100%;"
											class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter freight rate')"
											onchange="try{setCustomValidity('')}catch(e){}" required />
									</div>

									<div class="col-md-2">Royalty Rate*</div>

									<div class="col-md-4">
										<input type="text" id="royalty_rate" name="royalty_rate"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" style="width: 100%;"
											class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter  Royalty rate')"
											onchange="try{setCustomValidity('')}catch(e){}" required />
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Sort No(Optional)</div>

									<div class="col-md-4">
										<input type="text" id="sort_no" name="sort_no" maxlength="3"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter Sort No')"
											onchange="try{setCustomValidity('')}catch(e){}"
											pattern="[0-9]+" class="form-control" style="width: 100%;">
									</div>

								</div>

								<div class="form-group"></div>
								<div class="col-lg-4"></div>
								<div class="col-lg-3">
									<input type="submit" class="btn btn-primary" value="Submit"
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




	<script>
		function checkstock() {

			var min_stock= document.getElementById("pmin_stock").value;
			var max_stock= document.getElementById("pmax_stock").value;
			/* alert( "min stk is"+min_stock);
			alert( "max stk is"+max_stock); */
			var len= max_stock.length;
			  
			
			var valid = true;
			
			if(len!=0){
			if (  parseFloat(min_stock) > parseFloat(max_stock)  ) {

				valid = false;
			} 

			if (valid == false) {
				
				alert("Enter Minimum Stock less than Maximum ");
				//document.getElementById("pmin_stock").value="";
				document.getElementById("pmax_stock").value="";
			}
		}

		}
	</script>

	<script>
		
		function allowOnlyNumber(evt){
		    var charCode = (evt.which) ? evt.which : event.keyCode
		    if (charCode == 46){
		        var inputValue = $("#floor").val();
		        var count = (inputValue.match(/'.'/g) || []).length;
		        
		        if(count<1){
		            if (inputValue.indexOf('.') < 1){
		                return true;
		            }
		            return false;
		        }else{
		            return false;
		        }
		    }
		    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
		        return false;
		    }
		    return true;
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
</body>
</html>