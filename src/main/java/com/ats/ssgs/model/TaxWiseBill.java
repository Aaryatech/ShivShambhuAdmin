package com.ats.ssgs.model;



public class TaxWiseBill {
	

	
	private int billHeadId;
	
	private int custId;
	
	private float qty;
	
	private String custGstNo;
	
	private String billNo;

	private String custName;
	
	private int projId;
	
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


	public int getCustId() {
		return custId;
	}


	public void setCustId(int custId) {
		this.custId = custId;
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


	public String getCustGstNo() {
		return custGstNo;
	}


	public void setCustGstNo(String custGstNo) {
		this.custGstNo = custGstNo;
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


	public int getProjId() {
		return projId;
	}


	public void setProjId(int projId) {
		this.projId = projId;
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

	

	public int getBillHeadId() {
		return billHeadId;
	}


	public void setBillHeadId(int billHeadId) {
		this.billHeadId = billHeadId;
	}


	@Override
	public String toString() {
		return "TaxWiseBill [billHeadId=" + billHeadId + ", custId=" + custId + ", qty=" + qty + ", custGstNo="
				+ custGstNo + ", billNo=" + billNo + ", custName=" + custName + ", projId=" + projId + ", cgstPer="
				+ cgstPer + ", sgstPer=" + sgstPer + ", igstPer=" + igstPer + ", cgstAmt=" + cgstAmt + ", sgstAmt="
				+ sgstAmt + ", igstAmt=" + igstAmt + ", taxAmt=" + taxAmt + ", discPer=" + discPer + ", discAmt="
				+ discAmt + ", taxableAmt=" + taxableAmt + ", totalAmt=" + totalAmt + ", getCustId()=" + getCustId()
				+ ", getQty()=" + getQty() + ", getBillNo()=" + getBillNo() + ", getCustGstNo()=" + getCustGstNo()
				+ ", getCustName()=" + getCustName() + ", getProjId()=" + getProjId() + ", getCgstPer()=" + getCgstPer()
				+ ", getSgstPer()=" + getSgstPer() + ", getIgstPer()=" + getIgstPer() + ", getCgstAmt()=" + getCgstAmt()
				+ ", getSgstAmt()=" + getSgstAmt() + ", getIgstAmt()=" + getIgstAmt() + ", getTaxAmt()=" + getTaxAmt()
				+ ", getDiscPer()=" + getDiscPer() + ", getDiscAmt()=" + getDiscAmt() + ", getTaxableAmt()="
				+ getTaxableAmt() + ", getTotalAmt()=" + getTotalAmt() + ", getBillHeadId()=" + getBillHeadId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}


	
}
