package com.ats.ssgs.model.rec;

public class PayRecoveryDetail {
	private int payDetailId;

	private int payHeadId;

	private String paymentDate;

	private float paidAmt;

	private int typeTx;

	private String txNo;

	private String remark;

	private int exInt1;

	private int exInt2;

	private String exVarchar1;

	private String exVarchar2;

	private String exDate1;

	private int exBool1;

	private int delStatus;

	public int getPayDetailId() {
		return payDetailId;
	}

	public void setPayDetailId(int payDetailId) {
		this.payDetailId = payDetailId;
	}

	public int getPayHeadId() {
		return payHeadId;
	}

	public void setPayHeadId(int payHeadId) {
		this.payHeadId = payHeadId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public float getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(float paidAmt) {
		this.paidAmt = paidAmt;
	}

	public int getTypeTx() {
		return typeTx;
	}

	public void setTypeTx(int typeTx) {
		this.typeTx = typeTx;
	}

	public String getTxNo() {
		return txNo;
	}

	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getExVarchar1() {
		return exVarchar1;
	}

	public void setExVarchar1(String exVarchar1) {
		this.exVarchar1 = exVarchar1;
	}

	public String getExVarchar2() {
		return exVarchar2;
	}

	public void setExVarchar2(String exVarchar2) {
		this.exVarchar2 = exVarchar2;
	}

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	public int getExBool1() {
		return exBool1;
	}

	public void setExBool1(int exBool1) {
		this.exBool1 = exBool1;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "PayRecoveryDetail [payDetailId=" + payDetailId + ", payHeadId=" + payHeadId + ", paymentDate="
				+ paymentDate + ", paidAmt=" + paidAmt + ", typeTx=" + typeTx + ", txNo=" + txNo + ", remark=" + remark
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVarchar1=" + exVarchar1 + ", exVarchar2="
				+ exVarchar2 + ", exDate1=" + exDate1 + ", exBool1=" + exBool1 + ", delStatus=" + delStatus + "]";
	}

}
