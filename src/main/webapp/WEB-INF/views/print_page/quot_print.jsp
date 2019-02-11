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
<title>Quotation Print</title>
</head>
<body onload="chkPlant()">
	<c:forEach items="${quotPrintData}" var="printData" varStatus="count">

		<table style="margin-left: 2px;" width="100%">
			<tr>
				<td align="left"><img
					src="/ssgs/resources/images/shambhu_logo.PNG"
					style="height: 80px; width: 80px;" alt="User Avatar" /></td>
				<td colspan="6" rowspan="3" width="50%"
					style="border-bottom: 1px solid #313131; border-top: 1px solid #313131 padding:10px; color: #FFF; font-size: 15px;">
					<p
						style="color: black; font-size: 20px; text-align:right; margin: 0px; font-weight: bold;">${printData.comp.compName}</p>
					<p
						style="color: #000; font-size: 11px; text-align: right; margin: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${printData.comp.compOfficeAdd}<br></br>
						Mobile No. ${printData.comp.contactNo1} <br></br>Email:
						${printData.comp.email1}
					</p>

				</td>
			</tr>
		</table>
		<p style="text-align: center; font-size: 14px; font-weight: bold;">
			<u>QUOTATION</u>
		</p>
		<hr></hr>
		<p style="margin-left: 100px;">Quotation No:<b>${quotNo1}</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	Date:<b>${printData.quotDetPrint[0].quotDate}</b></p>
		
		<p style="margin-left: 100px;">
			<b>To</b><br></br>
			<c:out value="${printData.cust.custName}"></c:out>
			<br></br> <u><br></br>Material Rate List With Carting Only For<b>
					<c:out value="${printData.proj.projName}"></c:out> &nbsp;&nbsp; Site
			</b></u>
		</p>
		
	  <div>	
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dear Sir,</div><div style="margin-left: 110px;">This has a reference to your site telephonic discussion with you regarding the supply of  ${plantName} to your site In this connection we are submitting herewith our competitive offer for the supply of ${plantName} on the following terms and conditions.</div>
		
<br/>
		<table width="80%" border="0" cellpadding="0" cellspacing="0"
			style="border-right: 1px solid #313131; margin-left: 100px;">
			<tr>
				<td
					style="border-left: 1px solid #000; border-top: 1px solid #000; padding: 2px; border-bottom: 1px solid #000;; text-align: center;">Sr
					No</td>
				<td
					style="border-left: 1px solid #000; border-top: 1px solid #000; padding: 2px; border-bottom: 1px solid #000; text-align: center;">Material</td>
				<td
					style="border-left: 1px solid #000; border-top: 1px solid #000; padding: 2px; border-bottom: 1px solid #000; text-align: center;">Unit</td>

				<td
					style="border-left: 1px solid #000; border-top: 1px solid #000; padding: 2px; border-bottom: 1px solid #000;; text-align: center;">Tax
					%</td>
				<td
					style="border-left: 1px solid #000; border-top: 1px solid #000; padding: 2px; border-bottom: 1px solid #000; text-align: center;">Taxable</td>
				<td
					style="border-left: 1px solid #000; border-top: 1px solid #000; padding: 2px; border-bottom: 1px solid #000; text-align: center;">Tax</td>


				<td
					style="border-left: 1px solid #000; border-top: 1px solid #000; padding: 2px 2px; text-align: center; border-bottom: 1px solid #000;">Rate
					/ Brass</td>
			</tr>

			<c:forEach items="${printData.quotDetPrint}" var="quot"
				varStatus="count3">

				<tr>

					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${count3.index+1}</td>
					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.itemName}</td>
					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.uomName}</td>


					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.taxPer}</td>
					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.taxableValue}</td>
					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.taxValue}</td>


					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">${quot.total}</td>

				</tr>

			</c:forEach>

		</table>
		
		<br></br>
		 
		 <c:if test="${plantId==70}"> 

						<div style="margin-left: 100px;font-size: 13px;padding:0px">
						<b>Note – 1)Above rates are valid till One month.<br/>
		2) Cement grade:Ultratech OPC 53Grade.<br/>
3)Our Bank Details – SHIV SHAMBHU BUILDCON
                                    IDBI BANK, AMBAD BRANCH, NASHIK
A/c No -<br/> &nbsp;&nbsp;&nbsp;1991102000005203.IFSC CODE – IBKL0001991<br/>
4)Toll charges will be paid by you at an actual.
5) Pumping charges will be extra Rs.175/-<br/>
                        6)  OUR GST NO.27ADHFS8127N1ZE</b>
						
						
						</div>
				</c:if> 
		<br/>
		<div style="margin-left: 100px;">
			<b><u>Terms And Conditions</u></b>
		</div>
		
		<c:forEach items="${printData.docTermList}" var="docTerm"
			varStatus="count1">

			<div style="margin-left: 100px;font-size: 13px;padding:0px">${count1.index+1})
				${docTerm.termDesc}</div>
		</c:forEach>
	<br/>
		<p style="margin-left: 90px;">We thank you for considering us for the supply of ${plantName} and we assure you of our Best Quality and services at all times. <br/>
	<b>Note:</b> I under sign read the above terms and conditions carefully and agree for the same.</p>
		<c:if test="${plantId!=70}"> 
		<div style="margin-left: 100px;">
			<u><b>Our Bank Details:</b></u>
		</div>
<div style="margin-left: 100px;font-size: 13px;padding:0px">
	${printData.comp.compName}<br/>
	
		A/c. No. ${printData.bank.accNo}<br/>
		${printData.bank.bankName}<br/>
		IFSC Code:
			${printData.bank.bankIfsc}</div>
</c:if>


	<p style="margin-left: 90px;">Thanking you,
	 </p>
	 
	 <p style="margin-left: 90px;">Yours Faithfully,

	 </p>
	 <p style="margin-left: 90px;">For ${printData.comp.compName} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Accepted By, 
	 </p>
	 <br/>
	 <p style="margin-left: 90px;"> Authorized Signatory&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      For. ${printData.cust.custName}
	 

	 </p>
	 
		<div style="page-break-after: always;"></div>



	</c:forEach>
  
	
</body>
</html>
