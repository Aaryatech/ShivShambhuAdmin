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
							<form
								action="${pageContext.request.contextPath}/insertRawMatItem"
								method="post" id="submitForm">

								<div class="row">

									<div class="col-md-2">Select Category*</div>

									<div class="col-md-4">
										<select id="catId" name="catId" class="standardSelect"
											tabindex="1" required
											onchange="try{setCustomValidity('')}catch(e){}">
											<option value="">Select</option>
											<c:forEach items="${catList}" var="cat">
												<c:choose>
													<c:when test="${cat.catId==editItem.catId}">
														<option value="${cat.catId}" selected>${cat.catDesc}</option>
													</c:when>
													<c:otherwise>
														<option value="${cat.catId}">${cat.catDesc}</option>
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>
									</div>

								</div>
								<input type="hidden" name="itemId" id="itemId" value="0">
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Item Code*</div>

									<div class="col-md-4">
										<input type="text" id="itemCode" name="itemCode" required
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter item code')"
											value="${editComp.contactNo1}"
											onchange="try{setCustomValidity('')}catch(e){}" /> <span
											class="error" aria-live="polite"></span>
									</div>

									<div class="col-md-2">Item Description*</div>
									<div class="col-md-4">
										<textarea id="itemDesc" name="itemDesc" class="form-control"
											style="width: 100%;" autocomplete="off"
											oninvalid="setCustomValidity('Please enter Item Description')"
											maxlength="200"
											onchange="try{setCustomValidity('')}catch(e){}" required>${editBankDetail.bankAddress}</textarea>
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

									<div class="col-md-2">Date*</div>
									<div class="col-md-4">
										<input type="text" id="itemDate" name="itemDate"
											autocomplete="off" value="${editPro.startDate}" readonly
											class="form-control" required style="width: 100%;">
									</div>


								</div>





								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Item OP Qty*</div>

									<div class="col-md-2">
										<input type="text" id="opQty" name="opQty" autocomplete="off"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" onchange="checkstock() "
											onkeypress="return allowOnlyNumber(event);"
											oninvalid="setCustomValidity('Please enter plan min stock')"
											class="form-control" style="width: 100%;">
									</div>
									<div class="col-md-2">OP Rate*</div>

									<div class="col-md-2">
										<input type="text" id="opQty" name="opRate"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" autocomplete="off"
											oninvalid="setCustomValidity('Please enter plan max stock')"
											onchange="checkstock() "
											onkeypress="return allowOnlyNumber(event);"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;" required>
									</div>


									<div class="col-md-2">CL Qty*</div>

									<div class="col-md-2">
										<input type="text" id="clQty" name="clQty" autocomplete="off"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" onchange="checkstock() "
											onkeypress="return allowOnlyNumber(event);"
											oninvalid="setCustomValidity('Please enter plan min stock')"
											class="form-control" style="width: 100%;">
									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">CL Rate*</div>

									<div class="col-md-2">
										<input type="text" id="clRate" name="clRate"
											pattern="[0-9]+(\.[0-9]{0,2})?%?" autocomplete="off"
											oninvalid="setCustomValidity('Please enter plan max stock')"
											onchange="checkstock() "
											onkeypress="return allowOnlyNumber(event);"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;" required>
									</div>

									<div class="col-md-2">Item Life*</div>

									<div class="col-md-2">
										<input type="text" id="itemLife" name="itemLife"
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter item life')"
											onchange="try{setCustomValidity('')}catch(e){}" required />
									</div>

									<div class="col-md-2">Item Schd*</div>

									<div class="col-md-2">
										<input type="text" id="itemSchd" name="itemSchd"
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter  Royalty rate')"
											onchange="try{setCustomValidity('')}catch(e){}" required />
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">


									<div class="col-md-2">Item Min Level*</div>

									<div class="col-md-2">
										<input type="text" id="minLevel" name="minLevel"
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter Minimum Level')"
											onchange="try{setCustomValidity('')}catch(e){}" required />
									</div>

									<div class="col-md-2">Item Max Level**</div>

									<div class="col-md-2">
										<input type="text" id="maxLevel" name="maxLevel"
											style="width: 100%;" class="form-control" autocomplete="off"
											oninvalid="setCustomValidity('Please enter  Maximum level')"
											onchange="try{setCustomValidity('')}catch(e){}" required />
									</div>

									<div class="col-md-2">Item Rod Level*</div>

									<div class="col-md-2">
										<input type="text" id="rodLevel" name="rodLevel"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter Rod level')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Item Location*</div>

									<div class="col-md-2">
										<input type="text" id="itemLocation" name="itemLocation"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter Location')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;">
									</div>
									<div class="col-md-2">Item Weight*</div>

									<div class="col-md-2">
										<input type="text" id="itemWeight" name="itemWeight"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter Weight')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;">
									</div>

									<div class="col-md-2">Item Abc*</div>

									<div class="col-md-2">
										<input type="text" id="itemAbc" name="itemAbc"
											autocomplete="off"
											oninvalid="setCustomValidity('Please enter ')"
											onchange="try{setCustomValidity('')}catch(e){}"
											class="form-control" style="width: 100%;">
									</div>

								</div>
								<div class="form-group"></div>
								<div class="row">



									<div class="col-md-2">Item Is Critical*</div>
									<div class="col-md-4">
										<select class="standardSelect" title="Please Select"
											name="isCritical" id="isCritical" required>
											<option value="">Select</option>
											<c:choose>
												<c:when test="${editItem.itemIsCritical==0}">
													<option value="0" selected>NO</option>
													<option value="1">YES</option>
												</c:when>
												<c:when test="${editItem.itemIsCritical==1}">
													<option value="0">NO</option>
													<option value="1" selected>YES</option>
												</c:when>
												<c:otherwise>
													<option value="0">NO</option>
													<option value="1">YES</option>
												</c:otherwise>

											</c:choose>

										</select>
									</div>


									<div class="col-md-2">Item Is Capital*</div>
									<div class="col-md-4">
										<select class="standardSelect" title="Please Select"
											name="isCapital" id="isCapital" required>
											<option value="">Select</option>
											<c:choose>
												<c:when test="${editItem.itemIsCapital==0}">
													<option value="0" selected>NO</option>
													<option value="1">YES</option>
												</c:when>
												<c:when test="${editItem.itemIsCapital==1}">
													<option value="0">NO</option>
													<option value="1" selected>YES</option>
												</c:when>
												<c:otherwise>
													<option value="0">NO</option>
													<option value="1">YES</option>
												</c:otherwise>

											</c:choose>

										</select>

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

	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=itemDate]').datepicker({
				dateFormat : 'dd-mm-yy',
				changeMonth:true,
				changeYear:true
				
				
			});
			
		});
	</script>

</body>
</html>