package com.ats.ssgs.model.mat;

public class GetVehDetail {

	private int matVehDetailId;

	private int matVehHeaderId;

	private float quantity;

	private int itemId;

	private float rate;

	private float value;

	private int uomId;

	private int delStatus;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private String exDate1;

	private int exBool1;

	private String uomName;
	private String itemDesc;
	private String itemCode;

	public int getMatVehDetailId() {
		return matVehDetailId;
	}

	public void setMatVehDetailId(int matVehDetailId) {
		this.matVehDetailId = matVehDetailId;
	}

	public int getMatVehHeaderId() {
		return matVehHeaderId;
	}

	public void setMatVehHeaderId(int matVehHeaderId) {
		this.matVehHeaderId = matVehHeaderId;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getUomId() {
		return uomId;
	}

	public void setUomId(int uomId) {
		this.uomId = uomId;
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

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Override
	public String toString() {
		return "GetVehDetail [matVehDetailId=" + matVehDetailId + ", matVehHeaderId=" + matVehHeaderId + ", quantity="
				+ quantity + ", itemId=" + itemId + ", rate=" + rate + ", value=" + value + ", uomId=" + uomId
				+ ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exDate1=" + exDate1 + ", exBool1=" + exBool1 + ", uomName=" + uomName
				+ ", itemDesc=" + itemDesc + ", itemCode=" + itemCode + "]";
	}

}
