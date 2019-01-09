<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>


<c:url var="getChalanListByPlant" value="/getChalanListByPlant" />
<%-- 

<c:url var="getCustInfoByCustId" value="/getCustInfoByCustId" />

<c:url var="getProjectByCustId" value="/getProjectByCustId" />


<c:url var="getPoDetailForOrderByPoId"
	value="/getPoDetailForOrderByPoId" />
	
	
<c:url var="getTempOrderHeader"
	value="/getTempOrderHeader" /> --%>
	
<style>
.buttonload {
    background-color: white; /* Green background */
    border: none; /* Remove borders */
    color: #ec268f; /* White text */
    padding: 12px 15px; /* Some padding */
    font-size: 13px; /* Set a font-size */
    display:none;
}

/* Add a right margin to each icon */
.fa {
    margin-left: -12px;
    margin-right: 8px;
}
 ::-webkit-scrollbar{
        height: 10px;
        width: 6px;
        background:#868e96;
    }
    ::-webkit-scrollbar-thumb:horizontal{
        background: #000;
        border-radius: 10px;
    }
</style>
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

<style>
.alert {
    padding: 20px;
    background-color: red;
    color: white;
    
}
.alert1 {
    padding: 20px;
    background-color: green;
    color: white;
    
}

.closebtn {
    margin-left: 15px;
    color: white;
    font-weight: bold;
    float: right;
    font-size: 22px;
    line-height: 20px;
    cursor: pointer;
    transition: 0.3s;
}

.closebtn:hover {
    color: black;
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
							
							<div class="alert">
							
							<span class="closebtn"
						onclick="this.parentElement.style.display='none';">&times;</span>
					<strong>Failed !</strong>     Data not submitted  !!
				</div>
							
							</c:when>
							
							<c:when test="${isError==2}">
							
							<div class="alert1">
							
							<span class="closebtn"
						onclick="this.parentElement.style.display='none';">&times;</span>
					<strong>Success</strong>     Data Submitted !!
				</div>
							
							</c:when>
							
							</c:choose>

				<div class="col-xs-12 col-sm-12">
					<div class="card">
					
						<div class="card-header">
							<div class="col-md-2">
								<strong>${title}</strong>
							</div>
							
							

						</div>
						<div class="card-body card-block">
							<form
								action="${pageContext.request.contextPath}/deleteRecordofChalan"
								method="post">
								<%-- <div class="row">

									<div class="col-md-2">Select Plant</div>

									<div class="col-md-4">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required
											oninvalid="setCustomValidity('Please select plant name')"
											onchange="getData()">
											<option value="">Select Plant</option>

											<c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select>
									</div>
									
									<div class="col-md-2">
										<input type="button"   onclick="showChalan1()" value="Submit 11">
									</div>
								</div>
								 --%>
								 
								 
<div class="row">

									<div class="col-md-1">Plant</div>

									<div class="col-md-2">
										<select id="plant_id" name="plant_id" class="standardSelect"
											tabindex="1" required>
											<option value="">Select Plant</option>

											<c:forEach items="${plantList}" var="plant">
												<option value="${plant.plantId}">${plant.plantName}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-1">From</div>
									<div class="col-md-2">
										<input type="text" id="from_date" name="from_date" required
											style="width: 100%;" class="form-control"
											value="${fromDate}"> <span class="error"
											aria-live="polite"></span>
									</div>
                                    <div class="col-md-1">To</div>
									<div class="col-md-2">
										<input type="text" id="to_date" name="to_date" required
											style="width: 100%;" class="form-control"
											value="${toDate}"> <span class="error"
											aria-live="polite"></span>
									</div>
										<div class="col-md-3">
										<input type="button" class="btn btn-primary" id="searchButton" value="Search"  onclick="showChalan()" >
<button class="buttonload" id="loader">
                                   <i class="fa fa-spinner fa-spin"></i>Loading
                                   </button>
									</div>
								</div>
								

								<div class="form-group"></div>
								
							
								
								<%-- <input type="checkbox" value="${item.itemId}" name="selectItem"> --%>
								
								<div class="card-body card-block">
									<table id="bootstrap-data-table"
										class="table table-striped table-bordered">
										<thead>
											<tr>
												<th style="text-align: center"><input
												type="checkbox" name="selAll" id="selAll" /> </th>
												<th style="text-align: center">Sr.</th>
												<th style="text-align: center">Chalan No</th>
												<th style="text-align: center">Chalan Date</th>
												<th style="text-align: center">Customer Name</th>
												<th style="text-align: center">Project Name</th>
												<th style="text-align: center">Vehicle No</th>
												<th style="text-align: center">Driver Name</th>
												
											<th style="text-align: center">Status</th>
 											<th style="text-align: center">Action</th>
											</tr>
										</thead>

									</table>
									
									<div class="col-md-2"></div>

								<div class="col-md-3">

									<button type="button" class="btn btn-primary"
										onclick="exportToExcel();" disabled="disabled" id="expExcel"
										style="align-content: center; width: 200px; margin-left: 80px;">
										Export To Excel</button>
								</div>


								<div class="col-md-3">

									<button type="button" class="btn btn-primary"
										onclick="genPdf()" disabled="disabled" id="PDFButton"
										style="align-content: center; width: 100px; margin-left: 80px;">
										PDF</button>
								</div>
								&nbsp;
								</div>

							<input type="submit" class="btn btn-primary" value="Delete"
										id="deleteId"
										onClick="var checkedVals = $('.chk:checkbox:checked').map(function() { return this.value;}).get();checkedVals=checkedVals.join(',');if(checkedVals==''){alert('No Rows Selected');return false;	}else{   return confirm('Are you sure want to delete record');}"
										style="align-content: center; width: 113px; margin-left: 40px;">
						</form>	
	</div>
						</div>
					</div>
				</div>
			</div>


		</div>
		<!-- .animated -->
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
		});
	</script>



	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$('input[id$=from_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});
			
			$('input[id$=to_date]').datepicker({
				dateFormat : 'dd-mm-yy'
			});

		});
	</script>

	<script type="text/javascript">
	
	// on plant change function 
		function getData() { 
			var plantId = document.getElementById("plant_id").value;
			var valid = true;

			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please select plant");
			}

			if (valid == true) {

				$.getJSON('${getCustByPlantId}', {
					plantId : plantId,
					ajax : 'true',
				},

				function(data) {
					var html;
					var len = data.length;
					var html = '<option selected value="-1"  >Select</option>';

					for (var i = 0; i < len; i++) {

						html += '<option value="' + data[i].custId + '">'
								+ data[i].custName + '</option>';

					}
					html += '</option>';

					$('#cust_name').html(html);
					$("#cust_name").trigger("chosen:updated");
					/* getCustInfo();

					$('#po_id').html("-1");
					$("#po_id").trigger("chosen:updated");
					 */
					var dataTable = $('#bootstrap-data-table')
					.DataTable();
			dataTable.clear().draw();

				});
			}//end of if

		}
	</script>



	<script type="text/javascript">
	// onclick of submit to search order 
		function showChalan() {
		
		//alert("Hi View showChalan  ");
	
			var plantId = document.getElementById("plant_id").value;
			var valid = true;

			var fromDate = $("#from_date").val();
			var toDate = $("#to_date").val();
			var d1 = fromDate.split("-");
			var d2 = toDate.split("-");
			d1 = d1[2].concat(d1[1], d1[0]);
			d2 = d2[2].concat(d2[1], d2[0]);
			///alert("fromDate " +fromDate);
			///alert("toDate " +toDate);
			
			if (plantId == null || plantId == "") {
				valid = false;
				alert("Please select plant");
			}else if(fromDate==null || fromDate==""){
				valid=false;
				alert("Please select from date")
			}else if(toDate==null || toDate==""){
				valid=false;
				alert("Please select to date");
			}else if (parseInt(d1) > parseInt(d2)) {
				isValid = false;
			    alert("From Date must be less than To Date!!");
			} 
			
			if(valid==true){
			//	alert("plant Id " +plantId);
				$('#loader').show();
				$
						.getJSON(
								'${getChalanListByPlant}',
								{
									plantId : plantId,
									fromDate : fromDate,
									toDate : toDate,
									ajax : 'true',
								},

								function(data) {
									$('#loader').hide();
									document.getElementById("expExcel").disabled = false;
									document.getElementById("PDFButton").disabled = false;

									if (data == "") {
										alert("No records found !!");
										document.getElementById("expExcel").disabled = true;
										document.getElementById("PDFButton").disabled = true;

									}

									
									//alert("Order Data " +JSON.stringify(data));
									var chBox;
									 var dataTable = $('#bootstrap-data-table')
									.DataTable();
							dataTable.clear().draw();

							$.each(data,function(i, v) {
								var status;
								if(v.exFloat1==0){
										status="Open";		
										 var acButton = '<a href="#" class="action_btn" onclick="callEdit('
												+ v.chalanId
												+ ','
												+ i
												+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete('
														+ v.chalanId
														+ ','
														+ i
														+ ')" style="color:black"><i class="fa fa-trash"></i></a>&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callClose('
															+ v.chalanId
															+ ','
															+ i
															+ ')" style="color:black"><i class="fa fa-times"></i></a>&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callPdf('
															+ v.chalanId
															+ ','
															+ i
															+ ')" style="color:black"><i class="fa fa-file-pdf-o"></i></a>'
															
															 chBox = '<input  type="checkbox" class="chk" name="selectChalanToDelete" id='+v.chalanId+' class="check"  value='+v.chalanId+'>'
								}else{
									status="Closed";	
									var acButton = '<a href="#" class="action_btn" onclick="callEdit1('
										+ v.chalanId
										+ ','
										+ i
										+ ')" style="color:black"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callDelete1('
													+ v.chalanId
													+ ','
													+ i
													+ ')" style="color:black"><i class="fa fa-times"></i></a>&nbsp;&nbsp;<a href="#" class="action_btn" onclick="callPdf('
														+ v.chalanId
														+ ','
														+ i
														+ ')" style="color:black"><i class="fa fa-file-pdf-o"></i></a>'
									
									chBox=""
									
									
								}

 
												dataTable.row
														.add(
																[chBox,
																		i + 1,
																		v.chalanNo,
																		v.chalanDate,
																		v.custName,
																		v.projName,
																		v.vehNo,
																		v.driverName,
																		status,
																		acButton
																		 ])
														.draw();
											}); 
						
								});	
				
}//end of if valid ==true
						
		}
	
	function callEdit(chalanId){
		
		window.open("${pageContext.request.contextPath}/editChalan/"+chalanId);
		
	}
	
function callClose(chalanId){
		
		window.open("${pageContext.request.contextPath}/closeChalan/"+chalanId);
		
	}
	
	function callPdf(chalanId,key){
		//alert("call Pdf " +chalanId);
		   window.open('${pageContext.request.contextPath}/pdf?url=pdf/showChalanPdf/'+chalanId);

	}
	
	function callDelete(chalanId,key){
		//alert("call Pdf " +chalanId);
		   window.open('${pageContext.request.contextPath}/deleteChalan/'+chalanId,"_self");

	}
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
		<script type="text/javascript">
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			
		
			var plantId= document.getElementById("plant_id").value;
		
			//alert("plant_id"+plant_id);
			

			window.open('${pageContext.request.contextPath}/showOpenChalanListPdf/'
					+ plantId);
			document.getElementById("expExcel").disabled = true;

		}
	</script>

		



</body>
</html>