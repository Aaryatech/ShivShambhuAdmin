package com.ats.ssgs.model.prodrm;


public class ProdReportHeader {
	
	private int itemId;
	
	private String itemName;
	private String itemCode;
	private float planQty;
	private float productionQty;
	
	private int status;
	
	private String uomName;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
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

	public float getPlanQty() {
		return planQty;
	}

	public void setPlanQty(float planQty) {
		this.planQty = planQty;
	}

	public float getProductionQty() {
		return productionQty;
	}

	public void setProductionQty(float productionQty) {
		this.productionQty = productionQty;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	@Override
	public String toString() {
		return "ProdReportHeader [itemId=" + itemId + ", itemName=" + itemName + ", itemCode=" + itemCode + ", planQty="
				+ planQty + ", productionQty=" + productionQty + ", status=" + status + ", uomName=" + uomName + "]";
	}
	
	
	
	

}
