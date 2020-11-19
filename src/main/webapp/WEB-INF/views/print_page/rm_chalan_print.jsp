<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- <jsp:useBean id="currency" scope="session"
             class="com.ats.ssgs.common.Currency"/>
 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Challan Print</title>
</head>
<body>
	<div
		style="color: black; font-size: 20px; text-align: center; margin: 0px; font-weight: bold;">M/S.${printData.comp.compName}</div>
	<table style="margin-left: 10px;" width="100%">
		<tr>
			<td align="left"><img
				src="/ssgs/resources/images/shambhu_logo.PNG"
				style="height: 45px; width: 45px;" alt="User Avatar" /></td>
			<td colspan="1" rowspan="1" width="90%"
				style="border-bottom: 1px solid #313131; border-top: 1px solid #313131 padding:10px; color: #FFF; font-size: 13px;">
				<%-- <p
					style="color: black; font-size: 20px; text-align: center; margin: 0px; font-weight: bold;">M/S.${printData.comp.compName}</p> --%>
				<p
					style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
					<b>Office Address:</b>${printData.comp.compOfficeAdd}<br></br>
					Mobile No. ${printData.comp.contactNo1}
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email:
					${printData.comp.email1}
				</p>

				<p
					style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
					<b>Plant Address:</b>${printData.chalanItemList[0].plantAddress1}<br></br>
					Mobile No. ${printData.chalanItemList[0].plantContactNo1}
				</p>

			</td>
		</tr>
	</table>
	<hr style="margin-left: 10px;"></hr>

	<!-- <p style="text-align: center; font-size: 14px;font-weight: bold;"><u>Chalan</u></p>
 -->
	<center>
		<b>Delivery Challan (for sale) </b>
	</center>

	<div
		style="margin-left: 10px; padding: 4px; font-size: 13px; text-align: left">
		<b>Challan No : </b>${ch_no1}
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Date
			: </b> ${printData.chalanItemList[0].chalanDate}
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
	</div>
	<div style="margin-left: 10px; padding: 4px; font-size: 13px;">
		<b>Customer Name : </b>
		<c:out value="${printData.chalanItemList[0].custName}"></c:out>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


	</div>
	<div
		style="margin-left: 10px; padding: 4px; font-size: 13px; text-align: left">
		<b>Project Name :</b> ${printData.chalanItemList[0].projName}
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

	</div>
	<div
		style="margin-left: 10px; text-align: left; padding: 4px; font-size: 13px;">		
		<b>Delivery Address : </b>${printData.chalanItemList[0].devAddress}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>

	<div
		style="margin-left: 10px; text-align: left; padding: 4px; font-size: 13px;">
		
		<b>Batch No </b>: ${printData.chalanItemList[0].batchNo}
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<b>RST No : </b>${printData.chalanItemList[0].exVar1}
	</div>

	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		style="border-top: 1px solid #313131; border-right: 1px solid #313131; margin-left: 10px;">
		<tr>
			<td width="5%"
				style="border-bottom: 1px solid #313131; border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 12px;">Sr.
				No.</td>
			<td align="left" width="35%"
				style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 13px; color: #000; font-size: 12px; text-align: left">Item
				Name</td>
			<td align="left" width="15%"
				style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 13px; color: #000; font-size: 12px; text-align: left">
				UOM</td>
			<td align="left" width="15%"
				style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 13px; color: #000; font-size: 12px; text-align: left">Order
				Quantity</td>

			<td align="left" width="15%"
				style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 13px; color: #000; font-size: 12px; text-align: left">
				Chalan Quantity</td>
			<td align="left" width="15%"
				style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 13px; color: #000; font-size: 12px; text-align: left">Cumulative
				Chalan Quantity</td>

		</tr>



		<c:forEach items="${printData.chalanItemList}" var="quot"
			varStatus="count3">

			<tr>
				<td
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${count3.index+1}</td>
				<td
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.shortName}</td>
				<td
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.uom}</td>
				<td
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.totalQuan}</td>
				<td
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.itemQty}</td>

				<c:forEach items="${rstList}" var="temp" varStatus="count3">
					<c:if test="${quot.itemId==temp.itemId}">

						<td
							style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${temp.result}</td>
					</c:if>
				</c:forEach>

			</tr>

		</c:forEach>
	</table>



	<div style="margin-left: 10px; padding: 3px; font-size: 13px;"
		align="left">
		
		<b>Out Time : </b>${printData.chalanItemList[0].vehTimeOut}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>In
			Time : </b>${temp}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		<b>Out Km : </b>
		${printData.chalanItemList[0].outKm}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>In
			Km : </b>${temp1}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	
	<div style="margin-left: 10px; padding: 3px; font-size: 13px;"
		align="left">
		<b>Batching
			Time : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<b>Pumping/Dumping</b>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
	</div>
	<div style="margin-left: 10px; padding: 3px; font-size: 13px;"
		align="left">
		<b>Site
			In Time : </b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<b>Site Out Time : </b>
	</div>

	<div style="margin-left: 10px; padding: 4px; font-size: 13px;">
	<b>Vehicle No : </b>
		${printData.chalanItemList[0].vehNo}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<b>Driver : ${printData.chalanItemList[0].usrName}</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<b>Site Incharge : </b>
	</div>



	<div style="margin-left: 10px; padding: 4px; font-size: 13px;">
		<b>Sign</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp; <b></b>${printData.chalanItemList[0].sitePersonName}</div>
		
		
		<!-- 19-11-2020 Mahendra -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
		style="border-top: 1px solid #313131; border-right: 1px solid #313131; margin-left: 10px;">

		<tr>
			<td align="left" width="35%" rowspan="2"
				style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 5px; text-align: left">
				<span style="font-size: 12px;"> <b>CAUTION:</b>Wet Cement and
					Concrete Are Caustic Material Which Can Cause Serious Skin
					Injuiry.To Avoid Risk Of Such Injury Protective Clothing
					(Gloves,Booted) Should Be Worn During All Handling Operations Where
					Direct Skin Contact Occurs,Area Should Be Washed Throughly With
					Clean Water.We Are Not Responsible For Slump Strength OrQuality Of
					Concrete When Water Has Been Added On Site At The Request Of
					Customer<br />
					<hr></hr> <b>WARRANTY DISCLAIMER</b>:<b> SSIP Ready mix concrete
				</b>warrants that the product identified are in accordance with the
					appropriate BIS specification.No one is authorized to make any
					modification or additions to this warranty <b>SSIP Ready mix
						concrete </b> makes no warranty either expressed or implied with
					respect to this products and disclaims any implied of merchantable
					or fitness for particular purpose.<b> SSIP Ready mix concrete </b>
					has no control over other ingredient mixed with this product or in
					final application.<b>SSIP Ready mix concrete </b> warrants <b>SSIP
						Ready mix concrete </b> does not and can not warrant the finished
					work.In all event <b>SSIP Ready mix concrete </b> shall not be
					liable to direct,indirect,special,incidental of consequential
					damages arising out of the use of this product.Even if advised of
					possibilities of such damages in no case shall<b>SSIP Ready mix
						concrete </b> liability exceed the purchase price of the product <b>Contact
						No:</b> 9922419336/9823354777.
					<hr /> <b>Note:1) If necessary,additional does of admixture may
						be added at project site to regain the workability of concrete .
						2) Concrete should be consumed within 3 hours of batching time.</b>
			</span>
			</td>

		</tr>

	</table>
		<!--  -->

	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		style="border-top: 1px solid #313131; border-right: 1px solid #313131; margin-left: 10px;">
		<tr>
			<td align="left" width="35%" rowspan="1"
				style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 13px; text-align: left">
				<b>M/S.${printData.comp.compName}</b> <br /> <br /> <br /> <br />
				<br /> <b>Authorized Signature</b>
			</td>
			<td align="left" width="35%" rowspan="2"
				style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 12px; text-align: left">
				<p style="font-size: 12px; text-align: left">I/we Certify that
					to the best of my/our knowledge the particulars are true and
					correct and complete</p> <br />


				<center>
					<p style="font-size: 12px; text-align: left">
						Authorize Signature & Name with Seal of Receiver <br />
						<center>(of/on behalf of Customer)</center>
					</p>
				</center>

			</td>
		</tr>

	</table>

	<div style="page-break-after: always;"></div>

</body>
</html>