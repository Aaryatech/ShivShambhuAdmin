package com.ats.ssgs.model.enq;

public class TempEnqItem {

	private int itemId;

	private String itemName;
	
	private float enqQty;
	
	private int uomId;

	private String uomName;
	
	private String itemEnqRemark;
	
	private int isDuplicate;
	
	private int itemUomId;
	
	private float itemRate1;
	
	public int getItemUomId() {
		return itemUomId;
	}

	public void setItemUomId(int itemUomId) {
		this.itemUomId = itemUomId;
	}

	public int getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(int isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

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

	public float getItemRate1() {
		return itemRate1;
	}

	public void setItemRate1(float itemRate1) {
		this.itemRate1 = itemRate1;
	}

	@Override
	public String toString() {
		return "TempEnqItem [itemId=" + itemId + ", itemName=" + itemName + ", enqQty=" + enqQty + ", uomId=" + uomId
				+ ", uomName=" + uomName + ", itemEnqRemark=" + itemEnqRemark + ", isDuplicate=" + isDuplicate
				+ ", itemUomId=" + itemUomId + ", itemRate1=" + itemRate1 + "]";
	}
	
}
