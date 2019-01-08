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

<c:url var="editInAddPayRec" value="/editInAddPayRec" />

<c:url var="getDetailEditForPayRec" value="/getDetailEditForPayRec" />

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
							<%-- <div class="col-md-4" align="left">
								<a
									href="${pageContext.request.contextPath}/showMatIssueContractorList"><strong>Material
										Issue Contractor List</strong></a>
							</div> --%>

						</div>
						<div class="card-body card-block">
							<form name="submitForm" 
								action="${pageContext.request.contextPath}/editSubmitDetailPayRec"
								id="submitForm" method="post">

								<div class="row">

									<div class="col-md-2">Customer Name</div>

									<div class="col-md-2">
										<input type="text" id="custName" name="custName"
											autocomplete="off" value="${editRec.custName}" required
											class="form-control" readonly required style="width: 100%;">
									</div>

									<div class="col-md-1">Bill No.</div>

									<div class="col-md-3">
										<input type="text" id="billNo" name="billNo"
											autocomplete="off" value="${editRec.billNo}" required
											class="form-control" readonly required style="width: 100%;">
									</div>


									<div class="col-md-2">Bill Date*</div>
									<div class="col-md-2">
										<input type="text" id="billDate" name="billDate" readonly
											autocomplete="off" value="${editRec.billDate}"
											class="form-control" required style="width: 100%;">
									</div>
								</div>
								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Credit Start Date</div>

									<div class="col-md-2">
										<input type="text" id="creditDate1" name="creditDate1"
											autocomplete="off" value="${editRec.creditDate1}" required
											class="form-control" readonly required style="width: 100%;">
									</div>

									<div class="col-md-2">Credit Date</div>

									<div class="col-md-2">
										<input type="text" id="creditDate3" name="creditDate3"
											autocomplete="off" value="${editRec.creditDate3}" required
											class="form-control" readonly required style="width: 100%;">
									</div>


									<div class="col-md-2">Follow Up Date*</div>
									<div class="col-md-2">
										<input type="text" id="creditDate2" name="creditDate2"
											autocomplete="off" value="${editRec.creditDate2}"
											class="form-control" required style="width: 100%;">
									</div>
								</div>

								<div class="form-group"></div>
								<div class="row">



									<div class="col-md-2">Bill Total*</div>
									<div class="col-md-2">
										<input type="text" id="billTotal" name="billTotal"
											autocomplete="off" value="${editRec.billTotal}" readonly
											class="form-control" required style="width: 100%;">
									</div>

									<div class="col-md-2">Paid Amount*</div>
									<div class="col-md-2">
										<input type="text" id="paidAmt1" name="paidAmt1"
											autocomplete="off" value="${editRec.paidAmt}" 
											onkeypress="return allowOnlyNumber(event);"readonly
											class="form-control" required style="width: 100%;">
									</div>

									<div class="col-md-2">Pending Amount*</div>
									<div class="col-md-2">
										<input type="text" id="pendingAmt" name="pendingAmt"
										onkeypress="return allowOnlyNumber(event);"
											autocomplete="off" value="${editRec.pendingAmt}" readonly
											class="form-control" required style="width: 100%;">
									</div>
								</div>

								<input type="hidden" value="${editRec.payHeadId}"
									name="payHeadId" id="payHeadId">

								<hr>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Paid Amount</div>
									<div class="col-md-4">
										<input type="text" id="paidAmt" name="paidAmt"
										onkeypress="return allowOnlyNumber(event);" onchange="checkstock() "
											class="form-control" autocomplete="off" style="width: 100%;">
									</div>


									<div class="col-md-2">Select Transaction Type*</div>

									<div class="col-md-4">
										<select id="txType" name="txType" class="standardSelect"
											onchange="hideDiv(this.value)" tabindex="1">
											<option value="-1">Select</option>
											<option value="0">Cash</option>
											<option value="1">Online</option>
											<option value="2">Cheque</option>

										</select>
									</div>
								</div>



								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Transaction No*</div>
									<div class="col-md-3">
										<input type="text" id="txNo" name="txNo" class="form-control"
											autocomplete="off" style="width: 100%;"
											
											onkeypress="return allowOnlyNumber(event);">
									</div>

									<div class="col-md-1">Payment Date*</div>
									<div class="col-md-3">
										<input type="text" id="paymentDate" name="paymentDate"
											required class="form-control" autocomplete="off"
											style="width: 100%;">
									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">
										<input type="button" value="Add" class="btn btn-primary"
											style="align-content: center; width: 113px;" onclick=" validatedate(); add(); " />
									</div>
								</div>

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
												<th style="text-align: center">Payment Date</th>
												<th style="text-align: center">Paid Amount</th>
												<th style="text-align: center; width: 5%;">Action</th>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${editRecDetail}" var="recDetail"
												varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}</td>

													<td style="text-align: left"><c:out
															value="${recDetail.paymentDate}" /></td>


													<td style="text-align: left"><c:out
															value="${recDetail.paidAmt}" /></td>


													<td style="text-align:center"><a href="#"
														onclick="callEdit(${recDetail.payHeadId},${count.index})"><i
															class="fa fa-edit" style="color:black"></i> <span class="text-muted"></span></a>
														<a href="#"
														onclick="callDelete(${recDetail.payHeadId},${count.index})"><i
															class="fa fa fa-trash-o" style="color:black"></i> <span class="text-muted"></span></a></td>




												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>

								<div class="form-group"></div>
								<div class="col-lg-4"></div>
								<div class="col-lg-2">

									<input type="submit" class="btn btn-primary" value="Submit" 
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
			var txType = document.getElementById("txType").value;
			var txNo = document.getElementById("txNo").value;
			var isEdit = document.getElementById("isEdit").value;
			var isDelete = document.getElementById("isDelete").value;
			var index = document.getElementById("index").value;
			var paidAmt = document.getElementById("paidAmt").value;
			var payHeadId = document.getElementById("payHeadId").value;
			var paymentDate = document.getElementById("paymentDate").value;
			
		

			//alert("Inside add ajax"+paidAmt);
			$
					.getJSON(
							'${editInAddPayRec}',
							{

								isDelete : isDelete,
								isEdit : isEdit,
								index : index,
								txType : txType,
								txNo : txNo,
								paidAmt : paidAmt,
								payHeadId : payHeadId,
								paymentDate :paymentDate,
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
														+ v.payDetailId
														+ ','
														+ i
														+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete('
														+ v.payDetailId
														+ ','
														+ i
														+ ')" style="color:black"><i class="fa fa-trash"></i></a>'

													dataTable.row.add(
															[ i + 1,
																	v.paymentDate,
																	v.paidAmt,
													
																	str ])
															.draw();
												});

							});
			document.getElementById("txType").value = " ";
			document.getElementById("paymentDate").value = " ";
			document.getElementById("paidAmt").value = " ";
			document.getElementById("txNo").value = " ";
			document.getElementById("isDelete").value = 0;
			document.getElementById("isEdit").value = 0;
			document.getElementById("index").value = 0;

		}

		function callEdit(payDetailId, index) {
 
			document.getElementById("isEdit").value = "1";
			$.getJSON('${getDetailEditForPayRec}', {
				payDetailId : payDetailId,
				index : index,
				ajax : 'true',

			}, function(data) {
			
				document.getElementById("txType").value = data.typeTx;
				document.getElementById("txNo").value = data.txNo;
				document.getElementById("paymentDate").value = data.paymentDate;
				document.getElementById("paidAmt").value = data.paidAmt;
				document.getElementById("index").value = index;
				
			});

		}

		function callDelete(payDetailId, index) {

			//alert("hii");
			document.getElementById("isEdit").value = 0;
			//alert("index" + index);
			$
					.getJSON(
							'${editInAddPayRec}',
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
														+ v.payDetailId
														+ ','
														+ i
														+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete('
														+ v.payDetailId
														+ ','
														+ i
														+ ')" style="color:black"><i class="fa fa-trash"></i></a>'
													dataTable.row.add(
															[ i + 1,
																v.paymentDate,
																v.paidAmt,
																	str ])
															.draw();
												});
							});

		}

		function validate(s) {
			var rgx = /^[0-9]*\.?[0-9]*$/;
			return s.match(rgx);
		}
		function callAlert(msg) {
			alert(msg);
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
		$(function() {
			$('#submitForm').submit(
					function() {
						$("input[type='submit']", this).val("Please Wait...")
								.attr('disabled', 'disabled');
						return true;
					});
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
	<script type="text/javascript">
		$(document).ready(function() {
			$('#bootstrap-data-table').DataTable();
		});
	</script>





	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=creditDate2]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			
			$('input[id$=paymentDate]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>
	<!-- <script type="text/javascript">
function hideDiv(type){
	  
	  
	if(type==0){
	   
	    document.getElementById("hide_div").style="display:none"
	} else 
		{
		 document.getElementById("hide_div").style="visible"

		} 
	}
</script> -->
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
		
		
		
		function validatedate()
		  {
			alert("inside");
			var inputText="5555555555555555";
			alert("hello date is:::"+document.getElementById("paymentDate").value);
		  var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
		  // Match the date format through regular expression
		  
		  if(inputText.value.match(dateformat))
		  {
			  
			  document.submitForm.paymentDate.focus();
		  //Test which seperator is used '/' or '-'
		 var opera1 = inputText.value.split('/');
		  var opera2 = inputText.value.split('-');
		  lopera1 = opera1.length;
		  lopera2 = opera2.length;
		  // Extract the string into month, date and year
		  if (lopera1>1)
		  {
			  alert("/");
			  var pdate = inputText.value.split('/');
		  }
		  else if (lopera2>1)
		  {
			  alert("-");
		  var pdate = inputText.value.split('-');
		  alert(pdate);
		  }
		  var dd = parseInt(pdate[0]);
		  var mm  = parseInt(pdate[1]);
		  var yy = parseInt(pdate[2]);
		  // Create list of days of a month [assume there is no leap year by default]
		  var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
		  if (mm==1 || mm>2)
		  {
		  if (dd>ListofDays[mm-1])
		  {alert('Invalid date format!');
		  return false;
		  }
		  }
		  if (mm==2)
		  {
		  var lyear = false;
		  if ( (!(yy % 4) && yy % 100) || !(yy % 400)) 
		  {
		  lyear = true;
		  }
		  if ((lyear==false) && (dd>=29))
		  {
		  alert('Invalid date format!');
		  return false;
		  }
		  if ((lyear==true) && (dd>29))
		  {
		  alert('Invalid date format!');
		  return false;
		  }
		  }
		  }
		  else
		  {
		  alert("Invalid date format!");
		  document.submitForm.paymentDate.focus();
		  return false;
		  }
		  }
	</script>

<script>
		function checkstock() {

			var pendingAmt= document.getElementById("pendingAmt").value;
			var paidAmt= document.getElementById("paidAmt").value;
			/* alert( "min stk is"+min_stock);
			alert( "max stk is"+max_stock); */
			//var len= max_stock.length;
			  
			
			var valid = true;
			
			
			if (  paidAmt > pendingAmt  ) {

				valid = false;
			} 

			if (valid == false) {
				
				alert("Enter Paid Amount less than Pending Amount ");
				document.getElementById("paidAmt").value="";
				//document.getElementById("pmax_stock").value="";
			}
		}

		
	</script>



</body>
</html>