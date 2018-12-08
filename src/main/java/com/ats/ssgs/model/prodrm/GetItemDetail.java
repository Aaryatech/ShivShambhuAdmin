package com.ats.ssgs.model.prodrm;

public class GetItemDetail {

	private int itemDetailId;

	private int itemId;

	private String rmName;

	private float noOfPiecesPerItem;

	private float rmQty;

	private float rmWeight;

	private String rmUomName;

	private float rmQuantity;

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

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	

	public float getNoOfPiecesPerItem() {
		return noOfPiecesPerItem;
	}

	public void setNoOfPiecesPerItem(float noOfPiecesPerItem) {
		this.noOfPiecesPerItem = noOfPiecesPerItem;
	}

	public float getRmQty() {
		return rmQty;
	}

	public void setRmQty(float rmQty) {
		this.rmQty = rmQty;
	}

	public float getRmWeight() {
		return rmWeight;
	}

	public void setRmWeight(float rmWeight) {
		this.rmWeight = rmWeight;
	}


	public String getRmUomName() {
		return rmUomName;
	}

	public void setRmUomName(String rmUomName) {
		this.rmUomName = rmUomName;
	}

	public float getRmQuantity() {
		return rmQuantity;
	}

	public void setRmQuantity(float rmQuantity) {
		this.rmQuantity = rmQuantity;
	}

	@Override
	public String toString() {
		return "GetItemDetail [itemDetailId=" + itemDetailId + ", itemId=" + itemId + ", rmName=" + rmName
				+ ", noOfPiecesPerItem=" + noOfPiecesPerItem + ", rmQty=" + rmQty + ", rmWeight=" + rmWeight
				+ ", rmUomName=" + rmUomName + ", rmQuantity=" + rmQuantity + "]";
	}

}
