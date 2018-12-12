package com.ats.ssgs.model.prodrm;


public class RmReportHeader {
	
	
	private int rmId;// ie rm Item Id

	private float autoRmReqQty;
	private float rmReqQty;
	private float rmIssueQty;
	private float rejectedQty;
	private float returnQty;
	
	private String itemCode;
	private String itemDesc;
	private String uomName;
	
	public int getRmId() {
		return rmId;
	}
	public void setRmId(int rmId) {
		this.rmId = rmId;
	}
	public float getAutoRmReqQty() {
		return autoRmReqQty;
	}
	public void setAutoRmReqQty(float autoRmReqQty) {
		this.autoRmReqQty = autoRmReqQty;
	}
	public float getRmReqQty() {
		return rmReqQty;
	}
	public void setRmReqQty(float rmReqQty) {
		this.rmReqQty = rmReqQty;
	}
	public float getRmIssueQty() {
		return rmIssueQty;
	}
	public void setRmIssueQty(float rmIssueQty) {
		this.rmIssueQty = rmIssueQty;
	}
	public float getRejectedQty() {
		return rejectedQty;
	}
	public void setRejectedQty(float rejectedQty) {
		this.rejectedQty = rejectedQty;
	}
	public float getReturnQty() {
		return returnQty;
	}
	public void setReturnQty(float returnQty) {
		this.returnQty = returnQty;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	@Override
	public String toString() {
		return "RmReportHeader [rmId=" + rmId + ", autoRmReqQty=" + autoRmReqQty + ", rmReqQty=" + rmReqQty
				+ ", rmIssueQty=" + rmIssueQty + ", rejectedQty=" + rejectedQty + ", returnQty=" + returnQty
				+ ", itemCode=" + itemCode + ", itemDesc=" + itemDesc + ", uomName=" + uomName + "]";
	}
	

	
	
	
}
