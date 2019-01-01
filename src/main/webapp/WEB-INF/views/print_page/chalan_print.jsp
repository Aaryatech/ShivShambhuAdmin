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
<p style="margin-left: 100px; text-align: left" >Chalan No:${printData.chalanItemList[0].chalanNo}
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date: ${printData.chalanItemList[0].chalanDate}

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vehicle No: ${printData.chalanItemList[0].vehNo}</p>
<p style="text-align: center; font-size: 14px;font-weight: bold;"><u>Chalan</u></p>
<p style="margin-left: 100px;"><b>Customer Name</b>
<c:out value="${printData.chalanItemList[0].custName}"></c:out><br></br></p>


<%-- <table  width="80%" border="0"  cellpadding="0" cellspacing="0" style="border-right:1px solid #313131; margin-left:100px; ">
<tr>
<td style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ; border-bottom:1px solid #000;;
  text-align: center;">Sr No</td>
<td style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">Material</td>
<td style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px ;border-bottom:1px solid #000;
  text-align: center;">Unit</td>
<td style="border-left:1px solid #000;border-top:1px solid #000;
  padding: 2px 2px;
  text-align: center;border-bottom:1px solid #000;">Rate / Brass</td>
</tr>


<c:forEach items="${printData.quotDetPrint}" var="quot" varStatus="count3">

<tr>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.quotHeadId}--${quot.quotDetailId}</td>
<td style="border-bottom:1px solid #313131; border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.itemName}</td>
<td style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000; font-size:10px;">${quot.uomName}</td>
<td align="right" style="border-bottom:1px solid #313131;border-left:1px solid #313131; padding:5px;color:#000;  font-size:10px;">${quot.total}</td>

</tr>
</c:forEach>

</table> --%>

<c:forEach items="${printData.chalanItemList}" var="chalan" varStatus="count3">
<p style="margin-left: 100px;">${count3.index+1})<b>Item Name:</b>${chalan.itemName}</p>

<p style="margin-left: 100px;"><b>Measurement</b> Length:${chalan.itemLengthPlant}&nbsp;Width:${chalan.itemWidthPlant}&nbsp;&nbsp;Height:${chalan.itemHeightPlant}&nbsp;&nbsp;Total:${chalan.itemTotalPlant}</p>

</c:forEach>
<hr style="margin-left: 100px;"></hr>
<p style="margin-left: 100px;"><b>Out Time:</b>${printData.chalanItemList[0].vehTimeOut}&nbsp;Out Km:${printData.chalanItemList[0].outKm}</p>
<p style="margin-left: 100px;" ><b>In Time:</b>${printData.chalanItemList[0].vehTimeIn}&nbsp;In Km:${printData.chalanItemList[0].inKm}</p>

<br></br>
<%-- <p style="margin-left: 100px;"><b ><u>Notes:-Terms And Conditions</u></b></p>
<c:forEach items="${printData.docTermList}" var="docTerm" varStatus="count1">

<p style="margin-left: 100px;">${count1.index+1}) ${docTerm.termDesc}</p>
</c:forEach>
<br></br>
<p style="margin-left: 100px;">We also supply material to following parties.</p>
<c:forEach items="${supplyList}" var="supply" varStatus="count4">

<p style="margin-left: 100px;">${count4.index+1}) ${supply.termDesc}</p>
</c:forEach>
<br></br>
<p style="margin-left: 100px;"><u><b>Our Bank Details:</b></u></p>

<p style="margin-left: 100px;">${printData.comp.compName}</p>
<p style="margin-left: 100px;">A/c. No. ${printData.bank.accNo}</p>
<p style="margin-left: 100px;">${printData.bank.bankName}</p>
<p style="margin-left: 100px;">IFSC Code: ${printData.bank.bankIfsc}</p> --%>

<div style="page-break-after: always;" ></div>
 
</body>
</html>