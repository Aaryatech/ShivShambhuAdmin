package com.ats.ssgs.model.rec;


public class GetPayRecoveryHeadCustWise {
	
	
	
	private int custId;
	
	
	/*@Column(name = "pay_head_id")
	private int payHeadId;
*/
	/*@Column(name = "bill_no")
	private String billNo;*/

	/*@Column(name = "bill_head_id")
	private int billHeadId;
*/
	private String billDate;

	
	
	private float billTotal;

	private float paidAmt;

	private float pendingAmt;
	
	private String custName;
	
	private String custMobNo;
/*
	public int getPayHeadId() {
		return payHeadId;
	}

	public void setPayHeadId(int payHeadId) {
		this.payHeadId = payHeadId;
	}*/

	/*public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public int getBillHeadId() {
		return billHeadId;
	}

	public void setBillHeadId(int billHeadId) {
		this.billHeadId = billHeadId;
	}*/

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

	public float getBillTotal() {
		return billTotal;
	}

	public void setBillTotal(float billTotal) {
		this.billTotal = billTotal;
	}

	public float getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(float paidAmt) {
		this.paidAmt = paidAmt;
	}

	public float getPendingAmt() {
		return pendingAmt;
	}

	public void setPendingAmt(float pendingAmt) {
		this.pendingAmt = pendingAmt;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobNo() {
		return custMobNo;
	}

	public void setCustMobNo(String custMobNo) {
		this.custMobNo = custMobNo;
	}

	@Override
	public String toString() {
		return "GetPayRecoveryHeadCustWise [custId=" + custId + ", billDate=" + billDate + ", billTotal=" + billTotal
				+ ", paidAmt=" + paidAmt + ", pendingAmt=" + pendingAmt + ", custName=" + custName + ", custMobNo="
				+ custMobNo + ", getBillDate()=" + getBillDate() + ", getCustId()=" + getCustId() + ", getBillTotal()="
				+ getBillTotal() + ", getPaidAmt()=" + getPaidAmt() + ", getPendingAmt()=" + getPendingAmt()
				+ ", getCustName()=" + getCustName() + ", getCustMobNo()=" + getCustMobNo() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	

}
