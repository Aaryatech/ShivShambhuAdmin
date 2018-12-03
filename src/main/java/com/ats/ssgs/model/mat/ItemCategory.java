package com.ats.ssgs.model.mat;

public class ItemCategory {

	private int itemCatId;

	private String itemCatName;

	private String date;

	private int delStatus;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private String exDate;

	private int exBool1;

	public int getItemCatId() {
		return itemCatId;
	}

	public void setItemCatId(int itemCatId) {
		this.itemCatId = itemCatId;
	}

	public String getItemCatName() {
		return itemCatName;
	}

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getExDate() {
		return exDate;
	}

	public void setExDate(String exDate) {
		this.exDate = exDate;
	}

	public int getExBool1() {
		return exBool1;
	}

	public void setExBool1(int exBool1) {
		this.exBool1 = exBool1;
	}

	@Override
	public String toString() {
		return "ItemCategory [itemCatId=" + itemCatId + ", itemCatName=" + itemCatName + ", date=" + date
				+ ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exDate=" + exDate + ", exBool1=" + exBool1 + "]";
	}

}
