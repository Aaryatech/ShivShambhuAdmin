package com.ats.ssgs.model;


public class GetItenwiseBillReport {

	private int item_id;

	private String itemName;
	
	private String itemCode;
	
	private float qty;
	
	private String hsnCode;
	
	private float rate;
	
	private float cgstPer;
	
	private float sgstPer;
	
	private float igstPer;
	
	private float cgstAmt;
	
	private float sgstAmt;
	
	private float igstAmt;
	
	private float taxAmt;
	
	private float discPer;
	
	
	private float discAmt;
	
	
	private float taxableAmt;
	
	
	private float totalAmt;

	
	private int delStatus;
	
	private String uomName;
	
	private float tcsAmt;
	
	public String getHsnCode() {
		return hsnCode;
	}


	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}


	public String getUomName() {
		return uomName;
	}


	public void setUomName(String uomName) {
		this.uomName = uomName;
	}


	public int getItem_id() {
		return item_id;
	}


	public void setItem_id(int item_id) {
		this.item_id = item_id;
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


	public float getCgstPer() {
		return cgstPer;
	}


	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}


	public float getSgstPer() {
		return sgstPer;
	}


	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}


	public float getIgstPer() {
		return igstPer;
	}


	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
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


	public float getIgstAmt() {
		return igstAmt;
	}


	public void setIgstAmt(float igstAmt) {
		this.igstAmt = igstAmt;
	}


	public float getTaxAmt() {
		return taxAmt;
	}


	public void setTaxAmt(float taxAmt) {
		this.taxAmt = taxAmt;
	}


	public float getDiscPer() {
		return discPer;
	}


	public void setDiscPer(float discPer) {
		this.discPer = discPer;
	}


	public float getDiscAmt() {
		return discAmt;
	}


	public void setDiscAmt(float discAmt) {
		this.discAmt = discAmt;
	}


	public float getTaxableAmt() {
		return taxableAmt;
	}


	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}


	public float getTotalAmt() {
		return totalAmt;
	}


	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}


	public int getDelStatus() {
		return delStatus;
	}


	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}


	public float getTcsAmt() {
		return tcsAmt;
	}


	public void setTcsAmt(float tcsAmt) {
		this.tcsAmt = tcsAmt;
	}


	@Override
	public String toString() {
		return "GetItenwiseBillReport [item_id=" + item_id + ", itemName=" + itemName + ", itemCode=" + itemCode
				+ ", qty=" + qty + ", hsnCode=" + hsnCode + ", rate=" + rate + ", cgstPer=" + cgstPer + ", sgstPer="
				+ sgstPer + ", igstPer=" + igstPer + ", cgstAmt=" + cgstAmt + ", sgstAmt=" + sgstAmt + ", igstAmt="
				+ igstAmt + ", taxAmt=" + taxAmt + ", discPer=" + discPer + ", discAmt=" + discAmt + ", taxableAmt="
				+ taxableAmt + ", totalAmt=" + totalAmt + ", delStatus=" + delStatus + ", uomName=" + uomName
				+ ", tcsAmt=" + tcsAmt + "]";
	}

}
