package com.ats.ssgs.model.enq;

public class TempEnqItem {

	private int itemId;

	private String itemName;
	
	private float enqQty;
	

	private int uomId;

	private String uomName;
	
	private String itemEnqRemark;
	
	

	public String getItemEnqRemark() {
		return itemEnqRemark;
	}

	public void setItemEnqRemark(String itemEnqRemark) {
		this.itemEnqRemark = itemEnqRemark;
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

	public int getUomId() {
		return uomId;
	}

	public void setUomId(int uomId) {
		this.uomId = uomId;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public float getEnqQty() {
		return enqQty;
	}

	public void setEnqQty(float enqQty) {
		this.enqQty = enqQty;
	}

	@Override
	public String toString() {
		return "TempEnqItem [itemId=" + itemId + ", itemName=" + itemName + ", enqQty=" + enqQty + ", uomId=" + uomId
				+ ", uomName=" + uomName + ", itemEnqRemark=" + itemEnqRemark + "]";
	}
	
}
