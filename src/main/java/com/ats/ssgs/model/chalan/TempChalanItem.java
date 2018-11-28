package com.ats.ssgs.model.chalan;

public class TempChalanItem {
	
	private float chalanQty; 	
	private int itemId;
	private int poId;
	private int poDetailId;
	private float remOrdQty;
	
	private int orderDetId ;
	private int orderId;
	
	private int index;
	private String itemName;
	private String uomName;
	
	private int uomId;
	
	
	

	
	
	public int getUomId() {
		return uomId;
	}
	public void setUomId(int uomId) {
		this.uomId = uomId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public float getChalanQty() {
		return chalanQty;
	}
	public void setChalanQty(float chalanQty) {
		this.chalanQty = chalanQty;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
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
	public float getRemOrdQty() {
		return remOrdQty;
	}
	public void setRemOrdQty(float remOrdQty) {
		this.remOrdQty = remOrdQty;
	}
	public int getOrderDetId() {
		return orderDetId;
	}
	public void setOrderDetId(int orderDetId) {
		this.orderDetId = orderDetId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "TempChalanItem [chalanQty=" + chalanQty + ", itemId=" + itemId + ", poId=" + poId + ", poDetailId="
				+ poDetailId + ", remOrdQty=" + remOrdQty + ", orderDetId=" + orderDetId + ", orderId=" + orderId
				+ ", index=" + index + ", itemName=" + itemName + ", uomName=" + uomName + "]";
	}
	
	
	
	
}
