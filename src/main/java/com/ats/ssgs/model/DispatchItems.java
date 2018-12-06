package com.ats.ssgs.model;


public class DispatchItems{

	private int orderDetId;

	private int itemId;

	private String itemCode;
	
	private String itemName;
	
	private float qty;
	
	private float remQty;

	public int getOrderDetId() {
		return orderDetId;
	}

	public void setOrderDetId(int orderDetId) {
		this.orderDetId = orderDetId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public float getRemQty() {
		return remQty;
	}

	public void setRemQty(float remQty) {
		this.remQty = remQty;
	}

	@Override
	public String toString() {
		return "DispatchItems [orderDetId=" + orderDetId + ", itemId=" + itemId + ", itemCode=" + itemCode
				+ ", itemName=" + itemName + ", qty=" + qty + ", remQty=" + remQty + "]";
	}
	
	
	
	
}
