package com.ats.ssgs.model;

public class PoChallanDetails {

	private String id;
	private int itemQty;
	private String chalanDate;
	private String chalanNo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public String getChalanDate() {
		return chalanDate;
	}
	public void setChalanDate(String chalanDate) {
		this.chalanDate = chalanDate;
	}
	public String getChalanNo() {
		return chalanNo;
	}
	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
	}
	@Override
	public String toString() {
		return "PoChallanDetails [id=" + id + ", itemQty=" + itemQty + ", chalanDate=" + chalanDate + ", chalanNo="
				+ chalanNo + "]";
	}
	
	
}
