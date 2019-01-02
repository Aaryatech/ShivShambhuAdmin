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
<c:out value="${printData.chalanItemList[0].custName}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Project:</b> ${printData.chalanItemList[0].projName}<br></br></p>


<%-- <table  width="80%" border="0"  cellpadding="0" cellspacing="0" style="border-right:1px solid #313131; margin-left:100px; ">
<tr>
<td  style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ; border-bottom:1px solid #000;;
  text-align: center;">Sr No</td>
<td style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">Material</td>
  </tr>
  <tr>
  <td rowspan="2" style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">Plant Measure</td>
  <td colspan="4" style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">Site Measure</td>
<td   rowspan="2" style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">L</td>
  <td  style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">W</td>
  <td  style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">H</td>
  
  <td   style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">T</td>
  </tr>
  


<c:forEach items="${printData.chalanItemList}" var="quot" varStatus="count3">

<tr>
<td  style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemId}</td>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemName}</td>
</tr>
<tr>
<td colspan="2"  style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemName}</td>
<td colspan="2"  align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000;  font-size:10px;">${quot.itemName}</td>
<td colspan="2"  style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemName}</td>
<td colspan="2"  align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000;  font-size:10px;">${quot.itemName}</td>
</tr>

</c:forEach>

</table> --%>

<table  width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131; border-right: 1px solid #313131; margin-left: 100px;" >
<tr>
				<td rowspan="2" width="2%"
					style="border-bottom: 1px solid #313131; border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 12px;">Sr. No.</td>
				<td align="left" width="35%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 12px; text-align: left">Item
					Name</td>
				<td align="center" width="10%" colspan="4"
					style="border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 12px; text-align: center;">
					Plant</td>
				<td align="center" width="10%" colspan="4"
					style="border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 12px; text-align: center;">Site</td>
			</tr>
			<tr>
				<td align="center" width="2%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;">L
				</td>
				<td align="center" width="2%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;">W</td>
					<td align="center" width="2%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;">H
				</td>
				<td align="center" width="2%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;">T</td>
				<td align="center" width="2%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;">L
				</td>
				<td align="center" width="2%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;">W</td>
					<td align="center" width="2%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;">H
				</td>
				<td align="center" width="2%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;">T</td>
			</tr>
			
			<c:forEach items="${printData.chalanItemList}" var="quot" varStatus="count3">

<tr>
<td  style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${count3.index+1}</td>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemName}</td>

<td  align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemLengthPlant}</td>
<td  align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000;  font-size:10px;">${quot.itemWidthPlant}</td>
<td align="right"  style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemHeightPlant}</td>
<td   align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000;  font-size:10px;">${quot.itemTotalPlant}</td>

<c:if test="${quot.itemTotalSite>0}">
<td align="right"  width="2%" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemLengthSite}</td>
<td  width="2%" align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000;  font-size:10px;">${quot.itemWidthSite}</td>
<td align="right" width="2%"  style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemHeightSite}</td>
<td  width="2%"  align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000;  font-size:10px;">${quot.itemTotalSite}</td>
</c:if>

<c:if test="${quot.itemTotalSite==0}">
<td align="right" width="2%"  style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; color:white; font-size:10px;">-</td>
<td  width="2%" align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; color:white; font-size:10px;">-</td>
<td align="right" width="2%"  style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; color:white; font-size:10px;">-</td>
<td  width="2%" align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; color:white;  font-size:10px;">-</td>
</c:if>
</tr>

</c:forEach>
			
			
			</table>
			


<%-- <c:forEach items="${printData.chalanItemList}" var="chalan" varStatus="count3">
<p style="margin-left: 100px;">${count3.index+1})<b>Item Name:</b>&nbsp;&nbsp;&nbsp;<u>${chalan.itemName}</u></p>

<p style="margin-left: 100px;"><b>Plant Measurement:</b> Length:<u>${chalan.itemLengthPlant}</u>&nbsp;Width:<u>${chalan.itemWidthPlant}</u>&nbsp;&nbsp;Height:<u>${chalan.itemHeightPlant}</u>&nbsp;&nbsp;Total:<u>${chalan.itemTotalPlant}</u></p>

<c:if test="${chalan.itemTotalSite==0.0}">
<p style="margin-left: 100px;"><b>Site Measurement:</b> Length:<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>Width:<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>Height:<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>Total:<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u></p>
</c:if>
<c:if test="${chalan.itemTotalSite>0}">
<p style="margin-left: 100px;"><b>Site Measurement:</b> Length:<u>${chalan.itemLengthSite}</u>&nbsp;Width:<u>${chalan.itemWidthSite}</u>&nbsp;&nbsp;Height:<u>${chalan.itemHeightSite}</u>&nbsp;&nbsp;Total:<u>${chalan.itemTotalSite}</u></p>
</c:if>
<p style="margin-left: 100px;"><b>Site Measurement</b> Length:${chalan.itemLengthSite}&nbsp;Width:${chalan.itemWidthSite}&nbsp;&nbsp;Height:${chalan.itemHeightSite}&nbsp;&nbsp;Total:${chalan.itemTotalSite}</p>

</c:forEach> --%>
<p style="margin-left: 100px;" align="left" ><b>Out Time:</b>${printData.chalanItemList[0].vehTimeOut}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Out Km:</b>${printData.chalanItemList[0].outKm}</p>
<p style="margin-left: 100px;" align="left"><b>In Time:</b>${printData.chalanItemList[0].vehTimeIn}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>In Km:</b>${printData.chalanItemList[0].inKm}</p>
<p style="margin-left: 100px;"><b>Driver Sign</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Site Incharge</b></p>
<p style="margin-left: 100px;"><b></b>${printData.chalanItemList[0].usrName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b></b>${printData.chalanItemList[0].sitePersonName}</p>


<div style="page-break-after: always;" ></div>
 
</body>
</html>