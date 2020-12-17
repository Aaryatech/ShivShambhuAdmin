package com.ats.ssgs.model;

public class MonthWiseBill {

	private String month;

	private String monthNo;

	private String year;

	private String billNo;

	private float cgstPer;

	private float sgstPer;

	private float igstPer;

	private float cgstAmt;

	private float sgstAmt;

	private float igstAmt;

	private float taxAmt;

	private float taxableAmt;

	private float totalAmt;

	private float tcsAmt;	
	private float grandTotal;
	private int billHeadId;


	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public float getCgstPer() {
		return cgstPer;
	}

	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}

	public float getSgstPer() {
		return sgstPer;
	}

	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}

	public float getIgstPer() {
		return igstPer;
	}

	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
	}

	public float getCgstAmt() {
		return cgstAmt;
	}

	public void setCgstAmt(float cgstAmt) {
		this.cgstAmt = cgstAmt;
	}

	public float getSgstAmt() {
		return sgstAmt;
	}

	public void setSgstAmt(float sgstAmt) {
		this.sgstAmt = sgstAmt;
	}

	public float getIgstAmt() {
		return igstAmt;
	}

	public void setIgstAmt(float igstAmt) {
		this.igstAmt = igstAmt;
	}

	public float getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(float taxAmt) {
		this.taxAmt = taxAmt;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getMonthNo() {
		return monthNo;
	}

	public void setMonthNo(String monthNo) {
		this.monthNo = monthNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public float getTcsAmt() {
		return tcsAmt;
	}

	public void setTcsAmt(float tcsAmt) {
		this.tcsAmt = tcsAmt;
	}

	public float getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}

	public int getBillHeadId() {
		return billHeadId;
	}

	public void setBillHeadId(int billHeadId) {
		this.billHeadId = billHeadId;
	}

	@Override
	public String toString() {
		return "MonthWiseBill [month=" + month + ", monthNo=" + monthNo + ", year=" + year + ", billNo=" + billNo
				+ ", cgstPer=" + cgstPer + ", sgstPer=" + sgstPer + ", igstPer=" + igstPer + ", cgstAmt=" + cgstAmt
				+ ", sgstAmt=" + sgstAmt + ", igstAmt=" + igstAmt + ", taxAmt=" + taxAmt + ", taxableAmt=" + taxableAmt
				+ ", totalAmt=" + totalAmt + ", tcsAmt=" + tcsAmt + ", grandTotal=" + grandTotal + ", billHeadId="
				+ billHeadId + "]";
	}

	
}
