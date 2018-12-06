package com.ats.ssgs.model.prodrm;

public class ProdPlanDetail {

	private int productionDetailId;

	private int productionHeaderId;

	private int itemId;

	private float openingQty;
	private float planQty;
	private float productionQty;
	private float rejectedQty;

	private int status;
	private int delStatus;

	private int int4;
	private int int5;

	private int boolean1;
	private int boolean2;
	private int boolean3;
	private int boolean4;
	private int boolean5;

	private String varchar2;
	private String varchar3;
	private String varchar4;
	private String varchar5;

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

	public int getInt4() {
		return int4;
	}

	public void setInt4(int int4) {
		this.int4 = int4;
	}

	public int getInt5() {
		return int5;
	}

	public void setInt5(int int5) {
		this.int5 = int5;
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

	public String getVarchar2() {
		return varchar2;
	}

	public void setVarchar2(String varchar2) {
		this.varchar2 = varchar2;
	}

	public String getVarchar3() {
		return varchar3;
	}

	public void setVarchar3(String varchar3) {
		this.varchar3 = varchar3;
	}

	public String getVarchar4() {
		return varchar4;
	}

	public void setVarchar4(String varchar4) {
		this.varchar4 = varchar4;
	}

	public String getVarchar5() {
		return varchar5;
	}

	public void setVarchar5(String varchar5) {
		this.varchar5 = varchar5;
	}

	@Override
	public String toString() {
		return "ProdPlanDetail [productionDetailId=" + productionDetailId + ", productionHeaderId=" + productionHeaderId
				+ ", itemId=" + itemId + ", openingQty=" + openingQty + ", planQty=" + planQty + ", productionQty="
				+ productionQty + ", rejectedQty=" + rejectedQty + ", status=" + status + ", delStatus=" + delStatus
				+ ", int4=" + int4 + ", int5=" + int5 + ", boolean1=" + boolean1 + ", boolean2=" + boolean2
				+ ", boolean3=" + boolean3 + ", boolean4=" + boolean4 + ", boolean5=" + boolean5 + ", varchar2="
				+ varchar2 + ", varchar3=" + varchar3 + ", varchar4=" + varchar4 + ", varchar5=" + varchar5 + "]";
	}
}
