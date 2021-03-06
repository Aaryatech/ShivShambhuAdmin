<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getRawItemByCatId" value="/getRawItemByCatId" />

<c:url var="editInAddItemDetail" value="/editInAddItemDetail" />

<c:url var="getDetailEditForItemDetail"
	value="/getDetailEditForItemDetail" />

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
							<div class="col-md-4">
								<strong>${title}</strong>
							</div>
							<div class="col-md-4"></div>
							<div class="col-md-4" align="left">
								<%-- <a
									href="${pageContext.request.contextPath}/showMatIssueContractorList"><strong>Material
										Issue Contractor List</strong></a> --%>
							</div>

						</div>
						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/insertItemDetail"
								id="submitForm" onsubmit="disableSubmitButton()" method="post">


								<div class="row">

									<div class="col-md-2">Item Name*</div>
									<div class="col-md-4">
										<input type="text" maxlength="10" class="form-control"
											style="width: 100%;" autocomplete="off" value="${itemName}"
											readonly>
									</div>

									<div class="col-md-2">Item Code*</div>
									<div class="col-md-4">
										<input type="text" id="itemCode" name="itemCode"
											maxlength="10" class="form-control" style="width: 100%;"
											autocomplete="off" value="${itemCode}" readonly>
									</div>


								</div>
								<hr>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Select Category*</div>
									<div class="col-md-4">
										<select id="catId" name="catId" class="standardSelect"
											tabindex="1" onchange="getItemByCatId()">

											<option value="-1">Select</option>
											<c:forEach items="${catList}" var="cat">
												<option value="${cat.catId}">${cat.catDesc}</option>
											</c:forEach>
										</select>
									</div>


									<div class="col-md-2">Select Item*</div>

									<div class="col-md-4">
										<select id="rmName" name="rmName" class="standardSelect">
											<option value="-1">Select</option>


										</select>
									</div>
								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Quantity</div>
									<div class="col-md-4">
										<input type="text" id="qty" name="qty" class="form-control"
											autocomplete="off" style="width: 100%;"
											pattern="[0-9]+(\.[0-9]{0,2})?%?"
											onkeypress="return allowOnlyNumber(event);">
									</div>

									<!-- <div class="col-md-2">UOM*</div>
									<div class="col-md-4">
										<input type="text" id="uom" name="uom" class="form-control"
											autocomplete="off" style="width: 100%;" value="kg" readonly>
									</div> -->

									<div class="col-md-2"></div>
									<div class="col-md-2">
										<input type="button" value="Add" class="btn btn-primary"
											style="align-content: center; width: 113px;" onclick="add()" />
									</div>


								</div>

								<div class="form-group"></div>
								<div class="row">


									<input type="hidden" id="itemIdnew" name="itemIdnew"
										value="${itemId}">
								</div>

								<div class="form-group"></div>
								<div class="row"></div>

								<input type="hidden" id="isDelete" name="isDelete" value="0">
								<input type="hidden" name="isEdit" id="isEdit" value="0">
								<input type="hidden" name="index" id="index" value="0">
								<div class="form-group"></div>




								<div class="card-body card-block">

									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>

												<th style="text-align: center; width: 5%;">Sr No</th>
												<th style="text-align: center">Item Name</th>
												<th style="text-align: center">Measurement of Unit</th>
												<th style="text-align: center">Item Quantity</th>
												<!-- <th style="text-align: center">Item Category</th> -->
												<th style="text-align: center; width: 5%;">Action</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${editItemDetail}" var="itemDetail"
												varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}</td>

													<td style="text-align: left"><c:out
															value="${itemDetail.rmName}" /></td>

													<td style="text-align: left"><c:out
															value="${itemDetail.uomName}" /></td>

													<td style="text-align: left"><c:out
															value="${itemDetail.rmQty}" /></td>

													<%-- 	<td style="text-align: left"><c:out
															value="${itemDetail.catDesc}" /></td>

 --%>
													<%-- <td style="text-align: left"><c:out
															value="${itemDetail.value}" /></td> --%>


													<td style="text-align: center"><a href="#"
														onclick="callEdit(${itemDetail.itemDetailId},${count.index})"><i
															class="fa fa-edit" style="color: black"></i> <span
															class="text-muted"></span></a> <a href="#"
														onclick="callDelete(${itemDetail.itemDetailId},${count.index})"><i
															class="fa fa-trash-o" style="color: black"></i> <span
															class="text-muted"></span></a></td>

												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>

								<div class="form-group"></div>
								<div class="col-lg-4"></div>
								<div class="col-lg-2">

									<input type="submit" class="btn btn-primary" value="Submit"
										id="submitButton" disabled
										style="align-content: center; width: 113px; margin-left: 20px;">

								</div>
								<div class="col-lg-2">

									<input type="reset" class="btn btn-primary" value="Clear"
										style="align-content: center; width: 113px; margin-left: 20px;">

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
		function add() {
			//alert("in add  ");
			var rmName = document.getElementById("rmName").value;
			var qty = document.getElementById("qty").value;
			var isEdit = document.getElementById("isEdit").value;
			var isDelete = document.getElementById("isDelete").value;
			var index = document.getElementById("index").value;
			var catId = document.getElementById("catId").value;
		
		/* 	var uom = document.getElementById("uom").value; */
			//alert("data is"+rmName    +qty  +index  +catId  +uom);

			//alert("Inside add ajax");
			$
					.getJSON(
							'${editInAddItemDetail}',
							{
								
								isDelete : isDelete,
								isEdit : isEdit,
								index : index,
								rmName : rmName,
								qty : qty,
								/* uom:uom, */
								catId : catId,
								
								ajax : 'true',

							},

							function(data) {

								//alert("Data " + JSON.stringify(data));

								var dataTable = $('#bootstrap-data-table')
										.DataTable();
								dataTable.clear().draw();

								$
										.each(
												data,
												function(i, v) {

													if (v.isDuplicate == 1) {
														alert("Item Already Added");
													}

													var str = '<a href="#" class="action_btn" onclick="callEdit('
															+ v.itemId
															+ ','
															+ i
															+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete('
															+ v.itemId
															+ ','
															+ i
															+ ')" style="color:black"><i class="fa fa-trash"></i></a>'

													dataTable.row.add(
															[ i + 1,
																	v.rmName,
																	v.uomName,
																	v.rmQty,
																	
																	str ])
															.draw();
												});

							});
			//document.getElementById("itemName").options.selectedIndex=0;
			document.getElementById("qty").value = "";
			document.getElementById("isDelete").value = 0;
			document.getElementById("isEdit").value = 0;
			document.getElementById("index").value = 0;

			document.getElementById("submitButton").disabled = false;

		}
		
		
		function callEdit(itemDetailId, index) {
		/* 	alert("hii");
			alert(itemDetailId); */

			document.getElementById("isEdit").value = "1";
			$.getJSON('${getDetailEditForItemDetail}', {
				itemDetailId : itemDetailId,
				index : index,
				ajax : 'true',

			}, function(data) {
			// alert("data" + data);
				//alert(data.int1); 
				$("#catId").val(data.int1);
				$("#catId").trigger("chosen:updated");
				
				document.getElementById("rmName").value = data.itemId;
				
				document.getElementById("qty").value = data.rmQty;
				
			
				document.getElementById("index").value = index;
				//alert(data.int1); 
				
			

				$.getJSON('${getRawItemByCatId}', {

					catId :data.int1,
					ajax : 'true',

				},

				function(data1) {
					 
					var html;
					var n="-";
					
					var len = data1.length;
					 
					var html = '<option value="-1"  >Select Item</option>';
					for (var i = 0; i < len; i++) {
		
						if(data1[i].itemId==data.rmId){
							 
							html += '<option value="' + data1[i].itemId + '" selected>'
							+ data1[i].itemDesc + '</option>';
							
						}else{
							 
							html += '<option value="' + data1[i].itemId + '">'
							+ data1[i].itemCode + n 	+ data1[i].itemDesc + '</option>';
							
						}

						
					}
					//html += '</option>';

					$('#rmName').html(html);
					//$("#itemName").trigger("chosen:updated");

						//$("#rmName").val(data.rmId);
					$("#rmName").trigger("chosen:updated");
					//alert(data.itemId);

				});


			});

		}


	
		function callDelete(itemDetailId, index) {

			//alert("hii");
			document.getElementById("isEdit").value = 0;
			//alert("index" + index);
			$
					.getJSON(
							'${editInAddItemDetail}',
							{
								isDelete : 1,
								isEdit : 0,
								key : index,
								ajax : 'true',

							},

							function(data) {
								
								var dataTable = $('#bootstrap-data-table')
										.DataTable();
								dataTable.clear().draw();
								$
										.each(
												data,
												function(i, v) {

													var str = '<a href="#" class="action_btn" onclick="callEdit('
															+ v.itemId
															+ ','
															+ i
															+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete('
															+ v.itemId
															+ ','
															+ i
															+ ')" style="color:black"><i class="fa fa-trash"></i></a>'
													dataTable.row.add(
															[ i + 1,
																	v.rmName,
																	v.uomName,
																	v.rmQty,
																	
																	str ])
															.draw();
												});
							});

		}

	
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
			var dataTable = $('#bootstrap-data-table').DataTable();
			columnDefs: [ {
				targets : [ 2 ],
				className : "right"
			}, ]

		});
	</script>
	<script type="text/javascript">
		/* 	$(function() {
				$('#submitForm').submit(
						function() {
							$("input[type='submit']", this).val("Please Wait...")
									.attr('disabled', 'disabled');
							return true;
						});
			}); */
		function disableSubmitButton() {
			document.getElementById('submitButton').innerHTML = 'Please Wait';
			document.getElementById("submitButton").disabled = true;
		}
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
	<script type="text/javascript">
		$(document).ready(function() {
			$('#bootstrap-data-table').DataTable();
		});
	</script>





	<script type="text/javascript">
		function getItemByCatId() {
			//document.getElementById("submitButton").disabled=true;

			var catId = document.getElementById("catId").value;
				//alert("catId Id " + catId);
				

			var valid = true;

			if (catId == null || catId == "") {
				valid = false;
				alert("Please Select Category");
			}

			if (valid == true) {

				$.getJSON('${getRawItemByCatId}', {

					catId : catId,
					ajax : 'true',

				},

				function(data) {
					//alert("hiii");
					var html;
					var n="-";
					var len = data.length;
					//alert("length is"+len);
					var html = '<option selected value="-1"  >Select</option>';
					for (var i = 0; i < len; i++) {
						//alert(data[i].itemCode)
						
						html += '<option value="' + data[i].itemId + '">'
								+ data[i].itemCode + n + data[i].itemDesc + '</option>';
					}
					html += '</option>';

					$('#rmName').html(html);
					$("#rmName").trigger("chosen:updated");

				});

			}

		}
	</script>


	<script type="text/javascript">
		function getUom() {
			//document.getElementById("submitButton").disabled=true;

			/* var catId = document.getElementById("catId").value;
				alert("catId Id " + catId);
				

			var valid = true;

			if (catId == null || catId == "") {
				valid = false;
				alert("Please Select Category");
			} */

			<!-- if (valid == true) {

				$.getJSON('${getOneRmItem}', {

					//catId : catId,
					ajax : 'true',

				},

				function(data) {
					//alert("hiii");
					var html;
					var len = data.length;
					alert("length is"+len);
					var html = '<option selected value="-1"  >Select</option>';
					
						
						
						html += '<option value="' + data. + '">'
								+ data[i].itemCode + '</option>';
				
					html += '</option>';

					$('#uom').html(html);
					$("#uom").trigger("chosen:updated");

				});

			}

		}
		</script>
	-->
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>

</body>
</html>