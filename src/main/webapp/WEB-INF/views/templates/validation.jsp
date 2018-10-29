<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>




<form id=”myForm”>
 <input type=”text” name=”cardnumber” id=”cardnumber”
  required  oninvalid="setCustomValidity('Please enter valid mobile no ')"
    onchange="try{setCustomValidity('')}catch(e){}"
   pattern="[0-9]{10}" >
  
   <button>Submit</button>
</form>




</body>
</html>