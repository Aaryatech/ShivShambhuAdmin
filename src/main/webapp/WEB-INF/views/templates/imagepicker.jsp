<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript"></script>

</head>


<body>

	<form id="form1">

		<input type="hidden" id="url" value='${url}' /> <input type='file'
			id="imgInp" /> <img id="imageFile" src="#" alt="your image"
			style="width: 100px; height: 100px" align="top" />
	</form>



	<script type="text/javascript">
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#imageFile').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#imgInp").change(function() {
			var url = $('#url').val();
			alert(url);
			readURL(this);
		});
	</script>
</body>
</html>