package com.ats.ssgs.model.chalan;

public class GetChalanPendingReport {

	private int orderDetId;
	private int itemId;
	private String itemName;
	private String itemCode;
	private String orderDate;
	private String orderNo;
	private String custName;
	private String custMobNo;
	private float orderQty;

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

	public float getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(float orderQty) {
		this.orderQty = orderQty;
	}

	public int getOrderDetId() {
		return orderDetId;
	}

	public void setOrderDetId(int orderDetId) {
		this.orderDetId = orderDetId;
	}

	@Override
	public String toString() {
		return "GetChalanPendingReport [orderDetId=" + orderDetId + ", itemId=" + itemId + ", itemName=" + itemName
				+ ", itemCode=" + itemCode + ", orderDate=" + orderDate + ", orderNo=" + orderNo + ", custName="
				+ custName + ", custMobNo=" + custMobNo + ", orderQty=" + orderQty + "]";
	}

}
