package com.ats.ssgs.model.prodrm;

import java.io.Serializable;

public class ItemDetail implements Serializable{

	private int itemDetailId;
	
	private int itemId;
	
	private int rmId;
	
	private String rmName;
	
	private int noOfPiecesPerItem;
	
	private int rmType;
	
	private int rmUomId;
	
	private int rmQty;
	
	private float rmWeight;
	
	private int delStatus;

	
	public int getNoOfPiecesPerItem() {
		return noOfPiecesPerItem;
	}

	public void setNoOfPiecesPerItem(int noOfPiecesPerItem) {
		this.noOfPiecesPerItem = noOfPiecesPerItem;
	}

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

	public int getRmId() {
		return rmId;
	}

	public void setRmId(int rmId) {
		this.rmId = rmId;
	}

	public int getRmType() {
		return rmType;
	}

	public void setRmType(int rmType) {
		this.rmType = rmType;
	}

	public int getRmQty() {
		return rmQty;
	}

	public void setRmQty(int rmQty) {
		this.rmQty = rmQty;
	}

	

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
    
	
	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public int getRmUomId() {
		return rmUomId;
	}

	public void setRmUomId(int rmUomId) {
		this.rmUomId = rmUomId;
	}


	public float getRmWeight() {
		return rmWeight;
	}

	public void setRmWeight(float rmWeight) {
		this.rmWeight = rmWeight;
	}


	@Override
	public String toString() {
		return "ItemDetail [itemDetailId=" + itemDetailId + ", itemId=" + itemId + ", rmId=" + rmId + ", rmName="
				+ rmName + ", noOfPiecesPerItem=" + noOfPiecesPerItem + ", rmType=" + rmType + ", rmUomId=" + rmUomId
				+ ", rmQty=" + rmQty + ", rmWeight=" + rmWeight + ", delStatus=" + delStatus + "]";
	}
   
}
