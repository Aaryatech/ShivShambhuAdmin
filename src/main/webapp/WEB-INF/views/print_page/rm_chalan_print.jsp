<%@page
contentType="text/html; charset=utf-8"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%-- <jsp:useBean id="currency" scope="session"
             class="com.ats.ssgs.common.Currency"/>
 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Chalan Print</title>
</head>
<body>

<table style="margin-left: 100px;" width="100%"  >
 <tr>
 <td  align="left"><img  src="/ssgs/resources/images/shambhu_logo.PNG" style="height: 80px;width: 80px;" alt="User Avatar"/></td>
    <td colspan="6" rowspan="3" width="50%" style="border-bottom:1px solid #313131;border-top:1px solid #313131 padding:10px;color:#FFF; font-size:15px;">
     <p style="color:black; font-size:20px; text-align:center; margin:0px;font-weight: bold;">${printData.comp.compName}</p>
 <p style="color:#000; font-size:11px; text-align:right;margin:0px;">${printData.comp.compOfficeAdd}<br></br> Mobile No. ${printData.comp.contactNo1} <br></br>Email: ${printData.comp.email1}</p>

</td>
</tr>
</table>
<hr style="margin-left: 100px;"></hr>
<br></br>
<!-- <p style="text-align: center; font-size: 14px;font-weight: bold;"><u>Chalan</u></p>
 -->
<p style="margin-left: 100px; text-align: left" ><b>Chalan No:</b>${printData.chalanItemList[0].chalanNo}
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Date:</b> ${printData.chalanItemList[0].chalanDate}

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Vehicle No:</b> ${printData.chalanItemList[0].vehNo}</p>
<p style="margin-left: 100px;"><b>Customer Name:</b>
<c:out value="${printData.chalanItemList[0].custName}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Project:</b> ${printData.chalanItemList[0].projName}
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>RST No:</b> ${printData.chalanItemList[0].exVar1}<br></br></p>
<p style="margin-left: 100px; text-align: left" ><b>Delivery Address:</b>
</p>





<table  width="1%" border="0" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131; border-right: 1px solid #313131; margin-left: 100px;" >
<tr>
<td rowspan="2" width="2%" style="border-bottom: 1px solid #313131; border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 12px;">Sr. No.</td>
<td align="left" width="35%" rowspan="2" style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 12px; text-align: left">Item Name</td>
<td align="left" width="5%" rowspan="2"style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 12px; text-align: left">Order Quantity</td>
<td align="left" width="5%" rowspan="2" style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 12px; text-align: left">Current Chalan Quantity</td>
<td align="left" width="5%" rowspan="2"	style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 12px; text-align: left">Total Chalan Quantity</td>
</tr>
			
			
			
<c:forEach items="${printData.chalanItemList}" var="quot" varStatus="count3">

<tr>
<td  style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${count3.index+1}</td>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemName}</td>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.totalQuan}</td>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemQty}</td>

<c:forEach items="${rstList}" var="temp" varStatus="count3">
<c:if test="${quot.itemId==temp.itemId}">
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${temp.result}</td>
</c:if>
</c:forEach>

</tr>

</c:forEach>
</table>
		


<p style="margin-left: 100px;" align="left" ><b>Out Time:</b>${printData.chalanItemList[0].vehTimeOut}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Out Km:</b>${printData.chalanItemList[0].outKm}</p>
<p style="margin-left: 100px;" align="left"><b>In Time:</b>${temp}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>In Km:</b>${printData.chalanItemList[0].inKm}</p>
<p style="margin-left: 100px;"><b>Driver :${printData.chalanItemList[0].usrName}</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Site Incharge</b></p>
<p style="margin-left: 100px;"><b>Sign</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b></b>${printData.chalanItemList[0].sitePersonName}</p>
<table  width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131; border-right: 1px solid #313131; margin-left: 100px;" >
<tr>
<td align="left" width="35%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 15px; text-align: left">
<b>${printData.comp.compName}</b>
<br/><br/><br/><br/><br/><br/>
<b>Authorized Signature</b>
</td>
<td align="left" width="35%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 12px; text-align: left">
<p style="font-size: 12px; text-align: left">I/we Clerify that to the best of myfour knowledge the<br/> particulars are true and correct and complete</p>
<br/><br/><br/><br/>
<p style="font-size: 12px; text-align: left">Authorize Signature & Name with Seal of Receiver <br/><center>(of/on behalf of Customer)</center></p>

</td>
</tr>

</table>

<div style="page-break-after: always;" ></div>
 
</body>
</html>