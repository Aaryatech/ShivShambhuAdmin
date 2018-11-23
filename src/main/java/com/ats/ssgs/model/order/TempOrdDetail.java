package com.ats.ssgs.model.order;

public class TempOrdDetail {
	
	private int itemId;
	
	private int poDetailId;
	
	private float orderQty;
	private float total;
	
	private float orderRate;
	
	
	public float getOrderRate() {
		return orderRate;
	}
	public void setOrderRate(float orderRate) {
		this.orderRate = orderRate;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getPoDetailId() {
		return poDetailId;
	}
	public void setPoDetailId(int poDetailId) {
		this.poDetailId = poDetailId;
	}
	public float getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(float orderQty) {
		this.orderQty = orderQty;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "TempOrdDetail [itemId=" + itemId + ", poDetailId=" + poDetailId + ", orderQty=" + orderQty + ", total="
				+ total + ", orderRate=" + orderRate + "]";
	}
	
	
	

}
