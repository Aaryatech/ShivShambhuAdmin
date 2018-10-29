<%@page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>



<title>Sufee Admin - HTML5 Admin Template</title>

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
<!-- <link rel="stylesheet" href="assets/css/bootstrap-select.less"> -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/scss/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/lib/chosen/chosen.min.css">

<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800'
	rel='stylesheet' type='text/css'>



<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/gtranslatepopupblock.css">
	

<!-- DatePicker -->
<link
	href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script>
	$(function() {
		$("#datepicker-12").datepicker();
	});
</script>

<!-- DatePicker -->





</head>


<!-- Translate -->


<script type="text/javascript" src="http://www.google.com/jsapi"></script>

<script type="text/javascript">
google.load("elements", "1", {packages: "transliteration"});
</script> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script>
function OnLoad() {                
    var options = {
        sourceLanguage:
        google.elements.transliteration.LanguageCode.ENGLISH,
        destinationLanguage:
        [google.elements.transliteration.LanguageCode.MARATHI],
        shortcutKey: 'ctrl+g',
        transliterationEnabled: true
    };

    var control = new google.elements.transliteration.TransliterationControl(options);
    control.makeTransliteratable(["txtMarathi"]);
    var keyVal = 32; // Space key
    $("#txtEnglish").on('keydown', function(event) {
        if(event.keyCode === 32) {
            var engText = $("#txtEnglish").val() + " ";
            var engTextArray = engText.split(" ");
            $("#txtMarathi").val($("#txtMarathi").val() + engTextArray[engTextArray.length-2]);

            document.getElementById("txtMarathi").focus();
            $("#txtMarathi").trigger ( {
                type: 'keypress', keyCode: keyVal, which: keyVal, charCode: keyVal
            } );
        }
    });

    $("#txtMarathi").bind ("keyup",  function (event) {
        setTimeout(function(){ $("#txtEnglish").val($("#txtEnglish").val() + " "); document.getElementById("txtEnglish").focus()},0);
    });
} //end onLoad function

google.setOnLoadCallback(OnLoad);
</script> 
<!-- Translate -->
	<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>

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

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<strong>Masked Input</strong> <small> Small Text Mask</small>
						</div>
						<div class="card-body card-block">
							<div class="form-group">

								<div class="col-lg-6">

									<p>
										<label class=" form-control-label"></label>Date input
									</p>
									Date Picker <input type="text" id="datepicker-12">

								</div>

								<div class="col-lg-6">
									<label>Phone input</label>
									<div>

										<input type="text">
									</div>
									<small class="form-text text-muted">ex. (999) 999-9999</small>
								</div>
							</div>
							<div class="form-group">
								<label class=" form-control-label">Name in English</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-phone"></i>
									</div>
									<input class="form-control" id="txtEnglish">
								</div>
							</div>
							
							
							<div class="form-group">
								<label class=" form-control-label">Marathi Name</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-phone"></i>
									</div>
									<input class="form-control" id="txtMarathi">
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
								<small class="form-text text-muted">ex. 99-9999999</small>
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
								<small class="form-text text-muted">ex. 999-99-9999</small>
							</div>
							<div class="form-group">
								<label class=" form-control-label">Eye Script</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-asterisk"></i>
									</div>
									<input class="form-control">
								</div>
								<small class="form-text text-muted">ex. ~9.99 ~9.99 999</small>
							</div>
							<div class="form-group">
								<label class=" form-control-label">Credit Card Number</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-credit-card"></i>
									</div>
									<input class="form-control">
								</div>
								<small class="form-text text-muted">ex. 9999 9999 9999
									9999</small>
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



			</div>


		</div>
		<!-- .animated -->
	</div>
	<!-- .content -->


	
	<!-- /#right-panel -->

	<!-- Right Panel -->


	<script
		src="${pageContext.request.contextPath}/resources/assets/js/vendor/jquery-2.1.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/plugins.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/lib/chosen/chosen.jquery.min.js"></script>

	<script>
		jQuery(document).ready(function() {
			jQuery(".standardSelect").chosen({
				disable_search_threshold : 10,
				no_results_text : "Oops, nothing found!",
				width : "100%"
			});
		});
	</script>
	
	
	
<script>
    function googleTranslateElementInit() {
        new google.translate.TranslateElement({
            pageLanguage: 'en,mr', 
            includedLanguages: 'mr,en', 
            layout: google.translate.TranslateElement.InlineLayout.SIMPLE,
            autoDisplay: false
        }, 'google_translate_element');
        var a = document.querySelector("#google_translate_element");
        a.selectedIndex=1;
        a.dispatchEvent(new Event('change'));
        
        $('.google_translate_element').each(function(){
            id=$(this).data('label-for');
            $('#'+id).val( $(this).text() );
         });
        
        
    }
</script>

</body>
</html>