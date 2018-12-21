package com.ats.ssgs.model;

public class GetAddItemDetail {
	
	
	private int isDuplicate;
	
	private String itemName;
	
	private String uom;
	
	private float qty;
	
	private String categoryName;

	public int getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(int isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "GetAddItemDetail [isDuplicate=" + isDuplicate + ", itemName=" + itemName + ", uom=" + uom + ", qty="
				+ qty + ", categoryName=" + categoryName + ", getIsDuplicate()=" + getIsDuplicate() + ", getItemName()="
				+ getItemName() + ", getUom()=" + getUom() + ", getQty()=" + getQty() + ", getCategoryName()="
				+ getCategoryName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	

}
