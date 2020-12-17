package com.ats.ssgs.model;

public class GetDatewiseReport {
	private String billDate;

	private int custId;

	private int billHeadId;

	private float qty;

	private String billNo;

	private String custName;

	private float cgstPer;

	private float sgstPer;

	private float igstPer;

	private float cgstAmt;

	private float sgstAmt;

	private float igstAmt;

	private float taxAmt;

	private float discPer;

	private float discAmt;

	private float taxableAmt;

	private float totalAmt;
	
	private float tcsAmt;
	private float grandTotal;

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getBillHeadId() {
		return billHeadId;
	}

	public void setBillHeadId(int billHeadId) {
		this.billHeadId = billHeadId;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public float getDiscPer() {
		return discPer;
	}

	public void setDiscPer(float discPer) {
		this.discPer = discPer;
	}

	public float getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(float discAmt) {
		this.discAmt = discAmt;
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

	@Override
	public String toString() {
		return "GetDatewiseReport [billDate=" + billDate + ", custId=" + custId + ", billHeadId=" + billHeadId
				+ ", qty=" + qty + ", billNo=" + billNo + ", custName=" + custName + ", cgstPer=" + cgstPer
				+ ", sgstPer=" + sgstPer + ", igstPer=" + igstPer + ", cgstAmt=" + cgstAmt + ", sgstAmt=" + sgstAmt
				+ ", igstAmt=" + igstAmt + ", taxAmt=" + taxAmt + ", discPer=" + discPer + ", discAmt=" + discAmt
				+ ", taxableAmt=" + taxableAmt + ", totalAmt=" + totalAmt + ", tcsAmt=" + tcsAmt + ", grandTotal="
				+ grandTotal + "]";
	}

}
