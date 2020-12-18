package com.ats.ssgs.model;

public class GetDailySalesReport {
	
	private String id;
	private String custName;
	private String siteName;
	private String itemDesc;
	private float qty;
	private float rate;
	private float taxableAmt;
	private float cgstAmt;
	private float sgstAmt;
	private float totalAmt;
	private float grandTotal;
	private float tcsAmt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public float getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	public float getCgstAmt() {
		return cgstAmt;
	}
	public void setCgstAmt(float cgstAmt) {
		this.cgstAmt = cgstAmt;
	}
	public float getSgstAmt() {
		return sgstAmt;
	}
	public void setSgstAmt(float sgstAmt) {
		this.sgstAmt = sgstAmt;
	}
	public float getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}
	public float getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}
	public float getTcsAmt() {
		return tcsAmt;
	}
	public void setTcsAmt(float tcsAmt) {
		this.tcsAmt = tcsAmt;
	}
	
	@Override
	public String toString() {
		return "GetDailySalesReport [id=" + id + ", custName=" + custName + ", siteName=" + siteName + ", itemDesc="
				+ itemDesc + ", qty=" + qty + ", rate=" + rate + ", taxableAmt=" + taxableAmt + ", cgstAmt=" + cgstAmt
				+ ", sgstAmt=" + sgstAmt + ", totalAmt=" + totalAmt + ", grandTotal=" + grandTotal + ", tcsAmt="
				+ tcsAmt + "]";
	}

}
