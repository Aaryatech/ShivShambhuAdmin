package com.ats.ssgs.model;




public class GetExistingItemDetail {
	
	
	private int itemDetailId;

	private String rmName;
	
	private String  uomName;
	
	private float rmQty;
	
	private String catDesc;

	public int getItemDetailId() {
		return itemDetailId;
	}

	public void setItemDetailId(int itemDetailId) {
		this.itemDetailId = itemDetailId;
	}

	

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}


	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public float getRmQty() {
		return rmQty;
	}

	public void setRmQty(float rmQty) {
		this.rmQty = rmQty;
	}

	@Override
	public String toString() {
		return "GetExistingItemDetail [itemDetailId=" + itemDetailId + ", rmName=" + rmName + ", uomName=" + uomName
				+ ", rmQty=" + rmQty + ", catDesc=" + catDesc + ", getItemDetailId()=" + getItemDetailId()
				+ ", getRmName()=" + getRmName() + ", getUomName()=" + getUomName() + ", getCatDesc()=" + getCatDesc()
				+ ", getRmQty()=" + getRmQty() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	
	
	

}
