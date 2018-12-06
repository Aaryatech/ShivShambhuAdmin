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
	</head>
<body>

	<c:url var="getItemList" value="/getItemList"></c:url>
<c:url var="getDispatchItemsByDate" value="/getDispatchItemsByDate"/>
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
								<strong>Dispatch Sheet</strong>
							</div>
											

						</div>
						<div class="card-body card-block">
								<div class="row">

									<div class="col-md-1">Plant</div>

									<div class="col-md-3">
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
										<div class="col-md-1">
										<input type="button" class="btn btn-primary" id="searchButton" value="Search"  onclick="searchItem()" >

									</div>
								</div>
								
								
								<div class="card-body card-block" style="overflow: scroll;  width: 72vw;">
									<table id="table1"
										class="table table-striped table-bordered"  style="table-layout: none; width:100%">
										<thead>
											<tr>
											<th>Sr.No.</th>
											<th>Item Name</th>
											<th>Item Code</th>
											
											</tr>
										</thead>
                                         <tbody>
                                         
                                         </tbody> 
									</table>
								</div>
								<input type="button" id="pdf" value="PDF" class="btn btn-primary"
													onclick="genPdf()" disabled/>
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
	function reformatDateString(s) {
		  var b = s.split(/\D/);
		  return b.reverse().join('-');
		}
		function searchItem() {
			
			 
		
		
		
			  $('#table1 td').remove();
			$('#table1 th').remove();
			var isValid = validate();

			if (isValid) {
				var plantId = $("#plant_id").val();
				var startDate = reformatDateString($("#from_date").val());
				var endDate=reformatDateString($("#to_date").val());
				var listDate = [];
				
				var dateMove = new Date(startDate);
				var strDate = startDate;

				while (strDate < endDate){
				  var strDate = dateMove.toISOString().slice(0,10);
				  listDate.push(strDate);
				  dateMove.setDate(dateMove.getDate()+1);
				};
			 
		        
				$.getJSON('${getItemList}',{
					
					                plantId : plantId,
									listDate : JSON.stringify(listDate),
									fromDate:$("#from_date").val(),
			                     	toDate:$("#to_date").val(),
									ajax : 'true'

								},
								function(data) {

									$('#table1 td').remove();

									if (data == "") {
										alert("No records found !!");
										document.getElementById("pdf").disabled = true;

									}
									else{ 
										document.getElementById("pdf").disabled = false;

										   var tr;	var k=3;
									        tr = document.getElementById('table1').tHead.children[0];
									        tr.insertCell(0).outerHTML = "<th aligh='right'>Sr.</th>"
									        tr.insertCell(1).outerHTML = "<th aligh='right'>Item Name</th>"
									        tr.insertCell(2).outerHTML = "<th aligh='right'>Item Code</th>"
									        var colCount=listDate.length;
									        
									        	for (index = 0; index < listDate.length; ++index) {
									        	
									        		 tr.insertCell(k+index).outerHTML = "<th><input type='date' id=from_date"+index+" name=from_date"+index+" required	style=width: 100%; onchange=onDateChange("+index+","+index+") class='form-control' value="+listDate[index]+" ><a href=# onclick=onDateChange("+index+","+colCount+")> <i class='fa fa-trash-o'></i></a></th>";
									        	}
									       
									      tr.insertCell(k+listDate.length).outerHTML = "<th align='right'>Plan_Qty</th>"
								$
										.each(
												data.itemList,
												function(i, v) {
													
													var tr = $('<tr></tr>');
													 
												  	tr.append($('<td></td>').html(""+(i+1)));

												  	tr.append($('<td></td>').html(v.itemName));
												  	tr.append($('<td></td>').html(v.itemCode));

												  	for (index = 0; index < listDate.length; ++index) {
											        	
											        	
										        		 tr.append($('<td></td>').html('<input type=text readonly min=0 max=30  id="date'+index+''+v.itemId+'" name="date'+index+''+v.itemId+'" style="text-align:right;" class=form-control value="0.0" />'));
										        	}
													tr.append($('<td></td>').html('<input type=text  min=0 max=30  id="prod_plan_qty'+index+''+v.itemId+'" name="prod_plan_qty'+index+''+v.itemId+'" style="text-align:right;" class=form-control value="0.0" />'));
												  	$('#table1').append(tr);
												});
								
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date1,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
																document.getElementById("date"+0+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date2,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
																document.getElementById("date"+1+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date3,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
															
																document.getElementById("date"+2+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date4,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
																
																document.getElementById("date"+3+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date5,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
															
																document.getElementById("date"+4+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date6,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
																document.getElementById("date"+5+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date7,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
																document.getElementById("date"+6+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date8,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
															
																document.getElementById("date"+7+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date9,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
															
																document.getElementById("date"+8+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date10,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
															
																document.getElementById("date"+9+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date11,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
															
																document.getElementById("date"+10+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
								
								$
								.each(
										data.itemList,
										function(i, v) {
								$
												.each(
														data.date12,
														function(j, m) {
															
															if(m.itemId==v.itemId)
																{
															
																document.getElementById("date"+11+''+v.itemId).value =m.remQty;

																}
														});
										  
										});
									}
								  	
										
									
									} );  

							}
		}
		 
	</script>
	
	<script type="text/javascript">
	
	
	
		function validate() {

			var plantId = $("#plant_id").val();
			var fromDate = $("#from_date").val();
			var toDate = $("#to_date").val();
			
			var isValid = true;

			 if (plantId == "" || plantId == null) {

				isValid = false;
				alert("Please Select Plant");

			} else if (fromDate == "" || fromDate == null) {

				isValid = false;
				alert("Please Select From Date");

			}else if (toDate == "" || toDate == null) {

				isValid = false;
				alert("Please Select To Date");

			}else
				{
				
				
				var d1 = fromDate.split("-");
				var d2 = toDate.split("-");

				d1 = d1[2].concat(d1[1], d1[0]);
				d2 = d2[2].concat(d2[1], d2[0]);

				if (parseInt(d1) > parseInt(d2)) {
					isValid = false;
				    alert("From Date must be less than To Date!!");
				} else {

				}
				/* var total_days = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);

                 if(total_days>12)
                 {
                	alert("From Date and To Date Difference should not be gretor than 12");
                 } */
				}
			return isValid;

		}
	</script>
	 <script type="text/javascript">
	 function onDateChange(index,key)
		{
		 alert(key);	alert(index);
			var plantId=document.getElementById("plant_id").value;
			var date=document.getElementById("from_date"+index).value;
		
			$.getJSON('${getDispatchItemsByDate}',{
				
	            plantId : plantId,
				date:date,
				ajax : 'true'

			},
			function(data) {
				
				$
				.each(
						data.itemList,
						function(i, v) {
							document.getElementById("date"+key+''+v.itemId).value='0.0';

							
						});
				
				if (data == "") {
					alert("No records found !!");
				}
				else{
				
					$
					.each(
							data.date1,
							function(i, v) {
								
					document.getElementById("date"+key+''+v.itemId).value=v.remQty;
							});
				if(key!=index)
					{
					$
					.each(
							data.date1,
							function(i, v) {
								
					document.getElementById("prod_plan_qty"+key+''+v.itemId).value=v.remQty;
							});
					}
				}
			});
			
			
			
		}
	 
	 </script>
	 <script type="text/javascript">

function genPdf(){
	window.open('${pageContext.request.contextPath}/showDispatchPdf');
}

</script>
</body>
</html>