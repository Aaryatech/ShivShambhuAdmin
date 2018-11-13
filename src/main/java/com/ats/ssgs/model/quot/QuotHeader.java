package com.ats.ssgs.model.quot;

import java.util.List;

public class QuotHeader {

	private int quotHeadId;

	private String quotNo;

	private int custId;

	private int userId;

	private String quotDate;

	private int enqHeadId;

	private int companyId;

	private int plantIds;

	private int status;

	private int delStatus;

	private float quotTaxableAmt;

	private float quotTaxAmt;

	private float quotTotal;

	private int payTermId;

	private int transportTermId;

	private String payTerms;

	private String transportTerms;

	private String otherRemark1;

	private String otherRemark2;

	private String otherRemark3;

	private String otherRemark4;

	private float otherValueBeforeTax1;

	private float otherValueBeforeTax2;

	private float otherValueAfterTax1;

	private float otherValueAfterTax2;

	private int projId;

	private int noOfTolls;

	private float tollCost;

	private float otherCost;

	private float transportCost;

	private int exInt1;

	private int exInt2;

	private int exInt3;

	private String exVar1;

	private String exVar2;

	private String exVar3;

	private String exDate1;

	private String exDate2;

	private int exBool1;

	private int exBool2;

	private int exBool3;

	private float quotValue;

	private float taxValue;

	private float taxableValue;

	private int quotTermId;

	List<QuotDetail> quotDetailList;

	public int getQuotHeadId() {
		return quotHeadId;
	}

	public void setQuotHeadId(int quotHeadId) {
		this.quotHeadId = quotHeadId;
	}

	public String getQuotNo() {
		return quotNo;
	}

	public void setQuotNo(String quotNo) {
		this.quotNo = quotNo;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getQuotDate() {
		return quotDate;
	}

	public void setQuotDate(String quotDate) {
		this.quotDate = quotDate;
	}

	public int getEnqHeadId() {
		return enqHeadId;
	}

	public void setEnqHeadId(int enqHeadId) {
		this.enqHeadId = enqHeadId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getPlantIds() {
		return plantIds;
	}

	public void setPlantIds(int plantIds) {
		this.plantIds = plantIds;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public float getQuotTaxableAmt() {
		return quotTaxableAmt;
	}

	public void setQuotTaxableAmt(float quotTaxableAmt) {
		this.quotTaxableAmt = quotTaxableAmt;
	}

	public float getQuotTaxAmt() {
		return quotTaxAmt;
	}

	public void setQuotTaxAmt(float quotTaxAmt) {
		this.quotTaxAmt = quotTaxAmt;
	}

	public float getQuotTotal() {
		return quotTotal;
	}

	public void setQuotTotal(float quotTotal) {
		this.quotTotal = quotTotal;
	}

	public int getPayTermId() {
		return payTermId;
	}

	public void setPayTermId(int payTermId) {
		this.payTermId = payTermId;
	}

	public int getTransportTermId() {
		return transportTermId;
	}

	public void setTransportTermId(int transportTermId) {
		this.transportTermId = transportTermId;
	}

	public String getPayTerms() {
		return payTerms;
	}

	public void setPayTerms(String payTerms) {
		this.payTerms = payTerms;
	}

	public String getTransportTerms() {
		return transportTerms;
	}

	public void setTransportTerms(String transportTerms) {
		this.transportTerms = transportTerms;
	}

	public String getOtherRemark1() {
		return otherRemark1;
	}

	public void setOtherRemark1(String otherRemark1) {
		this.otherRemark1 = otherRemark1;
	}

	public String getOtherRemark2() {
		return otherRemark2;
	}

	public void setOtherRemark2(String otherRemark2) {
		this.otherRemark2 = otherRemark2;
	}

	public String getOtherRemark3() {
		return otherRemark3;
	}

	public void setOtherRemark3(String otherRemark3) {
		this.otherRemark3 = otherRemark3;
	}

	public String getOtherRemark4() {
		return otherRemark4;
	}

	public void setOtherRemark4(String otherRemark4) {
		this.otherRemark4 = otherRemark4;
	}

	public float getOtherValueBeforeTax1() {
		return otherValueBeforeTax1;
	}

	public void setOtherValueBeforeTax1(float otherValueBeforeTax1) {
		this.otherValueBeforeTax1 = otherValueBeforeTax1;
	}

	public float getOtherValueBeforeTax2() {
		return otherValueBeforeTax2;
	}

	public void setOtherValueBeforeTax2(float otherValueBeforeTax2) {
		this.otherValueBeforeTax2 = otherValueBeforeTax2;
	}

	public float getOtherValueAfterTax1() {
		return otherValueAfterTax1;
	}

	public void setOtherValueAfterTax1(float otherValueAfterTax1) {
		this.otherValueAfterTax1 = otherValueAfterTax1;
	}

	public float getOtherValueAfterTax2() {
		return otherValueAfterTax2;
	}

	public void setOtherValueAfterTax2(float otherValueAfterTax2) {
		this.otherValueAfterTax2 = otherValueAfterTax2;
	}

	public int getProjId() {
		return projId;
	}

	public void setProjId(int projId) {
		this.projId = projId;
	}

	public int getNoOfTolls() {
		return noOfTolls;
	}

	public void setNoOfTolls(int noOfTolls) {
		this.noOfTolls = noOfTolls;
	}

	public float getTollCost() {
		return tollCost;
	}

	public void setTollCost(float tollCost) {
		this.tollCost = tollCost;
	}

	public float getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(float otherCost) {
		this.otherCost = otherCost;
	}

	public float getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(float transportCost) {
		this.transportCost = transportCost;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public int getExInt3() {
		return exInt3;
	}

	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	public String getExDate2() {
		return exDate2;
	}

	public void setExDate2(String exDate2) {
		this.exDate2 = exDate2;
	}

	public int getExBool1() {
		return exBool1;
	}

	public void setExBool1(int exBool1) {
		this.exBool1 = exBool1;
	}

	public int getExBool2() {
		return exBool2;
	}

	public void setExBool2(int exBool2) {
		this.exBool2 = exBool2;
	}

	public int getExBool3() {
		return exBool3;
	}

	public void setExBool3(int exBool3) {
		this.exBool3 = exBool3;
	}

	public int getQuotTermId() {
		return quotTermId;
	}

	public void setQuotTermId(int quotTermId) {
		this.quotTermId = quotTermId;
	}

	public List<QuotDetail> getQuotDetailList() {
		return quotDetailList;
	}

	public void setQuotDetailList(List<QuotDetail> quotDetailList) {
		this.quotDetailList = quotDetailList;
	}

	public float getQuotValue() {
		return quotValue;
	}

	public void setQuotValue(float quotValue) {
		this.quotValue = quotValue;
	}

	public float getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(float taxValue) {
		this.taxValue = taxValue;
	}

	public float getTaxableValue() {
		return taxableValue;
	}

	public void setTaxableValue(float taxableValue) {
		this.taxableValue = taxableValue;
	}

	@Override
	public String toString() {
		return "QuotHeader [quotHeadId=" + quotHeadId + ", quotNo=" + quotNo + ", custId=" + custId + ", userId="
				+ userId + ", quotDate=" + quotDate + ", enqHeadId=" + enqHeadId + ", companyId=" + companyId
				+ ", plantIds=" + plantIds + ", status=" + status + ", delStatus=" + delStatus + ", quotTaxableAmt="
				+ quotTaxableAmt + ", quotTaxAmt=" + quotTaxAmt + ", quotTotal=" + quotTotal + ", payTermId="
				+ payTermId + ", transportTermId=" + transportTermId + ", payTerms=" + payTerms + ", transportTerms="
				+ transportTerms + ", otherRemark1=" + otherRemark1 + ", otherRemark2=" + otherRemark2
				+ ", otherRemark3=" + otherRemark3 + ", otherRemark4=" + otherRemark4 + ", otherValueBeforeTax1="
				+ otherValueBeforeTax1 + ", otherValueBeforeTax2=" + otherValueBeforeTax2 + ", otherValueAfterTax1="
				+ otherValueAfterTax1 + ", otherValueAfterTax2=" + otherValueAfterTax2 + ", projId=" + projId
				+ ", noOfTolls=" + noOfTolls + ", tollCost=" + tollCost + ", otherCost=" + otherCost
				+ ", transportCost=" + transportCost + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3="
				+ exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exDate1=" + exDate1
				+ ", exDate2=" + exDate2 + ", exBool1=" + exBool1 + ", exBool2=" + exBool2 + ", exBool3=" + exBool3
				+ ", quotValue=" + quotValue + ", taxValue=" + taxValue + ", taxableValue=" + taxableValue
				+ ", quotTermId=" + quotTermId + ", quotDetailList=" + quotDetailList + "]";
	}

}
