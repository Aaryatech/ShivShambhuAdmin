package com.ats.ssgs.model.prodrm;

public class GetProdPlanDetail {

	private int productionDetailId;

	private int productionHeaderId;

	private int itemId;

	private float openingQty;
	private float planQty;
	private float productionQty;
	private float rejectedQty;

	private String productionBatch;

	private int status;
	private int delStatus;

	private int exInt1;
	private int exInt2;

	private int boolean1;
	private int boolean2;
	private int boolean3;
	private int boolean4;
	private int boolean5;

	private String exVar1;
	private String exVar2;
	private String exVar3;
	private String exVar4;
	
	//#
	private String itemName;
	private String itemCode;
	private String uomName;
	
	public int getProductionDetailId() {
		return productionDetailId;
	}
	public void setProductionDetailId(int productionDetailId) {
		this.productionDetailId = productionDetailId;
	}
	public int getProductionHeaderId() {
		return productionHeaderId;
	}
	public void setProductionHeaderId(int productionHeaderId) {
		this.productionHeaderId = productionHeaderId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public float getOpeningQty() {
		return openingQty;
	}
	public void setOpeningQty(float openingQty) {
		this.openingQty = openingQty;
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
	public float getRejectedQty() {
		return rejectedQty;
	}
	public void setRejectedQty(float rejectedQty) {
		this.rejectedQty = rejectedQty;
	}
	public String getProductionBatch() {
		return productionBatch;
	}
	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public int getBoolean1() {
		return boolean1;
	}
	public void setBoolean1(int boolean1) {
		this.boolean1 = boolean1;
	}
	public int getBoolean2() {
		return boolean2;
	}
	public void setBoolean2(int boolean2) {
		this.boolean2 = boolean2;
	}
	public int getBoolean3() {
		return boolean3;
	}
	public void setBoolean3(int boolean3) {
		this.boolean3 = boolean3;
	}
	public int getBoolean4() {
		return boolean4;
	}
	public void setBoolean4(int boolean4) {
		this.boolean4 = boolean4;
	}
	public int getBoolean5() {
		return boolean5;
	}
	public void setBoolean5(int boolean5) {
		this.boolean5 = boolean5;
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
	public String getExVar3() {
		return exVar3;
	}
	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}
	public String getExVar4() {
		return exVar4;
	}
	public void setExVar4(String exVar4) {
		this.exVar4 = exVar4;
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
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	
	@Override
	public String toString() {
		return "GetProdPlanDetail [productionDetailId=" + productionDetailId + ", productionHeaderId="
				+ productionHeaderId + ", itemId=" + itemId + ", openingQty=" + openingQty + ", planQty=" + planQty
				+ ", productionQty=" + productionQty + ", rejectedQty=" + rejectedQty + ", productionBatch="
				+ productionBatch + ", status=" + status + ", delStatus=" + delStatus + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", boolean1=" + boolean1 + ", boolean2=" + boolean2 + ", boolean3=" + boolean3
				+ ", boolean4=" + boolean4 + ", boolean5=" + boolean5 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + ", exVar4=" + exVar4 + ", itemName=" + itemName + ", itemCode=" + itemCode
				+ ", uomName=" + uomName + "]";
	}

}
