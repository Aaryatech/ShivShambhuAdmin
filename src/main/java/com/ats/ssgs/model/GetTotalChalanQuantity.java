package com.ats.ssgs.model;




public class GetTotalChalanQuantity {

	private int itemId;

	private float result;

	private float totalOrdQuan;
	
	private float totalRemOrdQuan;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}

	public float getTotalOrdQuan() {
		return totalOrdQuan;
	}

	public void setTotalOrdQuan(float totalOrdQuan) {
		this.totalOrdQuan = totalOrdQuan;
	}

	public float getTotalRemOrdQuan() {
		return totalRemOrdQuan;
	}

	public void setTotalRemOrdQuan(float totalRemOrdQuan) {
		this.totalRemOrdQuan = totalRemOrdQuan;
	}

	@Override
	public String toString() {
		return "GetTotalChalanQuantity [itemId=" + itemId + ", result=" + result + ", totalOrdQuan=" + totalOrdQuan
				+ ", totalRemOrdQuan=" + totalRemOrdQuan + "]";
	}
	
	
}