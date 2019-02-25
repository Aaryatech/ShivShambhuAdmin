<%@page contentType="text/html; charset=ISO8859_1"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="currency" scope="session"
	class="com.ats.ssgs.common.Currency" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<style>
.cut-text {
	text-overflow: ellipsis;
	overflow: hidden;
	width: 150px;
	height: 1.7em;
}
</style>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Bill PDF</title>
</head>
<body onload="inwords()">
	<c:forEach items="${billHeaderList}" var="billHeaderRes"
		varStatus="cnt">

		<c:set var="srCnt" value="0" />
		<c:set var="totalRowCount" value="0" />
		<c:set var="maxRowCount" value="34" />
		<div style="font-size: 10px; text-align: right;">${taxName}</div>
		<div style="color: black; font-size: 15px; text-align: center;">
			TAX INVOICE</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-left: 1px solid #313131; border-right: 1px solid #313131; border-top: 1px solid #313131;">
			<tr>
				<!--  row str -->
				<td colspan="6" rowspan="3" width="50%"
					style="border-bottom: 1px solid #313131; padding: 10px; color: #FFF; font-size: 15px;">
					<p
						style="color: black; font-size: 20px; text-align: left; margin: 0px; font-weight: bold;">
						M/S <b>${billHeaderRes.compName}</b>
					</p>
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						<b>${billHeaderRes.compOfficeAdd}</b> <br></br> <b>GSTIN/UIN:${billHeaderRes.compGstNo}</b>
						<br></br> <b>State : Maharashtra ,Code: 27 </b> <br></br> <b>Contact:${billHeaderRes.contactNo1}</b>
						<br></br> <b>Email:${billHeaderRes.email1}</b>
					</p>

				</td>

				<td width="25%" colspan="3"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 15px; text-align: center">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Invoice No.
						<!-- </p> 
      	<p style="color:#000; font-size:11px; text-align:left;margin:0px;"> -->
						<b>${billHeaderRes.billNo}</b>
					</p>

				</td>
				<td colspan="3" width="25%" 
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #FFF; font-size: 15px;">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Dated.
						<!-- </p> 
      	<p style="color:#000; font-size:11px; text-align:left;margin:0px;"> -->
						<b>${billHeaderRes.billDate}</b>
					</p>
				</td>

			</tr>

			<tr >
				<td width="25%" colspan="3"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 15px; text-align: center">

					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Buyer's Order No.: <b>${ref}</b>
					</p>
				</td>
				<td width="25%" colspan="3"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 15px; text-align: center">


					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Dated.
						<!-- </p> 
      	<p style="color:#000; font-size:11px; text-align:left;margin:0px;"> -->
						<b>${billHeaderRes.billDate}</b>
					</p>
				</td>


			</tr>
			<tr>


			</tr>
			<tr>


			</tr>
			<tr>
				<!--  row strats -->
				<td width="50%" rowspan="4" colspan="6"
					style="padding: 8px; color: #FFF; font-size: 14px;">
					<!--  1st td -->
					<p
						style="color: #000; font-size: 15px; text-align:; left; margin: 0px;">
						<b>Buyer: </b>
					</p>
					<p
						style="color: #000; font-size: 13px; text-align: left; margin: 0px;">
						<b>${billHeaderRes.custName}</b>
					</p>
					<p
						style="color: #000; font-size: 12px; text-align: left; margin: 0px;">
						GSTIN/UIN: ${billHeaderRes.custGstNo}</p>
					<p
						style="color: #000; font-size: 12px; text-align: left; margin: 0px;">State
						: Maharashtra ,Code: 27</p>
					<p
						style="color: #000; font-size: 12px; text-align: left; margin: 0px;">Contact:
						${billHeaderRes.custMobNo}</p>

				</td>
				<!--  1st td ends -->


				<%-- <td width="25%" colspan="3"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 15px; text-align: center">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Buyer's Order No.:
						
						<b>${ref}</b>
					</p>

				</td>
				<td colspan="3" width="25%"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #FFF; font-size: 15px;">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Dated.
						<!-- </p> 
      	<p style="color:#000; font-size:11px; text-align:left;margin:0px;"> -->
						<b>${billHeaderRes.billDate}</b>
					</p>
				</td>
 --%>
			</tr>
			<!-- row ends -->
			<tr>
				<td width="25%" colspan="6"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 15px; text-align: center">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">Despatch
						Doc. No. ${billHeaderRes.chalanNo}</p>

				</td>

			</tr>
			<tr>

				<td colspan="6" width="25%"
					style="border-left: 1px solid #313131; border-bottom: 0px solid #313131; padding: 10px; color: #FFF; font-size: 15px;">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">Project
						Name - ${billHeaderRes.projName} ${billHeaderRes.location}</p>

				</td>

			</tr>
			<!-- <tr>

			</tr> -->

		</table>
		<c:set var="tAmt" value="0" />
		<c:set var="totalQty" value="0" />
		<c:set var="taxableAmt" value="0" />
		<c:set var="totalAmt" value="0" />
		<c:set var="totalCgst" value="0" />
		<c:set var="totalSgst" value="0" />
		<c:set var="totalDisc" value="0" />


		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-right: 1px solid #313131">
			<tr>
				<td rowspan="2" width="2%"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">No.</td>
				<td align="left" width="30%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 10px; text-align: left">Decription
					Of Goods.</td>
				<td align="center" width="8%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 0.2px; color: #000; font-size: 10px;">HSN/SAC</td>


				<td align="center" width="6%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Qty</td>
				<td align="center" width="6%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Rate</td>


				<td align="center" width="6%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">UOM
				</td>

				<td align="center" width="8%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Taxable
					Amt</td>

				<td align="center" width="10%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">
					CGST</td>
				<td align="center" width="10%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">SGST</td>
				<td align="center" width="10%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">Disc.</td>

				<td align="center" width="10%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Amount</td>



			</tr>
			<tr>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">CGST
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">SGST
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Disc.
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>


			</tr>

			<c:forEach items="${billHeaderRes.getBillDetails}" var="billDetails"
				varStatus="count">

				<c:set var="totalRowCount" value="${totalRowCount+1}" />
				<c:choose>

					<c:when test="${totalRowCount eq maxRowCount}">
		</table>
		<div style="page-break-after: always;"></div>
		<h6 align="center">TAX INVOICE</h6>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-left: 1px solid #313131; border-right: 1px solid #313131; border-top: 1px solid #313131;">
			<tr>
				<td colspan="6" rowspan="3" width="50%"
					style="border-bottom: 1px solid #313131; padding: 10px; color: #FFF; font-size: 15px;">
					<p
						style="color: black; font-size: 20px; text-align: left; margin: 0px; font-weight: bold;">
						M/S <b>${billHeaderRes.compName}</b>
					</p>
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						<b>${billHeaderRes.compOfficeAdd}</b> <br></br> <b>GSTIN/UIN:${billHeaderRes.compGstNo}</b>
						<br></br> <b>State : Maharashtra ,Code: 27 </b> <br></br> <b>Contact:${billHeaderRes.contactNo1}</b>
						<br></br> <b>Email:${billHeaderRes.email1}</b>
					</p>

				</td>

				<td width="25%" colspan="3"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 15px; text-align: center">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Invoice No.
						<!-- </p> 
      	<p style="color:#000; font-size:11px; text-align:left;margin:0px;"> -->
						<b>${billHeaderRes.billNo}</b>
					</p>

				</td>
				<td colspan="3" width="25%"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #FFF; font-size: 15px;">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Dated.
						<!-- </p> 
      	<p style="color:#000; font-size:11px; text-align:left;margin:0px;"> -->
						<b>${billHeaderRes.billDate}</b>
					</p>
				</td>

			</tr>
			<tr>


			</tr>
			<tr>


			</tr>
			<tr>
				<td width="50%" rowspan="4" colspan="6"
					style="padding: 8px; color: #FFF; font-size: 14px;">
					<p
						style="color: #000; font-size: 15px; text-align:; left; margin: 0px;">
						<b>Buyer: </b>
					</p>
					<p
						style="color: #000; font-size: 13px; text-align: left; margin: 0px;">
						<b>${billHeaderRes.custName}</b>
					</p>
					<p
						style="color: #000; font-size: 12px; text-align: left; margin: 0px;">
						GSTIN/UIN: ${billHeaderRes.custGstNo}</p>
					<p
						style="color: #000; font-size: 12px; text-align: left; margin: 0px;">State
						: Maharashtra ,Code: 27</p>
					<p
						style="color: #000; font-size: 12px; text-align: left; margin: 0px;">Contact:
						${billHeaderRes.custMobNo}</p>

				</td>


				<td width="25%" colspan="3"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 15px; text-align: center">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Buyer's Order No.:
						<!-- </p> 
      	<p style="color:#000; font-size:11px; text-align:left;margin:0px;"> -->
						<b>${ref}</b>
					</p>

				</td>
				<td colspan="3" width="25%"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #FFF; font-size: 15px;">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						Dated.
						<!-- </p> 
      	<p style="color:#000; font-size:11px; text-align:left;margin:0px;"> -->
						<b>${billHeaderRes.billDate}</b>
					</p>
				</td>

			</tr>
			<tr>
				<td width="25%" colspan="6"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 15px; text-align: center">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">Despatch
						Doc. No.</p>

				</td>

			</tr>
			<tr>

				<td colspan="6" width="25%"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 10px; color: #FFF; font-size: 15px;">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">Project
						Name</p>

				</td>

			</tr>
			<tr>



			</tr>

		</table>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-right: 1px solid #313131">
			<tr>
				<td rowspan="2" width="2%"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">No.</td>
				<td align="left" width="30%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 10px; text-align: left">Decription
					Of Goods.</td>
				<td align="center" width="8%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 0.2px; color: #000; font-size: 10px;">HSN/SAC</td>

				<td align="center" width="6%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">UOM
				</td>

				<td align="center" width="6%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Qty</td>
				<td align="center" width="6%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Rate</td>
				<td align="center" width="8%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Taxable
					Amt</td>

				<td align="center" width="10%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">
					CGST</td>
				<td align="center" width="10%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">SGST</td>
				<td align="center" width="10%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">Disc.</td>

				<td align="center" width="10%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Amount</td>



			</tr>
			<tr>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">CGST
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">SGST
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Disc.
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>


			</tr>
			<c:set var="totalRowCount" value="0" />

			</c:when>
			</c:choose>
			<c:set var="srCnt" value="${srCnt+1}" />


			<tr>
				<td
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${count.index+1}</td>
				<td class="cut-text"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${billDetails.itemName}</td>
				<td align="left"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${billDetails.hsnCode}</td>



				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.qty}" /></td>
				<c:set var="totalQty" value="${totalQty+billDetails.qty}" />
				<td align="center"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${(billDetails.rate*100)/(100+billDetails.cgstPer+billDetails.sgstPer)}" /></td>
				<td align="left"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${billDetails.uomName}</td>

				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.taxableAmt}" /></td>
				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.cgstPer}" /></td>
				<c:set var="taxableAmt" value="${taxableAmt+billDetails.taxableAmt}" />

				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.cgstAmt}" /></td>
				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.sgstPer}" /></td>
				<c:set var="totalCgst" value="${totalCgst+billDetails.cgstAmt}" />
				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.sgstAmt}" /></td>
				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.discPer}" /></td>
				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.discAmt}" /></td>
				<td align="right"
					style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
						type="number" maxFractionDigits="2" minFractionDigits="2"
						value="${billDetails.totalAmt}" var="t" /> ${t}</td>
				<c:set var="totalSgst" value="${totalSgst+billDetails.sgstAmt}" />
				<c:set var="totalDisc" value="${totalDisc+billDetails.discAmt}" />
				<c:set var="tAmt" value="${tAmt+billDetails.totalAmt}" />


			</tr>
			</c:forEach>
			<tr>
				<td align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b>Total</b></td>

				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${totalQty}" /></b></td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${taxableAmt}" /></b></td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>

				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${totalCgst}" /></b></td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>

				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${totalSgst}" /></b></td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>

				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${totalDisc}" /></b></td>

				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${tAmt}" /></b></td>

			</tr>


			<!-- Round_Off -->

			<c:set var="roundVal" value="${Math.round(tAmt)}" />

			<c:set var="roundOffVal" value="${roundVal-tAmt}" />

			<tr>
				<td align="left"
					style="border-top: 0px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td colspan="12" align="right"
					style="border-top: 0px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b>Round
						off</b></td>

				<td align="right"
					style="border-top: 0px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${roundOffVal}" /></b></td>

			</tr>

			<!-- TOTAL -->
			<tr>
				<td align="left"
					style="border-top: 0px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td colspan="12" align="right"
					style="border-top: 0px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b>Total</b></td>

				<td align="right"
					style="border-top: 0px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${roundVal}" /></b></td>

			</tr>


		</table>



		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-right: 1px solid #313131">
			<tr>

				<td colspan="8" width="60%"
					style="border-left: 1px solid #313131; border-top: 0px solid #313131; padding: 8px; color: #000; font-size: 10px;">&nbsp;
					Amount Chargeable (in words)</td>
				<!-- <td colspan="4" width="40%"
					style="border-top: 1px solid #313131; padding: 8px; color: #000; font-size: 10px;">&nbsp;
					E.& O.E</td> -->



			</tr>
			<tr>

				<td colspan="12" width="100%"
					style="border-left: 1px solid #313131; font-weight: bold; padding: 8px; color: #000; font-size: 13px;">&nbsp;
					Indian Rupees ${billHeaderRes.printWord}</td>

			</tr>
			<tr>
				<td rowspan="2" width="2%"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">No.</td>
				<td align="left" width="10%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 10px; text-align: left">
					HSN/SAC</td>

				<td align="left" width="13%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 10px; text-align: left">
					Taxable Amt</td>



				<td align="center" width="20%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">
					Central Tax</td>
				<td align="center" width="20%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">State
					Tax</td>
				<td align="center" width="20%" colspan="2"
					style="border-left: 1px solid #313131; border-top: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">IGST
				</td>


				<td align="center" width="15%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Amount</td>



			</tr>
			<tr>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Rate
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Rate
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Rate
					%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>


			</tr>
			<c:set var="taxableHsnAmt" value="0" />

			<c:set var="totalHsnCgst" value="0" />

			<c:set var="totalHsnSgst" value="0" />
			<c:set var="totalHsnIgst" value="0" />



			<c:set var="tHsnAmt" value="0" />
			<c:forEach items="${billHeaderRes.getBillDetByHsn}" var="hsnpdf"
				varStatus="count">

				<tr>
					<td
						style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${count.index+1}</td>
					<td class="cut-text"
						style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${hsnpdf.hsnCode}</td>



					<td align="right"
						style="border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${hsnpdf.taxableAmt}" /></td>


					<td align="right"
						style="border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${hsnpdf.cgstPer}" /></td>


					<td align="right"
						style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${hsnpdf.cgstAmt}" /></td>



					<td align="right"
						style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${hsnpdf.sgstPer}" /></td>

					<td align="right"
						style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${hsnpdf.sgstAmt}" /></td>

					<td align="right"
						style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${hsnpdf.igstPer}" /></td>

					<td align="right"
						style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${hsnpdf.igstAmt}" /></td>





					<td align="right"
						style="border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${hsnpdf.totalAmt}" var="t" /> ${t}</td>



					<c:set var="taxableHsnAmt"
						value="${taxableHsnAmt+hsnpdf.taxableAmt}" />

					<c:set var="totalHsnCgst" value="${totalHsnCgst+hsnpdf.cgstAmt}" />

					<c:set var="totalHsnSgst" value="${totalHsnSgst+hsnpdf.sgstAmt}" />

					<c:set var="totalHsnIgst" value="${totalHsnIgst+hsnpdf.igstAmt}" />



					<c:set var="tHsnAmt" value="${tHsnAmt+hsnpdf.totalAmt}" />
				</tr>
			</c:forEach>

			<tr>
				<td align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b>Total</b></td>

				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${taxableHsnAmt}" /></b></td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>

				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${totalHsnCgst}" /></b></td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>

				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${totalHsnSgst}" /></b></td>

				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>

				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${totalHsnIgst}" /></b></td>



				<td align="right"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" maxFractionDigits="2" minFractionDigits="2"
							value="${tHsnAmt}" /></b></td>



			</tr>

		</table>



































		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-right: 1px solid #313131; border-left: 1px solid #313131;">



			<tr>
				<td colspan="8" width="60%"
					style="padding: 8px; color: #000; font-size: 13px;">Company's
					PAN: ${billHeaderRes.compPanNo}<br />Remark :
					${billHeaderRes.exVar2}
				</td>
				<td colspan="4" width="60%"
					style="border-left: 1px solid #313131; padding: 8px; color: #000; font-size: 15px;">
					<p
						style="color: #000; font-size: 13px; text-align: left; margin: 0px;">
						Company's Bank Details:<br /> Bank Name:
						${billHeaderRes.bankDetail.bankName}<br /> A/C No:
						${billHeaderRes.bankDetail.accNo}<br /> Branch & IFSC Code:
						${billHeaderRes.bankDetail.bankAddress} ----
						${billHeaderRes.bankDetail.bankIfsc}
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="12" width="100%"
					style="border-top: 1px solid #313131; padding: 8px; color: #000; font-size: 13px;">&nbsp;
					Declaration: We declare that this invoice shows actual price of the
					goods described and that all particulars are true and correct .</td>
			</tr>
			<tr>
				<td colspan="8" width="60%"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; padding: 0px; color: #000; font-size: 11px;">
					<p
						style="color: #000; font-size: 12px; text-align: center; margin: 0px;">
						Customer's Seal & Signature<br />
					</p>

				</td>
				<td align="center" colspan="4" width="40%"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 12px;">
					<b> for M/S ${billHeaderRes.compName}</b><br /> <br /> <br />
					Authorised Signature
				</td>
			</tr>

		</table>
		<div style="text-align: center; font-size: 12px;">This is
			computer generated invoice.</div>

		<div style="page-break-after: always;"></div>
	</c:forEach>

</body>
</html>