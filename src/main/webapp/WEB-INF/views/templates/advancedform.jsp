<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Advanced Form</title>
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

	<!-- Left Panel -->
	<jsp:include page="/WEB-INF/views/common/left.jsp"></jsp:include>
	<!-- Left Panel -->


	<!-- Header-->
	<jsp:include page="/WEB-INF/views/common/right.jsp"></jsp:include>
	<!-- Header-->


	<div class="breadcrumbs">
		<div class="col-sm-4">
			<div class="page-header float-left">
				<div class="page-title">
					<h1>Dashboard</h1>
				</div>
			</div>
		</div>
		<div class="col-sm-8">
			<div class="page-header float-right">
				<div class="page-title">
					<ol class="breadcrumb text-right">
						<li><a href="#">Dashboard</a></li>
						<li><a href="#">Forms</a></li>
						<li class="active">Advanced</li>
					</ol>
				</div>
			</div>
		</div>
	</div>

	<div class="content mt-3">
		<div class="animated fadeIn">

			<div class="row">

				<div class="col-xs-6 col-sm-6">
					<div class="card">
						<div class="card-header">
							<strong>Masked Input</strong> <small> Small Text Mask</small>
						</div>
						<div class="card-body card-block">

							<div class="form-group">
								<label class=" form-control-label">Date input</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="datepicker">
								</div>
								<small class="form-text text-muted">ex. 99/99/9999</small>
							</div>

							<div class="form-group">
								<label class=" form-control-label">Phone input</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-phone"></i>
									</div>
									<input class="form-control">
								</div>
							</div>

							<div class="form-group">
								<label class=" form-control-label">Taxpayer
									Identification Numbers</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-usd"></i>
									</div>
									<input class="form-control">
								</div>
							</div>

							<div class="form-group">
								<label class=" form-control-label">Social Security
									Number</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-male"></i>
									</div>
									<input class="form-control">
								</div>
							</div>

							<div class="form-group">
								<label class=" form-control-label">Eye Script</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-asterisk"></i>
									</div>
									<input class="form-control">
								</div>
							</div>

							<div class="form-group">
								<label class=" form-control-label">Credit Card Number</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-credit-card"></i>
									</div>
									<input class="form-control">
								</div>
							</div>
							
						</div>
					</div>
				</div>

				<div class="col-xs-6 col-sm-6">
					<div class="card">
						<div class="card-header">
							<strong class="card-title">Standard Select</strong>
						</div>
						<div class="card-body">

							<select data-placeholder="Choose a Country..."
								class="standardSelect" tabindex="1">
								<option value=""></option>
								<option value="United States">United States</option>
								<option value="United Kingdom">United Kingdom</option>
								<option value="Afghanistan">Afghanistan</option>
								<option value="Aland Islands">Aland Islands</option>
								<option value="Albania">Albania</option>
								<option value="Algeria">Algeria</option>
								<option value="American Samoa">American Samoa</option>
								<option value="Andorra">Andorra</option>
								<option value="Angola">Angola</option>
								<option value="Anguilla">Anguilla</option>
								<option value="Antarctica">Antarctica</option>
							</select>
						</div>
					</div>

					<div class="card">
						<div class="card-header">
							<strong class="card-title">Multi Select</strong>
						</div>
						<div class="card-body">

							<select data-placeholder="Choose a country..." multiple
								class="standardSelect">
								<option value=""></option>
								<option value="United States">United States</option>
								<option value="United Kingdom">United Kingdom</option>
								<option value="Afghanistan">Afghanistan</option>
								<option value="Aland Islands">Aland Islands</option>
								<option value="Albania">Albania</option>
								<option value="Algeria">Algeria</option>
								<option value="American Samoa">American Samoa</option>
								<option value="Andorra">Andorra</option>
								<option value="Angola">Angola</option>
								<option value="Anguilla">Anguilla</option>
								<option value="Antarctica">Antarctica</option>
							</select>

						</div>
					</div>

					<div class="card">
						<div class="card-header">
							<strong class="card-title">Multi Select with Groups</strong>
						</div>
						<div class="card-body">

							<select data-placeholder="Your Favorite Football Team" multiple
								class="standardSelect" tabindex="5">
								<option value=""></option>
								<optgroup label="NFC EAST">
									<option>Dallas Cowboys</option>
									<option>New York Giants</option>
									<option>Philadelphia Eagles</option>
									<option>Washington Redskins</option>
								</optgroup>
								<optgroup label="NFC NORTH">
									<option>Chicago Bears</option>
									<option>Detroit Lions</option>
									<option>Green Bay Packers</option>
									<option>Minnesota Vikings</option>
								</optgroup>
								<optgroup label="NFC SOUTH">
									<option>Atlanta Falcons</option>
									<option>Carolina Panthers</option>
									<option>New Orleans Saints</option>
									<option>Tampa Bay Buccaneers</option>
								</optgroup>
								<optgroup label="NFC WEST">
									<option>Arizona Cardinals</option>
									<option>St. Louis Rams</option>
									<option>San Francisco 49ers</option>
									<option>Seattle Seahawks</option>
								</optgroup>
								<optgroup label="AFC EAST">
									<option>Buffalo Bills</option>
									<option>Miami Dolphins</option>
									<option>New England Patriots</option>
									<option>New York Jets</option>
								</optgroup>
								<optgroup label="AFC NORTH">
									<option>Baltimore Ravens</option>
									<option>Cincinnati Bengals</option>
									<option>Cleveland Browns</option>
									<option>Pittsburgh Steelers</option>
								</optgroup>
								<optgroup label="AFC SOUTH">
									<option>Houston Texans</option>
									<option>Indianapolis Colts</option>
									<option>Jacksonville Jaguars</option>
									<option>Tennessee Titans</option>
								</optgroup>
								<optgroup label="AFC WEST">
									<option>Denver Broncos</option>
									<option>Kansas City Chiefs</option>
									<option>Oakland Raiders</option>
									<option>San Diego Chargers</option>
								</optgroup>
							</select>

						</div>
					</div>

				</div>
				<!-- Dropdown Ends Here -->

				<!-- /# column -->
				<div class="col-lg-6">
					<div class="card">
						<div class="card-header">
							<h4>Custom Tab</h4>
						</div>
						<div class="card-body">
							<div class="custom-tab">

								<nav>
									<div class="nav nav-tabs" id="nav-tab" role="tablist">
										<a class="nav-item nav-link active" id="custom-nav-home-tab"
											data-toggle="tab" href="#custom-nav-home" role="tab"
											aria-controls="custom-nav-home" aria-selected="true">Home</a>
							
										<a class="nav-item nav-link" id="custom-nav-profile-tab"
											data-toggle="tab" href="#custom-nav-profile" role="tab"
											aria-controls="custom-nav-profile" aria-selected="false">Profile</a>
									
										<a class="nav-item nav-link" id="custom-nav-contact-tab"
											data-toggle="tab" href="#custom-nav-contact" role="tab"
											aria-controls="custom-nav-contact" aria-selected="false">Contact</a>
									
										<a class="nav-item nav-link" id="custom-nav-day4-tab"
											data-toggle="tab" href="#custom-nav-day4" role="tab"
											aria-controls="custom-nav-day4" aria-selected="false">Day4</a>
								
										<a class="nav-item nav-link" id="custom-nav-day5-tab"
											data-toggle="tab" href="#custom-nav-day5" role="tab"
											aria-controls="custom-nav-day5" aria-selected="false">Day5</a>
									</div>
								</nav>
								<div class="tab-content pl-3 pt-2" id="nav-tabContent">
									<div class="tab-pane fade show active" id="custom-nav-home"
										role="tabpanel" aria-labelledby="custom-nav-home-tab">
										<p>Some sample text here.</p>
									</div>
									<div class="tab-pane fade" id="custom-nav-profile"
										role="tabpanel" aria-labelledby="custom-nav-profile-tab">
										<p>some sample text 2 .</p>
									</div>
									<div class="tab-pane fade" id="custom-nav-contact"
										role="tabpanel" aria-labelledby="custom-nav-contact-tab">
										<p>Some sample text 3.</p>
									</div>
									<div class="tab-pane fade" id="custom-nav-day4" role="tabpanel"
										aria-labelledby="custom-nav-day4-tab">
										<p>Some sample text 4.</p>
									</div>
									<div class="tab-pane fade" id="custom-nav-day5" role="tabpanel"
										aria-labelledby="custom-nav-day5-tab">
										<p>Some sample text 5.</p>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- /# column -->



			</div>


		</div>
		<!-- .animated -->
	</div>
	<!-- .content -->



	<div class="content mt-3">
		<div class="animated fadeIn">
			<div class="row">

				<div class="col-md-12">
					<div class="card">
						<div class="card-header">
							<strong class="card-title">Data Table</strong>
						</div>
						<div class="card-body">
							<table id="bootstrap-data-table"
								class="table table-striped table-bordered">
								<thead>
									<tr>
									<th class="no-sort sorting_desc">
            <input type="checkbox" id="flowcheckall" value="" />&nbsp;All
        </th>
										<th>Name</th>
										<th>Position</th>
										<th>Office</th>
										<th>Salary</th>
									</tr>
								</thead>
								<tbody>
									<tr >
									<td><input type='checkbox' id='checkall' name='mydata' value="1 "></td>
										<td>Tiger Nixon</td>
										<td>System Architect</td>
										<td>Edinburgh</td>
										<td>$320,800</td>
									</tr>
									<tr >
									<td><input type='checkbox' id='checkall' name='mydata' value="2 "></td>
										<td>Garrett Winters</td>
										<td>Accountant</td>
										<td>Tokyo</td>
										<td>$170,750</td>
									</tr>
									<tr>
									<td><input type='checkbox' id='checkall' name='mydata' value="3 "></td>
										<td>Ashton Cox</td>
										<td>Junior Technical Author</td>
										<td>San Francisco</td>
										<td>$86,000</td>
									</tr>
									<tr>
									<td><input type='checkbox' id='checkall' name='mydata' value="1 "></td>
										<td>Cedric Kelly</td>
										<td>Senior Javascript Developer</td>
										<td>Edinburgh</td>
										<td>$433,060</td>
									</tr>
									<tr>
									<td><input type='checkbox' id='checkall' name='mydata' value="1 "></td>
										<td>Airi Satou</td>
										<td>Accountant</td>
										<td>Tokyo</td>
										<td>$162,700</td>
									</tr>
									<tr>
									<td><input type='checkbox' id='checkall' name='mydata' value="1 "></td>
										<td>Brielle Williamson</td>
										<td>Integration Specialist</td>
										<td>New York</td>
										<td>$372,000</td>
									</tr>
									<tr>
									<td><input type='checkbox' id='checkall' name='mydata' value="1 "></td>
										<td>Herrod Chandler</td>
										<td>Sales Assistant</td>
										<td>San Francisco</td>
										<td>$137,500</td>
									</tr>
									<tr>
									<td><input type='checkbox' id='checkall' name='mydata' value="1 "></td>
										<td>Rhona Davidson</td>
										<td>Integration Specialist</td>
										<td>Tokyo</td>
										<td>$327,900</td>
									</tr>
									<tr>
									<td><input type='checkbox' id='checkall' name='mydata' value="1 "></td>
										<td>Colleen Hurst</td>
										<td>Javascript Developer</td>
										<td>San Francisco</td>
										<td>$205,500</td>
									</tr>
									<tr>
									<td><input type='checkbox' id='checkall' name='mydata' value="1 "></td>
										<td>Sonya Frost</td>
										<td>Software Engineer</td>
										<td>Edinburgh</td>
										<td>$103,600</td>
									</tr>
	

								</tbody>
							</table>
						</div>
					</div>
				</div>


			</div>
		</div>
		<!-- .animated -->
	</div>
	<!-- .content -->


	</div>
	<!-- /#right-panel -->



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
                disable_search_threshold: 10,
                no_results_text: "Oops, nothing found!",
                width: "100%"
            });
        });
    </script>

	<script type="text/javascript">
        $(document).ready(function() {
           $('#bootstrap-data-table-export').DataTable(); 


          $("#flowcheckall").click(function () {
              $('#bootstrap-data-table tbody input[type="checkbox"]').prop('checked', this.checked);
          });
          
        } );
    </script>


	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
  $( function() {
	  $('input[id$=datepicker]').datepicker({
		    dateFormat: 'dd-mm-yy'
		});
  } );
  </script>


</body>
</html>