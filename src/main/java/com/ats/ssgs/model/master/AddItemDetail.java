package com.ats.ssgs.model.master;

public class AddItemDetail {

	private int itemDetailId;
	private int itemId;

	private int rmType;

	private int rmId;

	private String rmName;

	private int rmUomId;

	private float rmQty;

	private float rmWeight;

	private float noPiecesPerItem;

	private int delStatus;

	private int int1;

	private int int2;

	private String exVar1;
	private int boll1;

	private int boll2;

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
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

	public int getRmType() {
		return rmType;
	}

	public void setRmType(int rmType) {
		this.rmType = rmType;
	}

	public int getRmId() {
		return rmId;
	}

	public void setRmId(int rmId) {
		this.rmId = rmId;
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

	public float getNoPiecesPerItem() {
		return noPiecesPerItem;
	}

	public void setNoPiecesPerItem(float noPiecesPerItem) {
		this.noPiecesPerItem = noPiecesPerItem;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getInt1() {
		return int1;
	}

	public void setInt1(int int1) {
		this.int1 = int1;
	}

	public int getInt2() {
		return int2;
	}

	public void setInt2(int int2) {
		this.int2 = int2;
	}

	public int getBoll1() {
		return boll1;
	}

	public void setBoll1(int boll1) {
		this.boll1 = boll1;
	}

	public int getBoll2() {
		return boll2;
	}

	public void setBoll2(int boll2) {
		this.boll2 = boll2;
	}

	@Override
	public String toString() {
		return "AddItemDetail [itemDetailId=" + itemDetailId + ", itemId=" + itemId + ", rmType=" + rmType + ", rmId="
				+ rmId + ", rmName=" + rmName + ", rmUomId=" + rmUomId + ", rmQty=" + rmQty + ", rmWeight=" + rmWeight
				+ ", noPiecesPerItem=" + noPiecesPerItem + ", delStatus=" + delStatus + ", int1=" + int1 + ", int2="
				+ int2 + ", exVar1=" + exVar1 + ", boll1=" + boll1 + ", boll2=" + boll2 + "]";
	}

}
