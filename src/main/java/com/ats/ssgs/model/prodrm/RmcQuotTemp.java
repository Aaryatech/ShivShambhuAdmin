package com.ats.ssgs.model.prodrm;

public class RmcQuotTemp {

	private int tempDetailId;

	private int itemDetailId;

	private int rmId;

	private float rmQty;// ie constant

	private float itemOpRate;// ie rate

	private float itemWt;

	private String itemCode;// rm Item Code

	private String itemDesc;// rm Item Name

	private String uom;

	private int itemId;

	private String itemName;// fg Item Name

	private float unitRate = 0;

	private float amt = 0;
	private int delStatus;

	private int quotDetailId;

	private int quotNo;

	private int poNo;

	private int chalanNo;

	private int orderNo;

	private int billNo;

	public int getItemDetailId() {
		return itemDetailId;
	}

	public void setItemDetailId(int itemDetailId) {
		this.itemDetailId = itemDetailId;
	}

	public int getRmId() {
		return rmId;
	}

	public void setRmId(int rmId) {
		this.rmId = rmId;
	}

	public float getRmQty() {
		return rmQty;
	}

	public void setRmQty(float rmQty) {
		this.rmQty = rmQty;
	}

	public float getItemOpRate() {
		return itemOpRate;
	}

	public void setItemOpRate(float itemOpRate) {
		this.itemOpRate = itemOpRate;
	}

	public float getItemWt() {
		return itemWt;
	}

	public void setItemWt(float itemWt) {
		this.itemWt = itemWt;
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public float getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(float unitRate) {
		this.unitRate = unitRate;
	}

	public float getAmt() {
		return amt;
	}

	public void setAmt(float amt) {
		this.amt = amt;
	}

	public int getTempDetailId() {
		return tempDetailId;
	}

	public void setTempDetailId(int tempDetailId) {
		this.tempDetailId = tempDetailId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getQuotDetailId() {
		return quotDetailId;
	}

	public void setQuotDetailId(int quotDetailId) {
		this.quotDetailId = quotDetailId;
	}

	public int getQuotNo() {
		return quotNo;
	}

	public void setQuotNo(int quotNo) {
		this.quotNo = quotNo;
	}

	public int getPoNo() {
		return poNo;
	}

	public void setPoNo(int poNo) {
		this.poNo = poNo;
	}

	public int getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(int chalanNo) {
		this.chalanNo = chalanNo;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	@Override
	public String toString() {
		return "RmcQuotTemp [tempDetailId=" + tempDetailId + ", itemDetailId=" + itemDetailId + ", rmId=" + rmId
				+ ", rmQty=" + rmQty + ", itemOpRate=" + itemOpRate + ", itemWt=" + itemWt + ", itemCode=" + itemCode
				+ ", itemDesc=" + itemDesc + ", uom=" + uom + ", itemId=" + itemId + ", itemName=" + itemName
				+ ", unitRate=" + unitRate + ", amt=" + amt + ", delStatus=" + delStatus + ", quotDetailId="
				+ quotDetailId + ", quotNo=" + quotNo + ", poNo=" + poNo + ", chalanNo=" + chalanNo + ", orderNo="
				+ orderNo + ", billNo=" + billNo + "]";
	}

}
