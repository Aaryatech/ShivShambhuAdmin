package com.ats.ssgs.model;

public class TaxSummery {
	private int billDetailId;

	private String custGstNo;
	private String custName;

	private float gstPer;

	private float taxableAmt;

	private float taxAmt;

	private float totalAmt;

	private float sgstAmt;
	private float cgstAmt;
	private float igstAmt;
	private float tcsAmt;

	public int getBillDetailId() {
		return billDetailId;
	}

	public void setBillDetailId(int billDetailId) {
		this.billDetailId = billDetailId;
	}

	public String getCustGstNo() {
		return custGstNo;
	}

	public void setCustGstNo(String custGstNo) {
		this.custGstNo = custGstNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public float getGstPer() {
		return gstPer;
	}

	public void setGstPer(float gstPer) {
		this.gstPer = gstPer;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(float taxAmt) {
		this.taxAmt = taxAmt;
	}

	public float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}

	public float getSgstAmt() {
		return sgstAmt;
	}

	public void setSgstAmt(float sgstAmt) {
		this.sgstAmt = sgstAmt;
	}

	public float getCgstAmt() {
		return cgstAmt;
	}

	public void setCgstAmt(float cgstAmt) {
		this.cgstAmt = cgstAmt;
	}

	public float getIgstAmt() {
		return igstAmt;
	}

	public void setIgstAmt(float igstAmt) {
		this.igstAmt = igstAmt;
	}

	public float getTcsAmt() {
		return tcsAmt;
	}

	public void setTcsAmt(float tcsAmt) {
		this.tcsAmt = tcsAmt;
	}

	@Override
	public String toString() {
		return "TaxSummery [billDetailId=" + billDetailId + ", custGstNo=" + custGstNo + ", custName=" + custName
				+ ", gstPer=" + gstPer + ", taxableAmt=" + taxableAmt + ", taxAmt=" + taxAmt + ", totalAmt=" + totalAmt
				+ ", sgstAmt=" + sgstAmt + ", cgstAmt=" + cgstAmt + ", igstAmt=" + igstAmt + ", tcsAmt=" + tcsAmt + "]";
	}


}
