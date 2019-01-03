package com.ats.ssgs.model;

public class GetPoReport {

	private int poDetailId;
	private int poId;

	private String poDate;
	private String poNo;

	private int itemId;
	private String custName;
	private String custMobNo;
	private String itemName;
	private float poRemainingQty;

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public int getPoDetailId() {
		return poDetailId;
	}

	public void setPoDetailId(int poDetailId) {
		this.poDetailId = poDetailId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
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

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public float getPoRemainingQty() {
		return poRemainingQty;
	}

	public void setPoRemainingQty(float poRemainingQty) {
		this.poRemainingQty = poRemainingQty;
	}

	
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "GetPoReport [poDetailId=" + poDetailId + ", poId=" + poId + ", poDate=" + poDate + ", poNo=" + poNo
				+ ", itemId=" + itemId + ", custName=" + custName + ", custMobNo=" + custMobNo + ", itemName="
				+ itemName + ", poRemainingQty=" + poRemainingQty + "]";
	}

}
