<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>

<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin Login</title>
<meta name="description" content="Web Panel Login">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="apple-touch-icon"
	href="${pageContext.request.contextPath}/resources/apple-icon.png">
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
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800'
	rel='stylesheet' type='text/css'>
	<link href="https://fonts.googleapis.com/css?family=Abril+Fatface" rel="stylesheet">
	
<style type="text/css">
.card-header {
    background-color: rgba(0,0,0,0.00);
    }
    
</style>

</head>
<body class="bg-dark" style="background-color: #e7e1bf!important;">

	<div align="right" style="margin-right: 15px;"></div>

	<div class="sufee-login d-flex align-content-center flex-wrap"
		style="position: absolute; top: 35%; left: 50%; width: 30em; height: 18em; margin-top: -9em; margin-left: -15em;">

		<div class="container">
			<div class="login-content">

				<div align="center"></div>

				<form action="loginProcess" method="post">
					<div class="col-lg-12" style="padding-right: 2px; padding-left: 0px;">
						<div class="card" style="border-radius: 2.25rem;" >

							<div class="card-header" align="center" style="font-family: 'Abril Fatface', cursive; font-size: 17px; font-weight: bold;color:#245345;">LOGIN</div>
							<div class="card-body card-block">

								<c:if test="${not empty errorMessage}">
									<div class="alert alert-danger" role="alert">${errorMessage}</div>
								</c:if>


								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-user"></i>
										</div>
										<input type="text" id="username" name="username"  style="margin-left: 4px; font-size:0.96em; border-radius:20px;"
											maxlength="10" placeholder="User Name" class="form-control"
											autocomplete="off">
									</div>
								</div>

								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-asterisk"></i>
										</div>
										<input type="password" id="password" name="password"
											placeholder="Password" class="form-control" style="margin-left: 4px; font-size:0.96em; border-radius:20px;"
											autocomplete="off">
									</div>
								</div>
								<button type="submit" class="btn btn-primary"
									style="align-content: center; width: 90px; background-color:#6a7464; border-radius:21px; border-color: #748279; margin-left:120px;">
									Login</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>


	<script
		src="${pageContext.request.contextPath}/resources/assets/js/vendor/jquery-2.1.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/plugins.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>


</body>
</html>
