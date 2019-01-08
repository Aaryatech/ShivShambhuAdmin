package com.ats.ssgs.model.quot;

import java.util.Date;

public class GetQuotDetailPrint {
	
	private int quotDetailId;

	private int quotHeadId;

	private int itemId;

	private float quotQty;

	private float rate;

	
	private float total;
	
	
	private float taxPer;//new
	private float taxableValue;//new 
	private float taxValue;//new
	private float isTaxInc;//new
	
	
	
	private String uomName;
	
	private String itemName;
	
	private String itemCode;
	
	private int quotUomId;
	
	private String quotNo;
	
	private String quotDate;
	
	

	public String getQuotDate() {
		return quotDate;
	}

	public void setQuotDate(String quotDate) {
		this.quotDate = quotDate;
	}

	public int getQuotDetailId() {
		return quotDetailId;
	}

	public void setQuotDetailId(int quotDetailId) {
		this.quotDetailId = quotDetailId;
	}

	public int getQuotHeadId() {
		return quotHeadId;
	}

	public void setQuotHeadId(int quotHeadId) {
		this.quotHeadId = quotHeadId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public float getQuotQty() {
		return quotQty;
	}

	public void setQuotQty(float quotQty) {
		this.quotQty = quotQty;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getQuotUomId() {
		return quotUomId;
	}

	public void setQuotUomId(int quotUomId) {
		this.quotUomId = quotUomId;
	}

	public String getQuotNo() {
		return quotNo;
	}

	public void setQuotNo(String quotNo) {
		this.quotNo = quotNo;
	}

	
	
	
	public float getTaxPer() {
		return taxPer;
	}

	public void setTaxPer(float taxPer) {
		this.taxPer = taxPer;
	}

	public float getTaxableValue() {
		return taxableValue;
	}

	public void setTaxableValue(float taxableValue) {
		this.taxableValue = taxableValue;
	}

	public float getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(float taxValue) {
		this.taxValue = taxValue;
	}

	public float getIsTaxInc() {
		return isTaxInc;
	}

	public void setIsTaxInc(float isTaxInc) {
		this.isTaxInc = isTaxInc;
	}

	@Override
	public String toString() {
		return "GetQuotDetailPrint [quotDetailId=" + quotDetailId + ", quotHeadId=" + quotHeadId + ", itemId=" + itemId
				+ ", quotQty=" + quotQty + ", rate=" + rate + ", total=" + total + ", taxPer=" + taxPer
				+ ", taxableValue=" + taxableValue + ", taxValue=" + taxValue + ", isTaxInc=" + isTaxInc + ", uomName="
				+ uomName + ", itemName=" + itemName + ", itemCode=" + itemCode + ", quotUomId=" + quotUomId
				+ ", quotNo=" + quotNo + ", quotDate=" + quotDate + "]";
	}
	
	
}
