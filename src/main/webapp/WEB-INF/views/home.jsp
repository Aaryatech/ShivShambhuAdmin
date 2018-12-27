<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>

<link rel="apple-touch-icon" href="apple-icon.png">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/favicon.ico">

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
	href="${pageContext.request.contextPath}/resources/assets/scss/style.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/menu.css">

<link
	href="${pageContext.request.contextPath}/resources/assets/css/lib/vector-map/jqvmap.min.css"
	rel="stylesheet">

<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800'
	rel='stylesheet' type='text/css'>


<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

<style type="text/css">
.bg-overlay {
 /*   background: linear-gradient(rgb(186, 178, 132), rgba(155, 208, 52, 0.4)),   url(/ssgs/resources/images/stone.jpeg), url("${pageContext.request.contextPath}/resources/images/stone.jpeg");
  background-repeat: no-repeat; */
    background-size: cover;
    background-position: center center;
    color: #fff;
    height:auto;
  
   
}
/* .card{
background-color: transparent;

} */
</style> 
</head>


<!-- 
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script> -->


<body onload="setData()" class="bg-overlay">
	<c:url var="getChartData" value="/getGraphDataForDistwiseOrderHistory"></c:url>

	<c:url var="getCatOrdQty" value="/getCatOrdQty"></c:url>

	<c:url var="getCatwiseTrend" value="/getCatwiseTrend"></c:url>



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

					<div class="col-xs-12 col-sm-12">




						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">
							<div class="card text-white bg-flat-color-2" style="  background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.totalEnq}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total No of Enquiry</font>
									</p>

								</div>
							</div>

						</div>



						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2" style="  background: #59c9f2;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.totalQuotPending}</span>
									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Pending Quotations</font>
									</p>

								</div>
							</div>
						</div>


						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2" style="  background: #22a3ac;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.totalQuotGenerated}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Generated Quotations</font>
									</p>

								</div>
							</div>

						</div>

					</div>

					<div class="col-xs-12 col-sm-12">

						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">
							<div class="card text-white bg-flat-color-3" style="  background: #20a8d8;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.totalPoPending}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Pending Po</font>
									</p>


								</div>
							</div>

						</div>


						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-3" style="  background: #59c9f2;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.totalOrderAmount}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Order Amount</font>
									</p>

								</div>
							</div>

						</div>



						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-3" style="  background: #20a8d8;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.totalBillAmount}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Bill Amount</font>
									</p>

								</div>
							</div>

						</div>

						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2" style="  background: #4f90b9;">
								<div class="card-body pb-0" align="center" >

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.totalTaxBillAmt}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Tax Bill Amount</font>
									</p>

								</div>
							</div>

						</div>




						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2" style="  background: #64c2de;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.totalTaxableBillAmt}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Taxable Bill Amount</font>
									</p>

								</div>
							</div>

						</div>





						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-2" style="  background: #4f90b9;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.paymentRecPaid}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Amount recovery</font>
									</p>

								</div>
							</div>

						</div>






						<div class="col-sm-6 col-lg-4" style="cursor: pointer;max-width: 27.333333%;">

							<div class="card text-white bg-flat-color-3" style="  background: #64c2de;">
								<div class="card-body pb-0" align="center">

									<h4 class="mb-0">

										<span class="count" style="font-size: 30px;">${dashBoard.paymentRecOutstandingPending}</span>

									</h4>
									<p style="font-size: 15px; font-weight: bold; color: white;">
										<font color="white">Total Outstanding Amount</font>
									</p>

								</div>
							</div>

						</div>


					</div>




				</div>
			</div>
		</div>
	</div>




	<script
		src="${pageContext.request.contextPath}/resources/assets/js/vendor/jquery-2.1.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/plugins.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>


	<script
		src="${pageContext.request.contextPath}/resources/assets/js/dashboard.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/widgets.js"></script>





</body>
</html>
