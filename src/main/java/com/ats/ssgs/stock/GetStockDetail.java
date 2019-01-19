package com.ats.ssgs.stock;

public class GetStockDetail {

	private int stockDetId;

	private int stockId;

	private int itemId;

	private float opQty;

	private float prodQty;

	private float chalanQty;

	private float closingQty;

	private int delStatus;

	private int userId;

	private String detailDate;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private float exFloat1;

	private float exFloat2;

	private String exDate1;

	private int exBool1;

	private String itemName;
	private String uomName;
	private String itemCode;

	public int getStockDetId() {
		return stockDetId;
	}

	public void setStockDetId(int stockDetId) {
		this.stockDetId = stockDetId;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public float getOpQty() {
		return opQty;
	}

	public void setOpQty(float opQty) {
		this.opQty = opQty;
	}

	public float getProdQty() {
		return prodQty;
	}

	public void setProdQty(float prodQty) {
		this.prodQty = prodQty;
	}

	public float getChalanQty() {
		return chalanQty;
	}

	public void setChalanQty(float chalanQty) {
		this.chalanQty = chalanQty;
	}

	public float getClosingQty() {
		return closingQty;
	}

	public void setClosingQty(float closingQty) {
		this.closingQty = closingQty;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDetailDate() {
		return detailDate;
	}

	public void setDetailDate(String detailDate) {
		this.detailDate = detailDate;
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

	public float getExFloat1() {
		return exFloat1;
	}

	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}

	public float getExFloat2() {
		return exFloat2;
	}

	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	public int getExBool1() {
		return exBool1;
	}

	public void setExBool1(int exBool1) {
		this.exBool1 = exBool1;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Override
	public String toString() {
		return "GetStockDetail [stockDetId=" + stockDetId + ", stockId=" + stockId + ", itemId=" + itemId + ", opQty="
				+ opQty + ", prodQty=" + prodQty + ", chalanQty=" + chalanQty + ", closingQty=" + closingQty
				+ ", delStatus=" + delStatus + ", userId=" + userId + ", detailDate=" + detailDate + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exFloat1=" + exFloat1
				+ ", exFloat2=" + exFloat2 + ", exDate1=" + exDate1 + ", exBool1=" + exBool1 + ", itemName=" + itemName
				+ ", uomName=" + uomName + ", itemCode=" + itemCode + "]";
	}

}
