package com.ats.ssgs.model.mat;

public class TempMatIssueDetail {

	private int matDetailId;

	private int itemId;

	private String itemName;

	private float itemRate;

	private float quantity;

	private float value;

	private int uomId;

	private String uomName;

	private int catId;

	private int isDuplicate;

	public int getMatDetailId() {
		return matDetailId;
	}

	public void setMatDetailId(int matDetailId) {
		this.matDetailId = matDetailId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public float getItemRate() {
		return itemRate;
	}

	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(int isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	@Override
	public String toString() {
		return "TempMatIssueDetail [matDetailId=" + matDetailId + ", itemId=" + itemId + ", itemName=" + itemName
				+ ", itemRate=" + itemRate + ", quantity=" + quantity + ", value=" + value + ", uomId=" + uomId
				+ ", uomName=" + uomName + ", catId=" + catId + ", isDuplicate=" + isDuplicate + "]";
	}

}
