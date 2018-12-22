package com.ats.ssgs.model;

public class GetAddItemDetail {
	private int itemDetailId;
	
	private int itemId;
	
	private int rmId;
	
	private int isDuplicate;
	
	private String rmName;
	
	
	private float qty;
	
	private String categoryName;
	
	private int uomId;

	private String uomName;

	private int catId;

	
	
	

	public int getItemDetailId() {
		return itemDetailId;
	}

	public void setItemDetailId(int itemDetailId) {
		this.itemDetailId = itemDetailId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(int isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	
	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
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

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	
	
	public int getRmId() {
		return rmId;
	}

	public void setRmId(int rmId) {
		this.rmId = rmId;
	}

	@Override
	public String toString() {
		return "GetAddItemDetail [itemDetailId=" + itemDetailId + ", itemId=" + itemId + ", rmId=" + rmId
				+ ", isDuplicate=" + isDuplicate + ", rmName=" + rmName + ", qty=" + qty + ", categoryName="
				+ categoryName + ", uomId=" + uomId + ", uomName=" + uomName + ", catId=" + catId
				+ ", getItemDetailId()=" + getItemDetailId() + ", getItemId()=" + getItemId() + ", getIsDuplicate()="
				+ getIsDuplicate() + ", getRmName()=" + getRmName() + ", getQty()=" + getQty() + ", getCategoryName()="
				+ getCategoryName() + ", getUomId()=" + getUomId() + ", getUomName()=" + getUomName() + ", getCatId()="
				+ getCatId() + ", getRmId()=" + getRmId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	
	
}
