package com.ats.ssgs.model;

public class GetPendingBillReport {

	private int chalanDetId;

	private String itemName;
	private String itemCode;
	private String orderDate;
	private String orderNo;

	private String chalanDate;
	private String chalanNo;
	private String custName;
	private String custMobNo;
	private float itemQty;

	public int getChalanDetId() {
		return chalanDetId;
	}

	public void setChalanDetId(int chalanDetId) {
		this.chalanDetId = chalanDetId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getChalanDate() {
		return chalanDate;
	}

	public void setChalanDate(String chalanDate) {
		this.chalanDate = chalanDate;
	}

	public String getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
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

	public float getItemQty() {
		return itemQty;
	}

	public void setItemQty(float itemQty) {
		this.itemQty = itemQty;
	}

	@Override
	public String toString() {
		return "GetPendingBillReport [chalanDetId=" + chalanDetId + ", itemName=" + itemName + ", itemCode=" + itemCode
				+ ", orderDate=" + orderDate + ", orderNo=" + orderNo + ", chalanDate=" + chalanDate + ", chalanNo="
				+ chalanNo + ", custName=" + custName + ", custMobNo=" + custMobNo + ", itemQty=" + itemQty + "]";
	}

}
