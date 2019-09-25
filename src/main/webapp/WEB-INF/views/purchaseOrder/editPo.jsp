<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<c:url var="getProjectByCustId" value="/getProjectByCustId" />

<c:url var="getItemsAndEnqItemList" value="/getItemsAndEnqItemList" />


<meta name="description" content="Sufee Admin - HTML5 Admin Template">
<meta name="viewport" content="width=device-width, initial-scale=1">

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

	<jsp:include page="/WEB-INF/views/common/right.jsp"></jsp:include>

	<!-- Header-->
	<!-- Header-->



	<div class="content mt-3">
		<div class="animated fadeIn">

			<div class="row">

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<strong>${title}</strong> Quotation No : <strong>${editPo.quatationNo}</strong>
							&nbsp; Date : <strong>${editPo.qutDate}</strong>
						</div>
						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/submitEditPurchaseOrder"
								onsubmit="return confirm('Do you really want to submit the Purchase Order ?');"
								method="post">

								<div class="row">

									<div class="col-md-2">PO No.</div>

									<div class="col-md-4">
										<input type="text" id="poNo" name="poNo" class="form-control"
											value="${editPo.poNo}"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											readonly>
									</div>


								</div>

								<div class="form-group"></div>

								<div class="row">

									<div class="col-md-2">Plant</div>

									<div class="col-md-4">
										<input type="text" id="plantName" name="plantName"
											class="form-control" value="${editPo.plantName}"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											readonly>
									</div>
									<div class="col-md-2">Customer</div>
									<div class="col-md-4">
										<input type="text" id="custName" name="custName"
											class="form-control" value="${editPo.custName}"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											readonly>
									</div>

								</div>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">PO Date</div>
									<div class="col-md-4">
										<input type="text" id="poDate" name="poDate"
											class="form-control"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											value="${editPo.poDate}">
									</div>
									<div class="col-md-2">Payment Term</div>

									<div class="col-md-4">
										<input type="text" id="payTerm" name="payTerm"
											class="form-control"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											value="${editPo.payTerm}" readonly>
									</div>


								</div>



								<div class="form-group"></div>

								<div class="row">
									<div class="col-md-2">PO Validity Date</div>
									<div class="col-md-4">
										<input type="text" id="poValidityDate" name="poValidityDate"
											class="form-control"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											value="${editPo.poValidityDate}">
									</div>

									<div class="col-md-2">Delivery</div>
									<div class="col-md-4">
										<input type="text" id="delivery" name="delivery"
											class="form-control" value="${editPo.varchar1}"
											style="height: 32px; padding-bottom: 12px; font-size: 15px;"
											required>
									</div>

								</div>
								<input type="hidden" id="taxIncl" name="taxIncl"
									value="${editPo.extra1}" readonly>
								<%-- <div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Tax Include</div>
									<div class="col-md-4">
										<c:choose>
											<c:when test="${editPo.editPo==1}">
										 No
										</c:when>
											<c:otherwise>
										YES
										</c:otherwise>
										</c:choose>

									</div>

								</div>
								 --%>

								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-2">Is Tax Extra</div>
									<div class="col-md-4">
										<c:choose>
											<c:when test="${editPo.extra1==1}">
										 YES
										</c:when>
											<c:otherwise>
										  No
										</c:otherwise>
										</c:choose>

									</div>

								</div>





								<div class="card-body card-block">

									<table id="bootstrap-data-table"
										class="table table-striped table-bordered"
										data-page-length='-1'>

										<thead>

											<tr>

												<th style="text-align: center" width="2%">Sr</th>

												<th style="text-align: center" class="col-md-1">Item</th>
												<th style="text-align: center" class="col-md-1">Qty</th>
												<th style="text-align: center" class="col-md-1">PO
													Alert Qty</th>
												<th style="text-align: center" class="col-md-1">Item
													Rate</th>
												<th style="text-align: center" class="col-md-1">GST</th>
												<th style="text-align: center" class="col-md-1">Tax</th>
												<!-- <th style="text-align: center" class="col-md-1">Other
													Cost After Tax</th>
 -->
												<th style="text-align: center" class="col-md-1">Final</th>

											</tr>
										</thead>
										<tbody>

											<c:forEach items="${editPo.getPoDetailList}"
												var="getPoDetailList" varStatus="count">
												<tr>

													<td style="text-align: center">${count.index+1}</td>


													<td style="text-align: left"><c:out
															value="${getPoDetailList.itemName}" /></td>

													<td style="width: 100px"><input type="text"
														id="pOqty${getPoDetailList.itemId}"
														name="pOqty${getPoDetailList.itemId}"
														value="${getPoDetailList.poQty}" class="form-control"
														pattern="[+-]?([0-9]*[.])?[0-9]+"
														style="height: 32px; padding-bottom: 12px; text-align: right; font-size: 15px;"
														required></td>

													<td style="width: 100px"><input type="text"
														id="pOAlertqty${getPoDetailList.itemId}"
														name="pOAlertqty${getPoDetailList.itemId}"
														value="${getPoDetailList.extra1}" class="form-control"
														pattern="[+-]?([0-9]*[.])?[0-9]+"
														style="height: 32px; padding-bottom: 12px; text-align: right; font-size: 15px;"
														required></td>

													<td style="width: 100px"><input type="text"
														id="taxableAmt${getPoDetailList.itemId}"
														style="height: 32px; padding-bottom: 12px; text-align: right; font-size: 15px;"
														name="taxableAmt${getPoDetailList.itemId}"
														pattern="[+-]?([0-9]*[.])?[0-9]+"
														onchange="calFinalValue(${getPoDetailList.taxPer},${getPoDetailList.itemId});"
														value="${getPoDetailList.poRate}" class="form-control"
														required></td>

													<td style="text-align: right;"><c:out
															value="${getPoDetailList.taxPer}%" /></td>


													<td id="taxValuetd${getPoDetailList.itemId}"
														style="text-align: right"><c:out
															value="${getPoDetailList.taxAmt}" /></td>

													<%-- <td style="width: 100px"><input type="text"
														id="othCostAftTax${getPoDetailList.itemId}"
														style="height: 32px; padding-bottom: 12px; text-align: right; font-size: 15px;"
														name="othCostAftTax${getPoDetailList.itemId}"
														pattern="[+-]?([0-9]*[.])?[0-9]+"
														onchange="calFinalValue(${getPoDetailList.taxPer},${getPoDetailList.itemId});"
														value="${getPoDetailList.otherCharges}"
														class="form-control" required> <input
														type="hidden" id="taxAmt${getPoDetailList.itemId}"
														name="taxAmt${getPoDetailList.itemId}"
														value="${getPoDetailList.taxAmt}" required></td> --%>

													<td style="width: 100px"><input type="text" readonly
														id="finalAmt${getPoDetailList.itemId}"
														style="height: 32px; padding-bottom: 12px; text-align: right; font-size: 15px;"
														name="finalAmt${getPoDetailList.itemId}"
														value="${getPoDetailList.total}" class="form-control"
														readonly></td>


												</tr>
											</c:forEach>
										</tbody>

									</table>


								</div>


								<div class="form-group"></div>
								<div class="row">

									<div class="col-md-2">Remark</div>

									<div class="col-md-9">
										<input type="text" id="poRemark" name="poRemark"
											class="form-control" value="${editPo.remark}" value="-"
											style="height: 32px; padding-bottom: 12px; text-align: left; font-size: 15px;"
											required>
									</div>


								</div>
								<div class="form-group"></div>
								<div class="row">
									<div class="col-md-12" style="text-align: center">

										<button type="submit" style="height: 35px; width: 80px">Submit</button>
									</div>
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
				disable_search_threshold : 1,
				no_results_text : "Oops, nothing found!",
				width : "100%"
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#bootstrap-data-table').DataTable();
			
			 $("#selAll").click(function () {
	              $('#bootstrap-data-table tbody input[type="checkbox"]').prop('checked', this.checked);
	          });
		});
	</script>



	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=poDate]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			$('input[id$=poValidityDate]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			
		});
	</script>


	<script type="text/javascript">
		function calFinalValue(taxPer,itemId) {
			
			var taxIncl = parseInt(document.getElementById("taxIncl").value);
			
			var rate = parseFloat(document.getElementById("taxableAmt"+itemId).value);
			var othCostAftTax = parseFloat(document.getElementById("othCostAftTax"+itemId).value);
		 
			var taxValue = parseFloat((taxPer/100)*rate);
			 
			
			if(taxIncl==1){
				document.getElementById("finalAmt"+itemId).value = (rate+taxValue+othCostAftTax).toFixed(2);
				document.getElementById("taxAmt"+itemId).value = (taxValue).toFixed(2); 
				document.getElementById("taxValuetd"+itemId).innerHTML = (taxValue).toFixed(2); 
				
			}else{
				document.getElementById("finalAmt"+itemId).value = (rate+othCostAftTax).toFixed(2);
				document.getElementById("taxAmt"+itemId).value = (0).toFixed(2); 
				document.getElementById("taxValuetd"+itemId).innerHTML = (0).toFixed(2); 
			}
		
		}
		 
		</script>


	<!--  CalcAll function onchange of KM  -->

	<script type="text/javascript">
		function calcAll() {
			//alert("KM " +km);
			var isTaxInc;
			if (document.getElementById('is_tax_inc').checked) {
				isTaxInc = document.getElementById('is_tax_inc').value;
				}
			else{
				isTaxInc=0;
			}
			//alert("isTaxInc " +isTaxInc);
			
			var otherCostHeader= document.getElementById("other_cost").value;
			var tollCost= document.getElementById("toll_amt").value;
			var km= document.getElementById("no_of_km").value;
			
			var valid=true;
			
			 if(otherCostHeader==null || otherCostHeader==""){
				 valid=false;
			 }
			 else if(tollCost==null || tollCost==""){
				 valid=false;
			 }
			 else if(km==null || km==""){
			 valid=false;
 			}
	//alert("Valid " +valid);
			 if(valid==true){

		 var plantId=${quotHeader.plantIds};
		 var enqHeadId=${quotHeader.enqHeadId};
			
		 $.getJSON('${getItemsAndEnqItemList}', {

				plantId : plantId,
				enqHeadId : enqHeadId,
				ajax : 'true',

			},

			function(data) {
				//alert("data " +JSON.stringify(data));
				var len = data.length;
				
				 if(tollCost==null || tollCost==""){
					tollCost=0;
				 }
				 
				 for (var i = 0; i < len; i++) {
					 var frRate=parseFloat(data[i].freightRate);
					 var transCost=frRate*km;
					 var itemRate=parseFloat(data[i].itemRate1);
					 var royRate=parseFloat(data[i].royaltyRate);
					 var taxPer=parseFloat(data[i].totalTaxPer);
					 
					 if(isTaxInc==0){
						 taxPer=0;
					 }
					// alert("transCost" +transCost);
					
					document.getElementById("trans_cost"+data[i].itemId).value=transCost.toFixed(2);
					document.getElementById("toll_cost"+data[i].itemId).value=tollCost;
					document.getElementById("other_cost"+data[i].itemId).value=otherCostHeader;
					
					 var otherCost= document.getElementById("other_cost"+data[i].itemId).value;
					 
					 if(otherCost==null || otherCost==""){
						 otherCost=0;
					 }
					 
					 var taxableAmt=parseFloat(itemRate)+parseFloat(tollCost)+parseFloat(transCost)+parseFloat(otherCost)+parseFloat(royRate);
					// alert("taxabelAmt " +taxableAmt); 
					 document.getElementById("taxable_amt"+data[i].itemId).value=taxableAmt;

					 var taxAmt=(taxableAmt*taxPer)/100;
					 document.getElementById("tax_amt"+data[i].itemId).value=taxAmt;
					 
					var otherCostAfterTax= document.getElementById("oth_cost_aft_tax"+data[i].itemId).value;
					 
					 if(otherCostAfterTax==null || otherCostAfterTax==""){
						 otherCostAfterTax=0;
					 }
					 var finalAmt=parseFloat(taxableAmt)+parseFloat(taxAmt)+parseFloat(otherCostAfterTax);

					 document.getElementById("final_amt"+data[i].itemId).value=finalAmt;
					 
				 } 
				 
			});
 
			 }
		}
</script>


</body>
</html>