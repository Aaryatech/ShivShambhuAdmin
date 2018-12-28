<%@page
contentType="text/html; charset=ISO8859_1"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <jsp:useBean id="currency" scope="session"
             class="com.ats.ssgs.common.Currency"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Quotation Print</title>
</head>
<body>
<c:forEach items="${quotPrintData}" var="printData" varStatus="count">



<table width="100%" border="0"  cellpadding="0" cellspacing="0" style="border-left:1px solid #313131;border-right:1px solid #313131;border-top:1px solid #313131;">
  <tr>
    <td colspan="6" rowspan="3" width="50%" style="border-bottom:1px solid #313131;border-top:1px solid #313131 padding:10px;color:#FFF; font-size:15px;">
     <p style="color:black; font-size:20px; text-align:center; margin:0px;font-weight: bold;">${printData.comp.compName}</p>
 <p style="color:#000; font-size:11px; text-align:right;margin:0px;">${printData.comp.compOfficeAdd}<br></br> Mobile No. ${printData.comp.contactNo1} <br></br>Email: ${printData.comp.email1}</p>

</td>
</tr>
</table>
<hr></hr>
<b>To</b><br></br>
<c:out value="${printData.cust.custName}"></c:out>


<table  width="100%" border="0"  cellpadding="0" cellspacing="0" style="border-right:1px solid #313131">
<thead>
<tr>
<th style="border-left:1px solid #AAAAAA;border-top:1px solid #AAAAAA;
  padding: 2px ; border-bottom:1px solid #AAAAAA;;
  text-align: center;">Sr No</th>
<th style="border-left:1px solid #AAAAAA;border-top:1px solid #AAAAAA;
  padding: 2px ;border-bottom:1px solid #AAAAAA;
  text-align: center;">Material</th>
<th style="border-left:1px solid #AAAAAA; border-top:1px solid #AAAAAA;
  padding: 2px ;border-bottom:1px solid #AAAAAA;
  text-align: center;">Unit</th>
<th style="border-left:1px solid #AAAAAA;border-top:1px solid #AAAAAA;
  padding: 2px 2px;
  text-align: center;border-bottom:1px solid #AAAAAA;">Rate / Brass</th>
</tr>
</thead>
<tfoot>
<!-- <tr>
<td colspan="4">
<div class="links"><a href="#">&laquo;</a> <a class="active" href="#">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">&raquo;</a></div>
</td>
</tr> -->
</tfoot>
<tbody>


<c:forEach items="${printData.quotDetPrint}" var="quot" varStatus="count">

<tr>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${count.index+1}</td>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemName}</td>
<td style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.uomName}</td>
<td style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.total}</td>

</tr>
</c:forEach>
</tbody>

</table>
<c:forEach items="${printData.docTermList}" var="docTerm" varStatus="count1">

<p>${count1.index} ${docTerm.termDesc}</p>
<div style="page-break-after: always;" ></div>
</c:forEach>
</c:forEach>
</body>
</html>