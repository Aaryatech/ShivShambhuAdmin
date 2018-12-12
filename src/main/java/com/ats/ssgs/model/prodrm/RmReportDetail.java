package com.ats.ssgs.model.prodrm;


public class RmReportDetail {
	

	private int bomReqDetailId;
	
	private int bomReqId;;

	private int rmId;

	private String uom;

	private float autoRmReqQty;
	private float rmReqQty;

	private float rmIssueQty;

	private float rejectedQty;
	private float returnQty;

	private String mrnBatch;

	private int status;
	
	private String bomReqDate;
	
	private String itemCode;
	private String itemDesc;
	private String uomName;
	
	
	public int getBomReqDetailId() {
		return bomReqDetailId;
	}
	public void setBomReqDetailId(int bomReqDetailId) {
		this.bomReqDetailId = bomReqDetailId;
	}
	public int getBomReqId() {
		return bomReqId;
	}
	public void setBomReqId(int bomReqId) {
		this.bomReqId = bomReqId;
	}
	public int getRmId() {
		return rmId;
	}
	public void setRmId(int rmId) {
		this.rmId = rmId;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
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
	public String getMrnBatch() {
		return mrnBatch;
	}
	public void setMrnBatch(String mrnBatch) {
		this.mrnBatch = mrnBatch;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getBomReqDate() {
		return bomReqDate;
	}
	public void setBomReqDate(String bomReqDate) {
		this.bomReqDate = bomReqDate;
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
		return "RmReportDetail [bomReqDetailId=" + bomReqDetailId + ", bomReqId=" + bomReqId + ", rmId=" + rmId
				+ ", uom=" + uom + ", autoRmReqQty=" + autoRmReqQty + ", rmReqQty=" + rmReqQty + ", rmIssueQty="
				+ rmIssueQty + ", rejectedQty=" + rejectedQty + ", returnQty=" + returnQty + ", mrnBatch=" + mrnBatch
				+ ", status=" + status + ", bomReqDate=" + bomReqDate + ", itemCode=" + itemCode + ", itemDesc="
				+ itemDesc + ", uomName=" + uomName + "]";
	}
	
}
